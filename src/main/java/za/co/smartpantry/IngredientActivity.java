package za.co.smartpantry;
import android.app.*; import android.os.Bundle; import android.view.ViewGroup; import android.widget.*;

public class IngredientActivity extends Activity {
    DatabaseHelper db; EditText name,quantity,unit,expiry; long itemId=-1;
    @Override protected void onCreate(Bundle b){super.onCreate(b);db=new DatabaseHelper(this);itemId=getIntent().getLongExtra("item_id",-1);
        LinearLayout root=Ui.page(this,itemId==-1?"Add ingredient":"Edit ingredient");
        name=field("Ingredient name"); quantity=field("Quantity"); unit=field("Unit (e.g. pieces, g, ml)"); expiry=field("Expiry date (optional, YYYY-MM-DD)");
        root.addView(name);root.addView(quantity);root.addView(unit);root.addView(expiry);
        if(itemId!=-1){PantryItem x=db.getItem(itemId);if(x!=null){name.setText(x.name);quantity.setText(String.valueOf(x.quantity));unit.setText(x.unit);expiry.setText(x.expiry==null?"":x.expiry);}}
        Button save=Ui.button(this,"Save ingredient");save.setOnClickListener(v->save());root.addView(save);
        Button cancel=Ui.button(this,"Cancel");cancel.setOnClickListener(v->finish());root.addView(cancel);setContentView(root);
    }
    EditText field(String hint){EditText e=new EditText(this);e.setHint(hint);e.setSingleLine(true);return e;}
    void save(){
        String n=name.getText().toString().trim(),q=quantity.getText().toString().trim(),u=unit.getText().toString().trim(),ex=expiry.getText().toString().trim();
        if(n.isEmpty()||q.isEmpty()||u.isEmpty()){new AlertDialog.Builder(this).setMessage("Ingredient name, quantity and unit are required.").setPositiveButton("OK",null).show();return;}
        double amount;try{amount=Double.parseDouble(q);}catch(Exception e){amount=-1;}
        if(amount<=0){new AlertDialog.Builder(this).setMessage("Quantity must be a number greater than zero.").setPositiveButton("OK",null).show();return;}
        if(itemId==-1)db.addItem(n,amount,u,ex);else db.updateItem(itemId,n,amount,u,ex);
        Toast.makeText(this,"Ingredient saved",Toast.LENGTH_SHORT).show();finish();
    }
}
