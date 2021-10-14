/*
jQuery XML Parser with Sorting and Filtering
Written by Ben Lister (@bahnburner) January 2010 
Last revised Aug 7, 2012
Tutorial: http://blog.darkcrimson.com/2010/01/jquery-xml-parser/

Licensed under the MIT License:
http://www.opensource.org/licenses/mit-license.php
*/

$.fn.extend
({ 
   GML_parser : function(data, el)
   {
		el.hide();
		var e = el;
		el.show();
		
		// parse GML
		var format = new OpenLayers.Format.XML();
		data = format.read(data);
		var entries = $(data).find('msGMLOutput'),
		layers = entries.children()
		layers.each(function() {
			header = false;
			
			layerId = this.nodeName;
			layerId = layerId.replace("_layer","")
			tableHtml = '<hr><h3>' + layerId + '</h3><table class="tablesorter" id="' + layerId + '"> <thead> </thead> <tbody> </tbody> </table>'
			$(tableHtml).appendTo(el);
			
			xmlHead = '<tr>';
			
			//dbg2.each(function() {alert((this).nodeName);})
			features = $(this).children()
			
			features.each(function() {
				// Add matched items to an array
				xmlArr = '<tr data-filtercriteria="'+ "" +'">';
				
				elements = $(this).children()
				elements.each(function() {
					//Atrributes from XML nodes
					xmlHead += '<th class="header">'+(this).nodeName+'</th>';
					xmlArr += '<td>'+ (this).textContent +'</td>';
				});
				
				if (!header) xmlHead += '</tr>';
				if (!header) {$(xmlHead).appendTo($('#'+layerId+' > thead')); header = true;}
				
				xmlArr += '</tr>';
				$(xmlArr).appendTo($('#'+layerId+' > tbody'));
			});
		}); 
		
		
		
		//Add sort and zebra stripe to table
		window.setTimeout(function(){ el.find('table').tablesorter({sortList:[[0,0],[0,0]], widgets: ['zebra']});}, 120);
		el.find('table').hide().slideDown('200');
		
		
		
		//Filter results functionality
		var nav_link = $('#xml_nav li a');
		
		nav_link.click( function() {
			
			var tr = el.find('tbody > tr'),
			attr_class = $(this).attr('class');
			tr.show(); //Show all rows
			
			switch (attr_class) {
				case  'filter_10 hit' :
					$(tr).filter(function() {
						return parseFloat($(this).data('filtercriteria')) > 10;
					}).hide();
				break;
				
				case  'filter_10_20 hit' :
					$(tr).filter(function() {
						return parseFloat($(this).data('filtercriteria')) < 10 || parseFloat($(this).data('filtercriteria')) > 20  ;
					}).hide();
				break;
				
				case  'filter_20 hit' :
					$(tr).filter(function() {
						return parseFloat($(this).data('filtercriteria')) < 20;
					}).hide();
				break;
				
				case  'filter_0 hit' :
					$(tr).show();
					
				break;
										
			} // end filter switch
				tr.removeClass('stripe');	
				$('table > tbody > tr:visible:odd').addClass('stripe');
		});// end filter   
		
	} 
})
    

