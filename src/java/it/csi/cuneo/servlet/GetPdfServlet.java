package it.csi.cuneo.servlet;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.csi.cuneo.CuneoLogger;

public abstract class GetPdfServlet extends HttpServlet{
	private final String this_class=" [GetPdfServlet::";
	private static final long serialVersionUID = -6741431408681454783L;
	private static final String CONTENT_TYPE_PDF = "application/pdf";
	private static final String CONTENT_TYPE_DOWNLOAD = "application/x-download";
	private static final String ERROR_PAGE = "/jsp/view/errorePopup.jsp";
	
	/* Variabili da impostare nel costruttore di OGNI sottoclasse */
	private String outputName = null;
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String this_method = this_class+":service]";
		if (null==this.getOutputName()){
			ServletException se = new ServletException("\nPdfPagamentiServlet- Tentativo fallito a causa di outputName non definito");
			throw se;
		}
		
		try{
			String action=request.getParameter("action");
			String tipoDoc = request.getParameter("tipoDocumento");
			byte ba[] = getPdf(request);
			if(ba!=null) {
				response.setContentLength(ba.length);
				
				if ("download".equals(action)){
					response.setContentType(CONTENT_TYPE_DOWNLOAD);
					response.addHeader("Content-Disposition","attachment;filename = " + this.getOutputName());
				}else //if ("open".equals(action)) // azione predefinita
					response.setContentType(CONTENT_TYPE_PDF);
				
				OutputStream out = response.getOutputStream();
				out.write(ba);
				//out.flush();
				//out.close();
			}else {
				response.setContentType("text/html");
				if(tipoDoc.equals("AV"))
					response.getWriter().write("<h1><b>Avviso di pagamento non disponibile, contatta l'assistenza</b></h1>");
				else
					response.getWriter().write("<h1><b>Ricevuta non disponibile, contatta l'assistenza</b></h1>");
			}
		}catch (Exception e){
			CuneoLogger.debug(this_method, "Errore nella Servlet "+e);
			//System.err.println("\nPdfServlet.service()\n"+e.getMessage()+"\n");
			request.setAttribute("javax.servlet.jsp.jspException", e);
			request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
		}
	}	

	protected abstract byte[] getPdf(HttpServletRequest request)
			throws Exception;

	public String getOutputName(){
		return outputName;
	}
	public void setOutputName(String outputName){
		this.outputName = outputName;
	}
}