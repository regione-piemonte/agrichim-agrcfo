package it.csi.agrc.servlet;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URL;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.namespace.QName ;
import javax.xml.ws.BindingProvider ;

import org.apache.cxf.frontend.ClientProxy ;
import org.apache.cxf.transport.http.HTTPConduit ;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy ;

import it.csi.agrc.Autenticazione;
import it.csi.agrc.BeanParametri;
import it.csi.agrc.Pagamento;
import it.csi.cuneo.CuneoLogger;
import it.csi.cuneo.Utili;
import it.csi.cuneo.servlet.GetPdfServlet;
import it.csi.smrcomms.agripagopasrv.business.pagopa.EsitoLeggiDocumento;
import it.csi.smrcomms.agripagopasrv.business.pagopa.PagoPAService;
import it.csi.smrcomms.agripagopasrv.business.pagopa.PagoPAWS;
import it.csi.smrcomms.agripagopasrv.business.pagopa.ParametriLeggiDocumento;

public class PdfAvvisoPagamento extends GetPdfServlet{
	private static final long serialVersionUID = -4325611315132055180L;
	private final static String this_class = "it.csi.agrc.servlet.PdfAvvisoPagamento";
	HttpSession session;
	ServletContext context;
	Autenticazione aut;
	Object dataSource;
	
	public PdfAvvisoPagamento(){
		this.setOutputName("avvisoPagamento.pdf");
	}
	
	
	
	@Override
	protected byte[] getPdf(HttpServletRequest request) throws Exception {
		final String this_method = "["+this_class+"::getPdf]";
		byte file[] = null;
		String idRichiesta = request.getParameter("idRichiesta");
		String tipoDoc = request.getParameter("tipoDocumento");
		String tipoPag = request.getParameter("tipoPagamento");
		session = request.getSession();
	    aut = (Autenticazione)session.getAttribute("aut");
	    if (aut==null){
	      ServletException se = new ServletException("Sessione non valida");
	      throw se;
	    }
	    context=session.getServletContext();
	    if (Utili.POOLMAN)
	      dataSource=context.getAttribute("poolBean");
	    else
	      dataSource=context.getAttribute("dataSourceBean");
		try {
			Pagamento pagamenti = new Pagamento(dataSource, aut);
			Pagamento p = pagamenti.selectPagamento(idRichiesta,tipoPag, true);
			BeanParametri par=(BeanParametri)context.getAttribute("beanParametriApplication");
			PagoPAWS ppaWS = getPagoPAWS(par.getAgripagopaWSDL());
			ParametriLeggiDocumento pld = new ParametriLeggiDocumento();
			pld.setCodiceFiscalePIVA(p.getCf());
			pld.setIuv(p.getIuv());
			pld.setIdApplicativo(58);
			pld.setTipoDocumento(tipoDoc);
			pld.setImporto(BigDecimal.ZERO);
			CuneoLogger.debug(this_method,"ParametriLeggiDocumento setCodiceFiscalePIVA "+pld.getCodiceFiscalePIVA());
			CuneoLogger.debug(this_method,"ParametriLeggiDocumento setIuv "+pld.getIuv());
			CuneoLogger.debug(this_method,"ParametriLeggiDocumento setTipoDocumento "+pld.getTipoDocumento());
			CuneoLogger.debug(this_method,"ParametriLeggiDocumento setApplicationId "+pld.getIdApplicativo());
			EsitoLeggiDocumento esitold = ppaWS.leggiDocumento(pld);
			if(esitold.getDescErrore() == null && esitold.getFile()!=null){
				file = esitold.getFile();
			}else {
				CuneoLogger.debug(this_method, "ritorno leggiDocumento negativo : id errore -> "+esitold.getIdErrore());
				CuneoLogger.debug(this_method, "ritorno leggiDocumento negativo : id errore -> "+esitold.getDescErrore());
			}
		}catch(Exception e) {
			CuneoLogger.error(this_method, "Exception during getPdf method in AgrichimFO "+e);
		}finally {
			return file;
		}
		
	}
	public static PagoPAWS getPagoPAWS(String urlWSDL) throws Exception {
		final String THIS_METHOD = "["+this_class+"::getPagoPAWS]";
		CuneoLogger.debug(THIS_METHOD, " BEGIN.");
		CuneoLogger.debug(THIS_METHOD, " PAGOPA_WSDL = " + urlWSDL);
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
			CuneoLogger.error(THIS_METHOD, "Exception during the invocation of getPagoPAWS method in AgrichimFO "+e);
			throw e;
		}finally{
			CuneoLogger.debug(THIS_METHOD," END");
			return pagoPAWS;
		}
	}
}