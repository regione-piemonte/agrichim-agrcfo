// Codici unicode per le lettere accentate da inserire nei PDF
// \u00E0 = à
// \u00E8 = è

package it.csi.agrc.servlet;

//import java.io.*;
import inetsoft.report.StyleConstants;
//import java.util.*;
import inetsoft.report.TabularSheet;
//import inetsoft.report.io.*;
import inetsoft.report.lens.DefaultTableLens;
import inetsoft.report.lens.DefaultTextLens;
import inetsoft.report.painter.HTMLPresenter;
//import inetsoft.report.internal.*;
import inetsoft.report.painter.ImagePainter;
import it.csi.agrc.Analisi;
import it.csi.agrc.Autenticazione;
import it.csi.agrc.Azoto;
import it.csi.agrc.BeanParametri;
import it.csi.agrc.Calcimetria;
import it.csi.agrc.CampioneTerrenoDati;
import it.csi.agrc.ComplessoScambio;
import it.csi.agrc.ConducibilitaSalinita;
import it.csi.agrc.DatiAppezzamento;
import it.csi.agrc.EtichettaCampione;
import it.csi.agrc.FosforoMetodoOLSEN;
import it.csi.agrc.GranulometriaA4Frazioni;
import it.csi.agrc.GranulometriaA5Frazioni;
import it.csi.agrc.GranulometriaMetodoBojoucos;
import it.csi.agrc.GranulometriaStandard;
import it.csi.agrc.Laboratorio;
import it.csi.agrc.MetalliPesanti;
import it.csi.agrc.MicroelementiMetodoDTPA;
import it.csi.agrc.ReazioneSuolo;
import it.csi.agrc.Responsabile;
import it.csi.agrc.SostanzaOrganica;
import it.csi.agrc.TracciabilitaPOP;
import it.csi.agrc.UmiditaCampione;
import it.csi.cuneo.ReportUtils;
import it.csi.cuneo.Utili;
import it.csi.cuneo.servlet.PdfServlet;

import java.awt.Font;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * <p>Title: Agrichim - Front Office</p>
 * <p>Description: Richiesta analisi chimiche su campioni biologici agrari</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: CSI Piemonte - Progettazione e Sviluppo - Cuneo</p>
 * @author Michele Piantà, Piergiorgio Chiriotti
 * @version 1.0.0
 */

public class PdfRisultatoAnalisiTER extends PdfServletAgrc
{
  private static final long serialVersionUID = 2569306552984120638L;

  HttpSession session;
  ServletContext context;
  Autenticazione aut;
  Object dataSource;

  private static final int INDICE_COLONNA_TIPO_ANALISI = 0;
  private static final int INDICE_COLONNA_VALORE_RISCONTRATO = 1;
  private static final int INDICE_COLONNA_VALORE_PREDEFINITO = 2; // @@TODO@@
  private static final int INDICE_COLONNA_UNITA_MISURA = 3;
  private static final int INDICE_COLONNA_NOTE = 4;
  private static final int INDICE_COLONNA_METODO = 5;
  
  public PdfRisultatoAnalisiTER()
  {
    this.setOutputName("risultatoAnalisiTER.pdf");
    this.setTemplateName("pdfRisultatoAnalisiTER.srt");
  }

  protected void stampaPdf(HttpServletRequest request,
  		TabularSheet xss)
      throws Exception
  {
    StringBuffer giudizio=new StringBuffer();
    boolean granulometria=false;
    String sGranulometria="";
    double argilla=0,sabbia=0,limo=0;
    session = request.getSession();
    aut = (Autenticazione)session.getAttribute("aut");
    controllaAut(aut);

    String idRichiesta=request.getParameter("idRichiesta");
    long lIdRichiesta;
    try
    {
      lIdRichiesta=Long.parseLong(idRichiesta);
    }
    catch(Exception e)
    {
      lIdRichiesta=0;
    }

    context=session.getServletContext();
    if (Utili.POOLMAN)
      dataSource=context.getAttribute("poolBean");
    else
      dataSource=context.getAttribute("dataSourceBean");

    //Footer
    ReportUtils.setFooter(xss);

    //Impostazione dei dati relativi alla testa del PDF: sono dati che si trovano
    //all'interno della tabella parametro e vengono precaricati all'avvio dell'applicativo
    // nel bean BeanParametri
    BeanParametri beanParametriApplication=(BeanParametri)context.getAttribute("beanParametriApplication");

  String direzione = "";
    
    if(beanParametriApplication.getDirezione()!=null)
    {
    	direzione = beanParametriApplication.getDirezione();
    }
    
    xss.setElement("tbxAssessorato",beanParametriApplication.getAssessorato()+"\n"+direzione);
   // xss.setElement("tbxAssessorato",beanParametriApplication.getAssessorato()+"\n"+beanParametriApplication.getDirezione());
    xss.setElement("tbxSettore",beanParametriApplication.getSettore()+"\n"+beanParametriApplication.getLabAgr());


    /**
     * Questo mi serve per tutti i dati del campione memorizzati nella tabella
     * ETICHETTA_CAMPIONE
     * */
    EtichettaCampione etichettaCampionePDF=new EtichettaCampione();
    etichettaCampionePDF.setDataSource(dataSource);
    etichettaCampionePDF.setAut(aut);
    etichettaCampionePDF.setIdRichiesta(idRichiesta);
    etichettaCampionePDF.selectPdf(idRichiesta);
    etichettaCampionePDF.setIdRichiesta(idRichiesta);

    /**
     * Questo mi serve per sapere le analisi che sono state richieste
     * */
    Analisi analisi=new Analisi();
    analisi.setDataSource(dataSource);
    analisi.setAut(aut);
    analisi.setIdRichiesta(lIdRichiesta);
    analisi.select();

    /**
     * Questo mi serve per leggere la data di emissione del referto
     * */
    TracciabilitaPOP tracciabilitaPOP=new TracciabilitaPOP();
    tracciabilitaPOP.setDataSource(dataSource);
    tracciabilitaPOP.setAut(aut);
    tracciabilitaPOP.select(idRichiesta);

    /**
     * Questo mi serve per leggere i dati del terreno in cui è stato prelevato il
     * campione
     * */
    DatiAppezzamento datiAppezzamento= new DatiAppezzamento();
    datiAppezzamento.setDataSource(dataSource);
    datiAppezzamento.setAut(aut);
    datiAppezzamento.selectForPdf(idRichiesta,true);

    CampioneTerrenoDati campioneTerrenoDati= new CampioneTerrenoDati();
    campioneTerrenoDati.setDataSource(dataSource);
    campioneTerrenoDati.setAut(aut);
    campioneTerrenoDati.select(idRichiesta);

    int numRigheTblAnalisi=0;

    /**
     * Imposto tutte le tabelle che mi permettono di leggere i record
     * relativi alla analisi effettuate
     * */
    UmiditaCampione umiditaCampione= new UmiditaCampione();
    umiditaCampione.setDataSource(dataSource);
    umiditaCampione.setAut(aut);
    umiditaCampione.setIdRichiesta(lIdRichiesta);
    boolean bUmiditaCampione=umiditaCampione.select();

    GranulometriaA4Frazioni granulometriaA4Frazioni= new GranulometriaA4Frazioni();
    granulometriaA4Frazioni.setDataSource(dataSource);
    granulometriaA4Frazioni.setAut(aut);
    granulometriaA4Frazioni.setIdRichiesta(lIdRichiesta);
    boolean bGranulometriaA4Frazioni=granulometriaA4Frazioni.select();

    GranulometriaA5Frazioni granulometriaA5Frazioni= new GranulometriaA5Frazioni();
    granulometriaA5Frazioni.setDataSource(dataSource);
    granulometriaA5Frazioni.setAut(aut);
    granulometriaA5Frazioni.setIdRichiesta(lIdRichiesta);
    boolean bGranulometriaA5Frazioni=granulometriaA5Frazioni.select();

    GranulometriaStandard granulometriaStandard= new GranulometriaStandard();
    granulometriaStandard.setDataSource(dataSource);
    granulometriaStandard.setAut(aut);
    granulometriaStandard.setIdRichiesta(lIdRichiesta);
    boolean bGranulometriaStandard=granulometriaStandard.select();

    GranulometriaMetodoBojoucos granulometriaMetodoBojoucos= new GranulometriaMetodoBojoucos();
    granulometriaMetodoBojoucos.setDataSource(dataSource);
    granulometriaMetodoBojoucos.setAut(aut);
    granulometriaMetodoBojoucos.setIdRichiesta(lIdRichiesta);
    boolean bGranulometriaMetodoBojoucos=granulometriaMetodoBojoucos.select();



    ReazioneSuolo reazioneSuolo= new ReazioneSuolo();
    reazioneSuolo.setDataSource(dataSource);
    reazioneSuolo.setAut(aut);
    reazioneSuolo.setIdRichiesta(lIdRichiesta);
    boolean bReazioneSuolo=reazioneSuolo.select();

    Calcimetria calcimetria= new Calcimetria();
    calcimetria.setDataSource(dataSource);
    calcimetria.setAut(aut);
    calcimetria.setIdRichiesta(lIdRichiesta);
    boolean bCalcimetria=calcimetria.select();

    SostanzaOrganica sostanzaOrganica= new SostanzaOrganica();
    sostanzaOrganica.setDataSource(dataSource);
    sostanzaOrganica.setAut(aut);
    sostanzaOrganica.setIdRichiesta(lIdRichiesta);
    boolean bSostanzaOrganica=sostanzaOrganica.select();

    Azoto azoto= new Azoto();
    azoto.setDataSource(dataSource);
    azoto.setAut(aut);
    azoto.setIdRichiesta(lIdRichiesta);
    boolean bAzoto=azoto.select();

    ComplessoScambio complessoScambio= new ComplessoScambio();
    complessoScambio.setDataSource(dataSource);
    complessoScambio.setAut(aut);
    complessoScambio.setIdRichiesta(lIdRichiesta);
    boolean bComplessoScambio=complessoScambio.select();

    FosforoMetodoOLSEN fosforoMetodoOLSEN= new FosforoMetodoOLSEN();
    fosforoMetodoOLSEN.setDataSource(dataSource);
    fosforoMetodoOLSEN.setAut(aut);
    fosforoMetodoOLSEN.setIdRichiesta(lIdRichiesta);
    boolean bFosforoMetodoOLSEN=fosforoMetodoOLSEN.select();

    MicroelementiMetodoDTPA microelementiMetodoDTPA= new MicroelementiMetodoDTPA();
    microelementiMetodoDTPA.setDataSource(dataSource);
    microelementiMetodoDTPA.setAut(aut);
    microelementiMetodoDTPA.setIdRichiesta(lIdRichiesta);
    boolean bMicroelementiMetodoDTPA=microelementiMetodoDTPA.select();

    //AGRICHIM-45
    //Metalli pesanti
    MetalliPesanti metalliPesanti = new MetalliPesanti();
    metalliPesanti.setDataSource(dataSource);
    metalliPesanti.setAut(aut);
    metalliPesanti.setIdRichiesta(lIdRichiesta);
    boolean bMetalliPesanti = metalliPesanti.select();    

    ConducibilitaSalinita conducibilitaSalinita= new ConducibilitaSalinita();
    conducibilitaSalinita.setDataSource(dataSource);
    conducibilitaSalinita.setAut(aut);
    conducibilitaSalinita.setIdRichiesta(lIdRichiesta);
    boolean bConducibilitaSalinita=conducibilitaSalinita.select();

    Laboratorio laboratorioAnalisi = new Laboratorio(dataSource, aut);

    /**
     * Utilizzato per la firma del referto
     * */
    Responsabile resp=new Responsabile();


    /***************************************************************
     * Header comune a tutte le pagine
     */
    String etichetta=null;
    laboratorioAnalisi.select(null,beanParametriApplication.getPartitaIVALab());

    etichetta="SEDE: "+laboratorioAnalisi.getIndirizzoPdf();
    laboratorioAnalisi.select(etichettaCampionePDF.getCodLabAnalisi(),beanParametriApplication.getPartitaIVALab());
    etichetta+="\nLABORATORIO DI ANALISI\n"+laboratorioAnalisi.getIndirizzoPdf();
    //etichetta+="\n"+beanParametriApplication.getLabConsegna2();
    if(beanParametriApplication.getLabConsegna2()!=null)
    {
      etichetta+="\n"+beanParametriApplication.getLabConsegna2();
    }
    xss.setElement("tbxIndirizzo",etichetta);


    //System.out.println("PdfRisultatoAnalisiTER.stampaPdf() - Inizio creazione pdf");
    ImagePainter logoRegione = new ImagePainter(this.getImage("logoRegione.gif"));
    xss.setElement("imgLogoRegione",logoRegione);

    DefaultTableLens tblCampione=new DefaultTableLens(xss.getTable("tblCampione"));
    for (int i=0;i<2;i++)
    {
      tblCampione.setFont (i,1,new Font("SansSerif",Font.BOLD,8));
      tblCampione.setFont(i,3,new Font("SansSerif",Font.BOLD,8));
      tblCampione.setFont(i,5,new Font("SansSerif",Font.BOLD,8));
    }
    for (int i=0;i<2;i++)
    {
      tblCampione.setColBorder(i,0,StyleConstants.NO_BORDER);
      tblCampione.setColBorder(i,2,StyleConstants.NO_BORDER);
      tblCampione.setColBorder(i,4,StyleConstants.NO_BORDER);
    }

    tblCampione.setColWidth(0,60);
    tblCampione.setColWidth(1,135);
    tblCampione.setColWidth(2,65);
    tblCampione.setColWidth(3,160);
    tblCampione.setColWidth(4,70);
    tblCampione.setColWidth(5,44);

    if (etichettaCampionePDF.getDescMateriale()!= null)
      tblCampione.setObject(0,1," "+etichettaCampionePDF.getDescMateriale());

    if (etichettaCampionePDF.getIdRichiesta()!= null)
      tblCampione.setObject(0,3," "+etichettaCampionePDF.getIdRichiesta());

    if (etichettaCampionePDF.getDataInserimentoRichiesta()!= null)
      tblCampione.setObject(0,5," "+etichettaCampionePDF.getDataInserimentoRichiesta());

    if (datiAppezzamento.getComuneAppezzamentoDescr()!= null)
      tblCampione.setObject(1,1," "+datiAppezzamento.getComuneAppezzamentoDescr());

    if (etichettaCampionePDF.getDescrizioneEtichetta()!= null)
      tblCampione.setObject(1,3," "+etichettaCampionePDF.getDescrizioneEtichetta());

    if (datiAppezzamento.getProfondita() != null)
      tblCampione.setObject(1,5," "+datiAppezzamento.getProfondita());

    xss.setElement("tblCampione", tblCampione);


    DefaultTextLens txtLaboratorio=new  DefaultTextLens();
    if (laboratorioAnalisi.getDescrizione()!=null)
    txtLaboratorio.setText(" "+laboratorioAnalisi.getDescrizione()+"  ");
    xss.setElement("txtLaboratorio", txtLaboratorio);

    // Patch 19/10/2004
    // Aggiunta di numerocampione/anno al pdf risultatoAnalisiTER
    xss.setElement("txtNCampAnno", etichettaCampionePDF.getNumeroCampione()+"/"+
                                   etichettaCampionePDF.getAnnoCampione());

    DefaultTableLens tblCommittente=new DefaultTableLens(xss.getTable("tblCommittente"));
    for (int i=0;i<4;i++)
    {
      tblCommittente.setFont (1,i,new Font("SansSerif",Font.BOLD,8));
      tblCommittente.setFont(3,i,new Font("SansSerif",Font.BOLD,8));
      tblCommittente.setFont(5,i,new Font("SansSerif",Font.BOLD,8));
    }
    for (int i=0;i<4;i++)
    {
      tblCommittente.setRowBorder(0,i,StyleConstants.NO_BORDER);
      tblCommittente.setRowBorder(2,i,StyleConstants.NO_BORDER);
      tblCommittente.setRowBorder(4,i,StyleConstants.NO_BORDER);
    }

    tblCommittente.setColWidth(0,130);
    tblCommittente.setColWidth(1,130);
    tblCommittente.setColWidth(2,215);
    tblCommittente.setColWidth(3,65);

    tblCommittente.setRowFont (1,new Font("SansSerif",Font.BOLD,8));
    tblCommittente.setRowFont (3,new Font("SansSerif",Font.BOLD,8));
    tblCommittente.setRowFont (5,new Font("SansSerif",Font.BOLD,8));

    String riga1[]=new String[4];
    String riga2[]=new String[4];
    String riga3[]=new String[2];
    int riga=1;
    /*
    if (etichettaCampionePDF.selectPerPDFRisultatoAnalisi(true,riga1,riga2,riga3,etichettaCampionePDF.getCodiceMateriale()))
    {
      tblCommittente.setObject(riga,0,riga1[0]!=null?riga1[0]:" ");
      tblCommittente.setObject(riga,1,riga1[1]!=null?riga1[1]:" ");
      tblCommittente.setObject(riga,2,riga1[2]!=null?riga1[2]:" ");
      tblCommittente.setObject(riga,3,riga1[3]!=null?riga1[3]:" ");
      riga+=2;
    }
    else
    {
      PdfServlet.resetTableHeader(tblCommittente, 3);
      tblCommittente.setObject(0,0,"Proprietario:");
      tblCommittente.setObject(0,1,"Inserimento dati:");
      tblCommittente.setObject(0,2,"Indirizzo:");
      tblCommittente.setObject(0,3,"Telefono:");
      tblCommittente.setObject(2,0,"Classe coltura:");
      tblCommittente.setObject(2,1,"Specie prevista:");
      tblCommittente.setObject(2,2," ");
      tblCommittente.setObject(2,3," ");      
    }
    */
    boolean tecnico=etichettaCampionePDF.selectPerPDFRisultatoAnalisi(true,riga1,riga2,riga3,etichettaCampionePDF.getCodiceMateriale());
    String riferCatCoorGeo=etichettaCampionePDF.getRiferCatCoorGeoPDF();
    
    PdfServlet.resetTableHeader(tblCommittente, 3);
    
    if (tecnico)
    {
	    tblCommittente.setObject(0,0,"Committente:");
	    tblCommittente.setObject(0,1,"Tecnico:");
	    tblCommittente.setObject(0,2,"Proprietario:");
	    tblCommittente.setObject(0,3,"Indirizzo proprietario:");
    }
    else
    {
    	tblCommittente.setSpan(0, 0, new java.awt.Dimension(2, 1));
	    tblCommittente.setObject(0,0,"Proprietario:");
	    tblCommittente.setSpan(0, 2, new java.awt.Dimension(2, 1));
	    tblCommittente.setObject(0,2,"Indirizzo proprietario:");
    }	
    tblCommittente.setObject(2,0,"Coltura in atto o precedente:");
    tblCommittente.setObject(2,1,"Coltura prevista:");
    tblCommittente.setSpan(2, 2, new java.awt.Dimension(2, 1));
    tblCommittente.setObject(2,2,"Coordinate geografiche e riferimenti catastali"); 
    
    
    tblCommittente.setObject(riga,0,riga2[0]!=null?riga2[0]:" ");
    tblCommittente.setObject(riga,1,riga2[2]!=null?riga2[2]:" ");
    
    if (tecnico)
    {
    	tblCommittente.setObject(riga,0,riga1[0]!=null?riga1[0]:" ");
        tblCommittente.setObject(riga,1,riga1[1]!=null?riga1[1]:" "); 
        tblCommittente.setObject(riga,2,riga2[0]!=null?riga2[0]:" ");
        tblCommittente.setObject(riga,3,riga2[2]!=null?riga2[2]:" ");
    }
    else
    {
    	tblCommittente.setSpan(riga, 0, new java.awt.Dimension(2, 1));
    	tblCommittente.setObject(riga,0,riga2[0]!=null?riga2[0]:" ");
    	tblCommittente.setSpan(riga, 2, new java.awt.Dimension(2, 1));
        tblCommittente.setObject(riga,2,riga2[2]!=null?riga2[2]:" ");
    }
    
    tblCommittente.setObject(riga+2,0,riga3[0]!=null?riga3[0]:" ");
    tblCommittente.setObject(riga+2,1,riga3[1]!=null?riga3[1]:" ");
    
    tblCommittente.setSpan(riga+2, 2, new java.awt.Dimension(2, 1));
   
    tblCommittente.setObject(riga+2,2,riferCatCoorGeo);
    /*
    tblCommittente.setObject(riga,0,riga2[0]!=null?riga2[0]:" ");
    tblCommittente.setObject(riga,1,riga2[1]!=null?riga2[1]:" ");
    tblCommittente.setObject(riga,2,riga2[2]!=null?riga2[2]:" ");
    tblCommittente.setObject(riga,3,riga2[3]!=null?riga2[3]:" ");
    tblCommittente.setObject(riga+2,0,riga3[0]!=null?riga3[0]:" ");
    tblCommittente.setObject(riga+2,1,riga3[1]!=null?riga3[1]:" ");
    */
    xss.setElement("tblCommittente", tblCommittente);


    DefaultTableLens tblAnalisi=new DefaultTableLens(xss.getTable("tblAnalisi"));
    
    /**
    *  Imposto il numero di righe della tabella
    */
    if (bUmiditaCampione)
    {
      if (!"".equals(umiditaCampione.getUmiditaCampionePDF())) numRigheTblAnalisi++;
      if (!"".equals(umiditaCampione.getSostanzaSeccaPDF())) numRigheTblAnalisi++;
    }
    if (bGranulometriaStandard) numRigheTblAnalisi+=4;
    if (bGranulometriaA4Frazioni) numRigheTblAnalisi+=6;
    if (bGranulometriaA5Frazioni) numRigheTblAnalisi+=8;
    if (bGranulometriaMetodoBojoucos) numRigheTblAnalisi+=6;
    if (bReazioneSuolo) numRigheTblAnalisi+=1;
    if (bConducibilitaSalinita) numRigheTblAnalisi+=1;
    if (bCalcimetria)
    {
      if (!"".equals(calcimetria.getCarbonatoCalcioTotalePDF())) numRigheTblAnalisi++;
      if (!"".equals(calcimetria.getCalcareAttivoPDF())) numRigheTblAnalisi++;
    }
    if (bSostanzaOrganica) numRigheTblAnalisi+=2;
    if (bAzoto) numRigheTblAnalisi+=2;
    if (bComplessoScambio)
    {
      if (!"".equals(complessoScambio.getCapacitaScambioCationicoPDF())) numRigheTblAnalisi++;
      if (!"".equals(complessoScambio.getCalcioScambiabilePDF())) numRigheTblAnalisi++;
      if (!"".equals(complessoScambio.getCalcioScambiabileMeq100PDF())) numRigheTblAnalisi++;
      if (!"".equals(complessoScambio.getCalcioScambiabileCscPDF())) numRigheTblAnalisi++;
      if (!"".equals(complessoScambio.getMagnesioScambiabilePDF())) numRigheTblAnalisi++;
      if (!"".equals(complessoScambio.getMagnesioScambiabileMeq100PDF())) numRigheTblAnalisi++;
      if (!"".equals(complessoScambio.getMagnesioScambiabileCscPDF())) numRigheTblAnalisi++;
      if (!"".equals(complessoScambio.getPotassioScambiabilePDF())) numRigheTblAnalisi++;
      if (!"".equals(complessoScambio.getPotassioScambiabileMeq100PDF())) numRigheTblAnalisi++;
      if (!"".equals(complessoScambio.getPotassioScambiabileCscPDF())) numRigheTblAnalisi++;
      if (!"".equals(complessoScambio.getSodioScambiabilePDF())) numRigheTblAnalisi++;
      if (!"".equals(complessoScambio.getSodioScambiabileMeq100PDF())) numRigheTblAnalisi++;
      if (!"".equals(complessoScambio.getSodioScambiabileCscPDF())) numRigheTblAnalisi++;
      if (!"".equals(complessoScambio.getCaMgPDF())) numRigheTblAnalisi++;
      if (!"".equals(complessoScambio.getCaKPDF())) numRigheTblAnalisi++;
      if (!"".equals(complessoScambio.getMgKPDF())) numRigheTblAnalisi++;
    }
    if (bFosforoMetodoOLSEN) numRigheTblAnalisi+=2;
    if (bMicroelementiMetodoDTPA)
    {
      if (!"".equals(microelementiMetodoDTPA.getFerroAssimilabilePDF())) numRigheTblAnalisi++;
      if (!"".equals(microelementiMetodoDTPA.getManganeseAssimilabilePDF())) numRigheTblAnalisi++;
      if (!"".equals(microelementiMetodoDTPA.getZincoAssimilabilePDF())) numRigheTblAnalisi++;
      if (!"".equals(microelementiMetodoDTPA.getRameAssimilabilePDF())) numRigheTblAnalisi++;
      if (!"".equals(microelementiMetodoDTPA.getBoroAssimilabilePDF())) numRigheTblAnalisi++;
    }
    if (bMetalliPesanti)
    {
      if (! Utili.isEmpty(metalliPesanti.getFerroTotale())) numRigheTblAnalisi++;
      if (! Utili.isEmpty(metalliPesanti.getManganeseTotale())) numRigheTblAnalisi++;
      if (! Utili.isEmpty(metalliPesanti.getZincoTotale())) numRigheTblAnalisi++;
      if (! Utili.isEmpty(metalliPesanti.getRameTotale())) numRigheTblAnalisi++;
      if (! Utili.isEmpty(metalliPesanti.getBoroTotale())) numRigheTblAnalisi++;
      if (! Utili.isEmpty(metalliPesanti.getCadmioTotale())) numRigheTblAnalisi++;
      if (! Utili.isEmpty(metalliPesanti.getCromoTotale())) numRigheTblAnalisi++;
      if (! Utili.isEmpty(metalliPesanti.getNichelTotale())) numRigheTblAnalisi++;
      if (! Utili.isEmpty(metalliPesanti.getPiomboTotale())) numRigheTblAnalisi++;
      if (! Utili.isEmpty(metalliPesanti.getStronzioTotale())) numRigheTblAnalisi++;
      //if (! Utili.isEmpty(metalliPesanti.getAltroMetalloTotale())) numRigheTblAnalisi++; 
    }

    PdfServlet.resetTableHeader(tblAnalisi, numRigheTblAnalisi);

    /*
    tblAnalisi.setColWidth(0,20); // sparisce
    tblAnalisi.setColWidth(1,117); // diventa colonna 0
    tblAnalisi.setColWidth(INDICE_COLONNA_VALORE_RISCONTRATO,52);
    tblAnalisi.setColWidth(3,30); // diventa colonna 2
    tblAnalisi.setColWidth(INDICE_COLONNA_UNITA_MISURA,57);
    tblAnalisi.setColWidth(5,30); // sparisce
    tblAnalisi.setColWidth(INDICE_COLONNA_NOTE,82);
    tblAnalisi.setColWidth(INDICE_COLONNA_METODO,140);
     */

    //La colonna del tipo analisi non può andare a capo
    tblAnalisi.setColLineWrap(INDICE_COLONNA_TIPO_ANALISI, false);
    //La colonna del Valore riscontrato non può andare a capo
    tblAnalisi.setColLineWrap(INDICE_COLONNA_VALORE_RISCONTRATO, false);
    //La colonna Unità di misura non può andare a capo
    tblAnalisi.setColLineWrap(INDICE_COLONNA_UNITA_MISURA, false);
    //La colonna Note non può andare a capo
    tblAnalisi.setColLineWrap(INDICE_COLONNA_NOTE, false);
    //La colonna Metodo non può andare a capo
    tblAnalisi.setColLineWrap(INDICE_COLONNA_METODO, false);

    /**
     * Imposto la riga di header in grassetto
     * */
    tblAnalisi.setRowFont (0,new Font("SansSerif",Font.BOLD,8));

    //Imposto gli allineamenti per la riga d'intestazione della tabella analisi 
    tblAnalisi.setAlignment(0,INDICE_COLONNA_VALORE_RISCONTRATO,StyleConstants.H_CENTER);
    tblAnalisi.setAlignment(0,INDICE_COLONNA_UNITA_MISURA,StyleConstants.H_CENTER);
    tblAnalisi.setAlignment(0,INDICE_COLONNA_NOTE,StyleConstants.H_CENTER);
    tblAnalisi.setAlignment(0,INDICE_COLONNA_METODO,StyleConstants.H_CENTER);

    //Imposto gli allineamenti per le singole celle  
    for (int i=1;i<=numRigheTblAnalisi;i++)
    {
      tblAnalisi.setAlignment(i,INDICE_COLONNA_VALORE_RISCONTRATO,StyleConstants.H_RIGHT);
      tblAnalisi.setAlignment(i,INDICE_COLONNA_UNITA_MISURA,StyleConstants.H_CENTER);
      tblAnalisi.setAlignment(i,INDICE_COLONNA_NOTE,StyleConstants.H_CENTER);
      tblAnalisi.setAlignment(i,INDICE_COLONNA_METODO,StyleConstants.H_CENTER);
    }

    int rigaCorTblAnalisi=0;
    Font fontBold = new Font("SansSerif",Font.BOLD,8);
    if (bUmiditaCampione)
    {
      if (!"".equals(umiditaCampione.getUmiditaCampionePDF()))
      {
    	scriviAnalisi(tblAnalisi, ++rigaCorTblAnalisi, fontBold, Utili.iso2unicode("Umidità"),
    				  umiditaCampione.getUmiditaCampionePDF()+" ", null,
    				  "%", "% acqua sul tal quale", null);
      }

      if (!"".equals(umiditaCampione.getSostanzaSeccaPDF()))
      {
      	scriviAnalisi(tblAnalisi, ++rigaCorTblAnalisi, fontBold, "Sostanza secca",
      				  umiditaCampione.getSostanzaSeccaPDF()+" ", null,
      				  "%", "% sost. Secca sul tal\nquale", null);
      }
    }
    if (bGranulometriaStandard)
    {
      granulometria=true;
      try
      {
        argilla=Double.parseDouble(granulometriaStandard.getArgilla().replace(',','.'));
        sabbia=Double.parseDouble(granulometriaStandard.getSabbiaTotale().replace(',','.'));
        limo=Double.parseDouble(granulometriaStandard.getLimoTotale().replace(',','.'));
      }
      catch (Exception e)
      {
        granulometria=false;
      }

      scriviAnalisi(tblAnalisi, ++rigaCorTblAnalisi, fontBold, "Granulometria",
  					null, null,
  					"%", "diametro \"d\" delle\nparticelle in mm", "II.5 ingegnerizzato (metodo della pipetta)");

      scriviAnalisi(tblAnalisi, ++rigaCorTblAnalisi, null, "Sabbia",
    		  		granulometriaStandard.getSabbiaTotalePDF()+" ", null,
					"%", "0,05<d<2", null);
      
      scriviAnalisi(tblAnalisi, ++rigaCorTblAnalisi, null, "Limo",
    		  		granulometriaStandard.getLimoTotalePDF()+" ", null,
		  			"%", "0,002<d<0,05", null);

      scriviAnalisi(tblAnalisi, ++rigaCorTblAnalisi, null, "Argilla",
		  			granulometriaStandard.getArgillaPDF()+" ", null,
		  			"%", "d<0,002", null);
    }

    if (bGranulometriaA4Frazioni)
    {
      granulometria=true;
      try
      {
        argilla=Double.parseDouble(granulometriaA4Frazioni.getArgilla().replace(',','.'));
        sabbia=Double.parseDouble(granulometriaA4Frazioni.getSabbiaTotale().replace(',','.'));
        limo=Double.parseDouble(granulometriaA4Frazioni.getLimoTotale().replace(',','.'));
      }
      catch (Exception e)
      {
        granulometria=false;
      }

      scriviAnalisi(tblAnalisi, ++rigaCorTblAnalisi, fontBold, "Granulometria",
					null, null,
					"%", "diametro \"d\" delle\nparticelle in mm", "II.5 ingegnerizzato (metodo della pipetta)");
      
      scriviAnalisi(tblAnalisi, ++rigaCorTblAnalisi, null, "Sabbia",
    		  		granulometriaA4Frazioni.getSabbiaTotalePDF()+" ", null,
    		  		"%", "0,05<d<2", null);

      scriviAnalisi(tblAnalisi, ++rigaCorTblAnalisi, null, "Limo",
    		  		granulometriaA4Frazioni.getLimoTotalePDF()+" ", null,
    		  		"%", "0,002<d<0,05", null);
		
      scriviAnalisi(tblAnalisi, ++rigaCorTblAnalisi, null, "Argilla",
    		  		granulometriaA4Frazioni.getArgillaPDF()+" ", null,
    		  		"%", "d<0,002", null);

      scriviAnalisi(tblAnalisi, ++rigaCorTblAnalisi, null, "Limo grosso",
		  			granulometriaA4Frazioni.getLimoGrossoPDF()+" ", null,
		  			"%", "0,02<d<0,05", null);
	
      scriviAnalisi(tblAnalisi, ++rigaCorTblAnalisi, null, "Limo fine",
		  			granulometriaA4Frazioni.getLimoFinePDF()+" ", null,
		  			"%", "0,002<d<0,02", null);
    }

    if (bGranulometriaA5Frazioni)
    {
      granulometria=true;
      try
      {
        argilla=Double.parseDouble(granulometriaA5Frazioni.getArgilla().replace(',','.'));
        sabbia=Double.parseDouble(granulometriaA5Frazioni.getSabbiaTotale().replace(',','.'));
        limo=Double.parseDouble(granulometriaA5Frazioni.getLimoTotale().replace(',','.'));
      }
      catch (Exception e)
      {
        granulometria=false;
      }

      scriviAnalisi(tblAnalisi, ++rigaCorTblAnalisi, fontBold, "Granulometria",
				null, null,
				"%", "diametro \"d\" delle\nparticelle in mm", "II.5 ingegnerizzato (metodo della pipetta)");
   
      scriviAnalisi(tblAnalisi, ++rigaCorTblAnalisi, null, "Sabbia",
    		  		granulometriaA5Frazioni.getSabbiaTotalePDF()+" ", null,
    		  		"%", "0,05<d<2", null);

      scriviAnalisi(tblAnalisi, ++rigaCorTblAnalisi, null, "Limo",
    		  		granulometriaA5Frazioni.getLimoTotalePDF()+" ", null,
    		  		"%", "0,002<d<0,05", null);
	
      scriviAnalisi(tblAnalisi, ++rigaCorTblAnalisi, null, "Argilla",
    		  		granulometriaA5Frazioni.getArgillaPDF()+" ", null,
    		  		"%", "d<0,002", null);

      scriviAnalisi(tblAnalisi, ++rigaCorTblAnalisi, null, "Limo grosso",
		  			granulometriaA5Frazioni.getLimoGrossoPDF()+" ", null,
		  			"%", "0,02<d<0,05", null);

      scriviAnalisi(tblAnalisi, ++rigaCorTblAnalisi, null, "Limo fine",
		  			granulometriaA5Frazioni.getLimoFinePDF()+" ", null,
		  			"%", "0,002<d<0,02", null);

      scriviAnalisi(tblAnalisi, ++rigaCorTblAnalisi, null, "Sabbia grossa",
    		  		granulometriaA5Frazioni.getSabbiaGrossaPDF()+" ", null,
    		  		"%", "0,2<d<2", null);

      scriviAnalisi(tblAnalisi, ++rigaCorTblAnalisi, null, "Sabbia fine",
    		  		granulometriaA5Frazioni.getSabbiaFinePDF()+" ", null,
    		  		"%", "0,05<d<0,2", null);
    }

    if (bGranulometriaMetodoBojoucos)
    {
      granulometria=true;
      try
      {
        argilla=Double.parseDouble(granulometriaMetodoBojoucos.getArgillaPDF().replace(',','.'));
        sabbia=Double.parseDouble(granulometriaMetodoBojoucos.getSabbiaTotalePDF().replace(',','.'));
        limo=Double.parseDouble(granulometriaMetodoBojoucos.getLimoTotalePDF().replace(',','.'));
      }
      catch (Exception e)
      {
        granulometria=false;
      }


      scriviAnalisi(tblAnalisi, ++rigaCorTblAnalisi, fontBold, "Granulometria",
					null, null,
					"%", "diametro \"d\" delle\nparticelle in mm", "Bojoucos");

      scriviAnalisi(tblAnalisi, ++rigaCorTblAnalisi, null, "Sabbia",
    		  		granulometriaMetodoBojoucos.getSabbiaTotalePDF()+" ", null,
    		  		"%", "0,05<d<2", null);

      scriviAnalisi(tblAnalisi, ++rigaCorTblAnalisi, null, "Limo",
    		  		granulometriaMetodoBojoucos.getLimoTotalePDF()+" ", null,
    		  		"%", "0,002<d<0,05", null);
	
      scriviAnalisi(tblAnalisi, ++rigaCorTblAnalisi, null, "Argilla",
    		  		granulometriaMetodoBojoucos.getArgillaPDF()+" ", null,
    		  		"%", "d<0,002", null);

      scriviAnalisi(tblAnalisi, ++rigaCorTblAnalisi, null, "Limo grosso",
    		  		granulometriaMetodoBojoucos.getLimoGrossoPDF()+" ", null,
    		  		"%", "0,02<d<0,05", null);

      scriviAnalisi(tblAnalisi, ++rigaCorTblAnalisi, null, "Limo fine",
    		  		granulometriaMetodoBojoucos.getLimoFinePDF()+" ", null,
    		  		"%", "0,002<d<0,02", null);
    }

    if (bReazioneSuolo)
    {
      scriviAnalisi(tblAnalisi, ++rigaCorTblAnalisi, fontBold, "pH",
    		  		reazioneSuolo.getPhPDF()+" ", null,
    		  		null, null, "III.1 (in acqua; rapporto 1:2,5)");
    }

    if (bConducibilitaSalinita)
    {
      scriviAnalisi(tblAnalisi, ++rigaCorTblAnalisi, fontBold, Utili.iso2unicode("Conducibilità (salinità)"),
    		  		conducibilitaSalinita.getConducibilitaPDF()+" ", null,
    		  		"micro S/cm", null, "IV.1 (Conducimetro)");
    }

    if (bCalcimetria)
    {
      if (!"".equals(calcimetria.getCarbonatoCalcioTotalePDF()))
      {
        scriviAnalisi(tblAnalisi, ++rigaCorTblAnalisi, fontBold, "Calcare totale",
        			  calcimetria.getCarbonatoCalcioTotalePDF()+" ", null,
        			  "%", null, "V.1 (Calcimetro Dietrich)");
      }
      if (!"".equals(calcimetria.getCalcareAttivoPDF()))
      {
        scriviAnalisi(tblAnalisi, ++rigaCorTblAnalisi, fontBold, "Calcare attivo",
        			  calcimetria.getCalcareAttivoPDF()+" ", null,
        			  "%", null, "V.2 (con Ammonio ossalato)");
      }
    }

    if (bSostanzaOrganica)
    {
      if (sostanzaOrganica.getSostanzaOrganicaPDF()!=null &&
          !"".equals(sostanzaOrganica.getSostanzaOrganicaPDF()))
      {
        scriviAnalisi(tblAnalisi, ++rigaCorTblAnalisi, fontBold, "Sostanza organica",
        			  sostanzaOrganica.getSostanzaOrganicaPDF()+" ", null,
        			  "%", null, "calcolato");

        scriviAnalisi(tblAnalisi, ++rigaCorTblAnalisi, null, "Carbonio organico",
    				  sostanzaOrganica.getCarbonioOrganicoPDF()+" ", null,
    				  "%", null, "interno (Walkley - Black con misurazione colorimetrica)");
      }
      else
      {
        scriviAnalisi(tblAnalisi, ++rigaCorTblAnalisi, fontBold, "Sostanza organica",
        			  sostanzaOrganica.getSostanzaOrganicaMetodoAnaPDF()+" ", null,
        			  "%", null, "calcolato");

        scriviAnalisi(tblAnalisi, ++rigaCorTblAnalisi, null, "Carbonio organico",
        		  	  sostanzaOrganica.getCarbonioOrganicoMetodoAnaPDF()+" ", null,
        		  	  "%", null, "VII.1 (analizzatore elementare)");
      }
    }

    if (bAzoto)
    {
    	scriviAnalisi(tblAnalisi, ++rigaCorTblAnalisi, fontBold, "Azoto totale",
    				  azoto.getAzotoPDF()+" ", null,
    				  "%", null, "XIV.1 (analizzatore elementare)");

    	scriviAnalisi(tblAnalisi, ++rigaCorTblAnalisi, null, "Rapporto C/N",
    				  azoto.getRapportoCNPDF()+" ", null,
    				  null, null, "calcolato");
    }

    if (bComplessoScambio)
    {
      if (!"".equals(complessoScambio.getCapacitaScambioCationicoPDF()))
      {
    	scriviAnalisi(tblAnalisi, ++rigaCorTblAnalisi, fontBold, Utili.iso2unicode("Capacità di scambio cationico"),
    				  complessoScambio.getCapacitaScambioCationicoPDF()+" ", null,
    				  "meq/100 g", null, "XIII.2 (con BaCl<sub>2</sub> e (OHCH<sub>2</sub>CH<sub>2</sub>)<sub>3</sub>N)");
      }
      if (!"".equals(complessoScambio.getCalcioScambiabilePDF()))
      {
        scriviAnalisi(tblAnalisi, ++rigaCorTblAnalisi, fontBold, "Calcio scambiabile",
        			  complessoScambio.getCalcioScambiabilePDF()+" ", null,
        			  "p.p.m.", null, "XIII.5 (con BaCl<sub>2</sub> e (OHCH<sub>2</sub>CH<sub>2</sub>)<sub>3</sub>N)");
      }
      if (!"".equals(complessoScambio.getCalcioScambiabileMeq100PDF()))
      {
        scriviAnalisi(tblAnalisi, ++rigaCorTblAnalisi, null, "Calcio scambiabile",
  			  		  complessoScambio.getCalcioScambiabileMeq100PDF()+" ", null,
  			  		  "meq/100 g", null, "calcolato");
      }
      if (!"".equals(complessoScambio.getCalcioScambiabileCscPDF()))
      {
        scriviAnalisi(tblAnalisi, ++rigaCorTblAnalisi, null, "% Calcio scambiabile sulla CSC",
        			  complessoScambio.getCalcioScambiabileCscPDF()+" ", null,
        			  "%", null, "calcolato");
      }
      if (!"".equals(complessoScambio.getMagnesioScambiabilePDF()))
      {
        scriviAnalisi(tblAnalisi, ++rigaCorTblAnalisi, fontBold, "Magnesio scambiabile",
        			  complessoScambio.getMagnesioScambiabilePDF()+" ", null,
        			  "p.p.m.", null, "XIII.5 (con BaCl<sub>2</sub> e (OHCH<sub>2</sub>CH<sub>2</sub>)<sub>3</sub>N)");
      }
      if (!"".equals(complessoScambio.getMagnesioScambiabileMeq100PDF()))
      {
        scriviAnalisi(tblAnalisi, ++rigaCorTblAnalisi, null, "Magnesio scambiabile",
        			  complessoScambio.getMagnesioScambiabileMeq100PDF()+" ", null,
        			  "meq/100 g", null, "calcolato");
      }
      if (!"".equals(complessoScambio.getMagnesioScambiabileCscPDF()))
      {
        scriviAnalisi(tblAnalisi, ++rigaCorTblAnalisi, null, "% Magnesio scambiabile sulla CSC",
        			  complessoScambio.getMagnesioScambiabileCscPDF()+" ", null,
        			  "%", null, "calcolato");
      }
      if (!"".equals(complessoScambio.getPotassioScambiabilePDF()))
      {
        scriviAnalisi(tblAnalisi, ++rigaCorTblAnalisi, fontBold, "Potassio scambiabile",
        			  complessoScambio.getPotassioScambiabilePDF()+" ", null,
        			  "p.p.m.", null, "XIII.5 (con BaCl<sub>2</sub> e (OHCH<sub>2</sub>CH<sub>2</sub>)<sub>3</sub>N)");
      }
      if (!"".equals(complessoScambio.getPotassioScambiabileMeq100PDF()))
      {
        scriviAnalisi(tblAnalisi, ++rigaCorTblAnalisi, null, "Potassio scambiabile",
        			  complessoScambio.getPotassioScambiabileMeq100PDF()+" ", null,
        			  "meq/100 g", null, "calcolato");
      }
      if (!"".equals(complessoScambio.getPotassioScambiabileCscPDF()))
      {
        scriviAnalisi(tblAnalisi, ++rigaCorTblAnalisi, null, "% Potassio scambiabile sulla CSC",
        			  complessoScambio.getPotassioScambiabileCscPDF()+" ", null,
        			  "%", null, "calcolato");
      }
      if (!"".equals(complessoScambio.getSodioScambiabilePDF()))
      {
        scriviAnalisi(tblAnalisi, ++rigaCorTblAnalisi, fontBold, "Sodio scambiabile",
        			  complessoScambio.getSodioScambiabilePDF()+" ", null,
        			  "p.p.m.", null, "XIII.5 (con BaCl<sub>2</sub> e (OHCH<sub>2</sub>CH<sub>2</sub>)<sub>3</sub>N)");
      }
      if (!"".equals(complessoScambio.getSodioScambiabileMeq100PDF()))
      {
        scriviAnalisi(tblAnalisi, ++rigaCorTblAnalisi, null, "Sodio scambiabile",
        			  complessoScambio.getSodioScambiabileMeq100PDF()+" ", null,
        			  "meq/100 g", null, "calcolato");
      }
      if (!"".equals(complessoScambio.getSodioScambiabileCscPDF()))
      {
        scriviAnalisi(tblAnalisi, ++rigaCorTblAnalisi, null, "% Sodio scambiabile sulla CSC",
        		  	  complessoScambio.getSodioScambiabileCscPDF()+" ", null,
        		  	  "%", null, "calcolato");
      }
      if (!"".equals(complessoScambio.getCaMgPDF()))
      {
        scriviAnalisi(tblAnalisi, ++rigaCorTblAnalisi, null, "Ca/Mg",
        			  complessoScambio.getCaMgPDF()+" ", null,
        			  null, "rapporto in meq/100g", "calcolato");
      }
      if (!"".equals(complessoScambio.getCaKPDF()))
      {
        scriviAnalisi(tblAnalisi, ++rigaCorTblAnalisi, null, "Ca/K",
        			  complessoScambio.getCaKPDF()+" ", null,
        			  null, "rapporto in meq/100g", "calcolato");
      }
      if (!"".equals(complessoScambio.getMgKPDF()))
      {
        scriviAnalisi(tblAnalisi, ++rigaCorTblAnalisi, null, "Mg/K",
        			  complessoScambio.getMgKPDF()+" ", null,
        			  null, "rapporto in meq/100g", "calcolato");
      }
    }


    if (bFosforoMetodoOLSEN)
    {
      scriviAnalisi(tblAnalisi, ++rigaCorTblAnalisi, fontBold, "Fosforo assimilabile",
    		  		fosforoMetodoOLSEN.getFosforoAssimilabilePDF()+" ", null,
    		  		"p.p.m.", null, "XV.3 (metodo Olsen)");
      scriviAnalisi(tblAnalisi, ++rigaCorTblAnalisi, null, "Anidride fosforica assimilabile",
    		  		fosforoMetodoOLSEN.getAnidrideFosforicaPDF()+" ", null,
    		  		"p.p.m.", null, "calcolato");
    }

    if (bMicroelementiMetodoDTPA)
    {
      if (!"".equals(microelementiMetodoDTPA.getFerroAssimilabilePDF()))
      {
        scriviAnalisi(tblAnalisi, ++rigaCorTblAnalisi, fontBold, "Ferro assimilabile",
        			  microelementiMetodoDTPA.getFerroAssimilabilePDF()+" ", null,
        			  "p.p.m.", null, "XII.1 (ed.92) (metodo Lindsay e Norwell)");
      }
      if (!"".equals(microelementiMetodoDTPA.getManganeseAssimilabilePDF()))
      {
        scriviAnalisi(tblAnalisi, ++rigaCorTblAnalisi, fontBold, "Manganese assimilabile",
        			  microelementiMetodoDTPA.getManganeseAssimilabilePDF()+" ", null,
        			  "p.p.m.", null, "XII.1 (ed.92) (metodo Lindsay e Norwell)");
      }
      if (!"".equals(microelementiMetodoDTPA.getZincoAssimilabilePDF()))
      {
        scriviAnalisi(tblAnalisi, ++rigaCorTblAnalisi, fontBold, "Zinco assimilabile",
        			  microelementiMetodoDTPA.getZincoAssimilabilePDF()+" ", null,
        			  "p.p.m.", null, "XII.1 (metodo Lindsay e Norwell)"); // è giusto che non ci sia (ed.92)
      }
      if (!"".equals(microelementiMetodoDTPA.getRameAssimilabilePDF()))
      {
    	scriviAnalisi(tblAnalisi, ++rigaCorTblAnalisi, fontBold, "Rame assimilabile",
    			   	  microelementiMetodoDTPA.getRameAssimilabilePDF()+" ", null,
    			   	  "p.p.m.", null, "XII.1 (metodo Lindsay e Norwell)"); // è giusto che non ci sia (ed.92)
      }
      if (!"".equals(microelementiMetodoDTPA.getBoroAssimilabilePDF()))
      {
    	scriviAnalisi(tblAnalisi, ++rigaCorTblAnalisi, fontBold, "Boro solubile",
    			microelementiMetodoDTPA.getBoroAssimilabilePDF()+" ", null,
			   	  "p.p.m.", null, "XVI.2");
      }
    }

    if (bMetalliPesanti)
    {
      if (! Utili.isEmpty(metalliPesanti.getFerroTotale()))
      {
        scriviAnalisi(tblAnalisi, ++rigaCorTblAnalisi, fontBold, "Ferro totale", Utili.formatElementValue(metalliPesanti.getFerroTotale()) + " ", null, "p.p.m.", null, "Metodo Interno (in acqua regia)");
      }
      if (! Utili.isEmpty(metalliPesanti.getManganeseTotale()))
      {
        scriviAnalisi(tblAnalisi, ++rigaCorTblAnalisi, fontBold, "Manganese totale", Utili.formatElementValue(metalliPesanti.getManganeseTotale()) + " ", null, "p.p.m.", null, "XI.1 (mineralizzazione in microonde)");
      }
      if (! Utili.isEmpty(metalliPesanti.getZincoTotale()))
      {
        scriviAnalisi(tblAnalisi, ++rigaCorTblAnalisi, fontBold, "Zinco totale", Utili.formatElementValue(metalliPesanti.getZincoTotale()) + " ", null, "p.p.m.", null, "XI.1 (mineralizzazione in microonde)");
      }
      if (! Utili.isEmpty(metalliPesanti.getRameTotale()))
      {
        scriviAnalisi(tblAnalisi, ++rigaCorTblAnalisi, fontBold, "Rame totale", Utili.formatElementValue(metalliPesanti.getRameTotale()) + " ", null, "p.p.m.", null, "XI.1 (mineralizzazione in microonde)");
      }
      if (! Utili.isEmpty(metalliPesanti.getBoroTotale()))
      {
      	scriviAnalisi(tblAnalisi, ++rigaCorTblAnalisi, fontBold, "Boro totale", Utili.formatElementValue(metalliPesanti.getBoroTotale()) + " ", null, "p.p.m.", null, "XVI.2");      	
      }
      if (! Utili.isEmpty(metalliPesanti.getCadmioTotale()))
      {
        scriviAnalisi(tblAnalisi, ++rigaCorTblAnalisi, fontBold, "Cadmio totale", Utili.formatElementValue(metalliPesanti.getCadmioTotale()) + " ", null, "p.p.m.", null, "XI.1 (mineralizzazione in microonde)");
      }
      if (! Utili.isEmpty(metalliPesanti.getCromoTotale()))
      {
        scriviAnalisi(tblAnalisi, ++rigaCorTblAnalisi, fontBold, "Cromo totale", Utili.formatElementValue(metalliPesanti.getCromoTotale()) + " ", null, "p.p.m.", null, "XI.1 (mineralizzazione in microonde)");
      }
      if (! Utili.isEmpty(metalliPesanti.getNichelTotale()))
      {
        scriviAnalisi(tblAnalisi, ++rigaCorTblAnalisi, fontBold, "Nichel totale", Utili.formatElementValue(metalliPesanti.getNichelTotale()) + " ", null, "p.p.m.", null, "XI.1 (mineralizzazione in microonde)");
      }
      if (! Utili.isEmpty(metalliPesanti.getPiomboTotale()))
      {
        scriviAnalisi(tblAnalisi, ++rigaCorTblAnalisi, fontBold, "Piombo totale", Utili.formatElementValue(metalliPesanti.getPiomboTotale()) + " ", null, "p.p.m.", null, "XI.1 (mineralizzazione in microonde)");
      }
      if (! Utili.isEmpty(metalliPesanti.getStronzioTotale()))
      {
        scriviAnalisi(tblAnalisi, ++rigaCorTblAnalisi, fontBold, "Stronzio totale", Utili.formatElementValue(metalliPesanti.getStronzioTotale()) + " ", null, "p.p.m.", null, "XII.1 (ed.92) (metodo Lindsay e Norwell)");
      }
      /*if (! Utili.isEmpty(metalliPesanti.getAltroMetalloTotale()))
      {
        scriviAnalisi(tblAnalisi, ++rigaCorTblAnalisi, fontBold, "Altro metallo totale", Utili.formatElementValue(metalliPesanti.getAltroMetalloTotale()) + " ", null, "p.p.m.", null, "XII.1 (ed.92) (metodo Lindsay e Norwell)");
      }*/
    }

    /**
     * Mi occupo di andare a preparare i giudizi
     *
     * */
    /**
   *  Devo leggere i dati dal database
   **/
   java.util.Enumeration enumAnalisi=analisi.getCodiciAnalisi().elements();
   String codice=null;
   boolean giudiziGranulometria=false;
   boolean giudiziPH=false;
   boolean giudiziSostOrganica=false;
   boolean giudiziCSC=false;
   boolean giudiziPotassio=false;
   boolean giudiziFosforo=false;
   boolean giudiziAzoto=false;
   while(enumAnalisi.hasMoreElements())
   {
     codice=(String)enumAnalisi.nextElement();
     if (it.csi.agrc.Analisi.ANA_A4FRAZIONI.equals(codice)
                         ||
        it.csi.agrc.Analisi.ANA_A5FRAZIONI.equals(codice)
                         ||
        it.csi.agrc.Analisi.ANA_STANDARD.equals(codice))
     {
       giudiziGranulometria=true;
     }
     if (it.csi.agrc.Analisi.ANA_PACCHETTO_TIPO.equals(codice))
     {
       giudiziPH=true;
       giudiziSostOrganica=true;
       giudiziCSC=true;
       giudiziPotassio=true;
       giudiziFosforo=true;
       giudiziAzoto=true;
     }
     if (it.csi.agrc.Analisi.ANA_PACCHETTO_COMP_SCAMBIO.equals(codice))
     {
       giudiziCSC=true;
       giudiziPotassio=true;
     }

     if (it.csi.agrc.Analisi.ANA_PH.equals(codice)) giudiziPH=true;
     if (it.csi.agrc.Analisi.ANA_SOSTANZAORGANICA.equals(codice)) giudiziSostOrganica=true;
     if (it.csi.agrc.Analisi.ANA_CAPACITASCAMBIOCATIONICO.equals(codice)) giudiziCSC=true;
     if (it.csi.agrc.Analisi.ANA_POTASSIO.equals(codice)) giudiziPotassio=true;
     if (it.csi.agrc.Analisi.ANA_FOSFORO.equals(codice)) giudiziFosforo=true;
     if (it.csi.agrc.Analisi.ANA_AZOTO.equals(codice)) giudiziAzoto=true;

    }


    /**
     * Scrivo il giudizio sulla granulometria
     * */
    if (granulometria && giudiziGranulometria)
    {
      sGranulometria=PdfRisultatoAnalisiTER.scriviGiudizioGranulometria(argilla,sabbia,limo);
      giudizio.append("\nTerreno ");
      giudizio.append(sGranulometria);
    }

    /**
     * Scrivo il giudizio sull'analisi del ph
     * */
    if (giudiziPH)
    {
      try
      {
        double ph=Double.parseDouble(reazioneSuolo.getPhPDF().replace(',','.'));
        giudizio.append(Utili.iso2unicode("\nLa reazione del suolo è "));
        giudizio.append(PdfRisultatoAnalisiTER.scriviGiudizioPH(ph));
      }
      catch(Exception e) {}
    }

    /**
     * Scrivo il giudizio sull'analisi della sostanza organica
     * */
    if (giudiziSostOrganica)
    {
      try
      {
        double sostanzaOrg=0;
        if (sostanzaOrganica.getSostanzaOrganica()!=null)
          sostanzaOrg=Double.parseDouble(sostanzaOrganica.getSostanzaOrganica().replace(',','.'));
        else
          sostanzaOrg=Double.parseDouble(sostanzaOrganica.getSostanzaOrganicaMetodoAna().replace(',','.'));
        giudizio.append("\nIl terreno ");
        giudizio.append(PdfRisultatoAnalisiTER.scriviGiudizioSostOrg(sostanzaOrg,sGranulometria,campioneTerrenoDati.getSpeciePrevista()));
        giudizio.append(" di sostanza organica.");
      }
      catch(Exception e) {}
    }
    /**
     * Scrivo il giudizio sull'analisi del CSC
     * */
    if (giudiziCSC)
    {
      try
      {
        double csc=Double.parseDouble(complessoScambio.getCapacitaScambioCationico().replace(',','.'));
        giudizio.append(Utili.iso2unicode("\nLa capacità di scambio cationico è  "));
        giudizio.append(PdfRisultatoAnalisiTER.scriviGiudizioCSC(csc));
      }
      catch(Exception e) {}
    }
    /**
     * Scrivo il giudizio sull'analisi del potassio
     * */
    if (giudiziPotassio && giudiziCSC)
    {
      try
      {
        double csc=Double.parseDouble(complessoScambio.getPotassioScambiabileCsc().replace(',','.'));
        giudizio.append(Utili.iso2unicode("\nLa dotazione in potassio scambiabile, in rapporto alla CSC è  "));
        giudizio.append(PdfRisultatoAnalisiTER.scriviGiudizioPotassio(csc));
      }
      catch(Exception e) {}
    }
    /**
     * Scrivo il giudizio sull'analisi del fosforo
     * */
    if (giudiziFosforo)
    {
      try
      {
        double fosforo=Double.parseDouble(fosforoMetodoOLSEN.getFosforoAssimilabile().replace(',','.'));
        giudizio.append("\nIl terreno presenta una dotazione ");
        giudizio.append(PdfRisultatoAnalisiTER.scriviGiudizioFosforo(fosforo,sGranulometria));
        giudizio.append(" di fosforo assimilabile");
      }
      catch(Exception e) {}
    }
    /**
     * Scrivo il giudizio sull'analisi dell' azoto
     * */
    if (giudiziAzoto && giudiziSostOrganica)
    {
      try
      {
        double rapportoCN=Double.parseDouble(azoto.getRapportoCN().replace(',','.'));
        giudizio.append("\nIn base al rapporto C/N il terreno presenta probabilmente una mineralizzazione ");
        giudizio.append(PdfRisultatoAnalisiTER.scriviGiudizioCN(rapportoCN));
      }
      catch(Exception e) {}
    }

    xss.setElement("tblAnalisi", tblAnalisi);

    DefaultTableLens tblGiudizi=new DefaultTableLens(xss.getTable("tblGiudizi"));
    if (giudizio.toString().equals(""))
      tblGiudizi.setObject(0,0,"Relativamente alle determinazioni eseguite si esprime il seguente giudizio: \n\n\n\n\n");
    else
      tblGiudizi.setObject(0,0,"Relativamente alle determinazioni eseguite si esprime il seguente giudizio:"+giudizio.toString());
    xss.setElement("tblGiudizi", tblGiudizi);

    DefaultTextLens txtBoxData=new  DefaultTextLens();
    if (tracciabilitaPOP.getData()!=null)
      xss.setElement("txtBoxData", tracciabilitaPOP.getData());
    else xss.setElement("txtBoxData", "");
    /**
     * Vado a vedere se il referto è stato firmato
     * */
    if (etichettaCampionePDF.getFirma(resp))
    {
      /**
       * Referto firmato: devo visualizzare il titolo onorifico,
       * il cognome, il nome e la firma (immagine)
       */
      String nomeFir,cogFirm,titoloFirm;
      nomeFir=resp.getNome();
      cogFirm=resp.getCognome();
      titoloFirm=resp.getTitoloOnorifico();
      if (nomeFir==null) nomeFir="";
      if (cogFirm==null) cogFirm="";
      if (titoloFirm==null) titoloFirm="";

      DefaultTextLens txtBoxTitoloOnorifico=new  DefaultTextLens();
      txtBoxTitoloOnorifico.setText(titoloFirm+" "+cogFirm+" "+nomeFir);
      xss.setElement("txtBoxTitoloOnorifico", txtBoxTitoloOnorifico);

      if (resp.getFirma()!=null)
      {
        xss.setElement("imgFirma",new ImagePainter(resp.getFirma()));
      }
      else
      {
        /**
         * Ci sono stati dei problemi con l'immagine della firma quindi
         * elimino il componente che la visualizza
         */
        xss.removeElement("imgFirma");
      }
    }
    else
    {
      /**
       * referto non firmato: devo eliminare i componenti che permettono la sua
       * visualizzazione
       */
     xss.removeElement("txtBoxFirma");
     xss.removeElement("txtBoxTitoloOnorifico");
     xss.removeElement("imgFirma");
    }
    String note=resp.getNote();
    if (note==null) note="";

    DefaultTableLens tblNote=new DefaultTableLens(xss.getTable("tblNote"));
    if (note.length()<100) note+="\n\n\n\n\n";
    else
    {
      if (note.length()<200) note+="\n\n\n\n";
      else
      {
        if (note.length()<300) note+="\n\n\n";
        else
        {
          if (note.length()<400) note+="\n\n";
          else note+="\n";
        }
      }
    }
    tblNote.setObject(0,0,"Note: "+note);
    xss.setElement("tblNote", tblNote);

  }

  private static void scriviAnalisi(DefaultTableLens tblAnalisi,
		  							  int rigaCorTblAnalisi,
		  							  Font newFont,
		  							  String colTipoAnalisi,
		  							  String colValoreRiscontrato,
		  							  String colValorePredefinito,
		  							  String colUnitaMisura,
		  							  String colNote,
		  							  String colMetodo)
  {
	  if (newFont != null)
		  tblAnalisi.setFont(rigaCorTblAnalisi, INDICE_COLONNA_TIPO_ANALISI, newFont);
	  if (colTipoAnalisi != null)
		  tblAnalisi.setObject(rigaCorTblAnalisi, INDICE_COLONNA_TIPO_ANALISI, colTipoAnalisi);
	  if (colValoreRiscontrato != null)
		  tblAnalisi.setObject(rigaCorTblAnalisi, INDICE_COLONNA_VALORE_RISCONTRATO, colValoreRiscontrato);
	  if (colValorePredefinito != null)
		  tblAnalisi.setObject(rigaCorTblAnalisi, INDICE_COLONNA_VALORE_PREDEFINITO, colValorePredefinito);
	  if (colUnitaMisura != null)
		  tblAnalisi.setObject(rigaCorTblAnalisi, INDICE_COLONNA_UNITA_MISURA, colUnitaMisura);
	  if (colNote != null)
		  tblAnalisi.setObject(rigaCorTblAnalisi, INDICE_COLONNA_NOTE, colNote);
	  if (colMetodo != null)
	  {
	  	//Pedice gestito da codice
	  	//Da Designer Style Report 9, selezionare cella, tasto destro Format, Attribute, Presenter: Basic HTML
	  	//nella cella, con Edit, scrivere per esempio: H<sub>2</sub>O o H<sup>2</sup>O
	  	tblAnalisi.setPresenter(rigaCorTblAnalisi, INDICE_COLONNA_METODO, new HTMLPresenter());

		  tblAnalisi.setObject(rigaCorTblAnalisi, INDICE_COLONNA_METODO, colMetodo);
	  }
  }

  private static String scriviGiudizioGranulometria(double argilla,
                                                  double sabbia,
                                                  double limo)
  {
    if (argilla <= (2 * sabbia - 170) ) return "sabbioso";
    if (argilla <= (sabbia - 70) ) return "sabbioso franco";
    if (sabbia > 52.5 && argilla < 20 ) return "franco sabbioso";
    if (argilla < 7.5 && limo <= 50 ) return "franco sabbioso";
    if (sabbia >= 45 && limo <= 27.5 && argilla <= 35 ) return "franco sabbioso argilloso";
    if (sabbia >= 45 && limo <= 20 ) return "argilloso sabbioso";
    if (sabbia >= 20 && sabbia< 45 && argilla >= 27.5 && argilla <= 40 ) return "franco argilloso";
    if (limo <= 40 && argilla >= 40 ) return "argilloso";
    if (argilla >= 40 && limo >= 40 ) return "argilloso limoso";
    if (sabbia <= 20  && argilla >= 27.5 && argilla <= 40 ) return "franco limoso argilloso";
    if (limo >= 80 && argilla <= 12.5 ) return "limoso";
    if (limo >= 50 && argilla <= 27.5 ) return "franco limoso";
    return "franco";
  }

  private static String scriviGiudizioPH(double pH)
  {
    if(pH < 5.3 ) return "peracida";
    if(pH < 6 ) return "acida";
    if(pH < 6.7 ) return "subacida";
    if(pH > 8.8 ) return "peralcalina";
    if(pH > 8.2 ) return "alcalina";
    if(pH > 7.3 ) return "subalcalina";
    return "neutra";
  }

  private static String scriviGiudizioSostOrg(double sostanzaOrg,
                                              String sGranulometria,
                                              String specie)
  {
    //System.out.println("\n\n\n\n\n");
    //System.out.println("specie "+specie);
    //System.out.println("\n\n\n\n\n");

    if ("".equals(sGranulometria) || sGranulometria==null)
    {
      if ("93".equals(specie))
      {
        if (sostanzaOrg < 1 ) return Utili.iso2unicode("è povero");
        if (sostanzaOrg > 1.5 ) return "ha una buona dotazione";
        return "ha una normale dotazione";
      }
      else
      {
        if (sostanzaOrg < 1.5 ) return Utili.iso2unicode("è povero");
        if (sostanzaOrg > 2 ) return "ha una buona dotazione";
        return "ha una normale dotazione";
      }
    }
    if ("sabbioso".equals(sGranulometria) ||
       "sabbioso franco".equals(sGranulometria) ||
       "franco sabbioso".equals(sGranulometria))
    {
      if (sostanzaOrg < 0.8 ) return  Utili.iso2unicode("è molto povero");
      if (sostanzaOrg >=  0.8 && sostanzaOrg < 1.4 ) return  Utili.iso2unicode("è povero");
      if (sostanzaOrg >=  1.4 && sostanzaOrg < 2 ) return  "ha una dotazione media";
      if (sostanzaOrg >=  2 ) return  "ha una elevata dotazione";
    }
    if ("franco sabbioso argilloso".equals(sGranulometria) ||
       "franco argilloso".equals(sGranulometria) ||
       "franco".equals(sGranulometria) ||
       "franco limoso".equals(sGranulometria))
    {
      if (sostanzaOrg < 1 ) return  Utili.iso2unicode("è molto povero");
      if (sostanzaOrg >=  1 && sostanzaOrg < 1.8 ) return  Utili.iso2unicode("è povero");
      if (sostanzaOrg >=  1.8 && sostanzaOrg < 2.5 ) return  "ha una dotazione media";
      if (sostanzaOrg >=  2.5 ) return  "ha una elevata dotazione";
    }
    if ("argilloso".equals(sGranulometria) ||
       "argilloso limoso".equals(sGranulometria) ||
       "limoso".equals(sGranulometria) ||
       "franco limoso argilloso".equals(sGranulometria) ||
       "argilloso sabbioso".equals(sGranulometria))
    {
      if (sostanzaOrg < 1.2 ) return  Utili.iso2unicode("è molto povero");
      if (sostanzaOrg >=  1.2 && sostanzaOrg< 2.2 ) return  Utili.iso2unicode("è povero");
      if (sostanzaOrg >=  2.2 && sostanzaOrg< 3 ) return  "ha una dotazione media";
      if (sostanzaOrg >=  3 ) return  "ha una elevata dotazione";
    }
    return "";
  }

  private static String scriviGiudizioCSC(double csc)
  {
    if (csc < 10 ) return  "bassa";
    if (csc > 20 ) return  "elevata";
    return  "media";
  }

  private static String scriviGiudizioPotassio(double csc)
  {
    if (csc < 1.5 ) return  "molto bassa";
    if (csc >= 1.5 && csc< 3 ) return  "bassa";
    if (csc >= 3 && csc< 4 ) return  "media";
    if (csc >= 4 ) return  "elevata";
    return "";
  }

  private static String scriviGiudizioFosforo(double fosforo,
                                              String sGranulometria)
  {
    if ("".equals(sGranulometria) || sGranulometria==null)
    {
      if (fosforo < 8 ) return  "molto bassa";
      if (fosforo >= 8 && fosforo < 10 ) return  "bassa";
      if (fosforo >= 10 && fosforo < 18 ) return  "media";
      if (fosforo >= 18 ) return  "elevata";
    }
    if ("sabbioso".equals(sGranulometria) ||
       "sabbioso franco".equals(sGranulometria) ||
       "franco sabbioso".equals(sGranulometria))
    {
      if (fosforo < 10 ) return  "molto bassa";
      if (fosforo >= 10 && fosforo < 13 ) return  "bassa";
      if (fosforo >= 13 && fosforo < 18 ) return  "media";
      if (fosforo >= 18 ) return  "elevata";
    }
    if ("franco sabbioso argilloso".equals(sGranulometria) ||
       "franco argilloso".equals(sGranulometria) ||
       "franco".equals(sGranulometria) ||
       "franco limoso".equals(sGranulometria))
    {
      if (fosforo < 8 ) return  "molto bassa";
      if (fosforo >= 8 && fosforo < 11 ) return  "bassa";
      if (fosforo >= 11 && fosforo < 15 ) return  "media";
      if (fosforo >= 15 ) return  "elevata";
    }
    if ("argilloso".equals(sGranulometria) ||
       "argilloso limoso".equals(sGranulometria) ||
       "limoso".equals(sGranulometria) ||
       "franco limoso argilloso".equals(sGranulometria) ||
       "argilloso sabbioso".equals(sGranulometria))
    {
      if (fosforo < 5 ) return  "molto bassa";
      if (fosforo >= 5 && fosforo < 9 ) return  "bassa";
      if (fosforo >= 9 && fosforo < 11 ) return  "media";
      if (fosforo >= 11 ) return  "elevata";
    }
    return "";
  }

  private static String scriviGiudizioCN(double rapportoCN)
  {
    if (rapportoCN < 9 ) return  "veloce";
    if (rapportoCN >= 9 && rapportoCN <= 11 ) return  "normale";
    if (rapportoCN > 11 ) return  "lenta";
    return "";
  }

}
