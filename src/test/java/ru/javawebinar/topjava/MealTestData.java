package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;
    public static final int NOT_FOUND = 10;

    public static final int USERMEALID = 100002;
    public static final int ADMINMEALID = 100005;

    public static final Meal adminMeal = new Meal(100005, LocalDateTime.of(2021, Month.JUNE, 18, 9, 0), "admin_breakfast", 700);

    public static final List<Meal> USERMEALS = Stream.of(
            new Meal(100002, LocalDateTime.of(2021, Month.JUNE, 20, 9, 0), "user_breakfast", 500),
            new Meal(100003, LocalDateTime.of(2021, Month.JUNE, 20, 14, 0), "user_dinner", 600),
            new Meal(100004, LocalDateTime.of(2021, Month.JUNE, 20, 19, 0), "user_supper", 700)
    ).sorted(Comparator.comparing(Meal::getDateTime).reversed()).collect(Collectors.toList());

/*
    public static final List<Meal> ADMINMEALS = Stream.of(
            new Meal(100005, LocalDateTime.of(2021, Month.JUNE, 18, 9, 0), "admin_breakfast", 700),
            new Meal(100006, LocalDateTime.of(2021, Month.JUNE, 18, 14, 0), "admin_dinner", 800),
            new Meal(100007, LocalDateTime.of(2021, Month.JUNE, 18, 19, 0), "admin_supper", 900)
    ).sorted(Comparator.comparing(Meal::getDateTime).reversed()).collect(Collectors.toList());
*/

    public static Meal getNew() {
        return new Meal( LocalDateTime.of(2021, Month.JUNE, 18, 7, 0), "admin_new", 700);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(adminMeal);
        updated.setDescription("apdated meal");
        updated.setDateTime(LocalDateTime.of(2021, Month.JUNE, 01, 00, 0));
        updated.setCalories(2000);
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

}
