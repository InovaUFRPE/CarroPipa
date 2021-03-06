package com.inova.ufrpe.processos.carropipa.infraestrutura.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.inova.ufrpe.processos.carropipa.R;

public class ApresentActivity extends AppCompatActivity {

    private Button btn_Criar;
    private Button btn_Entrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_apresent );
        btn_Criar = findViewById(R.id.button2);
        btn_Entrar = findViewById(R.id.btn_entrar);

        btn_Entrar.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ApresentActivity.this,LoginActivity.class));
                finish();
            }
        } );
        btn_Criar.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ApresentActivity.this,CadastroUsuarioActivity.class));
                finish();

            }
        } );

    }
}
