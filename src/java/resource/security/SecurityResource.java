package resource.security;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import org.apache.johnzon.mapper.Mapper;
import org.apache.johnzon.mapper.MapperBuilder;
import services.security.SecurityService;

/**
 *
 * @author alonsocucei
 */
@Stateless
@Path("/security")
public class SecurityResource {
    
    @Produces("application/json")
    @POST
    @Path("/profile")
    public Response getProfile(String params) {
        final Mapper mapper = new MapperBuilder().build();
        Map<String, String> paramsMap = mapper.readObject(params, Map.class);
        
        try {
            String profile = SecurityService.getProfile(paramsMap.get("access_token"), paramsMap.get("user_id"));
            Map<String, String> profileMap = mapper.readObject(profile, Map.class);
            return Response.ok(new Profile(profileMap.get("picture"), profileMap.get("nickname"))).build();
        } catch (IOException ioe) {
            return Response.serverError().entity(ioe.getMessage()).build();
        }
    }
    
    @POST
    @Path("/authorize")
    public Response isAuthorized(String params) {
        final Mapper mapper = new MapperBuilder().build();
        Map<String, Object> paramsMap = mapper.readObject(params, Map.class);
        boolean completeParams = paramsMap.containsKey("access_token")
                && paramsMap.get("access_token") != null
                && paramsMap.containsKey("user_id")
                && paramsMap.get("user_id") != null
                && paramsMap.containsKey("roles")
                && paramsMap.get("roles") != null;
        
        
        if (!completeParams) {
            return Response.status(Response.Status.BAD_REQUEST).entity("params missing").build();
        }
        
        try {
            boolean isAuthorized = SecurityService.isAuthorized(
                    paramsMap.get("access_token").toString(),
                    paramsMap.get("user_id").toString(),
                    (List)paramsMap.get("roles")
            );
            
            if (isAuthorized) {
                return Response.ok("").build();
            } else {
                return Response.status(Response.Status.UNAUTHORIZED).entity("").build();
            }
        } catch (IOException ioe) {
            return Response.serverError().entity(ioe.getMessage()).build();
        }
    }
}
