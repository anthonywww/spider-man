package com.github.anthonywww.spiderman;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;

import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.UnsupportedMimeTypeException;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Worker {
	
	private static final Logger logger = LoggerFactory.getLogger(Worker.class);
	
	private Connection session;
	
	public Worker(Connection session) {
		this.session = session;
		logger.debug("Worker initialized with session Id {}", session.toString());
	}
	
	public Document requestPage(final String url) {
		try {
			return session.newRequest().url(url).get();
		} catch (MalformedURLException e) {
			logger.error("Attempted to request page for malformed URL: {}", url);
		} catch (HttpStatusException e) {
			logger.error("Non HTTP-200 status for URL: {} {}", url, e.getMessage());
		} catch (UnsupportedMimeTypeException e) {
			logger.error("Unsupported MIME Type for URL: {} {}", url, e.getMessage());
		} catch (SocketTimeoutException e) {
			logger.error("Socket timeout for URL: {}", url);
		} catch (IOException e) {
			logger.error("Exception for URL: {} {}", url, e.getMessage());
		}
		return null;
	}
	
	public String getLinkFromSelector(final String selector) {
		return null;
	}
	
	
}
