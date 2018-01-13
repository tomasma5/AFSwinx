package model;

import org.bson.types.ObjectId;

public class MongoDocumentEntity {

    MongoDocumentEntity() { }

    private ObjectId id;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }
}
