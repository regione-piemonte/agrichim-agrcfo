<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.agrc.*, it.csi.jsf.htmpl.*, it.csi.cuneo.*, java.net.*, it.csi.cuneo.servlet.*" isThreadSafe="true" %>
<%@ page import="it.csi.csi.porte.InfoPortaDelegata" %>
<%@ page import="it.csi.csi.util.xml.PDConfigReader" %>
<%@ page import="it.csi.csi.porte.proxy.PDProxy" %>
<%@ page import="it.csi.iride2.policy.entity.Identita" %>
<%@ page import="it.csi.papua.papuaserv.presentation.rest.profilazione.client.PapuaservRESTClient" %>
<%@ page import="it.csi.papua.papuaserv.presentation.rest.profilazione.client.PapuaservProfilazioneServiceFactory" %>
<%@ page import="it.csi.papua.papuaserv.dto.gestioneutenti.Ruolo" %>

<%
    // Elimina la sessione eventualmente già esistente
    session.removeAttribute("aut");
%>

<%@ include file="/jsp/dataSource.inc" %>

<jsp:useBean
  id="beanParametriApplication"
  scope="application"
  class="it.csi.agrc.BeanParametri">
</jsp:useBean>


<%
  CuneoLogger.debug(this,"Sono in /jsp/controller/loginPA");	
  it.csi.agrc.Autenticazione aut=new it.csi.agrc.Autenticazione();
  it.csi.agrc.AutenticazioneQry autQry=new it.csi.agrc.AutenticazioneQry();
  autQry.setDataSource(dataSource);

  Identita identita = (Identita) session.getAttribute(Constants.SESSION.IDENTITA);

  PapuaservRESTClient papuaClient =  bindingPapua();
  //PolicyEnforcerHelperService irideUtilityClient = (PolicyEnforcerHelperService) PDProxy.newInstance(infoPDUtility);
  CuneoLogger.debug(this,"papuaClientFO = "+papuaClient!=null?"ok":"null!");
  CuneoLogger.debug(this,"identita.getCodFiscale() = "+identita.getCodFiscale());
  CuneoLogger.debug(this,"identita.getLivelloAutenticazione() = "+identita.getLivelloAutenticazione());
  
  String ruolo = null;
  //Ruolo[] ruoli = papuaClient.findRuoliForPersonaInApplication(identita,new Application("AGRCFO"));
  Ruolo[] ruoli = papuaClient.findRuoliForPersonaInApplicazione(identita.getCodFiscale(), identita.getLivelloAutenticazione(), Constants.ID_PROCEDIMENTO_AGCRFO);
  CuneoLogger.debug(this,"ruoli.length = "+ruoli.length);
  int nRuoli = 0;
  for (; nRuoli<ruoli.length; nRuoli++)
  {
    //ruolo = ruoli[nRuoli].getMnemonico();
    ruolo = ruoli[nRuoli].getCodice();//?
  	CuneoLogger.debug(this, "ruolo ["+nRuoli+"]: "+ruolo);
  	/* Accesso permesso solo se l'utente ha tra i ruoli il ruolo TITOLARE_CF@UTENTI_IRIDE2 */ 
    if ("TITOLARE_CF@UTENTI_IRIDE2".equals(ruolo))
    	break;
  }

  if (ruoli.length == 0 || nRuoli >= ruoli.length)
  {
    CuneoLogger.debug(this,"Accesso Front Office NON CONVALIDATO");
    response.sendRedirect(beanParametriApplication.getUrlStartApplicationPA());
    return;
  }

  Anagrafiche anag = new Anagrafiche();
  anag.setDataSource(dataSource);
  Anagrafica a = null;
  //aut.setDataSource(dataSource);

  String codiceFiscale = identita.getCodFiscale();
  a = anag.getAnagraficaDaCF(codiceFiscale);
  if (a==null)
  {
    // L'utente entrato non esiste in anagrafica.
    // Quindi non entra. Punto.
    response.sendRedirect(beanParametriApplication.getUrlStartApplicationPA());
    return;
  }
  // Altrimenti esiste e quindi si continua
  //aut.caricaDati(UID,FUID,null);
  aut.setUtente(autQry.caricaDati(null,codiceFiscale,null));

  // Se si effettua il login da qui, allora siamo in RuparPiemonte!
  aut.setPA(true);

  session.setAttribute("aut",aut);

  CuneoLogger.debug(this,"Accesso Front Office convalidato - "+codiceFiscale);

  Utili.forward(request, response, "/jsp/view/principale.jsp");
%>

<%!

private PapuaservRESTClient bindingPapua() throws Exception {
    PapuaservRESTClient client = null;
    CuneoLogger.debug(this, "agrichimFO loginPA.jsp -- bindingPapua BEGIN");
    try {
        client = PapuaservProfilazioneServiceFactory.getRestServiceClient();
    } catch (Exception e) {
    	CuneoLogger.error(this, "Exception during the invocation of bindingPapua method in AgrichimFO loginPA.jsp");
        throw e;
    }
    CuneoLogger.debug(this, "agrichimFO loginPA.jsp -- bindingPapua END");

    return client;

}
%>


