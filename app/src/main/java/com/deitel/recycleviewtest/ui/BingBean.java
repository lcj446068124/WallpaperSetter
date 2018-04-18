package com.deitel.recycleviewtest.ui;

import com.deitel.recycleviewtest.base.BaseBean;
import com.deitel.recycleviewtest.base.Contracts;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BingBean {
    @SerializedName("images")
    private List<ImageBean> images;

    public List<ImageBean> getImages() {
        return images;
    }

    public void setImages(List<ImageBean> images) {
        this.images = images;
    }

    public static class ImageBean extends BaseBean {
        @SerializedName("enddate")
        private String enddate;
        @SerializedName("url")
        private String url;
        @SerializedName("copyright")
        private String copyright;

        public String getEnddate() {
            return enddate;
        }

        public void setEnddate(String enddate) {
            this.enddate = enddate;
        }

        public String getUrl() {
            return Contracts.Url.BING_BASE + url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getCopyright() {
            return copyright;
        }

        public void setCopyright(String copyright) {
            this.copyright = copyright;
        }


    }

}
