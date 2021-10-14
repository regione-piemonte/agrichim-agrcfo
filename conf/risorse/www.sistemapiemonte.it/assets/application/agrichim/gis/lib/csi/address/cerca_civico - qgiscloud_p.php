<?php
	include("lanci_service.php");
	
	class JSONCivico
	{
		var $id = "";
		var $label = "";
		var $value = "";
		var $x = 0;
		var $y = 0;
		var $geometra = "";
		var $idgeometra = "";
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
		
		$conn_string = "host=tst-pgdbs.csi.it port=5432 dbname=PGISTST1 user=qgiscloud_p password=ed1ge2ai";
		$dbconn = pg_connect ($conn_string);
		
		$xcoord = $civico->x;
		$ycoord = $civico->y;
		$query = 'SELECT * FROM geometri WHERE ST_Intersects(ST_SetSRID(ST_MakePoint('.$xcoord.', '.$ycoord.'),32632),geom)';
	    $result = pg_query($query) or die('Query fallita: ' . pg_last_error());    
    
		while ($r = pg_fetch_array($result, null, PGSQL_ASSOC))
		{
			$com->geometra = trim($r["geometra"]);
			$com->idgeometra = trim($r["id"]);
		}
		
		pg_free_result($result);
		pg_close($dbconn);
	}
	/*
	echo "<pre>";
	print_r($com);
	echo "</pre><hr>";
	*/
	
	echo json_encode(array($com));
?>