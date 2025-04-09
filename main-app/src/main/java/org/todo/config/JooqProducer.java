package org.todo.config;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import javax.sql.DataSource;

@Singleton
public class JooqProducer {

    @Produces
    @ApplicationScoped
    public DSLContext dslContext(DataSource dataSource) {
        return DSL.using(dataSource, org.jooq.SQLDialect.POSTGRES);
    }
}

