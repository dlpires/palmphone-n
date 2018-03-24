package com.appnative.dlpires.palmphone_n.Activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.appnative.dlpires.palmphone_n.Classes.Professor;
import com.appnative.dlpires.palmphone_n.R;

public class EditarUserPage extends AppCompatActivity {

    //COMPONENTES DA TELA
    private EditText txtNome;
    private EditText txtRg;
    private EditText txtDataNasc;
    private EditText nameImage;
    private Button btnChooseImage;
    private Button btnSalvar;

    //OUTRAS REFERENCIAS
    private Professor professor;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editar_user_page);

        //INICIANDO A TOOLBAR
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarCadastro);
        setSupportActionBar(toolbar);

        //CARREGANDO COMPONENTES DA TELA
        txtNome = (EditText) findViewById(R.id.txtNome);
        txtRg = (EditText) findViewById(R.id.txtRg);
        txtDataNasc = (EditText) findViewById(R.id.txtDtNasc);
        nameImage = (EditText) findViewById(R.id.txtNomeArq);
        btnChooseImage = (Button) findViewById(R.id.btnEscArq);
        btnSalvar = (Button) findViewById(R.id.btnCadastro);

        //INSTANCIANDO OBJETOS
        professor = new Professor();

        //COLOCANDO OS DADOS NOS EDIT TEXTS
        getDadosProfessor();

        //EVENTO DE CLICK NO BOTÃO PARA ESCOLHER IMAGEM
        btnChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                escolherArquivo();
            }
        });

        //EVENTO DE CLICK NO BOTÃO SALVAR
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setProfessor();
                salvarUsuario();
            }
        });

    }

    //MÉTODO QUE ACIONA O BOTÃO VOLTAR DA TOOLBAR
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //PEGA O ID DO ITEM DO MENU DA TOOLBAR E VERIFICA SE É IGUAL ID DO COMPONENTE HOME (VOLTAR), ONDE NO CASO ELE EXECUTA A AÇÃO
        //DE VOLTAR NA PÁGINA ANTERIOR
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, UserPage.class);
                startActivity(intent);
                finish(); // Finaliza a Activity atual
                break;

            default:break;
        }

        return true;
    }

    //MÉTODO PARA INSERIR INFORMAÇÕES NO OBJETO
    private void setProfessor() {
        professor.setNomeProf(txtNome.getText().toString());
        professor.setDataNascProf(txtDataNasc.getText().toString());
        professor.setRgProf(txtRg.getText().toString());
    }

    //MÉTODO PARA RESGATAR INFORMAÇÕES DO USUÁRIO A SEREM EDITADASA
    private void getDadosProfessor(){
        //LENDO ARQUIVO JSON COM OS DADOS DO PROFESSOR
        Professor prof = professor.readJson(this);

        //INSERINDO AS INFORMAÇÕES
        txtNome.setText(prof.getNomeProf());
        txtRg.setText(prof.getRgProf());
        txtDataNasc.setText(prof.getDataNascProf());
    }

    //MÉTODO PARA SALVAR OS DADOS NO FIREBASE
    private void salvarUsuario() {
        //VALIDANDO FORMULÁRIO
        if (!validateForm()){
            return;
        }

        //SALVANDO USUÁRIO
        professor.update(this, imageUri);



    }

    //MÉTODO PARA ESCOLHER A IMAGEM
    private void escolherArquivo() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, "Selecione uma Imagem"), 123);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK){
            if(requestCode == 123){
                imageUri = data.getData();
                nameImage.setText(imageUri.toString().substring(imageUri.toString().lastIndexOf("/")+1,imageUri.toString().length()));
            }
        }

    }

    //MÉTODO PARA VALIDAR FORM DE CADASTRO
    private boolean validateForm(){
        boolean valid = true;

        //VERIFICANDO SE AS CAIXAS DE TEXTO ESTÃO EM BRANCO OU NÃO, DE FORMA A INFORMAR AO USUÁRIO QUE INSIRA AS INFORMAÇÕES
        String user = txtRg.getText().toString();
        if (TextUtils.isEmpty(user)) {
            txtRg.setError("Required");
            valid = false;
        } else {
            txtRg.setError(null);
        }

        user = txtDataNasc.getText().toString();
        if (TextUtils.isEmpty(user)) {
            txtDataNasc.setError("Required");
            valid = false;
        } else {
            txtDataNasc.setError(null);
        }

        user = txtNome.getText().toString();
        if (TextUtils.isEmpty(user)) {
            txtNome.setError("Required");
            valid = false;
        } else {
            txtNome.setError(null);
        }

        return valid;
    }

    //MÉTODO DO BOTÃO VOLTAR DO DISPOSITIVO
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, UserPage.class);
        startActivity(intent);
        finish(); // Finaliza a Activity atual
    }
}
