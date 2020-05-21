package br.edu.ifpb;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import com.mongodb.gridfs.GridFS;
import org.bson.types.ObjectId;

import java.io.File;
import java.io.FileInputStream;

public class testDB {
    public static void main(String[] args) {
        MongoClientURI uri = new MongoClientURI("mongodb+srv://Joshaby:7070@cluster-e8gs6.gcp.mongodb.net/test?retryWrites=true&w=majority");
        MongoClient mongoClient = new MongoClient(uri);
        MongoDatabase database = mongoClient.getDatabase("sample_analytics");
        database.createCollection("Teste");
        for (String name : database.listCollectionNames()) System.out.println(name);
    }

    public static byte[] LoadImage(String filePath) throws Exception {
        File file = new File(filePath);
        int size = (int)file.length();
        byte[] buffer = new byte[size];
        FileInputStream in = new FileInputStream(file);
        in.read(buffer);
        in.close();
        return buffer;
    }
//    public ObjectId upload(String filePath, String fileName) {
//        ObjectId fileId = null;
//        try {
//            MongoDatabase imgDb = mongo.getDatabase("imageDatabase");
//
//// Create a gridFSBucket
//            GridFSBucket gridBucket = GridFSBuckets.create(imgDb);
//
//
//            InputStream inStream = new FileInputStream(new File(filePath));
//
//// Create some customize options for the details that describes
//// the uploaded image
//            GridFSUploadOptions uploadOptions = new GridFSUploadOptions().chunkSizeBytes(1024).metadata(new Document("type", "image").append("content_type", "image/png"));
//
//            fileId = gridBucket.uploadFromStream(fileName, inStream, uploadOptions);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            mongo.close();
//        }
//        return fileId;
//    }
}
