package resource.export;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import model.Achievement;
import model.AchievementType;
import model.entities.Indicator;
import org.apache.johnzon.mapper.Mapper;
import org.apache.johnzon.mapper.MapperBuilder;
import services.security.SecurityService;

/**
 *
 * @author alonsocucei
 */

@Stateless
@Path("/export")
public class ExportToCSVResource {
    @PersistenceContext(unitName = "UTJMonitor")
    private EntityManager em;
    
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
    
    @POST
    @Path("/indicators")
    @Produces("text/csv")
    public Response indicatorsToCSV(@FormParam("data")String data) {
        final Mapper mapper = new MapperBuilder().build();
        Map<String, String> paramsMap = mapper.readObject(data, Map.class);
        
        try {
            if (!SecurityService.isAuthorized(paramsMap.get("access_token"), paramsMap.get("user_id"), Collections.singletonList("Writer"))) {
                throw new IOException("User not authorized");
            }
        } catch(IOException ioe) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        
        final StringBuilder headerValue = new StringBuilder("attachment;filename=\"Indicadores.csv\"");
        final StringBuilder csv = new StringBuilder();
        final String datePattern = "yyyy-MM-dd";
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datePattern);
                
        List<Indicator> indicators = em.createQuery("SELECT i FROM Indicator i", Indicator.class).getResultList();
        indicators.forEach(
            indicator -> {
                csv.append("\"").append(indicator.getName()).append("\"\n");
                List<Achievement> achievements = (List<Achievement>)indicator.getAchievements();
                Map<Timestamp, Double> progress = new HashMap<>();
                Map<Timestamp, Double> goals = new HashMap<>();
                achievements.forEach(
                        a -> {
                            if (a.getAchievementType() == AchievementType.GOAL) {
                                goals.put(a.getDate(), a.getData());
                            } else {
                                progress.put(a.getDate(), a.getData());
                                
                            }
                        }
                );
                
                csv.append("FECHA,META,AVANCE\n");

                achievements.forEach(
                    a -> {
                        Double goalData = goals.get(a.getDate());
                        Double progressData = progress.get(a.getDate());
                        
                        csv.append(simpleDateFormat.format(a.getDate()))
                                .append(",")
                                .append(goalData != null ? goalData : "SIN VALOR")
                                .append(",")
                                .append(progressData != null ? progressData : "SIN VALOR")
                                .append("\n");
                    }
                );

                csv.append("\n");
            }
        );
        
        return Response.ok(csv.toString()).header(HttpHeaders.CONTENT_DISPOSITION, headerValue.toString()).build();
    }
}
