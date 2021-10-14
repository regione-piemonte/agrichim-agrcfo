package it.csi.agrc.servlet;

import it.csi.agrc.Analisi;
import it.csi.agrc.BeanAnalisi;
import it.csi.agrc.BeanColtura;
import it.csi.agrc.BeanOrganizzazioniTecnico;
import it.csi.agrc.BeanParametri;
import it.csi.agrc.BeanProvincia;
import it.csi.agrc.BeanTipoCampione;
import it.csi.agrc.CodiciMisuraPsr;
import it.csi.agrc.Coltura;
import it.csi.agrc.OrganizzazioniTecnico;
import it.csi.agrc.Parametro;
import it.csi.agrc.Province;
import it.csi.agrc.TipoCampione;
import it.csi.csi.porte.InfoPortaDelegata;
import it.csi.csi.porte.proxy.PDProxy;
import it.csi.csi.util.xml.PDConfigReader;
import it.csi.cuneo.CuneoLogger;
import it.csi.cuneo.Mail;
import it.csi.cuneo.Utili;
import it.csi.solmr.interfaceCSI.anag.services.AnagServiceCSIInterface;

import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Vector;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.sql.DataSource;

/**
 * Title:        Agrichim - Front Office
 * Description:  Richiesta analisi chimiche su campioni biologici agrari
 * Copyright:    Copyright (c) 2003
 * Company:      CSI Piemonte - Progettazione e Sviluppo - Cuneo
 * @author Michele Piantà, Piergiorgio Chiriotti
 * @version 1.0.0
 */


public class ServletCaricaParametri extends HttpServlet
{
  private static final long serialVersionUID = -4780407775143117853L;

  /**Initialize global variables*/
  public void init(ServletConfig config) throws ServletException
  {
    BeanParametri beanParametriApplication=null;
    Object dataSource = null;
    
    try
    {
      super.init(config);
      beanParametriApplication=new BeanParametri();
      ServletContext context=config.getServletContext();
      beanParametriApplication.setPassPhrase(
          context.getInitParameter("passPhrase"));
      beanParametriApplication.setMailDestinatario1(
          context.getInitParameter("emailErroreDest"));
      beanParametriApplication.setMailMittente(
          context.getInitParameter("emailErroreMitt"));
      beanParametriApplication.setHostServerPosta(
          context.getInitParameter("hostServerPosta"));
      beanParametriApplication.setNomeServerWebApplication(
          context.getInitParameter("nomeServer"));
      beanParametriApplication.setMaxRecordXPagina(
          context.getInitParameter("maxRecordXPagina"));
      beanParametriApplication.setMaxPagamentiXPagina(
              context.getInitParameter("maxPagamentiXPagina"));
      beanParametriApplication.setUrlStartApplicationSP(
          context.getInitParameter("urlStartApplicationSP"));
      beanParametriApplication.setUrlStartApplicationPA(
          context.getInitParameter("urlStartApplicationPA"));
      beanParametriApplication.setUrlHomePageApplication(
          context.getInitParameter("urlHomePageApplication"));
      beanParametriApplication.setUrlGIS(
          context.getInitParameter("urlGIS"));
      beanParametriApplication.setUrlIride(
          context.getInitParameter("urlIride"));
      beanParametriApplication.setNomeServerGIS(
          context.getInitParameter("nomeServerGIS"));
      beanParametriApplication.setNomeComandoGIS(
          context.getInitParameter("nomeComandoGIS"));
      beanParametriApplication.setUrlRitornoGISSP(
          context.getInitParameter("urlRitornoGISSP"));
      beanParametriApplication.setUrlRitornoGISPA(
          context.getInitParameter("urlRitornoGISPA"));
      beanParametriApplication.setIrideLogoutURLSP(
          context.getInitParameter("irideLogoutURLSP"));
      beanParametriApplication.setIrideLogoutURLPA(
          context.getInitParameter("irideLogoutURLPA"));
      beanParametriApplication.setTokenApiManagerEndpoint(
          context.getInitParameter("tokenApiManagerEndpoint"));
      beanParametriApplication.setTokenApiManagerKey(
          context.getInitParameter("tokenApiManagerKey"));
      beanParametriApplication.setTokenApiManagerSecret(
          context.getInitParameter("tokenApiManagerSecret"));
      beanParametriApplication.setTokenApiManagerXAuth(
          context.getInitParameter("tokenApiManagerXAuth"));
      beanParametriApplication.setSigwgssrvSigwgssrvEndpoint(
          context.getInitParameter("sigwgssrvSigwgssrvEndpoint"));
      beanParametriApplication.setSigwgssrvSigwgssrvWsdl(
          context.getInitParameter("sigwgssrvSigwgssrvWsdl"));
      //agripagopa
      beanParametriApplication.setAgripagopaIsPa(
              context.getInitParameter("agripagopaIsPa"));
      beanParametriApplication.setAgripagopaIsNotPa(
              context.getInitParameter("agripagopaIsNotPa"));
      beanParametriApplication.setAgripagopaIsSpid(
              context.getInitParameter("agripagopaIsSpid"));
      beanParametriApplication.setAgripagopaPageReferral(
              context.getInitParameter("agripagopaPageReferral"));
      beanParametriApplication.setAgripagopaAnnulla(
              context.getInitParameter("agripagopaAnnulla"));
      beanParametriApplication.setAgripagopaWSDL(
              context.getInitParameter("agripagopaWSDL"));

      //Istanza servizi anagrafe
      InfoPortaDelegata pdAnagService = PDConfigReader.read(context.getResourceAsStream("/WEB-INF/pdAnagService.xml"));
      AnagServiceCSIInterface anagServiceCSIInterface = (AnagServiceCSIInterface) PDProxy.newInstance(pdAnagService);
      beanParametriApplication.setAnagServiceCSIInterface(anagServiceCSIInterface);     

      /**
       * Rimuovo gli eventuali bean di applicazione dovuto ad eventuali problemi
       **/
      context.removeAttribute("beanParametriApplication");
      context.removeAttribute("poolBean");
      context.removeAttribute("dataSourceBean");


      if (!Utili.POOLMAN)
      {
        CuneoLogger.debug(this, "L'applicazione utilizza il Connection Pool di WebLogic");
        DataSource dataSourceBean=null;
        javax.naming.Context contextNaming = new javax.naming.InitialContext();
        String jndiName = (String)context.getInitParameter("datasource.jndiname");
        dataSourceBean = (DataSource) contextNaming.lookup(jndiName);
        context.setAttribute("dataSourceBean",dataSourceBean);
        dataSource=dataSourceBean;
      }
      else
      {
        CuneoLogger.debug(this, "L'applicazione utilizza Poolman+Tram");
        it.csi.jsf.web.pool.BeanGenericPool bean=new it.csi.jsf.web.pool.BeanGenericPool();
        context.setAttribute("poolBean",bean);
        dataSource=bean;
      }
      caricaParametri(dataSource,context,beanParametriApplication);


      /**
       * Imposto i bean di applicazione
       **/
      context.setAttribute("beanParametriApplication",beanParametriApplication);


      caricaProvincia(dataSource,context);
      caricaOrganizzazioneTecnico(dataSource,context);
      caricaTipoCampione(dataSource,context);
      caricaColtura(dataSource,context);
      caricaAnalisi(dataSource,context);
      
      //Istanzio servizi sigmater
      /*InfoPortaDelegata pdSigmater = PDConfigReader.read(context.getResourceAsStream("/WEB-INF/defpd_sigwgssrv.xml"));
      InfoPortaDelegata info = pdSigmater.getPlugins()[0];
      info.getProperties().put("csi.auth.basic",beanParametriApplication.getSIUS()+ "/" + beanParametriApplication.getSIPW());
      beanParametriApplication.setSigwgssrvSrv((SigwgssrvSrv) PDProxy.newInstance(pdSigmater));*/
    }
    catch(Exception e)
    {
        Mail mail=new Mail();
        mail.setEMailSender(beanParametriApplication.getMailMittente());
        mail.setEMailReceiver(beanParametriApplication.getMailDestinatario());
        mail.setHost(beanParametriApplication.getHostServerPosta());
        mail.preparaMail("["+beanParametriApplication.getNomeServerWebApplication()+"]   "+e.getMessage(),"Problemi nel servlet","",e.toString());
        CuneoLogger.debug(this,"Servlet - invio mail: "+mail.inviaMail());
        e.printStackTrace();
    }
  }

  static private void caricaProvincia(Object dataSource,
                                      ServletContext context)
  throws Exception, SQLException
  {
    context.removeAttribute("beanProvincia");
    Vector id=new Vector();
    Vector sigla=new Vector();
    Province province=new Province();
    BeanProvincia beanProvincia=new BeanProvincia();
    province.setDataSource(dataSource);
    province.fill(id,sigla);
    beanProvincia.setIdProvincia(id);
    beanProvincia.setSiglaProvincia(sigla);
    context.setAttribute("beanProvincia",beanProvincia);
  }

  static private void caricaParametri(Object dataSource,
                                      ServletContext context,
                                      BeanParametri beanParametri)
  throws Exception, SQLException
  {
    Parametro parametro=new Parametro();
    parametro.setDataSource(dataSource);
    parametro.leggiParametri(beanParametri);
    beanParametri.logParametri();
  }





  static private void caricaOrganizzazioneTecnico(Object dataSource,
                                                  ServletContext context)
  throws Exception, SQLException
  {
    context.removeAttribute("beanOrgTecnico");
    Vector cod=new Vector();
    Vector desc=new Vector();
    OrganizzazioniTecnico orgTecnico=new OrganizzazioniTecnico();
    orgTecnico.setDataSource(dataSource);
    orgTecnico.getTipiOrganizzazione(cod,desc);
    BeanOrganizzazioniTecnico beanOrgTecnico=new BeanOrganizzazioniTecnico();
    beanOrgTecnico.setCod(cod);
    beanOrgTecnico.setDesc(desc);
    context.setAttribute("beanOrgTecnico",beanOrgTecnico);
  }

  static private void caricaTipoCampione(Object dataSource,
                                         ServletContext context)
  throws Exception, SQLException
  {
    context.removeAttribute("beanTipoCampione");
    Vector cod=new Vector();
    Vector desc=new Vector();
    TipoCampione tipoCampione=new TipoCampione();
    BeanTipoCampione beantipoCampione=new BeanTipoCampione();
    tipoCampione.setDataSource(dataSource);
    tipoCampione.selectCodDesc(TipoCampione.MATERIALE,cod,desc);
    beantipoCampione.setCodMateriale(cod);
    beantipoCampione.setDescMateriale(desc);

    cod.clear();
    desc.clear();
    tipoCampione.selectCodDesc(TipoCampione.LABORATORIO,cod,desc);
    beantipoCampione.setCodLaboratorio(cod);
    beantipoCampione.setDescLaboratorio(desc);

    cod.clear();
    desc.clear();
    tipoCampione.selectCodDesc(TipoCampione.MODALITA,cod,desc);
    beantipoCampione.setCodModalita(cod);
    beantipoCampione.setDescModalita(desc);
    
    //Lettura codici misura PSR
    CodiciMisuraPsr codiciMisuraPsr = new CodiciMisuraPsr();
    codiciMisuraPsr.setDataSource(dataSource);
    codiciMisuraPsr.fill();   
    beantipoCampione.setCodiciMisuraPsr(codiciMisuraPsr);

    context.setAttribute("beanTipoCampione", beantipoCampione);
  }

  static private void caricaColtura(Object dataSource,
                                    ServletContext context)
  throws Exception, SQLException
  {
    context.removeAttribute("beanColtura");
    Vector cod=new Vector();
    Vector desc=new Vector();
    Coltura coltura=new Coltura();
    BeanColtura beanColtura=new BeanColtura();
    coltura.setDataSource(dataSource);
    coltura.selectCodDesc(Coltura.IMPOSTA_QUERY_SUPERFICIE,cod,desc);
    beanColtura.setCodSuperficie(cod);
    beanColtura.setDescSuperficie(desc);

    cod.clear();
    desc.clear();
    coltura.selectCodDesc(Coltura.IMPOSTA_QUERY_ESPOSIZIONE,cod,desc);
    beanColtura.setCodEsposizione(cod);
    beanColtura.setDescEsposizione(desc);

    cod.clear();
    desc.clear();
    coltura.selectCodDesc(Coltura.IMPOSTA_QUERY_CONCIMAZIONE_ORGANICA,cod,desc);
    beanColtura.setCodConcimazioneOrganica(cod);
    beanColtura.setDescConcimazioneOrganica(desc);

    cod.clear();
    desc.clear();
    coltura.selectCodDesc(Coltura.IMPOSTA_QUERY_CONCIMAZIONE,cod,desc);
    beanColtura.setCodConcimazione(cod);
    beanColtura.setDescConcimazione(desc);

    cod.clear();
    desc.clear();
    coltura.selectCodDesc(Coltura.IMPOSTA_QUERY_PRODUTTIVITA,cod,desc);
    beanColtura.setCodProduttivita(cod);
    beanColtura.setDescProduttivita(desc);

    cod.clear();
    desc.clear();
    coltura.selectCodDesc(Coltura.IMPOSTA_QUERY_PROFONDITA_PRELIEVO,cod,desc);
    beanColtura.setCodProfonditaPrelievo(cod);
    beanColtura.setDescProfonditaPrelievo(desc);

    cod.clear();
    desc.clear();
    coltura.selectCodDesc(Coltura.IMPOSTA_QUERY_COLTURA,cod,desc);
    beanColtura.setCodColtura(cod);
    beanColtura.setDescColtura(desc);

    cod.clear();
    desc.clear();
    coltura.selectCodDesc(Coltura.IMPOSTA_QUERY_LAVORAZIONE_TERRENO,cod,desc);
    beanColtura.setCodLavorazioneTerreno(cod);
    beanColtura.setDescLavorazioneTerreno(desc);

    cod.clear();
    desc.clear();
    coltura.selectCodDesc(Coltura.IMPOSTA_QUERY_IRRIGAZIONE,cod,desc);
    beanColtura.setCodIrrigazione(cod);
    beanColtura.setDescIrrigazione(desc);

    cod.clear();
    desc.clear();
    coltura.selectCodDesc(Coltura.IMPOSTA_QUERY_MODALITA_COLTIVAZIONE,cod,desc);
    beanColtura.setCodModalitaColtivazione(cod);
    beanColtura.setDescModalitaColtivazione(desc);


    context.setAttribute("beanColtura",beanColtura);
  }

  static private void caricaAnalisi(Object dataSource,
                                    ServletContext context)
  throws Exception, SQLException
  {
    context.removeAttribute("beanAnalisi");
    Hashtable analisiTipo=new Hashtable(22,0.95f);
    Hashtable costoAnalisi=new Hashtable(220,0.9f);
    Analisi analisi=new Analisi();
    analisi.setDataSource(dataSource);
    analisi.selectTipiAnalisi(analisiTipo);
    analisi.selectCostoAnalisi(costoAnalisi);
    BeanAnalisi beanAnalisi=new BeanAnalisi();
    beanAnalisi.setAnalisi(analisiTipo);
    beanAnalisi.setCostoAnalisi(costoAnalisi);
    beanAnalisi.setImportoSpedizione(analisi.getCostoSpedizione());
    context.setAttribute("beanAnalisi",beanAnalisi);
  }


}
