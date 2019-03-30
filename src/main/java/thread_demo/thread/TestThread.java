package thread_demo.thread;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author zw00678
 *
 */
public class TestThread {
	
	private static final ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
	private static final ExecutorService catchedThreadPool = Executors.newCachedThreadPool();
	private static final ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(3);
	
	public static int j = 0;
	
	
	public static void main(String[] args) {
		for(int i = 1;i <= 50;i++) {
			
//			doSingleThreadWork();
			doCachedThreadWork();
		}
	}
	
    public static void doSingleThreadWork(){
        singleThreadExecutor.execute(new Runnable() {
			public void run() {
				try {
					Thread.sleep(6000);
					System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis()))+"-----singleThreadExecutor");
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
        });
    }
    public static void doCachedThreadWork(){
    	catchedThreadPool.execute(new Runnable() {
			public void run() {
				try {
					Thread.sleep(6000);
					j++;
					System.out.println("µÚ---"+j+"-----´Î");
					Thread.sleep(j);
					System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date(System.currentTimeMillis()))+"-----catchedThreadPool");
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
        });
    }
    
    public static void doscheduledThreadWork(){
//    	scheduledThreadPool.s
    	scheduledThreadPool.scheduleAtFixedRate(new Runnable() {
			
			public void run() {
				// TODO Auto-generated method stub
				
			}
		}, j, j, null);
//    	scheduledThreadPool.execute(new Runnable() {
//			
//			public void run() {
//				// TODO Auto-generated method stub
//				
//			}
//		});
    }
    
   
}
