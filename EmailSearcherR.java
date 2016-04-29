/** Date: 2016-SEP-15
* File Name: EmailSearcherR.java
* Purpose:Pull emails from selected imap email address
*
* Details Pulled:
* date(YYYYMMDD);NUMBER;Date(Day Mon Date HH:mm:ss TZ YYYY; FROM(email address); SUBJECT
*                                                                                        
* Created By: Umesh Rege                                                                 
* Associated Files:
*                 1. MANIFEST.MF
*		  2. flume.conf
* How to use:
*               1. Edit EmailSearchR.java to see emails for how many days and other      
*                  required details (email address, password, folder etc)
*               2. Compile to create CLASS file
*               3. Create JAR file using MANIFEST.MF file in the package
*               4. Execute flume_ng/flume binary pointing to flume.conf in this package
*
* Credits: This java file has ref to mail.jar from JavaMail API
*
* Note: This has only been tested with gmail only as on 28th April, 2016
*/


import java.io.*;
import java.util.*;
import java.util.Properties;
import java.util.Date;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.SearchTerm;
import javax.mail.search.DateTerm;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.search.ReceivedDateTerm;
import javax.mail.search.ComparisonTerm;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import javax.mail.search.AndTerm;
import javax.mail.FetchProfile;
import javax.mail.Folder;
import com.sun.mail.imap.IMAPFolder.FetchProfileItem;
import javax.mail.FetchProfile.Item;
 
/**
 * This program demonstrates how to search for e-mail messages which satisfy
 * a search criterion.
 * @author www.codejava.net
 *
 */
public class EmailSearcherR {
 
    /**
     * Searches for e-mail messages containing the specified keyword in
     * Subject field.
     * @param host
     * @param port
     * @param userName
     * @param password
     * @param keyword
     */
    public void searchEmail(String host, String port, String userName,
            String password, final String keyword) throws IOException, Exception {
        Properties properties = new Properties();
 
        // server setting
        properties.put("mail.imap.host", host);
        properties.put("mail.imap.port", port);
 
        // SSL setting
        properties.setProperty("mail.imap.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        properties.setProperty("mail.imap.socketFactory.fallback", "false");
        properties.setProperty("mail.imap.socketFactory.port",
                String.valueOf(port));
 
        Session session = Session.getDefaultInstance(properties);
 		int i;
		int j = 0;
		int k = 1;
		int t = 2;
	for (i = t; i > 0; i--) {
        try {
            //System.out.println("#############################################################");
            // connects to the message store
            Store store = session.getStore("imap");
            store.connect(userName, password);
 
            // opens the inbox folder
            Folder folderInbox = store.getFolder("<FOLDER_NAME>");
            folderInbox.open(Folder.READ_ONLY);
 
            // creates a search criterion
		
	    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date myDate = new Date(System.currentTimeMillis());
            //System.out.println("result is "+ dateFormat.format(myDate));
            Calendar cal = Calendar.getInstance();
            cal.setTime(myDate);
            cal.add(Calendar.DATE, -i);
			
            //System.out.println("From Date: " + dateFormat.format(cal.getTime()));
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		String dtp = formatter.format(cal.getTime());

	//Calendar cal = Calendar.getInstance();
		SearchTerm fromdate = new ReceivedDateTerm(ComparisonTerm.GT, cal.getTime());
            	cal.setTime(myDate);
		j = i - 1;
            	cal.add(Calendar.DATE, -j);
            	//System.out.println("To Date: " + dateFormat.format(cal.getTime()));
		SearchTerm todate = new ReceivedDateTerm(ComparisonTerm.LT, cal.getTime());
		SearchTerm searchCondition = new AndTerm(fromdate, todate);

 
            // performs search through the folder
            	Message[] foundMessages = folderInbox.search(searchCondition);
		//System.out.println(dtp + ";Total Messages " + foundMessages.length);
		Date sdate = new Date();
		
		// Performs pre-fetch of required headers
       		//System.out.println(dtp + ";Starting pre-fetch:" + sdate.toString());
		FetchProfile fetchProfile = new FetchProfile();
		fetchProfile.add(FetchProfile.Item.ENVELOPE);
		fetchProfile.add(FetchProfile.Item.FLAGS);
		fetchProfile.add(FetchProfile.Item.CONTENT_INFO);
		fetchProfile.add(FetchProfileItem.HEADERS);
		fetchProfile.add("X-mailer");
		fetchProfile.add("Date");
		fetchProfile.add("To");
		folderInbox.fetch(foundMessages, fetchProfile);
		Date edate = new Date();
       		//System.out.println(dtp + ";End pre-fetch:" + edate.toString());
		try {
                                Thread.sleep(1000);
                        } catch (InterruptedException ie) {
                                }
int n=1;
for (Message message: foundMessages) {
	
System.out.println(dtp + ";" + n + ";" + message.getReceivedDate() + ";" + message.getAllRecipients()[0] + ";" + message.getSubject()); //Subject is already local, no additional fetch required
	n += 1;
    }  // end of for loop for printing data

 
            // disconnect
            folderInbox.close(false);
            store.close();
        } catch (NoSuchProviderException ex) {
            System.out.println("No provider.");
            ex.printStackTrace();
        } catch (MessagingException ex) {
            System.out.println("Could not connect to the message store.");
            ex.printStackTrace();
        }
            //System.out.println("#############################################################");
		Runtime rs =  Runtime.getRuntime();
      	//	System.out.println("Free memory in JVM before Garbage Collection = "+rs.freeMemory());
     		rs.gc();
      	//	System.out.println("Free memory in JVM after Garbage Collection = "+rs.freeMemory());
			try {
                		//System.out.println("Sleeping for < a minute after round " + k + " of " + t);
                         	Thread.sleep(2000);
                        } catch (InterruptedException ie) {
                                }
	k = k + 1;
    } //end of for loop for looping through dates
} //end of searchEmail method
    /**
     * Test this program with a Gmail's account
     */
    public static void main(String[] args) throws MessagingException, IOException, Exception {
	Date s1date = new Date();
       //System.out.println("Starting :" + s1date.toString());
        String host = "imap.gmail.com";
        String port = "993";
        String userName = "<EMAIL_ID>";
        String password = "<PASSWORD_FOR_EMAIL>";
        EmailSearcherR searcher = new EmailSearcherR();
        String keyword = "";
        searcher.searchEmail(host, port, userName, password, keyword);
	Date e1date = new Date();
       	//System.out.println("End of prog:" + e1date.toString());
    } //end of main

 
}
