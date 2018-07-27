package jsoup_demo.util;

import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.io.FileUtils;

public class Easyutil {
	
	
	/** 
     * 下载图片到本地 
     * @param picUrl 图片Url 
     * @param localPath 本地保存图片地址 
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
           int state = urlcon.getResponseCode(); //图片状态  
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
     * 根据url获取文件名 
     * @param url  
     * @return 文件名 
     */  
    public static String getFileNameFromUrl(String url){    
        //获取后缀  
        String sux = url.substring(url.lastIndexOf("."));  
        if(sux.length() > 4){  
            sux = ".jpg";  
        }  
        int i = (int)(Math.random()*1000);  
        //随机时间戳文件名称  
        String name = new Long(System.currentTimeMillis()).toString()+ i + sux;   
        return name;    
    }  
}
