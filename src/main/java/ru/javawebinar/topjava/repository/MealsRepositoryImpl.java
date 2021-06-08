package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealDbInMemory;

import java.util.List;

public class MealsRepositoryImpl implements MealsRepository {

    private final MealDbInMemory mealDb;

    public MealsRepositoryImpl() {
        mealDb = new MealDbInMemory();
    }

    public void deleteMeal(int mealId){
        mealDb.deleteMealById(mealId);
    }
    public Meal addMeal(Meal meal){
        return mealDb.addMeal(meal);
    }
    public Meal getMealById(int mealId){return mealDb.findById(mealId);}
    public Meal updateMeal(Meal meal){ return mealDb.update(meal);}
    public List<Meal> getAllMeals(){return mealDb.getAllMeals();}
}
