package service.admin.strategic;

import java.util.Set;
import model.entities.StrategicItem;

/**
 *
 * @author alonsocucei
 */
public interface StrategicService {
    Set<StrategicItem> findAll();
    boolean addStrategicItem(StrategicItem strategicItem);
    boolean removeStrategicItem(StrategicItem strategicItem);
    boolean editStrategicItem(StrategicItem strategicItem);
}
