package resource.indicators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import model.Achievement;
import model.MeasureUnit;
import model.entities.Indicator;
import resource.ResourceBase;

/**
 *
 * @author alonso.sanchez
 */
@Produces("application/json")
@Stateless
@Path("/indicators/pide")
public class PIDEIndicatorResource extends ResourceBase<Indicator> {
    @PersistenceContext(unitName = "UTJMonitor")
    private EntityManager em;
    
    public PIDEIndicatorResource() {
        super(Indicator.class);
    }
    
    @GET
    @Path("/objectives/axes")
    @Produces({MediaType.APPLICATION_JSON})
    public List<? extends Object> findIndicatorsWithParents(@QueryParam("parents")String parents) {
        
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
                + " WHERE i.strategicItem IS NOT NULL";
        
        List<Indicator> indicators = em
                .createQuery(indicatorsQuery, Indicator.class)
                .getResultList()
                .stream()
//                .filter(indicator -> indicator.getAchievements().size() > 0)
                .collect(Collectors.toList());
        
        Map<Long, List<LeafTreeItem>> treeMapIndicatorItems = new HashMap<>();
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
                
                if (!treeMapIndicatorItems.containsKey(indicator.getStrategicItem().getId())) {
                    treeMapIndicatorItems.put(indicator.getStrategicItem().getId(), new ArrayList<>());
                }
                
                treeMapIndicatorItems.get(indicator.getStrategicItem().getId()).add(indicatorTreeItem);
            }
        );
        
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<model.entities.StrategicItem> cq = cb.createQuery(model.entities.StrategicItem.class);
        Root<model.entities.StrategicItem> item = cq.from(model.entities.StrategicItem.class);
        cq.select(item).where(cb.equal(item.get("strategicType").get("name"), "axe"));
        
        TypedQuery<model.entities.StrategicItem> q = em.createQuery(cq);
        List<model.entities.StrategicItem> axes = q.getResultList();
        
        List<TreeItem> treeItems = axes
                .stream()
                .map(
                        axe -> {
                            TreeItem axeTreeItem = new TreeItem(axe.getName(), new Attribute(axe.getId()));
                            List<model.entities.StrategicItem> topics = axe.getChildren();
                            topics
                                .stream()
                                .forEach(
                                    topic -> {
                                        List<TreeItem> objectiveTreeItems =
                                            topic.getChildren()
                                            .stream()
                                            .map(
                                                objective -> {
                                                    TreeItem objectiveTreeItem =
                                                        new TreeItem(
                                                            objective.getName(),
                                                            new Attribute(objective.getId())
                                                        );
                                                    
                                                    objectiveTreeItem.children = treeMapIndicatorItems.get(objective.getId());
                                                    return objectiveTreeItem;
                                                }
                                            )
                                            .collect(Collectors.toList());
                                        
                                        axeTreeItem.children.addAll(objectiveTreeItems);
                                    }
                                );
                            return axeTreeItem;
                        }
                )
                .collect(Collectors.toList());
        
        
        return treeItems;
    }
    
    @GET
    @Path("/")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Indicator> findIndicators() {
        return super.findAll();
    }
    
    @GET
    @Path("/active")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Map<String, Object>> findActiveIndicators() {
        String indicatorsQuery = "Select i"
                + " FROM Indicator i"
                + " WHERE i.status.name LIKE 'Activo' AND i.indicatorType.id = 1";
        
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
                        
                        if (i.getStrategicItem() != null) {
                            properties.put("strategicItem", i.getStrategicItem().getId());
                        }
                        
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

