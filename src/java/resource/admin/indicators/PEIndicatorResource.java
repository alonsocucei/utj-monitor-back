package resource.admin.indicators;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
    public List<PE> findAllPE() {
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
    public List<PE> updatePE(String data) {
        final EntityManager em = getEntityManager();
        final PE[] pe = mapper.readObject(data, PE[].class);
        
        PE persistedPE;
        
        for (PE peItem: pe) {
            persistedPE = em.find(PE.class, peItem.getId());
        
            if (persistedPE == null) {
                peItem.setId(0);
                em.persist(peItem);
            } else {
                em.merge(peItem);
            }
        }
        
        return findAllPE();
    }
    
    @POST    
    @Path("/types")
    @Consumes({MediaType.APPLICATION_JSON})
    public List<PEType> updatePETypes(String data) {
        final EntityManager em = getEntityManager();
        final PEType[] peTypes = mapper.readObject(data, PEType[].class);
        
        PEType persistedType;
        
        for (PEType peItem: peTypes) {
            persistedType = em.find(PEType.class, peItem.getId());
        
            if (persistedType == null) {
                peItem.setId(0);
                em.persist(peItem);
            } else {
                em.merge(peItem);
            }
        }
        
        return findAllPETypes();
    }

    @DELETE
    public List<PE> removePE(String data) {
        final EntityManager em = getEntityManager();
        final PE[] pe = mapper.readObject(data, PE[].class);
        
        for (PE peItem: pe) {
            em.remove(getEntityManager().merge(peItem));
        }
        
        return findAllPE();
    }
    
    @DELETE
    @Path("/types")
    public List<PEType> removePEType(String data) {
        final EntityManager em = getEntityManager();
        final PEType[] peTypes = mapper.readObject(data, PEType[].class);
        
        for (PEType peItem: peTypes) {
            em.remove(getEntityManager().merge(peItem));
        }
        
        return findAllPETypes();
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
