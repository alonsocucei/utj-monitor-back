package resource.admin.strategic;

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
import model.entities.StrategicItem;
import model.entities.StrategicType;
import org.apache.johnzon.mapper.MapperBuilder;
import org.apache.johnzon.mapper.Mapper;
import resource.ResourceBase;

/**
 *
 * @author alonsocucei
 */
@Stateless
@Path("admin/strategic")
public class StrategicItemResource extends ResourceBase<StrategicItem> {

    @PersistenceContext(unitName = "UTJMonitor")
    private EntityManager em;
    
    private final Mapper mapper = new MapperBuilder().build();
    
    public StrategicItemResource() {
        super(StrategicItem.class);
    }

    @POST
    @Path("")
    @Consumes({MediaType.APPLICATION_JSON})
    public void createStrategicItem(String entity) {
        final StrategicItem strategicItem = mapper.readObject(entity, StrategicItem.class);
        super.create(strategicItem);
    }

    @PUT
    @Path("/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    public StrategicItem edit(@PathParam("id") Long id, String entity) {
        final StrategicItem strategicItem = mapper.readObject(entity, StrategicItem.class);
        return super.edit(strategicItem);
    }

    @DELETE
    @Path("/{id}")
    public void remove(@PathParam("id") Long id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public StrategicItem find(@PathParam("id") Long id) {
        return super.find(id);
    }

    @GET
    @Path("")
    @Override
    @Produces({MediaType.APPLICATION_JSON})
    public List<StrategicItem> findAll() {
        return super.findAll();
    }
    
    @GET
    @Path("/hola")
    @Produces({MediaType.APPLICATION_JSON})
    public String hola() {
        return "Hola mundo";
    }

    @GET
    @Path("/types")
    @Produces({MediaType.APPLICATION_JSON})
    public List<StrategicType> findAllTypes() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(StrategicType.class));
        return getEntityManager().createQuery(cq).getResultList();
    }

    @GET
    @Path("/{from}/{to}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<StrategicItem> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("/count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }
}
