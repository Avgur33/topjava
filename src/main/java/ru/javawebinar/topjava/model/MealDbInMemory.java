package ru.javawebinar.topjava.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealDbInMemory {
    private final ConcurrentMap<Integer,Meal> mealsDb;
    private final AtomicInteger cntId;

    public MealDbInMemory() {
        this.cntId = new AtomicInteger();
        cntId.set(0);
        this. mealsDb = new ConcurrentHashMap<>();
    }

    public Meal addMeal(Meal meal){
        meal.setId(cntId.incrementAndGet());
        return mealsDb.putIfAbsent(meal.getId(), meal);
    }

    public void deleteMeal(Meal meal){
        mealsDb.remove(meal.getId(),meal);
    }

    public void deleteMealById(int id){
        mealsDb.remove(id);
    }

    public Meal update(Meal meal){
        return mealsDb.replace(meal.getId(), meal);
    }

    public Meal findById(int id){
        return mealsDb.get(id);
    }

    public List<Meal> getAllMeals(){
        return new ArrayList<>(mealsDb.values());
    }
}
