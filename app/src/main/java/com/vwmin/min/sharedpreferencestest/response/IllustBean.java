package com.vwmin.min.sharedpreferencestest.response;



import java.io.Serializable;
import java.util.List;

public class IllustBean implements Serializable {


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
    private List<MetaPagesBean> meta_pages;


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

    public List<MetaPagesBean> getMeta_pages() {
        return meta_pages;
    }

    public void setMeta_pages(List<MetaPagesBean> meta_pages) {
        this.meta_pages = meta_pages;
    }

    public class ImageUrlsBean implements Serializable{


        private String square_medium;
        private String medium;
        private String large;
        private String original;

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

        public String getOriginal() {
            return original;
        }

        public void setOriginal(String original) {
            this.original = original;
        }
    }

    public class MetaSinglePageBean implements Serializable{


        private String original_image_url;

        public String getOriginal_image_url() {
            return original_image_url;
        }

        public void setOriginal_image_url(String original_image_url) {
            this.original_image_url = original_image_url;
        }
    }

    public class TagsBean implements Serializable{

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public class MetaPagesBean implements Serializable {

        private ImageUrlsBean image_urls;

        public ImageUrlsBean getImage_urls() {
            return image_urls;
        }


    }

    public class UserBean implements Serializable {

        private int id;
        private String name;
        private String account;
        private ProfileImageUrlsBean profile_image_urls;
        private String comment;
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

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public boolean isIs_followed() {
            return is_followed;
        }

        public void setIs_followed(boolean is_followed) {
            this.is_followed = is_followed;
        }

        public class ProfileImageUrlsBean implements Serializable{


            private String medium;

            public String getMedium() {
                return medium;
            }

            public void setMedium(String medium) {
                this.medium = medium;
            }
        }
    }
}
