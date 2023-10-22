package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

import com.example.demo.entities.*;

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

        a1 = new Appointment(p1, d1, r1, LocalDateTime.of(2023, 4, 24, 9, 30), LocalDateTime.of(2023, 4, 24, 10, 30));
        a2 = new Appointment(p1, d1, r1, LocalDateTime.of(2023, 4, 24, 10, 30), LocalDateTime.of(2023, 4, 24, 11, 30));
        a3 = new Appointment(p1, d1, r1, LocalDateTime.of(2023, 4, 24, 11, 30), LocalDateTime.of(2023, 4, 24, 12, 30));
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

}
