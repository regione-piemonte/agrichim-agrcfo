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

public class MicroelementiMetodoDTPA extends BeanDataSource
{
  private long idRichiesta;
  private String letturaFerro;
  private String diluizioneFerro;
  private String ferroAssimilabile;
  private String letturaManganese;
  private String diluizioneManganese;
  private String manganeseAssimilabile;
  private String letturaZinco;
  private String diluizioneZinco;
  private String zincoAssimilabile;
  private String letturaRame;
  private String diluizioneRame;
  private String rameAssimilabile;
  private String letturaBoro;
  private String diluizioneBoro;
  private String boroAssimilabile;

  public MicroelementiMetodoDTPA ()
  {
  }
  public MicroelementiMetodoDTPA ( long idRichiesta, String letturaFerro, String diluizioneFerro, String ferroAssimilabile, String letturaManganese, String diluizioneManganese, String manganeseAssimilabile, String letturaZinco, String diluizioneZinco, String zincoAssimilabile, String letturaRame, String diluizioneRame, String rameAssimilabile, String letturaBoro, String diluizioneBoro, String boroAssimilabile )
  {
    this.idRichiesta=idRichiesta;
    this.letturaFerro=letturaFerro;
    this.diluizioneFerro=diluizioneFerro;
    this.ferroAssimilabile=ferroAssimilabile;
    this.letturaManganese=letturaManganese;
    this.diluizioneManganese=diluizioneManganese;
    this.manganeseAssimilabile=manganeseAssimilabile;
    this.letturaZinco=letturaZinco;
    this.diluizioneZinco=diluizioneZinco;
    this.zincoAssimilabile=zincoAssimilabile;
    this.letturaRame=letturaRame;
    this.diluizioneRame=diluizioneRame;
    this.rameAssimilabile=rameAssimilabile;
    this.letturaBoro=letturaBoro;
    this.diluizioneBoro=diluizioneBoro;
    this.boroAssimilabile=boroAssimilabile;
  }

  /**
   * Questo metodo va a leggere il record della tabella MICROELEMENTI_METODO_DTPA
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
      query = new StringBuffer("SELECT LETTURA_FERRO, DILUIZIONE_FERRO,");
      query.append("FERRO_ASSIMILABILE, LETTURA_MANGANESE, DILUIZIONE_MANGANESE,");
      query.append("MANGANESE_ASSIMILABILE, LETTURA_ZINCO, DILUIZIONE_ZINCO,");
      query.append("ZINCO_ASSIMILABILE, LETTURA_RAME, DILUIZIONE_RAME,");
      query.append("RAME_ASSIMILABILE, LETTURA_BORO,");
      query.append("DILUIZIONE_BORO, BORO_ASSIMILABILE ");
      query.append("FROM MICROELEMENTI_METODO_DTPA ");
      query.append("WHERE ID_RICHIESTA = ");
      query.append(getIdRichiesta());
      //CuneoLogger.debug(this,query.toString());
      ResultSet rs=stmt.executeQuery(query.toString());
      String temp;
      if (rs.next())
      {
        temp=rs.getString("LETTURA_FERRO");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setLetturaFerro(temp);

        temp=rs.getString("DILUIZIONE_FERRO");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setDiluizioneFerro(temp);

        temp=rs.getString("FERRO_ASSIMILABILE");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setFerroAssimilabile(temp);

        temp=rs.getString("LETTURA_MANGANESE");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setLetturaManganese(temp);

        temp=rs.getString("DILUIZIONE_MANGANESE");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setDiluizioneManganese(temp);

        temp=rs.getString("MANGANESE_ASSIMILABILE");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setManganeseAssimilabile(temp);

        temp=rs.getString("LETTURA_ZINCO");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setLetturaZinco(temp);

        temp=rs.getString("DILUIZIONE_ZINCO");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setDiluizioneZinco(temp);

        temp=rs.getString("ZINCO_ASSIMILABILE");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setZincoAssimilabile(temp);

        temp=rs.getString("LETTURA_RAME");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setLetturaRame(temp);

        temp=rs.getString("DILUIZIONE_RAME");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setDiluizioneRame(temp);

        temp=rs.getString("RAME_ASSIMILABILE");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setRameAssimilabile(temp);

        temp=rs.getString("LETTURA_BORO");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setLetturaBoro(temp);

        temp=rs.getString("DILUIZIONE_BORO");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setDiluizioneBoro(temp);

        temp=rs.getString("BORO_ASSIMILABILE");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setBoroAssimilabile(temp);

        return true;
      }
      else return false;
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("select della classe MicroelementiMetodoDTPA");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("select della classe MicroelementiMetodoDTPA"
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

  public String getLetturaFerro()
  {
    return this.letturaFerro;
  }
  public void setLetturaFerro( String newLetturaFerro )
  {
    if (newLetturaFerro!=null) letturaFerro=newLetturaFerro.replace(',','.');
    else this.letturaFerro = newLetturaFerro;
  }

  public String getDiluizioneFerro()
  {
    return this.diluizioneFerro;
  }
  public void setDiluizioneFerro( String newDiluizioneFerro )
  {
    if (newDiluizioneFerro!=null) diluizioneFerro=newDiluizioneFerro.replace(',','.');
    else this.diluizioneFerro = newDiluizioneFerro;
  }

  public String getFerroAssimilabilePDF()
  {
    if (ferroAssimilabile==null) return "";
    else
    {
      ferroAssimilabile=ferroAssimilabile.replace(',','.');
      ferroAssimilabile=Utili.nf1.format(Double.parseDouble(ferroAssimilabile));
      ferroAssimilabile=ferroAssimilabile.replace('.',',');
      return ferroAssimilabile;
    }
  }

  public String getFerroAssimilabile()
  {
    return this.ferroAssimilabile;
  }
  public void setFerroAssimilabile( String newFerroAssimilabile )
  {
    if (newFerroAssimilabile!=null) ferroAssimilabile=newFerroAssimilabile.replace(',','.');
    else this.ferroAssimilabile = newFerroAssimilabile;
  }

  public String getLetturaManganese()
  {
    return this.letturaManganese;
  }
  public void setLetturaManganese( String newLetturaManganese )
  {
    if (newLetturaManganese!=null) letturaManganese=newLetturaManganese.replace(',','.');
    else this.letturaManganese = newLetturaManganese;
  }

  public String getDiluizioneManganese()
  {
    return this.diluizioneManganese;
  }
  public void setDiluizioneManganese( String newDiluizioneManganese )
  {
    if (newDiluizioneManganese!=null) diluizioneManganese=newDiluizioneManganese.replace(',','.');
    else this.diluizioneManganese = newDiluizioneManganese;
  }

  public String getManganeseAssimilabilePDF()
  {
    if (manganeseAssimilabile==null) return "";
    else
    {
      manganeseAssimilabile=manganeseAssimilabile.replace(',','.');
      manganeseAssimilabile=Utili.nf1.format(Double.parseDouble(manganeseAssimilabile));
      manganeseAssimilabile=manganeseAssimilabile.replace('.',',');
      return manganeseAssimilabile;
    }
  }

  public String getManganeseAssimilabile()
  {
    return this.manganeseAssimilabile;
  }
  public void setManganeseAssimilabile( String newManganeseAssimilabile )
  {
    if (newManganeseAssimilabile!=null) manganeseAssimilabile=newManganeseAssimilabile.replace(',','.');
    else this.manganeseAssimilabile = newManganeseAssimilabile;
  }

  public String getLetturaZinco()
  {
    return this.letturaZinco;
  }
  public void setLetturaZinco( String newLetturaZinco )
  {
    if (newLetturaZinco!=null) letturaZinco=newLetturaZinco.replace(',','.');
    else this.letturaZinco = newLetturaZinco;
  }

  public String getDiluizioneZinco()
  {
    return this.diluizioneZinco;
  }
  public void setDiluizioneZinco( String newDiluizioneZinco )
  {
    if (newDiluizioneZinco!=null) diluizioneZinco=newDiluizioneZinco.replace(',','.');
    else this.diluizioneZinco = newDiluizioneZinco;
  }

  public String getZincoAssimilabilePDF()
  {
    if (zincoAssimilabile==null) return "";
    else
    {
      zincoAssimilabile=zincoAssimilabile.replace(',','.');
      zincoAssimilabile=Utili.nf1.format(Double.parseDouble(zincoAssimilabile));
      zincoAssimilabile=zincoAssimilabile.replace('.',',');
      return zincoAssimilabile;
    }
  }

  public String getZincoAssimilabile()
  {
    return this.zincoAssimilabile;
  }
  public void setZincoAssimilabile( String newZincoAssimilabile )
  {
    if (newZincoAssimilabile!=null) zincoAssimilabile=newZincoAssimilabile.replace(',','.');
    else this.zincoAssimilabile = newZincoAssimilabile;
  }

  public String getLetturaRame()
  {
    return this.letturaRame;
  }
  public void setLetturaRame( String newLetturaRame )
  {
    if (newLetturaRame!=null) letturaRame=newLetturaRame.replace(',','.');
    else this.letturaRame = newLetturaRame;
  }

  public String getDiluizioneRame()
  {
    return this.diluizioneRame;
  }
  public void setDiluizioneRame( String newDiluizioneRame )
  {
    if (newDiluizioneRame!=null) diluizioneRame=newDiluizioneRame.replace(',','.');
    else this.diluizioneRame = newDiluizioneRame;
  }

  public String getRameAssimilabilePDF()
  {
    if (rameAssimilabile==null) return "";
    else
    {
      rameAssimilabile=rameAssimilabile.replace(',','.');
      rameAssimilabile=Utili.nf1.format(Double.parseDouble(rameAssimilabile));
      rameAssimilabile=rameAssimilabile.replace('.',',');
      return rameAssimilabile;
    }
  }

  public String getRameAssimilabile()
  {
    return this.rameAssimilabile;
  }
  public void setRameAssimilabile( String newRameAssimilabile )
  {
    if (newRameAssimilabile!=null) rameAssimilabile=newRameAssimilabile.replace(',','.');
    else this.rameAssimilabile = newRameAssimilabile;
  }

  public String getLetturaBoro()
  {
    return this.letturaBoro;
  }
  public void setLetturaBoro( String newLetturaBoro )
  {
    if (newLetturaBoro!=null) letturaBoro=newLetturaBoro.replace(',','.');
    else this.letturaBoro = newLetturaBoro;
  }

  public String getDiluizioneBoro()
  {
    return this.diluizioneBoro;
  }
  public void setDiluizioneBoro( String newDiluizioneBoro )
  {
    if (newDiluizioneBoro!=null) diluizioneBoro=newDiluizioneBoro.replace(',','.');
    else this.diluizioneBoro = newDiluizioneBoro;
  }

  public String getBoroAssimilabilePDF()
  {
    if (boroAssimilabile==null) return "";
    else
    {
      boroAssimilabile=boroAssimilabile.replace(',','.');
      boroAssimilabile=Utili.nf2.format(Double.parseDouble(boroAssimilabile));
      boroAssimilabile=boroAssimilabile.replace('.',',');
      return boroAssimilabile;
    }
  }

  public String getBoroAssimilabile()
  {
    return this.boroAssimilabile;
  }
  public void setBoroAssimilabile( String newBoroAssimilabile )
  {
    if (newBoroAssimilabile!=null) boroAssimilabile=newBoroAssimilabile.replace(',','.');
    else this.boroAssimilabile = newBoroAssimilabile;
  }
}