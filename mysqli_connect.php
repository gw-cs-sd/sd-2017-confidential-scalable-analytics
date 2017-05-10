<?php

	DEFINE('DB_HOST', 'localhost');
	DEFINE('DB_USERNAME', 'root');
	DEFINE('DB_PASSWORD', 'huy458**9');
	DEFINE('DB_NAME', 'everify');

	$dbConnection = mysqli_connect(DB_HOST, DB_USERNAME, DB_PASSWORD, DB_NAME);

	if(!$dbConnection){
		die ('MySQL Error: ' . mysqli_connect_error());
	}

	mysqli_set_charset($dbConnection, "utf8");		

?>