var demande = {};
var itiniraire = [];
var propositionCounter = 0;
var propositions = [];
var demandes = [];
var server  = "http://localhost:9999"; // server web-service rest
var mqServer  = "http://localhost:8080";
$(document).ready(function()
{
  hundleLoginForm();
  getVehiculeType();
  prepareAddressFinder();
  hundleCompareTime();
  hundleCovoitureur();
  hundleValidateRequest();
  handlePropositionsList();

 $('.date-fr').bootstrapMaterialDatePicker
    ({
        format: 'DD/MM/YYYY HH:mm',
        lang: 'fr',
        weekStart: 1, 
        cancelText : 'ANNULER',
        nowButton : true,
        switchOnClick : true,
        minDate : new Date()
    });
    $('.date-fr').val(new Date().toLocaleString());

    $.material.init()
});




  
 function initSearchForm(){
   getVehiculeType();
   prepareAddressFinder();
 }

function getVehiculeType(){

    //appel du web service recuperation de type vehicule
    $.ajax( {
		    type:'Get',
		    url:server + '/vehicules',       
		    dataType: 'json',
		    success:function (responseData, textStatus, errorThrown) {
		    	
		    	$.each( responseData, function( index, vehicule ){
		    		$('#vehiculeTypeSelect').append($('<option>', {
			            value: vehicule.maxPassenger,
			            text: vehicule.type
			        }));
		    	});
		    	
		    	
			    },
	    	error: function (responseData, textStatus, errorThrown) {
	   		    alert('Erruer de recuperation de type de vehicule !');
	    	}
	
    	})
    
    }

function prepareAddressFinder(){
	
	$( "#departureAddressInput,#arrivalAddressInput" ).autocomplete({
	      source: function( request, response ) {
	    	  var url = mqServer + "/address?voie="+request.term; 
	        $.ajax({
	          url: url,
	          dataType: "json",
	          data: {
	            q: request.term
	          },
	          success: function( data ) {
	        	var addressList = [];
	        	  $.each(data,function(index,address){
	        		 var value = address.numero + "," + address.voie + "," + address.code_post;
	        		 addressList.push({value : value, lon : address.lon, lat : address.lat}); 
	        	  });
	            response( addressList );
	          }
	        });
	      },
	      minLength: 4,
	      select: function( event, ui ) {

	        itiniraire.push(ui.item.lon);
	        itiniraire.push(ui.item.lat);
	        
	        toastr.success("lon : " + ui.item.lon +"<br/>" +"lat : " +ui.item.lat  );
	      },
	      open: function() {
	        $( this ).removeClass( "ui-corner-all" ).addClass( "ui-corner-top" );
	      },
	      close: function() {
	        $( this ).removeClass( "ui-corner-top" ).addClass( "ui-corner-all" );
	      }
	    }).data( "ui-autocomplete" )._renderItem = function( ul, item ) {
			  return $( "<li>" )
			    .append( "<span class='proposition' >" + item.value + "</span>" )
			    .appendTo( ul );
			};
}


function hundleCovoitureur(){
	$("#covoitureurs").change(function(){
			var number = $("#covoitureurs").val();	
	var capacity = $("#vehiculeTypeSelect option:selected").val();
	if(capacity < number)
		toastr.warning("Vous avez dépassé la capacité du véhicule");
	});
}

function hundleCompareTime(){
	$('.date-fr').change(function(){

		var depart = $('#departureTime').val().replace('à','');
		var arrivee = $('#arrivalTime').val().replace('à','');;
		if(arrivee < depart){
			toastr.warning("Horaire d'arrivée doit être superieure à l'horaire' de départ")
		}
	});
}


function hundleLoginForm(){

    $('#login-form-link').click(function(e) {
		$("#login-form").delay(100).fadeIn(100);
 		$("#register-form").fadeOut(100);
		$('#register-form-link').removeClass('active');
		$(this).addClass('active');
		e.preventDefault();
	});
	$('#register-form-link').click(function(e) {
		$("#register-form").delay(100).fadeIn(100);
 		$("#login-form").fadeOut(100);
		$('#login-form-link').removeClass('active');
		$(this).addClass('active');
		e.preventDefault();
	});
}

function hundleValidateRequest(){
	 $("#searchBtn").click(function(){
		 
		 propositionCounter++;

		 demande.id = propositionCounter;
		 demande.max_price = $("#max_price").val();
		 demande.max_passeneger = $("#covoitureurs").val();
		 demande.max_delay = $("#deviation-time").val();
		 demande.dep_lon = itiniraire[0];
		 demande.dep_lat = itiniraire[1];
		 demande.arr_lon = itiniraire[2];
		 demande.arr_lat = itiniraire[3];
		 demande.vehiculeType = $("#vehiculeTypeSelect option:selected").text();
		 //demande.vehiculeType ="Citadine";
		 
		 $("#resultSuggestion").addClass("hidden");
		 $("#noSuggestion").addClass("hidden");
		 
		 sendRequest(demande);		 
		 
	 });
	 
}

function sendRequest(demande){
	demandes.push(demande);
	$.ajax( {
			 	beforeSend: function(xhrObj){
			        xhrObj.setRequestHeader("Content-Type","application/json");
			        xhrObj.setRequestHeader("Accept","application/json");
			    },
			    type:'Post',
			    url:mqServer +"/suggestions",       
			    //dataType: 'application/json',
			    data : JSON.stringify(demande),
			    success:function (responseData, textStatus, errorThrown) {
			    	
			    	if(responseData.demandeDistance!= undefined)
			    	{
			    		/******** proposition counter *****/
			    		
						 if(propositionCounter > 0){
						 	$(".notificationZone").removeClass("hidden");
						 	$("#propositionCounter").html(propositionCounter)
						 }

						 /**********************************/

			    		var proposition = { "id" : propositionCounter, "from" : $('#departureAddressInput').val(),"to" : $('#arrivalAddressInput').val(),"price" : responseData.max_price,"delay" : responseData.max_delay};
			    		propositions.push(proposition);
			    		
			    		buildResponseModal();

				    	$("#demandePrice").html(demande.max_price);
				    	$("#demandePassenger").html(demande.max_passeneger);
				    	$("#demandeDelay").html(demande.max_delay);
				    	
				    	
				    	$("#suggestionPrice").html(responseData.max_price);
				    	$("#suggestionPassenger").html(responseData.max_passenger);
				    	$("#suggestionDelay").html(responseData.max_delay);
				    	$("#suggestionVehicule").html(responseData.vehiculeType);
				    	$("#suggestionDistance").html(responseData.demandeDistance +  " Km");
				    	$("#suggestionDuration").html(responseData.demandeDuration + " min");
				    	
				    	$("#spinnerLoader").addClass("hidden");
				    	$("#resultSuggestion").removeClass("hidden");
				    	
				    	if(parseInt(responseData.max_passenger) <= parseInt(demande.max_passeneger))
				    		$("#suggestionPassenger").addClass("success");
				    	else
				    		$("#suggestionPassenger").addClass("warning");
				    	if(parseInt(responseData.max_price) <= parseInt(demande.max_price))
				    		$("#suggestionPrice").addClass("success");
				    	else
				    		$("#suggestionPrice").addClass("warning");
				    	if(parseInt(responseData.max_delay) <= parseInt(demande.max_delay))
				    		$("#suggestionDelay").addClass("success");
				    	else
				    		$("#suggestionDelay").addClass("warning");
				    }
			    	else{
			    		$("#spinnerLoader").addClass("hidden");
				    	$("#noSuggestion").removeClass("hidden");
			    	}
			    },error: function (responseData, textStatus, errorThrown) {
			    	toastr.warning("Erreur lors du calcul de votre itinéraire !");
		    	}
		 });
}

function resendRequest(demande){
	demandes.push(demande);
	$.ajax( {
			 	beforeSend: function(xhrObj){
			        xhrObj.setRequestHeader("Content-Type","application/json");
			        xhrObj.setRequestHeader("Accept","application/json");
			    },
			    type:'Post',
			    url:mqServer +"/suggestions",       
			    //dataType: 'application/json',
			    data : JSON.stringify(demande),
			    success:function (responseData, textStatus, errorThrown) {
			    	
			    	if(responseData.demandeDistance!= undefined)
			    	{

			    		var proposition = { "id" : demande.id, "from" : demande.from,"to" : demande.to,"price" : demande.max_price,"delay" : demande.max_delay};
			    		propositions.push(proposition);
			    		
			    		buildResponseModal();

				    	$("#demandePrice").html(demande.max_price);
				    	$("#demandePassenger").html(demande.max_passeneger);
				    	$("#demandeDelay").html(demande.max_delay);
				    	
				    	
				    	$("#suggestionPrice").html(responseData.max_price);
				    	$("#suggestionPassenger").html(responseData.max_passenger);
				    	$("#suggestionDelay").html(responseData.max_delay);
				    	$("#suggestionVehicule").html(responseData.vehiculeType);
				    	$("#suggestionDistance").html(responseData.demandeDistance +  " Km");
				    	$("#suggestionDuration").html(responseData.demandeDuration + " min");
				    	
				    	$("#spinnerLoader").addClass("hidden");
				    	$("#resultSuggestion").removeClass("hidden");
				    	
				    	if(parseInt(responseData.max_passenger) <= parseInt(demande.max_passeneger))
				    		$("#suggestionPassenger").addClass("success");
				    	else
				    		$("#suggestionPassenger").addClass("warning");
				    	if(parseInt(responseData.max_price) <= parseInt(demande.max_price))
				    		$("#suggestionPrice").addClass("success");
				    	else
				    		$("#suggestionPrice").addClass("warning");
				    	if(parseInt(responseData.max_delay) <= parseInt(demande.max_delay))
				    		$("#suggestionDelay").addClass("success");
				    	else
				    		$("#suggestionDelay").addClass("warning");
				    }
			    	else{
			    		$("#spinnerLoader").addClass("hidden");
				    	$("#noSuggestion").removeClass("hidden");
			    	}
			    },error: function (responseData, textStatus, errorThrown) {
			    	toastr.warning("Erreur lors du calcul de votre itinéraire !");
		    	}
		 });
}

function handlePropositionsList(){
	$('#propositionCounter').click(function(){
		$("#propositionsModal").modal();
	})
}


function buildResponseModal(){
	$('#responseListBody').html("");
	var content = "";
	
	propositions.forEach(function(element) {
	  content += '<div class="alert alert-info ">'+
              '<div class="col-lg-10">'+
              	  '<strong>'+element.id+'&nbsp;-&nbsp;</strong>'+
                  '<strong>De: </strong><span>'+ element.from+'</span>&nbsp;'+
                  '<strong>A :</strong><span>'+element.to+'</span>&nbsp;'+
                  'pour <strong class="btn-danger">'+element.price+'€</strong>'+
                  ' retard <strong class="btn-warning">'+element.delay+'</strong>'+
              '</div><div class="col-lg-2">'+
                  '<a  href="javascript:refuseProposition('+element.id+')" class="label label-danger">Refuser</a>'+
                  '<a  href="javascript:waitProposition('+element.id+')" class="label label-warning">Relancer</a>'+
                  '<a href="javascript:acceptProposition('+element.id+')" class="label label-success">Accepter</a>'+
              '</div></div>';
	});
	$('#responseListBody').html(content);
	
}

function refuseProposition(id){
	propositions = propositions.filter(function(el) {
	    return el.id !== id;
	});

	demandes = demandes.filter(function(el) {
	    return el.id !== id;
	});
	propositionCounter--;
	$('#propositionCounter').html(propositionCounter);
	buildResponseModal();
}
function waitProposition(id){
	propositions = propositions.filter(function(el) {
	    return el.id !== id;
	});
	 $.each(demandes,function(index,propo){
	        	if(propo.id==id)
	        	{
	        		sendRequest(propo);
	        	}	
	 });
	$("#propositionsModal").modal("hide");
	toastr.warning("La demande a été relancée");
}

function acceptProposition(id){
	propositions = propositions.filter(function(el) {
	    return el.id !== id;
	});
	demandes = demandes.filter(function(el) {
	    return el.id !== id;
	});
	
	propositionCounter--;
	$('#propositionCounter').html(propositionCounter);
	buildResponseModal();
	$("#propositionsModal").modal("hide");
	toastr.success("La demande a été validée, facturation en cours");
}