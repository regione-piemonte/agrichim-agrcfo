package it.csi.agrc;

import it.csi.cuneo.CuneoLogger;
import it.csi.solmr.interfaceCSI.anag.services.AnagServiceCSIInterface;

import java.util.StringTokenizer;

/**
 * Title:        Agrichim - Front Office
 * Description:  Richiesta analisi chimiche su campioni biologici agrari
 * Copyright:    Copyright (c) 2003
 * Company:      CSI Piemonte - Progettazione e Sviluppo - Cuneo
 * @author Michele Piantà, Piergiorgio Chiriotti
 * @version 1.0.0
 */

 /**
 * Questa classe viene utilizzata per memorizzare (tramite le proprietà
 * dei bean) i seguenti parametri:
 * - passPhrase
 * - mailMittente
 * - mailDestinatario []
 * - hostServerPosta
 * - nomeServerWebApplication
 * - maxRecordXPagina
 * - maxPagamentiXPagina
 * - urlStartApplicationSP
 * - urlStartApplicationPA
 * - urlHomePageApplication
 * - urlGIS
 * - urlIride
 * - nomeServerGIS
 * - nomeComandoGIS
 * - urlRitornoGISSP
 * - urlRitornoGISPA
 * - irideLogoutURLSP
 * - irideLogoutURLPA
 */

public class BeanParametri implements java.io.Serializable
{
  private static final long serialVersionUID = -7431674566961289911L;

  private String mailMittente;
  private String mailDestinatario[];
  private String hostServerPosta;
  private String nomeServerWebApplication;
  private int maxRecordXPagina;
  private int maxPagamentiXPagina;
  private String urlHomePageApplication;
  private String passPhrase;
  private String urlGIS;
  private String urlIride;
  private String nomeServerGIS;
  private String nomeComandoGIS;
  private String dicituraIntroduttivaGIS;
  private String urlRitornoGISPA;
  private String urlStartApplicationPA;
  private String irideLogoutURLPA;
  private String irideLogoutURLSP;
  private String urlRitornoGISSP;
  private String urlStartApplicationSP;
  private String VBCC;
  private String BOBA;
  private String assessorato;
  private String direzione;
  private String settore;
  private String labAgr;
  private String labConsegna2;
  private String partitaIVALab;
  private String GFEP;
  private String TXT2;
  private String TXT5;
  private String TXT6;
  private String metalliPesantiScontoNumero;
  private String metalliPesantiScontoPercentuale;
  private String SIPW;
  private String SIUS;
  private String tokenApiManagerEndpoint;
  private String tokenApiManagerKey;
  private String tokenApiManagerSecret;
  private String tokenApiManagerXAuth;
  private String sigwgssrvSigwgssrvEndpoint;
  private String sigwgssrvSigwgssrvWsdl;
  private String agripagopaIsPa;
  private String agripagopaIsNotPa;
  private String agripagopaPageReferral;
  private String agripagopaAnnulla;
  private String agripagopaIsSpid;
  private String agripagopaWSDL;
  
  private AnagServiceCSIInterface anagServiceCSIInterface;

  public BeanParametri() {  }

  public void setMailMittente(String newMailMittente)
  {
    mailMittente = newMailMittente;
    CuneoLogger.debug(this,"FO mailMittente "+this.mailMittente);
  }

  public String getMailMittente()
  {
    return mailMittente;
  }

  public String[] getMailDestinatario()
  {
    return mailDestinatario;
  }

  public void setMailDestinatario1(String mailDestinatari)
  {
    CuneoLogger.debug(this,"FO mailDestinatari "+mailDestinatari);
    StringTokenizer sToken = new StringTokenizer(mailDestinatari,";");
    int numToken=sToken.countTokens();
    mailDestinatario = new String[numToken];
    for (int i=0;i< mailDestinatario.length; i++ )
    {
       mailDestinatario[i]=sToken.nextToken();
       CuneoLogger.debug(this,"FO mailDestinatario["+i+"] "+this.mailDestinatario[i]);
    }
  }

  public void setHostServerPosta(String newHostServerPosta)
  {
    hostServerPosta = newHostServerPosta;
    CuneoLogger.debug(this,"FO hostServerPosta "+this.hostServerPosta);
  }

  public String getHostServerPosta()
  {
    return hostServerPosta;
  }

  public void setNomeServerWebApplication(String newNomeServerWebApplication)
  {
    nomeServerWebApplication = newNomeServerWebApplication;
    CuneoLogger.debug(this,"FO nomeServerWebApplication "+this.nomeServerWebApplication);
  }

  public String getNomeServerWebApplication()
  {
    return nomeServerWebApplication;
  }

  public void setMaxRecordXPagina(String newMaxRecordXPagina)
  {
    try
    {
      maxRecordXPagina = Integer.parseInt(newMaxRecordXPagina);
      CuneoLogger.debug(this,"FO maxRecordXPagina "+this.maxRecordXPagina);
    }
    catch(Exception e)
    {
      maxRecordXPagina=15;
      CuneoLogger.debug(this,"FO maxRecordXPagina exception 15");
    }
  }

  public int getMaxRecordXPagina()
  {
    return maxRecordXPagina;
  }
  
  public void setMaxRecordXPagina(int maxRecordXPagina) {
		this.maxRecordXPagina = maxRecordXPagina;
	}

  public int getMaxPagamentiXPagina() {
	return maxPagamentiXPagina;
}

public void setMaxPagamentiXPagina(String maxPagamentiXPagina) {
	try{
		this.maxPagamentiXPagina = Integer.parseInt(maxPagamentiXPagina);
      CuneoLogger.debug(this,"FO maxPagamentiXPagina "+this.maxPagamentiXPagina);
    }catch(Exception e){
    	this.maxPagamentiXPagina=15;
      CuneoLogger.debug(this,"FO maxPagamentiXPagina exception 15");
    }
}

public void setMaxPagamentiXPagina(int maxPagamentiXPagina) {
	this.maxPagamentiXPagina = maxPagamentiXPagina;
}

public void setUrlHomePageApplication(String urlHomePageApplication)
  {
    this.urlHomePageApplication = urlHomePageApplication;
    CuneoLogger.debug(this,"FO urlHomePageApplication "+this.urlHomePageApplication);
  }
  public String getUrlHomePageApplication()
  {
    return urlHomePageApplication;
  }
  public void setPassPhrase(String passPhrase)
  {
    this.passPhrase = passPhrase;
    CuneoLogger.debug(this,"FO passPhrase "+this.passPhrase);
  }
  public String getPassPhrase()
  {
    return passPhrase;
  }
  public void setUrlGIS(String urlGIS)
  {
    this.urlGIS = urlGIS;
    CuneoLogger.debug(this,"FO urlGIS "+this.urlGIS);
  }
  public String getUrlGIS()
  {
    return urlGIS;
  }
  public String getUrlIride()
  {
    return urlIride;
  }
  public void setUrlIride(String urlIride)
  {
    this.urlIride = urlIride;
    CuneoLogger.debug(this,"FO urlIride "+this.urlIride);
  }
  public void setNomeServerGIS(String nomeServerGIS)
  {
    this.nomeServerGIS = nomeServerGIS;
    CuneoLogger.debug(this,"FO nomeServerGIS "+this.nomeServerGIS);
  }
  public String getNomeServerGIS()
  {
    return nomeServerGIS;
  }
  public void setNomeComandoGIS(String nomeComandoGIS)
  {
    this.nomeComandoGIS = nomeComandoGIS;
    CuneoLogger.debug(this,"FO nomeComandoGIS "+this.nomeComandoGIS);
  }
  public String getNomeComandoGIS()
  {
    return nomeComandoGIS;
  }
  public void setUrlRitornoGISPA(String urlRitornoGISPA)
  {
    this.urlRitornoGISPA = urlRitornoGISPA;
    CuneoLogger.debug(this,"FO urlRitornoGISPA "+this.urlRitornoGISPA);
  }
  public String getUrlRitornoGISPA()
  {
    return urlRitornoGISPA;
  }
  public void setUrlStartApplicationPA(String urlStartApplicationPA)
  {
    this.urlStartApplicationPA = urlStartApplicationPA;
    CuneoLogger.debug(this,"FO urlStartApplicationPA "+this.urlStartApplicationPA);
  }
  public String getUrlStartApplicationPA()
  {
    return urlStartApplicationPA;
  }
  public void setIrideLogoutURLPA(String irideLogoutURLPA)
  {
    this.irideLogoutURLPA = irideLogoutURLPA;
    CuneoLogger.debug(this,"FO irideLogoutURLPA "+this.irideLogoutURLPA);
  }
  public String getIrideLogoutURLPA()
  {
    return irideLogoutURLPA;
  }
  public void setIrideLogoutURLSP(String irideLogoutURLSP)
  {
    this.irideLogoutURLSP = irideLogoutURLSP;
    CuneoLogger.debug(this,"FO irideLogoutURLSP "+this.irideLogoutURLSP);
  }
  public String getIrideLogoutURLSP()
  {
    return irideLogoutURLSP;
  }
  public void setUrlRitornoGISSP(String urlRitornoGISSP)
  {
    this.urlRitornoGISSP = urlRitornoGISSP;
    CuneoLogger.debug(this,"FO urlRitornoGISSP "+this.urlRitornoGISSP);
  }
  public String getUrlRitornoGISSP()
  {
    return urlRitornoGISSP;
  }
  public void setUrlStartApplicationSP(String urlStartApplicationSP)
  {
    this.urlStartApplicationSP = urlStartApplicationSP;
    CuneoLogger.debug(this,"FO urlStartApplicationSP "+this.urlStartApplicationSP);
  }
  public String getUrlStartApplicationSP()
  {
    return urlStartApplicationSP;
  }

  public String getVBCC()
  {
    return VBCC;
  }
  public void setVBCC(String VBCC)
  {
    this.VBCC = VBCC;
  }
  public String getBOBA()
  {
    return BOBA;
  }
  public void setBOBA(String BOBA)
  {
    this.BOBA = BOBA;
  }
  public String getAssessorato()
  {
    return assessorato;
  }
  public void setAssessorato(String assessorato)
  {
    this.assessorato = assessorato;
  }
  public String getDirezione()
  {
    return direzione;
  }
  public void setDirezione(String direzione)
  {
    this.direzione = direzione;
  }
  public String getSettore()
  {
    return settore;
  }
  public void setSettore(String settore)
  {
    this.settore = settore;
  }
  public String getLabAgr()
  {
    return labAgr;
  }
  public void setLabAgr(String labAgr)
  {
    this.labAgr = labAgr;
  }
  public String getLabConsegna2()
  {
    return labConsegna2;
  }
  public void setLabConsegna2(String labConsegna2)
  {
    this.labConsegna2 = labConsegna2;
  }
  public String getPartitaIVALab()
  {
    return partitaIVALab;
  }
  public void setPartitaIVALab(String partitaIVALab)
  {
    this.partitaIVALab = partitaIVALab;
  }
  public String getGFEP()
  {
    return GFEP;
  }
  public void setGFEP(String GFEP)
  {
    this.GFEP = GFEP;
  }
  public String getTXT2()
  {
    return TXT2;
  }
  public void setTXT2(String TXT2)
  {
    this.TXT2 = TXT2;
  }
  public String getTXT5()
  {
    return TXT5;
  }
  public void setTXT5(String TXT5)
  {
    this.TXT5 = TXT5;
  }
  public String getTXT6()
  {
    return TXT6;
  }
  public void setTXT6(String TXT6)
  {
    this.TXT6 = TXT6;
  }
	public String getMetalliPesantiScontoNumero()
	{
		return metalliPesantiScontoNumero;
	}
	
	public void setMetalliPesantiScontoNumero(String metalliPesantiScontoNumero)
	{
		this.metalliPesantiScontoNumero = metalliPesantiScontoNumero;
	}
	
	public String getMetalliPesantiScontoPercentuale()
	{
		return metalliPesantiScontoPercentuale;
	}
	
	public void setMetalliPesantiScontoPercentuale(String metalliPesantiScontoPercentuale)
	{
		this.metalliPesantiScontoPercentuale = metalliPesantiScontoPercentuale;
	}

  public String getDicituraIntroduttivaGIS()
  {
    return dicituraIntroduttivaGIS;
  }

  public void setDicituraIntroduttivaGIS(String dicituraIntroduttivaGIS)
  {
    this.dicituraIntroduttivaGIS = dicituraIntroduttivaGIS;
  }

	
	public AnagServiceCSIInterface getAnagServiceCSIInterface()
	{
		return anagServiceCSIInterface;
	}
	
	public void setAnagServiceCSIInterface(AnagServiceCSIInterface anagServiceCSIInterface)
	{
		this.anagServiceCSIInterface = anagServiceCSIInterface;
	}
	
	public String getSIPW() {
		return SIPW;
	}
	
	public void setSIPW(String SIPW) {
		this.SIPW = SIPW;
	}
	
	public String getSIUS() {
		return SIUS;
	}
	
	public void setSIUS(String SIUS) {
		this.SIUS = SIUS;
	}
	
	

	public String getTokenApiManagerEndpoint()
  {
    return tokenApiManagerEndpoint;
  }

  public void setTokenApiManagerEndpoint(String tokenApiManagerEndpoint)
  {
    this.tokenApiManagerEndpoint = tokenApiManagerEndpoint;
  }

  public String getTokenApiManagerKey()
  {
    return tokenApiManagerKey;
  }

  public void setTokenApiManagerKey(String tokenApiManagerKey)
  {
    this.tokenApiManagerKey = tokenApiManagerKey;
  }

  public String getTokenApiManagerSecret()
  {
    return tokenApiManagerSecret;
  }

  public void setTokenApiManagerSecret(String tokenApiManagerSecret)
  {
    this.tokenApiManagerSecret = tokenApiManagerSecret;
  }

  public String getTokenApiManagerXAuth()
  {
    return tokenApiManagerXAuth;
  }

  public void setTokenApiManagerXAuth(String tokenApiManagerXAuth)
  {
    this.tokenApiManagerXAuth = tokenApiManagerXAuth;
  }

  public String getSigwgssrvSigwgssrvEndpoint()
  {
    return sigwgssrvSigwgssrvEndpoint;
  }

  public void setSigwgssrvSigwgssrvEndpoint(String sigwgssrvSigwgssrvEndpoint)
  {
    this.sigwgssrvSigwgssrvEndpoint = sigwgssrvSigwgssrvEndpoint;
  }

  public String getSigwgssrvSigwgssrvWsdl()
  {
    return sigwgssrvSigwgssrvWsdl;
  }

  public void setSigwgssrvSigwgssrvWsdl(String sigwgssrvSigwgssrvWsdl)
  {
    this.sigwgssrvSigwgssrvWsdl = sigwgssrvSigwgssrvWsdl;
  }

	public void logParametri()
	{
		CuneoLogger.debug(this, "BOBA: "+getBOBA());
		CuneoLogger.debug(this, "VBCC: "+getVBCC());
		CuneoLogger.debug(this, "TXAS: "+getAssessorato());
		CuneoLogger.debug(this, "TXDI: "+getDirezione());
		CuneoLogger.debug(this, "TXLA: "+getLabAgr());
		CuneoLogger.debug(this, "TXSF: "+getSettore());
		CuneoLogger.debug(this, "TXLC: "+getLabConsegna2());
		CuneoLogger.debug(this, "TXPI: "+getPartitaIVALab());
		CuneoLogger.debug(this, "GFEP: "+getGFEP());
		CuneoLogger.debug(this, "metalliPesantiScontoNumero: "+ getMetalliPesantiScontoNumero());
		CuneoLogger.debug(this, "metalliPesantiScontoPercentuale: "+ getMetalliPesantiScontoPercentuale());
		CuneoLogger.debug(this, "SIPW: "+ getSIPW());
		CuneoLogger.debug(this, "SIUS: "+ getSIUS());
		CuneoLogger.debug(this, "dicituraIntroduttivaGIS: "+ getDicituraIntroduttivaGIS());
	}

	public String getAgripagopaIsPa() {
		return agripagopaIsPa;
	}

	public void setAgripagopaIsPa(String agripagopaIsPa) {
		this.agripagopaIsPa = agripagopaIsPa;
		CuneoLogger.debug(this,"FO agripagopaIsPa "+this.agripagopaIsPa);
	}

	public String getAgripagopaIsNotPa() {
		return agripagopaIsNotPa;
	}

	public void setAgripagopaIsNotPa(String agripagopaIsNotPa) {
		this.agripagopaIsNotPa = agripagopaIsNotPa;
		CuneoLogger.debug(this,"FO agripagopaIsNotPa "+this.agripagopaIsNotPa);
	}

	public String getAgripagopaPageReferral() {
		return agripagopaPageReferral;
	}

	public void setAgripagopaPageReferral(String agripagopaPageReferral) {
		this.agripagopaPageReferral = agripagopaPageReferral;
		CuneoLogger.debug(this,"FO agripagopaPageReferral "+this.agripagopaPageReferral);
	}

	public String getAgripagopaAnnulla() {
		return agripagopaAnnulla;
	}

	public void setAgripagopaAnnulla(String agripagopaAnnulla) {
		this.agripagopaAnnulla = agripagopaAnnulla;
		CuneoLogger.debug(this,"FO agripagopaAnnulla "+this.agripagopaAnnulla);
	}

	public String getAgripagopaIsSpid() {
		return agripagopaIsSpid;
	}

	public void setAgripagopaIsSpid(String agripagopaIsSpid) {
		this.agripagopaIsSpid = agripagopaIsSpid;
		CuneoLogger.debug(this,"FO agripagopaIsSpid "+this.agripagopaIsSpid);
	}

	public String getAgripagopaWSDL() {
		return agripagopaWSDL;
	}

	public void setAgripagopaWSDL(String agripagopaWSDL) {
		this.agripagopaWSDL = agripagopaWSDL;
		CuneoLogger.debug(this,"FO agripagopaWSDL "+this.agripagopaWSDL);
	}
}