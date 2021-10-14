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

public class ReazioneSuolo extends BeanDataSource
{
  private long idRichiesta;
  private String phAcqua;
  private String phCloruroPotassio;
  private String phTampone;

  public ReazioneSuolo ()
  {
  }
  public ReazioneSuolo ( long idRichiesta, String phAcqua, String phCloruroPotassio, String phTampone )
  {
    this.idRichiesta=idRichiesta;
    this.phAcqua=phAcqua;
    this.phCloruroPotassio=phCloruroPotassio;
    this.phTampone=phTampone;
  }


  /**
   * Questo metodo va a leggere il record della tabella REAZIONE_SUOLO
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
      query = new StringBuffer("SELECT PH_ACQUA,PH_CLORURO_POTASSIO,PH_TAMPONE ");
      query.append("FROM REAZIONE_SUOLO ");
      query.append("WHERE ID_RICHIESTA = ");
      query.append(getIdRichiesta());
      //CuneoLogger.debug(this,query.toString());
      ResultSet rs=stmt.executeQuery(query.toString());
      String temp;
      if (rs.next())
      {
        temp=rs.getString("PH_ACQUA");
        if (temp!=null) temp=Utili.nf1.format(Double.parseDouble(temp));
        this.setPhAcqua(temp);

        temp=rs.getString("PH_CLORURO_POTASSIO");
        if (temp!=null) temp=Utili.nf1.format(Double.parseDouble(temp));
        this.setPhCloruroPotassio(temp);

        temp=rs.getString("PH_TAMPONE");
        if (temp!=null) temp=Utili.nf1.format(Double.parseDouble(temp));
        this.setPhTampone(temp);

        return true;
      }
      else return false;
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("select della classe ReazioneSuolo");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("select della classe ReazioneSuolo"
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

  public String getPhAcqua()
  {
    return this.phAcqua;
  }
  public void setPhAcqua( String newPhAcqua )
  {
    if (newPhAcqua!=null) phAcqua=newPhAcqua.replace(',','.');
    else this.phAcqua = newPhAcqua;
  }

  public String getPhCloruroPotassio()
  {
    return this.phCloruroPotassio;
  }
  public void setPhCloruroPotassio( String newPhCloruroPotassio )
  {
    if (newPhCloruroPotassio!=null) phCloruroPotassio=newPhCloruroPotassio.replace(',','.');
    else this.phCloruroPotassio = newPhCloruroPotassio;
  }

  public String getPhTampone()
  {
    return this.phTampone;
  }
  public void setPhTampone( String newPhTampone )
  {
    if (newPhTampone!=null) phTampone=newPhTampone.replace(',','.');
    else this.phTampone = newPhTampone;
  }

  public String getPhPDF()
  {
    if (phAcqua==null && phCloruroPotassio==null && phTampone==null) return "";
    else
    {
      String ph;
      if (phAcqua!=null) ph=phAcqua;
      else
      {
        if (phCloruroPotassio!=null) ph=phCloruroPotassio;
        else
        {
          ph=phTampone;
        }
      }
      ph=ph.replace(',','.');
      ph=Utili.nf1.format(Double.parseDouble(ph));
      ph=ph.replace('.',',');
      return ph;
    }
  }
}