package ru.javawebinar.topjava.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;


@Repository
@Transactional(readOnly = true)
public class JpaMealRepository implements MealRepository {



    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        if (meal.isNew() ){
            meal.setUser(new User());
            meal.getUser().setId(userId);
            em.persist(meal);
            return meal;
        }else {
            Meal curMeal = em.find(Meal.class, meal.id());
            if (curMeal.getUser().id()!= userId) { throw new NotFoundException("invalid user_id");}
            meal.setUser(new User());
            meal.getUser().setId(userId);
            return em.merge(meal);
        }
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        return em.createNamedQuery(Meal.DELETE)
                .setParameter("id", id)
                .setParameter("user_id",userId)
                .executeUpdate() != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        Meal meal = em.find(Meal.class, id);
        if (meal == null) {throw new NotFoundException("invalid user_id");}
        if (meal.getUser().id() != userId) {throw new NotFoundException("invalid user_id");}
        return meal;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return (List<Meal>) em.createNamedQuery(Meal.ALL_SORTED)
                .setParameter("user_id", userId)
                .getResultList();
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {

        return (List<Meal>) em.createNamedQuery(Meal.ALL_BETWEEN)
                .setParameter("user_id", userId)
                .setParameter("start_date", startDateTime)
                .setParameter("stop_date", endDateTime)
                .getResultList();
    }
}