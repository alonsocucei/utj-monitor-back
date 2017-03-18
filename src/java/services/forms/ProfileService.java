package services.forms;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.Set;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import models.Profile;
import models.forms.Catalog;
import test.forms.ProfileCatalogs;
import utils.json.CatalogsGenerator;

/**
 * @author alonsocucei
 */
@Stateless
@Path("/profiles")
public class ProfileService {

    @PersistenceUnit
    private EntityManagerFactory emf;

    @GET
    @Path("/catalogs")
    @Produces("text/plain")
    public String getCatalogs() {
        EntityManager em = null;
        em = emf.createEntityManager();
        
        Set<Catalog> catalogs = ProfileCatalogs.getCatalogs();

        return CatalogsGenerator.catalogsToJson(catalogs);
    }

    @POST
    @Consumes("application/json")
    public Response createProfile(InputStream is) {
        //method to parse the data
        Profile profile = new Profile();
        //persist profile in DB
        return Response.created(URI.create("/profiles/" + "{id}")).build();
    }

    @GET
    @Path("{id}")
    @Produces("application/json")
    public StreamingOutput getProfile(@PathParam("id") int id) {
        boolean exists = true;

        if (!exists) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }

        return new StreamingOutput() {
            public void write(OutputStream output) throws IOException, WebApplicationException {

            }
        };
    }

    @PUT
    @Path("{id}")
    @Consumes("application/json")
    public void updateProfile(@PathParam("id") int id, InputStream is) {
        //update code here
    }
}
