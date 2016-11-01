package utils.json;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Set;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;
import models.forms.Catalog;
import models.forms.Catalog.CatalogEntry;

/**
 * @author alonsocucei
 */
public class CatalogsGenerator {

    public static String catalogsToJson(Set<Catalog> catalogs) {
        String jsonCatalogsAsString = null;
        JsonObjectBuilder builder = Json.createObjectBuilder();

        for (Catalog catalog : catalogs) {
            JsonArrayBuilder valuesBuilder = Json.createArrayBuilder();

            for (CatalogEntry entry : catalog.getEntries()) {
                valuesBuilder.add(
                        Json.createObjectBuilder()
                        .add("value", entry.getValue())
                        .add("text", entry.getLabel())
                );
            }
            
            builder.add(catalog.getName(), valuesBuilder);
        }
        
        try(StringWriter stringWriter = new StringWriter();) {
            JsonObject jsonCatalogs = builder.build();

            JsonWriter jsonWriter = Json.createWriter(stringWriter);
            jsonWriter.writeObject(jsonCatalogs);
            jsonWriter.close();
            jsonCatalogsAsString = stringWriter.toString();
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
        
        return jsonCatalogsAsString;
    }
}
