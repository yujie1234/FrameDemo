package com.example.moddle_main.bean;

import android.support.annotation.NonNull;

import com.android.common.util.StringUtils;
import com.android.common.util.TimeUtils;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

public class NewsItemBean implements MultiItemEntity,Comparable<NewsItemBean> {

//    public static final int TEXT = 1;
    public static final int PICTURE_1 = 2;
    public static final int PICTURE_2 = 3;
    public static final int PICTURE_3 = 4;
    /**
     * template : normal1
     * lmodify : 2018-07-02 14:08:18
     * source : 澎湃新闻
     * postid : DLND1I590001875N
     * title : 南部战区海军某驱逐舰支队迎来新战舰:呼和浩特舰
     * mtime : 2018-07-02 14:08:18
     * hasImg : 1
     * topic_background : http://img2.cache.netease.com/m/newsapp/reading/cover1/C1348646712614.jpg
     * digest : 军方权威媒体近日公开的新闻报道低调披露，解放军南部战区海军某驱逐舰支队迎来一艘新战舰：呼和浩特舰。南部战区微信公众号6月30日发布的《传承红色基因铸牢铁拳军魂—
     * boardid : news_guonei8_bbs
     * alias : Top News
     * hasAD : 1
     * imgsrc : http://cms-bucket.nosdn.127.net/2018/07/02/fdb229543fe04cbb83b02c41b4c6ba2f.png
     * ptime : 2018-07-02 13:50:11
     * daynum : 17714
     * hasHead : 1
     * order : 1
     * votecount : 0
     * hasCover : false
     * docid : DLND1I590001875N
     * tname : 头条
     * url_3w : http://news.163.com/18/0702/13/DLND1I590001875N.html
     * priority : 115
     * url : http://3g.163.com/news/18/0702/13/DLND1I590001875N.html
     * ads : [{"subtitle":"","skipType":"photoset","skipID":"00AP0001|2294652","tag":"photoset","title":"男子苦寻女儿13年 她已成美国学霸","imgsrc":"bigimg","url":"00AP0001|2294652"},{"subtitle":"","skipType":"photoset","skipID":"00AP0001|2294632","tag":"photoset","title":"小学举行毕业典礼 小学生穿\"博士服\"","imgsrc":"bigimg","url":"00AP0001|2294632"},{"subtitle":"","skipType":"photoset","skipID":"00AP0001|2294629","tag":"photoset","title":"沈阳持续高温 \"大炮\"出动上街降温","imgsrc":"bigimg","url":"00AP0001|2294629"},{"subtitle":"","skipType":"photoset","skipID":"00AP0001|2294628","tag":"photoset","title":"周立波持枪案撤诉后回国 获粉丝送花","imgsrc":"bigimg","url":"00AP0001|2294628"},{"subtitle":"","skipType":"photoset","skipID":"00AP0001|2294611","tag":"photoset","title":"学霸频频参赛 获奖证书铺满教室地面","imgsrc":"bigimg","url":"00AP0001|2294611"}]
     * ename : androidnews
     * replyCount : 0
     * ltitle : 南部战区海军某驱逐舰支队迎来新战舰:呼和浩特舰
     * hasIcon : false
     * subtitle :
     * cid : C1348646712614
     */

    private String template;
    private String lmodify;
    private String source;
    private String postid;
    private String title;
    private String mtime;
    private int hasImg;
    private String topic_background;
    private String digest;
    private String boardid;
    private String alias;
    private int hasAD;
    private String imgsrc;
    private String ptime;
    private String daynum;
    private int hasHead;
    private int order;
    private int votecount;
    private boolean hasCover;
    private String docid;
    private String tname;
    private String url_3w;
    private int priority;
    private String url;
    private String ename;
    private int replyCount;
    private String ltitle;
    private boolean hasIcon;
    private String subtitle;
    private String cid;
    private List<AdsBean> ads;

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getLmodify() {
        return lmodify;
    }

    public void setLmodify(String lmodify) {
        this.lmodify = lmodify;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMtime() {
        return mtime;
    }

    public void setMtime(String mtime) {
        this.mtime = mtime;
    }

    public int getHasImg() {
        return hasImg;
    }

    public void setHasImg(int hasImg) {
        this.hasImg = hasImg;
    }

    public String getTopic_background() {
        return topic_background;
    }

    public void setTopic_background(String topic_background) {
        this.topic_background = topic_background;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public String getBoardid() {
        return boardid;
    }

    public void setBoardid(String boardid) {
        this.boardid = boardid;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public int getHasAD() {
        return hasAD;
    }

    public void setHasAD(int hasAD) {
        this.hasAD = hasAD;
    }

    public String getImgsrc() {
        return imgsrc;
    }

    public void setImgsrc(String imgsrc) {
        this.imgsrc = imgsrc;
    }

    public String getPtime() {
        return ptime;
    }

    public void setPtime(String ptime) {
        this.ptime = ptime;
    }

    public String getDaynum() {
        return daynum;
    }

    public void setDaynum(String daynum) {
        this.daynum = daynum;
    }

    public int getHasHead() {
        return hasHead;
    }

    public void setHasHead(int hasHead) {
        this.hasHead = hasHead;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getVotecount() {
        return votecount;
    }

    public void setVotecount(int votecount) {
        this.votecount = votecount;
    }

    public boolean isHasCover() {
        return hasCover;
    }

    public void setHasCover(boolean hasCover) {
        this.hasCover = hasCover;
    }

    public String getDocid() {
        return docid;
    }

    public void setDocid(String docid) {
        this.docid = docid;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public String getUrl_3w() {
        return url_3w;
    }

    public void setUrl_3w(String url_3w) {
        this.url_3w = url_3w;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    public String getLtitle() {
        return ltitle;
    }

    public void setLtitle(String ltitle) {
        this.ltitle = ltitle;
    }

    public boolean isHasIcon() {
        return hasIcon;
    }

    public void setHasIcon(boolean hasIcon) {
        this.hasIcon = hasIcon;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public List<AdsBean> getAds() {
        return ads;
    }

    public void setAds(List<AdsBean> ads) {
        this.ads = ads;
    }

    public static class AdsBean {
        /**
         * subtitle :
         * skipType : photoset
         * skipID : 00AP0001|2294652
         * tag : photoset
         * title : 男子苦寻女儿13年 她已成美国学霸
         * imgsrc : bigimg
         * url : 00AP0001|2294652
         */

        private String subtitle;
        private String skipType;
        private String skipID;
        private String tag;
        private String title;
        private String imgsrc;
        private String url;

        public String getSubtitle() {
            return subtitle;
        }

        public void setSubtitle(String subtitle) {
            this.subtitle = subtitle;
        }

        public String getSkipType() {
            return skipType;
        }

        public void setSkipType(String skipType) {
            this.skipType = skipType;
        }

        public String getSkipID() {
            return skipID;
        }

        public void setSkipID(String skipID) {
            this.skipID = skipID;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImgsrc() {
            return imgsrc;
        }

        public void setImgsrc(String imgsrc) {
            this.imgsrc = imgsrc;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    @Override
    public int getItemType() {
        if(!StringUtils.isEmpty(digest)){
            return PICTURE_1;
        }else{
            return PICTURE_2;
        }

    }

    @Override
    public int compareTo(@NonNull NewsItemBean newsItemBean) {
        long currentT = TimeUtils.string2Millis(ptime);
        long compareT = TimeUtils.string2Millis(newsItemBean.getPtime());
        if(currentT>compareT){
            return 1;
        }else if(currentT<compareT){
            return -1;
        }
        return -1;
    }
}
