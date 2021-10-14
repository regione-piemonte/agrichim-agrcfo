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

public class OrganizzazioneProfessionale  extends BeanDataSource
{

	public OrganizzazioneProfessionale(){}
	
  public OrganizzazioneProfessionale(Object dataSource, Autenticazione aut)
  {
    this.setDataSource(dataSource);
    this.setAut(aut);
  }
  /*
  public OrganizzazioneProfessionale ( String idOrganizzazione,
    String idTipoOrganizzazione, String cfPartitaIva, String ragioneSociale,
    String sedeTerritoriale, String indirizzo, String cap, String comuneResidenza,
    String telefono, String fax, String email )
  {
    this.idOrganizzazione=idOrganizzazione;
    this.idTipoOrganizzazione=idTipoOrganizzazione;
    this.cfPartitaIva=cfPartitaIva;
    this.ragioneSociale=ragioneSociale;
    this.sedeTerritoriale=sedeTerritoriale;
    this.indirizzo=indirizzo;
    this.cap=cap;
    this.comuneResidenza=comuneResidenza;
    this.telefono=telefono;
    this.fax=fax;
    this.email=email;
  }
  */

  private String idOrganizzazione;
  private String idTipoOrganizzazione;
  private String tipoOrganizzazione;
  private String cfPartitaIva;
  private String ragioneSociale;
  private String sedeTerritoriale;
  private String indirizzo;
  private String cap;
  private String comune;
  private String siglaProvincia;
  private String telefono;
  private String fax;
  private String email;

  public void select(String idOrganizzazione)
  throws Exception, SQLException
  {
    if (idOrganizzazione==null)
      return;
    if (!isConnection())
      throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn=null;
    StringBuffer query=new StringBuffer("");
    try
    {
      conn=getConnection();
      Statement stmt = conn.createStatement();

      query = new StringBuffer("SELECT ID_ORGANIZZAZIONE, OP.ID_TIPO_ORGANIZZAZIONE, ");
      query.append("CF_PARTITA_IVA, RAGIONE_SOCIALE, SEDE_TERRITORIALE, INDIRIZZO,");
      query.append("OP.CAP, C.DESCRIZIONE AS COMUNE, P.SIGLA_PROVINCIA, ");
      query.append("TELEFONO, FAX, EMAIL, T.DESCRIZIONE AS TIPO_ORGANIZZAZIONE ");
      query.append("FROM TIPO_ORGANIZZAZIONE T, PROVINCIA P, COMUNE C, ");
      query.append("ORGANIZZAZIONE_PROFESSIONALE OP ");
      query.append("WHERE ID_ORGANIZZAZIONE=").append(idOrganizzazione);
      query.append(" AND OP.COMUNE_RESIDENZA=C.CODICE_ISTAT");
      query.append(" AND C.PROVINCIA=P.ID_PROVINCIA");
      query.append(" AND OP.ID_TIPO_ORGANIZZAZIONE=T.ID_TIPO_ORGANIZZAZIONE");
      //CuneoLogger.debug(this,query.toString());
      ResultSet rset = stmt.executeQuery(query.toString());
      if (rset.next())
      {
        this.setIdOrganizzazione(rset.getString("ID_ORGANIZZAZIONE"));
        this.setIdTipoOrganizzazione(rset.getString("ID_TIPO_ORGANIZZAZIONE"));
        this.setTipoOrganizzazione(rset.getString("TIPO_ORGANIZZAZIONE"));
        this.setRagioneSociale(rset.getString("RAGIONE_SOCIALE"));
        this.setSedeTerritoriale(rset.getString("SEDE_TERRITORIALE"));
        this.setIndirizzo(rset.getString("INDIRIZZO"));
        this.setCap(rset.getString("CAP"));
        this.setComune(rset.getString("COMUNE"));
        this.setSiglaProvincia(rset.getString("SIGLA_PROVINCIA"));
        this.setTelefono(rset.getString("TELEFONO"));
        this.setFax(rset.getString("FAX"));
        this.setEmail(rset.getString("EMAIL"));
        this.setCfPartitaIva(rset.getString("CF_PARTITA_IVA"));
      }
      rset.close();
      stmt.close();
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("select della classe OrganizzazioneProfessionale");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("select della classe OrganizzazioneProfessionale"
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

  public String getIdOrganizzazione()
  {
    return this.idOrganizzazione;
  }
  public void setIdOrganizzazione( String newIdOrganizzazione )
  {
    this.idOrganizzazione = newIdOrganizzazione;
  }

  public String getIdTipoOrganizzazione()
  {
    return this.idTipoOrganizzazione;
  }
  public void setIdTipoOrganizzazione( String newIdTipoOrganizzazione )
  {
    this.idTipoOrganizzazione = newIdTipoOrganizzazione;
  }

  public String getCfPartitaIva()
  {
    return this.cfPartitaIva;
  }
  public void setCfPartitaIva( String newCfPartitaIva )
  {
    this.cfPartitaIva = newCfPartitaIva;
  }

  public String getRagioneSociale()
  {
    return this.ragioneSociale;
  }
  public void setRagioneSociale( String newRagioneSociale )
  {
    this.ragioneSociale = newRagioneSociale;
  }

  public String getSedeTerritoriale()
  {
    return this.sedeTerritoriale;
  }
  public void setSedeTerritoriale( String newSedeTerritoriale )
  {
    this.sedeTerritoriale = newSedeTerritoriale;
  }

  public String getIndirizzo()
  {
    return this.indirizzo;
  }
  public void setIndirizzo( String newIndirizzo )
  {
    this.indirizzo = newIndirizzo;
  }

  public String getCap()
  {
    return this.cap;
  }
  public void setCap( String newCap )
  {
    this.cap = newCap;
  }


  public String getTelefono()
  {
    return this.telefono;
  }
  public void setTelefono( String newTelefono )
  {
    this.telefono = newTelefono;
  }

  public String getFax()
  {
    return this.fax;
  }
  public void setFax( String newFax )
  {
    this.fax = newFax;
  }

  public String getEmail()
  {
    return this.email;
  }
  public void setEmail( String newEmail )
  {
    this.email = newEmail;
  }
  public String getComune()
  {
    return comune;
  }
  public void setComune(String comune)
  {
    this.comune = comune;
  }
  public void setSiglaProvincia(String siglaProvincia)
  {
    this.siglaProvincia = siglaProvincia;
  }
  public String getSiglaProvincia()
  {
    return siglaProvincia;
  }
  public String getTipoOrganizzazione()
  {
    return tipoOrganizzazione;
  }
  public void setTipoOrganizzazione(String tipoOrganizzazione)
  {
    this.tipoOrganizzazione = tipoOrganizzazione;
  }
}