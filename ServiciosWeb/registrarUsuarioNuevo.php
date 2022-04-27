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

$usuario = $_POST["usuario"];
$password = password_hash($_POST["password"], PASSWORD_DEFAULT);


# Ejecutar la sentencia SQL
$resultado = mysqli_query($con, "INSERT INTO usuarios VALUES ('$usuario', '$password', '', '', 1)");

# Comprobar si se ha ejecutado correctamente
if (!$resultado) {
	echo 'Ha ocurrido algún error: ' . mysqli_error($con);
}else{
	echo 'Usuario nuevo añadido con éxito';
}
?>