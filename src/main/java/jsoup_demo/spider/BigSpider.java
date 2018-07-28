package jsoup_demo.spider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jsoup_demo.model.HouseIntroduction;

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
//		System.out.println();
		// 用户在console输入什么 我们可以给他当前什么类型的房屋数据
			aSpider(BigSpider.PICK_URL);
	}
	
	public static void aSpider(String url) {
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
	        // 列表
			Elements houseTabList = document.getElementsByClass("list-con-box").select("ul.pList").select("li");
			List<HouseIntroduction> houseIntroList = new ArrayList<HouseIntroduction>();
			HouseIntroduction aHouse = new HouseIntroduction();
			System.out.println(document.toString());
			if(null == houseTabList || houseTabList.size() == 0){
				throw new RuntimeException("该网站怕是检测到了呀!!!!请注意");
			}
			for(Element e: houseTabList) {
				// 解析房屋示例图片Div
				Elements houseImgDiv = e.getElementsByClass("listImg");
				String titleImgUrl = houseImgDiv.select("img.lazy").attr("abs:src");
				// 房屋实例图片
				aHouse.setImgUrl(titleImgUrl);
				Elements houseContentDiv = e.getElementsByClass("listCon");
				String houseTitle = houseContentDiv.select("h3.listTit").select("a").html();
				// 房屋信息标题
				aHouse.setTitle(houseTitle);
				Elements houseSummarys = houseContentDiv.select("div.listX").select("p");
				// 标题下的三行
				String oneRowText = houseSummarys.get(0).text().replaceAll(" · ", "·");
				String twoRowText = houseSummarys.get(1).text().replaceAll(" · ", "·");
				String threeRowText = houseSummarys.get(2).text().replaceAll(" · ", "·");
				System.out.println(oneRowText.split("·").length);
				System.out.println(oneRowText.split("·")[0]);
				System.out.println(oneRowText.split("·")[1]);
				System.out.println(oneRowText.split("·")[2]);
				System.out.println(oneRowText.split("·")[3]);
				System.out.println(oneRowText.split("·")[4]);
				System.out.println(threeRowText.split("·")[0]);
				// 房屋分布
				aHouse.setHouseDistribute("" != oneRowText && null != oneRowText && oneRowText.length() == 5 && StringUtils.isEmpty((oneRowText.split("·")[0]))?"":oneRowText.split("·")[0]);
				// 房屋面积
				aHouse.setHouseaArea("" != oneRowText && null != oneRowText && oneRowText.length() == 5 && StringUtils.isEmpty((oneRowText.split("·")[1]))?"":oneRowText.split("·")[1]);
				// 房屋朝向
				aHouse.setHouseDirection("" != oneRowText && null != oneRowText && oneRowText.length() == 5 && StringUtils.isEmpty((oneRowText.split("·")[2]))?"":oneRowText.split("·")[2]);
				// 房屋楼层
				aHouse.setHouseFloor("" != oneRowText && null != oneRowText && oneRowText.length() == 5 && StringUtils.isEmpty((oneRowText.split("·")[3]))?"":oneRowText.split("·")[3]);
				// 房屋装修
				aHouse.setHouseDecoration("" != oneRowText && null != oneRowText && oneRowText.length() == 5 && StringUtils.isEmpty((oneRowText.split("·")[4]))?"":oneRowText.split("·")[4]);
				// 房屋位置
				aHouse.setHouseLocation("" != twoRowText && null != twoRowText && twoRowText.length() == 1 && StringUtils.isEmpty((twoRowText.split("·")[0]))?"":twoRowText.split("·")[0]);
				// 房屋关注度
				aHouse.setHouseAttention("" != threeRowText && null != threeRowText && threeRowText.length() == 3 && StringUtils.isEmpty((threeRowText.split("·")[0]))?"":threeRowText.split("·")[0]);
				// 房屋查看频率
				aHouse.setHouseLookRate("" != threeRowText && null != threeRowText && threeRowText.length() == 3 && StringUtils.isEmpty((threeRowText.split("·")[1]))?"":threeRowText.split("·")[1]);
				// 房屋发布时间
				aHouse.setHouseReleaseTime("" != threeRowText && null != threeRowText && threeRowText.length() == 3 && StringUtils.isEmpty((threeRowText.split("·")[2]))?"":threeRowText.split("·")[2]);
				
				// 总价 单价
				
				// 标签集
				houseIntroList.add(aHouse);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	






}
