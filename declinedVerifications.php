<!-- This page is using icons from font awesome (Font Awesome by Dave Gandy - http://fontawesome.io).  CDN: //maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css  -->
<?php
	session_start();
	if(isset($_SESSION['userId'])){
		$userId = $_SESSION['userId'];
	} else {
		header('Location: index.html');
	}
	require_once('mysqli_connect.php');
?>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="stylesheet.css">
	<link href='https://fonts.googleapis.com/css?family=Ubuntu:400,500,700|PT+Sans|Yellowtail' rel='stylesheet' type='text/css'>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script>
	<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
	<title>eVerify</title>
</head>
<body>
	<div class="container-fluid">
		<div class="row">
			<div class="col-xs-2 leftMenuBar topLeftMenuBar"><h1>eVERIFY</h1></div>
			<div class="col-xs-10 topRightMenuBar">
				<?php

					$userResult = mysqli_query($dbConnection, "SELECT * FROM users WHERE user_id = " . $userId);
					$row = mysqli_fetch_array($userResult);
					$name = $row['first_name'] . " " . $row['last_name'];
					echo "<h2 id='nameTitle'>" . $name . "</h2>";
				?>
			</div>
		</div>
		<div class="row mainRow">
			<div class="col-xs-2 leftMenuBar">
				<ul class="leftNav">
					<li><a href="account.php"><span><i class="fa fa-cog" aria-hidden="true"></i></span>ACCOUNT</a></li>
					<li><a href="addVerification.php"><span><i class="fa fa-plus" aria-hidden="true"></i></span>NEW VERIFICATION</a></li>
					<li><a href="pendingVerifications.php"><span><i class="fa fa-file-text-o" aria-hidden="true"></i></span>PENDING VERIFICATIONS</a></li>
					<li><a href="approvedVerifications.php"><span><i class="fa fa-check" aria-hidden="true"></i></span>APPROVED VERIFICATIONS</a></li>
					<li class="activeMenuLine"><a class="activeMenuItem" href="declinedVerifications.php"><span><i class="fa fa-times" aria-hidden="true"></i></span>REJECTED VERIFICATIONS</a></li>
					<li><a href="logOut.php"><span><i class="fa fa-sign-out" aria-hidden="true"></i></span>LOG OUT</a></li>
				</ul>
			</div>
			<div class="col-xs-10 rightContent">
				<h4>REJECTED VERIFICATIONS</h4>
				<?php
						$rejectedVerifications = "SELECT * FROM rejected_verifications 
													INNER JOIN companies on companies.company_id=rejected_verifications.company_id
													INNER JOIN users on users.user_id=rejected_verifications.user_id
													WHERE rejected_verifications.user_id=" . $userId;

						$rejectedVerifications = mysqli_query($dbConnection, $rejectedVerifications);
						if($rejectedVerifications && mysqli_num_rows($rejectedVerifications)){
							echo 	"<table class='verificationsTable'> 
											<tr>
												<th>TRANSACTION ID</th>
												<th>DATE SUBMITTED</th>
												<th>DATE REJECTED</th>
												<th>REASON</th>
												<th>LENDER</th>
												<th>QUALIFYING BALANCE</th>
											</tr>";
							while($row = mysqli_fetch_array($rejectedVerifications)){
								$date = strtotime($row['date']);
								$dateUploaded = date('M d, Y', $date);
								$time = ltrim(date("h:i a", $date), 0);
								
								$actionDate = strtotime($row['action_date']);
								$dateRejected = date('M d, Y', $actionDate);
								$timeRejected = ltrim(date("h:i a", $actionDate), 0);
								
								$currentDate = date('M d, Y');

								if($currentDate == $dateUploaded){
									$date = "Today at " . $time;
								} else {
									$date = $dateUploaded;
								}
								if($currentDate == $dateRejected){
									$actionDate = "Today at " . $timeRejected;
								} else {
									$actionDate = $dateRejected;
								}
								echo "<tr>"; 
								echo "<td>" . $row['rejected_verification_id'] . "</td>" . 
									 "<td>" . $date . "</td>" . 
									 "<td>" . $actionDate . "</td>" . 
									 "<td>" . $row['reason'] . "</td>" .
									 "<td>" . $row['company_name'] . "</td>" .
									 "<td>$" . number_format($row['threshold']) . "</td>"; 
								echo "</tr>";
							}
							echo	"</table>"; 
						} else {
							echo "<p id='noPendingVerificationsNotice'>You have no rejected verifications.</p>";
						}
					?>
			</div>
		</div>
	</div>
</body>
</html>