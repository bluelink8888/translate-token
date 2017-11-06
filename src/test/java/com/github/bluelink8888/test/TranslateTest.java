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
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.github.bluelink8888.translate.TokenImpl;

/**
 * For test
 * @author YuWeiHung
 *
 */
public class TranslateTest {

  private TokenImpl tokenImpl;

  /**
   * Init setting 
   */
  @Before
  public void beforeTest() {
    tokenImpl = new TokenImpl();
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
    String googleUrl = "";
    try {
      googleUrl = "https://translate.google.com.tw/translate_a/single?"
          + "client=t&sl=en&tl=zh-TW&hl=zh-TW"
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
        System.out.println(sb.toString());
      } else {
        System.out.println("failure");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

}
