package model.converters;

import java.time.LocalDate;
import org.apache.johnzon.mapper.Converter;

/**
 *
 * @author alonsocucei
 */
public class LocalDateToDateConverter implements Converter<LocalDate> {

    @Override
    public String toString(LocalDate attribute) {
        return attribute.toString();
    }

    @Override
    public LocalDate fromString(String text) {
        return LocalDate.parse(text);
    }
}