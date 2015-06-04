package com.weiyun.domain.channel.entity;

import java.io.Serializable;
import java.util.List;

public class ChannelList
  implements  Serializable
{
	private static final long serialVersionUID = 1L;
	private List<ChannelListUnit> channelListUnits;

	public List<ChannelListUnit> getClu() {
		return channelListUnits;
	}

	public void setClu(List<ChannelListUnit> clu) {
		this.channelListUnits = clu;
	}
	
}
