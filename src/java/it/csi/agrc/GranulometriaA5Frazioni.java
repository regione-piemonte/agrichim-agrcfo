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

public class GranulometriaA5Frazioni extends BeanDataSource
{

  private long idRichiesta;
  private String argilla;
  private String sabbiaTotale;
  private String sabbiaGrossa;
  private String limoTotale;
  private String limoFine;
  private String sabbiaFine;
  private String limoGrosso;

  public GranulometriaA5Frazioni ()
  {
  }
  public GranulometriaA5Frazioni ( long idRichiesta, String argilla, String sabbiaTotale, String sabbiaGrossa, String limoTotale, String limoFine, String sabbiaFine, String limoGrosso )
  {
    this.idRichiesta=idRichiesta;
    this.argilla=argilla;
    this.sabbiaTotale=sabbiaTotale;
    this.sabbiaGrossa=sabbiaGrossa;
    this.limoTotale=limoTotale;
    this.limoFine=limoFine;
    this.sabbiaFine=sabbiaFine;
    this.limoGrosso=limoGrosso;
  }

  /**
   * Questo metodo va a leggere il record della tabella GRANULOMETRIA_A_5_FRAZIONI
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
      query = new StringBuffer("SELECT ARGILLA,LIMO_TOTALE,LIMO_FINE,LIMO_GROSSO,");
      query.append("SABBIA_TOTALE,SABBIA_GROSSA,SABBIA_FINE ");
      query.append("FROM GRANULOMETRIA_A_5_FRAZIONI ");
      query.append("WHERE ID_RICHIESTA = ");
      query.append(getIdRichiesta());
      //CuneoLogger.debug(this,query.toString());
      ResultSet rs=stmt.executeQuery(query.toString());
      String temp;
      if (rs.next())
      {
        temp=rs.getString("ARGILLA");
        if (temp!=null) temp=Utili.nf2.format(Double.parseDouble(temp));
        this.setArgilla(temp);

        temp=rs.getString("LIMO_TOTALE");
        if (temp!=null) temp=Utili.nf2.format(Double.parseDouble(temp));
        this.setLimoTotale(temp);

        temp=rs.getString("LIMO_FINE");
        if (temp!=null) temp=Utili.nf2.format(Double.parseDouble(temp));
        this.setLimoFine(temp);

        temp=rs.getString("LIMO_GROSSO");
        if (temp!=null) temp=Utili.nf2.format(Double.parseDouble(temp));
        this.setLimoGrosso(temp);

        temp=rs.getString("SABBIA_TOTALE");
        if (temp!=null) temp=Utili.nf2.format(Double.parseDouble(temp));
        this.setSabbiaTotale(temp);

        temp=rs.getString("SABBIA_GROSSA");
        if (temp!=null) temp=Utili.nf2.format(Double.parseDouble(temp));
        this.setSabbiaGrossa(temp);

        temp=rs.getString("SABBIA_FINE");
        if (temp!=null) temp=Utili.nf2.format(Double.parseDouble(temp));
        this.setSabbiaFine(temp);

        return true;
      }
      else return false;
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("select della classe GranulometriaA5Frazioni");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("select della classe GranulometriaA5Frazioni"
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

  public String getArgilla()
  {
    return this.argilla;
  }
  public String getArgillaPDF()
  {
    if (argilla==null) return "";
    else
    {
      argilla=argilla.replace(',','.');
      argilla=Utili.nf1.format(Double.parseDouble(argilla));
      argilla=argilla.replace('.',',');
      return argilla;
    }
  }
  public void setArgilla( String newArgilla )
  {
    if (newArgilla!=null) argilla=newArgilla.replace(',','.');
    else this.argilla = newArgilla;
  }

  public String getSabbiaTotale()
  {
    return this.sabbiaTotale;
  }
  public String getSabbiaTotalePDF()
  {
    if (sabbiaTotale==null) return "";
    else
    {
      sabbiaTotale=sabbiaTotale.replace(',','.');
      sabbiaTotale=Utili.nf1.format(Double.parseDouble(sabbiaTotale));
      sabbiaTotale=sabbiaTotale.replace('.',',');
      return sabbiaTotale;
    }
  }
  public void setSabbiaTotale( String newSabbiaTotale )
  {
    if (newSabbiaTotale!=null) sabbiaTotale=newSabbiaTotale.replace(',','.');
    else this.sabbiaTotale = newSabbiaTotale;
  }

  public String getSabbiaGrossa()
  {
    return this.sabbiaGrossa;
  }
  public String getSabbiaGrossaPDF()
  {
    if (sabbiaGrossa==null) return "";
    else
    {
      sabbiaGrossa=sabbiaGrossa.replace(',','.');
      sabbiaGrossa=Utili.nf1.format(Double.parseDouble(sabbiaGrossa));
      sabbiaGrossa=sabbiaGrossa.replace('.',',');
      return sabbiaGrossa;
    }
  }
  public void setSabbiaGrossa( String newSabbiaGrossa )
  {
    if (newSabbiaGrossa!=null) sabbiaGrossa=newSabbiaGrossa.replace(',','.');
    else this.sabbiaGrossa = newSabbiaGrossa;
  }

  public String getLimoTotale()
  {
    return this.limoTotale;
  }
  public String getLimoTotalePDF()
  {
    if (limoTotale==null) return "";
    else
    {
      limoTotale=limoTotale.replace(',','.');
      limoTotale=Utili.nf1.format(Double.parseDouble(limoTotale));
      limoTotale=limoTotale.replace('.',',');
      return limoTotale;
    }
  }
  public void setLimoTotale( String newLimoTotale )
  {
    if (newLimoTotale!=null) limoTotale=newLimoTotale.replace(',','.');
    else this.limoTotale = newLimoTotale;
  }

  public String getLimoFine()
  {
    return this.limoFine;
  }
  public String getLimoFinePDF()
  {
    if (limoFine==null) return "";
    else
    {
      limoFine=limoFine.replace(',','.');
      limoFine=Utili.nf1.format(Double.parseDouble(limoFine));
      limoFine=limoFine.replace('.',',');
      return limoFine;
    }
  }
  public void setLimoFine( String newLimoFine )
  {
    if (newLimoFine!=null) limoFine=newLimoFine.replace(',','.');
    else this.limoFine = newLimoFine;
  }

  public String getSabbiaFine()
  {
    return this.sabbiaFine;
  }
  public String getSabbiaFinePDF()
  {
    if (sabbiaFine==null) return "";
    else
    {
      sabbiaFine=sabbiaFine.replace(',','.');
      sabbiaFine=Utili.nf1.format(Double.parseDouble(sabbiaFine));
      sabbiaFine=sabbiaFine.replace('.',',');
      return sabbiaFine;
    }
  }
  public void setSabbiaFine( String newSabbiaFine )
  {
    this.sabbiaFine = newSabbiaFine;
  }

  public String getLimoGrosso()
  {
    return this.limoGrosso;
  }
  public String getLimoGrossoPDF()
  {
    if (limoGrosso==null) return "";
    else
    {
      limoGrosso=limoGrosso.replace(',','.');
      limoGrosso=Utili.nf1.format(Double.parseDouble(limoGrosso));
      limoGrosso=limoGrosso.replace('.',',');
      return limoGrosso;
    }
  }
  public void setLimoGrosso( String newLimoGrosso )
  {
    this.limoGrosso = newLimoGrosso;
  }
}