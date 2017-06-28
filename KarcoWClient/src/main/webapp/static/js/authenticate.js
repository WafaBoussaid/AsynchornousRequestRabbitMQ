var server = "http://localhost:9999/";

$(document).ready(function()
{
  hundleLoginForm();
  hundleLogin();
});

function hundleLogin(){
	$("#login-submit").click(function(){
	var user = {};
	user.id = 55;
	user.Role = "user";
	user.Name = "wafa";
	user.Email = $("#email").val();
	user.Password = $("#password").val();  

   var url = server + '/user/authenticate?email=' + user.Email + '&password=' +user.Password;
	//appel du web service recuperation de type vehicule
    $.ajax( {
		    type:'Post',
		    headers: { 
		        'Accept': 'application/json',
		        'Content-Type': 'application/json' 
		    },
		    url: url,       
		    dataType: 'json',
		    data : 	JSON.stringify({ Email : user.Email, Password : user.Password}),
		    success:function (responseData, textStatus, errorThrown) {
		    		if(responseData.id > 0)
		    			window.location.replace("/index.html");
		    		else
		    			toastr.error("Email ou password erroné")
			    },
	    	error: function (responseData, textStatus, errorThrown) {
	   		    toastr.error("Authentification indisponible")
	    	}
	
    	})
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





