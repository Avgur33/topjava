package ru.javawebinar.topjava.web.meal;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.javawebinar.topjava.to.MealTo;

import java.util.List;

@RestController
@RequestMapping(value = "/ui/meals", produces = MediaType.APPLICATION_JSON_VALUE)
public class MealUIController extends AbstractMealController {
    @Override
    @GetMapping
    public List<MealTo> getAll() {
        return super.getAll();
    }
}
