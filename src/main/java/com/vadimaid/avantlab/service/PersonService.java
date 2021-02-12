package com.vadimaid.avantlab.service;

import com.querydsl.core.types.Predicate;
import com.vadimaid.avantlab.dto.PersonDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;

public interface PersonService {

    Page<PersonDto> findAll(Predicate predicate, Pageable pageable);
    PersonDto findById(String personId);
    PersonDto create(PersonDto source);
    PersonDto update(String personId, PersonDto source);
    void delete(String personId);
    Flux findAllAsync(Predicate predicate, Pageable pageable);

}
