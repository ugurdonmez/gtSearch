/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tr.edu.metu.ncc.cng.gtsearch.ks;

import java.util.HashMap;
import tr.edu.metu.ncc.cng.gtsearch.solr.SolrCache;

/**
 *
 * @author ugur
 */
public class KSCache {
    
    private HashMap<String, String> ksCache;
    
    private static KSCache instance = null;

    public HashMap<String, String> getKsCache() {
        return ksCache;
    }

    public KSCache() {
        ksCache = new HashMap<String, String>();
    }

    public static KSCache getInstance() {
        if (instance == null) {
            synchronized (KSCache.class) {
                if (instance == null) {
                    instance = new KSCache();
                }
            }
        }
        return instance;
    }  
}
