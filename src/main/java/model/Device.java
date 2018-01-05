package model;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

public class Device extends Document {
    //TODO

    private ObjectId id;

    public ObjectId getId() {
        return id;
    }
}
