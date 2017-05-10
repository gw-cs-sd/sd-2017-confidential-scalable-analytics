<!-- This page is using icons from font awesome (Font Awesome by Dave Gandy - http://fontawesome.io).  CDN: //maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css  -->
<?php
	session_start();
	if(isset($_SESSION['companyId'])){
		$companyId = $_SESSION['companyId'];
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
	<script src="javascript.js"></script>
	<title>eVerify</title>
</head>
<body>
	<div class="container-fluid">
		<div class="imageUnderlay"></div>
		<div class="row">
			<div class="col-xs-2 leftMenuBar topLeftMenuBar"><h1>eVERIFY</h1></div>
			<div class="col-xs-10 topRightMenuBar">
				<?php
					$userResult = mysqli_query($dbConnection, "SELECT * FROM companies WHERE company_id = " . $companyId);
					$row = mysqli_fetch_array($userResult);
					$company = $row['company_name'];
					echo "<h2 id='nameTitle'>" . $company . "</h2>";
				?>
			</div>
		</div>
		<div class="row mainRow">
			<div class="col-xs-2 leftMenuBar">
				<ul class="leftNav">
					<li><a href="account.php"><span><i class="fa fa-cog" aria-hidden="true"></i></span>ACCOUNT</a></li>
					<li class="activeMenuLine"><a class="activeMenuItem" href="newVerifications.php"><span><i class="fa fa-plus" aria-hidden="true"></i></span>NEW VERIFICATIONS</a></li>
					<li><a href="acceptedVerifications.php"><span><i class="fa fa-check" aria-hidden="true"></i></span>ACCEPTED VERIFICATIONS</a></li>
					<li><a href="rejectedVerifications.php"><span><i class="fa fa-times" aria-hidden="true"></i></span>REJECTED VERIFICATIONS</a></li>
					<li><a href="logOut.php"><span><i class="fa fa-sign-out" aria-hidden="true"></i></span>LOG OUT</a></li>
				</ul>
			</div>
			<div class="col-xs-10 rightContent">
				
				
					<h4>NEW VERIFICATIONS</h4>
					<?php
						$pendingVerifications = "SELECT * FROM pending_verifications 
													INNER JOIN users ON pending_verifications.user_id=users.user_id
													INNER JOIN companies on companies.company_id=pending_verifications.company_id
													WHERE pending_verifications.company_id=" . $companyId;

						$pendingVerifications = mysqli_query($dbConnection, $pendingVerifications);
						if($pendingVerifications && mysqli_num_rows($pendingVerifications)){
							echo 	"<table class='verificationsTable'> 
											<tr>
												<th>DATE UPLOADED</th>
												<th>TRANSACTION ID</th>
												<th>CLIENT</th>
												<th>QUALIFYING BALANCE</th>
												<th>REJECT</th>
												<th>VERIFY</th>
											</tr>";
							while($row = mysqli_fetch_array($pendingVerifications)){
								$date = strtotime($row['date']);
								$dateUploaded = date('M d, Y', $date);
								$time = ltrim(date("h:i a", $date), 0);
								$currentDate = date('M d, Y');
								if($currentDate == $dateUploaded){
									$date = "Today at " . $time;
								} else {
									$date = $dateUploaded;
								}
								echo "<tr>"; 
								echo "<td>" . $date . "</td>" . "<td>" . $row['pending_verification_id'] . "</td>" . "<td>" . $row['first_name'] . " " . $row['last_name'] . "</td>" . 
									 "<td> $" . number_format($row['threshold']) . "</td>" . 
									 "<td><span class='rejectVerification'
									 	onclick='rejectVerification(" . $row['pending_verification_id'] . ");'><i class=\"fa fa-times\" aria-hidden=\"true\"></i></span></td>" . 
									 "<td><span class='approveVerification' 
									 	onclick='loadVerifierJNLP(\"" . $row['first_name'] . "\",\"" . $row['last_name'] . "\"," .
									 		$row['threshold'] . ",\"" . $row['balance'] . "\"," . $row['user_id'] . "," . $row['pending_verification_id']  . ");' >
										<i class=\"fa fa-check\" aria-hidden=\"true\"></i></span></td>"; 
								echo "</tr>";
							} 
							echo	"</table>"; 
						} else {
							echo "<p id='noPendingVerificationsNotice'>You have no new verifications.</p>";
						}
					?>
			</div>
		</div>
	</div>
</body>
</html>