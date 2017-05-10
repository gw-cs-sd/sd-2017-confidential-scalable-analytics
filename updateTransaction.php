<?php
	session_start();
	if(!isset($_SESSION['companyId'])){
		header('Location: index.html');
	} 
	require_once('mysqli_connect.php');

	if(isset($_POST['result']) && isset($_POST['transactionId'])){
		$result = $_POST['result'];
		$verificationId = $_POST['transactionId'];
		$verification = mysqli_query($dbConnection, "SELECT * FROM pending_verifications WHERE pending_verification_id=" . $verificationId);
		if($verification && mysqli_num_rows($verification)){
			$verificationRow = mysqli_fetch_array($verification);
			$id = $verificationRow['pending_verification_id'];
			$userId = $verificationRow['user_id'];
			$companyId = $verificationRow['company_id'];
			$threshold = $verificationRow['threshold'];
		} 
		if($result == "PASS"){
			$updateVerification = "INSERT INTO approved_verifications VALUES ($id, $userId, $companyId, 'PASS', $threshold, '" . $verificationRow['date'] . "', NOW())";
		} else if($result == "FAIL") {
			$reason = 'Insufficient Funds';
			$updateVerification = "INSERT INTO rejected_verifications VALUES ($id, $userId, $companyId, $threshold, '" . $verificationRow['date'] . "', NOW(), '$reason')";
		}
		
		$updateVerification = mysqli_query($dbConnection, $updateVerification);
		if($updateVerification){
			$deleteVerification = mysqli_query($dbConnection, "DELETE FROM pending_verifications WHERE pending_verification_id=" . $verificationId);
		} else {
			//Error
		}

	} else {
		//Error -- missing fields
		header('Location: account.php');
	}

?>
