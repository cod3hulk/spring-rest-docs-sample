package com.example.repository;

import com.example.domain.Superhero;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Repository for storing and retrieving Superheroes
 */
public interface SuperheroRepository extends PagingAndSortingRepository<Superhero, Long> {
}
