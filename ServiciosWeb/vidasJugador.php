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



# Ejecutar la sentencia SQL
$resultado = mysqli_query($con, "SELECT vidas FROM usuarios WHERE nombre = '$usuario'");

# Comprobar si se ha ejecutado correctamente
if (!$resultado) {
echo 'Ha ocurrido algún error: ' . mysqli_error($con);
}else{
	if($resultado->num_rows>0){
		$fila = mysqli_fetch_row($resultado);
		echo $fila[0];
	}else{
		echo 0;
	}
}
?>