package it.csi.agrc;
import it.csi.cuneo.*;
import it.csi.jsf.web.pool.*;
import java.io.*;
import java.sql.*;
//import java.security.*;
import javax.sql.*;

/**
 * Title:        Agrichim - Front Office
 * Description:  Richiesta analisi chimiche su campioni biologici agrari
 * Copyright:    Copyright (c) 2003
 * Company:      CSI Piemonte - Progettazione e Sviluppo - Cuneo
 * @author Michele Piantà, Piergiorgio Chiriotti
 * @version 1.0.0
 */

/**
 * La classe Autenticazione viene utilizzata come classe base per memorizzare le
 * informazioni relative all'utente collegato al sistema (proprietà utente) e
 * gli eventuali problemi che possono verificarsi durante lesecuzione delle
 * query (query, contenutoQuery, errorCode)
 * E' un Java Bean che possiede le proprietà
 * -
 * - query: qui vengono memorizzate informazioni circa il metodo e la classe
 *   contenenti una query che ha generato l'errore
 * - contenutoQuery: viene memorizzata la query che ha causato un'errore
 * - errorCode: codice di errore
 * - dataSource
 * - utente
 * - queryRicerca: memorizza la query utilizzata nella ricerca delle'elenco
 *   delle analisi
 * - queryCountRicerca: come queryRicerca, solo che viene usata per contare
 *   il numero di record estratti
 * - idRichiestaCorrente: contiene l'id della richiesta attualmente in uso
 * - fase: la fase corrente della richiesta in bozza. inizialmente è 0
 * - coordinateGeografiche: se vale true significa che il materiale (TER, FOG,
 *   FRU) di cui si vuole fare l'analisi necessita dell'indicazione delle
 *   coordinate geografiche.
 * - codMateriale: codice materiale della richiesta in bozza corrente.
 *   Inizialmente è null.
 * - spedizioneFattura: se true significa che è stata richiesta la spedizione
 *   della fattura. Se false che non è stata richiesta
 * - costoAnalisi: memorizza al suo interno il costo totale dell'analisi
 *   di questo campione
 * - pa (booleano): default è false; se il login avviene da Rupar, allora TRUE
 */

public class Autenticazione extends BeanDAO implements Serializable
{
  private static final long serialVersionUID = -3006332118842972235L;

  private String contenutoQuery;
  private String errorCode;
  private String query;
  //private DataSource dataSource;
  private Utente utente;
  private String queryRicerca;
  private String queryCountRicerca;
  private long idRichiestaCorrente=0;
  private byte fase=0;
  private boolean coordinateGeografiche;
  private String codMateriale;
  private String istatComuneRichiesta;
  private String comuneRichiesta;
  private boolean spedizioneFattura;
  private double costoAnalisi;
  private Throwable eccezione;
  private boolean datiControllati=false;
  private boolean PA=false;
  //Elisa Poggio 20/10/2015 campo che 
  private boolean piemonte;
  private boolean spid=false;
  private String cod_fiscale_pagatore;
  private String avvisoControlliPagamenti;
  private String idRichiestaChecked;

  public Autenticazione()
  {
  }

  public void setContenutoQuery(String newContenutoQuery)
  {
    contenutoQuery = newContenutoQuery;
  }
  public String getContenutoQuery()
  {
    return contenutoQuery;
  }
  public void setErrorCode(String errorCode)
  {
    this.errorCode = errorCode;
  }
  public String getErrorCode()
  {
    return errorCode;
  }
  public void setQuery(String query)
  {
    this.query = query;
  }
  public String getQuery()
  {
    return query;
  }

  /*public void setDataSource(Object obj)
  {
    if (Utili.POOLMAN)
      this.setGenericPool((it.csi.jsf.web.pool.GenericPool)obj);
    else
      this.dataSource = (javax.sql.DataSource)obj;
  }

  public void setDataSource(javax.sql.DataSource dataSource)
  {
    this.dataSource = dataSource;
  }

  public javax.sql.DataSource getDataSource()
  {
    return dataSource;
  }

  public Connection getConnection()
      throws Exception, SQLException
  {
    if (Utili.POOLMAN)
    {
      //Sono in ambiente TomCat quindi restuituisco una connessione fornita
      //da PoolMan
      return this.getGenericPool().getConnection();
    }
    else
    {
      //Sono in ambiente WebLogic quindi restuituisco una connessione fornita
      //da DataSource
      return dataSource.getConnection();
    }
  }

  public boolean isConnection()
      throws Exception, SQLException
  {
    if (Utili.POOLMAN)
    {
      //Sono in ambiente TomCat quindi controllo se Poolman è inizializzato
      if (this.getGenericPool() == null) return false;
      else return true;
    }
    else
    {
      //Sono in ambiente WebLogic quindi controllo se il DataSource è
      // inizializzato
      if (dataSource == null) return false;
      else return true;
    }
  }*/
  
  public void setUtente(Utente utente) {
    this.utente = utente;
  }
  public Utente getUtente() {
    return utente;
  }

  /**
   * Caricamento dei dati dell'utente necessari all'applicazione
   * @param identificativoUtenteIride viene passato da IRIDE SP
   * @param codiceFiscale viene passato da IRIDE SP
   * @param partitaIVA viene passato da IRIDE SP
   * @throws Exception DataSource non inizializzato
   * @throws SQLException errore generico SQL
   */
  /*public void caricaDati(String identificativoUtenteIride,
                         String codiceFiscale,
                         String partitaIVA)
     throws Exception, SQLException
  {
    utente = new Utente(identificativoUtenteIride,codiceFiscale,partitaIVA);
    if (!isConnection())
           throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn=this.getConnection();

    if (!utente.leggiAnagraficaUtente(conn))
    {
      // Bisogna chiamare il modolo IRIDE SP per la restituzione di tutti
      // i dati necessari all'inserimento nella tabella Anagrafica
    }
    if (partitaIVA!=null)
      utente.leggiAnagraficaAzienda(conn);
    conn.close();
  }*/
  
  public void setQueryRicerca(String queryRicerca)
  {
    this.queryRicerca = queryRicerca;
  }
  public String getQueryRicerca()
  {
    return queryRicerca;
  }
  public void setQueryCountRicerca(String queryCountRicerca)
  {
    this.queryCountRicerca = queryCountRicerca;
  }
  public String getQueryCountRicerca()
  {
    return queryCountRicerca;
  }

  public void ricercaElencoCampioniBase()
  {
    this.ricercaElencoCampioniBase(false,null,null,null,null,
                                   null,null,null,null,null);
  }

  public void ricercaElencoCampioniBase(boolean filtri,
                                       String idRichiestaDa,
                                       String idRichiestaA,
                                       String dataDa,
                                       String dataA,
                                       String tipoMateriale,
                                       String codiceFiscale,
                                       String cognome,
                                       String nome,
                                       String etichetta)
  {
    String filtro="";
    boolean normale=true;
    if (filtri) filtro=impostaCriteriRicerca(idRichiestaDa,idRichiestaA,dataDa,
                                      dataA,tipoMateriale,codiceFiscale,
                                      cognome,nome,etichetta);

    if (filtro.length()>0 && filtro.charAt(0) == '1')
    {
      normale=false;
      filtro=filtro.substring(1);
    }

    /*
     Questa query mi informa sul numero di record restituiti per organizzare
     la visualizzazione della pagina
    */
    StringBuffer queryCount = new StringBuffer();
    if (normale)
     queryCount.append("SELECT COUNT(ID_RICHIESTA) AS NUM ");
    else
     queryCount.append("SELECT COUNT(DISTINCT ID_RICHIESTA) AS NUM ");
    queryCount.append("FROM ETICHETTA_CAMPIONE EC, MATERIALE M, ");
    if (normale)
     queryCount.append("CODIFICA_TRACCIABILITA CT ");
    else
     queryCount.append("CODIFICA_TRACCIABILITA CT, ANAGRAFICA A ");
    queryCount.append("WHERE EC.STATO_ATTUALE <> '00' ");
    if (!normale)
    {
      queryCount.append("AND (A.ID_ANAGRAFICA=EC.ANAGRAFICA_UTENTE OR ");
      queryCount.append("A.ID_ANAGRAFICA=EC.ANAGRAFICA_TECNICO OR ");
      queryCount.append("A.ID_ANAGRAFICA=EC.ANAGRAFICA_PROPRIETARIO) ");
    }
    queryCount.append("AND EC.CODICE_MATERIALE= M.CODICE_MATERIALE ");
    queryCount.append("AND EC.STATO_ATTUALE = CT.CODICE ");
    queryCount.append("AND (");
    queryCount.append("EC.ANAGRAFICA_UTENTE = ");
    queryCount.append(this.getUtente().getAnagraficaUtente());
    queryCount.append(" OR EC.ANAGRAFICA_TECNICO = ");
    queryCount.append(this.getUtente().getAnagraficaUtente());
    queryCount.append(" OR EC.ANAGRAFICA_PROPRIETARIO = ");
    queryCount.append(this.getUtente().getAnagraficaUtente());
    queryCount.append(" OR EC.ANAGRAFICA_PROPRIETARIO = ");
    queryCount.append(this.getUtente().getAnagraficaAzienda());
    queryCount.append(") ");
    queryCount.append(filtro);
    //CuneoLogger.debug(this,"queryCount "+queryCount.toString());
    /*
     Questa query invece mi restituisce i record
    */
    StringBuffer query = new StringBuffer("SELECT * FROM (");
    if (normale)
     query.append("SELECT EC.ID_RICHIESTA,");
    else
     query.append("SELECT DISTINCT EC.ID_RICHIESTA,");
    query.append("ROW_NUMBER() OVER (ORDER BY EC.ID_RICHIESTA DESC) AS NUM,");
    query.append("TO_CHAR(EC.DATA_INSERIMENTO_RICHIESTA,'DD/MM/YYYY') AS DATA,");
    query.append("EC.CODICE_MATERIALE AS CODICE_MATERIALE,");
    query.append("M.DESCRIZIONE AS MATERIALE,");
    query.append("EC.DESCRIZIONE_ETICHETTA,");
    query.append("CT.DESCRIZIONE AS DESC_STATO_ATTUALE,");
    query.append("EC.STATO_ATTUALE,");
    query.append("EC.ANAGRAFICA_UTENTE,");
    query.append("EC.ANAGRAFICA_TECNICO,");
    query.append("EC.ANAGRAFICA_PROPRIETARIO,");
    query.append("COALESCE(TABELLA_FATTURE.CONTEGGIO_FATTURE,0) AS CONTEGGIO_FATTURE,");
    query.append("TABELLA_FATTURE.NUMERO_FATTURA, ");
    query.append("TABELLA_FATTURE.ANNO AS ANNO_FATTURA ");
    query.append(" FROM ETICHETTA_CAMPIONE EC LEFT JOIN");
    query.append("(SELECT COUNT(CF.ID_RICHIESTA) CONTEGGIO_FATTURE, MAX(CF.NUMERO_FATTURA) NUMERO_FATTURA, MAX(CF.ANNO) ANNO, CF.ID_RICHIESTA ");
    query.append("FROM CAMPIONE_FATTURATO CF, FATTURA FA ");
    query.append("WHERE CF.ANNO = FA.ANNO ");
    query.append("AND CF.NUMERO_FATTURA = FA.NUMERO_FATTURA ");
    query.append("GROUP BY CF.ID_RICHIESTA) AS TABELLA_FATTURE USING (ID_RICHIESTA), ");
    query.append("MATERIALE M, ");
    if (normale)
    {
      query.append("CODIFICA_TRACCIABILITA CT ");
    }
    else
    {
      query.append("CODIFICA_TRACCIABILITA CT, ANAGRAFICA A ");
    }
    query.append("WHERE EC.STATO_ATTUALE <> '00' ");
    if (! normale)
    {
      query.append("AND (A.ID_ANAGRAFICA=EC.ANAGRAFICA_UTENTE OR ");
      query.append("A.ID_ANAGRAFICA=EC.ANAGRAFICA_TECNICO OR ");
      query.append("A.ID_ANAGRAFICA=EC.ANAGRAFICA_PROPRIETARIO) ");
    }
    query.append("AND EC.CODICE_MATERIALE= M.CODICE_MATERIALE ");
    query.append("AND EC.STATO_ATTUALE = CT.CODICE ");
    query.append("AND (");
    query.append("EC.ANAGRAFICA_UTENTE = ");
    query.append(this.getUtente().getAnagraficaUtente());
    query.append(" OR EC.ANAGRAFICA_TECNICO = ");
    query.append(this.getUtente().getAnagraficaUtente());
    query.append(" OR EC.ANAGRAFICA_PROPRIETARIO = ");
    query.append(this.getUtente().getAnagraficaUtente());
    query.append(" OR EC.ANAGRAFICA_PROPRIETARIO = ");
    query.append(this.getUtente().getAnagraficaAzienda());
    query.append(") ");
    query.append(filtro);
    query.append("ORDER BY EC.ID_RICHIESTA DESC");
    //CuneoLogger.debug(this,"query1 "+query.toString());
    this.setQueryCountRicerca(queryCount.toString());
    this.setQueryRicerca(query.toString());
  }

  public void ricercaElencoCampioniLaboratorio(String idRichiestaDa,
                                       String idRichiestaA,
                                       String dataDa,
                                       String dataA,
                                       String tipoMateriale,
                                       String codiceFiscale,
                                       String cognome,
                                       String nome,
                                       String etichetta)
  {
    boolean normale=true;
    String filtro=impostaCriteriRicerca(idRichiestaDa,idRichiestaA,dataDa,
                                      dataA,tipoMateriale,codiceFiscale,
                                      cognome,nome,etichetta);
    if (filtro.length()>0 && filtro.charAt(0) == '1')
    {
      normale=false;
      filtro=filtro.substring(1);
    }
    /*
      Questa query mi informa sul numero di record restituiti per organizzare
      la visualizzazione della pagina
    */

    StringBuffer queryCount = new StringBuffer();
    if (normale)
     queryCount.append("SELECT COUNT(ID_RICHIESTA) AS NUM ");
    else
     queryCount.append("SELECT COUNT(DISTINCT ID_RICHIESTA) AS NUM ");
    queryCount.append("FROM ETICHETTA_CAMPIONE EC, MATERIALE M, ");
    if (normale)
     queryCount.append("CODIFICA_TRACCIABILITA CT ");
    else
     queryCount.append("CODIFICA_TRACCIABILITA CT, ANAGRAFICA A ");
    queryCount.append("WHERE EC.STATO_ATTUALE <> '00' ");
    if (!normale)
    {
      queryCount.append("AND (A.ID_ANAGRAFICA=EC.ANAGRAFICA_UTENTE OR ");
      queryCount.append("A.ID_ANAGRAFICA=EC.ANAGRAFICA_TECNICO OR ");
      queryCount.append("A.ID_ANAGRAFICA=EC.ANAGRAFICA_PROPRIETARIO) ");
    }
    queryCount.append("AND EC.CODICE_MATERIALE= M.CODICE_MATERIALE ");
    queryCount.append("AND EC.STATO_ATTUALE = CT.CODICE ");
    queryCount.append(filtro);
    //CuneoLogger.debug(this,"queryCount "+queryCount.toString());

    /*
     Questa query invece mi restituisce i record
    */
    StringBuffer query = new StringBuffer("SELECT * FROM (");
    if (normale)
     query.append("SELECT EC.ID_RICHIESTA,");
    else
     query.append("SELECT DISTINCT EC.ID_RICHIESTA,");
    query.append("ROW_NUMBER() OVER (ORDER BY EC.ID_RICHIESTA DESC) AS NUM,");
    query.append("TO_CHAR(EC.DATA_INSERIMENTO_RICHIESTA,'DD/MM/YYYY')");
    query.append(" AS DATA,");
    query.append("EC.CODICE_MATERIALE AS CODICE_MATERIALE,");
    query.append("M.DESCRIZIONE AS MATERIALE,");
    query.append("EC.DESCRIZIONE_ETICHETTA,");
    query.append("CT.DESCRIZIONE AS DESC_STATO_ATTUALE,");
    query.append("EC.STATO_ATTUALE,");
    query.append("EC.ANAGRAFICA_UTENTE,");
    query.append("EC.ANAGRAFICA_TECNICO,");
    query.append("EC.ANAGRAFICA_PROPRIETARIO, ");
    query.append("COALESCE(TABELLA_FATTURE.CONTEGGIO_FATTURE,0) AS CONTEGGIO_FATTURE,");
    query.append("TABELLA_FATTURE.NUMERO_FATTURA, ");
    query.append("TABELLA_FATTURE.ANNO AS ANNO_FATTURA ");
    query.append(" FROM ETICHETTA_CAMPIONE EC LEFT JOIN");
    query.append("(SELECT COUNT(CF.ID_RICHIESTA) CONTEGGIO_FATTURE, MAX(CF.NUMERO_FATTURA) NUMERO_FATTURA, MAX(CF.ANNO) ANNO, CF.ID_RICHIESTA ");
    query.append("FROM CAMPIONE_FATTURATO CF, FATTURA FA ");
    query.append("WHERE CF.ANNO = FA.ANNO ");
    query.append("AND CF.NUMERO_FATTURA = FA.NUMERO_FATTURA ");
    query.append("GROUP BY CF.ID_RICHIESTA) AS TABELLA_FATTURE USING (ID_RICHIESTA), ");
    query.append("MATERIALE M, ");
    if (normale)
      query.append("CODIFICA_TRACCIABILITA CT ");
    else
      query.append("CODIFICA_TRACCIABILITA CT, ANAGRAFICA A ");
    query.append("WHERE EC.STATO_ATTUALE <> '00' ");
    if (!normale)
    {
      query.append("AND (A.ID_ANAGRAFICA=EC.ANAGRAFICA_UTENTE OR ");
      query.append("A.ID_ANAGRAFICA=EC.ANAGRAFICA_TECNICO OR ");
      query.append("A.ID_ANAGRAFICA=EC.ANAGRAFICA_PROPRIETARIO) ");
    }
    query.append("AND EC.CODICE_MATERIALE= M.CODICE_MATERIALE ");
    query.append("AND EC.STATO_ATTUALE = CT.CODICE ");
    query.append(filtro);
    query.append("ORDER BY EC.ID_RICHIESTA DESC");
    //CuneoLogger.debug(this,"query "+query.toString());
    this.setQueryCountRicerca(queryCount.toString());
    this.setQueryRicerca(query.toString());
  }

  public void ricercaElencoCampioniTecnico(String idRichiestaDa,
                                       String idRichiestaA,
                                       String dataDa,
                                       String dataA,
                                       String tipoMateriale,
                                       String codiceFiscale,
                                       String cognome,
                                       String nome,
                                       String etichetta)
  {
    String filtro=impostaCriteriRicerca(idRichiestaDa,idRichiestaA,dataDa,
                                      dataA,tipoMateriale,codiceFiscale,
                                      cognome,nome,etichetta);
    /*
      Questa query mi informa sul numero di record restituiti per organizzare
      la visualizzazione della pagina
    */
    if (filtro.length()>0 && filtro.charAt(0) == '1')
      filtro=filtro.substring(1);
    StringBuffer queryCount = new StringBuffer("SELECT COUNT(*) AS NUM ");
    queryCount.append("FROM ANAGRAFICA A,ETICHETTA_CAMPIONE EC, MATERIALE M, ");
    queryCount.append("CODIFICA_TRACCIABILITA CT ");
    queryCount.append("WHERE EC.STATO_ATTUALE <> '00' ");
    queryCount.append("AND EC.CODICE_MATERIALE= M.CODICE_MATERIALE ");
    queryCount.append("AND EC.STATO_ATTUALE = CT.CODICE ");
    queryCount.append("AND A.ID_ORGANIZZAZIONE = ");
    queryCount.append(this.getUtente().getIdOrganizzazione());
    queryCount.append(" AND (");
    queryCount.append("EC.ANAGRAFICA_UTENTE=A.ID_ANAGRAFICA ");
    queryCount.append("OR EC.ANAGRAFICA_TECNICO = A.ID_ANAGRAFICA");
    queryCount.append(") ");
    queryCount.append(filtro);
    //CuneoLogger.debug(this,"queryCount "+queryCount.toString());

    /*
     Questa query invece mi restituisce i record
    */
    StringBuffer query = new StringBuffer("SELECT * FROM (");
    query.append("SELECT ROW_NUMBER() OVER (ORDER BY EC.ID_RICHIESTA DESC) AS NUM,");
    query.append("EC.ID_RICHIESTA,");
    query.append("TO_CHAR(EC.DATA_INSERIMENTO_RICHIESTA,'DD/MM/YYYY')");
    query.append(" AS DATA,");
    query.append("EC.CODICE_MATERIALE AS CODICE_MATERIALE,");
    query.append("M.DESCRIZIONE AS MATERIALE,");
    query.append("EC.DESCRIZIONE_ETICHETTA,");
    query.append("CT.DESCRIZIONE AS DESC_STATO_ATTUALE,");
    query.append("EC.STATO_ATTUALE,");
    query.append("EC.ANAGRAFICA_UTENTE,");
    query.append("EC.ANAGRAFICA_TECNICO,");
    query.append("EC.ANAGRAFICA_PROPRIETARIO ");
    query.append("FROM ANAGRAFICA A,ETICHETTA_CAMPIONE EC, MATERIALE M, ");
    query.append("CODIFICA_TRACCIABILITA CT ");
    query.append("WHERE EC.STATO_ATTUALE <> '00' ");
    query.append("AND EC.CODICE_MATERIALE= M.CODICE_MATERIALE ");
    query.append("AND EC.STATO_ATTUALE = CT.CODICE ");
    query.append("AND A.ID_ORGANIZZAZIONE = ");
    query.append(this.getUtente().getIdOrganizzazione());
    query.append(" AND (");
    query.append("EC.ANAGRAFICA_UTENTE=A.ID_ANAGRAFICA ");
    query.append("OR EC.ANAGRAFICA_TECNICO = A.ID_ANAGRAFICA");
    query.append(") ");
    query.append(filtro);
    //CuneoLogger.debug(this,"query2 "+query.toString());
    this.setQueryCountRicerca(queryCount.toString());
    this.setQueryRicerca(query.toString());
  }
  
  private String impostaCriteriRicerca(String idRichiestaDa,
          String idRichiestaA,
          String dataDa,
          String dataA,
          String tipoMateriale,
          String codiceFiscale,
          String cognome,
          String nome,
          String etichetta)
	{
	StringBuffer filtro=new StringBuffer(" ");
	if (idRichiestaDa!=null)
		filtro.append(" AND EC.ID_RICHIESTA >= ").append(idRichiestaDa);
	if (idRichiestaA!=null)
		filtro.append(" AND EC.ID_RICHIESTA <= ").append(idRichiestaA);
	if (dataDa!=null){
		filtro.append(" AND EC.DATA_INSERIMENTO_RICHIESTA >= to_timestamp('");
		filtro.append(dataDa).append("','DD/MM/YYYY') ");
	}
	if (dataA!=null){
		filtro.append(" AND EC.DATA_INSERIMENTO_RICHIESTA <= to_timestamp('");
		filtro.append(dataA).append("','DD/MM/YYYY') ");
	}
	if (tipoMateriale!=null){
		filtro.append(" AND EC.CODICE_MATERIALE = '").append(tipoMateriale);
		filtro.append("' ");
	}
	if (codiceFiscale!=null){
		filtro.setCharAt(0,'1');
		filtro.append(" AND A.CODICE_IDENTIFICATIVO = '").append(codiceFiscale);
		filtro.append("' ");
	}
	if (cognome!=null){
		filtro.setCharAt(0,'1');
		filtro.append(" AND UPPER(A.COGNOME_RAGIONE_SOCIALE) LIKE UPPER('%");
		filtro.append(Utili.toVarchar(cognome.trim())).append("%') ");
	}
	if (nome!=null){
		filtro.setCharAt(0,'1');
		filtro.append(" AND UPPER(A.NOME) LIKE UPPER('%");
		filtro.append(Utili.toVarchar(nome.trim())).append("%') ");
	}
	if (etichetta!=null){
		filtro.append(" AND UPPER(EC.DESCRIZIONE_ETICHETTA) LIKE UPPER('%");
		filtro.append(Utili.toVarchar(etichetta.trim())).append("%') ");
	}
	filtro.append(" ");
	return filtro.toString();
	}
  
  public void ricercaElencoPagamentiBase() {
    this.ricercaElencoPagamentiBase(false,null,null,null,null,
                                   null,null,null,null,null,null,null);
  }
  
  public void ricercaElencoPagamentiBase(boolean filtri,
          String idRichiestaDa,
          String idRichiestaA,
          String dataDa,
          String dataA,
          String tipoMateriale,
          String codiceFiscale,
          String cognome,
          String nome,
          String etichetta,
          String iuv,
          String statoPagamento){
	String filtro="";
	boolean normale=true;
	if (filtri) 
		filtro=impostaCriteriRicercaPagamenti(idRichiestaDa,idRichiestaA,dataDa,
	         dataA,tipoMateriale,codiceFiscale,cognome,nome,etichetta,iuv,statoPagamento);
	
	if (filtro.length()>0 && filtro.charAt(0) == '1') {
		normale=false;
		filtro=filtro.substring(1);
	}
	
	/*
	Questa query mi informa sul numero di record restituiti per organizzare
	la visualizzazione della pagina
	*/
	StringBuffer queryCount = new StringBuffer();
	if (normale)
		queryCount.append("SELECT COUNT(EC.ID_RICHIESTA) AS NUM ");
	else
		queryCount.append("SELECT COUNT(DISTINCT EC.ID_RICHIESTA) AS NUM ");
	queryCount.append("FROM ETICHETTA_CAMPIONE EC ");
	queryCount.append(" LEFT JOIN DATI_FATTURA DF on EC.ID_RICHIESTA = DF.ID_RICHIESTA ");
	if (!normale)
		queryCount.append(" LEFT JOIN ANAGRAFICA A on (A.ID_ANAGRAFICA=EC.ANAGRAFICA_UTENTE OR A.ID_ANAGRAFICA=EC.ANAGRAFICA_TECNICO OR A.ID_ANAGRAFICA=EC.ANAGRAFICA_PROPRIETARIO) ");
	if(iuv!=null) {
		queryCount.append(" LEFT JOIN PAGAMENTI P on EC.ID_RICHIESTA = P.ID_RICHIESTA ");
		if(iuv.startsWith("3")) 
	    	  iuv = iuv.substring(1);
		queryCount.append("WHERE UPPER(IUV) like UPPER('").append(iuv).append("') AND ");
	}else
		queryCount.append("WHERE ");
	queryCount.append(" EC.STATO_ATTUALE <> '00' and (");
	queryCount.append("EC.ANAGRAFICA_UTENTE = ");
	queryCount.append(this.getUtente().getAnagraficaUtente());
	queryCount.append(" OR EC.ANAGRAFICA_TECNICO = ");
	queryCount.append(this.getUtente().getAnagraficaUtente());
	queryCount.append(" OR EC.ANAGRAFICA_PROPRIETARIO = ");
	queryCount.append(this.getUtente().getAnagraficaUtente());
	queryCount.append(" OR EC.ANAGRAFICA_PROPRIETARIO = ");
	queryCount.append(this.getUtente().getAnagraficaAzienda());
	queryCount.append(") ");
	queryCount.append(filtro);
	CuneoLogger.debug(this,"queryCountElencoPagamentiBase "+queryCount.toString());
	/*
	Questa query invece mi restituisce i record
	*/
	StringBuffer query = new StringBuffer("SELECT * FROM (");
	if (normale)
		query.append("SELECT EC.ID_RICHIESTA,");
	else
		query.append("SELECT DISTINCT EC.ID_RICHIESTA,");
	query.append("ROW_NUMBER() OVER (ORDER BY EC.ID_RICHIESTA DESC) AS NUM,");
	query.append("TO_CHAR(EC.DATA_INSERIMENTO_RICHIESTA,'DD/MM/YYYY') AS DATA,");
	query.append("EC.DESCRIZIONE_ETICHETTA,");
	query.append("EC.STATO_ATTUALE,");
	query.append("EC.ANAGRAFICA_UTENTE,");
	query.append("EC.ANAGRAFICA_TECNICO,");
	query.append("EC.ANAGRAFICA_PROPRIETARIO,");
	query.append("(CASE WHEN DF.FATTURARE='U' THEN (select concat(cognome_ragione_sociale,' ',nome) from anagrafica where id_anagrafica=EC.anagrafica_utente) "+
			 		  " WHEN DF.FATTURARE='P' THEN (select concat(cognome_ragione_sociale,' ',nome) from anagrafica where id_anagrafica=EC.anagrafica_proprietario)  "+
			 		  " WHEN DF.FATTURARE='T' THEN (select concat(cognome_ragione_sociale,' ',nome) from anagrafica where id_anagrafica=EC.anagrafica_tecnico)  "+
			 		  " WHEN DF.FATTURARE='O' THEN (  "+
			                "CASE WHEN EC.anagrafica_tecnico IS NULL "+
			                      "THEN (select op.ragione_sociale from anagrafica a  "+
							            	"left join organizzazione_professionale op on a.id_organizzazione = op.id_organizzazione "+
			          				        "where a.id_anagrafica=ec.anagrafica_utente) "+
			                "ELSE (select op.ragione_sociale from anagrafica a "+
						            "left join organizzazione_professionale op on a.id_organizzazione = op.id_organizzazione "+
			  				        "where a.id_anagrafica=ec.anagrafica_tecnico)    "+
			                "END) "+
					   "WHEN DF.FATTURARE='A' THEN DF.ragione_sociale END) as DENOMINAZIONE, ");
	query.append("EC.PAGAMENTO,DF.CODICE_DESTINATARIO,DF.PEC,DF.FATTURA_SN, ");
	query.append("(with elenco_iuv as (select distinct FIRST_VALUE(iuv) " + 
			"    OVER( PARTITION BY ID_RICHIESTA ORDER BY data_annullamento desc) iuv,ID_RICHIESTA from PAGAMENTI) " + 
			"select case when p.data_annullamento is null then p.iuv else '' end iuv " + 
			"from pagamenti p " + 
			"  JOIN elenco_iuv ei ON p.id_richiesta = ei.id_richiesta and p.iuv = ei.iuv " + 
			"where p.id_richiesta = ec.id_richiesta),");
	query.append("(with elenco_iuv as ( select distinct FIRST_VALUE(iuv)  " + 
			"    OVER( PARTITION BY ID_RICHIESTA ORDER BY data_annullamento desc) iuv,ID_RICHIESTA from PAGAMENTI) " + 
			"select  TP.COD_TIPO_PAGAMENTO " + 
			"from pagamenti p " + 
			"  JOIN elenco_iuv ei ON p.id_richiesta = ei.id_richiesta and p.iuv = ei.iuv " + 
			"  LEFT JOIN tipo_pagamento TP ON P.ID_TIPO_PAGAMENTO = TP.ID_TIPO_PAGAMENTO " + 
			"where p.id_richiesta = ec.id_richiesta),");
	query.append("(with elenco_iuv as (select distinct FIRST_VALUE(iuv) " + 
			"    OVER( PARTITION BY ID_RICHIESTA ORDER BY data_annullamento desc) iuv,ID_RICHIESTA from PAGAMENTI) " + 
			"select p.data_annullamento " + 
			"from pagamenti p " + 
			"  JOIN elenco_iuv ei ON p.id_richiesta = ei.id_richiesta and p.iuv = ei.iuv " + 
			"where p.id_richiesta = ec.id_richiesta), ");
	query.append("(WITH traccia AS (SELECT max(id_richiesta) id_richiesta,max(progressivo) progressivo "
			+ "FROM tracciatura group by id_richiesta )\r\n" + 
			"SELECT p.codice " + 
			"FROM tracciatura p " + 
			"JOIN traccia ei ON p.id_richiesta = ei.id_richiesta AND p.progressivo = ei.progressivo " + 
			"WHERE p.id_richiesta = ec.id_richiesta), ");
	query.append("EC.CODICE_MATERIALE ");
	query.append(" FROM ETICHETTA_CAMPIONE EC ");
	query.append(" LEFT JOIN DATI_FATTURA DF on EC.ID_RICHIESTA = DF.ID_RICHIESTA ");
	if (!normale)
		query.append(" LEFT JOIN ANAGRAFICA A on (A.ID_ANAGRAFICA=EC.ANAGRAFICA_UTENTE OR A.ID_ANAGRAFICA=EC.ANAGRAFICA_TECNICO OR A.ID_ANAGRAFICA=EC.ANAGRAFICA_PROPRIETARIO) ");
	query.append("WHERE EC.STATO_ATTUALE <> '00' and (");
	query.append("EC.ANAGRAFICA_UTENTE = ");
	query.append(this.getUtente().getAnagraficaUtente());
	query.append(" OR EC.ANAGRAFICA_TECNICO = ");
	query.append(this.getUtente().getAnagraficaUtente());
	query.append(" OR EC.ANAGRAFICA_PROPRIETARIO = ");
	query.append(this.getUtente().getAnagraficaUtente());
	query.append(" OR EC.ANAGRAFICA_PROPRIETARIO = ");
	query.append(this.getUtente().getAnagraficaAzienda());
	query.append(") ");
	query.append(filtro);
	query.append(" group by EC.ID_RICHIESTA,DF.FATTURARE,DF.ragione_sociale,DF.CODICE_DESTINATARIO,DF.PEC,DF.FATTURA_SN,EC.CODICE_MATERIALE ");
	query.append(" ORDER BY EC.ID_RICHIESTA DESC");
	if(iuv!=null) {
		query.append(" IUV:'").append(iuv).append("'");
	}
	CuneoLogger.debug(this,"queryElencoPagamentiBase "+query.toString());
	this.setQueryCountRicerca(queryCount.toString());
	this.setQueryRicerca(query.toString());
	}
  
  public void ricercaElencoPagamentiLaboratorio(String idRichiestaDa,
          String idRichiestaA,
          String dataDa,
          String dataA,
          String tipoMateriale,
          String codiceFiscale,
          String cognome,
          String nome,
          String etichetta,
          String iuv,
          String statoPagamento)
	{
	boolean normale=true;
	String filtro=impostaCriteriRicercaPagamenti(idRichiestaDa,idRichiestaA,dataDa,
	         dataA,tipoMateriale,codiceFiscale,cognome,nome,etichetta,iuv,statoPagamento);
	if (filtro.length()>0 && filtro.charAt(0) == '1'){
		normale=false;
		filtro=filtro.substring(1);
	}
	/*
	Questa query mi informa sul numero di record restituiti per organizzare
	la visualizzazione della pagina
	*/
	
	StringBuffer queryCount = new StringBuffer();
	if (normale)
		queryCount.append("SELECT COUNT(EC.ID_RICHIESTA) AS NUM ");
	else
		queryCount.append("SELECT COUNT(DISTINCT EC.ID_RICHIESTA) AS NUM ");
	queryCount.append("FROM ETICHETTA_CAMPIONE EC ");
	queryCount.append(" LEFT JOIN DATI_FATTURA DF on EC.ID_RICHIESTA = DF.ID_RICHIESTA ");
	if (!normale)
		queryCount.append(" LEFT JOIN ANAGRAFICA A on (A.ID_ANAGRAFICA=EC.ANAGRAFICA_UTENTE OR A.ID_ANAGRAFICA=EC.ANAGRAFICA_TECNICO OR A.ID_ANAGRAFICA=EC.ANAGRAFICA_PROPRIETARIO) ");
	if(iuv!=null) {
		queryCount.append(" LEFT JOIN PAGAMENTI P on EC.ID_RICHIESTA = P.ID_RICHIESTA ");
		if(iuv.startsWith("3")) 
	    	  iuv = iuv.substring(1);
		queryCount.append("WHERE UPPER(IUV) like UPPER('").append(iuv).append("') AND ");
	}else
		queryCount.append("WHERE ");
	queryCount.append(" EC.STATO_ATTUALE <> '00' and (EC.ANAGRAFICA_UTENTE = ");
	queryCount.append(this.getUtente().getAnagraficaUtente());
	queryCount.append(" OR EC.ANAGRAFICA_TECNICO = ");
	queryCount.append(this.getUtente().getAnagraficaUtente());
	queryCount.append(" OR EC.ANAGRAFICA_PROPRIETARIO = ");
	queryCount.append(this.getUtente().getAnagraficaUtente());
	queryCount.append(" OR EC.ANAGRAFICA_PROPRIETARIO = ");
	queryCount.append(this.getUtente().getAnagraficaAzienda());
	queryCount.append(") ");
	queryCount.append(filtro);
	CuneoLogger.debug(this,"queryCountElencoPagamentiLaboratorio "+queryCount.toString());
	
	/*
	Questa query invece mi restituisce i record
	*/
	StringBuffer query = new StringBuffer("SELECT * FROM (");
	if (normale)
		query.append("SELECT EC.ID_RICHIESTA,");
	else
		query.append("SELECT DISTINCT EC.ID_RICHIESTA,");
	query.append("ROW_NUMBER() OVER (ORDER BY EC.ID_RICHIESTA DESC) AS NUM,");
	query.append("TO_CHAR(EC.DATA_INSERIMENTO_RICHIESTA,'DD/MM/YYYY')");
	query.append(" AS DATA,");
	query.append("EC.DESCRIZIONE_ETICHETTA,");
	query.append("EC.STATO_ATTUALE,");
	query.append("EC.ANAGRAFICA_UTENTE,");
	query.append("EC.ANAGRAFICA_TECNICO,");
	query.append("EC.ANAGRAFICA_PROPRIETARIO, ");
	query.append("(CASE WHEN DF.FATTURARE='U' THEN (select cognome_ragione_sociale from anagrafica where id_anagrafica=EC.anagrafica_utente) "+
	 		  " WHEN DF.FATTURARE='P' THEN (select cognome_ragione_sociale from anagrafica where id_anagrafica=EC.anagrafica_proprietario)  "+
	 		  " WHEN DF.FATTURARE='T' THEN (select concat(cognome_ragione_sociale,' ',nome) from anagrafica where id_anagrafica=EC.anagrafica_tecnico)  "+
	 		  " WHEN DF.FATTURARE='O' THEN (  "+
	                "CASE WHEN EC.anagrafica_tecnico IS NULL "+
	                      "THEN (select op.ragione_sociale from anagrafica a  "+
					            	"left join organizzazione_professionale op on a.id_organizzazione = op.id_organizzazione "+
	          				        "where a.id_anagrafica=ec.anagrafica_utente) "+
	                "ELSE (select op.ragione_sociale from anagrafica a "+
				            "left join organizzazione_professionale op on a.id_organizzazione = op.id_organizzazione "+
	  				        "where a.id_anagrafica=ec.anagrafica_tecnico)    "+
	                "END) "+
			   "WHEN DF.FATTURARE='A' THEN DF.ragione_sociale END) as DENOMINAZIONE, ");
	query.append("EC.PAGAMENTO,DF.CODICE_DESTINATARIO,DF.PEC,DF.FATTURA_SN, ");
	query.append("(with elenco_iuv as (select distinct FIRST_VALUE(iuv) " + 
			"    OVER( PARTITION BY ID_RICHIESTA ORDER BY data_annullamento desc) iuv,ID_RICHIESTA from PAGAMENTI) " + 
			"select case when p.data_annullamento is null then p.iuv else '' end iuv " + 
			"from pagamenti p " + 
			"  JOIN elenco_iuv ei ON p.id_richiesta = ei.id_richiesta and p.iuv = ei.iuv " + 
			"where p.id_richiesta = ec.id_richiesta),");
	query.append("(with elenco_iuv as ( select distinct FIRST_VALUE(iuv)  " + 
			"    OVER( PARTITION BY ID_RICHIESTA ORDER BY data_annullamento desc) iuv,ID_RICHIESTA from PAGAMENTI) " + 
			"select  TP.COD_TIPO_PAGAMENTO " + 
			"from pagamenti p " + 
			"  JOIN elenco_iuv ei ON p.id_richiesta = ei.id_richiesta and p.iuv = ei.iuv " + 
			"  LEFT JOIN tipo_pagamento TP ON P.ID_TIPO_PAGAMENTO = TP.ID_TIPO_PAGAMENTO " + 
			"where p.id_richiesta = ec.id_richiesta),");
	query.append("(with elenco_iuv as (select distinct FIRST_VALUE(iuv) " + 
					"    OVER( PARTITION BY ID_RICHIESTA ORDER BY data_annullamento desc) iuv,ID_RICHIESTA from PAGAMENTI) " + 
					"select p.data_annullamento " + 
					"from pagamenti p " + 
					"  JOIN elenco_iuv ei ON p.id_richiesta = ei.id_richiesta and p.iuv = ei.iuv " + 
					"where p.id_richiesta = ec.id_richiesta), ");
	query.append("(WITH traccia AS (SELECT max(id_richiesta) id_richiesta,max(progressivo) progressivo "
			+ "FROM tracciatura group by id_richiesta )\r\n" + 
			"SELECT p.codice " + 
			"FROM tracciatura p " + 
			"JOIN traccia ei ON p.id_richiesta = ei.id_richiesta AND p.progressivo = ei.progressivo " + 
			"WHERE p.id_richiesta = ec.id_richiesta), ");
	query.append("EC.CODICE_MATERIALE ");
	query.append(" FROM ETICHETTA_CAMPIONE EC ");
	query.append(" LEFT JOIN DATI_FATTURA DF on EC.ID_RICHIESTA = DF.ID_RICHIESTA ");
	if (!normale)
		query.append(" LEFT JOIN ANAGRAFICA A on (A.ID_ANAGRAFICA=EC.ANAGRAFICA_UTENTE OR A.ID_ANAGRAFICA=EC.ANAGRAFICA_TECNICO OR A.ID_ANAGRAFICA=EC.ANAGRAFICA_PROPRIETARIO) ");
	query.append("WHERE EC.STATO_ATTUALE <> '00' and (EC.ANAGRAFICA_UTENTE = ");
	query.append(this.getUtente().getAnagraficaUtente());
	query.append(" OR EC.ANAGRAFICA_TECNICO = ");
	query.append(this.getUtente().getAnagraficaUtente());
	query.append(" OR EC.ANAGRAFICA_PROPRIETARIO = ");
	query.append(this.getUtente().getAnagraficaUtente());
	query.append(" OR EC.ANAGRAFICA_PROPRIETARIO = ");
	query.append(this.getUtente().getAnagraficaAzienda());
	query.append(") ");
	query.append(filtro);
	query.append(" group by EC.ID_RICHIESTA,DF.FATTURARE,DF.ragione_sociale,DF.CODICE_DESTINATARIO,DF.PEC,DF.FATTURA_SN,EC.CODICE_MATERIALE ");
	query.append(" ORDER BY EC.ID_RICHIESTA DESC");
	if(iuv!=null) {
		query.append(" IUV:'").append(iuv).append("'");
	}
	CuneoLogger.debug(this,"queryElencoPagamentiLaboratorio "+query.toString());
	this.setQueryCountRicerca(queryCount.toString());
	this.setQueryRicerca(query.toString());
	}
  
  public void ricercaElencoPagamentiTecnico(String idRichiestaDa,
          String idRichiestaA,
          String dataDa,
          String dataA,
          String tipoMateriale,
          String codiceFiscale,
          String cognome,
          String nome,
          String etichetta,
          String iuv,
          String statoPagamento)
	{
	String filtro=impostaCriteriRicercaPagamenti(idRichiestaDa,idRichiestaA,dataDa,
	         dataA,tipoMateriale,codiceFiscale,cognome,nome,etichetta,iuv,statoPagamento);
	/*
	Questa query mi informa sul numero di record restituiti per organizzare
	la visualizzazione della pagina
	*/
	StringBuffer queryCount = new StringBuffer("");
	if (filtro.length()>0 && filtro.charAt(0) == '1')
		filtro=filtro.substring(1);
	
	queryCount.append("SELECT COUNT(*) AS NUM ");
	queryCount.append("FROM ETICHETTA_CAMPIONE EC ");
	queryCount.append(" LEFT JOIN DATI_FATTURA DF on EC.ID_RICHIESTA = DF.ID_RICHIESTA ");
	queryCount.append(" LEFT JOIN ANAGRAFICA A on (EC.ANAGRAFICA_UTENTE=A.ID_ANAGRAFICA OR EC.ANAGRAFICA_TECNICO = A.ID_ANAGRAFICA) ");
	if(iuv!=null) {
		queryCount.append(" LEFT JOIN PAGAMENTI P on EC.ID_RICHIESTA = P.ID_RICHIESTA ");
		if(iuv.startsWith("3")) 
	    	  iuv = iuv.substring(1);
		queryCount.append("WHERE UPPER(IUV) like UPPER('").append(iuv).append("') AND ");
	}else
		queryCount.append("WHERE ");
	queryCount.append(" EC.STATO_ATTUALE <> '00' and A.ID_ORGANIZZAZIONE = ");
	queryCount.append(this.getUtente().getIdOrganizzazione());
	queryCount.append(filtro);
	CuneoLogger.debug(this,"queryCountElencoPagamentiTecnico "+queryCount.toString());
	
	/*
	Questa query invece mi restituisce i record
	*/
	StringBuffer query = new StringBuffer("SELECT * FROM (");
	query.append("SELECT ROW_NUMBER() OVER (ORDER BY EC.ID_RICHIESTA DESC) AS NUM,");
	query.append("EC.ID_RICHIESTA,");
	query.append("TO_CHAR(EC.DATA_INSERIMENTO_RICHIESTA,'DD/MM/YYYY')");
	query.append(" AS DATA,");
	query.append("EC.DESCRIZIONE_ETICHETTA,");
	query.append("CT.DESCRIZIONE AS DESC_STATO_ATTUALE,");
	query.append("EC.STATO_ATTUALE,");
	query.append("EC.ANAGRAFICA_UTENTE,");
	query.append("EC.ANAGRAFICA_TECNICO,");
	query.append("EC.ANAGRAFICA_PROPRIETARIO, ");
	query.append("(CASE WHEN DF.FATTURARE='U' THEN (select cognome_ragione_sociale from anagrafica where id_anagrafica=EC.anagrafica_utente) "+
	 		  " WHEN DF.FATTURARE='P' THEN (select cognome_ragione_sociale from anagrafica where id_anagrafica=EC.anagrafica_proprietario)  "+
	 		  " WHEN DF.FATTURARE='T' THEN (select concat(cognome_ragione_sociale,' ',nome) from anagrafica where id_anagrafica=EC.anagrafica_tecnico)  "+
	 		  " WHEN DF.FATTURARE='O' THEN (  "+
	                "CASE WHEN EC.anagrafica_tecnico IS NULL "+
	                      "THEN (select op.ragione_sociale from anagrafica a  "+
					            	"left join organizzazione_professionale op on a.id_organizzazione = op.id_organizzazione "+
	          				        "where a.id_anagrafica=ec.anagrafica_utente) "+
	                "ELSE (select op.ragione_sociale from anagrafica a "+
				            "left join organizzazione_professionale op on a.id_organizzazione = op.id_organizzazione "+
	  				        "where a.id_anagrafica=ec.anagrafica_tecnico)    "+
	                "END) "+
			   "WHEN DF.FATTURARE='A' THEN DF.ragione_sociale END) as DENOMINAZIONE, ");
	query.append("EC.PAGAMENTO,DF.CODICE_DESTINATARIO,DF.PEC,DF.FATTURA_SN, ");
	query.append("(with elenco_iuv as (select distinct FIRST_VALUE(iuv) " + 
			"    OVER( PARTITION BY ID_RICHIESTA ORDER BY data_annullamento desc) iuv,ID_RICHIESTA from PAGAMENTI) " + 
			"select case when p.data_annullamento is null then p.iuv else '' end iuv " + 
			"from pagamenti p " + 
			"  JOIN elenco_iuv ei ON p.id_richiesta = ei.id_richiesta and p.iuv = ei.iuv " + 
			"where p.id_richiesta = ec.id_richiesta),");
	query.append("(with elenco_iuv as ( select distinct FIRST_VALUE(iuv)  " + 
			"    OVER( PARTITION BY ID_RICHIESTA ORDER BY data_annullamento desc) iuv,ID_RICHIESTA from PAGAMENTI) " + 
			"select  TP.COD_TIPO_PAGAMENTO " + 
			"from pagamenti p " + 
			"  JOIN elenco_iuv ei ON p.id_richiesta = ei.id_richiesta and p.iuv = ei.iuv " + 
			"  LEFT JOIN tipo_pagamento TP ON P.ID_TIPO_PAGAMENTO = TP.ID_TIPO_PAGAMENTO " + 
			"where p.id_richiesta = ec.id_richiesta),");
	query.append("(with elenco_iuv as (select distinct FIRST_VALUE(iuv) " + 
					"    OVER( PARTITION BY ID_RICHIESTA ORDER BY data_annullamento desc) iuv,ID_RICHIESTA from PAGAMENTI) " + 
					"select p.data_annullamento " + 
					"from pagamenti p " + 
					"  JOIN elenco_iuv ei ON p.id_richiesta = ei.id_richiesta and p.iuv = ei.iuv " + 
					"where p.id_richiesta = ec.id_richiesta),");
	query.append("(WITH traccia AS (SELECT max(id_richiesta) id_richiesta,max(progressivo) progressivo "
			+ "FROM tracciatura group by id_richiesta )\r\n" + 
			"SELECT p.codice " + 
			"FROM tracciatura p " + 
			"JOIN traccia ei ON p.id_richiesta = ei.id_richiesta AND p.progressivo = ei.progressivo " + 
			"WHERE p.id_richiesta = ec.id_richiesta), ");
	query.append("EC.CODICE_MATERIALE ");
	query.append(" FROM ETICHETTA_CAMPIONE EC ");
	query.append(" LEFT JOIN DATI_FATTURA DF on EC.ID_RICHIESTA = DF.ID_RICHIESTA ");
	query.append(" LEFT JOIN ANAGRAFICA A on (EC.ANAGRAFICA_UTENTE=A.ID_ANAGRAFICA OR EC.ANAGRAFICA_TECNICO = A.ID_ANAGRAFICA) ");
	query.append("WHERE EC.STATO_ATTUALE <> '00' and A.ID_ORGANIZZAZIONE = ");
	query.append(this.getUtente().getIdOrganizzazione());
	query.append(filtro);
	query.append("group by EC.ID_RICHIESTA,DF.FATTURARE,DF.ragione_sociale,EC.CODICE_MATERIALE ");
	query.append("ORDER BY ec.ID_RICHIESTA DESC ");
	if(iuv!=null) {
		query.append(" IUV:'").append(iuv).append("'");
	}
	
	CuneoLogger.debug(this,"queryElencoPagamentiTecnico "+query.toString());
	this.setQueryCountRicerca(queryCount.toString());
	this.setQueryRicerca(query.toString());
	}

  private String impostaCriteriRicercaPagamenti(String idRichiestaDa,
          String idRichiestaA,
          String dataDa,
          String dataA,
          String tipoMateriale,
          String codiceFiscale,
          String cognome,
          String nome,
          String etichetta,
          String iuv,
          String statoPagamento){
	StringBuffer filtro=new StringBuffer(" ");
	if (idRichiestaDa!=null)
		filtro.append(" AND EC.ID_RICHIESTA >= ").append(idRichiestaDa);
	if (idRichiestaA!=null)
		filtro.append(" AND EC.ID_RICHIESTA <= ").append(idRichiestaA);
	if (dataDa!=null){
		filtro.append(" AND EC.DATA_INSERIMENTO_RICHIESTA >= to_timestamp('");
		filtro.append(dataDa).append("','DD/MM/YYYY') ");
	}
	if (dataA!=null){
		filtro.append(" AND EC.DATA_INSERIMENTO_RICHIESTA <= to_timestamp('");
		filtro.append(dataA).append("','DD/MM/YYYY') ");
	}
	if (tipoMateriale!=null){
		filtro.append(" AND EC.CODICE_MATERIALE = '").append(tipoMateriale);
		filtro.append("' ");
	}
	if (codiceFiscale!=null){
		filtro.setCharAt(0,'1');
		filtro.append(" AND A.CODICE_IDENTIFICATIVO = '").append(codiceFiscale);
		filtro.append("' ");
	}
	if (cognome!=null){
		filtro.setCharAt(0,'1');
		filtro.append(" AND UPPER(A.COGNOME_RAGIONE_SOCIALE) LIKE UPPER('%");
		filtro.append(Utili.toVarchar(cognome.trim())).append("%') ");
	}
	if (nome!=null){
		filtro.setCharAt(0,'1');
		filtro.append(" AND UPPER(A.NOME) LIKE UPPER('%");
		filtro.append(Utili.toVarchar(nome.trim())).append("%') ");
	}
	if (etichetta!=null){
		filtro.append(" AND UPPER(EC.DESCRIZIONE_ETICHETTA) LIKE UPPER('%");
		filtro.append(Utili.toVarchar(etichetta.trim())).append("%') ");
	}
	if(statoPagamento!=null) {
		filtro.setCharAt(0,'1');
		filtro.append(" AND UPPER(EC.PAGAMENTO) = UPPER('").append(statoPagamento).append("') ");
	}
	filtro.append(" ");
	return filtro.toString();
	}

  public void setIdRichiestaCorrente(long idRichiestaCorrente)
  {
    this.idRichiestaCorrente = idRichiestaCorrente;
  }

  public long getIdRichiestaCorrente()
  {
    return idRichiestaCorrente;
  }
  public void setFase(byte fase)
  {
    this.fase = fase;
  }
  public byte getFase()
  {
    return fase;
  }
  public void setCoordinateGeografiche(boolean coordinateGeografiche)
  {
    this.coordinateGeografiche = coordinateGeografiche;
  }
  public boolean isCoordinateGeografiche()
  {
    return coordinateGeografiche;
  }
  public void setCodMateriale(String codMateriale)
  {
    this.codMateriale = codMateriale;
  }
  public String getCodMateriale()
  {
    return codMateriale;
  }
  public void setSpedizioneFattura(boolean spedizioneFattura)
  {
    this.spedizioneFattura = spedizioneFattura;
  }
  public boolean isSpedizioneFattura()
  {
    return spedizioneFattura;
  }
  public void setCostoAnalisi(double costoAnalisi) {
    this.costoAnalisi = costoAnalisi;
  }
  public double getCostoAnalisi() {
    return costoAnalisi;
  }
  public String getComuneRichiesta()
  {
    return comuneRichiesta;
  }
  public void setComuneRichiesta(String comuneRichiesta)
  {
    this.comuneRichiesta = comuneRichiesta;
  }
  public String getIstatComuneRichiesta()
  {
    return istatComuneRichiesta;
  }
  public void setIstatComuneRichiesta(String istatComuneRichiesta)
  {
    this.istatComuneRichiesta = istatComuneRichiesta;
  }
  public Throwable getEccezione()
  {
    return eccezione;
  }
  public void setEccezione(Throwable eccezione)
  {
    this.eccezione = eccezione;
  }
  public boolean isDatiControllati()
  {
    return datiControllati;
  }
  public void setDatiControllati(boolean datiControllati)
  {
    this.datiControllati = datiControllati;
  }

  public void setPA(boolean PA)
  {
    this.PA = PA;
  }
  public boolean isPA()
  {
    return PA;
  }

	public boolean isPiemonte()
	{
		return piemonte;
	}

	public void setPiemonte(boolean piemonte)
	{
		this.piemonte = piemonte;
	}

	public boolean isSpid() {
		return spid;
	}

	public void setSpid(boolean spid) {
		this.spid = spid;
	}

	public String getCod_fiscale_pagatore() {
		return cod_fiscale_pagatore;
	}

	public void setCod_fiscale_pagatore(String cod_fiscale_pagatore) {
		this.cod_fiscale_pagatore = cod_fiscale_pagatore;
	}

	public String getAvvisoControlliPagamenti() {
		return avvisoControlliPagamenti;
	}

	public void setAvvisoControlliPagamenti(String avvisoControlliPagamenti) {
		this.avvisoControlliPagamenti = avvisoControlliPagamenti;
	}

	public String getIdRichiestaChecked() {
		return idRichiestaChecked;
	}

	public void setIdRichiestaChecked(String idRichiestaChecked) {
		this.idRichiestaChecked = idRichiestaChecked;
	}


}