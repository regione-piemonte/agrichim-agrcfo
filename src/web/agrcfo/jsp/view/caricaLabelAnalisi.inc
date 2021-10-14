<jsp:useBean id="beanAnalisi" scope="application" class="it.csi.agrc.BeanAnalisi"/>

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

<%
  //Memorizzo la tariffa applicata nel bean si sessione
  session.setAttribute("aut",aut);

  /**
   * Vado a vedere se il parametro errore è valorizzato. In caso positivo
   * significa che devo visualizzare un messaggio di errore
   * */
  if ("errore".equals(request.getParameter("errore")))
  {
    templ.newBlock("errore");
  }

  String codice,note;
  java.util.Hashtable analisiHash=beanAnalisi.getAnalisi();
  java.util.Hashtable costoAnalisiHash=beanAnalisi.getCostoAnalisi();

  /**
   * Valorizzo il costo complessivo di partenza
   */
  templ.bset("costoComplessivo","0,00");

  /**
   * Valorizzo le label. Per le analisi del terreno devono valorizzarle
   * tutte e 20. Invece per erabcee e foglie frutta alcune non servono
   **/

  /**
   * Le label seguenti sono utilizzate solo dai terreni
   *
   **/
  if (aut.getCodMateriale().equals(it.csi.agrc.Analisi.MAT_TERRENO))
  {
    templ.bset("Label4Fra",(String)analisiHash.get(it.csi.agrc.Analisi.ANA_A4FRAZIONI));
    templ.bset("Label5Fra",(String)analisiHash.get(it.csi.agrc.Analisi.ANA_A5FRAZIONI));
    templ.bset("LabelCSC",(String)analisiHash.get(it.csi.agrc.Analisi.ANA_CAPACITASCAMBIOCATIONICO));
    templ.bset("LabelCaAtt",(String)analisiHash.get(it.csi.agrc.Analisi.ANA_CALCAREATTIVO));
    templ.bset("LabelCaCO3",(String)analisiHash.get(it.csi.agrc.Analisi.ANA_CALCARETOTALE));
    templ.bset("LabelSO",(String)analisiHash.get(it.csi.agrc.Analisi.ANA_SOSTANZAORGANICA));
    templ.bset("LabelSal",(String)analisiHash.get(it.csi.agrc.Analisi.ANA_SALINITA));
    templ.bset("LabelStd",(String)analisiHash.get(it.csi.agrc.Analisi.ANA_STANDARD));
    templ.bset("LabelpH",(String)analisiHash.get(it.csi.agrc.Analisi.ANA_PH));
  }
  templ.bset("LabelCa",(String)analisiHash.get(it.csi.agrc.Analisi.ANA_CALCIO));
  templ.bset("LabelK",(String)analisiHash.get(it.csi.agrc.Analisi.ANA_POTASSIO));
  templ.bset("LabelMg",(String)analisiHash.get(it.csi.agrc.Analisi.ANA_MAGNESIO));
  templ.bset("LabelN",(String)analisiHash.get(it.csi.agrc.Analisi.ANA_AZOTO));
  templ.bset("LabelP",(String)analisiHash.get(it.csi.agrc.Analisi.ANA_FOSFORO));
  templ.bset("LabelUm",(String)analisiHash.get(it.csi.agrc.Analisi.ANA_UMIDITA));

  //adesso non serve più fare la distinzione sui microelementi fra frutta
  //ed il resto, ma lascio tutto così se cambiano idea e facilmente ripristinabile

  /**
   *  Le seguenti label appartengono ai microelementi. Non devono essere
   *  visualizzate nel caso di Vegetali Frutta
   **/
  /*if (!aut.getCodMateriale().equals(it.csi.agrc.Analisi.MAT_FRUTTA))
  {*/
    templ.newBlock("microelementi");
    templ.set("microelementi.LabelMn",(String)analisiHash.get(it.csi.agrc.Analisi.ANA_MANGANESE));
    templ.set("microelementi.LabelZn",(String)analisiHash.get(it.csi.agrc.Analisi.ANA_ZINCO));
    templ.set("microelementi.LabelCu",(String)analisiHash.get(it.csi.agrc.Analisi.ANA_RAME));
    templ.set("microelementi.LabelFe",(String)analisiHash.get(it.csi.agrc.Analisi.ANA_FERRO));
    templ.set("microelementi.LabelB",(String)analisiHash.get(it.csi.agrc.Analisi.ANA_BORO));
  //}

  //Metalli pesanti
 
  templ.bset("LabelFeTot", (String) analisiHash.get(it.csi.agrc.Analisi.ANA_FERRO_TOTALE));
  templ.bset("LabelMnTot", (String) analisiHash.get(it.csi.agrc.Analisi.ANA_MANGANESE_TOTALE));
  templ.bset("LabelZnTot", (String) analisiHash.get(it.csi.agrc.Analisi.ANA_ZINCO_TOTALE));
  templ.bset("LabelCuTot", (String) analisiHash.get(it.csi.agrc.Analisi.ANA_RAME_TOTALE));

 // templ.bset("LabelBTot", (String) analisiHash.get(it.csi.agrc.Analisi.ANA_BORO_TOTALE));
  templ.bset("LabelCdTot", (String) analisiHash.get(it.csi.agrc.Analisi.ANA_CADMIO_TOTALE));
  templ.bset("LabelCrTot", (String) analisiHash.get(it.csi.agrc.Analisi.ANA_CROMO_TOTALE));
  templ.bset("LabelNiTot", (String) analisiHash.get(it.csi.agrc.Analisi.ANA_NICHEL_TOTALE));

  templ.bset("LabelPbTot", (String) analisiHash.get(it.csi.agrc.Analisi.ANA_PIOMBO_TOTALE));
 // templ.bset("LabelSrTot", (String) analisiHash.get(it.csi.agrc.Analisi.ANA_STRONZIO_TOTALE));
  //templ.bset("LabelMetTot", (String) analisiHash.get(it.csi.agrc.Analisi.ANA_ALTRO_METALLO_TOTALE));

  /**
   * Valorizzo il valore delle tariffe.Per le analisi del terreno devono valorizzarle
   * tutte e 20. Invece per erabcee e foglie frutta alcune non servono
   **/

  //Leggo la tariffa applicata
  String tariffa=null;
  switch(analisi.selectTariffaApplicata())
  {
    case 0: tariffa = Analisi.PREZZO_INTERO ;
            break;
    case 1: tariffa = Analisi.RIDUZIONE_1 ;
            break;
    case 2: tariffa = Analisi.RIDUZIONE_2 ;
            break;
    case 3: tariffa = Analisi.RIDUZIONE_3 ;
            break;
    case 4: tariffa = Analisi.RIDUZIONE_1_MACINATI ;
            break;
    case 5: tariffa = Analisi.RIDUZIONE_2_MACINATI ;
            break;
    case 6: tariffa = Analisi.RIDUZIONE_3_MACINATI ;
            break;
    default: tariffa = Analisi.RIDUZIONE_1 ;
  }
  codice=aut.getCodMateriale()+tariffa;


  /**
   * Le tariffe seguenti sono utilizzate solo dai terreni
   *
   **/
  if (aut.getCodMateriale().equals(it.csi.agrc.Analisi.MAT_TERRENO))
  {
    templ.bset("check4Fra",(String)costoAnalisiHash.get(it.csi.agrc.Analisi.ANA_A4FRAZIONI+codice));
    templ.bset("check5Fra",(String)costoAnalisiHash.get(it.csi.agrc.Analisi.ANA_A5FRAZIONI+codice));
    templ.bset("checkCSC",(String)costoAnalisiHash.get(it.csi.agrc.Analisi.ANA_CAPACITASCAMBIOCATIONICO+codice));

    /**
     * Se l'utente ha selezionato nuovo impianto, classe coltura uguale a
     * viticole e specie coltura uguale a vite l'analisi del calcare attivo
     * è gratis
     * */
    /*if (analisi.isCalcAttGratis())
      templ.bset("checkCaAtt","0");
    else*/
      templ.bset("checkCaAtt",(String)costoAnalisiHash.get(it.csi.agrc.Analisi.ANA_CALCAREATTIVO+codice));
System.out.println("ELISA 3");
    templ.bset("checkCaCO3",(String)costoAnalisiHash.get(it.csi.agrc.Analisi.ANA_CALCARETOTALE+codice));
    templ.bset("checkSO",(String)costoAnalisiHash.get(it.csi.agrc.Analisi.ANA_SOSTANZAORGANICA+codice));
    templ.bset("checkSal",(String)costoAnalisiHash.get(it.csi.agrc.Analisi.ANA_SALINITA+codice));
    templ.bset("checkStd",(String)costoAnalisiHash.get(it.csi.agrc.Analisi.ANA_STANDARD+codice));
    templ.bset("checkpH",(String)costoAnalisiHash.get(it.csi.agrc.Analisi.ANA_PH+codice));
  }

  templ.bset("checkCa",(String)costoAnalisiHash.get(it.csi.agrc.Analisi.ANA_CALCIO+codice));
  templ.bset("checkK",(String)costoAnalisiHash.get(it.csi.agrc.Analisi.ANA_POTASSIO+codice));
  templ.bset("checkN",(String)costoAnalisiHash.get(it.csi.agrc.Analisi.ANA_AZOTO+codice));
  templ.bset("checkP",(String)costoAnalisiHash.get(it.csi.agrc.Analisi.ANA_FOSFORO+codice));
  templ.bset("checkUm",(String)costoAnalisiHash.get(it.csi.agrc.Analisi.ANA_UMIDITA+codice));
  templ.bset("checkMg",(String)costoAnalisiHash.get(it.csi.agrc.Analisi.ANA_MAGNESIO+codice));

  templ.bset("ANA_PACCHETTO_TIPO",(String)costoAnalisiHash.get(it.csi.agrc.Analisi.ANA_PACCHETTO_TIPO+codice));
  if (aut.getCodMateriale().equals(it.csi.agrc.Analisi.MAT_TERRENO))
  {
    templ.bset("ANA_PACCHETTO_COMP_SCAMBIO",(String)costoAnalisiHash.get(it.csi.agrc.Analisi.ANA_PACCHETTO_COMP_SCAMBIO+codice));
  }
  templ.bset("ANA_PACCHETTO_MICROELEMENTI",(String)costoAnalisiHash.get(it.csi.agrc.Analisi.ANA_PACCHETTO_MICROELEMENTI+codice));
 // templ.bset("ANA_PACCHETTO_METALLI_PESANTI", (String) costoAnalisiHash.get(it.csi.agrc.Analisi.ANA_PACCHETTO_METALLI_PESANTI + codice));
  templ.bset("METALLI_PESANTI_SCONTO_NUMERO", beanParametriApplication.getMetalliPesantiScontoNumero());
  templ.bset("METALLI_PESANTI_SCONTO_PERCENTUALE", beanParametriApplication.getMetalliPesantiScontoPercentuale());

  //adesso non serve più fare la distinzione sui microelementi fra frutta
  //ed il resto, ma lascio tutto così se cambiano idea e facilmente ripristinabile
  /**
   *  Le seguenti tariffe appartengono ai microelementi. Non devono essere
   *  visualizzate nel caso di Vegetali Frutta
   **/
  /*if (!aut.getCodMateriale().equals(it.csi.agrc.Analisi.MAT_FRUTTA))
  {*/
    templ.set("microelementi.checkMn",(String)costoAnalisiHash.get(it.csi.agrc.Analisi.ANA_MANGANESE+codice));
    templ.set("microelementi.checkCu",(String)costoAnalisiHash.get(it.csi.agrc.Analisi.ANA_RAME+codice));
    templ.set("microelementi.checkFe",(String)costoAnalisiHash.get(it.csi.agrc.Analisi.ANA_FERRO+codice));
    templ.set("microelementi.checkZn",(String)costoAnalisiHash.get(it.csi.agrc.Analisi.ANA_ZINCO+codice));
    templ.set("microelementi.checkB",(String)costoAnalisiHash.get(it.csi.agrc.Analisi.ANA_BORO+codice));
  //}

  //Tariffe metalli pesanti
	templ.bset("checkFeTot", (String) costoAnalisiHash.get(it.csi.agrc.Analisi.ANA_FERRO_TOTALE + codice));
	templ.bset("checkMnTot", (String) costoAnalisiHash.get(it.csi.agrc.Analisi.ANA_MANGANESE_TOTALE + codice));
	templ.bset("checkZnTot", (String) costoAnalisiHash.get(it.csi.agrc.Analisi.ANA_ZINCO_TOTALE + codice));
	templ.bset("checkCuTot", (String) costoAnalisiHash.get(it.csi.agrc.Analisi.ANA_RAME_TOTALE + codice));
	
	//templ.bset("checkBTot", (String) costoAnalisiHash.get(it.csi.agrc.Analisi.ANA_BORO_TOTALE + codice));
	templ.bset("checkCdTot", (String) costoAnalisiHash.get(it.csi.agrc.Analisi.ANA_CADMIO_TOTALE + codice));
	templ.bset("checkCrTot", (String) costoAnalisiHash.get(it.csi.agrc.Analisi.ANA_CROMO_TOTALE + codice));
	templ.bset("checkNiTot", (String) costoAnalisiHash.get(it.csi.agrc.Analisi.ANA_NICHEL_TOTALE + codice));
	templ.bset("checkPbTot", (String) costoAnalisiHash.get(it.csi.agrc.Analisi.ANA_PIOMBO_TOTALE + codice));
	//templ.bset("checkSrTot", (String) costoAnalisiHash.get(it.csi.agrc.Analisi.ANA_STRONZIO_TOTALE + codice));
	//templ.bset("checkMetTot", (String) costoAnalisiHash.get(it.csi.agrc.Analisi.ANA_ALTRO_METALLO_TOTALE + codice));

  /**
   * Dopo aver preparato tutto le scheletro devo vedere ci sono dei dati da
   * leggere nel db
   * */
  if (aut.getFase()>5)
  {
    /**
    *  Devo leggere i dati dal database
    **/
    analisi.select();
    java.util.Enumeration enumAnalisi=analisi.getCodiciAnalisi().elements();
    boolean nessuno=true;
    boolean analisiCompleta=false;
    boolean analisiTipo=false;
    boolean analisiMetalliPesanti = false;

    while(enumAnalisi.hasMoreElements())
    {
      codice=(String)enumAnalisi.nextElement();
      templ.bset("checked"+codice,"checked");
      if (it.csi.agrc.Analisi.ANA_A4FRAZIONI.equals(codice)
                          ||
         it.csi.agrc.Analisi.ANA_A5FRAZIONI.equals(codice)
                          ||
         it.csi.agrc.Analisi.ANA_STANDARD.equals(codice))
      {
        nessuno=false;
        templ.bset("fisMeccCodice",codice);
      }
      if (it.csi.agrc.Analisi.ANA_PACCHETTO_MICROELEMENTI.equals(codice))
      {
        if (!aut.getCodMateriale().equals(it.csi.agrc.Analisi.MAT_TERRENO))
        {
          templ.bset("checked"+it.csi.agrc.Analisi.ANA_BORO,"checked");
        }
        templ.bset("checked"+it.csi.agrc.Analisi.ANA_FERRO,"checked");
        templ.bset("checked"+it.csi.agrc.Analisi.ANA_MANGANESE,"checked");
        templ.bset("checked"+it.csi.agrc.Analisi.ANA_ZINCO,"checked");
        templ.bset("checked"+it.csi.agrc.Analisi.ANA_RAME,"checked");
        analisiCompleta=true;
      }
      if (it.csi.agrc.Analisi.ANA_PACCHETTO_TIPO.equals(codice))
      {
        if (aut.getCodMateriale().equals(it.csi.agrc.Analisi.MAT_TERRENO))
        {
          templ.bset("checked"+it.csi.agrc.Analisi.ANA_PH,"checked");
          templ.bset("checked"+it.csi.agrc.Analisi.ANA_SOSTANZAORGANICA,"checked");
          templ.bset("checked"+it.csi.agrc.Analisi.ANA_CAPACITASCAMBIOCATIONICO,"checked");
          templ.bset("checked"+it.csi.agrc.Analisi.ANA_CALCARETOTALE,"checked");
        }
        templ.bset("checked"+it.csi.agrc.Analisi.ANA_MAGNESIO,"checked");
        templ.bset("checked"+it.csi.agrc.Analisi.ANA_POTASSIO,"checked");
        templ.bset("checked"+it.csi.agrc.Analisi.ANA_FOSFORO,"checked");
        templ.bset("checked"+it.csi.agrc.Analisi.ANA_CALCIO,"checked");
        templ.bset("checked"+it.csi.agrc.Analisi.ANA_AZOTO,"checked");
        analisiTipo=true;
      }
      if (it.csi.agrc.Analisi.ANA_PACCHETTO_COMP_SCAMBIO.equals(codice))
      {
        templ.bset("checked"+it.csi.agrc.Analisi.ANA_CAPACITASCAMBIOCATIONICO,"checked");
        templ.bset("checked"+it.csi.agrc.Analisi.ANA_MAGNESIO,"checked");
        templ.bset("checked"+it.csi.agrc.Analisi.ANA_POTASSIO,"checked");
        templ.bset("checked"+it.csi.agrc.Analisi.ANA_CALCIO,"checked");
      }
      if (it.csi.agrc.Analisi.ANA_PACCHETTO_METALLI_PESANTI.equals(codice))
      {
        templ.bset("checked" + it.csi.agrc.Analisi.ANA_FERRO_TOTALE, "checked");
        templ.bset("checked"+ it.csi.agrc.Analisi.ANA_MANGANESE_TOTALE, "checked");
        templ.bset("checked" + it.csi.agrc.Analisi.ANA_ZINCO_TOTALE, "checked");
        templ.bset("checked" + it.csi.agrc.Analisi.ANA_RAME_TOTALE, "checked");
       // templ.bset("checked" + it.csi.agrc.Analisi.ANA_BORO_TOTALE, "checked");
        templ.bset("checked" + it.csi.agrc.Analisi.ANA_CADMIO_TOTALE, "checked");
        templ.bset("checked"+ it.csi.agrc.Analisi.ANA_CROMO_TOTALE, "checked");
        templ.bset("checked" + it.csi.agrc.Analisi.ANA_NICHEL_TOTALE, "checked");
        templ.bset("checked" + it.csi.agrc.Analisi.ANA_PIOMBO_TOTALE, "checked");
        //templ.bset("checked" + it.csi.agrc.Analisi.ANA_STRONZIO_TOTALE, "checked");
        //templ.bset("checked" + it.csi.agrc.Analisi.ANA_ALTRO_METALLO_TOTALE, "checked");
        
        analisiMetalliPesanti = true;
      }      
    }
    if (analisiTipo) templ.bset("checkedTipoSi","checked");
    else templ.bset("checkedTipoNo","checked");
    if (analisiCompleta) templ.bset("checkedAnalisiCompletaSi","checked");
    else templ.bset("checkedAnalisiCompletaNo","checked");
    if (nessuno) templ.bset("checkedNessuno","checked");
    /*if (analisiMetalliPesanti)
    {
    	templ.bset("checkedMetPesSi","checked");
    }
    else
    {
    	templ.bset("checkedMetPesNo","checked");    
    }*/
    note=analisi.getNote();
    if (note==null) note="";
    templ.bset("note",note);
  }
  else
  {
    /**
    * Devo impostare i valori dei radioButton a No e quello dell'analisi
    * fisico meccanica a Nessuna
    */
    templ.bset("checkedTipoNo","checked");
    templ.bset("checkedAnalisiCompletaNo","checked");
    templ.bset("checkedNessuno","checked");
  }
  /**
     * Imposto la pagina corrente: questo serve all'interno della funzione
     * javascript chiamata all'onload della pagina per sapere
     * se il tipo di materiale è Vegetali Frutta o Vegetali Foglie (Fruttiferi
     * e Vite) dato utilizzano tutte e 2 la stessa pasina html:
     * foglieFruttaTipoAnalisi.htm
     * */
    if (aut.getCodMateriale().equals(it.csi.agrc.Analisi.MAT_FRUTTA))
      templ.bset("pagina","3");//Non serve più distinguere ma lascio tutto
  // così se cambiassero di nuovo idea è tutto in piedi
    if (aut.getCodMateriale().equals(it.csi.agrc.Analisi.MAT_FOGLIE))
      templ.bset("pagina","3");
%>