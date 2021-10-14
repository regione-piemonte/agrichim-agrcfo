package it.csi.agrc;

//import java.sql.*;
import it.csi.cuneo.*;
import java.io.*;
import java.awt.*;
//import javax.swing.*;

/**
 * <p>Title: Agrichim - Front Office</p>
 * <p>Description: Richiesta analisi chimiche su campioni biologici agrari</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: CSI Piemonte - Progettazione e Sviluppo - Cuneo</p>
 * @author Michele Piantà, Piergiorgio Chiriotti
 * @version 1.0.0
 */

public class Responsabile extends BeanDataSource implements Serializable
{
  private static final long serialVersionUID = -8855508654657954602L;

  private String titoloOnorifico;
  private String nome;
  private String cognome;
  private String idFirma;
  private Image firma;
  private String note;


  public void setTitoloOnorifico(String titoloOnorifico)
  {
    this.titoloOnorifico = titoloOnorifico;
  }
  public String getTitoloOnorifico()
  {
    return titoloOnorifico;
  }

  public void setNome(String nome)
  {
    this.nome = nome;
  }
  public String getNome()
  {
    return nome;
  }

  public void setCognome(String cognome)
  {
    this.cognome = cognome;
  }
  public String getCognome()
  {
    return cognome;
  }

  public void setIdFirma(String idFirma)
  {
    this.idFirma = idFirma;
  }
  public String getIdFirma()
  {
    return idFirma;
  }
  public void setFirma(Image firma) {
    this.firma = firma;
  }
  public Image getFirma() {
    return firma;
  }
  public void setNote(String note)
  {
    this.note = note;
  }
  public String getNote()
  {
    return note;
  }
}