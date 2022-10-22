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
    	
        // 检测是否为多媒体上传
        if (!ServletFileUpload.isMultipartContent(request)) {
            // 如果不是则停止
            PrintWriter writer = response.getWriter();
            writer.println("Error: 表单必须包含 enctype=multipart/form-data");
            writer.flush();
            return;
        }
		
		DiskFileItemFactory factory = new DiskFileItemFactory();
		//2、创建一个文件上传解析器
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
	        //如果fileitem中封装的是普通输入项的数据
	        if(item.isFormField()){
	        	//String name = item.getFieldName();
	        	String value = item.getString("UTF-8");
	        	pList.add(value);//将非文件的其他参数放到一个list中，后面可以顺序的去取到
	        	//System.out.println("name"+name+"value"+value);
	        	continue;
	        }else{//如果fileitem中封装的是上传文件
	        	InputStream stream=item.getInputStream();//上传文件需要的文件流参数
	        	filename=item.getName();   //上传文件需要的参数
	        	String savepath=getServletContext().getRealPath("/WEB-INF/upload");
	        	
	            File path=new File(savepath);  //这个要自己写具体的路径，是需要上传文件需要的参数
	            
	            if(!path.exists()){ 
	                    path.mkdir(); 
	            }
	            
	            Upload.uploadFile(stream, path,filename);   //调用工具类方法
	            if(filename==null || filename.trim().equals("")){
	            	//判空处理
	            }
	            
	            filePath = savepath+"\\"+filename;
	            continue;
	        }
		}
		//开始顺序取非文件参数
		
		String sender = (String) request.getSession().getAttribute("sender");
		String receiver = pList.get(0);
		String password = (String) request.getSession().getAttribute("password");
		String content = pList.get(2);
		String subject = pList.get(1);
		String flag = (String) request.getSession().getAttribute("server");
		String reply = "";
		
		System.out.println("接收人： "+receiver);
		System.out.println("主题： "+receiver);
    	System.out.println("发送者："+sender);
    	System.out.println("密码："+password);
    	System.out.println("服务器："+flag);
    	System.out.println("文件路径："+filePath);
	
        System.out.println("SENDER-"+":/>" + "使用"+flag+"邮箱开始发送邮件...");
		if(flag.equals("163.com")){
	        // 创建邮件对象
	        SocketMailSend mail = new SocketMailSend();
	        mail.setHost("smtp.163.com"); // 邮件服务器地址
	        mail.setFrom(sender); // 发件人邮箱
	        mail.addTo(receiver); // 收件人邮箱
	        
	        //mail.addCc("test@m1.com");//抄送
	        //mail.addBcc("test@m2.com");//密送
	        
	        mail.setSubject(subject); // 邮件主题
	        mail.setUser(sender); // 用户名
	        mail.setPassword(password); // 密码 ssc123456
	        mail.setContent(content); //邮件正文
	        mail.addAttachment(filePath); // 添加附件
	        reply = mail.send();
		}else{
			//1700829748@qq.com quddqjbxsgmdeihd
	        // 创建邮件对象
	        SocketMailSend mail = new SocketMailSend();
	        mail.setHost("smtp.qq.com"); // 邮件服务器地址
	        mail.setFrom(sender); // 发件人邮箱
	        mail.addTo(receiver); // 收件人邮箱
	        
	        //mail.addCc("test@m1.com");//抄送
	        //mail.addBcc("test@m2.com");//密送
	        
	        mail.setSubject(subject); // 邮件主题
	        mail.setUser(sender); // 用户名
	        mail.setPassword(password); // 密码 ssc123456
	        mail.setContent("这是一个测试，请不要回复！"); //邮件正文
	        mail.addAttachment(filePath); // 添加附件
	        reply = mail.send();
	        
		}
		
		System.out.println(reply); // 发送
		//发送成功或异常处理
        if(reply.startsWith("Exception:>")){
        	request.setAttribute("state", 400);
        	request.getRequestDispatcher("/send_mail.jsp").forward(request, response);
        }
        else{
        	System.out.println("SENDER-"+ ":/>" + "邮件已发送完毕！");
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
