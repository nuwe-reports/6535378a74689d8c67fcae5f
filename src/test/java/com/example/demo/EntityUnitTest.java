package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

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
@AutoConfigureTestDatabase(replace=Replace.NONE)
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
}
