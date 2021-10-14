<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.agrc.*,it.csi.jsf.htmpl.*,it.csi.cuneo.*" isThreadSafe="true" %>

<%
  Htmpl templ =
  HtmplFactory.getInstance(application).getHtmpl("/jsp/layout/confermaRichiesta.htm");
%>

<%@ include file="/jsp/controllaLogin.inc" %>

<%
  templ.bset("idRichiesta",String.valueOf(aut.getIdRichiestaCorrente()));

 /*A seconda del tipo di materiale da analizzare dovrò tornare a una delle
    seguenti pagine*/

  String codMateriale = aut.getCodMateriale();
  if (Analisi.MAT_TERRENO.equals(codMateriale))
      templ.bset("pageBack","terrenoTipoAnalisi.jsp");
  else if (Analisi.MAT_ERBACEE.equals(codMateriale))
      templ.bset("pageBack","erbaceeTipoAnalisi.jsp");
  else if (Analisi.MAT_FOGLIE.equals(codMateriale) || Analisi.MAT_FRUTTA.equals(codMateriale))
      templ.bset("pageBack","foglieFruttaTipoAnalisi.jsp");
  else if (Analisi.ALTRE_MATRICI.equals(codMateriale))
      templ.bset("pageBack","terrenoAltreMatrici.jsp?let=true");

  templ.bset("tipoMateriale",codMateriale);
%>


<%= templ.text() %>
