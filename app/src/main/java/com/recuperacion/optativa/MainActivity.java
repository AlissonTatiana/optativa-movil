package com.recuperacion.optativa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText etnum_serie, etdescripcion, ettamanio, etsis_oper, etcamara, etram;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etnum_serie = findViewById(R.id.num_serie);
        etdescripcion = findViewById(R.id.descripcion);
        ettamanio = findViewById(R.id.tamanio);
        etsis_oper = findViewById(R.id.sis_oper);
        etcamara = findViewById(R.id.camara);
        etram = findViewById(R.id.ram);

    }
    //Metodo para registrar un producto
    public void Registrar(View view){
        BasedeDatos admin = new BasedeDatos(this, "optativa", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        String num_serie = etnum_serie.getText().toString();
        String descripcion = etdescripcion.getText().toString();
        String tamanio = ettamanio.getText().toString();
        String sis_oper = etsis_oper.getText().toString();
        String camara = etcamara.getText().toString();
        String ram = etram.getText().toString();

        if(!num_serie.isEmpty()
                && !descripcion.isEmpty() && !tamanio.isEmpty() &&
                !sis_oper.isEmpty() && !camara.isEmpty() && !ram.isEmpty()){
            ContentValues registro = new ContentValues();
            registro.put("num_serie", num_serie);
            registro.put("descripcion", descripcion);
            registro.put("tamanio", tamanio);
            registro.put("sis_oper", sis_oper);
            registro.put("camara", camara);
            registro.put("ram", ram);

            bd.insert("producto", null, registro);
            bd.close();
            etnum_serie.setText("");
            etdescripcion.setText("");
            ettamanio.setText("");
            etsis_oper.setText("");
            etcamara.setText("");
            etram.setText("");

            Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "LLena todos los datos", Toast.LENGTH_SHORT).show();
        }
    }




    public void  Buscar(View view){
        BasedeDatos admin = new BasedeDatos(this, "optativa", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        String buscar = etnum_serie.getText().toString();
        if(!buscar.isEmpty()){
            Cursor cursor = bd.rawQuery
                    ("SELECT * FROM producto WHERE num_serie ='"+buscar+"'", null);

            if(cursor.moveToFirst()){
                etdescripcion.setText(cursor.getString(1));
                ettamanio.setText(cursor.getString(2));
                etsis_oper.setText(cursor.getString(3));
                etcamara.setText(cursor.getString(4));
                etram.setText(cursor.getString(5));
            } else {
                Toast.makeText(this, "No existe el producto", Toast.LENGTH_SHORT).show();
            }
            bd.close();
        } else{
            Toast.makeText(this, "Introduce un numero de serie", Toast.LENGTH_SHORT).show();
        }
    }
    public void Eliminar(View view){
        BasedeDatos admin = new BasedeDatos(this, "optativa", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        String eliminar = etnum_serie.getText().toString();
        if(!eliminar.isEmpty()){
            int cantidad = bd.delete("producto", "num_serie='"+eliminar+"'", null);

            if(cantidad == 1){
                etnum_serie.setText("");
                etdescripcion.setText("");
                ettamanio.setText("");
                etsis_oper.setText("");
                etcamara.setText("");
                etram.setText("");
                Toast.makeText(this, "Producto eliminado", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "No existe el producto", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(this, "Introduce un numero de serie", Toast.LENGTH_SHORT).show();
        }
        bd.close();
    }

    public void Actualizar(View view){
        BasedeDatos admin = new BasedeDatos(this, "optativa", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        String num_serie = etnum_serie.getText().toString();
        String descripcion = etdescripcion.getText().toString();
        String tamanio = ettamanio.getText().toString();
        String sis_oper = etsis_oper.getText().toString();
        String camara = etcamara.getText().toString();
        String ram = etram.getText().toString();

        if(!num_serie.isEmpty()
                && !descripcion.isEmpty() && !tamanio.isEmpty() &&
                !sis_oper.isEmpty() && !camara.isEmpty() && !ram.isEmpty()){

            ContentValues registro = new ContentValues();
            registro.put("num_serie", num_serie);
            registro.put("descripcion", descripcion);
            registro.put("tamanio", tamanio);
            registro.put("sis_oper", sis_oper);
            registro.put("camara", camara);
            registro.put("ram", ram);

            int cantidad = bd.update("producto", registro, "num_serie='"+num_serie+"'", null);
            if(cantidad == 1){
                Toast.makeText(this, "Producto actualizado", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "No existe el producto", Toast.LENGTH_SHORT).show();
            }
            etnum_serie.setText("");
            etdescripcion.setText("");
            ettamanio.setText("");
            etsis_oper.setText("");
            etcamara.setText("");
            etram.setText("");

            Toast.makeText(this, "Actualización exitosa", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "LLena todos los datos", Toast.LENGTH_SHORT).show();
        }
        bd.close();
    }

}