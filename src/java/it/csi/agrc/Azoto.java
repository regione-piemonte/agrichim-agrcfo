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

public class Azoto extends BeanDataSource
{
  private long idRichiesta;
  private String azotoTotaleMetodoAna;
  private String azotoKjeldahl;
  private String rapportoCN;
  private SostanzaOrganica sostanzaOrg=new SostanzaOrganica();

  public Azoto ()
  {
  }
  public Azoto ( long idRichiesta, String azotoTotaleMetodoAna, String azotoKjeldahl, String rapportoCN )
  {
    this.idRichiesta=idRichiesta;
    this.azotoTotaleMetodoAna=azotoTotaleMetodoAna;
    this.azotoKjeldahl=azotoKjeldahl;
    this.rapportoCN=rapportoCN;
  }

  /**
   * Questo metodo va a leggere il record della tabella AZOTO
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
      query = new StringBuffer("SELECT AZOTO_TOTALE_METODO_ANA,");
      query.append("AZOTO_KJELDAHL,RAPPORTO_C_N ");
      query.append("FROM AZOTO ");
      query.append("WHERE ID_RICHIESTA = ");
      query.append(getIdRichiesta());
      //CuneoLogger.debug(this,query.toString());
      ResultSet rs=stmt.executeQuery(query.toString());
      String temp;
      if (rs.next())
      {
        temp=rs.getString("AZOTO_TOTALE_METODO_ANA");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setAzotoTotaleMetodoAna(temp);

        temp=rs.getString("AZOTO_KJELDAHL");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setAzotoKjeldahl(temp);

        temp=rs.getString("RAPPORTO_C_N");
        if (temp!=null) temp=Utili.nf1.format(Double.parseDouble(temp));
        this.setRapportoCN(temp);

        return true;
      }
      else return false;
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("select della classe Azoto");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("select della classe Azoto"
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
  throws Exception, SQLException
  {
    idRichiesta = newIdRichiesta;
    /**
    * Leggo i dati dalla tabella SOSTANZA_ORGANICA che mi serviranno per
    * effettuare i calcoli
    */
    sostanzaOrg.setIdRichiesta(idRichiesta);

    if (Utili.POOLMAN)
    {
     //Sono in ambiente TomCat quindi imposto il pool
     //di PoolMan
     sostanzaOrg.setGenericPool(getGenericPool());
    }
    else
    {
     //Sono in ambiente WebLogic quindi imposto
     //il DataSource
     sostanzaOrg.setDataSource(getDataSource());
    }
    sostanzaOrg.setAut(getAut());
    sostanzaOrg.select();
  }

  public String getAzotoTotaleMetodoAna()
  {
    return this.azotoTotaleMetodoAna;
  }
  public void setAzotoTotaleMetodoAna( String newAzotoTotaleMetodoAna )
  {
    if (newAzotoTotaleMetodoAna!=null) azotoTotaleMetodoAna=newAzotoTotaleMetodoAna.replace(',','.');
    else this.azotoTotaleMetodoAna = newAzotoTotaleMetodoAna;
  }

  public String getAzotoPDF()
  {
    if (azotoTotaleMetodoAna==null && azotoKjeldahl==null) return "";
    else
    {
      String azoto;
      if (azotoTotaleMetodoAna==null) azoto=azotoKjeldahl;
      else azoto=azotoTotaleMetodoAna;
      azoto=azoto.replace(',','.');
      azoto=Utili.nf3.format(Double.parseDouble(azoto));
      azoto=azoto.replace('.',',');
      return azoto;
    }
  }

  public String getAzotoKjeldahl()
  {
    return this.azotoKjeldahl;
  }
  public void setAzotoKjeldahl( String newAzotoKjeldahl )
  {
    if (newAzotoKjeldahl!=null) azotoKjeldahl=newAzotoKjeldahl.replace(',','.');
    else this.azotoKjeldahl = newAzotoKjeldahl;
  }

  public String getRapportoCNPDF()
  {
    if (rapportoCN==null) return "";
    else
    {
      rapportoCN=rapportoCN.replace(',','.');
      rapportoCN=Utili.nf1.format(Double.parseDouble(rapportoCN));
      rapportoCN=rapportoCN.replace('.',',');
      return rapportoCN;
    }
  }

  public String getRapportoCN()
  {
    return this.rapportoCN;
  }
  public void setRapportoCN( String newRapportoCN )
  {
    if (newRapportoCN!=null) rapportoCN=newRapportoCN.replace(',','.');
    else this.rapportoCN = newRapportoCN;
  }
}