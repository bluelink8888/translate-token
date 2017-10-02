package com.github.bluelink8888.test;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.github.bluelink8888.translate.TokenImpl;

public class TranslateTest {
  
  
  private TokenImpl tokenImpl;
  
  
  @Before
  public void beforeTest(){
    try {
      tokenImpl = new TokenImpl();
    } catch (ClientProtocolException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  @Test
  public void basicTest(){
    try {
      String token = tokenImpl.getToken("");
      System.out.println(token);
      Assert.assertNotNull(token);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  @Test
  public void translateTest(){
    Assert.assertEquals("", "");
  }
  
}
