package com.example.demo;

import com.example.demo.entities.Appointment;
import com.example.demo.entities.Doctor;
import com.example.demo.entities.Patient;
import com.example.demo.entities.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestInstance(Lifecycle.PER_CLASS)
class EntityUnitTest {

    @Autowired
    private TestEntityManager entityManager;

    private Doctor d1;

    private Patient p1;

    private Room r1;

    private Appointment a1;
    private Appointment a2;
    private Appointment a3;

    @BeforeEach
    void setUp() {
        d1 = new Doctor("John", "Doe", 30, "john.doe@example.com");
        p1 = new Patient("Jane", "Doe", 25, "jane.doe@example.com");
        r1 = new Room("Room A");

        a1 = new Appointment(p1, d1, r1, LocalDateTime.of(2023, 4, 24, 9, 30), LocalDateTime.of(2023, 4, 24, 10, 45));
        a2 = new Appointment(p1, d1, r1, LocalDateTime.of(2023, 4, 24, 10, 30), LocalDateTime.of(2023, 4, 24, 11, 30));
        a3 = new Appointment(p1, d1, r1, LocalDateTime.of(2023, 4, 24, 11, 15), LocalDateTime.of(2023, 4, 24, 12, 30));
    }

    @Test
    void testDoctorEntity() {
        entityManager.persistAndFlush(d1);
        Doctor foundDoctor = entityManager.find(Doctor.class, d1.getId());
        assertEquals(d1.getFirstName(), foundDoctor.getFirstName());
        assertEquals(d1.getLastName(), foundDoctor.getLastName());
        assertEquals(d1.getAge(), foundDoctor.getAge());
        assertEquals(d1.getEmail(), foundDoctor.getEmail());

    }

    @Test
    void testPatientEntity() {
        entityManager.persistAndFlush(p1);
        Patient foundPatient = entityManager.find(Patient.class, p1.getId());
        assertEquals(p1.getFirstName(), foundPatient.getFirstName());
        assertEquals(p1.getLastName(), foundPatient.getLastName());
        assertEquals(p1.getAge(), foundPatient.getAge());
        assertEquals(p1.getEmail(), foundPatient.getEmail());

    }

    @Test
    void testRoomEntity() {
        entityManager.persistAndFlush(r1);
        Room foundRoom = entityManager.find(Room.class, r1.getRoomName());
        assertEquals(r1.getRoomName(), foundRoom.getRoomName());

    }

    @Test
    void testAppointmentInitialization() {
        entityManager.persistAndFlush(a1);
        Appointment foundAppointment = entityManager.find(Appointment.class, a1.getId());
        assertEquals(a1.getStartsAt(), foundAppointment.getStartsAt());
        assertEquals(a1.getFinishesAt(), foundAppointment.getFinishesAt());
        assertEquals(a1.getDoctor(), foundAppointment.getDoctor());
        assertEquals(a1.getPatient(), foundAppointment.getPatient());
        assertEquals(a1.getRoom(), foundAppointment.getRoom());

    }

    @Test
    void testAppointmentOverlaps() {
        assertTrue(a1.overlaps(a2));
        assertTrue(a2.overlaps(a3));
        assertFalse(a1.overlaps(a3));

    }

    @Test
    void testGettersAndSetters() {
        Appointment appointment = new Appointment();
        appointment.setDoctor(d1);
        appointment.setPatient(p1);
        appointment.setRoom(r1);
        appointment.setStartsAt(LocalDateTime.of(2023, 4, 24, 9, 30));
        appointment.setFinishesAt(LocalDateTime.of(2023, 4, 24, 10, 30));

        assertEquals(d1, appointment.getDoctor());
        assertEquals(p1, appointment.getPatient());
        assertEquals(r1, appointment.getRoom());
        assertEquals(LocalDateTime.of(2023, 4, 24, 9, 30), appointment.getStartsAt());
        assertEquals(LocalDateTime.of(2023, 4, 24, 10, 30), appointment.getFinishesAt());
    }

}
