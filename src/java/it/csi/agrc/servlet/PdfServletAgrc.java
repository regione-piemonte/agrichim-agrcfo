package it.csi.agrc.servlet;

import it.csi.cuneo.servlet.*;
import it.csi.agrc.*;
//import java.io.*;
import javax.servlet.*;
//import javax.servlet.http.*;

/**
 * <p>Title: Agrichim - Front Office</p>
 * <p>Description: Richiesta analisi chimiche su campioni biologici agrari</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: CSI Piemonte - Progettazione e Sviluppo - Cuneo</p>
 * @author Michele Piantà, Piergiorgio Chiriotti
 * @version 1.0.0
 */

public abstract class PdfServletAgrc extends PdfServlet
{
  protected void controllaAut(Autenticazione aut)
      throws ServletException
  {
    if (aut==null)
    {
      ServletException se = new ServletException("Sessione non valida");
      throw se;
    }
  }
}