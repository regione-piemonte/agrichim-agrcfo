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

public class MacroMicroElemIndFoglie extends BeanDataSource
{
  private String pesoCampione;
  private String volumeDiluizione;
  private String letturaCaPpm;
  private String calcioPpm;
  private String calcio;
  private String letturaMgPpm;
  private String magnesioPpm;
  private String magnesio;
  private String letturaKPpm;
  private String potassioPpm;
  private String potassio;
  private String azoto;
  private String azotoPpm;
  private String fosforoPpm;
  private String fosforo;
  private String diluizioneFe;
  private String ferroPpm;
  private String letturaFePpm;
  private String diluizioneMn;
  private String manganesePpm;
  private String letturaMnPpm;
  private String diluizioneZn;
  private String zincoPpm;
  private String letturaZnPpm;
  private String diluizioneCu;
  private String ramePpm;
  private String letturaCuPpm;
  private String diluizioneB;
  private String boroPpm;
  private String letturaBPpm;
  private long idRichiesta;

  public MacroMicroElemIndFoglie ()
  {
  }
  public MacroMicroElemIndFoglie ( long idRichiesta, String pesoCampione, String volumeDiluizione, String letturaCaPpm, String calcioPpm, String calcio, String letturaMgPpm, String magnesioPpm, String magnesio, String letturaKPpm, String potassioPpm, String potassio, String azoto, String azotoPpm, String fosforoPpm, String fosforo, String diluizioneFe, String ferroPpm, String letturaFePpm, String diluizioneMn, String manganesePpm, String letturaMnPpm, String diluizioneZn, String zincoPpm, String letturaZnPpm, String diluizioneCu, String ramePpm, String letturaCuPpm, String diluizioneB, String boroPpm, String letturaBPpm )
  {
    this.idRichiesta=idRichiesta;
    this.pesoCampione=pesoCampione;
    this.volumeDiluizione=volumeDiluizione;
    this.letturaCaPpm=letturaCaPpm;
    this.calcioPpm=calcioPpm;
    this.calcio=calcio;
    this.letturaMgPpm=letturaMgPpm;
    this.magnesioPpm=magnesioPpm;
    this.magnesio=magnesio;
    this.letturaKPpm=letturaKPpm;
    this.potassioPpm=potassioPpm;
    this.potassio=potassio;
    this.azoto=azoto;
    this.azotoPpm=azotoPpm;
    this.fosforoPpm=fosforoPpm;
    this.fosforo=fosforo;
    this.diluizioneFe=diluizioneFe;
    this.ferroPpm=ferroPpm;
    this.letturaFePpm=letturaFePpm;
    this.diluizioneMn=diluizioneMn;
    this.manganesePpm=manganesePpm;
    this.letturaMnPpm=letturaMnPpm;
    this.diluizioneZn=diluizioneZn;
    this.zincoPpm=zincoPpm;
    this.letturaZnPpm=letturaZnPpm;
    this.diluizioneCu=diluizioneCu;
    this.ramePpm=ramePpm;
    this.letturaCuPpm=letturaCuPpm;
    this.diluizioneB=diluizioneB;
    this.boroPpm=boroPpm;
    this.letturaBPpm=letturaBPpm;
  }


  /**
   * Questo metodo va a leggere il record della tabella MACRO_MICRO_ELEM_IND_FOGLIE
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
      query = new StringBuffer("SELECT * ");
      query.append("FROM MACRO_MICRO_ELEM_IND_FOGLIE ");
      query.append("WHERE ID_RICHIESTA = ");
      query.append(getIdRichiesta());
      ResultSet rs=stmt.executeQuery(query.toString());
      String temp;
      if (rs.next())
      {
        temp=rs.getString("PESO_CAMPIONE");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setPesoCampione(temp);

        temp=rs.getString("VOLUME_DILUIZIONE");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setVolumeDiluizione(temp);

        temp=rs.getString("LETTURA_CA_PPM");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setLetturaCaPpm(temp);

        temp=rs.getString("CALCIO_PPM");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setCalcioPpm(temp);

        temp=rs.getString("CALCIO");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setCalcio(temp);

        temp=rs.getString("LETTURA_MG_PPM");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setLetturaMgPpm(temp);

        temp=rs.getString("MAGNESIO_PPM");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setMagnesioPpm(temp);

        temp=rs.getString("MAGNESIO");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setMagnesio(temp);

        temp=rs.getString("LETTURA_K_PPM");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setLetturaKPpm(temp);

        temp=rs.getString("POTASSIO_PPM");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setPotassioPpm(temp);

        temp=rs.getString("POTASSIO");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setPotassio(temp);

        temp=rs.getString("AZOTO");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setAzoto(temp);

        temp=rs.getString("AZOTO_PPM");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setAzotoPpm(temp);

        temp=rs.getString("FOSFORO_PPM");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setFosforoPpm (temp);

        temp=rs.getString("FOSFORO");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setFosforo(temp);

        temp=rs.getString("DILUIZIONE_FE");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setDiluizioneFe (temp);

        temp=rs.getString("FERRO_PPM");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setFerroPpm (temp);

        temp=rs.getString("LETTURA_FE_PPM");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setLetturaFePpm (temp);

        temp=rs.getString("DILUIZIONE_MN");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setDiluizioneMn (temp);

        temp=rs.getString("MANGANESE_PPM");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setManganesePpm (temp);

        temp=rs.getString("LETTURA_MN_PPM");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setLetturaMnPpm (temp);

        temp=rs.getString("DILUIZIONE_ZN");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setDiluizioneZn (temp);

        temp=rs.getString("ZINCO_PPM");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setZincoPpm (temp);

        temp=rs.getString("LETTURA_ZN_PPM");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setLetturaZnPpm (temp);

        temp=rs.getString("DILUIZIONE_CU");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setDiluizioneCu (temp);

        temp=rs.getString("RAME_PPM");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setRamePpm (temp);

        temp=rs.getString("LETTURA_CU_PPM");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setLetturaCuPpm (temp);

        temp=rs.getString("DILUIZIONE_B");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setDiluizioneB (temp);

        temp=rs.getString("BORO_PPM");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setBoroPpm (temp);

        temp=rs.getString("LETTURA_B_PPM");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setLetturaBPpm (temp);

        return true;
      }
      else return false;
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("select della classe MacroMicroElemIndFoglie");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("select della classe MacroMicroElemIndFoglie"
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
  public void setIdRichiesta(long idRichiesta)
  {
    this.idRichiesta = idRichiesta;
  }

  public String getPesoCampione()
  {
    return this.pesoCampione;
  }
  public void setPesoCampione( String newPesoCampione )
  {
    if (newPesoCampione!=null) pesoCampione=newPesoCampione.replace(',','.');
    else this.pesoCampione = newPesoCampione;
  }

  public String getVolumeDiluizione()
  {
    return this.volumeDiluizione;
  }
  public void setVolumeDiluizione( String newVolumeDiluizione )
  {
    if (newVolumeDiluizione!=null) volumeDiluizione=newVolumeDiluizione.replace(',','.');
    else this.volumeDiluizione = newVolumeDiluizione;
  }

  public String getLetturaCaPpm()
  {
    return this.letturaCaPpm;
  }
  public void setLetturaCaPpm( String newLetturaCaPpm )
  {
    if (newLetturaCaPpm!=null) letturaCaPpm=newLetturaCaPpm.replace(',','.');
    else this.letturaCaPpm = newLetturaCaPpm;
  }


  public String getCalcioPpmPDF()
  {
    if (calcioPpm==null) return "";
    else return calcioPpm.replace('.',',');
  }
  public String getCalcioPpm()
  {
    return this.calcioPpm;
  }
  public void setCalcioPpm( String newCalcioPpm )
  {
    if (newCalcioPpm!=null) calcioPpm=newCalcioPpm.replace(',','.');
    else this.calcioPpm = newCalcioPpm;
  }


  public String getCalcioPDF()
  {
    if (calcio==null) return "";
    else return calcio.replace('.',',');
  }
  public String getCalcio()
  {
    return this.calcio;
  }
  public void setCalcio( String newCalcio )
  {
    if (newCalcio!=null) calcio=newCalcio.replace(',','.');
    else this.calcio = newCalcio;
  }

  public String getLetturaMgPpm()
  {
    return this.letturaMgPpm;
  }
  public void setLetturaMgPpm( String newLetturaMgPpm )
  {
    if (newLetturaMgPpm!=null) letturaMgPpm=newLetturaMgPpm.replace(',','.');
    else this.letturaMgPpm = newLetturaMgPpm;
  }


  public String getMagnesioPpmPDF()
  {
    if (magnesioPpm==null) return "";
    else return magnesioPpm.replace('.',',');
  }
  public String getMagnesioPpm()
  {
    return this.magnesioPpm;
  }
  public void setMagnesioPpm( String newMagnesioPpm )
  {
    if (newMagnesioPpm!=null) magnesioPpm=newMagnesioPpm.replace(',','.');
    else this.magnesioPpm = newMagnesioPpm;
  }


  public String getMagnesioPDF()
  {
    if (magnesio==null) return "";
    else return magnesio.replace('.',',');
  }
  public String getMagnesio()
  {
    return this.magnesio;
  }
  public void setMagnesio( String newMagnesio )
  {
    if (newMagnesio!=null) magnesio=newMagnesio.replace(',','.');
    else this.magnesio = newMagnesio;
  }

  public String getLetturaKPpm()
  {
    return this.letturaKPpm;
  }
  public void setLetturaKPpm( String newLetturaKPpm )
  {
    if (newLetturaKPpm!=null) letturaKPpm=newLetturaKPpm.replace(',','.');
    else this.letturaKPpm = newLetturaKPpm;
  }


  public String getPotassioPpmPDF()
  {
    if (potassioPpm==null) return "";
    else return potassioPpm.replace('.',',');
  }
  public String getPotassioPpm()
  {
    return this.potassioPpm;
  }
  public void setPotassioPpm( String newPotassioPpm )
  {
    if (newPotassioPpm!=null) potassioPpm=newPotassioPpm.replace(',','.');
    else this.potassioPpm = newPotassioPpm;
  }


  public String getPotassioPDF()
  {
    if (potassio==null) return "";
    else return potassio.replace('.',',');
  }
  public String getPotassio()
  {
    return this.potassio;
  }
  public void setPotassio( String newPotassio )
  {
    if (newPotassio!=null) potassio=newPotassio.replace(',','.');
    else this.potassio = newPotassio;
  }


  public String getAzotoPDF()
  {
    if (azoto==null) return "";
    else return azoto.replace('.',',');
  }
  public String getAzoto()
  {
    return this.azoto;
  }
  public void setAzoto( String newAzoto )
  {
    if (newAzoto!=null) azoto=newAzoto.replace(',','.');
    else this.azoto = newAzoto;
  }

  public String getAzotoPpmPDF()
  {
    if (azotoPpm==null) return "";
    else return azotoPpm.replace('.',',');
  }
  public String getAzotoPpm()
  {
    return this.azotoPpm;
  }
  public void setAzotoPpm( String newAzotoPpm )
  {
    if (newAzotoPpm!=null) azotoPpm=newAzotoPpm.replace(',','.');
    else this.azotoPpm = newAzotoPpm;
  }

  public String getFosforoPpmPDF()
  {
     if (fosforoPpm==null) return "";
     else return fosforoPpm.replace('.',',');
  }
  public String getFosforoPpm()
  {
    return this.fosforoPpm;
  }
  public void setFosforoPpm( String newFosforoPpm )
  {
    if (newFosforoPpm!=null) fosforoPpm=newFosforoPpm.replace(',','.');
    else this.fosforoPpm = newFosforoPpm;
  }

  public String getFosforoPDF()
  {
    if (fosforo==null) return "";
    else return fosforo.replace('.',',');
  }
  public String getFosforo()
  {
    return this.fosforo;
  }
  public void setFosforo( String newFosforo )
  {
    if (newFosforo!=null) fosforo=newFosforo.replace(',','.');
    else this.fosforo = newFosforo;
  }

  public String getDiluizioneFe()
  {
    return this.diluizioneFe;
  }
  public void setDiluizioneFe( String newDiluizioneFe )
  {
    if (newDiluizioneFe!=null) diluizioneFe=newDiluizioneFe.replace(',','.');
    else this.diluizioneFe = newDiluizioneFe;
  }


  public String getFerroPpmPDF()
  {
    if (ferroPpm==null) return "";
    else return ferroPpm.replace('.',',');
  }
  public String getFerroPpm()
  {
    return this.ferroPpm;
  }
  public void setFerroPpm( String newFerroPpm )
  {
    if (newFerroPpm!=null) ferroPpm=newFerroPpm.replace(',','.');
    else this.ferroPpm = newFerroPpm;
  }

  public String getLetturaFePpm()
  {
    return this.letturaFePpm;
  }
  public void setLetturaFePpm( String newLetturaFePpm )
  {
    if (newLetturaFePpm!=null) letturaFePpm=newLetturaFePpm.replace(',','.');
    else this.letturaFePpm = newLetturaFePpm;
  }

  public String getDiluizioneMn()
  {
    return this.diluizioneMn;
  }
  public void setDiluizioneMn( String newDiluizioneMn )
  {
    if (newDiluizioneMn!=null) diluizioneMn=newDiluizioneMn.replace(',','.');
    else this.diluizioneMn = newDiluizioneMn;
  }


  public String getManganesePpmPDF()
  {
    if (manganesePpm==null) return "";
    else return manganesePpm.replace('.',',');
  }
  public String getManganesePpm()
  {
    return this.manganesePpm;
  }
  public void setManganesePpm( String newManganesePpm )
  {
    if (newManganesePpm!=null) manganesePpm=newManganesePpm.replace(',','.');
    else this.manganesePpm = newManganesePpm;
  }

  public String getLetturaMnPpm()
  {
    return this.letturaMnPpm;
  }
  public void setLetturaMnPpm( String newLetturaMnPpm )
  {
    if (newLetturaMnPpm!=null) letturaMnPpm=newLetturaMnPpm.replace(',','.');
    else this.letturaMnPpm = newLetturaMnPpm;
  }

  public String getDiluizioneZn()
  {
    return this.diluizioneZn;
  }
  public void setDiluizioneZn( String newDiluizioneZn )
  {
    if (newDiluizioneZn!=null) diluizioneZn=newDiluizioneZn.replace(',','.');
    else this.diluizioneZn = newDiluizioneZn;
  }


  public String getZincoPpmPDF()
  {
    if (zincoPpm==null) return "";
    else return zincoPpm.replace('.',',');
  }
  public String getZincoPpm()
  {
    return this.zincoPpm;
  }
  public void setZincoPpm( String newZincoPpm )
  {
    if (newZincoPpm!=null) zincoPpm=newZincoPpm.replace(',','.');
    else this.zincoPpm = newZincoPpm;
  }

  public String getLetturaZnPpm()
  {
    return this.letturaZnPpm;
  }
  public void setLetturaZnPpm( String newLetturaZnPpm )
  {
    if (newLetturaZnPpm!=null) letturaZnPpm=newLetturaZnPpm.replace(',','.');
    else this.letturaZnPpm = newLetturaZnPpm;
  }

  public String getDiluizioneCu()
  {
    return this.diluizioneCu;
  }
  public void setDiluizioneCu( String newDiluizioneCu )
  {
    if (newDiluizioneCu!=null) diluizioneCu=newDiluizioneCu.replace(',','.');
    else this.diluizioneCu = newDiluizioneCu;
  }


  public String getRamePpmPDF()
  {
    if (ramePpm==null) return "";
    else return ramePpm.replace('.',',');
  }
  public String getRamePpm()
  {
    return this.ramePpm;
  }
  public void setRamePpm( String newRamePpm )
  {
    if (newRamePpm!=null) ramePpm=newRamePpm.replace(',','.');
    else this.ramePpm = newRamePpm;
  }

  public String getLetturaCuPpm()
  {
    return this.letturaCuPpm;
  }
  public void setLetturaCuPpm( String newLetturaCuPpm )
  {
    if (newLetturaCuPpm!=null) letturaCuPpm=newLetturaCuPpm.replace(',','.');
    else this.letturaCuPpm = newLetturaCuPpm;
  }

  public String getDiluizioneB()
  {
    return this.diluizioneB;
  }
  public void setDiluizioneB( String newDiluizioneB )
  {
    if (newDiluizioneB!=null) diluizioneB=newDiluizioneB.replace(',','.');
    else this.diluizioneB = newDiluizioneB;
  }

  public String getBoroPpmPDF()
  {
    if (boroPpm==null) return "";
    else return boroPpm.replace('.',',');
  }
  public String getBoroPpm()
  {
    return this.boroPpm;
  }
  public void setBoroPpm( String newBoroPpm )
  {
    if (newBoroPpm!=null) boroPpm=newBoroPpm.replace(',','.');
    else this.boroPpm = newBoroPpm;
  }

  public String getLetturaBPpm()
  {
    return this.letturaBPpm;
  }
  public void setLetturaBPpm( String newLetturaBPpm )
  {
    if (newLetturaBPpm!=null) letturaBPpm=newLetturaBPpm.replace(',','.');
    else this.letturaBPpm = newLetturaBPpm;
  }


  public String getKCaPDF()
  {
    String kCa;
    try
    {
      kCa=""+(Double.parseDouble(getPotassioPpm())/Double.parseDouble(getCalcioPpm()));
      kCa=Utili.nf3.format(Double.parseDouble(kCa));
      kCa.replace('.',',');
      return kCa;
    }
    catch(Exception e)
    {
      return "";
    }
  }
}