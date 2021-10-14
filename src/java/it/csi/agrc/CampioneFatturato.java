package it.csi.agrc;

import it.csi.cuneo.*;
import java.sql.*;
import java.util.*;

/**
 * Title:        Piani di concimazione ed utilizzazione agronomica
 * Description:  Impostazione, simulazione, gestione e stampa di PCUA & C.
 * Copyright:    Copyright (c) 2002
 * Company:      CSI Piemonte
 * @author
 * @version 1.0
 */

public class CampioneFatturato extends BeanDataSource
{
  private String idRichiesta;
  private String anno;
  private String numeroFattura;
  private String importoImponibile;
  private String importoIva;
  private String importoSpedizione;


  public CampioneFatturato ()
  {
  }
  public CampioneFatturato (  String idRichiesta, String anno,
                              String numeroFattura, String importoImponibile,
                              String importoIva, String importoSpedizione )
  {
    this.idRichiesta = idRichiesta;
    this.anno = anno;
    this.numeroFattura = numeroFattura;

    if (importoImponibile!=null) importoImponibile=
            Utili.nf2.format(Double.parseDouble(importoImponibile));
    this.importoImponibile = importoImponibile;

    if (importoIva!=null) importoIva=
            Utili.nf2.format(Double.parseDouble(importoIva));
    this.importoIva = importoIva;

    if (importoSpedizione!=null) importoSpedizione=
            Utili.nf2.format(Double.parseDouble(importoSpedizione));
    this.importoSpedizione = importoSpedizione;
  }

  /**
   * Questa funzione viene utilizzata dal valorizzare i dati all'interno del
   * PDF EsempioFattura
   * @param idFattura: numero della fattura di cui si vogliono visualizzare i
   * dati
   * @param anno: anno in cui è stata emessa la fattura
   * @throws Exception
   * @throws SQLException
   */
  public Vector select(String idFattura, String anno)
  throws Exception, SQLException
  {
    Vector ris=new Vector();
    if (!isConnection())
      throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn=null;
    StringBuffer query=new StringBuffer("");
    //String proprietario;
    try
    {
      conn=getConnection();
      Statement stmt = conn.createStatement();
      query = new StringBuffer("SELECT C.ANNO, C.NUMERO_FATTURA,");
      query.append("C.ID_RICHIESTA,");
      query.append("C.IMPORTO_IMPONIBILE, C.IMPORTO_IVA,");
      query.append("COALESCE(DF.IMPORTO_SPEDIZIONE,0) AS IMPORTO_SPEDIZIONE ");
      query.append("FROM CAMPIONE_FATTURATO C, DATI_FATTURA DF ");
      query.append("WHERE C.NUMERO_FATTURA = ");
      query.append(idFattura);
      query.append(" AND C.ANNO = ").append(anno);
      query.append(" AND C.ID_RICHIESTA=DF.ID_RICHIESTA ");
      query.append("ORDER BY C.ID_RICHIESTA ");
      //CuneoLogger.debug(this, query.toString());
      ResultSet rset = stmt.executeQuery(query.toString());
      while (rset.next())
      {
        ris.add(
            new CampioneFatturato(
                  rset.getString("ID_RICHIESTA"),
                  rset.getString("ANNO"),
                  rset.getString("NUMERO_FATTURA"),
                  rset.getString("IMPORTO_IMPONIBILE"),
                  rset.getString("IMPORTO_IVA"),
                  rset.getString("IMPORTO_SPEDIZIONE")
                  )
        );
      }
      rset.close();
      stmt.close();
      return ris;
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("select della classe CampioneFatturato");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("select della classe CampioneFatturato"
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

  public String getIdRichiesta()
  {
    return this.idRichiesta;
  }
  public void setIdRichiesta( String newIdRichiesta )
  {
    this.idRichiesta = newIdRichiesta;
  }


  public String getAnno()
  {
    return this.anno;
  }
  public void setAnno( String newAnno )
  {
    this.anno = newAnno;
  }

  public String getNumeroFattura()
  {
    return this.numeroFattura;
  }
  public void setNumeroFattura( String newNumeroFattura )
  {
    this.numeroFattura = newNumeroFattura;
  }

  public String getImportoImponibile()
  {
    return this.importoImponibile;
  }
  public void setImportoImponibile(String importoImponibile)
  {
    this.importoImponibile = importoImponibile;
  }

  public String getImportoIva()
  {
    return this.importoIva;
  }
  public void setImportoIva(String importoIva)
  {
    this.importoIva = importoIva;
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