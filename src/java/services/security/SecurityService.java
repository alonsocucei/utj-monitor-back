package services.security;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
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
    
    public static boolean isAuthorized(String accessToken, String userId, List<String> roles) throws IOException {
        boolean isAuthorized = false;
        final Mapper mapper = new MapperBuilder().build();
        final String profile = SecurityService.getProfile(accessToken, userId);
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
