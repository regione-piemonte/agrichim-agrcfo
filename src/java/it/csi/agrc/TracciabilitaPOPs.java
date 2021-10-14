package it.csi.agrc;
import it.csi.cuneo.*;
import java.util.*;
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

public class TracciabilitaPOPs extends BeanDataSource
{
  private Vector tracciabilitaPOPs = new Vector();

  public TracciabilitaPOPs()
  {
  }

  public void fill(String idRichiesta)
  throws Exception, SQLException
  {
    if (!isConnection())
       throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn=null;
    StringBuffer query=new StringBuffer("");
    //String anagraficaTecnico;
    //String anagraficaProprietario;
    //String proprietario;
    //String richiedente;
    try
    {
        conn=getConnection();
        Statement stmt = conn.createStatement();

        query = new StringBuffer("SELECT TO_CHAR(T.DATA,'DD/MM/YYYY')");
        query.append(" AS DATA,");
        query.append("CT.DESCRIZIONE AS AZIONE,T.NOTE ");
        query.append("FROM TRACCIATURA T,CODIFICA_TRACCIABILITA CT ");
        query.append("WHERE T.ID_RICHIESTA = ");
        query.append(idRichiesta);
        query.append(" AND T.CODICE = CT.CODICE ");
        query.append(" ORDER BY T.PROGRESSIVO");
        //CuneoLogger.debug(this,query.toString());
        ResultSet rset = stmt.executeQuery(query.toString());
        while (rset.next())
          add( new TracciabilitaPOP (
                                      rset.getString("DATA"),
                                      rset.getString("AZIONE"),
                                      rset.getString("NOTE")
                                    )
              );
        rset.close();
        stmt.close();
    }
    catch(java.sql.SQLException ex)
    {
        this.getAut().setQuery("fill della classe TracciabilitaPOPs");
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

  public int size()
  {
      return tracciabilitaPOPs.size();
  }

  public TracciabilitaPOP get(int i)
  {
      return (TracciabilitaPOP)tracciabilitaPOPs.elementAt(i);
  }

  public void add(TracciabilitaPOP i)
  {
      tracciabilitaPOPs.addElement(i);
  }


}