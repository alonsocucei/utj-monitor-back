package test.forms;

import java.util.HashSet;
import java.util.Set;
import models.forms.Catalog;

/**
 * @author alonsocucei
 */
public class ProfileCatalogs {
    public static Set<Catalog> getCatalogs() {
        Set<Catalog> catalogs = new HashSet<>();
        
        Catalog studyLevels = new Catalog("studyLevels", "The list of valid study levels for an employee.");
        studyLevels.addEntry(1, "Primaria");
        studyLevels.addEntry(2, "Secundaria");
        studyLevels.addEntry(4, "Bachillerato");
        studyLevels.addEntry(8, "Técnico Superior Universitario");
        studyLevels.addEntry(16, "Licenciatura");
        studyLevels.addEntry(32, "Maestría");
        studyLevels.addEntry(64, "Doctorado");
        
        Catalog studyStatus = new Catalog("studyStatus", "The list of status a study level can have.");
        studyStatus.addEntry(1, "En Curso");
        studyStatus.addEntry(2, "Egresado");
        studyStatus.addEntry(4, "Trunco");
        studyStatus.addEntry(8, "Titulado");
        
        Catalog secretariats = new Catalog("secretariats", "The list of secretariats at UTJ.");
        secretariats.addEntry(1, "Rectoría");
        secretariats.addEntry(2, "Académica");
        secretariats.addEntry(4, "Administrativa");
        secretariats.addEntry(8, "Vinculación");
        
        Catalog subAreas = new Catalog("subAreas", "The list of sub areas for each secretariats at UTJ.");
        subAreas.addEntry(1, "Planeación");
        subAreas.addEntry(2, "Carrera...");
        subAreas.addEntry(4, "Servicios Estudiantiles");
        
        Catalog positions = new Catalog("positions", "The name that receives a job position for an employee.");
        positions.addEntry(1, "Secretario(a)");
        positions.addEntry(2, "Director(a)");
        positions.addEntry(4, "Asistente");
        positions.addEntry(8, "Jefe(a) de Departamento");
        
        Catalog managers = new Catalog("managers", "The name of each manager at UTJ.");
        managers.addEntry(1, "Manager 1");
        managers.addEntry(2, "Manager 2");
        
        catalogs.add(studyLevels);
        catalogs.add(studyStatus);
        catalogs.add(secretariats);
        catalogs.add(subAreas);
        catalogs.add(positions);
        catalogs.add(managers);
        
        return catalogs;
    }
}
