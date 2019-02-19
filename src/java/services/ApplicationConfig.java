package services;

import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.core.Application;
import resource.admin.indicators.CatalogsResourceV2;
import resource.admin.indicators.IndicatorResourceV2;
import resource.admin.indicators.PEIndicatorResourceV2;
import resource.admin.strategic.StrategicItemResourceV2;
import resource.backup.BackupResourceV2;
import resource.hook.SystemHookV2;
import resource.indicators.MECASUTIndicatorResourceV2;
import resource.indicators.PEGraphicIndicatorResourceV2;
import resource.indicators.PIDEIndicatorResourceV2;
import resource.security.SecurityResource;

@javax.ws.rs.ApplicationPath("v2")
public class ApplicationConfig extends Application {
    private Set<Object> singletons = new HashSet<>();
    private Set<Class<?>> stateless = new HashSet<>();
    
    public ApplicationConfig() {
        stateless.add(StrategicItemResourceV2.class);
        stateless.add(IndicatorResourceV2.class);
        stateless.add(PIDEIndicatorResourceV2.class);
        stateless.add(MECASUTIndicatorResourceV2.class);
        stateless.add(PEIndicatorResourceV2.class);
        stateless.add(PEGraphicIndicatorResourceV2.class);
        stateless.add(SecurityResource.class);
        stateless.add(CatalogsResourceV2.class);
        singletons.add(new SystemHookV2());
        singletons.add(new BackupResourceV2());
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