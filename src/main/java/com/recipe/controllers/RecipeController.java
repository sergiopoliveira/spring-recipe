package com.recipe.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.recipe.commands.RecipeCommand;
import com.recipe.exceptions.NotFoundException;
import com.recipe.services.RecipeService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class RecipeController {

	private final RecipeService recipeService;

	@Autowired
	public RecipeController(RecipeService recipeService) {
		this.recipeService = recipeService;
	}
	
	@GetMapping("/recipe/{id}/show")
	public String showById(@PathVariable String id, Model model) {
		
		model.addAttribute("recipe", recipeService.findById(new Long(id)));
		
		return "recipe/show";
	}
	
	@GetMapping("/recipe/new")
	public String newRecipe(Model model) {
		
		model.addAttribute("recipe", new RecipeCommand());
		
		return "recipe/recipeform";
	}
	
	@GetMapping("recipe/{id}/update")
	public String updateRecipe(@PathVariable String id, Model model) {
		model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));
		return "recipe/recipeform";
	}
	
	@PostMapping("recipe")
	public String saveOrUpdate(@ModelAttribute RecipeCommand command) {
		RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);
		
		return "redirect:/recipe/" + savedCommand.getId() + "/show";
	}
	
	@GetMapping("recipe/{id}/delete")
	public String deleteById(@PathVariable String id) {
		
		log.debug("Deleting id: " + id);
		
		recipeService.deleteById(Long.valueOf(id));
		return "redirect:/";
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(NotFoundException.class)
	public ModelAndView handleNotFound(Exception e) {
		
		log.error("Handling not found exception");
		log.error(e.getMessage());
		
		ModelAndView modelAndView = new ModelAndView();
		
		modelAndView.setViewName("404error");
		modelAndView.addObject("e", e);
		
		return modelAndView;
	}
	
}
