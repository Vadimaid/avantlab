package com.vadimaid.avantlab.mapper;

import com.vadimaid.avantlab.dto.PersonDto;
import com.vadimaid.avantlab.entity.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class PersonMapper {

    public PersonDto toDto(Person source) {
        PersonDto dto = new PersonDto();
        dto.setId(source.getId());
        dto.setFirstName(source.getFirstName());
        dto.setLastName(source.getLastName());
        dto.setEmail(source.getEmail());
        dto.setPhoneNumber(source.getPhoneNumber());
        return dto;
    }

    public Person toEntity(PersonDto source) {
        return Person
                .builder()
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .email(source.getEmail())
                .phoneNumber(source.getPhoneNumber())
                .build();
    }

    public Page<PersonDto> toPage(List<Person> source, Pageable pageable) {
        return new PageImpl<>(toDtoList(source), pageable, source.size());
    }

    public List<PersonDto> toDtoList(List<Person> source) {
        return source
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<Person> toList(Iterable<Person> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }
}
