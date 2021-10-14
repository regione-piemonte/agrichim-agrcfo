<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.agrc.*, it.csi.cuneo.*" isThreadSafe="true" %>
<%@ page import="it.csi.iride2.policy.entity.Identita" %>
<%@ page import="it.csi.csi.porte.InfoPortaDelegata" %>
<%@ page import="it.csi.csi.util.xml.PDConfigReader" %>
<%@ page import="it.csi.csi.porte.proxy.PDProxy" %>
<%@ page import="it.csi.iride2.policy.interfaces.PolicyEnforcerBaseService" %>
<%@ page import="it.csi.iride2.policy.entity.Application"%>
<%@ page import="it.csi.papua.papuaserv.dto.gestioneutenti.Ruolo"%>
<%@ page import="java.util.StringTokenizer" %>
<%// Elimina la sessione eventualmente già esistente
  session.removeAttribute("aut");
%>
<%@ include file="/jsp/dataSource.inc" %>
<jsp:useBean
  id="beanParametriApplication"
  scope="application"
  class="it.csi.agrc.BeanParametri">
</jsp:useBean>


<%
  CuneoLogger.debug(this,"Sono in /jsp/controller/login");	
  it.csi.agrc.Autenticazione aut=new it.csi.agrc.Autenticazione();
  it.csi.agrc.AutenticazioneQry autQry=new it.csi.agrc.AutenticazioneQry();
  autQry.setDataSource(dataSource);
  
  Identita identita = (Identita) session.getAttribute(Constants.SESSION.IDENTITA);
  CuneoLogger.debug(this,"identita -> "+identita.toString());
  String ruolo = (String) session.getAttribute("ruolo");
  CuneoLogger.debug(this,"ruolo -> "+ruolo);
  int nRuoli = 0;                   

  if ("MONITORAGGIO@AGRICOLTURA".equals(ruolo)) {
  	CuneoLogger.debug(this,"Accesso Front Office come utente MONITORAGGIO");
  	Utili.forward(request, response, "/jsp/view/monitoraggio.jsp");
  } else {
  	if (session.getAttribute("checkRuoloTitolare_CF") == null) {
  		CuneoLogger.debug(this,"Accesso Front Office NON CONVALIDATO");
  		response.sendRedirect(beanParametriApplication.getUrlStartApplicationSP());
  		return;		
  	}
  }

  /*
  if (!"TITOLARE_CF@UTENTI_IRIDE2".equals(ruolo)) {
    CuneoLogger.debug(this,"Accesso Front Office NON CONVALIDATO");
    response.sendRedirect(beanParametriApplication.getUrlStartApplicationSP());
    return;
  }
  */
  
  Anagrafiche anag = new Anagrafiche();
  anag.setDataSource(dataSource);
  Anagrafica a = null, az = null;
  //aut.setDataSource(dataSource);

  String codiceFiscale = identita.getCodFiscale();
  String cognome = identita.getCognome();
  String nome = identita.getNome();
  
  a = anag.getAnagraficaDaCF(codiceFiscale);
  if (a==null) {
	  CuneoLogger.debug(this,"Nuovo utente -> da inserire");
    // L'utente entrato non esiste in anagrafica.
    // Bisogna recuperare i dati dell'utente da IRIDE e quindi
    // aggiornare la nostra anagrafica.
    try {
      a = new Anagrafica ( null, codiceFiscale,
                          "F",cognome,
                          nome,
                          null, // strada + numero civico
                          null, // cap
                          null, // comune residenza
                          null, // telefono
                          null, // cellulare
                          null, // fax
                          null, // e-mail
                          null, // idOrganizzazione
                          "P", // = persona fisica
                          null, // idAnagraficaAzienda
                          null ); // idAnagrafica2
      a.setDataSource(dataSource);
      a.insert();
      session.setAttribute("primoAccesso","true");
    } catch(Exception ex) {
      ex.printStackTrace();
      response.sendRedirect(beanParametriApplication.getUrlStartApplicationSP());
      return;
    }
  }

  String idAnagraficaAzienda = a.getIdAnagraficaAzienda();
  
  if (idAnagraficaAzienda != null){
	  CuneoLogger.debug(this,"idAnagraficaAzienda -> "+idAnagraficaAzienda);
	  az = anag.getAnagrafica(idAnagraficaAzienda);
  }else
	  CuneoLogger.debug(this,"idAnagraficaAzienda -> null");

  // Adesso l'if lo facciamo sull'esistenza di un'azienda...
  if (az == null)
    aut.setUtente(autQry.caricaDati(null,codiceFiscale,null));
  else
    aut.setUtente(autQry.caricaDati(null,codiceFiscale,az.getCodiceIdentificativo()));
  String spid = (String) session.getAttribute("spid");
  CuneoLogger.debug(this,"spid -> "+spid);
  boolean spid1 = (spid!=null&&spid.equals("true"))?true:false;
  CuneoLogger.debug(this,"spid1 -> "+spid1);
  aut.setSpid(spid1);
  session.setAttribute("aut",aut);

  CuneoLogger.debug(this,"Accesso Front Office convalidato - "+codiceFiscale);

  Utili.forward(request, response, "/jsp/view/principale.jsp");
%>


