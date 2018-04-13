package resource.admin.indicators;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import model.entities.PE;
import model.entities.PEIndicator;
import model.entities.PEType;
import org.apache.johnzon.mapper.MapperBuilder;
import org.apache.johnzon.mapper.Mapper;
import resource.ResourceBase;

/**
 *
 * @author alonsocucei
 */
@Stateless
@Path("admin/pe")
public class PEIndicatorResource extends ResourceBase<PEIndicator> {

    @PersistenceContext(unitName = "UTJMonitor")
    private EntityManager em;
    
    private final Mapper mapper = new MapperBuilder().build();
    
    public PEIndicatorResource() {
        super(PEIndicator.class);
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<PE> findAllPEs() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(PE.class));
        return getEntityManager().createQuery(cq).getResultList();
    }
    
    @GET
    @Path("/types")
    @Produces({MediaType.APPLICATION_JSON})
    public List<PEType> findAllPETypes() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(PEType.class));
        return getEntityManager().createQuery(cq).getResultList();
    }
    
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public PE createPE(String entity) {
        final PE pe = mapper.readObject(entity, PE.class);
        getEntityManager().persist(pe);
        
        return pe;
    }
    
    @POST    
    @Path("/types")
    @Consumes({MediaType.APPLICATION_JSON})
    public PEType createOrUpdatePEType(String entity) {
        final PEType peType = mapper.readObject(entity, PEType.class);
        final PEType persistedType = getEntityManager().find(PEType.class, peType.getId());
        
        if (persistedType == null) {
            getEntityManager().persist(peType);
        } else {
            if (!persistedType.getName().equals(peType.getName())) {
                persistedType.setName(peType.getName());
                getEntityManager().merge(persistedType);
            }
        }
        
        return peType;
    }

    @DELETE
    @Path("/{id}")
    public void removePE(@PathParam("id") Long id) {
        final PE pe = getEntityManager().find(PE.class, id);
        getEntityManager().remove(getEntityManager().merge(pe));
    }
    
    @DELETE
    @Path("/types/{id}")
    public void removePEType(@PathParam("id") Long id) {
        final PEType peType = getEntityManager().find(PEType.class, id);
        getEntityManager().remove(getEntityManager().merge(peType));
    }
    
//    @PUT
//    @Path("/{id}")
//    @Consumes({MediaType.APPLICATION_JSON})
//    public StrategicItem edit(@PathParam("id") Long id, String entity) {
//        final StrategicItem strategicItem = mapper.readObject(entity, StrategicItem.class);
//        return super.edit(strategicItem);
//    }


//    @GET
//    @Path("/{id}")
//    @Produces({MediaType.APPLICATION_JSON})
//    public StrategicItem find(@PathParam("id") Long id) {
//        return super.find(id);
//    }


    @Override
    public EntityManager getEntityManager() {
        return em;
    }
}
