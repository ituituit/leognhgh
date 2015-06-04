package com.weiyun.domain;

import java.io.Serializable;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.weiyun.domain.gallery.entity.Meta;

public class DocUnit
  implements Serializable
{
  public static final DocUnit NULL = new DocUnit();
  private static final long serialVersionUID = -5639525048604461603L;
  private DocBody body = new DocBody();
  private Meta meta = new Meta();

  public static boolean isNull(DocUnit paramDocUnit)
  {
    return (paramDocUnit == null) || (paramDocUnit == NULL) || (paramDocUnit.equals(NULL));
  }

  public boolean equals(Object paramObject)
  {
    if ((paramObject == null) || (!(paramObject instanceof DocUnit)))
      return false;
    return ((DocUnit)paramObject).meta.equals(this.meta);
  }

  public void log(String paramString){
	  Log.i("DocUnit:", paramString);
  }
  public DocBody getBody()
  {
    return this.body;
  }

  public String getDocumentIdfromMeta()
  {
    if (!TextUtils.isEmpty(getBody().getDocumentId()))
      return getBody().getDocumentId();
    if (!TextUtils.isEmpty(getMeta().getDocumentId()))
    {
      getBody().setDocumentId(getMeta().getDocumentId());
      return getMeta().getDocumentId();
    }
    throw new IllegalArgumentException("can't find documentId");
  }

  public Meta getMeta()
  {
    if (this.meta == null)
      this.meta = new Meta();
    return this.meta;
  }

  public int hashCode()
  {
    return this.meta.hashCode() + this.body.hashCode();
  }

  public void setBody(DocBody paramDocBody)
  {
    this.body = paramDocBody;
  }

  public void setMeta(Meta paramMeta)
  {
    this.meta = paramMeta;
  }
}
 