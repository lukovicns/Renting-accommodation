package com.project.logger;

import java.io.File;
import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import com.project.web_service.IntercepterWebService;

public class AgentLogger {
	private final static Logger logger = Logger.getLogger(IntercepterWebService.class.getName());

	public static void log(Level level, String message) throws SecurityException, IOException {
		logger.addHandler(new ConsoleHandler());
		FileHandler fileHandler = new FileHandler(System.getProperty("user.dir") + "\\log.txt",true);
		fileHandler.setFormatter(new SimpleFormatter());
		logger.addHandler(fileHandler);
		logger.log(level, message);
	}
}
