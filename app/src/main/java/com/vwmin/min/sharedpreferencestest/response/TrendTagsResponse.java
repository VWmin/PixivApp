package com.vwmin.min.sharedpreferencestest.response;

import java.util.List;

public class TrendTagsResponse {


    private List<TrendTagsBean> trend_tags;

    public List<TrendTagsBean> getTrend_tags() {
        return trend_tags;
    }

    public void setTrend_tags(List<TrendTagsBean> trend_tags) {
        this.trend_tags = trend_tags;
    }

    public static class TrendTagsBean {
        /**
         * tag : 黒タイツ
         * illust : {"id":73209279,"title":"三玖","type":"illust","image_urls":{"square_medium":"https://i.pximg.net/c/360x360_70/img-master/img/2019/02/16/03/22/26/73209279_p0_square1200.jpg","medium":"https://i.pximg.net/c/540x540_70/img-master/img/2019/02/16/03/22/26/73209279_p0_master1200.jpg","large":"https://i.pximg.net/c/600x1200_90/img-master/img/2019/02/16/03/22/26/73209279_p0_master1200.jpg"},"caption":"僕は三玖推しです！！！","restrict":0,"user":{"id":2744600,"name":"fu-ta","account":"fu_fu_ta","profile_image_urls":{"medium":"https://i.pximg.net/user-profile/img/2016/05/07/23/13/24/10903439_4755a6f2ae0ddde911cb7b416120b17e_170.jpg"},"is_followed":false},"tags":[{"name":"五等分の花嫁"},{"name":"中野三玖"},{"name":"五等分の花嫁1000users入り"},{"name":"黒タイツ"}],"tools":["Photoshop"],"create_date":"2019-02-16T03:22:26+09:00","page_count":1,"width":827,"height":1169,"sanity_level":2,"x_restrict":0,"series":null,"meta_single_page":{"original_image_url":"https://i.pximg.net/img-original/img/2019/02/16/03/22/26/73209279_p0.jpg"},"meta_pages":[],"total_view":24225,"total_bookmarks":3487,"is_bookmarked":false,"visible":true,"is_muted":false}
         */

        private String tag;
        private IllustBean illust;

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public IllustBean getIllust() {
            return illust;
        }

        public void setIllust(IllustBean illust) {
            this.illust = illust;
        }

        public static class IllustBean {
            /**
             * id : 73209279
             * title : 三玖
             * type : illust
             * image_urls : {"square_medium":"https://i.pximg.net/c/360x360_70/img-master/img/2019/02/16/03/22/26/73209279_p0_square1200.jpg","medium":"https://i.pximg.net/c/540x540_70/img-master/img/2019/02/16/03/22/26/73209279_p0_master1200.jpg","large":"https://i.pximg.net/c/600x1200_90/img-master/img/2019/02/16/03/22/26/73209279_p0_master1200.jpg"}
             * caption : 僕は三玖推しです！！！
             * restrict : 0
             * user : {"id":2744600,"name":"fu-ta","account":"fu_fu_ta","profile_image_urls":{"medium":"https://i.pximg.net/user-profile/img/2016/05/07/23/13/24/10903439_4755a6f2ae0ddde911cb7b416120b17e_170.jpg"},"is_followed":false}
             * tags : [{"name":"五等分の花嫁"},{"name":"中野三玖"},{"name":"五等分の花嫁1000users入り"},{"name":"黒タイツ"}]
             * tools : ["Photoshop"]
             * create_date : 2019-02-16T03:22:26+09:00
             * page_count : 1
             * width : 827
             * height : 1169
             * sanity_level : 2
             * x_restrict : 0
             * series : null
             * meta_single_page : {"original_image_url":"https://i.pximg.net/img-original/img/2019/02/16/03/22/26/73209279_p0.jpg"}
             * meta_pages : []
             * total_view : 24225
             * total_bookmarks : 3487
             * is_bookmarked : false
             * visible : true
             * is_muted : false
             */

            private int id;
            private String title;
            private String type;
            private ImageUrlsBean image_urls;
            private String caption;
            private int restrict;
            private UserBean user;
            private String create_date;
            private int page_count;
            private int width;
            private int height;
            private int sanity_level;
            private int x_restrict;
            private Object series;
            private MetaSinglePageBean meta_single_page;
            private int total_view;
            private int total_bookmarks;
            private boolean is_bookmarked;
            private boolean visible;
            private boolean is_muted;
            private List<TagsBean> tags;
            private List<String> tools;
            private List<?> meta_pages;

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

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public ImageUrlsBean getImage_urls() {
                return image_urls;
            }

            public void setImage_urls(ImageUrlsBean image_urls) {
                this.image_urls = image_urls;
            }

            public String getCaption() {
                return caption;
            }

            public void setCaption(String caption) {
                this.caption = caption;
            }

            public int getRestrict() {
                return restrict;
            }

            public void setRestrict(int restrict) {
                this.restrict = restrict;
            }

            public UserBean getUser() {
                return user;
            }

            public void setUser(UserBean user) {
                this.user = user;
            }

            public String getCreate_date() {
                return create_date;
            }

            public void setCreate_date(String create_date) {
                this.create_date = create_date;
            }

            public int getPage_count() {
                return page_count;
            }

            public void setPage_count(int page_count) {
                this.page_count = page_count;
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

            public int getSanity_level() {
                return sanity_level;
            }

            public void setSanity_level(int sanity_level) {
                this.sanity_level = sanity_level;
            }

            public int getX_restrict() {
                return x_restrict;
            }

            public void setX_restrict(int x_restrict) {
                this.x_restrict = x_restrict;
            }

            public Object getSeries() {
                return series;
            }

            public void setSeries(Object series) {
                this.series = series;
            }

            public MetaSinglePageBean getMeta_single_page() {
                return meta_single_page;
            }

            public void setMeta_single_page(MetaSinglePageBean meta_single_page) {
                this.meta_single_page = meta_single_page;
            }

            public int getTotal_view() {
                return total_view;
            }

            public void setTotal_view(int total_view) {
                this.total_view = total_view;
            }

            public int getTotal_bookmarks() {
                return total_bookmarks;
            }

            public void setTotal_bookmarks(int total_bookmarks) {
                this.total_bookmarks = total_bookmarks;
            }

            public boolean isIs_bookmarked() {
                return is_bookmarked;
            }

            public void setIs_bookmarked(boolean is_bookmarked) {
                this.is_bookmarked = is_bookmarked;
            }

            public boolean isVisible() {
                return visible;
            }

            public void setVisible(boolean visible) {
                this.visible = visible;
            }

            public boolean isIs_muted() {
                return is_muted;
            }

            public void setIs_muted(boolean is_muted) {
                this.is_muted = is_muted;
            }

            public List<TagsBean> getTags() {
                return tags;
            }

            public void setTags(List<TagsBean> tags) {
                this.tags = tags;
            }

            public List<String> getTools() {
                return tools;
            }

            public void setTools(List<String> tools) {
                this.tools = tools;
            }

            public List<?> getMeta_pages() {
                return meta_pages;
            }

            public void setMeta_pages(List<?> meta_pages) {
                this.meta_pages = meta_pages;
            }

            public static class ImageUrlsBean {
                /**
                 * square_medium : https://i.pximg.net/c/360x360_70/img-master/img/2019/02/16/03/22/26/73209279_p0_square1200.jpg
                 * medium : https://i.pximg.net/c/540x540_70/img-master/img/2019/02/16/03/22/26/73209279_p0_master1200.jpg
                 * large : https://i.pximg.net/c/600x1200_90/img-master/img/2019/02/16/03/22/26/73209279_p0_master1200.jpg
                 */

                private String square_medium;
                private String medium;
                private String large;

                public String getSquare_medium() {
                    return square_medium;
                }

                public void setSquare_medium(String square_medium) {
                    this.square_medium = square_medium;
                }

                public String getMedium() {
                    return medium;
                }

                public void setMedium(String medium) {
                    this.medium = medium;
                }

                public String getLarge() {
                    return large;
                }

                public void setLarge(String large) {
                    this.large = large;
                }
            }

            public static class UserBean {
                /**
                 * id : 2744600
                 * name : fu-ta
                 * account : fu_fu_ta
                 * profile_image_urls : {"medium":"https://i.pximg.net/user-profile/img/2016/05/07/23/13/24/10903439_4755a6f2ae0ddde911cb7b416120b17e_170.jpg"}
                 * is_followed : false
                 */

                private int id;
                private String name;
                private String account;
                private ProfileImageUrlsBean profile_image_urls;
                private boolean is_followed;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
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

                public ProfileImageUrlsBean getProfile_image_urls() {
                    return profile_image_urls;
                }

                public void setProfile_image_urls(ProfileImageUrlsBean profile_image_urls) {
                    this.profile_image_urls = profile_image_urls;
                }

                public boolean isIs_followed() {
                    return is_followed;
                }

                public void setIs_followed(boolean is_followed) {
                    this.is_followed = is_followed;
                }

                public static class ProfileImageUrlsBean {
                    /**
                     * medium : https://i.pximg.net/user-profile/img/2016/05/07/23/13/24/10903439_4755a6f2ae0ddde911cb7b416120b17e_170.jpg
                     */

                    private String medium;

                    public String getMedium() {
                        return medium;
                    }

                    public void setMedium(String medium) {
                        this.medium = medium;
                    }
                }
            }

            public static class MetaSinglePageBean {
                /**
                 * original_image_url : https://i.pximg.net/img-original/img/2019/02/16/03/22/26/73209279_p0.jpg
                 */

                private String original_image_url;

                public String getOriginal_image_url() {
                    return original_image_url;
                }

                public void setOriginal_image_url(String original_image_url) {
                    this.original_image_url = original_image_url;
                }
            }

            public static class TagsBean {
                /**
                 * name : 五等分の花嫁
                 */

                private String name;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }
            }
        }
    }
}
