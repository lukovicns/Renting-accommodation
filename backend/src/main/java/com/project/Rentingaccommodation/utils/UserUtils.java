package com.project.Rentingaccommodation.utils;

import com.project.Rentingaccommodation.service.AdminService;
import com.project.Rentingaccommodation.service.UserService;

public class UserUtils {

	public static boolean userExists(String email, UserService userService, AdminService adminService) {
		if (userService.findByEmail(email) != null || adminService.findByEmail(email) != null) {
			return true;
		}
		return false;
	}
}
