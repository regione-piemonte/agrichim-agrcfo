package it.csi.cuneo.servlet;

import inetsoft.report.PDFPrinter;
import inetsoft.report.ReportSheet;
import inetsoft.report.StyleConstants;
import inetsoft.report.TabularSheet;
import inetsoft.report.io.Builder;
import inetsoft.report.lens.DefaultTableLens;

import java.awt.Image;
import java.awt.PrintJob;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.ImageIcon;

/**
 * <p>Title: Agrichim - Front Office</p>
 * <p>Description: Richiesta analisi chimiche su campioni biologici agrari</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: CSI Piemonte - Progettazione e Sviluppo - Cuneo</p>
 * @author Michele Piantà, Piergiorgio Chiriotti
 * @version 1.0.0
 */

public abstract class PdfServlet extends HttpServlet
{
  private static final String CONTENT_TYPE_PDF = "application/pdf";
  private static final String CONTENT_TYPE_DOWNLOAD = "application/x-download";
  private static final String ERROR_PAGE = "/jsp/view/errorePopup.jsp";

  /* Variabili da impostare nel costruttore di OGNI sottoclasse */
  private String outputName = null;
  private String templateName = null;

  //Gestione cache dei template per evitare che si generino dei memory leak
  private static HashMap<String, ReportSheet> cacheTemplate = new HashMap<String, ReportSheet>();

  public void service(HttpServletRequest request,
                      HttpServletResponse response)
    throws ServletException, IOException
  {
    if (null==this.getOutputName())
    {
      ServletException se =
          new ServletException("\nPdfServlet - Tentativo fallito a causa di outputName non definito");
      throw se;
    }

    if (null==this.getTemplateName())
    {
      ServletException se =
          new ServletException("\nPdfServlet - Tentativo fallito a causa di templateName non definito");
      throw se;
    }

    try
    {
      String action=request.getParameter("action");
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      creaPdf(request, response, baos);

      byte ba[] = baos.toByteArray();
      response.setContentLength(ba.length);
      if ("download".equals(action))
      {
        response.setContentType(CONTENT_TYPE_DOWNLOAD);
        response.addHeader("Content-Disposition",
                           "attachment;filename = " + this.getOutputName());
      }
      else //if ("open".equals(action)) // azione predefinita
        response.setContentType(CONTENT_TYPE_PDF);

      OutputStream out = response.getOutputStream();
      out.write(ba);
      //out.flush();
      //out.close();
    }
    catch (Exception e)
    {
      e.printStackTrace(System.err);
      //System.err.println("\nPdfServlet.service()\n"+e.getMessage()+"\n");
      request.setAttribute("javax.servlet.jsp.jspException", e);
      request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
    }
  }

  private final void creaPdf(HttpServletRequest request,
                             HttpServletResponse response,
                             OutputStream outStampa)
      throws Exception
  {
    try
    {
      //InputStream fis = getStream(this.getTemplateName());
      //TabularSheet xss = (TabularSheet)Builder.getBuilder(Builder.TEMPLATE, fis).read("./");

      //Lettura template con la gestione della cache
      TabularSheet xss = (TabularSheet) createReport(this.getTemplateName());    	
    	
      stampaPdf(request, xss);

      PDFPrinter pdf = new PDFPrinter(outStampa);
      pdf.setPageSize(StyleConstants.PAPER_A4);
      PrintJob pj = pdf.getPrintJob();
      xss.print(pj);
      pdf.close();
    }
    catch (Exception e)
    {
      //e.printStackTrace();
      throw e;
    }
  }

  /**
   * Lettura template per la generazione delle stampe,
   * effettuata una sola volta e poi tenuta in memoria
   * @param nomeTemplate
   * @return ReportSheet
   * @throws Exception
   */
  protected ReportSheet createReport(String nomeTemplate) throws Exception
  {
    ReportSheet cachedObject = (ReportSheet) cacheTemplate.get(nomeTemplate);

    // TODO Riga seguente da scommentare ai fini dello sviluppo
    // Riduce rischi out of memory perché non fa la cache dei template
    //cachedObject = null;

    if (cachedObject == null)
    {
      InputStream fis = getStream(nomeTemplate);

      //Se non trova il template in cache lo legge direttamente
      cachedObject = Builder.getBuilder(Builder.TEMPLATE, fis).read("./");
      cacheTemplate.put(nomeTemplate, cachedObject);
    }
    //else
    //{
    //  AgriLogger.debug(this, "[StampeServlet::createReport] Template recuperato dalla cache");
    //}

    //Effettua la clone del ReportSheet per essere thread safe
    return (ReportSheet) cachedObject.clone();
  }

  protected abstract void stampaPdf(HttpServletRequest request,
  		TabularSheet xss)
      throws Exception;

  protected InputStream getStream(String resource)
  {
    return getClass().getClassLoader().getResourceAsStream(resource);
  }

  protected Image getImage(String image)
  {
    if (image == null)
    {
      System.err.println("Errore: Immagine non specificata");
      return null;
    }

    final byte[][] buffer = new byte[1][];
    try
    {
      InputStream resource = getStream(image);
      if (resource == null)
      {
        System.err.println("Errore: Risorsa "+image+" non trovata");
        return null;
      }

      BufferedInputStream in = new BufferedInputStream(resource);
      ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
      buffer[0] = new byte[1024];
      int n;
      while ((n = in.read(buffer[0])) > 0)
        out.write(buffer[0], 0, n);
      in.close();
      out.flush();
      buffer[0] = out.toByteArray();
    }
    catch (IOException ioe)
    {
      System.err.println(ioe.toString());
      return null;
    }

    if (buffer[0] == null)
    {
      System.err.println("PdfServlet.getImage('"+image+"') - errore: immagine non trovata");
      return null;
    }
    if (buffer[0].length == 0)
    {
      System.err.println("PdfServlet.getImage('"+image+"') - errore: immagine di lunghezza zero");
      return null;
    }

    return new ImageIcon(buffer[0]).getImage();
  }

  public static void resetTableHeader(DefaultTableLens table, int newSize)
  {
    int iColCount = table.getColCount();
    Vector vColHeader = new Vector();
    for (int icol=0; icol<iColCount; icol++)
      vColHeader.add(table.getObject(0,icol));
    table.setRowCount(newSize+1);
    for (int icol=0; icol<iColCount; icol++)
      table.setObject(0,icol,vColHeader.get(icol));
  }


  public String getOutputName()
  {
    return outputName;
  }
  public void setOutputName(String outputName)
  {
    this.outputName = outputName;
  }
  public String getTemplateName()
  {
    return templateName;
  }
  public void setTemplateName(String templateName)
  {
    this.templateName = templateName;
  }
}
