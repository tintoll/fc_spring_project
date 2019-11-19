package com.fc.project3.mycontact.controller;

import com.fc.project3.mycontact.domain.Person;
import com.fc.project3.mycontact.domain.dto.PersonDto;
import com.fc.project3.mycontact.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping("/{id}")
    public Person getPerson(@PathVariable Long id) {
        return personService.getPerson(id);
    }

    @PostMapping
    public void createPerson(@RequestBody PersonDto personDto) {
        personService.create(personDto);
    }

    @PutMapping("/{id}")
    public void updatePerson(@PathVariable Long id, @RequestBody PersonDto personDto) {
        personService.modify(id, personDto);
    }

    @PatchMapping("/{id}")
    public void updateName(@PathVariable Long id, String name) {
        personService.updateName(id, name);
    }

    @DeleteMapping("/{id}")
    public void deltePerson(@PathVariable Long id) {
        personService.delete(id);
    }
}
