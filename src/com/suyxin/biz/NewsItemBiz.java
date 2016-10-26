package com.suyxin.biz;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.suyxin.bean.CommonException;
import com.suyxin.bean.NewsItem;
import com.suyxin.csdn.DataUtil;
import com.suyxin.csdn.URLUtil;

public class NewsItemBiz
{
    
	public static List<String> getQusItems(){
		try {
			String zhihuUrl = "http://www.zhihu.com/question/23147606";		
			String htmlContent = DataUtil.doGet(zhihuUrl);
			System.out.println(htmlContent);
			Document doc = 	Jsoup.parse(htmlContent);
			
		    Elements elements = doc.getElementsByClass("zm-editable-content");
			
		    System.out.println(String.format("总共有%s个",elements.size()));
		for (int i = 0; i < elements.size(); i++) {
			Element element = elements.get(i);
			System.out.println(element.text());
			Elements imgs = doc.getElementsByTag("img");
			for (int j = 0; j < imgs.size(); j++) {
				Element img = imgs.get(j);
				System.out.println(img.attr("data-original"));
			}
			System.out.println("------------------------------");
		}
			
			
		return null;
		} catch (Exception e) {
			
			e.printStackTrace();
			return null;
		}
		
		
	}
	
	public List<NewsItem> getNewsItems( int newsType , int currentPage) throws CommonException
	{
		String urlStr = URLUtil.generateUrl(newsType, currentPage);
		
		String htmlStr = DataUtil.doGet(urlStr);
		
		List<NewsItem> newsItems = new ArrayList<NewsItem>();
		NewsItem newsItem = null;

		  /**
         * 分析html对象的文档结构，抽出公共的结构，以些为模块解析抓数据
         * 			<div class="unit">
         <h1><a href="http://www.csdn.net/article/2016-10-20/2826663" target="_blank" >大数据技术人年度盛事! BDTC 2016将于12月8-10日在京举行</a></h1>
         <h4>发表于<span class="ago">2016-10-20 07:47</span>|<span class="view_time">167次阅读</span>|<span class="num_recom">0条评论</span></h4>
         <dl>
         <dt>
         </dt>
         <dd>100多场技术演讲，累计邀请超过130位技术专家担任演讲嘉宾。预计数千名大数据行业精英、技术专家及意见领袖将齐聚2016中国大数据技术大会。</dd>
         </dl>
         <div class="tag"><a href="http://www.csdn.net/tag/%E5%A4%A7%E6%95%B0%E6%8D%AE/news" target="_blank">大数据</a><a href="http://www.csdn.net/tag/%E6%95%B0%E6%8D%AE%E5%BA%93/news" target="_blank">数据库</a><a href="http://www.csdn.net/tag/bdtc/news" target="_blank">BDTC</a><a href="http://www.csdn.net/tag/csdn/news" target="_blank">CSDN</a></div>
         </div>

         */
		Document doc = Jsoup.parse(htmlStr);
		Elements units = doc.getElementsByClass("unit");
		for (int i = 0; i < units.size(); i++)
		{
			newsItem = new NewsItem();
			newsItem.setNewsType(newsType);

			Element unit_ele = units.get(i);

			Element h1_ele = unit_ele.getElementsByTag("h1").get(0);
			Element h1_a_ele = h1_ele.child(0);
			String title = h1_a_ele.text();
			String href = h1_a_ele.attr("href");

			newsItem.setLink(href);
			newsItem.setTitle(title);

			Element h4_ele = unit_ele.getElementsByTag("h4").get(0);
			Element ago_ele = h4_ele.getElementsByClass("ago").get(0);
			String date = ago_ele.text();

			newsItem.setDate(date);

			Element dl_ele = unit_ele.getElementsByTag("dl").get(0);// dl
			Element dt_ele = dl_ele.child(0);// dt
			try
			{// ����û��ͼƬ
				Element img_ele = dt_ele.child(0);
				String imgLink = img_ele.child(0).attr("src");
				newsItem.setImgLink(imgLink);
			} catch (IndexOutOfBoundsException e)
			{

			}
			Element content_ele = dl_ele.child(1);// dd
			String content = content_ele.text();
			newsItem.setContent(content);
			newsItems.add(newsItem);
		}

		return newsItems;

	}

}
