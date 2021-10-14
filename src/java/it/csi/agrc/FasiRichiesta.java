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

public class FasiRichiesta extends BeanDataSource
{

  public int updateFase(int fase)
      throws Exception, SQLException
  {
    if (!isConnection())
           throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn=this.getConnection();
    StringBuffer query=new StringBuffer("");
    Statement stmt = null;

    try
    {
      stmt = conn.createStatement();
      query = new StringBuffer("UPDATE FASI_RICHIESTA ");
      query.append("SET NUMERO_FASE= ");
      query.append(fase);
      query.append(" WHERE ID_RICHIESTA= ");
      query.append(this.getAut().getIdRichiestaCorrente());
      /**
       * Modifico i dati relativi alla fasse
       **/
      int updated = stmt.executeUpdate( query.toString() );
      stmt.close();
      return updated;
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("updateFase della classe FasiRichiesta");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("updateFase della classe FasiRichiesta"
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

  public int updateFase(long idRichiesta,int fase,Connection conn)
  throws Exception, SQLException
  {
    StringBuffer query=new StringBuffer("");
    Statement stmt = conn.createStatement();
    query = new StringBuffer("UPDATE FASI_RICHIESTA ");
    query.append("SET NUMERO_FASE= ");
    query.append(fase);
    query.append(" WHERE ID_RICHIESTA= ");
    query.append(idRichiesta);
    //CuneoLogger.debug(this,"updateFase "+ query.toString());
    /**
     * Modifico i dati relativi alla fasse
     **/
    int updated = stmt.executeUpdate( query.toString() );
    stmt.close();
    return updated;
  }

  public int insertFase(long idRichiesta,Connection conn)
  throws Exception, SQLException
  {
    StringBuffer query=new StringBuffer("");
    Statement stmt = conn.createStatement();
    query = new StringBuffer("INSERT INTO FASI_RICHIESTA ");
    query.append("(ID_RICHIESTA,NUMERO_FASE) ");
    query.append("VALUES(").append(idRichiesta);
    query.append(",").append(1);
    query.append(")");
    /**
     * Modifico i dati relativi alla fasse
     **/
    int inserted = stmt.executeUpdate( query.toString() );
    stmt.close();
    return inserted;
  }



}