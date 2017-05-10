<?php
	
	session_start();
	if(!isset($_SESSION['userId'])){
		header('Location: index.html');
	} 
	if(isset($_POST['verifier']) && isset($_POST['threshold']) && isset($_POST['cipherBankBalances']) && 
		isset($_POST['N']) && isset($_POST['cipherThreshold']) && isset($_POST['userId']) && isset($_POST['mask'])){
		require_once('mysqli_connect.php');
		//Threshold number of banks.
		$verifier = $_POST['verifier'];
		$threshold = (int)$_POST['threshold'];
		$cipherBankBalances = $_POST['cipherBankBalances'];
		$N = $_POST['N'];
		$cipherThreshold = $_POST['cipherThreshold'];
		$userId = (int)$_POST['userId'];
		$mask = $_POST['mask'];
		
		$eVerifyParameters = $cipherBankBalances . " " . $N . " " . $cipherThreshold . " " . $mask;
		
		$result = exec("java Paillier_eVerify " . $eVerifyParameters); // Input: Ciphertext list of bank balances, N, cipherthreshold and mask.
		//Sums cipher balances, subtracts threshold and returns result

		$verifierId = mysqli_query($dbConnection, "SELECT company_id FROM companies WHERE company_name='" . $verifier . "'");
		if($verifierId && mysqli_num_rows($verifierId)){
			$verifierId = mysqli_fetch_array($verifierId);
			$verifierId = $verifierId['company_id'];
		} else {
			$verifierId = 0;
		}

		$addVerification = "INSERT INTO pending_verifications VALUES (null, $userId, $verifierId, $threshold, '$result', NOW())";
		$addVerification = mysqli_query($dbConnection, $addVerification);
		if($addVerification){
			$pendingId = mysqli_insert_id($dbConnection);
			$documentId = 0;
			echo strval($pendingId);
		} else {
			//Error message
		}

	} else {
		//Error -- missing fields
		header('Location: account.php');
	}

?>
