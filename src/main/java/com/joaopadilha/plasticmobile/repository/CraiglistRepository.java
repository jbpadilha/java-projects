package com.joaopadilha.plasticmobile.repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.joaopadilha.plasticmobile.entity.Craiglist;

public class CraiglistRepository {
	
	public List<Craiglist> findBySearch(String searchQuery) {
		URL url;
		List<Craiglist> listCraiglist = new ArrayList<Craiglist>();
		try {
			url = new URL("https://toronto.craigslist.ca/search/hhh?query="+searchQuery);
			URLConnection con = url.openConnection();
			InputStream is =con.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
			String line = null;
			
			Craiglist craiglist = new Craiglist();
			
		    while ((line = br.readLine()) != null) {
		    	
		    	if(line.contains("result-date")
		    			&& (
		    			(craiglist.getDate()==null && craiglist.getTitle() ==null) 
		    			|| 
		    			(craiglist.getDate()!=null && craiglist.getTitle() !=null))
		    	){
		    		craiglist = new Craiglist();
		    		Pattern p = Pattern.compile("datetime=\"(.*?)\"");
		    		Matcher m = p.matcher(line);
		    		while(m.find()) {
		    			LocalDateTime formatDateTime = LocalDateTime.parse(m.group(1), formatter);
		    			craiglist.setDate(formatDateTime.getMonth().getDisplayName(TextStyle.SHORT, Locale.CANADA) + " "+formatDateTime.getDayOfMonth());
		    		}
		    	}
		    	else if(line.contains("result-title")){
		    		Pattern p = Pattern.compile("<a href=\"(.*?)\" class=\"result-title hdrlnk\">(.*?)</a>");
		    		Matcher m = p.matcher(line);
		    		while(m.find()) {
		    			craiglist.setTitle(m.group(2));
		    		}
		    		listCraiglist.add(craiglist);
		    	}
		    	
		    }
		} catch (IOException e) {
			e.printStackTrace();
		}
        return listCraiglist;
	};
	
}
