package za.co.smartpantry;
import android.app.*;import android.os.Bundle;import android.widget.*;import android.content.*;
public class SettingsActivity extends Activity {
    @Override protected void onCreate(Bundle b){super.onCreate(b);
        LinearLayout root=Ui.page(this,"Settings");
        root.addView(Ui.text(this,"About Smart Pantry Manager",19));
        root.addView(Ui.text(this,"A local-first Java Android application that helps reduce food waste by suggesting recipes using only ingredients recorded in your pantry.",15));
        root.addView(Ui.text(this,"Database: SQLite on this device. Pantry records remain available after closing and reopening the app.",15));
        CheckBox alerts=new CheckBox(this);alerts.setText("Show expiring-soon alerts (preference demo)");
        alerts.setChecked(getPreferences(MODE_PRIVATE).getBoolean("expiry_alerts",false));
        alerts.setOnCheckedChangeListener((v,checked)->getPreferences(MODE_PRIVATE).edit().putBoolean("expiry_alerts",checked).apply());
        root.addView(alerts);
        Button home=Ui.button(this,"Back to pantry");home.setOnClickListener(v->startActivity(new Intent(this,MainActivity.class)));root.addView(home);
        Ui.nav(this,root);setContentView(root);
    }
}
