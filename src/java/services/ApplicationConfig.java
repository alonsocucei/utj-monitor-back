package services;

import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.core.Application;
import resource.admin.indicators.CatalogsResource;
import resource.admin.indicators.IndicatorResource;
import resource.admin.indicators.PEIndicatorResource;
import resource.admin.strategic.StrategicItemResource;
import resource.backup.BackupResource;
import resource.hook.SystemHook;
import resource.indicators.MECASUTIndicatorResource;
import resource.indicators.PEGraphicIndicatorResource;
import resource.indicators.PIDEIndicatorResource;

@javax.ws.rs.ApplicationPath("v2")
public class ApplicationConfig extends Application {
    private Set<Object> singletons = new HashSet<>();
    private Set<Class<?>> stateless = new HashSet<>();
    
    public ApplicationConfig() {
        stateless.add(StrategicItemResource.class);
        stateless.add(IndicatorResource.class);
        stateless.add(PIDEIndicatorResource.class);
        stateless.add(MECASUTIndicatorResource.class);
        stateless.add(PEIndicatorResource.class);
        stateless.add(PEGraphicIndicatorResource.class);
        stateless.add(CatalogsResource.class);
        singletons.add(new SystemHook());
        singletons.add(new BackupResource());
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