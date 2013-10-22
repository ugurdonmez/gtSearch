/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tr.edu.metu.ncc.cng.gtsearch.xmlparser.solr;

/**
 *
 * @author ugur
 */
public class SolrDocument {
    
    private String title;
    private String host;
    //private String content;
    //private String url;

    public SolrDocument() {
    }

    public SolrDocument(String title, String host, String content, String url) {
        this.title = title;
        this.host = host;
        //this.content = content;
        //this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

//    public String getContent() {
//        return content;
//    }
//
//    public void setContent(String content) {
//        this.content = content;
//    }
//
//    public String getUrl() {
//        return url;
//    }
//
//    public void setUrl(String url) {
//        this.url = url;
//    }
    
}
