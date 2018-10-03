package br.com.samirrolemberg.synchro;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import br.com.samirrolemberg.synchro.activity.AdicionarFeedActivity;
import br.com.samirrolemberg.synchro.dialogs.DialogSobre;
import br.com.samirrolemberg.synchro.fragment.InicialFragment;
import br.com.samirrolemberg.synchro.util.C;

public class InicialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicial);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new InicialFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_inicial, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_adicionar_feed) {
            SharedPreferences preferences = C.getPreferences(C.getContext().getString(R.string.c_ADICIONAR_FEED));
            preferences.edit().remove(C.getContext().getString(R.string.FEED_CACHE)).commit();
            Intent intent = new Intent(this, AdicionarFeedActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.menu_sobre) {
            final View inflated = LayoutInflater.from(C.getContext()).inflate(R.layout.dialog_sobre, null);
            final View build = new DialogSobre(inflated).build();
            final AlertDialog dialog = new AlertDialog.Builder(InicialActivity.this)
                    .setView(build)
                    .setPositiveButton("Fechar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            dialog.dismiss();
                        }
                    })
                    .setCancelable(true)
                    .show();
        }

        return super.onOptionsItemSelected(item);
    }


}
