/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tr.edu.metu.ncc.cng.gtsearch.xmlparser.solr;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;

/**
 *
 * @author ugur
 */
public class SolrParser {
    
    public ArrayList<SolrDocument> getContextList(String xmlStr) throws ParserConfigurationException, SAXException, IOException {
        //Create a "parser factory" for creating SAX parsers
        SAXParserFactory spfac = SAXParserFactory.newInstance();

        //Now use the parser factory to create a SAXParser object
        SAXParser sp = spfac.newSAXParser();

        //Create an instance of this class; it defines all the handler methods
        SolrDefaultHandler handler = new SolrDefaultHandler();

        //Finally, tell the parser to parse the input and notify the handler
        sp.parse(new ByteArrayInputStream(xmlStr.toString().getBytes()), handler);
        
        return handler.getResultList();
    
    }
}
