package resource.indicators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import model.Achievement;
import model.MeasureUnit;
import model.entities.Indicator;
import resource.ResourceBaseV2;

/**
 *
 * @author alonso.sanchez
 */
@Produces("application/json")
@Stateless
@Path("/indicators/pe")
public class PEGraphicIndicatorResourceV2 extends ResourceBaseV2<Indicator> {

    @PersistenceContext(unitName = "UTJMonitor")
    private EntityManager em;

    public PEGraphicIndicatorResourceV2() {
        super(Indicator.class);
    }
    
    
    @GET
    @Path("/tree")
    @Produces({MediaType.APPLICATION_JSON})
    public List<? extends Object> findIndicatorsTree() {
        return getIndicatorsTree(em);
    }
    
    public static List<? extends Object> getIndicatorsTree(EntityManager em) {

        class Attribute {

            private long id;

            public Attribute(long id) {
                this.id = id;
            }

            public long getId() {
                return id;
            }
            
            @Override
            public String toString() {
                return "id: "+ id;
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
            
            @Override
            public String toString() {
                return super.toString()
                        + ", type: " +  type;
            }
        }

        class Item {

            private String title;
            private Attribute attr;

            public Item(String title, Attribute attr) {
                this.title = title;
                this.attr = attr;
            }
            
            @Override
            public String toString() {
                return "title:" + title + ", attr: " + attr;
            }
        }


        class TreeItem extends Item {

            private List<Item> children;

            public TreeItem(String title, Attribute attr) {
                super(title, attr);
                children = new ArrayList<>();
            }
            
            @Override
            public String toString(){
                return super.toString()
                        + ", children: " + children;
            }
        }

        String indicatorsQuery = "Select i"
                + " FROM Indicator i"
                + " WHERE i.status.name LIKE 'Activo' AND i.indicatorType.id = 3";

        List<Indicator> indicators = em
                .createQuery(indicatorsQuery, Indicator.class)
                .getResultList()
                .stream()
                .collect(Collectors.toList());

        Map<Long, TreeItem> treeMapIndicators = new HashMap<>();
        List<TreeItem> treeIndicators = new ArrayList<>();
        List<Indicator> leafIndicators = new ArrayList<>();
        
        indicators
                .stream()
                .forEach(
                    indicator -> {
                        final String name = indicator.getName();
                        final Long id = indicator.getId();

                        if (indicator.getIsGlobal()) {
                            TreeItem indicatorTreeItem = new TreeItem(
                                    name,
                                    new Attribute(id)
                            );

                            treeIndicators.add(indicatorTreeItem);
                            treeMapIndicators.put(id, indicatorTreeItem);
                        } else {
                            if (indicator.getPideIndicator() != null) {
                                leafIndicators.add(indicator);
                            }
                        }
                    }
                );
                
        leafIndicators.forEach(
            indicator -> {
                Long parentId = indicator.getPideIndicator().getId();
                TreeItem parentItem = treeMapIndicators.get(parentId);

                if (parentItem != null) {
                    parentItem.children.add(
                            new Item(
                                    indicator.getName(),
                                    new IndicatorAttribute(
                                        indicator.getId(),
                                        "indicator",
                                        indicator.getAchievements(),
                                        indicator.getPideIndicator().getMeasureUnit()
                                    )
                            )
                    );
                }
            }
        );
        
        return treeIndicators.stream().filter(i -> i.children.size() > 0).collect(Collectors.toList());
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
                + " WHERE i.status.name LIKE 'Activo' AND i.indicatorType.id = 3";

        List<Map<String, Object>> indicators = em
                .createQuery(indicatorsQuery, Indicator.class)
                .getResultList()
                .stream()
                .filter(indicator -> indicator.getAchievements().size() > 0)
                .map(i -> {
                    Map<String, Object> properties = new HashMap<>();
                    properties.put("achievements", i.getAchievements());
                    properties.put("resetType", i.getPideIndicator().getResetType());
                    properties.put("description", i.getPideIndicator().getDescription());
                    properties.put("measureUnit", i.getPideIndicator().getMeasureUnit());
                    properties.put("grades", i.getGrades());
                    properties.put("responsible", i.getResponsible());
                    properties.put("resetDates", i.getPideIndicator().getResetDates());
                    properties.put("perdiodicity", i.getPideIndicator().getPeriodicity());
                    properties.put("name", i.getPideIndicator().getName());
                    properties.put("id", i.getId());
                    properties.put("direction", i.getPideIndicator().getDirection());
                    properties.put("indicatorType", i.getIndicatorType().getId());

                    List<String> keysToRemove = new ArrayList<>();

                    for (String key : properties.keySet()) {
                        if (properties.get(key) == null) {
                            keysToRemove.add(key);
                        }
                    }

                    for (String key : keysToRemove) {
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
