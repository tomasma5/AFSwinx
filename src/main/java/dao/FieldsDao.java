package dao;

import model.afclassification.Field;

/**
 * Mongo DAO for application screens
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0
 */
public interface FieldsDao extends AbstractGenericDao<Field> {

    /**
     * Gets field by class name, field name and type - which should be unique combination
     * @param className given class name
     * @param fieldName given field name
     * @param type given type
     * @return found field or null
     */
    public Field getByClassAndFieldNameAndType(String className, String fieldName, String type);
}
