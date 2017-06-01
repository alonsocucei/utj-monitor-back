package service.admin.strategic;

import java.util.Collections;
import java.util.Set;
import model.entities.StrategicItem;

/**
 *
 * @author alonsocucei
 */
public class StrategicItemService implements StrategicService {
    public Set<StrategicItem> findAll() {
        return Collections.EMPTY_SET;
    }
    
    public boolean addStrategicItem(StrategicItem strategicItem) {
        return false;
    }
    
    public boolean editStrategicItem(StrategicItem strategicItem) {
        return false;
    }
    
    public boolean removeStrategicItem(StrategicItem strategicItem) {
        return false;
    }
}
