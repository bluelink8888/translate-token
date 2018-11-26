package com.github.bluelink8888.translate.impl;

import java.util.ArrayList;
import java.util.List;

import com.github.bluelink8888.translate.Token;

/**
 * This class implement Token, main feature is calculator token param
 * Token actually just need get once before u translate.
 * @author YuWeiHung
 *
 */
public class TokenImpl extends Token{
  
  private List<Long> tkArray;

  public TokenImpl() {
    super();
    tkArray = this.getTkArray();
  }
  
  /**
   * This method get google translate needs tk param,
   * actually final result is number, prevent exception happen so choose return string
   */
  @Override
  public String getToken(String t) {
    List<Integer> f = new ArrayList<Integer>();
    
    for(int i = 0; i < t.length();i++){
      int l = t.codePointAt(i);
      if(128 > l){
        f.add(l);
      }else{
        if(2048 > l){
          f.add(l >> 6 |192);
        }else{
          if (55296 == (l & 64512) && i + 1 < t.length() && 56320 == (t.codePointAt(i+1) & 64512)){
            l = 65536 + ((l & 1023) << 10) + (t.codePointAt(++i) & 1023);
            f.add(l>>18 |240);
            f.add(l >> 12 & 63 | 128);
          }else{
            f.add(l >> 12 | 224);
            f.add(l >> 6 & 63 | 128);
            f.add(l & 63 | 128);
          }
        }
      }
    }
    
    long tkk1 = tkArray.get(0);
    long tkk2 = tkArray.get(1);
    
    long a = tkk1;
    
    for(int i = 0; i < f.size(); i++){
      a += Long.parseLong(f.get(i).toString());
      a = this.zp(a, "+-a^+6");
    }
    
    a = this.zp(a, "+-3^+b+-f");
    a ^= tkk2;
    
    if(0>a){
      a = ((a & 2147483647L) + 2147483648L);
    }
    a %= 1E6;
    
    return a + "." + (a^tkk1);
  }

  /**
   * this method from google translate calculate tk param 
   */
  private long zp(long a, String b){
    long d = 0;
    for(int i = 0; i<b.length()-2; i+=3){
      String c = "" + b.charAt(i+2);
      d = b.charAt(i+2);
      d = ("a".codePointAt(0)<= d ? c.codePointAt(0) -87 : Long.parseLong(c));
      String x = "" + b.charAt(i+1);
      d = ("+".equals(x)) ? a>>>d : a<<d;
      String y = "" + b.charAt(i);
      a = ("+".equals(y)) ? a + d & 4294967295L : a ^ d;
    }
    
    return a;
  }
}
