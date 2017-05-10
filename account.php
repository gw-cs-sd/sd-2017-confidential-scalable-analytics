<!-- This page is using icons from font awesome (Font Awesome by Dave Gandy - http://fontawesome.io).  CDN: //maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css  -->
<?php
	session_start();
	if(isset($_SESSION['userId'])){
		$userType = "user";
		$userId = $_SESSION['userId'];
	} else if(isset($_SESSION['companyId'])){
		$userType = "company";
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
		<div class="row">
			<div class="col-xs-2 leftMenuBar topLeftMenuBar"><h1>eVERIFY</h1></div>
			<div class="col-xs-10 topRightMenuBar">
				<?php
					if($userType == "user"){
						$userResult = mysqli_query($dbConnection, "SELECT * FROM users WHERE user_id = " . $userId);
						$row = mysqli_fetch_array($userResult);
						$name = $row['first_name'] . " " . $row['last_name'];;
						echo "<h2 id='nameTitle'>" . $name . "</h2>";
					} else {
						$userResult = mysqli_query($dbConnection, "SELECT * FROM companies WHERE company_id = " . $companyId);
						$row = mysqli_fetch_array($userResult);
						$company = $row['company_name'];
						echo "<h2 id='nameTitle'>" . $company . "</h2>";
					}
				?>
			</div>
		</div>
		<div class="row mainRow">
			<div class="col-xs-2 leftMenuBar">
				<ul class="leftNav">
					<li class="activeMenuLine"><a class="activeMenuItem" href="account.php"><span><i class="fa fa-cog" aria-hidden="true"></i></span>ACCOUNT</a></li>
					<?php if($userType == "user"): ?>
						<li><a href="addVerification.php"><span><i class="fa fa-plus" aria-hidden="true"></i></span>NEW VERIFICATION</a></li>
						<li><a href="pendingVerifications.php"><span><i class="fa fa-file-text-o" aria-hidden="true"></i></span>PENDING VERIFICATIONS</a></li>
						<li><a href="approvedVerifications.php"><span><i class="fa fa-check" aria-hidden="true"></i></span>APPROVED VERIFICATIONS</a></li>
						<li><a href="declinedVerifications.php"><span><i class="fa fa-times" aria-hidden="true"></i></span>REJECTED VERIFICATIONS</a></li>
					<?php else: ?>
						<li><a href="newVerifications.php"><span><i class="fa fa-plus" aria-hidden="true"></i></span>NEW VERIFICATIONS</a></li>
						<li><a href="acceptedVerifications.php"><span><i class="fa fa-check" aria-hidden="true"></i></span>ACCEPTED VERIFICATIONS</a></li>
						<li><a href="rejectedVerifications.php"><span><i class="fa fa-times" aria-hidden="true"></i></span>REJECTED VERIFICATIONS</a></li>
					<?php endif; ?>
					<li><a href="logOut.php"><span><i class="fa fa-sign-out" aria-hidden="true"></i></span>LOG OUT</a></li>
				</ul>
			</div>
			<div class="col-xs-10 rightContent">
	
				<?php
					if($userType == "user"){
						$emailAddress = $row['email'];
						$joinDate = strtotime($row['join_date']);

						$pendingVerifications = mysqli_query($dbConnection, "SELECT COUNT(*) number FROM pending_verifications
																   WHERE pending_verifications.user_id = " . $userId);
						$pendingVerifications = mysqli_fetch_assoc($pendingVerifications);
						$pendingNumber = $pendingVerifications['number'];

						$acceptedVerifications = mysqli_query($dbConnection, "SELECT COUNT(*) number FROM approved_verifications
																   WHERE approved_verifications.user_id = " . $userId);
						$acceptedVerifications = mysqli_fetch_assoc($acceptedVerifications);
						$acceptedNumber = $acceptedVerifications['number'];

						$rejectedVerifications = mysqli_query($dbConnection, "SELECT COUNT(*) number FROM rejected_verifications
																   WHERE rejected_verifications.user_id = " . $userId);
						$rejectedVerifications = mysqli_fetch_assoc($rejectedVerifications);
						$rejectedNumber = $rejectedVerifications['number'];

						echo '<div id="userProfile">';

						echo '<h4 class="userProfileHeader">USER PROFILE</h4>';

						echo '<a class="userProfileLabel">User name: </a><a>' . $name . '</a><br />';
						echo '<a class="userProfileLabel">Email address: </a><a>' . $emailAddress . '</a><br />';
						echo '<a class="userProfileLabel">Member since: </a><a>' . date('M d, Y', $joinDate) . '</a><br />';

						echo '<h4 class="userTransactionsHeader">TRANSACTIONS</h4>';
						
						echo '<a class="userProfileLabel">Pending verifications: </a><a class="userProfileResult">' . $pendingNumber . '</a><br />';
						echo '<a class="userProfileLabel">Accepted verifications: </a><a class="userProfileResult">' . $acceptedNumber . '</a><br />';
						echo '<a class="userProfileLabel">Rejected verifications: </a><a class="userProfileResult">' . $rejectedNumber . '</a><br />';
						echo '</div>';
					} else {
						$emailAddress = $row['email'];
						$joinDate = strtotime($row['join_date']);

						$pendingVerifications = mysqli_query($dbConnection, "SELECT COUNT(*) number FROM pending_verifications
																   WHERE pending_verifications.company_id = " . $companyId);
						$pendingVerifications = mysqli_fetch_assoc($pendingVerifications);
						$pendingNumber = $pendingVerifications['number'];

						$acceptedVerifications = mysqli_query($dbConnection, "SELECT COUNT(*) number FROM approved_verifications
																   WHERE approved_verifications.company_id = " . $companyId);
						$acceptedVerifications = mysqli_fetch_assoc($acceptedVerifications);
						$acceptedNumber = $acceptedVerifications['number'];

						$rejectedVerifications = mysqli_query($dbConnection, "SELECT COUNT(*) number FROM rejected_verifications
																   WHERE rejected_verifications.company_id = " . $companyId);
						$rejectedVerifications = mysqli_fetch_assoc($rejectedVerifications);
						$rejectedNumber = $rejectedVerifications['number'];

						echo '<div id="userProfile">';

						echo '<h4 class="userProfileHeader">COMPANY PROFILE</h4>';

						echo '<a class="userProfileLabel">Company name: </a><a>' . $company . '</a><br />';
						echo '<a class="userProfileLabel">Email address: </a><a>' . $emailAddress . '</a><br />';
						echo '<a class="userProfileLabel">Member since: </a><a>' . date('M d, Y', $joinDate) . '</a><br />';
						
						echo '<h4 class="userTransactionsHeader">TRANSACTIONS</h4>';

						echo '<a class="userProfileLabel">Pending verifications: </a><a class="userProfileResult">' . $pendingNumber . '</a><br />';
						echo '<a class="userProfileLabel">Accepted verifications: </a><a class="userProfileResult">' . $acceptedNumber . '</a><br />';
						echo '<a class="userProfileLabel">Rejected verifications: </a><a class="userProfileResult">' . $rejectedNumber . '</a><br />';
						echo '</div>';
					}
				?>
				
			</div>
		</div>
	</div>
</body>
</html>