<?php

$hostname= "localhost";
$user="id20591426_aamir";
$pass="pKOOq}}GXYn3[@X5";
$dbname="id20591426_cabxury";

$con= mysqli_connect($hostname, $user, $pass, $dbname);

$email=$_POST["email"];
$pass=$_POST["pass"];

$query= "select * FROM driver where email='".$email."' AND password='".$pass."'";

$sel=mysqli_query($con,$query);
$abc=mysqli_num_rows($sel);

if($abc>0){
	echo "Login successfully";
}
else{
	echo "Login Failed";
}
