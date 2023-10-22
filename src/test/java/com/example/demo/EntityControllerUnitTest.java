
package com.example.demo;

import com.example.demo.controllers.DoctorController;
import com.example.demo.controllers.PatientController;
import com.example.demo.controllers.RoomController;
import com.example.demo.entities.Doctor;
import com.example.demo.entities.Patient;
import com.example.demo.entities.Room;
import com.example.demo.repositories.DoctorRepository;
import com.example.demo.repositories.PatientRepository;
import com.example.demo.repositories.RoomRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;

@WebMvcTest(DoctorController.class)
class DoctorControllerUnitTest {

    @MockBean
    private DoctorRepository doctorRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldFetchAllDoctors() throws Exception {
        Doctor doctor1 = new Doctor("John", "Doe", 30, "john.doe@example.com");
        Doctor doctor2 = new Doctor("Jane", "Doe", 28, "jane.doe@example.com");

        when(doctorRepository.findAll()).thenReturn(Arrays.asList(doctor1, doctor2));

        mockMvc.perform(get("/api/doctors")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].firstName", is("John")))
                .andExpect(jsonPath("$[1].firstName", is("Jane")));
    }

    @Test
    void shouldFetchDoctorById() throws Exception {
        Doctor doctor = new Doctor("John", "Doe", 30, "john.doe@example.com");
        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));

        mockMvc.perform(get("/api/doctors/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is("John")));
    }

    @Test
    void shouldReturn404WhenFetchingNonExistingDoctor() throws Exception {
        when(doctorRepository.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/doctors/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateNewDoctor() throws Exception {
        Doctor doctor = new Doctor("John", "Doe", 30, "john.doe@example.com");
        when(doctorRepository.save(any(Doctor.class))).thenReturn(doctor);

        mockMvc.perform(post("/api/doctor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(doctor)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is("John")));
    }

    @Test
    void shouldDeleteExistingDoctor() throws Exception {
        Doctor doctor = new Doctor("John", "Doe", 30, "john.doe@example.com");
        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));
        doNothing().when(doctorRepository).deleteById(1L);

        mockMvc.perform(delete("/api/doctors/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturn404WhenDeletingNonExistingDoctor() throws Exception {
        when(doctorRepository.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/doctors/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteAllDoctors() throws Exception {
        doNothing().when(doctorRepository).deleteAll();

        mockMvc.perform(delete("/api/doctors")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}


@WebMvcTest(PatientController.class)
class PatientControllerUnitTest {

    @MockBean
    private PatientRepository patientRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllPatients_shouldReturnListOfPatients() throws Exception {
        Patient patient1 = new Patient("John", "Doe", 25, "john.doe@example.com");
        Patient patient2 = new Patient("Jane", "Doe", 30, "jane.doe@example.com");
        when(patientRepository.findAll()).thenReturn(Arrays.asList(patient1, patient2));

        mockMvc.perform(get("/api/patients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].firstName", is("John")))
                .andExpect(jsonPath("$[1].firstName", is("Jane")));
    }

    @Test
    void getPatientById_shouldReturnPatient() throws Exception {
        Patient patient = new Patient("John", "Doe", 25, "john.doe@example.com");
        patient.setId(1L);
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));

        mockMvc.perform(get("/api/patients/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is("John")));
    }

    @Test
    void createPatient_shouldReturnCreatedPatient() throws Exception {
        Patient patient = new Patient("John", "Doe", 25, "john.doe@example.com");
        when(patientRepository.save(any(Patient.class))).thenReturn(patient);

        mockMvc.perform(post("/api/patient")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patient)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is("John")));
    }

    @Test
    void deletePatient_shouldReturnHttpStatusOk() throws Exception {
        Patient patient = new Patient("John", "Doe", 25, "john.doe@example.com");
        patient.setId(1L);
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));

        mockMvc.perform(delete("/api/patients/1"))
                .andExpect(status().isOk());
    }
}

@WebMvcTest(RoomController.class)
class RoomControllerUnitTest {

    @MockBean
    private RoomRepository roomRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllRooms_shouldReturnListOfRooms() throws Exception {
        Room room1 = new Room("Room A");
        Room room2 = new Room("Room B");
        when(roomRepository.findAll()).thenReturn(Arrays.asList(room1, room2));

        mockMvc.perform(get("/api/rooms"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].roomName", is("Room A")))
                .andExpect(jsonPath("$[1].roomName", is("Room B")));
    }

    @Test
    void getRoomByRoomName_shouldReturnRoom() throws Exception {
        Room room = new Room("Room A");
        when(roomRepository.findByRoomName("Room A")).thenReturn(Optional.of(room));

        mockMvc.perform(get("/api/rooms/Room A"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roomName", is("Room A")));
    }

    @Test
    void createRoom_shouldReturnCreatedRoom() throws Exception {
        Room room = new Room("Room A");
        when(roomRepository.save(any(Room.class))).thenReturn(room);

        mockMvc.perform(post("/api/room")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(room)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.roomName", is("Room A")));
    }

    @Test
    void deleteRoom_shouldReturnHttpStatusOk() throws Exception {
        Room room = new Room("Room A");
        when(roomRepository.findByRoomName("Room A")).thenReturn(Optional.of(room));

        mockMvc.perform(delete("/api/rooms/Room A"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteAllRooms_shouldReturnHttpStatusOk() throws Exception {
        mockMvc.perform(delete("/api/rooms"))
                .andExpect(status().isOk());
    }
}