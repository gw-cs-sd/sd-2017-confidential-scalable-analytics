<?php

	session_start();
	require_once('mysqli_connect.php');

	$username = $_POST['username'];
	$password = $_POST['password'];
	$loginType = $_POST['login'];

	$validLogin = false;

	if($loginType == "user"){
		$users = mysqli_query($dbConnection, "SELECT * FROM users");
	} else {
		$users = mysqli_query($dbConnection, "SELECT * FROM companies");
	}

	while($row = mysqli_fetch_array($users)){

		if($row['email'] == $username && $row['password'] == $password){
			$validLogin = true;
			if($loginType == "user"){
				unset($_SESSION['companyId']);
				$_SESSION['userId'] = $row['user_id'];
			} else {
				unset($_SESSION['userId']);
				$_SESSION['companyId'] = $row['company_id'];
			}
			$_SESSION['loggedIn'] = 'true';
			break;
		}
	}

	if($validLogin){
		header('Location: account.php');
	} else {
		header('Location: index.html');
	}

?>