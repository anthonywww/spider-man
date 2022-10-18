package com.github.anthonywww.spiderman;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class SpiderMan implements ISpiderMan {

	private static final Logger logger = LoggerFactory.getLogger(SpiderMan.class);
	
	public static final String NAME = "spiderman";
	public static final String VERSION = "0.1.0";
	private int threadCount = Runtime.getRuntime().availableProcessors();
	private ThreadFactory workerThreadFactory;
	private ExecutorService executors;
	
	private Map<String, String> cookies;
	
	public SpiderMan() {
		cookies = new HashMap<String, String>();
	}

	public SpiderMan(int threadCount) {
		if (threadCount > 0) {
			this.threadCount = threadCount;
		}
	}

	@Override
	public int getThreadCount() {
		return threadCount;
	}
	
	@Override
	public void setCookies(Map<String, String> cookies) {
		this.cookies = cookies;
	}
	
	@Override
	public Map<String, String> getCookies() {
		return cookies;
	}
	
	@Override
	public void crawl(Set<String> urls) {

		executors = Executors.newFixedThreadPool(threadCount,
			(workerThreadFactory == null ? getDefaultThreadFactory() : workerThreadFactory)
		);
		
		logger.info("Starting worker thread pool with {} thread(s) ...", threadCount);
		
	}
	
	
	protected ThreadFactory getThreadFactory() {
		return workerThreadFactory;
	}
	
	protected ExecutorService getExecutorService() {
		return executors;
	}
	
	public static final ThreadFactory getDefaultThreadFactory() {
		
		final ThreadFactory workerThreadFactory = new ThreadFactory() {
			int worker = 0;
			@Override
			public Thread newThread(Runnable r) {
				worker++;
				return new Thread(r, "worker-" + worker);
			}
		};

		return workerThreadFactory;
	}

}
