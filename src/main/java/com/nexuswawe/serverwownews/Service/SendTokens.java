package com.nexuswawe.serverwownews.Service;

import com.google.firebase.database.DatabaseReference;
import com.nexuswawe.serverwownews.Core.IsendDataToRtDb;
import com.nexuswawe.serverwownews.Core.InitialFirebase;
import com.nexuswawe.serverwownews.Repo.ScrapData;

import java.io.IOException;
import java.util.List;

public class SendTokens extends InitialFirebase implements IsendDataToRtDb, AutoCloseable {

    public SendTokens() {
        super();
    }

    @Override
    public void SendDataToRtDatabase()throws IOException {

            AddAdminSdk();
            BuildFireBaseRDb();
            GetFirebaseDatabaseInstance();

            DatabaseReference ref = getDatabase().getReference("wow_tokens");
            System.out.println("Added to database referance. Waiting for send Tokens.");


            // UiPosts sınıfından veri almak ve Firebase Realtime Database'e yazmak

            List<String> wowTokensList = ScrapData.WowToken();
            ref.setValueAsync(wowTokensList);
            System.out.println("Tokens sended to Firebase Realtime DB.");

    }

    @Override
    public void close() throws Exception {

    }
}
