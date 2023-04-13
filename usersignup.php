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

$query= "insert into user(name,email,phone_number,password,age) values('".$name."','".$email."','".$phone."','".$pass."','".$age."')";
$ins=mysqli_query($con,$query);


if($ins)
{
	echo "Data inserted successfully";
}
else{
	echo "Data not inserted";
}

?>