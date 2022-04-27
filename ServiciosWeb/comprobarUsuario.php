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
$password = $_POST["password"];
$token = $_POST["token"];


# Ejecutar la sentencia SQL
$resultado = mysqli_query($con, "SELECT password FROM usuarios WHERE nombre = '$usuario'");

# Comprobar si se ha ejecutado correctamente
if (!$resultado) {
echo 'Ha ocurrido algún error: ' . mysqli_error($con);
}else{
	if($resultado->num_rows>0){
		
		$fila = mysqli_fetch_row($resultado);
		echo password_verify($password, $fila[0]);
		# Ejecutar la sentencia SQL
		if(password_verify($password, $fila[0]) != '0'){
			$resultado2 = mysqli_query($con, "UPDATE usuarios SET tokenFirebase = '$token' WHERE nombre = '$usuario'");

			# Comprobar si se ha ejecutado correctamente
			if (!$resultado2) {
				echo 'Ha ocurrido algún error: ' . mysqli_error($con);
			}
		}
	}else{
		echo 0;
	}
}





?>