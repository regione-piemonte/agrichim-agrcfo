<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.agrc.*,it.csi.jsf.htmpl.*,it.csi.cuneo.*,java.text.*" isThreadSafe="true" %>

<%
  Htmpl templ =
  HtmplFactory.getInstance(application).getHtmpl("/jsp/layout/ritornoPagamento.htm");
%>
<%@ include file="/jsp/controllaLogin.inc" %>
<%@ include file="/jsp/dataSource.inc" %>
<jsp:useBean id="beanAnalisi" scope="application" class="it.csi.agrc.BeanAnalisi"/>

<jsp:useBean
     id="pagamenti"
     scope="page"
     class="it.csi.agrc.Pagamento">
<%
    pagamenti.setDataSource(dataSource);
    pagamenti.setAut(aut);
%>
</jsp:useBean>
<%
templ.bset("line_iuv",(String)session.getAttribute("iuv"));
session.removeAttribute("iuv");
String importo = Utili.valuta((String)session.getAttribute("pagamento_importo"));
templ.bset("line_imp", importo);
session.removeAttribute("pagamento_importo");
if(((String)session.getAttribute("pagamento_tipoPagamento")).equals("M1")){
	templ.newBlock("RitornoOKM1");
}else if(((String)session.getAttribute("pagamento_tipoPagamento")).equals("M3")){
	templ.newBlock("RitornoOKM3");
}else{
	templ.newBlock("RitornoKO");
}
session.removeAttribute("pagamento_tipoPagamento");
%>

<%= templ.text() %>
