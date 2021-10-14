package it.csi.agrc;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import javax.xml.datatype.XMLGregorianCalendar;

import it.csi.cuneo.BeanDataSource;
import it.csi.cuneo.CuneoLogger;

public class Pagamento extends BeanDataSource
{
	private final String CLASS_NAME = "Pagamento";
	
	public Pagamento()
	{}
  
  public Pagamento(Object dataSource, Autenticazione aut)
  {
    this.setDataSource(dataSource);
    this.setAut(aut);
  }

  private String operazione;
  private String cf;
  private String ruolo;
  private String liv;
  private String idProcedimento_accesso;
  private String idProcedimento;
  private String idFruitore;
  private String codicePagamento;
  private String codiceTracciatura;
  private String importo;
  private String pagatore_idAnagraficaAzienda;
  private String pagatore_nome;
  private String pagatore_cognome;
  private String pagatore_codiceFiscale;
  private String pagatore_ragioneSociale;
  private String pagatore_idUnicoPagatore;
  private String pagatore_piva;
  private String pagatore_email;
  private String pagatore_pec;
  private String versante_nome;
  private String versante_cognome;
  private String versante_codiceFiscale;
  private String versante_ragioneSociale;
  private String versante_idUnicoPagatore;
  private String versante_piva;
  private String versante_email;
  private String versante_pec;
  private String scelta_pagatore_idAnagraficaAzienda;
  private String scelta_pagatore_nome;
  private String scelta_pagatore_cognome;
  private String scelta_pagatore_codiceFiscale;
  private String scelta_pagatore_ragioneSociale;
  private String scelta_pagatore_idUnicoPagatore;
  private String scelta_pagatore_piva;
  private String scelta_pagatore_email;
  private String scelta_pagatore_pec;
  private String scelta_versante_nome;
  private String scelta_versante_cognome;
  private String scelta_versante_codiceFiscale;
  private String scelta_versante_ragioneSociale;
  private String scelta_versante_idUnicoPagatore;
  private String scelta_versante_piva;
  private String scelta_versante_email;
  private String scelta_versante_pec; 
  private String tipoPagamento;
  private String iuv;
  private String esito;
  private String idRichiesta;
  //ricerca pagamenti
  private int passo = 30;
  private int baseElementi;
  private int numRecord;
  private String codiceMateriale;
  private String statoAttuale;
  public String conteggioFatture;
  public String numeroFattura;
  public String annoFattura;
  private String dataInserimentoRichiesta;
  private String descMateriale;
  private String descrizioneEtichetta;
  private String descStatoAttuale;
  private String proprietario;
  private String richiedente;
  private String denominazione;
  private String statoPagamento;
  private Vector<Pagamento> pagamenti = new Vector<Pagamento>();
  private Date dataPagamento;
private String dataAnnullamento;
private String fatturaRichiestaSN;
private String idRichiesteMultiple;
private String pec;
private String codDestinatario;
  
  //select
  public Pagamento(String importo,
		  String fattura_richiesta,
		  String pagatore_idAnagraficaAzienda,
		  String pagatore_nome,
		  String pagatore_cognome,
		  String pagatore_codiceFiscale,
		  String pagatore_ragioneSociale,
		  String pagatore_idUnicoPagatore,
		  String pagatore_piva,
		  String pagatore_email,
		  String pagatore_pec,
		  String versante_nome,
		  String versante_cognome,
		  String versante_codiceFiscale,
		  String versante_ragioneSociale,
		  String versante_idUnicoPagatore,
		  String versante_piva,
		  String versante_email,
		  String versante_pec   ) {
	  this.importo = importo;
	  this.pagatore_idAnagraficaAzienda=pagatore_idAnagraficaAzienda;
	  this.fatturaRichiestaSN = fattura_richiesta;
	  //pagatore
	  this.pagatore_nome=pagatore_nome;
	  this.pagatore_cognome=pagatore_cognome;
	  this.pagatore_codiceFiscale=pagatore_codiceFiscale;
	  this.pagatore_ragioneSociale=pagatore_ragioneSociale;
	  this.pagatore_idUnicoPagatore=pagatore_idUnicoPagatore;
	  this.pagatore_piva=pagatore_piva;
	  this.pagatore_email=pagatore_email;
	  this.pagatore_pec=pagatore_pec;
	  //versante
	  this.versante_nome=versante_nome;
	  this.versante_cognome=versante_cognome;
	  this.versante_codiceFiscale=versante_codiceFiscale;
	  this.versante_ragioneSociale=versante_ragioneSociale;
	  this.versante_idUnicoPagatore=versante_idUnicoPagatore;
	  this.versante_piva=versante_piva;
	  this.versante_email=versante_email;
	  this.versante_pec=versante_pec;
	  
  }
  //selectMultiploCompleto
  public Pagamento(String importo,
		  String fattura_richiesta,
		  String pagatore_idAnagraficaAzienda,
		  String pagatore_nome,
		  String pagatore_cognome,
		  String pagatore_codiceFiscale,
		  String pagatore_ragioneSociale,
		  String pagatore_idUnicoPagatore,
		  String pagatore_piva,
		  String pagatore_email,
		  String pagatore_pec,
		  boolean XXXX, //serve solo per differenziare e separare
		  String scelta_pagatore_nome,
		  String scelta_pagatore_cognome,
		  String scelta_pagatore_codiceFiscale,
		  String scelta_pagatore_idUnicoPagatore,
		  String scelta_pagatore_piva,
		  String scelta_pagatore_email,
		  String scelta_pagatore_pec,
		  String scelta_pagatore_ragioneSociale) {
	  this.importo = importo;
	  this.pagatore_idAnagraficaAzienda=pagatore_idAnagraficaAzienda;
	  this.fatturaRichiestaSN = fattura_richiesta;
	  //pagatore
	  this.pagatore_nome=pagatore_nome;
	  this.pagatore_cognome=pagatore_cognome;
	  this.pagatore_codiceFiscale=pagatore_codiceFiscale;
	  this.pagatore_ragioneSociale=pagatore_ragioneSociale;
	  this.pagatore_idUnicoPagatore=pagatore_idUnicoPagatore;
	  this.pagatore_piva=pagatore_piva;
	  this.pagatore_email=pagatore_email;
	  this.pagatore_pec=pagatore_pec;
	  //versante
	  this.versante_nome=pagatore_nome;
	  this.versante_cognome=pagatore_cognome;
	  this.versante_codiceFiscale=pagatore_codiceFiscale;
	  this.versante_ragioneSociale=pagatore_ragioneSociale;
	  this.versante_idUnicoPagatore=pagatore_idUnicoPagatore;
	  this.versante_piva=pagatore_piva;
	  this.versante_email=pagatore_email;
	  this.versante_pec=pagatore_pec;
	  //proprietario
	//pagatore
	  this.scelta_pagatore_nome=scelta_pagatore_nome;
	  this.scelta_pagatore_cognome=scelta_pagatore_cognome;
	  this.scelta_pagatore_codiceFiscale=scelta_pagatore_codiceFiscale;
	  this.scelta_pagatore_idUnicoPagatore=scelta_pagatore_idUnicoPagatore;
	  this.scelta_pagatore_piva=scelta_pagatore_piva;
	  this.scelta_pagatore_email=scelta_pagatore_email;
	  this.scelta_pagatore_pec=scelta_pagatore_pec;
	  this.scelta_pagatore_ragioneSociale=scelta_pagatore_ragioneSociale;
	  //versante
	  this.scelta_versante_nome=scelta_pagatore_nome;
	  this.scelta_versante_cognome=scelta_pagatore_cognome;
	  this.scelta_versante_codiceFiscale=scelta_pagatore_codiceFiscale;
	  this.scelta_versante_idUnicoPagatore=scelta_pagatore_idUnicoPagatore;
	  this.scelta_versante_piva=scelta_pagatore_piva;
	  this.scelta_versante_email=scelta_pagatore_email;
	  this.scelta_versante_pec=scelta_pagatore_pec;
	  this.scelta_versante_ragioneSociale=scelta_pagatore_ragioneSociale;
  }
  //selectPagamento
  public Pagamento(String idRichiesta,
		  String iuv,
		  String cod_fisc) {
	  this.idRichiesta = idRichiesta;
	  this.iuv=iuv;
	  this.cf=cod_fisc;
  }
  
  public Pagamento(Pagamento p) {
	  this.importo = p.getImporto()!=null?p.getImporto():"";
	  this.pagatore_idAnagraficaAzienda=p.getPagatore_idAnagraficaAzienda()!=null?p.getPagatore_idAnagraficaAzienda():"";
	  //pagatore
	  this.pagatore_nome=p.getPagatore_nome()!=null?p.getPagatore_nome():"";
	  this.pagatore_cognome=p.getPagatore_cognome()!=null?p.getPagatore_cognome():"";
	  this.pagatore_codiceFiscale=p.getPagatore_codiceFiscale()!=null?p.getPagatore_codiceFiscale():"";
	  this.pagatore_ragioneSociale=p.getPagatore_ragioneSociale()!=null?p.getPagatore_ragioneSociale():"";
	  this.pagatore_idUnicoPagatore=p.getPagatore_idUnicoPagatore()!=null?p.getPagatore_idUnicoPagatore():"";
	  this.pagatore_piva=p.getPagatore_piva()!=null?p.getPagatore_piva():"";
	  this.pagatore_email=p.getPagatore_email()!=null?p.getPagatore_email():"";
	  this.pagatore_pec=p.getPagatore_pec()!=null?p.getPagatore_pec():"";
	  //versante
	  this.versante_nome=p.getVersante_nome()!=null?p.getVersante_nome():"";
	  this.versante_cognome=p.getVersante_cognome()!=null?p.getVersante_cognome():"";
	  this.versante_codiceFiscale=p.getVersante_codiceFiscale()!=null?p.getVersante_codiceFiscale():"";
	  this.versante_ragioneSociale=p.getVersante_ragioneSociale()!=null?p.getVersante_ragioneSociale():"";
	  this.versante_idUnicoPagatore=p.getVersante_idUnicoPagatore()!=null?p.getVersante_idUnicoPagatore():"";
	  this.versante_piva=p.getVersante_piva()!=null?p.getVersante_piva():"";
	  this.versante_email=p.getVersante_email()!=null?p.getVersante_email():"";
	  this.versante_pec=p.getVersante_pec()!=null?p.getVersante_pec():"";
  }
  
  public Pagamento(String ID_RICHIESTA, 
			String DATA, 
			String DESCRIZIONE_ETICHETTA, 
			String STATO_ATTUALE, 
			String proprietario, 
			String richiedente, 
			String DENOMINAZIONE,
			String IUV,
			String PAGAMENTO,
			String COD_TIPO_PAGAMENTO,
			String CODICE_DESTINATARIO,
			String PEC,
			String FATTURA_SN,
			String DATA_ANNULLAMENTO,
			String CODICE_TRACCIATURA,
			String CODICE_MATERIALE) {
			// TODO Auto-generated constructor stub
	  this.idRichiesta = ID_RICHIESTA;
	  this.dataInserimentoRichiesta = DATA;
	  this.descrizioneEtichetta = DESCRIZIONE_ETICHETTA;
	  this.statoAttuale = STATO_ATTUALE;
	  this.proprietario = proprietario;
	  this.richiedente = richiedente;
	  this.statoPagamento = PAGAMENTO;
	  this.denominazione = DENOMINAZIONE;
	  this.iuv = IUV;
	  this.tipoPagamento = COD_TIPO_PAGAMENTO;
	  this.pec = PEC;
	  this.codDestinatario = CODICE_DESTINATARIO;
	  this.fatturaRichiestaSN = FATTURA_SN;
	  this.dataAnnullamento = DATA_ANNULLAMENTO;
	  this.codiceTracciatura = CODICE_TRACCIATURA;
	  this.codiceMateriale = CODICE_MATERIALE;
		}

public String toString() {
	  return "cf "+cf
			  +" importo "+importo
			  +" pagatore_idAnagraficaAzienda "+ pagatore_idAnagraficaAzienda
			  +" pagatore_nome "+pagatore_nome
			  +" pagatore_codiceFiscale "+ pagatore_codiceFiscale
			  +" pagatore_ragioneSociale "+pagatore_ragioneSociale
			  +" pagatore_idUnicoPagatore "+pagatore_idUnicoPagatore
			  +" pagatore_piva "+pagatore_piva
			  +" pagatore_email "+pagatore_email
			  +" pagatore_pec "+pagatore_pec
			  +" versante_nome "+versante_nome
			  +" versante_cognome "+versante_cognome
			  +" versante_codiceFiscale "+versante_codiceFiscale
			  +" versante_ragioneSociale "+versante_ragioneSociale
			  +" versante_idUnicoPagatore "+versante_idUnicoPagatore
			  +" versante_email "+versante_email
			  +" versante_pec "+versante_pec;
  }
  
  
  public Pagamento select(String idRichiesta) throws Exception, SQLException {
	  String method = CLASS_NAME + " - select - ";
    if (!isConnection())
    	throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn=null;
    StringBuffer query=new StringBuffer("");
    Pagamento p = null;
    try{
      conn=getConnection();
      Statement stmt = conn.createStatement();
      query = new StringBuffer("SELECT EC.ID_RICHIESTA as NUMERO_RICHIESTA, ");
	  query.append("DF.FATTURA_SN as FATTURA_RICHIESTA, ");
	  query.append("AN1.email as EMAIL_UTE, AN1.cognome_ragione_sociale as COG_UTE, AN1.nome as NOM_UTE , AN1.codice_identificativo as CFPIVA_UTE, AN1.tipo_persona as TIPO_PERS_UTE, ");
	  query.append("AN2.email as EMAIL_TEC, AN2.cognome_ragione_sociale as COG_TEC, AN2.nome as NOM_TEC , AN2.codice_identificativo as CFPIVA_TEC, AN2.tipo_persona as TIPO_PERS_TEC, ");
	  query.append("AN3.email as EMAIL_PROP, AN3.cognome_ragione_sociale as COG_PROP, AN3.nome as NOM_PROP , AN3.codice_identificativo as CFPIVA_PROP, AN3.tipo_persona as TIPO_PERS_PROP, "); 
	  query.append("ec.anagrafica_tecnico , OP1.cf_partita_iva as CFPIVA_ORG_UTE, OP2.cf_partita_iva as CFPIVA_ORG_TEC, DF.cf_partita_iva as CFPIVA_AE, ");
	  query.append("op1.ragione_sociale as DEN_ORG_UTE, op2.ragione_sociale as DEN_ORG_TEC, DF.ragione_sociale as DEN_ORG_AE, ");
	  query.append("DF.codice_destinatario as COD_DEST,  ");
	  query.append("DF.pec as PEC_AN , DF.FATTURA_SN, DF.FATTURARE as INTESTAZ_FATTURA, ");
	  query.append("SUM(AR.COSTO_ANALISI) as COSTO_ANALISI ");
	  query.append("FROM ETICHETTA_CAMPIONE EC ");
	  query.append("LEFT JOIN ANAGRAFICA AN1 ON AN1.ID_ANAGRAFICA = EC.ANAGRAFICA_UTENTE ");
	  query.append("LEFT JOIN ANAGRAFICA AN2 ON AN2.ID_ANAGRAFICA = EC.ANAGRAFICA_TECNICO ");
	  query.append("LEFT JOIN ANAGRAFICA AN3 ON AN3.ID_ANAGRAFICA = EC.ANAGRAFICA_PROPRIETARIO ");
	  query.append("LEFT JOIN ORGANIZZAZIONE_PROFESSIONALE OP1 ON OP1.ID_ORGANIZZAZIONE = AN1.ID_ORGANIZZAZIONE ");
	  query.append("LEFT JOIN ORGANIZZAZIONE_PROFESSIONALE OP2 ON OP2.ID_ORGANIZZAZIONE = AN2.ID_ORGANIZZAZIONE ");
	  query.append("LEFT JOIN DATI_FATTURA DF ON DF.ID_RICHIESTA = EC.ID_RICHIESTA ");
	  query.append("left join campione_fatturato CF on DF.id_richiesta = CF.id_richiesta ");
	  query.append("left join analisi_richieste AR on DF.id_richiesta = AR.id_richiesta ");
      query.append("WHERE 1 = 1 ");
      if (idRichiesta != null && !idRichiesta.equals(""))
    	  query.append("and EC.ID_RICHIESTA = "+idRichiesta);
      query.append(" group by NUMERO_RICHIESTA, " + 
      		"EMAIL_UTE,COG_UTE,NOM_UTE,CFPIVA_UTE,TIPO_PERS_UTE, " + 
      		"EMAIL_TEC,COG_TEC,NOM_TEC,CFPIVA_TEC,TIPO_PERS_TEC, " + 
      		"EMAIL_PROP,COG_PROP,NOM_PROP,CFPIVA_PROP,TIPO_PERS_PROP, " +
      		"CFPIVA_ORG_UTE, CFPIVA_ORG_TEC, CFPIVA_AE, "+
      		"DEN_ORG_UTE, DEN_ORG_TEC, DEN_ORG_AE, "+
      		"COD_DEST,PEC_AN, " + 
      		"DF.FATTURA_SN,INTESTAZ_FATTURA ");//il 12 -> COSTO_ANALISI
      CuneoLogger.debug(method,query.toString());
      ResultSet rset = stmt.executeQuery(query.toString());
      
      if (rset.next()){
    	  String email="";
    	  String nome="";
    	  String cognome="";
    	  String rag_soc="";
    	  String p_iva="";
    	  String cod_fisc="";
    	  String unico_pagatore = "";
    	  String[] a;
    	  String fattura_richiesta=rset.getString("FATTURA_RICHIESTA");
    	  if(rset.getString("FATTURA_RICHIESTA").equals("N")) {
    		  CuneoLogger.debug(method,"FATTURA_RICHIESTA = N");
    		  if(this.getAut().getUtente().getTipoUtente()=='P') {
    			  email = rset.getString("EMAIL_UTE");
	    		  cognome = rset.getString("COG_UTE");
	    		  nome = rset.getString("NOM_UTE");
	    		  unico_pagatore = rset.getString("TIPO_PERS_UTE");
	    		  if(rset.getString("CFPIVA_UTE").length()>11)  
	        		  cod_fisc = rset.getString("CFPIVA_UTE");
	        	  else
	        		  p_iva = rset.getString("CFPIVA_UTE");
    		  }else if(this.getAut().getUtente().getTipoUtente()=='T' || this.getAut().getUtente().getTipoUtente()=='L'){
    			  email = rset.getString("EMAIL_PROP");
        		  if(rset.getString("CFPIVA_PROP").length()>11) {
        			  CuneoLogger.debug(method,"CFPIVA_PROP > 11");
        			  cod_fisc = rset.getString("CFPIVA_PROP");
        			  cognome = rset.getString("COG_PROP");
    	    		  nome = rset.getString("NOM_PROP");
        			  unico_pagatore = "F";
        		  }else {
        			  CuneoLogger.debug(method,"CFPIVA_PROP < 11");
        			  p_iva = rset.getString("CFPIVA_PROP");
        			  rag_soc = rset.getString("COG_PROP");
        			  unico_pagatore = "G";
        		  }
    		  }else {
	    		  email = rset.getString("EMAIL_PROP");
	    		  cognome = rset.getString("COG_PROP");
	    		  nome = rset.getString("NOM_PROP");
	    		  unico_pagatore = rset.getString("TIPO_PERS_PROP");
	    		  if(rset.getString("CFPIVA_PROP").length()>11)  
	    			  cod_fisc = rset.getString("CFPIVA_PROP");
	    		  else
	    			  p_iva = rset.getString("CFPIVA_PROP");
    		  }
    	  //Utente
    	  }else if(rset.getString("INTESTAZ_FATTURA").equals("U")) {
    		  CuneoLogger.debug(method,"INTESTAZ_FATTURA = U");
    		  email = rset.getString("EMAIL_UTE");
    		  cognome = rset.getString("COG_UTE");
    		  nome = rset.getString("NOM_UTE");
    		  unico_pagatore = rset.getString("TIPO_PERS_UTE");
    		  if(rset.getString("TIPO_PERS_UTE").equals("F")) 
        		  cod_fisc = rset.getString("CFPIVA_UTE");
        	  else if(rset.getString("TIPO_PERS_UTE").equals("G")) 
        		  p_iva = rset.getString("CFPIVA_UTE");
    	  //Tecnico
    	  }else if (rset.getString("INTESTAZ_FATTURA").equals("T")) {
    		  CuneoLogger.debug(method,"INTESTAZ_FATTURA = T");
    		  email = rset.getString("EMAIL_TEC");
    		  cognome = rset.getString("COG_TEC");
    		  nome = rset.getString("NOM_TEC");
    		  unico_pagatore = rset.getString("TIPO_PERS_TEC");
    		  if(rset.getString("TIPO_PERS_TEC").equals("F")) 
        		  cod_fisc = rset.getString("CFPIVA_TEC");
        	  else if(rset.getString("TIPO_PERS_TEC").equals("G")) 
        		  p_iva = rset.getString("CFPIVA_TEC");
    	  //Proprietario	  
    	  }else if(rset.getString("INTESTAZ_FATTURA").equals("P")) {
    		  CuneoLogger.debug(method,"INTESTAZ_FATTURA = P");
    		  unico_pagatore = rset.getString("TIPO_PERS_PROP");
    		  email = rset.getString("EMAIL_PROP");
    		  if(rset.getString("TIPO_PERS_PROP").equals("F")) {
	    		  cognome = rset.getString("COG_PROP");
	    		  nome = rset.getString("NOM_PROP");
        		  cod_fisc = rset.getString("CFPIVA_PROP");
    		  }else if(rset.getString("TIPO_PERS_PROP").equals("G")) {
    			  rag_soc = rset.getString("COG_PROP");
        		  p_iva = rset.getString("CFPIVA_PROP");
    		  }
    	  //Organizzazione
    	  }else if(rset.getString("INTESTAZ_FATTURA").equals("O")) {
    		  CuneoLogger.debug(method,"INTESTAZ_FATTURA = O");
    		  email = rset.getString("EMAIL_PROP");
    		  unico_pagatore = "G";
    		  if(rset.getString("anagrafica_tecnico")==null||rset.getString("anagrafica_tecnico").equals("")) {
    			  CuneoLogger.debug(method,"anagrafica_tecnico null");
    			  rag_soc = rset.getString("DEN_ORG_UTE");
    			  p_iva = rset.getString("CFPIVA_ORG_UTE");
    		  }else {
    			  CuneoLogger.debug(method,"anagrafica_tecnico not null");
    			  rag_soc = rset.getString("DEN_ORG_TEC");
    			  p_iva = rset.getString("CFPIVA_ORG_TEC");
    		  }
    	  //Altri Estremi
    	  }else if(rset.getString("INTESTAZ_FATTURA").equals("A")) {
    		  CuneoLogger.debug(method,"INTESTAZ_FATTURA = A");
    		  email = rset.getString("EMAIL_PROP");
    		  if(rset.getString("CFPIVA_AE").length()>11) {
    			  CuneoLogger.debug(method,"CFPIVA_AE > 11");
    			  cod_fisc = rset.getString("CFPIVA_AE");
    			  a = rset.getString("DEN_ORG_AE").split(" ");
        		  cognome = a[0];
        		  nome = a[1];
    			  unico_pagatore = "F";
    		  }else {
    			  CuneoLogger.debug(method,"CFPIVA_AE < 11");
    			  p_iva = rset.getString("CFPIVA_AE");
    			  rag_soc = rset.getString("DEN_ORG_AE");
    			  unico_pagatore = "G";
    		  }
    	  }
    	  CuneoLogger.debug(method,"END Dati Pagamento -> costruttore oggetto Pagamento ");
    	  p = new Pagamento(rset.getString("COSTO_ANALISI"),fattura_richiesta,
    		//pagatore
			  "",//rset.getString("pagatore_idAnagraficaAzienda"),
			  nome,
			  cognome,
			  cod_fisc,
			  rag_soc,
			  unico_pagatore,
			  p_iva,
			  email,
			  rset.getString("PEC_AN"),
			//versante
			  nome,
			  cognome,
			  cod_fisc,
			  rag_soc,
			  unico_pagatore,
			  p_iva,
			  email,
			  rset.getString("PEC_AN"));   
    	  
      }
      rset.close();
      stmt.close();
      //imposto il CF dell'utente per il pagamento
      p.setCf(this.getAut().getUtente().getCodiceFiscale());
      p.setIdRichiesta(idRichiesta);
      CuneoLogger.debug(method, p.toString());
    }catch(java.sql.SQLException ex) {
    	CuneoLogger.error(method,"SQLException - "+ex);
    	this.getAut().setQuery("select della classe Pagamento");
    	this.getAut().setContenutoQuery(query.toString());
    	throw (ex);
    }catch(Exception e){
    	CuneoLogger.error(method,"Exception - "+e);
    	this.getAut().setQuery("select della classe Pagamento"
                            +": non è una SQLException ma una Exception"
                            +" generica");
    	this.getAut().setContenutoQuery(query.toString());
    	throw (e);
    }finally{
    	if (conn!=null) conn.close();
    		return p;
    }
  }
  
  public Pagamento selectMultiplo(String idRichiesta) throws Exception, SQLException {
	  String method = CLASS_NAME + " - selectMultiplo - ";
    if (!isConnection())
    	throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn=null;
    StringBuffer query=new StringBuffer("");
    Pagamento p = null;
    try{
      conn=getConnection();
      Statement stmt = conn.createStatement();
      query = new StringBuffer("SELECT  ");//EC.ID_RICHIESTA as NUMERO_RICHIESTA,
	  query.append("DF.FATTURA_SN as FATTURA_RICHIESTA, ");
	  query.append("AN1.email as EMAIL_UTE, AN1.cognome_ragione_sociale as COG_UTE, AN1.nome as NOM_UTE , AN1.codice_identificativo as CFPIVA_UTE, AN1.tipo_persona as TIPO_PERS_UTE, ");
	  query.append("AN2.email as EMAIL_TEC, AN2.cognome_ragione_sociale as COG_TEC, AN2.nome as NOM_TEC , AN2.codice_identificativo as CFPIVA_TEC, AN2.tipo_persona as TIPO_PERS_TEC, ");
	  query.append("AN3.email as EMAIL_PROP, AN3.cognome_ragione_sociale as COG_PROP, AN3.nome as NOM_PROP , AN3.codice_identificativo as CFPIVA_PROP, AN3.tipo_persona as TIPO_PERS_PROP, "); 
	  query.append("ec.anagrafica_tecnico , OP1.cf_partita_iva as CFPIVA_ORG_UTE, OP2.cf_partita_iva as CFPIVA_ORG_TEC, DF.cf_partita_iva as CFPIVA_AE, ");
	  query.append("op1.ragione_sociale as DEN_ORG_UTE, op2.ragione_sociale as DEN_ORG_TEC, DF.ragione_sociale as DEN_ORG_AE, ");
	  query.append("DF.codice_destinatario as COD_DEST,  ");
	  query.append("DF.pec as PEC_AN , DF.FATTURA_SN, DF.FATTURARE as INTESTAZ_FATTURA, ");
	  query.append("SUM(AR.COSTO_ANALISI) as COSTO_ANALISI ");
	  query.append("FROM ETICHETTA_CAMPIONE EC ");
	  query.append("LEFT JOIN ANAGRAFICA AN1 ON AN1.ID_ANAGRAFICA = EC.ANAGRAFICA_UTENTE ");
	  query.append("LEFT JOIN ANAGRAFICA AN2 ON AN2.ID_ANAGRAFICA = EC.ANAGRAFICA_TECNICO ");
	  query.append("LEFT JOIN ANAGRAFICA AN3 ON AN3.ID_ANAGRAFICA = EC.ANAGRAFICA_PROPRIETARIO ");
	  query.append("LEFT JOIN ORGANIZZAZIONE_PROFESSIONALE OP1 ON OP1.ID_ORGANIZZAZIONE = AN1.ID_ORGANIZZAZIONE ");
	  query.append("LEFT JOIN ORGANIZZAZIONE_PROFESSIONALE OP2 ON OP2.ID_ORGANIZZAZIONE = AN2.ID_ORGANIZZAZIONE ");
	  query.append("LEFT JOIN DATI_FATTURA DF ON DF.ID_RICHIESTA = EC.ID_RICHIESTA ");
	  query.append("left join campione_fatturato CF on DF.id_richiesta = CF.id_richiesta ");
	  query.append("left join analisi_richieste AR on DF.id_richiesta = AR.id_richiesta ");
      query.append("WHERE 1 = 1 ");
      if (idRichiesta != null && !idRichiesta.equals(""))
    	  query.append("and EC.ID_RICHIESTA in ("+idRichiesta+")");
      query.append(" group by  " + //NUMERO_RICHIESTA,
      		"EMAIL_UTE,COG_UTE,NOM_UTE,CFPIVA_UTE,TIPO_PERS_UTE, " + 
      		"EMAIL_TEC,COG_TEC,NOM_TEC,CFPIVA_TEC,TIPO_PERS_TEC, " + 
      		"EMAIL_PROP,COG_PROP,NOM_PROP,CFPIVA_PROP,TIPO_PERS_PROP,anagrafica_tecnico, " +
      		"CFPIVA_ORG_UTE, CFPIVA_ORG_TEC, CFPIVA_AE, "+
      		"DEN_ORG_UTE, DEN_ORG_TEC, DEN_ORG_AE, "+
      		"COD_DEST,PEC_AN, " + 
      		"DF.FATTURA_SN,INTESTAZ_FATTURA ");//il 12 -> COSTO_ANALISI
      CuneoLogger.debug(method,query.toString());
      ResultSet rset = stmt.executeQuery(query.toString());
      if (rset.next()){
    	  String email="";
    	  String nome="";
    	  String cognome="";
    	  String rag_soc="";
    	  String p_iva="";
    	  String cod_fisc="";
    	  String unico_pagatore = "";
    	  String[] a;
    	  String fattura_richiesta = rset.getString("FATTURA_RICHIESTA");
    	  if(rset.getString("FATTURA_RICHIESTA").equals("N")) {
    		  CuneoLogger.debug(method,"FATTURA_RICHIESTA = N");
    		  if(this.getAut().getUtente().getTipoUtente()=='P') {
    			  email = rset.getString("EMAIL_UTE");
	    		  cognome = rset.getString("COG_UTE");
	    		  nome = rset.getString("NOM_UTE");
	    		  unico_pagatore = rset.getString("TIPO_PERS_UTE");
	    		  if(rset.getString("CFPIVA_UTE").length()>11)  
	        		  cod_fisc = rset.getString("CFPIVA_UTE");
	        	  else
	        		  p_iva = rset.getString("CFPIVA_UTE");
    		  }else if(this.getAut().getUtente().getTipoUtente()=='T' || this.getAut().getUtente().getTipoUtente()=='L'){
    			  email = rset.getString("EMAIL_PROP");
        		  if(rset.getString("CFPIVA_PROP").length()>11) {
        			  CuneoLogger.debug(method,"CFPIVA_PROP > 11");
        			  cod_fisc = rset.getString("CFPIVA_PROP");
        			  cognome = rset.getString("COG_PROP");
    	    		  nome = rset.getString("NOM_PROP");
        			  unico_pagatore = "F";
        		  }else {
        			  CuneoLogger.debug(method,"CFPIVA_PROP < 11");
        			  p_iva = rset.getString("CFPIVA_PROP");
        			  rag_soc = rset.getString("COG_PROP");
        			  unico_pagatore = "G";
        		  }
    		  }else {
	    		  email = rset.getString("EMAIL_PROP");
	    		  cognome = rset.getString("COG_PROP");
	    		  nome = rset.getString("NOM_PROP");
	    		  unico_pagatore = rset.getString("TIPO_PERS_PROP");
	    		  if(rset.getString("CFPIVA_PROP").length()>11)  
	    			  cod_fisc = rset.getString("CFPIVA_PROP");
	    		  else
	    			  p_iva = rset.getString("CFPIVA_PROP");
    		  }
    	  //Utente
    	  }else if(rset.getString("INTESTAZ_FATTURA").equals("U")) {
    		  CuneoLogger.debug(method,"INTESTAZ_FATTURA = U");
    		  email = rset.getString("EMAIL_UTE");
    		  cognome = rset.getString("COG_UTE");
    		  nome = rset.getString("NOM_UTE");
    		  unico_pagatore = rset.getString("TIPO_PERS_UTE");
    		  if(rset.getString("TIPO_PERS_UTE").equals("F")) 
        		  cod_fisc = rset.getString("CFPIVA_UTE");
        	  else if(rset.getString("TIPO_PERS_UTE").equals("G")) 
        		  p_iva = rset.getString("CFPIVA_UTE");
    	  //Tecnico
    	  }else if (rset.getString("INTESTAZ_FATTURA").equals("T")) {
    		  CuneoLogger.debug(method,"INTESTAZ_FATTURA = T");
    		  email = rset.getString("EMAIL_TEC");
    		  cognome = rset.getString("COG_TEC");
    		  nome = rset.getString("NOM_TEC");
    		  unico_pagatore = rset.getString("TIPO_PERS_TEC");
    		  if(rset.getString("TIPO_PERS_TEC").equals("F")) 
        		  cod_fisc = rset.getString("CFPIVA_TEC");
        	  else if(rset.getString("TIPO_PERS_TEC").equals("G")) 
        		  p_iva = rset.getString("CFPIVA_TEC");
    	  //Proprietario	  
    	  }else if(rset.getString("INTESTAZ_FATTURA").equals("P")) {
    		  CuneoLogger.debug(method,"INTESTAZ_FATTURA = P");
    		  email = rset.getString("EMAIL_PROP");
    		  cognome = rset.getString("COG_PROP");
    		  nome = rset.getString("NOM_PROP");
    		  unico_pagatore = rset.getString("TIPO_PERS_PROP");
    		  if(rset.getString("TIPO_PERS_PROP").equals("F")) 
        		  cod_fisc = rset.getString("CFPIVA_PROP");
        	  else if(rset.getString("TIPO_PERS_PROP").equals("G")) 
        		  p_iva = rset.getString("CFPIVA_PROP");
    	  //Organizzazione
    	  }else if(rset.getString("INTESTAZ_FATTURA").equals("O")) {
    		  CuneoLogger.debug(method,"INTESTAZ_FATTURA = O");
    		  email = rset.getString("EMAIL_PROP");
    		  unico_pagatore = "G";
    		  if(rset.getString("anagrafica_tecnico")==null||rset.getString("anagrafica_tecnico").equals("")) {
    			  CuneoLogger.debug(method,"anagrafica_tecnico null");
    			  rag_soc = rset.getString("DEN_ORG_UTE");
    			  p_iva = rset.getString("CFPIVA_ORG_UTE");
    		  }else {
    			  CuneoLogger.debug(method,"anagrafica_tecnico not null");
    			  rag_soc = rset.getString("DEN_ORG_TEC");
    			  p_iva = rset.getString("CFPIVA_ORG_TEC");
    		  }
    	  //Altri Estremi
    	  }else if(rset.getString("INTESTAZ_FATTURA").equals("A")) {
    		  CuneoLogger.debug(method,"INTESTAZ_FATTURA = A");
    		  email = rset.getString("EMAIL_PROP");
    		  if(rset.getString("CFPIVA_AE").length()>11) {
    			  CuneoLogger.debug(method,"CFPIVA_AE > 11");
    			  cod_fisc = rset.getString("CFPIVA_AE");
    			  a = rset.getString("DEN_ORG_AE").split(" ");
        		  cognome = a[0];
        		  nome = a[1];
    			  unico_pagatore = "F";
    		  }else {
    			  CuneoLogger.debug(method,"CFPIVA_AE < 11");
    			  p_iva = rset.getString("CFPIVA_AE");
    			  rag_soc = rset.getString("DEN_ORG_AE");
    			  unico_pagatore = "G";
    		  }
    	  }
    	  CuneoLogger.debug(method,"END Dati Pagamento -> costruttore oggetto Pagamento ");
    	  p = new Pagamento(rset.getString("COSTO_ANALISI"),fattura_richiesta,
    		//pagatore
			  "",//rset.getString("pagatore_idAnagraficaAzienda"),
			  nome,
			  cognome,
			  cod_fisc,
			  rag_soc,
			  unico_pagatore,
			  p_iva,
			  email,
			  rset.getString("PEC_AN"),
			//versante
			  nome,
			  cognome,
			  cod_fisc,
			  rag_soc,
			  unico_pagatore,
			  p_iva,
			  email,
			  rset.getString("PEC_AN"));   
    	  
      }
      rset.close();
      stmt.close();
      //imposto il CF dell'utente per il pagamento
      p.setCf(this.getAut().getUtente().getCodiceFiscale());
      p.setIdRichiesta(idRichiesta);
      CuneoLogger.debug(method, p.toString());
    }catch(java.sql.SQLException ex) {
    	CuneoLogger.error(method,"SQLException - "+ex);
    	this.getAut().setQuery("selectMultiplo della classe Pagamento");
    	this.getAut().setContenutoQuery(query.toString());
    	throw (ex);
    }catch(Exception e){
    	CuneoLogger.error(method,"Exception - "+e);
    	this.getAut().setQuery("selectMultiplo della classe Pagamento"
                            +": non è una SQLException ma una Exception"
                            +" generica");
    	this.getAut().setContenutoQuery(query.toString());
    	throw (e);
    }finally{
    	if (conn!=null) conn.close();
    		return p;
    }
  }
  
  public Pagamento selectMultiploCompleto(String idRichiesta, String sceltaPagatore) throws Exception, SQLException {
		String method = CLASS_NAME + " - selectMultiploCompleto - ";
		if (!isConnection())
			throw new Exception("Riferimento a DataSource non inizializzato");
		Connection conn=null;
		StringBuffer query=new StringBuffer("");
		Pagamento p = null;
		String[] array_id_ric = idRichiesta.split(",");
		for(int x=0; x < array_id_ric.length; x++) {
			if(array_id_ric[x].contains("A")) {
				String am = array_id_ric[x].substring(0,array_id_ric[x].length()-1);
				array_id_ric[x] = am;
			}
		}
		try{
			conn=getConnection();
			PreparedStatement stmt = null;
			query = new StringBuffer("SELECT ");
			query.append("DF.FATTURA_SN as FATTURA_RICHIESTA, ");
			query.append("DF.codice_destinatario as COD_DEST, ");
			query.append("DF.pec as PEC_DF, DF.FATTURARE as INTESTAZ_FATTURA, ");
			query.append("SUM(AR.COSTO_ANALISI) as COSTO_ANALISI ");
			query.append("FROM ETICHETTA_CAMPIONE EC ");
			query.append("LEFT JOIN DATI_FATTURA DF ON DF.ID_RICHIESTA = EC.ID_RICHIESTA ");
			query.append("left join campione_fatturato CF on DF.id_richiesta = CF.id_richiesta ");
			query.append("left join analisi_richieste AR on DF.id_richiesta = AR.id_richiesta ");
			query.append("WHERE EC.ID_RICHIESTA in ( ");
			for(int x=0; x<array_id_ric.length; x++) {
				if(x!=(array_id_ric.length)-1)
					query.append(" ?,");
				else
					query.append(" ? )");
			}
			query.append("group by DF.FATTURA_SN, COD_DEST, PEC_DF, INTESTAZ_FATTURA ");
			CuneoLogger.debug(method,query.toString());
			stmt = conn.prepareStatement(query.toString());
			int i = 1;
			for(String x : array_id_ric) {
				stmt.setBigDecimal(i++, x != null && !x.equals("")?new BigDecimal(x):null);
				CuneoLogger.debug(method,"idRichiesta - "+x != null && !x.equals("")?x:"");
			}
			ResultSet rset = stmt.executeQuery();
			boolean continua = true;
			int num = 0;
			String fattura_richiesta = null;
			String codice_destinatario = null;
			String pec = null;
			String intestazione_fattura = null;
			String importo = null;
			while (rset.next()){
				num++;
				if(num>1){
					continua=false;
					break;
				}
				fattura_richiesta = rset.getString("FATTURA_RICHIESTA");
				codice_destinatario = rset.getString("COD_DEST");
				pec = rset.getString("PEC_DF");
				intestazione_fattura = rset.getString("INTESTAZ_FATTURA");
				importo = rset.getString("COSTO_ANALISI");
			}
			rset.close();
			//inizializzo statement e query
			stmt = null;
			query = null;
			String email="";
			String nome="";
			String cognome="";
			String rag_soc="";
			String p_iva="";
			String cod_fisc="";
			String unico_pagatore = "";
			if(continua){
				if(fattura_richiesta.equals("N")) {
					CuneoLogger.debug(method,"FATTURA_RICHIESTA = N");
					if(this.getAut().getUtente().getTipoUtente()=='P') {
						ArrayList<String> res = getQueryAnagraficaUtente(array_id_ric);
						email = res.get(0);
						nome = res.get(1);
						cognome = res.get(2);
						unico_pagatore = res.get(3);
						if(res.get(4).length()>11)
							cod_fisc = res.get(4);
						else
							p_iva = res.get(4);
					}else if(this.getAut().getUtente().getTipoUtente()=='T' || this.getAut().getUtente().getTipoUtente()=='L'){
						ArrayList<String> res = getQueryAnagraficaProprietario(array_id_ric);
						email = res.get(0);
						  if(res.get(1).length()>11) {
							  CuneoLogger.debug(method,"CFPIVA_PROP > 11");
							  cod_fisc = res.get(1);
							  cognome = res.get(2);
							  nome = res.get(3);
							  unico_pagatore = "F";
						  }else {
							  CuneoLogger.debug(method,"CFPIVA_PROP < 11");
							  p_iva = res.get(1);
							  rag_soc = res.get(2);
							  unico_pagatore = "G";
						  }
					}else{
						ArrayList<String> res = getQueryAnagraficaProprietario(array_id_ric);
						email = res.get(0);
						cognome = res.get(2);
						nome = res.get(3);
						unico_pagatore = res.get(4);
						if(res.get(1).length()>11)  
							cod_fisc = res.get(1);
						else
							p_iva = res.get(1);
					  }
				//Utente
				}else if(intestazione_fattura.equals("U")) {
					CuneoLogger.debug(method,"INTESTAZ_FATTURA = U");
					ArrayList<String> res = getQueryAnagraficaUtente(array_id_ric);
					email = res.get(0);
					nome = res.get(1);
					cognome = res.get(2);
					unico_pagatore = res.get(3);
					if(unico_pagatore.equals("F"))
						cod_fisc = res.get(4);
					else if(unico_pagatore.equals("G"))
						p_iva = res.get(4);
				//Tecnico
				}else if (intestazione_fattura.equals("T")) {
					CuneoLogger.debug(method,"INTESTAZ_FATTURA = T");
					ArrayList<String> res = getQueryAnagraficaTecnico(array_id_ric);
					email = res.get(0);
					nome = res.get(1);
					cognome = res.get(2);
					unico_pagatore = res.get(3);
					if(unico_pagatore.equals("F"))
						cod_fisc = res.get(4);
					else if(unico_pagatore.equals("G"))
						p_iva = res.get(4);
				//Proprietario
				}else if (intestazione_fattura.equals("P")) {
					CuneoLogger.debug(method,"INTESTAZ_FATTURA = P");
					ArrayList<String> res = getQueryAnagraficaProprietario(array_id_ric);
					unico_pagatore = res.get(4);
					email = res.get(0);
					if(unico_pagatore.equals("F")) {
					  cognome = res.get(2);
					  nome = res.get(3);
					  cod_fisc = res.get(1);
					}else if(unico_pagatore.equals("G")) {
					  rag_soc = res.get(2);
					  p_iva = res.get(1);
					}
				//Organizzazione
				}else if (intestazione_fattura.equals("O")) {
					CuneoLogger.debug(method,"INTESTAZ_FATTURA = O");
					ArrayList<String> res = getQueryAnagraficaOrganizzazione(array_id_ric);
					email = res.get(0);
					unico_pagatore = "G";
					if(res.get(1)==null||res.get(1).equals("")) {
						CuneoLogger.debug(method,"anagrafica_tecnico null");
						rag_soc = res.get(3);
						p_iva = res.get(2);
					}else{
						CuneoLogger.debug(method,"anagrafica_tecnico not null");
						rag_soc = res.get(5);
						p_iva = res.get(4);
					}
				//Altri Estremi
				}else if (intestazione_fattura.equals("A")) {
					CuneoLogger.debug(method,"INTESTAZ_FATTURA = A");
					ArrayList<String> res = getQueryAnagraficaAltriEstremi(array_id_ric);
					email = res.get(0);
					if(res.get(1).length()>11){
						CuneoLogger.debug(method,"CFPIVA_AE > 11");
						cod_fisc = res.get(1);
						String[] a = res.get(2).split(" ");
						cognome = a[0];
						nome = a[1];
						unico_pagatore = "F";
					}else{
						CuneoLogger.debug(method,"CFPIVA_AE < 11");
						p_iva = res.get(1);
						rag_soc = res.get(2);
						unico_pagatore = "G";
					}
				}
				//Setto comunque tutti i dati per pagatore versante di proprietario, tecnico ed organizzazione
				//nel caso in cui si proceda ad un pagamento multiplo in cui si possa scegliere cosa fare
				String scelta_email="";
				String scelta_nome="";
				String scelta_cognome="";
				String scelta_p_iva="";
				String scelta_cod_fisc="";
				String scelta_unico_pagatore="";
				String scelta_rag_soc="";
				if(sceltaPagatore!=null && sceltaPagatore.equals("Proprietario")) {
					CuneoLogger.debug(method,"SCELTA PAGATORE PROPRIETARIO");
					ArrayList<String> res = getQueryAnagraficaProprietario(array_id_ric);
					scelta_unico_pagatore = res.get(4);
					scelta_email = res.get(0);
					if(scelta_unico_pagatore.equals("F")) {
					  scelta_cognome = res.get(2);
					  scelta_nome = res.get(3);
					  scelta_cod_fisc = res.get(1);
					}else if(scelta_unico_pagatore.equals("G")) {
					  scelta_rag_soc = res.get(2);
					  scelta_p_iva = res.get(1);
					}
				}else if(sceltaPagatore!=null && sceltaPagatore.equals("Tecnico")) {
					CuneoLogger.debug(method,"SCELTA PAGATORE TECNICO");
					ArrayList<String> res = getQueryAnagraficaUtente(array_id_ric);
					scelta_email = res.get(0);
					scelta_nome = res.get(1);
					scelta_cognome = res.get(2);
					scelta_unico_pagatore = res.get(3);
					if(scelta_unico_pagatore.equals("F"))
						scelta_cod_fisc = res.get(4);
					else if(scelta_unico_pagatore.equals("G"))
						scelta_p_iva = res.get(4);
				}else if(sceltaPagatore!=null && sceltaPagatore.equals("Organizzazione")) {	
					CuneoLogger.debug(method,"SCELTA PAGATORE ORGANIZZAZIONE");
					ArrayList<String> res = getQueryAnagraficaOrganizzazione(array_id_ric);
					scelta_email = res.get(0);
					scelta_unico_pagatore = "G";
					if(res.get(1)==null||res.get(1).equals("")) {
						CuneoLogger.debug(method,"anagrafica_tecnico null");
						scelta_rag_soc = res.get(3);
						scelta_p_iva = res.get(2);
					}else{
						CuneoLogger.debug(method,"anagrafica_tecnico not null");
						scelta_rag_soc = res.get(5);
						scelta_p_iva = res.get(4);
					}
				}
				CuneoLogger.debug(method,"END Dati Pagamento -> costruttore oggetto Pagamento ");	
				p = new Pagamento(importo,fattura_richiesta,
						//pagatore
						"",//rset.getString("pagatore_idAnagraficaAzienda"),
						nome,
						cognome,
						cod_fisc,
						rag_soc,
						unico_pagatore,
						p_iva,
						email,
						pec,
						false,
						//pagatore prop
						scelta_nome,
						scelta_cognome,
						scelta_cod_fisc,
						scelta_unico_pagatore,
						scelta_p_iva,
						scelta_email,
						pec,
						scelta_rag_soc); 	
			}
			rset.close();
			//imposto il CF dell'utente per il pagamento
			p.setCf(this.getAut().getUtente().getCodiceFiscale());
			p.setIdRichiesta(idRichiesta);
			CuneoLogger.debug(method, p.toString());
		}catch(java.sql.SQLException ex) {
			CuneoLogger.error(method,"SQLException - "+ex);
			this.getAut().setQuery("selectMultiploCompleto della classe Pagamento");
			this.getAut().setContenutoQuery(query.toString());
			throw (ex);
		}catch(Exception e){
			CuneoLogger.error(method,"Exception - "+e);
			this.getAut().setQuery("selectMultiploCompleto della classe Pagamento"
				  +": non è una SQLException ma una Exception"
				  +" generica");
			this.getAut().setContenutoQuery(query.toString());
			throw (e);
		}finally{
			if (conn!=null) conn.close();
			return p;
		}
	  }

  public ArrayList<String> getQueryAnagraficaUtente(String[] array_id_ric) throws Exception, SQLException {
		String method = CLASS_NAME + " - getQueryAnagraficaUtente - ";
		if (!isConnection())
			throw new Exception("Riferimento a DataSource non inizializzato");
		Connection conn=null;
		StringBuffer query=new StringBuffer("");
		ArrayList<String> output=new ArrayList<String>();
		try{
			conn=getConnection();
			PreparedStatement stmt = null;
			query.append("select distinct AN1.email AS EMAIL_UTE, ");
			query.append("AN1.cognome_ragione_sociale AS COG_UTE, ");
			query.append("AN1.nome AS NOM_UTE, ");
			query.append("AN1.codice_identificativo AS CFPIVA_UTE, ");
			query.append("AN1.tipo_persona AS TIPO_PERS_UTE, ");
			query.append("AN1.PEC AS PEC_A_UTE ");
			query.append("FROM ");
			query.append("ETICHETTA_CAMPIONE EC ");
			query.append("LEFT JOIN ANAGRAFICA AN1 ON AN1.ID_ANAGRAFICA = EC.ANAGRAFICA_UTENTE ");
			query.append("WHERE EC.ID_RICHIESTA IN ( ");
			for(int x=0; x<array_id_ric.length; x++) {
				if(x!=(array_id_ric.length)-1)
					query.append(" ?,");
				else
					query.append(" ? )");
			}
			CuneoLogger.debug(method,"query - "+query.toString());
			stmt = conn.prepareStatement(query.toString());
			int i = 1;
			for(String x : array_id_ric) {
				stmt.setBigDecimal(i++, x != null && !x.equals("")?new BigDecimal(x):null);
				CuneoLogger.debug(method,"idRichiesta - "+x != null && !x.equals("")?x:"");
			}
			ResultSet rset = stmt.executeQuery();
			int num = 0;
			while (rset.next()){
				num++;
				if(num>1){
					break;
				}
				output.add(rset.getString("EMAIL_UTE"));
				output.add(rset.getString("COG_UTE"));
				output.add(rset.getString("NOM_UTE"));
				output.add(rset.getString("TIPO_PERS_UTE"));  
				output.add(rset.getString("CFPIVA_UTE"));
			}
		}catch(java.sql.SQLException ex) {
			CuneoLogger.error(method,"SQLException - "+ex);
			this.getAut().setQuery("getQueryAnagraficaUtente della classe Pagamento");
			this.getAut().setContenutoQuery(query.toString());
			throw (ex);
		}catch(Exception e){
			CuneoLogger.error(method,"Exception - "+e);
			this.getAut().setQuery("getQueryAnagraficaUtente della classe Pagamento"
				  +": non è una SQLException ma una Exception"
				  +" generica");
			this.getAut().setContenutoQuery(query.toString());
			throw (e);
		}finally{
			return output;
		}
	}
  public ArrayList<String> getQueryAnagraficaProprietario(String[] array_id_ric) throws Exception, SQLException {
		String method = CLASS_NAME + " - getQueryAnagraficaProprietario - ";
		if (!isConnection())
			throw new Exception("Riferimento a DataSource non inizializzato");
		Connection conn=null;
		StringBuffer query=new StringBuffer("");
		ArrayList<String> output=new ArrayList<String>();
		try{
			conn=getConnection();
			PreparedStatement stmt = null;
			query.append("select distinct AN3.email AS EMAIL_PROP, ");
			query.append("AN3.cognome_ragione_sociale AS COG_PROP, ");
			query.append("AN3.nome AS NOM_PROP, ");
			query.append("AN3.codice_identificativo AS CFPIVA_PROP, ");
			query.append("AN3.tipo_persona AS TIPO_PERS_PROP, ");
			query.append("AN3.PEC AS PEC_A_PROP ");
			query.append("FROM ");
			query.append("ETICHETTA_CAMPIONE EC ");
			query.append("LEFT JOIN ANAGRAFICA AN3 ON AN3.ID_ANAGRAFICA = EC.ANAGRAFICA_PROPRIETARIO ");
			query.append("WHERE EC.ID_RICHIESTA IN ( ");
			for(int x=0; x<array_id_ric.length; x++) {
				if(x!=(array_id_ric.length)-1)
					query.append(" ?,");
				else
					query.append(" ? )");
			}
			CuneoLogger.debug(method,"query - "+query.toString());
			stmt = conn.prepareStatement(query.toString());
			int i = 1;
			for(String x : array_id_ric) {
				stmt.setBigDecimal(i++, x != null && !x.equals("")?new BigDecimal(x):null);
				CuneoLogger.debug(method,"idRichiesta - "+x != null && !x.equals("")?x:"");
			}
			ResultSet rset = stmt.executeQuery();
			int num = 0;
			while (rset.next()){
				num++;
				if(num>1){
					break;
				}
				output.add(rset.getString("EMAIL_PROP"));
				output.add(rset.getString("CFPIVA_PROP"));
				output.add(rset.getString("COG_PROP"));
				output.add(rset.getString("NOM_PROP"));
				output.add(rset.getString("TIPO_PERS_PROP"));  
			}
		}catch(java.sql.SQLException ex) {
			CuneoLogger.error(method,"SQLException - "+ex);
			this.getAut().setQuery("getQueryAnagraficaProprietario della classe Pagamento");
			this.getAut().setContenutoQuery(query.toString());
			throw (ex);
		}catch(Exception e){
			CuneoLogger.error(method,"Exception - "+e);
			this.getAut().setQuery("getQueryAnagraficaProprietario della classe Pagamento"
				  +": non è una SQLException ma una Exception"
				  +" generica");
			this.getAut().setContenutoQuery(query.toString());
			throw (e);
		}finally{
			return output;
		}
	}
  public ArrayList<String> getQueryAnagraficaTecnico(String[] array_id_ric) throws Exception, SQLException {
		String method = CLASS_NAME + " - getQueryAnagraficaTecnico - ";
		if (!isConnection())
			throw new Exception("Riferimento a DataSource non inizializzato");
		Connection conn=null;
		StringBuffer query=new StringBuffer("");
		ArrayList<String> output=new ArrayList<String>();
		try{
			conn=getConnection();
			PreparedStatement stmt = null;
			query.append("select distinct AN2.email AS EMAIL_TEC, ");
			query.append("AN2.cognome_ragione_sociale AS COG_TEC, ");
			query.append("AN2.nome AS NOM_TEC, ");
			query.append("AN2.codice_identificativo AS CFPIVA_TEC, ");
			query.append("AN2.tipo_persona AS TIPO_PERS_TEC, ");
			query.append("AN2.PEC AS PEC_A_TEC ");
			query.append("FROM ");
			query.append("ETICHETTA_CAMPIONE EC ");
			query.append("LEFT JOIN ANAGRAFICA AN2 ON AN2.ID_ANAGRAFICA = EC.ANAGRAFICA_TECNICO ");
			query.append("WHERE EC.ID_RICHIESTA IN ( ");
			for(int x=0; x<array_id_ric.length; x++) {
				if(x!=(array_id_ric.length)-1)
					query.append(" ?,");
				else
					query.append(" ? )");
			}
			CuneoLogger.debug(method,"query - "+query.toString());
			stmt = conn.prepareStatement(query.toString());
			int i = 1;
			for(String x : array_id_ric) {
				stmt.setBigDecimal(i++, x != null && !x.equals("")?new BigDecimal(x):null);
				CuneoLogger.debug(method,"idRichiesta - "+x != null && !x.equals("")?x:"");
			}
			ResultSet rset = stmt.executeQuery();
			int num = 0;
			while (rset.next()){
				num++;
				if(num>1){
					break;
				}
				output.add(rset.getString("EMAIL_TEC"));
				output.add(rset.getString("CFPIVA_TEC"));
				output.add(rset.getString("COG_TEC"));
				output.add(rset.getString("NOM_TEC"));
				output.add(rset.getString("TIPO_PERS_TEC"));  
			}
		}catch(java.sql.SQLException ex) {
			CuneoLogger.error(method,"SQLException - "+ex);
			this.getAut().setQuery("getQueryAnagraficaTecnico della classe Pagamento");
			this.getAut().setContenutoQuery(query.toString());
			throw (ex);
		}catch(Exception e){
			CuneoLogger.error(method,"Exception - "+e);
			this.getAut().setQuery("getQueryAnagraficaTecnico della classe Pagamento"
				  +": non è una SQLException ma una Exception"
				  +" generica");
			this.getAut().setContenutoQuery(query.toString());
			throw (e);
		}finally{
			return output;
		}
	}
  public ArrayList<String> getQueryAnagraficaOrganizzazione(String[] array_id_ric) throws Exception, SQLException {
		String method = CLASS_NAME + " - getQueryAnagraficaOrganizzazione - ";
		if (!isConnection())
			throw new Exception("Riferimento a DataSource non inizializzato");
		Connection conn=null;
		StringBuffer query=new StringBuffer("");
		ArrayList<String> output=new ArrayList<String>();
		try{
			conn=getConnection();
			PreparedStatement stmt = null;
			query.append("select distinct AN3.email AS EMAIL_PROP, ec.anagrafica_tecnico, ");
			query.append("OP1.cf_partita_iva AS CFPIVA_ORG_UTE, ");
			query.append("op1.ragione_sociale AS DEN_ORG_UTE, ");
			query.append("OP2.cf_partita_iva AS CFPIVA_ORG_TEC, ");
			query.append("op2.ragione_sociale AS DEN_ORG_TEC ");
			query.append("FROM ");
			query.append("ETICHETTA_CAMPIONE EC ");
			query.append("LEFT JOIN ANAGRAFICA AN1 ON AN1.ID_ANAGRAFICA = EC.ANAGRAFICA_UTENTE ");
			query.append("LEFT JOIN ANAGRAFICA AN2 ON AN2.ID_ANAGRAFICA = EC.ANAGRAFICA_TECNICO ");
			query.append("LEFT JOIN ANAGRAFICA AN3 ON AN3.ID_ANAGRAFICA = EC.ANAGRAFICA_PROPRIETARIO ");
			query.append("LEFT JOIN ORGANIZZAZIONE_PROFESSIONALE OP1 ON OP1.ID_ORGANIZZAZIONE = AN1.ID_ORGANIZZAZIONE ");
			query.append("LEFT JOIN ORGANIZZAZIONE_PROFESSIONALE OP2 ON OP2.ID_ORGANIZZAZIONE = AN2.ID_ORGANIZZAZIONE ");
			query.append("WHERE EC.ID_RICHIESTA IN ( ");
			for(int x=0; x<array_id_ric.length; x++) {
				if(x!=(array_id_ric.length)-1)
					query.append(" ?,");
				else
					query.append(" ? )");
			}
			CuneoLogger.debug(method,"query - "+query.toString());
			stmt = conn.prepareStatement(query.toString());
			int i = 1;
			for(String x : array_id_ric) {
				stmt.setBigDecimal(i++, x != null && !x.equals("")?new BigDecimal(x):null);
				CuneoLogger.debug(method,"idRichiesta - "+x != null && !x.equals("")?x:"");
			}
			ResultSet rset = stmt.executeQuery();
			int num = 0;
			while (rset.next()){
				num++;
				if(num>1){
					break;
				}
				output.add(rset.getString("EMAIL_PROP"));
				output.add(rset.getString("anagrafica_tecnico"));
				output.add(rset.getString("CFPIVA_ORG_UTE"));
				output.add(rset.getString("DEN_ORG_UTE"));
				output.add(rset.getString("CFPIVA_ORG_TEC"));
				output.add(rset.getString("DEN_ORG_TEC"));
			}
		}catch(java.sql.SQLException ex) {
			CuneoLogger.error(method,"SQLException - "+ex);
			this.getAut().setQuery("getQueryAnagraficaOrganizzazione della classe Pagamento");
			this.getAut().setContenutoQuery(query.toString());
			throw (ex);
		}catch(Exception e){
			CuneoLogger.error(method,"Exception - "+e);
			this.getAut().setQuery("getQueryAnagraficaOrganizzazione della classe Pagamento"
				  +": non è una SQLException ma una Exception"
				  +" generica");
			this.getAut().setContenutoQuery(query.toString());
			throw (e);
		}finally{
			return output;
		}
	}
  public ArrayList<String> getQueryAnagraficaAltriEstremi(String[] array_id_ric) throws Exception, SQLException {
		String method = CLASS_NAME + " - getQueryAnagraficaAltriEstremi - ";
		if (!isConnection())
			throw new Exception("Riferimento a DataSource non inizializzato");
		Connection conn=null;
		StringBuffer query=new StringBuffer("");
		ArrayList<String> output=new ArrayList<String>();
		try{
			conn=getConnection();
			PreparedStatement stmt = null;
			query.append("select distinct AN3.email AS EMAIL_PROP, ");
			query.append("DF.cf_partita_iva AS CFPIVA_AE, ");
			query.append("DF.ragione_sociale AS DEN_ORG_AE ");
			query.append("FROM ");
			query.append("ETICHETTA_CAMPIONE EC ");
			query.append("LEFT JOIN ANAGRAFICA AN3 ON AN3.ID_ANAGRAFICA = EC.ANAGRAFICA_PROPRIETARIO ");
			query.append("LEFT JOIN DATI_FATTURA DF ON DF.ID_RICHIESTA = EC.ID_RICHIESTA ");
			query.append("WHERE EC.ID_RICHIESTA IN ( ");
			for(int x=0; x<array_id_ric.length; x++) {
				if(x!=(array_id_ric.length)-1)
					query.append(" ?,");
				else
					query.append(" ? )");
			}
			CuneoLogger.debug(method,"query - "+query.toString());
			stmt = conn.prepareStatement(query.toString());
			int i = 1;
			for(String x : array_id_ric) {
				stmt.setBigDecimal(i++, x != null && !x.equals("")?new BigDecimal(x):null);
				CuneoLogger.debug(method,"idRichiesta - "+x != null && !x.equals("")?x:"");
			}
			ResultSet rset = stmt.executeQuery();
			int num = 0;
			while (rset.next()){
				num++;
				if(num>1){
					break;
				}
				output.add(rset.getString("EMAIL_PROP"));
				output.add(rset.getString("CFPIVA_AE"));
				output.add(rset.getString("DEN_ORG_AE"));
			}
		}catch(java.sql.SQLException ex) {
			CuneoLogger.error(method,"SQLException - "+ex);
			this.getAut().setQuery("getQueryAnagraficaAltriEstremi della classe Pagamento");
			this.getAut().setContenutoQuery(query.toString());
			throw (ex);
		}catch(Exception e){
			CuneoLogger.error(method,"Exception - "+e);
			this.getAut().setQuery("getQueryAnagraficaAltriEstremi della classe Pagamento"
				  +": non è una SQLException ma una Exception"
				  +" generica");
			this.getAut().setContenutoQuery(query.toString());
			throw (e);
		}finally{
			return output;
		}
	}
			
  public boolean inserimentoRisultatoPagamento(String idRichiesta,String tipoPagamento,String iuv,String esito) throws Exception, SQLException {
	  String method = CLASS_NAME + " - inserimentoRisultatoPagamento - ";
	  if (!isConnection())
	    	throw new Exception("Riferimento a DataSource non inizializzato");
	    Connection conn=null;
	    StringBuffer query=new StringBuffer("");
	    int updated = 0;
	    try{
	      conn=getConnection();
	      Statement stmt = conn.createStatement();
	      query = new StringBuffer("INSERT INTO PAGAMENTI ( ");
	      StringBuffer column=new StringBuffer("");
	      StringBuffer values=new StringBuffer("");
	      column.append(" ID_PAGAMENTI, ID_RICHIESTA, ");
	      values.append(" (SELECT nextval('ID_PAGAMENTI')), "+idRichiesta+" , ");
	      if(esito!=null) {
	    	  column.append(" ESITO, ");
	    	  values.append(" '"+esito+"' ,");
	    	  if(esito.equalsIgnoreCase("OK")) {
	    		  if(tipoPagamento!=null) {
	    			  column.append(" ID_TIPO_PAGAMENTO, ");
	    			  values.append(" (select id_tipo_pagamento from tipo_pagamento where cod_tipo_pagamento ='"+tipoPagamento+"') ,");
	    		  }
	    		  if(iuv!=null) {
	    			  column.append(" IUV, ");
	    			  values.append(" '"+iuv+"' ,");
	    		  }
	    		  if(this.getAut().getCod_fiscale_pagatore()!=null) {
	    			  column.append(" CF_PARTITA_IVA, ");
	    			  values.append(" '"+this.getAut().getCod_fiscale_pagatore()+"' ,");
	    		  }
	    	  }
	      }
	      column.append(" DATA_RICHIESTA_PAGAMENTO ) ");
	      query.append(column);
	      query.append("VALUES ( ");
	      query.append(values);
	      query.append(" now() ) "); 
	      CuneoLogger.debug(method,query.toString());
	      updated = stmt.executeUpdate( query.toString());
	      stmt.close();
	    }catch(java.sql.SQLException ex) {
	    	CuneoLogger.error(method,"SQLException - "+ex);
	    	this.getAut().setQuery("inserimentoRisultatoPagamento della classe Pagamento per id richiesta "+this.idRichiesta);
	    	this.getAut().setContenutoQuery(query.toString());
	    	throw (ex);
	    }catch(Exception e){
	    	CuneoLogger.error(method,"Exception - "+e);
	    	this.getAut().setQuery("inserimentoRisultatoPagamento della classe Pagamento per id richiesta "+this.idRichiesta
	                            +": non è una SQLException ma una Exception"
	                            +" generica");
	    	this.getAut().setContenutoQuery(query.toString());
	    	throw (e);
	    }finally{
	    	CuneoLogger.debug(method,"ESITO INSERT INTO PAGAMENTI : "+(updated>0?"positivo":"negativo"));
	    	if (conn!=null) conn.close();
	    		return updated>0?true:false;
	    }
	  }
  
  public boolean updateFlagPagamento(String idRichiesta, Date dataPagamento) throws Exception, SQLException {
	  String method = CLASS_NAME + " - updateFlagPagamento - ";
	  if (!isConnection())
	    	throw new Exception("Riferimento a DataSource non inizializzato");
	    Connection conn=null;
	    StringBuffer query=new StringBuffer("");
	    int update_flag = 0;
	    boolean update_data = false;
	    try{
			conn=getConnection();
			Statement stmt = conn.createStatement();
			stmt = conn.createStatement();
			query = new StringBuffer("UPDATE ETICHETTA_CAMPIONE SET PAGAMENTO = 'S' WHERE ID_RICHIESTA = "+idRichiesta);
			CuneoLogger.debug(method,query.toString());
			update_flag = stmt.executeUpdate( query.toString());
			CuneoLogger.debug(method,"ESITO UPDATE ETICHETTA_CAMPIONE : "+(update_flag>0?"positivo":"negativo"));
			if(update_flag>0) {
				update_data = updateDataPagamento(idRichiesta,dataPagamento);
			}
			stmt.close();
	    }catch(java.sql.SQLException ex) {
	    	CuneoLogger.error(method,"SQLException - "+ex);
	    	this.getAut().setQuery("updateFlagPagamento della classe Pagamento per id richiesta "+idRichiesta);
	    	this.getAut().setContenutoQuery(query.toString());
	    	throw (ex);
	    }catch(Exception e){
	    	CuneoLogger.error(method,"Exception - "+e);
	    	this.getAut().setQuery("updateFlagPagamento della classe Pagamento per id richiesta "+idRichiesta
	                            +": non è una SQLException ma una Exception"
	                            +" generica");
	    	this.getAut().setContenutoQuery(query.toString());
	    	throw (e);
	    }finally{
	    	if (conn!=null) conn.close();
	    		return (update_flag>0 && update_data)?true:false;
	    }
  }
  
  public boolean updateDataPagamento(String idRichiesta, Date dataPagamento) throws Exception, SQLException {
	  String method = CLASS_NAME + " - updateDataPagamento - ";
	  if (!isConnection())
	    	throw new Exception("Riferimento a DataSource non inizializzato");
	    Connection conn=null;
	    StringBuffer query=new StringBuffer("");
	    int update_data = 0;
	    PreparedStatement stmt = null;
	    try{
			conn=getConnection();
			//update sulla Pagamenti
			query = new StringBuffer("UPDATE PAGAMENTI SET DATA_PAGAMENTO = ? ");
			query.append("WHERE ID_RICHIESTA = ? and data_annullamento is null ");
			CuneoLogger.debug(method,query.toString());
			stmt = conn.prepareStatement(query.toString());
			Timestamp ts = null;
			if(dataPagamento==null) 
				ts = new Timestamp(new Date().getTime());
			else
				ts = new Timestamp(dataPagamento.getTime());
				
			stmt.setTimestamp(1, ts);
			stmt.setBigDecimal(2, new BigDecimal(idRichiesta));
			update_data = stmt.executeUpdate();
			CuneoLogger.debug(method,"ESITO UPDATE PAGAMENTI : "+(update_data>0?"positivo":"negativo"));
			
			//update sulla etichetta_campione
			query = new StringBuffer("UPDATE ETICHETTA_CAMPIONE SET DATA_INCASSO = ? ");
			query.append("WHERE ID_RICHIESTA = ? ");
			CuneoLogger.debug(method,query.toString());
			stmt = conn.prepareStatement(query.toString());
			stmt.setTimestamp(1, ts);
			stmt.setBigDecimal(2, new BigDecimal(idRichiesta));
			update_data = stmt.executeUpdate();
			CuneoLogger.debug(method,"ESITO UPDATE ETICHETTA_CAMPIONE : "+(update_data>0?"positivo":"negativo"));
			stmt.close();
	    }catch(java.sql.SQLException ex) {
	    	CuneoLogger.error(method,"SQLException - "+ex);
	    	this.getAut().setQuery("updateDataPagamento della classe Pagamento per id richiesta "+idRichiesta);
	    	this.getAut().setContenutoQuery(query.toString());
	    	throw (ex);
	    }catch(Exception e){
	    	CuneoLogger.error(method,"Exception - "+e);
	    	this.getAut().setQuery("updateDataPagamento della classe Pagamento per id richiesta "+idRichiesta
	                            +": non è una SQLException ma una Exception"
	                            +" generica");
	    	this.getAut().setContenutoQuery(query.toString());
	    	throw (e);
	    }finally{
	    	if (conn!=null) conn.close();
	    		return update_data>0?true:false;
	    }
  }
  
  public boolean updateAnnullaPagamento(String idRichiesta) throws Exception, SQLException {
	  String method = CLASS_NAME + " - updateAnnullaPagamento - ";
	  if (!isConnection())
	    	throw new Exception("Riferimento a DataSource non inizializzato");
	    Connection conn=null;
	    StringBuffer query=new StringBuffer("");
	    int update_data = 0;
	    try{
			conn=getConnection();
			Statement stmt = conn.createStatement();
			stmt = conn.createStatement();
			
			query = new StringBuffer("UPDATE PAGAMENTI SET DATA_ANNULLAMENTO = now() ");
			query.append("WHERE ID_RICHIESTA in ("+idRichiesta+")");
			CuneoLogger.debug(method,query.toString());
			update_data = stmt.executeUpdate( query.toString());
			CuneoLogger.debug(method,"ESITO updateAnnullaPagamento : "+(update_data>0?"positivo":"negativo"));
			stmt.close();
	    }catch(java.sql.SQLException ex) {
	    	CuneoLogger.error(method,"SQLException - "+ex);
	    	this.getAut().setQuery("updateAnnullaPagamento della classe Pagamento per id richiesta "+idRichiesta);
	    	this.getAut().setContenutoQuery(query.toString());
	    	throw (ex);
	    }catch(Exception e){
	    	CuneoLogger.error(method,"Exception - "+e);
	    	this.getAut().setQuery("updateAnnullaPagamento della classe Pagamento per id richiesta "+idRichiesta
	                            +": non è una SQLException ma una Exception"
	                            +" generica");
	    	this.getAut().setContenutoQuery(query.toString());
	    	throw (e);
	    }finally{
	    	if (conn!=null) conn.close();
	    		return update_data>0?true:false;
	    }
  }
  
  public Pagamento selectPagamento(String idRichiesta, String tipo_pagamento, boolean pagato) throws Exception, SQLException {
	  String method = CLASS_NAME + " - selectPagamento - ";
    if (!isConnection())
    	throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn=null;
    StringBuffer query=new StringBuffer("");
    Pagamento p = null;
    try{
      conn=getConnection();
      Statement stmt = conn.createStatement();
      query = new StringBuffer("SELECT ");//P.ID_RICHIESTA as NUMERO_RICHIESTA,
	  query.append("P.IUV, P.CF_PARTITA_IVA, SUM(AR.COSTO_ANALISI) as IMPORTO ");
	  query.append("FROM PAGAMENTI P ");
	  query.append(" join analisi_richieste AR on P.ID_RICHIESTA=AR.ID_RICHIESTA ");
	  if(tipo_pagamento.equals("M3") && !pagato)
		  query.append("WHERE P.DATA_PAGAMENTO IS NULL ");
	  else
		  query.append("WHERE 1=1 ");
      if (idRichiesta != null && !idRichiesta.equals(""))
    	  query.append("and P.ID_RICHIESTA in ("+idRichiesta+") ");
      query.append(" and p.data_annullamento is null group by IUV,CF_PARTITA_IVA ");
      CuneoLogger.debug(method,query.toString());
      ResultSet rset = stmt.executeQuery(query.toString());
      if (rset.next()){
    	 p = new Pagamento();
//    	 p.setIdRichiesta(rset.getString("NUMERO_RICHIESTA"));
//    	 CuneoLogger.debug(method, p.getIdRichiesta());
    	 p.setIuv(rset.getString("IUV"));
    	 CuneoLogger.debug(method, p.getIuv());
    	 p.setCf(rset.getString("CF_PARTITA_IVA"));
    	 CuneoLogger.debug(method, p.getCf());
    	 p.setImporto(rset.getString("IMPORTO"));
    	 CuneoLogger.debug(method, p.getImporto());
      }
      rset.close();
      stmt.close();
    }catch(java.sql.SQLException ex) {
    	CuneoLogger.error(method,"SQLException - "+ex);
    	this.getAut().setQuery("selectPagamento della classe Pagamento");
    	this.getAut().setContenutoQuery(query.toString());
    	throw (ex);
    }catch(Exception e){
    	CuneoLogger.error(method,"Exception - "+e);
    	this.getAut().setQuery("selectPagamento della classe Pagamento"
                            +": non è una SQLException ma una Exception"
                            +" generica");
    	this.getAut().setContenutoQuery(query.toString());
    	throw (e);
    }finally{
    	if (conn!=null) conn.close();
    		return p;
    }
  }
  
  public void fillElencoPagamenti() throws Exception, SQLException{
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
	    try{
	      conn=getConnection();
	      Statement stmt = conn.createStatement();
	      ResultSet rset = stmt.executeQuery(queryCount);
	      if (rset.next())
	        this.numRecord=rset.getInt("NUM");
	      rset.close();

	      int inizio,fine;
	      if ( this.baseElementi == 0) 
	    	  inizio=1;
	      else 
	    	  inizio=this.getBaseElementi();
	      
	      if ( this.passo == 0 ) 
	    	  fine=size();
	      else 
	    	  fine=inizio+this.getPasso()-1;
	      //la query è in Autenticazione.java -> ricercaElencoPagamenti+X
	      //devo però filtrare per iuv e va messo in questa where
	      //il campo è salvato in Autenticazione.java ma mi serve qui
	      String [] arrOfStr = null;
	      if(query.indexOf("IUV:")>0) {
	    	  arrOfStr = query.split("(IUV:)");
	    	  query = arrOfStr[0];
		      query+=") AS ELENCO_CAMPIONI WHERE NUM BETWEEN "+inizio+" AND "+fine;
		      String iuv;
		      if(arrOfStr[1].startsWith("'3")) {
		    	  iuv = "'"+arrOfStr[1].substring(2);
		      }else
		    	  iuv = arrOfStr[1];
		      query+=" AND UPPER(IUV) like ("+iuv+") ";
	      }else
	    	  query+=") AS ELENCO_CAMPIONI WHERE NUM BETWEEN "+inizio+" AND "+fine;
	      CuneoLogger.debug(this,query);
	      rset = stmt.executeQuery(query);

	      while (rset.next()){
	        anagraficaProprietario=rset.getString("ANAGRAFICA_PROPRIETARIO");
	        anagraficaUtente=rset.getString("ANAGRAFICA_UTENTE");
	        if (anagraficaProprietario==null){
	          proprietario=anagrafiche.getNomeCognome(conn,anagraficaUtente);
	        }else{
	          proprietario=anagrafiche.getNomeCognome(conn,anagraficaProprietario);
	        }
	        anagraficaTecnico=rset.getString("ANAGRAFICA_TECNICO");
	        if (anagraficaTecnico==null){
	          richiedente=anagrafiche.getNomeCognome(conn,anagraficaUtente);
	        }else{
	          richiedente=anagrafiche.getNomeCognome(conn,anagraficaTecnico);
	        }

	        add(new Pagamento(rset.getString("ID_RICHIESTA"),
	                                  rset.getString("DATA"),
	                                  rset.getString("DESCRIZIONE_ETICHETTA"),
	                                  rset.getString("STATO_ATTUALE"),
	                                  proprietario,
	                                  richiedente,
	                                  rset.getString("DENOMINAZIONE"),
	                                  rset.getString("IUV"),
	                                  rset.getString("PAGAMENTO"),
	                                  rset.getString("COD_TIPO_PAGAMENTO"),
	                                  rset.getString("CODICE_DESTINATARIO"),
	                                  rset.getString("PEC"),
	                                  rset.getString("FATTURA_SN"),
	                                  rset.getString("DATA_ANNULLAMENTO"),
	                                  rset.getString("CODICE"),
	                                  rset.getString("CODICE_MATERIALE"))
	                                  );
	      }
	      rset.close();
	      
	      //fix per annullamento iuv associato a + id_richiesta
	      for(Pagamento p : this.pagamenti) {
	    	  if(p.getIuv()!=null && !p.getIuv().equals("")) {
	    		  //seleziono dal DB tutti gli id_richiesta per uno iuv
	    		  query = "select id_richiesta " + 
	    		  		"from PAGAMENTI where iuv = (select iuv from PAGAMENTI where id_richiesta = "+p.getIdRichiesta()+" "+ 
	    		  		"and data_annullamento is null) order by id_richiesta ASC ";
	    		  rset = stmt.executeQuery(query);
	    		  int x = 0;
	    		  String idRichieste = "";
	    		  while (rset.next()){
	    			  if(x==0) 
	    				  idRichieste+=rset.getString("ID_RICHIESTA");
	    			  else
	    				  idRichieste+=","+rset.getString("ID_RICHIESTA");
	    			  x++;
	    		  }
	    		  if(x>1)
	    			  p.setIdRichiesteMultiple(idRichieste);
	    		  rset.close();
	    	  }
	      }
	      stmt.close();
	    }catch(java.sql.SQLException ex){
	      this.getAut().setQuery("fillElencoPagamenti della classe Pagamenti");
	      this.getAut().setContenutoQuery(query);
	      throw (ex);
	    }catch(Exception e){
	      this.getAut().setQuery("fillElencoPagamenti della classe Pagamenti"
	                             +": non è una SQLException ma una Exception"
	                             +" generica");
	      this.getAut().setContenutoQuery(query);
	      throw (e);
	    }finally{
	      if (conn!=null) conn.close();
	    }
	  }
  
  
  
  
  
  
  //getter & setter
  
public String getOperazione() {
	return operazione;
}
public void setOperazione(String operazione) {
	this.operazione = operazione;
}
public String getCf() {
	return cf;
}
public void setCf(String cf) {
	this.cf = cf;
}
public String getRuolo() {
	return ruolo;
}
public void setRuolo(String ruolo) {
	this.ruolo = ruolo;
}
public String getLiv() {
	return liv;
}
public void setLiv(String liv) {
	this.liv = liv;
}
public String getIdProcedimento_accesso() {
	return idProcedimento_accesso;
}
public void setIdProcedimento_accesso(String idProcedimento_accesso) {
	this.idProcedimento_accesso = idProcedimento_accesso;
}
public String getIdProcedimento() {
	return idProcedimento;
}
public void setIdProcedimento(String idProcedimento) {
	this.idProcedimento = idProcedimento;
}
public String getIdFruitore() {
	return idFruitore;
}
public void setIdFruitore(String idFruitore) {
	this.idFruitore = idFruitore;
}
public String getCodicePagamento() {
	return codicePagamento;
}
public void setCodicePagamento(String codicePagamento) {
	this.codicePagamento = codicePagamento;
}
public String getImporto() {
	return importo;
}
public void setImporto(String importo) {
	this.importo = importo;
}
public String getPagatore_idAnagraficaAzienda() {
	return pagatore_idAnagraficaAzienda;
}
public void setPagatore_idAnagraficaAzienda(String pagatore_idAnagraficaAzienda) {
	this.pagatore_idAnagraficaAzienda = pagatore_idAnagraficaAzienda;
}
public String getPagatore_nome() {
	return pagatore_nome;
}
public void setPagatore_nome(String pagatore_nome) {
	this.pagatore_nome = pagatore_nome;
}
public String getPagatore_codiceFiscale() {
	return pagatore_codiceFiscale;
}
public void setPagatore_codiceFiscale(String pagatore_codiceFiscale) {
	this.pagatore_codiceFiscale = pagatore_codiceFiscale;
}
public String getPagatore_ragioneSociale() {
	return pagatore_ragioneSociale;
}
public void setPagatore_ragioneSociale(String pagatore_ragioneSociale) {
	this.pagatore_ragioneSociale = pagatore_ragioneSociale;
}
public String getPagatore_idUnicoPagatore() {
	return pagatore_idUnicoPagatore;
}
public void setPagatore_idUnicoPagatore(String pagatore_idUnicoPagatore) {
	this.pagatore_idUnicoPagatore = pagatore_idUnicoPagatore;
}
public String getPagatore_piva() {
	return pagatore_piva;
}
public void setPagatore_piva(String pagatore_piva) {
	this.pagatore_piva = pagatore_piva;
}
public String getPagatore_email() {
	return pagatore_email;
}
public void setPagatore_email(String pagatore_email) {
	this.pagatore_email = pagatore_email;
}
public String getPagatore_pec() {
	return pagatore_pec;
}
public void setPagatore_pec(String pagatore_pec) {
	this.pagatore_pec = pagatore_pec;
}
public String getVersante_nome() {
	return versante_nome;
}
public void setVersante_nome(String versante_nome) {
	this.versante_nome = versante_nome;
}
public String getVersante_cognome() {
	return versante_cognome;
}
public void setVersante_cognome(String versante_cognome) {
	this.versante_cognome = versante_cognome;
}
public String getVersante_codiceFiscale() {
	return versante_codiceFiscale;
}
public void setVersante_codiceFiscale(String versante_codiceFiscale) {
	this.versante_codiceFiscale = versante_codiceFiscale;
}
public String getVersante_ragioneSociale() {
	return versante_ragioneSociale;
}
public void setVersante_ragioneSociale(String versante_ragioneSociale) {
	this.versante_ragioneSociale = versante_ragioneSociale;
}
public String getVersante_idUnicoPagatore() {
	return versante_idUnicoPagatore;
}
public void setVersante_idUnicoPagatore(String versante_idUnicoPagatore) {
	this.versante_idUnicoPagatore = versante_idUnicoPagatore;
}
public String getVersante_email() {
	return versante_email;
}
public void setVersante_email(String versante_email) {
	this.versante_email = versante_email;
}
public String getVersante_pec() {
	return versante_pec;
}
public void setVersante_pec(String versante_pec) {
	this.versante_pec = versante_pec;
}
public String getTipoPagamento() {
	return tipoPagamento;
}
public void setTipoPagamento(String tipoPagamento) {
	this.tipoPagamento = tipoPagamento;
}

public String getPagatore_cognome() {
	return pagatore_cognome;
}

public void setPagatore_cognome(String pagatore_cognome) {
	this.pagatore_cognome = pagatore_cognome;
}


public String getVersante_piva() {
	return versante_piva;
}

public void setVersante_piva(String versante_piva) {
	this.versante_piva = versante_piva;
}


public String getIuv() {
	return iuv;
}

public void setIuv(String iuv) {
	this.iuv = iuv;
}

public String getEsito() {
	return esito;
}

public void setEsito(String esito) {
	this.esito = esito;
}

public String getIdRichiesta() {
	return idRichiesta;
}

public void setIdRichiesta(String idRichiesta) {
	this.idRichiesta = idRichiesta;
}

public int getPasso() {
	return passo;
}

public void setPasso(int passo) {
	this.passo = passo;
}

public int getBaseElementi() {
	return baseElementi;
}

public void setBaseElementi(int baseElementi) {
	this.baseElementi = baseElementi;
}

public int getNumRecord() {
	return numRecord;
}

public void setNumRecord(int numRecord) {
	this.numRecord = numRecord;
}

public Vector<Pagamento> getPagamenti() {
	return pagamenti;
}

public void setPagamenti(Vector<Pagamento> pagamenti) {
	this.pagamenti = pagamenti;
}
public int size() {
  return this.pagamenti.size();
}
public Pagamento get(int i)
{
  return this.pagamenti.elementAt(i);
}
public void add(Pagamento i)
{
	this.pagamenti.addElement(i);
}

public String getCodiceMateriale() {
	return codiceMateriale;
}

public void setCodiceMateriale(String codiceMateriale) {
	this.codiceMateriale = codiceMateriale;
}

public String getStatoAttuale() {
	return statoAttuale;
}

public void setStatoAttuale(String statoAttuale) {
	this.statoAttuale = statoAttuale;
}

public String getConteggioFatture() {
	return conteggioFatture;
}

public void setConteggioFatture(String conteggioFatture) {
	this.conteggioFatture = conteggioFatture;
}

public String getNumeroFattura() {
	return numeroFattura;
}

public void setNumeroFattura(String numeroFattura) {
	this.numeroFattura = numeroFattura;
}

public String getAnnoFattura() {
	return annoFattura;
}

public void setAnnoFattura(String annoFattura) {
	this.annoFattura = annoFattura;
}

public String getDataInserimentoRichiesta() {
	return dataInserimentoRichiesta;
}

public void setDataInserimentoRichiesta(String dataInserimentoRichiesta) {
	this.dataInserimentoRichiesta = dataInserimentoRichiesta;
}

public String getDescMateriale() {
	return descMateriale;
}

public void setDescMateriale(String descMateriale) {
	this.descMateriale = descMateriale;
}

public String getDescrizioneEtichetta() {
	return descrizioneEtichetta;
}

public void setDescrizioneEtichetta(String descrizioneEtichetta) {
	this.descrizioneEtichetta = descrizioneEtichetta;
}

public String getDescStatoAttuale() {
	return descStatoAttuale;
}

public void setDescStatoAttuale(String descStatoAttuale) {
	this.descStatoAttuale = descStatoAttuale;
}

public String getProprietario() {
	return proprietario;
}

public void setProprietario(String proprietario) {
	this.proprietario = proprietario;
}

public String getRichiedente() {
	return richiedente;
}

public void setRichiedente(String richiedente) {
	this.richiedente = richiedente;
}

public String getDenominazione() {
	return denominazione;
}

public void setDenominazione(String denominazione) {
	this.denominazione = denominazione;
}

public String getStatoPagamento() {
	return statoPagamento;
}

public void setStatoPagamento(String statoPagamento) {
	this.statoPagamento = statoPagamento;
}

public Date getDataPagamento() {
	return dataPagamento;
}

public void setDataPagamento(Date dataPagamento) {
	this.dataPagamento = dataPagamento;
}

public void setDataPagamento(XMLGregorianCalendar calendar) {
	if(calendar != null) {
		this.dataPagamento = getDate(calendar);
	}
}
public static Date getDate(XMLGregorianCalendar obj){
    Date date= obj.toGregorianCalendar().getTime();
    CuneoLogger.debug("getDate", "inside date parse method");
    return date;
}

public String getDataAnnullamento() {
	return dataAnnullamento;
}

public void setDataAnnullamento(String dataAnnullamento) {
	this.dataAnnullamento = dataAnnullamento;
}

public String getFatturaRichiestaSN() {
	return fatturaRichiestaSN;
}

public void setFatturaRichiestaSN(String fatturaRichiestaSN) {
	this.fatturaRichiestaSN = fatturaRichiestaSN;
}

public String getIdRichiesteMultiple() {
	return idRichiesteMultiple;
}

public void setIdRichiesteMultiple(String idRichiesteMultiple) {
	this.idRichiesteMultiple = idRichiesteMultiple;
}

public String getPec() {
	return pec;
}

public void setPec(String pec) {
	this.pec = pec;
}

public String getCodDestinatario() {
	return codDestinatario;
}

public void setCodDestinatario(String codDestinatario) {
	this.codDestinatario = codDestinatario;
}

public String getScelta_pagatore_idAnagraficaAzienda() {
	return scelta_pagatore_idAnagraficaAzienda;
}

public void setScelta_pagatore_idAnagraficaAzienda(String scelta_pagatore_idAnagraficaAzienda) {
	this.scelta_pagatore_idAnagraficaAzienda = scelta_pagatore_idAnagraficaAzienda;
}

public String getScelta_pagatore_nome() {
	return scelta_pagatore_nome;
}

public void setScelta_pagatore_nome(String scelta_pagatore_nome) {
	this.scelta_pagatore_nome = scelta_pagatore_nome;
}

public String getScelta_pagatore_cognome() {
	return scelta_pagatore_cognome;
}

public void setScelta_pagatore_cognome(String scelta_pagatore_cognome) {
	this.scelta_pagatore_cognome = scelta_pagatore_cognome;
}

public String getScelta_pagatore_codiceFiscale() {
	return scelta_pagatore_codiceFiscale;
}

public void setScelta_pagatore_codiceFiscale(String scelta_pagatore_codiceFiscale) {
	this.scelta_pagatore_codiceFiscale = scelta_pagatore_codiceFiscale;
}

public String getScelta_pagatore_ragioneSociale() {
	return scelta_pagatore_ragioneSociale;
}

public void setScelta_pagatore_ragioneSociale(String scelta_pagatore_ragioneSociale) {
	this.scelta_pagatore_ragioneSociale = scelta_pagatore_ragioneSociale;
}

public String getScelta_pagatore_idUnicoPagatore() {
	return scelta_pagatore_idUnicoPagatore;
}

public void setScelta_pagatore_idUnicoPagatore(String scelta_pagatore_idUnicoPagatore) {
	this.scelta_pagatore_idUnicoPagatore = scelta_pagatore_idUnicoPagatore;
}

public String getScelta_pagatore_piva() {
	return scelta_pagatore_piva;
}

public void setScelta_pagatore_piva(String scelta_pagatore_piva) {
	this.scelta_pagatore_piva = scelta_pagatore_piva;
}

public String getScelta_pagatore_email() {
	return scelta_pagatore_email;
}

public void setScelta_pagatore_email(String scelta_pagatore_email) {
	this.scelta_pagatore_email = scelta_pagatore_email;
}

public String getScelta_pagatore_pec() {
	return scelta_pagatore_pec;
}

public void setScelta_pagatore_pec(String scelta_pagatore_pec) {
	this.scelta_pagatore_pec = scelta_pagatore_pec;
}

public String getScelta_versante_nome() {
	return scelta_versante_nome;
}

public void setScelta_versante_nome(String scelta_versante_nome) {
	this.scelta_versante_nome = scelta_versante_nome;
}

public String getScelta_versante_cognome() {
	return scelta_versante_cognome;
}

public void setScelta_versante_cognome(String scelta_versante_cognome) {
	this.scelta_versante_cognome = scelta_versante_cognome;
}

public String getScelta_versante_codiceFiscale() {
	return scelta_versante_codiceFiscale;
}

public void setScelta_versante_codiceFiscale(String scelta_versante_codiceFiscale) {
	this.scelta_versante_codiceFiscale = scelta_versante_codiceFiscale;
}

public String getScelta_versante_ragioneSociale() {
	return scelta_versante_ragioneSociale;
}

public void setScelta_versante_ragioneSociale(String scelta_versante_ragioneSociale) {
	this.scelta_versante_ragioneSociale = scelta_versante_ragioneSociale;
}

public String getScelta_versante_idUnicoPagatore() {
	return scelta_versante_idUnicoPagatore;
}

public void setScelta_versante_idUnicoPagatore(String scelta_versante_idUnicoPagatore) {
	this.scelta_versante_idUnicoPagatore = scelta_versante_idUnicoPagatore;
}

public String getScelta_versante_piva() {
	return scelta_versante_piva;
}

public void setScelta_versante_piva(String scelta_versante_piva) {
	this.scelta_versante_piva = scelta_versante_piva;
}

public String getScelta_versante_email() {
	return scelta_versante_email;
}

public void setScelta_versante_email(String scelta_versante_email) {
	this.scelta_versante_email = scelta_versante_email;
}

public String getScelta_versante_pec() {
	return scelta_versante_pec;
}

public void setScelta_versante_pec(String scelta_versante_pec) {
	this.scelta_versante_pec = scelta_versante_pec;
}

public String getCodiceTracciatura() {
	return codiceTracciatura;
}

public void setCodiceTracciatura(String codiceTracciatura) {
	this.codiceTracciatura = codiceTracciatura;
}
}