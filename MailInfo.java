package jmail;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import java.util.ArrayList;
import java.util.StringTokenizer;

@XmlRootElement( name = "mailinfo" )
@XmlType( propOrder = { "subject", "sender","mailto", "mailbody", "mailhost" } )
public class MailInfo {
	private String subject;
	private String sender;
	private String mailto;
	private String mailbody;
	private String mailhost;

	public String getSubject() {
		return subject;
	}
	@XmlElement( name = "subject" )
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getSender() {
		return sender;
	}
	@XmlElement( name = "sender" )
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getMailbody() {
		return mailbody;
	}
	@XmlElement( name = "mailbody" )
	public void setMailbody(String mailbody) {
		this.mailbody = mailbody;
	}
	public String getMailhost() {
		return mailhost;
	}
	@XmlElement( name = "mailhost" )
	public void setMailhost(String mailhost) {
		this.mailhost = mailhost;
	}
	public String getMailto() {
		return mailto;
	}
	@XmlElement(name="mailto")
	public void setMailto(String mailto) {
		this.mailto = mailto;
	}
	public ArrayList<String> getToMail(){
		StringTokenizer strTkn = new StringTokenizer(this.mailto, ",");
		ArrayList<String> arrLis = new ArrayList<String>(this.mailto.length());
		while(strTkn.hasMoreTokens())
			arrLis.add(strTkn.nextToken());
		return arrLis;
	}

}
