/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tr.edu.metu.ncc.cng.gtsearch.xmlparser.ks;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

/**
 *
 * @author ugur
 */
public class MohseContextExclusionStrategy implements ExclusionStrategy{

    @Override
    public boolean shouldSkipField(FieldAttributes f) {
        return (f.getDeclaringClass() == MohseContext.class && f.getName().equals("concept"))||
            (f.getDeclaringClass() == MohseContext.class && f.getName().equals("context"));
    }

    @Override
    public boolean shouldSkipClass(Class<?> type) {
        return false;  
    }
    
}
