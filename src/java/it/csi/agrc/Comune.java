package it.csi.agrc;

//import it.csi.cuneo.*;

/**
 * <p>Title: Agrichim - Front Office</p>
 * <p>Description: Richiesta analisi chimiche su campioni biologici agrari</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: CSI Piemonte - Progettazione e Sviluppo - Cuneo</p>
 * @author Michele Piantà, Piergiorgio Chiriotti
 * @version 1.0.0
 */

public class Comune
{

  public Comune ()
  {
  }
  public Comune ( String codiceIstat, String codiceIstatOld, String provincia, String provinciaOld, String descrizione, String cap, String codiceFiscale )
  {
    this.codiceIstat=codiceIstat;
    this.codiceIstatOld=codiceIstatOld;
    this.provincia=provincia;
    this.provinciaOld=provinciaOld;
    this.descrizione=descrizione;
    this.cap=cap;
    this.codiceFiscale=codiceFiscale;
  }
  public Comune ( String codiceIstat, String descrizione, String provincia )
  {
    this.codiceIstat=codiceIstat;
    this.descrizione=descrizione;
    this.provincia=provincia;
  }
  public Comune ( String codiceIstat, String descrizione )
  {
    this.codiceIstat=codiceIstat;
    this.descrizione=descrizione;
  }

  public String codiceIstat;
  public String codiceIstatOld;
  public String provincia;
  public String provinciaOld;
  public String descrizione;
  public String cap;
  public String codiceFiscale;

  public String getCodiceIstat()
  {
    return this.codiceIstat;
  }
  public void setCodiceIstat( String newCodiceIstat )
  {
    this.codiceIstat = newCodiceIstat;
  }

  public String getCodiceIstatOld()
  {
    return this.codiceIstatOld;
  }
  public void setCodiceIstatOld( String newCodiceIstatOld )
  {
    this.codiceIstatOld = newCodiceIstatOld;
  }

  public String getProvincia()
  {
    return this.provincia;
  }
  public void setProvincia( String newProvincia )
  {
    this.provincia = newProvincia;
  }

  public String getProvinciaOld()
  {
    return this.provinciaOld;
  }
  public void setProvinciaOld( String newProvinciaOld )
  {
    this.provinciaOld = newProvinciaOld;
  }

  public String getDescrizione()
  {
    return this.descrizione;
  }
  public void setDescrizione( String newDescrizione )
  {
    this.descrizione = newDescrizione;
  }

  public String getCap()
  {
    return this.cap;
  }
  public void setCap( String newCap )
  {
    this.cap = newCap;
  }

  public String getCodiceFiscale()
  {
    return this.codiceFiscale;
  }
  public void setCodiceFiscale( String newCodiceFiscale )
  {
    this.codiceFiscale = newCodiceFiscale;
  }
}