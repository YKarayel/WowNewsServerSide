package com.nexuswawe.serverwownews.Service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.ErrorCode;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.*;
import com.nexuswawe.serverwownews.Core.IinitialFirabase;
import com.nexuswawe.serverwownews.Core.InitialFirebase;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.google.firebase.messaging.FirebaseMessagingException;


import java.io.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class IsPostUpdatedThenSendNotification  implements AutoCloseable {


    private String imageUrlNtf;
    private String bodyNtf;
    private String titleNtf;
    private String tag;
    private String postUrl;

    public String getTag() {
        return tag;
    }

    public String getImageUrlNtf() {
        return imageUrlNtf;
    }

    public String getBodyNtf() {
        return bodyNtf;
    }

    public String getTitleNtf() {
        return titleNtf;
    }


    private Connection conn = null;

    private IinitialFirabase _initialfirebase;

    public IsPostUpdatedThenSendNotification(IinitialFirabase _initialfirebase) {
        this._initialfirebase = _initialfirebase;
    }

        public void NotificationAndPostManager() throws IOException {

            String url = "https://www.wowhead.com/news";
            File file = new File("latestPostDateTimeStr.txt"); // Dosya oluştur veya aç //Düzelt

            String latestPostDateTimeStr = null;
            boolean postUpdated = false;
            Document document = Jsoup.connect(url).get();
            Elements posts = document.select("div.news-list[data-zaf-dynamic=list] div.news-list-card");

            if (posts.isEmpty()) {
                System.out.println("No data found.");
                return;
            }
            // Eski en son post tarihini dosyadan oku
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                latestPostDateTimeStr = br.readLine();
                //System.out.println("Last post date read from txt file ");
            } catch (IOException e) {
                System.err.println("Error reading file: " + e.getMessage());
                return;
            }

            // En son postun tarihini tutmak için bir LocalDateTime değişkeni oluştur
            LocalDateTime latestPostDateTime = null;

            //En postun tarihini LocalDateTime türüne dönüştür.
            if (latestPostDateTimeStr != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd 'at' h:mm a", Locale.ENGLISH);
                latestPostDateTime = LocalDateTime.parse(latestPostDateTimeStr, formatter);
            }

            // Her bir postu kontrol et
            Element head = null;
            for (Element post : posts) {

                // Tarih öğesini seç
                Element dateElement = post.selectFirst("span.news-list-card-content-byline-date");
                Element newsTitle = post.selectFirst("h3.heading-size-2");
                Element newsBody = post.selectFirst("div.news-list-card-content-body");
                String imageUrl = post.selectFirst("a.news-list-card-teaser-image").attr("style");
                tag = post.selectFirst("a.news-list-card-type.fa.fa-thumb-tack").text().toUpperCase((Locale.ENGLISH));
                String postUrl1 = post.selectFirst("div.news-list-card a").attr("href");
                if (!postUrl1.contains("https://www.wowhead.com"))
                    postUrl = "https://www.wowhead.com" + postUrl1;
                else {
                    postUrl = postUrl1;
                }
                System.out.println(tag);

                titleNtf = newsTitle.text();
                bodyNtf = newsBody.text();

                if (imageUrl != null) {
                    imageUrlNtf = imageUrl.substring(imageUrl.indexOf("url(") + 4, imageUrl.indexOf(")")); // "url(" ve ")" arasındaki URL'yi al
                }
                if (dateElement != null) {
                    String dateStr = dateElement.attr("title");


                    // Tarih stringini LocalDateTime objesine çevir
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd 'at' h:mm a", Locale.ENGLISH);
                    LocalDateTime postDateTime = LocalDateTime.parse(dateStr, formatter);

                    // En son postun tarihini güncelle

                    if (latestPostDateTime == null || postDateTime.isAfter(latestPostDateTime)) {
                        latestPostDateTime = postDateTime;
                        System.out.println("Yeni post date: " + latestPostDateTime.format(DateTimeFormatter.ofPattern("yyyy/MM/dd 'at' h:mm a", Locale.ENGLISH)));
                        postUpdated = true;


                    }
                }
                break;
            }
            //Yeni post tarihini txt dosyasına yazdırma
            if (postUpdated) {

                try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                    bw.write(latestPostDateTime.format(DateTimeFormatter.ofPattern("yyyy/MM/dd 'at' h:mm a", Locale.ENGLISH)));
                    System.out.println("Yeni post tarihi txt klasorune yazdirildi.");
                } catch (IOException e) {
                    System.err.println("Error writing file: " + e.getMessage());
                    return;
                }


                _initialfirebase.AddAdminSdk();
                _initialfirebase.BuildFireBaseRDb();

               /* FileInputStream serviceAccount = new FileInputStream("wownews-8d9b8-firebase-adminsdk-phqt7-6c6ea24f27.json"); //Düzelt
                FirebaseOptions options = FirebaseOptions.builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .build();
                FirebaseApp.initializeApp(options);
            } catch (IOException e) {
                System.err.println("Error reading Firebase credentials file: " + e.getMessage());
            }
            System.out.println("Firebase initialized");*/

                // Topic adı
                String topic = null;
                if (tag.equals("LIVE") || tag.equals("PTR") || tag.equals("DRAGONFLIGHT") || tag.equals("WOW")) {
                    topic = "wow_live_ptr";
                } else if (tag.equals("DIABLO IV") || tag.equals("DIABLO 4") || tag.equals("DIABLO")) {
                    topic = "diablo_4";
                } else if (tag.equals("WRATH") || tag.equals("WOTLK")) {
                    topic = "wow_wrath";
                } else if (tag.equals("TBC")) {
                    topic = "wow_tbc";
                } else if (tag.equals("CLASSIC")) {
                    topic = "wow_classic";
                } else if (tag.equals("BLIZZARD")) {
                    topic = "blizzard";
                } else if (tag.equals("ARCLIGHT")) {
                    topic = "arclight";
                } else if (tag.equals("DIABLO II") || tag.equals("DIABLO III")) {
                    topic = "d2_d3";
                } else if (tag.equals("IMMORTAL")) {
                    topic = "d_immortal";
                } else if (tag.equals("OVERWATCH")) {
                    topic = "overwatch";
                }

                if (topic.equals("wow_live_ptr") || topic.equals("diablo_4") || topic.equals("wow_wrath") || topic.equals("wow_tbc") || topic.equals("wow_classic") ||
                        topic.equals("blizzard") || topic.equals("arclight") || topic.equals("d2_d3") || topic.equals("d_immortal") || topic.equals("overwatch")) {

                    System.out.println("Topic: " + topic);
                    System.out.println(titleNtf);
                    System.out.println(bodyNtf);
                    System.out.println(imageUrlNtf);
                    System.out.println(postUrl);

                    // Bildirim nesnesi oluşturma
                    Message message = Message.builder()
                            .setNotification(Notification.builder()
                                    .setTitle(titleNtf)
                                    .setBody(bodyNtf)
                                    .setImage(imageUrlNtf)
                                    .build())
                            .setTopic(topic)
                            .putData("url", postUrl)
                            .build();


                    // FCM üzerinden mesaj gönderme
                    try {
                        String response = FirebaseMessaging.getInstance().send(message);
                        System.out.println("Mesaj gönderildi. Response: " + response);
                    } catch (FirebaseMessagingException e) {
                        System.out.println("Mesaj gönderilemedi. Hata kodu: " + e.getErrorCode() + ", Hata: " + e.getMessage());
                    }

                    //FirebaseApp.getInstance().delete();

            }
        }
    }

    @Override
    public void close() throws Exception {

    }
}



