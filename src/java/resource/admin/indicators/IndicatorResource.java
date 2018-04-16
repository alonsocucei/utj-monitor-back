package resource.admin.indicators;

import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import model.Direction;
import model.MeasureUnit;
import model.entities.Indicator;
import model.entities.IndicatorType;
import model.entities.Periodicity;
import model.entities.Position;
import model.entities.Status;
import model.entities.StrategicItem;
import org.apache.johnzon.mapper.Mapper;
import org.apache.johnzon.mapper.MapperBuilder;
import resource.ResourceBase;

/**
 *
 * @author alonso.sanchez
 */
@Produces("application/json")
@Stateless
@Path("admin/indicators")
public class IndicatorResource extends ResourceBase<Indicator> {
    @PersistenceContext(unitName = "UTJMonitor")
    private EntityManager em;
    
    private final Mapper mapper = new MapperBuilder().build();
    
    public IndicatorResource() {
        super(Indicator.class);
    }
    
//    @GET
//    @Produces({MediaType.APPLICATION_JSON})
//    @Path("/pide")
//    public List<PIDEIndicator> findPIDEIndicators() {
//        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
//        cq.select(cq.from(PIDEIndicator.class));
//        List<PIDEIndicator> pideIndicators = getEntityManager().createQuery(cq).getResultList();
//        
//        return pideIndicators;
//    }
//    
//    @GET
//    @Produces({MediaType.APPLICATION_JSON})
//    @Path("/mecasut")
//    public List<MECASUTIndicator> findMECASUTIndicators() {
//        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
//        cq.select(cq.from(MECASUTIndicator.class));
//        List<MECASUTIndicator> mecasutIndicators = getEntityManager().createQuery(cq).getResultList();
//        
//        return mecasutIndicators;
//    }
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<? extends Object> findIndicators() {
        class SummaryIndicator {
            long id;
            String name;
            Status status;
            
            SummaryIndicator(long id, String name, Status status) {
                this.id = id;
                this.name = name;
                this.status = status;
            }
        }
        
        List<Indicator> indicators = super.findAll();
        
        List<SummaryIndicator> summaryIndicators = indicators.stream()
                .map(
                    i -> new SummaryIndicator(i.getId(), i.getName(), i.getStatus())
                )
                .collect(Collectors.toList());
        
        return summaryIndicators;
    }
    
    @GET
    @Path("/pide")
    @Produces({MediaType.APPLICATION_JSON})
    public List<? extends Object> findPIDEIndicators() {
        class SummaryIndicator {
            long id;
            String name;
            String description;
            Status status;
            StrategicItem strategicItem;
            Direction direction;
            MeasureUnit measureUnit;
            String baseYear;
            
            SummaryIndicator(long id, String name, String description, 
                    Status status, StrategicItem strategicItem,
                    Direction direction, MeasureUnit measureUnit,
                    String baseYear) {
                this.id = id;
                this.name = name;
                this.description = description;
                this.status = status;
                this.strategicItem = strategicItem;
                this.direction = direction;
                this.measureUnit = measureUnit;
                this.baseYear = baseYear;
            }
        }
        
        String indicatorsQuery = "Select i"
                + " FROM Indicator i"
                + " WHERE i.indicatorType.id = 1 AND i.strategicItem IS NOT NULL";
        
        List<Indicator> indicators = em.createQuery(indicatorsQuery, Indicator.class)
                .getResultList();
        
        List<SummaryIndicator> summaryIndicators = indicators.stream()
                .map(
                    i -> new SummaryIndicator(
                            i.getId(), i.getName(), i.getDescription(),
                            i.getStatus(), i.getStrategicItem(),
                            i.getDirection(), i.getMeasureUnit(),
                            i.getBaseYear()
                    )
                )
                .collect(Collectors.toList());
        
        return summaryIndicators;
    }
    
//    @GET
//    @Produces({MediaType.APPLICATION_JSON})
//    public List<? extends Object> findIndicators() {
//        class SummaryIndicator {
//            long id;
//            String name;
//            Status status;
//            
//            SummaryIndicator(long id, String name, Status status) {
//                this.id = id;
//                this.name = name;
//                this.status = status;
//            }
//        }
//        
//        List<SummaryIndicator> summaryIndicators;
//        List<PIDEIndicator> pideIndicators = findPIDEIndicators();
//        
//        summaryIndicators = 
//            pideIndicators
//                .stream()
//                .map(i -> new SummaryIndicator(i.getId(), i.getName(), i.getStatus()))
//                .collect(Collectors.toList());
//        
//        List<MECASUTIndicator> mecasutIndicators = findMECASUTIndicators();
//        
//        summaryIndicators.addAll(
//                mecasutIndicators
//                    .stream()
//                    .map(
//                        i -> new SummaryIndicator(i.getId(), i.getName(), i.getStatus())
//                    )
//                    .collect(Collectors.toList())
//        );
//        
//        return summaryIndicators; 
//    }
    
//    @GET
//    @Path("/{id}")
//    @Produces({MediaType.APPLICATION_JSON})
//    public <T extends GeneralIndicator> T findPIDEIndicator(@PathParam("id") Long id, @PathParam("type") String type) {
//        return (T) getEntityManager().find(indicatorTypes.get(type), id);
//    }
    
    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Indicator findIndicator(@PathParam("id") Long id) {
        return super.find(id);
    }
    
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    public Indicator createIndicator(String entity) {
        return super.create(mapper.readObject(entity, Indicator.class));
    }
//    @POST
//    @Produces({MediaType.APPLICATION_JSON})
//    public <T extends GeneralIndicator> T createIndicator(String entity) {
//        final GeneralIndicator indicator = mapper.readObject(entity, GeneralIndicator.class);
//        List<IndicatorType> types = findTypes();
//        final Map<Long, Class> indicatorClassesMap = 
//                types
//                    .stream()
//                    .collect(
//                        Collectors.toMap(
//                            t -> t.getId(),
//                            t -> {
//                                try {
//                                    return Class.forName("model.entities." + t.getName() + "Indicator");
//                                } catch(ClassNotFoundException cnfe) {
//                                    System.err.println(
//                                            "Type: " 
//                                            + t.getName() 
//                                            + " doesn't have a corresponding class: " 
//                                            + " model.entities." + t.getName() + "Indicator");
////                                    throw new IllegalStateException("Indicator type not found.");
//                                    return GeneralIndicator.class;
//                                }
//                            }
//                        )
//                    );
//                
//        final Class indicatorClass = indicatorClassesMap.get(indicator.getIndicatorType().getId());                
//        final T entityObject = mapper.readObject(entity, indicatorClass);
//        
//         getEntityManager().persist(entityObject);
//         return entityObject;
//    }
    
    @POST
    @Path("/clone/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Indicator cloneIndicator(@PathParam("id") Long id, String entity) {
        final Indicator originalIndicator = super.find(id);
        Indicator newIndicator = null;
        
        if (originalIndicator != null) {
            try {
                newIndicator = originalIndicator.clone();
            } catch(CloneNotSupportedException cnse) {
                cnse.printStackTrace();
                return null;
            }
            
            newIndicator.setId(-1);
            
            final Indicator pideIndicator = mapper.readObject(entity, Indicator.class);
            newIndicator.setName(pideIndicator.getName());
        
            super.create(newIndicator);
        }
        
        return newIndicator;
    }
    
    @DELETE
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public void deleteIndicator(@PathParam("id") Long id) {
        final Indicator indicator = super.find(id);
        
        super.remove(indicator);
    }
    
    @PUT
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Indicator editPIDEIndicator(@PathParam("id") Long id, String entity) {
        final Indicator pideIndicator = mapper.readObject(entity, Indicator.class);
        return super.edit(pideIndicator);
    }
    
    @GET
    @Path("/periodicities")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Periodicity> findPeriodicities() {
        javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Periodicity.class));
        return em.createQuery(cq).getResultList();
    }
    
    @GET
    @Path("/types")
    @Produces({MediaType.APPLICATION_JSON})
    public List<IndicatorType> findTypes() {
        javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(IndicatorType.class));
        return em.createQuery(cq).getResultList();
    }
    
    @GET
    @Path("/status")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Status> findStatus() {
        javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Status.class));
        return em.createQuery(cq).getResultList();
    }
    
    @GET
    @Path("/positions")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Position> findResponsibles() {
        javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Position.class));
        return em.createQuery(cq).getResultList();
    }
    
    @Override
    public EntityManager getEntityManager() {
        return em;
    }
}

