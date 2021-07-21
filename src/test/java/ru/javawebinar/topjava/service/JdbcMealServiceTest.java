package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;

import java.time.LocalDate;
import java.time.Month;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.MealTestData.meals;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ActiveProfiles(Profiles.JDBC)
public class JdbcMealServiceTest extends AbstractMealServiceTest{
    @Override
    @Test
    public void getBetweenInclusive() {
        MATCHER.assertMatch(service.getBetweenInclusive(
                LocalDate.of(2020, Month.JANUARY, 30),
                LocalDate.of(2020, Month.JANUARY, 30), USER_ID),
                meal3, meal2, meal1);
    }
    @Override
    @Test
    public void getBetweenWithNullDates() {
        MATCHER.assertMatch(service.getBetweenInclusive(null, null, USER_ID), meals);
    }
}
