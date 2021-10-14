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

public class TipoCampione extends BeanDataSource
{

  private String codMateriale;
  private String descMateriale;
  private long idRichiesta;
  private String istatComune;
  private String etichettaCampione;
  private String codLaboratorio;
  private String descLaboratorio;
  private String codModalitaConsegna;
  private String descModalitaConsegna;
  private String note;
  private String descComune;
	private String codiceMisuraPsr;
  private String noteMisuraPsr;

  private static final String QUERY_MATERIALE=
   "SELECT CODICE_MATERIALE,DESCRIZIONE FROM MATERIALE ORDER BY DESCRIZIONE";
  private static final String QUERY_LABORATORIO=
   "SELECT CODICE_LABORATORIO,DESCRIZIONE FROM LABORATORIO WHERE DATA_FINE_VALIDITA IS NULL ORDER BY DESCRIZIONE";
  private static final String QUERY_MODALITA=
   "SELECT CODICE_MODALITA,DESCRIZIONE FROM MODALITA_DI_CONSEGNA ORDER BY DESCRIZIONE";

  public static final int  MATERIALE=1;
  public static final int  LABORATORIO=2;
  public static final int  MODALITA=3;

  public TipoCampione()
  {
  }

  public void select()
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
      query = new StringBuffer("SELECT EC.CODICE_MATERIALE AS COD_MATERIALE,");
      query.append("M.DESCRIZIONE AS DESC_MATERIALE,");
      query.append("DA.COMUNE_APPEZZAMENTO AS ISTAT_COMUNE, C.DESCRIZIONE ");
      query.append("AS DESC_COMUNE, EC.DESCRIZIONE_ETICHETTA,");
      query.append("L.CODICE_LABORATORIO AS COD_LABORATORIO,");
      query.append("L.DESCRIZIONE AS DESC_LABORATORIO,");
      query.append("MC.CODICE_MODALITA AS COD_MODALITA,");
      query.append("MC.DESCRIZIONE AS DESC_MODALITA,");
      query.append("EC.NOTE_CLIENTE,FR.NUMERO_FASE AS FASE, ");
      query.append("EC.CODICE_MISURA_PSR, EC.NOTE_MISURA_PSR ");
      query.append("FROM ETICHETTA_CAMPIONE EC,MATERIALE M,DATI_APPEZZAMENTO DA,");
      query.append("COMUNE C,LABORATORIO L, MODALITA_DI_CONSEGNA MC, ");
      query.append("FASI_RICHIESTA FR ");
      query.append("WHERE EC.ID_RICHIESTA= ").append(this.getIdRichiesta());
      query.append(" AND EC.CODICE_MATERIALE=M.CODICE_MATERIALE ");
      query.append("AND EC.ID_RICHIESTA=DA.ID_RICHIESTA ");
      query.append("AND DA.COMUNE_APPEZZAMENTO=C.CODICE_ISTAT ");
      query.append("AND EC.LABORATORIO_CONSEGNA=L.CODICE_LABORATORIO ");
      query.append("AND EC.CODICE_MODALITA = MC.CODICE_MODALITA ");
      query.append("AND EC.ID_RICHIESTA = FR.ID_RICHIESTA");
      ResultSet rset = stmt.executeQuery(query.toString());
      if (rset.next())
      {
          this.setCodMateriale(rset.getString("COD_MATERIALE"));
          this.setDescMateriale(rset.getString("DESC_MATERIALE"));
          this.setIstatComune(rset.getString("ISTAT_COMUNE"));
          this.setDescComune(rset.getString("DESC_COMUNE"));
          this.setEtichettaCampione(rset.getString("DESCRIZIONE_ETICHETTA"));
          this.setCodLaboratorio(rset.getString("COD_LABORATORIO"));
          this.setDescLaboratorio(rset.getString("DESC_LABORATORIO"));
          this.setCodModalitaConsegna(rset.getString("COD_MODALITA"));
          this.setDescModalitaConsegna(rset.getString("DESC_MODALITA"));
          this.setNote(rset.getString("NOTE_CLIENTE"));
          this.setCodiceMisuraPsr(rset.getString("CODICE_MISURA_PSR"));
          this.setNoteMisuraPsr(rset.getString("NOTE_MISURA_PSR"));

          /**
           * Dato che non posso impostare qui l'attributo di sessione:
           * session.setAttribute("aut",aut);
           * e non voglio passare l'oggetto session a questo metodo,
           * l'attributo di sessione verrà impostato dalla pagina jsp
           */
          this.getAut().setFase((byte)rset.getInt("FASE"));
      }
      rset.close();
      stmt.close();
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("select della classe TipoCampione");
      this.getAut().setContenutoQuery(query.toString());
      ex.printStackTrace();
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("select della classe TipoCampione"
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

  
  public boolean isPiemonteByIstatComune(String istatComune) throws Exception, SQLException {
	  if (!isConnection())
		  throw new Exception("Riferimento a DataSource non inizializzato");
	  Connection conn=this.getConnection();
	  StringBuffer query=new StringBuffer("");
//	  Statement stmt = null;
	  PreparedStatement stmt = null;
	  String id_regione="";

	  try {
//		  stmt = conn.createStatement();
		  query = new StringBuffer("select id_regione from provincia p, comune c ");
		  query.append("WHERE c.codice_istat = ? ");
		  query.append(" and c.provincia = p.id_provincia ");
		  stmt = conn.prepareStatement(query.toString());
		  stmt.setString(1, istatComune);
		  ResultSet rset = stmt.executeQuery();
		  if (rset.next())
			  id_regione= rset.getString("ID_REGIONE");
		  rset.close();
		  stmt.close();

		  if(id_regione.equals("01"))
			  return true;
		  else
			  return false;
	  } catch(java.sql.SQLException ex) {
		  this.getAut().setQuery("select della classe TipoCampione");
		  this.getAut().setContenutoQuery(query.toString());
		  ex.printStackTrace();
		  throw (ex);
	  } catch(Exception e) {
		  this.getAut().setQuery("select della classe TipoCampione"
				  +": non è una SQLException ma una Exception"
				  +" generica");
		  e.printStackTrace();
		  throw (e);
	  } finally {
		  if (conn!=null) conn.close();
	  }
  }
  	
  
  
  public int insert()
      throws Exception, SQLException
  {
    if (!isConnection())
           throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn=this.getConnection();
    conn.setAutoCommit(false);
    conn.setTransactionIsolation( Connection.TRANSACTION_SERIALIZABLE );
    StringBuffer query=new StringBuffer("");
    Statement stmt = null;

    try
    {
      stmt = conn.createStatement();
      // recupero il valore da utilizzare per ID_RICHIESTA
      query.append( "SELECT nextval('ID_RICHIESTA') as NEXTVAL" );
      ResultSet rset = stmt.executeQuery( query.toString() );
      if ( rset.next() )
        this.setIdRichiesta(rset.getLong( "NEXTVAL" ));
      rset.close();
      stmt.close();

      query.setLength( 0 );
      stmt = conn.createStatement();

      query = new StringBuffer( "INSERT INTO ETICHETTA_CAMPIONE ");
      query.append("(ID_RICHIESTA,CODICE_MATERIALE,DESCRIZIONE_ETICHETTA,");
      query.append("ANAGRAFICA_UTENTE,ANAGRAFICA_PROPRIETARIO,");
      query.append("LABORATORIO_CONSEGNA,");
      query.append("CODICE_MODALITA,STATO_ATTUALE,PAGAMENTO,");
      query.append("NOTE_CLIENTE,DATA_INSERIMENTO_RICHIESTA,");
      query.append("CODICE_MISURA_PSR, NOTE_MISURA_PSR)");
      query.append("VALUES(").append(this.getIdRichiesta());
      query.append(",'").append(this.getCodMateriale());
      if (this.getEtichettaCampione()==null)
      {
        query.append("',null,");
      }
      else
      {
        query.append("','").append(Utili.toVarchar(getEtichettaCampione())).append("',");
      }
      query.append(this.getAut().getUtente().getAnagraficaUtente());
      query.append(",").append(this.getAut().getUtente().getAnagraficaAzienda());
      query.append(",'").append(Utili.toVarchar(getCodLaboratorio()));
      query.append("','").append(Utili.toVarchar(getCodModalitaConsegna()));
      if (this.getNote()==null)
      {
        query.append("','00','N',null");
      }
      else
      {
        query.append("','00','N','").append(Utili.toVarchar(getNote())).append("'");
      }
      query.append(",to_timestamp('").append(Utili.getSystemDate()).append("','DD-MM-YYYY')");
      query.append(",").append(this.getCodiceMisuraPsr());
      if (getNoteMisuraPsr() != null)
      {
      	query.append(",'").append(Utili.toVarchar(getNoteMisuraPsr())).append("'");
      }
      else
      {
      	query.append(",").append("null");
      }
      query.append(")");
      /**
       * Inserisco i dati relativi all'etichetta campione
       **/
      int inserted = stmt.executeUpdate( query.toString() );
      stmt.close();

      /**
       * Memorizzo idRichiesta nella sessione per poterlo utilizzare in tutte
       * le fasi ed imposto la fase a 1
       * Dato che non posso impostare qui l'attributo di sessione:
       * session.setAttribute("aut",aut);
       * e non voglio passare l'oggetto session a questo metodo,
       * l'attributo di sessione verrà impostato dalla pagina jsp
       * */
      this.getAut().setFase((byte)1);
      this.getAut().setIdRichiestaCorrente(this.idRichiesta);
      if (Analisi.MAT_TERRENO.equals(this.getCodMateriale())
               ||
          Analisi.MAT_FOGLIE.equals(this.getCodMateriale())
               ||
          Analisi.MAT_FRUTTA.equals(this.getCodMateriale()))
      {
        this.getAut().setCoordinateGeografiche(true);
      }
      else // if (Analisi.MAT_ERBACEE.equals(this.getCodMateriale())
      {
        this.getAut().setCoordinateGeografiche(false);
      }
      /**
       * Inserisco i dati relativi alla fasse
       **/
      FasiRichiesta fase=new FasiRichiesta();
      fase.insertFase(this.getIdRichiesta(),conn);

      query.setLength( 0 );
      stmt = conn.createStatement();
      query = new StringBuffer( "INSERT INTO DATI_APPEZZAMENTO ");
      query.append("(ID_RICHIESTA,COMUNE_APPEZZAMENTO) ");
      query.append("VALUES(").append(this.getIdRichiesta());
      query.append(",'").append(this.getIstatComune());
      query.append("')");
      /**
       * Inserisco i dati relativi all'appezzamento
       **/
      inserted = stmt.executeUpdate( query.toString() );
      stmt.close();
      conn.commit();
      return inserted;
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("insert della classe TipoCampione");
      this.getAut().setContenutoQuery(query.toString());
      try
      {
        conn.rollback();
      }
      catch( java.sql.SQLException ex2 )
      {
        this.getAut().setQuery("insert della classe TipoCampione"
                               +":problemi con il rollback");
        this.getAut().setContenutoQuery(query.toString());
        throw (ex2);
      }
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("insert della classe TipoCampione"
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




  public int update(boolean isPiemonte)
      throws Exception, SQLException
  {
    if (!isConnection())
           throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn=this.getConnection();
    conn.setAutoCommit(false);
    //conn.setTransactionIsolation( Connection.TRANSACTION_SERIALIZABLE );
    StringBuffer query=new StringBuffer("");
    Statement stmt = null;

    try
    {
      stmt = conn.createStatement();
      query = new StringBuffer( "UPDATE ETICHETTA_CAMPIONE SET " );
      query.append("CODICE_MATERIALE = '").append(this.getCodMateriale());
      if (this.getEtichettaCampione()==null)
      {
        query.append("',DESCRIZIONE_ETICHETTA = null,");
      }
      else
      {
        query.append("',DESCRIZIONE_ETICHETTA = '");
        query.append(Utili.toVarchar(getEtichettaCampione())).append("',");
      }
      query.append("LABORATORIO_CONSEGNA = '").append(Utili.toVarchar(getCodLaboratorio()));
      query.append("',CODICE_MODALITA = '").append(Utili.toVarchar(getCodModalitaConsegna()));
      if (this.getNote()==null)
      {
        query.append("',NOTE_CLIENTE = null");
      }
      else
      {
        query.append("',NOTE_CLIENTE = '").append(Utili.toVarchar(getNote())).append("'");
      }
      query.append(", CODICE_MISURA_PSR = ").append(getCodiceMisuraPsr());
      if (this.getNoteMisuraPsr() == null)
      {
        query.append(", NOTE_MISURA_PSR = null");
      }
      else
      {
        query.append(",NOTE_MISURA_PSR = '").append(Utili.toVarchar(getNoteMisuraPsr())).append("'");
      }
      query.append(" WHERE ID_RICHIESTA = ").append(this.getIdRichiesta());
      /**
      * Modifico i dati della tabella ETICHETTA_CAMPIONE
      *
      */
      int updated = stmt.executeUpdate( query.toString() );
      stmt.close();

      query.setLength( 0 );
      stmt = conn.createStatement();
      if(isPiemonte)
      {
    	  query = new StringBuffer( "UPDATE DATI_APPEZZAMENTO SET ");
    	  query.append("COMUNE_APPEZZAMENTO = '").append(this.getIstatComune());
    	  query.append("' WHERE ID_RICHIESTA = ").append(this.getIdRichiesta());
      }
      else
      {
    	  query = new StringBuffer( "UPDATE DATI_APPEZZAMENTO SET ");
    	  query.append(" SEZIONE= null");
    	  query.append(", localita_appezzamento = null");
          query.append(", FOGLIO= null");
          query.append(", PARTICELLA_CATASTALE= null");
          query.append(", SUBPARTICELLA= null");
          query.append(", COORDINATA_NORD_UTM= null");
          query.append(", COORDINATA_EST_UTM= null");
          query.append(", COORDINATA_NORD_BOAGA=null, COORDINATA_EST_BOAGA=null");
          query.append(", coordinata_nord_gradi = null ");
          query.append(", coordinata_nord_minuti = null ");
          query.append(", coordinata_nord_secondi = null ");
          query.append(", TIPO_GEOREFERENZIAZIONE= null ");
          query.append(", coordinata_est_gradi = null ");
          query.append(", coordinata_est_minuti = null ");
          query.append(", coordinata_est_secondi = null ");
          query.append(", codice_coordinate_gradi= null ");
    	  query.append(", COMUNE_APPEZZAMENTO = '").append(this.getIstatComune());
    	  query.append("' WHERE ID_RICHIESTA = ").append(this.getIdRichiesta());
      }
      /**
       * Modifico i dati relativi all'appezzamento
       **/
      updated = stmt.executeUpdate( query.toString() );
      stmt.close();
      conn.commit();

      /**
       * Memorizzo l'eventuale variazione del tipo di terreno per sapere
       * se richedere o meno le coordinate geografiche
       * Dato che non posso impostare qui l'attributo di sessione:
       * session.setAttribute("aut",aut);
       * e non voglio passare l'oggetto session a questo metodo,
       * l'attributo di sessione verrà impostato dalla pagina jsp
       * */
      if (Analisi.MAT_TERRENO.equals(this.getCodMateriale())
               ||
          Analisi.MAT_FOGLIE.equals(this.getCodMateriale())
               ||
          Analisi.MAT_FRUTTA.equals(this.getCodMateriale()))
      {
        this.getAut().setCoordinateGeografiche(true);
      }
      else // if (Analisi.MAT_ERBACEE.equals(this.getCodMateriale())
      {
        this.getAut().setCoordinateGeografiche(false);
      }
      return updated;
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("update della classe TipoCampione");
      this.getAut().setContenutoQuery(query.toString());
      try
      {
        conn.rollback();
      }
      catch( java.sql.SQLException ex2 )
      {
        this.getAut().setQuery("update della classe TipoCampione"
                               +":problemi con il rollback");
        this.getAut().setContenutoQuery(query.toString());
        throw (ex2);
      }
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("update della classe TipoCampione"
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
   * Se scelta è uguale a:
   * - MATERIALE esegue la query che estrae tutti i materiali
   * - LABORATORIO esegue la query che estrae tutti i laboratori
   * - MODALITA esegue la query che estrae tutte le modalità di consegna
   *
   * Viene utilizzata per restituire i codici e le descrizioni dalle
   * tabelle di transcodifica
   **/
  public void selectCodDesc(int scelta,Vector cod,Vector desc)
    throws Exception, SQLException
  {
    cod.clear();
    desc.clear();
    if (!isConnection())
      throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn=null;
    String query=null;
    try
    {
      conn=getConnection();
      Statement stmt = conn.createStatement();
      switch(scelta)
      {
        case MATERIALE:query=QUERY_MATERIALE;
                       break;
        case LABORATORIO:query=QUERY_LABORATORIO;
                         break;
        case MODALITA:query=QUERY_MODALITA;
                      break;
      }
      ResultSet rset = stmt.executeQuery(query);
      String temp;
      while (rset.next())
      {
        temp=rset.getString(1);
        if (temp==null) temp="";
        cod.add(temp);

        temp=rset.getString(2);
        if (temp==null) temp="";
        desc.add(temp);
      }
      rset.close();
      stmt.close();
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("selectCodDesc della classe TipoCampione");
      this.getAut().setContenutoQuery(query);
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("selectCodDesc della classe TipoCampione"
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

  /**
   * Questa funzione controlla che i dati che verranno inseriti o modificati nel
   * database siano consistenti
   * */
  public String ControllaDati()
  {
    StringBuffer errore=new StringBuffer("");
    if (this.getCodMateriale()==null
                ||
        "-1".equals(this.getCodMateriale())
                ||
        "".equals(this.getCodMateriale())
        )
    {
      errore.append(";1");
    }

    if (this.getDescComune()==null
                ||
        "".equals(this.getDescComune())
                ||
        this.getIstatComune()==null
                        ||
        "".equals(this.getIstatComune())
        )
     {
       errore.append(";2");
     }

    if (this.getEtichettaCampione()!=null && this.getEtichettaCampione().length()>40)
    {
      errore.append(";3");
    }

    if (this.getCodLaboratorio()==null
                ||
        "-1".equals(this.getCodLaboratorio())
                ||
        "".equals(this.getCodLaboratorio())
        )
    {
      errore.append(";4");
    }

    if (this.getCodModalitaConsegna()==null
                ||
        "-1".equals(this.getCodModalitaConsegna())
                ||
        "".equals(this.getCodModalitaConsegna())
        )
    {
      errore.append(";5");
    }

    if (this.getNote()!=null && this.getNote().length()>512)
    {
      errore.append(";6");
    }

    /**
    * Se non sono stati trovati errori restituisce null
    */
    if (errore.toString().equals("")) return null;
    else return errore.toString();
  }

  public String getCodMateriale()
  {
    return codMateriale;
  }
  public void setCodMateriale(String codMateriale)
  {
    this.codMateriale = codMateriale;
  }
  public void setDescMateriale(String descMateriale)
  {
    this.descMateriale = descMateriale;
  }
  public String getDescMateriale()
  {
    return descMateriale;
  }
  public void setIdRichiesta(long idRichiesta)
  {
    this.idRichiesta = idRichiesta;
  }
  public long getIdRichiesta()
  {
    return idRichiesta;
  }
  public void setIstatComune(String istatComune)
  {
    this.istatComune = istatComune;
  }
  public String getIstatComune()
  {
    return istatComune;
  }
  public void setEtichettaCampione(String etichettaCampione)
  {
    this.etichettaCampione = etichettaCampione;
  }
  public String getEtichettaCampione()
  {
    return etichettaCampione;
  }
  public void setCodLaboratorio(String codLaboratorio)
  {
    this.codLaboratorio = codLaboratorio;
  }
  public String getCodLaboratorio()
  {
    return codLaboratorio;
  }
  public void setDescLaboratorio(String descLaboratorio)
  {
    this.descLaboratorio = descLaboratorio;
  }
  public String getDescLaboratorio()
  {
    return descLaboratorio;
  }
  public void setCodModalitaConsegna(String codModalitaConsegna)
  {
    this.codModalitaConsegna = codModalitaConsegna;
  }
  public String getCodModalitaConsegna()
  {
    return codModalitaConsegna;
  }
  public void setDescModalitaConsegna(String descModalitaConsegna)
  {
    this.descModalitaConsegna = descModalitaConsegna;
  }
  public String getDescModalitaConsegna()
  {
    return descModalitaConsegna;
  }
  public void setNote(String note)
  {
    this.note = note;
  }
  public String getNote()
  {
    return note;
  }
  public void setDescComune(String descComune)
  {
    this.descComune = descComune;
  }
  public String getDescComune()
  {
    return descComune;
  }
  public String getCodiceMisuraPsr()
	{
		return codiceMisuraPsr;
	}
	public void setCodiceMisuraPsr(String codiceMisuraPsr)
	{
		this.codiceMisuraPsr = codiceMisuraPsr;
	}
	public String getNoteMisuraPsr()
	{
		return noteMisuraPsr;
	}
	public void setNoteMisuraPsr(String noteMisuraPsr)
	{
		this.noteMisuraPsr = noteMisuraPsr;
	}
}