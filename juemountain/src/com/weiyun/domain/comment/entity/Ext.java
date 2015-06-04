package com.weiyun.domain.comment.entity;

import java.io.Serializable;

public class Ext
  implements Serializable
{
  private static final long serialVersionUID = 3340810973751401849L;
  private String sid;

  public String getSid()
  {
    return this.sid;
  }

  public void setSid(String paramString)
  {
    this.sid = paramString;
  }
}