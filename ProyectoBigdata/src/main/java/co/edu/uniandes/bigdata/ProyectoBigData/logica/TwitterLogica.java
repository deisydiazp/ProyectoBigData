package co.edu.uniandes.bigdata.ProyectoBigData.logica;

import co.edu.uniandes.bigdata.ProyectoBigData.dto.SentimientosTwitter;
import com.mongodb.MapReduceCommand;
import com.mongodb.MongoClient;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import org.bson.Document;

/**
 * @generated
 */
@Stateless
public class TwitterLogica {

    public static final String DB_SERVER = "localhost";
    public static final int DB_PORT = 27017;
    /*public static final String DB_NAME = "Grupo10Twitter";
    public static final String COLLECTION_NAME = "OriginalData";*/
    public static final String DB_NAME = "Grupo10TwitterAnnotated";
   // public static final String COLLECTION_NAME = "DatasetAnnoted_1";

    public List<SentimientosTwitter> obtenerDatasetsAnotados(int numDataset, String type) {

        String COLLECTION_NAME = "DatasetAnnoted_" + numDataset;
        MongoDatabase mongoDB = new MongoClient(DB_SERVER, DB_PORT).getDatabase(DB_NAME);
        MongoCollection mongoCollection = mongoDB.getCollection(COLLECTION_NAME);
        
        String atributo = "Annoted".equals(type) ? "sentiment_score_annoted" : "sentiment_score";
        
        AggregateIterable<Document> result = mongoCollection.aggregate(Arrays.asList(
                new Document("$group", new Document("_id", "$" + atributo).append("count", new Document("$sum", 1)))
        ));
       
        List<SentimientosTwitter> sentimientos = new ArrayList<>();
        for (Document doc : result) {
            SentimientosTwitter sentimiento = new SentimientosTwitter();
            String nombre = doc.get("_id", String.class);
            if(nombre.equals("Very positive"))
                nombre = "Very Pos.";
            if(nombre.equals("Very negative"))
                nombre = "Very Neg.";
            
            sentimiento.setId(nombre);
            sentimiento.setCantidad(doc.get("count", Integer.class));
            sentimientos.add(sentimiento);
        }
        return sentimientos;
    }
    
    public static List<SentimientosTwitter> obtenerCantidadUsuarios(int numDataset, String type){
        String COLLECTION_NAME = "DatasetAnnoted_" + numDataset;
        MongoDatabase mongoDB = new MongoClient(DB_SERVER, DB_PORT).getDatabase(DB_NAME);
        MongoCollection mongoCollection = mongoDB.getCollection(COLLECTION_NAME);
        
        AggregateIterable<Document> result = mongoCollection.aggregate(Arrays.asList(
                new Document("$group", new Document("_id", "$authorNickname").append("count", new Document("$sum", 1))),
                new Document("$match", new Document("count", new Document("$gt", 15))),
                new Document("$sort", new Document("count", -1))
            ));
        
        List<SentimientosTwitter> sentimientos = new ArrayList<>();
        int i = 0;
        for (Document doc : result) {
            String nombreUsuario = doc.get("_id", String.class);
            System.out.println(nombreUsuario + " - " + doc.get("count", Integer.class));
            if(nombreUsuario.equals(""))
                continue;
            SentimientosTwitter sentimiento = new SentimientosTwitter();           
            sentimiento.setId(nombreUsuario);
            sentimiento.setCantidad(doc.get("count", Integer.class));
            sentimientos.add(sentimiento);
            
            i++;
            if(i==20)
                break;
        }
        
        return sentimientos;
    }
    
    
    public static List<SentimientosTwitter> obtenerCantidadTemas(int numDataset,String type){
        String COLLECTION_NAME = "DatasetAnnoted_" + numDataset;
        MongoDatabase mongoDB = new MongoClient(DB_SERVER, DB_PORT).getDatabase(DB_NAME);
        MongoCollection mongoCollection = mongoDB.getCollection(COLLECTION_NAME);

        String regex = "";
        if(type.equals("Tags"))
            regex = "/(#[^\\s]+)/g";
        if(type.equals("Person"))
            regex = "/(@[^\\s]+)/g";
        
        String map = "function(){\n"
                + "		var resultados=this.content.match(" + regex + ");\n"
                + "		if(resultados)for(var i=0;i<resultados.length;i++){\n"
                + "			emit(resultados[i],1);\n"
                + "		}\n"
                + "	}";
        String reduce = "function(key, values){\n"
                + "		return Array.sum(values);\n"
                + "	}";

        MongoCursor iterador = mongoCollection.mapReduce(map, reduce).filter(new Document("content", new Document("$regex", "#[^\\s]+"))).iterator();

        Map<String, Integer> resultados=new HashMap<>();
        while (iterador.hasNext()) {
            Document next = (Document) iterador.next();
            resultados.put(next.getString("_id"), next.get("value", Double.class).intValue());
        }
        resultados=sortHashMapByValues(resultados);
        
        List<SentimientosTwitter> sentimientos = new ArrayList<>();
        
        int i = 0;
        for(String key:resultados.keySet()){
            System.out.println(key+"-"+resultados.get(key));
            SentimientosTwitter sentimiento = new SentimientosTwitter();           
            sentimiento.setId(key);
            sentimiento.setCantidad(resultados.get(key));
            sentimientos.add(sentimiento);
            
            i++;
            if(i==20)
                break;
        }
        
        return sentimientos;
        
    }

    public static void main(String[] args) {
    
        //List<SentimientosTwitter> sentimientos = obtenerCantidadUsuarios();
    }

    public static LinkedHashMap<String, Integer> sortHashMapByValues(
            Map<String, Integer> passedMap) {
        List<String> mapKeys = new ArrayList<>(passedMap.keySet());
        List<Integer> mapValues = new ArrayList<>(passedMap.values());
        Collections.sort(mapValues, Collections.reverseOrder());
        Collections.sort(mapKeys);

        LinkedHashMap<String,Integer> sortedMap
                = new LinkedHashMap<>();

        Iterator<Integer> valueIt = mapValues.iterator();
        while (valueIt.hasNext()) {
            Integer val = valueIt.next();
            Iterator<String> keyIt = mapKeys.iterator();

            while (keyIt.hasNext()) {
                String key = keyIt.next();
                Integer comp1 = passedMap.get(key);
                Integer comp2 = val;

                if (comp1.equals(comp2)) {
                    keyIt.remove();
                    sortedMap.put(key, val);
                    break;
                }
            }
        }
        return sortedMap;
    }
}
