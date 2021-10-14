package it.csi.agrc.servlet;

//import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import it.csi.agrc.*;
import it.csi.cuneo.*;
import it.csi.cuneo.servlet.PdfServlet;

import java.awt.*;
//import java.util.*;
import inetsoft.report.*;
//import inetsoft.report.internal.*;
import inetsoft.report.painter.*;
//import inetsoft.report.io.*;
import inetsoft.report.lens.*;

/**
 * <p>Title: Agrichim - Front Office</p>
 * <p>Description: Richiesta analisi chimiche su campioni biologici agrari</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: CSI Piemonte - Progettazione e Sviluppo - Cuneo</p>
 * @author Michele Piantà, Piergiorgio Chiriotti
 * @version 1.0.0
 */

public class PdfRisultatoAnalisiFRU extends PdfServletAgrc
{
  private static final long serialVersionUID = -41483886376887781L;
	
  HttpSession session;
  ServletContext context;
  Autenticazione aut;
  Object dataSource;

  public PdfRisultatoAnalisiFRU()
  {
    this.setOutputName("risultatoAnalisiFRU.pdf");
    this.setTemplateName("pdfRisultatoAnalisi.srt");
  }

  protected void stampaPdf(HttpServletRequest request,
  		TabularSheet xss)
      throws Exception
  {
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
     * Questo mi serve per leggere la data di emissione del referto
     * */
    TracciabilitaPOP tracciabilitaPOP=new TracciabilitaPOP();
    tracciabilitaPOP.setDataSource(dataSource);
    tracciabilitaPOP.setAut(aut);
    tracciabilitaPOP.select(idRichiesta);

    /**
     * Questo mi serve per leggere i dati del terreno in cui è sato prelevato il
     * campione
     * */
    DatiAppezzamento datiAppezzamento= new DatiAppezzamento();
    datiAppezzamento.setDataSource(dataSource);
    datiAppezzamento.setAut(aut);
    datiAppezzamento.selectForPdf(idRichiesta,false);

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

    MacroMicroElemIndFrutta macroMicroElemIndFrutta= new MacroMicroElemIndFrutta();
    macroMicroElemIndFrutta.setDataSource(dataSource);
    macroMicroElemIndFrutta.setAut(aut);
    macroMicroElemIndFrutta.setIdRichiesta(lIdRichiesta);
    boolean bMacroMicroElemIndFrutta=macroMicroElemIndFrutta.select();

    Laboratorio laboratorioAnalisi = new Laboratorio(dataSource, aut);

    /**
     * Utilizzato per la firma del referto
     * */
    Responsabile resp=new Responsabile();

    /***************************************************************
     * Header comune a tutte le pagine
     */
    String etichetta=null;

    //Impostazione dei dati relativi alla testa del PDF: sono dati che si trovano
    //all'interno della tabella parametro e vengono precaricati all'avvio dell'applicativo
    // nel bean BeanParametri
    BeanParametri beanParametriApplication=(BeanParametri)context.getAttribute("beanParametriApplication");


    laboratorioAnalisi.select(null,beanParametriApplication.getPartitaIVALab());

    etichetta="SEDE: "+laboratorioAnalisi.getIndirizzoPdf();
    laboratorioAnalisi.select(etichettaCampionePDF.getCodLabAnalisi(),beanParametriApplication.getPartitaIVALab());
    etichetta+="\nLABORATORIO DI ANALISI\n"+laboratorioAnalisi.getIndirizzoPdf();
    xss.setElement("tbxIndirizzo",etichetta);


    //CuneoLogger.debug(this,"PdfRisultatoAnalisiFRU.stampaPdf() - Inizio creazione pdf");
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
    tblCampione.setColWidth(3,184);
    tblCampione.setColWidth(4,50);
    tblCampione.setColWidth(5,40);

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


    //CuneoLogger.debug(this,"Passo di qua: PdfRisultatoAnalisiFRU.java");

    xss.setElement("tblCampione", tblCampione);

    DefaultTextLens txtLaboratorio=new  DefaultTextLens();
    if (laboratorioAnalisi.getDescrizione()!=null)
    txtLaboratorio.setText(" "+laboratorioAnalisi.getDescrizione()+"  ");
    xss.setElement("txtLaboratorio", txtLaboratorio);


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
    if (etichettaCampionePDF.selectPerPDFRisultatoAnalisi(false,riga1,riga2,riga3,etichettaCampionePDF.getCodiceMateriale()))
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
    }
    tblCommittente.setObject(riga,0,riga2[0]!=null?riga2[0]:" ");
    tblCommittente.setObject(riga,1,riga2[1]!=null?riga2[1]:" ");
    tblCommittente.setObject(riga,2,riga2[2]!=null?riga2[2]:" ");
    tblCommittente.setObject(riga,3,riga2[3]!=null?riga2[3]:" ");
    tblCommittente.setObject(riga+2,0,riga3[0]!=null?riga3[0]:" ");
    tblCommittente.setObject(riga+2,1,riga3[1]!=null?riga3[1]:" ");
    xss.setElement("tblCommittente", tblCommittente);


    DefaultTableLens tblAnalisi=new DefaultTableLens(xss.getTable("tblAnalisi"));
    /**
    *  Imposto il numero di righe della tabella
    */
    if (bUmiditaCampione) numRigheTblAnalisi+=1;
    if (bMacroMicroElemIndFrutta)
    {
      if (!"".equals(macroMicroElemIndFrutta.getAzotoPpmPDF())) numRigheTblAnalisi++;
      if (!"".equals(macroMicroElemIndFrutta.getAzotoPDF())) numRigheTblAnalisi++;
      if (!"".equals(macroMicroElemIndFrutta.getFosforoPpmPDF())) numRigheTblAnalisi++;
      if (!"".equals(macroMicroElemIndFrutta.getFosforoPDF())) numRigheTblAnalisi++;
      if (!"".equals(macroMicroElemIndFrutta.getCalcioPpmPDF())) numRigheTblAnalisi++;
      if (!"".equals(macroMicroElemIndFrutta.getCalcioPDF())) numRigheTblAnalisi++;
      if (!"".equals(macroMicroElemIndFrutta.getMagnesioPpmPDF())) numRigheTblAnalisi++;
      if (!"".equals(macroMicroElemIndFrutta.getMagnesioPDF())) numRigheTblAnalisi++;
      if (!"".equals(macroMicroElemIndFrutta.getPotassioPpmPDF())) numRigheTblAnalisi++;
      if (!"".equals(macroMicroElemIndFrutta.getPotassioPDF())) numRigheTblAnalisi++;
      if (!"".equals(macroMicroElemIndFrutta.getFerroPpmPDF())) numRigheTblAnalisi++;
      if (!"".equals(macroMicroElemIndFrutta.getManganesePpmPDF())) numRigheTblAnalisi++;
      if (!"".equals(macroMicroElemIndFrutta.getRamePpmPDF())) numRigheTblAnalisi++;
      if (!"".equals(macroMicroElemIndFrutta.getZincoPpmPDF())) numRigheTblAnalisi++;
      if (!"".equals(macroMicroElemIndFrutta.getBoroPpmPDF())) numRigheTblAnalisi++;
    }


    PdfServlet.resetTableHeader(tblAnalisi, numRigheTblAnalisi);

    tblAnalisi.setColWidth(0,24);
    tblAnalisi.setColWidth(1,75);
    tblAnalisi.setColWidth(2,120);
    tblAnalisi.setColWidth(3,50);
    tblAnalisi.setColWidth(4,120);
    tblAnalisi.setColWidth(5,71);
    tblAnalisi.setColWidth(6,71);

    /**
     * Imposto la riga di header in grassetto
     * */
    tblAnalisi.setRowFont (0,new Font("SansSerif",Font.BOLD,8));

    tblAnalisi.setAlignment(0,0,StyleConstants.H_CENTER);
    tblAnalisi.setAlignment(0,2,StyleConstants.H_CENTER);
    tblAnalisi.setAlignment(0,3,StyleConstants.H_CENTER);
    tblAnalisi.setAlignment(0,4,StyleConstants.H_CENTER);
    tblAnalisi.setAlignment(0,5,StyleConstants.H_CENTER);
    tblAnalisi.setAlignment(0,6,StyleConstants.H_CENTER);


    for (int i=1;i<=numRigheTblAnalisi;i++)
    {
      //Imposto l'allineamento per la colonna dei valori riscontrati
      tblAnalisi.setAlignment(i,2,StyleConstants.H_RIGHT);
      //Imposto l'allineamento per la colonna delle unità di misura
      tblAnalisi.setAlignment(i,4,StyleConstants.H_CENTER);
      //Imposto l'allineamento per la colonna delle note
      tblAnalisi.setAlignment(i,5,StyleConstants.H_CENTER);
      //Imposto l'allineamento per la colonna del metodo SISS
      tblAnalisi.setAlignment(i,6,StyleConstants.H_CENTER);
    }

    int rigaCorTblAnalisi=1;
    if (bUmiditaCampione)
    {
      tblAnalisi.setFont (rigaCorTblAnalisi,1,new Font("SansSerif",Font.BOLD,8));
      tblAnalisi.setObject(rigaCorTblAnalisi,1,"Sostanza secca");
      tblAnalisi.setObject(rigaCorTblAnalisi,2,umiditaCampione.getSostanzaSeccaPDF()+" ");
      tblAnalisi.setObject(rigaCorTblAnalisi,4,"%");
      rigaCorTblAnalisi+=1;
    }
    if (bMacroMicroElemIndFrutta)
    {
      if (!"".equals(macroMicroElemIndFrutta.getAzotoPpmPDF()))
      {
        tblAnalisi.setFont (rigaCorTblAnalisi,1,new Font("SansSerif",Font.BOLD,8));
        tblAnalisi.setObject(rigaCorTblAnalisi,1,"Azoto");
        tblAnalisi.setObject(rigaCorTblAnalisi,2,macroMicroElemIndFrutta.getAzotoPpmPDF()+" ");
        tblAnalisi.setObject(rigaCorTblAnalisi,4,"ppm");
        tblAnalisi.setObject(rigaCorTblAnalisi,6,"interno");
        rigaCorTblAnalisi++;
      }
      if (!"".equals(macroMicroElemIndFrutta.getAzotoPDF()))
      {
        tblAnalisi.setObject(rigaCorTblAnalisi,1,"Azoto");
        tblAnalisi.setObject(rigaCorTblAnalisi,2,macroMicroElemIndFrutta.getAzotoPDF()+" ");
        tblAnalisi.setObject(rigaCorTblAnalisi,4,"%");
        tblAnalisi.setObject(rigaCorTblAnalisi,6,"calcolato");
        rigaCorTblAnalisi++;
      }
      if (!"".equals(macroMicroElemIndFrutta.getFosforoPpmPDF()))
      {
        tblAnalisi.setFont (rigaCorTblAnalisi,1,new Font("SansSerif",Font.BOLD,8));
        tblAnalisi.setObject(rigaCorTblAnalisi,1,"Fosforo");
        tblAnalisi.setObject(rigaCorTblAnalisi,2,macroMicroElemIndFrutta.getFosforoPpmPDF()+" ");
        tblAnalisi.setObject(rigaCorTblAnalisi,4,"ppm");
        tblAnalisi.setObject(rigaCorTblAnalisi,6,"interno");
        rigaCorTblAnalisi++;
      }
      if (!"".equals(macroMicroElemIndFrutta.getFosforoPDF()))
      {
        tblAnalisi.setObject(rigaCorTblAnalisi,1,"Fosforo");
        tblAnalisi.setObject(rigaCorTblAnalisi,2,macroMicroElemIndFrutta.getFosforoPDF()+" ");
        tblAnalisi.setObject(rigaCorTblAnalisi,4,"%");
        tblAnalisi.setObject(rigaCorTblAnalisi,6,"calcolato");
        rigaCorTblAnalisi++;
      }
      if (!"".equals(macroMicroElemIndFrutta.getCalcioPpmPDF()))
      {
        tblAnalisi.setFont (rigaCorTblAnalisi,1,new Font("SansSerif",Font.BOLD,8));
        tblAnalisi.setObject(rigaCorTblAnalisi,1,"Calcio");
        tblAnalisi.setObject(rigaCorTblAnalisi,2,macroMicroElemIndFrutta.getCalcioPpmPDF()+" ");
        tblAnalisi.setObject(rigaCorTblAnalisi,4,"ppm");
        tblAnalisi.setObject(rigaCorTblAnalisi,6,"interno");
        rigaCorTblAnalisi++;
      }
      if (!"".equals(macroMicroElemIndFrutta.getCalcioPDF()))
      {
        tblAnalisi.setObject(rigaCorTblAnalisi,1,"Calcio");
        tblAnalisi.setObject(rigaCorTblAnalisi,2,macroMicroElemIndFrutta.getCalcioPDF()+" ");
        tblAnalisi.setObject(rigaCorTblAnalisi,4,"%");
        tblAnalisi.setObject(rigaCorTblAnalisi,6,"calcolato");
        rigaCorTblAnalisi++;
      }
      if (!"".equals(macroMicroElemIndFrutta.getMagnesioPpmPDF()))
      {
        tblAnalisi.setFont (rigaCorTblAnalisi,1,new Font("SansSerif",Font.BOLD,8));
        tblAnalisi.setObject(rigaCorTblAnalisi,1,"Magnesio");
        tblAnalisi.setObject(rigaCorTblAnalisi,2,macroMicroElemIndFrutta.getMagnesioPpmPDF()+" ");
        tblAnalisi.setObject(rigaCorTblAnalisi,4,"ppm");
        tblAnalisi.setObject(rigaCorTblAnalisi,6,"interno");
        rigaCorTblAnalisi++;
      }
      if (!"".equals(macroMicroElemIndFrutta.getMagnesioPDF()))
      {
        tblAnalisi.setObject(rigaCorTblAnalisi,1,"Magnesio");
        tblAnalisi.setObject(rigaCorTblAnalisi,2,macroMicroElemIndFrutta.getMagnesioPDF()+" ");
        tblAnalisi.setObject(rigaCorTblAnalisi,4,"%");
        tblAnalisi.setObject(rigaCorTblAnalisi,6,"calcolato");
        rigaCorTblAnalisi++;
      }
      if (!"".equals(macroMicroElemIndFrutta.getPotassioPpmPDF()))
      {
        tblAnalisi.setFont (rigaCorTblAnalisi,1,new Font("SansSerif",Font.BOLD,8));
        tblAnalisi.setObject(rigaCorTblAnalisi,1,"Potassio");
        tblAnalisi.setObject(rigaCorTblAnalisi,2,macroMicroElemIndFrutta.getPotassioPpmPDF()+" ");
        tblAnalisi.setObject(rigaCorTblAnalisi,4,"ppm");
        tblAnalisi.setObject(rigaCorTblAnalisi,6,"interno");
        rigaCorTblAnalisi++;
      }
      if (!"".equals(macroMicroElemIndFrutta.getPotassioPDF()))
      {
        tblAnalisi.setObject(rigaCorTblAnalisi,1,"Potassio");
        tblAnalisi.setObject(rigaCorTblAnalisi,2,macroMicroElemIndFrutta.getPotassioPDF()+" ");
        tblAnalisi.setObject(rigaCorTblAnalisi,4,"%");
        tblAnalisi.setObject(rigaCorTblAnalisi,6,"calcolato");
        rigaCorTblAnalisi++;
      }
      if (!"".equals(macroMicroElemIndFrutta.getFerroPpmPDF()))
      {
        tblAnalisi.setObject(rigaCorTblAnalisi,1,"Ferro");
        tblAnalisi.setFont (rigaCorTblAnalisi,1,new Font("SansSerif",Font.BOLD,8));
        tblAnalisi.setObject(rigaCorTblAnalisi,2,macroMicroElemIndFrutta.getFerroPpmPDF()+" ");
        tblAnalisi.setObject(rigaCorTblAnalisi,4,"ppm");
        tblAnalisi.setObject(rigaCorTblAnalisi,6,"interno");
        rigaCorTblAnalisi++;
      }
      if (!"".equals(macroMicroElemIndFrutta.getManganesePpmPDF()))
      {
        tblAnalisi.setObject(rigaCorTblAnalisi,1,"Manganese");
        tblAnalisi.setFont (rigaCorTblAnalisi,1,new Font("SansSerif",Font.BOLD,8));
        tblAnalisi.setObject(rigaCorTblAnalisi,2,macroMicroElemIndFrutta.getManganesePpmPDF()+" ");
        tblAnalisi.setObject(rigaCorTblAnalisi,4,"ppm");
        tblAnalisi.setObject(rigaCorTblAnalisi,6,"interno");
        rigaCorTblAnalisi++;
      }
      if (!"".equals(macroMicroElemIndFrutta.getRamePpmPDF()))
      {
        tblAnalisi.setObject(rigaCorTblAnalisi,1,"Rame");
        tblAnalisi.setFont (rigaCorTblAnalisi,1,new Font("SansSerif",Font.BOLD,8));
        tblAnalisi.setObject(rigaCorTblAnalisi,2,macroMicroElemIndFrutta.getRamePpmPDF()+" ");
        tblAnalisi.setObject(rigaCorTblAnalisi,4,"ppm");
        tblAnalisi.setObject(rigaCorTblAnalisi,6,"interno");
        rigaCorTblAnalisi++;
      }
      if (!"".equals(macroMicroElemIndFrutta.getZincoPpmPDF()))
      {
        tblAnalisi.setObject(rigaCorTblAnalisi,1,"Zinco");
        tblAnalisi.setFont (rigaCorTblAnalisi,1,new Font("SansSerif",Font.BOLD,8));
        tblAnalisi.setObject(rigaCorTblAnalisi,2,macroMicroElemIndFrutta.getZincoPpmPDF()+" ");
        tblAnalisi.setObject(rigaCorTblAnalisi,4,"ppm");
        tblAnalisi.setObject(rigaCorTblAnalisi,6,"interno");
        rigaCorTblAnalisi++;
      }
      if (!"".equals(macroMicroElemIndFrutta.getBoroPpmPDF()))
      {
        tblAnalisi.setObject(rigaCorTblAnalisi,1,"Boro");
        tblAnalisi.setFont (rigaCorTblAnalisi,1,new Font("SansSerif",Font.BOLD,8));
        tblAnalisi.setObject(rigaCorTblAnalisi,2,macroMicroElemIndFrutta.getBoroPpmPDF()+" ");
        tblAnalisi.setObject(rigaCorTblAnalisi,4,"ppm");
        tblAnalisi.setObject(rigaCorTblAnalisi,6,"interno");
        rigaCorTblAnalisi++;
      }
    }

    xss.setElement("tblAnalisi", tblAnalisi);

    DefaultTextLens txtBoxData=new  DefaultTextLens();
    txtBoxData.setText("11/15/2004");
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

      ImagePainter firma = new ImagePainter(resp.getFirma());
      xss.setElement("imgFirma",firma);
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

}
