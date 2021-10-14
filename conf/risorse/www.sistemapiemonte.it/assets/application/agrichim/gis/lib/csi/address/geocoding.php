<html>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	
	<head>
		<title>Ricerca coordinate civico</title>
		
		<link href="lib/jquery/css/ui-lightness/jquery-ui-1.9.2.custom.css" rel="stylesheet">
		<script type="text/javascript" src="lib/jquery/jquery-1.8.3.js"></script>
		<script type="text/javascript" src="lib/jquery/jquery-ui-1.9.2.custom.min.js"></script>
		
		<style>
			body{
				font-family: helvetica, verdana;
			}
			.ui-autocomplete-loading {
				background: white url('images/ui-anim_basic_16x16.gif') right center no-repeat;
			}
			#comune { width: 25em; }
			#via { width: 25em; }
			
			#risultato{
				border: 1px solid #888;
				width: 510px;
				height: 50px;
				background-color: #eee;
				font-family: monospace;
				font-size: 14px;
				padding: 5px;
				margin-top: 0px;
			}
			h1{
				font-size: 20px;
			}
		</style>
		
		<script>
		
		// ISTAT preimpostato su vercelli
		var istatComune = "002158";
		var idL2 = "";
		
		// ------------------------------------------------------------------------------
		$(function() {
			$( "#comune" ).autocomplete({
				source: function( request, response ) {
					$.ajax({
						url: "cerca_comune.php",
						dataType: "json",
						data: {
							nome: request.term
						},
						success: function( data )
						{
							response( $.map( data, function( item ) {
								return {
									label: item.label,
									value: item.label,
									istat: item.value
								}
							}));
						}
					});
				},
				minLength: 3,
				delay: 100,
				select: function( event, ui ) {
					if(ui.item) istatComune = ui.item.istat;
				},
				open: function() {
					$( this ).removeClass( "ui-corner-all" ).addClass( "ui-corner-top" );
				},
				close: function() {
					$( this ).removeClass( "ui-corner-top" ).addClass( "ui-corner-all" );
				}
			});
		});
		
		// ------------------------------------------------------------------------------
		$(function() {
			$( "#via" ).autocomplete({
				source: function( request, response ) {
					$.ajax({
						url: "cerca_via.php",
						dataType: "json",
						data: {
							istat: istatComune,
							nome: request.term
						},
						success: function( data )
						{
							response( $.map( data, function( item ) {
								return {
									label: item.label,
									value: item.label,
									idL2: item.value
								}
							}));
						}
					});
				},
				minLength: 2,
				select: function( event, ui ) {
					if(ui.item) idL2 = ui.item.idL2;
				},
				open: function() {
					$( this ).removeClass( "ui-corner-all" ).addClass( "ui-corner-top" );
				},
				close: function() {
					$( this ).removeClass( "ui-corner-top" ).addClass( "ui-corner-all" );
				}
			});
		});
		
		// ------------------------------------------------------------------------------
		$(function() {
			$( "#civico" ).autocomplete({
				source: function( request, response ) {
					$.ajax({
						url: "cerca_civico.php",
						dataType: "json",
						data: {
							idl2: idL2,
							civico: request.term
						},
						success: function( data )
						{
							response( $.map( data, function( item ) {
								return {
									id: item.id,
									label: item.label,
									value: item.label,
									x: item.x,
									y: item.y,
									tecnico: item.tecnico,
									area: item.area,
									//geometra: item.geometra,
									idgeometra: item.idgeometra
								}
							}));
						}
					});
				},
				minLength: 1,
				select: function( event, ui ) {
					if(ui.item)
					{
						x = ui.item.x;
						y = ui.item.y;
						//geometra = ui.item.geometra;
						tecnico = ui.item.tecnico;
						area = ui.item.area;
						
						risultato = "<b>x:</b> "+x+"&nbsp;&nbsp;<b>y:</b> "+y+"<br><b>Area: </b>"+area+"<br><b>Tecnico: </b>"+tecnico;
						document.getElementById("risultato").innerHTML = risultato;
					}
				},
				open: function() {
					$( this ).removeClass( "ui-corner-all" ).addClass( "ui-corner-top" );
				},
				close: function() {
					$( this ).removeClass( "ui-corner-top" ).addClass( "ui-corner-all" );
				}
			});
		});
		
		</script>
		
	</head>
	
	<body>
		<h1>Ricerca coordinate civico Comune di Vercelli</h1>
		<table class="ui-widget">
			<tr>
				<td><label for="comune">comune: </label></td>
				<td><input id="comune" /></td>
			</tr>
			<tr>
				<td><label for="via">via: </label></td>
				<td><input id="via" /></td>
			</tr>
			<tr>
				<td><label for="civico">civico: </label></td>
				<td><input id="civico" /></td>
			</tr>
		</table>
		<br/>
		<div>
			coordinate:
			<div id="risultato">
				<b>x:</b> -&nbsp;&nbsp;<b>y:</b> -
				<br><b>Area: </b> -
				<br><b>Tecnico: </b> -
			</div>
		</div>
	</body>
	
</html>