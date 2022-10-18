package com.github.anthonywww.spiderman;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import org.fusesource.jansi.AnsiConsole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.anthonywww.spiderman.crawlers.HeadlessCrawler;
import com.github.anthonywww.spiderman.crawlers.SeleniumCrawler;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = SpiderMan.NAME, mixinStandardHelpOptions = true, version = SpiderMan.VERSION, description = "A generic web-crawler.")
public class Main implements Callable<Integer> {

	private static final Logger logger = LoggerFactory.getLogger(Main.class);
	private int threads = Runtime.getRuntime().availableProcessors();

	@Option(names = { "-u", "--urls" }, required = true, arity = "1..*", description = "URLs to begin scanning.")
	private String[] urls;

	@Option(names = { "-o", "--output" }, defaultValue = "json", description = "Set the output report type; json, excel, html. (default json)")
	private String outputFormat;

	@Option(names = { "-t", "--threads" }, defaultValue = "-1", description = "Number of threads to use when processing. (default # of cores)")
	private void setThreads(int threads) {
		if (threads > 0) {
			this.threads = threads;
		}
	}
		
	@Option(names = { "-s", "--selectors" }, arity = "0..*", description = "CSS selectors of elements to grab and save in the report.")
	private String[] cssSelectors;
	
	@Option(names = { "-f", "--follow-selectors" }, arity = "0..*", description = "CSS selectors of hrefs to follow.")
	private String[] followSelectors;

	@Option(names = {"-l", "--log-level"}, defaultValue = "INFO", description = "Set the default logger level. (default INFO)")
	private void setLogLevel(String level) {
		
		java.util.logging.Level julLevel = java.util.logging.Level.INFO;
		
		switch(level.toUpperCase()) {
		case "TRACE":
			julLevel = java.util.logging.Level.FINEST;
			break;
		case "DEBUG":
			julLevel = java.util.logging.Level.CONFIG;
			break;
		case "INFO":
			julLevel = java.util.logging.Level.INFO;
			break;
		case "WARN":
			julLevel = java.util.logging.Level.WARNING;
			break;
		case "ERROR":
			julLevel = java.util.logging.Level.SEVERE;
			break;
		default:
			System.err.println("Log level must be either: TRACE, DEBUG, INFO, WARN, ERROR");
			System.exit(1);
		}
		
		java.util.logging.Logger.getLogger("").setLevel(julLevel);
		java.util.logging.Logger.getLogger("io.netty").setLevel(java.util.logging.Level.OFF);
		java.util.logging.Logger.getLogger("javax.net").setLevel(java.util.logging.Level.OFF);
		java.util.logging.Logger.getLogger("io.perfmark.impl").setLevel(java.util.logging.Level.OFF);
	}
	
	@Override
	public Integer call() throws Exception {

		System.out.println("Initializing ...");
		
		logger.info("{} v{}", SpiderMan.NAME, SpiderMan.VERSION);
		
		ISpiderMan crawler = new HeadlessCrawler(threads);
		
		crawler.crawl(Arrays.stream(urls).collect(Collectors.toSet()));
		
		/*
		workerExecutor.execute(() -> {
			logger.info("Worker threads: {}", threads);
		});
		
		for (int i=0; i<100; i++) {
			workerExecutor.submit(() -> {
				logger.info("Worker threads: {}", threads);
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {}
			});
		}
		
		workerExecutor.awaitTermination(10, TimeUnit.SECONDS);
		*/
		
		return 0;
	}

	public static void main(String[] args) {
		
		// Install ANSI Console
		AnsiConsole.systemInstall();
		
		int exitCode = new CommandLine(new Main()).execute(args);
		System.exit(exitCode);
	}
	
	static {
		// Custom JUL properties
		try {
			java.util.logging.LogManager.getLogManager().readConfiguration(Main.class.getClassLoader().getResourceAsStream("logging.properties"));
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
