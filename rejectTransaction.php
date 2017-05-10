<?php
	session_start();
	if(!isset($_SESSION['companyId'])){
		header('Location: index.html');
	} 
	require_once('mysqli_connect.php');
	
	if(isset($_REQUEST['id'])){
		require_once('mysqli_connect.php');

		$verificationId = $_REQUEST['id'];
		$verification = mysqli_query($dbConnection, "SELECT * FROM pending_verifications WHERE pending_verification_id=" . $verificationId);

		if($verification && mysqli_num_rows($verification)){

			$verificationRow = mysqli_fetch_array($verification);
			$id = $verificationRow['pending_verification_id'];
			$userId = $verificationRow['user_id'];
			$companyId = $verificationRow['company_id'];
			$threshold = $verificationRow['threshold'];
			$reason = 'Qualifying Balance';

			$addRejectedVerification = "INSERT INTO rejected_verifications VALUES (null, $userId, $companyId, $threshold, '" . $verificationRow['date'] . "', NOW(), '$reason')";
			$addRejectedVerification = mysqli_query($dbConnection, $addRejectedVerification);
			if($addRejectedVerification){
				$deleteVerification = mysqli_query($dbConnection, "DELETE FROM pending_verifications WHERE pending_verification_id=" . $verificationId);
			}
		} else {
			//Error
		}

	} else {
		//Error -- missing fields
		header('Location: account.php');
	}

?>
