package com.fc.project3.mycontact.service;

import com.fc.project3.mycontact.domain.Person;
import com.fc.project3.mycontact.domain.dto.PersonDto;
import com.fc.project3.mycontact.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public void create(PersonDto personDto) {
        Person person = new Person();
        person.set(personDto);
        person.setName(personDto.getName());

        personRepository.save(person);
    }

    @Transactional
    public void modify(Long id, PersonDto personDto) {
        Person person = personRepository.findById(id).orElseThrow( () -> new RuntimeException("아이디가 없습니다."));

        if (!person.getName().equals(personDto.getName())) {
            throw new RuntimeException("이름이 동일하지 않습니다.");
        }

        person.set(personDto);

        personRepository.save(person);
    }

    @Transactional
    public void updateName(Long id, String name) {
        Person person = personRepository.findById(id).orElseThrow( () -> new RuntimeException("아이디가 없습니다."));
        person.setName(name);

        personRepository.save(person);
    }

    @Transactional
    public void delete(Long id) {
        Person person = personRepository.findById(id).orElseThrow( () -> new RuntimeException("아이디가 없습니다."));

        person.setDeleted(true);

        personRepository.save(person);
    }
}
