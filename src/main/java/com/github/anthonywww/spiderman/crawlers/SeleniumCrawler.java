package com.github.anthonywww.spiderman.crawlers;

import java.util.Set;

import com.github.anthonywww.spiderman.ISpiderMan;
import com.github.anthonywww.spiderman.Report;
import com.github.anthonywww.spiderman.ReportType;
import com.github.anthonywww.spiderman.SpiderMan;

public class SeleniumCrawler extends SpiderMan implements ISpiderMan {

	private String hubUrl;

	public SeleniumCrawler(String hubUrl, int threads) {
		super();
		this.hubUrl = hubUrl;
	}

	@Override
	public void crawl(Set<String> urls) {

	}

	@Override
	public void setUserAgent(String userAgent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Report getReport() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveReportAsFile(ReportType type, String path) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageCrawl(ISpiderMan spider, Object... pageObject) {
		// TODO Auto-generated method stub
		
	}

}
