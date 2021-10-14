<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.agrc.*,it.csi.cuneo.*" isThreadSafe="true" %>
<%@ page import="org.apache.cxf.endpoint.Client" %> 
<%@ page import="org.apache.cxf.frontend.ClientProxy" %>
<%@ page import="it.csi.smrcomms.agripagopasrv.business.pagopa.*" %>
<%@ page import="org.apache.cxf.message.Message" %>
<%@ page import="java.net.*" %>
<%@ page import="java.util.*" %>
<%@ page import="javax.xml.namespace.QName" %>
<%@ page import="javax.xml.ws.BindingProvider" %>
<%@ page import="org.apache.cxf.transport.http.HTTPConduit" %>
<%@ page import="org.apache.cxf.transports.http.configuration.HTTPClientPolicy" %>

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
List<Long> idRichiesta= new ArrayList<Long>();
idRichiesta = (List<Long>)session.getAttribute("pagamento_idRichiesta");
session.removeAttribute("pagamento_idRichiesta");
Pagamento p = null;
boolean ok = false;
for(Long ric : idRichiesta){
	p = new Pagamento();
	p.setIdRichiesta(""+ric);
	p.setIuv((String) request.getParameter("iuv"));
	p.setEsito((String) request.getParameter("esito"));
	CuneoLogger.debug("/controller/ritornoPagamento.jsp","ritornoPagamento.jsp -------> ritorno id richiesta : "+p.getIdRichiesta());
	CuneoLogger.debug("/controller/ritornoPagamento.jsp","ritornoPagamento.jsp -------> ritorno IUV : "+p.getIuv());
	CuneoLogger.debug("/controller/ritornoPagamento.jsp","ritornoPagamento.jsp -------> ritorno ESITO : "+p.getEsito());

	if(p.getEsito().equals("OK")){
	    if(p.getIuv().subSequence(0,2).equals("RF")){
	        p.setTipoPagamento("M1");
	    }else
	        p.setTipoPagamento("M3");
	}else
	    p.setTipoPagamento("null");

	session.setAttribute("iuv",p.getIuv());
	session.setAttribute("pagamento_tipoPagamento",p.getTipoPagamento());
	if(p.getIuv()!=null && (!p.getIuv().equals("null") || !p.getIuv().equals(""))
	&& p.getEsito()!=null && (!p.getEsito().equals("null") || !p.getEsito().equals("")))
	    ok = pagamenti.inserimentoRisultatoPagamento(p.getIdRichiesta(),p.getTipoPagamento(),p.getIuv(),p.getEsito());
	CuneoLogger.debug("/controller/ritornoPagamento.jsp","ritorno dall'inserimento della tupla in PAGAMENTI con esito "+(ok?"positivo":"negativo"));
}
session.setAttribute("iuv",(String) request.getParameter("iuv"));
session.setAttribute("pagamento_tipoPagamento",p.getTipoPagamento());

if(!ok)
	p.setEsito("KO");
//verificaPagamento
PagoPAWS ppaWS = getPagoPAWS(beanParametriApplication.getAgripagopaWSDL());
ParametriVerificaDatiPagamentoSingolo pvdps = new ParametriVerificaDatiPagamentoSingolo();
pvdps.setCodiceFiscalePIVA(aut.getCod_fiscale_pagatore());
CuneoLogger.debug("/controller/ritornoPagamento.jsp","ParametriVerificaDatiPagamentoSingolo setCodiceFiscalePIVA "+pvdps.getCodiceFiscalePIVA());
pvdps.setIuv(p.getIuv());
CuneoLogger.debug("/controller/ritornoPagamento.jsp","ParametriVerificaDatiPagamentoSingolo setIuv "+pvdps.getIuv());
pvdps.setApplicationId("58");
CuneoLogger.debug("/controller/ritornoPagamento.jsp","ParametriVerificaDatiPagamentoSingolo setApplicationId "+pvdps.getApplicationId());
EsitoVerificaDatiPagamentoSingolo esitovdps = ppaWS.verificaDatiPagamentoSingolo(pvdps);
if(esitovdps.getDescErrore() == null && esitovdps.getEsito().equals("Pagamento eseguito")){
	CuneoLogger.debug("/controller/ritornoPagamento.jsp","ritorno EsitoVerificaDatiPagamentoSingolo positivo : esito -> "+esitovdps.getEsito());
	for(Long ric : idRichiesta){
	    ok = pagamenti.updateFlagPagamento(""+ric,null);
	    CuneoLogger.debug("/controller/ritornoPagamento.jsp","ritorno dall'update di ETICHETTA_CAMPIONE con esito "+(ok?"positivo":"negativo"));
	}
}else{
	CuneoLogger.debug("/controller/ritornoPagamento.jsp","ritorno EsitoVerificaDatiPagamentoSingolo negativo : iuv -> "+esitovdps.getIuv());
	CuneoLogger.debug("/controller/ritornoPagamento.jsp","ritorno EsitoVerificaDatiPagamentoSingolo negativo : esito -> "+esitovdps.getEsito());
	CuneoLogger.debug("/controller/ritornoPagamento.jsp","ritorno EsitoVerificaDatiPagamentoSingolo negativo : id errore -> "+esitovdps.getIdErrore()!=null?esitovdps.getIdErrore():"");
	CuneoLogger.debug("/controller/ritornoPagamento.jsp","ritorno EsitoVerificaDatiPagamentoSingolo negativo : desc errore-> "+esitovdps.getDescErrore()!=null?esitovdps.getDescErrore():"");
}
Utili.forward(request, response, "/jsp/view/ritornoPagamento.jsp");
%>

<%! 
public static PagoPAWS getPagoPAWS(String urlWSDL) throws Exception {
	final String THIS_METHOD = "[::getPagoPAWS]";
	CuneoLogger.debug("/controller/ritornoPagamento.jsp",THIS_METHOD + " BEGIN.");
	CuneoLogger.debug("/controller/ritornoPagamento.jsp",THIS_METHOD + " PAGOPA_WSDL = " + urlWSDL);
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
		CuneoLogger.error("/controller/ritornoPagamento.jsp", "Exception during the invocation of getPagoPAWS method in AgrichimFO /controller/ritornoPagamento.jsp "+e);
		throw e;
	}finally{
		CuneoLogger.debug("/controller/ritornoPagamento.jsp",THIS_METHOD + " END");
		return pagoPAWS;
	}
}
%> 