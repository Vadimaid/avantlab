package com.vadimaid.avantlab.service.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.vadimaid.avantlab.dto.PersonDto;
import com.vadimaid.avantlab.entity.Person;
import com.vadimaid.avantlab.mapper.PersonMapper;
import com.vadimaid.avantlab.repository.PersonRepository;
import com.vadimaid.avantlab.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;

    @Override
    public Page<PersonDto> findAll(Predicate predicate, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder(predicate);

        List<Person> personList =
                Objects.isNull(builder.getValue()) ?
                        personRepository.findAll() :
                        personMapper.toList(personRepository.findAll(builder.getValue()));

        return personMapper.toPage(personList, pageable);
    }

    @Override
    public PersonDto findById(String personId) {
        Person person = getPersonById(personId);
        return personMapper.toDto(person);
    }

    @Override
    public PersonDto create(PersonDto source) {
        Person person = personMapper.toEntity(source);
        personRepository.save(person);
        return personMapper.toDto(person);
    }

    @Override
    public PersonDto update(String personId, PersonDto source) {
        Person updatingEntity = getPersonById(personId);

        updatingEntity.setFirstName(source.getFirstName());
        updatingEntity.setLastName(source.getLastName());
        updatingEntity.setEmail(source.getEmail());
        updatingEntity.setPhoneNumber(source.getPhoneNumber());

        personRepository.save(updatingEntity);

        return personMapper.toDto(updatingEntity);
    }

    @Override
    public void delete(String personId) {
        Person person = getPersonById(personId);
        personRepository.delete(person);
    }

    @Override
    public Flux findAllAsync(Predicate predicate, Pageable pageable) {
        return Flux
                .interval(Duration.ofSeconds(1))
                .map(x -> this.getActualData(predicate, pageable));
    }

    private Person getPersonById(String personId) {
        Optional<Person> person = personRepository.findById(personId);
        if (!person.isPresent()) {
            throw new IllegalArgumentException("Person with ID " + personId + " not found.");
        }
        return person.get();
    }

    private Page<PersonDto> getActualData(Predicate predicate, Pageable pageable) {
        List<Person> personList = personMapper.toList(personRepository.findAll(predicate));
        return personMapper.toPage(personList, pageable);
    }
}
