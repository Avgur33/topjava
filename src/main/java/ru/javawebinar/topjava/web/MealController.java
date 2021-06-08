package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealsRepository;
import ru.javawebinar.topjava.repository.MealsRepositoryImpl;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;

import static org.slf4j.LoggerFactory.getLogger;

public class MealController extends HttpServlet {
    private final Logger log;
    private final Integer caloriesPerDay;
    private final MealsRepository mealsRepository;

    private static String INSERT_OR_UPDATE = "/meal.jsp";
    private static String LIST_MEAL = "/mealscrud.jsp";

    public MealController() {
        super();
        this.log = getLogger(MealController.class);
        this.caloriesPerDay = 2000;
        this.mealsRepository = new MealsRepositoryImpl();
        mealsRepository.addMeal(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        mealsRepository.addMeal(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        mealsRepository.addMeal(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        mealsRepository.addMeal(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        mealsRepository.addMeal(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        mealsRepository.addMeal(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        mealsRepository.addMeal(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward;
        String action = request.getParameter("action");
        action = action != null? action: "default";
        switch (action.toLowerCase()) {
            case ("delete"):
                int mealId = Integer.parseInt(request.getParameter("mealId"));
                mealsRepository.deleteMeal(mealId);
                forward = LIST_MEAL;
                request.setAttribute("meals", MealsUtil.filteredByStreams(mealsRepository.getAllMeals(), LocalTime.MIN, LocalTime.MAX, caloriesPerDay));
                break;
            case ("listmeal"):
                forward = LIST_MEAL;
                request.setAttribute("meals", MealsUtil.filteredByStreams(mealsRepository.getAllMeals(), LocalTime.MIN, LocalTime.MAX, caloriesPerDay));
                break;
            case ("update"):
                forward = INSERT_OR_UPDATE;
                Meal meal = mealsRepository.getMealById(Integer.parseInt(request.getParameter("mealId")));
                request.setAttribute("meal", meal);
                break;
            default:
                forward = INSERT_OR_UPDATE;
                break;
        }
        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String description = request.getParameter("description");
        String strCalories = request.getParameter("calories");
        int calories = strCalories!=null? Integer.parseInt(strCalories):0;

        LocalDateTime localDateTime = LocalDateTime.parse(request.getParameter("dateTime"));

        Meal meal = new Meal(localDateTime,description,calories);
        try {
            meal.setId(Integer.parseInt(request.getParameter("id")));
            mealsRepository.updateMeal(meal);
        }catch(NumberFormatException numberFormatException){
            mealsRepository.addMeal(meal);
        }

        RequestDispatcher view = request.getRequestDispatcher(LIST_MEAL);

        request.setAttribute("meals", MealsUtil.filteredByStreams(mealsRepository.getAllMeals(), LocalTime.MIN, LocalTime.MAX, caloriesPerDay));
        view.forward(request, response);
    }
}
