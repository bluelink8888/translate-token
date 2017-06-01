package org.yuwei.google_translate;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

public class Client {

  public static void main(String[] args) {

    Token token = new Token();
    
    String target = "problem";
    
    String googleUrl = "https://translate.google.com.tw/translate_a/single?"
        + "client=t&sl=en&tl=zh-TW&hl=zh-TW&dt=at&dt=bd&dt=ex&dt=ld&dt=md&dt=qca&dt=rw&dt=rm&dt=ss&dt=t&ie=UTF-8&oe=UTF-8&swap=1&source=btn&ssel=5&tsel=5&kc=0&"
        + "tk=" + token.bp(target) + "&q=" + target;

    HttpClient client = HttpClientBuilder.create().build();
    HttpGet req = new HttpGet(googleUrl);

    try {
      HttpResponse resp = client.execute(req);
      resp.addHeader("Content-Type", "application/json; charset=UTF-8");
      if (resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
        HttpEntity entity = resp.getEntity();
        BufferedReader bf = new BufferedReader(new InputStreamReader(
            entity.getContent()));
        StringBuffer sb = new StringBuffer();
        String line = "";
        while ((line = bf.readLine()) != null) {
          sb.append(line);
        }
        System.out.println(sb.toString());
      }else{
        System.out.println("failure");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
