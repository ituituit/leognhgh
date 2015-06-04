package com.weiyun.domain.channel.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class ChannelStyle
  implements Serializable
{
  private static final long serialVersionUID = 8232944010267397811L;
  private ArrayList<String> images;
  private ArrayList<String> sImages;
  private String tag;
  private String type;

  public ArrayList<String> getImages()
  {
    return this.images;
  }

  public String getTag()
  {
    return this.tag;
  }

  public String getType()
  {
    return this.type;
  }

//  public ArrayList<String> getsImages()
//  {
//    if ((this.sImages == null) && (this.images != null) && (this.images.size() > 0))
//    {
//      this.sImages = new ArrayList();
//      Iterator localIterator = this.images.iterator();
//      while (localIterator.hasNext())
//      {
//        String str = (String)localIterator.next();
//        if (str.startsWith("http://d.ifengimg.com/w"))
//          this.sImages.add("http://d.ifengimg.com/q30_" + str.substring(22));
//        else if (str.matches("http://y[0-9]*\\.ifengimg.com/.*\\.jpg"))
//          this.sImages.add("http://d.ifengimg.com/q30_" + str.substring(7));
//      }
//    }
//    return this.sImages;
//  }

  public void setImages(ArrayList<String> paramArrayList)
  {
    this.images = paramArrayList;
  }

  public void setTag(String paramString)
  {
    this.tag = paramString;
  }

  public void setType(String paramString)
  {
    this.type = paramString;
  }

  public void setsImages(ArrayList<String> paramArrayList)
  {
    this.sImages = paramArrayList;
  }
}
