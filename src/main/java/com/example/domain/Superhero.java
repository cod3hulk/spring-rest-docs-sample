package com.example.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;



/**
 * Domain object representing a Superhero
 */
@Entity
public class Superhero {

	@Id
	@GeneratedValue(
			strategy = GenerationType.AUTO)
	private Long id;

	private String name;

	private String universe;

	Superhero() {
		// used by jpa
	}

	public Superhero(String name, String universe) {
		this.name = name;
		this.universe = universe;
	}

	public String getName() {
		return name;
	}

	public String getUniverse() {
		return universe;
	}
}
