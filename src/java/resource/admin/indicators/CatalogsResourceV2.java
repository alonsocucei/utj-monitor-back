package resource.admin.indicators;

import java.util.List;
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
import javax.ws.rs.core.Response;
import model.entities.IndicatorType;
import model.entities.MeasureUnitType;
import model.entities.Periodicity;
import model.entities.Status;
import org.apache.johnzon.mapper.Mapper;
import org.apache.johnzon.mapper.MapperBuilder;

/**
 *
 * @author alonso.sanchez
 */
@Produces("application/json")
@Stateless
@Path("/catalogs")
public class CatalogsResourceV2 {
    
    @PersistenceContext(unitName = "UTJMonitor")
    private EntityManager em;
    
    private final Mapper mapper = new MapperBuilder().build();
    
    @GET
    @Path("/unitTypes")
    @Produces({MediaType.APPLICATION_JSON})
    public Response findMeasureUnitTypes() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(MeasureUnitType.class));
        
        try {
            List<MeasureUnitType> list = getEntityManager().createQuery(cq).getResultList();
            return Response.ok(list).build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }
    
    @PUT
    @Path("/unitTypes/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getMeasureUnitType(@PathParam("id") Long id, String entity) {
        MeasureUnitType type = mapper.readObject(entity, MeasureUnitType.class);
        
        try {
            getEntityManager().merge(type);
            return Response.ok(type).build();
        } catch (Exception e) {
            return Response.serverError().entity(type).build();
        }
    }
    
    @DELETE
    @Path("/unitTypes/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response deleteMeasureUnitType(@PathParam("id") Long id) {
        MeasureUnitType entity = getEntityManager().find(MeasureUnitType.class, id);
        
        try {
            getEntityManager().remove(entity);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.serverError().entity(entity).build();
        }
    }
    
    @POST
    @Path("/unitTypes")
    @Produces({MediaType.APPLICATION_JSON})
    public Response addMeasureUnitType(String entity) {
        MeasureUnitType type = mapper.readObject(entity, MeasureUnitType.class);
        
        try {
            getEntityManager().persist(type);
            return Response.accepted().entity(type).build();
        } catch (Exception e) {
            return Response.serverError().entity(type).build();
        }
    }
    
    @GET
    @Path("/indicatorTypes")
    @Produces({MediaType.APPLICATION_JSON})
    public Response findIndicatorTypes() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(IndicatorType.class));
        try {
            List<IndicatorType> list =  getEntityManager().createQuery(cq).getResultList();
            return Response.ok(list).build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }
    
    @PUT
    @Path("/indicatorTypes/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getIndicatorType(@PathParam("id") Long id, String entity) {
        IndicatorType type = mapper.readObject(entity, IndicatorType.class);
        
        try {
            getEntityManager().merge(type);
            return Response.ok(type).build();
        } catch (Exception e) {
            return Response.serverError().entity(type).build();
        }
    }
    
    @DELETE
    @Path("/indicatorTypes/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response deleteIndicatorType(@PathParam("id") Long id) {
        IndicatorType entity = getEntityManager().find(IndicatorType.class, id);
        
        try {
            getEntityManager().remove(entity);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.serverError().entity(entity).build();
        }
    }
    
    @POST
    @Path("/indicatorTypes")
    @Produces({MediaType.APPLICATION_JSON})
    public Response addIndicatorType(String entity) {
        IndicatorType type = mapper.readObject(entity, IndicatorType.class);
        
        try {
            getEntityManager().persist(type);
            return Response.accepted().entity(type).build();
        } catch (Exception e) {
            return Response.serverError().entity(type).build();
        }
    }
    
    @GET
    @Path("/status")
    @Produces({MediaType.APPLICATION_JSON})
    public Response findStatus() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(Status.class));
        try {
            List<Status> list =  getEntityManager().createQuery(cq).getResultList();
            return Response.ok(list).build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }
    
    @PUT
    @Path("/status/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getStatus(@PathParam("id") Long id, String entity) {
        Status type = mapper.readObject(entity, Status.class);
        
        try {
            getEntityManager().merge(type);
            return Response.ok(type).build();
        } catch (Exception e) {
            return Response.serverError().entity(type).build();
        }
    }
    
    @DELETE
    @Path("/status/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response deleteStatus(@PathParam("id") Long id) {
        Status entity = getEntityManager().find(Status.class, id);
        
        try {
            getEntityManager().remove(entity);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.serverError().entity(entity).build();
        }
    }
    
    @POST
    @Path("/status")
    @Produces({MediaType.APPLICATION_JSON})
    public Response addStatus(String entity) {
        Status type = mapper.readObject(entity, Status.class);
        
        try {
            getEntityManager().persist(type);
            return Response.accepted().entity(type).build();
        } catch (Exception e) {
            return Response.serverError().entity(type).build();
        }
    }
    
    @GET
    @Path("/periodicities")
    @Produces({MediaType.APPLICATION_JSON})
    public Response findPeriodicities() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(Periodicity.class));
        try {
            List<Periodicity> list =  getEntityManager().createQuery(cq).getResultList();
            return Response.ok(list).build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }
    
    @PUT
    @Path("/periodicities/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getPeriodicity(@PathParam("id") Long id, String entity) {
        Periodicity type = mapper.readObject(entity, Periodicity.class);
        
        try {
            getEntityManager().merge(type);
            return Response.ok(type).build();
        } catch (Exception e) {
            return Response.serverError().entity(type).build();
        }
    }
    
    @DELETE
    @Path("/periodicities/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response deletePeriodicity(@PathParam("id") Long id) {
        Periodicity entity = getEntityManager().find(Periodicity.class, id);
        
        try {
            getEntityManager().remove(entity);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.serverError().entity(entity).build();
        }
    }
    
    @POST
    @Path("/periodicities")
    @Produces({MediaType.APPLICATION_JSON})
    public Response addPeriodicity(String entity) {
        Periodicity type = mapper.readObject(entity, Periodicity.class);
        
        try {
            getEntityManager().persist(type);
            return Response.accepted().entity(type).build();
        } catch (Exception e) {
            return Response.serverError().entity(type).build();
        }
    }
    
    public EntityManager getEntityManager() {
        return em;
    }
}


