
$(document).ready(function(){

	$("#userLogin").on("click", function(){
		$("#userLogin").css({"display":"none"});
		$("#companyLogin").css({"display":"none"});
		$("#loginForm").css({"display":"block"});
		$("#loginType").val("user");
	});

	$("#companyLogin").on("click", function(){
		$("#userLogin").css({"display":"none"});
		$("#companyLogin").css({"display":"none"});
		$("#loginForm").css({"display":"block"});
		$("#loginType").val("company");
	});

});

function loadDynamicJNLP(){
	$.ajax({
		url: 'dynamicJNLP.php',
        contentType: false,
        processData: false,
		success: function(){
			window.location.href = "paillierApp.jnlp";
		}
	});
}

function loadVerifierJNLP(firstName, lastName, threshold, balance, userId, verificationId){
	var parameters = [firstName, lastName, threshold, balance, userId, verificationId];
	$.ajax({
		url: 'verifierJNLP.php',
		data: {clientData : parameters},
		type: 'POST',
		success: function(){
			window.location.href = "verifierApp.jnlp";
		}
	});
}

function rejectVerification(pendingVerificationId){
	var reject = confirm("Are you sure you want to reject this verification?");
	if(reject == true){
		var pendingId = pendingVerificationId;
		$.ajax({
			url: 'rejectTransaction.php',
			data: {id : pendingId},
			type: 'POST',
			success: function(){
				window.location.href = "rejectedVerifications.php";
			}
		});
	} else {
		return false;
	}
}
