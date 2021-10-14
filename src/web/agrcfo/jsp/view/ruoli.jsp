<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.agrc.*, it.csi.cuneo.*, it.csi.jsf.htmpl.*" isThreadSafe="true" %>
<%@ page import="it.csi.iride2.policy.entity.Identita" %>
<%@ page import="it.csi.csi.porte.InfoPortaDelegata" %>
<%@ page import="it.csi.csi.util.xml.PDConfigReader" %>
<%@ page import="it.csi.csi.porte.proxy.PDProxy" %>
<%@ page import="it.csi.iride2.policy.interfaces.PolicyEnforcerBaseService" %>
<%@ page import="it.csi.iride2.policy.entity.Application"%>
<%@ page import="it.csi.papua.papuaserv.dto.gestioneutenti.Ruolo"%>
<%@ page import="java.util.StringTokenizer" %>

<jsp:useBean
  id="beanParametriApplication"
  scope="application"
  class="it.csi.agrc.BeanParametri">
</jsp:useBean>

<%
	Htmpl templ = HtmplFactory.getInstance(application).getHtmpl("/jsp/layout/ruoli.htm");
	templ.bset("urlHomePageApplication", request.getContextPath()+beanParametriApplication.getUrlHomePageApplication());
	templ.bset("serverPath","https://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath());

	Ruolo[] ruoli = (Ruolo[]) session.getAttribute("ruoli");
	String ruolo = "";
	int nRuoliCheck = 0;	
	for (; nRuoliCheck<ruoli.length; nRuoliCheck++) {
// 		ruolo = ruoli[nRuoliCheck].getMnemonico();
		ruolo = ruoli[nRuoliCheck].getCodice();
		CuneoLogger.debug(this, nRuoliCheck + ") " + ruolo);
		templ.set("ruoli.ruolo", ruolo);
		templ.set("ruoli.descrizioneRuolo", ruoli[nRuoliCheck].getDescrizione());		
	}
%>

<%= templ.text() %>
