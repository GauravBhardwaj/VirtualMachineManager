package com.vmware.vim25.mo.config;

import java.net.URL;
/*
 * This class will help in setting up all configuration URL's, username, password 
 * Also ip-address of vHost, alternateVhost and required Info.
*/

public class Config {
   
	static String url_lab="https://130.65.132.114/sdk"; // my Vcentre IP 
	static String adminId="administrator";
	static String password="12!@qwQW";
	static String vm_Name="VM1";
	static String alternateVhost="130.65.133.23";
	static String url_class_vcentre = "https://130.65.132.13/sdk";
	
	
	public static URL getUrl_class_vcentre() throws Exception{

		URL url = new URL(url_class_vcentre);		
		return url;
	}

	public static String getAlternateVhost() {
		return alternateVhost;
	}
	public static void setAlternateVhost(String alternateVhost) {
		Config.alternateVhost = alternateVhost;
	}
	public static URL getVmwareHostURL() throws Exception{

		URL url = new URL(url_lab);		
		return url;
	}
	public static String getVmwareLoginId(){
		return adminId;
	}  

	public static String getVmwarePassword() { 
		return  password; 
	}
	public static String getVmwareVM() {
		return vm_Name;
	}
}
