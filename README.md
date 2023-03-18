# gestor-recetas

Build for AWS
 
> sbt

> [gestor-recetas] $clean
> [gestor-recetas] $compile

> [gestor-recetas] $ dist

Renombrar archivo
> ./target/universal/gestor-recetas1.0-SNAPSHOT.zip

a

> ./target/universal/gestor-recetas-SNAPSHOT.zip


# Rutas

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

  Recibimos la receta con el id indicado (Int). La respuesta aceptada puede cambiarse añadiento la cabecera accept. Soportando XML o JSON si no se indica nada por defecto se   devuelve en formato JSON.
  
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
    
  
