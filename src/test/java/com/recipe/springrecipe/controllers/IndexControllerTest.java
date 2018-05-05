package com.recipe.springrecipe.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import com.recipe.controllers.IndexController;
import com.recipe.services.RecipeService;

public class IndexControllerTest {

	IndexController indexController;
	
	@Mock
	RecipeService recipeService;
	
	@Mock
	Model model;
	
	@Before
	public void setUp() throws Exception {
	MockitoAnnotations.initMocks(this);
	indexController = new IndexController(recipeService);
	}

	@Test
	public void testGetIndexPage() {
	assertEquals("index", indexController.getIndexPage(model));
	verify(model, times(1)).addAttribute(eq("recipes"), anySet());
	verify(recipeService, times(1)).getRecipes();
	}

}
