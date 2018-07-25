package service.admin.strategic;

import java.util.Collections;
import java.util.Set;
import model.entities.StrategicItem;

/**
 *
 * @author alonsocucei
 */
public class StrategicItemServiceV2 implements StrategicServiceV2 {
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
