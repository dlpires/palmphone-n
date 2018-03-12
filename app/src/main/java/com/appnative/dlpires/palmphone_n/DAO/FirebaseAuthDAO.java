package com.appnative.dlpires.palmphone_n.DAO;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.appnative.dlpires.palmphone_n.Activity.LoginPage;
import com.appnative.dlpires.palmphone_n.Classes.NotificaUser;
import com.appnative.dlpires.palmphone_n.Classes.Professor;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by root on 11/03/18.
 */

public class FirebaseAuthDAO {

    //REFERENCIAS FIREBASE
    private static FirebaseAuth auth = ConnectFirebase.getFirebaseAuth();
    private static ManipulaArquivo arq = new ManipulaArquivo();

    //BOOLEANO PARA RETORNO DE MÉTODOS DO TIPO BOOLEAN
    private static boolean b;

    public static boolean isB() {
        return b;
    }

    public static void setB(boolean b) {
        FirebaseAuthDAO.b = b;
    }

    //MÉTODO PARA CADASTRO DE USUÁRIO
    public static void createUser(final Context context, final Uri image, final Professor professor){
        //BARRA DE PROGRESSO INICIADAS
        NotificaUser.showProgressDialog(context);

        auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(professor.getEmailProf(), professor.getSenhaProf()).
                addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            //VERIFICANDO SE O MÉTODO FOI COMPLETADO COM EXITO, PARA REALIZAR AS PRÓXIMAS TAREFAS
                            if(image != null){
                                FirebaseStorageDAO.cadastrarFotoPerfil(context, image);
                            }
                            FirebaseDatabaseDAO.insereUsuario(context, professor);
                            NotificaUser.alertaCaixaDialogoOk(context, "Cadastro", "Usuário cadastrado com sucesso!", LoginPage.class);
                        }
                        else{
                            String erroExcecao = null;
                            try{
                                throw task.getException();
                            }catch (FirebaseAuthWeakPasswordException e){
                                erroExcecao = "Digite uma senha mais forte, contendo no mínimo 8 caracteres e com letras e numeros";
                            }catch (FirebaseAuthInvalidCredentialsException e){
                                erroExcecao = "Email digitado é invalido! Redigite o campo EMAIL!";
                            }catch (FirebaseAuthUserCollisionException e){
                                erroExcecao = "Email já cadastrado!";
                            }catch (Exception e){
                                erroExcecao = "Erro ao cadastrar!";
                                e.printStackTrace();
                            }

                            NotificaUser.alertaToast(context, "Erro: " + erroExcecao);
                        }

                        //BARRA DE PROGRESSO FECHADA
                        NotificaUser.hideProgressDialog();
                    }
                });


    }

    //MÉTODO PARA VERIFICAR SE USUÁRIO JA ESTÁ LOGADO
    @NonNull
    public static boolean usuarioLogado(){
        FirebaseUser user = ConnectFirebase.getFirebaseAuth().getCurrentUser();

        if (user != null) return true;
        else return false;
    }

    public static boolean loginUser(Professor professor, final Context context){

        //MOSTRANDO BARRA DE PROGRESSO (LOADING)
        NotificaUser.showProgressDialog(context);

        //CHAMANDO MÉTODO DE AUTENTICAÇÃO DO FIREBASE, PASSANDO AS INFORMAÇÕES CONTIDAS NO OBJETO
        auth.signInWithEmailAndPassword(professor.getEmailProf(), professor.getSenhaProf()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //CASO A REQUISIÇÃO FOR VALIDADA, MOSTRAR TELA PRINCIPAL
                if(task.isSuccessful()){
                    //SALVANDO INFORMAÇÕES DO USUÁRIO LOGADO
                    FirebaseDatabaseDAO.infUserLogado(context);
                    setB(true);
                }
                //CASO CONTRARIO, MOSTRA MENSAGEM AO USUÁRIO
                else{
                    NotificaUser.alertaCaixaDialogoSimple(context,"Falha no Login!" ,"Usuário ou Senha Incorreta!");
                    setB(false);
                }
                //FECHANDO BARRA DE PROGRESSO (LOADING)
                NotificaUser.hideProgressDialog();
            }
        });

        return isB();
    }

    //MÉTODO PARA SAIR DO SISTEMA
    public static void signOut(Context context){
        //APAGANDO ARQUIVO DE USUÁRIO LOGADO
        arq.apagarArquivo(context, auth.getCurrentUser().getUid());

        //Saindo do Sistema
        auth.signOut();
    }
}
