package dao;

import com.mongodb.client.MongoCollection;
import dao.database.MongoConnection;
import model.MongoDocumentEntity;
import org.bson.types.ObjectId;

import java.util.List;

/**
 * Generic DAO for mongo database
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0
 */
public abstract class GenericMongoDao<T extends MongoDocumentEntity> {

    protected MongoCollection<T> collection;

    GenericMongoDao() {
        collection = MongoConnection.getInstance().getDatabase().getCollection(getCollectionName(), getModelClass());
    }

    /**
     * Used to generify documents from Mongo db
     * @return class to which should be document casted
     */
    protected abstract Class getModelClass();

    /**
     * Gets name of collection which should DAO communicate with
     * @return name of corresponding collection in Mongo db
     */
    protected abstract String getCollectionName();

    /**
     * Creates new record in database
     * @param record records which should be inserted
     */
    public abstract void create(T record);

    /**
     * Updates record in database
     * @param record records which should be updated
     */
    public abstract void update(T record);

    /**
     * Finds record in database by object id
     * @param id object id of record which should be found
     * @return found record
     */
    public abstract T findByObjectId(ObjectId id);

    /**
     * Deletes record by object id
     * @param id object id of record which should be deleted
     */
    public abstract void deleteByObjectId(ObjectId id);

    /**
     * Finds all records
     * @return all records in given collection
     */
    public abstract List<T> findAll();

}
