package it.csi.agrc.servlet;

import java.awt.*;
//import javax.servlet.*;
import javax.servlet.http.*;
import inetsoft.report.*;
import it.csi.agrc.*;
//import it.csi.cuneo.*;
import inetsoft.report.lens.*;

/**
 * <p>Title: Agrichim - Front Office</p>
 * <p>Description: Richiesta analisi chimiche su campioni biologici agrari</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: CSI Piemonte - Progettazione e Sviluppo - Cuneo</p>
 * @author Michele Piantà, Piergiorgio Chiriotti
 * @version 1.0.0
 */

public class PdfAnagraficaCampioneERB extends PdfAnagraficaCampione
{
  private static final long serialVersionUID = -8441668785487408294L;

  public PdfAnagraficaCampioneERB()
  {
    this.setOutputName("anagraficaCampioneERB.pdf");
    this.setTemplateName("pdfAnagraficaCampioneERB.srt");
  }

  protected void stampaPdf(HttpServletRequest request,
  		TabularSheet xss)
      throws Exception
  {
    // Serve per controllare l'autenticazione, impostare gli attributi
    // ereditati e stampare le parti comuni
    super.stampaPdf(request, xss);

    CampioneVegetaliErbacee cve = new CampioneVegetaliErbacee(dataSource, aut);
    cve.select(idRichiesta);

    tblLens = new DefaultTableLens(xss.getTable("tblInformazioni"));
    tblLens.setColBackground(0,PDF_COL_BACKGROUND);
    tblLens.setColForeground(0,Color.white);
    tblLens.setColBackground(2,PDF_COL_BACKGROUND);
    tblLens.setColForeground(2,Color.white);
    tblLens.setObject(0,1,cve.getDataCampionamento());
    tblLens.setObject(0,3,cve.getCampioneTerreno());
    xss.setElement("tblInformazioni", tblLens);

    tblLens = new DefaultTableLens(xss.getTable("tblColtura"));
    tblLens.setColBackground(0,PDF_COL_BACKGROUND);
    tblLens.setColForeground(0,Color.white);
    tblLens.setColBackground(2,PDF_COL_BACKGROUND);
    tblLens.setColForeground(2,Color.white);
    tblLens.setObject(0,1,cve.getColturaDesc());
    tblLens.setObject(0,3,cve.getSpecieDesc());
    xss.setElement("tblColtura", tblLens);

    java.util.Enumeration enumAnalisi=analisi.getCodiciAnalisi().elements();
    tblLens = new DefaultTableLens(xss.getTable("tblAnalisi"));
    tblLens.setColBackground(0,PDF_COL_BACKGROUND);
    tblLens.setColForeground(0,Color.white);
    tblLens.setColBackground(2,PDF_COL_BACKGROUND);
    tblLens.setColForeground(2,Color.white);
    tblLens.setColBackground(4,PDF_COL_BACKGROUND);
    tblLens.setColForeground(4,Color.white);
    tblLens.setColBackground(6,PDF_COL_BACKGROUND);
    tblLens.setColForeground(6,Color.white);
    while(enumAnalisi.hasMoreElements())
    {
      String codice=(String)enumAnalisi.nextElement();

      if (it.csi.agrc.Analisi.ANA_PACCHETTO_MICROELEMENTI.equals(codice))
      {
        tblLens.setObject(3,1,PDF_ANALISI_CHECKED);
        tblLens.setObject(2,1,PDF_ANALISI_CHECKED);
        tblLens.setObject(2,3,PDF_ANALISI_CHECKED);
        tblLens.setObject(2,5,PDF_ANALISI_CHECKED);
        tblLens.setObject(2,7,PDF_ANALISI_CHECKED);
      }

      if (it.csi.agrc.Analisi.ANA_PACCHETTO_TIPO.equals(codice))
      {
        tblLens.setObject(0,5,PDF_ANALISI_CHECKED);
        tblLens.setObject(0,3,PDF_ANALISI_CHECKED);
        tblLens.setObject(1,1,PDF_ANALISI_CHECKED);
        tblLens.setObject(1,3,PDF_ANALISI_CHECKED);
        tblLens.setObject(0,1,PDF_ANALISI_CHECKED);
      }

      if(Analisi.ANA_AZOTO.equals(codice))
        tblLens.setObject(0,1,PDF_ANALISI_CHECKED);
      if(Analisi.ANA_POTASSIO.equals(codice))
        tblLens.setObject(0,3,PDF_ANALISI_CHECKED);
      if(Analisi.ANA_MAGNESIO.equals(codice))
        tblLens.setObject(0,5,PDF_ANALISI_CHECKED);
      if(Analisi.ANA_FOSFORO.equals(codice))
        tblLens.setObject(1,1,PDF_ANALISI_CHECKED);
      if(Analisi.ANA_CALCIO.equals(codice))
        tblLens.setObject(1,3,PDF_ANALISI_CHECKED);
      if(Analisi.ANA_FERRO.equals(codice))
        tblLens.setObject(2,1,PDF_ANALISI_CHECKED);
      if(Analisi.ANA_MANGANESE.equals(codice))
        tblLens.setObject(2,3,PDF_ANALISI_CHECKED);
      if(Analisi.ANA_ZINCO.equals(codice))
        tblLens.setObject(2,5,PDF_ANALISI_CHECKED);
      if(Analisi.ANA_RAME.equals(codice))
        tblLens.setObject(2,7,PDF_ANALISI_CHECKED);
      if(Analisi.ANA_BORO.equals(codice))
        tblLens.setObject(3,1,PDF_ANALISI_CHECKED);
      if(Analisi.ANA_UMIDITA.equals(codice))
        tblLens.setObject(4,1,PDF_ANALISI_CHECKED);
    }
    xss.setElement("tblAnalisi", tblLens);

    tblLens = new DefaultTableLens(xss.getTable("tblNoteAggiuntive"));
    strTmp = analisi.getNote();
    tblLens.setObject(0,1,strTmp==null?"":strTmp);
    xss.setElement("tblNoteAggiuntive", tblLens);
  }
}