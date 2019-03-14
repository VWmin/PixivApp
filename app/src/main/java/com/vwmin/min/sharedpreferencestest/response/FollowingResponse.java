package com.vwmin.min.sharedpreferencestest.response;

import java.util.List;

public class FollowingResponse {


    private List<UserPreviewsBean> user_previews;

    public List<UserPreviewsBean> getUser_previews() {
        return user_previews;
    }

    public void setUser_previews(List<UserPreviewsBean> user_previews) {
        this.user_previews = user_previews;
    }

    public static class UserPreviewsBean {
        /**
         * user : {"id":7210261,"name":"千夜QYS3","account":"qianye_qys3","profile_image_urls":{"medium":"https://i.pximg.net/user-profile/img/2017/11/28/00/46/09/13503196_920df8e84e4d1061e4dcdbc9ce9a4f7b_170.png"},"is_followed":true}
         * illusts : []
         * next_url : null
         */

        private IllustBean.UserBean user;
        private String next_url;
        private List<IllustBean> illusts;

        public IllustBean.UserBean getUser() {
            return user;
        }

        public void setUser(IllustBean.UserBean user) {
            this.user = user;
        }

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
}
