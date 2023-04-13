<?php

$hostname= "localhost";
$user="id20591426_aamir";
$pass="pKOOq}}GXYn3[@X5";
$dbname="id20591426_cabxury";

$con= mysqli_connect($hostname, $user, $pass, $dbname);

if($con){
	echo "Login successfully";
}
else{
	echo "Login Failed";
}

?>
