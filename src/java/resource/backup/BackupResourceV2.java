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
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import model.Section;
import model.entities.Backup;
import model.entities.BackupSection;
import model.entities.Indicator;
import model.entities.StrategicItem;
import org.apache.johnzon.mapper.Mapper;
import org.apache.johnzon.mapper.MapperBuilder;
import resource.ResourceBaseV2;

/**
 *
 * @author alonso.sanchez
 */
@Stateless
@Path("admin/data/backups")
public class BackupResourceV2 {

    @PersistenceContext(unitName = "UTJMonitor")
    private EntityManager em;

    private final Mapper mapper = new MapperBuilder().build();

    private Map<Section, Supplier<List>> getFunctionsMap() {
        Map<Section, Supplier<List>> functionsMap = new HashMap<>();

        functionsMap.put(Section.STRATEGIC, () -> ResourceBaseV2.findAll(em, StrategicItem.class));
//        functionsMap.put(Section.PIDE, () -> ResourceBaseV2.findAll(em, StrategicItem.class));
//        functionsMap.put(Section.STRATEGIC, () -> ResourceBaseV2.findAll(em, StrategicItem.class));
//        functionsMap.put(Section.STRATEGIC, () -> ResourceBaseV2.findAll(em, StrategicItem.class));
//        functionsMap.put(Section.STRATEGIC, () -> ResourceBaseV2.findAll(em, StrategicItem.class));
//        functionsMap.put(Section.STRATEGIC, () -> ResourceBaseV2.findAll(em, StrategicItem.class));
//        functionsMap.put(Section.STRATEGIC, () -> ResourceBaseV2.findAll(em, StrategicItem.class));

        return functionsMap;
    }

    private Long getMaxBackupId() {
        return (Long) em.createQuery("SELECT MAX(B.id) FROM Backup B").getSingleResult();
    }

    private Long getMaxSectionBackupId() {
        return (Long) em.createQuery("SELECT MAX(B.id) FROM BackupSection B").getSingleResult();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getBackupNames() {
        class BackupName {
            Long id;
            String name;
            
            public BackupName(Long id, String name) {
                this.id = id;
                this.name = name;
            }
        }

        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> cq = builder.createTupleQuery();
        Root<Backup> backup = cq.from(Backup.class);
        cq.multiselect(backup.<Long>get("id"), backup.<String>get("name"));
        List<Tuple> tupleResult = em.createQuery(cq).getResultList();
        List<BackupName> backupNames = tupleResult.stream().map(
                t -> {
                    return new BackupName((Long)t.get(0), (String)t.get(1));
                }
        ).collect(Collectors.toList());
        
        return Response.ok(backupNames).build();
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response createBackup(String backupName) {
        Backup newBackup = mapper.readObject(backupName, Backup.class);
        newBackup.setId(getMaxBackupId() + 1);
        List<BackupSection> backupSections = new ArrayList<>();
        Map<Section, Supplier<List>> functionsMap = getFunctionsMap();

        functionsMap.keySet()
                .forEach(
                        s -> {
                            BackupSection backupSection = new BackupSection();
                            String jsonData = mapper.writeArrayAsString(functionsMap.get(s).get());
                            backupSection.setId(getMaxSectionBackupId() + 1);
                            backupSection.setSection(s);
                            backupSection.setJsonData(jsonData);
                            backupSections.add(backupSection);
                        }
                );

        newBackup.setSections(backupSections);

        em.persist(newBackup);
        return Response.ok(newBackup).build();
    }
    
    @DELETE
    @Produces({MediaType.APPLICATION_JSON})
    public Response removeBackup(@PathParam("id") long id) {
        Backup entity = em.find(Backup.class, id);
        em.remove(em.merge(entity));
        
        return Response.ok().build();
    }
    
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

        while (matcher.find()) {
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
