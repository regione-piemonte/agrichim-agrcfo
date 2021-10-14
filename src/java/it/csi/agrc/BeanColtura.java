package it.csi.agrc;
import it.csi.cuneo.*;
import java.util.*;
//import java.sql.*;
//import it.csi.jsf.web.pool.*;
import java.io.*;

/**
 * Title:        Agrichim - Front Office
 * Description:  Richiesta analisi chimiche su campioni biologici agrari
 * Copyright:    Copyright (c) 2003
 * Company:      CSI Piemonte - Progettazione e Sviluppo - Cuneo
 * @author Michele Piantà, Piergiorgio Chiriotti
 * @version 1.0.0
 */

public class BeanColtura extends BeanDataSource implements Serializable
{
  private static final long serialVersionUID = 2975485871472833911L;

  private String codSuperficie[];
  private String descSuperficie[];

  private String codEsposizione[];
  private String descEsposizione[];

  private String codConcimazioneOrganica[];
  private String descConcimazioneOrganica[];

  private String codConcimazione[];
  private String descConcimazione[];

  private String codProduttivita[];
  private String descProduttivita[];

  private String codProfonditaPrelievo[];
  private String descProfonditaPrelievo[];

  private String codColtura[];
  private String descColtura[];

  private String codLavorazioneTerreno[];
  private String descLavorazioneTerreno[];

  private String codIrrigazione[];
  private String descIrrigazione[];

  private String codModalitaColtivazione[];
  private String descModalitaColtivazione[];


  public void setCodSuperficie( Vector codVector) {
    this.codSuperficie = (String[]) codVector.toArray(new String[0]);
  }
  public String[] getCodSuperficie() {
    return codSuperficie;
  }
  public void setDescSuperficie( Vector descVector) {
    this.descSuperficie = (String[]) descVector.toArray(new String[0]);
  }
  public String[] getDescSuperficie() {
    return descSuperficie;
  }
  public void setCodEsposizione( Vector codVector) {
    this.codEsposizione = (String[]) codVector.toArray(new String[0]);
  }
  public String[] getCodEsposizione() {
    return codEsposizione;
  }
  public void setDescEsposizione( Vector descVector) {
    this.descEsposizione = (String[]) descVector.toArray(new String[0]);
  }
  public String[] getDescEsposizione() {
    return descEsposizione;
  }
  public void setCodConcimazioneOrganica( Vector codVector) {
    this.codConcimazioneOrganica = (String[]) codVector.toArray(new String[0]);
  }
  public String[] getCodConcimazioneOrganica() {
    return codConcimazioneOrganica;
  }
  public void setDescConcimazioneOrganica( Vector descVector) {
    this.descConcimazioneOrganica = (String[]) descVector.toArray(new String[0]);
  }
  public String[] getDescConcimazioneOrganica() {
    return descConcimazioneOrganica;
  }
  public void setCodConcimazione( Vector codVector) {
    this.codConcimazione = (String[]) codVector.toArray(new String[0]);
  }
  public String[] getCodConcimazione() {
    return codConcimazione;
  }
  public void setDescConcimazione( Vector descVector) {
    this.descConcimazione = (String[]) descVector.toArray(new String[0]);
  }
  public String[] getDescConcimazione() {
    return descConcimazione;
  }
  public void setCodProduttivita( Vector codVector) {
    this.codProduttivita = (String[]) codVector.toArray(new String[0]);
  }
  public String[] getCodProduttivita() {
    return codProduttivita;
  }
  public void setDescProduttivita( Vector descVector) {
    this.descProduttivita = (String[]) descVector.toArray(new String[0]);
  }
  public String[] getDescProduttivita() {
    return descProduttivita;
  }
  public void setCodProfonditaPrelievo( Vector codVector) {
    this.codProfonditaPrelievo = (String[]) codVector.toArray(new String[0]);
  }
  public String[] getCodProfonditaPrelievo() {
    return codProfonditaPrelievo;
  }
  public void setDescProfonditaPrelievo( Vector descVector) {
    this.descProfonditaPrelievo = (String[]) descVector.toArray(new String[0]);
  }
  public String[] getDescProfonditaPrelievo() {
    return descProfonditaPrelievo;
  }
  public void setCodColtura( Vector codVector) {
    this.codColtura = (String[]) codVector.toArray(new String[0]);
  }
  public String[] getCodColtura() {
    return codColtura;
  }
  public void setDescColtura( Vector descVector) {
    this.descColtura = (String[]) descVector.toArray(new String[0]);
  }
  public String[] getDescColtura() {
    return descColtura;
  }
  public void setCodLavorazioneTerreno( Vector codVector) {
    this.codLavorazioneTerreno = (String[]) codVector.toArray(new String[0]);
  }
  public String[] getCodLavorazioneTerreno() {
    return codLavorazioneTerreno;
  }
  public void setDescLavorazioneTerreno( Vector descVector) {
    this.descLavorazioneTerreno = (String[]) descVector.toArray(new String[0]);
  }
  public String[] getDescLavorazioneTerreno() {
    return descLavorazioneTerreno;
  }
  public void setCodIrrigazione( Vector codVector) {
    this.codIrrigazione = (String[]) codVector.toArray(new String[0]);
  }
  public String[] getCodIrrigazione() {
    return codIrrigazione;
  }
  public void setDescIrrigazione( Vector descVector) {
    this.descIrrigazione = (String[]) descVector.toArray(new String[0]);
  }
  public String[] getDescIrrigazione() {
    return descIrrigazione;
  }
  public void setCodModalitaColtivazione( Vector codVector) {
    this.codModalitaColtivazione = (String[]) codVector.toArray(new String[0]);
  }
  public String[] getCodModalitaColtivazione() {
    return codModalitaColtivazione;
  }
  public void setDescModalitaColtivazione( Vector descVector) {
    this.descModalitaColtivazione = (String[]) descVector.toArray(new String[0]);
  }
  public String[] getDescModalitaColtivazione() {
    return descModalitaColtivazione;
  }





}