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

public class SostanzaOrganica extends BeanDataSource
{
  private long idRichiesta;
  private String letturaSostanzaOrganica;
  private String pesoCampione;
  private String sostanzaOrganica;
  private String carbonioOrganico;
  private String carbonioOrganicoMetodoAna;
  private String sostanzaOrganicaMetodoAna;

  public SostanzaOrganica ()
  {
  }
  public SostanzaOrganica ( long idRichiesta, String letturaSostanzaOrganica, String pesoCampione, String sostanzaOrganica, String carbonioOrganico, String carbonioOrganicoMetodoAna, String sostanzaOrganicaMetodoAna )
  {
    this.idRichiesta=idRichiesta;
    this.letturaSostanzaOrganica=letturaSostanzaOrganica;
    this.pesoCampione=pesoCampione;
    this.sostanzaOrganica=sostanzaOrganica;
    this.carbonioOrganico=carbonioOrganico;
    this.carbonioOrganicoMetodoAna=carbonioOrganicoMetodoAna;
    this.sostanzaOrganicaMetodoAna=sostanzaOrganicaMetodoAna;
  }

  /**
   * Questo metodo va a leggere il record della tabella SOSTANZA_ORGANICA
   * con idRichiesta uguale a qullo memorizzato nelll'attributo idRichiesta.
   * Il contenuto del record viene memorizzato all'interno del bean
   * @return se trova un record restituisce true, altrimenti false
   * @throws Exception
   * @throws SQLException
   */
  public boolean select() throws Exception, SQLException
  {
    if (!isConnection())
      throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn=null;
    Statement stmt =null;
    StringBuffer query=new StringBuffer("");
    try
    {
      conn=getConnection();
      stmt = conn.createStatement();
      query = new StringBuffer("SELECT LETTURA_SOSTANZA_ORGANICA,PESO_CAMPIONE,");
      query.append("SOSTANZA_ORGANICA,CARBONIO_ORGANICO,");
      query.append("CARBONIO_ORGANICO_METODO_ANA,SOSTANZA_ORGANICA_METODO_ANA ");
      query.append("FROM SOSTANZA_ORGANICA ");
      query.append("WHERE ID_RICHIESTA = ");
      query.append(getIdRichiesta());
      //CuneoLogger.debug(this,query.toString());
      ResultSet rs=stmt.executeQuery(query.toString());
      String temp;
      if (rs.next())
      {
        temp=rs.getString("LETTURA_SOSTANZA_ORGANICA");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setLetturaSostanzaOrganica(temp);

        temp=rs.getString("PESO_CAMPIONE");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setPesoCampione(temp);

        temp=rs.getString("SOSTANZA_ORGANICA");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setSostanzaOrganica(temp);

        temp=rs.getString("CARBONIO_ORGANICO");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setCarbonioOrganico(temp);

        temp=rs.getString("CARBONIO_ORGANICO_METODO_ANA");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setCarbonioOrganicoMetodoAna(temp);

        temp=rs.getString("SOSTANZA_ORGANICA_METODO_ANA");
        if (temp!=null) temp=Utili.nf2.format(Double.parseDouble(temp));
        this.setSostanzaOrganicaMetodoAna(temp);

        return true;
      }
      else return false;
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("select della classe SostanzaOrganica");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("select della classe SostanzaOrganica"
                            +": non è una SQLException ma una Exception"
                            +" generica");
      this.getAut().setContenutoQuery(query.toString());
      throw (e);
    }
    finally
    {
      stmt.close();
      if (conn!=null) conn.close();
    }
  }

  public long getIdRichiesta()
  {
    return this.idRichiesta;
  }
  public void setIdRichiesta( long newIdRichiesta )
  {
    this.idRichiesta = newIdRichiesta;
  }

  public String getLetturaSostanzaOrganica()
  {
    return this.letturaSostanzaOrganica;
  }
  public void setLetturaSostanzaOrganica( String newLetturaSostanzaOrganica )
  {
    if (newLetturaSostanzaOrganica!=null)
    {
      letturaSostanzaOrganica=newLetturaSostanzaOrganica.replace(',','.');
    }
    else letturaSostanzaOrganica = newLetturaSostanzaOrganica;
  }

  public String getPesoCampione()
  {
    return this.pesoCampione;
  }
  public void setPesoCampione( String newPesoCampione )
  {
    if (newPesoCampione!=null) pesoCampione=newPesoCampione.replace(',','.');
    else pesoCampione = newPesoCampione;
  }

  public String getSostanzaOrganicaPDF()
  {
    if (sostanzaOrganica==null) return "";
    else
    {
      sostanzaOrganica=sostanzaOrganica.replace(',','.');
      sostanzaOrganica=Utili.nf2.format(Double.parseDouble(sostanzaOrganica));
      sostanzaOrganica=sostanzaOrganica.replace('.',',');
      return sostanzaOrganica;
    }
  }

  public String getSostanzaOrganica()
  {
    return this.sostanzaOrganica;
  }
  public void setSostanzaOrganica( String newSostanzaOrganica )
  {
    if (newSostanzaOrganica!=null)
      sostanzaOrganica=newSostanzaOrganica.replace(',','.');
    else this.sostanzaOrganica = newSostanzaOrganica;
  }

  public String getCarbonioOrganicoPDF()
  {
    if (carbonioOrganico==null) return "";
    else
    {
      carbonioOrganico=carbonioOrganico.replace(',','.');
      carbonioOrganico=Utili.nf2.format(Double.parseDouble(carbonioOrganico));
      carbonioOrganico=carbonioOrganico.replace('.',',');
      return carbonioOrganico;
    }
  }

  public String getCarbonioOrganico()
  {
    return this.carbonioOrganico;
  }
  public void setCarbonioOrganico( String newCarbonioOrganico )
  {
    if (newCarbonioOrganico!=null)
      carbonioOrganico=newCarbonioOrganico.replace(',','.');
    else this.carbonioOrganico = newCarbonioOrganico;
  }

  public String getCarbonioOrganicoMetodoAnaPDF()
  {
    if (carbonioOrganicoMetodoAna==null) return "";
    else
    {
      carbonioOrganicoMetodoAna=carbonioOrganicoMetodoAna.replace(',','.');
      carbonioOrganicoMetodoAna=Utili.nf2.format(Double.parseDouble(carbonioOrganicoMetodoAna));
      carbonioOrganicoMetodoAna=carbonioOrganicoMetodoAna.replace('.',',');
      return carbonioOrganicoMetodoAna;
    }
  }

  public String getCarbonioOrganicoMetodoAna()
  {
    return this.carbonioOrganicoMetodoAna;
  }
  public void setCarbonioOrganicoMetodoAna( String newCarbonioOrganicoMetodoAna )
  {
    if (newCarbonioOrganicoMetodoAna!=null)
      carbonioOrganicoMetodoAna=newCarbonioOrganicoMetodoAna.replace(',','.');
    else this.carbonioOrganicoMetodoAna = newCarbonioOrganicoMetodoAna;
  }

  public String getSostanzaOrganicaMetodoAnaPDF()
  {
    if (sostanzaOrganicaMetodoAna==null) return "";
    else
    {
      sostanzaOrganicaMetodoAna=sostanzaOrganicaMetodoAna.replace(',','.');
      sostanzaOrganicaMetodoAna=Utili.nf2.format(Double.parseDouble(sostanzaOrganicaMetodoAna));
      sostanzaOrganicaMetodoAna=sostanzaOrganicaMetodoAna.replace('.',',');
      return sostanzaOrganicaMetodoAna;
    }
  }

  public String getSostanzaOrganicaMetodoAna()
  {
    return this.sostanzaOrganicaMetodoAna;
  }
  public void setSostanzaOrganicaMetodoAna( String newSostanzaOrganicaMetodoAna )
  {
    if (newSostanzaOrganicaMetodoAna!=null)
      sostanzaOrganicaMetodoAna=newSostanzaOrganicaMetodoAna.replace(',','.');
    else this.sostanzaOrganicaMetodoAna = newSostanzaOrganicaMetodoAna;
  }
}