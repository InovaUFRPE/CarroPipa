package com.inova.ufrpe.processos.carropipa.infraestrutura.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.inova.ufrpe.processos.carropipa.R;
import com.inova.ufrpe.processos.carropipa.infraestrutura.hardware.ExternalStorage;
import com.inova.ufrpe.processos.carropipa.pessoa.dominio.EnumStados;
import com.inova.ufrpe.processos.carropipa.pessoa.dominio.EnumTipos;
import com.inova.ufrpe.processos.carropipa.pessoa.dominio.Pessoa;

import java.io.File;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class PerfilActivity extends AppCompatActivity {

    private final int GALERY = 1;
    private final int CAMERA = 0;
    private File foto = null;
    private Bitmap btmReduzido;

    private EditText cpf;
    private EditText logradouro;
    private EditText complemento;
    private EditText cidade;
    private EditText bairro;
    private EditText cep;
    private Button limpar;
    private Button enviar;
    private Spinner uf;
    private FloatingActionButton tirarFoto;
    private FloatingActionButton abrirGaleria;
    private ImageView imageUser;
    private Spinner pessoaTipo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_perfil );

        cpf = findViewById(R.id.edt_cpf);
        logradouro = findViewById(R.id.edt_logradouro);
        complemento = findViewById(R.id.edt_complemento);
        cidade = findViewById(R.id.edt_cidade);
        bairro = findViewById(R.id.edt_bairro);
        cep = findViewById(R.id.edt_cep);
        limpar = findViewById(R.id.btn_limpar);
        enviar = findViewById(R.id.btn_enviar);
        uf = findViewById(R.id.spn_uf);
        pessoaTipo = findViewById(R.id.spn_tipo);
        tirarFoto = findViewById(R.id.fab_tirarFoto);
        abrirGaleria = findViewById(R.id.fab_abrirGaleria);
        imageUser = findViewById(R.id.img_user);

        //permissões para manipular arquivos:

        // trecho adiciona permissão de ler arquivos
        int PERMISSION_REQUEST = 0;
        if(ContextCompat.checkSelfPermission(PerfilActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            //não tem permissão: solicitar
            if(ActivityCompat.shouldShowRequestPermissionRationale(PerfilActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)){

            }else{
                ActivityCompat.requestPermissions(PerfilActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST);
            }
        }

        // trecho adiciona permissão de gravar arquivos
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            } else {
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST);
            }
        }



        Pessoa pessoa = new Pessoa();

        pessoa.setBairro(bairro.getText().toString());
        pessoa.setCpf(bairro.getText().toString());
        pessoa.setLogradouro(bairro.getText().toString());
        pessoa.setComplemento(bairro.getText().toString());
        pessoa.setCidade(bairro.getText().toString());
        pessoa.setCep(bairro.getText().toString());
        //pessoa.setBairro(bairro.getText().toString()); ler o spinner

        //Setar Spinners
        //Spiner Estados:
        ArrayAdapter<String> enumTiposArrayAdapter;
        enumTiposArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, EnumTipos.EnumTiposLista());
        pessoaTipo.setAdapter(enumTiposArrayAdapter);
        enumTiposArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Log.d("Verificando Spinner", pessoaTipo.getSelectedItem().toString());


        //Spiner Estados:
        ArrayAdapter<String> enumStadosArrayAdapter;
        enumStadosArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, EnumStados.enumEstadosLista());
        uf.setAdapter(enumStadosArrayAdapter);
        enumStadosArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //ação dos FABs:
        //Abrir Galeria
        abrirGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //verifica permissão de galeria
                int permissionCheck = ContextCompat.checkSelfPermission(PerfilActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
                //se tiver permissão tira foto
                if(permissionCheck == PackageManager.PERMISSION_GRANTED){
                    escolherFoto();
                }
                else{
                    // solicita permissão
                    ActivityCompat.requestPermissions(PerfilActivity.this,new String[]{
                            Manifest.permission.WRITE_EXTERNAL_STORAGE},GALERY);
                }

            }
        });
        //fim da galeria

        //abre a camerera
        tirarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //verifica permissão de camera
                int permissionCheck = ContextCompat.checkSelfPermission(PerfilActivity.this, Manifest.permission.CAMERA);
                //UseCamera camera = new UseCamera();
                //se tiver permissão tira foto
                if(permissionCheck == PackageManager.PERMISSION_GRANTED){
                    abreCamera();
                }
                else{
                    // solicita permissão
                    ActivityCompat.requestPermissions(PerfilActivity.this,new String[]{
                            Manifest.permission.CAMERA},CAMERA);
                }
            }
        });
        //fim do abre a camera



        //ação dos botões:
        //botão limpar
        limpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limparCampos();
            }
        });

        //botão enviar:
    }

    /**
     * Metodo abreCamera - Tira uma foto com a Camera do Dispositivo
     * @param : None
     * @return : None
     * @action : Retorna para o arquivo uma foto tirada pela camera do usuário
     */
    private void abreCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        try{
            ExternalStorage externalStorage = new ExternalStorage();
            foto = externalStorage.criarArquivo();
        }catch (IOException e) {   // Manipulação em caso de falha de criação do arquivo
            e.printStackTrace();
        }
        if(foto!= null) {
            Uri photoURI =
                    FileProvider.getUriForFile(getBaseContext(),
                            getBaseContext().getApplicationContext().getPackageName() + ".provider", foto);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            startActivityForResult(takePictureIntent, CAMERA);
        }
    }

    /**
     *Metodo Escolher foto - Escolhe Foto do usuário
     * @param : None
     * @return: None
     * @action: Abre a galeria do usuário para que ele possa escolher uma foto
     */
    private void escolherFoto() {
        Intent galery =
                new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galery,GALERY);
    }

    /**
     * Metodo Limpar campos - Limpa os campos de um formulário
     * params: None
     * return: None
     * action: Limpa os campos de um formulário
     */
    private void limparCampos() {
        cpf.setText(cpf.getHint());
        logradouro.setText(logradouro.getHint());
        complemento.setText(complemento.getHint());
        cidade.setText(cidade.getHint());
        bairro.setText(bairro.getHint());
        cep.setText(cep.getHint());
        uf.setSelection(0);
    }

    /*Trata a resposta de permissão do usuário
   de acordo com as constantes
    */
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults){

        switch (requestCode){
            case CAMERA:{
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    abreCamera();
                }
            }
            case GALERY:{
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    escolherFoto();
                }
            }
        }
    }


    /*
   Metodo que responde as Intentes de Tirar Foto e Abrir galeria
   Caso o requestCode seja GALERY ou CAMERA e coloca o resultado no ImageView
    */
    @Override
    protected  void onActivityResult(int requestCode, int resultCode, Intent data){

        String picturePath = null;
        super.onActivityResult(requestCode,resultCode, data);
        Bitmap thumb;
        if(requestCode == GALERY && resultCode== RESULT_OK){
            Uri selectedImage = data.getData();
            String[] filePath = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
            if(c.moveToFirst()){
                int columnIndex = c.getColumnIndex(filePath[0]);
                picturePath = c.getString(columnIndex);
            }

            c.close();

            //Necessario redimensionar a imagem lida
            if(picturePath==null){
                thumb = BitmapFactory.decodeResource(getBaseContext().getResources(),R.mipmap.ic_launcher_round);
            }
            thumb = (BitmapFactory.decodeFile(picturePath));
            btmReduzido = Bitmap.createScaledBitmap(thumb,150,150,true);
            imageUser.setImageBitmap(thumb);
        }
        if(requestCode == CAMERA && resultCode== RESULT_OK){

            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(foto)));
            //coloca a imagem no ImageView.
            if(data!=null){
                Bundle bundle = data.getExtras();
                if(bundle!=null){
                    thumb = (Bitmap) bundle.get("data");
                    btmReduzido = Bitmap.createScaledBitmap(thumb,150,150,true);
                    //return bitmap criando um metodo de saida
                    imageUser.setImageBitmap(thumb);
                }
            }
        }
    }
}
