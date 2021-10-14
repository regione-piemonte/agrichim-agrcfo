package it.csi.agrc;

//import it.csi.cuneo.*;
import java.sql.*;
//import it.csi.jsf.web.pool.*;
//import java.util.*;

/**
 * <p>Title: Agrichim - Front Office</p>
 * <p>Description: Richiesta analisi chimiche su campioni biologici agrari</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: CSI Piemonte - Progettazione e Sviluppo - Cuneo</p>
 * @author Michele Piantà, Piergiorgio Chiriotti
 * @version 1.0.0
 */

public interface Modello
{
  public void select()
      throws Exception, SQLException;

  public int insert()
      throws Exception, SQLException;

  public int update()
      throws Exception, SQLException;
}