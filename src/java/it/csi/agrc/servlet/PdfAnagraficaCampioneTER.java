// Codici unicode per le lettere accentate da inserire nei PDF
// \u00EC = ì

package it.csi.agrc.servlet;

//import java.util.*;
import java.awt.*;

//import javax.servlet.*;
import javax.servlet.http.*;

import inetsoft.report.*;
import inetsoft.report.lens.*;
import it.csi.agrc.*;
//import it.csi.cuneo.*;
import it.csi.cuneo.ReportUtils;
import it.csi.cuneo.Utili;

/**
 * <p>Title: Agrichim - Front Office</p>
 * <p>Description: Richiesta analisi chimiche su campioni biologici agrari</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: CSI Piemonte - Progettazione e Sviluppo - Cuneo</p>
 * @author Michele Piantà, Piergiorgio Chiriotti
 * @version 1.0.0
 */

public class PdfAnagraficaCampioneTER extends PdfAnagraficaCampione
{
  private static final long serialVersionUID = -8482811997199012135L;

  public PdfAnagraficaCampioneTER()
  {
    this.setOutputName("anagraficaCampioneTER.pdf");
    this.setTemplateName("pdfAnagraficaCampioneTER.srt");
  }

  protected void stampaPdf(HttpServletRequest request,
  		TabularSheet xss)
      throws Exception
  {
    // Serve per controllare l'autenticazione, impostare gli attributi
    // ereditati e stampare le parti comuni
    super.stampaPdf(request, xss);

    CampioneTerrenoDati ctd = new CampioneTerrenoDati(dataSource, aut);
    ctd.select(idRichiesta);

    tblLens = new DefaultTableLens(xss.getTable("tblInformazioni"));
    tblLens.setColBackground(0,PDF_COL_BACKGROUND);
    tblLens.setColForeground(0,Color.white);
    tblLens.setColBackground(2,PDF_COL_BACKGROUND);
    tblLens.setColForeground(2,Color.white);
    tblLens.setObject(0,1,selDescr(beanColtura.getCodProfonditaPrelievo(),
                                   beanColtura.getDescProfonditaPrelievo(),
                                   ctd.getIdProfondita()));
    tblLens.setObject(0,3,"S".equals(ctd.getNuovoImpianto())?Utili.iso2unicode("Sì"):"No");
    xss.setElement("tblInformazioni", tblLens);

    //Style
    ReportUtils.formatTableHeaderOneRow(xss, "lblInformazioni", true);
    
    tblLens = new DefaultTableLens(xss.getTable("tblColturaVecchio"));
    tblLens.setColBackground(0,PDF_COL_BACKGROUND);
    tblLens.setColForeground(0,Color.white);
    tblLens.setColBackground(2,PDF_COL_BACKGROUND);
    tblLens.setColForeground(2,Color.white);
    tblLens.setObject(0,1,selDescr(beanColtura.getCodColtura(),
                                   beanColtura.getDescColtura(),
                                   ctd.getIdColturaAttuale()));
    cod.clear();
    desc.clear();
    coltura.selectCodDescSpecie(Coltura.IMPOSTA_QUERY_SPECIE,cod,desc,ctd.getIdColturaAttuale());
    tblLens.setObject(0,3,selDescr(cod,
                                   desc,
                                   ctd.getSpecieAttuale()));
    xss.setElement("tblColturaVecchio", tblLens);
    
    //Style
    ReportUtils.formatTableHeaderOneRow(xss, "lblColturaVecchio", true);    

    tblLens = new DefaultTableLens(xss.getTable("tblColturaNuovo1"));
    tblLens.setColBackground(0,PDF_COL_BACKGROUND);
    tblLens.setColForeground(0,Color.white);
    tblLens.setColBackground(2,PDF_COL_BACKGROUND);
    tblLens.setColForeground(2,Color.white);
    tblLens.setObject(0,1,selDescr(beanColtura.getCodColtura(),
                                   beanColtura.getDescColtura(),
                                   ctd.getIdColturaPrevista()));
    cod.clear();
    desc.clear();
    coltura.selectCodDescSpecie(Coltura.IMPOSTA_QUERY_SPECIE,cod,desc,ctd.getIdColturaPrevista());
    tblLens.setObject(0,3,selDescr(cod,
                                   desc,
                                   ctd.getSpeciePrevista()));
    
    //Style
    ReportUtils.formatTableHeaderOneRow(xss, "lblColturaNuovo", true);
    
    /*cod.clear();
    desc.clear();
    coltura.selectCodDescSpecie(Coltura.IMPOSTA_QUERY_VARIETA,cod,desc,ctd.getSpeciePrevista());
    tblLens.setObject(1,1,selDescr(cod,
                                   desc,
                                   ctd.getIdVarieta()));
    cod.clear();
    desc.clear();
    coltura.selectCodDescSpecie(Coltura.IMPOSTA_QUERY_INNESTO,cod,desc,ctd.getSpeciePrevista());
    tblLens.setObject(1,3,selDescr(cod,
                                   desc,
                                   ctd.getIdInnesto()));
    cod.clear();
    desc.clear();
    coltura.selectCodDescSpecie(Coltura.IMPOSTA_QUERY_ALLEVAMENTO,cod,desc,ctd.getSpeciePrevista());
    tblLens.setObject(2,1,selDescr(cod,
                                   desc,
                                   ctd.getIdSistemaAllevamento()));*/
    xss.setElement("tblColturaNuovo1", tblLens);

    tblLens = new DefaultTableLens(xss.getTable("tblColturaNuovo2"));
    tblLens.setColBackground(0,PDF_COL_BACKGROUND);
    tblLens.setColForeground(0,Color.white);
    tblLens.setColBackground(2,PDF_COL_BACKGROUND);
    tblLens.setColForeground(2,Color.white);
    strTmp=ctd.getAnnoImpianto();
    tblLens.setObject(0,1,strTmp==null?"":strTmp);
    strTmp=ctd.getProduzioneQha();
    tblLens.setObject(1,1,strTmp==null?"":strTmp.replace('.',','));
    tblLens.setObject(1,3,selDescr(beanColtura.getCodSuperficie(),
                                   beanColtura.getDescSuperficie(),
                                   ctd.getSuperficieAppezzamento()));
    tblLens.setObject(2,1,"P".equals(ctd.getGiacitura())?"Piano":"Acclive");
    tblLens.setObject(2,3,selDescr(beanColtura.getCodEsposizione(),
                                   beanColtura.getDescEsposizione(),
                                   ctd.getIdEsposizione()));
    tblLens.setObject(3,1,"S".equals(ctd.getScheletro())?Utili.iso2unicode("Sì"):"No");
    strTmp=ctd.getPercentualePietre();
    tblLens.setObject(3,3,strTmp==null?"":strTmp+"%");
    tblLens.setObject(4,1,"S".equals(ctd.getStoppie())?Utili.iso2unicode("Sì"):"No");
    tblLens.setObject(5,1,selDescr(beanColtura.getCodConcimazioneOrganica(),
                                   beanColtura.getDescConcimazioneOrganica(),
                                   ctd.getTipoConcimazione()));
    tblLens.setObject(5,3,selDescr(beanColtura.getCodConcimazione(),
                                   beanColtura.getDescConcimazione(),
                                   ctd.getIdConcime()));
    tblLens.setObject(6,1,selDescr(beanColtura.getCodLavorazioneTerreno(),
                                   beanColtura.getDescLavorazioneTerreno(),
                                   ctd.getIdLavorazioneTerreno()));
    tblLens.setObject(6,3,selDescr(beanColtura.getCodIrrigazione(),
                                   beanColtura.getDescIrrigazione(),
                                   ctd.getIdIrrigazione()));
    tblLens.setObject(7,1,selDescr(beanColtura.getCodModalitaColtivazione(),
                                   beanColtura.getDescModalitaColtivazione(),
                                   ctd.getCodiceModalitaColtivazione()));
    xss.setElement("tblColturaNuovo2", tblLens);

    java.util.Enumeration enumAnalisi=analisi.getCodiciAnalisi().elements();
    tblLens = new DefaultTableLens(xss.getTable("tblAnalisi1"));
    DefaultTableLens tblLens2 = new DefaultTableLens(xss.getTable("tblAnalisi2"));
    DefaultTableLens tblLens3 = new DefaultTableLens(xss.getTable("tblAnalisi3"));
    tblLens.setColBackground(0,PDF_COL_BACKGROUND);
    tblLens.setColForeground(0,Color.white);
    tblLens.setColBackground(2,PDF_COL_BACKGROUND);
    tblLens.setColForeground(2,Color.white);
    tblLens.setColBackground(4,PDF_COL_BACKGROUND);
    tblLens.setColForeground(4,Color.white);
    tblLens.setColBackground(6,PDF_COL_BACKGROUND);
    tblLens.setColForeground(6,Color.white);
    //tblLens2.setColAutoSize(true);
    //tblLens2.setRowAutoSize(true);
    //tblLens2.setColWidth(1,441);
    tblLens3.setColBackground(0,PDF_COL_BACKGROUND);
    tblLens3.setColForeground(0,Color.white);
    tblLens3.setColBackground(2,PDF_COL_BACKGROUND);
    tblLens3.setColForeground(2,Color.white);
    tblLens3.setColBackground(4,PDF_COL_BACKGROUND);
    tblLens3.setColForeground(4,Color.white);
    tblLens3.setColBackground(6,PDF_COL_BACKGROUND);
    tblLens3.setColForeground(6,Color.white);
    while(enumAnalisi.hasMoreElements())
    {
      String codice=(String)enumAnalisi.nextElement();

      if (it.csi.agrc.Analisi.ANA_PACCHETTO_MICROELEMENTI.equals(codice))
      {
        tblLens3.setObject(1,1,PDF_ANALISI_CHECKED);
        tblLens3.setObject(1,3,PDF_ANALISI_CHECKED);
        tblLens3.setObject(1,5,PDF_ANALISI_CHECKED);
        tblLens3.setObject(1,7,PDF_ANALISI_CHECKED);
      }
      if (it.csi.agrc.Analisi.ANA_PACCHETTO_TIPO.equals(codice))
      {
        tblLens.setObject(0,1,PDF_ANALISI_CHECKED);
        tblLens.setObject(2,3,PDF_ANALISI_CHECKED);
        tblLens.setObject(2,1,PDF_ANALISI_CHECKED);
        tblLens.setObject(2,5,PDF_ANALISI_CHECKED);
        tblLens.setObject(0,5,PDF_ANALISI_CHECKED);
        tblLens.setObject(1,1,PDF_ANALISI_CHECKED);
        tblLens.setObject(1,5,PDF_ANALISI_CHECKED);
        tblLens.setObject(0,3,PDF_ANALISI_CHECKED);
        tblLens.setObject(1,3,PDF_ANALISI_CHECKED);
      }
      if (it.csi.agrc.Analisi.ANA_PACCHETTO_COMP_SCAMBIO.equals(codice))
      {
        tblLens.setObject(2,1,PDF_ANALISI_CHECKED);
        tblLens.setObject(0,5,PDF_ANALISI_CHECKED);
        tblLens.setObject(1,1,PDF_ANALISI_CHECKED);
        tblLens.setObject(0,3,PDF_ANALISI_CHECKED);
      }

      //AGRICHIM-44
      //Metalli pesanti
      /*if (it.csi.agrc.Analisi.ANA_PACCHETTO_METALLI_PESANTI.equals(codice))
      {
      	//Tutto il pacchetto
        checkMetalliPesanti(tblLens3, null, true);
      }
      else
      {*/
      	//Solo gli elementi presenti nell'analisi
        checkMetalliPesanti(tblLens3, codice, false);
      //}

      if(Analisi.ANA_PH.equals(codice))
        tblLens.setObject(0,1,PDF_ANALISI_CHECKED);
      if(Analisi.ANA_CALCIO.equals(codice))
        tblLens.setObject(0,3,PDF_ANALISI_CHECKED);
      if(Analisi.ANA_MAGNESIO.equals(codice))
        tblLens.setObject(0,5,PDF_ANALISI_CHECKED);
      if(Analisi.ANA_POTASSIO.equals(codice))
        tblLens.setObject(1,1,PDF_ANALISI_CHECKED);
      if(Analisi.ANA_AZOTO.equals(codice))
        tblLens.setObject(1,3,PDF_ANALISI_CHECKED);
      if(Analisi.ANA_FOSFORO.equals(codice))
        tblLens.setObject(1,5,PDF_ANALISI_CHECKED);
      if(Analisi.ANA_CAPACITASCAMBIOCATIONICO.equals(codice))
        tblLens.setObject(2,1,PDF_ANALISI_CHECKED);
      if(Analisi.ANA_SOSTANZAORGANICA.equals(codice))
        tblLens.setObject(2,3,PDF_ANALISI_CHECKED);
      if(Analisi.ANA_CALCARETOTALE.equals(codice))
        tblLens.setObject(2,5,PDF_ANALISI_CHECKED);
      if(Analisi.ANA_CALCAREATTIVO.equals(codice))
        tblLens.setObject(2,7,PDF_ANALISI_CHECKED);

      if(Analisi.ANA_STANDARD.equals(codice))
        tblLens2.setObject(0,1,PDF_ANALISI_CHECKED
                               +"     Standard (presenza di Limo, Sabbia e Argilla)");
      else if(Analisi.ANA_A4FRAZIONI.equals(codice))
        tblLens2.setObject(0,1,PDF_ANALISI_CHECKED
                               +"     a 4 frazioni (Limo grosso, Limo fine, Sabbia e Argilla)");
      else if(Analisi.ANA_A5FRAZIONI.equals(codice))
        tblLens2.setObject(0,1,PDF_ANALISI_CHECKED
                               +"     a 5 frazioni (Limo grosso, Limo fine, Sabbia grossa, Sabbia fine e Argilla)");

      if(Analisi.ANA_SALINITA.equals(codice))
        tblLens3.setObject(0,1,PDF_ANALISI_CHECKED);
      if(Analisi.ANA_FERRO.equals(codice))
        tblLens3.setObject(1,1,PDF_ANALISI_CHECKED);
      if(Analisi.ANA_MANGANESE.equals(codice))
        tblLens3.setObject(1,3,PDF_ANALISI_CHECKED);
      if(Analisi.ANA_ZINCO.equals(codice))
        tblLens3.setObject(1,5,PDF_ANALISI_CHECKED);
      if(Analisi.ANA_RAME.equals(codice))
        tblLens3.setObject(1,7,PDF_ANALISI_CHECKED);
      if(Analisi.ANA_BORO.equals(codice))
        tblLens3.setObject(2,1,PDF_ANALISI_CHECKED);
     /* if(Analisi.ANA_UMIDITA.equals(codice))
        tblLens3.setObject(3,1,PDF_ANALISI_CHECKED);
        */
    }
    xss.setElement("tblAnalisi1", tblLens);
    xss.setElement("tblAnalisi2", tblLens2);
    xss.setElement("tblAnalisi3", tblLens3);

    //Style
    ReportUtils.formatTableHeaderOneRow(xss, "lblAnalisi", true);
    ReportUtils.formatTableColumLeftRight(xss, "tblAnalisi2", false);

    tblLens = new DefaultTableLens(xss.getTable("tblNoteAggiuntive"));
    strTmp = analisi.getNote();
    tblLens.setObject(0,1,strTmp==null?"":strTmp);
    xss.setElement("tblNoteAggiuntive", tblLens);
    
    //Style
    ReportUtils.formatTableColumLeftRight(xss, "tblNoteAggiuntive", false);    
  }

  private void checkMetalliPesanti(DefaultTableLens tblLens, String codice, boolean allChecked)
  {
    if (allChecked || Analisi.ANA_FERRO_TOTALE.equals(codice))
    {
    	setCellChecked(tblLens, 3, 1, true);
    }
    if (allChecked || Analisi.ANA_MANGANESE_TOTALE.equals(codice))
    {
    	setCellChecked(tblLens, 3, 3, true);
    }
    if (allChecked || Analisi.ANA_ZINCO_TOTALE.equals(codice))
    {
    	setCellChecked(tblLens, 3, 5, true);
    }
    if (allChecked || Analisi.ANA_RAME_TOTALE.equals(codice))
    {
    	setCellChecked(tblLens, 3, 7, true);
    }
    /*
    if (allChecked || Analisi.ANA_BORO_TOTALE.equals(codice))
    {
    	setCellChecked(tblLens, 5, 1, true);
    }
    */
    if (allChecked || Analisi.ANA_CADMIO_TOTALE.equals(codice))
    {
    	setCellChecked(tblLens, 4, 3, true);
    }
    if (allChecked || Analisi.ANA_CROMO_TOTALE.equals(codice))
    {
    	setCellChecked(tblLens, 4, 5, true);
    }
    if (allChecked || Analisi.ANA_NICHEL_TOTALE.equals(codice))
    {
    	setCellChecked(tblLens, 4, 7, true);
    }
   
    if (allChecked || Analisi.ANA_PIOMBO_TOTALE.equals(codice))
    {
    	setCellChecked(tblLens, 4, 1, true);
    }
    
    /*
    if (allChecked || Analisi.ANA_STRONZIO_TOTALE.equals(codice))
    {
    	setCellChecked(tblLens, 6, 3, true);
    }
    *(
    /*if (allChecked || Analisi.ANA_ALTRO_METALLO_TOTALE.equals(codice))
    {
    	setCellChecked(tblLens, 6, 5, true);
    }*/
  }
}