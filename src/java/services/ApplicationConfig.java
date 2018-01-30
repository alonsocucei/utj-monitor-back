package services;

import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.core.Application;
import resource.admin.indicators.IndicatorResource;
import resource.admin.strategic.StrategicItemResource;

@javax.ws.rs.ApplicationPath("v1")
public class ApplicationConfig extends Application {
    private Set<Object> singletons = new HashSet<>();
    private Set<Class<?>> stateless = new HashSet<>();
    
    public ApplicationConfig() {
//        singletons.add(new ProfileService());
        stateless.add(StrategicItemResource.class);
        stateless.add(IndicatorResource.class);
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