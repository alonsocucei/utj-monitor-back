package resource.admin.indicators;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import model.entities.MeasureUnitType;

/**
 *
 * @author alonso.sanchez
 */
@Produces("application/json")
@Stateless
@Path("/catalogs")
public class CatalogsResource {
    
    @PersistenceContext(unitName = "UTJMonitor")
    private EntityManager em;
    
    @GET
    @Path("/unitTypes")
    @Produces({MediaType.APPLICATION_JSON})
    public List<? extends Object> findIndicatorsWithParents() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(MeasureUnitType.class));
        return getEntityManager().createQuery(cq).getResultList();
    }
    
    public EntityManager getEntityManager() {
        return em;
    }
}


