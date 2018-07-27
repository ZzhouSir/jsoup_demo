package jsoup_demo.util;

import java.io.File;
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
    public String downloadPicForNews(String picUrl,String localPath){  
        String filePath = "";  
        String url = "";  
        try {    
           URL httpurl = new URL(picUrl);  
           HttpURLConnection urlcon = (HttpURLConnection) httpurl.openConnection();  
           urlcon.setReadTimeout(3000);  
           urlcon.setConnectTimeout(3000);  
           int state = urlcon.getResponseCode(); //ͼƬ״̬  
           if(state == 200){  
               String fileName = getFileNameFromUrl(picUrl);    
               filePath = localPath + fileName;  
               File f = new File(filePath);    
               FileUtils.copyURLToFile(httpurl, f);   
//               Function fun = new Function();  
//               url = filePath.replace("/www/web/imgs", fun.getProValue("IMG_PATH"));  
           }  
        } catch (Exception e) {    
            e.printStackTrace();   
            return null;    
        }   
        return url;  
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
}
