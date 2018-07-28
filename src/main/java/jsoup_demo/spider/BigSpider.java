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
	// ����Ŀ���ַ
	private static final String PICK_URL = "https://sz.5i5j.com/ershoufang/";
	// ����ģ�����
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
		// �û���console����ʲô ���ǿ��Ը�����ǰʲô���͵ķ�������
			aSpider(BigSpider.PICK_URL);
	}
	
	public static void aSpider(String url) {
		try {
			HttpClient httpClient = HttpClients.createDefault();
			// �Ұ��Ҽ�ȫ��¥���󲿷�¥����Ϣ�б�
			HttpGet httpGet = new HttpGet(url);
			httpGet.setHeader("accept", ACCEPT_VAL);
			httpGet.setHeader("Accept-Encoding",ACCEPT_ENCODING_VAL);
			httpGet.setHeader("connection", CONNECTION);
			httpGet.setHeader("Accept-Language", ACCEPT_LANGUAGE_VAL);
			httpGet.setHeader("Content-Type",CONTENT_TYPE_VAL);
			// ģ��Google���������ͷ
			httpGet.setHeader("user-agent", USER_AGENT_VAL);
			httpGet.setHeader("Host",HOST_VAL);
			httpGet.setHeader("X-Requested-With", X_REQUESTED_WITH_VAL);
			HttpResponse response = httpClient.execute(httpGet);
	        String contents = EntityUtils.toString(response.getEntity(),"utf-8");
	        Document document = Jsoup.parse(contents);
	        // �б�
			Elements houseTabList = document.getElementsByClass("list-con-box").select("ul.pList").select("li");
			List<HouseIntroduction> houseIntroList = new ArrayList<HouseIntroduction>();
			HouseIntroduction aHouse = new HouseIntroduction();
			System.out.println(document.toString());
			if(null == houseTabList || houseTabList.size() == 0){
				throw new RuntimeException("����վ���Ǽ�⵽��ѽ!!!!��ע��");
			}
			for(Element e: houseTabList) {
				// ��������ʾ��ͼƬDiv
				Elements houseImgDiv = e.getElementsByClass("listImg");
				String titleImgUrl = houseImgDiv.select("img.lazy").attr("abs:src");
				// ����ʵ��ͼƬ
				aHouse.setImgUrl(titleImgUrl);
				Elements houseContentDiv = e.getElementsByClass("listCon");
				String houseTitle = houseContentDiv.select("h3.listTit").select("a").html();
				// ������Ϣ����
				aHouse.setTitle(houseTitle);
				Elements houseSummarys = houseContentDiv.select("div.listX").select("p");
				// �����µ�����
				String oneRowText = houseSummarys.get(0).text().replaceAll(" �� ", "��");
				String twoRowText = houseSummarys.get(1).text().replaceAll(" �� ", "��");
				String threeRowText = houseSummarys.get(2).text().replaceAll(" �� ", "��");
				System.out.println(oneRowText.split("��").length);
				System.out.println(oneRowText.split("��")[0]);
				System.out.println(oneRowText.split("��")[1]);
				System.out.println(oneRowText.split("��")[2]);
				System.out.println(oneRowText.split("��")[3]);
				System.out.println(oneRowText.split("��")[4]);
				System.out.println(threeRowText.split("��")[0]);
				// ���ݷֲ�
				aHouse.setHouseDistribute("" != oneRowText && null != oneRowText && oneRowText.length() == 5 && StringUtils.isEmpty((oneRowText.split("��")[0]))?"":oneRowText.split("��")[0]);
				// �������
				aHouse.setHouseaArea("" != oneRowText && null != oneRowText && oneRowText.length() == 5 && StringUtils.isEmpty((oneRowText.split("��")[1]))?"":oneRowText.split("��")[1]);
				// ���ݳ���
				aHouse.setHouseDirection("" != oneRowText && null != oneRowText && oneRowText.length() == 5 && StringUtils.isEmpty((oneRowText.split("��")[2]))?"":oneRowText.split("��")[2]);
				// ����¥��
				aHouse.setHouseFloor("" != oneRowText && null != oneRowText && oneRowText.length() == 5 && StringUtils.isEmpty((oneRowText.split("��")[3]))?"":oneRowText.split("��")[3]);
				// ����װ��
				aHouse.setHouseDecoration("" != oneRowText && null != oneRowText && oneRowText.length() == 5 && StringUtils.isEmpty((oneRowText.split("��")[4]))?"":oneRowText.split("��")[4]);
				// ����λ��
				aHouse.setHouseLocation("" != twoRowText && null != twoRowText && twoRowText.length() == 1 && StringUtils.isEmpty((twoRowText.split("��")[0]))?"":twoRowText.split("��")[0]);
				// ���ݹ�ע��
				aHouse.setHouseAttention("" != threeRowText && null != threeRowText && threeRowText.length() == 3 && StringUtils.isEmpty((threeRowText.split("��")[0]))?"":threeRowText.split("��")[0]);
				// ���ݲ鿴Ƶ��
				aHouse.setHouseLookRate("" != threeRowText && null != threeRowText && threeRowText.length() == 3 && StringUtils.isEmpty((threeRowText.split("��")[1]))?"":threeRowText.split("��")[1]);
				// ���ݷ���ʱ��
				aHouse.setHouseReleaseTime("" != threeRowText && null != threeRowText && threeRowText.length() == 3 && StringUtils.isEmpty((threeRowText.split("��")[2]))?"":threeRowText.split("��")[2]);
				
				// �ܼ� ����
				
				// ��ǩ��
				houseIntroList.add(aHouse);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	






}
