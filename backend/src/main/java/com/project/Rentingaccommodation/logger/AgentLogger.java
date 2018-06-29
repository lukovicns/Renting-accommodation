package com.project.Rentingaccommodation.logger;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.project.Rentingaccommodation.controller.AgentController;

public class AgentLogger {
	
	private final static Logger logger = Logger.getLogger(AgentController.class.getName());
	
	public static void log(Level level, String message) {
		logger.addHandler(new ConsoleHandler());
		logger.log(level, message);
	}
}
