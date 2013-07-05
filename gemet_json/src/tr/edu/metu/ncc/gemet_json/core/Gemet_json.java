/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tr.edu.metu.ncc.gemet_json.core;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import java.io.BufferedWriter;
import java.io.FileWriter;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author ugur
 */
public class Gemet_json {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        String keyword = "fishery";

        ClientConfig config = new DefaultClientConfig();
        Client client = Client.create(config);

        WebResource service = client.resource(URIBuilder.getConceptMatchingKeywordsURI(keyword));

        String keywordResponse = service.accept(MediaType.APPLICATION_XML).get(String.class);

        Gson gson = new Gson();

        KeywordData[] data = gson.fromJson(keywordResponse, KeywordData[].class);

        Concepts concepts = new Concepts(data[0].getUri());

        try {
            // Create file 
            FileWriter fstream = new FileWriter("out.txt");
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(concepts.xmlConcepts());
            //Close the output stream
            out.close();
        } catch (Exception e) {//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }

    }

    
}
