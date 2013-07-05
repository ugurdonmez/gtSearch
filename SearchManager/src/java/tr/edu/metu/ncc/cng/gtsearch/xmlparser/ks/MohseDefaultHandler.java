/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tr.edu.metu.ncc.cng.gtsearch.xmlparser.ks;

/**
 *
 * @author ugur
 */
import java.util.ArrayList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class MohseDefaultHandler extends DefaultHandler {

    private MohseContext mohseContext;
    private String temp;
    private ArrayList<MohseContext> contextList = new ArrayList<MohseContext>();

    public ArrayList<MohseContext> getContextList() {
        return contextList;
    }
    
    /*
     * When the parser encounters plain text (not XML elements),
     * it calls(this method, which accumulates them in a string buffer
     */
    public void characters(char[] buffer, int start, int length) {
        temp = new String(buffer, start, length);
    }


    /*
     * Every time the parser encounters the beginning of a new element,
     * it calls this method, which resets the string buffer
     */
    public void startElement(String uri, String localName,
            String qName, Attributes attributes) throws SAXException {
        temp = "";
        if (qName.equalsIgnoreCase("cohse:concept")) {
            mohseContext = new MohseContext();
            mohseContext.setConcept(attributes.getValue("cohse:concept"));
            mohseContext.setContext(attributes.getValue("cohse:context"));
            //mohseContext.setTerm(attributes.getValue("cohse:term"));
        }
        else if (qName.equalsIgnoreCase("cohse:term")) {
            mohseContext = new MohseContext();
            mohseContext.setConcept(attributes.getValue("cohse:concept"));
            mohseContext.setContext(attributes.getValue("cohse:context"));
            mohseContext.setTerm(attributes.getValue("cohse:term"));
        }
    }

    /*
     * When the parser encounters the end of an element, it calls this method
     */
    public void endElement(String uri, String localName, String qName)
            throws SAXException {

        if (qName.equalsIgnoreCase("cohse:concept")) {
            // add it to the list
            contextList.add(mohseContext);
        } 
        else if (qName.equalsIgnoreCase("cohse:term")) {
            contextList.add(mohseContext);
        }

    }
}
