<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.cuneo.*,it.csi.agrc.*" isThreadSafe="true" %>

<%
  it.csi.jsf.htmpl.Htmpl templ=null ;
%>

<%@ include file="/jsp/controllaLogin.inc" %>
<%@ include file="/jsp/dataSource.inc" %>

<jsp:useBean id="beanAnalisi" scope="application" class="it.csi.agrc.BeanAnalisi"/>

<jsp:useBean
     id="fase"
     scope="page"
     class="it.csi.agrc.FasiRichiesta">
    <%
      fase.setDataSource(dataSource);
      fase.setAut(aut);
    %>
</jsp:useBean>

<jsp:useBean
     id="fattura"
     scope="request"
     class="it.csi.agrc.DatiFattura">
    <%
      fattura.setDataSource(dataSource);
      fattura.setAut(aut);
      fattura.setImportoSpedizione(beanAnalisi.getImportoSpedizione());
    %>
</jsp:useBean>

<jsp:useBean
     id="etichettaCampione"
     scope="page"
     class="it.csi.agrc.EtichettaCampione">
    <%
      etichettaCampione.setDataSource(dataSource);
      etichettaCampione.setAut(aut);
      etichettaCampione.setIdRichiesta(""+aut.getIdRichiestaCorrente());
    %>
</jsp:useBean>

<jsp:setProperty name="fattura" property="*"/>

<%
  byte tariffa;
  try
  {
    tariffa=Byte.parseByte(request.getParameter("tariffa"));
    CuneoLogger.debug(this,"tariffa: "+tariffa);
    CuneoLogger.debug(this,"is_presente_anagrafe: "+((Boolean) session.getAttribute(Constants.SESSION.IS_PRESENTE_ANAGRAFE)));
    etichettaCampione.impostaTariffaApplicata(false,false,false,tariffa, ((Boolean) session.getAttribute(Constants.SESSION.IS_PRESENTE_ANAGRAFE)));
  }
  catch (Exception e)
  {
    CuneoLogger.debug(this,"Tariffa fattura "+e.toString());
  }
  
  /**
  * Controllo gli eventuali errori tramite jsp
  * Se c'è un errore vuol dire che non è stato eseguito correttamente il
  * javascript
  */
  String errore=fattura.ControllaDati();
  if (errore!=null)
  {
    Utili.forwardConParametri(request, response, "/jsp/view/fattura.jsp?errore="+errore);
    return;
  }

  if (aut.getFase()<4)
  {
    /**
     * Se la fase è minore di 4 devo portarla a 4. Devo quindi
     * memorizzarne il nuovo valore bel bean di sesione oltre a scriverlo
     * nel database.
     */
    fase.updateFase(4);
    aut.setFase((byte)4);
  }
  fattura.insertUpdate();
  if ("S".equals(fattura.getSpedizione()))
  {
    aut.setSpedizioneFattura(true);
  }
  else
  {
    aut.setSpedizioneFattura(false);
  }
  session.setAttribute("aut",aut);

  /*A seconda del tipo di materiale da analizzare dovorò andare in una delle
    seguenti pagine*/
  String codMateriale = aut.getCodMateriale();
  if (Analisi.MAT_TERRENO.equals(codMateriale))
      Utili.forward(request, response, "/jsp/view/terrenoDati.jsp");
  else if (Analisi.MAT_ERBACEE.equals(codMateriale))
      Utili.forward(request, response, "/jsp/view/erbaceeDati.jsp");
  else if (Analisi.MAT_FOGLIE.equals(codMateriale) || Analisi.MAT_FRUTTA.equals(codMateriale))
      Utili.forward(request, response, "/jsp/view/foglieFruttaDati.jsp");
  else if (Analisi.ALTRE_MATRICI.equals(codMateriale))
	  Utili.forward(request, response, "/jsp/view/terrenoAltreMatrici.jsp");
%>


