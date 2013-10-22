/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tr.edu.metu.ncc.cng.gtsearch.core;

import java.net.MalformedURLException;
import java.util.ArrayList;
import tr.edu.metu.ncc.cng.gtsearch.ks.KSManager;
import tr.edu.metu.ncc.cng.gtsearch.solr.SolrManager;
import tr.edu.metu.ncc.cng.gtsearch.xmlparser.ks.MohseContext;
import tr.edu.metu.ncc.cng.gtsearch.xmlparser.solr.SolrDocument;

/**
 *
 * @author ugur
 */
public class MultilingualSearchManager {
    
    
    // create new service for exact mathes-- 
    // dont need to call ks again
    public ArrayList<SolrDocument> getQueryResultsForExactMatchWithTrKeyword(String turkishKeyword) throws Exception {
        
        SolrManager solrManager = new SolrManager();
        
        return solrManager.getResultFromSolrServer(turkishKeyword);        
    }
    
    public ArrayList<SolrDocument> getRelations(String server, String context, String concept, String relation, String lang) throws Exception {
        
        KSManager ksManager = new KSManager();
        SolrManager solrManager = new SolrManager();
        
        ArrayList<MohseContext> keywordsTr = ksManager.getRelationQuery(server, context, concept, relation, lang);
        
        return solrManager.getResultFromSolrServer(keywordsTr);        
    }
    
    public ArrayList<SolrDocument> getQueryResults(String server, String context, String keyword) throws Exception {
        
        KSManager ksManager = new KSManager();
        SolrManager solrManager = new SolrManager();
        
        ArrayList<MohseContext> keywordsTr = ksManager.getQuery(server, context, keyword);
        
        return solrManager.getResultFromSolrServer(keywordsTr);
    }
    
    public ArrayList<MohseContext> getAllLabels(String server, String context, String lang) throws Exception {
        
        KSManager ksManager = new KSManager();
        
        return ksManager.getAllConceptLabel(server, context, lang);
        
    }
}
