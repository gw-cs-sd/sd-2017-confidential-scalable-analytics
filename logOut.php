<?php
	session_start();

	if(isset($_SESSION['userId'])){
		unset($_SESSION['userId']);
	} else if(isset($_SESSION['companyId'])){
		unset($_SESSION['companyId']);
	}

	unset($_SESSION['loggedIn']);
	header('Location: index.html');
?>