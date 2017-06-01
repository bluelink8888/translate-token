package org.yuwei.google_translate;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

public class Tkk {

  public static void main(String[] args) {
    String htmlUrl = "https://translate.google.com.tw/";
    
    HttpClient client = HttpClientBuilder.create().build();
    HttpGet req = new HttpGet(htmlUrl);
    
    try {
      HttpResponse resp = client.execute(req);
      if (resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
        HttpEntity entity = resp.getEntity();
        BufferedReader bf = new BufferedReader(new InputStreamReader(
            entity.getContent()));
        StringBuffer sb = new StringBuffer();
        String line = "";
        while ((line = bf.readLine()) != null) {
          sb.append(line);
        }
        
        String target = sb.toString();
        String tkk = target.substring(target.indexOf("TKK="), target.indexOf("WEB_TRANSLATION_PATH")-1);
        System.out.println("tkk : " + tkk);
        // System.out.println(sb.toString());
      }else{
        System.out.println("failure");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
