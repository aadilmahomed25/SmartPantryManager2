package za.co.smartpantry;
import android.app.*;import android.os.Bundle;import android.widget.*;
public class RecipeDetailActivity extends Activity {
    @Override protected void onCreate(Bundle b){super.onCreate(b);DatabaseHelper db=new DatabaseHelper(this);
        long id=getIntent().getLongExtra("recipe_id",-1);Recipe r=db.getRecipe(id);
        LinearLayout root=Ui.page(this,r==null?"Recipe not found":r.name);
        if(r!=null){
            root.addView(Ui.text(this,"REQUIRED INGREDIENTS",18));
            for(String ingredient:r.ingredientArray())root.addView(Ui.text(this,"• "+ingredient.trim(),16));
            root.addView(Ui.text(this,"PREPARATION METHOD",18));root.addView(Ui.text(this,r.steps,16));
        } else root.addView(Ui.text(this,"This recipe is no longer available.",15));
        Button back=Ui.button(this,"Back to suggestions");back.setOnClickListener(v->finish());root.addView(back);setContentView(root);
    }
}
