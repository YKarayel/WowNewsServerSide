package com.nexuswawe.serverwownews.Repo;



import com.nexuswawe.serverwownews.Core.WowheadNews;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ScrapData {

    public static List<WowheadNews> scrapData() throws IOException, InterruptedException {

        List<WowheadNews> wowheadNewsList = new ArrayList<>();
        String x = "*";

        try {
            for (int i = 1; i <= 3; i++) {
                String url = "https://www.wowhead.com/news?page=" + i;
                Document doc = Jsoup.connect(url).get();
                Thread.sleep(1000);
                Elements newsDivs = doc.select("div.news-list[data-zaf-dynamic=list] div.news-list-card");


                WowheadNews wowheadNews = null;
                for (Element newsDiv : newsDivs) {
                    Element dateElement = newsDiv.selectFirst("span.news-list-card-content-byline-date");
                    Element newsTitle = newsDiv.selectFirst("h3.heading-size-2");
                    String newsImage = newsDiv.selectFirst("a.news-list-card-teaser-image").attr("style");
                    String postUrl1 = newsDiv.selectFirst("div.news-list-card a").attr("href");
                    String dateStr = dateElement.attr("title");
                    String tag = newsDiv.selectFirst("a.news-list-card-type.fa.fa-thumb-tack").text().toUpperCase();

                    //Check Urls
                    String postUrl;
                    if (!postUrl1.contains("https://www.wowhead.com"))
                        postUrl = "https://www.wowhead.com" + postUrl1;
                    else {
                        postUrl = postUrl1;
                    }

                    //Escape from image null exception
                    if (newsImage.isEmpty() || newsImage == null) {

                        wowheadNews = new WowheadNews(newsTitle.text(), null, postUrl, dateStr, tag);

                    } else {
                        String imageUrl = newsImage.substring(newsImage.indexOf("url(") + 4, newsImage.indexOf(")")); // "url(" ve ")" arasındaki URL'yi al
                        wowheadNews = new WowheadNews(newsTitle.text(), imageUrl, postUrl, dateStr, tag);
                    }

                    System.out.print(x + " ");
                    wowheadNewsList.add(wowheadNews);
                }
            }


        }catch(Exception ex){
            ex.printStackTrace();
        }

        System.out.println(" ");
        System.out.println(wowheadNewsList.size() + "Post added to RT DB");

        return wowheadNewsList;

    }

    public static List<String> WowToken() throws IOException {

        List<String> wowTokensList = new ArrayList<>();
        String url = "https://wowtokenprices.com/";
        Document doc = null;

        doc = Jsoup.connect(url).get();

        String euTokenPrice = doc.select("span#eu-money-text.odometer").text();
        String naTokenPrice = doc.select("span#us-money-text.odometer").text();
        wowTokensList.add("EU Token Price: " + euTokenPrice);
        wowTokensList.add("NA Token Price: " + naTokenPrice);
        System.out.println("Tokens : " + wowTokensList);


        return wowTokensList;
    }

}