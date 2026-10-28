package za.co.smartpantry;

import android.content.*;
import android.database.Cursor;
import android.database.sqlite.*;
import java.text.Normalizer;
import java.util.*;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME="smart_pantry.db";
    private static final int DB_VERSION=1;
    public DatabaseHelper(Context context) { super(context, DB_NAME, null, DB_VERSION); }

    @Override public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE pantry(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, quantity REAL NOT NULL, unit TEXT NOT NULL, expiry TEXT)");
        db.execSQL("CREATE TABLE recipes(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, ingredients TEXT NOT NULL, steps TEXT NOT NULL)");
        seedRecipes(db);
    }
    @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { }

    private void seedRecipes(SQLiteDatabase db) {
        String[][] data = {
            {"Tomato Egg Scramble","egg,tomato,onion","1. Chop tomato and onion. 2. Cook in a pan. 3. Add beaten eggs and scramble until cooked."},
            {"Cheese Omelette","egg,cheese,milk","1. Beat eggs with a splash of milk. 2. Cook gently. 3. Add cheese, fold and serve."},
            {"Banana Pancakes","banana,egg,flour,milk","1. Mash banana. 2. Mix with egg, flour and milk. 3. Fry small pancakes until golden."},
            {"Garlic Pasta","pasta,garlic,oil,salt","1. Boil pasta. 2. Warm chopped garlic in oil. 3. Toss pasta with garlic oil and salt."},
            {"Tomato Pasta","pasta,tomato,onion,garlic,oil","1. Boil pasta. 2. Cook onion, garlic and tomato in oil. 3. Mix sauce with pasta."},
            {"Potato Hash","potato,onion,oil,salt","1. Dice potato and onion. 2. Fry in oil until tender and browned. 3. Season and serve."},
            {"Chicken Rice Bowl","chicken,rice,onion,oil,salt","1. Cook rice. 2. Cook chicken thoroughly with onion. 3. Serve chicken over rice."},
            {"Simple Fried Rice","rice,egg,carrot,peas,oil","1. Scramble egg. 2. Add carrot and peas. 3. Stir-fry with cooked rice."},
            {"Apple Oat Porridge","oats,milk,apple","1. Simmer oats in milk. 2. Dice and add apple. 3. Cook until creamy."},
            {"Peanut Banana Toast","bread,peanut butter,banana","1. Toast bread. 2. Spread peanut butter. 3. Top with sliced banana."},
            {"Tuna Sandwich","bread,tuna,mayonnaise","1. Drain tuna. 2. Mix with mayonnaise. 3. Place filling between bread slices."},
            {"Carrot Soup","carrot,potato,onion,water,salt","1. Chop vegetables. 2. Simmer in water until soft. 3. Blend carefully and season."},
            {"Spinach Cheese Toast","bread,spinach,cheese,butter","1. Cook spinach briefly. 2. Put spinach and cheese on bread. 3. Grill until melted."},
            {"Yoghurt Fruit Bowl","yoghurt,banana,apple","1. Slice fruit. 2. Add yoghurt to a bowl. 3. Mix fruit through yoghurt."},
            {"Bean Tomato Stew","beans,tomato,onion,garlic,oil","1. Cook onion and garlic in oil. 2. Add tomato and beans. 3. Simmer until thick."},
            {"Lemon Garlic Chicken","chicken,lemon,garlic,oil,salt","1. Mix lemon, garlic, oil and salt. 2. Coat chicken. 3. Cook fully through."},
            {"Corn Fritters","corn,egg,flour,milk,oil","1. Mix corn, egg, flour and milk. 2. Spoon into a lightly oiled pan. 3. Cook both sides."},
            {"Cucumber Tomato Salad","cucumber,tomato,lemon,salt","1. Chop cucumber and tomato. 2. Dress with lemon. 3. Season and toss."},
            {"Mushroom Toast","bread,mushroom,butter,salt","1. Slice mushrooms. 2. Cook in butter until tender. 3. Serve on toasted bread."},
            {"Quick Cheese Pasta","pasta,cheese,milk,salt","1. Boil pasta. 2. Warm milk and melt in cheese. 3. Stir through pasta and season."}
        };
        for (String[] r : data) {
            ContentValues cv=new ContentValues(); cv.put("name",r[0]); cv.put("ingredients",r[1]); cv.put("steps",r[2]);
            db.insert("recipes",null,cv);
        }
    }

    public long addItem(String name,double quantity,String unit,String expiry) {
        ContentValues cv=new ContentValues(); cv.put("name",name.trim()); cv.put("quantity",quantity);
        cv.put("unit",unit.trim()); cv.put("expiry",expiry);
        return getWritableDatabase().insert("pantry",null,cv);
    }
    public int updateItem(long id,String name,double quantity,String unit,String expiry) {
        ContentValues cv=new ContentValues(); cv.put("name",name.trim()); cv.put("quantity",quantity);
        cv.put("unit",unit.trim()); cv.put("expiry",expiry);
        return getWritableDatabase().update("pantry",cv,"id=?",new String[]{String.valueOf(id)});
    }
    public int deleteItem(long id) { return getWritableDatabase().delete("pantry","id=?",new String[]{String.valueOf(id)}); }

    public ArrayList<PantryItem> getPantry() {
        ArrayList<PantryItem> list=new ArrayList<>();
        try(Cursor c=getReadableDatabase().rawQuery("SELECT id,name,quantity,unit,expiry FROM pantry ORDER BY name COLLATE NOCASE",null)) {
            while(c.moveToNext()) list.add(new PantryItem(c.getLong(0),c.getString(1),c.getDouble(2),c.getString(3),c.getString(4)));
        }
        return list;
    }
    public PantryItem getItem(long id) {
        try(Cursor c=getReadableDatabase().rawQuery("SELECT id,name,quantity,unit,expiry FROM pantry WHERE id=?",new String[]{String.valueOf(id)})) {
            if(c.moveToFirst()) return new PantryItem(c.getLong(0),c.getString(1),c.getDouble(2),c.getString(3),c.getString(4));
        } return null;
    }
    public ArrayList<Recipe> getRecipes() {
        ArrayList<Recipe> list=new ArrayList<>();
        try(Cursor c=getReadableDatabase().rawQuery("SELECT id,name,ingredients,steps FROM recipes ORDER BY name",null)) {
            while(c.moveToNext()) list.add(new Recipe(c.getLong(0),c.getString(1),c.getString(2),c.getString(3)));
        } return list;
    }
    public Recipe getRecipe(long id) {
        try(Cursor c=getReadableDatabase().rawQuery("SELECT id,name,ingredients,steps FROM recipes WHERE id=?",new String[]{String.valueOf(id)})) {
            if(c.moveToFirst()) return new Recipe(c.getLong(0),c.getString(1),c.getString(2),c.getString(3));
        } return null;
    }

    public ArrayList<Recipe> getStrictMatches() {
        ArrayList<Recipe> matches=new ArrayList<>();
        Set<String> available=new HashSet<>();
        for(PantryItem item:getPantry()) if(item.quantity>0) available.add(normalize(item.name));
        for(Recipe recipe:getRecipes()) {
            boolean qualifies=true;
            for(String required:recipe.ingredientArray()) {
                if(!available.contains(normalize(required))) { qualifies=false; break; }
            }
            if(qualifies) matches.add(recipe);
        }
        return matches;
    }
    // Handles case, accents, whitespace, and simple English plurals.
    public static String normalize(String value) {
        String s=Normalizer.normalize(value.trim().toLowerCase(Locale.ROOT),Normalizer.Form.NFD)
                .replaceAll("\\p{M}+","").replaceAll("\\s+"," ");
        if(s.endsWith("ies") && s.length()>4) s=s.substring(0,s.length()-3)+"y";
        else if(s.endsWith("oes") && s.length()>4) s=s.substring(0,s.length()-2);
        else if(s.endsWith("s") && !s.endsWith("ss") && s.length()>3) s=s.substring(0,s.length()-1);
        return s;
    }
}
