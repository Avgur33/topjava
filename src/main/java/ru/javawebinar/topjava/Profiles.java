package ru.javawebinar.topjava;

import org.springframework.util.ClassUtils;

public class Profiles {
    public static final String
            JDBC = "jdbc",
            JPA = "jpa",
            DATAJPA = "datajpa";

    public static final String REPOSITORY_IMPLEMENTATION = JDBC;

    public static final String
            POSTGRES_DB = "postgres",
            HSQL_DB = "hsqldb";

    //  Get DB profile depending of DB driver in classpath
    public static String[] getActiveDbProfile() {
        if (ClassUtils.isPresent("org.postgresql.Driver", null)) {
            return new String[]{POSTGRES_DB, REPOSITORY_IMPLEMENTATION};
        } else if (ClassUtils.isPresent("org.hsqldb.jdbcDriver", null)) {
            return new String[]{HSQL_DB, REPOSITORY_IMPLEMENTATION};
        } else {
            throw new IllegalStateException("Could not find DB driver");
        }
    }
}
