package com.vwmin.min.sharedpreferencestest.event;

import com.vwmin.min.sharedpreferencestest.response.Illust;

public class IllustChangeEvent {
    private Illust newIllust;

    public IllustChangeEvent(Illust newIllust){
        this.newIllust = newIllust;
    }

    public Illust getNewIllust() {
        return newIllust;
    }

    public void setNewIllust(Illust newIllust) {
        this.newIllust = newIllust;
    }
}


