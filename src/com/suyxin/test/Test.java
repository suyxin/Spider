package com.suyxin.test;

import java.util.List;

import javax.lang.model.element.Element;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.suyxin.bean.CommonException;
import com.suyxin.bean.NewsItem;
import com.suyxin.biz.NewsItemBiz;
import com.suyxin.csdn.Constaint;
import com.suyxin.csdn.DataUtil;

public class Test
{
	
	@org.junit.Test
	public void testZhiHu()
	{
	
	
		NewsItemBiz.getQusItems();
		
		
			
	}

	
	
	
	

	@org.junit.Test
	public void test01()
	{
		NewsItemBiz biz = new NewsItemBiz();
		int currentPage = 1;
		try
		{
		
			List<NewsItem> newsItems = biz.getNewsItems(Constaint.NEWS_TYPE_YEJIE, currentPage);
			for (NewsItem item : newsItems)
			{
				System.out.println(item);
			}

			System.out.println("----------------------");
		
			newsItems = biz.getNewsItems(Constaint.NEWS_TYPE_CHENGXUYUAN, currentPage);
			for (NewsItem item : newsItems)
			{
				System.out.println(item);
			}
			System.out.println("----------------------");
		
			newsItems = biz.getNewsItems(Constaint.NEWS_TYPE_YANFA, currentPage);
			for (NewsItem item : newsItems)
			{
				System.out.println(item);
			}
			System.out.println("----------------------");
		
			newsItems = biz.getNewsItems(Constaint.NEWS_TYPE_YIDONG, currentPage);
			for (NewsItem item : newsItems)
			{
				System.out.println(item);
			}
			System.out.println("----------------------");

		} catch (CommonException e)
		{
			e.printStackTrace();
		}
	}

}
