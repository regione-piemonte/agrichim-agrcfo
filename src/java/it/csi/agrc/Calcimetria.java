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

public class Calcimetria extends BeanDataSource
{
  private long idRichiesta;
  private String letturaCalcimetro;
  private String pressioneAtmosferica;
  private String temperatura;
  private String tensioneDiVapore;
  private String carbonatoCalcioTotale;
  private String calcareAttivo;
  private String letturaFerroOssalato;
  private String diluizioneDeterminaFerro;
  private String ferroOssalato;
  private String indicePotereClorosante;

  public Calcimetria ()
  {
  }
  public Calcimetria ( long idRichiesta, String letturaCalcimetro, String pressioneAtmosferica, String temperatura, String tensioneDiVapore, String carbonatoCalcioTotale, String calcareAttivo, String letturaFerroOssalato, String diluizioneDeterminaFerro, String ferroOssalato, String indicePotereClorosante )
  {
    this.idRichiesta=idRichiesta;
    this.letturaCalcimetro=letturaCalcimetro;
    this.pressioneAtmosferica=pressioneAtmosferica;
    this.temperatura=temperatura;
    this.tensioneDiVapore=tensioneDiVapore;
    this.carbonatoCalcioTotale=carbonatoCalcioTotale;
    this.calcareAttivo=calcareAttivo;
    this.letturaFerroOssalato=letturaFerroOssalato;
    this.diluizioneDeterminaFerro=diluizioneDeterminaFerro;
    this.ferroOssalato=ferroOssalato;
    this.indicePotereClorosante=indicePotereClorosante;
  }

  /**
   * Questo metodo va a leggere il record della tabella CALCIMETRIA
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
      query = new StringBuffer("SELECT LETTURA_CALCIMETRO,PRESSIONE_ATMOSFERICA,");
      query.append("TEMPERATURA,TENSIONE_DI_VAPORE,CARBONATO_CALCIO_TOTALE,");
      query.append("CALCARE_ATTIVO,LETTURA_FERRO_OSSALATO,");
      query.append("DILUIZIONE_DETERMINA_FERRO,FERRO_OSSALATO,");
      query.append("INDICE_POTERE_CLOROSANTE ");
      query.append("FROM CALCIMETRIA ");
      query.append("WHERE ID_RICHIESTA = ");
      query.append(getIdRichiesta());
      //CuneoLogger.debug(this,query.toString());
      ResultSet rs=stmt.executeQuery(query.toString());
      String temp;
      if (rs.next())
      {
        temp=rs.getString("LETTURA_CALCIMETRO");
        if (temp!=null) temp=Utili.nf2.format(Double.parseDouble(temp));
        this.setLetturaCalcimetro(temp);

        temp=rs.getString("PRESSIONE_ATMOSFERICA");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setPressioneAtmosferica(temp);

        temp=rs.getString("TEMPERATURA");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setTemperatura(temp);

        temp=rs.getString("TENSIONE_DI_VAPORE");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setTensioneDiVapore(temp);

        temp=rs.getString("CARBONATO_CALCIO_TOTALE");
        if (temp!=null) temp=Utili.nf2.format(Double.parseDouble(temp));
        this.setCarbonatoCalcioTotale(temp);

        temp=rs.getString("CALCARE_ATTIVO");
        if (temp!=null) temp=Utili.nf1.format(Double.parseDouble(temp));
        this.setCalcareAttivo(temp);

        temp=rs.getString("LETTURA_FERRO_OSSALATO");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setLetturaFerroOssalato(temp);

        temp=rs.getString("DILUIZIONE_DETERMINA_FERRO");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setDiluizioneDeterminaFerro(temp);

        temp=rs.getString("FERRO_OSSALATO");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setFerroOssalato(temp);

        temp=rs.getString("INDICE_POTERE_CLOROSANTE");
        if (temp!=null) temp=Utili.nf2.format(Double.parseDouble(temp));
        this.setIndicePotereClorosante(temp);

        return true;
      }
      else return false;
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("select della classe Calcimetria");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("select della classe Calcimetria"
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

  public String getLetturaCalcimetro()
  {
    return this.letturaCalcimetro;
  }
  public void setLetturaCalcimetro( String newLetturaCalcimetro )
  {
    if (newLetturaCalcimetro!=null) letturaCalcimetro=newLetturaCalcimetro.replace(',','.');
    else this.letturaCalcimetro = newLetturaCalcimetro;
  }

  public String getPressioneAtmosferica()
  {
    return this.pressioneAtmosferica;
  }
  public void setPressioneAtmosferica( String newPressioneAtmosferica )
  {
    if (newPressioneAtmosferica!=null) pressioneAtmosferica=newPressioneAtmosferica.replace(',','.');
    else this.pressioneAtmosferica = newPressioneAtmosferica;
  }

  public String getTemperatura()
  {
    return this.temperatura;
  }
  public void setTemperatura( String newTemperatura )
  {
    if (newTemperatura!=null) temperatura=newTemperatura.replace(',','.');
    else temperatura = newTemperatura;
  }

  public String getTensioneDiVapore()
  {
    return this.tensioneDiVapore;
  }
  public void setTensioneDiVapore( String newTensioneDiVapore )
  {
    if (newTensioneDiVapore!=null) tensioneDiVapore=newTensioneDiVapore.replace(',','.');
    else tensioneDiVapore = newTensioneDiVapore;
  }

  public String getCarbonatoCalcioTotalePDF()
  {
    if (carbonatoCalcioTotale==null) return "";
    else
    {
      carbonatoCalcioTotale=carbonatoCalcioTotale.replace(',','.');
      carbonatoCalcioTotale=Utili.nf1.format(Double.parseDouble(carbonatoCalcioTotale));
      carbonatoCalcioTotale=carbonatoCalcioTotale.replace('.',',');
      return carbonatoCalcioTotale;
    }
  }

  public String getCarbonatoCalcioTotale()
  {
    return this.carbonatoCalcioTotale;
  }
  public void setCarbonatoCalcioTotale( String newCarbonatoCalcioTotale )
  {
    if (newCarbonatoCalcioTotale!=null) carbonatoCalcioTotale=newCarbonatoCalcioTotale.replace(',','.');
    else carbonatoCalcioTotale = newCarbonatoCalcioTotale;
  }

  public String getCalcareAttivoPDF()
  {
    if (calcareAttivo==null) return "";
    else
    {
      calcareAttivo=calcareAttivo.replace(',','.');
      calcareAttivo=Utili.nf2.format(Double.parseDouble(calcareAttivo));
      calcareAttivo=calcareAttivo.replace('.',',');
      return calcareAttivo;
    }
  }

  public String getCalcareAttivo()
  {
    return this.calcareAttivo;
  }
  public void setCalcareAttivo( String newCalcareAttivo )
  {
    if (newCalcareAttivo!=null) calcareAttivo=newCalcareAttivo.replace(',','.');
    else calcareAttivo = newCalcareAttivo;
  }

  public String getLetturaFerroOssalato()
  {
    return this.letturaFerroOssalato;
  }
  public void setLetturaFerroOssalato( String newLetturaFerroOssalato )
  {
    if (newLetturaFerroOssalato!=null) letturaFerroOssalato=newLetturaFerroOssalato.replace(',','.');
    else letturaFerroOssalato = newLetturaFerroOssalato;
  }

  public String getDiluizioneDeterminaFerro()
  {
    return this.diluizioneDeterminaFerro;
  }
  public void setDiluizioneDeterminaFerro( String newDiluizioneDeterminaFerro )
  {
    if (newDiluizioneDeterminaFerro!=null) diluizioneDeterminaFerro=newDiluizioneDeterminaFerro.replace(',','.');
    else this.diluizioneDeterminaFerro = newDiluizioneDeterminaFerro;
  }

  public String getFerroOssalato()
  {
    return this.ferroOssalato;
  }
  public void setFerroOssalato( String newFerroOssalato )
  {
    if (newFerroOssalato!=null) ferroOssalato=newFerroOssalato.replace(',','.');
    else this.ferroOssalato = newFerroOssalato;
  }

  public String getIndicePotereClorosante()
  {
    return this.indicePotereClorosante;
  }
  public void setIndicePotereClorosante( String newIndicePotereClorosante )
  {
    if (newIndicePotereClorosante!=null) indicePotereClorosante=newIndicePotereClorosante.replace(',','.');
    else this.indicePotereClorosante = newIndicePotereClorosante;
  }
}