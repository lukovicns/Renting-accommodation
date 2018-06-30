package com.project.logger;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import com.project.web_service.AccommodationWebService;
import com.project.web_service.IntercepterWebService;

public class WSLogger {
	private final static Logger logger = Logger.getLogger(AccommodationWebService.class.getName());
	
	public static void log(Level level, String message) throws SecurityException, IOException {
		logger.addHandler(new ConsoleHandler());
		FileHandler fileHandler = new FileHandler(System.getProperty("user.dir") + "\\WSLogger.txt",true);
		fileHandler.setFormatter(new SimpleFormatter());
		logger.addHandler(fileHandler);
		logger.log(level, message);
	}
}
