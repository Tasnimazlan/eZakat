package com.example.e_zakat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.view.Menu;

public class MainActivity extends AppCompatActivity {

    private EditText etWeight, etPrice;
    private Spinner spinnerType;
    private Button btnCalculate;

    private final int type_Wear = 200;
    private final int type_Keep = 85;

    private final String Wear = "Wear";
    private final String Keep = "Keep";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String inWeight = sharedPref.getString("weight", "");
        String inPrice = sharedPref.getString("price", "");

        etWeight = findViewById(R.id.etWeight);
        etPrice = findViewById(R.id.etPrice);

        etWeight.setText(inWeight);
        etPrice.setText(inPrice);

        spinnerType = findViewById(R.id.spinnerType);

        btnCalculate = findViewById(R.id.btnCalculate);


        String[] items = new String[]{Keep, Wear};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spinnerType.setAdapter(adapter);

        btnCalculate.setOnClickListener(view -> {


            if (etWeight.getText().toString().length() == 0) {
                etWeight.setError("Please insert the weight of gold.");
                Toast.makeText(this, "Missing weight of gold", Toast.LENGTH_SHORT).show();
                return;
            }

            if (etPrice.getText().toString().length() == 0) {
                etPrice.setError("Please insert the current value of gold ");
                Toast.makeText(this, "Missing current value of gold", Toast.LENGTH_SHORT).show();
                return;

            }


            double weight = Double.parseDouble(etWeight.getText().toString());
            double price = Double.parseDouble(etPrice.getText().toString());

            int typeVal;
            String spinValue = spinnerType.getSelectedItem().toString();
            if (spinValue.equals(Keep)) {
                typeVal = type_Keep;
            } else if (spinValue.equals(Wear)) {
                typeVal = type_Wear;
            } else {
                typeVal = 0;
            }

            double totalValueOfGold = weight * price;
            double uruf = weight - typeVal;
            double zakatPayable = uruf <= 0 ? 0 : price * uruf;
            double totalZakat = zakatPayable * 0.025;

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Receipts");
            builder.setMessage("" +
                    "Total Value of Gold :RM " + totalValueOfGold +
                    "\nZakat Payable :RM " + zakatPayable +
                    "\nUruf :RM " + uruf +
                    "\nTotal Zakat :RM " + totalZakat);

            // add the buttons
            builder.setPositiveButton("Next", null);

            // create and show the alert dialog
            AlertDialog dialog = builder.create();
            dialog.show();

            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("weight", String.valueOf(weight));
            editor.putString("price", String.valueOf(price));
            editor.apply();
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.aboutus:
                Toast.makeText(this,"About Us",Toast.LENGTH_LONG).show();

                Intent intent_abtus=new Intent(this,AboutUs.class);
                startActivity(intent_abtus);

                break;

            case R.id.developerdetails:
                Toast.makeText(this,"Developer Details",Toast.LENGTH_LONG).show();

                Intent intent_devdet=new Intent(this,DeveloperDetails.class);
                startActivity(intent_devdet);

                break;


            case R.id.appversion:
                Toast.makeText(this,"This app is up to date",Toast.LENGTH_LONG).show();

                Intent intent_appver=new Intent(this,AppVersion.class);
                startActivity(intent_appver);

                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
