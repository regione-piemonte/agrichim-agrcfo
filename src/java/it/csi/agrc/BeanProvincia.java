package it.csi.agrc;
import it.csi.cuneo.*;
import java.util.*;
import java.io.*;

/**
 * <p>Title: Agrichim - Front Office</p>
 * <p>Description: Richiesta analisi chimiche su campioni biologici agrari</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: CSI Piemonte - Progettazione e Sviluppo - Cuneo</p>
 * @author Michele Piantà, Piergiorgio Chiriotti
 * @version 1.0.0
 */

public class BeanProvincia extends BeanDataSource implements Serializable
{
  private static final long serialVersionUID = 545442128616595488L;
	
  private String idProvincia[];
  private String siglaProvincia[];

  public String[] getSiglaProvincia()
  {
    return siglaProvincia;
  }
  public void setSiglaProvincia(Vector sigla)
  {
    siglaProvincia = (String[]) sigla.toArray(new String[0]);
  }
  public String[] getIdProvincia()
  {
    return idProvincia;
  }
  public void setIdProvincia(Vector id)
  {
    idProvincia = (String[]) id.toArray(new String[0]);
  }
}
