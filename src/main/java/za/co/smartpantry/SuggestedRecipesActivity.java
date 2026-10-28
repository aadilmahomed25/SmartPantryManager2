package za.co.smartpantry;
import android.app.*;import android.os.Bundle;import android.content.Intent;import android.widget.*;import java.util.ArrayList;

public class SuggestedRecipesActivity extends Activity {
    DatabaseHelper db;
    @Override protected void onCreate(Bundle b){super.onCreate(b);db=new DatabaseHelper(this);render();}
    @Override protected void onResume(){super.onResume();if(db!=null)render();}
    void render(){
        LinearLayout root=Ui.page(this,"Suggested recipes");
        root.addView(Ui.text(this,"Only recipes whose every required ingredient is in your pantry are shown.",14));
        ArrayList<Recipe> recipes=db.getStrictMatches();
        if(recipes.isEmpty()) root.addView(Ui.text(this,"No recipes match your pantry yet. Add more ingredients and try again.",16));
        ScrollView scroll=new ScrollView(this);LinearLayout list=new LinearLayout(this);list.setOrientation(LinearLayout.VERTICAL);scroll.addView(list);
        for(Recipe r:recipes){Button b=Ui.button(this,r.name+"  ›");b.setOnClickListener(v->{Intent i=new Intent(this,RecipeDetailActivity.class);i.putExtra("recipe_id",r.id);startActivity(i);});list.addView(b);}
        root.addView(scroll,new LinearLayout.LayoutParams(-1,0,1));Ui.nav(this,root);setContentView(root);
    }
}
