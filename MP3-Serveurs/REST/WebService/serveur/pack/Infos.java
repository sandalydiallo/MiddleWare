package pack;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "infos")
public class Infos {
	
	private String action;
	private String music;
	
	@XmlElement
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}

	@XmlElement
	public String getMusic() {
		return music;
	}
	public void setMusic(String music) {
		this.music = music;
	}
} 