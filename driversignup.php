<?php

$hostname= "localhost";
$user="id20591426_aamir";
$pass="pKOOq}}GXYn3[@X5";
$dbname="id20591426_cabxury";
$con= mysqli_connect($hostname, $user, $pass, $dbname) or die("Error in connection");

$email=$_POST["email"];
$pass=$_POST["pass"];
$name=$_POST["name"];
$phone=$_POST["phone_number"];
$age=$_POST["age"];
$license=$_POST["license_number"];
$cmodel=$_POST["car_model"];

$query= "insert into user(name,email,phone_number,password,license_number,car_model,age) values('".$name."','".$email."','".$phone."','".$pass."','".$license."','".$cmodel."','".$age."')";
$ins=mysqli_query($con,$query);


if($ins)
{
	echo "Data inserted successfully";
}
else{
	echo "Data not inserted";
}

?>
