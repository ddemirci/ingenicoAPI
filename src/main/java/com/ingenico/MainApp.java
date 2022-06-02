package com.ingenico;

import com.ingenico.controller.DocumentController;
import com.ingenico.controller.ProfileController;
import com.ingenico.repository.DocumentRepository;
import com.ingenico.repository.ProfileRepository;
import com.ingenico.service.DocumentService;
import com.ingenico.service.ProfileService;
import org.eclipse.jetty.server.Server;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainApp {
    public static final String BASE_URI = "http://localhost:8080/";

    public static Server startServer() {

        ResourceConfig config = new ResourceConfig().packages("com.ingenico");
        config.register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(ProfileController.class).to(ProfileController.class);
                bind(ProfileService.class).to(ProfileService.class);
                bind(ProfileRepository.class).to(ProfileRepository.class);

                bind(DocumentController.class).to(DocumentController.class);
                bind(DocumentService.class).to(DocumentService.class);
                bind(DocumentRepository.class).to(DocumentRepository.class);
            }
        });

        return JettyHttpContainerFactory.createServer(URI.create(BASE_URI), config);
    }

    public static void main(String[] args) {

        try {
            final Server server = startServer();
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    System.out.println("Shutting down the application...");
                    server.stop();
                    System.out.println("Done, exit.");
                } catch (Exception e) {
                    Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, e);
                }
            }));

            System.out.printf("Application started.%nStop the application using CTRL+C%n");
            Thread.currentThread().join();
        } catch (InterruptedException ex) {
            Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
