<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.cuneo.*" isThreadSafe="true" %>
<%@ page import="org.apache.cxf.endpoint.Client" %> 
<%@ page import="org.apache.cxf.frontend.ClientProxy" %>
<%@ page import="it.csi.smrcomms.agripagopasrv.business.pagopa.*" %>
<%@ page import="org.apache.cxf.message.Message" %>
<%@ page import="java.net.*" %>
<%@ page import="javax.xml.namespace.QName" %>
<%@ page import="javax.xml.ws.BindingProvider" %>
<%@ page import="org.apache.cxf.transport.http.HTTPConduit" %>
<%@ page import="org.apache.cxf.transports.http.configuration.HTTPClientPolicy" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.io.OutputStream" %>
<%@ page import="it.csi.agrc.Pagamento" %>
<%
  it.csi.jsf.htmpl.Htmpl templ=null ;
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
%>
</jsp:useBean>
<%
String IdRichiesta = (String) request.getParameter("idRichiesta");
aut.setIdRichiestaChecked(IdRichiesta);
String azione = (String) request.getParameter("azione");
String tipo_pagamento = (String) request.getParameter("tipoPagamento");
boolean ok = false;
CuneoLogger.debug("/controller/controlliPagamenti.jsp","controlliPagamenti.jsp -------> START "+azione);
Pagamento p = new Pagamento();
p = pagamenti.selectPagamento(IdRichiesta,tipo_pagamento, false);
PagoPAWS ppaWS = getPagoPAWS(beanParametriApplication.getAgripagopaWSDL());
String messaggio_alert = "";
if(p==null || p.getIuv()==null || p.getIuv().equals("")){
	aut.setAvvisoControlliPagamenti("Pagamento non trovato");
}else if(azione.equals("aggiorna")){
	//verificaPagamento
	ParametriVerificaDatiPagamentoSingolo pvdps = new ParametriVerificaDatiPagamentoSingolo();
	pvdps.setCodiceFiscalePIVA(p.getCf());
	CuneoLogger.debug("/controller/controlliPagamenti.jsp","ParametriVerificaDatiPagamentoSingolo setCodiceFiscalePIVA "+pvdps.getCodiceFiscalePIVA());
	pvdps.setIuv(p.getIuv());
	CuneoLogger.debug("/controller/controlliPagamenti.jsp","ParametriVerificaDatiPagamentoSingolo setIuv "+pvdps.getIuv());
	pvdps.setApplicationId("58");
	CuneoLogger.debug("/controller/controlliPagamenti.jsp","ParametriVerificaDatiPagamentoSingolo setApplicationId "+pvdps.getApplicationId());
	EsitoVerificaDatiPagamentoSingolo esitovdps = ppaWS.verificaDatiPagamentoSingolo(pvdps);
	if(esitovdps.getDescErrore() == null && esitovdps.getEsito().equals("Pagamento eseguito")){
		CuneoLogger.debug("/controller/controlliPagamenti.jsp","ritorno EsitoVerificaDatiPagamentoSingolo positivo : esito -> "+esitovdps.getEsito());
		pagamenti.setDataPagamento(esitovdps.getDataEsito());
	    ok = pagamenti.updateFlagPagamento(IdRichiesta,pagamenti.getDataPagamento());
	    CuneoLogger.debug("/controller/controlliPagamenti.jsp","ritorno dall'update di ETICHETTA_CAMPIONE con esito "+(ok?"positivo":"negativo"));
	}else if(esitovdps.getDescErrore() == null && !esitovdps.getEsito().equals("Pagamento eseguito")){
		if(tipo_pagamento.equals("M3"))
			aut.setAvvisoControlliPagamenti("Esiste un avviso di pagamento non pagato, ristampalo e paga con i canali di PagoPA oppure annullalo. Se hai già pagato contatta l'assistenza");
		else
			aut.setAvvisoControlliPagamenti("Il pagamento on line e' in attesa, non e' ancora pervenuto; se il problema persiste contatta l'assistenza");
	}else{
		CuneoLogger.debug("/controller/controlliPagamenti.jsp","ritorno EsitoVerificaDatiPagamentoSingolo negativo : iuv -> "+esitovdps.getIuv());
		CuneoLogger.debug("/controller/controlliPagamenti.jsp","ritorno EsitoVerificaDatiPagamentoSingolo negativo : esito -> "+esitovdps.getEsito());
		CuneoLogger.debug("/controller/controlliPagamenti.jsp","ritorno EsitoVerificaDatiPagamentoSingolo negativo : id errore -> "+esitovdps.getIdErrore()!=null?esitovdps.getIdErrore():"");
		CuneoLogger.debug("/controller/controlliPagamenti.jsp","ritorno EsitoVerificaDatiPagamentoSingolo negativo : desc errore -> "+esitovdps.getDescErrore()!=null?esitovdps.getDescErrore():"");
		messaggio_alert = esitovdps.getEsito()!=null && !esitovdps.getEsito().equals("")?esitovdps.getEsito():"Impossibile aggiornare il pagamento";
		CuneoLogger.debug("/controller/controlliPagamenti.jsp","EsitoVerificaDatiPagamentoSingolo messaggio_alert "+messaggio_alert);
		aut.setAvvisoControlliPagamenti("Errore in fase di richiesta dati pagamento, contatta l'assistenza");
	}
}else if(azione.equals("annulla")){
	//annullaPagamento
	ParametriAnnullaPagamento pap = new ParametriAnnullaPagamento();
	pap.setCodiceFiscalePIVA(p.getCf());
	CuneoLogger.debug("/controller/controlliPagamenti.jsp","ParametriAnnullaPagamento setCodiceFiscalePIVA "+pap.getCodiceFiscalePIVA());
	pap.setIuv(p.getIuv());
	CuneoLogger.debug("/controller/controlliPagamenti.jsp","ParametriAnnullaPagamento setIuv "+pap.getIuv());
	pap.setIdApplicativo(58);
	CuneoLogger.debug("/controller/controlliPagamenti.jsp","ParametriAnnullaPagamento setApplicationId "+pap.getIdApplicativo());
	pap.setImporto(new BigDecimal(p.getImporto()));
	CuneoLogger.debug("/controller/controlliPagamenti.jsp","ParametriAnnullaPagamento setImporto "+pap.getImporto());
	EsitoAnnullaPagamento esitoap = ppaWS.annullaPagamento(pap);
	if(esitoap.getDescErrore() != null){
		CuneoLogger.debug("/controller/controlliPagamenti.jsp","ritorno EsitoAnnullaPagamento negativo : id errore -> "+esitoap.getIdErrore()!=null?esitoap.getIdErrore():"");
		CuneoLogger.debug("/controller/controlliPagamenti.jsp","ritorno EsitoAnnullaPagamento negativo : desc errore -> "+esitoap.getDescErrore()!=null?esitoap.getDescErrore():"");
		messaggio_alert = esitoap.getDescErrore()!=null && !esitoap.getDescErrore().equals("")?esitoap.getDescErrore():"Annullamento del pagamento non andato a buon fine";
		//messaggio_alert = Il documento non è stato trovato
		//messaggio_alert = messaggio_alert.replace("'", "\'");
		CuneoLogger.debug("/controller/controlliPagamenti.jsp","EsitoAnnullaPagamento messaggio_alert "+messaggio_alert);
		aut.setAvvisoControlliPagamenti("L'annullamento non e' possibile, contatta l'assistenza");
	}else{
		ok = pagamenti.updateAnnullaPagamento(IdRichiesta);
		aut.setAvvisoControlliPagamenti("Pagamento annullato");
	}
}else if(azione.equals("paga")){
    
}
CuneoLogger.debug("/view/controlliPagamenti.jsp","controlliPagamenti.jsp -------> END "+azione);
Utili.forward(request, response, "/jsp/controller/elencoPagamenti.jsp");
%>

<%! 
public static PagoPAWS getPagoPAWS(String urlWSDL) throws Exception {
	final String THIS_METHOD = "[::getPagoPAWS]";
	CuneoLogger.debug("/controller/controlliPagamenti.jsp",THIS_METHOD + " BEGIN.");
	CuneoLogger.debug("/controller/controlliPagamenti.jsp",THIS_METHOD + " PAGOPA_WSDL = " + urlWSDL);
	//Client client = null;
	PagoPAWS pagoPAWS = null;
	try {
		URI wsdlURI = new URI(urlWSDL);
		URL wsdlURL = wsdlURI.toURL();
		QName SERVICE_NAME = new QName("http://it/csi/smrcomms/agripagopasrv/business/pagopa", "PagoPAService");
		PagoPAService service = new PagoPAService(wsdlURL, SERVICE_NAME);
		// Recupero lo stub
		pagoPAWS = service.getPagoPAWSPort();
		BindingProvider bp = (BindingProvider) pagoPAWS;
		String url = urlWSDL.substring(0,urlWSDL.indexOf("?"));
		java.util.Map<String, Object> context = bp.getRequestContext();
		context.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, url);
		org.apache.cxf.endpoint.Client client = ClientProxy.getClient(pagoPAWS);
		HTTPConduit conduit = (HTTPConduit) client.getConduit();
		HTTPClientPolicy policy = conduit.getClient();
		policy.setConnectionTimeout(120000);
		policy.setReceiveTimeout(120000);
	}catch(Exception e){
		CuneoLogger.error("/controller/controlliPagamenti.jsp", "Exception during the invocation of getPagoPAWS method in AgrichimFO /controller/controlliPagamenti.jsp "+e);
		throw e;
	}finally{
		CuneoLogger.debug("/controller/controlliPagamenti.jsp",THIS_METHOD + " END");
		return pagoPAWS;
	}
}
%> 