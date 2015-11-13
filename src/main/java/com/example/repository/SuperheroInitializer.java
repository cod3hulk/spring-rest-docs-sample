package com.example.repository;

import com.example.domain.Superhero;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Helper class for initializing test data
 */
@Component
public class SuperheroInitializer implements InitializingBean {

    private final SuperheroRepository superheroRepo;

    @Autowired
    public SuperheroInitializer(SuperheroRepository superheroRepo) {
        this.superheroRepo = superheroRepo;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Superhero wolverine = new Superhero("Wolverine", "Marvel");
        Superhero theFlash = new Superhero("The Flash", "DC");

        superheroRepo.save(wolverine);
        superheroRepo.save(theFlash);
    }
}
