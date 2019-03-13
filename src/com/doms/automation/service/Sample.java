package com.doms.automation.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.doms.automation.helper.impl.AutomationHelperImpl;
import com.doms.automation.utils.NikeDOMSApplicationUtils;

public class Sample {

	public static void main(String[] args) throws IOException {
		String xml=readFile("C:\\Users\\ssaju\\Desktop\\email.txt");
		//System.out.println(xml);
		
		AutomationHelperImpl helper=new AutomationHelperImpl();
		String data=helper.validateXPathValue(xml, "/CommunicationEvent/Data");
		String xmlAmt=helper.validateXPathValue(data, "/Order/OrderLines/OrderLine/DerivedFromOrder/@OriginalTotalAmount");
		
		String amt="595";
		System.out.println(xmlAmt);
		if(Double.parseDouble(amt)==Double.parseDouble(xmlAmt)){
			System.out.println("Success");
		}
		else{
			System.out.println("Fail");
		}
		
		
    }
	
	public static String readFile(String fileName) throws IOException {
	    BufferedReader br = new BufferedReader(new FileReader(fileName));
	    try {
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();

	        while (line != null) {
	            sb.append(line);
	            sb.append("\n");
	            line = br.readLine();
	        }
	        return sb.toString();
	    } finally {
	        br.close();
	    }
	}
	
	


}
