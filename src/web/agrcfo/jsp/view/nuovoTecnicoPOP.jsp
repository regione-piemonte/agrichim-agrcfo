<%@ page errorPage="/jsp/view/errorePopup.jsp" %>
<%@ page import="it.csi.agrc.*,it.csi.jsf.htmpl.*,it.csi.cuneo.*" isThreadSafe="true" %>

<%
  Htmpl templ =
  HtmplFactory.getInstance(application).getHtmpl("/jsp/layout/nuovoTecnicoPOP.htm");
%>

<%@ include file="/jsp/controllaLogin.inc" %>
<%@ include file="/jsp/dataSource.inc" %>

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
     id="anagUte"
     scope="request"
     class="it.csi.agrc.Anagrafica"/>

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
  if ("codFisEs".equals(request.getParameter("codFisEs")))
  {
    templ.newBlock("codFisEsistente");
    errore="";
  }
  if (errore != null)
  {
    /**
      * Leggo i parametri che si tovano nel bean
      * */
    String cognome=anagUte.getCognomeRagioneSociale();
    String nome=anagUte.getNome();
    String codiceFiscale=anagUte.getCodiceIdentificativo();
    String indirizzo=anagUte.getIndirizzo();
    String cap=anagUte.getCap();
    String istat=anagUte.getComuneResidenza();
    if (istat==null) istat="";
    String comune=comuni.getDescrizioneComune(istat);
    String eMail=anagUte.getEmail();
    String telefono=anagUte.getTelefono();
    String cellulare=anagUte.getCellulare();
    String fax=anagUte.getFax();

    /**
      * Se qualche parametro è nullo devo impostarlo a vuoto altrimenti
      * htmpl si arrabbia
      * */
    if (cognome==null) cognome="";
    if (nome==null) nome="";
    if (codiceFiscale==null) codiceFiscale="";
    if (indirizzo==null) indirizzo="";
    if (cap==null) cap="";
    if (comune==null) comune="";
    if (eMail==null) eMail="";
    if (telefono==null) telefono="";
    if (cellulare==null) cellulare="";
    if (fax==null) fax="";

    templ.bset("cognome",cognome);
    templ.bset("nome",nome);
    templ.bset("codiceFiscale",codiceFiscale);
    templ.bset("indirizzo",indirizzo);
    templ.bset("cap",cap);
    templ.bset("istat",istat);
    templ.bset("comune",comune);
    templ.bset("eMail",eMail);
    templ.bset("telefono",telefono);
    templ.bset("cellulare",cellulare);
    templ.bset("fax",fax);

  }

%>

<%= templ.text() %>
