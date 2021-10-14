<?php
	include("lanci_service.php");
	
	class JSONCivico
	{
		var $id = "";
		var $label = "";
		var $value = "";
		var $x = 0;
		var $y = 0;
	}
	
	$idl2= "";
	if($_GET["idl2"]!="") $idl2=$_GET["idl2"];
	
	$descCivico = "";
	if($_GET["civico"]!="") $descCivico=$_GET["civico"];
	
	$maxRows = 10;

	$client = new lanciLanciService();

	# ---------------------------------------------------------------------
	# ricerca civico
	
	$in0 = $idl2;
	$in1 = $descCivico;
	$in2 = 1;
	$civico = $client->cercaCivico($in0,$in1,$in2);
	
	/*
	echo "<pre>";
	print_r($civico);
	echo "</pre>";
	*/
	
	$com = new JSONCivico();

	if($civico)
	{
		$com->id = $civico->id;
		$com->value = $civico->idL2Ufficiale;
		$com->label = $civico->numero." ".$civico->subalterno;
		if(trim($com->label) == "") $com->label=$descCivico;
		$com->x = $civico->x;
		$com->y = $civico->y;
	}
	/*
	echo "<pre>";
	print_r($com);
	echo "</pre><hr>";
	*/
	
	echo json_encode(array($com));
?>