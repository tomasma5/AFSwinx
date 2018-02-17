package dao;

import model.MongoDocumentEntity;
import org.bson.types.ObjectId;

import java.util.List;

/**
 * Generic DAO for mongo database
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0
 */
public interface GenericMongoDao<T extends MongoDocumentEntity> {

    /**
     * Used to generify documents from Mongo db
     * @return class to which should be document casted
     */
    Class getModelClass();

    /**
     * Gets name of collection which should DAO communicate with
     * @return name of corresponding collection in Mongo db
     */
    String getCollectionName();

    /**
     * Creates new record in database
     * @param record records which should be inserted
     */
    void create(T record);

    /**
     * Updates record in database
     * @param record records which should be updated
     */
    void update(T record);

    /**
     * Finds record in database by object id
     * @param id object id of record which should be found
     * @return found record
     */
    T findByObjectId(ObjectId id);

    /**
     * Deletes record by object id
     * @param id object id of record which should be deleted
     */
    void deleteByObjectId(ObjectId id);

    /**
     * Finds all records
     * @return all records in given collection
     */
    List<T> findAll();

}
