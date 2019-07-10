package resource.admin.indicators;

import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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
import model.entities.Backup;
import model.entities.Indicator;
import model.entities.IndicatorType;
import model.entities.Periodicity;
import model.entities.Position;
import model.entities.Status;
import model.entities.StrategicItem;
import org.apache.johnzon.mapper.Mapper;
import org.apache.johnzon.mapper.MapperBuilder;
import resource.ResourceBaseV2;

/**
 *
 * @author alonso.sanchez
 */
@Produces("application/json")
@Stateless
@Path("admin/indicators")
public class IndicatorResourceV2 extends ResourceBaseV2<Indicator> {
    @PersistenceContext(unitName = "UTJMonitor")
    private EntityManager em;
    
    private final Mapper mapper = new MapperBuilder().build();
    
    public IndicatorResourceV2() {
        super(Indicator.class);
    }
    
    public static List<Indicator> getIndicatorsByType(EntityManager em, String type) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Indicator> cq = builder.createQuery(Indicator.class);
        cq.where(builder.equal(cq.from(Indicator.class).get("indicatorType").get("name"), type.toUpperCase()));
        
        return em.createQuery(cq).getResultList();
    }
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<? extends Object> findIndicators() {
        class SummaryPIDE {
            String name;
            
            SummaryPIDE(String name) {
                this.name = name;
            }
        }
        
        class SummaryPE {
            String type;
            String name;
            
            SummaryPE(String type, String name) {
                this.type = type;
                this.name = name;
            }
        }
        
        class SummaryIndicator {
            long id;
            IndicatorType type;
            String name;
            Status status;
            SummaryPE pe;
            SummaryPIDE pideIndicator;
            Periodicity periodicity;
            boolean isGlobal;
            
            SummaryIndicator(long id, IndicatorType type, String name, Status status,
                    SummaryPE pe, SummaryPIDE pideIndicator, Periodicity periodicity,
                    boolean isGlobal) {
                this.id = id;
                this.type = type;
                this.name = name;
                this.status = status;
                this.pe = pe;
                this.pideIndicator = pideIndicator;
                this.periodicity = periodicity;
                this.isGlobal = isGlobal;
            }
        }
        
        
        List<Indicator> indicators = super.findAll();
        
        List<SummaryIndicator> summaryIndicators = indicators.stream()
                .map(
                    i -> {
                        SummaryPE pe = new SummaryPE("", "");
                        SummaryPIDE pide = new SummaryPIDE("");
                        
                        if (i.getPe() != null) {
                            pe.type = i.getPe().getType().getName();
                            pe.name = i.getPe().getName();
                        }
                        
                        if (i.getPideIndicator() != null) {
                            pide.name = i.getPideIndicator().getName();
                        }
                        
                        return new SummaryIndicator(i.getId(), i.getIndicatorType(),
                            i.getName(), i.getStatus(), pe, pide, i.getPeriodicity(),
                            i.getIsGlobal());
                        }
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
            IndicatorType type;
            String name;
            String description;
            Status status;
            StrategicItem strategicItem;
            Direction direction;
            MeasureUnit measureUnit;
            String baseYear;
            
            SummaryIndicator(long id, IndicatorType type, String name, String description, 
                    Status status, StrategicItem strategicItem,
                    Direction direction, MeasureUnit measureUnit,
                    String baseYear) {
                this.id = id;
                this.type = type;
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
                            i.getId(), i.getIndicatorType(), i.getName(), i.getDescription(),
                            i.getStatus(), i.getStrategicItem(),
                            i.getDirection(), i.getMeasureUnit(),
                            i.getBaseYear()
                    )
                )
                .collect(Collectors.toList());
        
        return summaryIndicators;
    }
    
    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Indicator findIndicator(@PathParam("id") Long id) {
        return super.find(id);
    }
    
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    public Indicator createIndicator(String entity) {
        System.out.println("creating indicator");
        System.out.println("input: " + entity);
        
        Indicator i = mapper.readObject(entity, Indicator.class);
        
        Long id = i.getId();
        if (id == 0) {
            id = (Long)em.createQuery("SELECT MAX(I.id) FROM Indicator I").getSingleResult();
            
            if (id == null) {
                id = 0L;
            }
            
            i.setId(id + 1);
        }
        
        return super.create(i);
    }
    
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
    
    @GET
    @Path("/pe/globals")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Indicator> getPEGlobalIndicators() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Indicator> cq = cb.createQuery(Indicator.class);
        Root<Indicator> indicator = cq.from(Indicator.class);
        cq.select(indicator).where(cb.isTrue(indicator.get("isGlobal")));
        
        return em.createQuery(cq).getResultList();
    }
    
    @Override
    public EntityManager getEntityManager() {
        return em;
    }
}
