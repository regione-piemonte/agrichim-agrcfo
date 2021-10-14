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

public class ComplessoScambio extends BeanDataSource
{
  private long idRichiesta;
  private String pesoSeccoProvetta;
  private String pesoSeccoAcquaProvetta;
  private String pesoTerreno;
  private String letturaMagnesioEdta;
  private String letturaBiancoEdta;
  private String capacitaScambioCationico;
  private String letturaCalcio;
  private String letturaMagnesio;
  private String letturaPotassio;
  private String letturaSodio;
  private String vbacl2PerEstrazione;
  private String diluizioneCalcio;
  private String diluizioneMagnesio;
  private String diluizionePotassio;
  private String diluizioneSodio;
  private String calcioScambiabile;
  private String magnesioScambiabile;
  private String potassioScambiabile;
  private String sodioScambiabile;
  private String calcioScambiabileMeq100;
  private String magnesioScambiabileMeq100;
  private String potassioScambiabileMeq100;
  private String sodioScambiabileMeq100;
  private String calcioScambiabileCsc;
  private String magnesioScambiabileCsc;
  private String potassioScambiabileCsc;
  private String sodioScambiabileCsc;
  private String saturazioneBasica;
  private String caMg;
  private String caK;
  private String mgK;

  public ComplessoScambio ()
  {
  }
  public ComplessoScambio ( long idRichiesta, String pesoSeccoProvetta, String pesoSeccoAcquaProvetta, String pesoTerreno, String letturaMagnesioEdta, String letturaBiancoEdta, String capacitaScambioCationico, String letturaCalcio, String letturaMagnesio, String letturaPotassio, String letturaSodio, String vbacl2PerEstrazione, String diluizioneCalcio, String diluizioneMagnesio, String diluizionePotassio, String diluizioneSodio, String calcioScambiabile, String magnesioScambiabile, String potassioScambiabile, String sodioScambiabile, String calcioScambiabileMeq100, String magnesioScambiabileMeq100, String potassioScambiabileMeq100, String sodioScambiabileMeq100, String calcioScambiabileCsc, String magnesioScambiabileCsc, String potassioScambiabileCsc, String sodioScambiabileCsc, String saturazioneBasica, String caMg, String caK, String mgK )
  {
    this.idRichiesta=idRichiesta;
    this.pesoSeccoProvetta=pesoSeccoProvetta;
    this.pesoSeccoAcquaProvetta=pesoSeccoAcquaProvetta;
    this.pesoTerreno=pesoTerreno;
    this.letturaMagnesioEdta=letturaMagnesioEdta;
    this.letturaBiancoEdta=letturaBiancoEdta;
    this.capacitaScambioCationico=capacitaScambioCationico;
    this.letturaCalcio=letturaCalcio;
    this.letturaMagnesio=letturaMagnesio;
    this.letturaPotassio=letturaPotassio;
    this.letturaSodio=letturaSodio;
    this.vbacl2PerEstrazione=vbacl2PerEstrazione;
    this.diluizioneCalcio=diluizioneCalcio;
    this.diluizioneMagnesio=diluizioneMagnesio;
    this.diluizionePotassio=diluizionePotassio;
    this.diluizioneSodio=diluizioneSodio;
    this.calcioScambiabile=calcioScambiabile;
    this.magnesioScambiabile=magnesioScambiabile;
    this.potassioScambiabile=potassioScambiabile;
    this.sodioScambiabile=sodioScambiabile;
    this.calcioScambiabileMeq100=calcioScambiabileMeq100;
    this.magnesioScambiabileMeq100=magnesioScambiabileMeq100;
    this.potassioScambiabileMeq100=potassioScambiabileMeq100;
    this.sodioScambiabileMeq100=sodioScambiabileMeq100;
    this.calcioScambiabileCsc=calcioScambiabileCsc;
    this.magnesioScambiabileCsc=magnesioScambiabileCsc;
    this.potassioScambiabileCsc=potassioScambiabileCsc;
    this.sodioScambiabileCsc=sodioScambiabileCsc;
    this.saturazioneBasica=saturazioneBasica;
    this.caMg=caMg;
    this.caK=caK;
    this.mgK=mgK;
  }

  /**
   * Questo metodo va a leggere il record della tabella COMPLESSO_SCAMBIO
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
      query.append("FROM COMPLESSO_SCAMBIO ");
      query.append("WHERE ID_RICHIESTA = ");
      query.append(getIdRichiesta());
      //CuneoLogger.debug(this,query.toString());
      ResultSet rs=stmt.executeQuery(query.toString());
      String temp;
      if (rs.next())
      {
        temp=rs.getString("PESO_SECCO_PROVETTA");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setPesoSeccoProvetta(temp);

        temp=rs.getString("PESO_SECCO_ACQUA_PROVETTA");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setPesoSeccoAcquaProvetta(temp);

        temp=rs.getString("PESO_TERRENO");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setPesoTerreno(temp);

        temp=rs.getString("LETTURA_MAGNESIO_EDTA");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setLetturaMagnesioEdta(temp);

        temp=rs.getString("LETTURA_BIANCO_EDTA");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setLetturaBiancoEdta(temp);

        temp=rs.getString("CAPACITA_SCAMBIO_CATIONICO");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setCapacitaScambioCationico(temp);

        temp=rs.getString("LETTURA_CALCIO");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setLetturaCalcio(temp);

        temp=rs.getString("LETTURA_MAGNESIO");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setLetturaMagnesio(temp);

        temp=rs.getString("LETTURA_POTASSIO");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setLetturaPotassio(temp);

        temp=rs.getString("LETTURA_SODIO");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setLetturaSodio(temp);

        temp=rs.getString("VBACL2_PER_ESTRAZIONE");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setVbacl2PerEstrazione(temp);

        temp=rs.getString("DILUIZIONE_CALCIO");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setDiluizioneCalcio(temp);

        temp=rs.getString("DILUIZIONE_MAGNESIO");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setDiluizioneMagnesio(temp);

        temp=rs.getString("DILUIZIONE_POTASSIO");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setDiluizionePotassio(temp);

        temp=rs.getString("DILUIZIONE_SODIO");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setDiluizioneSodio(temp);

        temp=rs.getString("CALCIO_SCAMBIABILE");
        if (temp!=null) temp=Utili.nf2.format(Double.parseDouble(temp));
        this.setCalcioScambiabile(temp);

        temp=rs.getString("MAGNESIO_SCAMBIABILE");
        if (temp!=null) temp=Utili.nf2.format(Double.parseDouble(temp));
        this.setMagnesioScambiabile(temp);

        temp=rs.getString("POTASSIO_SCAMBIABILE");
        if (temp!=null) temp=Utili.nf2.format(Double.parseDouble(temp));
        this.setPotassioScambiabile(temp);

        temp=rs.getString("SODIO_SCAMBIABILE");
        if (temp!=null) temp=Utili.nf2.format(Double.parseDouble(temp));
        this.setSodioScambiabile(temp);

        temp=rs.getString("CALCIO_SCAMBIABILE_MEQ_100");
        if (temp!=null) temp=Utili.nf2.format(Double.parseDouble(temp));
        this.setCalcioScambiabileMeq100(temp);

        temp=rs.getString("MAGNESIO_SCAMBIABILE_MEQ_100");
        if (temp!=null) temp=Utili.nf2.format(Double.parseDouble(temp));
        this.setMagnesioScambiabileMeq100(temp);

        temp=rs.getString("POTASSIO_SCAMBIABILE_MEQ_100");
        if (temp!=null) temp=Utili.nf2.format(Double.parseDouble(temp));
        this.setPotassioScambiabileMeq100(temp);

        temp=rs.getString("SODIO_SCAMBIABILE_MEQ_100");
        if (temp!=null) temp=Utili.nf2.format(Double.parseDouble(temp));
        this.setSodioScambiabileMeq100(temp);

        temp=rs.getString("CALCIO_SCAMBIABILE_CSC");
        if (temp!=null) temp=Utili.nf2.format(Double.parseDouble(temp));
        this.setCalcioScambiabileCsc(temp);

        temp=rs.getString("MAGNESIO_SCAMBIABILE_CSC");
        if (temp!=null) temp=Utili.nf2.format(Double.parseDouble(temp));
        this.setMagnesioScambiabileCsc(temp);

        temp=rs.getString("POTASSIO_SCAMBIABILE_CSC");
        if (temp!=null) temp=Utili.nf2.format(Double.parseDouble(temp));
        this.setPotassioScambiabileCsc(temp);

        temp=rs.getString("SODIO_SCAMBIABILE_CSC");
        if (temp!=null) temp=Utili.nf2.format(Double.parseDouble(temp));
        this.setSodioScambiabileCsc(temp);

        temp=rs.getString("SATURAZIONE_BASICA");
        if (temp!=null) temp=Utili.nf2.format(Double.parseDouble(temp));
        this.setSaturazioneBasica(temp);

        temp=rs.getString("CA_MG");
        if (temp!=null) temp=Utili.nf2.format(Double.parseDouble(temp));
        this.setCaMg(temp);

        temp=rs.getString("CA_K");
        if (temp!=null) temp=Utili.nf2.format(Double.parseDouble(temp));
        this.setCaK(temp);

        temp=rs.getString("MG_K");
        if (temp!=null) temp=Utili.nf2.format(Double.parseDouble(temp));
        this.setMgK(temp);

        return true;
      }
      else return false;
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("select della classe ComplessoScambio");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("select della classe ComplessoScambio"
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

  public String getPesoSeccoProvetta()
  {
    return this.pesoSeccoProvetta;
  }
  public void setPesoSeccoProvetta( String newPesoSeccoProvetta )
  {
    if (newPesoSeccoProvetta!=null) pesoSeccoProvetta=newPesoSeccoProvetta.replace(',','.');
    else this.pesoSeccoProvetta = newPesoSeccoProvetta;
  }

  public String getPesoSeccoAcquaProvetta()
  {
    return this.pesoSeccoAcquaProvetta;
  }
  public void setPesoSeccoAcquaProvetta( String newPesoSeccoAcquaProvetta )
  {
    if (newPesoSeccoAcquaProvetta!=null) pesoSeccoAcquaProvetta=newPesoSeccoAcquaProvetta.replace(',','.');
    else this.pesoSeccoAcquaProvetta = newPesoSeccoAcquaProvetta;
  }

  public String getPesoTerreno()
  {
    return this.pesoTerreno;
  }
  public void setPesoTerreno( String newPesoTerreno )
  {
    if (newPesoTerreno!=null) pesoTerreno=newPesoTerreno.replace(',','.');
    else this.pesoTerreno = newPesoTerreno;
  }

  public String getLetturaMagnesioEdta()
  {
    return this.letturaMagnesioEdta;
  }
  public void setLetturaMagnesioEdta( String newLetturaMagnesioEdta )
  {
    if (newLetturaMagnesioEdta!=null) letturaMagnesioEdta=newLetturaMagnesioEdta.replace(',','.');
    else this.letturaMagnesioEdta = newLetturaMagnesioEdta;
  }

  public String getLetturaBiancoEdta()
  {
    return this.letturaBiancoEdta;
  }
  public void setLetturaBiancoEdta( String newLetturaBiancoEdta )
  {
    if (newLetturaBiancoEdta!=null) letturaBiancoEdta=newLetturaBiancoEdta.replace(',','.');
    else this.letturaBiancoEdta = newLetturaBiancoEdta;
  }

  public String getCapacitaScambioCationicoPDF()
  {
    if (capacitaScambioCationico==null) return "";
    else
    {
      capacitaScambioCationico=capacitaScambioCationico.replace(',','.');
      capacitaScambioCationico=Utili.nf1.format(Double.parseDouble(capacitaScambioCationico));
      capacitaScambioCationico=capacitaScambioCationico.replace('.',',');
      return capacitaScambioCationico;
    }
  }

  public String getCapacitaScambioCationico()
  {
    return this.capacitaScambioCationico;
  }
  public void setCapacitaScambioCationico( String newCapacitaScambioCationico )
  {
    if (newCapacitaScambioCationico!=null) capacitaScambioCationico=newCapacitaScambioCationico.replace(',','.');
    else this.capacitaScambioCationico = newCapacitaScambioCationico;
  }

  public String getLetturaCalcio()
  {
    return this.letturaCalcio;
  }
  public void setLetturaCalcio( String newLetturaCalcio )
  {
    if (newLetturaCalcio!=null) letturaCalcio=newLetturaCalcio.replace(',','.');
    else this.letturaCalcio = newLetturaCalcio;
  }

  public String getLetturaMagnesio()
  {
    return this.letturaMagnesio;
  }
  public void setLetturaMagnesio( String newLetturaMagnesio )
  {
    if (newLetturaMagnesio!=null) letturaMagnesio=newLetturaMagnesio.replace(',','.');
    else this.letturaMagnesio = newLetturaMagnesio;
  }

  public String getLetturaPotassio()
  {
    return this.letturaPotassio;
  }
  public void setLetturaPotassio( String newLetturaPotassio )
  {
    if (newLetturaPotassio!=null) letturaPotassio=newLetturaPotassio.replace(',','.');
    else this.letturaPotassio = newLetturaPotassio;
  }

  public String getLetturaSodio()
  {
    return this.letturaSodio;
  }
  public void setLetturaSodio( String newLetturaSodio )
  {
    if (newLetturaSodio!=null) letturaSodio=newLetturaSodio.replace(',','.');
    else this.letturaSodio = newLetturaSodio;
  }

  public String getVbacl2PerEstrazione()
  {
    return this.vbacl2PerEstrazione;
  }
  public void setVbacl2PerEstrazione( String newVbacl2PerEstrazione )
  {
    if (newVbacl2PerEstrazione!=null) vbacl2PerEstrazione=newVbacl2PerEstrazione.replace(',','.');
    else this.vbacl2PerEstrazione = newVbacl2PerEstrazione;
  }

  public String getDiluizioneCalcio()
  {
    return this.diluizioneCalcio;
  }
  public void setDiluizioneCalcio( String newDiluizioneCalcio )
  {
    if (newDiluizioneCalcio!=null) diluizioneCalcio=newDiluizioneCalcio.replace(',','.');
    else this.diluizioneCalcio = newDiluizioneCalcio;
  }

  public String getDiluizioneMagnesio()
  {
    return this.diluizioneMagnesio;
  }
  public void setDiluizioneMagnesio( String newDiluizioneMagnesio )
  {
    if (newDiluizioneMagnesio!=null) diluizioneMagnesio=newDiluizioneMagnesio.replace(',','.');
    else this.diluizioneMagnesio = newDiluizioneMagnesio;
  }

  public String getDiluizionePotassio()
  {
    return this.diluizionePotassio;
  }
  public void setDiluizionePotassio( String newDiluizionePotassio )
  {
    if (newDiluizionePotassio!=null) diluizionePotassio=newDiluizionePotassio.replace(',','.');
    else this.diluizionePotassio = newDiluizionePotassio;
  }

  public String getDiluizioneSodio()
  {
    return this.diluizioneSodio;
  }
  public void setDiluizioneSodio( String newDiluizioneSodio )
  {
    if (newDiluizioneSodio!=null) diluizioneSodio=newDiluizioneSodio.replace(',','.');
    else this.diluizioneSodio = newDiluizioneSodio;
  }

  public String getCalcioScambiabilePDF()
  {
    if (calcioScambiabile==null) return "";
    else
    {
      calcioScambiabile=calcioScambiabile.replace(',','.');
      calcioScambiabile=Utili.nf0.format(Double.parseDouble(calcioScambiabile));
      calcioScambiabile=calcioScambiabile.replace('.',',');
      return calcioScambiabile;
    }
  }

  public String getCalcioScambiabile()
  {
    return this.calcioScambiabile;
  }
  public void setCalcioScambiabile( String newCalcioScambiabile )
  {
    if (newCalcioScambiabile!=null) calcioScambiabile=newCalcioScambiabile.replace(',','.');
    else this.calcioScambiabile = newCalcioScambiabile;
  }

  public String getMagnesioScambiabilePDF()
  {
    if (magnesioScambiabile==null) return "";
    else
    {
      magnesioScambiabile=magnesioScambiabile.replace(',','.');
      magnesioScambiabile=Utili.nf0.format(Double.parseDouble(magnesioScambiabile));
      magnesioScambiabile=magnesioScambiabile.replace('.',',');
      return magnesioScambiabile;
    }
  }

  public String getMagnesioScambiabile()
  {
    return this.magnesioScambiabile;
  }
  public void setMagnesioScambiabile( String newMagnesioScambiabile )
  {
    if (newMagnesioScambiabile!=null) magnesioScambiabile=newMagnesioScambiabile.replace(',','.');
    else this.magnesioScambiabile = newMagnesioScambiabile;
  }

  public String getPotassioScambiabilePDF()
  {
    if (potassioScambiabile==null) return "";
    else
    {
      potassioScambiabile=potassioScambiabile.replace(',','.');
      potassioScambiabile=Utili.nf0.format(Double.parseDouble(potassioScambiabile));
      potassioScambiabile=potassioScambiabile.replace('.',',');
      return potassioScambiabile;
    }
  }

  public String getPotassioScambiabile()
  {
    return this.potassioScambiabile;
  }
  public void setPotassioScambiabile( String newPotassioScambiabile )
  {
    if (newPotassioScambiabile!=null) potassioScambiabile=newPotassioScambiabile.replace(',','.');
    else this.potassioScambiabile = newPotassioScambiabile;
  }

  public String getSodioScambiabilePDF()
  {
    if (sodioScambiabile==null) return "";
    else
    {
      sodioScambiabile=sodioScambiabile.replace(',','.');
      sodioScambiabile=Utili.nf0.format(Double.parseDouble(sodioScambiabile));
      sodioScambiabile=sodioScambiabile.replace('.',',');
      return sodioScambiabile;
    }
  }

  public String getSodioScambiabile()
  {
    return this.sodioScambiabile;
  }
  public void setSodioScambiabile( String newSodioScambiabile )
  {
    if (newSodioScambiabile!=null) sodioScambiabile=newSodioScambiabile.replace(',','.');
    else this.sodioScambiabile = newSodioScambiabile;
  }

  public String getCalcioScambiabileMeq100PDF()
  {
    if (calcioScambiabileMeq100==null) return "";
    else
    {
      calcioScambiabileMeq100=calcioScambiabileMeq100.replace(',','.');
      calcioScambiabileMeq100=Utili.nf2.format(Double.parseDouble(calcioScambiabileMeq100));
      calcioScambiabileMeq100=calcioScambiabileMeq100.replace('.',',');
      return calcioScambiabileMeq100;
    }
  }

  public String getCalcioScambiabileMeq100()
  {
    return this.calcioScambiabileMeq100;
  }
  public void setCalcioScambiabileMeq100( String newCalcioScambiabileMeq100 )
  {
    if (newCalcioScambiabileMeq100!=null) calcioScambiabileMeq100=newCalcioScambiabileMeq100.replace(',','.');
    else this.calcioScambiabileMeq100 = newCalcioScambiabileMeq100;
  }

  public String getMagnesioScambiabileMeq100PDF()
  {
    if (magnesioScambiabileMeq100==null) return "";
    else
    {
      magnesioScambiabileMeq100=magnesioScambiabileMeq100.replace(',','.');
      magnesioScambiabileMeq100=Utili.nf2.format(Double.parseDouble(magnesioScambiabileMeq100));
      magnesioScambiabileMeq100=magnesioScambiabileMeq100.replace('.',',');
      return magnesioScambiabileMeq100;
    }
  }

  public String getMagnesioScambiabileMeq100()
  {
    return this.magnesioScambiabileMeq100;
  }
  public void setMagnesioScambiabileMeq100( String newMagnesioScambiabileMeq100 )
  {
    if (newMagnesioScambiabileMeq100!=null) magnesioScambiabileMeq100=newMagnesioScambiabileMeq100.replace(',','.');
    else this.magnesioScambiabileMeq100 = newMagnesioScambiabileMeq100;
  }

  public String getPotassioScambiabileMeq100PDF()
  {
    if (potassioScambiabileMeq100==null) return "";
    else
    {
      potassioScambiabileMeq100=potassioScambiabileMeq100.replace(',','.');
      potassioScambiabileMeq100=Utili.nf2.format(Double.parseDouble(potassioScambiabileMeq100));
      potassioScambiabileMeq100=potassioScambiabileMeq100.replace('.',',');
      return potassioScambiabileMeq100;
    }
  }

  public String getPotassioScambiabileMeq100()
  {
    return this.potassioScambiabileMeq100;
  }
  public void setPotassioScambiabileMeq100( String newPotassioScambiabileMeq100 )
  {
    if (newPotassioScambiabileMeq100!=null) potassioScambiabileMeq100=newPotassioScambiabileMeq100.replace(',','.');
    else this.potassioScambiabileMeq100 = newPotassioScambiabileMeq100;
  }

  public String getSodioScambiabileMeq100PDF()
  {
    if (sodioScambiabileMeq100==null) return "";
    else
    {
      sodioScambiabileMeq100=sodioScambiabileMeq100.replace(',','.');
      sodioScambiabileMeq100=Utili.nf2.format(Double.parseDouble(sodioScambiabileMeq100));
      sodioScambiabileMeq100=sodioScambiabileMeq100.replace('.',',');
      return sodioScambiabileMeq100;
    }
  }

  public String getSodioScambiabileMeq100()
  {
    return this.sodioScambiabileMeq100;
  }
  public void setSodioScambiabileMeq100( String newSodioScambiabileMeq100 )
  {
    if (newSodioScambiabileMeq100!=null) sodioScambiabileMeq100=newSodioScambiabileMeq100.replace(',','.');
    else this.sodioScambiabileMeq100 = newSodioScambiabileMeq100;
  }

  public String getCalcioScambiabileCscPDF()
  {
    if (calcioScambiabileCsc==null) return "";
    else
    {
      calcioScambiabileCsc=calcioScambiabileCsc.replace(',','.');
      calcioScambiabileCsc=Utili.nf1.format(Double.parseDouble(calcioScambiabileCsc));
      calcioScambiabileCsc=calcioScambiabileCsc.replace('.',',');
      return calcioScambiabileCsc;
    }
  }

  public String getCalcioScambiabileCsc()
  {
    return this.calcioScambiabileCsc;
  }
  public void setCalcioScambiabileCsc( String newCalcioScambiabileCsc )
  {
    if (newCalcioScambiabileCsc!=null) calcioScambiabileCsc=newCalcioScambiabileCsc.replace(',','.');
    else this.calcioScambiabileCsc = newCalcioScambiabileCsc;
  }

  public String getMagnesioScambiabileCscPDF()
  {
    if (magnesioScambiabileCsc==null) return "";
    else
    {
      magnesioScambiabileCsc=magnesioScambiabileCsc.replace(',','.');
      magnesioScambiabileCsc=Utili.nf1.format(Double.parseDouble(magnesioScambiabileCsc));
      magnesioScambiabileCsc=magnesioScambiabileCsc.replace('.',',');
      return magnesioScambiabileCsc;
    }
  }

  public String getMagnesioScambiabileCsc()
  {
    return this.magnesioScambiabileCsc;
  }
  public void setMagnesioScambiabileCsc( String newMagnesioScambiabileCsc )
  {
    if (newMagnesioScambiabileCsc!=null) magnesioScambiabileCsc=newMagnesioScambiabileCsc.replace(',','.');
    else this.magnesioScambiabileCsc = newMagnesioScambiabileCsc;
  }

  public String getPotassioScambiabileCscPDF()
  {
    if (potassioScambiabileCsc==null) return "";
    else
    {
      potassioScambiabileCsc=potassioScambiabileCsc.replace(',','.');
      potassioScambiabileCsc=Utili.nf1.format(Double.parseDouble(potassioScambiabileCsc));
      potassioScambiabileCsc=potassioScambiabileCsc.replace('.',',');
      return potassioScambiabileCsc;
    }
  }

  public String getPotassioScambiabileCsc()
  {
    return this.potassioScambiabileCsc;
  }
  public void setPotassioScambiabileCsc( String newPotassioScambiabileCsc )
  {
    if (newPotassioScambiabileCsc!=null) potassioScambiabileCsc=newPotassioScambiabileCsc.replace(',','.');
    else this.potassioScambiabileCsc = newPotassioScambiabileCsc;
  }

  public String getSodioScambiabileCscPDF()
  {
    if (sodioScambiabileCsc==null) return "";
    else
    {
      sodioScambiabileCsc=sodioScambiabileCsc.replace(',','.');
      sodioScambiabileCsc=Utili.nf1.format(Double.parseDouble(sodioScambiabileCsc));
      sodioScambiabileCsc=sodioScambiabileCsc.replace('.',',');
      return sodioScambiabileCsc;
    }
  }

  public String getSodioScambiabileCsc()
  {
    return this.sodioScambiabileCsc;
  }
  public void setSodioScambiabileCsc( String newSodioScambiabileCsc )
  {
    if (newSodioScambiabileCsc!=null) sodioScambiabileCsc=newSodioScambiabileCsc.replace(',','.');
    else this.sodioScambiabileCsc = newSodioScambiabileCsc;
  }

  public String getSaturazioneBasica()
  {
    return this.saturazioneBasica;
  }
  public void setSaturazioneBasica( String newSaturazioneBasica )
  {
    if (newSaturazioneBasica!=null) saturazioneBasica=newSaturazioneBasica.replace(',','.');
    else this.saturazioneBasica = newSaturazioneBasica;
  }

  public String getCaMgPDF()
  {
    if (caMg==null) return "";
    else
    {
      caMg=caMg.replace(',','.');
      caMg=Utili.nf1.format(Double.parseDouble(caMg));
      caMg=caMg.replace('.',',');
      return caMg;
    }
  }

  public String getCaMg()
  {
    return this.caMg;
  }
  public void setCaMg( String newCaMg )
  {
    if (newCaMg!=null) caMg=newCaMg.replace(',','.');
    else this.caMg = newCaMg;
  }

  public String getCaKPDF()
  {
    if (caK==null) return "";
    else
    {
      caK=caK.replace(',','.');
      caK=Utili.nf1.format(Double.parseDouble(caK));
      caK=caK.replace('.',',');
      return caK;
    }
  }

  public String getCaK()
  {
    return this.caK;
  }
  public void setCaK( String newCaK )
  {
    if (newCaK!=null) caK=newCaK.replace(',','.');
    else this.caK = newCaK;
  }

  public String getMgKPDF()
  {
    if (mgK==null) return "";
    else
    {
      mgK=mgK.replace(',','.');
      mgK=Utili.nf1.format(Double.parseDouble(mgK));
      mgK=mgK.replace('.',',');
      return mgK;
    }
  }

  public String getMgK()
  {
    return this.mgK;
  }
  public void setMgK( String newMgK )
  {
    if (newMgK!=null) mgK=newMgK.replace(',','.');
    else this.mgK = newMgK;
  }
}