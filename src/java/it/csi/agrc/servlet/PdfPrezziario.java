package it.csi.agrc.servlet;

//import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import it.csi.agrc.*;
import it.csi.cuneo.*;

//import java.awt.*;
import java.util.*;
import inetsoft.report.*;
//import inetsoft.report.internal.*;
import inetsoft.report.painter.*;
import inetsoft.report.lens.*;

/**
 * <p>Title: Agrichim - Front Office</p>
 * <p>Description: Richiesta analisi chimiche su campioni biologici agrari</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: CSI Piemonte - Progettazione e Sviluppo - Cuneo</p>
 * @author Michele Piantà, Piergiorgio Chiriotti
 * @version 1.0.0
 */

public class PdfPrezziario extends PdfServletAgrc
{
  private static final long serialVersionUID = 6543870506189744002L;

  public PdfPrezziario()
  {
    this.setOutputName("prezziario.pdf");
    this.setTemplateName("pdfPrezziario.srt");
  }

  protected void stampaPdf(HttpServletRequest request,
  		TabularSheet xss)
      throws Exception
  {
    // Questo PDF va generato solo se è stato effettuato con successo il login
    HttpSession session = request.getSession();
    Autenticazione aut = (Autenticazione)session.getAttribute("aut");
    Object dataSource;
    controllaAut(aut);

    ServletContext context=session.getServletContext();
    if (Utili.POOLMAN)
      dataSource=context.getAttribute("poolBean");
    else
      dataSource=context.getAttribute("dataSourceBean");
    
    //Footer
    ReportUtils.setFooter(xss);

    //CuneoLogger.debug(this,"PdfPrezziario.stampaPdf() - Inizio creazione pdf");
    ImagePainter logoRegione = new ImagePainter(this.getImage("logoRegione.gif"));
    xss.setElement("imgLogoRegione",logoRegione);

    //Impostazione dei dati relativi alla testa del PDF: sono dati che si trovano
    //all'interno della tabella parametro e vengono precaricati all'avvio dell'applicativo
    // nel bean BeanParametri
    BeanParametri beanParametriApplication=(BeanParametri)context.getAttribute("beanParametriApplication");


    Laboratorio laboratorio = new Laboratorio(dataSource, aut);
    laboratorio.select(null,beanParametriApplication.getPartitaIVALab());
    /***************************************************************
     * Header comune a tutte le pagine
     */
    if (laboratorio.getCodiceLaboratorio()!=null)
    {
      xss.setElement("tbxIndirizzo",laboratorio.getIndirizzoPdf());
    }

    String direzione = "";
    
    if(beanParametriApplication.getDirezione()!=null)
    {
    	direzione = beanParametriApplication.getDirezione();
    }
    
    xss.setElement("tbxAssessorato",beanParametriApplication.getAssessorato()+"\n"+direzione);
    
    //xss.setElement("tbxAssessorato",beanParametriApplication.getAssessorato()+"\n"+beanParametriApplication.getDirezione());


    BeanAnalisi beanAnalisi = (BeanAnalisi)getServletContext().getAttribute("beanAnalisi");
    Hashtable analisi = beanAnalisi.getAnalisi();
    Hashtable costoAnalisi = beanAnalisi.getCostoAnalisi();
    fillTerreno( analisi, costoAnalisi, xss );
    fillFrutta( analisi, costoAnalisi, xss );
    fillErbacee( analisi, costoAnalisi, xss );
    fillFoglie( analisi, costoAnalisi, xss );
    //CuneoLogger.debug(this,"PdfPrezziario.stampaPdf() - Fine creazione pdf");
  }

  private void fillTerreno( Hashtable analisi, Hashtable costoAnalisi,
  		ReportSheet xss )
  {
    String elementName = "tblTerreno";

    DefaultTableLens tblMateriale = new DefaultTableLens(xss.getTable(elementName));

    /*tblMateriale.setColWidth(0,80);
    tblMateriale.setColWidth(1,50);
    tblMateriale.setColWidth(2,60);
    tblMateriale.setColWidth(3,60);
    tblMateriale.setColWidth(4,60);*/

    //IMPOSTO L'ALLINEAMENTO delle colonne dei prezzi
    for (int i=0;i<=20;i++)
    {
     tblMateriale.setAlignment(i,1,StyleConstants.H_RIGHT);
     tblMateriale.setAlignment(i,2,StyleConstants.H_RIGHT);
     tblMateriale.setAlignment(i,3,StyleConstants.H_RIGHT);
     tblMateriale.setAlignment(i,4,StyleConstants.H_RIGHT);
    }

    tblMateriale.setObject(1,0,analisi.get(Analisi.ANA_PH));
    tblMateriale.setObject(2,0,analisi.get(Analisi.ANA_CALCIO));
    tblMateriale.setObject(3,0,analisi.get(Analisi.ANA_MAGNESIO));
    tblMateriale.setObject(4,0,analisi.get(Analisi.ANA_POTASSIO));
    tblMateriale.setObject(5,0,analisi.get(Analisi.ANA_AZOTO));
    tblMateriale.setObject(6,0,analisi.get(Analisi.ANA_FOSFORO));
    tblMateriale.setObject(7,0,analisi.get(Analisi.ANA_CAPACITASCAMBIOCATIONICO));
    tblMateriale.setObject(8,0,analisi.get(Analisi.ANA_SOSTANZAORGANICA));
    tblMateriale.setObject(9,0,analisi.get(Analisi.ANA_CALCARETOTALE));
    tblMateriale.setObject(10,0,analisi.get(Analisi.ANA_CALCAREATTIVO));
    tblMateriale.setObject(11,0,analisi.get(Analisi.ANA_STANDARD));
    tblMateriale.setObject(12,0,analisi.get(Analisi.ANA_A4FRAZIONI));
    tblMateriale.setObject(13,0,analisi.get(Analisi.ANA_A5FRAZIONI));
    tblMateriale.setObject(14,0,analisi.get(Analisi.ANA_SALINITA));
    tblMateriale.setObject(15,0,analisi.get(Analisi.ANA_FERRO));
    tblMateriale.setObject(16,0,analisi.get(Analisi.ANA_MANGANESE));
    tblMateriale.setObject(17,0,analisi.get(Analisi.ANA_ZINCO));
    tblMateriale.setObject(18,0,analisi.get(Analisi.ANA_RAME));
    tblMateriale.setObject(19,0,analisi.get(Analisi.ANA_BORO));
    tblMateriale.setObject(20,0,analisi.get(Analisi.ANA_UMIDITA));

    tblMateriale.setObject(1,1,Utili.valuta(costoAnalisi.get(Analisi.ANA_PH+Analisi.MAT_TERRENO+Analisi.PREZZO_INTERO)));
    tblMateriale.setObject(2,1,Utili.valuta(costoAnalisi.get(Analisi.ANA_CALCIO+Analisi.MAT_TERRENO+Analisi.PREZZO_INTERO)));
    tblMateriale.setObject(3,1,Utili.valuta(costoAnalisi.get(Analisi.ANA_MAGNESIO+Analisi.MAT_TERRENO+Analisi.PREZZO_INTERO)));
    tblMateriale.setObject(4,1,Utili.valuta(costoAnalisi.get(Analisi.ANA_POTASSIO+Analisi.MAT_TERRENO+Analisi.PREZZO_INTERO)));
    tblMateriale.setObject(5,1,Utili.valuta(costoAnalisi.get(Analisi.ANA_AZOTO+Analisi.MAT_TERRENO+Analisi.PREZZO_INTERO)));
    tblMateriale.setObject(6,1,Utili.valuta(costoAnalisi.get(Analisi.ANA_FOSFORO+Analisi.MAT_TERRENO+Analisi.PREZZO_INTERO)));
    tblMateriale.setObject(7,1,Utili.valuta(costoAnalisi.get(Analisi.ANA_CAPACITASCAMBIOCATIONICO+Analisi.MAT_TERRENO+Analisi.PREZZO_INTERO)));
    tblMateriale.setObject(8,1,Utili.valuta(costoAnalisi.get(Analisi.ANA_SOSTANZAORGANICA+Analisi.MAT_TERRENO+Analisi.PREZZO_INTERO)));
    tblMateriale.setObject(9,1,Utili.valuta(costoAnalisi.get(Analisi.ANA_CALCARETOTALE+Analisi.MAT_TERRENO+Analisi.PREZZO_INTERO)));
    tblMateriale.setObject(10,1,Utili.valuta(costoAnalisi.get(Analisi.ANA_CALCAREATTIVO+Analisi.MAT_TERRENO+Analisi.PREZZO_INTERO)));
    tblMateriale.setObject(11,1,Utili.valuta(costoAnalisi.get(Analisi.ANA_STANDARD+Analisi.MAT_TERRENO+Analisi.PREZZO_INTERO)));
    tblMateriale.setObject(12,1,Utili.valuta(costoAnalisi.get(Analisi.ANA_A4FRAZIONI+Analisi.MAT_TERRENO+Analisi.PREZZO_INTERO)));
    tblMateriale.setObject(13,1,Utili.valuta(costoAnalisi.get(Analisi.ANA_A5FRAZIONI+Analisi.MAT_TERRENO+Analisi.PREZZO_INTERO)));
    tblMateriale.setObject(14,1,Utili.valuta(costoAnalisi.get(Analisi.ANA_SALINITA+Analisi.MAT_TERRENO+Analisi.PREZZO_INTERO)));
    tblMateriale.setObject(15,1,Utili.valuta(costoAnalisi.get(Analisi.ANA_FERRO+Analisi.MAT_TERRENO+Analisi.PREZZO_INTERO)));
    tblMateriale.setObject(16,1,Utili.valuta(costoAnalisi.get(Analisi.ANA_MANGANESE+Analisi.MAT_TERRENO+Analisi.PREZZO_INTERO)));
    tblMateriale.setObject(17,1,Utili.valuta(costoAnalisi.get(Analisi.ANA_ZINCO+Analisi.MAT_TERRENO+Analisi.PREZZO_INTERO)));
    tblMateriale.setObject(18,1,Utili.valuta(costoAnalisi.get(Analisi.ANA_RAME+Analisi.MAT_TERRENO+Analisi.PREZZO_INTERO)));
    tblMateriale.setObject(19,1,Utili.valuta(costoAnalisi.get(Analisi.ANA_BORO+Analisi.MAT_TERRENO+Analisi.PREZZO_INTERO)));
    tblMateriale.setObject(20,1,Utili.valuta(costoAnalisi.get(Analisi.ANA_UMIDITA+Analisi.MAT_TERRENO+Analisi.PREZZO_INTERO)));

    tblMateriale.setObject(1,2,Utili.valuta(costoAnalisi.get(Analisi.ANA_PH+Analisi.MAT_TERRENO+Analisi.RIDUZIONE_1)));
    tblMateriale.setObject(2,2,Utili.valuta(costoAnalisi.get(Analisi.ANA_CALCIO+Analisi.MAT_TERRENO+Analisi.RIDUZIONE_1)));
    tblMateriale.setObject(3,2,Utili.valuta(costoAnalisi.get(Analisi.ANA_MAGNESIO+Analisi.MAT_TERRENO+Analisi.RIDUZIONE_1)));
    tblMateriale.setObject(4,2,Utili.valuta(costoAnalisi.get(Analisi.ANA_POTASSIO+Analisi.MAT_TERRENO+Analisi.RIDUZIONE_1)));
    tblMateriale.setObject(5,2,Utili.valuta(costoAnalisi.get(Analisi.ANA_AZOTO+Analisi.MAT_TERRENO+Analisi.RIDUZIONE_1)));
    tblMateriale.setObject(6,2,Utili.valuta(costoAnalisi.get(Analisi.ANA_FOSFORO+Analisi.MAT_TERRENO+Analisi.RIDUZIONE_1)));
    tblMateriale.setObject(7,2,Utili.valuta(costoAnalisi.get(Analisi.ANA_CAPACITASCAMBIOCATIONICO+Analisi.MAT_TERRENO+Analisi.RIDUZIONE_1)));
    tblMateriale.setObject(8,2,Utili.valuta(costoAnalisi.get(Analisi.ANA_SOSTANZAORGANICA+Analisi.MAT_TERRENO+Analisi.RIDUZIONE_1)));
    tblMateriale.setObject(9,2,Utili.valuta(costoAnalisi.get(Analisi.ANA_CALCARETOTALE+Analisi.MAT_TERRENO+Analisi.RIDUZIONE_1)));
    tblMateriale.setObject(10,2,Utili.valuta(costoAnalisi.get(Analisi.ANA_CALCAREATTIVO+Analisi.MAT_TERRENO+Analisi.RIDUZIONE_1)));
    tblMateriale.setObject(11,2,Utili.valuta(costoAnalisi.get(Analisi.ANA_STANDARD+Analisi.MAT_TERRENO+Analisi.RIDUZIONE_1)));
    tblMateriale.setObject(12,2,Utili.valuta(costoAnalisi.get(Analisi.ANA_A4FRAZIONI+Analisi.MAT_TERRENO+Analisi.RIDUZIONE_1)));
    tblMateriale.setObject(13,2,Utili.valuta(costoAnalisi.get(Analisi.ANA_A5FRAZIONI+Analisi.MAT_TERRENO+Analisi.RIDUZIONE_1)));
    tblMateriale.setObject(14,2,Utili.valuta(costoAnalisi.get(Analisi.ANA_SALINITA+Analisi.MAT_TERRENO+Analisi.RIDUZIONE_1)));
    tblMateriale.setObject(15,2,Utili.valuta(costoAnalisi.get(Analisi.ANA_FERRO+Analisi.MAT_TERRENO+Analisi.RIDUZIONE_1)));
    tblMateriale.setObject(16,2,Utili.valuta(costoAnalisi.get(Analisi.ANA_MANGANESE+Analisi.MAT_TERRENO+Analisi.RIDUZIONE_1)));
    tblMateriale.setObject(17,2,Utili.valuta(costoAnalisi.get(Analisi.ANA_ZINCO+Analisi.MAT_TERRENO+Analisi.RIDUZIONE_1)));
    tblMateriale.setObject(18,2,Utili.valuta(costoAnalisi.get(Analisi.ANA_RAME+Analisi.MAT_TERRENO+Analisi.RIDUZIONE_1)));
    tblMateriale.setObject(19,2,Utili.valuta(costoAnalisi.get(Analisi.ANA_BORO+Analisi.MAT_TERRENO+Analisi.RIDUZIONE_1)));
    tblMateriale.setObject(20,2,Utili.valuta(costoAnalisi.get(Analisi.ANA_UMIDITA+Analisi.MAT_TERRENO+Analisi.RIDUZIONE_1)));

    tblMateriale.setObject(1,3,Utili.valuta(costoAnalisi.get(Analisi.ANA_PH+Analisi.MAT_TERRENO+Analisi.RIDUZIONE_2)));
    tblMateriale.setObject(2,3,Utili.valuta(costoAnalisi.get(Analisi.ANA_CALCIO+Analisi.MAT_TERRENO+Analisi.RIDUZIONE_2)));
    tblMateriale.setObject(3,3,Utili.valuta(costoAnalisi.get(Analisi.ANA_MAGNESIO+Analisi.MAT_TERRENO+Analisi.RIDUZIONE_2)));
    tblMateriale.setObject(4,3,Utili.valuta(costoAnalisi.get(Analisi.ANA_POTASSIO+Analisi.MAT_TERRENO+Analisi.RIDUZIONE_2)));
    tblMateriale.setObject(5,3,Utili.valuta(costoAnalisi.get(Analisi.ANA_AZOTO+Analisi.MAT_TERRENO+Analisi.RIDUZIONE_2)));
    tblMateriale.setObject(6,3,Utili.valuta(costoAnalisi.get(Analisi.ANA_FOSFORO+Analisi.MAT_TERRENO+Analisi.RIDUZIONE_2)));
    tblMateriale.setObject(7,3,Utili.valuta(costoAnalisi.get(Analisi.ANA_CAPACITASCAMBIOCATIONICO+Analisi.MAT_TERRENO+Analisi.RIDUZIONE_2)));
    tblMateriale.setObject(8,3,Utili.valuta(costoAnalisi.get(Analisi.ANA_SOSTANZAORGANICA+Analisi.MAT_TERRENO+Analisi.RIDUZIONE_2)));
    tblMateriale.setObject(9,3,Utili.valuta(costoAnalisi.get(Analisi.ANA_CALCARETOTALE+Analisi.MAT_TERRENO+Analisi.RIDUZIONE_2)));
    tblMateriale.setObject(10,3,Utili.valuta(costoAnalisi.get(Analisi.ANA_CALCAREATTIVO+Analisi.MAT_TERRENO+Analisi.RIDUZIONE_2)));
    tblMateriale.setObject(11,3,Utili.valuta(costoAnalisi.get(Analisi.ANA_STANDARD+Analisi.MAT_TERRENO+Analisi.RIDUZIONE_2)));
    tblMateriale.setObject(12,3,Utili.valuta(costoAnalisi.get(Analisi.ANA_A4FRAZIONI+Analisi.MAT_TERRENO+Analisi.RIDUZIONE_2)));
    tblMateriale.setObject(13,3,Utili.valuta(costoAnalisi.get(Analisi.ANA_A5FRAZIONI+Analisi.MAT_TERRENO+Analisi.RIDUZIONE_2)));
    tblMateriale.setObject(14,3,Utili.valuta(costoAnalisi.get(Analisi.ANA_SALINITA+Analisi.MAT_TERRENO+Analisi.RIDUZIONE_2)));
    tblMateriale.setObject(15,3,Utili.valuta(costoAnalisi.get(Analisi.ANA_FERRO+Analisi.MAT_TERRENO+Analisi.RIDUZIONE_2)));
    tblMateriale.setObject(16,3,Utili.valuta(costoAnalisi.get(Analisi.ANA_MANGANESE+Analisi.MAT_TERRENO+Analisi.RIDUZIONE_2)));
    tblMateriale.setObject(17,3,Utili.valuta(costoAnalisi.get(Analisi.ANA_ZINCO+Analisi.MAT_TERRENO+Analisi.RIDUZIONE_2)));
    tblMateriale.setObject(18,3,Utili.valuta(costoAnalisi.get(Analisi.ANA_RAME+Analisi.MAT_TERRENO+Analisi.RIDUZIONE_2)));
    tblMateriale.setObject(19,3,Utili.valuta(costoAnalisi.get(Analisi.ANA_BORO+Analisi.MAT_TERRENO+Analisi.RIDUZIONE_2)));
    tblMateriale.setObject(20,3,Utili.valuta(costoAnalisi.get(Analisi.ANA_UMIDITA+Analisi.MAT_TERRENO+Analisi.RIDUZIONE_2)));

    tblMateriale.setObject(1,4,Utili.valuta(costoAnalisi.get(Analisi.ANA_PH+Analisi.MAT_TERRENO+Analisi.RIDUZIONE_3)));
    tblMateriale.setObject(2,4,Utili.valuta(costoAnalisi.get(Analisi.ANA_CALCIO+Analisi.MAT_TERRENO+Analisi.RIDUZIONE_3)));
    tblMateriale.setObject(3,4,Utili.valuta(costoAnalisi.get(Analisi.ANA_MAGNESIO+Analisi.MAT_TERRENO+Analisi.RIDUZIONE_3)));
    tblMateriale.setObject(4,4,Utili.valuta(costoAnalisi.get(Analisi.ANA_POTASSIO+Analisi.MAT_TERRENO+Analisi.RIDUZIONE_3)));
    tblMateriale.setObject(5,4,Utili.valuta(costoAnalisi.get(Analisi.ANA_AZOTO+Analisi.MAT_TERRENO+Analisi.RIDUZIONE_3)));
    tblMateriale.setObject(6,4,Utili.valuta(costoAnalisi.get(Analisi.ANA_FOSFORO+Analisi.MAT_TERRENO+Analisi.RIDUZIONE_3)));
    tblMateriale.setObject(7,4,Utili.valuta(costoAnalisi.get(Analisi.ANA_CAPACITASCAMBIOCATIONICO+Analisi.MAT_TERRENO+Analisi.RIDUZIONE_3)));
    tblMateriale.setObject(8,4,Utili.valuta(costoAnalisi.get(Analisi.ANA_SOSTANZAORGANICA+Analisi.MAT_TERRENO+Analisi.RIDUZIONE_3)));
    tblMateriale.setObject(9,4,Utili.valuta(costoAnalisi.get(Analisi.ANA_CALCARETOTALE+Analisi.MAT_TERRENO+Analisi.RIDUZIONE_3)));
    tblMateriale.setObject(10,4,Utili.valuta(costoAnalisi.get(Analisi.ANA_CALCAREATTIVO+Analisi.MAT_TERRENO+Analisi.RIDUZIONE_3)));
    tblMateriale.setObject(11,4,Utili.valuta(costoAnalisi.get(Analisi.ANA_STANDARD+Analisi.MAT_TERRENO+Analisi.RIDUZIONE_3)));
    tblMateriale.setObject(12,4,Utili.valuta(costoAnalisi.get(Analisi.ANA_A4FRAZIONI+Analisi.MAT_TERRENO+Analisi.RIDUZIONE_3)));
    tblMateriale.setObject(13,4,Utili.valuta(costoAnalisi.get(Analisi.ANA_A5FRAZIONI+Analisi.MAT_TERRENO+Analisi.RIDUZIONE_3)));
    tblMateriale.setObject(14,4,Utili.valuta(costoAnalisi.get(Analisi.ANA_SALINITA+Analisi.MAT_TERRENO+Analisi.RIDUZIONE_3)));
    tblMateriale.setObject(15,4,Utili.valuta(costoAnalisi.get(Analisi.ANA_FERRO+Analisi.MAT_TERRENO+Analisi.RIDUZIONE_3)));
    tblMateriale.setObject(16,4,Utili.valuta(costoAnalisi.get(Analisi.ANA_MANGANESE+Analisi.MAT_TERRENO+Analisi.RIDUZIONE_3)));
    tblMateriale.setObject(17,4,Utili.valuta(costoAnalisi.get(Analisi.ANA_ZINCO+Analisi.MAT_TERRENO+Analisi.RIDUZIONE_3)));
    tblMateriale.setObject(18,4,Utili.valuta(costoAnalisi.get(Analisi.ANA_RAME+Analisi.MAT_TERRENO+Analisi.RIDUZIONE_3)));
    tblMateriale.setObject(19,4,Utili.valuta(costoAnalisi.get(Analisi.ANA_BORO+Analisi.MAT_TERRENO+Analisi.RIDUZIONE_3)));
    tblMateriale.setObject(20,4,Utili.valuta(costoAnalisi.get(Analisi.ANA_UMIDITA+Analisi.MAT_TERRENO+Analisi.RIDUZIONE_3)));

    xss.setElement(elementName, tblMateriale);
  }

  private void fillFrutta( Hashtable analisi, Hashtable costoAnalisi,
  		ReportSheet xss )
  {
    String elementName = "tblFrutta";

    DefaultTableLens tblMateriale = new DefaultTableLens(xss.getTable(elementName));

    //IMPOSTO L'ALLINEAMENTO delle colonne dei prezzi
    for (int i=0;i<=6;i++)
    {
     tblMateriale.setAlignment(i,1,StyleConstants.H_RIGHT);
     tblMateriale.setAlignment(i,2,StyleConstants.H_RIGHT);
     tblMateriale.setAlignment(i,3,StyleConstants.H_RIGHT);
     tblMateriale.setAlignment(i,4,StyleConstants.H_RIGHT);
    }

    tblMateriale.setObject(1,0,analisi.get(Analisi.ANA_CALCIO));
    tblMateriale.setObject(2,0,analisi.get(Analisi.ANA_MAGNESIO));
    tblMateriale.setObject(3,0,analisi.get(Analisi.ANA_POTASSIO));
    tblMateriale.setObject(4,0,analisi.get(Analisi.ANA_AZOTO));
    tblMateriale.setObject(5,0,analisi.get(Analisi.ANA_FOSFORO));
    tblMateriale.setObject(6,0,analisi.get(Analisi.ANA_UMIDITA));

    tblMateriale.setObject(1,1,Utili.valuta(costoAnalisi.get(Analisi.ANA_CALCIO+Analisi.MAT_FRUTTA+Analisi.PREZZO_INTERO)));
    tblMateriale.setObject(2,1,Utili.valuta(costoAnalisi.get(Analisi.ANA_MAGNESIO+Analisi.MAT_FRUTTA+Analisi.PREZZO_INTERO)));
    tblMateriale.setObject(3,1,Utili.valuta(costoAnalisi.get(Analisi.ANA_POTASSIO+Analisi.MAT_FRUTTA+Analisi.PREZZO_INTERO)));
    tblMateriale.setObject(4,1,Utili.valuta(costoAnalisi.get(Analisi.ANA_AZOTO+Analisi.MAT_FRUTTA+Analisi.PREZZO_INTERO)));
    tblMateriale.setObject(5,1,Utili.valuta(costoAnalisi.get(Analisi.ANA_FOSFORO+Analisi.MAT_FRUTTA+Analisi.PREZZO_INTERO)));
    tblMateriale.setObject(6,1,Utili.valuta(costoAnalisi.get(Analisi.ANA_UMIDITA+Analisi.MAT_FRUTTA+Analisi.PREZZO_INTERO)));

    tblMateriale.setObject(1,2,Utili.valuta(costoAnalisi.get(Analisi.ANA_CALCIO+Analisi.MAT_FRUTTA+Analisi.RIDUZIONE_1)));
    tblMateriale.setObject(2,2,Utili.valuta(costoAnalisi.get(Analisi.ANA_MAGNESIO+Analisi.MAT_FRUTTA+Analisi.RIDUZIONE_1)));
    tblMateriale.setObject(3,2,Utili.valuta(costoAnalisi.get(Analisi.ANA_POTASSIO+Analisi.MAT_FRUTTA+Analisi.RIDUZIONE_1)));
    tblMateriale.setObject(4,2,Utili.valuta(costoAnalisi.get(Analisi.ANA_AZOTO+Analisi.MAT_FRUTTA+Analisi.RIDUZIONE_1)));
    tblMateriale.setObject(5,2,Utili.valuta(costoAnalisi.get(Analisi.ANA_FOSFORO+Analisi.MAT_FRUTTA+Analisi.RIDUZIONE_1)));
    tblMateriale.setObject(6,2,Utili.valuta(costoAnalisi.get(Analisi.ANA_UMIDITA+Analisi.MAT_FRUTTA+Analisi.RIDUZIONE_1)));

    tblMateriale.setObject(1,3,Utili.valuta(costoAnalisi.get(Analisi.ANA_CALCIO+Analisi.MAT_FRUTTA+Analisi.RIDUZIONE_2)));
    tblMateriale.setObject(2,3,Utili.valuta(costoAnalisi.get(Analisi.ANA_MAGNESIO+Analisi.MAT_FRUTTA+Analisi.RIDUZIONE_2)));
    tblMateriale.setObject(3,3,Utili.valuta(costoAnalisi.get(Analisi.ANA_POTASSIO+Analisi.MAT_FRUTTA+Analisi.RIDUZIONE_2)));
    tblMateriale.setObject(4,3,Utili.valuta(costoAnalisi.get(Analisi.ANA_AZOTO+Analisi.MAT_FRUTTA+Analisi.RIDUZIONE_2)));
    tblMateriale.setObject(5,3,Utili.valuta(costoAnalisi.get(Analisi.ANA_FOSFORO+Analisi.MAT_FRUTTA+Analisi.RIDUZIONE_2)));
    tblMateriale.setObject(6,3,Utili.valuta(costoAnalisi.get(Analisi.ANA_UMIDITA+Analisi.MAT_FRUTTA+Analisi.RIDUZIONE_2)));

    tblMateriale.setObject(1,4,Utili.valuta(costoAnalisi.get(Analisi.ANA_CALCIO+Analisi.MAT_FRUTTA+Analisi.RIDUZIONE_3)));
    tblMateriale.setObject(2,4,Utili.valuta(costoAnalisi.get(Analisi.ANA_MAGNESIO+Analisi.MAT_FRUTTA+Analisi.RIDUZIONE_3)));
    tblMateriale.setObject(3,4,Utili.valuta(costoAnalisi.get(Analisi.ANA_POTASSIO+Analisi.MAT_FRUTTA+Analisi.RIDUZIONE_3)));
    tblMateriale.setObject(4,4,Utili.valuta(costoAnalisi.get(Analisi.ANA_AZOTO+Analisi.MAT_FRUTTA+Analisi.RIDUZIONE_3)));
    tblMateriale.setObject(5,4,Utili.valuta(costoAnalisi.get(Analisi.ANA_FOSFORO+Analisi.MAT_FRUTTA+Analisi.RIDUZIONE_3)));
    tblMateriale.setObject(6,4,Utili.valuta(costoAnalisi.get(Analisi.ANA_UMIDITA+Analisi.MAT_FRUTTA+Analisi.RIDUZIONE_3)));

    xss.setElement(elementName, tblMateriale);
  }

  private void fillErbacee( Hashtable analisi, Hashtable costoAnalisi,
  		ReportSheet xss )
  {
    String elementName = "tblErbacee";

    DefaultTableLens tblMateriale = new DefaultTableLens(xss.getTable(elementName));

    //IMPOSTO L'ALLINEAMENTO delle colonne dei prezzi
    for (int i=0;i<=11;i++)
    {
     tblMateriale.setAlignment(i,1,StyleConstants.H_RIGHT);
     tblMateriale.setAlignment(i,2,StyleConstants.H_RIGHT);
     tblMateriale.setAlignment(i,3,StyleConstants.H_RIGHT);
     tblMateriale.setAlignment(i,4,StyleConstants.H_RIGHT);
    }

    tblMateriale.setObject(1,0,analisi.get(Analisi.ANA_CALCIO));
    tblMateriale.setObject(2,0,analisi.get(Analisi.ANA_MAGNESIO));
    tblMateriale.setObject(3,0,analisi.get(Analisi.ANA_POTASSIO));
    tblMateriale.setObject(4,0,analisi.get(Analisi.ANA_AZOTO));
    tblMateriale.setObject(5,0,analisi.get(Analisi.ANA_FOSFORO));
    tblMateriale.setObject(6,0,analisi.get(Analisi.ANA_FERRO));
    tblMateriale.setObject(7,0,analisi.get(Analisi.ANA_MANGANESE));
    tblMateriale.setObject(8,0,analisi.get(Analisi.ANA_ZINCO));
    tblMateriale.setObject(9,0,analisi.get(Analisi.ANA_RAME));
    tblMateriale.setObject(10,0,analisi.get(Analisi.ANA_BORO));
    tblMateriale.setObject(11,0,analisi.get(Analisi.ANA_UMIDITA));

    tblMateriale.setObject(1,1,Utili.valuta(costoAnalisi.get(Analisi.ANA_CALCIO+Analisi.MAT_ERBACEE+Analisi.PREZZO_INTERO)));
    tblMateriale.setObject(2,1,Utili.valuta(costoAnalisi.get(Analisi.ANA_MAGNESIO+Analisi.MAT_ERBACEE+Analisi.PREZZO_INTERO)));
    tblMateriale.setObject(3,1,Utili.valuta(costoAnalisi.get(Analisi.ANA_POTASSIO+Analisi.MAT_ERBACEE+Analisi.PREZZO_INTERO)));
    tblMateriale.setObject(4,1,Utili.valuta(costoAnalisi.get(Analisi.ANA_AZOTO+Analisi.MAT_ERBACEE+Analisi.PREZZO_INTERO)));
    tblMateriale.setObject(5,1,Utili.valuta(costoAnalisi.get(Analisi.ANA_FOSFORO+Analisi.MAT_ERBACEE+Analisi.PREZZO_INTERO)));
    tblMateriale.setObject(6,1,Utili.valuta(costoAnalisi.get(Analisi.ANA_FERRO+Analisi.MAT_ERBACEE+Analisi.PREZZO_INTERO)));
    tblMateriale.setObject(7,1,Utili.valuta(costoAnalisi.get(Analisi.ANA_MANGANESE+Analisi.MAT_ERBACEE+Analisi.PREZZO_INTERO)));
    tblMateriale.setObject(8,1,Utili.valuta(costoAnalisi.get(Analisi.ANA_ZINCO+Analisi.MAT_ERBACEE+Analisi.PREZZO_INTERO)));
    tblMateriale.setObject(9,1,Utili.valuta(costoAnalisi.get(Analisi.ANA_RAME+Analisi.MAT_ERBACEE+Analisi.PREZZO_INTERO)));
    tblMateriale.setObject(10,1,Utili.valuta(costoAnalisi.get(Analisi.ANA_BORO+Analisi.MAT_ERBACEE+Analisi.PREZZO_INTERO)));
    tblMateriale.setObject(11,1,Utili.valuta(costoAnalisi.get(Analisi.ANA_UMIDITA+Analisi.MAT_ERBACEE+Analisi.PREZZO_INTERO)));

    tblMateriale.setObject(1,2,Utili.valuta(costoAnalisi.get(Analisi.ANA_CALCIO+Analisi.MAT_ERBACEE+Analisi.RIDUZIONE_1)));
    tblMateriale.setObject(2,2,Utili.valuta(costoAnalisi.get(Analisi.ANA_MAGNESIO+Analisi.MAT_ERBACEE+Analisi.RIDUZIONE_1)));
    tblMateriale.setObject(3,2,Utili.valuta(costoAnalisi.get(Analisi.ANA_POTASSIO+Analisi.MAT_ERBACEE+Analisi.RIDUZIONE_1)));
    tblMateriale.setObject(4,2,Utili.valuta(costoAnalisi.get(Analisi.ANA_AZOTO+Analisi.MAT_ERBACEE+Analisi.RIDUZIONE_1)));
    tblMateriale.setObject(5,2,Utili.valuta(costoAnalisi.get(Analisi.ANA_FOSFORO+Analisi.MAT_ERBACEE+Analisi.RIDUZIONE_1)));
    tblMateriale.setObject(6,2,Utili.valuta(costoAnalisi.get(Analisi.ANA_FERRO+Analisi.MAT_ERBACEE+Analisi.RIDUZIONE_1)));
    tblMateriale.setObject(7,2,Utili.valuta(costoAnalisi.get(Analisi.ANA_MANGANESE+Analisi.MAT_ERBACEE+Analisi.RIDUZIONE_1)));
    tblMateriale.setObject(8,2,Utili.valuta(costoAnalisi.get(Analisi.ANA_ZINCO+Analisi.MAT_ERBACEE+Analisi.RIDUZIONE_1)));
    tblMateriale.setObject(9,2,Utili.valuta(costoAnalisi.get(Analisi.ANA_RAME+Analisi.MAT_ERBACEE+Analisi.RIDUZIONE_1)));
    tblMateriale.setObject(10,2,Utili.valuta(costoAnalisi.get(Analisi.ANA_BORO+Analisi.MAT_ERBACEE+Analisi.RIDUZIONE_1)));
    tblMateriale.setObject(11,2,Utili.valuta(costoAnalisi.get(Analisi.ANA_UMIDITA+Analisi.MAT_ERBACEE+Analisi.RIDUZIONE_1)));

    tblMateriale.setObject(1,3,Utili.valuta(costoAnalisi.get(Analisi.ANA_CALCIO+Analisi.MAT_ERBACEE+Analisi.RIDUZIONE_2)));
    tblMateriale.setObject(2,3,Utili.valuta(costoAnalisi.get(Analisi.ANA_MAGNESIO+Analisi.MAT_ERBACEE+Analisi.RIDUZIONE_2)));
    tblMateriale.setObject(3,3,Utili.valuta(costoAnalisi.get(Analisi.ANA_POTASSIO+Analisi.MAT_ERBACEE+Analisi.RIDUZIONE_2)));
    tblMateriale.setObject(4,3,Utili.valuta(costoAnalisi.get(Analisi.ANA_AZOTO+Analisi.MAT_ERBACEE+Analisi.RIDUZIONE_2)));
    tblMateriale.setObject(5,3,Utili.valuta(costoAnalisi.get(Analisi.ANA_FOSFORO+Analisi.MAT_ERBACEE+Analisi.RIDUZIONE_2)));
    tblMateriale.setObject(6,3,Utili.valuta(costoAnalisi.get(Analisi.ANA_FERRO+Analisi.MAT_ERBACEE+Analisi.RIDUZIONE_2)));
    tblMateriale.setObject(7,3,Utili.valuta(costoAnalisi.get(Analisi.ANA_MANGANESE+Analisi.MAT_ERBACEE+Analisi.RIDUZIONE_2)));
    tblMateriale.setObject(8,3,Utili.valuta(costoAnalisi.get(Analisi.ANA_ZINCO+Analisi.MAT_ERBACEE+Analisi.RIDUZIONE_2)));
    tblMateriale.setObject(9,3,Utili.valuta(costoAnalisi.get(Analisi.ANA_RAME+Analisi.MAT_ERBACEE+Analisi.RIDUZIONE_2)));
    tblMateriale.setObject(10,3,Utili.valuta(costoAnalisi.get(Analisi.ANA_BORO+Analisi.MAT_ERBACEE+Analisi.RIDUZIONE_2)));
    tblMateriale.setObject(11,3,Utili.valuta(costoAnalisi.get(Analisi.ANA_UMIDITA+Analisi.MAT_ERBACEE+Analisi.RIDUZIONE_2)));

    tblMateriale.setObject(1,4,Utili.valuta(costoAnalisi.get(Analisi.ANA_CALCIO+Analisi.MAT_ERBACEE+Analisi.RIDUZIONE_3)));
    tblMateriale.setObject(2,4,Utili.valuta(costoAnalisi.get(Analisi.ANA_MAGNESIO+Analisi.MAT_ERBACEE+Analisi.RIDUZIONE_3)));
    tblMateriale.setObject(3,4,Utili.valuta(costoAnalisi.get(Analisi.ANA_POTASSIO+Analisi.MAT_ERBACEE+Analisi.RIDUZIONE_3)));
    tblMateriale.setObject(4,4,Utili.valuta(costoAnalisi.get(Analisi.ANA_AZOTO+Analisi.MAT_ERBACEE+Analisi.RIDUZIONE_3)));
    tblMateriale.setObject(5,4,Utili.valuta(costoAnalisi.get(Analisi.ANA_FOSFORO+Analisi.MAT_ERBACEE+Analisi.RIDUZIONE_3)));
    tblMateriale.setObject(6,4,Utili.valuta(costoAnalisi.get(Analisi.ANA_FERRO+Analisi.MAT_ERBACEE+Analisi.RIDUZIONE_3)));
    tblMateriale.setObject(7,4,Utili.valuta(costoAnalisi.get(Analisi.ANA_MANGANESE+Analisi.MAT_ERBACEE+Analisi.RIDUZIONE_3)));
    tblMateriale.setObject(8,4,Utili.valuta(costoAnalisi.get(Analisi.ANA_ZINCO+Analisi.MAT_ERBACEE+Analisi.RIDUZIONE_3)));
    tblMateriale.setObject(9,4,Utili.valuta(costoAnalisi.get(Analisi.ANA_RAME+Analisi.MAT_ERBACEE+Analisi.RIDUZIONE_3)));
    tblMateriale.setObject(10,4,Utili.valuta(costoAnalisi.get(Analisi.ANA_BORO+Analisi.MAT_ERBACEE+Analisi.RIDUZIONE_3)));
    tblMateriale.setObject(11,4,Utili.valuta(costoAnalisi.get(Analisi.ANA_UMIDITA+Analisi.MAT_ERBACEE+Analisi.RIDUZIONE_3)));

    xss.setElement(elementName, tblMateriale);
  }

  private void fillFoglie( Hashtable analisi, Hashtable costoAnalisi,
  		ReportSheet xss )
  {
    String elementName = "tblFoglie";

    DefaultTableLens tblMateriale = new DefaultTableLens(xss.getTable(elementName));

    //IMPOSTO L'ALLINEAMENTO delle colonne dei prezzi
    for (int i=0;i<=11;i++)
    {
     tblMateriale.setAlignment(i,1,StyleConstants.H_RIGHT);
     tblMateriale.setAlignment(i,2,StyleConstants.H_RIGHT);
     tblMateriale.setAlignment(i,3,StyleConstants.H_RIGHT);
     tblMateriale.setAlignment(i,4,StyleConstants.H_RIGHT);
    }

    tblMateriale.setObject(1,0,analisi.get(Analisi.ANA_CALCIO));
    tblMateriale.setObject(2,0,analisi.get(Analisi.ANA_MAGNESIO));
    tblMateriale.setObject(3,0,analisi.get(Analisi.ANA_POTASSIO));
    tblMateriale.setObject(4,0,analisi.get(Analisi.ANA_AZOTO));
    tblMateriale.setObject(5,0,analisi.get(Analisi.ANA_FOSFORO));
    tblMateriale.setObject(6,0,analisi.get(Analisi.ANA_FERRO));
    tblMateriale.setObject(7,0,analisi.get(Analisi.ANA_MANGANESE));
    tblMateriale.setObject(8,0,analisi.get(Analisi.ANA_ZINCO));
    tblMateriale.setObject(9,0,analisi.get(Analisi.ANA_RAME));
    tblMateriale.setObject(10,0,analisi.get(Analisi.ANA_BORO));
    tblMateriale.setObject(11,0,analisi.get(Analisi.ANA_UMIDITA));

    tblMateriale.setObject(1,1,Utili.valuta(costoAnalisi.get(Analisi.ANA_CALCIO+Analisi.MAT_ERBACEE+Analisi.PREZZO_INTERO)));
    tblMateriale.setObject(2,1,Utili.valuta(costoAnalisi.get(Analisi.ANA_MAGNESIO+Analisi.MAT_ERBACEE+Analisi.PREZZO_INTERO)));
    tblMateriale.setObject(3,1,Utili.valuta(costoAnalisi.get(Analisi.ANA_POTASSIO+Analisi.MAT_ERBACEE+Analisi.PREZZO_INTERO)));
    tblMateriale.setObject(4,1,Utili.valuta(costoAnalisi.get(Analisi.ANA_AZOTO+Analisi.MAT_ERBACEE+Analisi.PREZZO_INTERO)));
    tblMateriale.setObject(5,1,Utili.valuta(costoAnalisi.get(Analisi.ANA_FOSFORO+Analisi.MAT_ERBACEE+Analisi.PREZZO_INTERO)));
    tblMateriale.setObject(6,1,Utili.valuta(costoAnalisi.get(Analisi.ANA_FERRO+Analisi.MAT_ERBACEE+Analisi.PREZZO_INTERO)));
    tblMateriale.setObject(7,1,Utili.valuta(costoAnalisi.get(Analisi.ANA_MANGANESE+Analisi.MAT_ERBACEE+Analisi.PREZZO_INTERO)));
    tblMateriale.setObject(8,1,Utili.valuta(costoAnalisi.get(Analisi.ANA_ZINCO+Analisi.MAT_ERBACEE+Analisi.PREZZO_INTERO)));
    tblMateriale.setObject(9,1,Utili.valuta(costoAnalisi.get(Analisi.ANA_RAME+Analisi.MAT_ERBACEE+Analisi.PREZZO_INTERO)));
    tblMateriale.setObject(10,1,Utili.valuta(costoAnalisi.get(Analisi.ANA_BORO+Analisi.MAT_ERBACEE+Analisi.PREZZO_INTERO)));
    tblMateriale.setObject(11,1,Utili.valuta(costoAnalisi.get(Analisi.ANA_UMIDITA+Analisi.MAT_ERBACEE+Analisi.PREZZO_INTERO)));

    tblMateriale.setObject(1,2,Utili.valuta(costoAnalisi.get(Analisi.ANA_CALCIO+Analisi.MAT_ERBACEE+Analisi.RIDUZIONE_1)));
    tblMateriale.setObject(2,2,Utili.valuta(costoAnalisi.get(Analisi.ANA_MAGNESIO+Analisi.MAT_ERBACEE+Analisi.RIDUZIONE_1)));
    tblMateriale.setObject(3,2,Utili.valuta(costoAnalisi.get(Analisi.ANA_POTASSIO+Analisi.MAT_ERBACEE+Analisi.RIDUZIONE_1)));
    tblMateriale.setObject(4,2,Utili.valuta(costoAnalisi.get(Analisi.ANA_AZOTO+Analisi.MAT_ERBACEE+Analisi.RIDUZIONE_1)));
    tblMateriale.setObject(5,2,Utili.valuta(costoAnalisi.get(Analisi.ANA_FOSFORO+Analisi.MAT_ERBACEE+Analisi.RIDUZIONE_1)));
    tblMateriale.setObject(6,2,Utili.valuta(costoAnalisi.get(Analisi.ANA_FERRO+Analisi.MAT_ERBACEE+Analisi.RIDUZIONE_1)));
    tblMateriale.setObject(7,2,Utili.valuta(costoAnalisi.get(Analisi.ANA_MANGANESE+Analisi.MAT_ERBACEE+Analisi.RIDUZIONE_1)));
    tblMateriale.setObject(8,2,Utili.valuta(costoAnalisi.get(Analisi.ANA_ZINCO+Analisi.MAT_ERBACEE+Analisi.RIDUZIONE_1)));
    tblMateriale.setObject(9,2,Utili.valuta(costoAnalisi.get(Analisi.ANA_RAME+Analisi.MAT_ERBACEE+Analisi.RIDUZIONE_1)));
    tblMateriale.setObject(10,2,Utili.valuta(costoAnalisi.get(Analisi.ANA_BORO+Analisi.MAT_ERBACEE+Analisi.RIDUZIONE_1)));
    tblMateriale.setObject(11,2,Utili.valuta(costoAnalisi.get(Analisi.ANA_UMIDITA+Analisi.MAT_ERBACEE+Analisi.RIDUZIONE_1)));

    tblMateriale.setObject(1,3,Utili.valuta(costoAnalisi.get(Analisi.ANA_CALCIO+Analisi.MAT_ERBACEE+Analisi.RIDUZIONE_2)));
    tblMateriale.setObject(2,3,Utili.valuta(costoAnalisi.get(Analisi.ANA_MAGNESIO+Analisi.MAT_ERBACEE+Analisi.RIDUZIONE_2)));
    tblMateriale.setObject(3,3,Utili.valuta(costoAnalisi.get(Analisi.ANA_POTASSIO+Analisi.MAT_ERBACEE+Analisi.RIDUZIONE_2)));
    tblMateriale.setObject(4,3,Utili.valuta(costoAnalisi.get(Analisi.ANA_AZOTO+Analisi.MAT_ERBACEE+Analisi.RIDUZIONE_2)));
    tblMateriale.setObject(5,3,Utili.valuta(costoAnalisi.get(Analisi.ANA_FOSFORO+Analisi.MAT_ERBACEE+Analisi.RIDUZIONE_2)));
    tblMateriale.setObject(6,3,Utili.valuta(costoAnalisi.get(Analisi.ANA_FERRO+Analisi.MAT_ERBACEE+Analisi.RIDUZIONE_2)));
    tblMateriale.setObject(7,3,Utili.valuta(costoAnalisi.get(Analisi.ANA_MANGANESE+Analisi.MAT_ERBACEE+Analisi.RIDUZIONE_2)));
    tblMateriale.setObject(8,3,Utili.valuta(costoAnalisi.get(Analisi.ANA_ZINCO+Analisi.MAT_ERBACEE+Analisi.RIDUZIONE_2)));
    tblMateriale.setObject(9,3,Utili.valuta(costoAnalisi.get(Analisi.ANA_RAME+Analisi.MAT_ERBACEE+Analisi.RIDUZIONE_2)));
    tblMateriale.setObject(10,3,Utili.valuta(costoAnalisi.get(Analisi.ANA_BORO+Analisi.MAT_ERBACEE+Analisi.RIDUZIONE_2)));
    tblMateriale.setObject(11,3,Utili.valuta(costoAnalisi.get(Analisi.ANA_UMIDITA+Analisi.MAT_ERBACEE+Analisi.RIDUZIONE_2)));

    tblMateriale.setObject(1,4,Utili.valuta(costoAnalisi.get(Analisi.ANA_CALCIO+Analisi.MAT_ERBACEE+Analisi.RIDUZIONE_3)));
    tblMateriale.setObject(2,4,Utili.valuta(costoAnalisi.get(Analisi.ANA_MAGNESIO+Analisi.MAT_ERBACEE+Analisi.RIDUZIONE_3)));
    tblMateriale.setObject(3,4,Utili.valuta(costoAnalisi.get(Analisi.ANA_POTASSIO+Analisi.MAT_ERBACEE+Analisi.RIDUZIONE_3)));
    tblMateriale.setObject(4,4,Utili.valuta(costoAnalisi.get(Analisi.ANA_AZOTO+Analisi.MAT_ERBACEE+Analisi.RIDUZIONE_3)));
    tblMateriale.setObject(5,4,Utili.valuta(costoAnalisi.get(Analisi.ANA_FOSFORO+Analisi.MAT_ERBACEE+Analisi.RIDUZIONE_3)));
    tblMateriale.setObject(6,4,Utili.valuta(costoAnalisi.get(Analisi.ANA_FERRO+Analisi.MAT_ERBACEE+Analisi.RIDUZIONE_3)));
    tblMateriale.setObject(7,4,Utili.valuta(costoAnalisi.get(Analisi.ANA_MANGANESE+Analisi.MAT_ERBACEE+Analisi.RIDUZIONE_3)));
    tblMateriale.setObject(8,4,Utili.valuta(costoAnalisi.get(Analisi.ANA_ZINCO+Analisi.MAT_ERBACEE+Analisi.RIDUZIONE_3)));
    tblMateriale.setObject(9,4,Utili.valuta(costoAnalisi.get(Analisi.ANA_RAME+Analisi.MAT_ERBACEE+Analisi.RIDUZIONE_3)));
    tblMateriale.setObject(10,4,Utili.valuta(costoAnalisi.get(Analisi.ANA_BORO+Analisi.MAT_ERBACEE+Analisi.RIDUZIONE_3)));
    tblMateriale.setObject(11,4,Utili.valuta(costoAnalisi.get(Analisi.ANA_UMIDITA+Analisi.MAT_ERBACEE+Analisi.RIDUZIONE_3)));

    xss.setElement(elementName, tblMateriale);
  }
}
