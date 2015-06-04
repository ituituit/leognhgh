package com.weiyun.domain.comment.entity;

import java.io.Serializable;

public class ParentComment extends Comment
  implements Serializable
{
  private static final long serialVersionUID = -8208630319233699874L;
  private int add_time;
  private int application;
  private int article_id;
  private int channel_id;
  private String client_ip;
  private String comment_contents;
  private String comment_date;
  private String comment_id;
  private String doc_name;
  private String doc_url;
  private Ext ext = new Ext();
  private String ext1;
  private String ext2;
  private String ext3;
  private int ext4;
  private int ext5;
  private int ext6;
  private int floorNum;
  private String ip_from;
  private int is_hiddenip;
  private int last_update_time;
  private int level;
  private String quote_id;
  private int report;
  private String uname;
  private String uuid;

  public int getAdd_time()
  {
    return this.add_time;
  }

  public int getApplication()
  {
    return this.application;
  }

  public int getArticle_id()
  {
    return this.article_id;
  }

  public int getChannel_id()
  {
    return this.channel_id;
  }

  public String getClient_ip()
  {
    return this.client_ip;
  }

  public String getComment_contents()
  {
    return this.comment_contents;
  }

  public String getComment_date()
  {
    return this.comment_date;
  }

  public String getComment_id()
  {
    return this.comment_id;
  }

  public String getDoc_name()
  {
    return this.doc_name;
  }

  public String getDoc_url()
  {
    return this.doc_url;
  }

  public Ext getExt()
  {
    return this.ext;
  }

  public String getExt1()
  {
    return this.ext1;
  }

  public String getExt2()
  {
    return this.ext2;
  }

  public String getExt3()
  {
    return this.ext3;
  }

  public int getExt4()
  {
    return this.ext4;
  }

  public int getExt5()
  {
    return this.ext5;
  }

  public int getExt6()
  {
    return this.ext6;
  }

  public int getFloorNum()
  {
    return this.floorNum;
  }

  public String getIp_from()
  {
    return this.ip_from;
  }

  public int getIs_hiddenip()
  {
    return this.is_hiddenip;
  }

  public int getLast_update_time()
  {
    return this.last_update_time;
  }

  public int getLevel()
  {
    return this.level;
  }

  public String getQuote_id()
  {
    return this.quote_id;
  }

  public int getReport()
  {
    return this.report;
  }

  public String getUname()
  {
    return this.uname;
  }

  public String getUuid()
  {
    return this.uuid;
  }

  public void setAdd_time(int paramInt)
  {
    this.add_time = paramInt;
  }

  public void setApplication(int paramInt)
  {
    this.application = paramInt;
  }

  public void setArticle_id(int paramInt)
  {
    this.article_id = paramInt;
  }

  public void setChannel_id(int paramInt)
  {
    this.channel_id = paramInt;
  }

  public void setClient_ip(String paramString)
  {
    this.client_ip = paramString;
  }

  public void setComment_contents(String paramString)
  {
    this.comment_contents = paramString;
  }

  public void setComment_date(String paramString)
  {
    this.comment_date = paramString;
  }

  public void setComment_id(String paramString)
  {
    this.comment_id = paramString;
  }

  public void setDoc_name(String paramString)
  {
    this.doc_name = paramString;
  }

  public void setDoc_url(String paramString)
  {
    this.doc_url = paramString;
  }

  public void setExt(Ext paramExt)
  {
    this.ext = paramExt;
  }

  public void setExt1(String paramString)
  {
    this.ext1 = paramString;
  }

  public void setExt2(String paramString)
  {
    this.ext2 = paramString;
  }

  public void setExt3(String paramString)
  {
    this.ext3 = paramString;
  }

  public void setExt4(int paramInt)
  {
    this.ext4 = paramInt;
  }

  public void setExt5(int paramInt)
  {
    this.ext5 = paramInt;
  }

  public void setExt6(int paramInt)
  {
    this.ext6 = paramInt;
  }

  public void setFloorNum(int paramInt)
  {
    this.floorNum = paramInt;
  }

  public void setIp_from(String paramString)
  {
    this.ip_from = paramString;
  }

  public void setIs_hiddenip(int paramInt)
  {
    this.is_hiddenip = paramInt;
  }

  public void setLast_update_time(int paramInt)
  {
    this.last_update_time = paramInt;
  }

  public void setLevel(int paramInt)
  {
    this.level = paramInt;
  }

  public void setQuote_id(String paramString)
  {
    this.quote_id = paramString;
  }

  public void setReport(int paramInt)
  {
    this.report = paramInt;
  }

  public void setUname(String paramString)
  {
    this.uname = paramString;
  }

  public void setUuid(String paramString)
  {
    this.uuid = paramString;
  }
}