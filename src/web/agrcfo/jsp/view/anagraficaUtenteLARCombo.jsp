<%@page import="it.csi.solmr.dto.anag.AnagAziendaVO"%>
<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.agrc.*,it.csi.jsf.htmpl.*,it.csi.cuneo.*,java.util.*" isThreadSafe="true" %>

<%
  Htmpl templ =
  HtmplFactory.getInstance(application).getHtmpl("/jsp/layout/anagraficaUtenteLAR.htm");
%>

<%@ include file="/jsp/controllaLogin.inc" %>
<%@ include file="/jsp/dataSource.inc" %>

<jsp:useBean
     id="beanOrgTecnico"
     scope="application"
     class="it.csi.agrc.BeanOrganizzazioniTecnico"/>

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
     id="etichettaCampione"
     scope="page"
     class="it.csi.agrc.EtichettaCampione">
<%
  etichettaCampione.setDataSource(dataSource);
  etichettaCampione.setAut(aut);
%>
</jsp:useBean>

<jsp:useBean
     id="organizzazioniTecnico"
     scope="page"
     class="it.csi.agrc.OrganizzazioniTecnico">
<%
  organizzazioniTecnico.setDataSource(dataSource);
  organizzazioniTecnico.setAut(aut);
%>
</jsp:useBean>


<%

  
  if ("readonly".equals(request.getParameter("disabledUpdate")))
  {
	//disabilito i campi di input
	templ.bset("disabledUpdate","readonly");
	templ.bset("comuneButtonStyle","style='visibility: hidden'");
  }
  else templ.bset("modificaAnagraficaStyle","style='visibility: hidden'");
  
	/**
	 * Per prima cosa controllo se il tipo cliente è un tecnico oppure un privato
	 * andando a vedere se è valorizzato il parametro tipoUtente. Se invece non è
	 * valorizzato chiamo il metodo getAnagraficaUtente
	 **/
  String tipoUtente=request.getParameter("tipoUtente");
  String tecnico[]=null;
  boolean organizzazione=false;//utilizzata per sapere se la pagina è stata ricaricata
  //in seguito ad un onchange sulla combo dei tipi delle organizzazioni o delle
  //organizzazioni

  /**
   * Con il metodo getAnagraficaUtente vado a vedere se questo campione è legato
   * ad un tecnico. Se si mi verrà restituito un vettore contenente tre stringhe:
   * tecnico[CODICE_IDENTIFICATIVO] : contiene il CODICE_IDENTIFICATIVO
   * tecnico[ID_ORGANIZZAZIONE] : contiene il ID_ORGANIZZAZIONE
   * tecnico[ID_TIPO_ORGANIZZAZIONE] : contiene il ID_TIPO_ORGANIZZAZIONE
   * Se invece viene restituito null significa che il campione è legato
   * ad un privato oppure che è la prima volta che si passa in questa fase
   **/
  if (tipoUtente==null)
  {
    tecnico=etichettaCampione.getAnagraficaUtente();
  }
  if ("T".equals(tipoUtente))
  {
    //Tipo cliente è un tecnico, quindi vado a leggere i valori delle
    //tre combo box e le carico in tecnico
    tecnico=new String[3];
    String temp=request.getParameter("tipoOrganizzazione");
    if (temp==null) temp="";
    tecnico[etichettaCampione.ID_TIPO_ORGANIZZAZIONE]=temp;

    temp=request.getParameter("organizzazione");
    if (temp==null) temp="";
    tecnico[etichettaCampione.ID_ORGANIZZAZIONE]=temp;

    temp=request.getParameter("anagraficaTecnico");
    if (temp==null) temp="";
    tecnico[etichettaCampione.ID_ANAGRAFICA]=temp;
  }
  if (tecnico==null)
  {
    //Tipo cliente privato, oppure prima volta che è in questa fase
    templ.bset("checkedPrivato","checked");
    tecnico=new String[3];
    tecnico[etichettaCampione.ID_TIPO_ORGANIZZAZIONE]="";
    tecnico[etichettaCampione.ID_ORGANIZZAZIONE]="";
    tecnico[etichettaCampione.ID_ANAGRAFICA]="";
  }
  else
  {
    //Tipo cliente tecnico
    templ.bset("checkedTecnico","checked");
  }

  /**
   * Carico la combo dei tipi di organizzazione
   **/
  String codStr[],descStr[];
  codStr=beanOrgTecnico.getCod();
  descStr=beanOrgTecnico.getDesc();
  for(int i=0;i<codStr.length;i++)
  {
    templ.newBlock("tipoOrganizzazione");
    if (tecnico[etichettaCampione.ID_TIPO_ORGANIZZAZIONE].equals(codStr[i]))
      templ.set("tipoOrganizzazione.selected","selected");
    else
      templ.set("tipoOrganizzazione.selected","");
    templ.set("tipoOrganizzazione.cod",codStr[i]);
    templ.set("tipoOrganizzazione.desc",descStr[i]);
  }

  /**
   * Carico la combo delle organizzazioni se è già stato scelto un tipo
   * di organizzazione
   **/
  Vector cod=new Vector(),desc=new Vector();
  int size;
  if (!"".equals(tecnico[etichettaCampione.ID_TIPO_ORGANIZZAZIONE]))
  {
    cod.clear();
    desc.clear();
    organizzazioniTecnico.getOrganizzazione(cod,desc,tecnico[etichettaCampione.ID_TIPO_ORGANIZZAZIONE]);
    size=cod.size();
    for(int i=0;i<size;i++)
    {
      templ.newBlock("organizzazione");
      if (tecnico[etichettaCampione.ID_ORGANIZZAZIONE].equals(cod.get(i)))
      {
        templ.set("organizzazione.selected","selected");
        /**
         * Se sono qua vuol dire che è stata ricaricata la combo delle
         * organizzazioni e quindi che bisogna riempire la combo dei
         * tecnici
         */
        organizzazione=true;
      }
      else
        templ.set("organizzazione.selected","");
      templ.set("organizzazione.cod",(String)cod.get(i));
      templ.set("organizzazione.desc",(String)desc.get(i));
    }

    /**
     * Carico la combo dei tecnici solo se è già stata scelta un
     * organizzazione
     **/
    if (!"".equals(tecnico[etichettaCampione.ID_ORGANIZZAZIONE]) && organizzazione)
    {
      cod.clear();
      desc.clear();
      organizzazioniTecnico.getTecnicoFromOrganizzazione(cod,desc,tecnico[etichettaCampione.ID_ORGANIZZAZIONE]);
      size=cod.size();
      for(int i=0;i<size;i++)
      {
        templ.newBlock("tecnico");
        if (tecnico[etichettaCampione.ID_ANAGRAFICA].equals(cod.get(i)))
          templ.set("tecnico.selected","selected");
        else
          templ.set("tecnico.selected","");
        templ.set("tecnico.cod",(String)cod.get(i));
        templ.set("tecnico.desc",(String)desc.get(i));
      }

  }

  }

  /**
   * Se l'utente vuole eseguire la ricerca il parametro anagraficaSearch
   * viene valorizzato con la partita iva o il codice fiscale desiderato.
   * Se il parametro anagraficaDup è valorizzato vuol dire che l'utente
   * ha inserito un codice identificativo, che esiste già nel database, senza
   * prima fare una ricerca.
   **/
  String codiceIdentificativo=request.getParameter("codiceIdentificativo");
  String codiceIdentificativoOld=request.getParameter("codiceIdentificativoOld");
  String tipoPersona=request.getParameter("tipoPersona");
  String cognome=request.getParameter("cognomeRagioneSociale");
  String nome=request.getParameter("nome");
  String indirizzo=request.getParameter("indirizzo");
  String cap=request.getParameter("cap");
  String comune=request.getParameter("comune");
  String comuneResidenza=request.getParameter("comuneResidenza");
  String telefono=request.getParameter("telefono");
  String cellulare=request.getParameter("cellulare");
  String fax=request.getParameter("fax");
  String email=request.getParameter("email");

  if (codiceIdentificativo==null) codiceIdentificativo="";

  if (codiceIdentificativoOld==null) codiceIdentificativoOld="";

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

  templ.bset("codiceFiscale",codiceIdentificativo);
  templ.bset("codiceFiscaleOld",codiceIdentificativoOld);
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

  if (aut.isCoordinateGeografiche() && aut.isPiemonte())
      templ.bset("pageBack","coordinateGIS.jsp");
  else
      templ.bset("pageBack","tipoCampione.jsp");

  //AGRICHIM-7
  //Visualizzazione se proprietario del campione è un'azienda agricola
  boolean isPresenteAnagrafe = false;
  if (codiceIdentificativo != null && ! "".equals(codiceIdentificativo))
  {
  	AnagAziendaVO anagAziendaVO = new AnagAziendaVO();
  	anagAziendaVO.setCUAA(codiceIdentificativo);
  	Vector elencoAziende = beanParametriApplication.getAnagServiceCSIInterface().serviceGetListIdAziende(anagAziendaVO, Boolean.TRUE, Boolean.FALSE);
  	isPresenteAnagrafe = elencoAziende != null && elencoAziende.size() > 0;
  }
  
  if (isPresenteAnagrafe)
  {
  	templ.set("checkedPresenteAnagrafe", "checked");
  }
  
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