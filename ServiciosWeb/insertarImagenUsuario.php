<?php

$DB_SERVER="localhost"; #la direccion del servidor
$DB_USER="Xahernandez141"; #el usuario para la base de datos
$DB_PASS="***"; #la clave para este usuario
$DB_DATABASE="Xahernandez141_usuarios"; #la base de datos a la que hay que conectarse


# Se establece la conexión:
$con = mysqli_connect($DB_SERVER, $DB_USER, $DB_PASS, $DB_DATABASE);
#Comprobamos conexión
if (mysqli_connect_errno($con)) {
echo 'Error de conexion: ' . mysqli_connect_error();
exit();
}

$image = $_POST['imagen'];
$username = $_POST['username'];


$query = "UPDATE usuarios SET imagen = '$image' WHERE nombre = '$username'";
$result = mysqli_query($con,$query);

if (!$result) {
	echo 'Ha ocurrido algún error: ' . mysqli_error($con);
	exit;
}
else {
	echo 'TODO OK';
}

?>