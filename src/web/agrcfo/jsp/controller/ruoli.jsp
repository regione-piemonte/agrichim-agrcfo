<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.agrc.*, it.csi.cuneo.*" isThreadSafe="true" %>
<%@ page import="it.csi.iride2.policy.entity.Identita" %>
<%@ page import="it.csi.csi.porte.InfoPortaDelegata" %>
<%@ page import="it.csi.csi.util.xml.PDConfigReader" %>
<%@ page import="it.csi.csi.porte.proxy.PDProxy" %>
<%@ page import="it.csi.iride2.policy.interfaces.PolicyEnforcerBaseService" %>
<%@ page import="it.csi.iride2.policy.entity.Application"%>
<%@ page import="it.csi.papua.papuaserv.dto.gestioneutenti.Ruolo"%>
<%@ page import="it.csi.papua.papuaserv.presentation.rest.profilazione.client.PapuaservRESTClient" %>
<%@ page import="it.csi.papua.papuaserv.presentation.rest.profilazione.client.PapuaservProfilazioneServiceFactory" %>
<%@ page import="java.util.StringTokenizer" %>


<%@ include file="/jsp/dataSource.inc" %>

<jsp:useBean
  id="beanParametriApplication"
  scope="application"
  class="it.csi.agrc.BeanParametri">
</jsp:useBean>


<%
CuneoLogger.debug(this,"####################### Sono in /jsp/controller/ruoli");	 
  // -------------------> CZ - 22/01/2011 Recupero identita SHIBBOLETH
  Identita identita = (Identita) session.getAttribute(Constants.SESSION.IDENTITA);
  // -----------------------------------------------------------------
  
  //InfoPortaDelegata infoPDPEP = PDConfigReader.read(session.getServletContext().getResourceAsStream("/WEB-INF/defPDPEPEJB.xml"));
  //PolicyEnforcerBaseService iridePEPClient = (PolicyEnforcerBaseService) PDProxy.newInstance(infoPDPEP);
  PapuaservRESTClient papuaClient =  bindingPapua();
  CuneoLogger.debug(this,"papuaClientFO = "+papuaClient!=null?"ok":"null!");
  CuneoLogger.debug(this,"identita.getCodFiscale() = "+identita.getCodFiscale());
  CuneoLogger.debug(this,"identita.getLivelloAutenticazione() = "+identita.getLivelloAutenticazione());
  
  Ruolo[] ruoli = papuaClient.findRuoliForPersonaInApplicazione(identita.getCodFiscale(), identita.getLivelloAutenticazione(), Constants.ID_PROCEDIMENTO_AGCRFO);
  
  CuneoLogger.debug(this,"ruoli.length = "+ruoli.length);
  String ruolo = null;
  int nRuoliCheck = 0;
  
// -------------------> CZ - 24/01/2011 - Elenca i Ruoli 
	CuneoLogger.debug(this,"Sono stati trovati " + ruoli.length + " ruoli");
	boolean checkRuoloMonitoraggio = false;	
	for (; nRuoliCheck<ruoli.length; nRuoliCheck++) {
// 		ruolo = ruoli[nRuoliCheck].getMnemonico();
		ruolo = ruoli[nRuoliCheck].getCodice();
		CuneoLogger.debug(this, nRuoliCheck + ") " + ruolo);
		CuneoLogger.debug(this,"ruolo -> " +ruolo);
		if ("MONITORAGGIO@AGRICOLTURA".equals(ruolo)) {
			session.setAttribute("checkRuoloTitolare_CF", ruolo);
			checkRuoloMonitoraggio = true;
		}
		if ("TITOLARE_CF@UTENTI_IRIDE2".equals(ruolo)) {
			session.setAttribute("checkRuoloTitolare_CF", ruolo);
		}		
	}
	session.setAttribute("ruolo", ruolo);
	session.setAttribute("ruoli",ruoli);
	CuneoLogger.debug(this, "Salto alla pagina di scelta dei ruoli ...");
	if (checkRuoloMonitoraggio) {
		Utili.forward(request, response, "/jsp/view/ruoli.jsp");
	} else {
		Utili.forward(request, response, "/jsp/controller/login.jsp");	
	}
// ------------------------------------------------------

%>
<%!

private PapuaservRESTClient bindingPapua() throws Exception {
    PapuaservRESTClient client = null;
    CuneoLogger.debug(this, "agrichimFO ruoli.jsp -- bindingPapua BEGIN");
    try {
        client = PapuaservProfilazioneServiceFactory.getRestServiceClient();
    } catch (Exception e) {
        CuneoLogger.error(this, "Exception during the invocation of bindingPapua method in AgrichimFO ruoli.jsp");
        throw e;
    }
    CuneoLogger.debug(this, "agrichimFO ruoli.jsp -- bindingPapua END");

    return client;

}
%>
