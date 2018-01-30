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
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import model.entities.IndicatorType;
import model.entities.Periodicity;
import model.entities.Status;

/**
 *
 * @author alonso.sanchez
 */
@Produces("application/json")
@Stateless
@Path("admin/indicator")
public class IndicatorResource {
    @PersistenceContext(unitName = "UTJMonitor")
    private EntityManager em;
    
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
}

