package resource.export;

import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import org.apache.johnzon.mapper.Mapper;
import org.apache.johnzon.mapper.MapperBuilder;

/**
 *
 * @author alonsocucei
 */

@Stateless
@Path("/export")
public class ExportToCSVResource {
    
    @POST
    @Path("/graphic")
    @Produces("text/csv")
    public Response graphicToCSV(@FormParam("data")String data) {
        final Mapper mapper = new MapperBuilder().build();
        Map<String, Object> paramsMap = mapper.readObject(data, Map.class);
        
        final StringBuilder headerValue = new StringBuilder();
        final StringBuilder csv = new StringBuilder();
        
        paramsMap.keySet().forEach(
            key -> {
                if (key.equalsIgnoreCase("name")) {
                    headerValue.append("attachment;filename=\"")
                            .append(paramsMap.get(key).toString())
                            .append(".csv\"");
                } else {
                    Map<String, Object> indicator = (Map)paramsMap.get(key);
                    csv.append(indicator.get("name")).append("\n");
                    List<Map<String, Object>> items = (List<Map<String, Object>>)indicator.get("items");
                    
                    csv.append("FECHA,META,AVANCE\n");
                    
                    items.forEach(
                        item -> {
                            
                            csv.append(item.get("date"))
                                    .append(",")
                                    .append(item.get("goal"))
                                    .append(",")
                                    .append(item.get("progress"))
                                    .append("\n");
                        }
                    );
                    
                    csv.append("\n");
                }
            }
        );
        
        return Response.ok(csv.toString()).header(HttpHeaders.CONTENT_DISPOSITION, headerValue.toString()).build();
    }
}
