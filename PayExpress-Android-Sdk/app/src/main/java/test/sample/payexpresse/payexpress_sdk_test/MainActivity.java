package test.sample.payexpresse.payexpress_sdk_test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.util.HashMap;
import java.util.Objects;

import sdk.payexpresse.com.PCallback;
import sdk.payexpresse.com.PayExpresse;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) this.findViewById(R.id.buy);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("click", "click on button");
                HashMap<String,Object> params = new HashMap<>();
                params.put("item_id", 2);

                HashMap<String, Object> themeOptions = new HashMap<>();

                themeOptions.put("pageTextPrimaryColor", "#333");

                new PayExpresse(MainActivity.this)
                        .setRequestTokenUrl("https://sample.payexpresse.com/paiement.php")
                        .setParams(params)
                        .setCallback(new PCallback() {
                            @Override
                            public void onResult(Result result) {
                                if(result == Result.SUCCESS)
                                {
                                    Toast.makeText(MainActivity.this, "Paiement Effectuer", Toast.LENGTH_SHORT).show();
                                }
                                else if(result == Result.CANCEL)
                                {
                                    Toast.makeText(MainActivity.this, "Vous avez annulez le paiement", Toast.LENGTH_SHORT).show();
                                }
                                else if(result == Result.ERROR)
                                {
                                    Toast.makeText(MainActivity.this, "Erreur lors du paiement", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setThemeOptions(themeOptions)
                        .send();
            }
        });





    }

    public void send(View v){

    }
}
