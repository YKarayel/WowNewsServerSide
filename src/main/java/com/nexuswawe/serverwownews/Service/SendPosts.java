package com.nexuswawe.serverwownews.Service;

import com.google.firebase.database.DatabaseReference;
import com.nexuswawe.serverwownews.Core.IsendDataToRtDb;
import com.nexuswawe.serverwownews.Core.InitialFirebase;
import com.nexuswawe.serverwownews.Core.WowheadNews;
import com.nexuswawe.serverwownews.Repo.ScrapData;

import java.io.IOException;
import java.util.List;

public class SendPosts  extends InitialFirebase implements IsendDataToRtDb, AutoCloseable {


    @Override
    public void SendDataToRtDatabase() throws IOException, InterruptedException {

        AddAdminSdk();
        BuildFireBaseRDb();
        GetFirebaseDatabaseInstance();

        DatabaseReference ref = getDatabase().getReference("wowhead_news");
        System.out.println("Added to database referance. Waiting for send Posts.");


        // UiPosts sınıfından veri almak ve Firebase Realtime Database'e yazmak
        List<WowheadNews> wowheadNewsList =  ScrapData.scrapData();
        if (10 < wowheadNewsList.size()) {
            ref.setValueAsync(wowheadNewsList);
            System.out.println("Posts sended to Firebase Realtime DB.");
        }

    }

    @Override
    public void close() throws Exception {

    }
}
