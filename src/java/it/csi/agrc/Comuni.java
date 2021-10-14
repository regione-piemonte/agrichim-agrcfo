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

public class Comuni extends BeanDataSource
{
  private Vector comuni = new Vector();

  public Comuni()
  {
  }
  public Comuni(Object dataSource, Autenticazione aut)
  {
    this.setDataSource(dataSource);
    this.setAut(aut);
  }

  public void fill()
      throws Exception, SQLException
  {
    fill(null);
  }
  public void fill(String idProvinciaSearch)
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

        query.append("SELECT CODICE_ISTAT, DESCRIZIONE, PROVINCIA ");
        query.append("FROM COMUNE ");
        if ( null != idProvinciaSearch )
          if ( !"".equals(idProvinciaSearch) )
            query.append("WHERE PROVINCIA='"+idProvinciaSearch+"' ");
        query.append("ORDER BY PROVINCIA, DESCRIZIONE");

        String queryStr=query.toString();
        /*
        if ( null != idProvinciaSearch )
          if ( !"".equals(idProvinciaSearch) )
            CuneoLogger.debug(this,"\nQuery Comuni.fill(idProvinciaSearch='"
                               +idProvinciaSearch+"')\n"+queryStr);
        else
          CuneoLogger.debug(this,"\nQuery Comuni.fill()\n"+queryStr);
        */

        ResultSet rset = stmt.executeQuery(queryStr);
        while (rset.next())
          add( new Comune( rset.getString("CODICE_ISTAT"),
                           rset.getString("DESCRIZIONE"),
                           rset.getString("PROVINCIA") )
              );
        rset.close();
        stmt.close();
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("fill della classe Comuni");
      this.getAut().setContenutoQuery(query.toString());
      ex.printStackTrace();
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("fill della classe Comuni"
                            +": non è una SQLException ma una Exception"
                            +" generica");
      e.printStackTrace();
      throw (e);
    }
    finally
    {
      if (conn!=null) conn.close();
    }
  }

  public String getDescrizioneComune(String idComuneSearch)
      throws Exception, SQLException
  {
    String descrizione="";

    if (!isConnection())
           throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn=this.getConnection();
    StringBuffer query=new StringBuffer("");
    Statement stmt = null;
    try
    {
        stmt = conn.createStatement();

        query.append("SELECT CODICE_ISTAT, DESCRIZIONE ");
        query.append("FROM COMUNE ");
        query.append("WHERE CODICE_ISTAT='"+idComuneSearch+"' ");

        String queryStr=query.toString();
        /*
        CuneoLogger.debug(this,"\nQuery Comuni.get(idComuneSearch='"
                           +idComuneSearch+"')\n"+queryStr);
        */

        ResultSet rset = stmt.executeQuery(queryStr);
        if (rset.next())
          descrizione = rset.getString("DESCRIZIONE");
        rset.close();
        stmt.close();
        return descrizione;
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("getDescrizioneComune della classe Comuni");
      this.getAut().setContenutoQuery(query.toString());
      ex.printStackTrace();
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("getDescrizioneComune della classe Comuni"
                            +": non è una SQLException ma una Exception"
                            +" generica");
      e.printStackTrace();
      throw (e);
    }
    finally
    {
      if (conn!=null) conn.close();
    }
  }

  public String getDescrizioneComuneProv(String idComuneSearch)
      throws Exception, SQLException
  {
    if (idComuneSearch==null)
      return null;

    String descrizione=null;

    if (!isConnection())
           throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn=this.getConnection();
    StringBuffer query=new StringBuffer("");
    Statement stmt = null;
    try
    {
        stmt = conn.createStatement();
        query.append("SELECT C.CODICE_ISTAT, C.DESCRIZIONE, P.SIGLA_PROVINCIA ");
        query.append("FROM PROVINCIA P, COMUNE C ");
        query.append("WHERE CODICE_ISTAT='"+idComuneSearch+"' ");
        query.append("AND C.PROVINCIA=P.ID_PROVINCIA ");

        String queryStr=query.toString();
        /*
        CuneoLogger.debug(this,"\nQuery Comuni.get(idComuneSearch='"
                           +idComuneSearch+"')\n"+queryStr);
        */

        ResultSet rset = stmt.executeQuery(queryStr);
        if (rset.next())
          descrizione = rset.getString("DESCRIZIONE")+" ("
                       +rset.getString("SIGLA_PROVINCIA")+")";
        rset.close();
        stmt.close();
        return descrizione;
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("getDescrizioneComune della classe Comuni");
      this.getAut().setContenutoQuery(query.toString());
      ex.printStackTrace();
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("getDescrizioneComune della classe Comuni"
                            +": non è una SQLException ma una Exception"
                            +" generica");
      e.printStackTrace();
      throw (e);
    }
    finally
    {
      if (conn!=null) conn.close();
    }
  }

  public int size()
  {
    return comuni.size();
  }

  public Comune get(int i)
  {
    return (Comune)comuni.elementAt(i);
  }

  public void add(Comune i)
  {
    comuni.addElement(i);
  }
}