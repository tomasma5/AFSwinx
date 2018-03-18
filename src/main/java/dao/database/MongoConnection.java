package dao.database;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.Collections;

import static dao.database.DBConstants.*;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

/**
 * Singleton class for connection with MongoDB
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0
 */
public class MongoConnection {

    private static MongoConnection instance;

    private MongoDatabase database;

    private MongoConnection() {
        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().register("model","model.partial", "model.afclassification").build()));
        MongoCredential credential = MongoCredential.createCredential(DB_USER, DB_AUTH_DATABASE, DB_PASSWORD.toCharArray());
        MongoClient mongoClient = new MongoClient(new ServerAddress(DB_HOSTNAME, DB_PORT),
                Collections.singletonList(credential), MongoClientOptions.builder().codecRegistry(pojoCodecRegistry).build());
        database = mongoClient.getDatabase(DB_NAME).withCodecRegistry(pojoCodecRegistry);
    }

    /**
     * Gets instance of mongo connection to Mongo database
     * @return mongo connection
     */
    public static synchronized MongoConnection getInstance() {
        if (instance == null) {
            instance = new MongoConnection();
        }
        return instance;
    }

    /**
     * Gets Mongo database
     * @return database
     */
    public MongoDatabase getDatabase() {
        return database;
    }

}
