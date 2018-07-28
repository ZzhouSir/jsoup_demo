package jsoup_demo.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.io.FileUtils;


public class Easyutil {
	
	
	/** 
     * ����ͼƬ������ 
     * @param picUrl ͼƬUrl 
     * @param localPath ���ر���ͼƬ��ַ 
     * @return 
     */  
    public static void downloadPicForNews(String picUrl,String localPath){  
        String filePath = "";  
        String url = "";  
        System.out.println("��ʼ����ͼƬ......");
        try {    
           URL httpurl = new URL(picUrl);  
           HttpURLConnection urlcon = (HttpURLConnection) httpurl.openConnection();  
           System.out.println("������......");
           urlcon.setReadTimeout(3000);  
           urlcon.setConnectTimeout(3000);  
           System.out.println("������......");
           int state = urlcon.getResponseCode(); //ͼƬ״̬  
           if(state == 200){  
               String fileName = getFileNameFromUrl(picUrl);    
               filePath = localPath + fileName;  
               System.out.println("������......");
               File f = new File(filePath);    
               FileUtils.copyURLToFile(httpurl, f);   
           }
           System.out.println("������ɣ�����");
        } catch (Exception e) {    
        	System.out.println("����ʧ�ܣ�����");
            e.printStackTrace();   
        }   
    }
    
    /** 
     * ����url��ȡ�ļ��� 
     * @param url  
     * @return �ļ��� 
     */  
    public static String getFileNameFromUrl(String url){    
        //��ȡ��׺  
        String sux = url.substring(url.lastIndexOf("."));  
        if(sux.length() > 4){  
            sux = ".jpg";  
        }  
        int i = (int)(Math.random()*1000);  
        //���ʱ����ļ�����  
        String name = new Long(System.currentTimeMillis()).toString()+ i + sux;   
        return name;    
    }  
    
    public static void downloadImgToLocalPath(String fromPath,String toPath) {
    	FileOutputStream out = null;
    	InputStream ins = null;
    	try {
	    	URL url = new URL(fromPath);
	    	HttpURLConnection con = (HttpURLConnection) url.openConnection();
	        out = new FileOutputStream(toPath);
	        ins = con.getInputStream();
	        byte[] b = new byte[1024];
	        int i=0;
	        while((i=ins.read(b))!=-1){
	            out.write(b, 0, i);
	        }
    	}catch(Exception e) {
    		e.printStackTrace();
    	}finally {
    		if(null != ins) {
    			try {ins.close();}catch(Exception e) {e.printStackTrace();}
    		}
    		if(null != out) {
    			try {out.close();}catch(Exception e) {e.printStackTrace();}
    		}
		}
        
    }
}
