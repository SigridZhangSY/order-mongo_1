package com.thoughtworks.ketsu.support;

import com.google.inject.AbstractModule;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionManager;
import org.junit.rules.TestRule;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

import javax.inject.Inject;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ApiTestRunner extends InjectBasedRunner {


    @Inject
    DB db;

    public ApiTestRunner(Class<?> klass) throws InitializationError {
        super(klass);
    }

    private final TestRule removeAllData = (base, description) -> new Statement() {
        @Override
        public void evaluate() throws Throwable {
            try {
                base.evaluate();
            } finally {
                db.getCollection("products").remove(new BasicDBObject());
            }
        }
    };

    @Override
    protected List<AbstractModule> getModules() {
        return Arrays.asList(new AbstractModule() {

            @Override
            protected void configure() {
            }
        });
    }

    @Override
    protected List<TestRule> getTestRules(Object target) {
        List<TestRule> rules = new ArrayList<>();
       rules.add(removeAllData);
        rules.addAll(super.getTestRules(target));
        return rules;
    }
}
