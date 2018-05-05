package com.recipe.springrecipe.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import com.recipe.controllers.IndexController;
import com.recipe.domain.Recipe;
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
		
	//given 
	Set<Recipe>	recipes = new HashSet<>();
	recipes.add(new Recipe());
	
	Recipe recipe = new Recipe();
	recipe.setId(1L);
	recipes.add(recipe);
	
	//when
	when(recipeService.getRecipes()).thenReturn(recipes);
	
	ArgumentCaptor<Set<Recipe>> argumentCaptor = ArgumentCaptor.forClass(Set.class);
		
	//then 
	assertEquals("index", indexController.getIndexPage(model));
	verify(model, times(1)).addAttribute(eq("recipes"), argumentCaptor.capture());
	verify(recipeService, times(1)).getRecipes();
	Set<Recipe> setInController = argumentCaptor.getValue();
	assertEquals(2,setInController.size());
	}

}
