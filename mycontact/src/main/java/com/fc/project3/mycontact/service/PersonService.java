package com.fc.project3.mycontact.service;

import com.fc.project3.mycontact.domain.Person;
import com.fc.project3.mycontact.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;


    public List<Person> getPersonExcludeBlock() {
        //List<Person> people = personRepository.findAll();
        //return people.stream().filter(person -> person.getBlock() == null).collect(Collectors.toList());
        return personRepository.findByBlockIsNull();
    }

    public Person getPerson(Long id) {
        return personRepository.findById(id).orElse(null);
    }
}
