package com.vwmin.min.sharedpreferencestest.response;

import java.util.List;



public class IllustsResponse {


    private String next_url;
    private List<IllustBean> illusts;



    public String getNext_url() {
        return next_url;
    }

    public void setNext_url(String next_url) {
        this.next_url = next_url;
    }

    public List<IllustBean> getIllusts() {
        return illusts;
    }

    public void setIllusts(List<IllustBean> illusts) {
        this.illusts = illusts;
    }
}
