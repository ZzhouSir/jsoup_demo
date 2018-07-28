package jsoup_demo.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
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
    public static void downloadPicForNews(String picUrl,String localPath){  
        String filePath = "";  
        String url = "";  
        System.out.println("开始下载图片......");
        try {    
           URL httpurl = new URL(picUrl);  
           HttpURLConnection urlcon = (HttpURLConnection) httpurl.openConnection();  
           System.out.println("下载中......");
           urlcon.setReadTimeout(3000);  
           urlcon.setConnectTimeout(3000);  
           System.out.println("下载中......");
           int state = urlcon.getResponseCode(); //图片状态  
           if(state == 200){  
               String fileName = getFileNameFromUrl(picUrl);    
               filePath = localPath + fileName;  
               System.out.println("下载中......");
               File f = new File(filePath);    
               FileUtils.copyURLToFile(httpurl, f);   
           }
           System.out.println("下载完成！！！");
        } catch (Exception e) {    
        	System.out.println("下载失败！！！");
            e.printStackTrace();   
        }   
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
