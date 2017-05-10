<?php
	
	session_start();
	if(isset($_REQUEST['clientData'])){
			$verifierAppData = $_REQUEST['clientData'];
			$clientName = $verifierAppData[0] . " " . $verifierAppData[1];
			$threshold = $verifierAppData[2];
			$balance = $verifierAppData[3];
			$userId = $verifierAppData[4];
			$verificationId = $verifierAppData[5];
	
			$jnlpFile = fopen("verifierApp.jnlp", "w");

			$jnlpContents =	'<?xml version="1.0" encoding="utf-8"?>
							<jnlp spec="1.0+" codebase="http://localhost/everify/" href="verifierApp.jnlp">
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
							          <jar href="paillierDecrypter.jar" />
							     </resources>
							     <application-desc main-class="verifierPackage.SwingApp">
							          <argument>' . $clientName . '</argument>
							          <argument>' . $threshold . '</argument>
							          <argument>' . $balance . '</argument>
							          <argument>' . $userId . '</argument>
							          <argument>' . $verificationId . '</argument>
							     </application-desc>
							     <update check="always" policy="prompt-update" />
							</jnlp>';

			fwrite($jnlpFile, $jnlpContents);
			fclose($jnlpFile);
			/*
				Redirect on AJAX return
				header('Location: verifierApp.jnlp');
			*/
	} else {
		header('Location: newVerifications.php');
	}

?>

