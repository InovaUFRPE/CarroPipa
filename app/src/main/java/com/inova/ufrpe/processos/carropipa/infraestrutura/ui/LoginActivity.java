package com.inova.ufrpe.processos.carropipa.infraestrutura.ui;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.inova.ufrpe.processos.carropipa.R;
import com.inova.ufrpe.processos.carropipa.infraestrutura.serverlayer.Conexao;
import com.inova.ufrpe.processos.carropipa.infraestrutura.validadores.Validacao;

public class   LoginActivity extends AppCompatActivity {

    private Button btn_logar;
    private EditText edt_login;
    private EditText edt_senha;
    private String url = "";
    private String parametros = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_logar = findViewById(R.id.btn_logar);
        edt_login = findViewById(R.id.edt_login);
        edt_senha = findViewById(R.id.edt_senha);

        //para logar no sistema:
        btn_logar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                //aqui pode gerar exception??
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
                if (isConnected){
                    validarCampos();
                }
                else{
                    //Snackbar.make(v, R.string.connection_failed, Snackbar.LENGTH_LONG ).show();
                    Toast.makeText(LoginActivity.this, getString(R.string.connection_failed), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void validarCampos(){
        String emailUser = edt_login.getText().toString();
        String senhaUser = edt_senha.getText().toString();
        boolean isValid = true;
        if(emailUser.isEmpty() || !new Validacao().validarEmail(emailUser)){
            edt_login.setError("Informação invalida");
            isValid = false;
        }
        if(senhaUser.isEmpty()){
            edt_senha.setError("Este campo não pode ser vazio");
            isValid = false;
        }
        if (isValid){
            Toast.makeText(this, "logado", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(LoginActivity.this,MapsActivity.class));
            finish();
            /*url = "http://192.168.15.148:5000/login/logar";
            parametros = "email=" + emailUser +"&senha=" + senhaUser;
            new SolicitaDados().execute(url);*/
        }
    }

    /*
    Usa asyncTasks!
    A classe interna a seguir conecta a internet e envia informações em segundo plano
     */
    private class SolicitaDados extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... url) {

            return Conexao.postDados(url[0], parametros);
        }

        //exibe os resultados
        @Override
        protected void onPostExecute(String results){

            //Criado para tratar a nova String vinda do Servidor;

            String[] resultado = results.split(",");


            if(resultado[0].contains("login_ok")){
                //exibir toast apenas para verificar os dados q chegam do servidor
                Intent autentication = new Intent(LoginActivity.this,MapsActivity.class);
                //autentication.putExtra("nome",resultado[1]);
                //autentication.putExtra("snome",resultado[2]);
                autentication.putExtra("email",resultado[1]);
                //autentication.putExtra("acesso",resultado[4]);
                startActivity(autentication);
            }
            else {
                Toast.makeText(LoginActivity.this, getString(R.string.userPass_failed), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
