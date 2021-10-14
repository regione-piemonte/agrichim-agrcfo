package it.csi.agrc;

import it.csi.cuneo.*;
import java.util.*;
import java.sql.*;

/**
 * <p>Title: Agrichim - Front Office</p>
 * <p>Description: Richiesta analisi chimiche su campioni biologici agrari</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: CSI Piemonte - Progettazione e Sviluppo - Cuneo</p>
 * @author Michele Piantà, Piergiorgio Chiriotti
 * @version 1.0.0
 */

public class OrganizzazioniTecnico extends BeanDataSource
{
  /**
   * Recupera dal database tutti i tipi di organizzazione
   * */
  public void getTipiOrganizzazione(Vector cod,Vector desc)
  throws Exception, SQLException
  {
    if (!isConnection())
       throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn=null;
    StringBuffer query=new StringBuffer("");
    try
    {
      String codStr,descStr;
      conn=getConnection();
      Statement stmt = conn.createStatement();
      query=new StringBuffer("SELECT ID_TIPO_ORGANIZZAZIONE,DESCRIZIONE ");
      query.append("FROM TIPO_ORGANIZZAZIONE ");
      query.append("ORDER BY DESCRIZIONE");
      //CuneoLogger.debug(this,query.toString());
      ResultSet rset = stmt.executeQuery(query.toString());
      while (rset.next())
      {
        codStr=rset.getString(1);
        descStr=rset.getString(2);
        if (codStr==null) codStr="";
        if (descStr==null) descStr="";
        cod.add(codStr);
        desc.add(descStr);
      }
      rset.close();
      stmt.close();
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("getTipiOrganizzazione della classe "
                            +"OrganizzazioniTecnico");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("getTipiOrganizzazione della classe "
                            +"OrganizzazioniTecnico: non è una "
                            +"SQLException ma una Exception generica");
      this.getAut().setContenutoQuery(query.toString());
      throw (e);
    }
    finally
    {
      if (conn!=null) conn.close();
    }
  }


  /**
   * Recupera dal database tutte le organizzazione di un determinato tipo
   * */
  public void getOrganizzazione(Vector cod,Vector desc,String codTipo)
  throws Exception, SQLException
  {
    if (!isConnection())
       throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn=null;
    StringBuffer query=new StringBuffer("");
    try
    {
      String codStr,descStr,temp;
      conn=getConnection();
      Statement stmt = conn.createStatement();
      query=new StringBuffer("SELECT OP.ID_ORGANIZZAZIONE ");
      query.append(",OP.RAGIONE_SOCIALE, OP.SEDE_TERRITORIALE ");
      query.append("FROM ORGANIZZAZIONE_PROFESSIONALE OP ");
      query.append("WHERE OP.ID_TIPO_ORGANIZZAZIONE = ");
      query.append(codTipo);
      query.append(" ORDER BY OP.RAGIONE_SOCIALE");
      //CuneoLogger.debug(this,query.toString());
      ResultSet rset = stmt.executeQuery(query.toString());
      while (rset.next())
      {
        codStr=rset.getString(1);
        temp=rset.getString(2);
        if (temp==null) temp="";
        descStr=temp;
        temp=rset.getString(3);
        if (temp==null) temp="";
        descStr=descStr+" "+temp;
        if (codStr==null) codStr="";
        cod.add(codStr);
        desc.add(descStr);
      }
      rset.close();
      stmt.close();
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("getOrganizzazione della classe "
                            +"OrganizzazioniTecnico");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("getOrganizzazione della classe "
                            +"OrganizzazioniTecnico: non è una "
                            +"SQLException ma una Exception generica");
      this.getAut().setContenutoQuery(query.toString());
      throw (e);
    }
    finally
    {
      if (conn!=null) conn.close();
    }
  }

  /**
   * Recupera dal database tutte i tecnici che fanno parte di una organizzazione
   * */
  public void getTecnicoFromOrganizzazione(Vector cod,Vector desc,String codOrg)
  throws Exception, SQLException
  {
    if (!isConnection())
       throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn=null;
    StringBuffer query=new StringBuffer("");
    try
    {
      String codStr,descStr,temp;
      conn=getConnection();
      Statement stmt = conn.createStatement();
      query=new StringBuffer("SELECT A.ID_ANAGRAFICA,");
      query.append("A.COGNOME_RAGIONE_SOCIALE,A.NOME ");
      query.append("FROM ANAGRAFICA A ");
      query.append("WHERE UPPER(A.TIPO_UTENTE) = 'T' ");
      query.append("AND A.ID_ORGANIZZAZIONE = ");
      query.append(codOrg);
      query.append(" ORDER BY A.COGNOME_RAGIONE_SOCIALE,A.NOME");
      //CuneoLogger.debug(this,query.toString());
      ResultSet rset = stmt.executeQuery(query.toString());
      while (rset.next())
      {
        codStr=rset.getString(1);
        temp=rset.getString(2);
        if (temp==null) temp="";
        descStr=temp;
        temp=rset.getString(3);
        if (temp==null) temp="";
        descStr=descStr+" "+temp;
        if (codStr==null) codStr="";
        cod.add(codStr);
        desc.add(descStr);

      }
      rset.close();
      stmt.close();
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("getTecnicoFromOrganizzazione della classe "
                            +"OrganizzazioniTecnico");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("getTecnicoFromOrganizzazione della classe "
                            +"OrganizzazioniTecnico: non è una "
                            +"SQLException ma una Exception generica");
      this.getAut().setContenutoQuery(query.toString());
      throw (e);
    }
    finally
    {
      if (conn!=null) conn.close();
    }
  }

}