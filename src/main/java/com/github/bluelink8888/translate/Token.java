package com.github.bluelink8888.translate;

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

/**
 * This class design for google translate, will get param before calculate tk param 
 * @author YuWeiHung
 *
 */
public abstract class Token {

  private String googleUrl = "https://translate.google.com/";

  private List<Long> tkArray;

  public Token() {
    tkArray = this.getGoogleArray();
  }

  /**
   * This method get param from google translate website page, will return calculate tk needs param
   * Cause google hide param in javascript code
   * @return
   */
  private List<Long> getGoogleArray() {

    HttpClient client = HttpClientBuilder.create().build();
    HttpGet req = new HttpGet(googleUrl);

    String tkk = null;

    HttpResponse resp = null;
    try {
      resp = client.execute(req);
    } catch (Exception e) {
      e.printStackTrace();
    }
    if (resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
      HttpEntity entity = resp.getEntity();
      BufferedReader bf = null;
      try {
        bf = new BufferedReader(new InputStreamReader(
            entity.getContent()));
      } catch (Exception e) {
        e.printStackTrace();
      }
      StringBuffer sb = new StringBuffer();
      String line = "";
      try {
        while ((line = bf.readLine()) != null) {
          sb.append(line);
        }
      } catch (Exception e) {
        e.printStackTrace();
      }

      String target = sb.toString();
      int tkkindex = target.indexOf("tkk:") + 5;
      tkk = target.substring(tkkindex , target.indexOf("'", tkkindex));
    } else {
      System.out.println("failure");
    }

    String[] tkks = tkk.split("\\.");
    List<Long> result = new ArrayList<Long>();
    
    for(String p : tkks){
      result.add(Long.parseLong(p));
    }
    
    return result;
  }

  protected List<Long> getTkArray() {
    return tkArray;
  }

  public abstract String getToken(String input);

}
