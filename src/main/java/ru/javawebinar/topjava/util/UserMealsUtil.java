package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );
        UserMealsUtil zaceni = new UserMealsUtil();
        List<UserMealWithExcess> mealsTo = zaceni.filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

        System.out.println("-----");
        mealsTo = zaceni.filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

        System.out.println("-----");
        mealsTo = zaceni.filteredByStream(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);
    }

    public List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate, Integer> caloriesOfDay = new HashMap<>();
        meals.forEach(x -> caloriesOfDay.merge(x.getDate(), x.getCalories(), Integer::sum));

        List<UserMealWithExcess> list = new ArrayList<>();
        meals.forEach(x -> {
            if (TimeUtil.isBetweenHalfOpen(x.getDateTime().toLocalTime(), startTime, endTime)) {
                list.add(createUserMealWithExcess(x, caloriesOfDay.get(x.getDate()) > caloriesPerDay));
            }
        });

        return list;
    }

    public List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate, Integer> caloriesOfDay = meals.stream()
                .collect(Collectors.groupingBy(UserMeal::getDate, Collectors.summingInt(UserMeal::getCalories)));

        return meals.stream()
                .filter(x -> TimeUtil.isBetweenHalfOpen(x.getTime(), startTime, endTime))
                .map(x -> createUserMealWithExcess(x, caloriesOfDay.get(x.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    public UserMealWithExcess createUserMealWithExcess(UserMeal meal, Boolean excess) {
        return new UserMealWithExcess(meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
    }

    public List<UserMealWithExcess> filteredByStream(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        return meals.stream().collect(new MyCollector(caloriesPerDay, startTime, endTime));
    }

    public class MyCollector implements Collector<UserMeal, List<UserMeal>, List<UserMealWithExcess>> {
        Map<LocalDate, Integer> caloriesPerDay;
        Integer kal;
        LocalTime start;
        LocalTime stop;

        public MyCollector(Integer kal, LocalTime start, LocalTime stop) {
            this.kal = kal;
            this.caloriesPerDay = new HashMap<>();
            this.start = start;
            this.stop = stop;
        }

        @Override
        public Supplier<List<UserMeal>> supplier() {
            return ArrayList::new;
        }

        @Override
        public BiConsumer<List<UserMeal>, UserMeal> accumulator() {
            return (s, b) -> {
                caloriesPerDay.merge(b.getDate(), b.getCalories(), Integer::sum);
                if (TimeUtil.isBetweenHalfOpen(b.getTime(), start, stop)) {
                    s.add(b);
                }
            };
        }

        @Override
        public BinaryOperator<List<UserMeal>> combiner() {
            return (l, r) -> {
                l.addAll(r);
                return l;
            };
        }

        @Override
        public Function<List<UserMeal>, List<UserMealWithExcess>> finisher() {
            return (r) -> r.stream()
                    .map(x -> createUserMealWithExcess(x, caloriesPerDay.get(x.getDate()) > kal))
                    .collect(Collectors.toList());
        }

        @Override
        public Set<Characteristics> characteristics() {
            return EnumSet.of(Characteristics.CONCURRENT);
        }
    }
}
