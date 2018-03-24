package com.appnative.dlpires.palmphone_n.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.ExpandableListAdapter;
import android.widget.ImageView;

import com.appnative.dlpires.palmphone_n.Classes.NotificaUser;
import com.appnative.dlpires.palmphone_n.Classes.Professor;
import com.appnative.dlpires.palmphone_n.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CadastroPage extends AppCompatActivity {

    //COMPONENTES DA TELA
    private EditText txtNome;
    private EditText txtRg;
    private EditText txtEmail;
    private EditText txtSenha;
    private EditText txtDataNasc;
    private EditText nameImage;
    private Button btnSelectDisc;
    private Button btnChooseImage;
    private Button btnSalvar;

    //OUTROS OBJETOS
    private ExpandableListAdapter listAdapter;
    private Professor professor;
    private CharSequence[] disciplinas;
    private ArrayList<String> disc;
    private Uri imageUri;

    //ATRIBUTOS
    private boolean[] b;

    //CLASSE QUE INSTANCIA A ACTIVITY
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro_page);

        //INICIANDO A TOOLBAR
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarCadastro);
        setSupportActionBar(toolbar);

        //CARREGANDO COMPONENTES DA TELA
        txtNome = (EditText) findViewById(R.id.txtNome);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtSenha = (EditText) findViewById(R.id.txtSenha);
        txtRg = (EditText) findViewById(R.id.txtRg);
        txtDataNasc = (EditText) findViewById(R.id.txtDtNasc);
        nameImage = (EditText) findViewById(R.id.txtNomeArq);
        btnSelectDisc = (Button) findViewById(R.id.btnDisciplinas);
        btnChooseImage = (Button) findViewById(R.id.btnEscArq);
        btnSalvar = (Button) findViewById(R.id.btnCadastro);


        //INSTANCIANDO OBJETOS
        professor = new Professor();
        disc = new ArrayList<>();

        //INICIAZANDO LISTA DE DISCIPLINAS
        setListaDisciplinas();

        //SETANDO ARRAY DE BOOLEANO
        b = new boolean[disciplinas.length];

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
        //EVENTO DE CLICK DO BOTÃO SELECIONAR DISCIPLINA
        btnSelectDisc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                escolherDisciplina();
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
                Intent intent = new Intent(this, LoginPage.class);
                startActivity(intent);
                finish(); // Finaliza a Activity atual
                break;

            default:break;
        }

        return true;
    }

    //MÉTODO QUE CARREGA A LISTA DE DISCIPLINAS
    private void setListaDisciplinas(){

        disciplinas = new CharSequence[]{"SIF039 - Redes de Computadores II",
                "SIF033 - Engenharia de Software III",
                "SIF040 - Projeto de Sistemas I",
                "SIF068 - Tópicos em Linguagem de Programação",
                "SIF012 - Linguagem de Programação I",
                "SIF032 - Engenharia de Software II",
                "SIF035 - Interface Humano Computador"
        };

    }

    //MÉTODO PARA INSERIR INFORMAÇÕES NO OBJETO
    private void setProfessor() {
        professor.setNomeProf(txtNome.getText().toString());
        professor.setEmailProf(txtEmail.getText().toString());
        professor.setDataNascProf(txtDataNasc.getText().toString());
        professor.setRgProf(txtRg.getText().toString());
        professor.setSenhaProf(txtSenha.getText().toString());
    }

    //MÉTODO PARA SALVAR OS DADOS NO FIREBASE
    private void salvarUsuario() {
        //VALIDANDO FORMULÁRIO
        if (!validateForm()){
            return;
        }

        //SALVANDO USUÁRIO
        professor.create(this, imageUri);
    }

    //MÉTODO QUE ABRE A JANELA PARA ESCOLHER AS DISCIPLINAS
    private void escolherDisciplina(){
        new AlertDialog.Builder(this)
                .setTitle("Selecione as Disciplinas:")
                .setMultiChoiceItems(disciplinas, b, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if(isChecked){
                            disc.add((String) disciplinas[which]);
                            b[which] = true;
                        }
                        else{
                            disc.remove((String) disciplinas[which]);
                            b[which] = false;
                        }
                    }
                })
                .setPositiveButton("CONFIRMAR",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                professor.setDisciplinas(disc);
                            }
                        })
                .setNegativeButton("CANCELAR", null)
                .show();
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
        String user = txtEmail.getText().toString();
        if (TextUtils.isEmpty(user)) {
            txtEmail.setError("Required");
            valid = false;
        } else {
            txtEmail.setError(null);
        }

        user = txtSenha.getText().toString();
        if (TextUtils.isEmpty(user)) {
            txtSenha.setError("Required");
            valid = false;
        } else {
            txtSenha.setError(null);
        }

        user = txtRg.getText().toString();
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

        //VERIFICANDO SE ALGUMA DISCIPLINA FOI CADASTRADA
        if(professor.getDisciplinas().isEmpty()){
            NotificaUser.alertaCaixaDialogoSimple(this, "Atenção!", "Selecione no mínimo uma disciplina.");
            valid = false;
        }

        return valid;
    }

    //MÉTODO DO BOTÃO VOLTAR DO DISPOSITIVO
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, LoginPage.class);
        startActivity(intent);
        finish(); // Finaliza a Activity atual
    }
}
