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

public class FosforoMetodoOLSEN extends BeanDataSource
{
  private long idRichiesta;
  private String letturaFosforo;
  private String fosforoAssimilabile;
  private String anidrideFosforica;

  public FosforoMetodoOLSEN ()
  {
  }
  public FosforoMetodoOLSEN ( long idRichiesta, String letturaFosforo, String fosforoAssimilabile, String anidrideFosforica )
  {
    this.idRichiesta=idRichiesta;
    this.letturaFosforo=letturaFosforo;
    this.fosforoAssimilabile=fosforoAssimilabile;
    this.anidrideFosforica=anidrideFosforica;
  }

  /**
   * Questo metodo va a leggere il record della tabella FOSFORO_METODO_OLSEN
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
      query = new StringBuffer("SELECT LETTURA_FOSFORO, FOSFORO_ASSIMILABILE,");
      query.append("ANIDRIDE_FOSFORICA ");
      query.append("FROM FOSFORO_METODO_OLSEN ");
      query.append("WHERE ID_RICHIESTA = ");
      query.append(getIdRichiesta());
      //CuneoLogger.debug(this,query.toString());
      ResultSet rs=stmt.executeQuery(query.toString());
      String temp;
      if (rs.next())
      {
        temp=rs.getString("LETTURA_FOSFORO");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setLetturaFosforo(temp);

        temp=rs.getString("FOSFORO_ASSIMILABILE");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setFosforoAssimilabile(temp);

        temp=rs.getString("ANIDRIDE_FOSFORICA");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setAnidrideFosforica(temp);

        return true;
      }
      else return false;
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("select della classe FosforoMetodoOLSEN");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("select della classe FosforoMetodoOLSEN"
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

  public String getLetturaFosforo()
  {
    return this.letturaFosforo;
  }
  public void setLetturaFosforo( String newLetturaFosforo )
  {
    if (newLetturaFosforo!=null) letturaFosforo=newLetturaFosforo.replace(',','.');
    else this.letturaFosforo = newLetturaFosforo;
  }

  public String getFosforoAssimilabilePDF()
  {
    if (fosforoAssimilabile==null) return "";
    else
    {
      fosforoAssimilabile=fosforoAssimilabile.replace(',','.');
      fosforoAssimilabile=Utili.nf0.format(Double.parseDouble(fosforoAssimilabile));
      fosforoAssimilabile=fosforoAssimilabile.replace('.',',');
      return fosforoAssimilabile;
    }
  }

  public String getFosforoAssimilabile()
  {
    return this.fosforoAssimilabile;
  }
  public void setFosforoAssimilabile( String newFosforoAssimilabile )
  {
    if (newFosforoAssimilabile!=null) fosforoAssimilabile=newFosforoAssimilabile.replace(',','.');
    else this.fosforoAssimilabile = newFosforoAssimilabile;
  }

  public String getAnidrideFosforicaPDF()
  {
    if (anidrideFosforica==null) return "";
    else
    {
      anidrideFosforica=anidrideFosforica.replace(',','.');
      anidrideFosforica=Utili.nf0.format(Double.parseDouble(anidrideFosforica));
      anidrideFosforica=anidrideFosforica.replace('.',',');
      return anidrideFosforica;
    }
  }

  public String getAnidrideFosforica()
  {
    return this.anidrideFosforica;
  }
  public void setAnidrideFosforica( String newAnidrideFosforica )
  {
    if (newAnidrideFosforica!=null) anidrideFosforica=newAnidrideFosforica.replace(',','.');
    else this.anidrideFosforica = newAnidrideFosforica;
  }
}