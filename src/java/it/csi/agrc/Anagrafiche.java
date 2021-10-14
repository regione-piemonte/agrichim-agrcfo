package it.csi.agrc;
import it.csi.cuneo.*;
import java.util.*;
import java.sql.*;

/**
 * Title:        Agrichim - Front Office
 * Description:  Richiesta analisi chimiche su campioni biologici agrari
 * Copyright:    Copyright (c) 2003
 * Company:      CSI Piemonte - Progettazione e Sviluppo - Cuneo
 * @author Michele Piantà, Piergiorgio Chiriotti
 * @version 1.0.0
 */

public class Anagrafiche extends BeanDataSource
{
  private Vector anagrafiche = new Vector();
  private String cancella;

  public Anagrafiche()
  {
  }
  public Anagrafiche(Object dataSource, Autenticazione aut)
  {
    this.setDataSource(dataSource);
    this.setAut(aut);
  }

  public String getNomeCognome(Connection conn,String idAnagrafica)
  throws Exception, SQLException
  {
    String nome=null,cognome=null;
    StringBuffer query=new StringBuffer("");
    Statement stmt = conn.createStatement();
    query=new StringBuffer("SELECT A.NOME,A.COGNOME_RAGIONE_SOCIALE ");
    query.append("FROM ANAGRAFICA A ");
    query.append("WHERE a.ID_ANAGRAFICA = ").append(idAnagrafica);
    ResultSet rset = stmt.executeQuery(query.toString());
    if (rset.next())
    {
      nome = rset.getString("NOME");
      cognome = rset.getString("COGNOME_RAGIONE_SOCIALE");
    }
    rset.close();
    stmt.close();
    if (nome == null) nome="";
    if (cognome == null) cognome="";
    return nome +" "+cognome;
  }


  public void fill()
      throws Exception, SQLException
  {
    fill(null,false);
  }


  public Anagrafica getAnagraficaDaCF(String codiceFiscale)
      throws Exception, SQLException
  {
    Anagrafica a=null;
    fill(codiceFiscale,true);
    if ( size()==1 )
      a = get(0);
    return a;
  }

  public Anagrafica getAnagrafica(String idAnagraficaSearch)
      throws Exception, SQLException
  {
    Anagrafica a=null;
    fill(idAnagraficaSearch,false);
    if ( size()==1 )
      a = get(0);
    return a;
  }

  public void fill(String stringSearch, boolean isCodiceIdentificativo) throws Exception, SQLException {
    if (!isConnection())
           throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn=this.getConnection();
    StringBuffer query=new StringBuffer("");
    Statement stmt = null;
    try {
        stmt = conn.createStatement();

        query.append("SELECT * FROM ANAGRAFICA ");
        if ( null != stringSearch )
          if ( !"".equals(stringSearch) )
            if (isCodiceIdentificativo)
              query.append("WHERE CODICE_IDENTIFICATIVO='"+stringSearch+"' ");
            else
              query.append("WHERE ID_ANAGRAFICA="+stringSearch+" ");
        //query.append("ORDER BY COGNOME_RAGIONE_SOCIALE");
        CuneoLogger.debug(this,"\nQuery Anagrafiche.fill()\n"+query.toString());
        ResultSet rset = stmt.executeQuery(query.toString());

        anagrafiche.clear();
        while (rset.next())
          add( new Anagrafica( rset.getString("ID_ANAGRAFICA"),
                               rset.getString("CODICE_IDENTIFICATIVO"),
                               rset.getString("TIPO_PERSONA"),
                               rset.getString("COGNOME_RAGIONE_SOCIALE"),
                               rset.getString("NOME"),
                               rset.getString("INDIRIZZO"),
                               rset.getString("CAP"),
                               rset.getString("COMUNE_RESIDENZA"),
                               rset.getString("TELEFONO"),
                               rset.getString("CELLULARE"),
                               rset.getString("FAX"),
                               rset.getString("EMAIL"),
                               rset.getString("ID_ORGANIZZAZIONE"),
                               rset.getString("TIPO_UTENTE"),
                               rset.getString("ID_ANAGRAFICA_AZIENDA"),
                               rset.getString("ID_ANAGRAFICA_2"))
              );
        rset.close();
        stmt.close();
    } catch(java.sql.SQLException ex) {
      this.getAut().setQuery("fill della classe Anagrafiche");
      this.getAut().setContenutoQuery(query.toString());
      ex.printStackTrace();
      throw (ex);
    } catch(Exception e) {
      this.getAut().setQuery("fill della classe Anagrafiche"
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

  public String getProprietario()
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
        query.append("SELECT EC.ANAGRAFICA_PROPRIETARIO ");
        query.append("FROM ETICHETTA_CAMPIONE EC ");
        query.append("WHERE EC.ID_RICHIESTA = ");
        query.append(this.getAut().getIdRichiestaCorrente());
        String queryStr=query.toString();
        ResultSet rset = stmt.executeQuery(queryStr);

        if (rset.next()) return rset.getString("ANAGRAFICA_PROPRIETARIO");
        else return null;
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("getProprietario della classe Anagrafiche");
      this.getAut().setContenutoQuery(query.toString());
      ex.printStackTrace();
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("getProprietario della classe Anagrafiche"
                            +": non è una SQLException ma una Exception"
                            +" generica");
      e.printStackTrace();
      throw (e);
    }
    finally
    {
      stmt.close();
      if (conn!=null) conn.close();
    }
  }
  public String getUtente()
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
		  query.append("SELECT EC.ANAGRAFICA_UTENTE ");
		  query.append("FROM ETICHETTA_CAMPIONE EC ");
		  query.append("WHERE EC.ID_RICHIESTA = ");
		  query.append(this.getAut().getIdRichiestaCorrente());
		  String queryStr=query.toString();
		  ResultSet rset = stmt.executeQuery(queryStr);
		  
		  if (rset.next()) return rset.getString("ANAGRAFICA_UTENTE");
		  else return null;
	  }
	  catch(java.sql.SQLException ex)
	  {
		  this.getAut().setQuery("getUtente della classe Anagrafiche");
		  this.getAut().setContenutoQuery(query.toString());
		  ex.printStackTrace();
		  throw (ex);
	  }
	  catch(Exception e)
	  {
		  this.getAut().setQuery("getUtente della classe Anagrafiche"
				  +": non è una SQLException ma una Exception"
				  +" generica");
		  e.printStackTrace();
		  throw (e);
	  }
	  finally
	  {
		  stmt.close();
		  if (conn!=null) conn.close();
	  }
  }
  public String getOrganizzazione()
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
		  query.append("SELECT A.ID_ORGANIZZAZIONE "); 
		  query.append("from "); 
		  query.append("ANAGRAFICA A ");
		  if(this.getAut().getUtente().getTipoUtente()=='L')
			  query.append("left join ETICHETTA_CAMPIONE EC on A.ID_ANAGRAFICA = EC.ANAGRAFICA_TECNICO ");
		  else
			  query.append("left join ETICHETTA_CAMPIONE EC on A.ID_ANAGRAFICA = EC.ANAGRAFICA_UTENTE ");
		  query.append("WHERE EC.ID_RICHIESTA = ");
		  query.append(this.getAut().getIdRichiestaCorrente());
		  String queryStr=query.toString();
		  ResultSet rset = stmt.executeQuery(queryStr);
		  
		  if (rset.next()) return rset.getString("ID_ORGANIZZAZIONE");
		  else return null;
	  }
	  catch(java.sql.SQLException ex)
	  {
		  this.getAut().setQuery("getOrganizzazione della classe Anagrafiche");
		  this.getAut().setContenutoQuery(query.toString());
		  ex.printStackTrace();
		  throw (ex);
	  }
	  catch(Exception e)
	  {
		  this.getAut().setQuery("getOrganizzazione della classe Anagrafiche"
				  +": non è una SQLException ma una Exception"
				  +" generica");
		  e.printStackTrace();
		  throw (e);
	  }
	  finally
	  {
		  stmt.close();
		  if (conn!=null) conn.close();
	  }
  }

  public String getIdFromCodFiscPIVA(String codFiscPIVA)
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
        query.append("SELECT A.ID_ANAGRAFICA FROM ANAGRAFICA A ");
        query.append("WHERE UPPER(A.CODICE_IDENTIFICATIVO) = '");
        query.append(codFiscPIVA.toUpperCase());
        query.append("'");
        String queryStr=query.toString();
        ResultSet rset = stmt.executeQuery(queryStr);

        if (rset.next()) return rset.getString("ID_ANAGRAFICA");
        else return null;
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("getIdFromCodFiscPIVA della classe Anagrafiche");
      this.getAut().setContenutoQuery(query.toString());
      ex.printStackTrace();
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("getIdFromCodFiscPIVA della classe Anagrafiche"
                            +": non è una SQLException ma una Exception"
                            +" generica");
      e.printStackTrace();
      throw (e);
    }
    finally
    {
      stmt.close();
      if (conn!=null) conn.close();
    }
  }

  public int size()
  {
    return anagrafiche.size();
  }

  public Anagrafica get(int i)
  {
    return (Anagrafica)anagrafiche.elementAt(i);
  }

  public void add(Anagrafica i)
  {
    anagrafiche.addElement(i);
  }

  public String getCancella()
  {
      return cancella;
  }

  public void setCancella(String newCancella)
  {
      cancella = newCancella;
  }
}