# gestor-recetas

El objetivo de esta aplicación REST es aprender un poco los fundamentos de las tecnologias al lado del Servidor, como despliegues en la nube, en este caso AWS.
La aplicación usa una base de datos local h2 para guardar la información de unas recetas(rutas explicadas más abajo), en este caso no he podido configurar una en AWS.

De momento hay activo un balanceador que estará disponible para la evaluación, más tarde seran eliminadas con terraform (terraform destroy).
Puedes encontrar la ruta y empezar a hacer peticiones en:
> http://gestor-recetas-1270346934.eu-south-2.elb.amazonaws.com/

Este cuenta con 3 instancias que usará el balnceador, más que suficiente para una aplicación de pruebas ya que no va a soportar carga masiva de usuarios. Cuenta con un grupo de segurdad HTTP abierto para lanzar peticiones por el puerto 80.
En este caso no cuenta con una base de datos en la nube, la gestiona la applicación con una base de datos en local.

La estrucuta se ha organizado por Controlladores, que en este caso controlla las peticiones de las recetas.
Modelos, que son nuestro modelos para la base de datos, ingredientes y receta.
Y Vistas, en las que se encuentras los recursos con los que se puede comunicar el Controller con los Modelos. Y las vistas XML para proporcionar una respuesta en XML si así se indicase en la cabecera accept. (Detallado en rutas.)


## Rutas

POST -> /recipes

  Dado el json con el siguiente formato. Incluiremos una receta en nuestra base de datos.

    {
    	"name": "Tostada con jamón",
    	"stars": 2,
    	"description": "Tostadas las de toda la vida",
    	"ingredients": [
      	 	{
            	"name": "Pan de molde",
            	"amount": 2.0,
            	"measure": "unidad"
        	}
    	]
	}
    
    
GET -> recipe/2

  Recibimos la receta con el id indicado (Int). La respuesta aceptada puede cambiarse añadiento la cabecera accept. Soportando XML o JSON indicandolo en la cabecera Accept.
  
  > Accept : application/xml
  
GET -> /recipes

  Recibimos todas las recetas.
  Habrá que indicar el maximo de elemento que queremos que devuelva y en que posición de inicio. Por defecto devuelve los 10 primeros elementos.
  Para indicar estos valores se tienen que enviar las siguientes cabeceras
  
  > Max-row : 10

  > Min-row : 0
  
DELETE -> /recipe/1

  Borra de la base de datos la receta con el id indicado. (Int)
 
PUT -> /recipe/1
  Actualiza una receta existente dado un JSON con el formato indicado en el POST /recipes
  
PUT -> /ingredient/recipe/1
  Añade un ingrediente a la receta indicada. Para ello habra que indicar un JSON con el siguiente formato. Siendo measure y amount obligatorios los 2 campos si uno existe.
   json.

    {
           "name": "Pan de molde",
           "amount": 2.0,
           "measure": "unidad"
	}
    
  

## Build for AWS
 
> sbt

> [gestor-recetas] $clean

> [gestor-recetas] $compile

> [gestor-recetas] $ dist

Renombrar archivo
> ./target/universal/gestor-recetas1.0-SNAPSHOT.zip

a

> ./target/universal/gestor-recetas-SNAPSHOT.zip
