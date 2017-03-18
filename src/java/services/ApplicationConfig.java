package services;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.ws.rs.core.Application;
import services.forms.ProfileService;

@javax.ws.rs.ApplicationPath("v1")
public class ApplicationConfig extends Application {
    private Set<Object> singletons = new HashSet<>();
    private Set<Class<?>> stateless = new HashSet<>();
    
    public ApplicationConfig() {
        singletons.add(new ProfileService());
    }
    
    @Override
    public Set<Class<?>> getClasses() {
        return stateless;
    }
    
    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }
}