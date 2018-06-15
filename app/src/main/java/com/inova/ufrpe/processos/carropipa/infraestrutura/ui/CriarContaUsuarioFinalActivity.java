package com.inova.ufrpe.processos.carropipa.infraestrutura.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.inova.ufrpe.processos.carropipa.R;

public class CriarContaUsuarioFinalActivity extends AppCompatActivity {

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
        } );

    }
    private void verificarCampos() {
         nome = editNome.getText().toString().trim();
         sobrenome = editSobreNome.getText().toString().trim();
         celular = editTelefone.getText().toString().trim();
         email = editEmail.getText().toString().trim();
         senha = editSenha.getText().toString().trim();

        if (email.isEmpty() || senha.isEmpty() || celular.isEmpty() || nome.isEmpty()|| sobrenome.isEmpty()) {
            Toast.makeText( CriarContaUsuarioFinalActivity.this, getString( R.string.campo_vazio ), Toast.LENGTH_SHORT ).show();

        }  else if (email.equals("Seu Email") || senha.equals( "senha" ) || nome.equals( "Nome" ) || sobrenome.equals( "Sobrenome" ) || celular.equals( "Telefone" )) {
            Toast.makeText( CriarContaUsuarioFinalActivity.this, getString( R.string.campo_vazio ), Toast.LENGTH_SHORT ).show();

        } else {
            verificarEntradas();
        }


    }

    private void verificarEntradas(){
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editEmail.setError("Email inválido");
        } else if (validarNome( nome )) {
            editNome.setError("Nome inválido");

        } else if (validarSobrenome( sobrenome )) {
            editSobreNome.setError("sobrenome inválido");

        } else if (validarNumero( celular )) {
            editSobreNome.setError("Telefone inválido");

        } else {
            //Função para criar a Conta
            finish();
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

}
