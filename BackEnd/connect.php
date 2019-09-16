<?php

$db = "test";
$user = $_POST["user"];
$pass = $_POST["pass"];
$host = "192.168.64.2";
//change host address to localhost or current IP


$databaseConnection =  mysqli_connect($host, $user, $pass, $db);

if($databaseConnection)
{
 $q = "select * from user where user like '$user' and pass like '$pass'";
 $result = mysqli_query($databaseConnection, $q);
 if(mysqli_num_rows($result) > 0)
 {
   echo "login successful..!";
 }
else
 {
  echo "Conecction failed...";
 }
}
else
{
  echo "Not Connected...";
}

?>
