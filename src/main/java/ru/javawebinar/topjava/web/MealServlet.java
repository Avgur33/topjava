package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.inmemory.InMemoryMealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Objects;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

    private MealRepository repository;
    //init spring
    private ConfigurableApplicationContext appCtx;
    private MealRestController mealRestController;
    private AdminRestController adminUserController;
    @Override
    public void init() {
         appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
         System.out.println("Bean definition names: ");
         Arrays.stream(appCtx.getBeanDefinitionNames()).forEachOrdered(System.out::println);
         adminUserController = appCtx.getBean(AdminRestController.class);
         adminUserController.create(new User(null, "userName", "email@mail.ru", "password", Role.ADMIN));
         mealRestController = appCtx.getBean(MealRestController.class);
         mealRestController.getAll().forEach(System.out::println);

         repository = new InMemoryMealRepository();
    }

    @Override
    public void destroy() {
        appCtx.close();
        super.destroy();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");

        Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                id.isEmpty() ? null : Integer.parseInt(request.getParameter("userId")),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));

        log.info(meal.isNew() ? "Create {}" : "Update {}", meal);

        if (meal.isNew()) {
            mealRestController.create(meal);
        } else {
            mealRestController.update(meal, meal.getId());
        }
        //repository.save(meal,1);
        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                log.info("Delete {}", id);

                //ToDo check all
                mealRestController.delete(id);
                //repository.delete(id, 1);

                response.sendRedirect("meals");
                break;
            case "create":
            case "update":
                final Meal meal = "create".equals(action) ?
                        new Meal(null,LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                        mealRestController.get(getId(request));
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                break;
            case "all":
            default:
                log.info("getAll");
                //ToDo check all
                request.setAttribute("meals", mealRestController.getAll());
                /*request.setAttribute("meals",
                        MealsUtil.getTos(repository.getAll(1), MealsUtil.DEFAULT_CALORIES_PER_DAY));*/
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
