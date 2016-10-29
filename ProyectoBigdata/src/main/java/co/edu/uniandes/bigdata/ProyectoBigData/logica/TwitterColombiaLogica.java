/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.bigdata.ProyectoBigData.logica;

import co.edu.uniandes.bigdata.ProyectoBigData.util.DataGrapher;
import co.edu.uniandes.bigdata.ProyectoBigData.util.GraphValue;
import co.edu.uniandes.bigdata.ProyectoBigData.util.MongoDataRecord;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.ejb.Stateless;
import org.bson.Document;

/**
 *
 * @author Rodrigo B
 */
@Stateless
public class TwitterColombiaLogica {
    
    // BD Mongo
    public static final String DB_SERVER = "localhost";
    public static final int DB_PORT = 27017;
    public static final String DB_NAME = "Grupo10Twitter";
    public static final String COLLECTION_NAME = "OriginalData";

    private final MongoDatabase mongoDB;
    private final MongoCollection mongoCollection;

    public TwitterColombiaLogica() {

        // Mongo connection
        mongoDB = new MongoClient(DB_SERVER, DB_PORT).getDatabase(DB_NAME);
        if (mongoDB.getCollection(COLLECTION_NAME) == null) {
            mongoDB.createCollection(COLLECTION_NAME);
        }
        mongoCollection = mongoDB.getCollection(COLLECTION_NAME);

    }
    
    // to plot
    public List<MongoDataRecord> getInfluencers(String tagString){
        
        int limitResults = 10; 
        String userString = "\".\""; // regex para buscar todos los valores sin filtro
        
        if(tagString == null || tagString.equals("")){
            tagString = "\".\""; // regex para buscar todos los valores sin filtro
        }
        
        return getMongoFunctionResults("getInfluencers(" + tagString + ", " + userString + ")", limitResults);
        
    }

    // to plot
    public List<MongoDataRecord> getTopTopics(String userString){
        
        int limitResults = 10;
        String tagString = "\".\""; // regex para buscar todos los valores sin filtro
        
        if(userString == null || userString.equals("")){
            userString = "\".\""; // regex para buscar todos los valores sin filtro
        }
        
        return getMongoFunctionResults("getTopTopics(" + tagString + ", " + userString + ")", limitResults);
        
    }
    
    // to graphs
    public List<DataGrapher> getTwitterDashboard(String tagString, String userString, int limit){
            
        int limitResults = limit;
        
        if(tagString == null || tagString.equals("none")){
            tagString = "\".\""; // regex para buscar todos los valores sin filtro
        }else{
            tagString =  "\"" + tagString + "\"";
        }
        
        if(userString == null || userString.equals("none")){
            userString = "\".\""; // regex para buscar todos los valores sin filtro
        }else{
            userString =  "\"" + userString + "\"";
        }
        
        List<DataGrapher> graphs = new ArrayList<>();
        
        // get Hashtags(Y) in Time(X)
        graphs.add(
            createGrapherDataFromMongoResults("Tweets de temas en el Tiempo", DataGrapher.BARRAS, 
                getMongoFunctionResults("getDSTopicsInTime(" + tagString + ", " + userString + ")", limitResults), 
                true, false, false, false, // X_value
                false, false, true, false, // Y_value
                true, false, false // value
            )
        );
        graphs.add(
            createGrapherDataFromMongoResults("Retweets de temas en el Tiempo", DataGrapher.BARRAS, 
                getMongoFunctionResults("getDSTopicsInTime(" + tagString + ", " + userString + ")", limitResults), 
                true, false, false, false, // X_value
                false, false, true, false, // Y_value
                false, true, false // value
            )
        );
        graphs.add(
            createGrapherDataFromMongoResults("Seguidores de temas en el Tiempo", DataGrapher.BARRAS, 
                getMongoFunctionResults("getDSTopicsInTime(" + tagString + ", " + userString + ")", limitResults), 
                true, false, false, false, // X_value
                false, false, true, false, // Y_value
                false, false, true // value
            )
        );
        
        //getMongoFunctionResults("getGeneralInfo(" + tagString + ", " + userString + ")", limitResults);
       
        return graphs;
    }
    
    
    /*
    Only 1 true bool per X, Y and values
    */
    public DataGrapher createGrapherDataFromMongoResults(String graphName, String graphType, List<MongoDataRecord> mongoResults, 
            boolean hashtagX, boolean userX, boolean dateX, boolean sentimentX, 
            boolean hashtagY, boolean userY, boolean dateY, boolean sentimentY,
            boolean tweetV, boolean retweetV, boolean followersV){
        
        DataGrapher dg = new DataGrapher(graphName, graphType);
        
        mongoResults.stream().forEach((mdr) -> {
            
            //GraphValue values
            String x_label = null;
            String y_label = null;
            float value = 0;
            
            // check x_labels
            if(hashtagX){
                x_label = mdr.getHashtag();
            }else if(userX){
                x_label = mdr.getUser();
            }else if(dateX){
                x_label = mdr.getDate();
            }else if(sentimentX){
                x_label = mdr.getSentiment();
            }
            
            // check y_labels
            if(hashtagY){
                y_label = mdr.getHashtag();
            }else if(userY){
                y_label = mdr.getUser();
            }else if(dateY){
                y_label = mdr.getDate();
            }else if(sentimentY){
                y_label = mdr.getSentiment();
            }
            
            // check values
            if(tweetV){
                value = mdr.getTweets();
            }else if(retweetV){
                value = mdr.getRetweets();
            }else if(followersV){
                value = mdr.getFollowers();
            }
            
            dg.addGraph_value(new GraphValue(x_label, y_label, value));
            
        });
        
        return dg;
    }
    
    public List<MongoDataRecord> getMongoFunctionResults(String functionStringWithParameters, int limitResults){
        Document docMongo = mongoDB.runCommand(new Document("$eval", functionStringWithParameters));
        String collectionResults = docMongo.getString("retval");
        
        MongoCursor cursor = mongoDB.getCollection(collectionResults).find().sort( new Document("_id.date", 1)).limit(limitResults).iterator();
        List<MongoDataRecord> mongoResults = new ArrayList<>();
        
        while (cursor.hasNext()) {
            String resultString = cursor.next().toString();
            
            // obtener propiedades del resultado
            String hashtag = getValueFromMongoResult(resultString, "hashtag", false),
                user = getValueFromMongoResult(resultString, "user", false),
                date = getValueFromMongoResult(resultString, "date", false),
                sentiment = getValueFromMongoResult(resultString, "sentiment", false);
            
            float tweets = Float.parseFloat(getValueFromMongoResult(resultString, "totalTweets", true)), 
                retweets = Float.parseFloat(getValueFromMongoResult(resultString, "totalReTweet", true)), 
                followers = Float.parseFloat(getValueFromMongoResult(resultString, "totalFollowers", true));
            
            mongoResults.add(new MongoDataRecord(hashtag, user, date, sentiment, tweets, retweets, followers));
            
            System.out.println(hashtag + "|" + user + "|" +  date + "|" +  sentiment + "|" +  tweets + "|" +  retweets + "|" +  followers);
        }
        
        return mongoResults;
    }
    
    public String getValueFromMongoResult(String mongoResult, String property, boolean returnsNumberIfNoExists){
        
        Pattern regexItems = Pattern.compile("(?s)(?<=" + property + "=).+?(?=,|})");
        Matcher mItems = regexItems.matcher(mongoResult);
        
        if (mItems.find()) {
            return mItems.group(0);
        }else{
            if(returnsNumberIfNoExists){
                return "0";
            }else{
                return "";
            }
        }
    }
       
}
