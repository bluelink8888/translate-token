package com.github.bluelink8888.test;

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
import org.apache.http.util.EntityUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.github.bluelink8888.constant.Language;
import com.github.bluelink8888.translate.Token;
import com.github.bluelink8888.translate.Translate;
import com.github.bluelink8888.translate.impl.TokenImpl;
import com.github.bluelink8888.translate.impl.TranslateImpl;

/**
 * For test
 * @author YuWeiHung
 */
public class TranslateTest {

  private Token tokenImpl;
  
  private Translate tranlsateImpl;
  
  private String failure;

  /**
   * Init setting 
   */
  @Before
  public void beforeTest() {
    tokenImpl = new TokenImpl();
    tranlsateImpl = TranslateImpl.create();
    failure = "failure";
  }

  /**
   * Test token is null or not
   */
  @Test
  public void basicTest() {
    try {
      Assert.assertNotNull(tokenImpl.getToken(""));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Test translate work or not
   */
  @Test
  public void translateTest() {

    String target = "no problem";
    String result = this.translate(target, Language.ENGLISH, Language.TRADITIONAL_CHINESE);
    Assert.assertNotEquals(failure, result);
  }
  
  
  /**
   * Test all Language can translate
   */
  @Test
  public void translateLanguage(){
    String target = "test";
    for(Language language : Language.values()){
      Assert.assertNotEquals(failure, this.translate(target, Language.ENGLISH, language));
    }
  }
  
  /**
   * Test default method will return same result with implement method
   */
  @Test
  public void translateMethodTest(){
    String target = "test";

    Assert.assertEquals(tranlsateImpl.translate(target), 
        tranlsateImpl.translate(target, Language.ENGLISH, Language.TRADITIONAL_CHINESE));
  }
  
  
  /**
   * Translate feature through google service
   * @param target
   * @param sl means your target String language
   * @param tl means your result String language
   * @return translate result
   */
  public String translate(String target, Language sl, Language tl){
    String result = "";
    String googleUrl = "";
    try {
      googleUrl = "https://translate.google.com.tw/translate_a/single?"
          + "client=t&"
          + "sl=" + sl.getValue() + "&"
          + "tl=" + tl.getValue() + "&"
          + "hl=" + Language.TRADITIONAL_CHINESE.getValue()
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
        result = sb.toString().substring(4, sb.toString().indexOf(",")-1);
      } else {
        System.out.println("Http status code : " + resp.getStatusLine().getStatusCode());
        System.out.println(EntityUtils.toString(resp.getEntity()));
        result = failure;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }
}
