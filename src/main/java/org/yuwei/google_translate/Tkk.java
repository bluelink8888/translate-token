package org.yuwei.google_translate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

public class Tkk {
  
  public List<Long> getTkkArray(){
    
    String htmlUrl = "https://translate.google.com.tw/";
    
    HttpClient client = HttpClientBuilder.create().build();
    HttpGet req = new HttpGet(htmlUrl);
    
    String tkk = null;
    
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
        tkk = target.substring(target.indexOf("TKK="), target.indexOf("WEB_TRANSLATION_PATH")-1);
      }else{
        System.out.println("failure");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    String[] tkks = tkk.split(";");
    
    List<Long> result = new ArrayList<Long>();
    
    for(int i= (tkks.length-1); i >= 0; i--){
      long temp = 0;
      if(i==2){
        temp = Long.parseLong(tkks[i].substring(tkks[i].indexOf(" ")+1, tkks[i].indexOf("+")));
        result.add(temp);
      }else{
        temp = Long.parseLong(tkks[i].substring(tkks[i].indexOf("x3d")+3));
        result.add(temp);
      }
    }
    
    return result;
  }
}
