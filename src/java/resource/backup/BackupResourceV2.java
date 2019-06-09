package resource.backup;

import java.io.StringReader;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import model.entities.Indicator;
import model.entities.IndicatorType;
import model.entities.StrategicItem;
import org.apache.johnzon.mapper.Mapper;
import org.apache.johnzon.mapper.MapperBuilder;

/**
 *
 * @author alonso.sanchez
 */
@Stateless
@Path("admin/backup")
public class BackupResourceV2 {
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
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(Indicator.class));
        List<Indicator> indicators = getEntityManager().createQuery(cq).getResultList();
        
        return indicators;
    }
    
//    @POST
//    @Path("/indicators/{type}")
//    @Consumes({MediaType.APPLICATION_JSON})
//    public Response setIndicators(String indicators, @PathParam("type") String type) {
//        String parsedInput = parse(indicators);
//        EntityManager em = getEntityManager();
//        IndicatorType peType;
//        
//        CriteriaBuilder cb = em.getCriteriaBuilder();
//        CriteriaQuery<IndicatorType> cq = cb.createQuery(IndicatorType.class);
//        Root<IndicatorType> typeRoot = cq.from(IndicatorType.class);
//        cq.select(typeRoot).where(cb.like(cb.lower(typeRoot.get("name")), "pe"));
//        TypedQuery<IndicatorType> q = em.createQuery(cq);
//        peType = q.getSingleResult();
//        
//        cq.select(typeRoot).where(cb.like(cb.lower(typeRoot.get("name")), type.toLowerCase()));
//        q = em.createQuery(cq);
//        
////        List<Indicator> persistedIndicators = new ArrayList<>();
////        List<Indicator> notPersistedIndicators = new ArrayList<>();
//        List<Indicator> globals = new ArrayList<>();
//        List<Indicator> pide = new ArrayList<>();
//        List<Indicator> pe = new ArrayList<>();
//        List<Indicator> mecasut = new ArrayList<>();
//        
//        Map<Long, Indicator> mapPE = this.getPEGlobalIndicators();
//        
//        try {
//            IndicatorType resultType = q.getSingleResult();
//
//            Indicator[] mappedIndicators = mapper.readArray(new StringReader(parsedInput), Indicator.class);
//            
//            Stream.of(mappedIndicators)
////                .filter(i -> i.getIndicatorType().getId() == resultType.getId())
//            
//                .forEach(
//                    i -> {
//                        switch(i.getIndicatorType().getName()) {
//                            case "PIDE":
//                                if (i.getStrategicItem() != null && i.getStrategicItem().getName().startsWith("7")) {
//                                    i.setIndicatorType(peType);
//                                    i.setStrategicItem(null);
//                                    i.setIsGlobal(true);
//                                    pe.add(i);
//                                } else {
//                                    pide.add(i);
//                                }
//                                
//                                break;
//                            case "MECASUT":
//                                mecasut.add(i);
//                                break;
//                            case "PE":
//                                if (i.getPe() != null && i.getPideIndicator() != null) {
//                                    Indicator global = mapPE.get(i.getPideIndicator().getId());
//                                    i.setPideIndicator(global);
//                                    pe.add(i);
//                                }
//                                
//                                break;
//                        }
//                        
////                        em.persist(i);
////                        persistedIndicators.add(i);
//                    }
//            );
//        
//        } catch (NoResultException nre) {
//            return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Indicator type not found.").build();
//        }
//        
//        Map<String, List<Indicator>> allIndicators = new HashMap<>();
////        allIndicators.put("persisted", persistedIndicators);
////        allIndicators.put("not persisted", notPersistedIndicators);
//        allIndicators.put("pide", pide);
//        allIndicators.put("mecasut", mecasut);
//        allIndicators.put("pe", pe);
//        
//        return Response.ok(allIndicators).build();
//    }
    @POST
    @Path("/indicators")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response setIndicators(String indicators, @QueryParam("type") String type, @QueryParam("isGlobal") String isGlobal) {
        String parsedInput = parse(indicators);
        EntityManager em = getEntityManager();
        
        
        List<Indicator> persistedIndicators = new ArrayList<>();
        List<Indicator> notPersistedIndicators = new ArrayList<>();
        Map<Long, Indicator> mapPE;
        final boolean isGlobalPE;
        final boolean isPE;
        
        if (type.equalsIgnoreCase("pe")) {
            mapPE = this.getPEGlobalIndicators();
            isPE = true;
            if (isGlobal.equalsIgnoreCase("true")) {
                isGlobalPE = true;
            } else {
                isGlobalPE = false;
            }
        } else {
            isPE = false;
            isGlobalPE = false;
        }
        
        Indicator[] mappedIndicators = mapper.readArray(new StringReader(parsedInput), Indicator.class);

        Stream.of(mappedIndicators)
            .forEach(
                i -> {
                    if (isPE) {
                        if (isGlobalPE) {
                            if (!i.getIsGlobal()) {
                                notPersistedIndicators.add(i);
                                return;
                            }
                        } else {
                            if (i.getIsGlobal()) {
                                notPersistedIndicators.add(i);
                                return;
                            }
                        }
                    }
                    
                    em.persist(i);
                    persistedIndicators.add(i);
                }
        );
        
        
        Map<String, List<Indicator>> allIndicators = new HashMap<>();
        allIndicators.put("persisted", persistedIndicators);
        allIndicators.put("not persisted", notPersistedIndicators);
        
        return Response.ok(allIndicators).build();
    }
    
    private Map<Long, Indicator> getPEGlobalIndicators() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Indicator> cq = cb.createQuery(Indicator.class);
        Root<Indicator> root = cq.from(Indicator.class);
        cq.select(root).where(
            cb.and(
                cb.like(cb.lower(root.get("indicatorType").get("name")), "pe")),
                cb.equal(root.get("isGlobal"), true)
            );
        
        TypedQuery<Indicator> q = em.createQuery(cq);
        Map<Long, Indicator> map = new HashMap<>();
        q.getResultList().stream()
                .forEach(i -> map.put(i.getId(), i));
        
        return map;
    }
    
    private String parse(String indicators) {
        Pattern timePattern = Pattern.compile("(\"date\":\\s)?(\\{\\s+\"date\":\\s\\d{1,2}.+?\"time\":\\s(\\d+),\\s+\"day\":\\s\\d{1,2}\\s+\\})", Pattern.MULTILINE | Pattern.DOTALL);
        Matcher matcher = timePattern.matcher(indicators);
        String replacedString = null;
        
        while(matcher.find()) {
            Instant instant = Instant.ofEpochMilli(Long.parseLong(matcher.group(3)));
            replacedString = matcher.replaceFirst("$1\"" + DateTimeFormatter.ISO_DATE.format(LocalDateTime.ofInstant(instant, ZoneId.of("GMT"))) + " 00:00:00\"");
            matcher = timePattern.matcher(replacedString);
        }
        
        return replacedString;
    }
    
    public EntityManager getEntityManager() {
        return em;
    }
}
