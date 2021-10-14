package it.csi.agrc;
import it.csi.cuneo.*;
//import java.util.*;
import java.sql.*;
//import it.csi.jsf.web.pool.*;

/**
 * Title:        Agrichim - Front Office
 * Description:  Richiesta analisi chimiche su campioni biologici agrari
 * Copyright:    Copyright (c) 2003
 * Company:      CSI Piemonte - Progettazione e Sviluppo - Cuneo
 * @author Michele Piantà, Piergiorgio Chiriotti
 * @version 1.0.0
 */

public class TracciabilitaPOP extends BeanDataSource
{

  private String data;
  private String azione;
  private String note;

  public TracciabilitaPOP()
  {
  }

  public TracciabilitaPOP(
                          String data,
                          String azione,
                          String note
                         )
  {
    this.data=data;
    this.azione=azione;
    this.note=note;
  }


  /**
   * Legge la data del record, con progressivo più alto, dalla tabella
   * tracciatura
   * */
  public void select(String idRichiesta)
  throws Exception, SQLException
  {
    if (!isConnection())
       throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn=null;
    StringBuffer query=new StringBuffer("");
    try
    {
        conn=getConnection();
        Statement stmt = conn.createStatement();

        query = new StringBuffer("SELECT TO_CHAR(T.DATA,'DD/MM/YYYY')");
        query.append(" AS DATA FROM TRACCIATURA T ");
        query.append("WHERE T.ID_RICHIESTA = ");
        query.append(idRichiesta);
        query.append(" ORDER BY T.PROGRESSIVO DESC");
        //CuneoLogger.debug(this,query.toString());
        ResultSet rset = stmt.executeQuery(query.toString());
        if (rset.next()) this.setData(rset.getString("DATA"));
        rset.close();
        stmt.close();
    }
    catch(java.sql.SQLException ex)
    {
        this.getAut().setQuery("select della classe TracciabilitaPOP");
        this.getAut().setContenutoQuery(query.toString());
        throw (ex);
    }
    catch(Exception e)
    {
        this.getAut().setQuery("select della classe TracciabilitaPOP"
                              +": non è una SQLException ma una Exception"
                              +" generica");
        this.getAut().setContenutoQuery(query.toString());
        throw (e);
    }
    finally
    {
        if (conn!=null) conn.close();
    }
  }



  public String getData()
  {
    return data;
  }
  public void setData(String data)
  {
    if (data==null)  data="";
    this.data = data;
  }
  public void setAzione(String azione)
  {
    this.azione = azione;
  }
  public String getAzione()
  {
    if (azione==null) azione="";
    return azione;
  }
  public void setNote(String note)
  {
    this.note = note;
  }
  public String getNote()
  {
    if (note==null)  note="";
    return note;
  }

}