package com.fc.project3.mycontact.repository;

import com.fc.project3.mycontact.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Long> {


    List<Person> findByBlockIsNull();

    @Query("select person from Person person where person.birthDay.monthOfBirthday = ?1")
    List<Person> findByBirthDay(int month);

    @Query("select person from Person person where person.birthDay.monthOfBirthday = :month")
    List<Person> findByBirthDayParam(@Param("month") int month);

    @Query(value = "select * from person where month_of_birthday = :month", nativeQuery = true)
    List<Person> findByBirthDayNative(@Param("month") int month);
}
