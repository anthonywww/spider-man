package com.github.anthonywww.spiderman;

import java.util.Map;
import java.util.Set;

public interface ISpiderMan {
	
	/**
	 * Set a custom user-agent string for the crawl session.
	 * 
	 * @param userAgent
	 */
	public void setUserAgent(String userAgent);
	
	/**
	 * Set of cookies to set for the crawl session.
	 * 
	 * @param cookies
	 */
	public void setCookies(Map<String, String> cookies);
	
	/**
	 * Get cookies set for this crawl session.
	 * 
	 * @return
	 */
	public Map<String, String> getCookies();
	
	/**
	 * Get the defined threads count available.
	 * 
	 * @return
	 */
	public int getThreadCount();
	
	/**
	 * Start crawling the provided list of URLs.
	 * 
	 * @param urls
	 */
	public void crawl(Set<String> urls);
	
	/**
	 * On crawl event
	 * 
	 * @param spider
	 * @param pageObject
	 */
	public void onPageCrawl(ISpiderMan spider, Object...pageObject);
	
	/**
	 * Get the report object.
	 * 
	 * @return
	 */
	public Report getReport();
	
	/**
	 * Save the report as a file at the path specified.
	 * 
	 * @param type
	 * @param path
	 */
	public void saveReportAsFile(ReportType type, String path);
	
}
