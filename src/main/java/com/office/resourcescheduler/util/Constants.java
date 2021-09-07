package com.office.resourcescheduler.util;

import com.office.resourcescheduler.controller.UserController;

public class Constants{
   public static final String RESOURCE_SCHEDULER = "/resourcescheduler"; 
   public static final String LOGIN = "/login";
   public static final String LOGOUT = "/logout";
   public static final String RESOURCES = "/resources/**";
   public static final String RESOURCES_URI = "/resource/**";
   public static final String RESERVATIONS = "/reservation/**";
   public static final String CALENDAR = "/calendar/**";
   public static final String USERS = UserController.USER_URI + "/**";
   public static final String ACCESS_DENIED = "/accessDenied";
   public static final String RESET = "/reset/**";
   
   public static final String ERROR_RECEIVER = "skaradkar57@gmail.com"; //"shubham.karadkar@altres.in";
}

