package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealsRepository {
    Meal addMeal(Meal meal);
    void deleteMeal(int mealId);
    Meal updateMeal(Meal meal);
    List<Meal> getAllMeals();
    Meal getMealById(int mealId);
}
