package com.example.quiz.database;



import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.quiz.models.Turno;


import java.util.ArrayList;

public class MiDB extends SQLiteOpenHelper {
    public MiDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE jugadores('nombre' VARCHAR(255) PRIMARY KEY NOT NULL, 'id' INTEGER NOT NULL, 'puntos' INTEGER not null)");
        sqLiteDatabase.execSQL("CREATE TABLE preguntas('id' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, 'hecha' INTEGER NOT NULL, 'pregunta' TEXT NOT NULL, 'respuesta1' VARCHAR(255) NOT NULL, 'respuesta2' VARCHAR(255) NOT NULL, 'respuesta3' VARCHAR(255) NOT NULL, 'respuesta4' VARCHAR(255) NOT NULL, 'tipo' VARCHAR(255) NOT NULL, 'correcta' INTEGER NOT NULL)");
        sqLiteDatabase.execSQL("insert into preguntas ('hecha', 'pregunta', 'respuesta1', 'respuesta2', 'respuesta3', 'respuesta4', 'tipo', 'correcta') values (0, '¿Quién fue el primer presidente de la democracia española tras el franquismo?', 'José María Aznar', 'Felipe González', 'Adolfo Suaréz', 'Leopoldo Calvo Sotelo', 'Historia', 2)");
        sqLiteDatabase.execSQL("insert into preguntas ('hecha', 'pregunta', 'respuesta1', 'respuesta2', 'respuesta3', 'respuesta4', 'tipo', 'correcta') values (0, '¿La invasión de qué fortaleza por parte de los revolucionarios es considerada como el punto de inicio de la Revolución Francesa?', 'La torre Eiffel', 'El palacio de Versailles', 'La Bastilla', 'El castillo de Chambord', 'Historia', 2)");
        sqLiteDatabase.execSQL("insert into preguntas ('hecha', 'pregunta', 'respuesta1', 'respuesta2', 'respuesta3', 'respuesta4', 'tipo', 'correcta') values (0, '¿En qué año el hombre pisó la Luna por primera vez?', '1969', '1970', '1968', '1972', 'Historia', 0)");
        sqLiteDatabase.execSQL("insert into preguntas ('hecha', 'pregunta', 'respuesta1', 'respuesta2', 'respuesta3', 'respuesta4', 'tipo', 'correcta') values (0, '¿Qué evento se considera que desencadenó la Primera Guerra Mundial?', 'El asesinato del archiduque Francisco Fernando de Habsburgo', 'El asesinato del primer canciller Alemán', 'Invasión de Alemania en Polonia', 'Hostilidades de Alemania con Rusia', 'Historia', 0)");
        sqLiteDatabase.execSQL("insert into preguntas ('hecha', 'pregunta', 'respuesta1', 'respuesta2', 'respuesta3', 'respuesta4', 'tipo', 'correcta') values (0, '¿A partir de qué suceso consideramos que termina la Edad Antigua y empieza la Edad Media?', 'La caída del imperio Romano de Occidente', 'El decubrimiento de América', 'La creación de la imprenta', 'La caída del imperio Otomano', 'Historia', 0)");
        sqLiteDatabase.execSQL("insert into preguntas ('hecha', 'pregunta', 'respuesta1', 'respuesta2', 'respuesta3', 'respuesta4', 'tipo', 'correcta') values (0, '¿Quién fue el primer presidente de Estados Unidos?', 'George Bush', 'George Washington', 'John Adams', 'Thomas Jefferson', 'Historia', 1)");
        sqLiteDatabase.execSQL("insert into preguntas ('hecha', 'pregunta', 'respuesta1', 'respuesta2', 'respuesta3', 'respuesta4', 'tipo', 'correcta') values (0, '¿Cuánto duró la Guerra de los Cien Años?', '100 años', '98 años', '103 años', '116 años', 'Historia', 3)");
        sqLiteDatabase.execSQL("insert into preguntas ('hecha', 'pregunta', 'respuesta1', 'respuesta2', 'respuesta3', 'respuesta4', 'tipo', 'correcta') values (0, '¿En qué año se creó la Organización de las Naciones Unidas?', '1945', '1936', '1950', '1946', 'Historia', 0)");
        sqLiteDatabase.execSQL("insert into preguntas ('hecha', 'pregunta', 'respuesta1', 'respuesta2', 'respuesta3', 'respuesta4', 'tipo', 'correcta') values (0, '¿Qué carabela no volvió del viaje en el que Colón arribó a América por primera vez?', 'Pinta', 'Niña ', 'Santa María', 'Colonizadora', 'Historia', 2)");
        sqLiteDatabase.execSQL("insert into preguntas ('hecha', 'pregunta', 'respuesta1', 'respuesta2', 'respuesta3', 'respuesta4', 'tipo', 'correcta') values (0, '¿Cuál es la narración épica más antigua de la historia, encontrada en tablillas de arcilla sobre las que se usó escritura cuneiforme?', 'La Epopeya de Gilgamesh', 'El Cantar del Mío Cid', 'La Odisea', 'La Eneida', 'Historia', 0)");
        sqLiteDatabase.execSQL("insert into preguntas ('hecha', 'pregunta', 'respuesta1', 'respuesta2', 'respuesta3', 'respuesta4', 'tipo', 'correcta') values (0, '¿Cómo se llama el filósofo español conocido por su desarrollo de la teoría del cierre categorial?', 'José Ortega y Gasset', 'Gustavo Bueno Martínez', 'Ramón Llull', 'Miguel de Unamuno', 'Historia', 1)");
        sqLiteDatabase.execSQL("insert into preguntas ('hecha', 'pregunta', 'respuesta1', 'respuesta2', 'respuesta3', 'respuesta4', 'tipo', 'correcta') values (0, '¿Cómo se apellidaban los dos exploradores que dieron la primera vuelta al mundo?', 'Magallanes-Colón', 'Colón-Elcano', 'Elcano-Vasco da Gama', 'Magallanes-Elcano', 'Historia', 3)");
        sqLiteDatabase.execSQL("insert into preguntas ('hecha', 'pregunta', 'respuesta1', 'respuesta2', 'respuesta3', 'respuesta4', 'tipo', 'correcta') values (0, '¿Qué faraón egipcio es conocido por haber intentado que su imperio pasase del politeísmo al monoteísmo a través del culto al dios Atón?', 'Amenhotep IV', 'Sanusret I', 'Tutankamón', 'Amenofis III', 'Historia', 0)");
        sqLiteDatabase.execSQL("insert into preguntas ('hecha', 'pregunta', 'respuesta1', 'respuesta2', 'respuesta3', 'respuesta4', 'tipo', 'correcta') values (0, '¿Qué emperador romano es conocido entre otras cosas por haber intentado someterse a operaciones de cambio de sexo?', 'Cesar Augusto', 'Nerón', 'Marco Aurelio Antonino Augusto', 'Maximiano', 'Historia', 2)");
        sqLiteDatabase.execSQL("insert into preguntas ('hecha', 'pregunta', 'respuesta1', 'respuesta2', 'respuesta3', 'respuesta4', 'tipo', 'correcta') values (0, '¿Qué filósofo de la Antigua Grecia creía que el elemento del que están compuestas todas las cosas es el agua?', 'Sócrates', 'Tales de Mileto', 'Aristóteles', 'Pitágoras', 'Historia', 1)");
        sqLiteDatabase.execSQL("insert into preguntas ('hecha', 'pregunta', 'respuesta1', 'respuesta2', 'respuesta3', 'respuesta4', 'tipo', 'correcta') values (0, '¿En qué idioma están escritos los manuscritos más antiguos pertenecientes a la parte de la Biblia que hoy conocemos como Nuevo Testamento?', 'Griego Antiguo', 'Latín', 'Árabe', 'Euskara', 'Historia', 0)");
        sqLiteDatabase.execSQL("insert into preguntas ('hecha', 'pregunta', 'respuesta1', 'respuesta2', 'respuesta3', 'respuesta4', 'tipo', 'correcta') values (0, '¿Quién era el gran ministro británico cuando la India Británica fue sacudida por la hambruna de Bengala?', 'Theresa May', 'Winston Churchill', 'Stanley Baldwin', 'Harold Wilson', 'Historia', 1)");
        sqLiteDatabase.execSQL("insert into preguntas ('hecha', 'pregunta', 'respuesta1', 'respuesta2', 'respuesta3', 'respuesta4', 'tipo', 'correcta') values (0, '¿Qué isla sirvió de prisión para Napoleón tras su derrota en la batalla de Waterloo?', 'Santa Felisa', 'Santa Clara', 'Santa Fe', 'Santa Elena', 'Historia', 3)");
        sqLiteDatabase.execSQL("insert into preguntas ('hecha', 'pregunta', 'respuesta1', 'respuesta2', 'respuesta3', 'respuesta4', 'tipo', 'correcta') values (0, '¿Quiénes fueron, según la leyenda, los dos hermanos fundadores de la ciudad de Roma?', 'Rómulo y Remo', 'Romino y Remo', 'Rómulo y Cesar', 'Rómulo y Ramo', 'Historia', 0)");
        sqLiteDatabase.execSQL("insert into preguntas ('hecha', 'pregunta', 'respuesta1', 'respuesta2', 'respuesta3', 'respuesta4', 'tipo', 'correcta') values (0, '¿Cómo se llama el fundador de la religión conocida como Movimiento de los Santos de los Últimos Días, asociados al Libro de Mormón?', 'Joseph Smith Jr.', 'Augusto Fernández', 'Christopher Adams', 'Carlos Díez', 'Historia', 0)");
        sqLiteDatabase.execSQL("insert into preguntas ('hecha', 'pregunta', 'respuesta1', 'respuesta2', 'respuesta3', 'respuesta4', 'tipo', 'correcta') values (0, '¿Qué líder militar africano se hizo conocido por usar una táctica de guerra llamada formación de cuernos de búfalo?', 'Shakir Williams', 'Akimba Solá', 'Momba Choló', 'Shaka Zulú', 'Historia', 3)");
        sqLiteDatabase.execSQL("insert into preguntas ('hecha', 'pregunta', 'respuesta1', 'respuesta2', 'respuesta3', 'respuesta4', 'tipo', 'correcta') values (0, '¿Para qué religión es especialmente importante el rey Haile Selassie I?', 'Cristiana', 'Judía', 'Musulmana', 'Rastafari', 'Historia', 3)");
        sqLiteDatabase.execSQL("insert into preguntas ('hecha', 'pregunta', 'respuesta1', 'respuesta2', 'respuesta3', 'respuesta4', 'tipo', 'correcta') values (0, '¿Cuál es el río más caudaloso del mundo?', 'Amazonas', 'Nilo', 'Ebro', 'Orinoco', 'Geografía', 0)");
        sqLiteDatabase.execSQL("insert into preguntas ('hecha', 'pregunta', 'respuesta1', 'respuesta2', 'respuesta3', 'respuesta4', 'tipo', 'correcta') values (0, '¿Cuál es el monte más alto del mundo?', 'K2', 'Everest', 'Kilimanjaro', 'Kenia', 'Geografía', 1)");
        sqLiteDatabase.execSQL("insert into preguntas ('hecha', 'pregunta', 'respuesta1', 'respuesta2', 'respuesta3', 'respuesta4', 'tipo', 'correcta') values (0, '¿Cuál es la lengua más hablada del mundo?', 'Español', 'Inglés', 'Chino Mandarín', 'Francés', 'Geografía', 2)");
        sqLiteDatabase.execSQL("insert into preguntas ('hecha', 'pregunta', 'respuesta1', 'respuesta2', 'respuesta3', 'respuesta4', 'tipo', 'correcta') values (0, '¿Cómo se llama la línea vertical imaginaria a partir del cual se miden las longitudes y que divide el mundo en dos mitades?', 'Ecuador', 'Meridiano de Greenwich', 'Meridiano Aztéca', 'Meridiano Central', 'Geografía', 1)");
        sqLiteDatabase.execSQL("insert into preguntas ('hecha', 'pregunta', 'respuesta1', 'respuesta2', 'respuesta3', 'respuesta4', 'tipo', 'correcta') values (0, '¿Dónde podemos encontrar la Casa Rosada?', 'Argentina', 'Estados Unidos', 'Chile', 'Colombia', 'Geografía', 0)");
        sqLiteDatabase.execSQL("insert into preguntas ('hecha', 'pregunta', 'respuesta1', 'respuesta2', 'respuesta3', 'respuesta4', 'tipo', 'correcta') values (0, '¿Entre qué países podemos encontrar el Estrecho de Bering?', 'Estados Unidos y Rusia', 'Japón y China', 'Francia y Reino Unido', 'España y Marruecos', 'Geografía', 0)");
        sqLiteDatabase.execSQL("insert into preguntas ('hecha', 'pregunta', 'respuesta1', 'respuesta2', 'respuesta3', 'respuesta4', 'tipo', 'correcta') values (0, '¿Cuál es la capital de Brasil?', 'Rio de Janeiro', 'Sao Paulo', 'Brasilia', 'Belo Horizonte', 'Geografía', 2)");
        sqLiteDatabase.execSQL("insert into preguntas ('hecha', 'pregunta', 'respuesta1', 'respuesta2', 'respuesta3', 'respuesta4', 'tipo', 'correcta') values (0, '¿Cuál es el país de mayor tamaño del mundo?', 'Estados Unidos', 'China', 'Brasil', 'Rusia', 'Geografía', 3)");
        sqLiteDatabase.execSQL("insert into preguntas ('hecha', 'pregunta', 'respuesta1', 'respuesta2', 'respuesta3', 'respuesta4', 'tipo', 'correcta') values (0, '¿Cuál es la capital de Nueva Zelanda?', 'Auckland', 'Wellington', 'Dunedin', 'Queenstown', 'Geografía', 0)");
        sqLiteDatabase.execSQL("insert into preguntas ('hecha', 'pregunta', 'respuesta1', 'respuesta2', 'respuesta3', 'respuesta4', 'tipo', 'correcta') values (0, '¿Qué cordillera separa Europa de Asia?', 'Montes Urales', 'Alpes', 'Montes Cárpatos', 'Apeninos', 'Geografía', 0)");
        sqLiteDatabase.execSQL("insert into preguntas ('hecha', 'pregunta', 'respuesta1', 'respuesta2', 'respuesta3', 'respuesta4', 'tipo', 'correcta') values (0, '¿Cuál es el nombre de la isla en la que vive la tribu no contactada menos conocida del mundo?', 'Sentinel del Norte', 'Santa María', 'Comino', 'Hopen', 'Geografía', 0)");
        sqLiteDatabase.execSQL("insert into preguntas ('hecha', 'pregunta', 'respuesta1', 'respuesta2', 'respuesta3', 'respuesta4', 'tipo', 'correcta') values (0, '¿Cuál es la capital de Filipinas?', 'Borácay', 'Manila', 'Mindanao', 'Cebú', 'Geografía', 1)");
        sqLiteDatabase.execSQL("insert into preguntas ('hecha', 'pregunta', 'respuesta1', 'respuesta2', 'respuesta3', 'respuesta4', 'tipo', 'correcta') values (0, '¿Además del alemán, el italiano y el francés, ¿qué otro idioma es hablado en ciertas regiones de Suiza?', 'Romache', 'Español', 'Rumano', 'Serbio', 'Geografía', 0)");
        sqLiteDatabase.execSQL("insert into preguntas ('hecha', 'pregunta', 'respuesta1', 'respuesta2', 'respuesta3', 'respuesta4', 'tipo', 'correcta') values (0, '¿Cuál es la capital de Mongolia?', 'Darjan', 'Hovd', 'Ulán Bator', 'Choybalsan', 'Geografía', 2)");
        sqLiteDatabase.execSQL("insert into preguntas ('hecha', 'pregunta', 'respuesta1', 'respuesta2', 'respuesta3', 'respuesta4', 'tipo', 'correcta') values (0, '¿Cuál es el país europeo del que provienen las familias de la mayor parte de los habitantes de Argentina?', 'España', 'Francia', 'Inglaterra', 'Italia', 'Geografía', 3)");
        sqLiteDatabase.execSQL("insert into preguntas ('hecha', 'pregunta', 'respuesta1', 'respuesta2', 'respuesta3', 'respuesta4', 'tipo', 'correcta') values (0, '¿En qué país se habla mayoritariamente el idioma tagálog?', 'Filipinas', 'Maldivas', 'India', 'Corea', 'Geografía', 0)");
        sqLiteDatabase.execSQL("insert into preguntas ('hecha', 'pregunta', 'respuesta1', 'respuesta2', 'respuesta3', 'respuesta4', 'tipo', 'correcta') values (0, '¿Cuál es la capital de Letonia?', 'Montreal', 'Riga', 'Valmiera', 'Bauska', 'Geografía', 1)");
        sqLiteDatabase.execSQL("insert into preguntas ('hecha', 'pregunta', 'respuesta1', 'respuesta2', 'respuesta3', 'respuesta4', 'tipo', 'correcta') values (0, '¿Cuál es la ciudad más poblada de África?', 'Nairobi', 'El Cairo', 'Lagos', 'Ciudad del Cabo', 'Geografía', 1)");
        sqLiteDatabase.execSQL("insert into preguntas ('hecha', 'pregunta', 'respuesta1', 'respuesta2', 'respuesta3', 'respuesta4', 'tipo', 'correcta') values (0, '¿Cómo se llama el plato de puchero típico de Hungría hecho a partir de carne, cebolla y pimientos?', 'Gulash', 'Meggyleves', 'Lángos', 'Halászlé', 'Geografía', 0)");
        sqLiteDatabase.execSQL("insert into preguntas ('hecha', 'pregunta', 'respuesta1', 'respuesta2', 'respuesta3', 'respuesta4', 'tipo', 'correcta') values (0, '¿Cuál es el nombre de la capital de Albania?', 'Pogradec', 'Permet', 'Durres', 'Tirana', 'Geografía', 3)");
        sqLiteDatabase.execSQL("insert into preguntas ('hecha', 'pregunta', 'respuesta1', 'respuesta2', 'respuesta3', 'respuesta4', 'tipo', 'correcta') values (0, '¿Qué cordillera recorre el norte de Marruecos, Túnez y Argelia?', 'Atlas', 'Nuba', 'Drakensberg', 'Tibesti', 'Geografía', 0)");
        sqLiteDatabase.execSQL("insert into preguntas ('hecha', 'pregunta', 'respuesta1', 'respuesta2', 'respuesta3', 'respuesta4', 'tipo', 'correcta') values (0, '¿Cómo se llama el plato típico de la cocina rusa elaborado con bolas de carne picada envueltas en masa?', 'Solianka', 'Pelmeni', 'Okroshka', 'Scchi', 'Geografía', 1)");
        sqLiteDatabase.execSQL("insert into preguntas ('hecha', 'pregunta', 'respuesta1', 'respuesta2', 'respuesta3', 'respuesta4', 'tipo', 'correcta') values (0, '¿Qué montaña es uno de los símbolos nacionales de Armenia?', 'Aghdagh Lerr', 'Aragats', 'Ararat', 'Azhdahak', 'Geografía', 2)");
        sqLiteDatabase.execSQL("insert into preguntas ('hecha', 'pregunta', 'respuesta1', 'respuesta2', 'respuesta3', 'respuesta4', 'tipo', 'correcta') values (0, '¿A quién interpretaba John Travolta en “Grease”?', 'Kenickie', 'Danny Zuko', 'Tom Chisum', 'Doody', 'Entretenimiento', 1)");
        sqLiteDatabase.execSQL("insert into preguntas ('hecha', 'pregunta', 'respuesta1', 'respuesta2', 'respuesta3', 'respuesta4', 'tipo', 'correcta') values (0, '¿Qué conocido cómico imitó a Hitler en la película “El Gran Dictador”?', 'Charles Chaplin', 'Mel Brooks', 'Richard Belzer', 'Jack Benny', 'Entretenimiento', 0)");
        sqLiteDatabase.execSQL("insert into preguntas ('hecha', 'pregunta', 'respuesta1', 'respuesta2', 'respuesta3', 'respuesta4', 'tipo', 'correcta') values (0, '¿Quién fue el director y a la vez protagonista de la película “Ciudadano Kane”?', 'Joseph Cotten', 'Everett Sloane', 'Orson Welles', 'Erskine Sanford', 'Entretenimiento', 2)");
        sqLiteDatabase.execSQL("insert into preguntas ('hecha', 'pregunta', 'respuesta1', 'respuesta2', 'respuesta3', 'respuesta4', 'tipo', 'correcta') values (0, '¿Quién fue el famoso cantante del grupo musical Queen?', 'Mick Jagger', 'Freddie Mercury', 'Michael Jackson', 'John Lennon', 'Entretenimiento', 1)");
        sqLiteDatabase.execSQL("insert into preguntas ('hecha', 'pregunta', 'respuesta1', 'respuesta2', 'respuesta3', 'respuesta4', 'tipo', 'correcta') values (0, '¿Cómo se llama la madre de Simba en la película de Disney “El Rey León”?', 'Sarabi', 'Nala', 'Banzai', 'Rafiki', 'Entretenimiento', 0)");
        sqLiteDatabase.execSQL("insert into preguntas ('hecha', 'pregunta', 'respuesta1', 'respuesta2', 'respuesta3', 'respuesta4', 'tipo', 'correcta') values (0, '¿Cómo se llama la ciudad donde se encuentra el Mago de Oz?', 'Ciudad Plata', 'Ciudad Rubí', 'Ciudad Diamante', 'Ciudad Esmeralda', 'Entretenimiento', 3)");
        sqLiteDatabase.execSQL("insert into preguntas ('hecha', 'pregunta', 'respuesta1', 'respuesta2', 'respuesta3', 'respuesta4', 'tipo', 'correcta') values (0, '¿Cuáles fueron los tres primeros componentes de “Los payasos de la tele”?', 'Gaby, Fofó, Miliki', 'Gaby, Foto, Miliki', 'Gaby, Foto, Milito', 'Gaby, Fofó, Mimiliki', 'Entretenimiento', 0)");
        sqLiteDatabase.execSQL("insert into preguntas ('hecha', 'pregunta', 'respuesta1', 'respuesta2', 'respuesta3', 'respuesta4', 'tipo', 'correcta') values (0, '¿De qué grupo es la canción “Smells like a teen spirit”?', 'Nirvana', 'Denver', 'Boston', 'Chicago', 'Entretenimiento', 0)");
        sqLiteDatabase.execSQL("insert into preguntas ('hecha', 'pregunta', 'respuesta1', 'respuesta2', 'respuesta3', 'respuesta4', 'tipo', 'correcta') values (0, '¿A qué banda de música metal pertenece el disco Master of Puppets?', 'Rammstein', 'Iron Maiden ', 'AC/DC', 'Metallica', 'Entretenimiento', 3)");
        sqLiteDatabase.execSQL("insert into preguntas ('hecha', 'pregunta', 'respuesta1', 'respuesta2', 'respuesta3', 'respuesta4', 'tipo', 'correcta') values (0, '¿Por qué película superventas de los años 90 es conocido el director y productor James Cameron?', 'Titanic', 'Lo que el viento se llevo', 'Salvar al soldado Ryan', 'Solo en casa', 'Entretenimiento', 0)");
        sqLiteDatabase.execSQL("insert into preguntas ('hecha', 'pregunta', 'respuesta1', 'respuesta2', 'respuesta3', 'respuesta4', 'tipo', 'correcta') values (0, '¿Qué película de Christopher Nolan narra la historia de un hombre con amnesia anterógrada, que no puede crear nuevos recuerdos a partir de lo que le va ocurriendo?', 'Interstellar', 'Memento', 'Tenet', 'Inception', 'Entretenimiento', 1)");
        sqLiteDatabase.execSQL("insert into preguntas ('hecha', 'pregunta', 'respuesta1', 'respuesta2', 'respuesta3', 'respuesta4', 'tipo', 'correcta') values (0, '¿Cómo se llama la protagonista de la saga de videojuegos The Legend of Zelda?', 'Link', 'Zelda', 'Epona', 'Impa', 'Entretenimiento', 0)");
        sqLiteDatabase.execSQL("insert into preguntas ('hecha', 'pregunta', 'respuesta1', 'respuesta2', 'respuesta3', 'respuesta4', 'tipo', 'correcta') values (0, '¿Qué banda de rock latinoamericana adoptó en sus inicios el nombre de Los Estereotipos?', 'Maná', 'Enanitos Verdes', 'Sonda Stereo', 'La cuca', 'Entretenimiento', 2)");
        sqLiteDatabase.execSQL("insert into preguntas ('hecha', 'pregunta', 'respuesta1', 'respuesta2', 'respuesta3', 'respuesta4', 'tipo', 'correcta') values (0, '¿Quién es el guionista de la novela gráfica Watchmen?', 'Alan Smith', 'Alan Moore', 'Michael Jefferson', 'Michael Affleck', 'Entretenimiento', 1)");
        sqLiteDatabase.execSQL("insert into preguntas ('hecha', 'pregunta', 'respuesta1', 'respuesta2', 'respuesta3', 'respuesta4', 'tipo', 'correcta') values (0, '¿A qué saga de películas pertenece el personaje conocido como Jack Sparrow?', 'Piratas del Caribe', 'Harry Potter', 'Star Wars', 'Star Trek', 'Entretenimiento', 0)");
        sqLiteDatabase.execSQL("insert into preguntas ('hecha', 'pregunta', 'respuesta1', 'respuesta2', 'respuesta3', 'respuesta4', 'tipo', 'correcta') values (0, '¿Cómo se llama el antagonista principal de la película de Disney El Rey León?', 'Simba', 'Scar', 'Rafiki', 'Mufasa', 'Entretenimiento', 1)");
        sqLiteDatabase.execSQL("insert into preguntas ('hecha', 'pregunta', 'respuesta1', 'respuesta2', 'respuesta3', 'respuesta4', 'tipo', 'correcta') values (0, '¿Qué actor, guionista y monologuista inglés guionizó la versión británica de la serie The Office?', 'Mackenzie Crook', 'Ricky Gervais', 'Martin Freeman', 'Patrick Baladi', 'Entretenimiento', 1)");
        sqLiteDatabase.execSQL("insert into preguntas ('hecha', 'pregunta', 'respuesta1', 'respuesta2', 'respuesta3', 'respuesta4', 'tipo', 'correcta') values (0, '¿En qué país transcurre la acción de la película Chappie?', 'Sudáfrica', 'Nueva Zelanda', 'Australia', 'Marruecos', 'Entretenimiento', 0)");
        sqLiteDatabase.execSQL("insert into preguntas ('hecha', 'pregunta', 'respuesta1', 'respuesta2', 'respuesta3', 'respuesta4', 'tipo', 'correcta') values (0, '¿Cómo se llama la práctica de quedarse tumbado boca abajo y manteniendo la rigidez del cuerpo, la cual se hizo viral a través de Internet mediante fotos y vídeos?', 'Planking', 'Planching', 'Streching', 'Worsting', 'Entretenimiento', 0)");
        sqLiteDatabase.execSQL("insert into preguntas ('hecha', 'pregunta', 'respuesta1', 'respuesta2', 'respuesta3', 'respuesta4', 'tipo', 'correcta') values (0, '¿Cuál es el primer videojuego de la saga Donkey Kong que tenía imágenes en 3D?', 'Donkey Kong 32', 'Donkey Kong Universe', 'Donkey Kong 64', 'Mario Bros 64', 'Entretenimiento', 2)");
        sqLiteDatabase.execSQL("insert into preguntas ('hecha', 'pregunta', 'respuesta1', 'respuesta2', 'respuesta3', 'respuesta4', 'tipo', 'correcta') values (0, '¿Cómo se llama el líder de los Autobots en Transformers?', 'Bumblebee', 'Optimus Prime', 'Iron Hide', 'Jazz', 'Entretenimiento', 1)");
        sqLiteDatabase.execSQL("insert into preguntas ('hecha', 'pregunta', 'respuesta1', 'respuesta2', 'respuesta3', 'respuesta4', 'tipo', 'correcta') values (0, '¿Qué serie producida por Netflix tiene como uno de sus antagonistas principales una entidad llamada Demogorgon?', 'Élite', 'Stranger Things', 'Dark', 'Travelers', 'Entretenimiento', 1)");
        sqLiteDatabase.execSQL("insert into preguntas ('hecha', 'pregunta', 'respuesta1', 'respuesta2', 'respuesta3', 'respuesta4', 'tipo', 'correcta') values (0, '¿Quién escribió la Ilíada y la Odisea?', 'Sócrates', 'Homero', 'Pirrón', 'Teodoro de Cirene', 'Arte y Literatura', 1)");
        sqLiteDatabase.execSQL("insert into preguntas ('hecha', 'pregunta', 'respuesta1', 'respuesta2', 'respuesta3', 'respuesta4', 'tipo', 'correcta') values (0, '¿Qué novela escribió Miguel de Cervantes y es considerada como una de los máximos exponentes de la literatura española y universal?', 'Don Quijote de la Mancha', 'El Lazarillo de Tormes', 'La Casa de Bernarda Alba', 'La Celestina', 'Arte y Literatura', 0)");
        sqLiteDatabase.execSQL("insert into preguntas ('hecha', 'pregunta', 'respuesta1', 'respuesta2', 'respuesta3', 'respuesta4', 'tipo', 'correcta') values (0, '¿Qué gran artista es conocido por haber pintado la Capilla Sixtina?', 'Mirón de Eléuteras', 'Augusto Rodin', 'Leonardo DaVinci', 'Miguel Angel', 'Arte y Literatura', 3)");
        sqLiteDatabase.execSQL("insert into preguntas ('hecha', 'pregunta', 'respuesta1', 'respuesta2', 'respuesta3', 'respuesta4', 'tipo', 'correcta') values (0, '¿Quién pintó el Guernica?', 'Picasso', 'Goya', 'Dalí', 'Miró', 'Arte y Literatura', 0)");
        sqLiteDatabase.execSQL("insert into preguntas ('hecha', 'pregunta', 'respuesta1', 'respuesta2', 'respuesta3', 'respuesta4', 'tipo', 'correcta') values (0, '¿De qué estilo arquitectónico es la catedral de Notre Dame?', 'Clásico', 'Gótico', 'Renacentista', 'Moderno', 'Arte y Literatura', 1)");
        sqLiteDatabase.execSQL("insert into preguntas ('hecha', 'pregunta', 'respuesta1', 'respuesta2', 'respuesta3', 'respuesta4', 'tipo', 'correcta') values (0, '¿De qué obra de Shakespeare forma parte el soliloquio “Ser o no ser, esa es la cuestión”?', 'Macbeth', 'La tempestad', 'Mucho ruido y pocas nueces', 'Hamlet', 'Arte y Literatura', 3)");
        sqLiteDatabase.execSQL("insert into preguntas ('hecha', 'pregunta', 'respuesta1', 'respuesta2', 'respuesta3', 'respuesta4', 'tipo', 'correcta') values (0, '¿Quién escribió “La colmena”?', 'Camilo José Cela', 'Arturo Pérez-Reverte', 'Carlos Ruiz Zafón', 'Quevedo', 'Arte y Literatura', 0)");
        sqLiteDatabase.execSQL("insert into preguntas ('hecha', 'pregunta', 'respuesta1', 'respuesta2', 'respuesta3', 'respuesta4', 'tipo', 'correcta') values (0, '¿Qué nombre tenía el caballo de Don Quijote de la Mancha?', 'Rociante', 'Rocinante', 'Desafiante', 'Relámpago', 'Arte y Literatura', 1)");
        sqLiteDatabase.execSQL("insert into preguntas ('hecha', 'pregunta', 'respuesta1', 'respuesta2', 'respuesta3', 'respuesta4', 'tipo', 'correcta') values (0, '¿En qué país fue considerado Gulliver un gigante durante sus viajes?', 'Liliput', 'Miniland', 'Smallvillage', 'Lilitown', 'Arte y Literatura', 0)");
        sqLiteDatabase.execSQL("insert into preguntas ('hecha', 'pregunta', 'respuesta1', 'respuesta2', 'respuesta3', 'respuesta4', 'tipo', 'correcta') values (0, '¿De qué país es originario el tipo de poesía conocido como haiku?', 'Corea', 'Mongolia', 'China', 'Japón', 'Arte y Literatura', 3)");
        sqLiteDatabase.execSQL("insert into preguntas ('hecha', 'pregunta', 'respuesta1', 'respuesta2', 'respuesta3', 'respuesta4', 'tipo', 'correcta') values (0, '¿Qué escritor hispanoparlante recibió el apodo de “el manco de Lepanto”?', 'Miguel de Cervantes', 'Miguel de Unamuno', 'Antonio Machado', 'Gustavo Adolfo Bécquer', 'Arte y Literatura', 0)");
        sqLiteDatabase.execSQL("insert into preguntas ('hecha', 'pregunta', 'respuesta1', 'respuesta2', 'respuesta3', 'respuesta4', 'tipo', 'correcta') values (0, '¿Cómo se llama el pintor noruego autor de la obra “El Grito”?', 'Vincent van Gogh', 'Edvard Munch', 'Claude Monet', 'Édouard Manet', 'Arte y Literatura', 1)");
        sqLiteDatabase.execSQL("insert into preguntas ('hecha', 'pregunta', 'respuesta1', 'respuesta2', 'respuesta3', 'respuesta4', 'tipo', 'correcta') values (0, '¿En qué otro idioma, además del castellano, escribió la novelista y poetisa Rosalía de Castro?', 'Gallego', 'Catalán', 'Euskara', 'Aragonés', 'Arte y Literatura', 0)");
        sqLiteDatabase.execSQL("insert into preguntas ('hecha', 'pregunta', 'respuesta1', 'respuesta2', 'respuesta3', 'respuesta4', 'tipo', 'correcta') values (0, '¿Qué personaje del universo literario de Harry Potter tiene una rata llamada Scabbers?', 'Ron Weasly', 'Harry Potter', 'Hermione Granger', 'Draco Malfoy', 'Arte y Literatura', 0)");
        sqLiteDatabase.execSQL("insert into preguntas ('hecha', 'pregunta', 'respuesta1', 'respuesta2', 'respuesta3', 'respuesta4', 'tipo', 'correcta') values (0, '¿De qué personaje ficticio estaba enamorado el Quijote?', 'Saladina', 'Dulcecita', 'Dulcinea', 'Dulceida', 'Arte y Literatura', 2)");
        sqLiteDatabase.execSQL("insert into preguntas ('hecha', 'pregunta', 'respuesta1', 'respuesta2', 'respuesta3', 'respuesta4', 'tipo', 'correcta') values (0, '¿Cuál es la velocidad de la luz?', '300.000.000 km/s', '400.000.000 m/s', '300 m/s', '3.000 km/h', 'Naturaleza y Ciencias', 0)");
        sqLiteDatabase.execSQL("insert into preguntas ('hecha', 'pregunta', 'respuesta1', 'respuesta2', 'respuesta3', 'respuesta4', 'tipo', 'correcta') values (0, '¿Qué gas nos protege de la radiación solar, concretamente de la radiación ultravioleta, formando una capa en la atmósfera?', 'Ozono', 'Hidrogeno', 'Nitrogeno', 'Oxigeno', 'Naturaleza y Ciencias', 0)");
        sqLiteDatabase.execSQL("insert into preguntas ('hecha', 'pregunta', 'respuesta1', 'respuesta2', 'respuesta3', 'respuesta4', 'tipo', 'correcta') values (0, '¿Cuál es el nombre técnico del miedo o fobia a las alturas?', 'Agorafobia', 'Homofobia', 'Agrafobia', 'Acrofobia', 'Naturaleza y Ciencias', 3)");
        sqLiteDatabase.execSQL("insert into preguntas ('hecha', 'pregunta', 'respuesta1', 'respuesta2', 'respuesta3', 'respuesta4', 'tipo', 'correcta') values (0, 'La fórmula E=mc2, ¿en qué teoría científica aparece?', 'Relatividad general', 'Relatividad atómica', 'Evolución', 'Gravitación universal', 'Naturaleza y Ciencias', 0)");
        sqLiteDatabase.execSQL("insert into preguntas ('hecha', 'pregunta', 'respuesta1', 'respuesta2', 'respuesta3', 'respuesta4', 'tipo', 'correcta') values (0, '¿Cuál es el ave de mayor envergadura que sigue viva actualmente?', 'Halcón cazado', 'Águila pescadora', 'Buitre común', 'Albatros', 'Naturaleza y Ciencias', 3)");
        sqLiteDatabase.execSQL("insert into preguntas ('hecha', 'pregunta', 'respuesta1', 'respuesta2', 'respuesta3', 'respuesta4', 'tipo', 'correcta') values (0, '¿Cuál es el principal tipo de célula que forma parte del sistema nervioso de humanos y otros animales?', 'Procariota', 'Neurona', 'Eucariota', 'Protista', 'Naturaleza y Ciencias', 1)");
        sqLiteDatabase.execSQL("insert into preguntas ('hecha', 'pregunta', 'respuesta1', 'respuesta2', 'respuesta3', 'respuesta4', 'tipo', 'correcta') values (0, '¿Cuál de estas no es una de las bases nitrogenadas del ADN?', 'Guanina', 'Adrenina', 'Citasina', 'Melanina', 'Naturaleza y Ciencias', 3)");
        sqLiteDatabase.execSQL("insert into preguntas ('hecha', 'pregunta', 'respuesta1', 'respuesta2', 'respuesta3', 'respuesta4', 'tipo', 'correcta') values (0, '¿Alrededor de qué planeta orbitan los satélites Ganímedes, Calisto, Ío y Europa?', 'Mercurio', 'Marte', 'Saturno', 'Júpiter', 'Naturaleza y Ciencias', 3)");
        sqLiteDatabase.execSQL("insert into preguntas ('hecha', 'pregunta', 'respuesta1', 'respuesta2', 'respuesta3', 'respuesta4', 'tipo', 'correcta') values (0, '¿Con qué denominación se conoce la línea dibujada por las estrellas Alnitak, Alnilam y Mintaka vistas desde nuestro planeta?', 'Cinturón de Orión', 'Osa Mayor', 'Osa Menor', 'Casiopea', 'Naturaleza y Ciencias', 0)");
        sqLiteDatabase.execSQL("insert into preguntas ('hecha', 'pregunta', 'respuesta1', 'respuesta2', 'respuesta3', 'respuesta4', 'tipo', 'correcta') values (0, '¿Cómo se llama la planta a partir de la cual suele ser elaborado el tequila?', 'Trigo', 'Agave', 'Cebada', 'Ajenjo', 'Naturaleza y Ciencias', 1)");
        sqLiteDatabase.execSQL("insert into preguntas ('hecha', 'pregunta', 'respuesta1', 'respuesta2', 'respuesta3', 'respuesta4', 'tipo', 'correcta') values (0, '¿Qué nombre recibe el sistema de transcripción fonética usado en el chino mandarín?', 'Chiniki', 'Romayi', 'Pinyin', 'Corini', 'Naturaleza y Ciencias', 2)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    // Obtener los nombres de los jugadores logrando un ArrayList
    public ArrayList<String> getNombresJugadores(){
        ArrayList<String> list = new ArrayList<>();
        Cursor cursor = getReadableDatabase().rawQuery("SELECT nombre FROM jugadores", (String[]) null);
        while(cursor.moveToNext()){
            list.add(cursor.getString(0));
        }
        cursor.close();
        return list;
    }

    // Obtener los nombres de los jugadores en un Array y por orden de id
    public String[] obtenerNombresJugadores(){
        String[] nombres = new String[this.numeroJugadores()];
        int i = 0;
        Cursor cursor = getReadableDatabase().rawQuery("SELECT nombre FROM jugadores ORDER BY id", (String[]) null);
        while(cursor.moveToNext()){
            nombres[i] = cursor.getString(0);
            i++;
        }
        cursor.close();
        return nombres;
    }

    // Obtener las puntuaciones actuales de los jugadores y por orden de id
    public int[] obtenerPuntuacionesJugadores(){
        int[] puntuaciones = new int[this.numeroJugadores()];
        int i = 0;
        Cursor cursor = getReadableDatabase().rawQuery("SELECT puntos FROM jugadores ORDER BY id", (String[]) null);
        while(cursor.moveToNext()){
            puntuaciones[i] = cursor.getInt(0);
            i++;
        }
        cursor.close();
        return puntuaciones;
    }


    public boolean existeJugador(String nickname){
        return getReadableDatabase().rawQuery("SELECT nombre FROM jugadores WHERE nombre='" + nickname + "'", (String[]) null).getCount() != 0;
    }

    public void insertarJugador(int id, String nickname){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO jugadores VALUES('"+nickname+"', "+id+", 0)");
        db.close();
    }

    public int numeroJugadores(){
       return getReadableDatabase().rawQuery("SELECT * FROM jugadores", (String[]) null).getCount();

    }

    public int numeroPreguntas(){
        return getReadableDatabase().rawQuery("SELECT * FROM preguntas", (String[]) null).getCount();
    }

    public void limpiarTablaJugadores(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM jugadores WHERE 1");
        db.close();
    }

    public void eliminarJugador(String name){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM jugadores WHERE nombre='" + name + "'");
        db.close();
    }

    public Turno recibirPreguntaYMarcarComoLeida(int idPregunta){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor;
        // Si idPregunta es 0, significa que es la primera pregunta de la partida. Si es algún otro número, se hace así por si se rota el móvil.
        if(idPregunta == 0){
             cursor = db.rawQuery("SELECT id, pregunta, respuesta1, respuesta2, respuesta3, respuesta4, tipo, correcta FROM preguntas WHERE hecha = 0 ORDER BY random() LIMIT 1", null);
        }else{
             cursor = db.rawQuery("SELECT id, pregunta, respuesta1, respuesta2, respuesta3, respuesta4, tipo, correcta FROM preguntas WHERE id = "+idPregunta+" ORDER BY random() LIMIT 1", null);
        }
        //cursor = db.rawQuery("SELECT id, pregunta, respuesta1, respuesta2, respuesta3, respuesta4, tipo, correcta FROM preguntas WHERE hecha = 0 ORDER BY random() LIMIT 1", null);
        cursor.moveToFirst();
        // Obtenemos el identificador de la pregunta para poder marcarla como leída después.
        int id = cursor.getInt(0);
        // Obtenemos los elementos necesarios para construir un Turno
        String pregunta = cursor.getString(1);
        ArrayList<String> respuestas = new ArrayList<>();
        for (int i = 2; i <= 5; i++) {
            respuestas.add(cursor.getString(i));
        }
        String tipo = cursor.getString(6);
        int correcta = cursor.getInt(7);
        Turno turno = new Turno(id, pregunta, respuestas, tipo, correcta);

        cursor.close();
        // Marcamos la pregunta como leída
        db.execSQL("UPDATE preguntas SET hecha = 1 WHERE id = '"+id+"'");
        db.close();
        return turno;
    }

    public void marcarPreguntasComoNoLeidas(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE preguntas SET hecha = 0");
        db.close();
    }

    public String nombreJugadorActual(int idJugadorActual){
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT nombre FROM jugadores WHERE id = " + idJugadorActual + "", null);
        cursor.moveToFirst();
        // Obtenemos el nombre del jugador con el id
        String nombre = cursor.getString(0);

        cursor.close();
        db.close();
        return nombre;
    }

    public void sumarPunto(int idJugadorAcierto){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE jugadores SET puntos = puntos+1 WHERE id = "+idJugadorAcierto+"");
        db.close();
    }

    public boolean comprobarGanador(){
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT MAX(puntos) FROM jugadores", null);
        cursor.moveToFirst();
        // Obtenemos el nombre del jugador con el id
        int puntos = cursor.getInt(0);
        cursor.close();
        db.close();

        return puntos == 7;
    }

    public int idUltimoJugador(){
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT MAX(id) FROM jugadores", null);
        cursor.moveToFirst();
        // Obtenemos el nombre del jugador con el id
        int id = cursor.getInt(0);
        cursor.close();
        db.close();
        return id;
    }

    public void reordenarIds(){
        // Obtenemos todos los ids en un arraylist
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT id FROM jugadores", null);
        ArrayList<Integer> listaIds = new ArrayList<>();
        // Mientras haya más ids
        while(cursor.moveToNext()){
            int id = cursor.getInt(0);
            listaIds.add(id);
        }
        // Una vez tenemos los IDs los vamos reordenando empezando por el 0
        for(int i = 0; i < listaIds.size(); i++){
            int idCambiar = listaIds.get(i);
            db.execSQL("UPDATE jugadores SET id = "+i+" WHERE id = "+idCambiar+"");
        }
        cursor.close();
        db.close();
    }

    // Obtener los nombres de los jugadores en un Array y por orden de puntos
    public String[] jugadoresPorPuntos(){
        String[] nombres = new String[this.numeroJugadores()];
        int i = 0;
        Cursor cursor = getReadableDatabase().rawQuery("SELECT nombre FROM jugadores ORDER BY puntos DESC", (String[]) null);
        while(cursor.moveToNext()){
            nombres[i] = cursor.getString(0);
            i++;
        }
        cursor.close();
        return nombres;
    }

    // Miramos si no hay preguntas sin hacer
    public boolean hayPreguntasDisponibles(){
        return getReadableDatabase().rawQuery("SELECT * FROM preguntas WHERE hecha = 0", (String[]) null).getCount() != 0;

    }
}