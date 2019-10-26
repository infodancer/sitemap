package org.infodancer.sitemap;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SitemapEntry
{
	Long id;
	String url;
	Float priority;
	Date modified;
	ChangeFreq changeFreq;
	
	public SitemapEntry(String url)
	{
		this.url = url;
	}
	
	public Long getId()
	{
		return id;
	}
	
	public void setId(Long id)
	{
		this.id = id;
	}
	
	public String getUrl()
	{
		return url;
	}
	
	public void setUrl(String url)
	{
		this.url = url;
	}
	
	public Float getPriority()
	{
		return priority;
	}
	
	public void setPriority(Float priority)
	{
		this.priority = priority;
	}
	
	public Date getModified()
	{
		return modified;
	}
	
	public void setModified(Date modified)
	{
		this.modified = modified;
	}
	
	public ChangeFreq getChangeFreq()
	{
		return changeFreq;
	}

	public void setChangeFreq(ChangeFreq freq)
	{
		this.changeFreq = freq;
	}

	/**
	 * Calculates the change frequency based on the last modified time and the current time.
	 * The values ALWAYS and NEVER will not be used, as they should be set manually.
	 */
	public void calculateChangeFreq()
	{
		if (modified != null) 
		{
			long current = System.currentTimeMillis();
			long updated = modified.getTime();
			long hours = (((current - updated) / 1000) / 60 / 60);
			if (hours < 2) changeFreq = ChangeFreq.HOURLY;
			else if (hours < 24) changeFreq = ChangeFreq.DAILY;
			else if (hours < 168) changeFreq = ChangeFreq.WEEKLY;
			else if (hours < 720) changeFreq = ChangeFreq.MONTHLY;
			else changeFreq = ChangeFreq.YEARLY;
		}
	}

	public String toString()
	{
		StringBuilder s = new StringBuilder();
		s.append("<url>\n");
		s.append("<loc>");
		s.append(url);
		s.append("</loc>\n");
		if (modified != null)
		{
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			s.append("<lastmod>");
			s.append(format.format(modified));
			s.append("</lastmod>\n");
		}
		
		if (changeFreq != null)
		{
			s.append("<changefreq>");
			switch (changeFreq)
			{
				case ALWAYS: s.append("always"); break;
				case HOURLY: s.append("hourly"); break;
				case DAILY: s.append("daily"); break;
				case WEEKLY: s.append("weekly"); break;
				case MONTHLY: s.append("monthly"); break;
				case YEARLY: s.append("yearly"); break;
				case NEVER: s.append("never"); break;
			}
			s.append("</changefreq>\n");			
		}
		
		if (priority != null)
		{
			s.append("<priority>");
			s.append(Float.toString(priority));
			s.append("</priority>\n");			
		}
		s.append("</url>\n");
		return s.toString();
	}
}
