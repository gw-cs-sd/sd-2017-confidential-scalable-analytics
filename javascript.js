
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

	$("#closeDocumentImage").on("click", function(){
		$("#documentContainerHidden").css({"display":"none"});
	});

	$(".rightContent").click(function(){
		$("#companySuggestionBox").css({"display":"none"});
	});

	$("#uploadDocumentButton").click(function(){
		$("#image").click();
	});
		
	$("#image").on("change", function(){
		addOverlay();
		//$("#uploadDocumentSubmit").click();
		var file = $("#image").prop('files')[0];
		//ocrCall(file);
	});

	$("#submitVerificationButton").click(function(){
		if($("#verifier").val() && $("#threshold").val() && $("#bankList li").length){
			$("#bankList li").each(function(e){
				if($("#bankNames").val()){
					$("#bankNames").val($("#bankNames").val() + "," + $(this).text());
				} else {
					$("#bankNames").val($(this).text());
				}
				$("#newVerificationForm").submit();
			});
		} else {
			alert("Please fill in all fields and upload a document.");
			return false;
		}
	});

	$("#documentContainerHidden").on("click", "#closeDocumentView", function(){
		$("#documentContainerHidden").css({"display":"none"});
		removeImageUnderlay();
	});

	$(".imageUnderlay").on("click", function(){
		$("#documentContainerHidden").css({"display":"none"});
		removeImageUnderlay();
	});

	$("#addBankButton").on("click", function(){
		if($("#banks").val().trim()){
			var bank = $("#banks").val();
			$("#bankList").append("<li style='margin-left:16px;font-size:18px;color:#253240;'>" + bank + "</li>");
			$("#banks").val("");
		} else {
			alert("Please enter a bank name.");
		}
	});

});

function addOverlay(){
	$(".overlay").css({"display":"block"});
}

function removeOverlay(){
	$(".overlay").css({"display":"none"});
}

function addImageUnderlay(){
	$(".imageUnderlay").css({"display":"block"});
}

function removeImageUnderlay(){
	$(".imageUnderlay").css({"display":"none"});
}

function selectCompanySuggestion(company){
	$("#verifier").val(company);
	$("#companySuggestionBox").css({"display":"none"});
}

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
