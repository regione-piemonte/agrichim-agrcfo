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

public class ConducibilitaSalinita extends BeanDataSource
{
  private long idRichiesta;
  private String conducibilita;

  public ConducibilitaSalinita ()
  {
  }
  public ConducibilitaSalinita ( long idRichiesta, String conducibilita )
  {
    this.idRichiesta=idRichiesta;
    this.conducibilita=conducibilita;
  }

  /**
   * Questo metodo va a leggere il record della tabella CONDUCIBILITA_SALINITA
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
      query = new StringBuffer("SELECT CONDUCIBILITA ");
      query.append("FROM CONDUCIBILITA_SALINITA ");
      query.append("WHERE ID_RICHIESTA = ");
      query.append(getIdRichiesta());
      //CuneoLogger.debug(this,query.toString());
      ResultSet rs=stmt.executeQuery(query.toString());
      if (rs.next())
      {
        this.setConducibilita(rs.getString("CONDUCIBILITA"));
        return true;
      }
      else return false;
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("select della classe ConducibilitaSalinita");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("select della classe ConducibilitaSalinita"
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

  public String getConducibilitaPDF()
  {
    if (conducibilita==null) return "";
    else return conducibilita;
  }

  public String getConducibilita()
  {
    return this.conducibilita;
  }
  public void setConducibilita( String newConducibilita )
  {
    this.conducibilita = newConducibilita;
  }
}