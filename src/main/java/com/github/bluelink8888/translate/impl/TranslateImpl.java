package com.github.bluelink8888.translate.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import com.github.bluelink8888.constant.Language;
import com.github.bluelink8888.translate.Token;
import com.github.bluelink8888.translate.Translate;

public class TranslateImpl implements Translate {

  private static Token tokenImpl;
  
  public static TranslateImpl create(){
    // Initial token
    tokenImpl = new TokenImpl();
    return new TranslateImpl();
  }
  
  @Override
  public String translate(String target, Language sl, Language tl) {
    String result = "";
    String googleUrl = "";
    try {
      googleUrl = "https://translate.google.com.tw/translate_a/single?"
          + "client=t&" + "sl=" + sl.getValue() + "&" + "tl=" + tl.getValue()
          + "&" + "hl=" + Language.TRADITIONAL_CHINESE.getValue()
          + "&dt=at&dt=bd&dt=ex&dt=ld&dt=md&dt=qca&dt=rw&dt=rm&dt=ss&dt=t"
          + "&ie=UTF-8&oe=UTF-8" + "&swap=1&source=btn&ssel=5&tsel=5&kc=0"
          + "&tk=" + tokenImpl.getToken(target) + "&q="
          + URLEncoder.encode(target, "UTF-8");
    } catch (UnsupportedEncodingException e1) {
      e1.printStackTrace();
    }

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
        result = sb.toString().substring(4, sb.toString().indexOf(",") - 1);
      } else {
        result = "failure";
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }

}
