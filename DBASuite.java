package jmail;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Properties;
import java.util.Scanner;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;

public class DBASuite {
	public static void main(String[] args) {
		if(args.length!=3){
			System.out.println("Invalid number of arguments");
			System.out.println("Argument one is the XML file");
			System.out.println("Argument two is the HTML file to attach to the e-mail.");
			System.out.println("Argument three is the Y(to attach the HTML file) or N(to show HTML file as mail body)");
			System.out.println("java -jar jmail.jar xmlfile1.xml htmlfile.html Y");
			System.exit(0);
		}
		String xmlArg=args[0].toString();
		String htmlArg=args[1].toString();
		String isAtt=args[2].toString();
		/*String xmlArg="mail1.xml";
		String htmlArg="health_check_report.html";
		String isAtt="Y";*/
		File xmlFile=new File(xmlArg);
		File htmlFile=new File(htmlArg);
		JAXBContext jaxbContext;
		try {
			jaxbContext = JAXBContext.newInstance(MailInfo.class);
			Unmarshaller jaxbUnmarshaller;
			jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			MailInfo mail = (MailInfo)jaxbUnmarshaller.unmarshal( xmlFile );
			Properties properties = System.getProperties();  
			properties.setProperty("mail.smtp.host", mail.getMailhost());  
			Session session = Session.getDefaultInstance(properties);  
			Message message = new MimeMessage(session);  
			message.setFrom(new InternetAddress(mail.getSender()));
			for (int i=0;i<mail.getToMail().size();i++)
				message.addRecipient(Message.RecipientType.TO,new InternetAddress(mail.getToMail().get(i)));  
			message.setSubject(mail.getSubject());
			if(!isAtt.toLowerCase().equals("y")){
				Scanner scanner = new Scanner( htmlFile, "UTF-8" );
				String text = scanner.useDelimiter("\\A").next();
				scanner.close(); 
				message.setContent(text, "text/html");
			}else{
				DataSource source = new FileDataSource(htmlFile);
				BodyPart messageBodyPart = new MimeBodyPart();
				messageBodyPart.setText(mail.getMailbody());
				Multipart multipart = new MimeMultipart();
				multipart.addBodyPart(messageBodyPart);
				messageBodyPart = new MimeBodyPart();
				messageBodyPart.setDataHandler(new DataHandler(source));
				messageBodyPart.setFileName(htmlArg);
				multipart.addBodyPart(messageBodyPart);
				message.setContent(multipart);
			}
			Transport.send(message);  
		} catch (JAXBException e) {
			System.out.println("XML file with wrong tags or format!");
		} catch (AddressException e) {
			System.out.println("Mail server not reacheable");
		} catch (MessagingException e) {
			System.out.println("Fail to send e-mail.");
			System.out.println("Check if the attached file is on the correct location.");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
	}
}