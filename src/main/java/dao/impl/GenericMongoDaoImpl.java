package dao.impl;

import com.mongodb.client.MongoCollection;
import dao.GenericMongoDao;
import dao.database.MongoConnection;
import model.MongoDocumentEntity;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

/**
 * Implementation of Generic DAO for mongo database
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0
 */
public abstract class GenericMongoDaoImpl<T extends MongoDocumentEntity> implements GenericMongoDao<T> {

    protected MongoCollection<T> collection;

    GenericMongoDaoImpl() {
        collection = MongoConnection.getInstance().getDatabase().getCollection(getCollectionName(), getModelClass());
    }

    @Override
    public void create(T record) {
        collection.insertOne(record);
    }

    @Override
    public void update(T record) {
        collection.replaceOne(eq("_id", record.getId()), record);
    }

    @Override
    public T findByObjectId(ObjectId id) {
       return collection.find(eq("_id", id)).first();
    }

    @Override
    public void deleteByObjectId(ObjectId id) {
        collection.deleteOne(eq("_id", id));
    }

    @Override
    public List<T> findAll() {
        return collection.find().into(new ArrayList<T>());
    }
}
