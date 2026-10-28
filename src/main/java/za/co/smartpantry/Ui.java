package za.co.smartpantry;
import android.app.Activity;
import android.content.*;
import android.graphics.Typeface;
import android.view.*;
import android.widget.*;
import android.graphics.Color;

public class Ui {
    public static final int GREEN=Color.rgb(23,107,85), DARK=Color.rgb(16,74,59), GOLD=Color.rgb(229,169,61);
    public static LinearLayout page(Activity a,String title) {
        LinearLayout root=new LinearLayout(a); root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(20,18,20,18); root.setBackgroundColor(Color.rgb(247,249,246));
        TextView heading=new TextView(a); heading.setText(title); heading.setTextSize(26);
        heading.setTypeface(null,Typeface.BOLD); heading.setTextColor(DARK); heading.setPadding(0,0,0,12);
        root.addView(heading); return root;
    }
    public static Button button(Activity a,String text) {
        Button b=new Button(a); b.setText(text); b.setAllCaps(false); b.setTextColor(Color.WHITE);
        b.setBackgroundTintList(android.content.res.ColorStateList.valueOf(GREEN)); return b;
    }
    public static TextView text(Activity a,String value,int size) {
        TextView t=new TextView(a); t.setText(value); t.setTextSize(size); t.setTextColor(Color.rgb(40,50,45));
        t.setPadding(4,8,4,8); return t;
    }
    public static void nav(Activity a,LinearLayout root) {
        LinearLayout bar=new LinearLayout(a); bar.setOrientation(LinearLayout.HORIZONTAL);
        String[] labels={"Pantry","Suggestions","Settings"};
        Class<?>[] targets={MainActivity.class,SuggestedRecipesActivity.class,SettingsActivity.class};
        for(int i=0;i<labels.length;i++){ final Class<?> target=targets[i]; Button b=button(a,labels[i]); 
            LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(0,ViewGroup.LayoutParams.WRAP_CONTENT,1);
            p.setMargins(2,8,2,0); bar.addView(b,p); b.setOnClickListener(v->a.startActivity(new Intent(a,target))); }
        root.addView(bar);
    }
}
