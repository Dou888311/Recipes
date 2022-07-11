# Recipe

* The project is a RESTful API and is an implementation of the [Recipes](https://hyperskill.org/projects/180) project
  from [hyperskill.org](https://www.jetbrains.com/ru-ru/academy/)

## About

* HTTP Basic is used for authentication
* H2 has been used as the database
* The endpoints are the following:
1. `POST /api/register`: for registration users in program
   ```json
   {
      "email": "Cook_Programmer@somewhere.com",
      "password": "RecipeInBinary"
   }
   ```

2. `POST /api/recipe/new` with basic Authentication (HTTP Basic) of already registered users - email and password: 
    ```json
    {
       "email": "Cook_Programmer@somewhere.com",
       "password": "RecipeInBinary"
    }
    ```
   and request body:
    ```json
      {
          "name": "Fresh Mint Tea",
          "category": "beverage",
          "description": "Light, aromatic and refreshing beverage, ...",
          "ingredients": ["boiled water", "honey", "fresh mint leaves"],
          "directions": ["Boil water", "Pour boiling hot water into a mug", "Add fresh mint leaves", "Mix and let the mint leaves seep for 3-5 minutes", "Add honey and mix again"]
      }
    ```
   stores the recipe in the database and return its id:
    ```json
    {
        "id": 1
    }
    ```

3. `GET /api/recipe/{recipe_id}` with Authentication (HTTP Basic) of already registered users: returns recipe as json:
    ```json
    {
        "name": "Fresh Mint Tea",
        "category": "beverage",
        "date": "2022-02-25T11:12:08.832742",
        "description": "Light, aromatic and refreshing beverage, ...",
        "ingredients": [
            "boiled water",
            "honey",
            "fresh mint leaves"
        ],
        "directions": [
            "Boil water",
            "Pour boiling hot water into a mug",
            "Add fresh mint leaves",
            "Mix and let the mint leaves seep for 3-5 minutes",
            "Add honey and mix again"
        ]
    }
    ```
4. `PUT /api/recipe/{recipe_id}` with Authentication (HTTP Basic): update recipe if current user added this recipe. Example request body:
    ```json
      {
          "name": "Warming Ginger Tea",
          "category": "beverage",
          "description": "Ginger tea is a warming drink for cool weather, ...",
          "ingredients": ["1 inch ginger root, minced", "1/2 lemon, juiced", "1/2 teaspoon manuka honey"],
          "directions": ["Place all ingredients in a mug and fill with warm water (not too hot so you keep the beneficial honey compounds in tact)", "Steep for 5-10 minutes", "Drink and enjoy"]
      }
    ```
5. `DELETE /api/recipe/` with Authentication (HTTP Basic): removes recipe by id if current user is the owner.

6. `GET /api/recipe/search?category={category_name}` or
   `GET /api/recipe/search?name={name}` searches for all recipes for given request parameters.
