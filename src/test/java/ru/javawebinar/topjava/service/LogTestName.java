package ru.javawebinar.topjava.service;

import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Log the currently running test.
 *
 * <p>Typical usage:
 *
 * <p>{@code @Rule public LogTestName logTestName = new LogTestName();}
 *
 * <p>See also:
 * <br>{@link org.junit.Rule}
 * <br>{@link org.junit.rules.TestWatcher}
 */
public class LogTestName implements TestRule {
    @Override
    public Statement apply(Statement base, Description description) {
        return new Statement() {

            @Override
            public void evaluate() throws Throwable {
                System.out.println("==================================================before==================================================");
                base.evaluate();
                System.out.println("==================================================after==================================================");
            }
        };
    }
}