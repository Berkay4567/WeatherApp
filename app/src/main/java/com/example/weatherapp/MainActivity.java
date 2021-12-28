package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.widget.Toast;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.weatherapp.Retrofit.ApiClient;
import com.example.weatherapp.Retrofit.ApiInterface;
import com.example.weatherapp.Retrofit.Example;
import android.widget.ListView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.List;
public class MainActivity extends AppCompatActivity {
    Button goster,gecmisisil;
    ImageView search;
    TextView tempText , descText , humidityText,ad ;
    EditText textField;
    veritabani myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      //  v1=new veritabani(this);
        myDb = new veritabani(this);
        search = findViewById(R.id.search);
        tempText = findViewById(R.id.tempText);
        descText = findViewById(R.id.descText);
        humidityText = findViewById(R.id.humidityText);
        textField = findViewById(R.id.textField);
        goster=(Button)findViewById((R.id.goster));
        gecmisisil=(Button)findViewById((R.id.gecmisisil));
        AddData();
        ViewData();
        DeleteData();
  /*      search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try
                {

                  //  ekleme(textField.getText().toString());
                }
                finally
                {
                    //v1.close();

               }


            }
        });*/
    }
        public void AddData(){
            search.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            getWeatherData(textField.getText().toString().trim());

                        }
                    }
            );
        }
    public void ViewData(){

        goster.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Cursor res = myDb.getAllData();

                        if(res.getCount()==0){
                            Toast.makeText(MainActivity.this,"Veri yok",Toast.LENGTH_LONG).show();
                            return;
                        }

                        StringBuffer buffer=new StringBuffer();

                        while(res.moveToNext()){

                            buffer.append("id: "+ res.getString(0)+"\n");

                            buffer.append("Sehir: "+ res.getString(1)+"\n");

                            buffer.append(res.getString(2)+"\n");

                            buffer.append(res.getString(3)+"\n");

                            buffer.append(res.getString(4)+"\n\n\n");
                        }
                        //show all data

                        showMessage("Geçmiş",buffer.toString());


                    }
                }
        );

    }
    public void showMessage(String title, String Message){

        AlertDialog.Builder builder= new AlertDialog.Builder(this);

        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    public void DeleteData(){
        gecmisisil.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Integer deletedRows = myDb.deleteData(textField.getText().toString());

                        if (deletedRows > 0) {
                            Toast.makeText(MainActivity.this, "Ver silindi", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Veri silinemedi", Toast.LENGTH_LONG).show();
                        }
                    }

                }
        );
    }


        /*
        goster.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  bilgileriGoster();
            }
        }));*/

    private void getWeatherData(String name){

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<Example> call = apiInterface.getWeatherData(name);

        call.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {

                tempText.setText("Derece"+" "+response.body().getMain().getTemp()+" °C");
                humidityText.setText("Nem "+"% "+response.body().getMain().getHumidity());
                descText.setText("Hissedilen"+" "+response.body().getMain().getFeels_like()+" °C");

                boolean isInserted =  myDb.insertData(textField.getText().toString(),
                        tempText.getText().toString(),
                        humidityText.getText().toString(),
                        descText.getText().toString());

                if(isInserted==true)
                    Toast.makeText(MainActivity.this,"Veri islendi",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this,"Veri işlenemedi",Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {

            }
        });

    }
/*
private void ekleme(String textField)
{
    SQLiteDatabase db =v1.getWritableDatabase();
    ContentValues cv1=new ContentValues();
    cv1.put("sehir",textField);
    db.insertOrThrow("bilgiler",null, cv1);
}
    private String[] sutunlar={"textField"};
    private  void bilgileriGoster()
    {
        SQLiteDatabase db=v1.getReadableDatabase();
        Cursor okunanlar=db.query("bilgiler",sutunlar,null,null,null,null,null);
        while(okunanlar.moveToNext())
        {
         String sehirr=okunanlar.getString(okunanlar.getColumnIndex("textField"));
            ad.setText(sehirr);
        }
    }
*/
}
