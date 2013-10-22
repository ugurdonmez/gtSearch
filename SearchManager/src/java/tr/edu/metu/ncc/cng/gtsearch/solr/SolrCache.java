/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tr.edu.metu.ncc.cng.gtsearch.solr;

import java.util.HashMap;

/**
 *
 * @author ugur
 */
public class SolrCache {

    private HashMap<String, String> solrCache;
    private static SolrCache instance = null;

    public HashMap<String, String> getSolrCache() {
        return solrCache;
    }
    
    private SolrCache() {
        solrCache = new HashMap<String, String>();
    }

    public static SolrCache getInstance() {
        if (instance == null) {
            synchronized (SolrCache.class) {
                if (instance == null) {
                    instance = new SolrCache();
                }
            }
        }
        return instance;
    }    
}
