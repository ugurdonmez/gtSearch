/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tr.edu.metu.ncc.gemet_json.core;

/**
 *
 * @author ugur
 */
public class RelationData {
    private String source;
    private String relation;
    private String target;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public RelationData() {
    }
    
    public String toXmlRelation() {
        
        StringBuilder buf = new StringBuilder();
        
        String[] sub = relation.split("/");
        
        String[] last = sub[sub.length-1].split("#");
        
        if ( last[last.length-1].equals("narrower")) {
            buf.append("<skos:narrower rdf:resource=\""+target+"\"/>");
        }
        else if (last[last.length-1].equals("broader")) {
            buf.append("<skos:broader rdf:resource=\""+target+"\"/>");
        }
        else if (last[last.length-1].equals("related")) {
            buf.append("<skos:related rdf:resource=\""+target+"\"/>");
        }
        
        buf.append("\n");
        
        return buf.toString();
    }
}
