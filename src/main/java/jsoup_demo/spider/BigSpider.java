package jsoup_demo.spider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import jsoup_demo.model.HouseIntroduction;
import jsoup_demo.util.Easyutil;

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
import org.springframework.util.StringUtils;

public class BigSpider {
	// 爬虫目标地址
	private static final String PICK_URL = "https://sz.5i5j.com/ershoufang/";
	// 请求模拟参数
	private static final String ACCEPT_VAL = "*/*";
	private static final String ACCEPT_ENCODING_VAL = "gzip, deflate, br";
	private static final String CONNECTION = "Keep-Alive";
	private static final String ACCEPT_LANGUAGE_VAL = "zh-CN,zh;q=0.9";
	private static final String CONTENT_TYPE_VAL = "application/x-www-form-urlencoded";
	private static final String USER_AGENT_VAL = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36";
	private static final String HOST_VAL = "sz.5i5j.com";
	private static final String X_REQUESTED_WITH_VAL = "XMLHttpRequest";
	
	public static void main(String[] args) {
		BasicConfigurator.configure();
		Scanner sc = new Scanner(System.in);
		List<HouseIntroduction> houses = aSpider(BigSpider.PICK_URL);
		System.out.println("你想知道房屋的哪一方面的信息呢?");
		while(sc.hasNext()){
			String consoleStr = sc.next();
			for(HouseIntroduction house : houses) {
				if(consoleStr.equals("房屋图片")) {
					System.out.println("<"+house.getTitle()+">\n<房屋图片>--"+house.getImgUrl());
				}
				if(consoleStr.equals("下载房屋图片")) {
					Easyutil.downloadPicForNews(house.getImgUrl(),"D:\\图片\\");	
				}
				if(consoleStr.equals("房屋信息标题")) {
					System.out.println("<"+house.getTitle()+">\n<房屋信息>--"+house.getTitle());
				}
				if(consoleStr.equals("房屋分布")) {
					System.out.println("<"+house.getTitle()+">\n<房屋分布>--"+house.getHouseDistribute());
				}
				if(consoleStr.equals("房屋面积")) {
					System.out.println("<"+house.getTitle()+">\n<房屋面积>--"+house.getHouseaArea());
				}
				if(consoleStr.equals("房屋朝向")) {
					System.out.println("<"+house.getTitle()+">\n<房屋朝向>--"+house.getHouseDirection());
				}
				if(consoleStr.equals("房屋楼层")) {
					System.out.println("<"+house.getTitle()+">\n<房屋楼层>--"+house.getHouseFloor());
				}
				if(consoleStr.equals("房屋装修")) {
					System.out.println("<"+house.getTitle()+">\n<房屋装修>--"+house.getHouseDecoration());
				}
				if(consoleStr.equals("房屋位置")) {
					System.out.println("<"+house.getTitle()+">\n<房屋位置>--"+house.getHouseLocation());
				}
				if(consoleStr.equals("房屋关注度")) {
					System.out.println("<"+house.getTitle()+">\n<房屋关注度>--"+house.getHouseAttention());
				}
				if(consoleStr.equals("房屋查看频率")) {
					System.out.println("<"+house.getTitle()+">\n<房屋查看频率>--"+house.getHouseLookRate());
				}
				if(consoleStr.equals("房屋信息发布时间")) {
					System.out.println("<"+house.getTitle()+">\n<房屋信息发布时间>--"+house.getHouseReleaseTime());
				}
				if(consoleStr.equals("房屋单价")) {
					System.out.println("<"+house.getTitle()+">\n<房屋单价>--"+house.getHouseUnitPrice());
				}
				if(consoleStr.equals("房屋优点标签集")) {
					System.out.println("<"+house.getTitle()+">\n<房屋优点标签集>--"+house.getHouseGoodTagList().toString());
				}
				System.out.println("");
			}
		}
	}
	
	public static List<HouseIntroduction> aSpider(String url) {
		try {
			HttpClient httpClient = HttpClients.createDefault();
			// 我爱我家全部楼盘左部分楼盘信息列表
			HttpGet httpGet = new HttpGet(url);
			httpGet.setHeader("accept", ACCEPT_VAL);
			httpGet.setHeader("Accept-Encoding",ACCEPT_ENCODING_VAL);
			httpGet.setHeader("connection", CONNECTION);
			httpGet.setHeader("Accept-Language", ACCEPT_LANGUAGE_VAL);
			httpGet.setHeader("Content-Type",CONTENT_TYPE_VAL);
			// 模拟Google浏览器请求头
			httpGet.setHeader("user-agent", USER_AGENT_VAL);
			httpGet.setHeader("Host",HOST_VAL);
			httpGet.setHeader("X-Requested-With", X_REQUESTED_WITH_VAL);
			HttpResponse response = httpClient.execute(httpGet);
	        String contents = EntityUtils.toString(response.getEntity(),"utf-8");
	        Document document = Jsoup.parse(contents);
	        System.out.println(document.toString());
	        // 列表
			Elements houseTabList = document.getElementsByClass("list-con-box").select("ul.pList").select("li");
			List<HouseIntroduction> houseIntroList = new ArrayList<HouseIntroduction>();
			if(StringUtils.isEmpty(contents)){
				throw new RuntimeException("该网站怕是被检测到了呀!!!!请注意");
			}
			for(Element e: houseTabList) {
				HouseIntroduction aHouse = new HouseIntroduction();
				// 解析房屋示例图片Div
				Elements houseImgDiv = e.getElementsByClass("listImg");
				String titleImgUrl = StringUtils.isEmpty(houseImgDiv.select("img.lazy").attr("abs:src"))?houseImgDiv.select("img.lazy").attr("abs:data-src"):houseImgDiv.select("img.lazy").attr("abs:src");
				// 房屋实例图片
				aHouse.setImgUrl(titleImgUrl);
				Elements houseContentDiv = e.getElementsByClass("listCon");
				String houseTitle = houseContentDiv.select("h3.listTit").select("a").html();
				// 房屋信息标题
				aHouse.setTitle(houseTitle);
				Elements listXEle = houseContentDiv.select("div.listX");
				Elements houseSummarys = listXEle.select("p");
				// 标题下的三行
				String oneRowText = houseSummarys.get(0).text().replaceAll(" · ", "·");
				String twoRowText = houseSummarys.get(1).text().replaceAll(" · ", "·");
				String threeRowText = houseSummarys.get(2).text().replaceAll(" · ", "·");
				// 房屋分布
				aHouse.setHouseDistribute("" != oneRowText && null != oneRowText && oneRowText.length() == 5 && StringUtils.isEmpty((oneRowText.split("·")[0]))?"":oneRowText.split("·")[0]);
				// 房屋面积
				aHouse.setHouseaArea("" != oneRowText && null != oneRowText && oneRowText.length() == 5 && StringUtils.isEmpty((oneRowText.split("·")[1]))?"":oneRowText.split("·")[1]);
				// 房屋朝向
				aHouse.setHouseDirection("" != oneRowText && null != oneRowText && oneRowText.length() == 5 && StringUtils.isEmpty((oneRowText.split("·")[2]))?"":oneRowText.split("·")[2]);
				// 房屋楼层
				aHouse.setHouseFloor("" != oneRowText && null != oneRowText && oneRowText.length() == 5 && StringUtils.isEmpty((oneRowText.split("·")[3]))?"":oneRowText.split("·")[3]);
				// 房屋装修
				if(5 == oneRowText.length()) {
					aHouse.setHouseDecoration("" != oneRowText && null != oneRowText && oneRowText.length() == 5 && StringUtils.isEmpty((oneRowText.split("·")[4]))?"":oneRowText.split("·")[4]);
				}
				// 房屋位置
				aHouse.setHouseLocation("" != twoRowText && null != twoRowText && twoRowText.length() == 1 && StringUtils.isEmpty((twoRowText.split("·")[0]))?"":twoRowText.split("·")[0]);
				// 房屋关注度
				aHouse.setHouseAttention("" != threeRowText && null != threeRowText && threeRowText.length() == 3 && StringUtils.isEmpty((threeRowText.split("·")[0]))?"":threeRowText.split("·")[0]);
				// 房屋查看频率
				aHouse.setHouseLookRate("" != threeRowText && null != threeRowText && threeRowText.length() == 3 && StringUtils.isEmpty((threeRowText.split("·")[1]))?"":threeRowText.split("·")[1]);
				// 房屋发布时间
				aHouse.setHouseReleaseTime("" != threeRowText && null != threeRowText && threeRowText.length() == 3 && StringUtils.isEmpty((threeRowText.split("·")[2]))?"":threeRowText.split("·")[2]);
				// 总价 单价
				Elements jiaDiv = listXEle.select("div.jia"); 
				String houseTotalPrice = jiaDiv.text().split(" ")[0];
				String houseUnitPrice = jiaDiv.text().split(" ")[1];
				aHouse.setHouseTotalPrice(houseTotalPrice);
				aHouse.setHouseUnitPrice(houseUnitPrice);
				// 标签集
				Elements listTagDiv = houseContentDiv.select("div.listTag").select("span");
				List<String> houseTagList = new ArrayList<String>();
				if(listTagDiv.size() > 0) {
					for(Element tagEle : listTagDiv) {
						houseTagList.add(tagEle.text());
					}
				}
				aHouse.setHouseGoodTagList(houseTagList);
				houseIntroList.add(aHouse);
			}
			return houseIntroList;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	






}
