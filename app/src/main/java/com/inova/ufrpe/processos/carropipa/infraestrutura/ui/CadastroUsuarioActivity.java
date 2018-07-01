package com.inova.ufrpe.processos.carropipa.infraestrutura.ui;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.inova.ufrpe.processos.carropipa.R;
import com.inova.ufrpe.processos.carropipa.infraestrutura.serverlayer.Conexao;

public class CadastroUsuarioActivity extends AppCompatActivity {

    private EditText editNome;
    private EditText editSobreNome;
    private EditText editEmail;
    private EditText editTelefone;
    private EditText editSenha;
    
    private String nome;
    private String sobrenome;
    private String email;
    private String celular;
    private String senha;
    
    private Button btn_criar;
    private String url = "";
    private String parametros = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_criar_conta );

        editNome = findViewById( R.id.editNome );
        editSobreNome = findViewById( R.id.editSobrenome );
        editEmail = findViewById( R.id.editEmail );
        editTelefone = findViewById( R.id.editTelefone );
        editSenha = findViewById( R.id.editSenha );
        btn_criar = findViewById( R.id.btn_criarconta );

        btn_criar.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificarCampos();
            }
        });
    }

    private void verificarCampos() {
         nome = editNome.getText().toString().trim();
         sobrenome = editSobreNome.getText().toString().trim();
         celular = editTelefone.getText().toString().trim();
         email = editEmail.getText().toString().trim();
         senha = editSenha.getText().toString().trim();
         boolean isValid = true;
         if (nome.isEmpty()){
             editNome.setError("Este campo não pode ser vazio");
             isValid = false;
         }
         if (sobrenome.isEmpty()){
            editSobreNome.setError("Este campo não pode ser vazio");
             isValid = false;
         }
         if (celular.isEmpty()){
            editTelefone.setError("Este campo não pode ser vazio");
             isValid = false;
         }
         if (email.isEmpty()){
            editEmail.setError("Este campo não pode ser vazio");
             isValid = false;
         }
         if (senha.isEmpty()){
            editSenha.setError("Este campo não pode ser vazio");
            isValid = false;
         }
         if (isValid){
             verificarEntradas();
         }
    }

    private void verificarEntradas(){ 
        boolean isValid = true;
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editEmail.setError("Email inválido");
            isValid = false;
        } 
        if (validarNome( nome )) {
            editNome.setError("Nome inválido");
            isValid = false;
        } 
        if (validarSobrenome( sobrenome )) {
            editSobreNome.setError("sobrenome inválido");
            isValid = false;
        } 
        if (validarNumero( celular )) {
            editTelefone.setError("Telefone inválido");
            isValid = false;
        } 
        if(isValid){
            //Criar com API
            ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
            //aqui pode gerar exception??
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
            if (isConnected){
                Toast.makeText(CadastroUsuarioActivity.this, getString(R.string.connection_sucess), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(CadastroUsuarioActivity.this,LoginActivity.class));
                finish();
                /*url = "http://192.168.15.148:5000/cadastro/cadastrar";
                parametros = "email=" + email +"&senha=" + senha +"&celular=" + celular +"&sobrenome=" + sobrenome +"&nome=" + nome;
                new SolicitaDados().execute(url);*/
            }
            else{ 
                Toast.makeText(CadastroUsuarioActivity.this, getString(R.string.connection_failed), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public Boolean validarNome(String nome) {
        return nome.matches("^(?![ ])(?!.*[ ]{2})((?:e|da|do|das|dos|de|d'|D'|la|las|el|los)" +
                "\\s*?|(?:[A-Z][^\\s]*\\s*?)(?!.*[ ]$))+$");
    }

    public Boolean validarSobrenome(String sobreNome) {
        return (sobreNome.matches("^(?![ ])(?!.*[ ]{2})((?:e|da|do|das|dos|de|d'|D'|la|las|el|los)" +
                "\\s*?|(?:[A-Z][^\\s]*\\s*?)(?!.*[ ]$))+$"));
    }
    public Boolean validarNumero(String numero) {
        return numero.matches("^[0-9]{0,5}+$");
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

            String[] resultado = results.split(", ");

            if(resultado[0].contains("login_ok")) {
                finish();
            }
            else{
                Toast.makeText(CadastroUsuarioActivity.this, getString(R.string.cadastration_failed), Toast.LENGTH_SHORT).show();
                // Falha no cadatros!! @TODO tratar erro, para exibir ao Usuário
            }
        }
    }
}
