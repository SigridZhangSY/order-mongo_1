package com.thoughtworks.ketsu.infrastructure.records;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.thoughtworks.ketsu.MainServer;
import com.thoughtworks.ketsu.domain.product.ProductRepository;
import com.thoughtworks.ketsu.domain.user.UserRepository;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.*;
import org.apache.ibatis.type.TypeHandler;
import org.mybatis.guice.mappers.MapperProvider;
import org.mybatis.guice.session.SqlSessionManagerProvider;
import org.mybatis.guice.transactional.Transactional;
import org.mybatis.guice.transactional.TransactionalMethodInterceptor;

import javax.ws.rs.core.Application;
import java.io.Reader;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.Properties;

import static com.google.inject.matcher.Matchers.*;
import static com.google.inject.name.Names.named;
import static com.google.inject.util.Providers.guicify;
import static org.apache.ibatis.io.Resources.getResourceAsReader;

public class Models extends AbstractModule {

    @Override
    protected void configure() {
        bind(ProductRepository.class).to(com.thoughtworks.ketsu.infrastructure.repositories.ProductRepository.class);
        bind(UserRepository.class).to(com.thoughtworks.ketsu.infrastructure.repositories.UserRepository.class);

    }
}

