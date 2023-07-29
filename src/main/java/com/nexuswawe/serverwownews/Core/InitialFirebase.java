package com.nexuswawe.serverwownews.Core;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.io.FileInputStream;
import java.io.IOException;

public class InitialFirebase implements IinitialFirabase {

    static FirebaseOptions options;
    static FirebaseDatabase database;
    static FirebaseApp firebaseApp;
    static FileInputStream serviceAccount;

    public FirebaseDatabase getDatabase() {
        return database;
    }

    @Override
    public  void AddAdminSdk() {
        try {
            if (serviceAccount == null) {
                serviceAccount = new FileInputStream("wownews-8d9b8-firebase-adminsdk-phqt7-6c6ea24f27.json");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void BuildFireBaseRDb() throws IOException, RuntimeException{

        if (FirebaseApp.getApps().isEmpty() || firebaseApp == null) {
            options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://wownews-8d9b8-default-rtdb.europe-west1.firebasedatabase.app")
                    .build();
            firebaseApp = FirebaseApp.initializeApp(options);
            System.out.println("Firebase initialized.");

        }
    }

    public void GetFirebaseDatabaseInstance(){
        if(database == null ) {
            database = FirebaseDatabase.getInstance();
            System.out.println("Database instance created. Waiting for database referance...");
        }

    }
}



