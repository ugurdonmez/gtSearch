/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tr.edu.metu.ncc.gemet_json.core;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author uğur
 */
public final class Concepts {
    
    private Map<String,Concept> concepts;
    private String mainConcept;

    public Concepts(String concept) {
        
        System.out.println("Concepts constructors" + concept);
        
        concepts = new HashMap<String,Concept>();
        this.mainConcept = concept;
        addToConcepts(concept);
    }
    
    public void addToConcepts(String conceptUri) {
        
        System.out.println(conceptUri);
        
        if ( concepts.containsKey(conceptUri) == false) {
            Concept concept = new Concept(conceptUri);
            concepts.put(conceptUri, concept);
            for ( String key : concept.getAllRelation() ) {
                addToConcepts(key);
            }
        }
    }

    public Map<String, Concept> getConcepts() {
        return concepts;
    }

    public void setConcepts(Map<String, Concept> concepts) {
        this.concepts = concepts;
    }
    
    public String xmlConcepts() {
        
        StringBuilder xmlBuf = new StringBuilder();
        
        xmlBuf.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?>" +
"<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" "  +
         "xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\" "+
         "xmlns:skos=\"http://www.w3.org/2004/02/skos/core#\" "+
   "xml:base=\"http://www.eionet.europa.eu/gemet/\"> "+
"\n" +
"<skos:ConceptScheme rdf:about=\"gemetThesaurus\"> "+
"<rdfs:label>The GEMET Thesaurus</rdfs:label> " +
"</skos:ConceptScheme>\n");
        
        // add relations
        for ( Concept concept : concepts.values() ) {
            xmlBuf.append(concept.xmlConcept());
        }
        
        xmlBuf.append("</rdf:RDF>\n");
        
        return xmlBuf.toString();
    }
    
}
