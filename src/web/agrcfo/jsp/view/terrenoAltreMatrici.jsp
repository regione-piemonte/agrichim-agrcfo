<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.agrc.*,it.csi.jsf.htmpl.*,it.csi.cuneo.*" isThreadSafe="true" %>
<%
  Htmpl templ =
  HtmplFactory.getInstance(application).getHtmpl("/jsp/layout/terrenoAltreMatrici.htm");
%>
<%@ include file="/jsp/controllaLogin.inc" %>
<%@ include file="/jsp/dataSource.inc" %>
<jsp:useBean
     id="analisi"
     scope="page"
     class="it.csi.agrc.Analisi">
    <%
      analisi.setDataSource(dataSource);
      analisi.setAut(aut);
      analisi.setIdRichiesta(aut.getIdRichiestaCorrente());
    %>
</jsp:useBean>

<%
session.setAttribute("aut",aut);
String cf = (String) request.getParameter("cf");
String nPreventivo = (String) request.getParameter("nP");
String lettura = (String) request.getParameter("let");

if(cf==null && nPreventivo==null){
	analisi.selectPreventivo(""+aut.getIdRichiestaCorrente());
	templ.bset("cf",analisi.getCodice_fiscale()!=null?analisi.getCodice_fiscale():"");
    templ.bset("nPreventivo",analisi.getN_preventivo()!=null?analisi.getN_preventivo():"");
    templ.bset("costoComplessivo",analisi.getCosto_totale()!=null?analisi.getCosto_totale():"");
    templ.bset("noteLab",analisi.getNote_laboratorio()!=null?analisi.getNote_laboratorio():"");
    templ.bset("note",analisi.getNote()!=null?analisi.getNote():"");
    if(lettura==null || !lettura.equals("true"))
    	templ.bset("readonly","readonly");
}else if(cf!=null && nPreventivo!=null){
	//query
	analisi.selectPreventivo(cf,nPreventivo);
	templ.bset("cf",cf);
	templ.bset("nPreventivo",nPreventivo);
	templ.bset("costoComplessivo",analisi.getCosto_totale()!=null?analisi.getCosto_totale():"");
	templ.bset("noteLab",analisi.getNote_laboratorio()!=null?analisi.getNote_laboratorio():"");
	templ.bset("note",analisi.getNote()!=null?analisi.getNote():"");
}
if(lettura!=null && lettura.equals("true") && (analisi.getCosto_totale()==null || (analisi.getCosto_totale()!=null && analisi.getCosto_totale().equals(""))))
	 templ.newBlock("ALERT");
if(analisi.getCosto_totale()==null || (analisi.getCosto_totale()!=null && analisi.getCosto_totale().equals("")))
    templ.newBlock("KO");
else
    templ.newBlock("OK");
%>
<%= templ.text() %>
