package resource.backup;

import java.io.StringReader;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import model.entities.Indicator;
import model.entities.StrategicItem;
import org.apache.johnzon.mapper.Mapper;
import org.apache.johnzon.mapper.MapperBuilder;

/**
 *
 * @author alonso.sanchez
 */
@Stateless
@Path("admin/backup")
public class BackupResource {
    @PersistenceContext(unitName = "UTJMonitor")
    private EntityManager em;
    
    private final Mapper mapper = new MapperBuilder().build();
    
    @GET
    @Path("/strategic")
    @Produces({MediaType.APPLICATION_JSON})
    public StrategicItem getStrategic() {
        return getEntityManager().find(StrategicItem.class, new Long(1));
    }
    
    @POST
    @Path("/strategic")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response setStrategic(StrategicItem vision) {
        getEntityManager().merge(vision);
        
        return Response.ok().build();
    }
    
    @GET
    @Path("/indicators")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Indicator> indicators() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(Indicator.class));
        List<Indicator> indicators = getEntityManager().createQuery(cq).getResultList();
        
        return indicators;
    }
    
    @POST
    @Path("/indicators")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response setIndicators(String indicators) {
        String parsedInput = parse(indicators);
        EntityManager em = getEntityManager();
        
        Indicator[] mappedIndicators = mapper.readArray(new StringReader(parsedInput), Indicator.class);
        
        Stream.of(mappedIndicators).forEach(i -> em.persist(i));
        
        return Response.ok(parsedInput).build();
    }
    
    private String parse(String indicators) {
        Pattern timePattern = Pattern.compile("(\"date\":\\s)(\\{\\s+\"date\":\\s\\d{1,2}.+?\"time\":\\s(\\d+),\\s+\"day\":\\s\\d{1,2}\\s+\\})", Pattern.MULTILINE | Pattern.DOTALL);
        Matcher matcher = timePattern.matcher(indicators);
        String replacedString = null;
        
        while(matcher.find()) {
            Instant instant = Instant.ofEpochMilli(Long.parseLong(matcher.group(3)));
            replacedString = matcher.replaceFirst("$1\"" + DateTimeFormatter.ISO_DATE.format(LocalDateTime.ofInstant(instant, ZoneId.of("GMT"))) + " 00:00:00\"");
            System.out.println(replacedString);
            matcher = timePattern.matcher(replacedString);
        }
        
        return replacedString;
    }
    
    public EntityManager getEntityManager() {
        return em;
    }
}
