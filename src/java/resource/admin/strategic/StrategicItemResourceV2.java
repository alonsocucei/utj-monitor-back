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
import javax.ws.rs.core.Response;
import model.entities.StrategicItem;
import model.entities.StrategicType;
import org.apache.johnzon.mapper.MapperBuilder;
import org.apache.johnzon.mapper.Mapper;
import resource.ResourceBaseV2;

/**
 *
 * @author alonsocucei
 */
@Stateless
@Path("admin/strategic")
public class StrategicItemResourceV2 extends ResourceBaseV2<StrategicItem> {

    @PersistenceContext(unitName = "UTJMonitor")
    private EntityManager em;
    
    private final Mapper mapper = new MapperBuilder().build();
    
    public StrategicItemResourceV2() {
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
    @Path("/types")
    @Produces({MediaType.APPLICATION_JSON})
    public Response findStrategicTypes() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(StrategicType.class));
        
        try {
            List<StrategicType> list = getEntityManager().createQuery(cq).getResultList();
            return Response.ok(list).build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }
    
    @PUT
    @Path("/types/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getStrategicType(@PathParam("id") Long id, String entity) {
        StrategicType type = mapper.readObject(entity, StrategicType.class);
        
        try {
            getEntityManager().merge(type);
            return Response.ok(type).build();
        } catch (Exception e) {
            return Response.serverError().entity(type).build();
        }
    }
    
    @DELETE
    @Path("/types/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response deleteStrategicType(@PathParam("id") Long id) {
        StrategicType entity = getEntityManager().find(StrategicType.class, id);
        
        try {
            getEntityManager().remove(entity);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.serverError().entity(entity).build();
        }
    }
    
    @POST
    @Path("/types")
    @Produces({MediaType.APPLICATION_JSON})
    public Response addStrategicType(String entity) {
        StrategicType type = mapper.readObject(entity, StrategicType.class);
        
        try {
            getEntityManager().persist(type);
            return Response.accepted().entity(type).build();
        } catch (Exception e) {
            return Response.serverError().entity(type).build();
        }
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
