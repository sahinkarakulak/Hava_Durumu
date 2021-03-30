package com.mrcaracal.havadurumu;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    SharedPreferences GET;
    SharedPreferences.Editor SET;

    private String url;
    private String str_ulke, alinan_sehir_ismi, str_gorsel ,str_derece, str_nem, str_enlem, str_boylam, str_ruzgar_hizi;

    private StringRequest stringRequest;

    private TextInputEditText edt_sehir_ismi;
    private TextView txt_ulke, txt_sehir, txt_derece, txt_nem, txt_enlem, txt_boylam, txt_ruzgar_hizi;
    private ImageView img_ara, img_resim;

    private void init() {

        edt_sehir_ismi = findViewById(R.id.edt_sehir_ismi);
        txt_ulke = findViewById(R.id.txt_ulke);
        txt_sehir = findViewById(R.id.txt_sehir);
        txt_derece = findViewById(R.id.txt_derece);
        img_ara = findViewById(R.id.img_ara);
        img_resim = findViewById(R.id.img_resim);
        txt_nem = findViewById(R.id.txt_nem);
        txt_enlem = findViewById(R.id.txt_enlem);
        txt_boylam = findViewById(R.id.txt_boylam);
        txt_ruzgar_hizi = findViewById(R.id.txt_ruzgar_hizi);

        GET = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SET = GET.edit();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        edt_sehir_ismi.setText(GET.getString("sehirIsmi", "Bingöl"));
        txt_ulke.setText(GET.getString("ulkeKodu", "TR"));
        txt_derece.setText(GET.getString("derece", "27°C"));

        arat();

        img_ara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arat();
            }
        });

    }

    public void arat() {

        if (edt_sehir_ismi.getText().toString().equals("")) {
            Toast.makeText(this, "Bir şehir ismi giriniz", Toast.LENGTH_SHORT).show();
        } else {

            alinan_sehir_ismi = edt_sehir_ismi.getText().toString().trim();
            url = "https://api.openweathermap.org/data/2.5/weather?q=" + alinan_sehir_ismi + "&appid="+"SENİN_KEY_BURAYA_GELECEK";

            stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    // API ÇAĞIR
                    try {
                        JSONObject jsonObject = new JSONObject(response);

                        // Ülke Kodu Bilgisi Al
                        JSONObject jsonObject1 = jsonObject.getJSONObject("sys");
                        str_ulke = jsonObject1.getString("country");
                        txt_ulke.setText("" + str_ulke);

                        // Şehir İsmi Al
                        String str_sehir = jsonObject.getString("name");
                        txt_sehir.setText("" + str_sehir);

                        // Hava Durumu Görseli Al
                        JSONArray jsonArray = jsonObject.getJSONArray("weather");
                        JSONObject jsonObject2 = jsonArray.getJSONObject(0);
                        str_gorsel = jsonObject2.getString("icon");
                        Picasso.get().load("https://openweathermap.org/img/wn/" + str_gorsel + "@2x.png").into(img_resim);

                        // Sıcaklık Bilgisini Al
                        DecimalFormat df = new DecimalFormat();
                        df.setMaximumFractionDigits(1);
                        JSONObject jsonObject3 = jsonObject.getJSONObject("main");
                        double dbl_sicaklik = jsonObject3.getDouble("temp");
                        double celcius = (dbl_sicaklik - 272.15);
                        str_derece = df.format(celcius);
                        txt_derece.setText(str_derece + "°C");

                        // Nem Bilgisi Al
                        str_nem = jsonObject3.getString("humidity");
                        txt_nem.setText(": "+str_nem+"%");

                        // Rüzgar Hızı Bilgisi Al
                        JSONObject jsonObject4 = jsonObject.getJSONObject("wind");
                        str_ruzgar_hizi = jsonObject4.getString("speed");
                        txt_ruzgar_hizi.setText(": "+str_ruzgar_hizi+" km/s");

                        // Koordinat Bilgilerini Al
                        JSONObject jsonObject5 = jsonObject.getJSONObject("coord");
                        str_enlem = jsonObject5.getString("lat");
                        txt_enlem.setText(": "+str_enlem);

                        str_boylam = jsonObject5.getString("lon");
                        txt_boylam.setText(": "+str_boylam);



                        Log.d(TAG, "onResponse: Çalıştı");

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d(TAG, "onResponse: Çalışmadı");
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
            requestQueue.add(stringRequest);

            SET.putString("sehirIsmi", alinan_sehir_ismi);
            SET.putString("ulkeKodu", str_ulke);
            SET.putString("havaGorseli", str_gorsel);
            SET.putString("derece", str_derece);
            SET.commit();

        }

    }

    public void fab(View view) {

        arat();

    }
}