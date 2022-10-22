package MyMailAgent;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import util.*;

/**
 * Servlet implementation class DownloadEmail
 */

public class DownloadEmail extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DownloadEmail() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8"); 
    	response.setContentType("text/html;charset=utf-8"); 
    	response.setCharacterEncoding("utf-8");
    	
    	//ǰ�˴�������kidΪ�������
		String sender = (String) request.getSession().getAttribute("sender");
		String password = (String) request.getSession().getAttribute("password");
		String flag = (String) request.getSession().getAttribute("server");
		
//		List<String> mails = null;
//		
//		if(flag.equals("163.com")){
//	        // �����ʼ�����
//	        SocketReceiveMail pop3Client=new SocketReceiveMail("pop3.163.com",110);
//	        mails = pop3Client.recieveMail(sender,password);
//	        
//		}else{
//			//1700829748@qq.com quddqjbxsgmdeihd
//	        // �����ʼ�����
//	        SocketReceiveMail pop3Client=new SocketReceiveMail("pop3.qq.com",110);
//	        mails = pop3Client.recieveMail(sender,password);
//	        
//		}
//        request.setAttribute("mails", mails);
//        request.getRequestDispatcher("/ShowDownLoad.jsp").forward(request, response);
		
		//�汾2
        List<String> mails = new ArrayList<>();
		if(flag.equals("163.com")){
	        //ͨ��pop3Э�����ָ������Ĵ洢���ݣ�����pop3����
	        Properties props = new Properties();
	        Session session = Session.getDefaultInstance(props, null);
	       
	        try {
	        	Store store = session.getStore("pop3");
				store.connect("pop3.163.com", sender, password);
			      //��ȡ�ռ����·������ʼ��ȡ�ʼ�
		        Folder folder = store.getFolder("INBOX");
		        folder.open(Folder.READ_ONLY);
		        Message message[] = folder.getMessages();
		        System.out.println("Messages's length: " + message.length);
		        PraseMimeMessage pmm = null;
		        
		        //�����ȡ���Ķ���ʼ�����ÿһ���ʼ����н���
		        for (int i = 0; i < message.length; i++) {
		        	
		        	String mail ="";
		            pmm = new PraseMimeMessage((MimeMessage) message[i]);
		            mail+= " subject: " + pmm.getSubject()+"<br/>";
		            System.out.println("Message " + i + " subject: " + pmm.getSubject());
		            mail+="  containAttachment: "+ pmm.isContainAttach((Part) message[i])+"<br/>";
		            System.out.println("Message " + i + "  containAttachment: "+ pmm.isContainAttach((Part) message[i]));
		            mail+=" from: " + pmm.getFrom()+"<br/>";
		            System.out.println("Message " + i + " form: " + pmm.getFrom());
		            mail+=" to: "+ pmm.getMailAddress("to")+"<br/>";
		            System.out.println("Message " + i + " to: "+ pmm.getMailAddress("to"));
		
		            pmm.setDateFormat("yy��MM��dd�� HH:mm");
		            pmm.getMailContent((Part) message[i]);
		            mail+=" bodycontent: "+"<br/>"+ pmm.getBodyText()+"<br/>";
		            System.out.println("Message " + i + " bodycontent: \r\n"+ pmm.getBodyText());
		            String savepath= getServletContext().getRealPath("/WEB-INF/attach");
		            //�ж��ļ����Ƿ���ڣ����򴴽��ļ���
		            File file = new File(savepath);
		             if (!file.exists()) {
		                file.mkdirs();
		            }
		             
		            mail+=" Attach:"+"<br/>"+file.toString()+"<br/>";
		            //���渽��
		            pmm.setAttachPath(file.toString());
		            if(pmm.isContainAttach((Part) message[i]))
		            	pmm.saveAttachMent((Part) message[i]);
		            mails.add(mail);
		        }
		        request.setAttribute("mails", mails);
		        request.getRequestDispatcher("/ShowDownLoad.jsp").forward(request, response);
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        

		}
		else{
	        //ͨ��pop3Э�����ָ������Ĵ洢���ݣ�����pop3����
	        Properties props = new Properties();
	        Session session = Session.getDefaultInstance(props, null);
	       
	        try {
	        	Store store = session.getStore("pop3");
				store.connect("pop.qq.com", sender, password);
			      //��ȡ�ռ����·������ʼ��ȡ�ʼ�
		        Folder folder = store.getFolder("INBOX");
		        folder.open(Folder.READ_ONLY);
		        Message message[] = folder.getMessages();
		        System.out.println("Messages's length: " + message.length);
		        PraseMimeMessage pmm = null;
		        
		        //�����ȡ���Ķ���ʼ�����ÿһ���ʼ����н���
		        for (int i = 0; i < message.length; i++) {
		        	
		        	String mail ="";
		            pmm = new PraseMimeMessage((MimeMessage) message[i]);
		            mail+= " subject: " + pmm.getSubject()+"<br/>";
		            System.out.println("Message " + i + " subject: " + pmm.getSubject());
		            mail+="  containAttachment: "+ pmm.isContainAttach((Part) message[i])+"<br/>";
		            System.out.println("Message " + i + "  containAttachment: "+ pmm.isContainAttach((Part) message[i]));
		            mail+=" from: " + pmm.getFrom()+"<br/>";
		            System.out.println("Message " + i + " form: " + pmm.getFrom());
		            mail+=" to: "+ pmm.getMailAddress("to")+"<br/>";
		            System.out.println("Message " + i + " to: "+ pmm.getMailAddress("to"));
		
		            pmm.setDateFormat("yy��MM��dd�� HH:mm");
		            pmm.getMailContent((Part) message[i]);
		            mail+=" bodycontent: "+"<br/>"+ pmm.getBodyText()+"<br/>";
		            System.out.println("Message " + i + " bodycontent: \r\n"+ pmm.getBodyText());
		            String savepath= getServletContext().getRealPath("/WEB-INF/attach");
		            //�ж��ļ����Ƿ���ڣ����򴴽��ļ���
		            File file = new File(savepath);
		             if (!file.exists()) {
		                file.mkdirs();
		            }
		             
		            
		            //���渽��
		            pmm.setAttachPath(file.toString());
		       
		            if(pmm.isContainAttach((Part) message[i]))
		            	pmm.saveAttachMent((Part) message[i]);
		            mail+=" Attach:"+"<br/>"+pmm.getRealPath()+"<br/>";
		            mails.add(mail);
		        }
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	        request.setAttribute("mails", mails);
	        request.getRequestDispatcher("/ShowDownLoad.jsp").forward(request, response);
		}
    
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
