package it.csi.agrc;

import java.sql.*;
import it.csi.cuneo.*;

/**
 * <p>Title: Agrichim - Front Office</p>
 * <p>Description: Richiesta analisi chimiche su campioni biologici agrari</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: CSI Piemonte - Progettazione e Sviluppo - Cuneo</p>
 * @author Michele Piantà, Piergiorgio Chiriotti
 * @version 1.0.0
 */

public class Anagrafica extends BeanDataSource
{
  public String idAnagrafica;
  public String codiceIdentificativo;
  public String tipoPersona;
  public String cognomeRagioneSociale;
  public String nome;
  public String indirizzo;
  public String cap;
  public String comuneResidenza;
  public String telefono;
  public String cellulare;
  public String fax;
  public String email;
  public String idOrganizzazione;
  public String tipoUtente;
  public String idAnagrafica2;

  public static int ANAGRAFICA_AZIENDA = 0;
  public static int ANAGRAFICA_PRIVATO = 1;
  public static int ANAGRAFICA_TECNICO = 2;
  public static int ANAGRAFICA_TECNICO_INSERIMENTO = 3;
  private String idAnagraficaAzienda;

  public Anagrafica ()
  {
  }
  public Anagrafica ( String idAnagrafica, String codiceIdentificativo,
                      String tipoPersona, String cognomeRagioneSociale,
                      String nome, String indirizzo, String cap,
                      String comuneResidenza, String telefono, String cellulare,
                      String fax, String email, String idOrganizzazione,
                      String tipoUtente, String idAnagraficaAzienda,
                      String idAnagrafica2 )
  {
    this.idAnagrafica=idAnagrafica;
    this.codiceIdentificativo=codiceIdentificativo;
    this.tipoPersona=tipoPersona;
    this.cognomeRagioneSociale=cognomeRagioneSociale;
    this.nome=nome;
    this.indirizzo=indirizzo;
    this.cap=cap;
    this.comuneResidenza=comuneResidenza;
    this.telefono=telefono;
    this.cellulare=cellulare;
    this.fax=fax;
    this.email=email;
    this.idOrganizzazione=idOrganizzazione;
    this.tipoUtente=tipoUtente;
    this.idAnagraficaAzienda=idAnagraficaAzienda;
    this.idAnagrafica2=idAnagrafica2;
  }

  /**
   * Questo metodo cerca all'interno della tabella Anagrafica un record
   * corrispondente al codiceIdentificativo memorizzato:
   * se lo trova procede all'update, se non lo trova procede con l'inserimento
   **/
  public void insertUpdate()
    throws Exception, SQLException
  {
    this.insertUpdate(false,null);
  }

  /**
   * Questo metodo cerca all'interno della tabella Anagrafica un record
   * corrispondente al codiceIdentificativo memorizzato:
   * se lo trova procede all'update, se non lo trova procede con l'inserimento
   **/
  public void insertUpdate(boolean utenteLAR,String anagraficaUtenteLAR)
    throws Exception, SQLException
  {
    if (!isConnection())
      throw new Exception("Riferimento a DataSource non inizializzato");
    this.setIdAnagrafica(controllaAnagrafica());
    
    Connection conn=this.getConnection();
    conn.setAutoCommit(false);
    conn.setTransactionIsolation( Connection.TRANSACTION_SERIALIZABLE );
    StringBuffer query=new StringBuffer("");
    Statement stmt = null;
    try
    {
      /* (16/01/2012)
       * Questa query recupera l'id dell'anagrafica del proprietario del campione
       * Dal momento però che questo id va sostituito con quello inserito con la ricerca
       * sembra un passaggio piuttosto inutile...
       * 
       * ...ANZI... impostare idAnagrafica con ANAGRAFICA_PROPRIETARIO crea seri problemi
       * Vedere la Jira AGCHIMFO-3, tutte le duplicazioni di anagrafiche e la conseguente Jira AGCHIMBO-3
      stmt = conn.createStatement();
      query.append("SELECT ANAGRAFICA_PROPRIETARIO FROM ETICHETTA_CAMPIONE");
      query.append(" WHERE ID_RICHIESTA =  ").append(getAut().getIdRichiestaCorrente());
      CuneoLogger.debug(this,"\n\n\n"+query.toString());
      ResultSet rset = stmt.executeQuery(query.toString());
      if (rset.next())
         idAnagrafica=rset.getString("ANAGRAFICA_PROPRIETARIO");
      rset.close();
      stmt.close();
       */
      
      /* (16/01/2012)
       * Se idAnagrafica è valorizzato, significa che l'anagrafica richiesta esiste nel db
       * e dunque va aggiornata
       * Altrimenti va creato un nuovo record
       */
      if (this.getIdAnagrafica()!=null)
        this.update(conn);
      else
        this.insertNonSERIALIZABLE(conn,false);
      
      /* (16/01/2012)
       * Dopo aver aggiornato i dati dell'anagrafica o inserito un record di anagrafica nuovo
       * Aggiorniamo il record del campione con la nuova anagrafica di proprietario
       */
      EtichettaCampione ec=new  EtichettaCampione();
      ec.updateAnagraficaProprietario(conn,this.getIdAnagrafica(),
                                        this.getAut().getIdRichiestaCorrente());
      if (utenteLAR)
      {
        ec.updateAnagraficaTecnico(conn,anagraficaUtenteLAR,
                                        this.getAut().getIdRichiestaCorrente());
      }
      //cleanAnagrafiche(conn,idAnagrafica);
      conn.commit();
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("insertUpdate della classe Anagrafica");
      this.getAut().setContenutoQuery(query.toString());
      try
      {
        conn.rollback();
      }
      catch( java.sql.SQLException ex2 )
      {
        this.getAut().setQuery("insertUpdate della classe Anagrafica"
                               +":problemi con il rollback");
        this.getAut().setContenutoQuery(query.toString());
        throw (ex2);
      }
      throw (ex);
    }
    catch(Exception e)
    {
      if (this.getAut()==null)
        e.printStackTrace();
      else
      {
        this.getAut().setQuery("insertUpdate della classe Anagrafica"
                               +": non è una SQLException ma una Exception"
                               +" generica");
        this.getAut().setContenutoQuery(query.toString());
      }
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
   * Questo metodo viene utilizzato per inserire un anagrafica corrispondente
   * ad un nuovo tecnico
   **/
  public void insertTecnico()
    throws Exception, SQLException
  {
    if (!isConnection())
      throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn=this.getConnection();
    try
    {
      insert(conn,true);
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("insertTecnico della classe Anagrafica");
      this.getAut().setContenutoQuery("insertTecnico ");
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("insertUpdate della classe Anagrafica"
                             +": non è una SQLException ma una Exception"
                             +" generica");
      this.getAut().setContenutoQuery("insertTecnico ");
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

  public void insert() throws Exception, SQLException {
    if (!isConnection())
      throw new Exception("Riferimento a DataSource non inizializzato");
    this.setIdAnagrafica(controllaAnagrafica());
    Connection conn=this.getConnection();
    insert(conn,false);
  }

  public void insert(Connection conn) throws Exception, SQLException {
    insert(conn,false);
  }

  public void insert(Connection conn,boolean tecnico) throws Exception, SQLException {
    StringBuffer query=null;
    try {
      /* Si imposta a false l'autocommit perché le operazioni che vengono eseguite sul DB
       devono essere effettuate in blocco
       Si noti l'invocazione del metodo conn.commit() PRIMA di conn.close() e
       l'invocazione di conn.rollback() nel blocco catch{}
       */
      conn.setAutoCommit( false );
      // Per evitare ogni rischio di interferenza nel recuperare l'ultimo valore creato
      // nella sequence, imposto il massimo livello di isolamento di questa transazione
      conn.setTransactionIsolation( Connection.TRANSACTION_SERIALIZABLE );
      // recupero il valore da utilizzare per ID_ANAGRAFICA
//      Statement stmt=conn.createStatement();
      query= new StringBuffer( "SELECT nextval('ID_ANAGRAFICA') as NEXTVAL" );
      PreparedStatement stmt= conn.prepareStatement(query.toString());
      ResultSet rset = stmt.executeQuery();
      if ( rset.next() )
          this.setIdAnagrafica(rset.getString( "NEXTVAL" ));
      rset.close();
      stmt.close();

      query.setLength( 0 );
//      stmt = conn.createStatement();
      query = new StringBuffer( "INSERT INTO ANAGRAFICA " );
      query.append("(ID_ANAGRAFICA,CODICE_IDENTIFICATIVO, TIPO_PERSONA,");
      query.append("COGNOME_RAGIONE_SOCIALE, NOME, INDIRIZZO,");
      query.append("CAP, COMUNE_RESIDENZA, TELEFONO, CELLULARE, FAX, EMAIL, ");
      if (tecnico)
          query.append("ID_ORGANIZZAZIONE, ");
      query.append("TIPO_UTENTE, ID_ANAGRAFICA_2) ");
      query.append("VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?").append(tecnico?",?)":")");

      stmt = conn.prepareStatement(query.toString());
      int i = 1;
      stmt.setBigDecimal(i++, this.getIdAnagrafica()!=null?Utili.parseBigDecimal(this.getIdAnagrafica()):null);
      stmt.setString(i++, getCodiceIdentificativo()!=null?getCodiceIdentificativo():"");
//      stmt.setString(i++, tecnico?"F":getTipoPersona()!=null?getTipoPersona():"");
      stmt.setString(i++, "F");
      stmt.setString(i++, getCognomeRagioneSociale()!=null?getCognomeRagioneSociale():"");
      stmt.setString(i++, getNome()!=null?getNome():"");
      stmt.setString(i++, getIndirizzo()!=null?getIndirizzo():"");
      stmt.setString(i++, getCap()!=null?getCap():"");
      stmt.setString(i++, getComuneResidenza()!=null?getComuneResidenza():null);
      stmt.setString(i++, getTelefono()!=null?getTelefono():"");
      stmt.setString(i++, getCellulare()!=null?getCellulare():"");
      stmt.setString(i++, getFax()!=null?getFax():"");
      stmt.setString(i++, getEmail()!=null?getEmail():"");
      if (tecnico) {
    	  stmt.setBigDecimal(i++, getIdOrganizzazione()!=null?Utili.parseBigDecimal(getIdOrganizzazione()):null);
    	  stmt.setString(i++, "T");
    	  stmt.setBigDecimal(i++, this.getIdAnagrafica2()!=null?Utili.parseBigDecimal(this.getIdAnagrafica2()):null);
      } else {
    	  stmt.setString(i++, getTipoPersona()==null || "G".equals(getTipoPersona())?"":"P");
    	  stmt.setBigDecimal(i++, this.getIdAnagrafica2()!=null?Utili.parseBigDecimal(this.getIdAnagrafica2()):null);
      }
      CuneoLogger.debug(this,"query "+query.toString());
      CuneoLogger.debug(this,"getIdAnagrafica "+this.getIdAnagrafica()!=null?this.getIdAnagrafica():null);
      CuneoLogger.debug(this,"getCodiceIdentificativo "+getCodiceIdentificativo()!=null?getCodiceIdentificativo():"");
      CuneoLogger.debug(this,"getTipoPersona "+"F");
      CuneoLogger.debug(this,"getCognomeRagioneSociale "+getCognomeRagioneSociale()!=null?getCognomeRagioneSociale():"");
      CuneoLogger.debug(this,"getNome "+getNome()!=null?getNome():"");
      CuneoLogger.debug(this,"getIndirizzo "+getIndirizzo()!=null?getIndirizzo():"");
      CuneoLogger.debug(this,"getCap "+getCap()!=null?getCap():"");
      CuneoLogger.debug(this,"getComuneResidenza "+getComuneResidenza()!=null?getComuneResidenza():null);
      CuneoLogger.debug(this,"getTelefono "+getTelefono()!=null?getTelefono():"");
      CuneoLogger.debug(this,"getCellulare "+getCellulare()!=null?getCellulare():"");
      CuneoLogger.debug(this,"getFax "+getFax()!=null?getFax():"");
      CuneoLogger.debug(this,"getEmail "+getEmail()!=null?getEmail():"");
      if (tecnico) {
    	  CuneoLogger.debug(this,"getIdOrganizzazione "+getIdOrganizzazione()!=null?getIdOrganizzazione():null);
    	  CuneoLogger.debug(this,"TIPO_UTENTE "+"T");
    	  CuneoLogger.debug(this,"getIdAnagrafica2 "+this.getIdAnagrafica2()!=null?this.getIdAnagrafica2():null);
      } else {
    	  CuneoLogger.debug(this,"TIPO_UTENTE "+getTipoPersona()==null || "G".equals(getTipoPersona())?null:"P");
    	  CuneoLogger.debug(this,"getIdAnagrafica2 "+this.getIdAnagrafica2()!=null?this.getIdAnagrafica2():null);
      }
      stmt.executeUpdate();
      stmt.close();
      conn.commit();
    } catch(java.sql.SQLException ex) {
     this.getAut().setQuery("insert della classe Anagrafica");
     this.getAut().setContenutoQuery(query.toString());
     try {
       conn.rollback();
     } catch( java.sql.SQLException ex2 ) {
       this.getAut().setQuery("insert della classe Anagrafica"
                              +":problemi con il rollback");
       this.getAut().setContenutoQuery(query.toString());
       throw (ex2);
     }
     throw (ex);
   }
  }

  /**
   * E' simile al metodo insert(Connection conn,boolean tecnico) solo che non
   * gestisce la serializzazione
   * */
  public void insertNonSERIALIZABLE(Connection conn,boolean tecnico) throws Exception, SQLException {
    // recupero il valore da utilizzare per ID_ANAGRAFICA
//    Statement stmt=conn.createStatement();
    StringBuffer query= new StringBuffer( "SELECT nextval('ID_ANAGRAFICA') as NEXTVAL" );
    PreparedStatement stmt= conn.prepareStatement(query.toString());
    ResultSet rset = stmt.executeQuery();
    if ( rset.next() )
        this.setIdAnagrafica(rset.getString( "NEXTVAL" ));
    rset.close();
    stmt.close();

    query.setLength( 0 );
//    stmt = conn.createStatement();
    query = new StringBuffer( "INSERT INTO ANAGRAFICA " );
    query.append("(ID_ANAGRAFICA,CODICE_IDENTIFICATIVO, TIPO_PERSONA,");
    query.append("COGNOME_RAGIONE_SOCIALE, NOME, INDIRIZZO,");
    query.append("CAP, COMUNE_RESIDENZA, TELEFONO, CELLULARE, FAX, EMAIL, ");
    if (tecnico)
        query.append("ID_ORGANIZZAZIONE, ");
    query.append("TIPO_UTENTE, ID_ANAGRAFICA_2) ");
    query.append("VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?").append(tecnico?",?)":")");

    stmt = conn.prepareStatement(query.toString());
    int i = 1;
    stmt.setBigDecimal(i++, this.getIdAnagrafica()!=null?Utili.parseBigDecimal(this.getIdAnagrafica()):null);
    stmt.setString(i++, getCodiceIdentificativo()!=null?getCodiceIdentificativo():"");
    stmt.setString(i++, tecnico?"F":getTipoPersona()!=null?getTipoPersona():"");
//    stmt.setString(i++, "F");
    stmt.setString(i++, getCognomeRagioneSociale()!=null?getCognomeRagioneSociale():"");
    stmt.setString(i++, getNome()!=null?getNome():"");
    stmt.setString(i++, getIndirizzo()!=null?getIndirizzo():"");
    stmt.setString(i++, getCap()!=null?getCap():"");
    stmt.setString(i++, getComuneResidenza()!=null?getComuneResidenza():"");
    stmt.setString(i++, getTelefono()!=null?getTelefono():"");
    stmt.setString(i++, getCellulare()!=null?getCellulare():"");
    stmt.setString(i++, getFax()!=null?getFax():"");
    stmt.setString(i++, getEmail()!=null?getEmail():"");
    if (tecnico) {
  	  stmt.setBigDecimal(i++, getIdOrganizzazione()!=null?Utili.parseBigDecimal(getIdOrganizzazione()):null);
  	  stmt.setString(i++, "T");
  	  stmt.setBigDecimal(i++, this.getIdAnagrafica2()!=null?Utili.parseBigDecimal(this.getIdAnagrafica2()):null);
    } else {
  	  stmt.setString(i++, getTipoPersona()==null || "G".equals(getTipoPersona())?null:"P");
  	  stmt.setBigDecimal(i++, this.getIdAnagrafica2()!=null?Utili.parseBigDecimal(this.getIdAnagrafica2()):null);
    }
    CuneoLogger.debug(this,"query insertNonSERIALIZABLE "+query.toString());
    CuneoLogger.debug(this,"query "+query.toString());
    CuneoLogger.debug(this,"parametri ");
    CuneoLogger.debug(this,"getIdAnagrafica "+this.getIdAnagrafica()!=null?Utili.parseBigDecimal(this.getIdAnagrafica()):null);
    CuneoLogger.debug(this,"getCodiceIdentificativo "+getCodiceIdentificativo()!=null?getCodiceIdentificativo():"");
    CuneoLogger.debug(this,"getTipoPersona "+"F");
    CuneoLogger.debug(this,"getCognomeRagioneSociale "+getCognomeRagioneSociale()!=null?getCognomeRagioneSociale():"");
    CuneoLogger.debug(this,"getNome "+getNome()!=null?getNome():"");
    CuneoLogger.debug(this,"getIndirizzo "+getIndirizzo()!=null?getIndirizzo():"");
    CuneoLogger.debug(this,"getCap "+getCap()!=null?getCap():"");
    CuneoLogger.debug(this,"getComuneResidenza "+getComuneResidenza()!=null?getComuneResidenza():null);
    CuneoLogger.debug(this,"getTelefono "+getTelefono()!=null?getTelefono():"");
    CuneoLogger.debug(this,"getCellulare "+getCellulare()!=null?getCellulare():"");
    CuneoLogger.debug(this,"getFax "+getFax()!=null?getFax():"");
    CuneoLogger.debug(this,"getEmail "+getEmail()!=null?getEmail():"");
    if (tecnico) {
  	  CuneoLogger.debug(this,"getIdOrganizzazione "+getIdOrganizzazione()!=null?Utili.parseBigDecimal(getIdOrganizzazione()):null);
  	  CuneoLogger.debug(this,"TIPO_UTENTE "+"T");
  	  CuneoLogger.debug(this,"getIdAnagrafica2 "+this.getIdAnagrafica2()!=null?Utili.parseBigDecimal(this.getIdAnagrafica2()):null);
    } else {
  	  CuneoLogger.debug(this,"TIPO_UTENTE "+getTipoPersona()==null || "G".equals(getTipoPersona())?"":"P");
  	  CuneoLogger.debug(this,"getIdAnagrafica2 "+this.getIdAnagrafica2()!=null?Utili.parseBigDecimal(this.getIdAnagrafica2()):null);
    }
    stmt.executeUpdate();
    stmt.close();
  }

  public void update(Connection conn) throws Exception, SQLException {
    //Statement stmt = conn.createStatement();
    String query =this.updateString();
    PreparedStatement stmt = conn.prepareStatement(query);
    int i = 1;
    stmt.setString(i++, this.getCodiceIdentificativo()!=null?this.getCodiceIdentificativo():"");
    stmt.setString(i++, this.getCognomeRagioneSociale()!=null?this.getCognomeRagioneSociale():"");
    stmt.setString(i++, this.getNome()!=null?this.getNome():"");
    stmt.setString(i++, this.getIndirizzo()!=null?this.getIndirizzo():"");
    stmt.setString(i++, this.getCap()!=null?this.getCap():"");
    stmt.setString(i++, this.getComuneResidenza()!=null?this.getComuneResidenza():"");
    stmt.setString(i++, this.getTelefono()!=null?this.getTelefono():"");
    stmt.setString(i++, this.getCellulare()!=null?this.getCellulare():"");
    stmt.setString(i++, this.getFax()!=null?this.getFax():"");
    stmt.setString(i++, this.getEmail()!=null?this.getEmail():"");
    if(this.getIdAnagraficaAzienda()!=null)
    	stmt.setBigDecimal(i++, Utili.isNumber(this.getIdAnagraficaAzienda())?Utili.parseBigDecimal(this.getIdAnagraficaAzienda()):null);
    stmt.setBigDecimal(i++, this.getIdAnagrafica()!=null?Utili.isNumber(this.getIdAnagrafica())?Utili.parseBigDecimal(this.getIdAnagrafica()):null:null);
    CuneoLogger.debug(this,"query insertNonSERIALIZABLE "+query.toString());
   CuneoLogger.debug(this,"getCodiceIdentificativo "+this.getCodiceIdentificativo()!=null?this.getCodiceIdentificativo():"");
   CuneoLogger.debug(this,"getCognomeRagioneSociale "+this.getCognomeRagioneSociale()!=null?this.getCognomeRagioneSociale():"");
   CuneoLogger.debug(this,"getNome "+this.getNome()!=null?this.getNome():"");
   CuneoLogger.debug(this,"getIndirizzo "+this.getIndirizzo()!=null?this.getIndirizzo():"");
   CuneoLogger.debug(this,"getCap "+this.getCap()!=null?this.getCap():"");
   CuneoLogger.debug(this,"getComuneResidenza "+this.getComuneResidenza()!=null?this.getComuneResidenza():"");
   CuneoLogger.debug(this,"getTelefono "+this.getTelefono()!=null?this.getTelefono():"");
   CuneoLogger.debug(this,"getCellulare "+this.getCellulare()!=null?this.getCellulare():"");
   CuneoLogger.debug(this,"getFax "+this.getFax()!=null?this.getFax():"");
   CuneoLogger.debug(this,"getEmail "+this.getEmail()!=null?this.getEmail():"");
    if(this.getIdAnagraficaAzienda()!=null)
    	CuneoLogger.debug(this,"getIdAnagraficaAzienda "+(Utili.isNumber(this.getIdAnagraficaAzienda())?Utili.parseBigDecimal(this.getIdAnagraficaAzienda()):null));
   CuneoLogger.debug(this,"getTipoPersona "+this.getTipoPersona()!=null?this.getTipoPersona():"");
   CuneoLogger.debug(this,"getIdAnagrafica "+this.getIdAnagrafica()!=null?Utili.isNumber(this.getIdAnagrafica())?Utili.parseBigDecimal(this.getIdAnagrafica()):null:null);
    stmt.executeUpdate();
    stmt.close();
  }

  public int update() throws Exception, SQLException {
    if (!isConnection())
           throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn=this.getConnection();
    String query="";
//    Statement stmt = null;
    PreparedStatement stmt = null;
    try {
//      stmt = conn.createStatement();
      query =this.updateString();
      stmt = conn.prepareStatement(query);
      int i = 1;
      stmt.setString(i++, this.getCodiceIdentificativo()!=null?this.getCodiceIdentificativo():"");
      stmt.setString(i++, this.getCognomeRagioneSociale()!=null?this.getCognomeRagioneSociale():"");
      stmt.setString(i++, this.getNome()!=null?this.getNome():"");
      stmt.setString(i++, this.getIndirizzo()!=null?this.getIndirizzo():"");
      stmt.setString(i++, this.getCap()!=null?this.getCap():"");
      stmt.setString(i++, this.getComuneResidenza()!=null?this.getComuneResidenza():"");
      stmt.setString(i++, this.getTelefono()!=null?this.getTelefono():"");
      stmt.setString(i++, this.getCellulare()!=null?this.getCellulare():"");
      stmt.setString(i++, this.getFax()!=null?this.getFax():"");
      stmt.setString(i++, this.getEmail()!=null?this.getEmail():"");
      if(this.getIdAnagraficaAzienda()!=null)
    		  stmt.setBigDecimal(i++, Utili.isNumber(this.getIdAnagraficaAzienda())?Utili.parseBigDecimal(this.getIdAnagraficaAzienda()):null);
      stmt.setBigDecimal(i++, this.getIdAnagrafica()!=null?Utili.isNumber(this.getIdAnagrafica())?Utili.parseBigDecimal(this.getIdAnagrafica()):null:null);
      CuneoLogger.debug(this,"update "+query.toString());
      int updated = stmt.executeUpdate();
      stmt.close();
      return updated;
    } catch(java.sql.SQLException ex) {
      this.getAut().setQuery("update della classe Anagrafica");
      this.getAut().setContenutoQuery(query);
      ex.printStackTrace();
      throw (ex);
    } catch(Exception e) {
      this.getAut().setQuery("update della classe Anagrafica"
                            +": non è una SQLException ma una Exception"
                            +" generica");
      e.printStackTrace();
      throw (e);
    } finally {
      if (conn!=null) conn.close();
    }
  }

  /**
   *
   * @return la string che permette di eseguire un ipdate della tabella
   * Anagrafica
   */
  private String updateString() {
	  StringBuffer query = new StringBuffer( "UPDATE ANAGRAFICA SET " );
	    query.append("CODICE_IDENTIFICATIVO= ?, ");
	    query.append("COGNOME_RAGIONE_SOCIALE = ?, ");
	    query.append("NOME= ?, ");
	    query.append("INDIRIZZO= ?, ");
	    query.append("CAP= ?, ");
	    query.append("COMUNE_RESIDENZA= ?, ");
	    query.append("TELEFONO= ?, ");
	    query.append("CELLULARE= ?, ");
	    query.append("FAX= ?, ");
	    query.append("EMAIL= ? ");
	    if(this.idAnagraficaAzienda!=null)
	    	query.append(",ID_ANAGRAFICA_AZIENDA= ? ");
	    query.append("WHERE ID_ANAGRAFICA= ? ");
//    StringBuffer query = new StringBuffer( "UPDATE ANAGRAFICA SET " );
//    if (this.getCodiceIdentificativo()==null)
//      query.append("CODICE_IDENTIFICATIVO= null,");
//    else
//      query.append("CODICE_IDENTIFICATIVO='").append(this.getCodiceIdentificativo()).append("', ");
//    if (this.getCognomeRagioneSociale()==null)
//      query.append("COGNOME_RAGIONE_SOCIALE = null, ");
//    else
//      query.append("COGNOME_RAGIONE_SOCIALE='").append(Utili.toVarchar(getCognomeRagioneSociale())).append("', ");
//    if (this.getNome()==null)
//      query.append("NOME= null, ");
//    else
//      query.append("NOME='").append(Utili.toVarchar(getNome())).append("', ");
//    if (this.getIndirizzo()==null)
//      query.append("INDIRIZZO= null, ");
//    else
//      query.append("INDIRIZZO='").append(Utili.toVarchar(getIndirizzo())).append("', ");
//    if (this.getCap()==null)
//      query.append("CAP= null, ");
//    else
//      query.append("CAP='").append(this.getCap()).append("', ");
//    if (this.getComuneResidenza()==null)
//      query.append("COMUNE_RESIDENZA= null, ");
//    else
//      query.append("COMUNE_RESIDENZA='").append(Utili.toVarchar(getComuneResidenza())).append("', ");
//    if (this.getTelefono()==null)
//      query.append("TELEFONO= null, ");
//    else
//      query.append("TELEFONO='").append(Utili.toVarchar(getTelefono())).append("', ");
//    if (this.getCellulare()==null)
//      query.append("CELLULARE= null, ");
//    else
//      query.append("CELLULARE='").append(Utili.toVarchar(getCellulare())).append("', ");
//    if (this.getFax()==null)
//      query.append("FAX= null, ");
//    else
//      query.append("FAX='").append(Utili.toVarchar(getFax())).append("', ");
//    if (this.getEmail()==null)
//      query.append("EMAIL= null, ");
//    else
//      query.append("EMAIL='").append(Utili.toVarchar(getEmail())).append("', ");
//    if (this.getIdAnagraficaAzienda()==null)
//      query.append("ID_ANAGRAFICA_AZIENDA= null ");
//    else
//      query.append("ID_ANAGRAFICA_AZIENDA=").append(this.getIdAnagraficaAzienda());
    return query.toString();
  }


  /**
   * Questo metodo cancella un'anagrafica
   * che presenta il campo CODICE_IDENTIFICATIVO a null
   * @param conn connessione al database
   * @param idAnagrafica Anagrafica da cancellare
   * @throws Exception
   * @throws SQLException
   */
  public void cleanAnagrafiche(Connection conn,String idAnagrafica)
     throws Exception, SQLException
  {
    if (idAnagrafica==null) return;
    Statement stmt = conn.createStatement();
    StringBuffer query = new StringBuffer("DELETE FROM ANAGRAFICA ");
    query.append("WHERE CODICE_IDENTIFICATIVO IS NULL ");
    query.append("AND ID_ANAGRAFICA =").append(idAnagrafica);
    CuneoLogger.debug(this,query.toString());
    stmt.executeUpdate( query.toString() );
    stmt.close();
  }


  public String controllaAnagrafica() throws Exception, SQLException {
    if (getCodiceIdentificativo()==null ||
              "".equals(getCodiceIdentificativo()))
        return null;
    if (!isConnection())
      throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn=this.getConnection();
    StringBuffer query=new StringBuffer("");
    Statement stmt = null;
    try
    {
      stmt = conn.createStatement();
      query.append("SELECT A.ID_ANAGRAFICA FROM ANAGRAFICA A ");
      query.append("WHERE A.CODICE_IDENTIFICATIVO = '");
      query.append(this.getCodiceIdentificativo());
      query.append("'");
      String queryStr=query.toString();
      ResultSet rset = stmt.executeQuery(queryStr);

      if (rset.next())
        return (rset.getString("ID_ANAGRAFICA"));
      else return null;
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("controllaAnagrafica della classe Anagrafica");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("controllaAnagrafica della classe Anagrafica"
                             +": non è una SQLException ma una Exception"
                             +" generica");
      this.getAut().setContenutoQuery(query.toString());
      throw (e);
    }
    finally
    {
      if (conn!=null)  conn.close();
    }
  }


  /**
   * Questa funzione controlla che i dati che verranno inseriti o modificati nel
   * database siano consistenti
   * anagrafica puo' valere:
   * - ANAGRAFICA_AZIENDA: devo controllare i dati dell'azienda
   * - ANAGRAFICA_PRIVATO: devo controllare i dati della persona
   * - ANAGRAFICA_TECNICO: devo controllare i dati del tecnico
   * - ANAGRAFICA_TECNICO_INSERIMENTO: devo controllare i dati dell'inserimento
   *   di un nuovo tecnico da parte di un utente LAR
   * */
  public String ControllaDati(int anagrafica)
  {
    StringBuffer errore=new StringBuffer("");
    if (anagrafica==Anagrafica.ANAGRAFICA_PRIVATO)
    {
      /**
      *   elimino gli spazi in più
      */
      if (getIndirizzo()!=null) setIndirizzo(getIndirizzo().trim());
      if (getIndirizzo()==null || "".equals(getIndirizzo()) || getIndirizzo().length()>40)
      {
        errore.append(";1");
      }
      if (!Utili.controllaCap(getCap()))
      {
        errore.append(";2");
      }
      if (this.getComuneResidenza()==null
               ||
       "".equals(this.getComuneResidenza()))
      {
        errore.append(";3");
      }
      if (getEmail()!=null && !"".equals(getEmail()) && !Utili.controllaMail(getEmail(),50))
      {
        errore.append(";4");
      }
      if (getTelefono()!=null && getTelefono().length()>20)
      {
        errore.append(";5");
      }
      if (getCellulare()!=null && getCellulare().length()>20)
      {
        errore.append(";6");
      }
      if (getFax()!=null && getFax().length()>20)
      {
        errore.append(";7");
      }
    }
    if (anagrafica==Anagrafica.ANAGRAFICA_AZIENDA &&
        getCodiceIdentificativo()!=null && !"".equals(getCodiceIdentificativo()) )
    {
      /**
      *   elimino gli spazi in più
      */
      if (getIndirizzo()!=null && getIndirizzo().length()>40)
      {
        errore.append(";8");
      }
      if (getCap()!=null && !"".equals(getCap()) && !Utili.controllaCap(getCap()))
      {
        errore.append(";9");
      }
      if (this.getComuneResidenza()==null
               ||
       "".equals(this.getComuneResidenza()))
      {
        errore.append(";10");
      }
      if (getTelefono()!=null && getTelefono().length()>20)
      {
        errore.append(";11");
      }
      if (getFax()!=null && getFax().length()>20)
      {
        errore.append(";12");
      }
      if (getEmail()!=null && !"".equals(getEmail()) && !Utili.controllaMail(getEmail(),50))
      {
        errore.append(";13");
      }
      if (getCodiceIdentificativo().length()!=11 || !Utili.controllaPartitaIVA(getCodiceIdentificativo()))
      {
        errore.append(";14");
      }
      if (getCognomeRagioneSociale()!=null) setCognomeRagioneSociale(getCognomeRagioneSociale().trim());
      if (getCognomeRagioneSociale()==null || "".equals(getCognomeRagioneSociale()) || getCognomeRagioneSociale().length()>60)
      {
        errore.append(";15");
      }
    }
    if (anagrafica==Anagrafica.ANAGRAFICA_TECNICO)
    {
      int lung;
      if (getCodiceIdentificativo()==null) lung = 0;
      else lung= getCodiceIdentificativo().length();
      boolean azienda;
      if ("G".equals(getTipoPersona())) azienda = true;
      else azienda=false;
      if (!((lung==11 && azienda) || (lung==16 && !azienda)))
        errore.append(";1");
      else
      {
         if (lung==11 && !Utili.controllaPartitaIVA(this.getCodiceIdentificativo()))
         {
            errore.append(";1");
         }
         if (lung==16 && !Utili.controllaCodiceFiscale(this.getCodiceIdentificativo()))
         {
            errore.append(";1");
         }
      }
      if (getCognomeRagioneSociale()!=null) setCognomeRagioneSociale(getCognomeRagioneSociale().trim());
      if (getCognomeRagioneSociale() ==null
          || "".equals(getCognomeRagioneSociale())
          || getCognomeRagioneSociale().length()>60)
      {
        errore.append(";2");
      }
      if (getNome()!=null) setNome(getNome().trim());
      if (azienda)
      {
        if (getNome() !=null && getNome().length()>40)
        {
          errore.append(";3");
        }
      }
      else
      {
        if (getNome() ==null || "".equals(getNome() ) || getNome().length()>40)
        {
          errore.append(";3");
        }
      }
      if (getIndirizzo()!=null && getIndirizzo().length()>40)
      {
        errore.append(";4");
      }
      if (getCap()!=null && !Utili.controllaCap(getCap()))
      {
        errore.append(";5");
      }
      if (this.getComuneResidenza()==null
               ||
       "".equals(this.getComuneResidenza()))
      {
        errore.append(";6");
      }
      if (getEmail()!=null && !"".equals(getEmail()) && !Utili.controllaMail(getEmail(),50))
      {
        errore.append(";7");
      }
      if (getTelefono()!=null && getTelefono().length()>20)
      {
        errore.append(";8");
      }
      if (getCellulare()!=null && getCellulare().length()>20)
      {
        errore.append(";9");
      }
      if (getFax()!=null && getFax().length()>20)
      {
        errore.append(";10");
      }
    }
    if (anagrafica==Anagrafica.ANAGRAFICA_TECNICO_INSERIMENTO)
    {
      if (getCognomeRagioneSociale()!=null) setCognomeRagioneSociale(getCognomeRagioneSociale().trim());
      if (getCognomeRagioneSociale() ==null
          || "".equals(getCognomeRagioneSociale())
          || getCognomeRagioneSociale().length()>60)
      {
        errore.append(";1");
      }
      if (getNome()!=null) setNome(getNome().trim());
      if (getNome() ==null || "".equals(getNome()) || getNome().length()>40)
      {
        errore.append(";2");
      }

      int lung;
      if (getCodiceIdentificativo()==null) lung = 0;
      else lung= getCodiceIdentificativo().length();
      if (lung!= 16)
        errore.append(";3");
      else
      {
         if (lung==16 && !Utili.controllaCodiceFiscale(this.getCodiceIdentificativo()))
         {
            errore.append(";3");
         }
      }
      if (getIndirizzo()!=null) setIndirizzo(getIndirizzo().trim());
      if (getIndirizzo() ==null || "".equals(getIndirizzo())
          || getIndirizzo().length()>40)
      {
        errore.append(";4");
      }
      if (getCap()!=null && !"".equals(getCap()) && !Utili.controllaCap(getCap()))
      {
        errore.append(";5");
      }
      if (this.getComuneResidenza()==null
               ||
       "".equals(this.getComuneResidenza()))
      {
        errore.append(";6");
      }
      if (getEmail()!=null && !"".equals(getEmail()) && !Utili.controllaMail(getEmail(),50))
      {
        errore.append(";7");
      }
      if (getTelefono()!=null) setTelefono(getTelefono().trim());
      if (getCellulare()!=null) setCellulare(getCellulare().trim());
      if ((getTelefono()==null || "".equals(getTelefono()))
                          &&
          (getCellulare()==null || "".equals(getCellulare()))
         )
      {
        errore.append(";8");
        errore.append(";9");
      }
      else
      {
        if (getTelefono()!=null && getTelefono().length()>20)
        {
          errore.append(";8");
        }
        if (getCellulare()!=null && getCellulare().length()>20)
        {
          errore.append(";9");
        }
      }
      if (getFax()!=null && getFax().length()>20)
      {
        errore.append(";10");
      }
    }
    /**
    * Se non sono stati trovati errori restituisce null
    */
    if (errore.toString().equals("")) return null;
    else return errore.toString();
  }

  /**
   * Questa funzione controlla che i dati che verranno inseriti o modificati
   * (da un utente LAR) nel database siano consistenti
   * */
  public String ControllaDati(String tipoCliente,
                              String tipoOrganizzazione,
                              String organizzazione,
                              String anagraficaTecnico)
  {
    StringBuffer errore=new StringBuffer("");
    if ("T".equals(tipoCliente))
    {
      if (tipoOrganizzazione==null || "-1".equals(tipoOrganizzazione))
      {
        errore.append(";1;2;3");
      }
      else
      if (organizzazione==null || "-1".equals(organizzazione))
      {
        errore.append(";2;3");
      }
      else
      if (anagraficaTecnico==null || "-1".equals(anagraficaTecnico))
      {
        errore.append(";3");
      }
    }
    boolean azienda;
    if ("G".equals(getTipoPersona())) azienda = true;
    else azienda=false;
    int lung;
    if (getCodiceIdentificativo()==null) lung = 0;
    else lung= getCodiceIdentificativo().length();
  //  if (!((lung==11 && azienda) || (lung==16 && !azienda))) //jira 105
    if(lung==11 || lung==16)
	{
     //   errore.append(";4");
  //  else
   // {
       if (lung==11 && !Utili.controllaPartitaIVA(this.getCodiceIdentificativo()))
       {
          errore.append(";4");
       }
       if (lung==16 && !Utili.controllaCodiceFiscale(this.getCodiceIdentificativo()))
       {
          errore.append(";4");
       }
      }
    else //non dovrebbe arrivare neanche su questo ramo.
	   {
    	 errore.append(";4");
	   }
    if (getCognomeRagioneSociale()!=null) setCognomeRagioneSociale(getCognomeRagioneSociale().trim());
    if (getCognomeRagioneSociale() ==null
        || "".equals(getCognomeRagioneSociale())
        || getCognomeRagioneSociale().length()>60)
    {
      errore.append(";5");
    }
    if (getNome()!=null) setNome(getNome().trim());
    if ((getNome() ==null || "".equals(getNome())) && "F".equals(getTipoPersona())
        || (getNome() !=null && getNome().length()>40))
    {
      errore.append(";6");
    }

    if (getIndirizzo()!=null && !"".equals(getIndirizzo()) && getIndirizzo().length()>40)
      {
        errore.append(";7");
      }
      if (getCap()!=null && !"".equals(getCap()) && !Utili.controllaCap(getCap()))
      {
        errore.append(";8");
      }
    if (this.getComuneResidenza()==null
             ||
     "".equals(this.getComuneResidenza()))
    {
      errore.append(";9");
    }
    if (getEmail()!=null && !"".equals(getEmail()) && !Utili.controllaMail(getEmail(),50))
    {
      errore.append(";10");
    }
    if (getTelefono()!=null && getTelefono().length()>20)
    {
      errore.append(";11");
    }
    if (getCellulare()!=null && getCellulare().length()>20)
    {
      errore.append(";12");
    }
    if (getFax()!=null && getFax().length()>20)
    {
      errore.append(";13");
    }
    /**
    * Se non sono stati trovati errori restituisce null
    */
    if (errore.toString().equals("")) return null;
    else return errore.toString();
  }

  public String getCap()
  {
    return cap;
  }
  public void setCap(String cap)
  {
    this.cap = cap;
  }
  public String getCellulare()
  {
    return cellulare;
  }
  public void setCellulare(String cellulare)
  {
    this.cellulare = cellulare;
  }
  public String getCodiceIdentificativo()
  {
    if (codiceIdentificativo==null) return null;
    return codiceIdentificativo.toUpperCase();
  }
  public void setCodiceIdentificativo(String codiceIdentificativo)
  {
    this.codiceIdentificativo = codiceIdentificativo;
  }
  public String getCognomeRagioneSociale()
  {
    return cognomeRagioneSociale;
  }
  public void setCognomeRagioneSociale(String cognomeRagioneSociale)
  {
    this.cognomeRagioneSociale = cognomeRagioneSociale;
  }
  public String getComuneResidenza()
  {
    return comuneResidenza;
  }
  public void setComuneResidenza(String comuneResidenza)
  {
    this.comuneResidenza = comuneResidenza;
  }
  public String getEmail()
  {
    return email;
  }
  public void setEmail(String email)
  {
    this.email = email;
  }
  public String getFax()
  {
    return fax;
  }
  public void setFax(String fax)
  {
    this.fax = fax;
  }
  public String getIdAnagrafica()
  {
    return idAnagrafica;
  }
  public void setIdAnagrafica(String idAnagrafica)
  {
    this.idAnagrafica = idAnagrafica;
  }
  public String getIdOrganizzazione()
  {
    return idOrganizzazione;
  }
  public void setIdOrganizzazione(String idOrganizzazione)
  {
    this.idOrganizzazione = idOrganizzazione;
  }
  public String getIndirizzo()
  {
    return indirizzo;
  }
  public void setIndirizzo(String indirizzo)
  {
    this.indirizzo = indirizzo;
  }
  public String getNome()
  {
    return nome;
  }
  public void setNome(String nome)
  {
    this.nome = nome;
  }
  public String getTelefono()
  {
    return telefono;
  }
  public void setTelefono(String telefono)
  {
    this.telefono = telefono;
  }
  public String getTipoPersona()
  {
    return tipoPersona;
  }
  public void setTipoPersona(String tipoPersona)
  {
    this.tipoPersona = tipoPersona;
  }
  public String getTipoUtente()
  {
    return tipoUtente;
  }
  public void setTipoUtente(String tipoUtente)
  {
    this.tipoUtente = tipoUtente;
  }
  public void setIdAnagraficaAzienda(String idAnagraficaAzienda)
  {
    this.idAnagraficaAzienda = idAnagraficaAzienda;
  }
  public String getIdAnagraficaAzienda()
  {
    return idAnagraficaAzienda;
  }
  public String getIdAnagrafica2()
  {
    return idAnagrafica2;
  }
  public void setIdAnagrafica2(String idAnagrafica2)
  {
    this.idAnagrafica2 = idAnagrafica2;
  }
}