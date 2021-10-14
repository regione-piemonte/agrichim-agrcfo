<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.cuneo.*,java.util.*,it.csi.agrc.*" isThreadSafe="true" %>

<%
  it.csi.jsf.htmpl.Htmpl templ=null ;
%>

<%@ include file="/jsp/controllaLogin.inc" %>
<%@ include file="/jsp/dataSource.inc" %>

<jsp:useBean
     id="analisi"
     scope="page"
     class="it.csi.agrc.Analisi">
    <%
      analisi.setDataSource(dataSource);
      analisi.setAut(aut);
      analisi.setIdRichiesta(aut.getIdRichiestaCorrente());
    %>
</jsp:useBean>

<jsp:useBean
     id="fase"
     scope="page"
     class="it.csi.agrc.FasiRichiesta">
    <%
      fase.setDataSource(dataSource);
      fase.setAut(aut);
    %>
</jsp:useBean>


<%
  boolean calcAtt=false,umidita=false;

  Vector codiciAnalisi=new Vector();
  String pagina=request.getParameter("page");

  String codice[]=new String[2];

  if ("true".equals(request.getParameter("Tipo")))
  {
    codice[1]=request.getParameter("ANA_PACCHETTO_TIPO");
    codice[0]=Analisi.ANA_PACCHETTO_TIPO;
    codiciAnalisi.add(codice);
  }
  else
  {
    String codiceCSC,codiceMagnesio,codicePotassio,codiceCalcio;
    codiceCSC=request.getParameter(Analisi.ANA_CAPACITASCAMBIOCATIONICO);
    codiceMagnesio=request.getParameter(Analisi.ANA_MAGNESIO);
    codicePotassio=request.getParameter(Analisi.ANA_POTASSIO);
    codiceCalcio=request.getParameter(Analisi.ANA_CALCIO);
    if (codiceCSC!=null && codiceMagnesio!=null && codicePotassio!=null && codiceCalcio!=null)
    {
      codice=new String[2];
      codice[1]=request.getParameter("ANA_PACCHETTO_COMP_SCAMBIO");
      codice[0]=Analisi.ANA_PACCHETTO_COMP_SCAMBIO;
      codiciAnalisi.add(codice);
    }
    else
    {
      codice=new String[2];
      codice[1]=codiceCSC;
      if (codice[1]!=null)
      {
        codice[0]=Analisi.ANA_CAPACITASCAMBIOCATIONICO;
        codiciAnalisi.add(codice);
      }
      codice=new String[2];
      codice[1]=codicePotassio;
      if (codice[1]!=null)
      {
        codice[0]=Analisi.ANA_POTASSIO;
        codiciAnalisi.add(codice);
      }
      codice=new String[2];
      codice[1]=codiceMagnesio;
      if (codice[1]!=null)
      {
        codice[0]=Analisi.ANA_MAGNESIO;
        codiciAnalisi.add(codice);
      }
      codice=new String[2];
      codice[1]=codiceCalcio;
      if (codice[1]!=null)
      {
        codice[0]=Analisi.ANA_CALCIO;
        codiciAnalisi.add(codice);
      }
    }
    codice=new String[2];
    codice[1]=request.getParameter(Analisi.ANA_PH);
    if (codice[1]!=null)
    {
      codice[0]=Analisi.ANA_PH;
      codiciAnalisi.add(codice);
    }
    codice=new String[2];
    codice[1]=request.getParameter(Analisi.ANA_SOSTANZAORGANICA);
    if (codice[1]!=null)
    {
      codice[0]=Analisi.ANA_SOSTANZAORGANICA;
      codiciAnalisi.add(codice);
    }
    codice=new String[2];
    codice[1]=request.getParameter(Analisi.ANA_CALCARETOTALE);
    if (codice[1]!=null)
    {
      codice[0]=Analisi.ANA_CALCARETOTALE;
      codiciAnalisi.add(codice);
    }
    codice=new String[2];
    codice[1]=request.getParameter(Analisi.ANA_FOSFORO);
    if (codice[1]!=null)
    {
      codice[0]=Analisi.ANA_FOSFORO;
      codiciAnalisi.add(codice);
    }
    codice=new String[2];
    codice[1]=request.getParameter(Analisi.ANA_AZOTO);
    if (codice[1]!=null)
    {
      codice[0]=Analisi.ANA_AZOTO;
      codiciAnalisi.add(codice);
    }
  }

  codice=new String[2];
  codice[1]=request.getParameter(Analisi.ANA_SALINITA);
  if (codice[1]!=null)
  {
    codice[0]=Analisi.ANA_SALINITA;
    codiciAnalisi.add(codice);
  }

  if (aut.getCodMateriale().equals(it.csi.agrc.Analisi.MAT_TERRENO))
  {
    codice=new String[2];
    codice[1]=request.getParameter(Analisi.ANA_BORO);
    if (codice[1]!=null)
    {
      codice[0]=Analisi.ANA_BORO;
      codiciAnalisi.add(codice);
    }
  }
  if ("true".equals(request.getParameter("Micro")))
  {
    codice=new String[2];
    codice[1]=request.getParameter("ANA_PACCHETTO_MICROELEMENTI");
    codice[0]=Analisi.ANA_PACCHETTO_MICROELEMENTI;
    codiciAnalisi.add(codice);
  }
  else
  {
    codice=new String[2];
    codice[1]=request.getParameter(Analisi.ANA_FERRO);
    if (codice[1]!=null)
    {
      codice[0]=Analisi.ANA_FERRO;
      codiciAnalisi.add(codice);
    }
    codice=new String[2];
    codice[1]=request.getParameter(Analisi.ANA_ZINCO);
    if (codice[1]!=null)
    {
      codice[0]=Analisi.ANA_ZINCO;
      codiciAnalisi.add(codice);
    }
    codice=new String[2];
    codice[1]=request.getParameter(Analisi.ANA_RAME);
    if (codice[1]!=null)
    {
      codice[0]=Analisi.ANA_RAME;
      codiciAnalisi.add(codice);
    }
    codice=new String[2];
    codice[1]=request.getParameter(Analisi.ANA_MANGANESE);
    if (codice[1]!=null)
    {
      codice[0]=Analisi.ANA_MANGANESE;
      codiciAnalisi.add(codice);
    }
    if (!aut.getCodMateriale().equals(it.csi.agrc.Analisi.MAT_TERRENO))
    {
      codice=new String[2];
      codice[1]=request.getParameter(Analisi.ANA_BORO);
      if (codice[1]!=null)
      {
        codice[0]=Analisi.ANA_BORO;
        codiciAnalisi.add(codice);
      }
    }
  }

  codice=new String[2];
  codice[1]=request.getParameter(Analisi.ANA_UMIDITA);
  if (codice[1]!=null)
  {
    umidita=true;
    codice[0]=Analisi.ANA_UMIDITA;
    codiciAnalisi.add(codice);
  }
  codice=new String[2];
  codice[1]=request.getParameter(Analisi.ANA_CALCAREATTIVO);
  if (codice[1]!=null)
  {
    calcAtt=true;
    codice[0]=Analisi.ANA_CALCAREATTIVO;
    codiciAnalisi.add(codice);
  }

  codice=new String[2];
  codice[1]=request.getParameter("fisMecc");
  if ( null!=codice[1] && !"0".equals(codice[1]) )
  {
    codice[0]=request.getParameter("fisMeccCodice");
    codiciAnalisi.add(codice);
  }

  /*if ("true".equals(request.getParameter("MetPes")))
  {
    codice=new String[2];
    codice[1]=request.getParameter("ANA_PACCHETTO_METALLI_PESANTI");
    codice[0]=Analisi.ANA_PACCHETTO_METALLI_PESANTI;
    codiciAnalisi.add(codice);
  }
  else
  {*/
	  
	  //visto che i metalli pesanti se sono più di un tot valore recuperato da METALLI_PESANTI_SCONTO_NUMERO bisogna appilcare uno sconto segnato sul parametro METALLI_PESANTI_SCONTO_PERCENTUALE 
	  String sconto = request.getParameter("METALLI_PESANTI_SCONTO_PERCENTUALE");
      float scontoF = Float.parseFloat(sconto);
      CuneoLogger.debug(this,"Elisa sconto "+ sconto);
      CuneoLogger.debug(this,"Elisa scontoF "+ scontoF);
      String  numero = request.getParameter("METALLI_PESANTI_SCONTO_NUMERO");
      int numeroInt = new Integer(numero).intValue();
      //conto quanti metalli pesanti sono stati selezionati
      int numeroMetalliPesanti = 0;
      
      float importo = 0;
      float importoScontato = 0;
      
      String ferroTot = request.getParameter(Analisi.ANA_FERRO_TOTALE);
      String manganeseTot = request.getParameter(Analisi.ANA_MANGANESE_TOTALE);
      String zincoTot = request.getParameter(Analisi.ANA_ZINCO_TOTALE);
      String rameTot  = request.getParameter(Analisi.ANA_RAME_TOTALE);
      String cadmioTot = request.getParameter(Analisi.ANA_CADMIO_TOTALE);
      String cromoTotale = request.getParameter(Analisi.ANA_CROMO_TOTALE);
      String nichelTot = request.getParameter(Analisi.ANA_NICHEL_TOTALE);
      String piomboTotale = request.getParameter(Analisi.ANA_PIOMBO_TOTALE);
      
      if(ferroTot!=null)
      {
    	  numeroMetalliPesanti ++;
      }
      if(manganeseTot!=null)
      {
    	  numeroMetalliPesanti ++;
      }
      if(zincoTot!=null)
      {
    	  numeroMetalliPesanti ++;
      }
      if(rameTot!=null)
      {
    	  numeroMetalliPesanti ++;
      }
      if(cadmioTot!=null)
      {
    	  numeroMetalliPesanti ++;
      }
      if(cromoTotale!=null)
      {
    	  numeroMetalliPesanti ++;
      }
      if(nichelTot!=null)
      {
    	  numeroMetalliPesanti ++;
      }
      if(piomboTotale!=null)
      {
    	  numeroMetalliPesanti ++;
      }
      
		//Ferro totale
	  codice = new String[2];
	  codice[1] = ferroTot;
	  if (codice[1] != null)
	  {
		  if(numeroMetalliPesanti>numeroInt)
		  {
			 importo = Float.parseFloat(codice[1]);
			 
		     importo = (importo * 100) / 100;
			 importoScontato = importo * scontoF / 100;
			 importoScontato = (importoScontato * 100) / 100;
			 importoScontato = importo - importoScontato;
			
			 codice[1] = new Float(importoScontato).toString();
		  }  
		  
	    codice[0] = Analisi.ANA_FERRO_TOTALE;
	    codiciAnalisi.add(codice);
	  }
	
		//Manganese totale
	  codice = new String[2];
	  codice[1] = manganeseTot;
	  if (codice[1] != null)
	  {
		  if(numeroMetalliPesanti>numeroInt)
		  {
			 importo = Float.parseFloat(codice[1]);
			 
		     importo = (importo * 100) / 100;
			 importoScontato = importo * scontoF / 100;
			 importoScontato = (importoScontato * 100) / 100;
			 importoScontato = importo - importoScontato;
			
			 codice[1] = new Float(importoScontato).toString();
		  }  
	    codice[0] = Analisi.ANA_MANGANESE_TOTALE;
	    codiciAnalisi.add(codice);
	  }
	
		//Zinco totale
	  codice = new String[2];
	  codice[1] = zincoTot;
	  if (codice[1] != null)
	  {
		  if(numeroMetalliPesanti>numeroInt)
		  {
			 importo = Float.parseFloat(codice[1]);
			 
		     importo = (importo * 100) / 100;
			 importoScontato = importo * scontoF / 100;
			 importoScontato = (importoScontato * 100) / 100;
			 importoScontato = importo - importoScontato;
			
			 codice[1] = new Float(importoScontato).toString();
		  }  
	    codice[0] = Analisi.ANA_ZINCO_TOTALE;
	    codiciAnalisi.add(codice);
	  }
	
		//Rame totale
	  codice = new String[2];
	  codice[1] = rameTot;
	  if (codice[1] != null)
	  {
		  if(numeroMetalliPesanti>numeroInt)
		  {
			 importo = Float.parseFloat(codice[1]);
			 
		     importo = (importo * 100) / 100;
			 importoScontato = importo * scontoF / 100;
			 importoScontato = (importoScontato * 100) / 100;
			 importoScontato = importo - importoScontato;
			
			 codice[1] = new Float(importoScontato).toString();
		  }  
	    codice[0] = Analisi.ANA_RAME_TOTALE;
	    codiciAnalisi.add(codice);
	  }
	
		//Boro totale
	/*  codice = new String[2];
	  codice[1] = request.getParameter(Analisi.ANA_BORO_TOTALE);
	  if (codice[1] != null)
	  {
	    codice[0] = Analisi.ANA_BORO_TOTALE;
	    codiciAnalisi.add(codice);
	  }
	*/
		//Cadmio totale
	  codice = new String[2];
	  codice[1] = cadmioTot;
	  if (codice[1] != null)
	  {
		  if(numeroMetalliPesanti>numeroInt)
		  {
			 importo = Float.parseFloat(codice[1]);
			 
		     importo = (importo * 100) / 100;
			 importoScontato = importo * scontoF / 100;
			 importoScontato = (importoScontato * 100) / 100;
			 importoScontato = importo - importoScontato;
			
			 codice[1] = new Float(importoScontato).toString();
		  }  
	    codice[0] = Analisi.ANA_CADMIO_TOTALE;
	    codiciAnalisi.add(codice);
	  }
	
		//Cromo totale
	  codice = new String[2];
	  codice[1] = cromoTotale;
	  if (codice[1] != null)
	  {
		  if(numeroMetalliPesanti>numeroInt)
		  {
			 importo = Float.parseFloat(codice[1]);
			 
		     importo = (importo * 100) / 100;
			 importoScontato = importo * scontoF / 100;
			 importoScontato = (importoScontato * 100) / 100;
			 importoScontato = importo - importoScontato;
			
			 codice[1] = new Float(importoScontato).toString();
		  }  
	    codice[0] = Analisi.ANA_CROMO_TOTALE;
	    codiciAnalisi.add(codice);
	  }
	
		//Nichel totale
	  codice = new String[2];
	  codice[1] = nichelTot;
	  if (codice[1] != null)
	  {
		  if(numeroMetalliPesanti>numeroInt)
		  {
			 importo = Float.parseFloat(codice[1]);
			 
		     importo = (importo * 100) / 100;
			 importoScontato = importo * scontoF / 100;
			 importoScontato = (importoScontato * 100) / 100;
			 importoScontato = importo - importoScontato;
			
			 codice[1] = new Float(importoScontato).toString();
		  }  
	    codice[0] = Analisi.ANA_NICHEL_TOTALE;
	    codiciAnalisi.add(codice);
	  }
	
		//Piombo totale
	  codice = new String[2];
	  codice[1] = piomboTotale;
	  if (codice[1] != null)
	  {
		  if(numeroMetalliPesanti>numeroInt)
		  {
			 importo = Float.parseFloat(codice[1]);
			 
		     importo = (importo * 100) / 100;
			 importoScontato = importo * scontoF / 100;
			 importoScontato = (importoScontato * 100) / 100;
			 importoScontato = importo - importoScontato;
			
			 codice[1] = new Float(importoScontato).toString();
		  }  
	    codice[0] = Analisi.ANA_PIOMBO_TOTALE;
	    codiciAnalisi.add(codice);
	  }
	
		//Stronzio totale
	/*  codice = new String[2];
	  codice[1] = request.getParameter(Analisi.ANA_STRONZIO_TOTALE);
	  if (codice[1] != null)
	  {
	    codice[0] = Analisi.ANA_STRONZIO_TOTALE;
	    codiciAnalisi.add(codice);
	  }
		*/
		/*//Altro metallo totale
	  codice = new String[2];
	  codice[1] = request.getParameter(Analisi.ANA_ALTRO_METALLO_TOTALE);
	  if (codice[1] != null)
	  {
	    codice[0] = Analisi.ANA_ALTRO_METALLO_TOTALE;
	    codiciAnalisi.add(codice);
	  }
	}*/

  analisi.setCodiciAnalisi(codiciAnalisi);
  String note=request.getParameter("note");
  analisi.setNote(note);
  double costoAnalisi;
  try
  {
    costoAnalisi=Double.parseDouble(request.getParameter("costoAnalisiTot"));
  }
  catch(Exception e)
  {
    costoAnalisi=0;
  }
  /**
  * Non posso far proseguire se non è stata selezionata nessuna richiesta di
  * analisi. Se il costo è uguale a zero vuol dire che non è stata selezionata
  * nessuna richiesta: devo però controllare anche l'analisi del calcare
  * attivo perché in particolari condizioni può avere costo uguale a zero
  */
  if (costoAnalisi==0 && !calcAtt && !umidita)
  {
    Utili.forwardConParametri(request, response, "/jsp/view/"+pagina+"?errore=errore");
    return;
  }
  if (aut.getFase()<6)
  {
    /**
     * Questo campione è la prima volta che ha raggiunto questa fase,
     * quindi bisogna memorizzare nel bean e nella tabella la fase 3
     */
    fase.updateFase(6);
    aut.setFase((byte)6);
    analisi.insert();
  }
  else analisi.update();
  /**
  * Memorizzo all'interno della sessione il costo dell'analisi per visualizzarlo
  * nell'ultima pagina
  */
  aut.setCostoAnalisi(costoAnalisi);
  session.setAttribute("aut",aut);

  Utili.forward(request, response, "/jsp/view/confermaRichiesta.jsp");
%>


