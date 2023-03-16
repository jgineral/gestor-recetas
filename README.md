# gestor-recetas
 
!Importante, para que devuelva El formato XML Correctamente hay que hacer un import de la clase IngredientResource.
 import views.IngredientResource
 
 En el archivo -> "\gestor-recetas\target\scala-2.13\twirl\main\views\xml\receta.template.scala"


# Rutas

POST -> /recipes
  Dado el json con el siguiente formato
json.

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
    
