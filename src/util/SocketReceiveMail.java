package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class SocketReceiveMail {

    private Socket socket = null;
 
    private boolean debug=true;
 
 
    /*���캯��*/
    public SocketReceiveMail(String server,int port) throws UnknownHostException, IOException{
        try{
   
            socket=new Socket(server,port);//���½�socket��ʱ����Ѿ������������������

        }catch(Exception e){
   
            e.printStackTrace();
 
        }finally{
 
            System.out.println("�������ӣ�");
        }
    }
 
    
    //�����ʼ�����
    public List<String> recieveMail(String user,String password){
            
        try {
                BufferedReader in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
       
                BufferedWriter out=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
      
                user(user,in,out);//�����û���
       
                System.out.println("user ����ִ����ϣ�");
                
                pass(password,in,out);//��������
      
                System.out.println("pass ����ִ����ϣ�");
      
                stat(in,out);
      
                System.out.println("stat ����ִ����ϣ�");
                
                
                int count= list(in,out);
                List<String> mails=new ArrayList<>() ;
                  
                System.out.println("list ����ִ����ϣ�");
                for(int i=1;i<count;i++){
                	String mail = retr(i,in,out);
                	mails.add(mail);
                	System.out.println(mail);
                }
               
                System.out.println("retr ����ִ����ϣ�");
                quit(in,out);
                
                
                System.out.println("quit ����ִ����ϣ�");
                return mails;
            }catch(Exception e){
      
                e.printStackTrace();
      
                return null;
            }
        }
    
    //�õ����������ص�һ������
    public String getReturn(BufferedReader in){
    
        String line="";
  
        try{
            line=in.readLine();
   
            if(debug){
   
                System.out.println("����������״̬:"+line);
            }
        }catch(Exception e){
 
            e.printStackTrace();
        }
        return line;
    }
 
    //�ӷ��ص������еõ���һ���ֶ�,Ҳ���Ƿ������ķ���״̬��(+OK����-ERR)
    public String getResult(String line){

        StringTokenizer st=new StringTokenizer(line," ");

        return st.nextToken();
    }
 
    //��������
    private String sendServer(String str,BufferedReader in,BufferedWriter out) throws IOException{
        
        out.write(str);//��������
 
        out.newLine();//���Ϳ���
 
        out.flush();//��ջ�����
  
        if(debug){
  
            System.out.println("�ѷ�������:"+str);
         }
         return getReturn(in);
     }
 
    //user����
    
    public void user(String user,BufferedReader in,BufferedWriter out) throws IOException{

        String result = null;
 
        result=getResult(getReturn(in));//�ȼ�����ӷ������Ƿ��Ѿ��ɹ�
 
        if(!"+OK".equals(result)){
   
            throw new IOException("���ӷ�����ʧ��!");
        }
 
        result=getResult(sendServer("user "+user,in,out));//����user����

        if(!"+OK".equals(result)){
  
            throw new IOException("�û�������!");
        }
    }
 
    //pass����
    public void pass(String password,BufferedReader in,BufferedWriter out) throws IOException{
 
        String result = null;
 
        result = getResult(sendServer("pass "+password,in,out)); 
 
        if(!"+OK".equals(result)){

            throw new IOException("�������!");
        }
    }

    
    //stat����

    public int stat(BufferedReader in,BufferedWriter out) throws IOException{
 
        String result = null;
 
        String line = null;
  
        int mailNum = 0;
 
        line=sendServer("stat",in,out); 
 
        StringTokenizer st=new StringTokenizer(line," ");
  
        result=st.nextToken();
  
        if(st.hasMoreTokens())
 
            mailNum=Integer.parseInt(st.nextToken());
 
        else{
            
            mailNum=0;
            
        }
        
        if(!"+OK".equals(result)){
 
            throw new IOException("�鿴����״̬����!");
        }
 
        System.out.println("�����ʼ�"+mailNum+"��");
        return mailNum;
 }
    
        //�޲���list���� ��ȡ�ʼ��ж��ٷ�
        public int list(BufferedReader in,BufferedWriter out) throws IOException{
        	int count=0;
        	
            String message = "";
     
            String line = null;
     
            line=sendServer("list",in,out); 
     
            while(!".".equalsIgnoreCase(line)){
            	count++;
                message=message+line+"\n";    
            
                line=in.readLine().toString();
                }
            
                System.out.println(message);
                return count;
     }
        
          //������list����
                public void list_one(int mailNumber ,BufferedReader in,BufferedWriter out) throws IOException{

                    String result = null;
                     
                    result = getResult(sendServer("list "+mailNumber,in,out)); 
             
                    if(!"+OK".equals(result)){

                        throw new IOException("list����!");
                    }
             }
 
 //�õ��ʼ���ϸ��Ϣ
 
    public String getMessagedetail(BufferedReader in) throws UnsupportedEncodingException{
 
        String message = "";
  
        String line = null;
 
        try{
            line=in.readLine().toString();
  
            while(!".".equalsIgnoreCase(line)){
   
                message=message+line+"\n";
   
                line=in.readLine().toString();
            }
        }catch(Exception e){
  
            e.printStackTrace();
        }
    
            return message;
    }
 
 //retr����
    public String retr(int mailNum,BufferedReader in,BufferedWriter out) throws IOException, InterruptedException{
  
            String result = null;
   
            result=getResult(sendServer("retr "+mailNum,in,out));
  
            if(!"+OK".equals(result)){
  
                throw new IOException("�����ʼ�����!");
            }
            
            System.out.println("��"+mailNum+"��");
            return getMessagedetail(in);
    }
 
    //�˳�
    public void quit(BufferedReader in,BufferedWriter out) throws IOException{
 
        String result;
 
        result=getResult(sendServer("QUIT",in,out));
 
        if(!"+OK".equals(result)){
  
            throw new IOException("δ����ȷ�˳�");
        }
    }
 
}

