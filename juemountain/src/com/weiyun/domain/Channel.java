package com.weiyun.domain;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class Channel
  implements Parcelable
{
  private String adSite;
  private String channelName;
  private String channelSmallUrl;
  private String channelUrl;
  private String offlineUrl;
  private String statistic;

  public Channel()
  {
  }

  public Channel(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    this.channelName = paramString1;
    this.channelUrl = paramString2;
    this.offlineUrl = paramString3;
    this.statistic = paramString4;
    this.channelSmallUrl = paramString5;
  }

  public Channel(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6)
  {
    this.channelName = paramString1;
    this.channelUrl = paramString2;
    this.offlineUrl = paramString3;
    this.statistic = paramString4;
    this.channelSmallUrl = paramString5;
    this.adSite = paramString6;
  }

  public int describeContents()
  {
    return 0;
  }

  public String getAdSite()
  {
    return this.adSite;
  }

  public String getChannelName()
  {
    return this.channelName;
  }

  public String getChannelSmallUrl()
  {
    return this.channelSmallUrl;
  }

  public String getChannelUrl()
  {
    return this.channelUrl;
  }

  public String getOfflineUrl()
  {
    return this.offlineUrl;
  }

  public String getPrefetchChannelUrl()
  {
    return this.channelSmallUrl + "&page=1";
  }

  public String getStatistic()
  {
    return this.statistic;
  }

  public void setAdSite(String paramString)
  {
    this.adSite = paramString;
  }

  public void setChannelName(String paramString)
  {
    this.channelName = paramString;
  }

  public void setChannelSmallUrl(String paramString)
  {
    this.channelSmallUrl = paramString;
  }

  public void setChannelUrl(String paramString)
  {
    this.channelUrl = paramString;
  }

  public void setOfflineUrl(String paramString)
  {
    this.offlineUrl = paramString;
  }

  public void setStatistic(String paramString)
  {
    this.statistic = paramString;
  }

  public String toString()
  {
    return this.channelName;
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeString(this.channelName);
    paramParcel.writeString(this.channelUrl);
    paramParcel.writeString(this.offlineUrl);
    paramParcel.writeString(this.statistic);
    paramParcel.writeString(this.channelSmallUrl);
    paramParcel.writeString(this.adSite);
  }
}