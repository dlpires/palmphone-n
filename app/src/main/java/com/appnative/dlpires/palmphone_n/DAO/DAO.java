package com.appnative.dlpires.palmphone_n.DAO;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by root on 13/01/18.
 */

public class DAO {

    private static DatabaseReference databaseReference;
    private static FirebaseAuth auth;

    public static DatabaseReference getFirebase(){
        if (databaseReference == null){
            databaseReference = FirebaseDatabase.getInstance().getReference();
        }

        return databaseReference;
    }

    public static FirebaseAuth getFirebaseAuth(){
        if (auth == null){
            auth = FirebaseAuth.getInstance();
        }

        return auth;
    }
}
