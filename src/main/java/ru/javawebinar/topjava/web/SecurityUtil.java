package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.model.AbstractBaseEntity;

import static ru.javawebinar.topjava.util.UserUtil.DEFAULT_CALORIES_PER_DAY;

public class SecurityUtil {
    private static int id = AbstractBaseEntity.START_SEQ;
    private SecurityUtil() {
    }

    public static int authUserId() {
                return get().getUserTo().id();
    }

    public static int authUserCaloriesPerDay() {
                return get().getUserTo().getCaloriesPerDay();
    }
}