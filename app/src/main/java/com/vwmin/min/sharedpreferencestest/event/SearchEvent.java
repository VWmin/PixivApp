package com.vwmin.min.sharedpreferencestest.event;

public class SearchEvent {
    private int searchType;
    private String searchContent;

    public SearchEvent(int searchType, String searchContent){
        setSearchType(searchType);
        setSearchContent(searchContent);
    }


    public int getSearchType() {
        return searchType;
    }

    public void setSearchType(int searchType) {
        this.searchType = searchType;
    }

    public String getSearchContent() {
        return searchContent;
    }

    public void setSearchContent(String searchContent) {
        this.searchContent = searchContent;
    }
}
