package it.csi.agrc;

import it.csi.cuneo.*;
import java.sql.*;
//import it.csi.jsf.web.pool.*;
import java.util.*;

/**
 * <p>Title: Agrichim - Front Office</p>
 * <p>Description: Richiesta analisi chimiche su campioni biologici agrari</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: CSI Piemonte - Progettazione e Sviluppo - Cuneo</p>
 * @author Michele Piantà, Piergiorgio Chiriotti
 * @version 1.0.0
 */

public abstract class Campione  extends BeanDataSource implements Modello
{
  private long idRichiesta;
  private String idSpecie;
  private String campioneTerreno;
  private String giornoCampionamento;
  private String meseCampionamento;
  private String annoCampionamento;
  private String idColtura;


  public abstract void select()
      throws Exception, SQLException;

  public abstract int insert()
      throws Exception, SQLException;

  public abstract int update()
      throws Exception, SQLException;

  public void setIdRichiesta(long idRichiesta)
  {
    this.idRichiesta = idRichiesta;
  }
  public long getIdRichiesta()
  {
    return idRichiesta;
  }
  public void setIdSpecie(String idSpecie)
  {
    this.idSpecie = idSpecie;
  }
  public String getIdSpecie()
  {
    return idSpecie;
  }
  public void setCampioneTerreno(String campioneTerreno)
  {
    this.campioneTerreno = campioneTerreno;
  }
  public String getCampioneTerreno()
  {
    return campioneTerreno;
  }
  public void setGiornoCampionamento(String giornoCampionamento)
  {
    this.giornoCampionamento = giornoCampionamento;
  }
  public String getGiornoCampionamento()
  {
    return giornoCampionamento;
  }
  public void setMeseCampionamento(String meseCampionamento)
  {
    this.meseCampionamento = meseCampionamento;
  }
  public String getMeseCampionamento()
  {
    return meseCampionamento;
  }
  public void setAnnoCampionamento(String annoCampionamento)
  {
    this.annoCampionamento = annoCampionamento;
  }
  public String getAnnoCampionamento()
  {
    return annoCampionamento;
  }

  public void setDataCampionamento(String dataCampionamento)
  {
    StringTokenizer st=new StringTokenizer(dataCampionamento);
    if (st.hasMoreElements())
      this.setGiornoCampionamento(st.nextToken());
    if (st.hasMoreElements())
      this.setMeseCampionamento(st.nextToken());
    if (st.hasMoreElements())
      this.setAnnoCampionamento(st.nextToken());
  }

  public String getDataCampionamento()
  {
    if (this.getAnnoCampionamento()==null
            ||
        this.getMeseCampionamento()==null
            ||
        this.getGiornoCampionamento()==null) return null;
    return this.getGiornoCampionamento()+"/"+
           this.getMeseCampionamento()+"/"+
           this.getAnnoCampionamento();
  }
  public void setIdColtura(String idColtura)
  {
    this.idColtura = idColtura;
  }
  public String getIdColtura()
  {
    return idColtura;
  }

}