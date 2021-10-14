// Codici unicode per le lettere accentate da inserire nei PDF
// \u00EC = ì

package it.csi.agrc.servlet;

//import java.util.*;
import java.awt.*;
//import javax.servlet.*;
import javax.servlet.http.*;
import inetsoft.report.*;
import it.csi.agrc.*;
import it.csi.cuneo.Utili;
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

public class PdfAnagraficaCampioneFOGFRU extends PdfAnagraficaCampione
{
  private static final long serialVersionUID = -29013152077533371L;


  public PdfAnagraficaCampioneFOGFRU()
  {
    this.setOutputName("anagraficaCampioneFOGFRU.pdf");
    this.setTemplateName("pdfAnagraficaCampioneFOGFRU.srt");
  }

  protected void stampaPdf(HttpServletRequest request,
  		TabularSheet xss)
      throws Exception
  {
    // Serve per controllare l'autenticazione, impostare gli attributi
    // ereditati e stampare le parti comuni
    super.stampaPdf(request, xss);

    CampioneVegetaliFoglieFrutta cvff = new CampioneVegetaliFoglieFrutta(dataSource, aut);
    cvff.select(idRichiesta);

    tblLens = new DefaultTableLens(xss.getTable("tblInformazioni1"));
    tblLens.setColBackground(0,PDF_COL_BACKGROUND);
    tblLens.setColForeground(0,Color.white);
    tblLens.setColBackground(2,PDF_COL_BACKGROUND);
    tblLens.setColForeground(2,Color.white);
    tblLens.setObject(0,1,cvff.getDataCampionamento());
    tblLens.setObject(0,3,selDescr(beanColtura.getCodSuperficie(),
                                   beanColtura.getDescSuperficie(),
                                   cvff.getSuperficieAppezzamento()));
    xss.setElement("tblInformazioni1", tblLens);

    tblLens = new DefaultTableLens(xss.getTable("tblInformazioni2"));
    tblLens.setColBackground(0,PDF_COL_BACKGROUND);
    tblLens.setColForeground(0,Color.white);
    tblLens.setColBackground(2,PDF_COL_BACKGROUND);
    tblLens.setColForeground(2,Color.white);
    tblLens.setObject(0,1,"P".equals(cvff.getGiacitura())?"Piano":"Acclive");
    tblLens.setObject(0,3,selDescr(beanColtura.getCodEsposizione(),
                                   beanColtura.getDescEsposizione(),
                                   cvff.getIdEsposizione()));
    tblLens.setObject(1,1,"S".equals(cvff.getScheletro())?Utili.iso2unicode("Sì"):"No");
    tblLens.setObject(1,3,cvff.getAltitudineSlm());
    tblLens.setObject(2,1,cvff.getEtaImpianto());
    xss.setElement("tblInformazioni2", tblLens);

    tblLens = new DefaultTableLens(xss.getTable("tblColtura1"));
    tblLens.setColBackground(0,PDF_COL_BACKGROUND);
    tblLens.setColForeground(0,Color.white);
    tblLens.setColBackground(2,PDF_COL_BACKGROUND);
    tblLens.setColForeground(2,Color.white);
    tblLens.setObject(0,1,selDescr(beanColtura.getCodColtura(),
                                   beanColtura.getDescColtura(),
                                   cvff.getIdColtura()));
    cod.clear();
    desc.clear();
    coltura.selectCodDescSpecie(Coltura.IMPOSTA_QUERY_SPECIE,cod,desc,cvff.getIdColtura());
    tblLens.setObject(1,1,selDescr(cod,
                                   desc,
                                   cvff.getIdSpecie()));
    tblLens.setObject(1,3,cvff.getAltraSpecie());
    cod.clear();
    desc.clear();
    coltura.selectCodDescSpecie(Coltura.IMPOSTA_QUERY_VARIETA,cod,desc,cvff.getIdSpecie());
    tblLens.setObject(2,1,selDescr(cod,
                                   desc,
                                   cvff.getIdVarieta()));
    cod.clear();
    desc.clear();
    coltura.selectCodDescSpecie(Coltura.IMPOSTA_QUERY_INNESTO,cod,desc,cvff.getIdSpecie());
    tblLens.setObject(2,3,selDescr(cod,
                                   desc,
                                   cvff.getIdInnesto()));
    cod.clear();
    desc.clear();
    coltura.selectCodDescSpecie(Coltura.IMPOSTA_QUERY_ALLEVAMENTO,cod,desc,cvff.getIdSpecie());
    tblLens.setObject(3,1,selDescr(cod,
                                   desc,
                                   cvff.getIdSistemaAllevamento()));
    tblLens.setObject(3,3,cvff.getAltroAllevamento());
    xss.setElement("tblColtura1", tblLens);

    tblLens = new DefaultTableLens(xss.getTable("tblColtura2"));
    tblLens.setColBackground(0,PDF_COL_BACKGROUND);
    tblLens.setColForeground(0,Color.white);
    tblLens.setColBackground(2,PDF_COL_BACKGROUND);
    tblLens.setColForeground(2,Color.white);
    tblLens.setObject(0,1,cvff.getSestoImpianto1()==null?"":cvff.getSestoImpianto1().replace('.',','));
    tblLens.setObject(0,3,cvff.getSestoImpianto2()==null?"":cvff.getSestoImpianto2().replace('.',','));
    tblLens.setObject(1,1,cvff.getUnitaN());
    tblLens.setObject(1,3,cvff.getUnitaP2O5());
    tblLens.setObject(2,1,cvff.getUnitaK2O());
    tblLens.setObject(2,3,cvff.getUnitaMg());
    tblLens.setObject(3,1,cvff.getLetamazioneAnno()==null?"":cvff.getLetamazioneAnno().replace('.',','));
    tblLens.setObject(3,3,selDescr(beanColtura.getCodConcimazioneOrganica(),
                                   beanColtura.getDescConcimazioneOrganica(),
                                   cvff.getTipoConcimazione()));
    tblLens.setObject(4,1,selDescr(beanColtura.getCodConcimazione(),
                                   beanColtura.getDescConcimazione(),
                                   cvff.getIdConcime()));
    cod.clear();
    desc.clear();
    coltura.selectCodDesc(Coltura.IMPOSTA_QUERY_STADIO_FENOLOGICO,cod,desc);
    tblLens.setObject(4,3,selDescr(cod, desc,
                                   cvff.getIdStadioFenologico()));
    tblLens.setObject(5,1,selDescr(beanColtura.getCodProduttivita(),
                                   beanColtura.getDescProduttivita(),
                                   cvff.getCodiceProduttivita()));
    tblLens.setObject(5,3,cvff.getCampioneTerreno());
    xss.setElement("tblColtura2", tblLens);

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
