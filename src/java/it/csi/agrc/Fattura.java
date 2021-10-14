package it.csi.agrc;

import it.csi.cuneo.*;
import java.sql.*;

/**
 * Title:        Piani di concimazione ed utilizzazione agronomica
 * Description:  Impostazione, simulazione, gestione e stampa di PCUA & C.
 * Copyright:    Copyright (c) 2002
 * Company:      CSI Piemonte
 * @author
 * @version 1.0
 */

public class Fattura extends BeanDataSource
{
  private String anno;
  private String numeroFattura;
  private String dataFattura;
  private String pagata;
  private String annullata;
  private String partitaIvaOCf;
  private String ragioneSociale;
  private String indirizzo;
  private String cap;
  private String comune;
  private String siglaProvincia;
  private String importoSpedizione;
  private String imponibile;
  private String IVA;


  public Fattura ()
  {
  }

  /**
   * Costruttore utilizzato per gestire la stampa dell'elenco delle fatture
   * */
  public Fattura (  String anno, String numeroFattura, String dataFattura,
                    String pagata, String annullata, String partitaIvaOCf,
                    String ragioneSociale, String indirizzo, String cap,
                    String comune, String siglaProvincia,
                    String importoSpedizione,String imponibile,String IVA)
  {
    this.anno = anno;
    this.numeroFattura = numeroFattura;
    this.dataFattura = dataFattura;
    this.pagata = pagata;
    this.annullata = annullata;
    this.partitaIvaOCf = partitaIvaOCf;
    this.ragioneSociale = ragioneSociale;
    this.indirizzo = indirizzo;
    this.cap = cap;
    this.comune = comune;
    this.siglaProvincia = siglaProvincia;
    this.importoSpedizione = importoSpedizione;
    this.imponibile=imponibile;
    this.IVA=IVA;
  
  }

  /**
   * Costruttore usato per la ricerca delle fatture
   * */
  public Fattura (  String anno, String numeroFattura, String dataFattura, String pagata, String annullata, String partitaIvaOCf, String ragioneSociale)
  {
    this.anno = anno;
    this.numeroFattura = numeroFattura;
    this.dataFattura = dataFattura;
    this.pagata = pagata;
    this.annullata = annullata;
    this.partitaIvaOCf = partitaIvaOCf;
    this.ragioneSociale = ragioneSociale;
  }


  /**
   * Questa funzione viene utilizzata dal valorizzare i dati all'interno del
   * PDF EsempioFattura
   * @param idFattura: numero della fattura di cui si vogliono visualizzare i
   * dati
   * @param anno: anno in cui è stata emessa la fattura
   * @throws Exception
   * @throws SQLException
   */
  public void select(String idFattura, String anno)
  throws Exception, SQLException
  {
	  CuneoLogger.debug(this,"sono in select con questi parametri: idFattura " + idFattura );
	  CuneoLogger.debug(this,"sono in select con questi parametri: anno " + anno );
    if (!isConnection())
      throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn=null;
    StringBuffer query=new StringBuffer("");
    //String proprietario;
    try
    {
      conn=getConnection();
      Statement stmt = conn.createStatement();
      query = new StringBuffer("SELECT ANNO, NUMERO_FATTURA,");
      query.append("TO_CHAR(DATA_FATTURA ,'DD/MM/YYYY') AS DATA_FATTURA,");
      query.append("PAGATA, ANNULLATA, PARTITA_IVA_O_CF,");
      query.append("RAGIONE_SOCIALE, INDIRIZZO, CAP, COMUNE,");
      query.append("SIGLA_PROVINCIA, IMPORTO_SPEDIZIONE ");
      query.append("FROM FATTURA ");
      query.append("WHERE NUMERO_FATTURA = ");
      query.append(idFattura);
      query.append(" AND ANNO = ").append(anno);
      
      CuneoLogger.debug(this, query.toString());
      
      ResultSet rset = stmt.executeQuery(query.toString());
      if (rset.next())
      {
        this.setAnno(rset.getString("ANNO"));
        this.setNumeroFattura(rset.getString("NUMERO_FATTURA"));
        this.setDataFattura(rset.getString("DATA_FATTURA"));
        this.setPagata(rset.getString("PAGATA"));
        this.setAnnullata(rset.getString("ANNULLATA"));
        this.setPartitaIvaOCf(rset.getString("PARTITA_IVA_O_CF"));
        this.setRagioneSociale(rset.getString("RAGIONE_SOCIALE"));
        this.setIndirizzo(rset.getString("INDIRIZZO"));
        this.setCap(rset.getString("CAP"));
        this.setComune(rset.getString("COMUNE"));
        this.setSiglaProvincia(rset.getString("SIGLA_PROVINCIA"));
        this.setImportoSpedizione(rset.getString("IMPORTO_SPEDIZIONE"));
      }
      rset.close();
      stmt.close();
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("select della classe Fattura");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("select della classe Fattura"
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
   * Questa funzione viene utilizzata per sapere il numero e l'anno dell'ultima
   * fattura
   * @return restiruisce un vettore di stringhe contenente nella prima posizione
   * ANNO_FATTURA_AGRICHIM e nella seconda ULTIMA_FATTURA_AGRICHIM
   * @throws Exception
   * @throws SQLException
   */
  public String[] ultimaFattura()
  throws Exception, SQLException
  {
    return ultimaFattura(null);
  }

  /**
   * Questa funzione viene utilizzata per sapere il numero e l'anno dell'ultima
   * fattura
   * @return restiruisce un vettore di stringhe contenente nella prima posizione
   * ANNO_FATTURA_AGRICHIM e nella seconda ULTIMA_FATTURA_AGRICHIM
   * @throws Exception
   * @throws SQLException
   */
  public String[] ultimaFattura(Connection connPassata)
  throws Exception, SQLException
  {
    boolean chiudiConnesione=true;
    String param[]=new String[2];
    if (!isConnection())
      throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn=null;
    StringBuffer query=new StringBuffer("");
    try
    {
      if (connPassata==null)
        conn=getConnection();
      else
      {
        conn=connPassata;
        chiudiConnesione=false;
      }
      Statement stmt = conn.createStatement();
      query = new StringBuffer("SELECT ANNO_FATTURA_AGRICHIM, ULTIMA_FATTURA_AGRICHIM ");
      query.append("FROM PARAMETRI_FATTURE");
      //CuneoLogger.debug(this, query.toString());
      ResultSet rset = stmt.executeQuery(query.toString());
      if (rset.next())
      {
        param[0]=rset.getString("ANNO_FATTURA_AGRICHIM");
        param[1]=rset.getString("ULTIMA_FATTURA_AGRICHIM");
      }
      rset.close();
      stmt.close();
      return param;
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("ultimaFattura della classe Fattura");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("ultimaFattura della classe Fattura"
                            +": non è una SQLException ma una Exception"
                            +" generica");
      this.getAut().setContenutoQuery(query.toString());
      throw (e);
    }
    finally
    {
      if (chiudiConnesione)
        if (conn!=null) conn.close();
    }
  }

  /**
   * Questo metodo viene utilizzato per cancellare fisicamente una fattura (solo
   * se è l'ultima oppure per annullarla(in tutti gli altri casi))
   * @param anno: anno in cui è stata emessa la fattura
   * @param numero: numero della fattura
   * @throws Exception
   * @throws SQLException
   */
  public void cancella(String anno,String numero)
  throws Exception, SQLException
  {
    //String pagata=null;
    if (!isConnection())
      throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn=null;
    StringBuffer query=new StringBuffer();

    try
    {
      conn=getConnection();
      // Si imposta a false l'autocommit perché le operazioni che vengono eseguite sul DB
      // devono essere effettuate in blocco
      // Si noti l'invocazione del metodo conn.commit() PRIMA di conn.close() e
      // l'invocazione di conn.rollback() nel blocco catch{}
      conn.setAutoCommit( false );
      // Per evitare ogni rischio di interferenza nel recuperare l'ultimo valore creato
      // nella sequence, imposto il massimo livello di isolamento di questa transazione
      //conn.setTransactionIsolation( Connection.TRANSACTION_SERIALIZABLE );
      Statement stmt = conn.createStatement();

      String ultimaFattura[]=ultimaFattura(conn);
      if (anno.equals(ultimaFattura[0])
          && numero.equals(ultimaFattura[1]))
      {
        //Questa fattura è l'ultima fatta, quindi posso cancellarla
        query.append("DELETE FROM FATTURA");
        query.append(" WHERE ANNO = ").append(anno);
        query.append(" AND NUMERO_FATTURA=").append(numero);
        CuneoLogger.debug(this, query.toString());
        stmt.executeUpdate(query.toString());
        query.setLength(0);
        //Devo rimpostare il vecchio numero nella tabella parametri_fatture
        query.append("UPDATE PARAMETRI_FATTURE ");
        query.append("SET ULTIMA_FATTURA_AGRICHIM = ");
        query.append(Integer.parseInt(ultimaFattura[1])-1);
      }
      else
      {
        //Non posso cancellarla ma solo annullarla
        query.append("UPDATE FATTURA ");
        query.append("SET ANNULLATA='S' ");
        query.append("WHERE ANNO = ").append(anno);
        query.append(" AND NUMERO_FATTURA=").append(numero);
      }
      CuneoLogger.debug(this, query.toString());
      stmt.executeUpdate(query.toString());
      stmt.close();
      conn.commit();
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("cancella della classe Fattura");
      this.getAut().setContenutoQuery(query.toString());
      try
      {
        conn.rollback();
      }
      catch( java.sql.SQLException ex2 )
      {
        this.getAut().setQuery("cancella della classe Fattura"
                               +":problemi con il rollback");
        this.getAut().setContenutoQuery(query.toString());
        throw (ex2);
      }
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("cancella della classe Fattura"
                             +": non è una SQLException ma una Exception"
                             +" generica");
      this.getAut().setContenutoQuery(query.toString());
      throw (e);
    }
    finally
    {
      if (conn!=null)
      {
        conn.setAutoCommit(true);
        conn.close();
      }
    }
  }

  /**
   * Questo metodo viene utilizzato per segnare come pagata una fattura
   * @param anno: anno in cui è stata emessa la fattura
   * @param numero: numero della fattura
   * @throws Exception
   * @throws SQLException
   */
  public void pagata(String anno,String numero)
  throws Exception, SQLException
  {
    //String pagata=null;
    if (!isConnection())
      throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn=null;
    StringBuffer query=new StringBuffer();

    try
    {
      conn=getConnection();
      // Si imposta a false l'autocommit perché le operazioni che vengono eseguite sul DB
      // devono essere effettuate in blocco
      // Si noti l'invocazione del metodo conn.commit() PRIMA di conn.close() e
      // l'invocazione di conn.rollback() nel blocco catch{}
      conn.setAutoCommit( false );
      // Per evitare ogni rischio di interferenza nel recuperare l'ultimo valore creato
      // nella sequence, imposto il massimo livello di isolamento di questa transazione
      //conn.setTransactionIsolation( Connection.TRANSACTION_SERIALIZABLE );
      Statement stmt = conn.createStatement();


      //Imposta il campo Pagata della tabella fattura a "S" ----> N : il pagamento avverrà in BO
      query.append("UPDATE FATTURA ");
      query.append("SET PAGATA='N' ");
      query.append("WHERE ANNO = ").append(anno);
      query.append(" AND NUMERO_FATTURA=").append(numero);
      CuneoLogger.debug(this, query.toString());
      stmt.executeUpdate(query.toString());

      query.setLength(0);
      //Imposta tutti per i record della tabella fattura il campo PAGAMENTO
      //della tabella EtichettaCampione a "S" ----> N : il pagamento avverrà in BO
      query.append("UPDATE ETICHETTA_CAMPIONE ");
      query.append("SET PAGAMENTO='N' ");
      query.append("WHERE ID_RICHIESTA ");
      query.append("IN");
      query.append("(");
      query.append("SELECT ID_RICHIESTA ");
      query.append("FROM CAMPIONE_FATTURATO ");
      query.append("WHERE ANNO = ").append(anno);
      query.append(" AND NUMERO_FATTURA=").append(numero).append(")");

      CuneoLogger.debug(this, query.toString());
      stmt.executeUpdate(query.toString());
      stmt.close();
      conn.commit();
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("pagata della classe Fattura");
      this.getAut().setContenutoQuery(query.toString());
      try
      {
        conn.rollback();
      }
      catch( java.sql.SQLException ex2 )
      {
        this.getAut().setQuery("pagata della classe Fattura"
                               +":problemi con il rollback");
        this.getAut().setContenutoQuery(query.toString());
        throw (ex2);
      }
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("pagata della classe Fattura"
                             +": non è una SQLException ma una Exception"
                             +" generica");
      this.getAut().setContenutoQuery(query.toString());
      throw (e);
    }
    finally
    {
      if (conn!=null)
      {
        conn.setAutoCommit(true);
        conn.close();
      }
    }
  }

  public String getAnno()
  {
    if (anno==null) return "";
    return this.anno;
  }
  public void setAnno( String newAnno )
  {
    this.anno = newAnno;
  }


  public String getNumeroFattura()
  {
    if (numeroFattura==null) return "";
    return this.numeroFattura;
  }
  public void setNumeroFattura( String newNumeroFattura )
  {
    this.numeroFattura = newNumeroFattura;
  }


  public String getDataFattura()
  {
    if (dataFattura==null) return "";
    return this.dataFattura;
  }
  public void setDataFattura( String newDataFattura )
  {
    this.dataFattura = newDataFattura;
  }


  public String getPagata()
  {
    if (pagata==null) return "";
    return this.pagata;
  }
  public void setPagata( String newPagata )
  {
    this.pagata = newPagata;
  }


  public String getAnnullata()
  {
    if (annullata==null) return "";
    return this.annullata;
  }
  public void setAnnullata( String newAnnullata )
  {
    this.annullata = newAnnullata;
  }


  public String getPartitaIvaOCf()
  {
    if (partitaIvaOCf==null) return "";
    return this.partitaIvaOCf;
  }
  public void setPartitaIvaOCf( String newPartitaIvaOCf )
  {
    this.partitaIvaOCf = newPartitaIvaOCf;
  }


  public String getRagioneSociale()
  {
    if (ragioneSociale==null) return "";
    return this.ragioneSociale;
  }
  public void setRagioneSociale( String newRagioneSociale )
  {
    this.ragioneSociale = newRagioneSociale;
  }


  public String getIndirizzo()
  {
    if (indirizzo==null) return "";
    return this.indirizzo;
  }
  public void setIndirizzo( String newIndirizzo )
  {
    this.indirizzo = newIndirizzo;
  }


  public String getCap()
  {
    if (cap==null) return "";
    return this.cap;
  }
  public void setCap( String newCap )
  {
    this.cap = newCap;
  }


  public String getComune()
  {
    if (comune==null) return "";
    return this.comune;
  }
  public void setComune( String newComune )
  {
    this.comune = newComune;
  }


  public String getSiglaProvincia()
  {
    if (siglaProvincia==null) return "";
    return this.siglaProvincia;
  }
  public void setSiglaProvincia( String newSiglaProvincia )
  {
    this.siglaProvincia = newSiglaProvincia;
  }


  public String getImportoSpedizione()
  {
    if (importoSpedizione==null) return "";
    return this.importoSpedizione;
  }
  public void setImportoSpedizione( String newImportoSpedizione )
  {
    this.importoSpedizione = newImportoSpedizione;
  }
  public void setImponibile(String imponibile)
  {
    this.imponibile = imponibile;
  }
  public String getImponibile()
  {
    return imponibile;
  }
  public void setIVA(String IVA)
  {
    this.IVA = IVA;
  }
  public String getIVA()
  {
    return IVA;
  }

}