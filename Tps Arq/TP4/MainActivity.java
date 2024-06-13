package com.example.tp4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void sendMessage(View view) {
        EditText inputTextNombrePersona = (EditText) findViewById(R.id.editTextNombrePersona);
        String nombrePersona = inputTextNombrePersona.getText().toString();
        llamarApiGenero("https://api.genderize.io?name=" + nombrePersona, Request.Method.GET);
        llamarApiEdad("https://api.agify.io/?name=" + nombrePersona, Request.Method.GET);
    }
    private void llamarApiGenero(String url, int httpVerb) {
        TextView campoTextoGenero = (TextView) findViewById(R.id.textGenero);

        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(httpVerb, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject reader = new JSONObject(response);
                    String genero = reader.getString("gender");

                    if (genero.equals("male")) {
                        genero = "El genero es: hombre";
                    } else {
                        genero = "El genero es: mujer";
                    }

                    campoTextoGenero.setText(genero);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                campoTextoGenero.setText("Ocurrió un error");
            }
        });
        queue.add(stringRequest);
    }

    private void llamarApiEdad(String url, int httpVerb) {
        TextView campoTextoEdad = (TextView) findViewById(R.id.textEdad);

        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(httpVerb, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject reader = new JSONObject(response);
                    String edad = reader.getString("age");

                    campoTextoEdad.setText("La edad es: " + edad);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                campoTextoEdad.setText("Ocurrió un error");
            }
        });
        queue.add(stringRequest);
    }
}