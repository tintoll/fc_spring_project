package com.fc.project3.mycontact.repository;

import com.fc.project3.mycontact.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Long> {


    List<Person> findByBlockIsNull();

    List<Person> findByBirthDayBetween(LocalDate startDate, LocalDate endDate);

}
