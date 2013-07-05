/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tr.edu.metu.ncc.gemet_json.core;

import java.net.URI;
import javax.ws.rs.core.UriBuilder;

/**
 *
 * @author uğur
 */
public class URIBuilder {
    
    public static URI getAllTranslationsForConcept( String concept ) {
        
        String uri = "http://www.eionet.europa.eu/gemet/getAllTranslationsForConcept?concept_uri="+concept+"&property_uri=http://www.w3.org/2004/02/skos/core%23prefLabel";
        
        return UriBuilder.fromUri(uri).build();
    }
    
    public static URI getConceptMatchingKeywordsURI( String keyword ) {
        
        String uri = "http://www.eionet.europa.eu/gemet/getConceptsMatchingKeyword?keyword="+keyword+"&search_mode=0&thesaurus_uri=http://www.eionet.europa.eu/gemet/concept/&language=en";
        
        return UriBuilder.fromUri(uri).build();
    }
    
    public static URI getAllConceptRelativesURI( String concept ) {
        
        String uri = "http://www.eionet.europa.eu/gemet/getAllConceptRelatives?concept_uri=" + concept;
        
        return UriBuilder.fromUri(uri).build();
        
    }

}
