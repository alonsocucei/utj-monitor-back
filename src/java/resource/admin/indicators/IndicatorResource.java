/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resource.admin.indicators;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import model.entities.IndicatorType;
import model.entities.PIDEIndicator;
import model.entities.Periodicity;
import model.entities.Status;
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
public class IndicatorResource extends ResourceBase<PIDEIndicator> {
    @PersistenceContext(unitName = "UTJMonitor")
    private EntityManager em;
    
    private final Mapper mapper = new MapperBuilder().build();
    
    public IndicatorResource() {
        super(PIDEIndicator.class);
    }
    
    @GET
    @Path("/pide/items")
    @Produces({MediaType.APPLICATION_JSON})
    public List<PIDEIndicator> findPIDEIndicators() {
        javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(PIDEIndicator.class));
        return em.createQuery(cq).getResultList();
    }
    
    @POST
    @Path("/pide/items")
    @Produces({MediaType.APPLICATION_JSON})
    public PIDEIndicator createPIDEIndicator(String entity) {
        final PIDEIndicator pideIndicator = mapper.readObject(entity, PIDEIndicator.class);
        return super.create(pideIndicator);
    }
    
    @PUT
    @Path("/pide/items/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public void editPIDEIndicator(@PathParam("id") Long id, String entity) {
        final PIDEIndicator pideIndicator = mapper.readObject(entity, PIDEIndicator.class);
        super.edit(pideIndicator);
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
    
    @Override
    public EntityManager getEntityManager() {
        return em;
    }
}

