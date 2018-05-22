package chatsocket.bean;

import java.io.Serializable;

public class KeyPacket implements Serializable {
	private static final long serialVersionUID = 5140826547264710686L;
	private int whoId;
	private Integer content;

	public Integer getContent() {
		return content;
	}

	public void setContent(Integer content) {
		this.content = content;
	}

	public int getWhoId() {
		return whoId;
	}

	public void setWhoId(int whoId) {
		this.whoId = whoId;
	}
}
