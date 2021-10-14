package it.csi.agrc;
//import it.csi.cuneo.*;
//import it.csi.jsf.web.pool.*;
import java.io.*;
import java.sql.*;
//import javax.sql.*;

import it.csi.cuneo.CuneoLogger;

/**
 * Title:        Agrichim - Front Office
 * Description:  Richiesta analisi chimiche su campioni biologici agrari
 * Copyright:    Copyright (c) 2003
 * Company:      CSI Piemonte - Progettazione e Sviluppo - Cuneo
 * @author Michele Piantà, Piergiorgio Chiriotti
 * @version 1.0.0
 */

/**
 * La classe Utente viene utilizzata come classe base per memorizzare le
 * informazioni relative ad un utente
 * E' un Java Bean che possiede le proprietà:
 * - identificativoUtente
 * - anagraficaUtente
 * - anagraficaAzienda
 * - nome
 * - cognome
 * - codice fiscale
 * - partita iva
 * - tipoUtente
 * - idOrganizzazione
 */

public class Utente implements Serializable
{
  private static final long serialVersionUID = 4657971164074027302L;

  private String identificativoUtenteIride;
  private String anagraficaUtente;
  private String anagraficaAzienda;
  private String nome;
  private String cognome;
  private String codiceFiscale;
  private String partitaIva;
  private char tipoUtente;
  private String idOrganizzazione;

  public Utente(String identificativoUtenteIride,
                String codiceFiscale,
                String partitaIva)
  {
    this.identificativoUtenteIride=identificativoUtenteIride;
    this.codiceFiscale=codiceFiscale;
    this.partitaIva=partitaIva;
  }

  public String getIdentificativoUtenteIride() {
    return identificativoUtenteIride;
  }
  public void setIdentificativoUtenteIride(String identificativoUtenteIride) {
    this.identificativoUtenteIride = identificativoUtenteIride;
  }
  public void setAnagraficaUtente(String anagraficaUtente) {
    this.anagraficaUtente = anagraficaUtente;
  }
  public String getAnagraficaUtente() {
    return anagraficaUtente;
  }
  public void setAnagraficaAzienda(String anagraficaAzienda) {
    this.anagraficaAzienda = anagraficaAzienda;
  }
  public String getAnagraficaAzienda() {
    return anagraficaAzienda;
  }
  public void setNome(String nome) {
    this.nome = nome;
  }
  public String getNome() {
    return nome;
  }
  public void setCognome(String cognome) {
    this.cognome = cognome;
  }
  public String getCognome() {
    return cognome;
  }
  public void setCodiceFiscale(String codiceFiscale) {
    this.codiceFiscale = codiceFiscale;
  }
  public String getCodiceFiscale() {
    return codiceFiscale;
  }
  public void setPartitaIva(String partitaIva) {
    this.partitaIva = partitaIva;
  }
  public String getPartitaIva() {
    return partitaIva;
  }
  public void setTipoUtente(char tipoUtente) {
    this.tipoUtente = tipoUtente;
  }
  public char getTipoUtente() {
    return tipoUtente;
  }


  /**
   * Questo metodo carica l'oggetto utente con i dati relativi all'utente
   * @param conn
   * @return se il valore restituito è false vuol dire che non è stato
   * trovato nessun record nella tabella anagrafica che contiene quel
   * codice fiscale
   * @throws Exception
   * @throws SQLException
   */
  public boolean leggiAnagraficaUtente(Connection conn) throws Exception, SQLException{
	  CuneoLogger.debug(this,"sono in Utente.java / leggiAnagraficaUtente ");
        StringBuffer query=new StringBuffer("");
        Statement stmt = null;
        boolean trovato = false;
        try{
            stmt = conn.createStatement();
            query.append("SELECT A.ID_ANAGRAFICA,");
            query.append("A.COGNOME_RAGIONE_SOCIALE,");
            query.append("A.NOME, A.TIPO_UTENTE,A.ID_ORGANIZZAZIONE ");
            query.append("FROM ANAGRAFICA A ");
            query.append("WHERE A.CODICE_IDENTIFICATIVO = '").append(codiceFiscale);
            query.append("'");
            CuneoLogger.debug(this,query.toString());
            ResultSet rset = stmt.executeQuery(query.toString());
            if (rset.next()) {
              CuneoLogger.debug(this,"rset.next()");
              this.setAnagraficaUtente(rset.getString("ID_ANAGRAFICA"));
              CuneoLogger.debug(this,"this.getAnagraficaUtente() -> "+this.getAnagraficaUtente());
              this.setCognome(rset.getString("COGNOME_RAGIONE_SOCIALE"));
              CuneoLogger.debug(this,"this.getCognome() -> "+this.getCognome());
              this.setNome(rset.getString("NOME"));
              CuneoLogger.debug(this,"this.getNome() -> "+this.getNome());
              this.setTipoUtente(
                   rset.getString("TIPO_UTENTE").toUpperCase().charAt(0));
              CuneoLogger.debug(this,"this.getTipoUtente() -> "+this.getTipoUtente());
              this.setIdOrganizzazione(rset.getString("ID_ORGANIZZAZIONE"));
              CuneoLogger.debug(this,"this.getIdOrganizzazione() -> "+this.getIdOrganizzazione());
              trovato = true;
            }
            rset.close();
            return trovato;
        } catch(java.sql.SQLException ex) {
        	CuneoLogger.debug(this,"leggiAnagraficaUtente-SQLException : "+ex);
          //this.getAut().setQuery("leggiAnagraficaUtente della classe Utente");
          //this.getAut().setContenutoQuery(query.toString());
            throw (ex);
        } catch(Exception e) {
        	CuneoLogger.debug(this,"leggiAnagraficaUtente-Exception : "+e);
          /*
          this.getAut().setQuery("leggiAnagraficaUtente della classe Utente"
                                  +": non è una SQLException ma una Exception"
                                  +" generica");
          this.getAut().setContenutoQuery(query.toString());
          */
            throw (e);
        } finally {
            stmt.close();
        }
    }

    public void leggiAnagraficaAzienda(Connection conn)
    throws Exception, SQLException{
    	CuneoLogger.debug(this,"sono in Utente.java / leggiAnagraficaAzienda ");
        StringBuffer query=new StringBuffer("");
        Statement stmt = null;
        try {
            query.setLength( 0 );
            stmt = conn.createStatement();
            query.append("SELECT A.ID_ANAGRAFICA ");
            query.append("FROM ANAGRAFICA A ");
            query.append("WHERE A.CODICE_IDENTIFICATIVO = '").append(partitaIva);
            query.append("'");
            CuneoLogger.debug(this,"query -> "+query.toString());
            ResultSet rset = stmt.executeQuery(query.toString());
            if (rset.next()) {
            	CuneoLogger.debug(this,"rset.next() ");
              this.setAnagraficaAzienda(rset.getString("ID_ANAGRAFICA"));
              rset.close();
              stmt.close();
            } else {
            	CuneoLogger.debug(this,"NOT rset.next() ");
              rset.close();
              stmt.close();
              /** 
               * se sono qua vuol dire che non è stato trovato nessun record
               * nella tabella anagrafica che contiene questa partita iva
               * quindi devo inserirla
               */
              String idAnagraficaNext=null;

              query.setLength( 0 );
              query.append( "SELECT nextval('ID_ANAGRAFICA') as NEXTVAL" );
              stmt = conn.createStatement();
              rset = stmt.executeQuery( query.toString() );
              if ( rset.next() )
                	idAnagraficaNext = rset.getString( "NEXTVAL" );
              rset.close();
              stmt.close();

              query.setLength(0);
              query.append("INSERT INTO ANAGRAFICA");
              query.append("(ID_ANAGRAFICA,CODICE_IDENTIFICATIVO,TIPO_PERSONA,ID_ANAGRAFICA_2)");
              query.append(" VALUES(").append(idAnagraficaNext).append(",'");
              query.append(partitaIva);
              query.append("','G',").append(idAnagraficaNext).append(")");
              CuneoLogger.debug(this,"insert -> "+query.toString());
              stmt = conn.createStatement();
              stmt.executeUpdate(query.toString());
            }
        }catch(java.sql.SQLException ex){
        	CuneoLogger.debug(this,"leggiAnagraficaUtente-SQLException : "+ex);
            ex.printStackTrace();
            throw (ex);
        }catch(Exception e){
        	CuneoLogger.debug(this,"leggiAnagraficaUtente-Exception : "+e);
            e.printStackTrace();
            throw (e);
        }
    }
  public void setIdOrganizzazione(String idOrganizzazione)
  {
    this.idOrganizzazione = idOrganizzazione;
  }
  public String getIdOrganizzazione()
  {
    return idOrganizzazione;
  }
}