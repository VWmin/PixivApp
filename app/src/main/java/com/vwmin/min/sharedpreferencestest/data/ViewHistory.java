package com.vwmin.min.sharedpreferencestest.data;

import android.view.View;

import com.vwmin.min.sharedpreferencestest.response.IllustBean;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

import java.io.Serializable;


// 一个实例对应一条记录
public class ViewHistory extends LitePalSupport {

    private long id;
    @Column(unique = true)
    private long illust_id;

    public ViewHistory(long id){
        setIllust_id(id);
    }

    @Override
    public boolean save() {
        return super.save();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIllust_id() {
        return illust_id;
    }

    private void setIllust_id(long illust_id) {
        this.illust_id = illust_id;
    }
}
