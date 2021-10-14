package it.csi.agrc;

import it.csi.cuneo.*;

import java.sql.*;
//import it.csi.jsf.web.pool.*;
import java.io.*;
//import java.awt.*;
import javax.swing.*;

/**
 * Title:        Agrichim - Front Office
 * Description:  Richiesta analisi chimiche su campioni biologici agrari
 * Copyright:    Copyright (c) 2003
 * Company:      CSI Piemonte - Progettazione e Sviluppo - Cuneo
 * @author Michele Piantà, Piergiorgio Chiriotti
 * @version 1.0.0
 */

public class EtichettaCampione extends BeanDataSource
{
	private final String CLASS_NAME = "EtichettaCampione";
  
  public EtichettaCampione()
  {
  }
  public EtichettaCampione(Object dataSource, Autenticazione aut)
  {
    this.setDataSource(dataSource);
    this.setAut(aut);
  }

  private String idRichiesta;
  private String dataInserimentoRichiesta;
  private String codiceMateriale;
  private String descMateriale;
  private String descrizioneEtichetta;
  private String descStatoAttuale;
  private String proprietario;
  private String richiedente;
  private String statoAnomalia;
  public static final int ID_ANAGRAFICA=0;
  public static final int ID_ORGANIZZAZIONE=1;
  public static final int ID_TIPO_ORGANIZZAZIONE=2;
  private String anagraficaUtenteSingolo;
  public String conteggioFatture;
  public String numeroFattura;
  public String annoFattura;

  /**
   * Attributi aggiunti per la creazione del PDF
   */
  private String laboratorioConsegna;
  private String laboratorioAnalisi;
  private String anagraficaProprietario;
  private String anagraficaTecnico;
  private String anagraficaRichiedente;
  private String annoCampione;
  private String numeroCampione;
  private String note;
  private String codLabAnalisi;
  private String statoAttuale;
  private String pagamento;
  private String costo;
  private String fatturareA;  

  public EtichettaCampione(String idRichiesta,
      String descMateriale,
      String descrizioneEtichetta,
      String proprietario,
      String pagamento,
      String costo,
      String fatturareA
      )
	{
		this.idRichiesta =idRichiesta;
		this.descMateriale =descMateriale;
		this.descrizioneEtichetta = descrizioneEtichetta;
		this.proprietario=proprietario;
		this.pagamento=pagamento;
		this.costo=costo;
		this.fatturareA=fatturareA;
	}

  /**
   * Costruttore utilizzato per l'elenco bozze e l'elenco campioni
   *
   * @param idRichiesta Identificativo richiesta
   * @param dataInserimentoRichiesta Data inserimento richiesta
   * @param codiceMateriale Codice materiale
   * @param descMateriale Descrizione materiale
   * @param descrizioneEtichetta Descrizione campione presente sull'etichetta
   * @param descStatoAttuale Stato della richiesta
   * @param proprietario Nome e cognome del proprietario
   * @param richiedente Nome e cognome del richiedente
   */
  public EtichettaCampione(String idRichiesta,
                           String dataInserimentoRichiesta,
                           String codiceMateriale,
                           String descMateriale,
                           String descrizioneEtichetta,
                           String descStatoAttuale,
                           String statoAttuale,
                           String proprietario,
                           String richiedente,
                           String conteggioFatture,
                           String numeroFattura,
                           String annoFattura)
  {
    this.idRichiesta =idRichiesta;
    this.dataInserimentoRichiesta =dataInserimentoRichiesta;
    this.codiceMateriale =codiceMateriale;
    this.descMateriale =descMateriale;
    this.descrizioneEtichetta = descrizioneEtichetta;
    this.descStatoAttuale =descStatoAttuale;
    this.statoAttuale=statoAttuale;
    this.proprietario=proprietario;
    this.richiedente=richiedente;
    this.conteggioFatture = conteggioFatture;
    this.numeroFattura = numeroFattura;
    this.annoFattura = annoFattura;
  }

  public void select(String idRichiesta) throws Exception, SQLException {
    Anagrafiche anagrafiche=new Anagrafiche();
    if (!isConnection())
      throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn=null;
    StringBuffer query=new StringBuffer("");
    String proprietario;
    PreparedStatement stmt = null;
    try {
      conn=getConnection();
//      Statement stmt = conn.createStatement();
      query = new StringBuffer("SELECT EC.ID_RICHIESTA,");
      query.append("M.DESCRIZIONE AS MATERIALE,");
      query.append("EC.DESCRIZIONE_ETICHETTA,");
      query.append("EC.ANAGRAFICA_PROPRIETARIO, ");
      query.append("EC.ANAGRAFICA_UTENTE, ");
      query.append("EC.ANNO, ");
      query.append("EC.NUMERO_CAMPIONE, ");
      query.append("EC.STATO_ANOMALIA ");
      query.append("FROM ETICHETTA_CAMPIONE EC, MATERIALE M ");
      query.append("WHERE EC.ID_RICHIESTA = ? ");
      query.append(" AND EC.CODICE_MATERIALE= M.CODICE_MATERIALE ");
      stmt = conn.prepareStatement(query.toString());
      stmt.setBigDecimal(1, Utili.parseBigDecimal(idRichiesta));
      //CuneoLogger.debug(this,query.toString());
      ResultSet rset = stmt.executeQuery();
      if (rset.next()) {
        String anagraficaProprietario=rset.getString("ANAGRAFICA_PROPRIETARIO");
        String anagraficaUtente=rset.getString("ANAGRAFICA_UTENTE");
        if (anagraficaProprietario==null) {
          proprietario=anagrafiche.getNomeCognome(
                     conn,
                     anagraficaUtente);
        } else {
          proprietario=anagrafiche.getNomeCognome(
              conn,
              anagraficaProprietario);
        }
        this.setIdRichiesta(rset.getString("ID_RICHIESTA"));
        this.setDescMateriale(rset.getString("MATERIALE"));
        this.setDescrizioneEtichetta(
             rset.getString("DESCRIZIONE_ETICHETTA"));
        this.setProprietario(proprietario);
        this.setAnnoCampione(rset.getString("ANNO"));
        this.setNumeroCampione(rset.getString("NUMERO_CAMPIONE"));
        this.setStatoAnomalia(rset.getString("STATO_ANOMALIA"));
      }
      rset.close();
      stmt.close();
    } catch(java.sql.SQLException ex) {
      this.getAut().setQuery("select della classe EtichettaCampione");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    } catch(Exception e) {
      this.getAut().setQuery("select della classe EtichettaCampione"
                            +": non è una SQLException ma una Exception"
                            +" generica");
      this.getAut().setContenutoQuery(query.toString());
      throw (e);
    } finally {
      if (conn!=null) conn.close();
    }
  }

  public void selectPdf(String idRichiesta)
  throws Exception, SQLException
  {
    Anagrafiche anagrafiche=new Anagrafiche();
    if (!isConnection())
      throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn=null;
    StringBuffer query=new StringBuffer("");
    String anagraficaUtente;
    String proprietario;
    String richiedente;
    try
    {
      conn=getConnection();
      Statement stmt = conn.createStatement();
      query = new StringBuffer("SELECT EC.ID_RICHIESTA, ");
      //query.append("TRUNC(EC.DATA_INSERIMENTO_RICHIESTA) AS DATA_INSERIMENTO_RICHIESTA, ");
      query.append("TO_CHAR(EC.DATA_INSERIMENTO_RICHIESTA,'DD/MM/YYYY') AS DATA_INSERIMENTO_RICHIESTA, ");
      query.append("EC.ANNO, ");
      query.append("EC.NUMERO_CAMPIONE, ");
      query.append("EC.CODICE_MATERIALE, ");
      query.append("M.DESCRIZIONE AS MATERIALE, ");
      query.append("EC.LABORATORIO_CONSEGNA, ");
      query.append("EC.LABORATORIO_ANALISI, ");
      query.append("EC.DESCRIZIONE_ETICHETTA, ");
      query.append("EC.ANAGRAFICA_UTENTE, ");
      query.append("EC.ANAGRAFICA_TECNICO, ");
      query.append("EC.ANAGRAFICA_PROPRIETARIO, ");
      query.append("EC.STATO_ANOMALIA, ");
      query.append("EC.NOTE_CLIENTE ");
      query.append("FROM ETICHETTA_CAMPIONE EC, MATERIALE M ");
      query.append("WHERE EC.ID_RICHIESTA = ");
      query.append(idRichiesta);
      query.append(" AND EC.CODICE_MATERIALE=M.CODICE_MATERIALE ");
      //CuneoLogger.debug(this,query.toString());
      ResultSet rset = stmt.executeQuery(query.toString());
      if (rset.next())
      {
        anagraficaUtenteSingolo=anagraficaUtente=rset.getString("ANAGRAFICA_UTENTE");

        this.anagraficaProprietario=rset.getString("ANAGRAFICA_PROPRIETARIO");
        if (this.getAnagraficaProprietario()==null)
          this.setAnagraficaProprietario(anagraficaUtente);
        proprietario=anagrafiche.getNomeCognome(conn, this.getAnagraficaProprietario());

        this.setAnagraficaTecnico(rset.getString("ANAGRAFICA_TECNICO"));
        if (this.getAnagraficaTecnico()==null)
          this.setAnagraficaRichiedente(anagraficaUtente);
        else
          this.setAnagraficaRichiedente(this.getAnagraficaTecnico());
        richiedente=anagrafiche.getNomeCognome(conn, this.getAnagraficaRichiedente());

        this.setIdRichiesta(rset.getString("ID_RICHIESTA"));
        this.setDataInserimentoRichiesta(rset.getString("DATA_INSERIMENTO_RICHIESTA"));
        this.setAnnoCampione(rset.getString("ANNO"));
        this.setNumeroCampione(rset.getString("NUMERO_CAMPIONE"));
        this.setCodiceMateriale(rset.getString("CODICE_MATERIALE"));
        this.setDescMateriale(rset.getString("MATERIALE"));
        this.setLaboratorioConsegna(rset.getString("LABORATORIO_CONSEGNA"));
        this.setLaboratorioAnalisi(rset.getString("LABORATORIO_ANALISI"));
        this.setCodLabAnalisi(rset.getString("LABORATORIO_ANALISI"));
        this.setDescrizioneEtichetta(rset.getString("DESCRIZIONE_ETICHETTA"));
        this.setNote(rset.getString("NOTE_CLIENTE"));
        this.setProprietario(proprietario);
        this.setRichiedente(richiedente);
        this.setStatoAnomalia(rset.getString("STATO_ANOMALIA"));
      }
      rset.close();
      stmt.close();
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("select della classe EtichettaCampione");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("select della classe EtichettaCampione"
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

  public long duplica(long idRichiesta)
  throws Exception, SQLException
  {
    if (!isConnection())
      throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn=null;
    CallableStatement cstmt=null;
    StringBuffer query = new StringBuffer();
    long idRichiestaNuovo = 0;
    try
    {
      conn = getConnection();
      cstmt = conn.prepareCall(
          "{?=call DUPLICA_RICHIESTA(?,?)}");
      cstmt.registerOutParameter(1,Types.NUMERIC);
      cstmt.setLong(2, idRichiesta);
      cstmt.setLong(3, new Long(this.getAut().getUtente().getAnagraficaUtente()));
      cstmt.execute();
      idRichiestaNuovo = cstmt.getBigDecimal(1).longValue();
      cstmt.close();
      return idRichiestaNuovo;
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("duplica della classe EtichettaCampione");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("duplica della classe EtichettaCampione"
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

  public void updateAnagraficaProprietario(Connection conn,
                                           String anagraficaProprietario,
                                           long idRichiesta)
      throws Exception, SQLException
  {
    Statement stmt = conn.createStatement();
    StringBuffer query = new StringBuffer("UPDATE ETICHETTA_CAMPIONE SET ");
    query.append("ANAGRAFICA_TECNICO = null,");
    query.append("ANAGRAFICA_PROPRIETARIO = ").append(anagraficaProprietario);
    query.append(" WHERE ID_RICHIESTA=").append(idRichiesta);
    stmt.executeUpdate( query.toString() );
    stmt.close();
  }

  public void updateAnagraficaTecnico(Connection conn,
                                           String anagraficaTecnico,
                                           long idRichiesta)
      throws Exception, SQLException
  {
    Statement stmt = conn.createStatement();
    StringBuffer query = new StringBuffer("UPDATE ETICHETTA_CAMPIONE SET ");
    query.append("ANAGRAFICA_TECNICO = ").append(anagraficaTecnico);
    query.append(" WHERE ID_RICHIESTA=").append(idRichiesta);
    stmt.executeUpdate( query.toString() );
    stmt.close();
  }

  public String[] getAnagraficaUtente()
  throws Exception, SQLException
  {
    String[] vetStr=null;
    if (!isConnection())
      throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn=null;
    StringBuffer query=new StringBuffer("");
    //String anagraficaProprietario;
    //String proprietario;
    try
    {
      conn=getConnection();
      Statement stmt = conn.createStatement();
      query = new StringBuffer("SELECT A.ID_ANAGRAFICA,");
      query.append("A.ID_ORGANIZZAZIONE,OP.ID_TIPO_ORGANIZZAZIONE ");
      query.append("FROM ETICHETTA_CAMPIONE EC, ANAGRAFICA A,");
      query.append("ORGANIZZAZIONE_PROFESSIONALE OP ");
      query.append("WHERE EC.ID_RICHIESTA = ");
      query.append(this.getAut().getIdRichiestaCorrente());
      query.append(" AND EC.ANAGRAFICA_TECNICO=A.ID_ANAGRAFICA");
      query.append(" AND A.ID_ORGANIZZAZIONE=OP.ID_ORGANIZZAZIONE");
      //CuneoLogger.debug(this,query.toString());
      ResultSet rset = stmt.executeQuery(query.toString());
      if (rset.next())
      {
        vetStr=new String[3];
        vetStr[ID_ANAGRAFICA]=rset.getString("ID_ANAGRAFICA");
        vetStr[ID_ORGANIZZAZIONE]=rset.getString("ID_ORGANIZZAZIONE");
        vetStr[ID_TIPO_ORGANIZZAZIONE]=rset.getString("ID_TIPO_ORGANIZZAZIONE");
      }
      rset.close();
      stmt.close();
      return vetStr;
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("getAnagraficaUtente della classe EtichettaCampione");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("getAnagraficaUtente della classe EtichettaCampione"
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
   * Questa funzione viene utilizzata quando il campione viene inserito da
   * un utente LAR.
   * Ci restituisce true quando il proprietario del campione è un tecnico
   * e false quando è un privato
   *
   * @return true quando il proprietario del campione è un tecnico, false quando è un privato
   * @throws Exception
   * @throws SQLException
   */
  public boolean isProprietarioTecnico()
  throws Exception, SQLException
  {
    if (!isConnection())
      throw new Exception("Riferimento a DataSource non inizializzato");
    StringBuffer query=null;
    Statement stmt =null;
    Connection conn=null;
    try
    {
      conn=getConnection();
      stmt = conn.createStatement();
      query = new StringBuffer("SELECT ANAGRAFICA_TECNICO ");
      query.append("FROM ETICHETTA_CAMPIONE ");
      query.append("WHERE ID_RICHIESTA = ");
      query.append(this.getAut().getIdRichiestaCorrente());
      //CuneoLogger.debug(this,query.toString());
      ResultSet rset = stmt.executeQuery(query.toString());
      if (rset.next())
      {
        if (rset.getString("ANAGRAFICA_TECNICO")!=null)
          return true;
      }
      return false;
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("isProprietarioTecnico della classe EtichettaCampione");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("isProprietarioTecnico della classe EtichettaCampione"
                            +": non è una SQLException ma una Exception"
                            +" generica");
      this.getAut().setContenutoQuery(query.toString());
      throw (e);
    }
    finally
    {
      stmt.close();
      if (conn!=null) conn.close();
    }
  }

  /**
   *  Il metodo confermaFase7 permette di far passare un campione dallo stato
   * in bozza allo stato di analisi richiesta
   *
   * @throws Exception
   * @throws SQLException
   */
  public void confermaFase7()
      throws Exception, SQLException
  {
	final String LOG_HEADER = CLASS_NAME+"::confermaFase7() - ";
    CuneoLogger.debug(this,LOG_HEADER + "INIZIO");
	
    if (!isConnection())
           throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn = null;
    StringBuffer query = new StringBuffer();
    Statement stmt = null;

    try
    {
      conn = getConnection();
      // Si imposta a false l'autocommit perché le operazioni che vengono eseguite sul DB
      // devono essere effettuate in blocco
      // Si noti l'invocazione del metodo conn.commit() PRIMA di conn.close() e
      // l'invocazione di conn.rollback() nel blocco catch{}
      conn.setAutoCommit( false );
      // Per evitare ogni rischio di interferenza nel recuperare l'ultimo valore creato
      // nella sequence, imposto il massimo livello di isolamento di questa transazione
      // conn.setTransactionIsolation( Connection.TRANSACTION_SERIALIZABLE );

      /**
       * Cancello dalla tabella FASI_RICHIESTA il record relativo alla
       * richiesta che sta per passare da bozza ad analisi
       * */
      query.append("DELETE FROM FASI_RICHIESTA WHERE ");
      query.append("ID_RICHIESTA =");
      query.append(this.getIdRichiesta());
      CuneoLogger.debug(this,LOG_HEADER + query.toString());
      stmt = conn.createStatement();
      stmt.executeUpdate(query.toString());
      stmt.close();
      CuneoLogger.debug(this,LOG_HEADER + "Query 1 completata");

      /**
       * Modifico la tabella ETICHETTA_CAMPIONE cambiando STATO_ATTUALE in '10'
       * (Analisi richiesta) ed aggiorno la data di inserimento
       * */
      query.setLength(0);
      query.append("UPDATE ETICHETTA_CAMPIONE SET ");
      query.append("DATA_INSERIMENTO_RICHIESTA =");
      query.append("to_timestamp('").append(Utili.getSystemDate());
      query.append("','DD-MM-YYYY'),");
      query.append("STATO_ATTUALE = '10' ");
      query.append("WHERE ID_RICHIESTA = ");
      query.append(this.getIdRichiesta());
      CuneoLogger.debug(this,LOG_HEADER + query.toString());
      stmt = conn.createStatement();
      stmt.executeUpdate(query.toString());
      stmt.close();
      CuneoLogger.debug(this,LOG_HEADER + "Query 2 completata");

      /**
       * Inserisco il record corrispondente a questo campione sulla tabella
       * TRACCIATURA
       * */
      query.setLength(0);
      query.append("INSERT INTO TRACCIATURA(ID_RICHIESTA, PROGRESSIVO,");
      query.append("CODICE,DATA)");
      query.append("VALUES(");
      query.append(this.getIdRichiesta());
      query.append(",1");
      query.append(",'10'");
      query.append(",to_timestamp('").append(Utili.getSystemDate());
      query.append("','DD-MM-YYYY'))");
      CuneoLogger.debug(this,LOG_HEADER + query.toString());
      stmt = conn.createStatement();
      stmt.executeUpdate(query.toString());
      stmt.close();
      CuneoLogger.debug(this,LOG_HEADER + "Query 3 completata");

      conn.commit();
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("confermaFase7 della classe EtichettaCampione");
      this.getAut().setContenutoQuery(query.toString());
      try
      {
        conn.rollback();
        CuneoLogger.error(this,LOG_HEADER + ex.getMessage());
      }
      catch( java.sql.SQLException ex2 )
      {
        this.getAut().setQuery("confermaFase7 della classe EtichettaCampione"
                               +":problemi con il rollback");
        this.getAut().setContenutoQuery(query.toString());
        CuneoLogger.error(this,LOG_HEADER + ex2.getMessage());
        throw (ex2);
      }
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("confermaFase7 della classe EtichettaCampione"
                             +": non è una SQLException ma una Exception"
                             +" generica");
      this.getAut().setContenutoQuery(query.toString());
      CuneoLogger.error(this,LOG_HEADER + e.getMessage());
      throw (e);
    }
    finally
    {
      if (conn!=null)
      {
        conn.setAutoCommit(true);
        conn.close();
        CuneoLogger.debug(this,LOG_HEADER + "FINE");
      }
    }
  }


  /**
   * Il metodo getCostoAnalisi() equivale ad un attributo calcolato
   * della richiesta corrispondente ad un'istanza di questa classe.
   *
   * @return Una stringa contenente il costo dell'analisi
   * @throws Exception
   * @throws SQLException
   */
  public String getCostoAnalisi()
  throws Exception, SQLException
  {
    return getCostoAnalisi(null);
  }

  /**
   * Il metodo getCostoAnalisi() equivale ad un attributo calcolato
   * della richiesta corrispondente ad un'istanza di questa classe.
   *
   * @param costoSpedizione Se <b>null</b> viene ignorato, altrimenti deve contenere il costo della spedizione, che viene aggiunto al totale
   * @return Una stringa contenente il costo dell'analisi
   * @throws Exception
   * @throws SQLException
   */
  public String getCostoAnalisi(String costoSpedizione)
  throws Exception, SQLException
  {
    //Anagrafiche anagrafiche=new Anagrafiche();
    if (!isConnection())
      throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn=null;
    StringBuffer query=new StringBuffer("");
    double importoAnalisi=0;
    try
    {
      conn=getConnection();
      Statement stmt = conn.createStatement();
      query = new StringBuffer("SELECT SUM(AR.COSTO_ANALISI) AS COSTO_ANALISI ");
      query.append("FROM ANALISI_RICHIESTE AR ");
      query.append("WHERE AR.ID_RICHIESTA = ");
      query.append(this.idRichiesta);
      //CuneoLogger.debug(this,query.toString());
      ResultSet rset = stmt.executeQuery(query.toString());
      if (rset.next())
        importoAnalisi=rset.getDouble("COSTO_ANALISI");
      rset.close();
      stmt.close();
      importoAnalisi+=Double.parseDouble(costoSpedizione);
      //return Utili.valuta(importoAnalisi);
      return (Utili.nf2.format(importoAnalisi)).replace(',','.');
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("getCostoAnalisi della classe EtichettaCampione");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("getCostoAnalisi della classe EtichettaCampione"
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

  public String getDataRicezione()
      throws Exception, SQLException
  {
    if (!isConnection())
      throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn=null;
    StringBuffer query=new StringBuffer("");
    String dataRicezione="";
    try
    {
      conn=getConnection();
      Statement stmt = conn.createStatement();
      query = new StringBuffer("SELECT TO_CHAR(DATA,'DD/MM/YYYY') AS DATA ");
      query.append("FROM TRACCIATURA ");
      query.append("WHERE ID_RICHIESTA=");
      query.append(this.getIdRichiesta());
      query.append("AND CODICE='20' "); // codice "ricezione"
      query.append("ORDER BY DATA"); // nel dubbio ne esistano diversi, prendo sempre il primo
      //CuneoLogger.debug(this,query.toString());
      ResultSet rset = stmt.executeQuery(query.toString());
      if (rset.next())
        dataRicezione=rset.getString("DATA");
      rset.close();
      stmt.close();
      return dataRicezione;
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("getCostoAnalisi della classe EtichettaCampione");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("getCostoAnalisi della classe EtichettaCampione"
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

  
  public String getRiferCatCoorGeoPDF()
	      throws Exception, SQLException
  {
    if (!isConnection())
      throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn=null;
    StringBuffer query = new StringBuffer("");
    StringBuffer riferCatCoorGeo=new StringBuffer("");
    try
    {
      conn=getConnection();
      Statement stmt = conn.createStatement();
      query.append("SELECT C.DESCRIZIONE, P.SIGLA_PROVINCIA, D.COORDINATA_NORD_UTM, D.COORDINATA_EST_UTM, ");
      query.append("D.SEZIONE, D.FOGLIO, D.PARTICELLA_CATASTALE, D.tipo_georeferenziazione ");
      query.append("FROM DATI_APPEZZAMENTO D ");
      query.append("	LEFT JOIN COMUNE C ON D.COMUNE_APPEZZAMENTO=C.CODICE_ISTAT ");
      query.append("	LEFT JOIN PROVINCIA P ON C.PROVINCIA=P.ID_PROVINCIA ");
      query.append("WHERE ID_RICHIESTA= ").append(idRichiesta);
      
      //CuneoLogger.debug(this, query.toString());
      /*
	      Per le coordinate vengono visualizzati  campi COORDINATA_NORD_UTM e COORDINATA_EST_UTM della tabella DATI_APPEZAMENTO 
	      (le coordinate sono visualizzate solamente se valorizzate).
	      Per i riferimenti catastali (visualizzati se campo PARTICELLA di DAT_APPEZZAMENTO è valorizzato) 
	      vengono visualizzati la decodifica del campo COMUNE_APPEZZAMENTO (descrizione del comune e sigla provincia), 
	      il campo SEZIONE, il campo FOGLIO e il campo PARTICELLA_CATASTALE.
	      
	      UTM Nord 123456  - UTM Est 123456
		  Caluso (TO) - Sez. 1 - Fo 12 - Part. 33			

      */
      
      ResultSet rset = stmt.executeQuery(query.toString());
      if (rset.next())
      {
    	String comune=rset.getString("DESCRIZIONE");
    	String siglaProv=rset.getString("SIGLA_PROVINCIA");
    	String nordUtm=rset.getString("COORDINATA_NORD_UTM");
    	String estUtm=rset.getString("COORDINATA_EST_UTM");
    	String particella=rset.getString("PARTICELLA_CATASTALE");
    	String sezione=rset.getString("SEZIONE");
    	String foglio=rset.getString("FOGLIO");
    	String tipoGeo = rset.getString("tipo_georeferenziazione");
    	
    
    	
    	if (!(Utili.isEmpty(nordUtm) || Utili.isEmpty(estUtm)))
    	{
    		riferCatCoorGeo.append("UTM Nord ").append(nordUtm);
    		riferCatCoorGeo.append(" - UTM Est ").append(estUtm).append("\n");
    	}
    	riferCatCoorGeo.append(comune);
    	if (!Utili.isEmpty(siglaProv))
    		riferCatCoorGeo.append(" (").append(siglaProv).append(")");
    	
    	//JIRA 114
    	//26/04/2017 aggiunto il controllo sull fatto che tipoGeo possa essere null
    	if(this.getAut().getUtente().getTipoUtente() == 'P' || this.getAut().getUtente().getTipoUtente() == 'T')
            if(tipoGeo!=null && !tipoGeo.equals("C"))
       	 {
            	sezione="";
            	foglio="";
            	particella="";
       	 }
    	
		if (!Utili.isEmpty(sezione))
			riferCatCoorGeo.append(" - Sez. ").append(sezione);
		if (!Utili.isEmpty(foglio))	
			riferCatCoorGeo.append(" - Fo ").append(foglio);
		if (!Utili.isEmpty(particella))
			riferCatCoorGeo.append(" - Part. ").append(particella);
    	
      }
      rset.close();
      stmt.close();
      return riferCatCoorGeo.toString();
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("getRiferCatCoorGeoPDF della classe EtichettaCampione");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("getRiferCatCoorGeoPDF della classe EtichettaCampione"
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
   * Questo metodo viene utilizzato per creare il pdf del risultato delle
   * analisi
   * */
  public boolean selectPerPDFRisultatoAnalisi(boolean terreno,String riga1[],String riga2[],String riga3[],String codMateriale)
      throws Exception, SQLException
  {
    if (!isConnection())
      throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn=null;
    StringBuffer query=new StringBuffer("");
    String anagraficaTecnico="";
    String anagUtente="",anagProprietario="";
    boolean ris=false;
    try
    {
      conn=getConnection();
      Statement stmt = conn.createStatement();

      query = new StringBuffer("SELECT E.ANAGRAFICA_UTENTE, ");
      query.append("E.ANAGRAFICA_TECNICO,A.TIPO_UTENTE");
      query.append(",E.ANAGRAFICA_PROPRIETARIO ");
      query.append("FROM ETICHETTA_CAMPIONE E LEFT OUTER JOIN ANAGRAFICA A ON (E.ANAGRAFICA_UTENTE=A.ID_ANAGRAFICA) ");
      query.append("WHERE E.ID_RICHIESTA=");
      query.append(this.getIdRichiesta());
      //CuneoLogger.debug(this,query.toString());
      ResultSet rset = stmt.executeQuery(query.toString());
      if (rset.next())
      {
        anagraficaTecnico=rset.getString("ANAGRAFICA_TECNICO");
        anagUtente=rset.getString("ANAGRAFICA_UTENTE");
        anagProprietario=rset.getString("ANAGRAFICA_PROPRIETARIO");
        if (anagProprietario==null) anagProprietario=anagUtente;
        if (anagraficaTecnico==null)
        {
          anagraficaTecnico=anagUtente;
          String tipo=rset.getString("TIPO_UTENTE");
          if ("T".equals(tipo)) ris=true;
        }
        else ris=true;
      }
      rset.close();
      query.setLength(0);
      if (ris)
      {
        query.append("SELECT O.RAGIONE_SOCIALE,");
        query.append("C2.DESCRIZIONE AS COMUNE_ORG,A.COGNOME_RAGIONE_SOCIALE,");
        query.append("A.NOME,A.INDIRIZZO,C1.DESCRIZIONE AS COMUNE_IND,");
        query.append("P.SIGLA_PROVINCIA AS PROVINCIA,");
        query.append("A.TELEFONO,A.CELLULARE ");
        query.append("FROM ANAGRAFICA A, COMUNE C1,COMUNE C2, PROVINCIA P,");
        query.append("ORGANIZZAZIONE_PROFESSIONALE O ");
        query.append("WHERE A.ID_ANAGRAFICA=").append(anagraficaTecnico);
        query.append(" AND C1.CODICE_ISTAT=A.COMUNE_RESIDENZA");
        query.append(" AND P.ID_PROVINCIA=C1.PROVINCIA");
        query.append(" AND O.ID_ORGANIZZAZIONE=A.ID_ORGANIZZAZIONE");
        query.append(" AND C2.CODICE_ISTAT=O.COMUNE_RESIDENZA");
        //CuneoLogger.debug(this,query.toString());
        rset = stmt.executeQuery(query.toString());
        String temp1=null,temp2=null,temp3=null;
        if (rset.next())
        {
          temp1=rset.getString("RAGIONE_SOCIALE");
          temp2=rset.getString("COMUNE_ORG");
          if (temp1==null) temp1="";
          if (temp2==null) temp2="";
          if ("".equals(temp2)) riga1[0]=temp1;
          else
          {
            if ("".equals(temp1)) riga1[0]=temp2;
            else riga1[0]=temp1+ " - "+temp2;
          }

          temp1=rset.getString("COGNOME_RAGIONE_SOCIALE");
          temp2=rset.getString("NOME");
          if (temp1==null) temp1="";
          if (temp2==null) temp2="";
          riga1[1]=temp1+ " " +temp2;

          temp1=rset.getString("INDIRIZZO");
          temp2=rset.getString("COMUNE_IND");
          temp3=rset.getString("PROVINCIA");
          if (temp1==null) temp1="";
          if (temp2==null) temp2="";
          if (temp3==null) temp3="";
          if ("".equals(temp2)) riga1[2]=temp1;
          else
          {
            if ("".equals(temp1)) riga1[2]=temp2;
            else riga1[2]=temp1+ " - "+temp2;
          }

          riga1[2]+=" ("+temp3+")";

          temp1=rset.getString("TELEFONO");

          if (temp1==null)
          {
            temp1=rset.getString("CELLULARE");
            if (temp1==null) temp1="";
          }
          riga1[3]=temp1;
        }
        else
        {
          ris=false;
        }
        rset.close();
      }
      query.setLength(0);
      if (terreno)
      {
        query.append("SELECT PROP.COGNOME_RAGIONE_SOCIALE,PROP.NOME,");
        query.append("PROP.INDIRIZZO,C1.DESCRIZIONE AS COMUNE_IND,");
        query.append("P.SIGLA_PROVINCIA AS PROVINCIA,PROP.TELEFONO,PROP.CELLULARE,");
        query.append("UTENTE.COGNOME_RAGIONE_SOCIALE COG_TITOLARE,");
        query.append("UTENTE.NOME AS NOME_TITOLARE,");
        query.append(" C.DESCRIZIONE || ' - ' || S.DESCRIZIONE AS SPECIE_COLTURA,");
        query.append(" C2.DESCRIZIONE || ' - ' || S2.DESCRIZIONE AS SPECIE_COLTURA_ATTUALE ");
        query.append("FROM ANAGRAFICA PROP,ANAGRAFICA UTENTE,COMUNE C1,PROVINCIA P");
        query.append(",DATI_CAMPIONE_TERRENO D ");
        query.append("left join SPECIE_COLTURA S2 on s2.ID_SPECIE = D.COLTURA_ATTUALE ");
        query.append("left join CLASSE_COLTURA C2 on C2.ID_COLTURA = S2.ID_COLTURA ");
        query.append(",CLASSE_COLTURA C,SPECIE_COLTURA S");        
        query.append(" WHERE PROP.ID_ANAGRAFICA=").append(anagProprietario);
        query.append(" AND UTENTE.ID_ANAGRAFICA=").append(anagUtente);
        query.append(" AND D.ID_RICHIESTA=").append(getIdRichiesta());
        query.append(" AND C1.CODICE_ISTAT=PROP.COMUNE_RESIDENZA ");
        query.append(" AND P.ID_PROVINCIA=C1.PROVINCIA");
        query.append(" AND S.ID_SPECIE = D.COLTURA_PREVISTA");
        query.append(" AND C.ID_COLTURA=S.ID_COLTURA");
      }
      else
      {
        query.append("SELECT PROP.COGNOME_RAGIONE_SOCIALE,PROP.NOME,");
        query.append("PROP.INDIRIZZO,C1.DESCRIZIONE AS COMUNE_IND,");
        query.append("P.SIGLA_PROVINCIA AS PROVINCIA,PROP.TELEFONO,PROP.CELLULARE,");
        query.append("UTENTE.COGNOME_RAGIONE_SOCIALE COG_TITOLARE,");
        query.append("UTENTE.NOME AS NOME_TITOLARE ");
        query.append("FROM ANAGRAFICA PROP,ANAGRAFICA UTENTE,COMUNE C1,PROVINCIA P");
        query.append(" WHERE PROP.ID_ANAGRAFICA=").append(anagProprietario);
        query.append(" AND UTENTE.ID_ANAGRAFICA=").append(anagUtente);
        query.append(" AND C1.CODICE_ISTAT=PROP.COMUNE_RESIDENZA ");
        query.append(" AND P.ID_PROVINCIA=C1.PROVINCIA");
      }
      //CuneoLogger.debug(this,query.toString());
      rset = stmt.executeQuery(query.toString());
      String temp1=null,temp2=null,temp3=null;
      if (rset.next())
      {
        temp1=rset.getString("COGNOME_RAGIONE_SOCIALE");
        temp2=rset.getString("NOME");
        if (temp1==null) temp1="";
        if (temp2==null) temp2="";
        if ("".equals(temp2)) riga2[0]=temp1;
        else
        {
          if ("".equals(temp1)) riga2[0]=temp2;
          else riga2[0]=temp1+ " "+temp2;
        }

        temp1=rset.getString("COG_TITOLARE");
        temp2=rset.getString("NOME_TITOLARE");
        if (temp1==null) temp1="";
        if (temp2==null) temp2="";
        riga2[1]=temp1+ " " +temp2;

        temp1=rset.getString("INDIRIZZO");
        temp2=rset.getString("COMUNE_IND");
        temp3=rset.getString("PROVINCIA");
        if (temp1==null) temp1="";
        if (temp2==null) temp2="";
        if (temp3==null) temp3="";
        if ("".equals(temp2)) riga2[2]=temp1;
        else
        {
          if ("".equals(temp1)) riga2[2]=temp2;
          else riga2[2]=temp1+ " - "+temp2;
        }

          riga2[2]+=" ("+temp3+")";

        temp1=rset.getString("TELEFONO");

        if (temp1==null)
        {
          temp1=rset.getString("CELLULARE");
          if (temp1==null) temp1="";
        }
        riga2[3]=temp1;
        if (terreno)
        {
          riga3[0]=rset.getString("SPECIE_COLTURA_ATTUALE");
          riga3[1]=rset.getString("SPECIE_COLTURA");
        }
      }
      rset.close();

      if (!terreno)
      {
        query.setLength(0);
        if ("ERB".equals(codMateriale))
        {
          query.append("SELECT SO.DESCRIZIONE AS SPECIE,");
          query.append("CC.DESCRIZIONE AS COLTURA ");
          query.append("FROM CAMPIONE_VEGETALI_ERBACEE CVE, ");
          query.append("SPECIE_COLTURA SO LEFT OUTER JOIN CLASSE_COLTURA CC ON (SO.ID_COLTURA = CC.ID_COLTURA) ");
          query.append("WHERE CVE.ID_RICHIESTA=");
          query.append(this.getIdRichiesta());
          query.append(" AND CVE.ID_SPECIE=SO.ID_SPECIE");
        }
        if ("FOG".equals(codMateriale) || "FRU".equals(codMateriale))
        {
          query.append("SELECT SO.DESCRIZIONE AS SPECIE, CVF.ALTRA_SPECIE,");
          query.append("CC.DESCRIZIONE AS COLTURA ");
          query.append("FROM CAMPIONE_VEGETALI_FOGLIEFRUTTA CVF ");
          query.append("LEFT OUTER JOIN SPECIE_COLTURA SO ON (CVF.ID_SPECIE=SO.ID_SPECIE) ");
          query.append("LEFT OUTER JOIN CLASSE_COLTURA CC ON (CVF.ID_COLTURA = CC.ID_COLTURA) ");
          query.append("WHERE CVF.ID_RICHIESTA=");
          query.append(this.getIdRichiesta());
        }
        //CuneoLogger.debug(this,query.toString());
        rset = stmt.executeQuery(query.toString());
        if (rset.next())
        {
          riga3[0]=rset.getString("COLTURA");
          riga3[1]=rset.getString("SPECIE");
          if (riga3[1]==null || "".equals(riga3[1]))
          {
            if ("FOG".equals(getCodiceMateriale()) || "FRU".equals(getCodiceMateriale()))
            {
              riga3[1]=rset.getString("ALTRA_SPECIE");
            }
          }
        }
        rset.close();
      }
      rset.close();
      stmt.close();
      return ris;
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("selectPerPDFRisultatoAnalisi della classe EtichettaCampione");
      this.getAut().setContenutoQuery(query.toString());
      ex.printStackTrace();
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("selectPerPDFRisultatoAnalisi della classe EtichettaCampione"
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
   * Questo metodo mi restituisce l'immagine della firma del firmatario
   * dell'esito dell'analisi
   * */
  public boolean getFirma(Responsabile resp)
  throws Exception, SQLException
  {
    Connection conn=this.getConnection();
    StringBuffer query=new StringBuffer("");
    Statement stmt = null;
    InputStream resource=null;
    boolean trovato=false;
    try
    {
      stmt = conn.createStatement();
      query.append("SELECT F.NOME,F.COGNOME,F.TITOLO_ONORIFICO,F.FIRMA,FR.NOTE,");
      query.append("FR.ID_FIRMA ");
      query.append("FROM FIRMA_REFERTO FR LEFT OUTER JOIN FIRMA F ON (FR.ID_FIRMA = F.ID_FIRMA) ");
      query.append("WHERE FR.ID_RICHIESTA = ").append(getIdRichiesta());
      //CuneoLogger.debug(this,"query.toString() "+query.toString());
      ResultSet rset = stmt.executeQuery(query.toString());
      if (rset.next())
      {
        if (rset.getString("ID_FIRMA")!=null)
        {
          Blob bTemp=null;
          try
          {
            bTemp=rset.getBlob("FIRMA");
            resource=bTemp.getBinaryStream();
            resp.setNome(rset.getString("NOME"));
            resp.setCognome(rset.getString("COGNOME"));
            resp.setTitoloOnorifico(rset.getString("TITOLO_ONORIFICO"));
          }
          catch(Exception e) {}
          trovato=true;
        }
        resp.setNote(rset.getString("NOTE"));
      }
      rset.close();
    }
    catch(java.sql.SQLException ex)
    {
      ex.printStackTrace();
      this.getAut().setQuery("getFirma della classe EtichettaCampione");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    }
    catch(Exception e)
    {
      e.printStackTrace();
      this.getAut().setQuery("getFirma della classe EtichettaCampione"
                              +": non è una SQLException ma una Exception"
                              +" generica");
      this.getAut().setContenutoQuery(query.toString());
      throw (e);
    }
    finally
    {
      try
      {
        if (stmt!=null) stmt.close();
        if (conn!=null) conn.close();
      }
      catch(Exception e) {}
      if (resource!=null)
      {
        byte[] buffer = new byte[1024];
        try
        {
          BufferedInputStream in = new BufferedInputStream(resource);
          ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
          int n;
          while ((n = in.read(buffer)) > 0)
            out.write(buffer, 0, n);
          in.close();
          out.flush();
          buffer = out.toByteArray();
        }
        catch (IOException ioe)
        {
          System.err.println(ioe.toString());
        }

        if (buffer != null)
          resp.setFirma(new ImageIcon(buffer).getImage());
       }
    }
    return trovato;
  }

  /**
   * Questo metodo viene utilizzato per impostare quale tariffa udare per
   * questo campione
   *
   * @param LAR: se è true significa che la richeista è stata fatta da un LAR
   * @param tecnico: se è true significa che la richeista è stata fatta da un
   *  tecnico
   * @param privato: se è true significa che la richeista è stata fatta da un
   *  privato
   * attribuire per questa analisi una tariffa diversa da quella che spetterebbe
   * @throws Exception
   * @throws SQLException
   */
  public void impostaTariffaApplicata(boolean LAR,boolean tecnico,boolean privato, Boolean isPresenteAnagrafe) throws Exception, SQLException
  {
    impostaTariffaApplicata(LAR, tecnico, privato, (byte) -1, isPresenteAnagrafe);
  }


  /**
   * Questo metodo viene utilizzato per impostare quale tariffa udare per
   * questo campione
   *
   * @param LAR: se è true significa che la richeista è stata fatta da un LAR
   * @param tecnico: se è true significa che la richeista è stata fatta da un
   *  tecnico
   * @param privato: se è true significa che la richeista è stata fatta da un
   *  privato
   * @@param tariffaPrevista: se diverso da 1 è utilizzato per
   * attribuire per questa analisi una tariffa diversa da quella che spetterebbe
   * @throws Exception
   * @throws SQLException
   */
  public void impostaTariffaApplicata(boolean LAR,boolean tecnico,boolean privato, byte tariffaPrevista, Boolean isPresenteAnagrafe) throws Exception, SQLException  {
	CuneoLogger.debug(this,"impostaTariffaApplicata BEGIN");  
	CuneoLogger.debug(this,"LAR "+LAR);  
	CuneoLogger.debug(this,"tecnico "+tecnico);  
	CuneoLogger.debug(this,"privato "+privato); 
	CuneoLogger.debug(this,"isPresenteAnagrafe "+isPresenteAnagrafe); 
	CuneoLogger.debug(this,"tariffaPrevista "+tariffaPrevista);  
    if (!isConnection())
           throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn = null;
    StringBuffer query = new StringBuffer();
    Statement stmt = null;
    byte tariffa=0;
    try {
      conn = getConnection();
      // Si imposta a false l'autocommit perché le operazioni che vengono eseguite sul DB
      // devono essere effettuate in blocco
      // Si noti l'invocazione del metodo conn.commit() PRIMA di conn.close() e
      // l'invocazione di conn.rollback() nel blocco catch{}
      conn.setAutoCommit( false );
      // Per evitare ogni rischio di interferenza nel recuperare l'ultimo valore creato
      // nella sequence, imposto il massimo livello di isolamento di questa transazione
      //conn.setTransactionIsolation( Connection.TRANSACTION_SERIALIZABLE );
      stmt = conn.createStatement();

      //Tariffa 3 =  Fascia A (sconto 35%)
      //Tariffa 2 =  Fascia B (sconto 15%)
      //Tariffa 1 =  Fascia C (sconto 10%)
      
      if (tariffaPrevista != -1) {
        tariffa=tariffaPrevista;
      } else  {
        if (tecnico) {
          if (LAR) {
        	  //if()
            query.setLength(0);
            query.append("SELECT O.CONVENZIONATA ");
            query.append("FROM ETICHETTA_CAMPIONE E,ANAGRAFICA A,");
            query.append("ORGANIZZAZIONE_PROFESSIONALE OP,");
            query.append("TIPO_ORGANIZZAZIONE O ");
            query.append("WHERE E.ID_RICHIESTA=").append(getIdRichiesta());
            query.append(" AND E.ANAGRAFICA_TECNICO=A.ID_ANAGRAFICA ");
            query.append("AND A.ID_ORGANIZZAZIONE=OP.ID_ORGANIZZAZIONE ");
            query.append("AND OP.ID_TIPO_ORGANIZZAZIONE=O.ID_TIPO_ORGANIZZAZIONE ");
            //CuneoLogger.debug(this,"query tecnico LAR"+query.toString());
            ResultSet rset = stmt.executeQuery(query.toString());
            if (rset.next()) {
              if ("S".equals(rset.getString("CONVENZIONATA"))){
            	 /* if (isPresenteAnagrafe != null && isPresenteAnagrafe.booleanValue())
            		  tariffa=3;
            	  else {*/
	                //Richiedente LAR per tecnico convenzionato
	                tariffa=2;// sempre 2 nel caso in cui tecnico, lar, convenzionato
            	//  }
              }else{
            	  if (isPresenteAnagrafe != null && isPresenteAnagrafe.booleanValue())
            		  tariffa=2;
            	  else{
	                //Richiedente LAR per tecnico non convenzionato
	                tariffa=1;
            	  }
              }
            }else{
            	if (isPresenteAnagrafe != null && isPresenteAnagrafe.booleanValue())
          		  tariffa=2;
          	  	else{
	                //Richiedente LAR per tecnico non convenzionato
	                tariffa=1;
          	    }
            }
            rset.close();
          }else{
            query.setLength(0);
            query.append("SELECT O.CONVENZIONATA ");
            query.append("FROM ETICHETTA_CAMPIONE E,ANAGRAFICA A,");
            query.append("ORGANIZZAZIONE_PROFESSIONALE OP,");
            query.append("TIPO_ORGANIZZAZIONE O ");
            query.append("WHERE E.ID_RICHIESTA=").append(getIdRichiesta());
            query.append(" AND E.ANAGRAFICA_UTENTE=A.ID_ANAGRAFICA ");
            query.append("AND A.ID_ORGANIZZAZIONE=OP.ID_ORGANIZZAZIONE ");
            query.append("AND OP.ID_TIPO_ORGANIZZAZIONE=O.ID_TIPO_ORGANIZZAZIONE ");
            //CuneoLogger.debug(this,"query tecnico "+query.toString());
            ResultSet rset = stmt.executeQuery(query.toString());
            if (rset.next()){
              if ("S".equals(rset.getString("CONVENZIONATA"))){  //in questo caso è superfluo controllare se è in anagrafe o meno tanto la tariffa sarebbe sempre 3
                //Richiedente tecnico convenzionato
                tariffa=3;
              }else{
            	  if (isPresenteAnagrafe != null && isPresenteAnagrafe.booleanValue())
            		  tariffa=3;
            	  else{
	                //Richiedente tecnico non convenzionato
	                tariffa=2;
            	  }
              }
            }else{
            	 if (isPresenteAnagrafe != null && isPresenteAnagrafe.booleanValue())
            		 tariffa=3;
           	     else{
	                //Richiedente tecnico non convenzionato
	                tariffa=2;
           	      }
            }
            rset.close();
          }
        }
        if (privato){
          if (LAR){
            //Richiedente privato con LAR

          	if (isPresenteAnagrafe != null && isPresenteAnagrafe.booleanValue()){
            	//AGRICHIM-8
            	//Gestione dell'attribuzione della fascia di sconto più conveniente per le aziende agricole che presentano in proprio le richiesta (utente privato)          		
          		tariffa = 2;
          	}else{
          		tariffa = 1;
          	}
          } else{
            //Richiedente privato internet
          	
          	if (isPresenteAnagrafe != null && isPresenteAnagrafe.booleanValue()){
            	//AGRICHIM-8
            	//Gestione dell'attribuzione della fascia di sconto più conveniente per le aziende agricole che presentano in proprio le richiesta (utente privato)          		
          		tariffa = 3;
          	}else{
          		tariffa = 2;
          	}
          }
        }
      }


      /**
       * Modifico la tabella ETICHETTA_CAMPIONE cambiando la tariffa
       * */
      query.setLength(0);
      query.append("UPDATE ETICHETTA_CAMPIONE SET ");
      query.append(" TARIFFA_APPLICATA =").append(tariffa);
      query.append(" WHERE ID_RICHIESTA = ");
      query.append(this.getIdRichiesta());
      CuneoLogger.debug(this,"Update: "+query.toString());
      stmt.executeUpdate(query.toString());

      stmt.close();
      conn.commit();

      CuneoLogger.debug(this,"tariffa "+tariffa);
    }catch(java.sql.SQLException ex){
      this.getAut().setQuery("impostaTariffaApplicata della classe EtichettaCampione");
      this.getAut().setContenutoQuery(query.toString());
      try {
        conn.rollback();
      } catch( java.sql.SQLException ex2 ) {
        this.getAut().setQuery("impostaTariffaApplicata della classe EtichettaCampione"
                               +":problemi con il rollback");
        this.getAut().setContenutoQuery(query.toString());
        throw (ex2);
      }
      throw (ex);
    }catch(Exception e){
      this.getAut().setQuery("impostaTariffaApplicata della classe EtichettaCampione"
                             +": non è una SQLException ma una Exception"
                             +" generica");
      this.getAut().setContenutoQuery(query.toString());
      throw (e);
    }finally{
      if (conn!=null){
        conn.setAutoCommit(true);
        conn.close();
      }
    }
  }


  public void setIdRichiesta (String idRichiesta)
  {
    this.idRichiesta =idRichiesta ;
  }

  public void setDataInserimentoRichiesta (String dataInserimentoRichiesta)
  {
    this.dataInserimentoRichiesta = dataInserimentoRichiesta;
  }

  public void setDescMateriale (String descMateriale)
  {
    this.descMateriale = descMateriale;
  }

  public void setDescrizioneEtichetta (String descrizioneEtichetta)
  {
    this.descrizioneEtichetta = descrizioneEtichetta;
  }

  public String getIdRichiesta()
  {
    return idRichiesta;
  }
  public String getDataInserimentoRichiesta()
  {
    return dataInserimentoRichiesta;
  }
  public String getDescMateriale()
  {
    return descMateriale;
  }
  public String getDescrizioneEtichetta()
  {
    return descrizioneEtichetta;
  }
  public void setProprietario(String proprietario)
  {
    this.proprietario = proprietario;
  }
  public String getProprietario()
  {
    return proprietario;
  }
  public void setRichiedente(String richiedente)
  {
    this.richiedente = richiedente;
  }
  public String getRichiedente()
  {
    return richiedente;
  }
  public void setDescStatoAttuale(String descStatoAttuale)
  {
    this.descStatoAttuale = descStatoAttuale;
  }
  public String getDescStatoAttuale()
  {
    return descStatoAttuale;
  }
  public void setStatoAnomalia(String statoAnomalia)
  {
    this.statoAnomalia = statoAnomalia;
  }
  public String getStatoAnomalia()
  {
    return statoAnomalia;
  }
  public String getAnagraficaTecnico()
  {
    return anagraficaTecnico;
  }
  public void setAnagraficaTecnico(String anagraficaTecnico)
  {
    this.anagraficaTecnico = anagraficaTecnico;
  }
  public String getAnagraficaProprietario()
  {
    return anagraficaProprietario;
  }
  public void setAnagraficaProprietario(String anagraficaProprietario)
  {
    this.anagraficaProprietario = anagraficaProprietario;
  }
  public String getAnagraficaRichiedente()
  {
    return anagraficaRichiedente;
  }
  public void setAnagraficaRichiedente(String anagraficaRichiedente)
  {
    this.anagraficaRichiedente = anagraficaRichiedente;
  }
  public String getLaboratorioConsegna()
  {
    return laboratorioConsegna;
  }
  public void setLaboratorioConsegna(String laboratorioConsegna)
  {
    this.laboratorioConsegna = laboratorioConsegna;
  }
  public String getLaboratorioAnalisi()
  {
    return laboratorioAnalisi;
  }
  public void setLaboratorioAnalisi(String laboratorioAnalisi)
  {
    this.laboratorioAnalisi = laboratorioAnalisi;
  }
  public String getCodiceMateriale()
  {
    return codiceMateriale;
  }
  public void setCodiceMateriale(String codiceMateriale)
  {
    this.codiceMateriale = codiceMateriale;
  }
  public String getAnnoCampione()
  {
    return annoCampione;
  }
  public void setAnnoCampione(String annoCampione)
  {
    this.annoCampione = annoCampione;
  }
  public String getNumeroCampione()
  {
    return numeroCampione;
  }
  public void setNumeroCampione(String numeroCampione)
  {
    this.numeroCampione = numeroCampione;
  }
  public String getNote()
  {
    return note;
  }
  public void setNote(String note)
  {
    this.note = note;
  }
  public void setCodLabAnalisi(String codLabAnalisi)
  {
    this.codLabAnalisi = codLabAnalisi;
  }
  public String getCodLabAnalisi()
  {
    return codLabAnalisi;
  }
  public void setStatoAttuale(String statoAttuale)
  {
    this.statoAttuale = statoAttuale;
  }
  public String getStatoAttuale()
  {
    return statoAttuale;
  }
  public String getAnagraficaUtenteSingolo()
  {
    return anagraficaUtenteSingolo;
  }
  public void setAnagraficaUtenteSingolo(String anagraficaUtenteSingolo)
  {
    this.anagraficaUtenteSingolo = anagraficaUtenteSingolo;
  }
  public void setPagamento(String pagamento) {
    this.pagamento = pagamento;
  }
  public String getPagamento() {
    return pagamento;
  }
  public void setCosto(String costo)
  {
    this.costo = costo;
  }
  public String getCosto()
  {
    return costo;
  }
  public void setFatturareA(String fatturareA)
  {
    this.fatturareA = fatturareA;
  }
  public String getFatturareA()
  {
    return fatturareA;
  }
  public String getConteggioFatture()
	{
		return conteggioFatture;
	}
	public void setConteggioFatture(String conteggioFatture)
	{
		this.conteggioFatture = conteggioFatture;
	}
	public String getNumeroFattura()
	{
		return numeroFattura;
	}
	public void setNumeroFattura(String numeroFattura)
	{
		this.numeroFattura = numeroFattura;
	}
	public String getAnnoFattura()
	{
		return annoFattura;
	}
	public void setAnnoFattura(String annoFattura)
	{
		this.annoFattura = annoFattura;
	}  
}