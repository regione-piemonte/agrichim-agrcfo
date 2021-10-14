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

public class Analisi extends BeanDataSource implements Modello
{
  //Prezzo di mercato
  public static final String PREZZO_INTERO = "PI";
  //Tariffa ridotta da applicare all'analisi per privato e T non convenzionato che richiedono l'analisi tramite cartaceo al LAR
  public static final String RIDUZIONE_1 = "R1";
  //Tariffa ridotta da applicare all'analisi per privato e T non convenzionato che richiedono l'analisi tramite internet oppure per T convenzionato che richiede l'analisi tramite cartaceo al LAR
  public static final String RIDUZIONE_2 = "R2";
  //Tariffa ridotta da applicare all'analisi per T convenzionato che richiede analisi tramite internet
  public static final String RIDUZIONE_3 = "R3";
  //Tariffa ridotta per MACINATI da applicare all'analisi per privato e T non convenzionato che richiedono l'analisi tramite cartaceo al LAR
  public static final String RIDUZIONE_1_MACINATI = "R1M";
  //Tariffa ridotta per MACINATI da applicare all'analisi per privato e T non convenzionato che richiedono l'analisi tramite internet oppure per T convenzionato che richiede l'analisi tramite cartaceo al LAR
  public static final String RIDUZIONE_2_MACINATI = "R2M";
  //Tariffa ridotta per MACINATI da applicare all'analisi per T convenzionato che richiede analisi tramite internet
  public static final String RIDUZIONE_3_MACINATI = "R3M";


  /**
   * Impostiamo costanti per tutti i codici dei terreni e per
   * tutti i codici delle analisi
   */
  public static final String MAT_TERRENO = "TER";
  public static final String MAT_ERBACEE = "ERB";
  public static final String MAT_FOGLIE = "FOG";
  public static final String MAT_FRUTTA = "FRU";
  public static final String ALTRE_MATRICI = "ZMA";

  public static final String ANA_A4FRAZIONI = "4Fra";
  public static final String ANA_A5FRAZIONI = "5Fra";
  public static final String ANA_AZOTO = "N";
  public static final String ANA_BORO = "B";
  public static final String ANA_CALCAREATTIVO = "CaAtt";
  public static final String ANA_CALCARETOTALE = "CaCO3";
  public static final String ANA_CALCIO = "Ca";
  public static final String ANA_CAPACITASCAMBIOCATIONICO = "CSC";
  public static final String ANA_FERRO = "Fe";
  public static final String ANA_FOSFORO = "P";
  public static final String ANA_MANGANESE = "Mn";
  public static final String ANA_MAGNESIO = "Mg";
  public static final String ANA_PH = "pH";
  public static final String ANA_POTASSIO = "K";
  public static final String ANA_RAME = "Cu";
  public static final String ANA_SALINITA = "Sal";
  public static final String ANA_SOSTANZAORGANICA = "SO";
  public static final String ANA_STANDARD = "Std";
  public static final String ANA_UMIDITA = "Um";
  public static final String ANA_ZINCO = "Zn";
  public static final String ANA_FERRO_TOTALE = "FeTot";
  public static final String ANA_MANGANESE_TOTALE = "MnTot";  
  public static final String ANA_ZINCO_TOTALE = "ZnTot";
  public static final String ANA_RAME_TOTALE = "CuTot";
 // public static final String ANA_BORO_TOTALE = "BTot";
  public static final String ANA_CADMIO_TOTALE = "CdTot";
  public static final String ANA_CROMO_TOTALE = "CrTot";
  public static final String ANA_NICHEL_TOTALE = "NiTot";
  public static final String ANA_PIOMBO_TOTALE = "PbTot";
 // public static final String ANA_STRONZIO_TOTALE = "SrTot";
  //public static final String ANA_ALTRO_METALLO_TOTALE = "MetTot";

  /**
   * I 3 sucessivi vengono utilizzati per individuare dei pacchetti di sconto
   * */
  public static final String ANA_PACCHETTO_TIPO = "TIPO";
  public static final String ANA_PACCHETTO_COMP_SCAMBIO = "CO";
  public static final String ANA_PACCHETTO_MICROELEMENTI = "MICRO";
  public static final String ANA_PACCHETTO_METALLI_PESANTI = "METAL";

  private long idRichiesta;
  private Vector<String> codiciAnalisi;
  private String note;
  
  //Preventivi
  private String codice_fiscale;
  private String n_preventivo;
  private String note_laboratorio;
  private String costo_totale;


  public Analisi()
  {
  }
  public Analisi(Object dataSource, Autenticazione aut)
  {
    this.setDataSource(dataSource);
    this.setAut(aut);
  }

  /**
   * Se l'utente ha selezionato nuovo impianto, classe coltura uguale a
   * viticole e specie coltura uguale a vite l'analisi del calcare attivo
   * è gratis e quindi devo restituire true, altrimenti false
   * @return true se l'analisi del Calcare attivo è gratis, no altrimenti
   * @throws Exception
   * @throws SQLException
   */
  public boolean isCalcAttGratis()
    throws Exception, SQLException
  {
    if (!isConnection())
      throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn=null;
    StringBuffer query=null;
    boolean ris=false;
    try
    {
      conn=getConnection();
      Statement stmt = conn.createStatement();

      query = new StringBuffer("SELECT D.COLTURA_PREVISTA ");
      query.append("FROM DATI_CAMPIONE_TERRENO D,SPECIE_COLTURA S ");
      query.append("WHERE D.COLTURA_PREVISTA=S.ID_SPECIE ");
      query.append("AND D.ID_RICHIESTA=").append(getIdRichiesta());
      query.append("AND UPPER(D.NUOVO_IMPIANTO)='S' ");
      query.append("AND UPPER(S.DESCRIZIONE) = 'VITE' ");
      ResultSet rset = stmt.executeQuery(query.toString());
      if (rset.next()) ris=true;
      rset.close();
      stmt.close();
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("isCalcAttGratis della classe Analisi");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("isCalcAttGratis della classe Analisi"
                            +": non è una SQLException ma una Exception"
                            +" generica");
      this.getAut().setContenutoQuery(query.toString());
      throw (e);
    }
    finally
    {
      if (conn!=null) conn.close();
    }
    return ris;
  }

  /**
   *  Legge tutti i dati dalla tabella TIPI_DI_ANALISI e li memorizza
   *  in una tabella di hash all'interno di un bean di tipo
   *  BeanAnalisi
   **/
  public void selectTipiAnalisi(Hashtable<String, String> analisi)
    throws Exception, SQLException
  {
    if (!isConnection())
      throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn=null;
    StringBuffer query=null;
    try
    {
      conn=getConnection();
      Statement stmt = conn.createStatement();
      query=new StringBuffer("SELECT CODICE_ANALISI,DESCRIZIONE ");
      query.append("FROM TIPI_DI_ANALISI ");
      query.append("ORDER BY CODICE_ANALISI");
      CuneoLogger.debug(this,"query.toString() "+query.toString());
      ResultSet rset = stmt.executeQuery(query.toString());
      String cod,desc;
      while (rset.next())
      {
        cod=rset.getString("CODICE_ANALISI");
        if (cod!=null)
        {
          desc=rset.getString("DESCRIZIONE");
          if (desc==null) desc="";
          analisi.put(cod, desc);
        }
      }
      rset.close();
      stmt.close();
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("selectTipiAnalisi della classe Analisi");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("selectTipiAnalisi della classe Analisi"
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
  
  
  public void selectPreventivo(String cf, String preventivo) throws Exception, SQLException {
	  if (!isConnection())
		  throw new Exception("Riferimento a DataSource non inizializzato");
	  Connection conn=null;
	  StringBuffer query=null;
	  try {
		  conn=getConnection();
		  Statement stmt = conn.createStatement();
		  query=new StringBuffer("SELECT p.codice_fiscale, p.numero_preventivo, p.importo, p.note_aggiuntive ");
		  query.append("FROM preventivi p ");
		  query.append("left join etichetta_campione ec on p.id_preventivi=ec.id_preventivi ");
		  query.append("WHERE p.numero_preventivo='").append(preventivo).append("' ");
		  query.append("AND p.codice_fiscale=UPPER('").append(cf).append("')");
		  CuneoLogger.debug(this,"query.toString() "+query.toString());
		  ResultSet rset = stmt.executeQuery(query.toString());
		  String importo = null,note_aggiuntive = null, note_cliente=null;
		  while (rset.next()) {
			  importo=rset.getString("IMPORTO");
			  note_aggiuntive=rset.getString("NOTE_AGGIUNTIVE");
		  }
		  this.costo_totale=importo;
		  this.note_laboratorio=note_aggiuntive;
		  this.note=note_cliente;
		  rset.close();
		  stmt.close();
	  } catch(java.sql.SQLException ex) {
		  this.getAut().setQuery("selectPreventivo della classe Analisi");
		  this.getAut().setContenutoQuery(query.toString());
		  throw (ex);
	  } catch(Exception e) {
		  this.getAut().setQuery("selectPreventivo della classe Analisi"
				  +": non è una SQLException ma una Exception"
				  +" generica");
		  this.getAut().setContenutoQuery(query.toString());
		  throw (e);
	  } finally {
		  if (conn!=null) conn.close();
	  }
  }
  
  public void selectPreventivo(String idRichiesta) throws Exception, SQLException {
	  if (!isConnection())
		  throw new Exception("Riferimento a DataSource non inizializzato");
	  Connection conn=null;
	  StringBuffer query=null;
	  try {
		  conn=getConnection();
		  Statement stmt = conn.createStatement();
		  query=new StringBuffer("SELECT p.numero_preventivo, p.codice_fiscale, p.importo, p.note_aggiuntive ");
		  query.append("FROM preventivi p ");
		  query.append("left join etichetta_campione ec on p.id_preventivi=ec.id_preventivi ");
		  query.append("WHERE ec.id_richiesta='").append(idRichiesta).append("' ");
		  CuneoLogger.debug(this,"query.toString() "+query.toString());
		  ResultSet rset = stmt.executeQuery(query.toString());
		  String importo = null,note_aggiuntive = null, note_cliente=null, cf=null, np=null;
		  while (rset.next()) {
			  np=rset.getString("NUMERO_PREVENTIVO");
			  cf=rset.getString("CODICE_FISCALE");
			  importo=rset.getString("IMPORTO");
			  note_aggiuntive=rset.getString("NOTE_AGGIUNTIVE");
		  }
		  query=new StringBuffer("SELECT ad.note ");
		  query.append("FROM analisi_dati ad ");
		  query.append("left join etichetta_campione ec on ec.id_richiesta=ad.id_richiesta  ");
		  query.append("WHERE ec.id_richiesta='").append(idRichiesta).append("' ");
		  CuneoLogger.debug(this,"query.toString() "+query.toString());
		  rset = stmt.executeQuery(query.toString());
		  while (rset.next()) {
			  note_cliente=rset.getString("NOTE");
		  }
		  this.n_preventivo = np;
		  this.codice_fiscale=cf;
		  this.costo_totale=importo;
		  this.note_laboratorio=note_aggiuntive;
		  this.note=note_cliente;
		  rset.close();
		  stmt.close();
	  } catch(java.sql.SQLException ex) {
		  this.getAut().setQuery("selectPreventivo della classe Analisi");
		  this.getAut().setContenutoQuery(query.toString());
		  throw (ex);
	  } catch(Exception e) {
		  this.getAut().setQuery("selectPreventivo della classe Analisi"
				  +": non è una SQLException ma una Exception"
				  +" generica");
		  this.getAut().setContenutoQuery(query.toString());
		  throw (e);
	  } finally {
		  if (conn!=null) conn.close();
	  }
  }
  
  public void updatePreventivo(String note, String nPreventivo, String costo, String cf) throws Exception, SQLException {
	  if (!isConnection())
		  throw new Exception("Riferimento a DataSource non inizializzato");
	  Connection conn=null;
	  StringBuffer query=null;
	  int inserted = 0;
	  try {
		  conn=getConnection();
		  Statement stmt = conn.createStatement();
		  //update analisi dati
		  query=new StringBuffer("UPDATE analisi_dati ");
		  query.append("SET note = '").append(Utili.toVarchar(note)).append("' ");
		  query.append("WHERE id_richiesta='").append(this.idRichiesta).append("' ");
		  CuneoLogger.debug(this,"query.toString() "+query.toString());
		  inserted = stmt.executeUpdate(query.toString());
		  if(inserted==0) {
		  	query.setLength(0);
			query.append("INSERT INTO ANALISI_DATI");
			query.append("(ID_RICHIESTA, NOTE) ");
			query.append("VALUES(");
			query.append(this.getIdRichiesta()).append(",'");
			query.append(Utili.toVarchar(note));
			query.append("')");
			CuneoLogger.debug(this,query.toString());
			inserted = stmt.executeUpdate(query.toString());
		  }
		  //update analisi_richieste
		  query=new StringBuffer("UPDATE analisi_richieste ");
		  query.append("SET costo_analisi = ").append(costo).append(" ");
		  query.append("WHERE id_richiesta='").append(this.idRichiesta).append("' ");
		  CuneoLogger.debug(this,"query.toString() "+query.toString());
		  inserted = stmt.executeUpdate(query.toString());
		  if(inserted==0) {
		  	query.setLength(0);
			query.append("INSERT INTO ANALISI_RICHIESTE");
			query.append("(ID_RICHIESTA, COSTO_ANALISI, CODICE_ANALISI) ");
			query.append("VALUES(");
			query.append(this.getIdRichiesta()).append(",");
			query.append(costo).append(",'AltreM')");
			CuneoLogger.debug(this,query.toString());
			inserted = stmt.executeUpdate(query.toString());
		  }
		  //update etichetta campione
		  query=new StringBuffer("UPDATE etichetta_campione ");
		  query.append("SET id_preventivi = (select id_preventivi from preventivi where numero_preventivo='").append(nPreventivo).append("' ");
		  query.append("and codice_fiscale='").append(cf).append("') ");
		  query.append("WHERE id_richiesta='").append(this.idRichiesta).append("' ");
		  CuneoLogger.debug(this,"query.toString() "+query.toString());
		  stmt.executeUpdate(query.toString());
		  stmt.close();
	  } catch(java.sql.SQLException ex) {
		  this.getAut().setQuery("updateNotePreventivo della classe Analisi");
		  this.getAut().setContenutoQuery(query.toString());
		  throw (ex);
	  } catch(Exception e) {
		  this.getAut().setQuery("updateNotePreventivo della classe Analisi"
				  +": non è una SQLException ma una Exception"
				  +" generica");
		  this.getAut().setContenutoQuery(query.toString());
		  throw (e);
	  } finally {
		  if (conn!=null) conn.close();
	  }
  }


  /**
   *   Legge tutti i dati dalla tabella tariffe e li memorizza
   *  in una tabella di hash all'interno di un bean di tipo
   *  BeanAnalisi
   **/
  public void selectCostoAnalisi(Hashtable<String, String> costoAnalisi)
    throws Exception, SQLException
  {
    if (!isConnection())
      throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn=null;
    StringBuffer query=null;
    String codiceMat=null,codAnalisi=null,prezzo=null;
    try
    {
      conn=getConnection();
      Statement stmt = conn.createStatement();
      query=new StringBuffer("SELECT T.CODICE_ANALISI,T.CODICE_MATERIALE,");
      query.append("T.PREZZO_INTERO,T.FASCIA_RIDUZIONE_1,");
      query.append("T.FASCIA_RIDUZIONE_2,T.FASCIA_RIDUZIONE_3, ");
      query.append("T.FASCIA_RIDUZIONE_1_MACINATI,");
      query.append("T.FASCIA_RIDUZIONE_2_MACINATI,");
      query.append("T.FASCIA_RIDUZIONE_3_MACINATI ");
      query.append("FROM TARIFFE T ");
      query.append("ORDER BY T.CODICE_MATERIALE,T.CODICE_ANALISI");
      //CuneoLogger.debug(this,"query.toString() "+query.toString());
      ResultSet rset = stmt.executeQuery(query.toString());
      while (rset.next())
      {
        codAnalisi=rset.getString("CODICE_ANALISI");
        codiceMat=rset.getString("CODICE_MATERIALE");
        if (codAnalisi!=null && codiceMat!=null)
        {
          prezzo=rset.getString("PREZZO_INTERO");
          if (prezzo == null) prezzo="0";
          costoAnalisi.put(codAnalisi+codiceMat+Analisi.PREZZO_INTERO,prezzo);
          prezzo=rset.getString("FASCIA_RIDUZIONE_1");
          if (prezzo == null) prezzo="0";
          costoAnalisi.put(codAnalisi+codiceMat+Analisi.RIDUZIONE_1,prezzo);
          prezzo=rset.getString("FASCIA_RIDUZIONE_2");
          if (prezzo == null) prezzo="0";
          costoAnalisi.put(codAnalisi+codiceMat+Analisi.RIDUZIONE_2,prezzo);
          prezzo=rset.getString("FASCIA_RIDUZIONE_3");
          if (prezzo == null) prezzo="0";
          costoAnalisi.put(codAnalisi+codiceMat+Analisi.RIDUZIONE_3,prezzo);

          /**
           * Per i macinati
           * */
          prezzo=rset.getString("FASCIA_RIDUZIONE_1_MACINATI");
          if (prezzo == null) prezzo="0";
          costoAnalisi.put(codAnalisi+codiceMat+Analisi.RIDUZIONE_1_MACINATI,prezzo);
          prezzo=rset.getString("FASCIA_RIDUZIONE_2_MACINATI");
          if (prezzo == null) prezzo="0";
          costoAnalisi.put(codAnalisi+codiceMat+Analisi.RIDUZIONE_2_MACINATI,prezzo);
          prezzo=rset.getString("FASCIA_RIDUZIONE_3_MACINATI");
          if (prezzo == null) prezzo="0";
          costoAnalisi.put(codAnalisi+codiceMat+Analisi.RIDUZIONE_3_MACINATI,prezzo);
        }
      }
      rset.close();
      stmt.close();
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("selectCostoAnalisi della classe Analisi");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("selectCostoAnalisi della classe Analisi"
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
   * Queto metodo va a leggere nella della CODICE_ANALISI tutte le
   * analisi richeste per un determinato campio e le memorizza
   * in un vector. Inoltre legge dalla tabella ANALISI_DATI le eventuali
   * note
   **/
  public void select()
      throws Exception, SQLException
  {
    select(null);
  }
  public void select(String idRichiestaSearch)
      throws Exception, SQLException
  {
    if (!isConnection())
           throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn = null;
    StringBuffer query = new StringBuffer("");
    Statement stmt = null;
    Vector<String> codAnalisi=new Vector<String>();
    try
    {
      conn = getConnection();
      stmt = conn.createStatement();
      query.append("SELECT CODICE_ANALISI FROM ANALISI_RICHIESTE ");
      query.append("WHERE ID_RICHIESTA =");
      if (idRichiestaSearch!=null)
        query.append(idRichiestaSearch);
      else
        query.append(this.getIdRichiesta());
     // CuneoLogger.debug(this,"\nQuery Analisi.select() - codici\n"+query.toString());
      ResultSet rset = stmt.executeQuery(query.toString());
      while (rset.next())
        codAnalisi.add(rset.getString("CODICE_ANALISI"));
      this.setCodiciAnalisi(codAnalisi);
      rset.close();
      query.setLength(0);
      query.append("SELECT NOTE FROM ANALISI_DATI WHERE ");
      query.append("ID_RICHIESTA =");
      if (idRichiestaSearch!=null)
        query.append(idRichiestaSearch);
      else
        query.append(this.getIdRichiesta());

      //CuneoLogger.debug(this,"\nQuery Analisi.select() - note\n"+query.toString());
      rset = stmt.executeQuery(query.toString());
      if (rset.next())
        this.setNote(rset.getString("NOTE"));
      rset.close();
      stmt.close();
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("select della classe CampioneTerrenoDati");
      this.getAut().setContenutoQuery(query.toString());
      ex.printStackTrace();
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("select della classe CampioneTerrenoDati"
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

  public int insert()
      throws Exception, SQLException
  {
    return insert(false);
  }

  /**
   * Se cancella è uguale a true significa che prima di inserire le analisi
   * richieste devo cancellare quelle vecchie
   * */
  private int insert(boolean cancella)
      throws Exception, SQLException
  {
    if (!isConnection())
           throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn = null;
    StringBuffer query = new StringBuffer();
    Statement stmt = null;
    String dati[]=new String[2];
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
      //conn.setTransactionIsolation( Connection.TRANSACTION_SERIALIZABLE );
      stmt = conn.createStatement();
      if (cancella)
      {
        query.append("DELETE FROM ANALISI_RICHIESTE WHERE ");
        query.append("ID_RICHIESTA =");
        query.append(this.getIdRichiesta());
        //CuneoLogger.debug(this,query.toString());
        stmt.executeUpdate(query.toString());
        query.setLength(0);
        query.append("DELETE FROM ANALISI_DATI WHERE ");
        query.append("ID_RICHIESTA =");
        query.append(this.getIdRichiesta());
        //CuneoLogger.debug(this,query.toString());
        stmt.executeUpdate(query.toString());
      }
      int inserted = 0;

      /**
       * Inserisco le analisi richieste nella tabella ANALISI_RICHIESTE
       * */
      @SuppressWarnings("rawtypes")
	  Enumeration enumAnalisi=getCodiciAnalisi().elements();
      

      while(enumAnalisi.hasMoreElements())
      {
        dati=(String[])enumAnalisi.nextElement();
        query.setLength(0);
        query.append("INSERT INTO ANALISI_RICHIESTE");
        query.append("(ID_RICHIESTA, CODICE_ANALISI, COSTO_ANALISI) ");
        query.append("VALUES(");
        query.append(this.getIdRichiesta()).append(",'");
        query.append(dati[0]);
        query.append("',").append(dati[1]).append(")");
        //CuneoLogger.debug(this,query.toString());
        inserted = stmt.executeUpdate(query.toString());
      }

      /**
       * Inserisco le eventuali note nella tabella ANALISI_DATI
       * */
      if (this.getNote()!=null)
      {
        query.setLength(0);
        query.append("INSERT INTO ANALISI_DATI");
        query.append("(ID_RICHIESTA, NOTE) ");
        query.append("VALUES(");
        query.append(this.getIdRichiesta()).append(",'");
        query.append(Utili.toVarchar(getNote()));
        query.append("')");
        //CuneoLogger.debug(this,query.toString());
        inserted = stmt.executeUpdate(query.toString());
      }

      stmt.close();
      conn.commit();
      return inserted;
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("insert della classe Analisi");
      this.getAut().setContenutoQuery(query.toString());
      try
      {
        conn.rollback();
      }
      catch( java.sql.SQLException ex2 )
      {
        this.getAut().setQuery("insert della classe Analisi"
                               +":problemi con il rollback");
        this.getAut().setContenutoQuery(query.toString());
        throw (ex2);
      }
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("insert della classe Analisi"
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

  public int update()
      throws Exception, SQLException
  {
    try
    {
      return insert(true);
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("update della classe Analisi");
      ex.printStackTrace();
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("update della classe Analisi"
                            +": non è una SQLException ma una Exception"
                            +" generica");
      e.printStackTrace();
      throw (e);
    }
  }

  /**
  *  Legge la "Tariffa Applicata" dalla tabella ETICHETTA_CAMPIONE
  **/
  public byte selectTariffaApplicata()
    throws Exception, SQLException
  {
    if (!isConnection())
      throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn=null;
    StringBuffer query=null;
    //String anagrafica=null;
    byte tariffaApplicata=0;
    byte tariffaMacinato=0;
    try
    {
      conn=getConnection();
      Statement stmt = conn.createStatement();
      query=new StringBuffer("SELECT TARIFFA_APPLICATA ");
      query.append("FROM ETICHETTA_CAMPIONE ");
      query.append("WHERE ID_RICHIESTA =");
      query.append(this.getIdRichiesta());
      CuneoLogger.debug(this,"query.toString() "+query.toString());
      ResultSet rset = stmt.executeQuery(query.toString());
      if (rset.next())
      {
        String temp=rset.getString("TARIFFA_APPLICATA");
        try
        {
          tariffaApplicata=Byte.parseByte(temp);
        }
        catch(Exception e)
        {}
      }
      rset.close();

      if ("ERB".equals(getAut().getCodMateriale())
          || "FOG".equals(getAut().getCodMateriale()))
      {
        query.setLength(0);
        query.append("SELECT MACINATO ");
        if ("ERB".equals(getAut().getCodMateriale()))
          query.append("FROM CAMPIONE_VEGETALI_ERBACEE ");
        else
          query.append("FROM CAMPIONE_VEGETALI_FOGLIEFRUTTA ");
        query.append("WHERE ID_RICHIESTA =");
        query.append(this.getIdRichiesta());
        rset = stmt.executeQuery(query.toString());
        if (rset.next())
        {
          if ("S".equals(rset.getString("MACINATO")))
              tariffaMacinato = 3;
        }
      rset.close();
      }

      CuneoLogger.debug(this,"tariffaApplicata "+tariffaApplicata);
      rset.close();
      stmt.close();
      return (byte)(tariffaApplicata+tariffaMacinato);
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("selectTariffaApplicata della classe Analisi");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("selectTariffaApplicata della classe Analisi"
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
   *  Legge il valore del costo di spedizione
   **/
  public String getCostoSpedizione()
    throws Exception, SQLException
  {
    if (!isConnection())
      throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn=null;
    StringBuffer query=null;
    String temp="0";
    try
    {
      conn=getConnection();
      Statement stmt = conn.createStatement();
      query = new StringBuffer("SELECT COSTO_DI_SPEDIZIONE FROM PARAMETRI_FATTURE ");
      ResultSet rset = stmt.executeQuery(query.toString());
      if (rset.next())
        temp=rset.getString("COSTO_DI_SPEDIZIONE");
      rset.close();
      stmt.close();
      return temp;
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("getCostoSpedizione della classe Analisi");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("getCostoSpedizione della classe Analisi"
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



  public void setIdRichiesta(long idRichiesta)
  {
    this.idRichiesta = idRichiesta;
  }

  public long getIdRichiesta()
  {
    return idRichiesta;
  }
  public void setCodiciAnalisi(Vector<String> codiciAnalisi)
  {
    this.codiciAnalisi = codiciAnalisi;
  }
  public Vector<String> getCodiciAnalisi()
  {
    return codiciAnalisi;
  }
  public void setNote(String note)
  {
    this.note = note;
  }
  public String getNote()
  {
    return note;
  }
public String getCodice_fiscale() {
	return codice_fiscale;
}
public void setCodice_fiscale(String codice_fiscale) {
	this.codice_fiscale = codice_fiscale;
}
public String getN_preventivo() {
	return n_preventivo;
}
public void setN_preventivo(String n_preventivo) {
	this.n_preventivo = n_preventivo;
}
public String getNote_laboratorio() {
	return note_laboratorio;
}
public void setNote_laboratorio(String note_laboratorio) {
	this.note_laboratorio = note_laboratorio;
}
public String getCosto_totale() {
	return costo_totale;
}
public void setCosto_totale(String costo_totale) {
	this.costo_totale = costo_totale;
}

}