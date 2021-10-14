<?php
	include("loto_service.php");
	
	class JSONVia
	{
		var $id = "";
		var $label = "";
		var $value = "";
	}
	
	$nome= "";
	if($_GET["nome"]!="") $nome=$_GET["nome"];
	
	$istat = "";
	if($_GET["istat"]!="") $istat=$_GET["istat"];
	
	// blocco su VERCELLI
	//$istat = "002158";
	
	$maxRows = 10;

	$client = new lotoLotoService();

	# ---------------------------------------------------------------------
	# ricerca via
	
	//echo $istat."<hr>";
	
	$inVia = new DatiRicercaViaByIstat();
	$inVia->istatComune = array($istat);
	$inVia->indirizzo = $nome;

	//$inVia->codUffEnteFornitore
	
	
	$in1 = new ConfigurazioneRicerca();
	$in1->modalita = 2;
	$in1->ordine = 1;
	
	$vie = $client->cercaVieComunaliByNomeAndIstat($inVia,$in1,true);
	
	/*
	echo "<pre>";
	print_r($vie);
	echo "</pre>";
	*/
	
	$resVia = array();
	$i = 0;
	while($i<$maxRows and $i<count($vie))
	{
		$com = new JSONVia();
		//$com->id = $vie[$i]->id;
		$com->value = $vie[$i]->idL2;
		$com->label = $vie[$i]->indirizzo->indirizzo;
		
		array_push($resVia,$com);
		$i++;
	}
	
	//echo count($comune)."<hr>";
	/*
	echo "<pre>";
	print_r($resVia);
	echo "</pre><hr>";
	*/
	
	echo json_encode($resVia);
	
?>