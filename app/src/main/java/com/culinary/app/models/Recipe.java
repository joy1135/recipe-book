package com.culinary.app.models;

import java.util.List;

public class Recipe {
    private int id;
    private String name;
    private String description;
    private String category; // breakfast, lunch, dinner
    private String cuisine;  // italian, russian, german
    private List<String> ingredients;
    private List<String> steps;
    private int cookingTime; // minutes
    private int servings;
    private int imageResId;
    private int difficulty; // 1-3

    public Recipe(int id, String name, String description, String category,
                  String cuisine, List<String> ingredients, List<String> steps,
                  int cookingTime, int servings, int imageResId, int difficulty) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.cuisine = cuisine;
        this.ingredients = ingredients;
        this.steps = steps;
        this.cookingTime = cookingTime;
        this.servings = servings;
        this.difficulty = difficulty;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getCategory() { return category; }
    public String getCuisine() { return cuisine; }
    public List<String> getIngredients() { return ingredients; }
    public List<String> getSteps() { return steps; }
    public int getCookingTime() { return cookingTime; }
    public int getServings() { return servings; }
    public int getImageResId() { return imageResId; }
    public int getDifficulty() { return difficulty; }
}
