package com.project.Rentingaccommodation.logger;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.project.Rentingaccommodation.controller.AccommodationTypeController;

public class AccommodationTypeLogger {
	
	private final static Logger logger = Logger.getLogger(AccommodationTypeController.class.getName());

	public static void log(Level level, String message) {
		logger.addHandler(new ConsoleHandler());
		logger.log(level, message);
	}
}
