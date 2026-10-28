# Smart Pantry Manager: 5–7 minute demo plan

**0:00–1:00 | GitHub**
- Open your public repository.
- Explain the project and show genuine incremental commits as you developed it.
- Point out README and Java source structure.

**1:00–3:30 | Live app**
- Launch the app and show the pantry list.
- Add an ingredient, then edit its quantity/name and delete a test ingredient.
- Show validation by attempting to save a blank name or invalid quantity.
- Add all ingredients for one recipe, open Suggested Recipes and show it appears.
- Remove one required ingredient and show that recipe disappears.
- Close and reopen the app to demonstrate persistence.
- Open a suggested recipe and explain its ingredient list and preparation steps.
- Show Settings.

**3:30–6:15 | Explain code**
- DatabaseHelper: explain the pantry and recipes tables, SQLiteOpenHelper, and CRUD methods.
- MainActivity: explain how the pantry list is read and displayed.
- IngredientActivity: explain form validation and insert/update selection.
- SuggestedRecipesActivity / DatabaseHelper.getStrictMatches(): explain why every required ingredient must match.
- Explain how Intents pass item_id or recipe_id between Activities.

**6:15–6:45 | Database choice**
- Explain SQLite is local, simple to demonstrate, and persists data without internet or a backend.

Speak naturally in your own words. Keep the final video between 5 and 7 minutes and include your narration throughout.
