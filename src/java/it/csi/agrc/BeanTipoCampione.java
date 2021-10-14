package it.csi.agrc;
import it.csi.cuneo.BeanDataSource;

//import java.sql.*;
//import it.csi.jsf.web.pool.*;
import java.io.Serializable;
import java.util.Vector;

/**
 * Title:        Agrichim - Front Office
 * Description:  Richiesta analisi chimiche su campioni biologici agrari
 * Copyright:    Copyright (c) 2003
 * Company:      CSI Piemonte - Progettazione e Sviluppo - Cuneo
 * @author Michele Piantà, Piergiorgio Chiriotti
 * @version 1.0.0
 */

public class BeanTipoCampione extends BeanDataSource implements Serializable
{
	private static final long serialVersionUID = 5951500688586827771L;

	private String codMateriale[];
    private String descMateriale[];

    private String codLaboratorio[];
    private String descLaboratorio[];

    private String codModalita[];
    private String descModalita[];
    
    private CodiciMisuraPsr codiciMisuraPsr;

    public void setCodMateriale(Vector codVector)
    {
      codMateriale = (String[]) codVector.toArray(new String[0]);
    }
    public void setDescMateriale(Vector descVector)
    {
      descMateriale = (String[]) descVector.toArray(new String[0]);
    }

    public String [] getCodMateriale()
    {
      return codMateriale;
    }
    public String [] getDescMateriale()
    {
      return descMateriale;
    }
    public void setCodLaboratorio(Vector codVector)
    {
      codLaboratorio = (String[]) codVector.toArray(new String[0]);
    }
    public void setDescLaboratorio(Vector descVector)
    {
      descLaboratorio = (String[]) descVector.toArray(new String[0]);
    }

    public String [] getCodLaboratorio()
    {
      return codLaboratorio;
    }
    public String [] getDescLaboratorio()
    {
      return descLaboratorio;
    }
    public void setCodModalita(Vector codVector)
    {
      codModalita = (String[]) codVector.toArray(new String[0]);
    }
    public void setDescModalita(Vector descVector)
    {
      descModalita = (String[]) descVector.toArray(new String[0]);
    }

    public String [] getCodModalita()
    {
      return codModalita;
    }
    public String [] getDescModalita()
    {
      return descModalita;
    }
		public CodiciMisuraPsr getCodiciMisuraPsr()
		{
			return codiciMisuraPsr;
		}
		public void setCodiciMisuraPsr(CodiciMisuraPsr codiciMisuraPsr)
		{
			this.codiciMisuraPsr = codiciMisuraPsr;
		}
}