package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealsRepository;
import ru.javawebinar.topjava.repository.MealsRepositoryImpl;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;

import static org.slf4j.LoggerFactory.getLogger;

public class MealController extends HttpServlet {
    private final Logger log;
    private final Integer caloriesPerDay;
    private final MealsRepository mealsRepository;

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

        String action = request.getParameter("action");
        action = action != null? action: "default";

        String LIST_MEAL = "/mealscrud.jsp";
        String INSERT_OR_UPDATE = "/meal.jsp";
        switch (action.toLowerCase()) {
            case ("delete"):
                int mealId = Integer.parseInt(request.getParameter("mealId"));
                mealsRepository.deleteMeal(mealId);
                request.setAttribute("meals", MealsUtil.filteredByStreams(mealsRepository.getAllMeals(), LocalTime.MIN, LocalTime.MAX, caloriesPerDay));
                request.getRequestDispatcher(LIST_MEAL).forward(request, response);
                break;
            case ("create"):
                response.sendRedirect("meal.jsp");
                //request.getRequestDispatcher(INSERT_OR_UPDATE).forward(request, response);
                break;
            case ("update"):
                Meal meal = mealsRepository.getMealById(Integer.parseInt(request.getParameter("mealId")));
                request.setAttribute("meal", meal);
                request.getRequestDispatcher(INSERT_OR_UPDATE).forward(request, response);
                break;
            default:
                request.setAttribute("meals", MealsUtil.filteredByStreams(mealsRepository.getAllMeals(), LocalTime.MIN, LocalTime.MAX, caloriesPerDay));
                request.getRequestDispatcher(LIST_MEAL).forward(request, response);
                break;
        }
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
        response.sendRedirect("mealscrud");
    }
}
