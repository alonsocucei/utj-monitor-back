package resource.admin.indicators;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import model.entities.PE;
import model.entities.PEIndicator;
import model.entities.PEType;
import org.apache.johnzon.mapper.MapperBuilder;
import org.apache.johnzon.mapper.Mapper;
import resource.ResourceBaseV2;

/**
 *
 * @author alonsocucei
 */
@Stateless
@Path("admin/pe")
public class PEIndicatorResourceV2 extends ResourceBaseV2<PEIndicator> {

    @PersistenceContext(unitName = "UTJMonitor")
    private EntityManager em;
    
    private final Mapper mapper = new MapperBuilder().build();
    
    public PEIndicatorResourceV2() {
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
                Long id = peItem.getId();
                
                if (id == 0) {
                    id = (Long)em.createQuery("SELECT MAX(P.id) FROM PE P").getSingleResult();
                    
                    if (id == null) {
                        id = 0L;
                    }
                    
                    id ++;
                }

                peItem.setId(id);
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
                Long id = (Long)em.createQuery("SELECT MAX(T.id) FROM PEType T").getSingleResult();
                if (id == null) {
                    id = 1L;
                }

                id ++;
                peItem.setId(id);
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
    
    @Override
    public EntityManager getEntityManager() {
        return em;
    }
}
