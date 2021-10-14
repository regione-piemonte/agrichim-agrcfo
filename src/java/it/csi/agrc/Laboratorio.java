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

public class Laboratorio extends BeanDataSource
{
  public Laboratorio()
  {
  }
  public Laboratorio(Object dataSource, Autenticazione aut)
  {
    this.setDataSource(dataSource);
    this.setAut(aut);
  }

  private String codiceLaboratorio;
  private String sede;
  private String descrizione;
  private String indirizzo;
  private String cap;
  private String comune;
  private String riferimenti;
  private String comuneDescr;
  private String siglaProv;
  private StringBuffer indirizzoPdf;




  /*public void select(String codLab)
  throws Exception, SQLException
  {
    if (!isConnection())
      throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn=null;
    StringBuffer query=new StringBuffer("");
    try
    {
      conn=getConnection();
      Statement stmt = conn.createStatement();
      query = new StringBuffer("SELECT L.CODICE_LABORATORIO, L.SEDE, ");
      query.append("L.DESCRIZIONE, L.INDIRIZZO, L.CAP, L.COMUNE, L.RIFERIMENTI, ");
      query.append("C.DESCRIZIONE AS COMUNE_DESCR, P.SIGLA_PROVINCIA SIGLA_PROV ");
      query.append("FROM PROVINCIA P, COMUNE C, LABORATORIO L ");
      if (codLab != null)
      {
        query.append("WHERE L.CODICE_LABORATORIO='");
        query.append(codLab);
      }
      else
      {
        query.append("WHERE L.SEDE='S");
      }
      query.append("' AND C.CODICE_ISTAT=L.COMUNE AND P.ID_PROVINCIA=C.PROVINCIA");
      //CuneoLogger.debug(this,query.toString());
      ResultSet rset = stmt.executeQuery(query.toString());
      if (rset.next())
      {
        this.setCodiceLaboratorio(rset.getString("CODICE_LABORATORIO"));
        this.setSede(rset.getString("SEDE"));
        this.setDescrizione(rset.getString("DESCRIZIONE"));
        this.setIndirizzo(rset.getString("INDIRIZZO"));
        this.setCap(rset.getString("CAP"));
        this.setComune(rset.getString("COMUNE"));
        this.setRiferimenti(rset.getString("RIFERIMENTI"));
        this.setComuneDescr(rset.getString("COMUNE_DESCR"));
        this.setSiglaProv(rset.getString("SIGLA_PROV"));

        indirizzoPdf = new StringBuffer();
        indirizzoPdf.append(this.getIndirizzo()).append(" - ");
        indirizzoPdf.append(this.getCap()).append(" ").append(this.getComuneDescr());
        indirizzoPdf.append(" (").append(this.getSiglaProv()).append(")\n");
        indirizzoPdf.append(this.getRiferimenti()).append("\n");
        indirizzoPdf.append("Partita IVA: 02843860012");
      }
      stmt.close();
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("select della classe Laboratorio");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("select della classe Laboratorio"
                            +": non è una SQLException ma una Exception"
                            +" generica");
      this.getAut().setContenutoQuery(query.toString());
      throw (e);
    }
    finally
    {
      if (conn!=null) conn.close();
    }
  }*/


  public void select(String codLab,String partitaIvaLab)
  throws Exception, SQLException
  {
    if (!isConnection())
      throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn=null;
    StringBuffer query=new StringBuffer("");
    try
    {
      conn=getConnection();
      Statement stmt = conn.createStatement();
      query = new StringBuffer("SELECT L.CODICE_LABORATORIO, L.SEDE, ");
      query.append("L.DESCRIZIONE, L.INDIRIZZO, L.CAP, L.COMUNE, L.RIFERIMENTI, ");
      query.append("C.DESCRIZIONE AS COMUNE_DESCR, P.SIGLA_PROVINCIA SIGLA_PROV ");
      query.append("FROM PROVINCIA P, COMUNE C, LABORATORIO L ");
      if (codLab != null)
      {
        query.append("WHERE L.CODICE_LABORATORIO='");
        query.append(codLab);
      }
      else
      {
        query.append("WHERE L.SEDE='S");
      }
      query.append("' AND C.CODICE_ISTAT=L.COMUNE AND P.ID_PROVINCIA=C.PROVINCIA");
      //CuneoLogger.debug(this,query.toString());
      ResultSet rset = stmt.executeQuery(query.toString());
      indirizzoPdf = new StringBuffer("");
      if (rset.next())
      {
        this.setCodiceLaboratorio(rset.getString("CODICE_LABORATORIO"));
        this.setSede(rset.getString("SEDE"));
        this.setDescrizione(rset.getString("DESCRIZIONE"));
        this.setIndirizzo(rset.getString("INDIRIZZO"));
        this.setCap(rset.getString("CAP"));
        this.setComune(rset.getString("COMUNE"));
        this.setRiferimenti(rset.getString("RIFERIMENTI"));
        this.setComuneDescr(rset.getString("COMUNE_DESCR"));
        this.setSiglaProv(rset.getString("SIGLA_PROV"));

        indirizzoPdf.append(this.getIndirizzo()).append(" - ");
        indirizzoPdf.append(this.getCap()).append(" ").append(this.getComuneDescr());
        indirizzoPdf.append(" (").append(this.getSiglaProv()).append(")");
        indirizzoPdf.append("\n");
        /*if (codLab == null)
          indirizzoPdf.append("\n");
        else
          indirizzoPdf.append(" - ");*/
        indirizzoPdf.append(this.getRiferimenti());
        if (codLab == null)
          indirizzoPdf.append(partitaIvaLab);
        else
          indirizzoPdf.append(" ");
      }
      stmt.close();
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("select della classe Laboratorio");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("select della classe Laboratorio"
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

  public String getCap()
  {
    return cap;
  }
  public void setCap(String cap)
  {
    this.cap = cap;
  }
  public String getCodiceLaboratorio()
  {
    return codiceLaboratorio;
  }
  public void setCodiceLaboratorio(String codiceLaboratorio)
  {
    this.codiceLaboratorio = codiceLaboratorio;
  }
  public String getComune()
  {
    return comune;
  }
  public void setComune(String comune)
  {
    this.comune = comune;
  }
  public String getDescrizione()
  {
    return descrizione;
  }
  public void setDescrizione(String descrizione)
  {
    this.descrizione = descrizione;
  }
  public String getIndirizzo()
  {
    return indirizzo;
  }
  public void setIndirizzo(String indirizzo)
  {
    this.indirizzo = indirizzo;
  }
  public String getRiferimenti()
  {
    return riferimenti;
  }
  public void setRiferimenti(String riferimenti)
  {
    this.riferimenti = riferimenti;
  }
  public String getSede()
  {
    return sede;
  }
  public void setSede(String sede)
  {
    this.sede = sede;
  }
  public String getComuneDescr()
  {
    return comuneDescr;
  }
  public void setComuneDescr(String comuneDescr)
  {
    this.comuneDescr = comuneDescr;
  }
  public String getSiglaProv()
  {
    return siglaProv;
  }
  public void setSiglaProv(String siglaProv)
  {
    this.siglaProv = siglaProv;
  }
  public String getIndirizzoPdf()
  {
    return indirizzoPdf.toString();
  }
}
