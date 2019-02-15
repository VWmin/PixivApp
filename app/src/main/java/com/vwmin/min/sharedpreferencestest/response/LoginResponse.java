package com.vwmin.min.sharedpreferencestest.response;

public class LoginResponse {


    private Response response;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public class Response{
        private String access_token;
        private int expires_in;
        private String token_type;
        private String scope;
        private String refresh_token;
        private User user;

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public class User{
            private Profile_image_urls profile_image_urls;

            public Profile_image_urls getProfile_image_urls() {
                return profile_image_urls;
            }

            public void setProfile_image_urls(Profile_image_urls profile_image_urls) {
                this.profile_image_urls = profile_image_urls;
            }

            public class Profile_image_urls{
               private String  px_16x16;
               private String  px_50x50;
               private String  px_170x170;

                public String getPx_16x16() {
                    return px_16x16;
                }

                public void setPx_16x16(String px_16x16) {
                    this.px_16x16 = px_16x16;
                }

                public String getPx_50x50() {
                    return px_50x50;
                }

                public void setPx_50x50(String px_50x50) {
                    this.px_50x50 = px_50x50;
                }

                public String getPx_170x170() {
                    return px_170x170;
                }

                public void setPx_170x170(String px_170x170) {
                    this.px_170x170 = px_170x170;
                }


            }
            private String id;
            private String name;
            private String account;
            private String mail_address;
            private boolean is_premium;
            private int     x_restrict;
            private boolean is_mail_authorized;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getAccount() {
                return account;
            }

            public void setAccount(String account) {
                this.account = account;
            }

            public String getMail_address() {
                return mail_address;
            }

            public void setMail_address(String mail_address) {
                this.mail_address = mail_address;
            }

            public boolean isIs_premium() {
                return is_premium;
            }

            public void setIs_premium(boolean is_premium) {
                this.is_premium = is_premium;
            }

            public int getX_restrict() {
                return x_restrict;
            }

            public void setX_restrict(int x_restrict) {
                this.x_restrict = x_restrict;
            }

            public boolean isIs_mail_authorized() {
                return is_mail_authorized;
            }

            public void setIs_mail_authorized(boolean is_mail_authorized) {
                this.is_mail_authorized = is_mail_authorized;
            }
        }

        private String device_token;

        public String getAccess_token() {
            return access_token;
        }

        public void setAccess_token(String access_token) {
            this.access_token = access_token;
        }

        public int getExpires_in() {
            return expires_in;
        }

        public void setExpires_in(int expires_in) {
            this.expires_in = expires_in;
        }

        public String getToken_type() {
            return token_type;
        }

        public void setToken_type(String token_type) {
            this.token_type = token_type;
        }

        public String getScope() {
            return scope;
        }

        public void setScope(String scope) {
            this.scope = scope;
        }

        public String getRefresh_token() {
            return refresh_token;
        }

        public void setRefresh_token(String refresh_token) {
            this.refresh_token = refresh_token;
        }

        public String getDevice_token() {
            return device_token;
        }

        public void setDevice_token(String device_token) {
            this.device_token = device_token;
        }
    }

}
