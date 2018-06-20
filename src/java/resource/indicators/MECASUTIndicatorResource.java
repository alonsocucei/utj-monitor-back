package resource.indicators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import model.Achievement;
import model.MeasureUnit;
import model.entities.ClassType;
import model.entities.Indicator;
import resource.ResourceBase;

/**
 *
 * @author alonso.sanchez
 */
@Produces("application/json")
@Stateless
@Path("/indicators/mecasut")
public class MECASUTIndicatorResource extends ResourceBase<Indicator> {
    @PersistenceContext(unitName = "UTJMonitor")
    private EntityManager em;
    
    public MECASUTIndicatorResource() {
        super(Indicator.class);
    }
    
    @GET
    @Path("/tree")
    @Produces({MediaType.APPLICATION_JSON})
    public List<? extends Object> findIndicatorsWithParents() {
        
        class Attribute {
            private long id;
            
            public Attribute(long id) {
                this.id = id;
            }
            
            public long getId() {
                return id;
            }
        }
        
        class IndicatorAttribute extends Attribute {
            private String type;
            private List<Achievement> achievements;
            private MeasureUnit measureUnit;
            
            public IndicatorAttribute(long id, String type,
                    List<Achievement> achievements, MeasureUnit measureUnit) {
                super(id);
                this.type = type;
                this.achievements = achievements;
                this.measureUnit = measureUnit;
            }
        }
        
        class LeafTreeItem {
            private String title;
            private Attribute attr;
            
            public LeafTreeItem(String title, Attribute attr) {
                this.title = title;
                this.attr = attr;
            }
        }
        
        class TreeItem extends LeafTreeItem {
            private List<LeafTreeItem> children;
            
            public TreeItem(String title, Attribute attr) {
                super(title, attr);
                children = new ArrayList<>();
            }
        }
        
        String indicatorsQuery = "Select i"
                + " FROM Indicator i"
                + " WHERE i.status.name LIKE 'Activo' AND i.indicatorType.id = 2";
        
        List<Indicator> indicators = em
                .createQuery(indicatorsQuery, Indicator.class)
                .getResultList()
                .stream()
//                .filter(indicator -> indicator.getAchievements().size() > 0)
                .collect(Collectors.toList());
        
        Map<ClassType, List<LeafTreeItem>> treeMapIndicators = new HashMap<>();
        indicators
        .stream()
        .forEach(
            indicator -> {
                LeafTreeItem indicatorTreeItem = new LeafTreeItem(
                        indicator.getName(), 
                        new IndicatorAttribute(
                                indicator.getId(),
                                "indicator",
                                indicator.getAchievements(),
                                indicator.getMeasureUnit()
                        )
                );
                
                if (!treeMapIndicators.containsKey(indicator.getClassType())) {
                    treeMapIndicators.put(indicator.getClassType(), new ArrayList<>());
                }
                
                treeMapIndicators.get(indicator.getClassType()).add(indicatorTreeItem);
            }
        );
        
        List<TreeItem> listIndicatorItems = new ArrayList<>();
        
        for(ClassType classType: treeMapIndicators.keySet()) {
            TreeItem treeItem = new TreeItem(classType.getName(), new Attribute(classType.getId()));
            treeItem.children = treeMapIndicators.get(classType);
            
            listIndicatorItems.add(treeItem);
        }
        
        return listIndicatorItems;
    }
    
    @GET
    @Path("/")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Indicator> findIndicators() {
        return super.findAll();
    }
    
    @GET
    @Path("/types")
    @Produces({MediaType.APPLICATION_JSON})
    public List<ClassType> findClassTypes() {
        Query query = em.createQuery("SELECT t FROM ClassType t");
        return query.getResultList();
    }
    
    @GET
    @Path("/active")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Map<String, Object>> findActiveIndicators() {
        String indicatorsQuery = "Select i"
                + " FROM Indicator i"
                + " WHERE i.status.name LIKE 'Activo' AND i.indicatorType.id = 2";
        
        List<Map<String, Object>> indicators = em
                .createQuery(indicatorsQuery, Indicator.class)
                .getResultList()
                .stream()
                .filter(indicator -> indicator.getAchievements().size() > 0)
                .map(i -> {
                        Map<String, Object> properties = new HashMap<>();
                        properties.put("achievements", i.getAchievements());
                        properties.put("resetType", i.getResetType());
                        properties.put("description", i.getDescription());
                        properties.put("measureUnit", i.getMeasureUnit());
                        properties.put("grades", i.getGrades());
                        properties.put("responsible", i.getResponsible());
                        properties.put("resetDates", i.getResetDates());
                        properties.put("perdiodicity", i.getPeriodicity());
                        properties.put("name", i.getName());
                        properties.put("id", i.getId());
                        properties.put("direction", i.getDirection());
                        properties.put("indicatorType", i.getIndicatorType().getId());
                        
                        List<String> keysToRemove = new ArrayList<>();
                        
                        for (String key: properties.keySet()) {
                            if (properties.get(key) == null) {
                                keysToRemove.add(key);
                            }
                        }
                        
                        for (String key: keysToRemove) {
                            properties.remove(key);
                        }
                        
                        return properties;
                    }
                )
                .collect(Collectors.toList());
        
        return indicators;
    }
    
    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Indicator findIndicator(@PathParam("id") Long id) {
        return super.find(id);
    }
    
    @Override
    public EntityManager getEntityManager() {
        return em;
    }
}

