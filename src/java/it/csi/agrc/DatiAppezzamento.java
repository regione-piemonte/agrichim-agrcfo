package it.csi.agrc;

import java.io.StringReader;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.StringTokenizer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import it.csi.cuneo.BeanDataSource;
import it.csi.cuneo.Constants;
import it.csi.cuneo.CuneoLogger;
import it.csi.cuneo.Utili;
import it.csi.sigmater.ws.client.sigwgssrvSigwgssrv.InfoParticella;

/**
 * <p>Title: Agrichim - Front Office</p>
 * <p>Description: Richiesta analisi chimiche su campioni biologici agrari</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: CSI Piemonte - Progettazione e Sviluppo - Cuneo</p>
 * @author Michele Piantà, Piergiorgio Chiriotti
 * @version 1.0.0
 */

public class DatiAppezzamento extends BeanDataSource
{
  private static final String CLASS_NAME = "DatiAppezzamento";

  public DatiAppezzamento ()
  {
  }
  public DatiAppezzamento(Object dataSource, Autenticazione aut)
  {
    this.setDataSource(dataSource);
    this.setAut(aut);
  }
  /*
  public DatiAppezzamento ( long idRichiesta, String localitaAppezzamento, String comuneAppezzamento, String particellaCatastale, String subparticella, String sezione, String foglio, String coordinataNordBoaga, String coordinataEstBoaga, String coordinataNordUtm, String coordinataEstUtm, String tipoGeoreferenziazione )
  {
    this.idRichiesta=idRichiesta;
    this.localitaAppezzamento=localitaAppezzamento;
    this.comuneAppezzamento=comuneAppezzamento;
    this.particellaCatastale=particellaCatastale;
    this.subparticella=subparticella;
    this.sezione=sezione;
    this.foglio=foglio;
    this.coordinataNordBoaga=coordinataNordBoaga;
    this.coordinataEstBoaga=coordinataEstBoaga;
    this.coordinataNordUtm=coordinataNordUtm;
    this.coordinataEstUtm=coordinataEstUtm;
    this.tipoGeoreferenziazione=tipoGeoreferenziazione;
  }
  */

  private String localitaAppezzamento;
  private String comuneAppezzamento;
  private String particellaCatastale;
  private String subparticella;
  private String sezione;
  private String foglio;
  private String coordinataNordBoaga;
  private String coordinataEstBoaga;
  private String coordinataNordUtm;
  private String coordinataEstUtm;
  private String tipoGeoreferenziazione;
  private long idRichiesta;
  private String piemonte;
  private String comuneAppezzamentoDescr;
  private String profondita;
  private String coordGradi;
  private String gradiNord;
  private String minutiNord;
  private String secondiNord;
  private String decimaliNord;
  private String gradiEst;
  private String minutiEst;
  private String secondiEst;
  private String decimaliEst;
  
  private String gradiNordTot;
  private String gradiEstTot;
  private String minutiNordTot;
  private String minutiEstTot;
  private String secondiNordTot;
  private String secondiEstTot;
  
  
  public static final String GRADI_DECIMALI="DD";
  public static final String GRADI_MINUTI_DECIMALI="DM";
  public static final String GRADI_MINUTI_SECONDI="DMS";

  public void select(String idRichiesta)
  throws Exception, SQLException
  {
    select(Long.parseLong(idRichiesta));
  }

  public void selectForPdf(String idRichiesta,boolean terreno)
  throws Exception, SQLException
  {
    //Anagrafiche anagrafiche=new Anagrafiche();
    if (!isConnection())
      throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn=null;
    StringBuffer query=new StringBuffer("");
    //String anagraficaProprietario;
    //String proprietario;
    try
    {
      conn=getConnection();
      Statement stmt = conn.createStatement();

      query = new StringBuffer("SELECT C.DESCRIZIONE AS COMUNE_APPEZZAMENTO_DESCR ");
      if (terreno) query.append(",P.DESCRIZIONE AS PROFONDITA ");
      query.append("FROM COMUNE C, DATI_APPEZZAMENTO D ");
      if (terreno) query.append(",DATI_CAMPIONE_TERRENO T,PROFONDITA_PRELIEVO P ");
      query.append("WHERE D.ID_RICHIESTA = ");
      query.append(idRichiesta);
      query.append(" AND D.COMUNE_APPEZZAMENTO = C.CODICE_ISTAT");
      if (terreno)
      {
        query.append(" AND T.ID_RICHIESTA = D.ID_RICHIESTA");
        query.append(" AND P.ID_PROFONDITA=T.ID_PROFONDITA");
      }
      //CuneoLogger.debug(this,query.toString());
      ResultSet rset = stmt.executeQuery(query.toString());
      if (rset.next())
      {
        this.setComuneAppezzamentoDescr(rset.getString("COMUNE_APPEZZAMENTO_DESCR"));
        if (terreno) this.setProfondita(rset.getString("PROFONDITA"));
      }
      rset.close();
      stmt.close();
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("selectForPdf della classe DatiAppezzamento");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("selectForPdf della classe DatiAppezzamento"
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


  public void select(long idRichiesta)
  throws Exception, SQLException
  {
    //Anagrafiche anagrafiche=new Anagrafiche();
    if (!isConnection())
      throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn=null;
    StringBuffer query=new StringBuffer("");
    //String anagraficaProprietario;
    //String proprietario;
    try
    {
      conn=getConnection();
      Statement stmt = conn.createStatement();
      query = new StringBuffer("SELECT ID_RICHIESTA, ");
      query.append("LOCALITA_APPEZZAMENTO, COMUNE_APPEZZAMENTO, ");
      query.append("COMUNE.DESCRIZIONE AS COMUNE_APPEZZAMENTO_DESCR, ");
      query.append("PARTICELLA_CATASTALE, SUBPARTICELLA, SEZIONE, FOGLIO, ");
      query.append("COORDINATA_NORD_BOAGA, COORDINATA_EST_BOAGA, ");
      query.append("COORDINATA_NORD_UTM, COORDINATA_EST_UTM, ");
      query.append("TIPO_GEOREFERENZIAZIONE, ID_REGIONE, ");
      query.append("codice_coordinate_gradi, coordinata_nord_gradi, coordinata_nord_minuti, ");
      query.append("coordinata_nord_secondi,coordinata_est_gradi, coordinata_est_minuti, coordinata_est_secondi ");
      query.append("FROM PROVINCIA, COMUNE, DATI_APPEZZAMENTO ");
      query.append("WHERE ID_RICHIESTA = ");
      query.append(idRichiesta);
      query.append(" AND DATI_APPEZZAMENTO.COMUNE_APPEZZAMENTO=COMUNE.CODICE_ISTAT");
      query.append(" AND COMUNE.PROVINCIA=PROVINCIA.ID_PROVINCIA");
      //CuneoLogger.debug(this,query.toString());
      ResultSet rset = stmt.executeQuery(query.toString());
      if (rset.next())
      {
        this.setIdRichiesta(rset.getLong("ID_RICHIESTA"));
        this.setLocalitaAppezzamento(rset.getString("LOCALITA_APPEZZAMENTO"));
        this.setComuneAppezzamento(rset.getString("COMUNE_APPEZZAMENTO"));
        this.setParticellaCatastale(rset.getString("PARTICELLA_CATASTALE"));
        this.setSubparticella(rset.getString("SUBPARTICELLA"));
        this.setSezione(rset.getString("SEZIONE"));
        this.setFoglio(rset.getString("FOGLIO"));
        this.setCoordinataNordBoaga(rset.getString("COORDINATA_NORD_BOAGA"));
        this.setCoordinataEstBoaga(rset.getString("COORDINATA_EST_BOAGA"));
        this.setCoordinataNordUtm(rset.getString("COORDINATA_NORD_UTM"));
        this.setCoordinataEstUtm(rset.getString("COORDINATA_EST_UTM"));
        this.setTipoGeoreferenziazione(rset.getString("TIPO_GEOREFERENZIAZIONE"));
        this.setPiemonte(rset.getString("ID_REGIONE"));
        this.setComuneAppezzamentoDescr(rset.getString("COMUNE_APPEZZAMENTO_DESCR"));
        
        coordGradi=rset.getString("codice_coordinate_gradi");
        
        gradiNord =rset.getString("coordinata_nord_gradi");
        gradiNordTot=rset.getString("coordinata_nord_gradi");
    	
        
        minutiNord =rset.getString("coordinata_nord_minuti");
        minutiNordTot =rset.getString("coordinata_nord_minuti");
        
        
        secondiNord =rset.getString("coordinata_nord_secondi");
        secondiNordTot = rset.getString("coordinata_nord_secondi");
       
        gradiEst =rset.getString("coordinata_est_gradi");
        gradiEstTot =rset.getString("coordinata_est_gradi");
       
        
        minutiEst =rset.getString("coordinata_est_minuti");
        minutiEstTot= rset.getString("coordinata_est_minuti");
        
        
        secondiEst =rset.getString("coordinata_est_secondi");
        secondiEstTot =rset.getString("coordinata_est_secondi"); 
        
        
        String[] temp=null;
        
        if (GRADI_DECIMALI.equals(coordGradi))
        {
        	//devo separare i gradi dai decimali
        	if (gradiNord!=null)
        	{
        		//int l =gradiNordTot.indexOf(".");
        		//gradiNordTot = gradiNordTot.substring(0, l)+gradiNordTot.substring(l, l+3);
        		temp=gradiNord.split("\\.");
        		if (temp!=null)
        		{
        			if (temp.length>0)
        				gradiNord=temp[0];
        			if (temp.length>1)
        				decimaliNord=temp[1];
        		}
        	}
        	if (gradiEst!=null)
        	{
        		//int l =gradiEstTot.indexOf(".");
               // gradiEstTot = gradiEstTot.substring(0, l)+gradiEstTot.substring(l, l+3);
        		temp=gradiEst.split("\\.");
        		if (temp!=null)
        		{
        			if (temp.length>0)
        				gradiEst=temp[0];
        			if (temp.length>1)
        				decimaliEst=temp[1];
        		}
        	}
        }
        
        if (GRADI_MINUTI_DECIMALI.equals(coordGradi))
        {
        	gradiNord=gradiNord.split("\\.")[0];
        	gradiEst=gradiEst.split("\\.")[0];
        	
        	//devo separare i minuti dai decimali
        	if (minutiNord!=null)
        	{
        		//int l =minutiNordTot.indexOf(".");
              //  minutiNordTot = minutiNordTot.substring(0, l)+minutiNordTot.substring(l, l+3);
        		temp=minutiNord.split("\\.");
        		if (temp!=null)
        		{
        			if (temp.length>0)
        				minutiNord=temp[0];
        			if (temp.length>1)
        				decimaliNord=temp[1];
        		}
        	}
        	if (minutiEst!=null)
        	{
        		//int l =minutiEstTot.indexOf(".");
               // minutiEstTot = minutiEstTot.substring(0, l)+minutiEstTot.substring(l, l+3);
        		temp=minutiEst.split("\\.");
        		if (temp!=null)
        		{
        			if (temp.length>0)
        				minutiEst=temp[0];
        			if (temp.length>1)
        				decimaliEst=temp[1];
        		}
        	}
        }
        
        if (GRADI_MINUTI_SECONDI.equals(coordGradi))
        {
        	gradiNord=gradiNord.split("\\.")[0];
        	gradiEst=gradiEst.split("\\.")[0];
        	minutiNord=minutiNord.split("\\.")[0];
        	minutiEst=minutiEst.split("\\.")[0];
        	
        	//devo separare i minuti dai decimali
        	if (secondiNord!=null)
        	{
        		// int l =secondiNordTot.indexOf(".");
        	   //  secondiNordTot = secondiNordTot.substring(0, l)+secondiNordTot.substring(l, l+3);
        	        
        		temp=secondiNord.split("\\.");
        		if (temp!=null)
        		{
        			if (temp.length>0)
        				secondiNord=temp[0];
        			if (temp.length>1)
        				decimaliNord=temp[1];
        		}
        	}
        	if (secondiEst!=null)
        	{
        		//int l =secondiEstTot.indexOf(".");
               // secondiEstTot = secondiEstTot.substring(0, l)+secondiEstTot.substring(l, l+3);
                
        		temp=secondiEst.split("\\.");
        		if (temp!=null)
        		{
        			if (temp.length>0)
        				secondiEst=temp[0];
        			if (temp.length>1)
        				decimaliEst=temp[1];
        		}
        	}
        }
        
      }
      else
        this.setIdRichiesta(-1);
      stmt.close();
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("select della classe DatiAppezzamento");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("select della classe DatiAppezzamento"
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
  

  public int update()
      throws Exception, SQLException
  {
	  String LOG_HEADER = CLASS_NAME + "::update() - ";
	  
	  CuneoLogger.debug(this, LOG_HEADER + "INIZIO");
	  
    if (!isConnection())
           throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn = null;
    StringBuffer query = null;
    PreparedStatement stmt = null;
    
    int update = 0;

    try
    {
  	  query = new StringBuffer(" UPDATE DATI_APPEZZAMENTO SET ");
      query.append(" SEZIONE = ? ");
      query.append(", LOCALITA_APPEZZAMENTO = ? ");
      query.append(", FOGLIO = ? ");
      query.append(", PARTICELLA_CATASTALE = ? ");
      query.append(", SUBPARTICELLA = ? ");
      query.append(", COORDINATA_NORD_BOAGA = ? ");
      query.append(", COORDINATA_EST_BOAGA = ? ");
      query.append(", COORDINATA_NORD_UTM = ? ");
      query.append(", COORDINATA_EST_UTM = ? ");
      query.append(", TIPO_GEOREFERENZIAZIONE = ? ");
      query.append(", codice_coordinate_gradi = ? ");
      query.append(", coordinata_nord_gradi = ? ");
      query.append(", coordinata_nord_minuti = ? ");
      query.append(", coordinata_nord_secondi = ? ");
      query.append(", coordinata_est_gradi = ? ");
      query.append(", coordinata_est_minuti = ? ");
      query.append(", coordinata_est_secondi = ? ");
      query.append(" WHERE ID_RICHIESTA = ? ");

      conn = getConnection();
      stmt = conn.prepareStatement(query.toString());

      stmt.setString(1, getSezione());
      stmt.setString(2, getLocalitaAppezzamento());
      stmt.setBigDecimal(3, Utili.parseBigDecimal(getFoglio())); // il campo su db è NUMERIC
      stmt.setBigDecimal(4, Utili.parseBigDecimal(getParticellaCatastale())); // il campo su db è NUMERIC
      stmt.setString(5, getSubparticella());
      stmt.setBigDecimal(6, Utili.parseBigDecimal(getCoordinataNordBoaga())); // il campo su db è NUMERIC
      stmt.setBigDecimal(7, Utili.parseBigDecimal(getCoordinataEstBoaga())); // il campo su db è NUMERIC
      stmt.setBigDecimal(8, Utili.parseBigDecimal(getCoordinataNordUtm())); // il campo su db è NUMERIC
      stmt.setBigDecimal(9, Utili.parseBigDecimal(getCoordinataEstUtm())); // il campo su db è NUMERIC
      
      stmt.setString(10, getTipoGeoreferenziazione());
            
      if (Utili.isEmpty(gradiNord) && Utili.isEmpty(gradiEst))
      {
    	  stmt.setString(11, null); //codice_coordinate_gradi
    	  stmt.setBigDecimal(12, null); //coordinata_nord_gradi
    	  stmt.setBigDecimal(13, null); //coordinata_nord_minuti
    	  stmt.setBigDecimal(14, null); //coordinata_nord_secondi
    	  stmt.setBigDecimal(15, null); //coordinata_est_gradi
    	  stmt.setBigDecimal(16, null); //coordinata_est_minuti
    	  stmt.setBigDecimal(17, null); //coordinata_est_secondi
      }
      else
      {
    	  //System.out.println(this, "coordGradi:"+coordGradi);
    	  stmt.setString(11, coordGradi);
    	  
    	  if (Utili.isEmpty(gradiNord))
          {
        	  stmt.setBigDecimal(12, null); //coordinata_nord_gradi
        	  stmt.setBigDecimal(13, null); //coordinata_nord_minuti
        	  stmt.setBigDecimal(14, null); //coordinata_nord_secondi
          }
    	  
    	  if (Utili.isEmpty(gradiEst))
          {
        	  stmt.setBigDecimal(15, null); //coordinata_est_gradi
        	  stmt.setBigDecimal(16, null); //coordinata_est_minuti
        	  stmt.setBigDecimal(17, null); //coordinata_est_secondi
          }
    	  
    	  if (GRADI_DECIMALI.equals(coordGradi))
    	  {
    		  if (!Utili.isEmpty(gradiNord))
    		  {
				  stmt.setBigDecimal(12, Utili.parseBigDecimal(gradiNord+"."+decimaliNord)); //coordinata_nord_gradi
		    	  stmt.setBigDecimal(13, null); //coordinata_nord_minuti
		    	  stmt.setBigDecimal(14, null); //coordinata_nord_secondi
    		  }
    		  if (!Utili.isEmpty(gradiEst))
    		  {
	        	  stmt.setBigDecimal(15, Utili.parseBigDecimal(gradiEst+"."+decimaliEst)); //coordinata_est_gradi
	        	  stmt.setBigDecimal(16, null); //coordinata_est_minuti
	        	  stmt.setBigDecimal(17, null); //coordinata_est_secondi
    		  }
    	  }
    	  if (GRADI_MINUTI_DECIMALI.equals(coordGradi))
    	  {
    		  if (!Utili.isEmpty(gradiNord))
    		  {
	    		  stmt.setBigDecimal(12, Utili.parseBigDecimal(gradiNord)); //coordinata_nord_gradi
	        	  stmt.setBigDecimal(13, Utili.parseBigDecimal(minutiNord+"."+decimaliNord)); //coordinata_nord_minuti
	        	  stmt.setBigDecimal(14, null); //coordinata_nord_secondi
    		  }
    		  if (!Utili.isEmpty(gradiEst))
    		  {
	        	  stmt.setBigDecimal(15, Utili.parseBigDecimal(gradiEst)); //coordinata_est_gradi
	        	  stmt.setBigDecimal(16, Utili.parseBigDecimal(minutiEst+"."+decimaliEst)); //coordinata_est_minuti
	        	  stmt.setBigDecimal(17, null); //coordinata_est_secondi
    		  }
    	  }
    	  if (GRADI_MINUTI_SECONDI.equals(coordGradi))
    	  {
    		  if (!Utili.isEmpty(gradiNord))
    		  {
	    		  stmt.setBigDecimal(12, Utili.parseBigDecimal(gradiNord)); //coordinata_nord_gradi
	        	  stmt.setBigDecimal(13, Utili.parseBigDecimal(minutiNord)); //coordinata_nord_minuti
	        	  stmt.setBigDecimal(14, Utili.parseBigDecimal(secondiNord+"."+decimaliNord)); //coordinata_nord_secondi
    		  }
    		  if (!Utili.isEmpty(gradiEst))
    		  {
	        	  stmt.setBigDecimal(15, Utili.parseBigDecimal(gradiEst)); //coordinata_est_gradi
	        	  stmt.setBigDecimal(16, Utili.parseBigDecimal(minutiEst)); //coordinata_est_minuti
	        	  stmt.setBigDecimal(17, Utili.parseBigDecimal(secondiEst+"."+decimaliEst)); //coordinata_est_secondi
    		  }
    	  }
      }
    	  
      
      stmt.setLong(18, new Long(getAut().getIdRichiestaCorrente()));

      update = stmt.executeUpdate();
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("update della classe DatiAppezzamento");
      this.getAut().setContenutoQuery(query.toString());
      ex.printStackTrace();
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("update della classe DatiAppezzamento"
                            +": non è una SQLException ma una Exception"
                            +" generica");
      e.printStackTrace();
      throw (e);
    }
    finally
    {
      if (conn!=null) conn.close();
    }
    
    CuneoLogger.debug(this, LOG_HEADER + "FINE");
    return update;
  }

  public int updateGIS()
      throws Exception, SQLException
  {
    if (!isConnection())
           throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn = null;
    StringBuffer query = null;
    Statement stmt = null;

    try
    {
      conn = getConnection();
      stmt = conn.createStatement();
      query = new StringBuffer("UPDATE DATI_APPEZZAMENTO ");
      query.append("SET SEZIONE='").append(this.getSezione().replace("'", "''").replace("+", " "));
      query.append("', FOGLIO=").append(this.getFoglio());
      //query.append(", PARTICELLA_CATASTALE=").append(this.getParticellaCatastale());
      query.append(", COORDINATA_NORD_UTM=").append(this.getCoordinataNordUtm());
      query.append(", COORDINATA_EST_UTM=").append(this.getCoordinataEstUtm());
      query.append(", COORDINATA_NORD_BOAGA=null, COORDINATA_EST_BOAGA=null");
      query.append(", coordinata_nord_gradi = null ");
      query.append(", coordinata_nord_minuti = null ");
      query.append(", coordinata_nord_secondi = null ");
      query.append(", coordinata_est_gradi = null ");
      query.append(", coordinata_est_minuti = null ");
      query.append(", coordinata_est_secondi = null ");
      query.append(", TIPO_GEOREFERENZIAZIONE='G' ");
      query.append("WHERE ID_RICHIESTA =").append(this.getIdRichiesta());
      //CuneoLogger.debug(this,query.toString());
      int updated = 0;
      updated = stmt.executeUpdate(query.toString());
      stmt.close();
      return updated;
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("update della classe DatiAppezzamento");
      this.getAut().setContenutoQuery(query.toString());
      ex.printStackTrace();
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("update della classe DatiAppezzamento"
                            +": non è una SQLException ma una Exception"
                            +" generica");
      e.printStackTrace();
      throw (e);
    }
    finally
    {
      if (conn!=null) conn.close();
    }
  }


  public boolean isRegionePiemonte()
  {
	  if (Constants.REGIONE.PIEMONTE.equals(getPiemonte())) return true;
	  return false;
  }
  
  public String ControllaDati()
  {
    StringBuffer errore=new StringBuffer("");
    //String tmpStr;

    /**
     * Controlli formali campo per campo
     * Se sono tutti vuoti non viene sollevata eccezione
     * Serve effettuare controlli incrociati (vedi più avanti)
     */
    if (getLocalitaAppezzamento() != null)
      setLocalitaAppezzamento(getLocalitaAppezzamento().trim());
    if (getLocalitaAppezzamento() != null && getLocalitaAppezzamento().length()>40)
    {
      errore.append(";1");
    }

    if (this.getSezione() != null)
    {
      /*this.setSezione(this.getSezione().toUpperCase());
      tmpStr = this.getSezione();
      if (tmpStr.length()==1 && tmpStr.charAt(0)!=' ' &&
        (tmpStr.charAt(0)<'A' || tmpStr.charAt(0)>'Z') )
      {
        errore.append(";2");
      }
      else if (tmpStr.length()>1 && !Utili.isNumber(tmpStr,0,99))
      {
        errore.append(";2");
      }*/
      if (this.getSezione().length()>30 )
      {
        errore.append(";2");
      }
    }

    boolean foglioParticella=true;

    if (this.getFoglio() == null)
      foglioParticella=false;
    else
    {
      if (!Utili.isNumber(this.getFoglio(),0,9999))
      {
        errore.append(";3");
        foglioParticella=false;
      }
    }
    if (this.getParticellaCatastale() == null)
      foglioParticella=false;
    else
    {
      if (!Utili.isNumber(this.getParticellaCatastale(),0,99999))
      {
        errore.append(";4");
        foglioParticella=false;
      }
    }

    if (this.getSubparticella() != null && this.getSubparticella().length()>20)
    {
      errore.append(";5");
    }

    if (foglioParticella
        && getCoordinataNordBoaga() == null
        && getCoordinataEstBoaga() == null
        && getCoordinataEstUtm() == null
        && getCoordinataNordUtm() == null)
    {
      this.setTipoGeoreferenziazione("M");
    }
    else
    {
      if (this.getCoordinataNordBoaga() !=null)
      {
        if (!Utili.isNumber(this.getCoordinataNordBoaga(),1000000,9999999))
        {
          errore.append(";6");
        }
        else
        {
          this.setCoordinataNordUtm(String.valueOf(Integer.parseInt(this.getCoordinataNordBoaga())-19));
          this.setTipoGeoreferenziazione("M");
        }
      }
      else if (this.getCoordinataEstBoaga() !=null)
      {
        errore.append(";6");
      }


      if (this.getCoordinataEstBoaga() !=null)
      {
        if (!Utili.isNumber(this.getCoordinataEstBoaga(),1000000,9999999))
        {
          errore.append(";7");
        }
        else
        {
          this.setCoordinataEstUtm(String.valueOf(Integer.parseInt(this.getCoordinataEstBoaga())-1000026));
          this.setTipoGeoreferenziazione("M");
        }
      }
      else if (this.getCoordinataNordBoaga() !=null)
      {
        errore.append(";7");
      }

      if ((this.getCoordinataNordUtm() !=null &&
          !Utili.isNumber(this.getCoordinataNordUtm(),1000000,9999999)) ||
          this.getCoordinataNordUtm()==null && this.getCoordinataEstUtm() !=null)
      {
        errore.append(";8");
      }
      if ((this.getCoordinataEstUtm() !=null &&
         !Utili.isNumber(this.getCoordinataEstUtm(),100000,999999)) ||
          this.getCoordinataEstUtm()==null && this.getCoordinataNordUtm() !=null)
      {
        errore.append(";9");
      }
      
      //AGRICHIM-46
      //Questo controllo non va fatto nel caso la regione sia diversa da Piemonte      
      if (Constants.REGIONE.PIEMONTE.equals(getPiemonte()) && this.getCoordinataNordUtm() == null && this.getCoordinataEstUtm() == null)
      {
        errore.append(";8;9");
      }
    }

    /**
     * Controlli incrociati ancora da implementare...
     */

    /**
    * Se non sono stati trovati errori restituisce null
    */
    if (errore.toString().equals("")) return null;
    else return errore.toString();
  }
  
  /*
   * Richiamando un servizio di sigmater ricaviamo le coordinate utm a partire dai dati catastali
   */
  public boolean ricavaUTMFromDatiCatastali(String istatComune, BeanParametri beanParametriApplication, String cuaa)
  {
	  /*
		   Il sistema accede al servizio di Sigmater SIGWGSSRV.cercaParticelleByFilter passando in input:
			  -	Codice istat comune: il codice istat del comune selezionato nella fase 1
			  -	Codice Belfiore comune : non valorizzato
			  -	Sezione : numero di Sezione indicato dall'utente
			  -	Sviluppo : non valorizzato
			  -	Numero : numero della particella catastale principale
			  -	Foglio : numero di Foglio indicato dall'utente
			  s
			  Il servizio restituisce un oggetto InfoParticella che contiene le informazioni relative alla geometria della particella, 
			  che vengono utilizzate per calcolare il centroide della particella in coordinate UTM, che vengono salvate 
			  sulla tabella DATI_APPEZZAMENTO nei campi COORDINATA_NORD_UTM e COORDINATA_EST_UTM.
		*/
		/*
			<param name="codIstatComune" type="java.lang.String" />
			<param name="codBelfioreComune" type="java.lang.String" />
			<param name="sezione" type="java.lang.String" />
			<param name="sviluppo" type="java.lang.String" />
			<param name="numero" type="java.lang.String" />
			<param name="foglio" type="java.lang.String" />
		*/
		List<InfoParticella> infoParticella= null;		
		try
		{
			it.csi.cuneo.CuneoLogger.debug(this,"cercaParticelleByFilter codIstatComune:"+istatComune);
			it.csi.cuneo.CuneoLogger.debug(this,"cercaParticelleByFilter sezione:"+sezione);
			it.csi.cuneo.CuneoLogger.debug(this,"cercaParticelleByFilter numero:"+particellaCatastale);
			it.csi.cuneo.CuneoLogger.debug(this,"cercaParticelleByFilter foglio:"+foglio);
			String sezioneInput=null;
			
			if (!"".equals(sezione))
				sezioneInput=sezione;
			
			SigmaterWs sigmaterWs = new SigmaterWs();
					
			infoParticella= sigmaterWs.bindingSigmaterSigwgssrvWs(beanParametriApplication).cercaParticelleByFilter(istatComune, null, sezioneInput, null, particellaCatastale, foglio, cuaa);  //.getSigwgssrvSrv().cercaParticelleByFilter(istatComune, null, sezioneInput, null, particellaCatastale, foglio);
			
			//infoParticella= beanParametriApplication.getSigwgssrvSrv().cercaParticelleByFilter(istatComune, null, sezioneInput, null, particellaCatastale, foglio);
		}
		catch(Exception e)
		{
			CuneoLogger.error(this,"Errore nella chiamata a cercaParticelleByFilter: "+e.getMessage());
		}
		
		if (infoParticella!=null && infoParticella.size()>0 && infoParticella.get(0)!=null)
		{
		  
		  //lettura delle coordinate
		  try
		  {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(infoParticella.get(0).getGeometriaGML()));
        Document doc = builder.parse(is);
        NodeList nl = doc.getElementsByTagName("gml:coordinates");
        if(nl.getLength() > 0)
        {
          String value = nl.item(0).getChildNodes().item(0).getNodeValue();
          if(value != null && value !="")
          {
            StringTokenizer strz = new StringTokenizer(value);
            double x = 0;
            double y = 0;
            int numElementi = 0;
            while (strz.hasMoreElements()) 
            {
              StringTokenizer strz2 = new StringTokenizer((String)strz.nextElement(),",");
              x += new Double((String)strz2.nextElement()).doubleValue();
              y += new Double((String)strz2.nextElement()).doubleValue();
              
              numElementi++;
            }
            
            x = x / numElementi;
            y = y / numElementi;
            
            coordinataNordUtm=Math.round(y)+""; 
            coordinataEstUtm=Math.round(x)+""; 
            tipoGeoreferenziazione="C";
          }
        }

        return true;
      }
		  catch(Exception ex)
		  {
		    CuneoLogger.error(this,"Errore nella lettura dell'xml gml: "+ex.getMessage());
		  }
		  
			/*Polygon polig=
					CastCSIObjectToJSFObject.castCSIPolygonToPolygon(infoParticella.get(0).getGeometriaGML());
			Point pw = polig.getCentroid();
			if (pw!=null)
			{
				//valorizzo le coordinate UTM
				coordinataNordUtm=Math.round(pw.getY())+""; 
				coordinataEstUtm=Math.round(pw.getX())+""; 
				tipoGeoreferenziazione="C";//calcolata
				return pw;
			}*/
						
		}
		return false;
  }
  
  public String verificaUTMInserite(String istatComune, BeanParametri beanParametriApplication, String cuaa)
  	 throws Exception
  {    
    
    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

    // root elements
    Document doc = docBuilder.newDocument();
    Element rootElement = doc.createElement("gml:Polygon");
    doc.appendChild(rootElement);
    
    Attr attr = doc.createAttribute("srsName");
    attr.setValue("EPSG:32632");
    rootElement.setAttributeNode(attr);
    
    attr = doc.createAttribute("xmlns:gml");
    attr.setValue("http://www.opengis.net/gml");
    rootElement.setAttributeNode(attr);

    // outerBoundaryIs elements
    Element outerBoundaryIs = doc.createElement("gml:outerBoundaryIs");
    rootElement.appendChild(outerBoundaryIs);
    
    // LinearRing elements
    Element linearRing = doc.createElement("gml:LinearRing");
    outerBoundaryIs.appendChild(linearRing);
    
    // coordinates elements
    Element coordinates = doc.createElement("gml:coordinates");
    linearRing.appendChild(coordinates);

    // set attribute to staff element
    attr = doc.createAttribute("decimal");
    attr.setValue(".");
    coordinates.setAttributeNode(attr);
    
    attr = doc.createAttribute("cs");
    attr.setValue(",");
    coordinates.setAttributeNode(attr);
    
    attr = doc.createAttribute("ts");
    attr.setValue(" ");
    coordinates.setAttributeNode(attr);
    
    String valuesCoordinate = "";
    
    valuesCoordinate += coordinataEstUtm+","+coordinataNordUtm;
    
    double x = Double.parseDouble(coordinataEstUtm) + 1;
    double y = Double.parseDouble(coordinataNordUtm);
    
    valuesCoordinate += " "+x+","+y;
    
    x = Double.parseDouble(coordinataEstUtm) + 1;
    y = Double.parseDouble(coordinataNordUtm) + 1;
    
    valuesCoordinate += " "+x+","+y;
    
    x = Double.parseDouble(coordinataEstUtm);
    y = Double.parseDouble(coordinataNordUtm) + 1;
    
    valuesCoordinate += " "+x+","+y;
    
    valuesCoordinate += " "+coordinataEstUtm+","+coordinataNordUtm;
    
    
    
    coordinates.appendChild(doc.createTextNode(valuesCoordinate));
    
	  /*Coordinate coordinate=new Coordinate();
	  
	  coordinate.x=Double.parseDouble(coordinataEstUtm);
	  coordinate.y=Double.parseDouble(coordinataNordUtm);
	  
	  PrecisionModel precisionModel=new PrecisionModel(PrecisionModel.FLOATING);
	  
	  Point pw=new Point(coordinate,precisionModel,0);

	  
	  EnvelopeWeb envelopew = new EnvelopeWeb();
	  
	  
	  envelopew.setNordest(new PuntoWeb(new Double(pw.getX()+Constants.METRI_BUFFER),new
	  Double(pw.getY()+Constants.METRI_BUFFER)));
	  envelopew.setSudovest(new PuntoWeb(new Double(pw.getX()-Constants.METRI_BUFFER),new
	  Double(pw.getY()-Constants.METRI_BUFFER)));
	   

	  CSICoordinate[] coords = new CSICoordinate[5];
	  coords[0] = new
	  CSICoordinate(envelopew.getSudovest().getCoordX().doubleValue(),envelopew.getNordest().getCoordY().doubleValue());
	  coords[1] = new
	  CSICoordinate(envelopew.getNordest().getCoordX().doubleValue(),envelopew.getNordest().getCoordY().doubleValue());
	  coords[2] = new
	  CSICoordinate(envelopew.getNordest().getCoordX().doubleValue(),envelopew.getSudovest().getCoordY().doubleValue());
	  coords[3] = new
	  CSICoordinate(envelopew.getSudovest().getCoordX().doubleValue(),envelopew.getSudovest().getCoordY().doubleValue());
	  coords[4] = coords[0];


	  CSIPolygon input = new CSIPolygon(new CSILinearRing[0],new
	  CSILinearRing(coords,Constants.SRID),Constants.SRID);*/
    
    TransformerFactory tf = TransformerFactory.newInstance();
    Transformer transformer = tf.newTransformer();
    transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
    StringWriter writer = new StringWriter();
    transformer.transform(new DOMSource(doc), new StreamResult(writer));
    String output = writer.getBuffer().toString().replaceAll("\n|\r", "");

	  //InfoParticella[] infoP = beanParametriApplication.getSigwgssrvSrv().cercaParticelleByEnvelope(input);
	  SigmaterWs sigmaterWs = new SigmaterWs();
	  List<InfoParticella> infoP = sigmaterWs.bindingSigmaterSigwgssrvWs(beanParametriApplication).cercaParticelleByEnvelope(output, cuaa);
	   //= beanParametriApplication.getSigwgssrvSrv().cercaParticelleByEnvelope(input);

	  if(infoP!=null && infoP.size()>0)  
	  {
		  CuneoLogger.debug(this," \n\n\n\n Il punto sta dentro alla seguente particella : ");
		  CuneoLogger.debug(this,"infoP[0].getCodIstatComune() " + infoP.get(0).getCodIstatComune());
		  CuneoLogger.debug(this,"infoP[0].getFoglio() " + infoP.get(0).getFoglio());
		  CuneoLogger.debug(this,"infoP[0].getSezione() " + infoP.get(0).getSezione());
		  CuneoLogger.debug(this,"infoP[0].getNumero()" + infoP.get(0).getNumero());
		  
		  if (infoP.get(0).getCodIstatComune()!=null && !istatComune.equals(infoP.get(0).getCodIstatComune().trim())) return "8;9;";
		  if (foglio!=null)
			  if (infoP.get(0).getFoglio()!=null && !foglio.equals(infoP.get(0).getFoglio().trim())) return "8;9;";
		  if (sezione!=null)
			  if (infoP.get(0).getSezione()!=null && !sezione.equals(infoP.get(0).getSezione().trim())) return "8;9;";
		  if (particellaCatastale!=null)
			  if (infoP.get(0).getNumero()!=null && !particellaCatastale.equals(infoP.get(0).getNumero().trim())) return "8;9;";
		  
		  if (infoP.get(0).getFoglio()!=null)
			  foglio=infoP.get(0).getFoglio().trim();
		  if (infoP.get(0).getSezione()!=null)
			  sezione=infoP.get(0).getSezione().trim();
		  if (infoP.get(0).getNumero()!=null)
			  particellaCatastale=infoP.get(0).getNumero().trim();
	  }
	  else
	  {
		  CuneoLogger.debug(this," Nessun risultato trovato ");
		  return "8;9;";
	  }
	  
	  return null;
  }

  public long getIdRichiesta()
  {
    return this.idRichiesta;
  }
  public void setIdRichiesta(long newIdRichiesta)
  {
    this.idRichiesta = newIdRichiesta;
  }

  public String getLocalitaAppezzamento()
  {
    return this.localitaAppezzamento;
  }
  public void setLocalitaAppezzamento( String newLocalitaAppezzamento )
  {
    this.localitaAppezzamento = newLocalitaAppezzamento;
  }

  public String getComuneAppezzamento()
  {
    return this.comuneAppezzamento;
  }
  public void setComuneAppezzamento( String newComuneAppezzamento )
  {
    this.comuneAppezzamento = newComuneAppezzamento;
  }

  public String getParticellaCatastale()
  {
    return this.particellaCatastale;
  }
  public void setParticellaCatastale( String newParticellaCatastale )
  {
    this.particellaCatastale = newParticellaCatastale;
  }

  public String getSubparticella()
  {
    return this.subparticella;
  }
  public void setSubparticella( String newSubparticella )
  {
    this.subparticella = newSubparticella;
  }

  public String getSezione()
  {
    return this.sezione;
  }
  public void setSezione( String newSezione )
  {
    this.sezione = newSezione;
  }

  public String getFoglio()
  {
    return this.foglio;
  }
  public void setFoglio( String newFoglio )
  {
    this.foglio = newFoglio;
  }

  public String getCoordinataNordBoaga()
  {
    return this.coordinataNordBoaga;
  }
  public void setCoordinataNordBoaga( String newCoordinataNordBoaga )
  {
    this.coordinataNordBoaga = newCoordinataNordBoaga;
  }

  public String getCoordinataEstBoaga()
  {
    return this.coordinataEstBoaga;
  }
  public void setCoordinataEstBoaga( String newCoordinataEstBoaga )
  {
    this.coordinataEstBoaga = newCoordinataEstBoaga;
  }

  public String getCoordinataNordUtm()
  {
    return this.coordinataNordUtm;
  }
  public void setCoordinataNordUtm( String newCoordinataNordUtm )
  {
    this.coordinataNordUtm = newCoordinataNordUtm;
  }

  public String getCoordinataEstUtm()
  {
    return this.coordinataEstUtm;
  }
  public void setCoordinataEstUtm( String newCoordinataEstUtm )
  {
    this.coordinataEstUtm = newCoordinataEstUtm;
  }

  public String getTipoGeoreferenziazione()
  {
    return this.tipoGeoreferenziazione;
  }
  public void setTipoGeoreferenziazione( String newTipoGeoreferenziazione )
  {
    this.tipoGeoreferenziazione = newTipoGeoreferenziazione;
  }
  public String getPiemonte()
  {
    return piemonte;
  }
  public void setPiemonte(String piemonte)
  {
    this.piemonte = piemonte;
  }
  public String getComuneAppezzamentoDescr()
  {
    return comuneAppezzamentoDescr;
  }
  public void setComuneAppezzamentoDescr(String comuneAppezzamentoDescr)
  {
    this.comuneAppezzamentoDescr = comuneAppezzamentoDescr;
  }
  public void setProfondita(String profondita)
  {
    this.profondita = profondita;
  }
  public String getProfondita()
  {
    return profondita;
  }
  
  
	
	public String getCoordGradi() {
		return coordGradi;
	}
	public void setCoordGradi(String coordGradi) {
		this.coordGradi = coordGradi;
	}
	public String getGradiNord() {
		return gradiNord;
	}
	public void setGradiNord(String gradiNord) {
		this.gradiNord = gradiNord;
	}
	public String getMinutiNord() {
		return minutiNord;
	}
	public void setMinutiNord(String minutiNord) {
		this.minutiNord = minutiNord;
	}
	public String getSecondiNord() {
		return secondiNord;
	}
	public void setSecondiNord(String secondiNord) {
		this.secondiNord = secondiNord;
	}
	public String getDecimaliNord() {
		return decimaliNord;
	}
	public void setDecimaliNord(String decimaliNord) {
		this.decimaliNord = decimaliNord;
	}
	public String getGradiEst() {
		return gradiEst;
	}
	public void setGradiEst(String gradiEst) {
		this.gradiEst = gradiEst;
	}
	public String getMinutiEst() {
		return minutiEst;
	}
	public void setMinutiEst(String minutiEst) {
		this.minutiEst = minutiEst;
	}
	public String getSecondiEst() {
		return secondiEst;
	}
	public void setSecondiEst(String secondiEst) {
		this.secondiEst = secondiEst;
	}
	public String getDecimaliEst() {
		return decimaliEst;
	}
	public void setDecimaliEst(String decimaliEst) {
		this.decimaliEst = decimaliEst;
	}
	public String getGradiNordTot() {
		return gradiNordTot;
	}
	public void setGradiNordTot(String gradiNordTot) {
		this.gradiNordTot = gradiNordTot;
	}
	public String getGradiEstTot() {
		return gradiEstTot;
	}
	public void setGradiEstTot(String gradiEstTot) {
		this.gradiEstTot = gradiEstTot;
	}
	public String getMinutiNordTot() {
		return minutiNordTot;
	}
	public void setMinutiNordTot(String minutiNordTot) {
		this.minutiNordTot = minutiNordTot;
	}
	public String getMinutiEstTot() {
		return minutiEstTot;
	}
	public void setMinutiEstTot(String minutiEstTot) {
		this.minutiEstTot = minutiEstTot;
	}
	public String getSecondiNordTot() {
		return secondiNordTot;
	}
	public void setSecondiNordTot(String secondiNordTot) {
		this.secondiNordTot = secondiNordTot;
	}
	public String getSecondiEstTot() {
		return secondiEstTot;
	}
	public void setSecondiEstTot(String secondiEstTot) {
		this.secondiEstTot = secondiEstTot;
	}
	
	public static void main(String[] args) {
		
//		String p = "AAAAA.GGTTTT";
//		int l =p.indexOf(".");
//		//System.out.println(l);
//		String m = p.substring(l, p.length());
//		System.out.println(m);
//		String n = p.substring(0, l);
//		//System.out.println(n);
//		System.out.println(n+m);
//		
		//System.out.println(p.substring(0, l)+p.substring(l, l+3));
		new java.sql.Date(new java.util.Date().getTime());
		Calendar calendar = new GregorianCalendar();
		System.out.println(calendar.getTimeInMillis());
	}
  
}