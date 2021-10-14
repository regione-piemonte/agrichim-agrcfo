<?php
	include("loto_service.php");
	
	class JSONComune
	{
		var $id = "";
		var $label = "";
		var $value = "";
	}
	
	$nome= "";
	if($_GET["nome"]!="") $nome=$_GET["nome"];
	
	$maxRows = 10;

	$client = new lotoLotoService();

	# ---------------------------------------------------------------------
	# ricerca comune
	
	$inComu = new DatiRicercaComune();
	$inComu->comune = $nome;
	$inComu->idRegione = "01"; // solo regione piemonte
	
	$in1 = new ConfigurazioneRicerca();
	$in1->modalita = 2;
	$in1->ordine = 1;
	
	$comuni = $client->cercaComuniByNome($inComu,$in1);
	
	$resComuni = array();
	$i = 0;
	while($i<$maxRows and $i<count($comuni))
	{
		$com = new JSONComune();
		$com->id = $comuni[$i]->id;
		$com->value = $comuni[$i]->codiceIstat;
		$com->label = $comuni[$i]->nome;
		
		array_push($resComuni,$com);
		$i++;
	}
	
	//echo count($comune)."<hr>";
	/*
	echo "<pre>";
	print_r($comuni);
	echo "</pre><hr>";
	*/
	
	echo json_encode($resComuni);
	
	/*
	echo "<pre>";
	print_r($resComuni);
	echo "</pre>";
	*/
?>