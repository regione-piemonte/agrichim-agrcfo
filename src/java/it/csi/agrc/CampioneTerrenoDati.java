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

public class CampioneTerrenoDati extends BeanDataSource implements Modello
{

  private long idRichiesta;
  private String idProfondita;
  private String nuovoImpianto;
  private String specieAttuale;
  private String speciePrevista;
  private String idVarieta;
  private String idInnesto;
  private String annoImpianto;
  private String idSistemaAllevamento;
  private String produzioneQha;
  private String superficieAppezzamento;
  private String giacitura;
  private String idEsposizione;
  private String scheletro;
  private String percentualePietre;
  private String stoppie;
  private String tipoConcimazione;
  private String idConcime;
  private String idLavorazioneTerreno;
  private String idIrrigazione;
  private String codiceModalitaColtivazione;
  private String idColturaAttuale;
  private String idColturaPrevista;

  public CampioneTerrenoDati ()
  {
  }
  public CampioneTerrenoDati(Object dataSource, Autenticazione aut)
  {
    this.setDataSource(dataSource);
    this.setAut(aut);
  }

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
    try
    {
      conn = getConnection();
      stmt = conn.createStatement();
      query.append("SELECT ID_PROFONDITA, NUOVO_IMPIANTO, ");
      query.append("COLTURA_ATTUALE, COLTURA_PREVISTA, ID_VARIETA,");
      query.append("ID_INNESTO, ANNO_IMPIANTO, ID_SISTEMA_ALLEVAMENTO,");
      query.append("PRODUZIONE_Q_HA, SUPERFICIE_APPEZZAMENTO, GIACITURA,");
      query.append("ID_ESPOSIZIONE, SCHELETRO, PERCENTUALE_PIETRE, STOPPIE,");
      query.append("TIPO_CONCIMAZIONE, ID_CONCIME, ID_LAVORAZIONE_TERRENO,");
      query.append("ID_IRRIGAZIONE, CODICE_MODALITA_COLTIVAZIONE,");
      query.append("SC1.ID_COLTURA AS ID_COLTURA_ATTUALE,");
      query.append("SC2.ID_COLTURA AS ID_COLTURA_PREVISTA ");
      query.append("FROM DATI_CAMPIONE_TERRENO LEFT OUTER JOIN SPECIE_COLTURA SC1 ON (COLTURA_ATTUALE = SC1.ID_SPECIE),");
      query.append("  SPECIE_COLTURA SC2 ");
      query.append("WHERE ID_RICHIESTA =");
      if (idRichiestaSearch!=null)
        query.append(idRichiestaSearch);
      else
        query.append(this.getIdRichiesta());
      query.append(" AND COLTURA_PREVISTA = SC2.ID_SPECIE");
      //CuneoLogger.debug(this,"Query CampioneTerrenoDati.select()\n"+ query.toString());
      ResultSet rset = stmt.executeQuery(query.toString());
      if (rset.next())
      {
        this.setIdProfondita(rset.getString("ID_PROFONDITA"));
        this.setNuovoImpianto(rset.getString("NUOVO_IMPIANTO"));
        this.setSpecieAttuale(rset.getString("COLTURA_ATTUALE"));
        this.setSpeciePrevista(rset.getString("COLTURA_PREVISTA"));
        this.setIdVarieta(rset.getString("ID_VARIETA"));
        this.setIdInnesto(rset.getString("ID_INNESTO"));
        this.setAnnoImpianto(rset.getString("ANNO_IMPIANTO"));
        this.setIdSistemaAllevamento(rset.getString("ID_SISTEMA_ALLEVAMENTO"));
        this.setProduzioneQha(rset.getString("PRODUZIONE_Q_HA"));
        this.setSuperficieAppezzamento(rset.getString("SUPERFICIE_APPEZZAMENTO"));
        this.setGiacitura(rset.getString("GIACITURA"));
        this.setIdEsposizione(rset.getString("ID_ESPOSIZIONE"));
        this.setScheletro(rset.getString("SCHELETRO"));
        this.setPercentualePietre(rset.getString("PERCENTUALE_PIETRE"));
        this.setStoppie(rset.getString("STOPPIE"));
        this.setTipoConcimazione(rset.getString("TIPO_CONCIMAZIONE"));
        this.setIdConcime(rset.getString("ID_CONCIME"));
        this.setIdLavorazioneTerreno(rset.getString("ID_LAVORAZIONE_TERRENO"));
        this.setIdIrrigazione(rset.getString("ID_IRRIGAZIONE"));
        this.setCodiceModalitaColtivazione(rset.getString("CODICE_MODALITA_COLTIVAZIONE"));
        this.setIdColturaAttuale(rset.getString("ID_COLTURA_ATTUALE"));
        this.setIdColturaPrevista(rset.getString("ID_COLTURA_PREVISTA"));
      }
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
    if (!isConnection())
           throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn = null;
    StringBuffer query = null;
    Statement stmt = null;
    try
    {
      conn = getConnection();
      stmt = conn.createStatement();
      query = new StringBuffer("INSERT INTO DATI_CAMPIONE_TERRENO (");
      query.append("ID_RICHIESTA,ID_PROFONDITA,NUOVO_IMPIANTO,");
      query.append("COLTURA_ATTUALE,COLTURA_PREVISTA,ID_VARIETA,ID_INNESTO,");
      query.append("ANNO_IMPIANTO,ID_SISTEMA_ALLEVAMENTO,PRODUZIONE_Q_HA,");
      query.append("SUPERFICIE_APPEZZAMENTO,GIACITURA,ID_ESPOSIZIONE,");
      query.append("SCHELETRO,PERCENTUALE_PIETRE,STOPPIE,TIPO_CONCIMAZIONE,");
      query.append("ID_CONCIME,ID_LAVORAZIONE_TERRENO,");
      query.append("ID_IRRIGAZIONE,CODICE_MODALITA_COLTIVAZIONE ");
      query.append(") VALUES (");
      query.append(this.getIdRichiesta());
      query.append(",").append(this.getIdProfondita());

      if (getNuovoImpianto()==null)
        query.append(",null");
      else
        query.append(",'").append(getNuovoImpianto()).append("'");
      if ("-1".equals(getSpecieAttuale()))
        query.append(",null");
      else
        query.append(",").append(getSpecieAttuale());
      if ("-1".equals(getSpeciePrevista()))
        query.append(",null");
      else
        query.append(",").append(getSpeciePrevista());
      if ("-1".equals(this.getIdVarieta()))
        query.append(",null");
      else
        query.append(",").append(getIdVarieta());
      if ("-1".equals(this.getIdInnesto()))
        query.append(",null");
      else
        query.append(",").append(getIdInnesto());
      if (getAnnoImpianto()==null)
        query.append(",null");
      else
        query.append(",").append(getAnnoImpianto());
      if ("-1".equals(getIdSistemaAllevamento()))
        query.append(",null");
      else
        query.append(",").append(getIdSistemaAllevamento());
      if (getProduzioneQha()==null)
        query.append(",null");
      else
        query.append(",").append(getProduzioneQha());
      if ("-1".equals(getSuperficieAppezzamento()))
        query.append(",null");
      else
        query.append(",").append(getSuperficieAppezzamento());
      query.append(",'").append(this.getGiacitura());
      if ("-1".equals(this.getIdEsposizione()) || this.getIdEsposizione()==null)
        query.append("',null");
      else
        query.append("','").append(getIdEsposizione()).append("'");
      query.append(",'").append(this.getScheletro()).append("'");
      if (getPercentualePietre()==null)
         query.append(",null");
      else
        query.append(",").append(getPercentualePietre());
      query.append(",'").append(this.getStoppie()).append("'");
      query.append(",'").append(this.getTipoConcimazione());
      if ("-1".equals(getIdConcime()))
        query.append("',null");
      else
        query.append("',").append(getIdConcime());
      if ("-1".equals(getIdLavorazioneTerreno()))
        query.append(",null");
      else
        query.append(",").append(getIdLavorazioneTerreno());
      if ("-1".equals(getIdIrrigazione()))
        query.append(",null");
      else
        query.append(",").append(getIdIrrigazione());
      if ("-1".equals(getCodiceModalitaColtivazione()))
        query.append(",null");
      else
        query.append(",'").append(getCodiceModalitaColtivazione()).append("'");
      query.append(")");
      //CuneoLogger.debug(this,"insert "+query.toString());
      int inserted = 0;
      inserted = stmt.executeUpdate(query.toString());
      stmt.close();
      /**
       * Modifico i dati relativi alla fase
       **/
      FasiRichiesta fase=new FasiRichiesta();
      fase.updateFase (this.getIdRichiesta(),5,conn);
      return inserted;
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("insert della classe CampioneTerrenoDati");
      this.getAut().setContenutoQuery(query.toString());
      ex.printStackTrace();
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("insert della classe CampioneTerrenoDati"
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

  public int update()
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
      query = new StringBuffer("UPDATE DATI_CAMPIONE_TERRENO ");
      query.append("SET ID_PROFONDITA = ").append(this.getIdProfondita());

      if (getNuovoImpianto()==null)
        query.append(",NUOVO_IMPIANTO = NULL");
      else
        query.append(",NUOVO_IMPIANTO = '").append(getNuovoImpianto()).append("'");

      query.append(",COLTURA_ATTUALE = ");
      if ("-1".equals(getSpecieAttuale()))
        query.append("null");
      else
        query.append(getSpecieAttuale());
      query.append(",COLTURA_PREVISTA = ");
      if ("-1".equals(getSpeciePrevista()))
        query.append("null");
      else
        query.append(getSpeciePrevista());
      query.append(",ID_VARIETA = ");
      if ("-1".equals(this.getIdVarieta()))
        query.append("null");
      else
        query.append(getIdVarieta());
      query.append(",ID_INNESTO = ");
      if ("-1".equals(this.getIdInnesto()))
        query.append("null");
      else
        query.append(getIdInnesto());
      query.append(",ANNO_IMPIANTO = ");
      if (getAnnoImpianto()==null)
        query.append("null");
      else
        query.append(getAnnoImpianto());
      query.append(",ID_SISTEMA_ALLEVAMENTO = ");
      if ("-1".equals(getIdSistemaAllevamento()))
        query.append("null");
      else
        query.append(getIdSistemaAllevamento());
      query.append(",PRODUZIONE_Q_HA = ");
      if (getProduzioneQha()==null)
        query.append("null");
      else
        query.append(getProduzioneQha());
      query.append(",SUPERFICIE_APPEZZAMENTO = ");
      if ("-1".equals(getSuperficieAppezzamento()))
        query.append("null");
      else
        query.append(getSuperficieAppezzamento());
      query.append(",GIACITURA = ");
      query.append("'").append(this.getGiacitura());
      query.append("',ID_ESPOSIZIONE = ");
      if ("-1".equals(this.getIdEsposizione()) || this.getIdEsposizione()==null)
        query.append("null");
      else
        query.append("'").append(getIdEsposizione()).append("'");
      query.append(",SCHELETRO = ");
      query.append("'").append(this.getScheletro()).append("'");
      query.append(",PERCENTUALE_PIETRE = ");
      if (getPercentualePietre()==null)
         query.append("null");
      else
        query.append(getPercentualePietre());
      query.append(",STOPPIE = '").append(this.getStoppie()).append("'");
      query.append(",TIPO_CONCIMAZIONE = ");
      query.append("'").append(this.getTipoConcimazione());
      query.append("',ID_CONCIME = ");
      if ("-1".equals(getIdConcime()))
        query.append("null");
      else
        query.append(getIdConcime());
      query.append(",ID_LAVORAZIONE_TERRENO = ");
      if ("-1".equals(getIdLavorazioneTerreno()))
        query.append("null");
      else
        query.append(getIdLavorazioneTerreno());
      query.append(",ID_IRRIGAZIONE = ");
      if ("-1".equals(getIdIrrigazione()))
        query.append("null");
      else
        query.append(getIdIrrigazione());
      query.append(",CODICE_MODALITA_COLTIVAZIONE = ");
      if ("-1".equals(getCodiceModalitaColtivazione()))
        query.append("null");
      else
        query.append("'").append(getCodiceModalitaColtivazione()).append("'");
      query.append(" WHERE ID_RICHIESTA =").append(this.getIdRichiesta());
      //CuneoLogger.debug(this,"Update: "+query.toString());
      int updated = 0;
      updated = stmt.executeUpdate(query.toString());
      stmt.close();
      return updated;
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("update della classe CampioneTerrenoDati");
      this.getAut().setContenutoQuery(query.toString());
      ex.printStackTrace();
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("update della classe CampioneTerrenoDati"
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
   * Questa funzione controlla che i dati che verranno inseriti o modificati nel
   * database siano consistenti
   * */
  public String ControllaDati()
  {
    StringBuffer errore=new StringBuffer("");
    if (getIdProfondita()==null
                ||
        "-1".equals(getIdProfondita())
                ||
        "".equals(getIdProfondita())
        )
    {
      errore.append(";1");
    }
    if (getIdColturaPrevista()==null
                ||
        "-1".equals(getIdColturaPrevista())
                ||
        "".equals(getIdColturaPrevista())
        )
    {
      errore.append(";2");
    }
    if (getSpeciePrevista()==null
                ||
        "-1".equals(getSpeciePrevista())
                ||
        "".equals(getSpeciePrevista())
        )
    {
      errore.append(";3");
    }
    if (getAnnoImpianto()!=null && !"".equals(getAnnoImpianto()) && !Utili.isNumber(getAnnoImpianto(),0,9999))
      errore.append(";4");
    /*if (getSuperficieAppezzamento()==null
                ||
        "-1".equals(getSuperficieAppezzamento())
                ||
        "".equals(getSuperficieAppezzamento())
        )
    {
      errore.append(";5");
    }*/
    if (getProduzioneQha()!=null && !"".equals(getProduzioneQha()) &&  !Utili.isDouble(getProduzioneQha(),0.0,999.99,2))
      errore.append(";6");
    /*if ("A".equals(getGiacitura()) || "a".equals(getGiacitura()))
    {
      if (getIdEsposizione()==null
                ||
        "-1".equals(getIdEsposizione())
                ||
        "".equals(getIdEsposizione())
        )
      {
        errore.append(";7");
      }
    }*/
    if ("S".equals(getScheletro()) || "s".equals(getScheletro()))
    {
      if (getPercentualePietre()==null || "".equals(getPercentualePietre()) || !Utili.isNumber(getPercentualePietre(),0,100))
        errore.append(";8");
    }
    if (getTipoConcimazione()==null
                ||
        "-1".equals(getTipoConcimazione())
                ||
        "".equals(getTipoConcimazione())
        )
    {
      errore.append(";9");
    }
    if ("S".equals(getTipoConcimazione())  || "s".equals(getTipoConcimazione())
                          ||
        "a".equals(getTipoConcimazione()) || "A".equals(getTipoConcimazione())
        )
    {
      if (getIdConcime()==null
                ||
        "-1".equals(getIdConcime())
                ||
        "".equals(getIdConcime())
        )
      {
        errore.append(";10");
      }
    }
    if (getCodiceModalitaColtivazione()==null
                ||
        "-1".equals(getCodiceModalitaColtivazione())
                ||
        "".equals(getCodiceModalitaColtivazione())
        )
      {
        errore.append(";11");
      }

    /**
    * Se non sono stati trovati errori restituisce null
    */
    if (errore.toString().equals("")) return null;
    else return errore.toString();
  }

  public void setIdRichiesta(long idRichiesta)
  {
    this.idRichiesta = idRichiesta;
  }
  public long getIdRichiesta()
  {
    return idRichiesta;
  }
  public void setIdProfondita(String idProfondita)
  {
    this.idProfondita = idProfondita;
  }
  public String getIdProfondita()
  {
    return idProfondita;
  }
  public void setNuovoImpianto(String nuovoImpianto)
  {
    this.nuovoImpianto = nuovoImpianto;
  }
  public String getNuovoImpianto()
  {
    return nuovoImpianto;
  }
  public void setSpecieAttuale(String specieAttuale)
  {
    this.specieAttuale = specieAttuale;
  }
  public String getSpecieAttuale()
  {
    return specieAttuale;
  }
  public void setSpeciePrevista(String speciePrevista)
  {
    this.speciePrevista = speciePrevista;
  }
  public String getSpeciePrevista()
  {
    return speciePrevista;
  }
  public void setIdVarieta(String idVarieta)
  {
    this.idVarieta = idVarieta;
  }
  public String getIdVarieta()
  {
    return idVarieta;
  }
  public void setIdInnesto(String idInnesto)
  {
    this.idInnesto = idInnesto;
  }
  public String getIdInnesto()
  {
    return idInnesto;
  }
  public void setAnnoImpianto(String annoImpianto)
  {
    this.annoImpianto = annoImpianto;
  }
  public String getAnnoImpianto()
  {
    return annoImpianto;
  }
  public void setIdSistemaAllevamento(String idSistemaAllevamento)
  {
    this.idSistemaAllevamento = idSistemaAllevamento;
  }
  public String getIdSistemaAllevamento()
  {
    return idSistemaAllevamento;
  }
  public void setProduzioneQha(String produzioneQha)
  {
    if (produzioneQha!=null) this.produzioneQha=produzioneQha.replace(',','.');
    else this.produzioneQha = produzioneQha;
  }
  public String getProduzioneQha()
  {
    return produzioneQha;
  }
  public void setSuperficieAppezzamento(String superficieAppezzamento)
  {
    this.superficieAppezzamento = superficieAppezzamento;
  }
  public String getSuperficieAppezzamento()
  {
    return superficieAppezzamento;
  }
  public void setGiacitura(String giacitura)
  {
    this.giacitura = giacitura;
  }
  public String getGiacitura()
  {
    return giacitura;
  }
  public void setIdEsposizione(String idEsposizione)
  {
    this.idEsposizione = idEsposizione;
  }
  public String getIdEsposizione()
  {
    return idEsposizione;
  }
  public void setScheletro(String scheletro)
  {
    this.scheletro = scheletro;
  }
  public String getScheletro()
  {
    return scheletro;
  }
  public void setPercentualePietre(String percentualePietre)
  {
    this.percentualePietre = percentualePietre;
  }
  public String getPercentualePietre()
  {
    return percentualePietre;
  }
  public void setStoppie(String stoppie)
  {
    this.stoppie = stoppie;
  }
  public String getStoppie()
  {
    return stoppie;
  }
  public void setTipoConcimazione(String tipoConcimazione)
  {
    this.tipoConcimazione = tipoConcimazione;
  }
  public String getTipoConcimazione()
  {
    return tipoConcimazione;
  }
  public void setIdConcime(String idConcime)
  {
    this.idConcime = idConcime;
  }
  public String getIdConcime()
  {
    return idConcime;
  }
  public void setIdLavorazioneTerreno(String idLavorazioneTerreno)
  {
    this.idLavorazioneTerreno = idLavorazioneTerreno;
  }
  public String getIdLavorazioneTerreno()
  {
    return idLavorazioneTerreno;
  }
  public void setIdIrrigazione(String idIrrigazione)
  {
    this.idIrrigazione = idIrrigazione;
  }
  public String getIdIrrigazione()
  {
    return idIrrigazione;
  }
  public void setCodiceModalitaColtivazione(String codiceModalitaColtivazione)
  {
    this.codiceModalitaColtivazione = codiceModalitaColtivazione;
  }
  public String getCodiceModalitaColtivazione()
  {
    return codiceModalitaColtivazione;
  }
  public void setIdColturaAttuale(String idColturaAttuale)
  {
    this.idColturaAttuale = idColturaAttuale;
  }
  public String getIdColturaAttuale()
  {
    return idColturaAttuale;
  }
  public void setIdColturaPrevista(String idColturaPrevista)
  {
    this.idColturaPrevista = idColturaPrevista;
  }
  public String getIdColturaPrevista()
  {
    return idColturaPrevista;
  }

}