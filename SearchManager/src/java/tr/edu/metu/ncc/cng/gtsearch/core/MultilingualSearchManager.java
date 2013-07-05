/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tr.edu.metu.ncc.cng.gtsearch.core;

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
    
    public ArrayList<SolrDocument> getQueryResults(String context,String keyword) throws Exception {
        
        KSManager ksManager = new KSManager();
        SolrManager solrManager = new SolrManager();
        
        ArrayList<MohseContext> keywordsTr = ksManager.getQuery(context, keyword);
        
        System.out.println("size " + keywordsTr.size());
        
        for ( MohseContext context1 : keywordsTr ) {
            System.out.println(context1.getTerm());
        }
        
        return solrManager.getResultFromSolrServer(keywordsTr);
    }
}
