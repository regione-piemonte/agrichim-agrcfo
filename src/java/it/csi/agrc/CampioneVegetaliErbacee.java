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

public class CampioneVegetaliErbacee  extends Campione
{
  /**
   * Attributi utilizzati per il PDF
   */
  private String specieDesc;
  private String colturaDesc;
  private String macinato;

  public CampioneVegetaliErbacee ()
  {
  }
  public CampioneVegetaliErbacee(Object dataSource, Autenticazione aut)
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
      query.append("SELECT TO_CHAR(CVE.DATA_CAMPIONAMENTO,'DD MM YYYY') ");
      query.append("AS DATA, CVE.CAMPIONE_TERRENO, ");
      if (idRichiestaSearch!=null)
        query.append("SC.DESCRIZIONE AS SPECIE_DESC, CC.DESCRIZIONE AS COLTURA_DESC, ");
      query.append("CVE.ID_SPECIE, SC.ID_COLTURA,CVE.MACINATO FROM ");
      if (idRichiestaSearch!=null)
        query.append("CLASSE_COLTURA CC, ");
      query.append("SPECIE_COLTURA SC, CAMPIONE_VEGETALI_ERBACEE CVE ");
      query.append("WHERE CVE.ID_RICHIESTA = ");
      if (idRichiestaSearch!=null)
        query.append(idRichiestaSearch);
      else
        query.append(this.getIdRichiesta());
      query.append(" AND CVE.ID_SPECIE=SC.ID_SPECIE");
      if (idRichiestaSearch!=null)
        query.append(" AND SC.ID_COLTURA=CC.ID_COLTURA");
      //CuneoLogger.debug(this,query.toString());
      ResultSet rset = stmt.executeQuery(query.toString());
      if (rset.next())
      {
        this.setDataCampionamento(rset.getString("DATA"));
        this.setIdSpecie(rset.getString("ID_SPECIE"));
        this.setCampioneTerreno(rset.getString("CAMPIONE_TERRENO"));
        this.setIdColtura(rset.getString("ID_COLTURA"));
        if (idRichiestaSearch!=null)
        {
          this.setSpecieDesc(rset.getString("SPECIE_DESC"));
          this.setColturaDesc(rset.getString("COLTURA_DESC"));
        }
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
      query = new StringBuffer("INSERT INTO CAMPIONE_VEGETALI_ERBACEE (");
      query.append("ID_RICHIESTA,DATA_CAMPIONAMENTO,ID_SPECIE,CAMPIONE_TERRENO");
      query.append(",MACINATO) VALUES (");
      query.append(this.getIdRichiesta());
      query.append(",to_timestamp('").append(this.getDataCampionamento());
      query.append("','DD/MM/YYYY')");
      query.append(",").append(this.getIdSpecie());
      if (getCampioneTerreno()==null)
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
      query = new StringBuffer("UPDATE CAMPIONE_VEGETALI_ERBACEE ");
      query.append("SET DATA_CAMPIONAMENTO = to_timestamp('");
      query.append(this.getDataCampionamento()).append("','DD/MM/YYYY')");
      query.append(",ID_SPECIE =").append(this.getIdSpecie());
      if (getCampioneTerreno()==null)
        query.append(",CAMPIONE_TERRENO = null ");
      else
        query.append(",CAMPIONE_TERRENO ='").append(Utili.toVarchar(getCampioneTerreno())).append("'");
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
    if (getIdColtura()==null
                ||
        "-1".equals(getIdColtura())
                ||
        "".equals(getIdColtura())
        )
    {
      errore.append(";2");
    }
    if (getIdSpecie()==null
                ||
        "-1".equals(getIdSpecie())
                ||
        "".equals(getIdSpecie())
        )
    {
      errore.append(";3");
    }
    if (getCampioneTerreno()!=null && getCampioneTerreno().length()>20)
      errore.append(";4");
    /**
    * Se non sono stati trovati errori restituisce null
    */
    if (errore.toString().equals("")) return null;
    else return errore.toString();
  }
  public String getColturaDesc()
  {
    return colturaDesc;
  }
  public void setColturaDesc(String colturaDesc)
  {
    this.colturaDesc = colturaDesc;
  }
  public void setSpecieDesc(String specieDesc)
  {
    this.specieDesc = specieDesc;
  }
  public String getSpecieDesc()
  {
    return specieDesc;
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