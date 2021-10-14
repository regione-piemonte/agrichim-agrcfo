package it.csi.agrc;
import it.csi.cuneo.*;
import java.util.*;
import java.sql.*;
//import it.csi.jsf.web.pool.*;

/**
 * Title:        Agrichim - Front Office
 * Description:  Richiesta analisi chimiche su campioni biologici agrari
 * Copyright:    Copyright (c) 2003
 * Company:      CSI Piemonte - Progettazione e Sviluppo - Cuneo
 * @author Michele Piantà, Piergiorgio Chiriotti
 * @version 1.0.0
 */

public class Coltura extends BeanDataSource
{
  public static final int  COLTURA_ERBACEE=1;
  public static final int  COLTURA_FOGLIE_FRUTTA=2;

  private static final String QUERY_SUPERFICIE=
   "SELECT ID_SUPERFICIE,DESCRIZIONE FROM SUPERFICIE ORDER BY ID_SUPERFICIE";
  private static final String QUERY_ESPOSIZIONE=
   "SELECT ID_ESPOSIZIONE,DESCRIZIONE FROM ESPOSIZIONE ORDER BY ID_ESPOSIZIONE";
  private static final String QUERY_CONCIMAZIONE_ORGANICA=
   "SELECT TIPO_CONCIMAZIONE,DESCRIZIONE FROM CONCIMAZIONE_ORGANICA ORDER BY TIPO_CONCIMAZIONE";
  private static final String QUERY_CONCIMAZIONE=
   "SELECT ID_CONCIME,DESCRIZIONE FROM CONCIME ORDER BY DESCRIZIONE";
  private static final String QUERY_SPECIE=
   "SELECT ID_SPECIE,DESCRIZIONE FROM SPECIE_COLTURA WHERE ID_COLTURA =";
  private static final String QUERY_VARIETA=
   "SELECT ID_VARIETA,DESCRIZIONE FROM VARIETA_SPECIE WHERE ID_SPECIE =";
  private static final String QUERY_INNESTO=
   "SELECT ID_INNESTO,DESCRIZIONE FROM PORTA_INNESTO WHERE ID_SPECIE =";
  private static final String QUERY_ALLEVAMENTO=
   "SELECT ID_SISTEMA_ALLEVAMENTO,DESCRIZIONE FROM SISTEMA_ALLEVAMENTO WHERE ID_SPECIE = ";
  private static final String QUERY_PRODUTTIVITA=
   "SELECT CODICE_PRODUTTIVITA,DESCRIZIONE FROM PRODUTTIVITA ORDER BY CODICE_PRODUTTIVITA";
  private static final String QUERY_STADIO_FENOLOGICO=
   "SELECT ID_STADIO_FENOLOGICO,DESCRIZIONE FROM STADIO_FENOLOGICO WHERE UPPER(CODICE_MATERIALE)='";
  private static final String QUERY_PROFONDITA_PRELIEVO=
   "SELECT ID_PROFONDITA,DESCRIZIONE FROM PROFONDITA_PRELIEVO ORDER BY ID_PROFONDITA";
  private static final String QUERY_COLTURA=
   "SELECT ID_COLTURA,DESCRIZIONE FROM CLASSE_COLTURA ORDER BY DESCRIZIONE";
  private static final String QUERY_LAVORAZIONE_TERRENO=
   "SELECT ID_LAVORAZIONE_TERRENO,DESCRIZIONE FROM LAVORAZIONE_TERRENO ORDER BY ID_LAVORAZIONE_TERRENO";
  private static final String QUERY_IRRIGAZIONE=
   "SELECT ID_IRRIGAZIONE,DESCRIZIONE FROM IRRIGAZIONE ORDER BY DESCRIZIONE";
  private static final String QUERY_MODALITA_COLTIVAZIONE=
   "SELECT CODICE_MODALITA_COLTIVAZIONE,DESCRIZIONE FROM MODALITA_COLTIVAZIONE ORDER BY DESCRIZIONE";

  public static final int IMPOSTA_QUERY_SUPERFICIE=1;
  public static final int IMPOSTA_QUERY_ESPOSIZIONE=2;
  public static final int IMPOSTA_QUERY_SPECIE=3;
  public static final int IMPOSTA_QUERY_VARIETA=4;
  public static final int IMPOSTA_QUERY_INNESTO=5;
  public static final int IMPOSTA_QUERY_ALLEVAMENTO=6;
  public static final int IMPOSTA_QUERY_CONCIMAZIONE_ORGANICA=7;
  public static final int IMPOSTA_QUERY_CONCIMAZIONE=8;
  public static final int IMPOSTA_QUERY_PRODUTTIVITA=9;
  public static final int IMPOSTA_QUERY_STADIO_FENOLOGICO=10;
  public static final int IMPOSTA_QUERY_PROFONDITA_PRELIEVO=11;
  public static final int IMPOSTA_QUERY_COLTURA=12;
  public static final int IMPOSTA_QUERY_LAVORAZIONE_TERRENO=13;
  public static final int IMPOSTA_QUERY_IRRIGAZIONE=14;
  public static final int IMPOSTA_QUERY_MODALITA_COLTIVAZIONE=15;

  public Coltura()
  {
  }
  public Coltura(Object dataSource, Autenticazione aut)
  {
    this.setDataSource(dataSource);
    this.setAut(aut);
  }


  /**
   * Se scelta è uguale a:
   * - COLTURA_ERBACEE esegue la query che estrae tutte le classi coltura
   *   per quelle di tipo erbaceo
   * - COLTURA_FOGLIE_FRUTTA esegue la query che estrae tutte le classi coltura
   *   per quelle di tipo arboreo
   *
   * Viene utilizzata per restituire i codici e le descrizioni dalle
   * tabelle di transcodifica
   **/
  public void selectCodDescClasse(int scelta,Vector cod,Vector desc)
    throws Exception, SQLException
  {
    cod.clear();
    desc.clear();
    if (!isConnection())
      throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn=null;
    StringBuffer query=new StringBuffer();
    try
    {
      conn=getConnection();
      Statement stmt = conn.createStatement();
      query.append("SELECT DISTINCT CC.ID_COLTURA,CC.DESCRIZIONE ");
      query.append("FROM SPECIE_COLTURA SC, CLASSE_COLTURA CC ");
      query.append("WHERE SC.ID_COLTURA = CC.ID_COLTURA ");
      query.append("AND SC.TIPO_COLTURA = ");
      switch(scelta)
      {
        case COLTURA_ERBACEE:query.append("'E'");
                             break;
        case COLTURA_FOGLIE_FRUTTA:query.append("'A'");;
                                   break;
      }
      //CuneoLogger.debug(this,query.toString());
      ResultSet rset = stmt.executeQuery(query.toString());
      String temp;
      while (rset.next())
      {
         temp=rset.getString(1);
         if (temp==null) temp="";
         cod.add(temp);

         temp=rset.getString(2);
         if (temp==null) temp="";
         desc.add(temp);
      }
      rset.close();
      stmt.close();
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("selectCodDesc della classe Coltura");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("selectCodDesc della classe Coltura"
                            +": non è una SQLException ma una Exception"
                            +" generica");
      this.getAut().setContenutoQuery(query.toString());
      throw (e);
    }
    finally
    {
      if (conn!=null) conn.close();
    }
  }


    /**
     * Se scelta è uguale a:
     * - IMPOSTA_QUERY_SPECIE esegue la query che estrae tutte le specie
     *   relative ad una coltura
     * - IMPOSTA_QUERY_VARIETA esegue la query che estrae tutte le varietà
     *   relative ad un specie
     * - IMPOSTA_QUERY_INNESTO esegue la query che estrae tutti gli innesti
     *   relativi ad un specie
     * - IMPOSTA_QUERY_ALLEVAMENTO esegue la query che estrae tutti gli
     *   allevamenti relativi ad un specie
     *
     * Viene utilizzata per restituire i codici e le descrizioni dalle
     * tabelle di transcodifica
     **/
    public void selectCodDescSpecie(int scelta,
                                    Vector cod,
                                    Vector desc,
                                    String codice)
    throws Exception, SQLException
    {
      if (!isConnection())
         throw new Exception("Riferimento a DataSource non inizializzato");
      Connection conn=null;
      String query=null;
      try
      {
        conn=getConnection();
        Statement stmt = conn.createStatement();
        switch(scelta)
        {
          case IMPOSTA_QUERY_SPECIE:query=QUERY_SPECIE;
                                    break;
          case IMPOSTA_QUERY_VARIETA:query=QUERY_VARIETA;
                                     break;
          case IMPOSTA_QUERY_INNESTO:query=QUERY_INNESTO;
                                     break;
          case IMPOSTA_QUERY_ALLEVAMENTO:query=QUERY_ALLEVAMENTO;
                                         break;
        }
        query+=(codice);
        query+=(" ORDER BY DESCRIZIONE");
        //CuneoLogger.debug(this,query.toString());
        ResultSet rset = stmt.executeQuery(query.toString());
        String temp;
        while (rset.next())
        {
           temp=rset.getString(1);
           if (temp==null) temp="";
           cod.add(temp);

           temp=rset.getString(2);
           if (temp==null) temp="";
           desc.add(temp);
        }
        rset.close();
        stmt.close();
      }
      catch(java.sql.SQLException ex)
      {
        this.getAut().setQuery("selectCodDescspecie della classe "
                              +"Coltura");
        this.getAut().setContenutoQuery(query.toString());
        throw (ex);
      }
      catch(Exception e)
      {
        this.getAut().setQuery("selectCodDescspecie della classe "
                              +"Coltura: non è una "
                              +"SQLException ma una Exception generica");
        this.getAut().setContenutoQuery(query.toString());
        throw (e);
      }
      finally
      {
        if (conn!=null) conn.close();
      }
  }


  /**
   * Se scelta è uguale a:
   * - IMPOSTA_QUERY_SUPERFICIE esegue la query che estrae tutti i valori
   *   che può assumere una superficie
   * - IMPOSTA_QUERY_ESPOSIZIONE esegue la query che estrae tutte le direzioni
   *   di esposizione
   * - IMPOSTA_QUERY_CONCIMAZIONE_ORGANICA esegue la query che estrae tutte le
   *   concimazione organiche
   * - IMPOSTA_QUERY_CONCIMAZIONE esegue la query che estrae tutti i
   *   concimi
   * - IMPOSTA_QUERY_PRODUTTIVITA esegue la query che estrae le produttività
   *   indicativa dell'anno passato
   * - IMPOSTA_QUERY_STADIO_FENOLOGICO esegue la query che estrae tutti
   *   gli stadi fenologici relativi al materiale del campione
   * - IMPOSTA_QUERY_PROFONDITA_PRELIEVO esegue la query che estrae tutti
   *   i valori della tabella PROFONDITA_PRELIEVO
   * - IMPOSTA_QUERY_COLTURA esegue la query che estrae tutti i valori dalla
   *   tabella CLASSE_COLTURA. A differenza del metodo selectCodDescClasse
   *   che distingue tra colture erbacee ed arboree questo le prende tutte
   * - IMPOSTA_QUERY_LAVORAZIONE_TERRENO esegue la query che estrae tutti i
   *   valori dalla tabella LAVORAZIONE_TERRENO
   * - IMPOSTA_QUERY_IRRIGAZIONE esegue la query che estrae tutti i valori dalla
   *   tabella IRRIGAZIONE
   * - IMPOSTA_QUERY_MODALITA_COLTIVAZIONE esegue la query che estrae tutti i
   *   valori dalla tabella COLTIVAZIONE
   *
   * Viene utilizzata per restituire i codici e le descrizioni dalle
   * tabelle di transcodifica
   **/
  public void selectCodDesc(int scelta,Vector cod,Vector desc)
    throws Exception, SQLException
  {
    cod.clear();
    desc.clear();
    if (!isConnection())
      throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn=null;
    String query=null;
    try
    {
      conn=getConnection();
      Statement stmt = conn.createStatement();
      switch(scelta)
      {
        case IMPOSTA_QUERY_SUPERFICIE:query=QUERY_SUPERFICIE;
                                      break;
        case IMPOSTA_QUERY_ESPOSIZIONE:query=QUERY_ESPOSIZIONE;
                                       break;
        case IMPOSTA_QUERY_CONCIMAZIONE_ORGANICA:query=QUERY_CONCIMAZIONE_ORGANICA;
                                                 break;
        case IMPOSTA_QUERY_CONCIMAZIONE:query=QUERY_CONCIMAZIONE;
                                        break;
        case IMPOSTA_QUERY_PRODUTTIVITA:query=QUERY_PRODUTTIVITA;
                                        break;
        case IMPOSTA_QUERY_STADIO_FENOLOGICO:query=QUERY_STADIO_FENOLOGICO;
                                             query+=this.getAut().getCodMateriale();
                                             query+="' ORDER BY DESCRIZIONE";
                                             break;
        case IMPOSTA_QUERY_PROFONDITA_PRELIEVO:query=QUERY_PROFONDITA_PRELIEVO;
                                               break;
        case IMPOSTA_QUERY_COLTURA:query=QUERY_COLTURA;
                                   break;
        case IMPOSTA_QUERY_LAVORAZIONE_TERRENO:query=QUERY_LAVORAZIONE_TERRENO;
                                                break;
        case IMPOSTA_QUERY_IRRIGAZIONE:query=QUERY_IRRIGAZIONE;
                                       break;
        case IMPOSTA_QUERY_MODALITA_COLTIVAZIONE:query=QUERY_MODALITA_COLTIVAZIONE;
                                                 break;
      }
      ResultSet rset = stmt.executeQuery(query);
      String temp;
      while (rset.next())
      {
        temp=rset.getString(1);
        if (temp==null) temp="";
        cod.add(temp);

        temp=rset.getString(2);
        if (temp==null) temp="";
        desc.add(temp);
      }
      rset.close();
      stmt.close();
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("selectCodDesc della classe Coltura");
      this.getAut().setContenutoQuery(query);
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("selectCodDesc della classe Coltura"
                            +": non è una SQLException ma una Exception"
                            +" generica");
      this.getAut().setContenutoQuery(query);
      throw (e);
    }
    finally
    {
      if (conn!=null) conn.close();
    }
  }

}