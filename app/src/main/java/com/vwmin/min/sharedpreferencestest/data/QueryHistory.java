package com.vwmin.min.sharedpreferencestest.data;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;
import org.litepal.Operator;

import java.util.List;

public class QueryHistory extends LitePalSupport implements Comparable<QueryHistory> {

    private int id;
    private long time;
    private String queryContent;

    public QueryHistory(String queryContent){
        setQueryContent(queryContent);
        setTime();
    }

    public QueryHistory(){}

    @Override
    public boolean save() {
        List<QueryHistory> matches = Operator.where("queryContent = ?", getQueryContent())
                .find(QueryHistory.class);
        if (matches.size()!=0){
            matches.get(0).setTime();
            matches.get(0).update(getId());
            return true;
        }else return super.save();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void setTime(){this.time = System.currentTimeMillis();}

    public String getQueryContent() {
        return queryContent;
    }

    public void setQueryContent(String queryContent) {
        this.queryContent = queryContent;
    }

    @Override
    public int compareTo(QueryHistory other) {
        return Long.compare(other.time, this.time);
    }

}
