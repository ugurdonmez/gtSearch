<%-- 
    Document   : results
    Created on : May 19, 2013, 3:01:32 PM
    Author     : ugur
--%>

<%@page import="tr.edu.metu.ncc.cng.gtsearch.xmlparser.solr.SolrDocument"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>GT Search</title>
    </head>
    
    <%
        ArrayList<SolrDocument> arr = (ArrayList) session.getAttribute("documents");
        String url = "";
        String title = "";
        if (arr.isEmpty() == false) {
            for (int i = 0; i < arr.size(); i++) {
                title = arr.get(i).getTitle();
                url =  arr.get(i).getUrl();
                %>
                <tr>
                    <td>
                        <a href=<%=url%>><%=title%></a> 

                    </td>

                </tr>
                <%      
            }
        } else {
            System.out.println("Array is empty");
            %>
            <tr>
                <td>
                    EMPTY
                </td>

            </tr>
            <%
        }
    %>
    
</html>
