package com.doms.automation.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;



import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.scheme.SchemeSocketFactory;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import sun.misc.BASE64Encoder;

import com.doms.automation.bean.EnvConfigVO;
import com.doms.automation.utils.NikeDOMSLogger;

public class PostRequest {
	private String responseXml;
	public String getResponseXml() {
		return responseXml;
	}

	public void setResponseXml(String responseXml) {
		this.responseXml = responseXml;
	}

	public synchronized String postSOAPReq(String env,String postXml,String url) {
		URL oURL;
		StringBuilder sb1 = new StringBuilder();
		 InputStream resStream = null;
		 OutputStream reqStream = null;
		try {
			SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null,  new TrustManager[] {new DefaultTrustManager()}, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
			oURL = new URL(url);
			HttpURLConnection con = (HttpURLConnection) oURL.openConnection();			
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-type", "text/xml; charset=utf-8");
			con.setDoOutput(true);
			   reqStream = con.getOutputStream();
			   reqStream.write(postXml.getBytes());
			   resStream = con.getInputStream();
			   InputStreamReader is = new InputStreamReader(resStream);
			   BufferedReader br2 = new BufferedReader(is);
				String read = br2.readLine();

				while(read != null) {
				    sb1.append(read);
				    read =br2.readLine();

				}
				NikeDOMSLogger.writeLog("NIKEDOMS_LOGGER_NAME",
						"", NikeDOMSLogger.LEVEL_INFO, "",
						"REQUEST IS"+postXml);
				NikeDOMSLogger.writeLog("NIKEDOMS_LOGGER_NAME",
						"", NikeDOMSLogger.LEVEL_INFO, "",
						"url IS"+url);
				NikeDOMSLogger.writeLog("NIKEDOMS_LOGGER_NAME",
						"", NikeDOMSLogger.LEVEL_INFO, "",
						"RESPONSE IS"+sb1.toString());
				System.out.println("RESPONSE IS"+sb1.toString());
				con.disconnect();

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			sb1.append("<Error");
			e.printStackTrace();
//			return e.getMessage();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			if(resStream!=null){
				
					try {
						resStream.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				
			}
			if(reqStream!=null){
				try {
					reqStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return sb1.toString();

	}
	
	private Map<String,String> getPacQueues(String host, String port){
		String path = "/esb-test/destinations.jsp";
		System.out.println("URL>>>"+"http://"+host+":"+port+path);
		HttpResponse response = null;
		Map<String, String> queueMap = new LinkedHashMap<String, String>();
		try{
			
		
			/*
			DefaultHttpClient httpclient = new DefaultHttpClient();
			
			
			HttpPost httppost = new HttpPost(
					"http://"+host+":"+port+path);
			
			// Execute HTTP Post Request
			response = httpclient.execute(httppost);
			
			
			HttpEntity r_entity = response.getEntity();
	          String xmlString = EntityUtils.toString(r_entity);
	          System.out.println("response: "+xmlString);
	          NikeDOMSLogger.writeLog("NIKEDOMS_LOGGER_NAME", "",
						NikeDOMSLogger.LEVEL_INFO, "",
						"xmlString>>>>" + xmlString+"<<<<<<<xmlString");*/
	          /*DocumentBuilderFactory factory = DocumentBuilderFactory
	                      .newInstance();
	          DocumentBuilder db = factory.newDocumentBuilder();
	          InputSource inStream = new InputSource(new StringReader(xmlString));
	          
	          Document doc = db.parse(inStream);
	          
	          XPathFactory xFactory = XPathFactory.newInstance();
	          XPath xpath = xFactory.newXPath();
	          
	         
	          NodeList favoris = (NodeList) xpath.evaluate("//html/body/table", doc, XPathConstants.NODESET);
	          for (int i = 0; i < favoris.getLength(); i++) {
	            Element workflow = (Element) favoris.item(i);
	            String queue = xpath.evaluate("tr/td/a", workflow);
	            queueMap.put("url_"+i, queue);
	          }*/
	          org.jsoup.nodes.Document doc =  Jsoup.parse(readURL("http://"+host+":"+port+path));
	             Elements a_tags = doc.select("a");
	             int i=0;
	             for(org.jsoup.nodes.Element p : a_tags)
	             {
	            	
	            	 queueMap.put("url_"+i, p.toString());
	            	 i++;
	             }

		}catch(Exception e){
			e.printStackTrace();
		}
		return queueMap;
	}
	private String readURL(String url) {

        String fileContents = "";
        String currentLine = "";
        InputStreamReader in=null;
       
        try {
        	in = new InputStreamReader(new URL(url).openStream());
            BufferedReader reader = new BufferedReader(in);
            fileContents = reader.readLine();
            while (currentLine != null) {
                currentLine = reader.readLine();
                fileContents += "\n" + currentLine;
            }
            reader.close();
            reader = null;
           
        } catch (Exception e) {
           // JOptionPane.showMessageDialog(null, e.getMessage(), "Error Message", JOptionPane.OK_OPTION);
            e.printStackTrace();

        }
        finally{
        	 try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
       

        return fileContents;
    }

	
	private Map<String,String> getChannelNames(String host, String port){
		
		Map<String,String> queueMap = this.getPacQueues(host,port);
		Map<String,String> channelMap = new LinkedHashMap<String, String>();
		for(int i = 0;i < queueMap.size();i++){
			String url = (String)queueMap.get("url_"+i);
			String[ ] temp;
			String[ ] temp1;
			String[ ] temp2;
			String[ ] temp3;
			String delimiter1 = ">";
			String delimiter4 = "=";
			String delimiter3 = "&";
			String  id = ""; 
			String queue ="" ;
			temp = url.split(delimiter1);
			id = temp[1];
			if ((id.trim()).equals("queue.SterlingX063Queue</a")) {
			queue = temp[0];
			temp1 = queue.split(delimiter4);
			String channel = temp1[3];
			temp2 = channel.split(delimiter3);
			channelMap.put("OrderCreationQueue",temp2[0]);
			}else if ((id.trim()).equals("queue.SterlingX088Queue</a")) {
			queue = temp[0];
			temp1 = queue.split(delimiter4);
			String channel = temp1[3];
			temp2 = channel.split(delimiter3);
			channelMap.put("GCQueue",temp2[0]);
			}else if ((id.trim()).equals("queue.SterlingX106Queue</a")) {
			queue = temp[0];
			temp1 = queue.split(delimiter4);
			String channel = temp1[3];
			temp2 = channel.split(delimiter3);
			channelMap.put("InlineQueue",temp2[0]);
			}else if ((id.trim()).equals("queue.SterlingX154Queue</a")) {
			queue = temp[0];
			temp1 = queue.split(delimiter4);
			String channel = temp1[3];
			temp2 = channel.split(delimiter3);
			channelMap.put("NikeIDShipmentUpdatesQueue",temp2[0]);
			}else if ((id.trim()).equals("queue.SterlingX105Queue</a")) {
			queue = temp[0];
			temp1 = queue.split(delimiter4);
			String channel = temp1[3];
			temp2 = channel.split(delimiter3);
			channelMap.put("NikeIDQueue",temp2[0]);
			}
			else if ((id.trim()).equals("queue.SterlingX151.3Queue</a")) {
				queue = temp[0];
				temp1 = queue.split(delimiter4);
				String channel = temp1[3];
				temp2 = channel.split(delimiter3);
				channelMap.put("NikeIDIntermediateQueue",temp2[0]);
				}
			
		}
		return channelMap;
	}
	public String postQueue(String env,String payload,String destName,String channelQueue,EnvConfigVO envConfigVO) {
		
		HttpResponse response = null;		
		String host =envConfigVO.getHostPac();
		String port=envConfigVO.getPortPac();
		String path=envConfigVO.getPathPac();
		String channelName = "";
		Map channelMap = this.getChannelNames(host,port);
		channelName = (String)channelMap.get(channelQueue);
		
		
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(
				"http://"+host+":"+port+path);		
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		nameValuePairs.add(new BasicNameValuePair("payload",
				payload));
		nameValuePairs.add(new BasicNameValuePair("destName",
				destName));
		nameValuePairs.add(new BasicNameValuePair(
				"channel",channelName));
		nameValuePairs.add(new BasicNameValuePair("type",
				"Queue"));

		synchronized (PostRequest.class) {
		try {		
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			// Execute HTTP Post Request
			response = httpclient.execute(httppost);
			System.out.println("Respone.. "+response.toString());
			
		}
		
	catch (Exception e) {
			e.printStackTrace();
		}}
		
	return response.toString();
	}
	public  HttpResponse post(String env,String xmldata,String interopApiName,String isFlow,EnvConfigVO envConfigVO) {
		
		HttpResponse response = null;
		DefaultHttpClient httpclient = new DefaultHttpClient();
		String progId=envConfigVO.getProgId();
		String servletName = envConfigVO.getServletName();
		String host =envConfigVO.getHost();
		String userID=envConfigVO.getUserID();
		String password=envConfigVO.getPassword();
		HttpPost httppost =null;
		System.out.println("url:- "+envConfigVO.getProtocol()+"://"+host+":"+envConfigVO.getDomsPort()+servletName);
		httppost = new HttpPost(
				envConfigVO.getProtocol()+"://"+host+":"+envConfigVO.getDomsPort()+servletName);
		synchronized (PostRequest.class) {
		try {
					
			
			HttpParams params = new BasicHttpParams();
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			nameValuePairs.add(new BasicNameValuePair("YFSEnvironment.progId",
					progId));
			nameValuePairs.add(new BasicNameValuePair("YFSEnvironment.userId",
					userID));
			nameValuePairs.add(new BasicNameValuePair(
					"YFSEnvironment.password", password));
			nameValuePairs.add(new BasicNameValuePair("YFSEnvironment.version",
					""));
			nameValuePairs.add(new BasicNameValuePair("YFSEnvironment.locale",
					""));
			nameValuePairs.add(new BasicNameValuePair("InteropApiName",interopApiName));
			nameValuePairs.add(new BasicNameValuePair("ServiceName", ""));
			nameValuePairs
					.add(new BasicNameValuePair("InteropApiData", xmldata));
			nameValuePairs.add(new BasicNameValuePair("TemplateData", ""));
			nameValuePairs.add(new BasicNameValuePair("IsFlow", isFlow));
			nameValuePairs.add(new BasicNameValuePair("InvokeFlow",
					"InvokeFlow"));

			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			// Execute HTTP Post Request
			response = "https".equalsIgnoreCase(envConfigVO.getProtocol())?
					getHttpClient().execute(httppost):
					httpclient.execute(httppost);
		
			
		}
		
	catch (Exception e) {
			e.printStackTrace();
		}
		
		finally{
		}
		}
	return response;
	}
	
public String postXML(String environment,String xml,String apiName,String isFlow,String assertion,EnvConfigVO envConfigVO) throws IllegalStateException, IOException{
		
		String strResponse = "";
		String responseData = "Success";

		HttpResponse response = null;
		NikeDOMSLogger.writeLog("NIKEDOMS_LOGGER_NAME",
				"PostRequest", NikeDOMSLogger.LEVEL_INFO, "postXML",
				"REQUEST IS"+xml);
		NikeDOMSLogger.writeLog("NIKEDOMS_LOGGER_NAME",
				"PostRequest", NikeDOMSLogger.LEVEL_INFO, "postXML",
				"APINAME IS"+apiName);
		System.out.println("Request IS>>>"+xml);
		response = this.post(environment, xml, apiName,isFlow,envConfigVO);
		
		if(response != null){
			strResponse =  responseToStr(response);
			setResponseXml(strResponse);
			
			if(!strResponse.contains(assertion)){
				responseData = strResponse;
			}														
		}
		
		return responseData;
	}
	public String responseToStr(HttpResponse response) throws IllegalStateException, IOException{
		StringBuilder sb1 = new StringBuilder();
		InputStream in = response.getEntity().getContent();
		InputStreamReader is = new InputStreamReader(in);
		
		BufferedReader br2 = new BufferedReader(is);
		String read = br2.readLine();
try{
		while(read != null) {
		    sb1.append(read);
		    read =br2.readLine();

		}
}
catch(Exception e){
	
}finally{
	if(null!=in){
		in.close();
		
	}
	if(null!=is){
	is.close();
	}
	if(null!=br2){
	br2.close();
	}
}
		in.close();
		
		NikeDOMSLogger.writeLog("NIKEDOMS_LOGGER_NAME",
				"PostRequest", NikeDOMSLogger.LEVEL_INFO, "responseToStr",
				"RESPONSE IS"+sb1.toString());
		System.out.println("RESPONSE IS"+sb1.toString());
		return sb1.toString();
	}
	
	public synchronized String postSOAPReqSecuredForAtgReturn(String env,String postXml,String selfServeUrl,String selfServeReturnUrl) {
		URL oURL;
		StringBuilder sb1 = new StringBuilder();
		 InputStream resStream = null;
		 OutputStream reqStream = null;
		try {
			// configure the SSLContext with a TrustManager
	        SSLContext ctx;			
				ctx = SSLContext.getInstance("TLS");
				ctx.init(new KeyManager[0], new TrustManager[] {new DefaultTrustManager()}, new SecureRandom());
			    SSLContext.setDefault(ctx);			
			oURL = new URL(selfServeUrl);
			HttpsURLConnection con = (HttpsURLConnection) oURL.openConnection();			
			con.setHostnameVerifier(new AllowAllHostnameVerifier());
			con.setDoOutput(true);			
			con.setRequestMethod("POST");
			con.setConnectTimeout(10000);
			con.setRequestProperty("Content-type", "text/xml; charset=utf-8");			
			BASE64Encoder enc = new sun.misc.BASE64Encoder();
		      String userpassword = "sharl1" + ":" + "Colasta_1i";
		      String encodedAuthorization = enc.encode( userpassword.getBytes() );
		      con.setRequestProperty("Authorization", "Basic "+		      
		            encodedAuthorization);
		   //  System.out.println("Response code.."+con.getResponseCode());
		     /* InputStream _is;
		      if (con.getResponseCode() >= 400) {
		          _is = con.getInputStream();
		      } else {
		           /* error from server */
		        //  _is = con.getErrorStream();
		    //  }
		
			   reqStream = con.getOutputStream();
			   oURL = new URL(selfServeReturnUrl);
			   con = (HttpsURLConnection) oURL.openConnection();
				//con.setHostnameVerifier(new AllowAllHostnameVerifier());
				con.setDoOutput(true);			
				con.setRequestMethod("POST");
				con.setConnectTimeout(10000);
				con.setRequestProperty("Content-type", "text/xml; charset=utf-8");			
				
			   userpassword = "sharl1" + ":" + "Colasta_1i";
			     encodedAuthorization = enc.encode( userpassword.getBytes() );
			      con.setRequestProperty("Authorization", "Basic "+		      
			            encodedAuthorization);
			   reqStream = con.getOutputStream();
			   if(!("").equals(postXml)){
			   reqStream.write(postXml.getBytes());
			   resStream = con.getInputStream();
			   InputStreamReader is = new InputStreamReader(resStream);
			   BufferedReader br2 = new BufferedReader(is);
				String read = br2.readLine();

				while(read != null) {
				    sb1.append(read);
				    read =br2.readLine();

				}
				NikeDOMSLogger.writeLog("NIKEDOMS_LOGGER_NAME",
						"", NikeDOMSLogger.LEVEL_INFO, "",
						"REQUEST IS"+postXml);
				NikeDOMSLogger.writeLog("NIKEDOMS_LOGGER_NAME",
						"", NikeDOMSLogger.LEVEL_INFO, "",
						"url IS"+selfServeReturnUrl);
				NikeDOMSLogger.writeLog("NIKEDOMS_LOGGER_NAME",
						"", NikeDOMSLogger.LEVEL_INFO, "",
						"RESPONSE IS"+sb1.toString());
				System.out.println("RESPONSE IS"+sb1.toString());
			   }
			   else{
				   sb1.append(con.getInputStream().toString());
				   
			   }
				con.disconnect();

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
		sb1.append("<Error");
			e.printStackTrace();
//			return e.getMessage();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		finally{
			if(resStream!=null){
				
					try {
						resStream.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				
			}
			if(reqStream!=null){
				try {
					reqStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return sb1.toString();

	}
	private static class DefaultTrustManager implements X509TrustManager {

        @Override
        public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}

        @Override
        public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }

	//////
	public synchronized String postSOAPReqSecured(String env,String postXml,String url) {
		URL oURL;
		StringBuilder sb1 = new StringBuilder();
		 InputStream resStream = null;
		 OutputStream reqStream = null;
		try {
			// configure the SSLContext with a TrustManager
	        SSLContext ctx;			
				ctx = SSLContext.getInstance("TLS");
				ctx.init(new KeyManager[0], new TrustManager[] {new DefaultTrustManager()}, new SecureRandom());
			    SSLContext.setDefault(ctx);			
			oURL = new URL(url);
			HttpsURLConnection con = (HttpsURLConnection) oURL.openConnection();
			
			con.setHostnameVerifier(new AllowAllHostnameVerifier());
			con.setDoOutput(true);			
			con.setRequestMethod("POST");
			con.setConnectTimeout(10000);
			con.setRequestProperty("Content-type", "text/xml; charset=utf-8");			
			BASE64Encoder enc = new sun.misc.BASE64Encoder();
		      String userpassword = "sharl1" + ":" + "Colasta_1i";
		      String encodedAuthorization = enc.encode( userpassword.getBytes() );
		      con.setRequestProperty("Authorization", "Basic "+		      
		            encodedAuthorization);
		   //  System.out.println("Response code.."+con.getResponseCode());
		     /* InputStream _is;
		      if (con.getResponseCode() >= 400) {
		          _is = con.getInputStream();
		      } else {
		           /* error from server */
		        //  _is = con.getErrorStream();
		    //  }
		
			   reqStream = con.getOutputStream();
			   if(!("").equals(postXml)){
			   reqStream.write(postXml.getBytes());
			   resStream = con.getInputStream();
			   InputStreamReader is = new InputStreamReader(resStream);
			   BufferedReader br2 = new BufferedReader(is);
				String read = br2.readLine();

				while(read != null) {
				    sb1.append(read);
				    read =br2.readLine();

				}
				NikeDOMSLogger.writeLog("NIKEDOMS_LOGGER_NAME",
						"", NikeDOMSLogger.LEVEL_INFO, "",
						"REQUEST IS"+postXml);
				NikeDOMSLogger.writeLog("NIKEDOMS_LOGGER_NAME",
						"", NikeDOMSLogger.LEVEL_INFO, "",
						"url IS"+url);
				NikeDOMSLogger.writeLog("NIKEDOMS_LOGGER_NAME",
						"", NikeDOMSLogger.LEVEL_INFO, "",
						"RESPONSE IS"+sb1.toString());
				System.out.println("RESPONSE IS"+sb1.toString());
			   }
			   else{
				   sb1.append(con.getInputStream().toString());
				   
			   }
				con.disconnect();

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
		sb1.append("<Error");
			e.printStackTrace();
//			return e.getMessage();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		finally{
			if(resStream!=null){
				
					try {
						resStream.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				
			}
			if(reqStream!=null){
				try {
					reqStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return sb1.toString();

	}
	
	
	
	private static HttpClient getHttpClient() {

		try {  
	        SSLContext ctx = SSLContext.getInstance("SSL");  
	        X509TrustManager tm = new X509TrustManager() {
	            public java.security.cert.X509Certificate[] getAcceptedIssuers() {  
	                return null;  
	            }  
	            @Override  
	            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {}  
	            @Override  
	            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {}  
	        };
	        X509HostnameVerifier verifier = new X509HostnameVerifier() {
	        	 
                @Override
                public void verify(String string, SSLSocket ssls) throws IOException {
                }
 
                public void verify(String string, X509Certificate xc) throws SSLException {
                }
 
                @Override
                public void verify(String string, String[] strings, String[] strings1) throws SSLException {
                }
 
                @Override
                public boolean verify(String string, SSLSession ssls) {
                    return true;
                }

				
            };
	        ctx.init(null, new TrustManager[]{tm}, null);
            SSLSocketFactory ssf = new SSLSocketFactory(ctx);
            ssf.setHostnameVerifier(verifier);
            DefaultHttpClient base = new DefaultHttpClient();
			ClientConnectionManager ccm = base.getConnectionManager();
            SchemeRegistry sr = ccm.getSchemeRegistry();
            sr.register(new Scheme("https", ssf, 443));
            return new DefaultHttpClient(ccm, base.getParams()); 
	    } catch (Exception ex) {  
	        ex.printStackTrace();  
	        return null;  
	    }  
	}
}
