package com.example.wh098;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    EditText et;
    TextView tv;
    String url="api.openweathermap.org/data/2.5/weather?q={city name}&appid={API key}";
    String apikey="b85f714208db61cc33587fe6e48bc350";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et=findViewById(R.id.et);
        tv=findViewById(R.id.tv);

    }

    public void getweather(view v){
        Retrofit retrofit =new Retrofit.Builder()
                .baseUrl("https!//api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        weatherapi myapi=retrofit.create(weatherapi.class);
        Call<Example> exampleCall=myapi.getweather(et.getText().toString().trim(),apikey);
        exampleCall.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                if(response.code()==404) {
                    Toast.makeText(MainActivity.this,"Please Enter a  vaild City",Toast.LENGTH_LONG).show();

                }
                else if(!(response.isSuccessful())){
                    Toast.makeText(MainActivity.this,response.code(),Toast.LENGTH_LONG).show();

                }
                Example mydata=response.body();
                Main main=mydata.getMain();
                Double temp=main.getTemp();
                Integer temperature=(int)(temp -273.15);
                tv.setText(String.valueOf(temperature)+"c");
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                Toast.makeText(MainActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();


            }
        });



    }
}
