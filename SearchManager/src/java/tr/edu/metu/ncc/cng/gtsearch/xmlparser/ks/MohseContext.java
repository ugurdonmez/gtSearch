/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tr.edu.metu.ncc.cng.gtsearch.xmlparser.ks;

/**
 *
 * @author ugur
 */
public class MohseContext {
    
    private String concept;
    private String context;
    private String term;

    public MohseContext() {
    }

    public MohseContext(String concept, String context, String term) {
        this.concept = concept;
        this.context = context;
        this.term = term;
    }

    public String getConcept() {
        return concept;
    }

    public void setConcept(String concept) {
        this.concept = concept;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }
    
}
