package jsoup_demo.spider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.BasicConfigurator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import jsoup_demo.model.HouseIntroduction;











public class BigSpider {
	//爬虫目标地址
	private static final String PICK_URL = "https://sz.5i5j.com/ershoufang/";
	
	public static void main(String[] args) {
		BasicConfigurator.configure();
//		System.out.println();
			aSpider(BigSpider.PICK_URL);
	}
	
	public static void aSpider(String url) {
		try {
			HttpClient httpClient = HttpClients.createDefault();
			// 我爱我家全部楼盘左部分楼盘信息列表
			HttpGet httpGet = new HttpGet(url);
			httpGet.setHeader("accept", "*/*");
			httpGet.setHeader("Accept-Encoding","gzip, deflate, br");
			httpGet.setHeader("connection", "Keep-Alive");
			httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.9");
			httpGet.setHeader("Content-Type","application/x-www-form-urlencoded");
			// 模拟Google浏览器请求头
			httpGet.setHeader("user-agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36");
			httpGet.setHeader("Host","sz.5i5j.com");
			httpGet.setHeader("X-Requested-With", "XMLHttpRequest");
			HttpResponse response = httpClient.execute(httpGet);
	        String contents = EntityUtils.toString(response.getEntity(),"utf-8");
	        Document document = Jsoup.parse(contents);
	        // 列表
			Elements houseTabList = document.getElementsByClass("list-con-box").select("ul.pList").select("li");
			List<HouseIntroduction> houseIntroList = new ArrayList<HouseIntroduction>();
			HouseIntroduction aHouse = new HouseIntroduction();
			for(Element e: houseTabList) {
				// 房屋示例图片Div
				Elements houseImgDiv = e.getElementsByClass("listImg");
				String titleImgUrl = houseImgDiv.select("img.lazy").attr("abs:src");
				aHouse.setImgUrl(titleImgUrl);
				Elements houseContentDiv = e.getElementsByClass("listCon");
				String houseTitle = houseContentDiv.select("h3.listTit").select("a").html();
				aHouse.setTitle(houseTitle);
				Elements houseSummarys = houseContentDiv.select("div.listX").select("p");
				for(Element pEle : houseSummarys) {
					System.out.println(pEle.text());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	






}
