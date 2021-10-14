<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.agrc.*,it.csi.jsf.htmpl.*,it.csi.cuneo.*" isThreadSafe="true" %>
<%@page import="java.util.Vector"%>
<%@page import="it.csi.solmr.dto.anag.AnagAziendaVO"%>

<%
  Htmpl templ =
  HtmplFactory.getInstance(application).getHtmpl("/jsp/layout/anagraficaTecnico.htm");
%>

<%@ include file="/jsp/controllaLogin.inc" %>
<%@ include file="/jsp/dataSource.inc" %>

<jsp:useBean
     id="anagrafiche"
     scope="page"
     class="it.csi.agrc.Anagrafiche">
<%
  anagrafiche.setDataSource(dataSource);
  anagrafiche.setAut(aut);
%>
</jsp:useBean>

<jsp:useBean
     id="comuni"
     scope="page"
     class="it.csi.agrc.Comuni">
<%
  comuni.setDataSource(dataSource);
  comuni.setAut(aut);
%>
</jsp:useBean>

<jsp:useBean
     id="anagrafica"
     scope="request"
     class="it.csi.agrc.Anagrafica">
    <%
      anagrafica.setDataSource(dataSource);
      anagrafica.setAut(aut);
    %>
</jsp:useBean>

<!--
  Il file che includo serve a gestire gli errori dovuti al funzionamento non
  corretto del javascript
-->
<%@ include file="/jsp/view/problemiJavascript.inc" %>

<%
  /**
  * Se sono in questa pagina perché c'è stato un errore non devo leggere i
  * dati che si trovano memorizzati dentro al database ma devo far vedere
  * quelli che l'utente ha inserito, che si trovano nella request
  */
  boolean erroreJsp=false;
  if (request.getParameter("errore") != null)
  {
    erroreJsp=true;
  }

  /**
   * Se l'utente vuole eseguire la ricerca il parametro anagraficaRicerca
   * viene valorizzato con la partita iva o il codice fiscale desiderato.
   **/
  String anagraficaRicerca=request.getParameter("codiceIdentificativo");
  String anagraficaDup=request.getParameter("anagraficaDup");
  String idAnagrafica=null,
         tipoPersona=null,
         cognome=null,
         nome=null,
         indirizzo=null,
         cap=null,
         comune=null,
         comuneResidenza=null,
         telefono=null,
         cellulare=null,
         fax=null,
         email=null,
         codiceFiscale=null,
         codiceFiscaleOld=null;

  boolean ricerca=false,campiBloccati=false;
  if (anagraficaDup==null)
  {
    if (anagraficaRicerca==null)
    {
      ricerca=false;
      idAnagrafica=anagrafiche.getProprietario();
      if (idAnagrafica!=null)
      	campiBloccati=true;
    }
    else
    {
      ricerca=true;
      //Dalla partita iva (codice fiscale) devo ottenere l'dentificativo
      idAnagrafica=anagrafiche.getIdFromCodFiscPIVA(anagraficaRicerca);
      if (idAnagrafica!=null)
      	campiBloccati=true;
    }
  }
  
  if ("readonly".equals(request.getParameter("disabledUpdate")))
  	campiBloccati=true;
  
  if (campiBloccati==true)
  {
	//disabilito i campi di input
	templ.bset("disabledUpdate","readonly");
	templ.bset("comuneButtonStyle","style='visibility: hidden'");
  }
  else templ.bset("modificaAnagraficaStyle","style='visibility: hidden'");
  
  
  if (aut.getFase()>2 || ricerca || (anagraficaDup!=null) || erroreJsp)
  {
    if (!erroreJsp) anagrafica = anagrafiche.getAnagrafica(idAnagrafica);
    if (anagraficaDup!=null)
    {
      templ.newBlock("onload");
        templ.set("onload.messaggio","Anagrafica già esistente: premere sul pulsante ricerca");
    }
    if (anagrafica!=null)
    {
      Utente utente = aut.getUtente();
      tipoPersona=anagrafica.getTipoPersona();
      cognome=anagrafica.getCognomeRagioneSociale();
      nome=anagrafica.getNome();
      indirizzo=anagrafica.getIndirizzo();
      cap=anagrafica.getCap();
      comuneResidenza=anagrafica.getComuneResidenza();
      if (comuneResidenza!=null)
        comune=comuni.getDescrizioneComune(comuneResidenza);
      telefono=anagrafica.getTelefono();
      cellulare=anagrafica.getCellulare();
      fax=anagrafica.getFax();
      email=anagrafica.getEmail();
      codiceFiscale=anagrafica.getCodiceIdentificativo();
      codiceFiscaleOld=anagrafica.getCodiceIdentificativo();
    }
    else
    {
      if (ricerca || anagraficaDup!=null)
      {
        templ.newBlock("onload");
        templ.set("onload.messaggio","La ricerca non ha prodotto risultati");
        codiceFiscale=anagraficaRicerca;
        codiceFiscaleOld="";
        tipoPersona=request.getParameter("tipoPersona");
        cognome=request.getParameter("cognomeRagioneSociale");
        nome=request.getParameter("nome");
        indirizzo=request.getParameter("indirizzo");
        cap=request.getParameter("cap");
        comune=request.getParameter("comune");
        comuneResidenza=request.getParameter("comuneResidenza");
        telefono=request.getParameter("telefono");
        cellulare=request.getParameter("cellulare");
        fax=request.getParameter("fax");
        email=request.getParameter("email");
      }
    }
  }

  if (aut.isCoordinateGeografiche() && aut.isPiemonte())
      templ.bset("pageBack","coordinateGIS.jsp");
  else
      templ.bset("pageBack","tipoCampione.jsp");

  /**
   * Controllo che i campi non siano null
   * */
  if (tipoPersona==null) tipoPersona="G";
  if (cognome==null) cognome="";
  if (nome==null) nome="";
  if (indirizzo==null) indirizzo="";
  if (cap==null) cap="";
  if (comune==null) comune="";
  if (comuneResidenza==null) comuneResidenza="";
  if (telefono==null) telefono="";
  if (cellulare==null) cellulare="";
  if (email==null) email="";
  if (fax==null) fax="";
  if (codiceFiscale==null) codiceFiscale="";
  if (codiceFiscaleOld==null) codiceFiscaleOld="";


  if ("G".equals(tipoPersona))
   templ.bset("checkedGiuridica","checked");
  else
    templ.bset("checkedFisica","checked");
  templ.bset("cognome",cognome);
  templ.bset("nome",nome);
  templ.bset("indirizzo",indirizzo);
  templ.bset("cap",cap);
  templ.bset("istat",comuneResidenza);
  templ.bset("comune",comune);
  templ.bset("eMail",email);
  templ.bset("telefono",telefono);
  templ.bset("cellulare",cellulare);
  templ.bset("fax",fax);
  templ.bset("codiceFiscale",codiceFiscale);
  templ.bset("codiceFiscaleOld",codiceFiscaleOld);

	//AGRICHIM-7
  //Visualizzazione se proprietario del campione è un'azienda agricola
  boolean isPresenteAnagrafe = false;
  if (codiceFiscale != null && ! "".equals(codiceFiscale))
  {
  	AnagAziendaVO anagAziendaVO = new AnagAziendaVO();
  	anagAziendaVO.setCUAA(codiceFiscale);
  	Vector elencoAziende = beanParametriApplication.getAnagServiceCSIInterface().serviceGetListIdAziende(anagAziendaVO, Boolean.TRUE, Boolean.FALSE);
  	isPresenteAnagrafe = elencoAziende != null && elencoAziende.size() > 0;
  //jira 136
  	if(!isPresenteAnagrafe)
  	{
  		CuneoLogger.debug(this,"non l'ho trovato per CUAA lo cerca per P.iva");
  		//se non è presente provo a cercarlo per partita IVA
  		anagAziendaVO = new AnagAziendaVO(); // AGRICHIM-142 - se non si resettano i parametri, la ricerca avviene con CUAA ancora valorizzato e fallisce sempre
  		anagAziendaVO.setPartitaIVA(codiceFiscale);
  		elencoAziende = beanParametriApplication.getAnagServiceCSIInterface().serviceGetListIdAziende(anagAziendaVO, Boolean.TRUE, Boolean.FALSE);
  	  	isPresenteAnagrafe = elencoAziende != null && elencoAziende.size() > 0;
  	    CuneoLogger.debug(this,"dopo la ricerca per p.iva isPresenteAnagrafe " + isPresenteAnagrafe);
  	}
  }
 /* if (isPresenteAnagrafe)
  {
  	templ.set("checkedPresenteAnagrafe", "checked");
  }
  */
  if (isPresenteAnagrafe)
  {
  	//templ.set(blkPresenteAnagrafe + ".checkedPresenteAnagrafe", "checked");
  	templ.set("presenteAnagrafe", "azienda censita");
  }
  else
  {
   templ.set("presenteAnagrafe", "azienda NON censita");
  }
  
  session.setAttribute(Constants.SESSION.IS_PRESENTE_ANAGRAFE, isPresenteAnagrafe);
%>

<%= templ.text() %>