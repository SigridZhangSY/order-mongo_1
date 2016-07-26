package com.thoughtworks.ketsu;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provider;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.thoughtworks.ketsu.domain.product.ProductRepository;
import com.thoughtworks.ketsu.web.jersey.Api;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.servlet.ServletRegistration;
import org.glassfish.grizzly.servlet.WebappContext;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.internal.inject.Injections;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spi.Container;
import org.glassfish.jersey.server.spi.ContainerLifecycleListener;
import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.servlet.ServletProperties;
import org.jvnet.hk2.guice.bridge.api.GuiceIntoHK2Bridge;

import javax.inject.Inject;
import javax.ws.rs.core.Application;
import java.net.URI;
import java.net.UnknownHostException;

import static org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory.createHttpServer;
import static org.jvnet.hk2.guice.bridge.api.GuiceBridge.getGuiceBridge;

public class MainServer {
    public static void main(String[] args) throws Exception {
        String contextPath = System.getenv().getOrDefault("CONTEXT_PATH", "/");
        WebappContext context = new WebappContext("Stacks", contextPath);
        context.setAttribute(ServletProperties.SERVICE_LOCATOR, Injections.createLocator());
        ServletRegistration servletRegistration = context.addServlet("ServletContainer",
                new ServletContainer(ResourceConfig.forApplicationClass(Api.class)));

        servletRegistration.addMapping("/*");

        HttpServer server = null;
        try {
            server = createHttpServer(URI.create("http://0.0.0.0:8088"));
            context.deploy(server);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error deploy");
            System.exit(1);
        }

        server.start();
        while (true) {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                server.shutdownNow();
            }
        }
    }

//    private static Injector InitInjector(){
//        return Guice.createInjector(new AbstractModule() {
//            @Override
//            protected void configure() {
//                String dbname = System.getenv().getOrDefault("MONGODB_DATABASE", "mongodb_store");
//                String host = System.getenv().getOrDefault("MONGODB_HOST", "localhost");
//                String username = System.getenv().getOrDefault("MONGODB_USER", "admin");
//                String password = System.getenv().getOrDefault("MONGODB_PASS", "mypass");
//                String connectURL = String.format(
//                        "mongodb://%s:%s@%s/%s",
//                        username,
//                        password,
//                        host,
//                        dbname
//                );
//
//                MongoClient mongoClient = null;
//                try {
//                    mongoClient = new MongoClient(
//                            new MongoClientURI(connectURL)
//                    );
//                }catch (UnknownHostException e){
//                    e.printStackTrace();
//                }
//
//                DB db = mongoClient.getDB("mongodb_store");
//                bind(DB.class).toInstance(db);
//                bind(Application.class).toProvider(ApplicationProvider.class);
//                bind(ProductRepository.class).to(com.thoughtworks.ketsu.infrastructure.repositories.ProductRepository.class);
//            }
//        });
//    }

//    private static class ApplicationProvider implements Provider<Application> {
//        @Inject
//        Injector injector;
//
//        @Override
//        public javax.ws.rs.core.Application get() {
//            Api api = new Api();
//
//            api.register(new ContainerLifecycleListener() {
//                @Override
//                public void onStartup(Container container) {
//                    bridge(container.getApplicationHandler().getServiceLocator(), injector);
//                }
//
//                @Override
//                public void onReload(Container container) {
//
//                }
//
//                @Override
//                public void onShutdown(Container container) {
//
//                }
//            });
//
//            return ResourceConfig.forApplication(api);
//        }
//
//        private void bridge(ServiceLocator serviceLocator, Injector injector) {
//            getGuiceBridge().initializeGuiceBridge(serviceLocator);
//            serviceLocator.getService(GuiceIntoHK2Bridge.class).bridgeGuiceInjector(injector);
//        }
//    }
}
