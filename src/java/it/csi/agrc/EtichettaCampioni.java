package it.csi.agrc;
import it.csi.cuneo.BeanDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
//import it.csi.jsf.web.pool.*;
import java.sql.Statement;
import java.util.Vector;

/**
 * Title:        Agrichim - Front Office
 * Description:  Richiesta analisi chimiche su campioni biologici agrari
 * Copyright:    Copyright (c) 2003
 * Company:      CSI Piemonte - Progettazione e Sviluppo - Cuneo
 * @author Michele Piantà, Piergiorgio Chiriotti
 * @version 1.0.0
 */

public class EtichettaCampioni extends BeanDataSource
{
  private Vector etichettaCampioni = new Vector();
  private String cancella;


  /**
   * indica il max numero di record che posso visualizzare in una pagina
   */
  private int passo=15;

  /**
   * indica la posizione del primo record che verrà visualizzato nella
   * pagina
   */
  private int baseElementi;

  /**
   * indica il numero di record totali che dovrei visualizzare
   */
  private int numRecord;

  public EtichettaCampioni()
  {
  }

  public void fillPrincipale()
  throws Exception, SQLException
  {
    Anagrafiche anagrafiche=new Anagrafiche();
    if (!isConnection())
       throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn=null;
    StringBuffer query=new StringBuffer("");
    String anagraficaTecnico;
    String anagraficaProprietario;
    String anagraficaUtente;
    String proprietario;
    String richiedente;
    try
    {
      conn=getConnection();
      Statement stmt = conn.createStatement();
      query = new StringBuffer("SELECT EC.ID_RICHIESTA,");
      query.append("TO_CHAR(EC.DATA_INSERIMENTO_RICHIESTA,'DD/MM/YYYY') AS DATA, ");
      query.append("M.DESCRIZIONE AS MATERIALE,");
      query.append("EC.DESCRIZIONE_ETICHETTA,");
      query.append("EC.ANAGRAFICA_UTENTE,");
      query.append("EC.ANAGRAFICA_TECNICO,");
      query.append("EC.ANAGRAFICA_PROPRIETARIO ");
      query.append("FROM ETICHETTA_CAMPIONE EC, MATERIALE M ");
      query.append("WHERE EC.STATO_ATTUALE = '00' ");
      query.append("AND EC.CODICE_MATERIALE= M.CODICE_MATERIALE ");
      query.append("AND EC.ANAGRAFICA_UTENTE = ");
      query.append(this.getAut().getUtente().getAnagraficaUtente());
      query.append(" ORDER BY EC.ID_RICHIESTA DESC ");

      //CuneoLogger.debug(this,query.toString());
      ResultSet rset = stmt.executeQuery(query.toString());
      while (rset.next())
      {
        anagraficaProprietario=rset.getString("ANAGRAFICA_PROPRIETARIO");
        anagraficaUtente=rset.getString("ANAGRAFICA_UTENTE");
        if (anagraficaProprietario==null)
        {
          proprietario=anagrafiche.getNomeCognome(
                       conn,
                       anagraficaUtente
                                                  );
        }
        else
        {
          proprietario=anagrafiche.getNomeCognome(
                       conn,
                       anagraficaProprietario
                                                  );
        }
        anagraficaTecnico=rset.getString("ANAGRAFICA_TECNICO");
        if (anagraficaTecnico==null)
        {
          richiedente=anagrafiche.getNomeCognome(
                       conn,
                       anagraficaUtente
                                                  );
        }
        else
        {
          richiedente=anagrafiche.getNomeCognome(
                      conn,
                      anagraficaTecnico
                                                 );
        }

        add(new EtichettaCampione(
                             rset.getString("ID_RICHIESTA"),
                             rset.getString("DATA"),
                             null,
                             rset.getString("MATERIALE"),
                             rset.getString("DESCRIZIONE_ETICHETTA"),
                             null,
                             null,
                             proprietario,
                             richiedente,
                             null,
                             null,
                             null
                                 )
            );

      }
      rset.close();
      stmt.close();
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("fillPrincipale della classe EtichettaCampioni");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("fillPrincipale della classe EtichettaCampioni"
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


  public void fillElencoCampioni()
      throws Exception, SQLException
  {
    Anagrafiche anagrafiche=new Anagrafiche();
    if (!isConnection())
      throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn=null;
    String anagraficaTecnico;
    String anagraficaProprietario;
    String anagraficaUtente;
    String proprietario;
    String richiedente;
    String query=this.getAut().getQueryRicerca();
    String queryCount=this.getAut().getQueryCountRicerca();
    try
    {
      conn=getConnection();
      Statement stmt = conn.createStatement();
      ResultSet rset = stmt.executeQuery(queryCount);
      if (rset.next())
        this.numRecord=rset.getInt("NUM");
      rset.close();

      int inizio,fine;
      if ( this.baseElementi == 0) inizio=1;
        else inizio=this.getBaseElementi();
      if ( this.passo == 0 ) fine=size();
        else fine=inizio+this.getPasso()-1;
      query+=") AS ELENCO_CAMPIONI WHERE NUM BETWEEN "+inizio+" AND "+fine;
      //CuneoLogger.debug(this,query);
      rset = stmt.executeQuery(query);

      while (rset.next())
      {
        anagraficaProprietario=rset.getString("ANAGRAFICA_PROPRIETARIO");
        anagraficaUtente=rset.getString("ANAGRAFICA_UTENTE");
        if (anagraficaProprietario==null)
        {
          proprietario=anagrafiche.getNomeCognome(
                     conn,
                     anagraficaUtente
                                                );
        }
        else
        {
          proprietario=anagrafiche.getNomeCognome(
              conn,
              anagraficaProprietario
              );
        }
        anagraficaTecnico=rset.getString("ANAGRAFICA_TECNICO");
        if (anagraficaTecnico==null)
        {
          richiedente=anagrafiche.getNomeCognome(
                     conn,
                     anagraficaUtente
                                                );
        }
        else
        {
          richiedente=anagrafiche.getNomeCognome(
              conn,
              anagraficaTecnico
              );
        }

        add(new EtichettaCampione(
                                  rset.getString("ID_RICHIESTA"),
                                  rset.getString("DATA"),
                                  rset.getString("CODICE_MATERIALE"),
                                  rset.getString("MATERIALE"),
                                  rset.getString("DESCRIZIONE_ETICHETTA"),
                                  rset.getString("DESC_STATO_ATTUALE"),
                                  rset.getString("STATO_ATTUALE"),
                                  proprietario,
                                  richiedente,
                                  rset.getString("CONTEGGIO_FATTURE"),
                                  rset.getString("NUMERO_FATTURA"),
                                  rset.getString("ANNO_FATTURA")
                                  )
                                  );
      }
      rset.close();
      stmt.close();
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("fillElencoCampioni della classe EtichettaCampioni");
      this.getAut().setContenutoQuery(query);
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("fillElencoCampioni della classe EtichettaCampioni"
                             +": non è una SQLException ma una Exception"
                             +" generica");
      this.getAut().setContenutoQuery(query);
      throw (e);
    }
    finally
    {
      if (conn!=null) conn.close();
    }
  }

  public int delete()
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
      //jira 145
      query = new StringBuffer( "delete from wrk_calcolo_utm where id_richiesta =  ");
      query.append(this.cancella);
      stmt.executeUpdate( query.toString() );
      stmt.close();
      
      stmt = conn.createStatement();
      query = new StringBuffer( "DELETE  FROM ETICHETTA_CAMPIONE EC ");
      query.append("WHERE EC.ID_RICHIESTA = ");
      query.append(this.cancella);
      int deleted = stmt.executeUpdate( query.toString() );
      stmt.close();
      return deleted;
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("delete della classe EtichettaCampioni");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("delete della classe EtichettaCampioni"
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

  /**
   * Questo metodo viene utilizzato per popolare i dati che vengono visualizzati
   * nel PDF della fattura
   * @param idFattura: contiene l'id della fattura
   * @param anno: contiene l'anno di emissione della fattura
   * @return restituisce quanto bisogna pagare per la spedizione della fatura
   * @throws Exception
   * @throws SQLException
   */
  public void selectForFatturaPDF(String idFattura,String anno)
      throws Exception, SQLException
  {
    if (!isConnection())
      throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn=null;
    Anagrafiche anagrafiche=new Anagrafiche();
    StringBuffer query=new StringBuffer("");
    String anagraficaProprietario;
    String anagraficaUtente;
    String proprietario;
    try
    {
      conn=getConnection();
      Statement stmt = conn.createStatement();
      query.append("SELECT M.DESCRIZIONE AS MATERIALE,");
      query.append("EC.DESCRIZIONE_ETICHETTA,");
      query.append("EC.ANAGRAFICA_UTENTE,EC.ANAGRAFICA_PROPRIETARIO ");
      query.append("FROM ETICHETTA_CAMPIONE EC,CAMPIONE_FATTURATO CF");
      query.append(",MATERIALE M ");
      query.append("WHERE CF.NUMERO_FATTURA = ").append(idFattura);
      query.append(" AND CF.ANNO=").append(anno);
      query.append(" AND EC.ID_RICHIESTA = CF.ID_RICHIESTA ");
      query.append("AND EC.CODICE_MATERIALE= M.CODICE_MATERIALE ");
      query.append("ORDER BY EC.ID_RICHIESTA ");
      //CuneoLogger.debug(this, query.toString());
      ResultSet rset = stmt.executeQuery(query.toString());
      while (rset.next())
      {
        anagraficaProprietario=rset.getString("ANAGRAFICA_PROPRIETARIO");
        anagraficaUtente=rset.getString("ANAGRAFICA_UTENTE");
        if (anagraficaProprietario==null)
        {
          proprietario=anagrafiche.getNomeCognome(
                     conn,
                     anagraficaUtente
                                                );
        }
        else
        {
          proprietario=anagrafiche.getNomeCognome(
              conn,
              anagraficaProprietario
              );
        }
        add(new EtichettaCampione(
                                    null,
                                    rset.getString("MATERIALE"),
                                    rset.getString("DESCRIZIONE_ETICHETTA"),
                                    proprietario,
                                    null,
                                    null,
                                    null
                                    )
                                    );
      }
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("selectForFatturaPDF della classe EtichettaCampioni");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("selectForFatturaPDF della classe EtichettaCampioni"
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
    return etichettaCampioni.size();
  }

  public EtichettaCampione get(int i)
  {
    return (EtichettaCampione)etichettaCampioni.elementAt(i);
  }

  public void add(EtichettaCampione i)
  {
    etichettaCampioni.addElement(i);
  }

  public String getCancella()
  {
    return cancella;
  }
  public void setCancella(String newCancella)
  {
    cancella = newCancella;
  }

  public int getPasso()
  {
    return passo;
  }

  public void setPasso(int newPasso)
  {
    passo = newPasso;
  }

  public int getBaseElementi()
  {
    return baseElementi;
  }

  public void setBaseElementi(int newBaseElementi)
  {
    baseElementi = newBaseElementi;
  }

  public int getNumRecord()
  {
    return numRecord;
  }
}