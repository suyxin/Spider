package com.zhy.biz;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.zhy.bean.CommonException;
import com.zhy.bean.NewsItem;
import com.zhy.csdn.DataUtil;
import com.zhy.csdn.URLUtil;

/**
 * ����NewItem��ҵ����
 * @author zhy
 * 
 */
public class NewsItemBiz
{
	/**
	 * ҵ�硢�ƶ����Ƽ���
	 * 
	 * @param htmlStr
	 * @return
	 * @throws CommonException 
	 */
	public List<NewsItem> getNewsItems( int newsType , int currentPage) throws CommonException
	{
		String urlStr = URLUtil.generateUrl(newsType, currentPage);
		
		String htmlStr = DataUtil.doGet(urlStr);
		
		List<NewsItem> newsItems = new ArrayList<NewsItem>();
		NewsItem newsItem = null;

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
