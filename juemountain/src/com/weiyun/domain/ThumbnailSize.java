package com.weiyun.domain;

import java.io.Serializable;

public class ThumbnailSize
  implements Serializable
{
  private static final long serialVersionUID = 1966477084106638495L;
  private String height;
  private String width;

  public String getHeight()
  {
    return this.height;
  }

  public String getWidth()
  {
    return this.width;
  }

  public void setHeight(String paramString)
  {
    this.height = paramString;
  }

  public void setWidth(String paramString)
  {
    this.width = paramString;
  }
}

/* Location:           /Users/pwl/Desktop/classes-dex2jar.jar
 * Qualified Name:     com.ifeng.news2.bean.ThumbnailSize
 * JD-Core Version:    0.6.2
 */