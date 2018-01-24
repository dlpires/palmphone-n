package com.appnative.dlpires.palmphone_n.DAO;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * Created by root on 13/01/18.
 */

public class ConnectFirebase {

    private static DatabaseReference databaseReference;
    private static FirebaseAuth auth;
    private static FirebaseDatabase database;
    private static FirebaseStorage firebaseStorage;
    private static StorageReference storageReference;

    public static DatabaseReference getFirebaseDbRef(){
        if (databaseReference == null){
            databaseReference = FirebaseDatabase.getInstance().getReference();
        }

        return databaseReference;
    }

    public static FirebaseDatabase getFirebaseDatabase(){
        if (database == null){
            database = FirebaseDatabase.getInstance();
        }

        return database;
    }

    public static FirebaseStorage getFirebaseStorage(){
        if (firebaseStorage == null){
            firebaseStorage = FirebaseStorage.getInstance();
        }

        return firebaseStorage;
    }

    public static StorageReference getStorageReference(){
        if (storageReference == null){
            storageReference = FirebaseStorage.getInstance().getReference();
        }

        return storageReference;
    }

    public static FirebaseAuth getFirebaseAuth(){
        if (auth == null){
            auth = FirebaseAuth.getInstance();
        }

        return auth;
    }


}
