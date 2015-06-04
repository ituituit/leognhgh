package com.weiyun.domain.gallery.entity;

import java.io.Serializable;
import java.util.ArrayList;

import android.text.TextUtils;

import com.weiyun.domain.Extension;

public class SlideItem
  implements Serializable
{
  private static final long serialVersionUID = -7377813905480156906L;
  private boolean bound;
  private String commentType;
  private String comments = "";
  private String commentsUrl;
  private String commentsall = "0";
  private String description = "";
  private String documentId = "";
  private ArrayList<Integer> extensions;
  private String id;
  private String image = "";
  private ArrayList<Extension> links;
  private String particpateCount = "0";
  private String position;
  private String shareurl = "";
  private String source = "";
  private String thumbnail = "";
  private String title = "";
  private String type = "";
  private String updateTime = "";
  private String url = "";
  private String wwwurl = "";

  public String getCommentType()
  {
    return this.commentType;
  }

  public String getComments()
  {
    if (("0".equals(this.comments)) || (TextUtils.isEmpty(this.comments)) || ("false".equals(this.comments)))
      return "";
    return this.comments;
  }

  public String getCommentsUrl()
  {
    return this.commentsUrl;
  }

  public String getCommentsall()
  {
    if (("0".equals(this.commentsall)) || (TextUtils.isEmpty(this.commentsall)) || ("false".equals(this.commentsall)))
      return "";
    return this.commentsall;
  }

  public String getDescription()
  {
    return this.description;
  }

  public String getDocumentId()
  {
    return this.documentId;
  }

  public ArrayList<Integer> getExtensions()
  {
    if (this.extensions == null)
      return new ArrayList();
    return this.extensions;
  }

  public String getId()
  {
    return this.id;
  }

  public String getImage()
  {
    return this.image;
  }

  public ArrayList<Extension> getLinks()
  {
    if (this.links == null)
      return new ArrayList();
    return this.links;
  }

  public String getParticpateCount()
  {
    return this.particpateCount;
  }

  public String getPosition()
  {
    return this.position;
  }

  public String getShareurl()
  {
    return this.shareurl;
  }

  public String getSource()
  {
    return this.source;
  }

  public String getThumbnail()
  {
    return this.thumbnail;
  }

  public String getTitle()
  {
    return this.title;
  }

  public String getType()
  {
    return this.type;
  }

  public String getUpdateTime()
  {
    return this.updateTime;
  }

  public String getUrl()
  {
    return this.url;
  }

  public String getWwwurl()
  {
    return this.wwwurl;
  }

  public boolean isBound()
  {
    return this.bound;
  }

  public void setBound(boolean paramBoolean)
  {
    this.bound = paramBoolean;
  }

  public void setCommentType(String paramString)
  {
    this.commentType = paramString;
  }

  public void setComments(String paramString)
  {
    this.comments = paramString;
  }

  public void setCommentsUrl(String paramString)
  {
    this.commentsUrl = paramString;
  }

  public void setCommentsall(String paramString)
  {
    this.commentsall = paramString;
  }

  public void setDescription(String paramString)
  {
    this.description = paramString;
  }

  public void setDocumentId(String paramString)
  {
    this.documentId = paramString;
  }

  public void setExtensions(ArrayList<Integer> paramArrayList)
  {
    this.extensions = paramArrayList;
  }

  public void setId(String paramString)
  {
    this.id = paramString;
  }

  public void setImage(String paramString)
  {
    this.image = paramString;
  }

  public void setLinks(ArrayList<Extension> paramArrayList)
  {
    this.links = paramArrayList;
  }

  public void setParticpateCount(String paramString)
  {
    this.particpateCount = paramString;
  }

  public void setPosition(String paramString)
  {
    this.position = paramString;
  }

  public void setShareurl(String paramString)
  {
    this.shareurl = paramString;
  }

  public void setSource(String paramString)
  {
    this.source = paramString;
  }

  public void setThumbnail(String paramString)
  {
    this.thumbnail = paramString;
  }

  public void setTitle(String paramString)
  {
    this.title = paramString;
  }

  public void setType(String paramString)
  {
    this.type = paramString;
  }

  public void setUpdateTime(String paramString)
  {
    this.updateTime = paramString;
  }

  public void setUrl(String paramString)
  {
    this.url = paramString;
  }

  public void setWwwurl(String paramString)
  {
    this.wwwurl = paramString;
  }
}
