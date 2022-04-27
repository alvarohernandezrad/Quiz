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


# Ejecutar la sentencia SQL
$resultado = mysqli_query($con, "SELECT * FROM ranking ORDER BY puntos DESC LIMIT 5");

# Comprobar si se ha ejecutado correctamente
if (!$resultado) {
echo 'Ha ocurrido algún error: ' . mysqli_error($con);
}else{
	while($row = mysqli_fetch_assoc($resultado)){
		$nombres[] = $row["nombre"];
		$puntos[] = $row["puntos"];
		$longitud[] = $row["longitud"];
		$latitud[] = $row["latitud"];
	}
	mysqli_free_result($resultado);
	echo json_encode($nombres);
	echo " ";
	echo json_encode($puntos);
	echo " ";
	echo json_encode($longitud);
	echo " ";
	echo json_encode($latitud);
}
?>