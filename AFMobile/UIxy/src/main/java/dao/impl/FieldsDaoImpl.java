package dao.impl;

import dao.FieldsDao;
import model.afclassification.Field;

import javax.enterprise.context.ApplicationScoped;
import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of DAO for fields
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0
 */
@ApplicationScoped
public class FieldsDaoImpl extends AbstractGenericDaoImpl<Field> implements FieldsDao {

    public FieldsDaoImpl() {
        super(Field.class);
    }

    @Override
    public Field getByClassAndFieldNameAndType(String className, String fieldName, String type) {
        String query = Field.CLASS_NAME + " = :className AND "+Field.FIELD_NAME + " = :fieldName AND "+Field.FIELD_TYPE+ " = :fieldType";
        Map<String, Object> params = new HashMap<>();
        params.put("className", className);
        params.put("fieldName", fieldName);
        params.put("fieldType", type);

        return getByWhereConditionSingleResult(query, params);
    }
}
