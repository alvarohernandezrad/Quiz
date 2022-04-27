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

$username = $_POST["username"];
$puntos = $_POST["puntos"];
$longitud = $_POST["longitud"];
$latitud = $_POST["latitud"];
$flag = $_POST["flag"];


if($flag == '0'){
	# Ejecutar la sentencia SQL
	$resultado = mysqli_query($con, "INSERT INTO ranking (nombre, puntos) VALUES ('$username', $puntos)");
}
else{
	# Ejecutar la sentencia SQL
	$resultado = mysqli_query($con, "INSERT INTO ranking (nombre, puntos, longitud, latitud) VALUES ('$username', $puntos, $longitud, $latitud)");
}
# Comprobar si se ha ejecutado correctamente
if (!$resultado) {
	echo 'Ha ocurrido algún error: ' . mysqli_error($con);
}else{
	echo "Todo OK";
}

# Ejecutar la sentencia SQL
$resultado2 = mysqli_query($con, "UPDATE usuarios SET vidas = 0 WHERE nombre = '$username'");
if (!$resultado2) {
	echo 'Ha ocurrido algún error: ' . mysqli_error($con);
}
?>