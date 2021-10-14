package it.csi.agrc.servlet;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.sql.DataSource;

import it.csi.agrc.Analisi;
import it.csi.agrc.Autenticazione;
import it.csi.agrc.BeanParametri;
import it.csi.agrc.Pagamento;
import it.csi.cuneo.CuneoLogger;

public class ServletPagaSiappagoPA extends HttpServlet {
	
	private static final long serialVersionUID = 3354504705543740441L;
	private final String CLASS_NAME = "[ServletPagaSiappagoPA";
	ServletConfig config;
	
	public void init(ServletConfig config) {
		this.config = config; 
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		String method = CLASS_NAME+ ":::doGet]";
		CuneoLogger.debug(method," START ");
	    ServletContext context=config.getServletContext();
	    BeanParametri beanParametriApplication = (BeanParametri) context.getAttribute("beanParametriApplication");
	    DataSource dataSourceBean = (DataSource) context.getAttribute("dataSourceBean");
	    Autenticazione aut =(Autenticazione) request.getSession().getAttribute("aut");    
	    String ret = "";
		try {
			ret = loadParametriPagamento((String) request.getParameter("idRichieste"),(String) request.getParameter("sceltaPagatore"), beanParametriApplication, dataSourceBean,aut);
		} catch (Exception e) {
			CuneoLogger.error(method,"Exception - "+e);
		}
		response.setContentType("text/plain");
		response.getWriter().write(ret);
		CuneoLogger.debug(method," END ");
	}
	
	private String loadParametriPagamento(String idRichieste, String sceltaPagatore, BeanParametri beanParametriApplication, DataSource dataSourceBean,Autenticazione aut) throws Exception{
		String method = CLASS_NAME+ ":::loadParametriPagamento]";
		CuneoLogger.debug(method," START ");
		StringBuilder ret = new StringBuilder();
		String[] array_id_ric = idRichieste.split(",");
		Pagamento pag_dao = new Pagamento(dataSourceBean,aut);
		Analisi analisi = new Analisi(dataSourceBean,aut);
		Pagamento pagamentiMultipli;
		StringBuilder richieste = new StringBuilder("");
		try {
			for(int x=0; x < array_id_ric.length; x++) {
				if(array_id_ric[x].contains("A")) {
					String am = array_id_ric[x].substring(0,array_id_ric[x].length()-1);
					array_id_ric[x] = am;
				}
				richieste.append(array_id_ric[x]).append(",");
			}
			pagamentiMultipli = pag_dao.selectMultiploCompleto(richieste.toString(),sceltaPagatore);
			analisi.select(array_id_ric[0]);
			//prendo 0 perché teoricamente dovrebbero essere tutte "Terreni" o "AltreM"
			boolean isAM = analisi.getCodiciAnalisi().get(0).equals("AltreM");
			ret.append("").append("\n");//operazione
			ret.append(pagamentiMultipli.getCf()!=null?pagamentiMultipli.getCf():"").append("\n");
			ret.append("TITOLARE_CF@UTENTI_IRIDE2").append("\n");
			ret.append("2").append("\n");
			ret.append("58").append("\n").append("58").append("\n").append("58").append("\n");
			ret.append(pagamentiMultipli.getImporto()!=null?pagamentiMultipli.getImporto():"").append("\n");
			if(pagamentiMultipli.getFatturaRichiestaSN().equals("S")){
				CuneoLogger.debug(method," getFatturaRichiestaSN=S ");
			    ret.append(isAM?"FI15":"FI05").append("\n");
				ret.append(pagamentiMultipli.getPagatore_idAnagraficaAzienda()!=null?pagamentiMultipli.getPagatore_idAnagraficaAzienda():"").append("\n");
				ret.append(pagamentiMultipli.getPagatore_nome()!=null?pagamentiMultipli.getPagatore_nome():"").append("\n");
				ret.append(pagamentiMultipli.getPagatore_cognome()!=null?pagamentiMultipli.getPagatore_cognome():"").append("\n");
				ret.append(pagamentiMultipli.getPagatore_codiceFiscale()!=null?pagamentiMultipli.getPagatore_codiceFiscale():"").append("\n");
				ret.append(pagamentiMultipli.getPagatore_ragioneSociale()!=null?pagamentiMultipli.getPagatore_ragioneSociale():"").append("\n");
				ret.append(pagamentiMultipli.getPagatore_idUnicoPagatore()!=null?pagamentiMultipli.getPagatore_idUnicoPagatore():"").append("\n");
				ret.append(pagamentiMultipli.getPagatore_piva()!=null?pagamentiMultipli.getPagatore_piva():"").append("\n");
				ret.append(pagamentiMultipli.getPagatore_email()!=null?pagamentiMultipli.getPagatore_email():"").append("\n");
				ret.append(pagamentiMultipli.getPagatore_pec()!=null?pagamentiMultipli.getPagatore_pec():"").append("\n");
				ret.append(pagamentiMultipli.getVersante_nome()!=null?pagamentiMultipli.getVersante_nome():"").append("\n");
				ret.append(pagamentiMultipli.getVersante_cognome()!=null?pagamentiMultipli.getVersante_cognome():"").append("\n");
				ret.append(pagamentiMultipli.getVersante_codiceFiscale()!=null?pagamentiMultipli.getVersante_codiceFiscale():"").append("\n");
				ret.append(pagamentiMultipli.getVersante_piva()!=null?pagamentiMultipli.getVersante_piva():"").append("\n");
				ret.append(pagamentiMultipli.getVersante_ragioneSociale()!=null?pagamentiMultipli.getVersante_ragioneSociale():"").append("\n");
				ret.append(pagamentiMultipli.getVersante_idUnicoPagatore()!=null?pagamentiMultipli.getVersante_idUnicoPagatore():"").append("\n");
				ret.append(pagamentiMultipli.getVersante_email()!=null?pagamentiMultipli.getVersante_email():"").append("\n");
				ret.append(pagamentiMultipli.getVersante_pec()!=null?pagamentiMultipli.getVersante_pec():"").append("\n");
			}else if(pagamentiMultipli.getFatturaRichiestaSN().equals("N") && array_id_ric.length>1 && aut.getUtente().getTipoUtente()=='T'){
				CuneoLogger.debug(method," getFatturaRichiestaSN=N && array_id_ric>1 && getTipoUtente=T ");
			    ret.append(isAM?"FI14":"FI04").append("\n");
			    ret.append(pagamentiMultipli.getScelta_pagatore_idAnagraficaAzienda()!=null?pagamentiMultipli.getScelta_pagatore_idAnagraficaAzienda():"").append("\n");
			    ret.append(pagamentiMultipli.getScelta_pagatore_nome()!=null?pagamentiMultipli.getScelta_pagatore_nome():"").append("\n");
			    ret.append(pagamentiMultipli.getScelta_pagatore_cognome()!=null?pagamentiMultipli.getScelta_pagatore_cognome():"").append("\n");
			    ret.append(pagamentiMultipli.getScelta_pagatore_codiceFiscale()!=null?pagamentiMultipli.getScelta_pagatore_codiceFiscale():"").append("\n");
			    ret.append(pagamentiMultipli.getScelta_pagatore_ragioneSociale()!=null?pagamentiMultipli.getScelta_pagatore_ragioneSociale():"").append("\n");
			    ret.append(pagamentiMultipli.getScelta_pagatore_idUnicoPagatore()!=null?pagamentiMultipli.getScelta_pagatore_idUnicoPagatore():"").append("\n");
			    ret.append(pagamentiMultipli.getScelta_pagatore_piva()!=null?pagamentiMultipli.getScelta_pagatore_piva():"").append("\n");
			    ret.append(pagamentiMultipli.getScelta_pagatore_email()!=null?pagamentiMultipli.getScelta_pagatore_email():"").append("\n");
			    ret.append(pagamentiMultipli.getScelta_pagatore_pec()!=null?pagamentiMultipli.getScelta_pagatore_pec():"").append("\n");
			    ret.append(pagamentiMultipli.getScelta_versante_nome()!=null?pagamentiMultipli.getScelta_versante_nome():"").append("\n");
			    ret.append(pagamentiMultipli.getScelta_versante_cognome()!=null?pagamentiMultipli.getScelta_versante_cognome():"").append("\n");
			    ret.append(pagamentiMultipli.getScelta_versante_codiceFiscale()!=null?pagamentiMultipli.getScelta_versante_codiceFiscale():"").append("\n");
			    ret.append(pagamentiMultipli.getScelta_versante_piva()!=null?pagamentiMultipli.getScelta_versante_piva():"").append("\n");
			    ret.append(pagamentiMultipli.getScelta_versante_ragioneSociale()!=null?pagamentiMultipli.getScelta_versante_ragioneSociale():"").append("\n");
			    ret.append(pagamentiMultipli.getScelta_versante_idUnicoPagatore()!=null?pagamentiMultipli.getScelta_versante_idUnicoPagatore():"").append("\n");
			    ret.append(pagamentiMultipli.getScelta_versante_email()!=null?pagamentiMultipli.getScelta_versante_email():"").append("\n");
			    ret.append(pagamentiMultipli.getScelta_versante_pec()!=null?pagamentiMultipli.getScelta_versante_pec():"").append("\n");
			}else if(pagamentiMultipli.getFatturaRichiestaSN().equals("N")){
				CuneoLogger.debug(method," getFatturaRichiestaSN=N ");
			    ret.append(isAM?"FI14":"FI04").append("\n");
			    ret.append(pagamentiMultipli.getPagatore_idAnagraficaAzienda()!=null?pagamentiMultipli.getPagatore_idAnagraficaAzienda():"").append("\n");
			    ret.append(pagamentiMultipli.getPagatore_nome()!=null?pagamentiMultipli.getPagatore_nome():"").append("\n");
			    ret.append(pagamentiMultipli.getPagatore_cognome()!=null?pagamentiMultipli.getPagatore_cognome():"").append("\n");
			    ret.append(pagamentiMultipli.getPagatore_codiceFiscale()!=null?pagamentiMultipli.getPagatore_codiceFiscale():"").append("\n");
			    ret.append(pagamentiMultipli.getPagatore_ragioneSociale()!=null?pagamentiMultipli.getPagatore_ragioneSociale():"").append("\n");
			    ret.append(pagamentiMultipli.getPagatore_idUnicoPagatore()!=null?pagamentiMultipli.getPagatore_idUnicoPagatore():"").append("\n");
			    ret.append(pagamentiMultipli.getPagatore_piva()!=null?pagamentiMultipli.getPagatore_piva():"").append("\n");
			    ret.append(pagamentiMultipli.getPagatore_email()!=null?pagamentiMultipli.getPagatore_email():"").append("\n");
			    ret.append(pagamentiMultipli.getPagatore_pec()!=null?pagamentiMultipli.getPagatore_pec():"").append("\n");
			    ret.append(pagamentiMultipli.getVersante_nome()!=null?pagamentiMultipli.getVersante_nome():"").append("\n");
			    ret.append(pagamentiMultipli.getVersante_cognome()!=null?pagamentiMultipli.getVersante_cognome():"").append("\n");
			    ret.append(pagamentiMultipli.getVersante_codiceFiscale()!=null?pagamentiMultipli.getVersante_codiceFiscale():"").append("\n");
			    ret.append(pagamentiMultipli.getVersante_piva()!=null?pagamentiMultipli.getVersante_piva():"").append("\n");
			    ret.append(pagamentiMultipli.getVersante_ragioneSociale()!=null?pagamentiMultipli.getVersante_ragioneSociale():"").append("\n");
			    ret.append(pagamentiMultipli.getVersante_idUnicoPagatore()!=null?pagamentiMultipli.getVersante_idUnicoPagatore():"").append("\n");
			    ret.append(pagamentiMultipli.getVersante_email()!=null?pagamentiMultipli.getVersante_email():"").append("\n");
			    ret.append(pagamentiMultipli.getVersante_pec()!=null?pagamentiMultipli.getVersante_pec():"").append("\n");
			}
			ret.append(beanParametriApplication.getAgripagopaPageReferral()).append("\n");
			ret.append(beanParametriApplication.getAgripagopaAnnulla()+"?ritAnnPag=S").append("\n");
			if(aut.getUtente().getTipoUtente()=='L')
			    ret.append("M3").append("\n");
			else
			    ret.append("").append("\n");
			CuneoLogger.debug(method,ret.toString());
		} catch (Exception e) {
			CuneoLogger.error(method,"Exception - "+e);
			  throw (e);
		}
		CuneoLogger.debug(method," END ");
		return ret.toString();
		
	}
}