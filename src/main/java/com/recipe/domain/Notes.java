package com.recipe.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(exclude = {"recipe"})
@Entity
public class Notes {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// not specifying cascade: that means that, if notes is deleted, then the recipe
	// is NOT deleted.
	@OneToOne
	private Recipe recipe;

	@Lob
	private String recipeNotes;
}
