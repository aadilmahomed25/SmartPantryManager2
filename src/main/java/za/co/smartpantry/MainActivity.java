package za.co.smartpantry;
import android.app.*; import android.content.*; import android.os.Bundle; import android.view.*; import android.widget.*;
import java.util.ArrayList;

public class MainActivity extends Activity {
    DatabaseHelper db; LinearLayout root, listBox;
    @Override protected void onCreate(Bundle b){super.onCreate(b); db=new DatabaseHelper(this); render();}
    @Override protected void onResume(){super.onResume(); if(db!=null) render();}
    void render(){
        root=Ui.page(this,"Smart Pantry Manager");
        root.addView(Ui.text(this,"Your ingredients, your next meal. Reduce waste by cooking with what you already have.",15));
        Button add=Ui.button(this,"+ Add ingredient"); add.setOnClickListener(v->startActivity(new Intent(this,IngredientActivity.class))); root.addView(add);
        Button suggestions=Ui.button(this,"Find recipes I can make"); suggestions.setOnClickListener(v->startActivity(new Intent(this,SuggestedRecipesActivity.class))); root.addView(suggestions);
        root.addView(Ui.text(this,"PANTRY ITEMS",18));
        ScrollView scroll=new ScrollView(this); listBox=new LinearLayout(this); listBox.setOrientation(LinearLayout.VERTICAL); scroll.addView(listBox);
        ArrayList<PantryItem> items=db.getPantry();
        if(items.isEmpty()) listBox.addView(Ui.text(this,"Your pantry is empty. Add ingredients to get started.",15));
        for(PantryItem item:items){
            LinearLayout row=new LinearLayout(this); row.setOrientation(LinearLayout.VERTICAL);
            row.setPadding(8,8,8,8); row.setBackgroundColor(0xFFFFFFFF);
            row.addView(Ui.text(this,item.name+"  •  "+item.quantity+" "+item.unit,17));
            if(item.expiry!=null && !item.expiry.trim().isEmpty()) row.addView(Ui.text(this,"Expiry: "+item.expiry,13));
            LinearLayout actions=new LinearLayout(this);
            Button edit=Ui.button(this,"Edit"); Button del=Ui.button(this,"Delete");
            edit.setOnClickListener(v->{Intent i=new Intent(this,IngredientActivity.class);i.putExtra("item_id",item.id);startActivity(i);});
            del.setOnClickListener(v->new AlertDialog.Builder(this).setTitle("Delete ingredient?")
                .setMessage("Remove "+item.name+" from your pantry?").setNegativeButton("Cancel",null)
                .setPositiveButton("Delete",(d,w)->{db.deleteItem(item.id);render();}).show());
            actions.addView(edit,new LinearLayout.LayoutParams(0,-2,1)); actions.addView(del,new LinearLayout.LayoutParams(0,-2,1)); row.addView(actions);
            LinearLayout.LayoutParams rp=new LinearLayout.LayoutParams(-1,-2); rp.setMargins(0,6,0,6); listBox.addView(row,rp);
        }
        root.addView(scroll,new LinearLayout.LayoutParams(-1,0,1)); Ui.nav(this,root); setContentView(root);
    }
}
