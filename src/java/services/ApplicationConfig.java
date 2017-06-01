package services;

import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.core.Application;
import resource.admin.strategic.StrategicItemResource;
import services.forms.ProfileService;

@javax.ws.rs.ApplicationPath("v1")
public class ApplicationConfig extends Application {
    private Set<Object> singletons = new HashSet<>();
    private Set<Class<?>> stateless = new HashSet<>();
    
    public ApplicationConfig() {
        singletons.add(new ProfileService());
        stateless.add(StrategicItemResource.class);
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