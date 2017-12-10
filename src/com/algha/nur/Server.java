package com.algha.nur;

public class Server {
	public static String SERVER_URL = "http://jezire.cn/nur/api.php";
	public static String APP_VERSION = "1.0";
	public static String GetNews(int page){
		return SERVER_URL+"?do=news&page="+page;
	}
	
	public static String GetInfo(String catid,int page) {
		return SERVER_URL+"?do=info&catid="+catid+"&page="+page;
	}
	
	public static String GetOneInfo(String Id){
		return SERVER_URL+"?do=get_info&id="+Id;
	}
	
	public static String GetAboutUrl(String page){
		return SERVER_URL+"?do=webview&page="+page;
	}
	
	public static String GetNewsUrl(String Id) {
		return SERVER_URL+"?do=newsview&id="+Id;
	}
	
	public static String CheckVertion(){
		return SERVER_URL+"?do=version";
	}
	
}
