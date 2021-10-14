// Codici unicode per le lettere accentate da inserire nei PDF
// \u00EC = ì

package it.csi.agrc.servlet;

//import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;

import inetsoft.report.TabularSheet;
import inetsoft.report.lens.DefaultTableLens;
import it.csi.agrc.Analisi;
import it.csi.cuneo.ReportUtils;
import it.csi.cuneo.Utili;


public class PdfAnagraficaCampioneZMA extends PdfAnagraficaCampione
{
  private static final long serialVersionUID = -8482811997199012135L;

  public PdfAnagraficaCampioneZMA()
  {
    this.setOutputName("anagraficaCampioneZMA.pdf");
    this.setTemplateName("pdfAnagraficaCampioneZMA.srt");
  }

  protected void stampaPdf(HttpServletRequest request, TabularSheet xss)
      throws Exception {
    super.stampaPdf(request, xss);
    //seleziono la tabella in pdfAnagraficaCampioneZMA.srt
    tblLens = new DefaultTableLens(xss.getTable("tblPreventivo"));
    ReportUtils.formatTableHeaderOneRow(xss, "lblPreventivo", true);
    ReportUtils.formatTableColumLeftRight(xss, "tblPreventivo", true);
    //select Preventivo
    Analisi analisi = new Analisi(dataSource, aut);
    analisi.selectPreventivo(idRichiesta);
	tblLens.setObject(0,1,analisi.getN_preventivo());
	tblLens.setObject(1,1,analisi.getCodice_fiscale());
	tblLens.setObject(2,1,Utili.valuta(analisi.getCosto_totale())+" euro");
	tblLens.setObject(3,1,analisi.getNote_laboratorio());
	tblLens.setObject(4,1,analisi.getNote());
    xss.setElement("tblPreventivo", tblLens);
  }
}