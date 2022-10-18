package com.github.anthonywww.spiderman.crawlers;

import java.util.Set;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.anthonywww.spiderman.ISpiderMan;
import com.github.anthonywww.spiderman.Report;
import com.github.anthonywww.spiderman.ReportType;
import com.github.anthonywww.spiderman.SpiderMan;
import com.github.anthonywww.spiderman.reporters.DefaultReporter;

public class HeadlessCrawler extends SpiderMan implements ISpiderMan {
	
	private static final Logger logger = LoggerFactory.getLogger(HeadlessCrawler.class);
	
	private Connection session;
	private Report report;
	
	public HeadlessCrawler(int threads) {
		super();
	}

	@Override
	public void crawl(Set<String> urls) {
		super.crawl(urls);
		
		session = Jsoup.newSession()
			.userAgent(String.format("%s/%s (+%s)", NAME, VERSION, "https://github.com/anthonywww/spiderman"))
			.followRedirects(true)
			.ignoreHttpErrors(false)
			.cookies(getCookies());
		
		
		report = new DefaultReporter();
	}
	
	@Override
	public void setUserAgent(String userAgent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Report getReport() {
		return report;
	}

	@Override
	public void saveReportAsFile(ReportType type, String path) {
		report.saveAsFile(type, path);
	}

	@Override
	public void onPageCrawl(ISpiderMan spider, Object... pageObject) {
		
		
		
		
	}
	
	public Connection getSession() {
		return session;
	}
	
}
