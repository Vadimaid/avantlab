package com.vadimaid.avantlab.controller;

import com.querydsl.core.types.Predicate;
import com.vadimaid.avantlab.dto.PersonDto;
import com.vadimaid.avantlab.entity.Person;
import com.vadimaid.avantlab.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/person")
public class PersonController {

    private final PersonService personService;

    @GetMapping(value = "/all")
    public ResponseEntity<Page<PersonDto>> findAll(
            @QuerydslPredicate(root = Person.class) Predicate predicate,
            Pageable pageable
    ){
        return ResponseEntity.ok(personService.findAll(predicate, pageable));
    }

    @GetMapping(value = "/{personId}")
    public ResponseEntity<PersonDto> findById(
            @PathVariable("personId") String personId
    ){
        return ResponseEntity.ok(personService.findById(personId));
    }

    @PostMapping(value = "/create")
    public ResponseEntity<PersonDto> createPerson(
            @RequestBody PersonDto source
    ){
        return ResponseEntity.ok(personService.create(source));
    }

    @PutMapping(value = "/{personId}/update")
    public ResponseEntity<PersonDto> updatePerson(
            @PathVariable("personId") String personId,
            @RequestBody PersonDto source
    ){
        return ResponseEntity.ok(personService.update(personId, source));
    }

    @DeleteMapping(value = "/{personId}/delete")
    public ResponseEntity<String> deletePerson(
            @PathVariable("personId") String personId
    ) {
        personService.delete(personId);
        return ResponseEntity.ok(personId + " is deleted!");
    }

    @GetMapping(value = "/flux", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux feed(
            @QuerydslPredicate(root = Person.class) Predicate predicate,
            Pageable pageable
    ) {
        return personService.findAllAsync(predicate, pageable);
    }

}
