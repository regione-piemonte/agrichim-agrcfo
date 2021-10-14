package it.csi.agrc;

import it.csi.cuneo.*;
import java.sql.*;
//import it.csi.jsf.web.pool.*;

/**
 * <p>Title: Agrichim - Front Office</p>
 * <p>Description: Richiesta analisi chimiche su campioni biologici agrari</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: CSI Piemonte - Progettazione e Sviluppo - Cuneo</p>
 * @author Michele Piantà, Piergiorgio Chiriotti
 * @version 1.0.0
 */

public class DatiFattura  extends BeanDataSource {
	String this_class = "[DatiFattura::";
  private String idRichiesta;
  private String fatturaSN;
  private String spedizione;
  private String importoSpedizione;
  private String fatturare;
  private String cfPartitaIva;
  private String ragioneSociale;
  private String indirizzo;
  private String cap;
  private String comune;
  private String comuneDesc;
  private String idRichiestaCorrente;
  private String fatturareCorrente;
  private String pec;
  private String cod_destinatario;

  public DatiFattura()
  {
  }
  public DatiFattura(Object dataSource, Autenticazione aut)
  {
    this.setDataSource(dataSource);
    this.setAut(aut);
  }
  /*
  public DatiFattura ( String idRichiesta, String fatturaSN, String spedizione, String fatturare, String cfPartitaIva, String ragioneSociale, String indirizzo, String cap, String comune )
  {
    this.idRichiesta=idRichiesta;
    this.fatturaSN=fatturaSN;
    this.spedizione=spedizione;
    this.fatturare=fatturare;
    this.cfPartitaIva=cfPartitaIva;
    this.ragioneSociale=ragioneSociale;
    this.indirizzo=indirizzo;
    this.cap=cap;
    this.comune=comune;
  }
  */


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
    StringBuffer query = null;
    Statement stmt = null;

    try
    {
      conn = getConnection();
      stmt = conn.createStatement();
      query = new StringBuffer("SELECT DF.*,A.PEC as PEC_ANAG,A.CODICE_DESTINATARIO as CD_ANAG, C.DESCRIZIONE ");
      query.append("FROM DATI_FATTURA DF ");
      query.append("LEFT OUTER JOIN COMUNE C ON (DF.COMUNE=C.CODICE_ISTAT) ");
      query.append("left join etichetta_campione ec on df.ID_RICHIESTA = ec.ID_RICHIESTA ");
      query.append("LEFT JOIN ANAGRAFICA A on (A.ID_ANAGRAFICA=EC.ANAGRAFICA_UTENTE OR A.ID_ANAGRAFICA=EC.ANAGRAFICA_TECNICO OR A.ID_ANAGRAFICA=EC.ANAGRAFICA_PROPRIETARIO) ");
      query.append("WHERE DF.ID_RICHIESTA = ");
      if ( idRichiestaSearch == null)
          query.append(this.getAut().getIdRichiestaCorrente());
        else
          query.append(idRichiestaSearch);
      query.append(" and (");
      query.append("EC.ANAGRAFICA_UTENTE = ");
      query.append(getAut().getUtente().getAnagraficaUtente());
      query.append(" OR EC.ANAGRAFICA_TECNICO = ");
      query.append(getAut().getUtente().getAnagraficaUtente());
      query.append(" OR EC.ANAGRAFICA_PROPRIETARIO = ");
      query.append(getAut().getUtente().getAnagraficaUtente());
      query.append(" OR EC.ANAGRAFICA_PROPRIETARIO = ");
      query.append(getAut().getUtente().getAnagraficaAzienda());
      query.append(")");
      
      //CuneoLogger.debug(this,"\nQuery DatiFattura.select()\n"+query.toString());
      ResultSet rset = stmt.executeQuery(query.toString());
      if (rset.next())
      {
        this.setIdRichiesta(rset.getString("ID_RICHIESTA"));
        this.setFatturaSN(rset.getString("FATTURA_SN"));
        this.setSpedizione(rset.getString("SPEDIZIONE"));
        this.setFatturare(rset.getString("FATTURARE"));
        this.setCfPartitaIva(rset.getString("CF_PARTITA_IVA"));
        this.setRagioneSociale(rset.getString("RAGIONE_SOCIALE"));
        this.setIndirizzo(rset.getString("INDIRIZZO"));
        this.setCap(rset.getString("CAP"));
        this.setComune(rset.getString("COMUNE"));
        this.setComuneDesc(rset.getString("DESCRIZIONE"));
        this.setPec(rset.getString("PEC")!=null&&!rset.getString("PEC").equals("")?rset.getString("PEC"):rset.getString("PEC_ANAG"));
        this.setCod_destinatario(rset.getString("CODICE_DESTINATARIO")!=null&&!rset.getString("CODICE_DESTINATARIO").equals("")?rset.getString("CODICE_DESTINATARIO"):rset.getString("CD_ANAG"));
        this.setImportoSpedizione(rset.getString("IMPORTO_SPEDIZIONE"));
      }
      rset.close();
      stmt.close();
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("select della classe DatiFattura");
      this.getAut().setContenutoQuery(query.toString());
      ex.printStackTrace();
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("select della classe DatiFattura"
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

  /**
   * Metodo insertUpdate()
   * Per il momento cancelliamo sempre il record relativo a ID_RICHIESTA
   * e lo inseriamo subito dopo, in modo da non dover scrivere anche la
   * UPDATE.
   * @return Numero di record inseriti (1 oppure 0)
   * @throws Exception
   * @throws SQLException
   */
  public int insertUpdate() throws Exception, SQLException {
	  String this_method = this_class+"::insertUpdate] ";
    if (!isConnection())
           throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn = null;
    StringBuffer query = null;
    Statement stmt = null;
    try {
      conn = getConnection();
      stmt = conn.createStatement();
      this.setIdRichiesta(String.valueOf(this.getAut().getIdRichiestaCorrente()));
      idRichiestaCorrente = this.getIdRichiesta();
      fatturareCorrente = this.getFatturare();
      query = new StringBuffer("DELETE FROM DATI_FATTURA ");
      query.append("WHERE ID_RICHIESTA = ").append(idRichiestaCorrente);
      CuneoLogger.debug(this_method,"\nQuery DatiFattura.insert() - DELETE\n"+query.toString());
      stmt.executeUpdate(query.toString());
      stmt.close();

      stmt = conn.createStatement();
      query = _costruisciQuery();
      CuneoLogger.debug(this_method,"\nQuery DatiFattura.insert() - INSERT\n"+query.toString());
      int inserted = 0;
      inserted = stmt.executeUpdate(query.toString());
      stmt.close();
      inserted = updateCodDestPec();//aggiorno pec e codice destinatario
      return inserted;
    } catch(java.sql.SQLException ex) {
    	CuneoLogger.debug(this_method,"SQLException -> "+ex);
      this.getAut().setQuery("insert della classe DatiFattura");
      this.getAut().setContenutoQuery(query.toString());
      ex.printStackTrace();
      throw (ex);
    } catch(Exception e) {
    	CuneoLogger.debug(this_method,"Exception -> "+e);
      this.getAut().setQuery("insert della classe DatiFattura: non è una SQLException ma una Exception generica");
      this.getAut().setContenutoQuery(query.toString());
      throw (e);
    } finally {
      if (conn!=null) conn.close();
    }
  }
  
  public int updateCodDestPec() throws Exception, SQLException {
	  String this_method = this_class+"::updateCodDestPec] ";
	    if (!isConnection())
	           throw new Exception("Riferimento a DataSource non inizializzato");
	    Connection conn = null;
	    StringBuffer query = null;
	    Statement stmt = null;
	    int inserted = 0;
	    try {
	      conn = getConnection();
	      stmt = conn.createStatement();
	      this.setIdRichiesta(String.valueOf(this.getAut().getIdRichiestaCorrente()));
	      idRichiestaCorrente = this.getIdRichiesta();
	      CuneoLogger.debug(this_method,"idRichiestaCorrente -> "+idRichiestaCorrente);
	      fatturareCorrente = this.getFatturare();
	      CuneoLogger.debug(this_method,"fatturareCorrente -> "+fatturareCorrente);
	      query = new StringBuffer();
	      if(fatturareCorrente!=null && fatturareCorrente.equals("O")) {
	    	  query.append("UPDATE ORGANIZZAZIONE_PROFESSIONALE ");
		      query.append("SET PEC = ").append((this.getPec()!=null?"'"+this.getPec()+"'":"NULL")).append(", ");
		      query.append(" CODICE_DESTINATARIO = ").append((this.getCod_destinatario()!=null?"'"+this.getCod_destinatario()+"'":"NULL"));
		      query.append("WHERE ID_ORGANIZZAZIONE = ");
		      query.append("(SELECT (CASE WHEN EC.anagrafica_tecnico IS NULL " + 
									" THEN (SELECT op.id_organizzazione "
											+ "FROM anagrafica a "
											+ "LEFT JOIN organizzazione_professionale op ON a.id_organizzazione = op.id_organizzazione "
											+ "WHERE a.id_anagrafica = ec.anagrafica_utente) " + 
									" ELSE (SELECT op.id_organizzazione "
											+ "FROM anagrafica a "
											+ "LEFT JOIN organizzazione_professionale op ON a.id_organizzazione = op.id_organizzazione "
											+ "WHERE a.id_anagrafica = ec.anagrafica_tecnico) END) "
							+ " FROM ETICHETTA_CAMPIONE EC ");	     
		      query.append("WHERE ID_RICHIESTA =").append(idRichiestaCorrente).append(") ");
	      } else {
		      query.append("UPDATE ANAGRAFICA ");
		      query.append("SET PEC = ").append((this.getPec()!=null?"'"+this.getPec()+"'":"NULL")).append(", ");
		      query.append(" CODICE_DESTINATARIO = ").append((this.getCod_destinatario()!=null?"'"+this.getCod_destinatario()+"'":"NULL"));
		      query.append(" WHERE ID_ANAGRAFICA = ");
		      if(fatturareCorrente==null || fatturareCorrente.equals("U")) {
		    	  query.append(" (SELECT anagrafica_utente FROM ETICHETTA_CAMPIONE WHERE ID_RICHIESTA =").append(idRichiestaCorrente).append(") ");
		      }else if(fatturareCorrente.equals("T")) {
		    	  query.append(" (SELECT anagrafica_tecnico FROM ETICHETTA_CAMPIONE WHERE ID_RICHIESTA =").append(idRichiestaCorrente).append(") ");
		      }else if(fatturareCorrente.equals("P")) {
		    	  query.append(" (SELECT anagrafica_proprietario FROM ETICHETTA_CAMPIONE WHERE ID_RICHIESTA =").append(idRichiestaCorrente).append(") ");
		      }
		      
	      }
	      CuneoLogger.debug(this_method,"\nQuery DatiFattura.updateCodDestPec() - UPDATE\n"+query.toString());
	      inserted = stmt.executeUpdate(query.toString());
	      stmt.close();
	    } catch(java.sql.SQLException ex) {
	    	CuneoLogger.debug(this_method,"SQLException -> "+ex);
	      this.getAut().setQuery("updateCodDestPec della classe DatiFattura");
	      this.getAut().setContenutoQuery(query.toString());
	      ex.printStackTrace();
	      throw (ex);
	    } catch(Exception e) {
	    	CuneoLogger.debug(this_method,"Exception -> "+e);
	      this.getAut().setQuery("updateCodDestPec della classe DatiFattura: non è una SQLException ma una Exception generica");
	      this.getAut().setContenutoQuery(query.toString());
	      throw (e);
	    } finally {
	      if (conn!=null) conn.close();
	      return inserted;
	    }
	  }

  private StringBuffer _costruisciQuery() {
    String fatturaSNCorrente = this.getFatturaSN();
    String spedizioneCorrente = this.getSpedizione();
    StringBuffer query = new StringBuffer("INSERT INTO DATI_FATTURA ");
    query.append("( ID_RICHIESTA, FATTURA_SN, SPEDIZIONE, IMPORTO_SPEDIZIONE, FATTURARE, ");
    query.append("CF_PARTITA_IVA, RAGIONE_SOCIALE, INDIRIZZO, CAP, COMUNE, PEC, CODICE_DESTINATARIO ) ");
    query.append("SELECT " + idRichiestaCorrente + " AS ID_RICHIESTA, '"
                 + fatturaSNCorrente + "' AS FATTURA_SN, " );

    if ("S".equals(fatturaSNCorrente)) {
      if ("S".equals(spedizioneCorrente))
        query.append("'S' AS SPEDIZIONE, "
                     + this.getImportoSpedizione() + " AS IMPORTO_SPEDIZIONE, '"
                     + fatturareCorrente + "' AS FATTURARE, " );
      else
        query.append("'N' AS SPEDIZIONE, "
                     + "NULL AS IMPORTO_SPEDIZIONE, '"
                     + fatturareCorrente + "' AS FATTURARE, " );

      /*
       * Per il momento queste cose non le cancello perché un giorno potrebbero servire,
       * ma per adesso non servono più

      if ("U".equals(fatturare)) // Utente
      {
        query.append("A.CODICE_IDENTIFICATIVO AS CF_PARTITA_IVA, ");
        query.append("A.COGNOME_RAGIONE_SOCIALE AS RAGIONE_SOCIALE, ");
        query.append("A.INDIRIZZO AS INDIRIZZO, ");
        query.append("A.CAP AS CAP, ");
        query.append("A.COMUNE_RESIDENZA AS COMUNE ");
        query.append("FROM ANAGRAFICA A, ETICHETTA_CAMPIONE EC ");
        query.append("WHERE EC.ID_RICHIESTA = " + idRichiestaCorrente);
        query.append(" AND A.ID_ANAGRAFICA=EC.ANAGRAFICA_UTENTE");
      }
      else if ("T".equals(fatturare)) // Tecnico
      {
        query.append("A.CODICE_IDENTIFICATIVO AS CF_PARTITA_IVA, ");
        query.append("A.COGNOME_RAGIONE_SOCIALE AS RAGIONE_SOCIALE, ");
        query.append("A.INDIRIZZO AS INDIRIZZO, ");
        query.append("A.CAP AS CAP, ");
        query.append("A.COMUNE_RESIDENZA AS COMUNE ");
        query.append("FROM ANAGRAFICA A, ETICHETTA_CAMPIONE EC ");
        query.append("WHERE EC.ID_RICHIESTA = " + idRichiestaCorrente);
        query.append(" AND A.ID_ANAGRAFICA=EC.ANAGRAFICA_TECNICO");
      }
      else if ("P".equals(fatturare)) // Proprietario
      {
        query.append("A.CODICE_IDENTIFICATIVO AS CF_PARTITA_IVA, ");
        query.append("A.COGNOME_RAGIONE_SOCIALE AS RAGIONE_SOCIALE, ");
        query.append("A.INDIRIZZO AS INDIRIZZO, ");
        query.append("A.CAP AS CAP, ");
        query.append("A.COMUNE_RESIDENZA AS COMUNE ");
        query.append("FROM ANAGRAFICA A, ETICHETTA_CAMPIONE EC ");
        query.append("WHERE EC.ID_RICHIESTA = " + idRichiestaCorrente);
        query.append(" AND A.ID_ANAGRAFICA=EC.ANAGRAFICA_PROPRIETARIO");
      }
      else if ("O".equals(fatturare)) // Organizzazione del tecnico
      {
        query.append("OP.CF_PARTITA_IVA AS CF_PARTITA_IVA, ");
        query.append("OP.RAGIONE_SOCIALE AS RAGIONE_SOCIALE, ");
        query.append("OP.INDIRIZZO AS INDIRIZZO, ");
        query.append("OP.CAP AS CAP, ");
        query.append("OP.COMUNE_RESIDENZA AS COMUNE ");
        query.append("FROM ORGANIZZAZIONE_PROFESSIONALE OP, ANAGRAFICA A, ETICHETTA_CAMPIONE EC ");
        query.append("WHERE EC.ID_RICHIESTA = " + idRichiestaCorrente);
        query.append(" AND A.ID_ANAGRAFICA=EC.ANAGRAFICA_TECNICO ");
        query.append("AND OP.ID_ORGANIZZAZIONE=A.ID_ORGANIZZAZIONE ");
      }
      */

      if ("A".equals(fatturare)) // Altro indirizzo
        query.append("'" + this.getCfPartitaIva() + "' AS CF_PARTITA_IVA, '"
                     + Utili.toVarchar(this.getRagioneSociale()) + "' AS RAGIONE_SOCIALE, '"
                     + Utili.toVarchar(this.getIndirizzo()) + "' AS INDIRIZZO, '"
                     + this.getCap() +"' AS CAP, '"
                     + Utili.toVarchar(this.getComune())+"' AS COMUNE, " );
      else // Per gli altri valori, si recuperano quelli aggiornati
           // da ANAGRAFICA o da ORGANIZZAZIONE_PROFESSIONALE
        query.append("NULL AS CF_PARTITA_IVA, "
                     + "NULL AS RAGIONE_SOCIALE, "
                     + "NULL AS INDIRIZZO, "
                     + "NULL AS CAP, "
                     + "NULL AS COMUNE, " );
      query.append((this.getPec()!=null?"'"+this.getPec()+"'":"NULL")+" AS PEC, ");
      query.append((this.getCod_destinatario()!=null?"'"+this.getCod_destinatario()+"'":"NULL")+" AS CODICE_DESTINATARIO ");
    } else {
        query.append("'N' AS SPEDIZIONE, NULL AS IMPORTO_SPEDIZIONE, "
                    + "NULL AS FATTURARE, NULL AS CF_PARTITA_IVA, "
                    + "NULL AS RAGIONE_SOCIALE, NULL AS INDIRIZZO, "
                    + "NULL AS CAP, NULL AS COMUNE, NULL AS PEC, NULL AS CODICE_DESTINATARIO " );
    }
    return query;
  }

  public String ControllaDati() {
    StringBuffer errore=new StringBuffer("");

    if ("A".equals(this.getFatturare())){
      int lung;
      if (getCfPartitaIva()==null) lung = 0;
      else lung=getCfPartitaIva().length();
      if (lung!=11 && lung!=16)
        errore.append(";1");
      else {
         if (lung==11 && !Utili.controllaPartitaIVA(this.getCfPartitaIva()))
            errore.append(";1");
         if (lung==16 && !Utili.controllaCodiceFiscale(this.getCfPartitaIva()))
            errore.append(";1");
      }

      if (getRagioneSociale()!=null)
        setRagioneSociale(getRagioneSociale().trim());
      if (getRagioneSociale()==null || "".equals(getRagioneSociale()) || getRagioneSociale().length()>60)
        errore.append(";2");
      if (getIndirizzo()!=null)
        setIndirizzo(getIndirizzo().trim());
      if (getIndirizzo()==null || "".equals(getIndirizzo()) || getIndirizzo().length()>40)
        errore.append(";3");
      if (!Utili.controllaCap(getCap()))
        errore.append(";4");
      if (this.getComune()==null || "".equals(this.getComune()))
        errore.append(";5");
      
      if((getCod_destinatario()==null || "".equals(getCod_destinatario()))&&(getPec()==null || "".equals(getPec()))) {
    	  errore.append(";7");
    	  errore.append(";8");
      }
      if(getCod_destinatario()!=null && !"".equals(getCod_destinatario()) && getCod_destinatario().length()<7) 
    	  errore.append(";7");
      if(getPec()!=null && !"".equals(getPec()) && !Utili.controllaMail(getPec(),50)) 
      	  errore.append(";8");
    }
    if ((this.getFatturare()==null || "".equals(this.getFatturare()))
              &&
        ("S".equals(this.getFatturaSN())))
    {
      errore.append(";6");
    }

    /**
    * Se non sono stati trovati errori restituisce null
    */
    if (errore.toString().equals("")) return null;
    else return errore.toString();
  }

  public String getIdRichiesta() {
    return this.idRichiesta;
  }
  public void setIdRichiesta( String newIdRichiesta ) {
    this.idRichiesta = newIdRichiesta;
  }

  public String getFatturaSN() {
    return fatturaSN;
  }
  public void setFatturaSN(String fatturaSN) {
    this.fatturaSN = fatturaSN;
  }

  public String getSpedizione() {
    return this.spedizione;
  }
  public void setSpedizione( String newSpedizione ) {
    this.spedizione = newSpedizione;
  }

  public String getImportoSpedizione() {
    return this.importoSpedizione;
  }
  public void setImportoSpedizione( String newImportoSpedizione ) {
    this.importoSpedizione = newImportoSpedizione;
  }

  public String getFatturare() {
    return this.fatturare;
  }
  public void setFatturare( String newFatturare ) {
    this.fatturare = newFatturare;
  }

  public String getCfPartitaIva() {
    if (null == this.cfPartitaIva) this.cfPartitaIva="";
    return this.cfPartitaIva;
  }
  public void setCfPartitaIva( String newCfPartitaIva ) {
    if (newCfPartitaIva!=null)
      this.cfPartitaIva=newCfPartitaIva.toUpperCase();
    else
      this.cfPartitaIva = newCfPartitaIva;
  }

  public String getRagioneSociale() {
    if (null == this.ragioneSociale) this.ragioneSociale="";
    return this.ragioneSociale;
  }
  public void setRagioneSociale( String newRagioneSociale ) {
    this.ragioneSociale = newRagioneSociale;
  }

  public String getIndirizzo() {
    if (null == this.indirizzo) this.indirizzo="";
    return this.indirizzo;
  }
  public void setIndirizzo( String newIndirizzo ) {
    this.indirizzo = newIndirizzo;
  }

  public String getCap() {
    if (null == this.cap) this.cap="";
    return this.cap;
  }
  public void setCap( String newCap ) {
    this.cap = newCap;
  }

  public String getComune() {
    if (null == this.comune) this.comune="";
    return this.comune;
  }
  public void setComune( String newComune ) {
    this.comune = newComune;
  }
  public void setComuneDesc(String comuneDesc) {
    this.comuneDesc = comuneDesc;
  }
  public String getComuneDesc() {
    if (null == this.comuneDesc) this.comuneDesc="";
    return comuneDesc;
  }
public String getPec() {
	return pec!=null?pec:"";
}
public void setPec(String pec) {
	this.pec = pec;
}
public String getCod_destinatario() {
	return cod_destinatario!=null?cod_destinatario:"";
}
public void setCod_destinatario(String cod_destinatario) {
	this.cod_destinatario = cod_destinatario;
}
}