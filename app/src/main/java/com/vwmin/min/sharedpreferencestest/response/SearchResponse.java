package com.vwmin.min.sharedpreferencestest.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchResponse {

    private String status;
    private List<ResponseBean> response;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ResponseBean> getResponse() {
        return response;
    }

    public void setResponse(List<ResponseBean> response) {
        this.response = response;
    }

    public static class ResponseBean {

        private int id;
        private String title;
        private String caption;
        private ImageUrlsBean image_urls;
        private int width;
        private int height;
        private StatsBean stats;
        private int publicity;
        private String age_limit;
        private String created_time;
        private String reuploaded_time;
        private UserBean user;
        private boolean is_manga;
        private boolean is_liked;
        private int favorite_id;
        private int page_count;
        private String book_style;
        private String type;
        private Object metadata;
        private Object content_type;
        private String sanity_level;
        private List<String> tags;
        private List<String> tools;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCaption() {
            return caption;
        }

        public void setCaption(String caption) {
            this.caption = caption;
        }

        public ImageUrlsBean getImage_urls() {
            return image_urls;
        }

        public void setImage_urls(ImageUrlsBean image_urls) {
            this.image_urls = image_urls;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public StatsBean getStats() {
            return stats;
        }

        public void setStats(StatsBean stats) {
            this.stats = stats;
        }

        public int getPublicity() {
            return publicity;
        }

        public void setPublicity(int publicity) {
            this.publicity = publicity;
        }

        public String getAge_limit() {
            return age_limit;
        }

        public void setAge_limit(String age_limit) {
            this.age_limit = age_limit;
        }

        public String getCreated_time() {
            return created_time;
        }

        public void setCreated_time(String created_time) {
            this.created_time = created_time;
        }

        public String getReuploaded_time() {
            return reuploaded_time;
        }

        public void setReuploaded_time(String reuploaded_time) {
            this.reuploaded_time = reuploaded_time;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public boolean isIs_manga() {
            return is_manga;
        }

        public void setIs_manga(boolean is_manga) {
            this.is_manga = is_manga;
        }

        public boolean isIs_liked() {
            return is_liked;
        }

        public void setIs_liked(boolean is_liked) {
            this.is_liked = is_liked;
        }

        public int getFavorite_id() {
            return favorite_id;
        }

        public void setFavorite_id(int favorite_id) {
            this.favorite_id = favorite_id;
        }

        public int getPage_count() {
            return page_count;
        }

        public void setPage_count(int page_count) {
            this.page_count = page_count;
        }

        public String getBook_style() {
            return book_style;
        }

        public void setBook_style(String book_style) {
            this.book_style = book_style;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Object getMetadata() {
            return metadata;
        }

        public void setMetadata(Object metadata) {
            this.metadata = metadata;
        }

        public Object getContent_type() {
            return content_type;
        }

        public void setContent_type(Object content_type) {
            this.content_type = content_type;
        }

        public String getSanity_level() {
            return sanity_level;
        }

        public void setSanity_level(String sanity_level) {
            this.sanity_level = sanity_level;
        }

        public List<String> getTags() {
            return tags;
        }

        public void setTags(List<String> tags) {
            this.tags = tags;
        }

        public List<String> getTools() {
            return tools;
        }

        public void setTools(List<String> tools) {
            this.tools = tools;
        }

        public static class ImageUrlsBean {

            private String px_128x128;
            private String px_480mw;
            private String large;

            public String getPx_128x128() {
                return px_128x128;
            }

            public void setPx_128x128(String px_128x128) {
                this.px_128x128 = px_128x128;
            }

            public String getPx_480mw() {
                return px_480mw;
            }

            public void setPx_480mw(String px_480mw) {
                this.px_480mw = px_480mw;
            }

            public String getLarge() {
                return large;
            }

            public void setLarge(String large) {
                this.large = large;
            }
        }

        public static class StatsBean {

            private int scored_count;
            private int score;
            private int views_count;
            private FavoritedCountBean favorited_count;
            private int commented_count;

            public int getScored_count() {
                return scored_count;
            }

            public void setScored_count(int scored_count) {
                this.scored_count = scored_count;
            }

            public int getScore() {
                return score;
            }

            public void setScore(int score) {
                this.score = score;
            }

            public int getViews_count() {
                return views_count;
            }

            public void setViews_count(int views_count) {
                this.views_count = views_count;
            }

            public FavoritedCountBean getFavorited_count() {
                return favorited_count;
            }

            public void setFavorited_count(FavoritedCountBean favorited_count) {
                this.favorited_count = favorited_count;
            }

            public int getCommented_count() {
                return commented_count;
            }

            public void setCommented_count(int commented_count) {
                this.commented_count = commented_count;
            }

            public static class FavoritedCountBean {


                @SerializedName("public")
                private int publicX;
                @SerializedName("private")
                private int privateX;

                public int getPublicX() {
                    return publicX;
                }

                public void setPublicX(int publicX) {
                    this.publicX = publicX;
                }

                public int getPrivateX() {
                    return privateX;
                }

                public void setPrivateX(int privateX) {
                    this.privateX = privateX;
                }
            }
        }

        public static class UserBean {


            private int id;
            private String account;
            private String name;
            private boolean is_following;
            private boolean is_follower;
            private boolean is_friend;
            private Object is_premium;
            private ProfileImageUrlsBean profile_image_urls;
            private Object stats;
            private Object profile;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getAccount() {
                return account;
            }

            public void setAccount(String account) {
                this.account = account;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public boolean isIs_following() {
                return is_following;
            }

            public void setIs_following(boolean is_following) {
                this.is_following = is_following;
            }

            public boolean isIs_follower() {
                return is_follower;
            }

            public void setIs_follower(boolean is_follower) {
                this.is_follower = is_follower;
            }

            public boolean isIs_friend() {
                return is_friend;
            }

            public void setIs_friend(boolean is_friend) {
                this.is_friend = is_friend;
            }

            public Object getIs_premium() {
                return is_premium;
            }

            public void setIs_premium(Object is_premium) {
                this.is_premium = is_premium;
            }

            public ProfileImageUrlsBean getProfile_image_urls() {
                return profile_image_urls;
            }

            public void setProfile_image_urls(ProfileImageUrlsBean profile_image_urls) {
                this.profile_image_urls = profile_image_urls;
            }

            public Object getStats() {
                return stats;
            }

            public void setStats(Object stats) {
                this.stats = stats;
            }

            public Object getProfile() {
                return profile;
            }

            public void setProfile(Object profile) {
                this.profile = profile;
            }

            public static class ProfileImageUrlsBean {

                private String px_50x50;

                public String getPx_50x50() {
                    return px_50x50;
                }

                public void setPx_50x50(String px_50x50) {
                    this.px_50x50 = px_50x50;
                }
            }
        }
    }


//    private String next_url;
//    private List<IllustB> illusts;
//    private long search_span_limit;
//
//    private class IllustB implements Serializable {
//        private int id;
//        private String title;
//        private String type;
//        private ImageUrlsBean image_urls;
//        private String caption;
//        private int restrict;
//        private UserBean user;
//        private String create_date;
//        private int page_count;
//        private int width;
//        private int height;
//        private int sanity_level;
//        private int x_restrict;
//        private Object series;
//        private List<String> meta_single_page;
//        private int total_view;
//        private int total_bookmarks;
//        private boolean is_bookmarked;
//        private boolean visible;
//        private boolean is_muted;
//        private List<TagsBean> tags;
//        private List<String> tools;
//        private List<MetaPagesBean> meta_pages;
//
//
//        public int getId() {
//            return id;
//        }
//
//        public void setId(int id) {
//            this.id = id;
//        }
//
//        public String getTitle() {
//            return title;
//        }
//
//        public void setTitle(String title) {
//            this.title = title;
//        }
//
//        public String getType() {
//            return type;
//        }
//
//        public void setType(String type) {
//            this.type = type;
//        }
//
//        public ImageUrlsBean getImage_urls() {
//            return image_urls;
//        }
//
//        public void setImage_urls(ImageUrlsBean image_urls) {
//            this.image_urls = image_urls;
//        }
//
//        public String getCaption() {
//            return caption;
//        }
//
//        public void setCaption(String caption) {
//            this.caption = caption;
//        }
//
//        public int getRestrict() {
//            return restrict;
//        }
//
//        public void setRestrict(int restrict) {
//            this.restrict = restrict;
//        }
//
//        public UserBean getUser() {
//            return user;
//        }
//
//        public void setUser(UserBean user) {
//            this.user = user;
//        }
//
//        public String getCreate_date() {
//            return create_date;
//        }
//
//        public void setCreate_date(String create_date) {
//            this.create_date = create_date;
//        }
//
//        public int getPage_count() {
//            return page_count;
//        }
//
//        public void setPage_count(int page_count) {
//            this.page_count = page_count;
//        }
//
//        public int getWidth() {
//            return width;
//        }
//
//        public void setWidth(int width) {
//            this.width = width;
//        }
//
//        public int getHeight() {
//            return height;
//        }
//
//        public void setHeight(int height) {
//            this.height = height;
//        }
//
//        public int getSanity_level() {
//            return sanity_level;
//        }
//
//        public void setSanity_level(int sanity_level) {
//            this.sanity_level = sanity_level;
//        }
//
//        public int getX_restrict() {
//            return x_restrict;
//        }
//
//        public void setX_restrict(int x_restrict) {
//            this.x_restrict = x_restrict;
//        }
//
//        public Object getSeries() {
//            return series;
//        }
//
//        public void setSeries(Object series) {
//            this.series = series;
//        }
//
//        public List<String> getMeta_single_page() {
//            return meta_single_page;
//        }
//
//        public void setMeta_single_page(List<String> meta_single_page) {
//            this.meta_single_page = meta_single_page;
//        }
//
//        public int getTotal_view() {
//            return total_view;
//        }
//
//        public void setTotal_view(int total_view) {
//            this.total_view = total_view;
//        }
//
//        public int getTotal_bookmarks() {
//            return total_bookmarks;
//        }
//
//        public void setTotal_bookmarks(int total_bookmarks) {
//            this.total_bookmarks = total_bookmarks;
//        }
//
//        public boolean isIs_bookmarked() {
//            return is_bookmarked;
//        }
//
//        public void setIs_bookmarked(boolean is_bookmarked) {
//            this.is_bookmarked = is_bookmarked;
//        }
//
//        public boolean isVisible() {
//            return visible;
//        }
//
//        public void setVisible(boolean visible) {
//            this.visible = visible;
//        }
//
//        public boolean isIs_muted() {
//            return is_muted;
//        }
//
//        public void setIs_muted(boolean is_muted) {
//            this.is_muted = is_muted;
//        }
//
//        public List<TagsBean> getTags() {
//            return tags;
//        }
//
//        public void setTags(List<TagsBean> tags) {
//            this.tags = tags;
//        }
//
//        public List<String> getTools() {
//            return tools;
//        }
//
//        public void setTools(List<String> tools) {
//            this.tools = tools;
//        }
//
//        public List<MetaPagesBean> getMeta_pages() {
//            return meta_pages;
//        }
//
//        public void setMeta_pages(List<MetaPagesBean> meta_pages) {
//            this.meta_pages = meta_pages;
//        }
//
//        public class ImageUrlsBean implements Serializable{
//
//
//            private String square_medium;
//            private String medium;
//            private String large;
//            private String original;
//
//            public String getSquare_medium() {
//                return square_medium;
//            }
//
//            public void setSquare_medium(String square_medium) {
//                this.square_medium = square_medium;
//            }
//
//            public String getMedium() {
//                return medium;
//            }
//
//            public void setMedium(String medium) {
//                this.medium = medium;
//            }
//
//            public String getLarge() {
//                return large;
//            }
//
//            public void setLarge(String large) {
//                this.large = large;
//            }
//
//            public String getOriginal() {
//                return original;
//            }
//
//            public void setOriginal(String original) {
//                this.original = original;
//            }
//        }
//
//        public class MetaSinglePageBean implements Serializable{
//
//
//            private String original_image_url;
//
//            public String getOriginal_image_url() {
//                return original_image_url;
//            }
//
//            public void setOriginal_image_url(String original_image_url) {
//                this.original_image_url = original_image_url;
//            }
//        }
//
//        public class TagsBean implements Serializable{
//
//            private String name;
//
//            public String getName() {
//                return name;
//            }
//
//            public void setName(String name) {
//                this.name = name;
//            }
//        }
//
//        public class MetaPagesBean implements Serializable {
//
//            private ImageUrlsBean image_urls;
//
//            public ImageUrlsBean getImage_urls() {
//                return image_urls;
//            }
//
//
//        }
//
//        public class UserBean implements Serializable {
//
//            private int id;
//            private String name;
//            private String account;
//            private ProfileImageUrlsBean profile_image_urls;
//            private String comment;
//            private boolean is_followed;
//
//            public int getId() {
//                return id;
//            }
//
//            public void setId(int id) {
//                this.id = id;
//            }
//
//            public String getName() {
//                return name;
//            }
//
//            public void setName(String name) {
//                this.name = name;
//            }
//
//            public String getAccount() {
//                return account;
//            }
//
//            public void setAccount(String account) {
//                this.account = account;
//            }
//
//            public ProfileImageUrlsBean getProfile_image_urls() {
//                return profile_image_urls;
//            }
//
//            public void setProfile_image_urls(ProfileImageUrlsBean profile_image_urls) {
//                this.profile_image_urls = profile_image_urls;
//            }
//
//            public String getComment() {
//                return comment;
//            }
//
//            public void setComment(String comment) {
//                this.comment = comment;
//            }
//
//            public boolean isIs_followed() {
//                return is_followed;
//            }
//
//            public void setIs_followed(boolean is_followed) {
//                this.is_followed = is_followed;
//            }
//
//            public class ProfileImageUrlsBean implements Serializable{
//
//
//                private String medium;
//
//                public String getMedium() {
//                    return medium;
//                }
//
//                public void setMedium(String medium) {
//                    this.medium = medium;
//                }
//            }
//        }
//    }
//
//
//
//    public String getNext_url() {
//        return next_url;
//    }
//
//    public void setNext_url(String next_url) {
//        this.next_url = next_url;
//    }
//
//    public List<IllustB> getIllusts() {
//        return illusts;
//    }
//
//    public void setIllusts(List<IllustB> illusts) {
//        this.illusts = illusts;
//    }
//
//    public long getSearch_span_limit() {
//        return search_span_limit;
//    }
//
//    public void setSearch_span_limit(long search_span_limit) {
//        this.search_span_limit = search_span_limit;
//    }
}
