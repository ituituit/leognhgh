package com.weiyun.domain;

import java.io.Serializable;
import java.util.ArrayList;

import android.util.Log;
import android.webkit.JavascriptInterface;

public class DocBodyInterface implements Serializable {
	// static final Pattern IMG_PATTERN =
	// Pattern.compile("<ifengimg\\s*.*?\\s+src=['\"](.+?)['\"][\\s\\S]*?>", 2);
	private static final long serialVersionUID = 2971754670203422875L;
	private String articleWidth;
	// private TopBanner btl;
	private String cTime;
	private String commentType;
	private String commentsExt = "";
	private String commentsUrl = "";
	private String documentId = "";
	private String editTime = "";
	private String editorcode = "";
	private long expiredTime;
	private ArrayList<Extension> extSlides = new ArrayList();
	private String extSlidesJson = "";
	private String fontSize;
	private String hasVideo;
	// private ArrayList<DocImage> img = new ArrayList();
	private String imgJson = "";
	private String introduction;
	private ArrayList<Extension> links = new ArrayList();
	// private LiveStream liveStream;
	private String liveStreamJson = "";
	// private ArrayList<Relation> relations = new ArrayList();
	private String shareurl = "";
	private String source = "";
	// private DocBody.Subscribe subscribe;
	private String[] survey;
	private String text = "";
	private String thumbnail;
	private String title = "";
	private String uTime;
	private String videoJson = "";
	private String videoPoster;
	private String videoSrc;
	// private ArrayList<VideoBody> videos = new ArrayList();
	private String[] vote;
	private String wapurl = "";
	private String wwwurl = "";

	public DocBodyInterface(DocBody docBody) {
		super();
		this.articleWidth = docBody.getArticleWidth();
		this.cTime = docBody.getcTime();
		this.commentType = docBody.getCommentType();
		this.commentsExt = docBody.getCommentsExt();
		this.commentsUrl = docBody.getCommentsUrl();
		this.documentId = docBody.getDocumentId();
		this.editTime = docBody.getEditTime();
		this.editorcode = docBody.getEditorcode();
		this.expiredTime = docBody.getExpiredTime();
		this.extSlides = docBody.getExtSlides();
		this.extSlidesJson = docBody.getExtSlidesJson();
		this.fontSize = docBody.getFontSize();
		this.hasVideo = docBody.getHasVideo();
		this.imgJson = docBody.getImgJson();
		this.introduction = docBody.getIntroduction();
		this.links = docBody.getLinks();
		this.liveStreamJson = docBody.getLiveStreamJson();
		this.shareurl = docBody.getShareurl();
		this.source = docBody.getSource();
		this.survey = docBody.getSurvey();
		this.text = docBody.getText();
		this.thumbnail = docBody.getThumbnail();
		this.title = docBody.getTitle();
		this.uTime = docBody.getuTime();
		this.videoJson = docBody.getVideoJson();
		this.videoPoster = docBody.getVideoPoster();
		this.videoSrc = docBody.getVideoSrc();
		this.vote = docBody.getVote();
		this.wapurl = docBody.getWapurl();
		this.wwwurl = docBody.getWwwurl();
	}


	@JavascriptInterface
	public void log() {
		Log.i("docbody:", "weiyun");
	}

	@JavascriptInterface
	public String getArticleWidth() {
		return articleWidth;
	}

	public void setArticleWidth(String articleWidth) {
		this.articleWidth = articleWidth;
	}

	@JavascriptInterface
	public String getcTime() {
		return cTime;
	}

	public void setcTime(String cTime) {
		this.cTime = cTime;
	}

	@JavascriptInterface
	public String getCommentType() {
		return commentType;
	}

	public void setCommentType(String commentType) {
		this.commentType = commentType;
	}

	@JavascriptInterface
	public String getCommentsExt() {
		return commentsExt;
	}

	public void setCommentsExt(String commentsExt) {
		this.commentsExt = commentsExt;
	}

	@JavascriptInterface
	public String getCommentsUrl() {
		return commentsUrl;
	}

	public void setCommentsUrl(String commentsUrl) {
		this.commentsUrl = commentsUrl;
	}

	@JavascriptInterface
	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	@JavascriptInterface
	public String getEditTime() {
		return editTime;
	}

	public void setEditTime(String editTime) {
		this.editTime = editTime;
	}

	@JavascriptInterface
	public String getEditorcode() {
		return editorcode;
	}

	public void setEditorcode(String editorcode) {
		this.editorcode = editorcode;
	}

	@JavascriptInterface
	public long getExpiredTime() {
		return expiredTime;
	}

	public void setExpiredTime(long expiredTime) {
		this.expiredTime = expiredTime;
	}

	@JavascriptInterface
	public ArrayList<Extension> getExtSlides() {
		return extSlides;
	}

	public void setExtSlides(ArrayList<Extension> extSlides) {
		this.extSlides = extSlides;
	}

	@JavascriptInterface
	public String getExtSlidesJson() {
		return extSlidesJson;
	}

	public void setExtSlidesJson(String extSlidesJson) {
		this.extSlidesJson = extSlidesJson;
	}

	@JavascriptInterface
	public String getFontSize() {
		return fontSize;
	}

	public void setFontSize(String fontSize) {
		this.fontSize = fontSize;
	}

	@JavascriptInterface
	public String getHasVideo() {
		return hasVideo;
	}

	public void setHasVideo(String hasVideo) {
		this.hasVideo = hasVideo;
	}

	@JavascriptInterface
	public String getImgJson() {
		return imgJson;
	}

	public void setImgJson(String imgJson) {
		this.imgJson = imgJson;
	}

	@JavascriptInterface
	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	@JavascriptInterface
	public ArrayList<Extension> getLinks() {
		return links;
	}

	public void setLinks(ArrayList<Extension> links) {
		this.links = links;
	}

	@JavascriptInterface
	public String getLiveStreamJson() {
		return liveStreamJson;
	}

	public void setLiveStreamJson(String liveStreamJson) {
		this.liveStreamJson = liveStreamJson;
	}

	@JavascriptInterface
	public String getShareurl() {
		return shareurl;
	}

	public void setShareurl(String shareurl) {
		this.shareurl = shareurl;
	}

	@JavascriptInterface
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@JavascriptInterface
	public String[] getSurvey() {
		return survey;
	}

	public void setSurvey(String[] survey) {
		this.survey = survey;
	}

	@JavascriptInterface
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@JavascriptInterface
	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	@JavascriptInterface
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@JavascriptInterface
	public String getuTime() {
		return uTime;
	}

	public void setuTime(String uTime) {
		this.uTime = uTime;
	}

	@JavascriptInterface
	public String getVideoJson() {
		return videoJson;
	}

	public void setVideoJson(String videoJson) {
		this.videoJson = videoJson;
	}

	@JavascriptInterface
	public String getVideoPoster() {
		return videoPoster;
	}

	public void setVideoPoster(String videoPoster) {
		this.videoPoster = videoPoster;
	}

	@JavascriptInterface
	public String getVideoSrc() {
		return videoSrc;
	}

	public void setVideoSrc(String videoSrc) {
		this.videoSrc = videoSrc;
	}

	@JavascriptInterface
	public String[] getVote() {
		return vote;
	}

	public void setVote(String[] vote) {
		this.vote = vote;
	}

	@JavascriptInterface
	public String getWapurl() {
		return wapurl;
	}

	public void setWapurl(String wapurl) {
		this.wapurl = wapurl;
	}

	@JavascriptInterface
	public String getWwwurl() {
		return wwwurl;
	}

	public void setWwwurl(String wwwurl) {
		this.wwwurl = wwwurl;
	}
	
}
