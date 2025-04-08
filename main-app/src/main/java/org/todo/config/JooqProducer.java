package org.todo.config;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import javax.sql.DataSource;
import jakarta.inject.Singleton;

@Singleton
public class JooqProducer {

    @Produces
    @ApplicationScoped
    public DSLContext dslContext(DataSource dataSource) {
        return DSL.using(dataSource, org.jooq.SQLDialect.POSTGRES);
    }
}
