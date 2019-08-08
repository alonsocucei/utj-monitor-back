package resource;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author alonso.sanchez
 */
@Produces("application/json")
@Stateless
@Path("/queries")
public class QueriesResource {
    @PersistenceContext(unitName = "UTJMonitor")
    private EntityManager em;
    
    @GET
    @Path("/get")
    @Produces({MediaType.APPLICATION_JSON})
    public Response returnSelectedRows(@QueryParam("query") String query) {
        try {
            Query q = em.createNativeQuery(query);
            return Response.ok(q.getResultList()).build();
        } catch (Exception sqle) {
            return Response.serverError().entity(sqle.getMessage()).build();
        }
    }
    
    @GET
    @Path("/create")
    @Produces({MediaType.TEXT_PLAIN})
    public Response returnExceuteStatus(@QueryParam("query") String query) {
        try {
            int status = em.createNativeQuery(query).executeUpdate();
            return Response.ok(status).build();
        } catch (Exception sqle) {
            return Response.serverError().entity(sqle.getMessage()).build();
        }
    }
}
