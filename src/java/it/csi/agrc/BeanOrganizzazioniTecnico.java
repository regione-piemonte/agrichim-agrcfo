package it.csi.agrc;

//import it.csi.cuneo.*;
import java.util.*;
//import java.sql.*;
import java.io.*;

/**
 * <p>Title: Agrichim - Front Office</p>
 * <p>Description: Richiesta analisi chimiche su campioni biologici agrari</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: CSI Piemonte - Progettazione e Sviluppo - Cuneo</p>
 * @author Michele Piantà, Piergiorgio Chiriotti
 * @version 1.0.0
 */

/**
 * Contiene al suo interno tutti i tipi di organizzazione tutti i tipi di organizzazione
 **/
public class BeanOrganizzazioniTecnico implements Serializable{
  private static final long serialVersionUID = 6074493136223041741L;

  private String cod[];
  private String desc[];

  public void setCod(Vector codVector)
  {
    cod = (String[]) codVector.toArray(new String[0]);
  }
  public void setDesc(Vector descVector)
  {
    desc = (String[]) descVector.toArray(new String[0]);
  }

  public String [] getCod()
  {
    return cod;
  }
  public String [] getDesc()
  {
    return desc;
  }

}