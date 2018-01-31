package model;

import org.bson.types.ObjectId;

/**
 * Defines mongo document entity - all of classes which inherits from this class will have mongodb {@link ObjectId} identifier
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0
 */
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
