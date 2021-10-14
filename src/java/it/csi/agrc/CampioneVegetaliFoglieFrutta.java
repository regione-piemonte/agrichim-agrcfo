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

public class CampioneVegetaliFoglieFrutta  extends Campione
{

  private String giacitura;
  private String superficieAppezzamento;
  private String idEsposizione;
  private String scheletro;
  private String altitudineSlm;
  private String etaImpianto;
  private String altraSpecie;
  private String idVarieta;
  private String idInnesto;
  private String idSistemaAllevamento;
  private String altroAllevamento;
  private String sestoImpianto1;
  private String sestoImpianto2;
  private String unitaN;
  private String unitaP2O5;
  private String unitaK2O;
  private String unitaMg;
  private String letamazioneAnno;
  private String tipoConcimazione;
  private String idConcime;
  private String idStadioFenologico;
  private String codiceProduttivita;
  private String macinato;

  public CampioneVegetaliFoglieFrutta ()
  {
  }
  public CampioneVegetaliFoglieFrutta(Object dataSource, Autenticazione aut)
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
      query.append("SELECT TO_CHAR(DATA_CAMPIONAMENTO,'DD MM YYYY') ");
      query.append("AS DATA,GIACITURA, SUPERFICIE_APPEZZAMENTO,");
      query.append("ID_ESPOSIZIONE, SCHELETRO, ALTITUDINE_SLM, ETA_IMPIANTO,");
      query.append("ID_COLTURA, ID_SPECIE, ALTRA_SPECIE, ID_VARIETA,");
      query.append("ID_INNESTO, ID_SISTEMA_ALLEVAMENTO, ALTRO_ALLEVAMENTO,");
      query.append("SESTO_IMPIANTO_1, SESTO_IMPIANTO_2,");
      query.append("UNITA_N, UNITA_P2O5, UNITA_K2O, UNITA_MG,");
      query.append("LETAMAZIONE_ANNO, TIPO_CONCIMAZIONE, ID_CONCIME,");
      query.append("ID_STADIO_FENOLOGICO, CODICE_PRODUTTIVITA,");
      query.append("CAMPIONE_TERRENO,MACINATO ");
      query.append("FROM CAMPIONE_VEGETALI_FOGLIEFRUTTA ");
      query.append("WHERE ID_RICHIESTA =");
      if (idRichiestaSearch!=null)
        query.append(idRichiestaSearch);
      else
        query.append(this.getIdRichiesta());
      //CuneoLogger.debug(this,"Query CampioneVegetaliFoglieFrutta.select()\n"+ query.toString());
      ResultSet rset = stmt.executeQuery(query.toString());
      if (rset.next())
      {
        this.setDataCampionamento(rset.getString("DATA"));
        this.setGiacitura(rset.getString("GIACITURA"));
        this.setSuperficieAppezzamento(rset.getString("SUPERFICIE_APPEZZAMENTO"));
        this.setIdEsposizione(rset.getString("ID_ESPOSIZIONE"));
        this.setScheletro(rset.getString("SCHELETRO"));
        this.setAltitudineSlm(rset.getString("ALTITUDINE_SLM"));
        this.setEtaImpianto(rset.getString("ETA_IMPIANTO"));
        this.setIdColtura(rset.getString("ID_COLTURA"));
        this.setIdSpecie(rset.getString("ID_SPECIE"));
        this.setAltraSpecie(rset.getString("ALTRA_SPECIE"));
        this.setIdVarieta(rset.getString("ID_VARIETA"));
        this.setIdInnesto(rset.getString("ID_INNESTO"));
        this.setIdSistemaAllevamento(rset.getString("ID_SISTEMA_ALLEVAMENTO"));
        this.setAltroAllevamento(rset.getString("ALTRO_ALLEVAMENTO"));
        this.setSestoImpianto1(rset.getString("SESTO_IMPIANTO_1"));
        this.setSestoImpianto2(rset.getString("SESTO_IMPIANTO_2"));
        this.setUnitaN(rset.getString("UNITA_N"));
        this.setUnitaP2O5(rset.getString("UNITA_P2O5"));
        this.setUnitaK2O(rset.getString("UNITA_K2O"));
        this.setUnitaMg(rset.getString("UNITA_MG"));
        this.setLetamazioneAnno(rset.getString("LETAMAZIONE_ANNO"));
        this.setTipoConcimazione(rset.getString("TIPO_CONCIMAZIONE"));
        this.setIdConcime(rset.getString("ID_CONCIME"));
        this.setIdStadioFenologico(rset.getString("ID_STADIO_FENOLOGICO"));
        this.setCodiceProduttivita(rset.getString("CODICE_PRODUTTIVITA"));
        this.setCampioneTerreno(rset.getString("CAMPIONE_TERRENO"));
        this.setMacinato(rset.getString("MACINATO"));
      }
      rset.close();
      stmt.close();
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("select della classe CampioneVegetaliErbacee");
      this.getAut().setContenutoQuery(query.toString());
      ex.printStackTrace();
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("select della classe CampioneVegetaliErbacee"
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

      query = new StringBuffer("INSERT INTO CAMPIONE_VEGETALI_FOGLIEFRUTTA (");
      query.append("ID_RICHIESTA,DATA_CAMPIONAMENTO,");
      query.append("GIACITURA, SUPERFICIE_APPEZZAMENTO, ID_ESPOSIZIONE,");
      query.append("SCHELETRO, ALTITUDINE_SLM, ETA_IMPIANTO,");
      query.append("ID_COLTURA,ID_SPECIE,ALTRA_SPECIE,");
      query.append("ID_VARIETA, ID_INNESTO, ID_SISTEMA_ALLEVAMENTO,");
      query.append("ALTRO_ALLEVAMENTO, SESTO_IMPIANTO_1, SESTO_IMPIANTO_2,");
      query.append("UNITA_N, UNITA_P2O5, UNITA_K2O, UNITA_MG,");
      query.append("LETAMAZIONE_ANNO, TIPO_CONCIMAZIONE, ID_CONCIME,");
      query.append("ID_STADIO_FENOLOGICO, CODICE_PRODUTTIVITA,");
      query.append("CAMPIONE_TERRENO,MACINATO ");

      query.append(") VALUES (");
      query.append(this.getIdRichiesta());
      query.append(",to_timestamp('").append(this.getDataCampionamento());
      query.append("','DD/MM/YYYY')");
      query.append(",'").append(this.getGiacitura());
      if ("-1".equals(getSuperficieAppezzamento()))
        query.append("',null");
      else
        query.append("',").append(getSuperficieAppezzamento());
      if ("-1".equals(this.getIdEsposizione()) || getIdEsposizione()==null)
        query.append(",null");
      else
        query.append(",'").append(getIdEsposizione()).append("'");
      query.append(",'").append(this.getScheletro());
      query.append("',").append(this.getAltitudineSlm());
      query.append(",").append(this.getEtaImpianto());
      query.append(",").append(this.getIdColtura());
      if ("-1".equals(this.getIdSpecie()))
      {
        query.append(",null");
        if (this.getAltraSpecie()==null)
          query.append(",null");
        else
          query.append(",'").append(Utili.toVarchar(getAltraSpecie())).append("'");
      }
      else
      {
        query.append(",").append(getIdSpecie()).append(",null");
      }
      if ("-1".equals(this.getIdVarieta()))
        query.append(",null");
      else
        query.append(",").append(getIdVarieta());

      if ("-1".equals(this.getIdInnesto()))
        query.append(",null");
      else
        query.append(",").append(getIdInnesto());

      if ("-1".equals(this.getIdSistemaAllevamento()))
      {
        query.append(",null");
        if (this.getAltroAllevamento()==null)
          query.append(",null");
        else
          query.append(",'").append(Utili.toVarchar(getAltroAllevamento())).append("'");
      }
      else
      {
        query.append(",").append(getIdSistemaAllevamento()).append(",null");
      }

      if (this.getSestoImpianto1()==null)
        query.append(",null");
      else
        query.append(",").append(getSestoImpianto1());
      if (this.getSestoImpianto2()==null)
        query.append(",null");
      else
        query.append(",").append(getSestoImpianto2());

      if (this.getUnitaN()==null)
        query.append(",null");
      else
        query.append(",").append(getUnitaN());
      if (this.getUnitaP2O5()==null)
        query.append(",null");
      else
        query.append(",").append(getUnitaP2O5());
      if (this.getUnitaK2O()==null)
        query.append(",null");
      else
        query.append(",").append(getUnitaK2O());
      if (this.getUnitaMg()==null)
        query.append(",null");
      else
        query.append(",").append(getUnitaMg());
      if (this.getLetamazioneAnno()==null)
        query.append(",null");
      else
        query.append(",").append(getLetamazioneAnno());
      query.append(",'").append(this.getTipoConcimazione());
      if ("-1".equals(getIdConcime()))
        query.append("',null");
      else
        query.append("',").append(getIdConcime());
      query.append(",").append(this.getIdStadioFenologico());
      if ("-1".equals(getCodiceProduttivita()))
        query.append(",null");
      else
        query.append(",'").append(getCodiceProduttivita()).append("'");
      if (this.getCampioneTerreno()==null)
        query.append(",null");
      else
        query.append(",'").append(Utili.toVarchar(getCampioneTerreno()));
      if ("S".equals(this.getMacinato()))
        query.append(",'S')");
      else
        query.append(",null)");
      //CuneoLogger.debug(this,"insert "+query.toString());
      int inserted = 0;
      inserted = stmt.executeUpdate(query.toString());
      stmt.close();
      /**
       * Modifico i dati relativi alla fasse
       **/
      FasiRichiesta fase=new FasiRichiesta();
      fase.updateFase (this.getIdRichiesta(),5,conn);
      return inserted;
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("insert della classe CampioneVegetaliErbacee");
      this.getAut().setContenutoQuery(query.toString());
      ex.printStackTrace();
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("insert della classe CampioneVegetaliErbacee"
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
      query = new StringBuffer("UPDATE CAMPIONE_VEGETALI_FOGLIEFRUTTA ");
      query.append("SET DATA_CAMPIONAMENTO = to_timestamp('");
      query.append(this.getDataCampionamento()).append("','DD/MM/YYYY')");
      query.append(",GIACITURA = ");
      query.append("'").append(this.getGiacitura());
      query.append("',SUPERFICIE_APPEZZAMENTO = ");
      if ("-1".equals(getSuperficieAppezzamento()))
        query.append("null");
      else
        query.append(getSuperficieAppezzamento());
      query.append(",ID_ESPOSIZIONE = ");
      if ("-1".equals(this.getIdEsposizione()) || getIdEsposizione()==null)
        query.append("null");
      else
        query.append("'").append(getIdEsposizione()).append("'");
      query.append(",SCHELETRO = ");
      query.append("'").append(this.getScheletro());
      query.append("',ALTITUDINE_SLM = ");
      query.append(this.getAltitudineSlm());
      query.append(",ETA_IMPIANTO = ");
      query.append(this.getEtaImpianto());
      query.append(",ID_COLTURA = ");
      query.append(this.getIdColtura());
      query.append(",ID_SPECIE = ");
      if ("-1".equals(this.getIdSpecie()))
      {
        query.append("null");
        query.append(",ALTRA_SPECIE = ");
        if (this.getAltraSpecie()==null)
          query.append("null");
        else
          query.append("'").append(Utili.toVarchar(getAltraSpecie())).append("'");
      }
      else
      {
        query.append(getIdSpecie());
        query.append(",ALTRA_SPECIE = null");
      }
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
      query.append(",ID_SISTEMA_ALLEVAMENTO = ");
      if ("-1".equals(this.getIdSistemaAllevamento()))
      {
        query.append("null");
        query.append(",ALTRO_ALLEVAMENTO = ");
        if (this.getAltroAllevamento()==null)
          query.append("null");
        else
          query.append("'").append(Utili.toVarchar(getAltroAllevamento())).append("'");
      }
      else
      {
        query.append(getIdSistemaAllevamento());
        query.append(",ALTRO_ALLEVAMENTO = null");
      }
      query.append(",SESTO_IMPIANTO_1 = ");
      if (this.getSestoImpianto1()==null)
        query.append("null");
      else
        query.append(getSestoImpianto1());
      query.append(",SESTO_IMPIANTO_2 = ");
      if (this.getSestoImpianto2()==null)
        query.append("null");
      else
        query.append(getSestoImpianto2());
      query.append(",UNITA_N = ");
      if (this.getUnitaN()==null)
        query.append("null");
      else
        query.append(getUnitaN());
      query.append(",UNITA_P2O5 = ");
      if (this.getUnitaP2O5()==null)
        query.append("null");
      else
        query.append(getUnitaP2O5());
      query.append(",UNITA_K2O = ");
      if (this.getUnitaK2O()==null)
        query.append("null");
      else
        query.append(getUnitaK2O());
      query.append(",UNITA_MG = ");
      if (this.getUnitaMg()==null)
        query.append("null");
      else
        query.append(getUnitaMg());
      query.append(",LETAMAZIONE_ANNO = ");
      if (this.getLetamazioneAnno()==null)
        query.append("null");
      else
        query.append(getLetamazioneAnno());
      query.append(",TIPO_CONCIMAZIONE = ");
      query.append("'").append(this.getTipoConcimazione());
      query.append("',ID_CONCIME = ");
      if ("-1".equals(getIdConcime()))
        query.append("null");
      else
        query.append(getIdConcime());
      query.append(",ID_STADIO_FENOLOGICO = ");
      query.append(this.getIdStadioFenologico());
      query.append(",CODICE_PRODUTTIVITA = ");
      if ("-1".equals(getCodiceProduttivita()))
        query.append("null");
      else
        query.append("'").append(getCodiceProduttivita()).append("'");
      query.append(",CAMPIONE_TERRENO = ");
      if (this.getCampioneTerreno()==null)
        query.append("null");
      else
        query.append("'").append(Utili.toVarchar(getCampioneTerreno())).append("'");
      query.append(",MACINATO = ");
      if ("S".equals(this.getMacinato()))
        query.append("'S'");
      else
        query.append("null");
      query.append(" WHERE ID_RICHIESTA =").append(this.getIdRichiesta());
      //CuneoLogger.debug(this,query.toString());
      int updated = 0;
      updated = stmt.executeUpdate(query.toString());
      stmt.close();
      return updated;
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("update della classe CampioneVegetaliErbacee");
      this.getAut().setContenutoQuery(query.toString());
      ex.printStackTrace();
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("update della classe CampioneVegetaliErbacee"
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
    if (getDataCampionamento()==null || !Utili.isDate(getDataCampionamento()))
    {
      errore.append(";1");
    }
    if ("A".equals(getGiacitura()) || "a".equals(getGiacitura()))
    {
      if (getIdEsposizione()==null
                ||
        "-1".equals(getIdEsposizione())
                ||
        "".equals(getIdEsposizione())
        )
      {
        errore.append(";2");
      }
    }
    if (getAltitudineSlm()==null  || !Utili.isNumber(getAltitudineSlm(),0,9999))
      errore.append(";3");
    if (getEtaImpianto()==null || !Utili.isNumber(getEtaImpianto(),0,99))
      errore.append(";4");
    if (getIdColtura()==null
                ||
        "-1".equals(getIdColtura())
                ||
        "".equals(getIdColtura())
        )
    {
      errore.append(";5");
    }
    if ((getIdSpecie()==null
                ||
        "-1".equals(getIdSpecie())
                ||
        "".equals(getIdSpecie()))
                &&
          ( getAltraSpecie()==null
            ||
            "".equals(getAltraSpecie())
            ||
          getAltraSpecie().length()>40)
        )
    {
      errore.append(";6");
    }
    if ((getIdSistemaAllevamento()==null
                ||
        "-1".equals(getIdSistemaAllevamento())
                ||
        "".equals(getIdSistemaAllevamento()))
                &&
          ( getAltroAllevamento()==null
            ||
            "".equals(getAltroAllevamento())
            ||
          getAltroAllevamento().length()>40)
        )
    {
      errore.append(";7");
    }
    if (getSestoImpianto1()!=null && !"".equals(getSestoImpianto1()) &&  !Utili.isDouble(getSestoImpianto1(),0.0,99.99,2))
      errore.append(";8");
    if (getSestoImpianto2()!=null && !"".equals(getSestoImpianto2()) &&  !Utili.isDouble(getSestoImpianto2(),0.0,99.99,2))
      errore.append(";9");
    if (getUnitaN()!=null && !"".equals(getUnitaN()) && !Utili.isNumber(getUnitaN(),0,999999))
      errore.append(";10");
    if (getUnitaP2O5()!=null && !"".equals(getUnitaP2O5()) && !Utili.isNumber(getUnitaP2O5(),0,999999))
      errore.append(";11");
    if (getUnitaK2O()!=null && !"".equals(getUnitaK2O()) && !Utili.isNumber(getUnitaK2O(),0,999999))
      errore.append(";12");
    if (getUnitaMg()!=null && !"".equals(getUnitaMg()) && !Utili.isNumber(getUnitaMg(),0,999999))
      errore.append(";13");
    if (getLetamazioneAnno()!=null && !"".equals(getLetamazioneAnno()) &&  !Utili.isDouble(getLetamazioneAnno(),0.0,99.99,2))
      errore.append(";14");
    if (getTipoConcimazione()==null
                ||
        "-1".equals(getTipoConcimazione())
                ||
        "".equals(getTipoConcimazione())
        )
    {
      errore.append(";15");
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
        errore.append(";16");
      }
    }
    if (getIdStadioFenologico()==null
                ||
        "-1".equals(getIdStadioFenologico())
                ||
        "".equals(getIdStadioFenologico())
        )
    {
      errore.append(";17");
    }
    if (getCampioneTerreno()!=null && getCampioneTerreno().length()>20)
      errore.append(";18");
    /**
    * Se non sono stati trovati errori restituisce null
    */
    if (errore.toString().equals("")) return null;
    else return errore.toString();
  }

  public void setGiacitura(String giacitura)
  {
    this.giacitura = giacitura;
  }
  public String getGiacitura()
  {
    return giacitura;
  }
  public void setSuperficieAppezzamento(String superficieAppezzamento)
  {
    this.superficieAppezzamento = superficieAppezzamento;
  }
  public String getSuperficieAppezzamento()
  {
    return superficieAppezzamento;
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
  public void setAltitudineSlm(String altitudineSlm)
  {
    this.altitudineSlm = altitudineSlm;
  }
  public String getAltitudineSlm()
  {
    return altitudineSlm;
  }
  public void setEtaImpianto(String etaImpianto)
  {
    this.etaImpianto = etaImpianto;
  }
  public String getEtaImpianto()
  {
    return etaImpianto;
  }
  public void setAltraSpecie(String altraSpecie)
  {
    this.altraSpecie = altraSpecie;
  }
  public String getAltraSpecie()
  {
    return altraSpecie;
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
  public void setIdSistemaAllevamento(String idSistemaAllevamento)
  {
    this.idSistemaAllevamento = idSistemaAllevamento;
  }
  public String getIdSistemaAllevamento()
  {
    return idSistemaAllevamento;
  }
  public void setAltroAllevamento(String altroAllevamento)
  {
    this.altroAllevamento = altroAllevamento;
  }
  public String getAltroAllevamento()
  {
    return altroAllevamento;
  }
  public void setSestoImpianto1(String sestoImpianto1)
  {
    if (sestoImpianto1!=null) this.sestoImpianto1=sestoImpianto1.replace(',','.');
    else this.sestoImpianto1 = sestoImpianto1;
  }
  public String getSestoImpianto1()
  {
    return sestoImpianto1;
  }
  public void setSestoImpianto2(String sestoImpianto2)
  {
    if (sestoImpianto2!=null) this.sestoImpianto2=sestoImpianto2.replace(',','.');
    else this.sestoImpianto2 = sestoImpianto2;
  }
  public String getSestoImpianto2()
  {
    return sestoImpianto2;
  }
  public void setUnitaN(String unitaN)
  {
    this.unitaN = unitaN;
  }
  public String getUnitaN()
  {
    return unitaN;
  }
  public void setUnitaP2O5(String unitaP2O5)
  {
    this.unitaP2O5 = unitaP2O5;
  }
  public String getUnitaP2O5()
  {
    return unitaP2O5;
  }
  public void setUnitaK2O(String unitaK2O)
  {
    this.unitaK2O = unitaK2O;
  }
  public String getUnitaK2O()
  {
    return unitaK2O;
  }
  public void setUnitaMg(String unitaMg)
  {
    this.unitaMg = unitaMg;
  }
  public String getUnitaMg()
  {
    return unitaMg;
  }
  public void setLetamazioneAnno(String letamazioneAnno)
  {
    if (letamazioneAnno!=null) this.letamazioneAnno=letamazioneAnno.replace(',','.');
    else this.letamazioneAnno = letamazioneAnno;
  }
  public String getLetamazioneAnno()
  {
    return letamazioneAnno;
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
  public void setIdStadioFenologico(String idStadioFenologico)
  {
    this.idStadioFenologico = idStadioFenologico;
  }
  public String getIdStadioFenologico()
  {
    return idStadioFenologico;
  }
  public void setCodiceProduttivita(String codiceProduttivita)
  {
    this.codiceProduttivita = codiceProduttivita;
  }
  public String getCodiceProduttivita()
  {
    return codiceProduttivita;
  }
  public void setMacinato(String macinato)
  {
    this.macinato = macinato;
  }
  public String getMacinato()
  {
    return macinato;
  }
}