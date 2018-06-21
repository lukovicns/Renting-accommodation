package com.project.Rentingaccommodation.utils;

import com.project.Rentingaccommodation.service.AdminService;
import com.project.Rentingaccommodation.service.AgentService;
import com.project.Rentingaccommodation.service.UserService;

public class UserUtils {

	public static boolean userExists(String email, UserService userService, AdminService adminService, AgentService agentService) {
		if (userService.findByEmail(email) != null || adminService.findByEmail(email) != null || agentService.findByEmail(email)!=null) {
			return true;
		}
		return false;
	}
}
