package MyMailAgent;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import javax.security.auth.Subject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

import com.sun.xml.internal.ws.wsdl.writer.document.Part;

import util.*;

/**
 * Servlet implementation class MailSendServlet
 */
public class MailSendServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MailSendServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	    /**
	     * @param args
	     */
		request.setCharacterEncoding("utf-8"); 
    	response.setContentType("text/html;charset=utf-8"); 
    	response.setCharacterEncoding("utf-8");
    	
        // ����Ƿ�Ϊ��ý���ϴ�
        if (!ServletFileUpload.isMultipartContent(request)) {
            // ���������ֹͣ
            PrintWriter writer = response.getWriter();
            writer.println("Error: ��������� enctype=multipart/form-data");
            writer.flush();
            return;
        }
		
		DiskFileItemFactory factory = new DiskFileItemFactory();
		//2������һ���ļ��ϴ�������
	    ServletFileUpload upload = new ServletFileUpload(factory);
	    List<String> pList = new ArrayList<>();
		List<FileItem> list = null;
		String filename=null;
		String filePath=null;
		try {
			list = upload.parseRequest(request);
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		for(FileItem item : list){
	        //���fileitem�з�װ������ͨ�����������
	        if(item.isFormField()){
	        	//String name = item.getFieldName();
	        	String value = item.getString("UTF-8");
	        	pList.add(value);//�����ļ������������ŵ�һ��list�У��������˳���ȥȡ��
	        	//System.out.println("name"+name+"value"+value);
	        	continue;
	        }else{//���fileitem�з�װ�����ϴ��ļ�
	        	InputStream stream=item.getInputStream();//�ϴ��ļ���Ҫ���ļ�������
	        	filename=item.getName();   //�ϴ��ļ���Ҫ�Ĳ���
	        	String savepath=getServletContext().getRealPath("/WEB-INF/upload");
	        	
	            File path=new File(savepath);  //���Ҫ�Լ�д�����·��������Ҫ�ϴ��ļ���Ҫ�Ĳ���
	            
	            if(!path.exists()){ 
	                    path.mkdir(); 
	            }
	            
	            Upload.uploadFile(stream, path,filename);   //���ù����෽��
	            if(filename==null || filename.trim().equals("")){
	            	//�пմ���
	            }
	            
	            filePath = savepath+"\\"+filename;
	            continue;
	        }
		}
		//��ʼ˳��ȡ���ļ�����
		
		String sender = (String) request.getSession().getAttribute("sender");
		String receiver = pList.get(0);
		String password = (String) request.getSession().getAttribute("password");
		String content = pList.get(2);
		String subject = pList.get(1);
		String flag = (String) request.getSession().getAttribute("server");
		String reply = "";
		
		System.out.println("�����ˣ� "+receiver);
		System.out.println("���⣺ "+receiver);
    	System.out.println("�����ߣ�"+sender);
    	System.out.println("���룺"+password);
    	System.out.println("��������"+flag);
    	System.out.println("�ļ�·����"+filePath);
	
        System.out.println("SENDER-"+":/>" + "ʹ��"+flag+"���俪ʼ�����ʼ�...");
		if(flag.equals("163.com")){
	        // �����ʼ�����
	        SocketMailSend mail = new SocketMailSend();
	        mail.setHost("smtp.163.com"); // �ʼ���������ַ
	        mail.setFrom(sender); // ����������
	        mail.addTo(receiver); // �ռ�������
	        
	        //mail.addCc("test@m1.com");//����
	        //mail.addBcc("test@m2.com");//����
	        
	        mail.setSubject(subject); // �ʼ�����
	        mail.setUser(sender); // �û���
	        mail.setPassword(password); // ���� ssc123456
	        mail.setContent(content); //�ʼ�����
	        mail.addAttachment(filePath); // ��Ӹ���
	        reply = mail.send();
		}else{
			//1700829748@qq.com quddqjbxsgmdeihd
	        // �����ʼ�����
	        SocketMailSend mail = new SocketMailSend();
	        mail.setHost("smtp.qq.com"); // �ʼ���������ַ
	        mail.setFrom(sender); // ����������
	        mail.addTo(receiver); // �ռ�������
	        
	        //mail.addCc("test@m1.com");//����
	        //mail.addBcc("test@m2.com");//����
	        
	        mail.setSubject(subject); // �ʼ�����
	        mail.setUser(sender); // �û���
	        mail.setPassword(password); // ���� ssc123456
	        mail.setContent("����һ�����ԣ��벻Ҫ�ظ���"); //�ʼ�����
	        mail.addAttachment(filePath); // ��Ӹ���
	        reply = mail.send();
	        
		}
		
		System.out.println(reply); // ����
		//���ͳɹ����쳣����
        if(reply.startsWith("Exception:>")){
        	request.setAttribute("state", 400);
        	request.getRequestDispatcher("/send_mail.jsp").forward(request, response);
        }
        else{
        	System.out.println("SENDER-"+ ":/>" + "�ʼ��ѷ�����ϣ�");
        	request.setAttribute("state", 200);
        	request.getRequestDispatcher("/send_mail.jsp").forward(request, response);
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
