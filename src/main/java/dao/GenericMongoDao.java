package dao;

import com.mongodb.client.MongoCollection;
import dao.database.MongoConnection;
import model.MongoDocumentEntity;
import org.bson.types.ObjectId;

import java.util.List;

public abstract class GenericMongoDao<T extends MongoDocumentEntity> {

    protected MongoCollection<T> collection;

    public GenericMongoDao() {
        collection = MongoConnection.getInstance().getDatabase().getCollection(getCollectionName(), getModelClass());
    }

    protected abstract Class getModelClass();

    protected abstract String getCollectionName();

    public abstract void create(T record);

    public abstract void update(T record);

    public abstract T findByObjectId(ObjectId id);

    public abstract void deleteByObjectId(ObjectId id);

    public abstract List<T> findAll();

}
