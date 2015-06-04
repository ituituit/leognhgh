package com.weiyun.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class SubscriptionBean implements Serializable {
	private static final long serialVersionUID = -3007915060184465460L;
	private HashMap<String, Channel> channels = new HashMap();
	private ArrayList<String> defaultChannel = new ArrayList();
	private ArrayList<String> defaultOrderMenuItems = new ArrayList();
	private String version;

	public void filterChannel() {
		for (int i = -1 + this.defaultOrderMenuItems.size(); i > 0; i--)
			if (((String) this.defaultOrderMenuItems.get(i))
					.equals(this.defaultOrderMenuItems.get(i - 1)))
				this.defaultOrderMenuItems.remove(i);
	}

	public HashMap<String, Channel> getChannels() {
		return this.channels;
	}

	public ArrayList<String> getDefaultChannel() {
		return this.defaultChannel;
	}

	public ArrayList<String> getDefaultOrderMenuItems() {
		return this.defaultOrderMenuItems;
	}

	public ArrayList<String> getOtherChannelNames() {
		ArrayList localArrayList = new ArrayList(this.channels.keySet());
		Iterator localIterator = this.defaultOrderMenuItems.iterator();
		while (localIterator.hasNext()) {
			String str = (String) localIterator.next();
			if (localArrayList.contains(str))
				localArrayList.remove(str);
		}
		return localArrayList;
	}

	public String getVersion() {
		return this.version;
	}

	// public Channel[] getchangedChannels()
	// {
	// Channel[] arrayOfChannel = new
	// Channel[this.defaultOrderMenuItems.size()];
	// int i = -1 + this.defaultOrderMenuItems.size();
	// if (i > 0)
	// {
	// if
	// (((String)this.defaultOrderMenuItems.get(i)).equals(this.defaultOrderMenuItems.get(i
	// - 1)))
	// this.defaultOrderMenuItems.remove(i);
	// while (true)
	// {
	// i--;
	// arrayOfChannel[i] =
	// ((Channel)this.channels.get(this.defaultOrderMenuItems.get(i)));
	// }
	// }
	// arrayOfChannel[0] =
	// ((Channel)this.channels.get(this.defaultOrderMenuItems.get(0)));
	// return arrayOfChannel;
	// }

	public void setChannels(HashMap<String, Channel> paramHashMap) {
		this.channels = paramHashMap;
	}

	public void setDefaultChannel(ArrayList<String> paramArrayList) {
		this.defaultChannel = paramArrayList;
	}

	public void setDefaultOrderMenuItems(ArrayList<String> paramArrayList) {
		this.defaultOrderMenuItems = paramArrayList;
	}

	public void setVersion(String paramString) {
		this.version = paramString;
	}
}

/*
 * Location: /Users/pwl/Desktop/classes-dex2jar.jar Qualified Name:
 * com.ifeng.news2.bean.SubscriptionBean JD-Core Version: 0.6.2
 */