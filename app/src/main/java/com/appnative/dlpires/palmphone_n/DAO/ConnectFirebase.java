package com.appnative.dlpires.palmphone_n.DAO;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * Created by root on 13/01/18.
 */

//CLASSE DAO PARA ACESSO AOS COMPONENTES DO FIREBASE
public class ConnectFirebase {

    //ATRIBUTOS ESTATICOS
    private static DatabaseReference databaseReference;
    private static FirebaseAuth auth;
    private static FirebaseDatabase database;
    private static FirebaseStorage firebaseStorage;
    private static StorageReference storageReference;

    //MÉTODO PARA O REALTIME DATABASE
    public static DatabaseReference getFirebaseDbRef(){
        if (databaseReference == null){
            databaseReference = FirebaseDatabase.getInstance().getReference();
        }

        return databaseReference;
    }
    //MÉTODO PARA O REALTIME DATABASE
    public static FirebaseDatabase getFirebaseDatabase(){
        if (database == null){
            database = FirebaseDatabase.getInstance();
        }

        return database;
    }

    //MÉTODO PARA O STORAGE FIREBASE
    public static FirebaseStorage getFirebaseStorage(){
        if (firebaseStorage == null){
            firebaseStorage = FirebaseStorage.getInstance();
        }

        return firebaseStorage;
    }

    //MÉTODO PARA O STORAGE FIREBASE
    public static StorageReference getStorageReference(){
        if (storageReference == null){
            storageReference = FirebaseStorage.getInstance().getReference();
        }

        return storageReference;
    }

    //MÉTODO PARA A AUTENTICAÇÃO DO FIREBASE
    public static FirebaseAuth getFirebaseAuth(){
        if (auth == null){
            auth = FirebaseAuth.getInstance();
        }

        return auth;
    }


}
