<%@ page errorPage="/jsp/view/errorePopup.jsp" %>
<%@ page import="it.csi.cuneo.*,it.csi.agrc.*,it.csi.jsf.htmpl.*" isThreadSafe="true" %>

<%
  Htmpl templ = null;
%>

<%@ include file="/jsp/controllaLogin.inc" %>

<jsp:useBean
     id="beanRicerca"
     scope="session"
     class="it.csi.agrc.BeanRicerca"/>

<%
  String idRichiestaDa = request.getParameter("idRichiestaDa");
  String idRichiestaA = request.getParameter("idRichiestaA");
  String dataDa = request.getParameter("dataDa");
  String dataA = request.getParameter("dataA");
  String tipoMateriale = request.getParameter("tipoMateriale");
  String codiceFiscale = request.getParameter("codiceFiscale");
  String cognome = request.getParameter("cognome");
  String nome = request.getParameter("nome");
  String etichetta = request.getParameter("etichetta");
  String organizzazione = request.getParameter("organizzazione");

  beanRicerca.setCodiceFiscale(codiceFiscale);
  beanRicerca.setCognome(cognome);
  beanRicerca.setDataAAnno(request.getParameter("dataAAnno"));
  beanRicerca.setDataAGiorno(request.getParameter("dataAGiorno"));
  beanRicerca.setDataAMese(request.getParameter("dataAMese"));
  beanRicerca.setDataDaAnno(request.getParameter("dataDaAnno"));
  beanRicerca.setDataDaGiorno(request.getParameter("dataDaGiorno"));
  beanRicerca.setDataDaMese(request.getParameter("dataDaMese"));
  beanRicerca.setEtichetta(etichetta);
  beanRicerca.setIdRichiestaA(idRichiestaA);
  beanRicerca.setIdRichiestaDa(idRichiestaDa);
  beanRicerca.setNome(nome);
  if ("false".equals(organizzazione))
    beanRicerca.setOrganizzazione('P');
  else
    beanRicerca.setOrganizzazione('T');
  beanRicerca.setTipoMateriale(tipoMateriale);
  /**
   * Memorizzo i cambiamenti del bean di sessione beanRicerca
   */
   session.setAttribute("beanRicerca",beanRicerca);

  if ("".equals(idRichiestaDa)) idRichiestaDa = null;
  if ("".equals(idRichiestaA)) idRichiestaA = null;
  if ("".equals(dataDa)) dataDa = null;
  if ("".equals(dataA)) dataA = null;
  if ("".equals(tipoMateriale)) tipoMateriale = null;
  if ("".equals(codiceFiscale)) codiceFiscale = null;
  if ("".equals(cognome)) cognome = null;
  if ("".equals(nome)) nome = null;
  if ("".equals(etichetta)) etichetta = null;
  if ("".equals(organizzazione)) organizzazione = null;

  switch (aut.getUtente().getTipoUtente())
  {
    case 'L':
      aut.ricercaElencoCampioniLaboratorio(idRichiestaDa, idRichiestaA,
                                           dataDa, dataA, tipoMateriale,
                                           codiceFiscale, cognome, nome,
                                           etichetta);
      break;
    case 'T':
      // se organizzazione=true, uso ricercaElencoCampioniTecnico
      // ed esco dallo switch, altrimenti ricado nel caso del tecnico/privato
      // e quindi scivolo nel case 'P'
      if ( "true".equals(organizzazione) )
      {
        aut.ricercaElencoCampioniTecnico(idRichiestaDa, idRichiestaA,
                                         dataDa, dataA, tipoMateriale,
                                         codiceFiscale, cognome, nome,
                                         etichetta);
        break;
      }
    case 'P':
      aut.ricercaElencoCampioniBase(true, idRichiestaDa, idRichiestaA,
                                    dataDa, dataA, tipoMateriale,
                                    codiceFiscale, cognome, nome,
                                    etichetta);
      break;
  }
%>

<html>
<head></head>
<body onLoad="window.opener.location.href='../../jsp/view/elencoCampioni.jsp';window.opener.focus();window.close();"></body>
</html>
