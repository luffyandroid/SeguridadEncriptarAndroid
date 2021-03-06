package com.example.seguridadencriptarandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class MainActivity extends AppCompatActivity {

    EditText etTexto, etPassword;
    TextView tvTexto;
    String textoSalida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etTexto = findViewById(R.id.mainActivityEtTexto);
        etPassword = findViewById(R.id.mainActivityEtPassword);
        tvTexto = findViewById(R.id.mainActivityTvTexto);

    }

    public void clickencriptar (View view){

        try{
            textoSalida = encriptar(etTexto.getText().toString(), etPassword.getText().toString());
            tvTexto.setText(textoSalida);
        }catch (Exception e){

            e.printStackTrace();

        }

    }

    private String encriptar(String datos, String password) throws Exception{
        SecretKeySpec secretKey = generateKey(password);
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] datosEncriptadosBytes = cipher.doFinal(datos.getBytes());
        String datosEncriptadosString = Base64.encodeToString(datosEncriptadosBytes, Base64.DEFAULT);
        return datosEncriptadosString;
    }

    private SecretKeySpec generateKey(String password) throws Exception{
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        byte[] key = password.getBytes("UTF-8");
        key = sha.digest(key);
        SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
        return secretKey;
    }

    public void clickdesencriptar (View view){

        try{

            textoSalida = desencriptar(textoSalida, etPassword.getText().toString());
            tvTexto.setText(textoSalida);

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private String desencriptar(String datos, String password) throws Exception{

        SecretKeySpec secretKey = generateKey(password);
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] datosDesencriptados = Base64.decode(datos, Base64.DEFAULT);
        byte[] datosDesencriptadosByte = cipher.doFinal(datosDesencriptados);
        String datosDesencriptadosString = new String(datosDesencriptadosByte);
        return datosDesencriptadosString;

    }

}
