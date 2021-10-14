package it.csi.agrc;
import java.io.Serializable;
import java.util.Hashtable;

import it.csi.cuneo.BeanDataSource;

/**
 * Title:        Agrichim - Front Office
 * Description:  Richiesta analisi chimiche su campioni biologici agrari
 * Copyright:    Copyright (c) 2003
 * Company:      CSI Piemonte - Progettazione e Sviluppo - Cuneo
 * @author Michele Piantà, Piergiorgio Chiriotti
 * @version 1.0.0
 */

public class BeanAnalisi extends BeanDataSource implements Serializable
{
  private static final long serialVersionUID = -8059975067662335723L;

  private Hashtable<?, ?> analisi;
  private Hashtable<?, ?> costoAnalisi;
  private String importoSpedizione;

  @SuppressWarnings("rawtypes")
  public Hashtable<?, ?> getAnalisi(){
	  if(analisi == null)
		  analisi = new Hashtable(22,0.95f);
	  return analisi;
  }
  
  public void setAnalisi(Hashtable<?, ?> analisi){
	  this.analisi = analisi;
  }

  public void setCostoAnalisi(Hashtable<?, ?> costoAnalisi) {
    this.costoAnalisi = costoAnalisi;
  }
  @SuppressWarnings("rawtypes")
  public Hashtable<?, ?> getCostoAnalisi() {
	  if(costoAnalisi == null)
		  costoAnalisi = new Hashtable(22,0.95f);
    return costoAnalisi;
  }
  public void setImportoSpedizione(String importoSpedizione)
  {
    this.importoSpedizione = importoSpedizione;
  }
  public String getImportoSpedizione()
  {
    return importoSpedizione;
  }
}