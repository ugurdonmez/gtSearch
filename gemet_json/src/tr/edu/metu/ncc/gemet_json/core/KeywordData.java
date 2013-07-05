/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tr.edu.metu.ncc.gemet_json.core;

/**
 *
 * @author ugur
 */
public class KeywordData {
    private StringData preferredLabel;
    private StringData definition;
    private String uri;
    private String thesaurus;

    public KeywordData() {
    }

    public StringData getPreferredLabel() {
        return preferredLabel;
    }

    public void setPreferredLabel(StringData preferredLabel) {
        this.preferredLabel = preferredLabel;
    }

    public StringData getDefinition() {
        return definition;
    }

    public void setDefinition(StringData definition) {
        this.definition = definition;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getThesaurus() {
        return thesaurus;
    }

    public void setThesaurus(String thesaurus) {
        this.thesaurus = thesaurus;
    }
    
}
