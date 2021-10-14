package it.csi.cuneo;
import it.csi.jsf.web.pool.*;
import java.io.*;
import java.sql.*;
//import javax.sql.DataSource;
import it.csi.agrc.*;

/**
 * Title:        Agrichim - Front Office
 * Description:  Richiesta analisi chimiche su campioni biologici agrari
 * Copyright:    Copyright (c) 2003
 * Company:      CSI Piemonte - Progettazione e Sviluppo - Cuneo
 * @author Michele Piantà, Piergiorgio Chiriotti
 * @version 1.0.0
 */

public class BeanDataSource extends BeanDAO
{
  private Autenticazione aut;
  private javax.sql.DataSource dataSource;

  public BeanDataSource()
  {
  }

  public Autenticazione getAut()
  {
    return aut;
  }

  public void setAut(Autenticazione aut)
  {
    this.aut = aut;
  }

  private void setLog(String fileName)
  {
    try
    {
        DriverManager.setLogWriter(
            new PrintWriter(new FileOutputStream(fileName)));
    }
    catch (FileNotFoundException e)
    {
        System.err.println(e.getMessage());
    }
  }

  public void setDataSource(Object obj)
  {
    if (Utili.POOLMAN)
      this.setGenericPool((it.csi.jsf.web.pool.GenericPool)obj);
    else
      this.dataSource = (javax.sql.DataSource)obj;
  }

  public void setDataSource(javax.sql.DataSource dataSource)
  {
    this.dataSource = dataSource;
  }

  public javax.sql.DataSource getDataSource()
  {
    return dataSource;
  }

  public Connection getConnection()
  throws Exception, SQLException
  {
    if (Utili.POOLMAN)
    {
      //Sono in ambiente TomCat quindi restuituisco una connessione fornita
      //da PoolMan
      return this.getGenericPool().getConnection();
    }
    else
    {
      //Sono in ambiente TomCat quindi restuituisco una connessione fornita
      //da DataSource
      return dataSource.getConnection();
    }
  }

  public boolean isConnection()
  throws Exception, SQLException
  {
    if (Utili.POOLMAN)
    {
      //Sono in ambiente TomCat quindi controllo se Poolman è inizializzato
      if (this.getGenericPool() == null) return false;
      else return true;
    }
    else
    {
      //Sono in ambiente WebLogic quindi controllo se il DataSource è
      // inizializzato
      if (dataSource == null) return false;
      else return true;
    }
  }

  protected InputStream getStream(String resource)
  {
    return getClass().getClassLoader().getResourceAsStream(resource);
  }

  /**
   * Controlla la Date e la converte in Timestamp
   *
   * @param val
   * @return
   */
  protected java.sql.Timestamp convertDateToTimestamp(java.util.Date val)
  {
    if (val == null) return null;
    return new Timestamp(val.getTime());
  }
}