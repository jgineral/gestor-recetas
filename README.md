# gestor-recetas
 
!Importante, para que devuelva El formato XML Correctamente hay que hacer un import de la clase IngredientResource.
 import views.IngredientResource
 
 En el archivo -> "\gestor-recetas\target\scala-2.13\twirl\main\views\xml\receta.template.scala"


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
            '"measure": "unidad"
	}
    
  
