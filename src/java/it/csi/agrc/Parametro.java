package it.csi.agrc;

import it.csi.cuneo.*;
import java.util.*;
import java.sql.*;


/**
 * <p>Title: Agrichim - Front Office</p>
 * <p>Description: Richiesta analisi chimiche su campioni biologici agrari</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: CSI Piemonte - Progettazione e Sviluppo - Cuneo</p>
 * @author Michele Piantà, Piergiorgio Chiriotti
 * @version 1.0.0
 */

public class Parametro extends BeanDataSource
{
  private final String BOBA="BOBA";
  private final String VBCC="VBCC";
  private final String TXAS="TXAS";
  private final String TXDI="TXDI";
  private final String TXLA="TXLA";
  private final String TXSF="TXSF";
  private final String TXLC="TXLC";
  private final String TXPI="TXPI";
  private final String GFEP="GFEP";
  private final String SIUS = "SIUS";
  private final String SIPW = "SIPW";
  private final String DICITURA_INTRODUTTIVA_GIS = "DIGS";
  private final String METALLI_PESANTI_SCONTO_NUMERO = "MTNM";
  private final String METALLI_PESANTI_SCONTO_PERCENTUALE = "MTPR";

  public void leggiParametri(BeanParametri parametri)
      throws Exception, SQLException
  {
    if (!isConnection())
           throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn=this.getConnection();
    StringBuffer query=new StringBuffer("");
    String idParametro=null;
    String valore=null;
    Statement stmt = null;
    try
    {
        stmt = conn.createStatement();
        query.append("SELECT ID_PARAMETRO, VALORE ");
        query.append("FROM PARAMETRO ");

        ResultSet rset = stmt.executeQuery(query.toString());

        HashMap hash=new HashMap();

        while (rset.next())
        {
          idParametro=rset.getString("ID_PARAMETRO");	
          //System.out.println("Leggo valore di '"+idParametro+"'");
          if (!idParametro.equals(SIPW))
          {	
            valore=rset.getString("VALORE");
            if (rset.wasNull())
              valore="";
        	  hash.put(idParametro,valore);
          }
        }
          
        parametri.setBOBA((String)hash.get(BOBA));
        parametri.setVBCC((String)hash.get(VBCC));
        parametri.setAssessorato((String)hash.get(TXAS));
        parametri.setDirezione((String)hash.get(TXDI));
        parametri.setLabAgr((String)hash.get(TXLA));
        parametri.setSettore((String)hash.get(TXSF));
        parametri.setLabConsegna2((String)hash.get(TXLC));
        parametri.setPartitaIVALab((String)hash.get(TXPI));
        parametri.setGFEP((String)hash.get(GFEP));
        parametri.setMetalliPesantiScontoNumero((String) hash.get(METALLI_PESANTI_SCONTO_NUMERO));
        parametri.setMetalliPesantiScontoPercentuale((String) hash.get(METALLI_PESANTI_SCONTO_PERCENTUALE));
        parametri.setSIUS((String)hash.get(SIUS));
        parametri.setDicituraIntroduttivaGIS((String) hash.get(DICITURA_INTRODUTTIVA_GIS));

        rset.close();
        //Leggo la password per accedere a sigmater
        query=new StringBuffer("select pgp_sym_decrypt(valore_b,  '##agrc_sigmater++')");
        query.append("from parametro ");
        query.append("where id_parametro = 'SIPW' ");
        
        rset = stmt.executeQuery(query.toString());
        if (rset.next())
        	parametri.setSIPW(rset.getString(1));
        
        rset.close();
        stmt.close();
        
    }
    catch(java.sql.SQLException ex)
    {
      ex.printStackTrace();
      this.getAut().setQuery("leggiParametri della classe Parametro");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    }
    catch(Exception e)
    {
      e.printStackTrace();
      this.getAut().setQuery("leggiParametri della classe Parametro"
                            +": non è una SQLException ma una Exception"
                            +" generica");
      throw (e);
    }
    finally
    {
      if (conn!=null) conn.close();
    }
  }
}