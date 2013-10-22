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
    
    public ArrayList<MohseContext> getQuery(String server, String context, String keyword) throws Exception {
        
        String concept = null;
       
        String urlTurkish;
        String xmlConceptsTr;
        ArrayList<MohseContext> contextListTr = new ArrayList<MohseContext>();
        
        // find labelled concept
        String url = createLabelledConceptQuery(server, context, keyword);
        String xmlConcepts = getResultsFromKS(url);
        
        // take concept id
        MohseParser xmlParser = new MohseParser();
        ArrayList<MohseContext> contextList = xmlParser.getContextList(xmlConcepts);
        
        // exact match
        if ( contextList.isEmpty() ) {
            url = createIncludeConceptLabelQuery(server, context, keyword);
            xmlConcepts = getResultsFromKS(url);
            contextList = xmlParser.getContextList(xmlConcepts);
        }
        
        // take Tr label for each context
        for (Iterator<MohseContext> it = contextList.iterator(); it.hasNext();) {
            concept = it.next().getConcept();
            urlTurkish = createPreferredConceptQuery(server, context, concept, "tr");
            xmlConceptsTr = getResultsFromKS(urlTurkish);
            contextListTr.addAll(xmlParser.getContextList(xmlConceptsTr));
        }
        
//        if ( contextListTr.isEmpty() ) {
//            throw new ConceptNotFoundException("not found " + keyword);
//        }
        
        return contextListTr;
        
    }
    
    public ArrayList<MohseContext> getAllConceptLabel(String server, String context, String lang ) throws Exception {
        
        String url = createPreferredConceptLabelAllQuery(server, context, lang);
        
        MohseParser xmlParser = new MohseParser();
        
        String xmlConcepts = getResultsFromKS(url);
        ArrayList<MohseContext> contextList = xmlParser.getContextList(xmlConcepts);
        
        return contextList;
    }
    
    public ArrayList<MohseContext> getRelationQuery(String server, String context, String concept, String relation, String lang) throws Exception {
        
        String url = createlinkedConceptsLangQuery(server, context, concept, relation, lang);
        
        MohseParser xmlParser = new MohseParser();
        
        String xmlConcepts = getResultsFromKS(url);
        ArrayList<MohseContext> contextList = xmlParser.getContextList(xmlConcepts);
        
        return contextList;
    }
    
    
    
    private String getResultsFromKS(String URL) throws Exception{
        
        // control cache if contains send directly
        if ( KSCache.getInstance().getKsCache().containsKey(URL) ) {
            return KSCache.getInstance().getKsCache().get(URL);
        }
        
        StringBuilder buf = new StringBuilder();
        
        URL ks_url = new URL(URL);
        URLConnection ks_con = ks_url.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(ks_con.getInputStream()));
        String inputLine;

        while ((inputLine = in.readLine()) != null) {
            buf.append(inputLine);
        }
        in.close();
        
        // insert to KSCache
        KSCache.getInstance().getKsCache().put(URL, buf.toString());
        
        return buf.toString();
    }
    
    private String createlinkedConceptsLangQuery(String server, String context, String concept, String relation, String lang) {
        
        StringBuilder buf = new StringBuilder();
        
        buf.append(server);
        buf.append("?service=linkedConceptsLang&context=");
        buf.append(context);
        buf.append("&arg=");
        buf.append(concept);
        buf.append(",");
        buf.append(relation);
        buf.append(",");
        buf.append(lang);
        
        return buf.toString();
    }
    
    private String createPreferredConceptLabelAllQuery(String server, String context, String lang) {
        
        StringBuilder buf = new StringBuilder();
        
        buf.append(server);
        buf.append("?service=preferredConceptLabelAll&context=");
        buf.append(context);
        buf.append("&arg=");
        buf.append(lang);
        
        return buf.toString();
    }
    
    private String createLabelledConceptQuery(String server, String context, String keyword) {
        
        StringBuilder buf = new StringBuilder();
        
        buf.append(server);
        buf.append("?service=labelledConcepts&context=");
        buf.append(context);
        buf.append("&arg=");
        buf.append(keyword);
        
        return buf.toString();
    }
    
    private String createIncludeConceptLabelQuery(String server, String context, String keyword) {
        
        StringBuilder buf = new StringBuilder();
        
        buf.append(server);
        buf.append("?service=includeConceptLabel&context=");
        buf.append(context);
        buf.append("&arg=");
        buf.append(keyword);
        
        return buf.toString();
    }
    
    private String createPreferredConceptQuery(String server, String context, String concept, String lang) { 
        
        StringBuilder buf = new StringBuilder();
        
        buf.append(server);
        buf.append("?service=preferredConceptLabel&context=");
        buf.append(context);
        buf.append("&arg=");
        buf.append(concept);
        buf.append(",");
        buf.append(lang);
        
        return buf.toString();
    }

    
    
}
