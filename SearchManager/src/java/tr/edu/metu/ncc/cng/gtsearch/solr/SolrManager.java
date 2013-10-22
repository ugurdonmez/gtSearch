/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tr.edu.metu.ncc.cng.gtsearch.solr;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import tr.edu.metu.ncc.cng.gtsearch.xmlparser.ks.MohseContext;
import tr.edu.metu.ncc.cng.gtsearch.xmlparser.solr.SolrDocument;
import tr.edu.metu.ncc.cng.gtsearch.xmlparser.solr.SolrParser;

/**
 *
 * @author ugur
 */
public class SolrManager {
    
    public ArrayList<SolrDocument> getResultFromSolrServer(String query) throws MalformedURLException, IOException, ParserConfigurationException, SAXException {
        
        
        String url = createQueryUrl(query);
                
        String resultXml = getResultStrFromServer(url);
                
        SolrParser parser = new SolrParser();
        if( resultXml.equals("") ) {
            return new ArrayList<SolrDocument>();
        }
        else {
            return parser.getContextList(resultXml);
        }
    }
    
    public ArrayList<SolrDocument> getResultFromSolrServer(ArrayList<MohseContext> keywordsTr) throws MalformedURLException, IOException, ParserConfigurationException, SAXException {
        
        ArrayList<SolrDocument> documents = new ArrayList<SolrDocument>();
        
        for ( MohseContext context : keywordsTr ) {
            documents.addAll(getResultFromSolrServer(context.getTerm()));
        }
        
        return documents;
        
    }
    
    private String getResultStrFromServer(String URL) throws MalformedURLException {
        
        // control cache if contains send directly
        if (SolrCache.getInstance().getSolrCache().containsKey(URL)) {
            return SolrCache.getInstance().getSolrCache().get(URL);
        }
        
        StringBuilder buf = new StringBuilder();
        
        URL ks_url = new URL(URL);
        URLConnection ks_con;
        try {
            ks_con = ks_url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(ks_con.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                buf.append(inputLine);
            }
            in.close();
        } catch (IOException ex) {
            return buf.toString();
        }
        
        // insert to SolrCache
        SolrCache.getInstance().getSolrCache().put(URL, buf.toString());
         
        return buf.toString();
    }
        
    private String createQueryUrl (String query) {
        
        StringBuilder buf = new StringBuilder();
        
        buf.append("http://localhost:8983/solr/select?q=\"");
        buf.append(query);
        buf.append("\"");

        return buf.toString();
    }

    
    
}
