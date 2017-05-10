<?php
	
	session_start();

	if(isset($_SESSION['userId'])){
		require_once('mysqli_connect.php');
		$userId = $_SESSION['userId'];
		$userResult = mysqli_query($dbConnection, "SELECT * FROM users WHERE user_id = " . $userId);
		$row = mysqli_fetch_array($userResult);
		$name = $row['first_name'] . " " . $row['last_name'];
			
		$jnlpFile = fopen("paillierApp.jnlp", "w");

		$jnlpContents =	'<?xml version="1.0" encoding="utf-8"?>
						<jnlp spec="1.0+" codebase="http://localhost/everify/" href="paillierApp.jnlp">
						     <information>
						          <title>Paillier Encrypter</title>
						          <vendor>eVERIFY</vendor>
						          <homepage href="http://localhost/everify/" />
						     </information>
						     <security>
						          <all-permissions/>
						     </security>
						     <resources>
						          <j2se version="1.7+" />
						          <jar href="paillierEncrypter.jar" />
						     </resources>
						     <application-desc main-class="clientPackage.SwingApp">
						          <argument>' . strval($userId) . '</argument>
						          <argument>' . $name . '</argument>
						     </application-desc>
						     <update check="always" policy="prompt-update" />
						</jnlp>';

		fwrite($jnlpFile, $jnlpContents);
		fclose($jnlpFile);
		/*
			Redirect on AJAX return
			header('Location: paillierApp.jnlp');
		*/
	} else {
		header('Location: addVerification.php');
	}

?>

