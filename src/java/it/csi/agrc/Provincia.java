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

public class Provincia
{

  public Provincia ()
  {
  }
  public Provincia ( String idProvincia, String idRegione, String siglaProvincia, String descrizione )
  {
    this.idProvincia=idProvincia;
    this.idRegione=idRegione;
    this.siglaProvincia=siglaProvincia;
    this.descrizione=descrizione;
  }
  public Provincia ( String idProvincia, String siglaProvincia )
  {
    this.idProvincia=idProvincia;
    this.siglaProvincia=siglaProvincia;
  }

  public String idProvincia;
  public String idRegione;
  public String siglaProvincia;
  public String descrizione;

  public String getIdProvincia()
  {
    return this.idProvincia;
  }
  public void setIdProvincia( String newIdProvincia )
  {
    this.idProvincia = newIdProvincia;
  }

  public String getIdRegione()
  {
    return this.idRegione;
  }
  public void setIdRegione( String newIdRegione )
  {
    this.idRegione = newIdRegione;
  }

  public String getSiglaProvincia()
  {
    return this.siglaProvincia;
  }
  public void setSiglaProvincia( String newSiglaProvincia )
  {
    this.siglaProvincia = newSiglaProvincia;
  }

  public String getDescrizione()
  {
    return this.descrizione;
  }
  public void setDescrizione( String newDescrizione )
  {
    this.descrizione = newDescrizione;
  }
}