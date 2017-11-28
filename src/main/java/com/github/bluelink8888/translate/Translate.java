package com.github.bluelink8888.translate;

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
import com.github.bluelink8888.translate.impl.TokenImpl;

public interface Translate {
  
  public String translate(String target, Language sl, Language tl);

  /**
   * Default translate method
   * 
   * @param target
   *          means u want translate
   * @return
   */
  public default String translate(String target) {

    Token tokenImpl = new TokenImpl();
    String result = "";
    String googleUrl = "";
    Language sl = Language.ENGLISH; Language tl = Language.TRADITIONAL_CHINESE;
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
