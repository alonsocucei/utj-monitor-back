package services.security;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.johnzon.mapper.Mapper;
import org.apache.johnzon.mapper.MapperBuilder;

/**
 *
 * @author alonsocucei
 */
public class SecurityService {
    private static final String auth0UsersURL = "https://handsonprogramming.auth0.com/api/v2/users/";
    
    public static String getProfile(String accessToken, String userId) throws IOException {
        return executePost(auth0UsersURL + userId, "Bearer " + accessToken);
    }
    
    public static Optional<Response> isIndicatorAuthorized(String accessToken, String userId,
            List<String> roles, Optional<Long> indicatorId) {
        Optional<Response> optionalResponse = Optional.ofNullable(null);
        
        try {
            if (!isAuthorized(accessToken, userId, roles, indicatorId)) {
                optionalResponse = Optional.ofNullable(Response
                            .status(javax.ws.rs.core.Response.Status.FORBIDDEN)
                            .entity("El usuario no tiene permiso de modificar este indicador.")
                            .type(MediaType.TEXT_PLAIN_TYPE)
                            .build()
                );
            }
        } catch (IOException ioe) {
            optionalResponse = Optional.ofNullable(
                    Response.serverError().entity(ioe.getMessage()).build()
            );
        }
        
        return optionalResponse;
    }
            
    public static Optional<Response> isFullWriteAuthorized(String accessToken, String userId) {
        Optional<Response> optionalResponse = Optional.ofNullable(null);
        
        try {
            if (!isAuthorized(accessToken, userId)) {
                optionalResponse = Optional.ofNullable(Response
                            .status(javax.ws.rs.core.Response.Status.FORBIDDEN)
                            .entity("Usuario sin permiso para modificar contenido.")
                            .type(MediaType.TEXT_PLAIN_TYPE)
                            .build()
                );
            }
        } catch (IOException ioe) {
            optionalResponse = Optional.ofNullable(
                    Response.serverError().entity(ioe.getMessage()).build()
            );
        }
        
        return optionalResponse;
    }
    
    public static boolean isAuthorized(String accessToken, String userId) throws IOException{
        final String profile = SecurityService.getProfile(accessToken, userId);
        boolean isRoleAuthorized = isRoleAuthorized(profile, Collections.singletonList("Writer"));
        return isRoleAuthorized && getIndicatorIds(profile).isEmpty();
    }
    
    public static boolean isAuthorized(String accessToken, String userId,
            List<String> roles, Optional<Long> indicatorId) throws IOException {
        boolean isRoleAuthorized = false;
        
        if (indicatorId.isPresent()) {
            final String profile = SecurityService.getProfile(accessToken, userId);
            isRoleAuthorized = isRoleAuthorized(profile, roles);

            if (isRoleAuthorized) {
                List<Number> indicatorIds = getIndicatorIds(profile);
                
                if (indicatorIds.isEmpty()) {
                    return true;
                }
                
                Long peIdLong = indicatorId.get();
                return indicatorIds.stream().anyMatch(id -> peIdLong.longValue() == id.longValue());
            }

            return false;
        } else {
            return isAuthorized(accessToken, userId, roles);
        }
    }
    
    private static List<Number> getIndicatorIds(String profile) {
        final Mapper mapper = new MapperBuilder().build();
        Map <String, Map> profileMap = mapper.readObject(profile, Map.class);
        Map <String, List<Number>> metadataMap = profileMap.get("user_metadata");
        List<Number> indicators = metadataMap.get("indicators");
        
        return indicators == null ? Collections.EMPTY_LIST : indicators;
    }
    
    public static boolean isAuthorized(String accessToken, String userId, List<String> roles) throws IOException {
        final String profile = SecurityService.getProfile(accessToken, userId);
        return isRoleAuthorized(profile, roles);
    }
    
    private static boolean isRoleAuthorized(String profile, List<String> roles) throws IOException {
        boolean isAuthorized = false;
        final Mapper mapper = new MapperBuilder().build();
        Map <String, Map> profileMap = mapper.readObject(profile, Map.class);
        Map <String, Map> metadataMap = profileMap.get("app_metadata");
        Map <String, List<String>> authorizationMap = metadataMap.get("authorization");
        
        List<String> rolesList = authorizationMap.get("roles");

        for (String role: rolesList) {
            if (roles.contains(role)) {
                isAuthorized = true;
                break;
            }
        }
        
        return isAuthorized;
    }
            
    private static String executePost(String targetURL, String urlParameters) 
        throws IOException {
        HttpURLConnection connection = null;

        try {
            //Create connection
            URL url = new URL(targetURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");

            connection.setRequestProperty("Authorization", urlParameters);
            connection.setUseCaches(false);
            connection.setDoOutput(false);

            //Get Response  
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder(); 
            
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            
            rd.close();
            
            return response.toString();
        } catch (IOException e) {
            throw new IOException("An error ocurred in the request to Auth0: " + e.getMessage());
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
