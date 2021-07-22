package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;
import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
@RequestMapping(value = "/meals")
public class JspMealController {

    private final MealService service;

    @Autowired
    public JspMealController(MealService service) {
        this.service = service;
    }


    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }

/*    @DeleteMapping()
    public String delMeal(HttpServletRequest request){
        int id = getId(request);
        int userId = SecurityUtil.authUserId();
        service.delete(id, userId);
        return "meals";
    }*/

    @PostMapping("/create")
    public String createMeal(Model model,HttpServletRequest request){
        String id = request.getParameter("id");
        Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));
        checkNew(meal);
        service.create(meal, SecurityUtil.authUserId());
        return "redirect:/meals";
    }
    @PostMapping("/update")
    public String updateMeal(Model model,HttpServletRequest request){
        String id = request.getParameter("id");
        Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));
        assureIdConsistent(meal, meal.getId());
        service.update(meal, SecurityUtil.authUserId());
        return "redirect:/meals";
    }

    @PostMapping
    public String postMeals(Model model,HttpServletRequest request, HttpServletResponse response){
        String id = request.getParameter("id");
        Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));

        int userId = SecurityUtil.authUserId();

        if (meal.isNew()) {
            checkNew(meal);
            service.create(meal, userId);
        } else {
            assureIdConsistent(meal, meal.getId());
            service.update(meal, userId);
        }
        return "redirect:meals";
    }

    @GetMapping()
    public String getMeals(Model model,HttpServletRequest request){
        int userId = SecurityUtil.authUserId();
        String action = request.getParameter("action");
        switch (action == null ? "all" : action) {
            case "delete" -> {
                int id = getId(request);
                service.delete(id, userId);
                return "redirect:meals";
            }
            case "create", "update" -> {
                final Meal meal = "create".equals(action) ?
                        new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                        service.get(getId(request), userId);
                model.addAttribute("meal", meal);
                return "mealForm";
            }
            case "filter" -> {
                LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
                LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
                LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
                LocalTime endTime = parseLocalTime(request.getParameter("endTime"));
                List<Meal> mealsDateFiltered = service.getBetweenInclusive(startDate, endDate, userId);
                model.addAttribute("meals", MealsUtil.getFilteredTos(mealsDateFiltered, SecurityUtil.authUserCaloriesPerDay(), startTime, endTime));
                return "meals";
            }
            default -> {
                model.addAttribute("meals", MealsUtil.getTos(service.getAll(userId), SecurityUtil.authUserCaloriesPerDay()));
                return "meals";
            }
        }
    }
}
