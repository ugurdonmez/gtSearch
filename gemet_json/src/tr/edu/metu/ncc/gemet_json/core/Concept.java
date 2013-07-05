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
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author uğur
 */
public class Concept {
    
    private String about;
    private String langTR;
    private String langEN;
    private ArrayList<String> relatedList;
    private ArrayList<String> narrowerList;
    private ArrayList<String> broaderList;

    public Concept(String uri) {
        
        relatedList = new ArrayList<String>();
        narrowerList = new ArrayList<String>();
        broaderList = new ArrayList<String>();
        
        this.about = uri;
        ClientConfig config = new DefaultClientConfig();
        Client client = Client.create(config);
        
        WebResource service = client.resource(URIBuilder.getAllTranslationsForConcept(about));
        
        String languages = service.accept(MediaType.APPLICATION_XML).get(String.class);
        
        Gson gson = new Gson();
        
        StringData[] datas = gson.fromJson(languages, StringData[].class);
        
        for ( StringData data : datas ) {
            if ( data.getLanguage().equals("en") ) {
                langEN = data.getString();
            }
            if ( data.getLanguage().equals("tr") ) {
                langTR = data.getString();
            }
        }
        
        service = client.resource(URIBuilder.getAllConceptRelativesURI(about));
        
        String relatives = service.accept(MediaType.APPLICATION_XML).get(String.class);
        
        RelationData[] relations = gson.fromJson(relatives, RelationData[].class);
        
        for ( RelationData relation : relations ) {
            if ( relation.getRelation().equals("http://www.w3.org/2004/02/skos/core#broader") ) {
                broaderList.add(relation.getTarget());
            }
            if ( relation.getRelation().equals("http://www.w3.org/2004/02/skos/core#related") ) {
                relatedList.add(relation.getTarget());
            }
            if ( relation.getRelation().equals("http://www.w3.org/2004/02/skos/core#narrower") ) {
                narrowerList.add(relation.getTarget());
            }
        }
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getLangTR() {
        return langTR;
    }

    public void setLangTR(String langTR) {
        this.langTR = langTR;
    }

    public String getLangEN() {
        return langEN;
    }

    public void setLangEN(String langEN) {
        this.langEN = langEN;
    }

    public ArrayList<String> getRelatedList() {
        return relatedList;
    }

    public void setRelatedList(ArrayList<String> relatedList) {
        this.relatedList = relatedList;
    }

    public ArrayList<String> getNarroverList() {
        return narrowerList;
    }

    public void setNarroverList(ArrayList<String> narroverList) {
        this.narrowerList = narroverList;
    }

    public ArrayList<String> getBroaderList() {
        return broaderList;
    }

    public void setBroaderList(ArrayList<String> broaderList) {
        this.broaderList = broaderList;
    }
    
    public String xmlConcept() {
        
        StringBuilder xmlBuf = new StringBuilder();
        
        xmlBuf.append("\n");
        xmlBuf.append("\t<skos:Concept rdf:about=\"").append(this.about).append("\">\n");
        
        // lang
        xmlBuf.append("\t\t<skos:altLabel xml:lang=\"tr\">").append(this.langTR).append("</skos:altLabel>\n");
        xmlBuf.append("\t\t<skos:altLabel xml:lang=\"en\">").append(this.langEN).append("</skos:altLabel>\n");
        
        // relations
        for ( String narrower : narrowerList ) {
            xmlBuf.append("\t\t<skos:narrower rdf:resource=\"").append(narrower).append("\"/>\n");
        }
        for ( String related : relatedList ) {
            xmlBuf.append("\t\t<skos:related rdf:resource=\"").append(related).append("\"/>\n");
        }
        for ( String broader : broaderList ) {
            xmlBuf.append("\t\t<skos:broader rdf:resource=\"").append(broader).append("\"/>\n");
        }
        
        xmlBuf.append("\t</skos:Concept>\n");
      
        return xmlBuf.toString();
    }
    
    public List<String> getAllRelation() {
        
        ArrayList<String> allRelations = new ArrayList<>();
        
        allRelations.addAll(this.broaderList);
        allRelations.addAll(this.relatedList);
        allRelations.addAll(this.narrowerList);
        
        return allRelations;
    }
}
