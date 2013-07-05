/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tr.edu.metu.ncc.cng.gtsearch.ks;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import tr.edu.metu.ncc.cng.gtsearch.xmlparser.ks.MohseContext;
import tr.edu.metu.ncc.cng.gtsearch.xmlparser.ks.MohseParser;

/**
 *
 * @author ugur
 */
public class KSManager {
    
    public ArrayList<MohseContext> getQuery(String context,String keyword) throws Exception {
        
        String concept = null;
       
        String urlTurkish;
        String xmlConceptsTr;
        ArrayList<MohseContext> contextListTr = new ArrayList<MohseContext>();
        
        // find labelled concept
        String url = createLabelledConceptQuery(context, keyword);
        String xmlConcepts = getResultsFromKS(url);
        
        // take concept id
        MohseParser xmlParser = new MohseParser();
        ArrayList<MohseContext> contextList = xmlParser.getContextList(xmlConcepts);
        
        // exact match
        System.out.println("ugur size");
        System.out.println(contextList.size());
        
        if ( contextList.isEmpty() ) {
            url = createIncludeConceptLabelQuery(context, keyword);
            xmlConcepts = getResultsFromKS(url);
            contextList = xmlParser.getContextList(xmlConcepts);
        }
        
        // take Tr label for each context
        for (Iterator<MohseContext> it = contextList.iterator(); it.hasNext();) {
            concept = it.next().getConcept();
            urlTurkish = createPreferredConceptQuery(context, concept, "tr");
            xmlConceptsTr = getResultsFromKS(urlTurkish);
            contextListTr.addAll(xmlParser.getContextList(xmlConceptsTr));
        }
        
        if ( contextListTr.isEmpty() ) {
            throw new ConceptNotFoundException("not found " + keyword);
        }
        
        return contextListTr;
        
    }
    
    private String getResultsFromKS(String URL) throws Exception{
        
        StringBuilder buf = new StringBuilder();
        
        URL ks_url = new URL(URL);
        URLConnection ks_con = ks_url.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(ks_con.getInputStream()));
        String inputLine;

        while ((inputLine = in.readLine()) != null) {
            buf.append(inputLine);
        }
        in.close();
        
        return buf.toString();
    }
    
    private String createLabelledConceptQuery(String context,String keyword) {
        
        StringBuilder buf = new StringBuilder();
        
        buf.append("http://localhost:8080/ks/KnowledgeService?service=labelledConcepts&context=");
        buf.append(context);
        buf.append("&arg=");
        buf.append(keyword);
        
        return buf.toString();
    }
    
    private String createIncludeConceptLabelQuery(String context,String keyword) {
        
        StringBuilder buf = new StringBuilder();
        
        buf.append("http://localhost:8080/ks/KnowledgeService?service=includeConceptLabel&context=");
        buf.append(context);
        buf.append("&arg=");
        buf.append(keyword);
        
        return buf.toString();
    }
    
    private String createPreferredConceptQuery(String context,String concept,String lang) { 
        
        StringBuilder buf = new StringBuilder();
        buf.append("http://localhost:8080/ks/KnowledgeService?service=preferredConceptLabel&context=");
        buf.append(context);
        buf.append("&arg=");
        buf.append(concept);
        buf.append(",");
        buf.append(lang);
        
        return buf.toString();
    }
    
    
}
