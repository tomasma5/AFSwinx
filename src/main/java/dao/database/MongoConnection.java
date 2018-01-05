package dao.database;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;

import java.util.Arrays;
import java.util.Collections;

import static dao.database.DBConstants.*;

public class MongoConnection {

    private static MongoConnection instance;

    private MongoDatabase database;

    private MongoConnection() {
        MongoCredential credential = MongoCredential.createCredential(DB_USER, DB_AUTH_DATABASE, DB_PASSWORD.toCharArray());
        MongoClient mongoClient = new MongoClient(new ServerAddress(DB_HOSTNAME, DB_PORT),
                Arrays.asList(credential));
        database = mongoClient.getDatabase(DB_NAME);
    }

    public static synchronized MongoConnection getInstance() {
        if (instance == null) {
            instance = new MongoConnection();
        }
        return instance;
    }

    public MongoDatabase getDatabase() {
        return database;
    }

}
