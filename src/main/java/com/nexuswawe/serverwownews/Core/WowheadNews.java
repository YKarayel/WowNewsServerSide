package com.nexuswawe.serverwownews.Core;

public class WowheadNews {

    private String title;
    private String body;
    private String imageUrl;
    private String postUrl;
    private String dateStr;

    private String tag;

    public WowheadNews(String title, String imageUrl, String postUrl, String dateStr, String tag) {

        this.title = title;
        this.imageUrl = imageUrl;
        this.postUrl = postUrl;
        this.dateStr = dateStr;
        this.tag = tag;


    }

    // Getter ve Setter metotları buraya eklenebilir


    @Override
    public String toString() {
        return ", \ntitle='" + title + '\'' +
                ", \nimageUrl='" + imageUrl + '\'' +
                ", \npostUrl='" + postUrl + '\'' +
                ", \ndateStr='" + dateStr + '\'' +
                ", \ntag='" + tag + '\'' +
                '}';
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPostUrl() {
        return postUrl;
    }

    public void setPostUrl(String postUrl) {
        this.postUrl = postUrl;
    }

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

}

