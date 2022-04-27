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
$resultado = mysqli_query($con, "SELECT tokenFirebase FROM usuarios");

# Comprobar si se ha ejecutado correctamente
if (!$resultado) {
echo 'Ha ocurrido algún error: ' . mysqli_error($con);
}else{
	while($row = mysqli_fetch_assoc($resultado)){
		$tokens[] = $row["tokenFirebase"];
	}
	mysqli_free_result($resultado);
}

$cabecera= array(
	'Authorization: key=AAAA6nJkep0:APA91bGUJDRkBbDRl9_G5moaPE2MTuSaZzqVHoYrr1d3iZgsC8HxTAQiyDYRCV6OxUUEJf13xNVuAgez7O9xMEl9x1m87W2wK6tPfaaBqSuJMZ6GID8L4a0ymJ2PMHsGwc30YX75Hh25',
	'Content-Type: application/json'
);

$msg= array(
	'registration_ids' => $tokens,
	'notification' => array(
		'body' => 'Este es el texto de la notificación! ',
		'title' => 'Título de la notificación',
		'icon' => 'ic_stat_ic_notification'
	)
);

$msgJSON = json_encode($msg);

$ch = curl_init(); #inicializar el handler de curl
#indicar el destino de la petición, el servicio FCM de google
curl_setopt( $ch, CURLOPT_URL, 'https://fcm.googleapis.com/fcm/send');
#indicar que la conexión es de tipo POST
curl_setopt( $ch, CURLOPT_POST, true );
#agregar las cabeceras
curl_setopt( $ch, CURLOPT_HTTPHEADER, $cabecera);
#Indicar que se desea recibir la respuesta a la conexión en forma de string
curl_setopt( $ch, CURLOPT_RETURNTRANSFER, true );
#agregar los datos de la petición en formato JSON
curl_setopt( $ch, CURLOPT_POSTFIELDS, $msgJSON );
#ejecutar la llamada
$resultado= curl_exec( $ch );
#cerrar el handler de curl
curl_close( $ch );

if (curl_errno($ch)) {
print curl_error($ch);
}
echo $resultado;

?>
	
