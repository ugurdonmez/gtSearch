/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tr.edu.metu.ncc.cng.gtsearch.xmlparser.solr;

/**
 *
 * @author ugur
 */
import java.util.ArrayList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SolrDefaultHandler extends DefaultHandler {

    private String temp;
    private SolrDocument solrDocument;
    private ArrayList<SolrDocument> solrDocumentList = new ArrayList<SolrDocument>();
    private XmlType type;

    public ArrayList<SolrDocument> getResultList() {
        return solrDocumentList;
    }
    
    /*
     * When the parser encounters plain text (not XML elements),
     * it calls(this method, which accumulates them in a string buffer
     */
    public void characters(char[] buffer, int start, int length) {
        temp = new String(buffer, start, length);
    }


    public void startElement(String uri, String localName,
            String qName, Attributes attributes) throws SAXException {
        temp = "";
        if (qName.equalsIgnoreCase("doc")) {
            solrDocument = new SolrDocument();
        }
        else if (qName.equalsIgnoreCase("arr")) {
            if(attributes.getValue("name").equals("content")) {
                type = XmlType.CONTENT;
            }
            else if(attributes.getValue("name").equals("title")) {
                type = XmlType.TITLE;
            }
            else if(attributes.getValue("name").equals("host")) {
                type = XmlType.HOST;
            }
        }
        else if (qName.equalsIgnoreCase("str")) {
            if( attributes.getValue("name") != null ) {
                if(attributes.getValue("name").equals("url")) {
                    type = XmlType.URL;
                }
            }
        }
    }

    /*
     * When the parser encounters the end of an element, it calls this method
     */
    public void endElement(String uri, String localName, String qName)
            throws SAXException {

        if (qName.equalsIgnoreCase("doc")) {
            // add it to the list
            solrDocumentList.add(solrDocument);
        }
        else if (qName.equalsIgnoreCase("str")) {
            if (type == XmlType.CONTENT) {
                // solrDocument.setContent(temp);
            }
            else if (type == XmlType.TITLE) {
                solrDocument.setTitle(temp);
            }
            else if (type == XmlType.URL) {
                // solrDocument.setUrl(temp);
            }
            else if (type == XmlType.HOST) {
                solrDocument.setHost(temp);
            }
        }
    }
}
