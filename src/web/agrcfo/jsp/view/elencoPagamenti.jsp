<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.agrc.*,it.csi.jsf.htmpl.*,it.csi.cuneo.*" isThreadSafe="true" %>

<%
  Htmpl templ = HtmplFactory.getInstance(application).getHtmpl("/jsp/layout/elencoPagamenti.htm");
%>

<%@ include file="/jsp/controllaLogin.inc" %>

<%@ include file="/jsp/dataSource.inc" %>

<jsp:useBean
     id="pagamenti"
     scope="page"
     class="it.csi.agrc.Pagamento">
    <%
    pagamenti.setDataSource(dataSource);
    pagamenti.setAut(aut);
    pagamenti.setPasso(beanParametriApplication.getMaxPagamentiXPagina());
    %>
</jsp:useBean>

<%
int attuale,passo;
try {
  String temp=request.getParameter("attuale");
  if (temp!=null) 
 	 attuale=Integer.parseInt(temp);
  else 
 	 attuale=1;
} catch(Exception eNum) {
  attuale=1;
}
String ritornoDaPagAnnullato = (String) session.getAttribute("ritAnnPag");
CuneoLogger.debug(this, "ritornoDaPagAnnullato "+ritornoDaPagAnnullato);
if(ritornoDaPagAnnullato!=null && ritornoDaPagAnnullato.equals("S")){
	templ.newBlock("avvisoRitPagamento");
	templ.set("avvisoRitPagamento.alertRitPagamento","Pagamento annullato, riselezionare la richiesta e procedere tramite il pulsante paga");
}
/**
 * La porzione di codice seguente permette di gestire una visione dei
 * record selezionati simile al motore di ricerca
 */
pagamenti.setBaseElementi(attuale);
pagamenti.fillElencoPagamenti();
int size=pagamenti.size();
if (size>0) {
	passo=pagamenti.getPasso();
	templ.newBlock("elencoPagamenti");
	templ.newBlock("elencoPagamenti.linkAnagrafica");
	if (attuale!=1) {
		templ.newBlock("indietro");
		templ.set("elencoPagamenti.indietro.attuale",""+(attuale-passo));
	} else
		   templ.set("elencoPagamenti.nonIndietro.nonIndietro","");
	
	if ((attuale+passo) <=pagamenti.getNumRecord()) {
		templ.newBlock("avanti");
		templ.set("elencoPagamenti.avanti.attuale",""+(attuale+passo));
	}
	
	templ.set("elencoPagamenti.num",""+pagamenti.getNumRecord());
	
	if (size!=pagamenti.getNumRecord()) {
		int numPag=((pagamenti.getNumRecord()-1)/passo)+1;
		int pagAtt=(attuale/passo)+1;
		templ.set("elencoPagamenti.pagine","Pagina "+pagAtt+"/"+numPag);
	}
	Pagamento e;
	String idRichiesta=null, codiceMateriale=null, stato40=null;
	for(int i=0;i<size;i++) {
		templ.newBlock("elencoPagamenti.elencoPagamentoBody");
		e=pagamenti.get(i);
		idRichiesta = e.getIdRichiesta();
		codiceMateriale = e.getCodiceMateriale();
		if(codiceMateriale.equals("ZMA"))
			idRichiesta = idRichiesta+"A";
		if (i==0){
			templ.newBlock("elencoSi");
			templ.set("elencoPagamentiSi.idRichiestaPrimo",idRichiesta);
			//templ.set("elencoPagamenti.elencoPagamentoBody.checked","checked");
		}
		if(idRichiesta.equals(aut.getIdRichiestaChecked())){
			templ.set("elencoPagamenti.elencoPagamentoBody.checked","checked");
			aut.setIdRichiestaChecked("");
		}
		templ.set("elencoPagamenti.elencoPagamentoBody.idRichiesta",idRichiesta);
		
		templ.set("elencoPagamenti.elencoPagamentoBody.statoAttuale", e.getStatoAttuale());
		
		templ.set("elencoPagamenti.elencoPagamentoBody.data",e.getDataInserimentoRichiesta());
		templ.set("elencoPagamenti.elencoPagamentoBody.descrizioneEtichetta",e.getDescrizioneEtichetta());
		templ.set("elencoPagamenti.elencoPagamentoBody.proprietario",e.getProprietario());
		templ.set("elencoPagamenti.elencoPagamentoBody.richiedente",e.getRichiedente());
		templ.set("elencoPagamenti.elencoPagamentoBody.tipoPagamento",e.getTipoPagamento());
		templ.set("elencoPagamenti.elencoPagamentoBody.iuv",e.getIuv());
		templ.set("elencoPagamenti.elencoPagamentoBody.idRichiesteMultiple",e.getIdRichiesteMultiple());
		templ.set("elencoPagamenti.elencoPagamentoBody.denominazione",e.getDenominazione());
		templ.set("elencoPagamenti.elencoPagamentoBody.statoPagamento",e.getStatoPagamento());
		templ.set("elencoPagamenti.elencoPagamentoBody.cod_dest",e.getCodDestinatario());
		templ.set("elencoPagamenti.elencoPagamentoBody.pec",e.getPec());
		templ.set("elencoPagamenti.elencoPagamentoBody.fattura_SN",e.getFatturaRichiestaSN());
		templ.set("elencoPagamenti.elencoPagamentoBody.codice_tracciatura",e.getCodiceTracciatura());
		
		if(e.getStatoPagamento()!=null && !e.getStatoPagamento().equals("")){
			if(e.getStatoPagamento().equals("S"))
				if(e.getTipoPagamento()!=null && e.getTipoPagamento().equals("M1"))
					  templ.set("elencoPagamenti.elencoPagamentoBody.descStatoPagamento","Pagata Online");
				else if(e.getTipoPagamento()!=null && e.getTipoPagamento().equals("M3"))
					  templ.set("elencoPagamenti.elencoPagamentoBody.descStatoPagamento","Pagata con avviso");
				else
				  templ.set("elencoPagamenti.elencoPagamentoBody.descStatoPagamento","Pagata");
			else if(e.getStatoPagamento().equals("N"))
				if(e.getTipoPagamento()!=null && e.getTipoPagamento().equals("M3") 
					    && (e.getDataAnnullamento()==null || e.getDataAnnullamento().equals("")))
                      templ.set("elencoPagamenti.elencoPagamentoBody.descStatoPagamento","Da Pagare - avviso emesso");
                else
				  templ.set("elencoPagamenti.elencoPagamentoBody.descStatoPagamento","Da Pagare - avviso da emettere");
			else if(e.getStatoPagamento().equals("G"))
				  templ.set("elencoPagamenti.elencoPagamentoBody.descStatoPagamento","Gratuita");
		}else
			  templ.set("elencoPagamenti.elencoPagamentoBody.descStatoPagamento","");
	}
}else
    templ.newBlock("elencoPagamentiNo");
if(aut.getAvvisoControlliPagamenti()!=null && aut.getAvvisoControlliPagamenti()!=""){
	templ.newBlock("avvisoControlli");
	templ.set("avvisoControlli.alertAvvisoControlli",aut.getAvvisoControlliPagamenti());
	aut.setAvvisoControlliPagamenti("");
}
	
%>

<%-- HTMPL genera ora la pagina web e si finalizza così lo stato stazionario --%>

<%= templ.text() %>
