/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tr.edu.metu.ncc.cng.gtsearch.servlets;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Document;
import org.w3c.tidy.Tidy;
import tr.edu.metu.ncc.cng.gtsearch.core.MultilingualSearchManager;
import tr.edu.metu.ncc.cng.gtsearch.xmlparser.ks.MohseContext;
import tr.edu.metu.ncc.cng.gtsearch.xmlparser.solr.SolrDocument;

/**
 *
 * @author ugur
 */
@WebServlet(name = "EnrichServlet", urlPatterns = {"/EnrichServlet"})
public class EnrichServlet extends HttpServlet {

    public final static String BROADER = "http://www.w3.org/2004/02/skos/core%23broader";
    public final static String RELATED = "http://www.w3.org/2004/02/skos/core%23related";
    public final static String NARROVER = "http://www.w3.org/2004/02/skos/core%23narrower";

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        response.setContentType("text/html;charset=UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        ArrayList<String> replaced = new ArrayList<String>();

        String innerHtml = request.getParameter("html");
        String termList = request.getParameter("term");

        System.out.println("EnrichServlet execute.");
        // System.out.println(innerHtml);
        // System.out.println(termList);

        PrintWriter out = response.getWriter();

        // get keywords 
        MultilingualSearchManager msm = new MultilingualSearchManager();

        ArrayList<MohseContext> documentsEnLabel = null;
        ArrayList<MohseContext> documentsTrLabel = null;

        documentsEnLabel = msm.getAllLabels("http://localhost:8080/ks/KnowledgeService", "http://localhost:8080/rdf/" + termList + ".rdf", "en");
        documentsTrLabel = msm.getAllLabels("http://localhost:8080/ks/KnowledgeService", "http://localhost:8080/rdf/" + termList + ".rdf", "tr");

        // convert xhtml by jtidy
        InputStream is = new URL(innerHtml).openStream();

        // FileInputStream fis = new FileInputStream(innerHtml);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        Tidy tidy = new Tidy();
        Document document = tidy.parseDOM(is, os);


        // take p tag with jsoup
        org.jsoup.nodes.Document doc = Jsoup.parse(os.toString());

        Elements p_elements = doc.getElementsByTag("p");



        for (Element element : p_elements) {

            HashMap<String, String> replaceMap = new HashMap<String, String>();

            for (MohseContext context : documentsEnLabel) {



                if (element.text().contains(context.getTerm()) && !isReplaced(replaced, context.getTerm())) {
                    ArrayList<SolrDocument> exactMaches;
                    ArrayList<SolrDocument> broaderMaches;
                    ArrayList<SolrDocument> relatedMaches;
                    ArrayList<SolrDocument> narrowerMaches;


                    exactMaches = msm.getQueryResults("http://localhost:8080/ks/KnowledgeService", "http://localhost:8080/rdf/" + termList + ".rdf", context.getTerm());



                    if (!exactMaches.isEmpty()) {
                        // String add = createTooltip(context.getTerm(), exactMaches);
                        broaderMaches = msm.getRelations("http://localhost:8080/ks/KnowledgeService", "http://localhost:8080/rdf/" + termList + ".rdf", context.getConcept(), BROADER, "tr");
                        relatedMaches = msm.getRelations("http://localhost:8080/ks/KnowledgeService", "http://localhost:8080/rdf/" + termList + ".rdf", context.getConcept(), RELATED, "tr");
                        narrowerMaches = msm.getRelations("http://localhost:8080/ks/KnowledgeService", "http://localhost:8080/rdf/" + termList + ".rdf", context.getConcept(), NARROVER, "tr");


                        String add = createTooltipWithRelations(context.getTerm(), exactMaches, relatedMaches, broaderMaches, narrowerMaches);
                        replaceMap.put(context.getTerm(), add);



                        // System.out.println("terms " + context.getTerm());

                        replaced.add(context.getTerm());
                    }

                }
            }

            for (String key : replaceMap.keySet()) {
                // innerHtml = innerHtml.replaceFirst(key, replaceMap.get(key));

                System.out.println(key);
                System.out.println(replaceMap.get(key));

                element.text(element.text().replaceFirst(key, replaceMap.get(key)));
            }

        }




//        // if innerHtml include enrich it
//        for (MohseContext context : documentsEnLabel) {
//            if (innerHtml.contains(context.getTerm()) && !isReplaced(replaced, context.getTerm())) {
//                // String add = createAdded(context.getTerm());
//                // String add = newCreateAdded(context.getTerm(), termList);
//                // innerHtml = innerHtml.replaceAll(context.getTerm(), add);
//                // replaced.add(context.getTerm());
//
//                // look for 
//
//                ArrayList<SolrDocument> exactMaches;
//                ArrayList<SolrDocument> broaderMaches;
//                ArrayList<SolrDocument> relatedMaches;
//                ArrayList<SolrDocument> narrowerMaches;
//
//                exactMaches = msm.getQueryResults("http://localhost:8080/ks/KnowledgeService", "http://localhost:8080/rdf/" + termList + ".rdf", context.getTerm());
//
//                
//
//                if (!exactMaches.isEmpty()) {
//                    // String add = createTooltip(context.getTerm(), exactMaches);
//                    broaderMaches = msm.getRelations("http://localhost:8080/ks/KnowledgeService", "http://localhost:8080/rdf/" + termList + ".rdf", context.getConcept(), BROADER, "tr");
//                    relatedMaches = msm.getRelations("http://localhost:8080/ks/KnowledgeService", "http://localhost:8080/rdf/" + termList + ".rdf", context.getConcept(), RELATED, "tr");
//                    narrowerMaches = msm.getRelations("http://localhost:8080/ks/KnowledgeService", "http://localhost:8080/rdf/" + termList + ".rdf", context.getConcept(), NARROVER, "tr");
//                    
//                    
//                    String add = createTooltipWithRelations(context.getTerm(), exactMaches, relatedMaches, broaderMaches, narrowerMaches);
//                    replaceMap.put(context.getTerm(), add);
//                    // innerHtml = innerHtml.replaceAll(context.getTerm(), add);
//                    replaced.add(context.getTerm());
//                }
//
//            }
//        }
//        
//        for (String key : replaceMap.keySet()) {
//            innerHtml = innerHtml.replaceFirst(key, replaceMap.get(key));
//        }

        try {
            out.println(doc.toString());
        } finally {
            out.close();
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(EnrichServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(EnrichServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private String createAdded(String term) throws Exception {
        return "<div class=\"editor-field\">"
                + "<span class=\"help\">" + term + "</span>"
                + "<div style=\"display: none\" class=\"tooltip\">"
                + "	<div class=\"close\"><a href=\"#\">close</a></div><br class=\"clear\">"
                + "	<div>" + createReplaceStr(term) + "</div>"
                + "</div> "
                + "</div>";
    }

    private String createAdded(String term, String termList) throws Exception {
        return "<div class=\"editor-field\">"
                + "<span class=\"help\">" + term + "</span>"
                + "<div style=\"display: none\" class=\"tooltip\">"
                + "	<div class=\"close\"><a href=\"#\">close</a></div><br class=\"clear\">"
                + "	<div>" + createReplaceStr(term, termList) + "</div>"
                + "</div> "
                + "</div>";
    }

    private String newCreateAdded(String term) throws Exception {
        return "<div class=\"help\" onclick=\"showTooltip(" + term + ", 'Simple Tooltip', ' " + createReplaceStr(term) + " ')\"> " + term + " </div>"
                + "<div style=\"display:none\" id=\"" + term + "\"></div> ";
    }

    private String newCreateAdded(String term, String termList) throws Exception {
        return "<div class=\"help\" onclick=\"showTooltip(" + term + ", 'Simple Tooltip', ' " + createReplaceStr(term, termList) + " ')\"> " + term + " </div>"
                + "<div style=\"display:none\" id=\"" + term + "\"></div> ";
    }

    private String createTooltip(String term, ArrayList<SolrDocument> documents) throws Exception {
        return "<div class=\"help\" onclick=\"showTooltip(" + term + ", 'Simple Tooltip', ' " + createReplaceStr(documents) + " ')\"> " + term + " </div>"
                + "<div style=\"display:none\" id=\"" + term + "\"></div> ";
    }

    private String createTooltipWithRelations(String term, ArrayList<SolrDocument> exact, ArrayList<SolrDocument> related, ArrayList<SolrDocument> broader, ArrayList<SolrDocument> narrover) throws Exception {
        return "<span class=\"help\" onclick=\"showTooltip(" + term + ", 'Simple Tooltip', ' " + createReplaceStrWithRelations(exact, related, broader, narrover) + " ')\"> " + term + " </span>"
                + "<span style=\"display:none\" id=\"" + term + "\"></span> ";
    }

    private String createReplaceStr(String term) throws Exception {
        MultilingualSearchManager msm = new MultilingualSearchManager();

        ArrayList<SolrDocument> documents = null;

        documents = msm.getQueryResults("http://localhost:8080/ks/KnowledgeService", "http://localhost:8080/rdf/fishery.rdf", term);

        StringBuilder buffer = new StringBuilder();

        for (SolrDocument doc : documents) {
            buffer.append("<p> <a href=");
            buffer.append(doc.getHost());
            buffer.append(">");
            buffer.append(doc.getTitle());
            buffer.append("</a> </p>");
        }

        return buffer.toString();
    }

    private String createReplaceStr(String term, String termList) throws Exception {
        MultilingualSearchManager msm = new MultilingualSearchManager();

        ArrayList<SolrDocument> documents = null;

        documents = msm.getQueryResults("http://localhost:8080/ks/KnowledgeService", "http://localhost:8080/rdf/" + termList + ".rdf", term);

        StringBuilder buffer = new StringBuilder();

        for (SolrDocument doc : documents) {
            buffer.append("<p> <a href=");
            buffer.append(doc.getHost());
            buffer.append(">");
            buffer.append(doc.getTitle());
            buffer.append("</a> </p>");
        }

        return buffer.toString();
    }

    private String createReplaceStr(ArrayList<SolrDocument> documents) throws Exception {

        StringBuilder buffer = new StringBuilder();

        for (SolrDocument doc : documents) {
            buffer.append("<p> <a href=");
            buffer.append(doc.getHost());
            buffer.append(">");
            buffer.append(doc.getTitle());
            buffer.append("</a> </p>");
        }

        return buffer.toString();
    }

    private String createReplaceStrWithRelations(ArrayList<SolrDocument> exact, ArrayList<SolrDocument> related, ArrayList<SolrDocument> broader, ArrayList<SolrDocument> narrover) throws Exception {

        StringBuilder buffer = new StringBuilder();

        buffer.append(convertSolrDoctoHtml(exact, "Exact"));
        buffer.append(convertSolrDoctoHtml(related, "Related"));
        buffer.append(convertSolrDoctoHtml(broader, "Broader"));
        buffer.append(convertSolrDoctoHtml(narrover, "Narrover"));

        return buffer.toString();
    }

    private String convertSolrDoctoHtml(ArrayList<SolrDocument> documents, String relation) {

        StringBuilder buffer = new StringBuilder();

        if (!documents.isEmpty()) {
            buffer.append("<h4> " + relation + " Matches </h4>");

            for (SolrDocument doc : documents) {
                buffer.append("<p> <a href=");
                buffer.append(doc.getHost());
                buffer.append(">");
                buffer.append(doc.getTitle());
                buffer.append("</a> </p>");
            }
        }

        return buffer.toString();

    }

    private boolean isReplaced(ArrayList<String> replaced, String term) {

        for (String str : replaced) {
            if (str.contains(term) || term.contains(str)) {
                return true;
            }
        }

        return false;
    }
}
