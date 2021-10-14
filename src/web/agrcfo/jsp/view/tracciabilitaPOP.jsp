<%@ page errorPage="/jsp/view/errorePopup.jsp" %>
<%@ page import="it.csi.agrc.*,it.csi.jsf.htmpl.*,it.csi.cuneo.*" isThreadSafe="true" %>

<%
  Htmpl templ =
  HtmplFactory.getInstance(application).getHtmpl("/jsp/layout/tracciabilitaPOP.htm");
%>

<%@ include file="/jsp/controllaLogin.inc" %>
<%@ include file="/jsp/dataSource.inc" %>

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
     id="tracciabilitaPOPs"
     scope="page"
     class="it.csi.agrc.TracciabilitaPOPs">
    <%
      tracciabilitaPOPs.setDataSource(dataSource);
      tracciabilitaPOPs.setAut(aut);
    %>
</jsp:useBean>

<%
  String idRichiesta = request.getParameter("dettaglio");
	if(idRichiesta.contains("A")){
		String rep = idRichiesta.substring(0,idRichiesta.length()-1);
		idRichiesta = rep;
	}
  String anomalia;
  etichettaCampione.select(idRichiesta);

  templ.newBlock("EtichettaCampione");
  templ.set("EtichettaCampione.idRichiesta",etichettaCampione.getIdRichiesta());
  templ.set("EtichettaCampione.numAnno",
		  (etichettaCampione.getNumeroCampione()!=null?etichettaCampione.getNumeroCampione():"")+"/"+(etichettaCampione.getAnnoCampione()!=null?etichettaCampione.getAnnoCampione():""));
  templ.set("EtichettaCampione.descMateriale",
            etichettaCampione.getDescMateriale());
  templ.set("EtichettaCampione.descrizioneEtichetta",
            etichettaCampione.getDescrizioneEtichetta());
  templ.set("EtichettaCampione.proprietario",etichettaCampione.getProprietario());
  if (etichettaCampione.getStatoAnomalia()!= null)
  {
    anomalia=etichettaCampione.getStatoAnomalia().toUpperCase();
    if (anomalia.equals("A"))
    {
      templ.newBlock("anomalia");
      templ.set("EtichettaCampione.anomalia.anomalia","ANOMALA");
    }
    if (anomalia.equals("B"))
    {
      templ.newBlock("anomalia");
      templ.set("EtichettaCampione.anomalia.anomalia","BLOCCATA");
    }
  }
  tracciabilitaPOPs.fill(idRichiesta);
  int size=tracciabilitaPOPs.size();
  TracciabilitaPOP t;
  for(int i=0;i<size;i++)
  {
    t=tracciabilitaPOPs.get(i);
    templ.newBlock("tracciatura");
    templ.set("EtichettaCampione.tracciatura.data",t.getData());
    templ.set("EtichettaCampione.tracciatura.azione",t.getAzione());
    templ.set("EtichettaCampione.tracciatura.note",t.getNote());
  }
%>

<%= templ.text() %>
