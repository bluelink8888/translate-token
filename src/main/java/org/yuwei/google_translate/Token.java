package org.yuwei.google_translate;

import java.util.LinkedList;
import java.util.List;

public class Token {
  
  public Object yp(Object a){
    
    return a;
  }
  
  /*
  zp = function (a, b) {
        for (var c = 0; c < b.length - 2; c += 3) {
            var d = b.charAt(c + 2)
                , d = "a" <= d ? d.charCodeAt(0) - 87 : Number(d)
                , d = "+" == b.charAt(c + 1) ? a >>> d : a << d;
            a = "+" == b.charAt(c) ? a + d & 4294967295 : a ^ d
        }
        return a
    };
  */
  // b = +-a^+6
  public long zp(long a, String b){
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
  
  public void bp(String t){
    
    List<Integer> f = new LinkedList<Integer>();
    
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
    
    long a = 0;
    
    for(int i = 0; i < f.size(); i++){
      System.out.println("a loop " + i + " : " + a);
      a += Long.parseLong(f.get(i).toString());
      a = this.zp(a, "+-a^+6");
    }
    
    a = this.zp(a, "+-3^+b+-f");
    
    a ^= 100;
    
    if(0>a){
      a = ((a & 2147483647L) + 2147483648L);
    }
    a %= 1E6;
    
    System.out.println("a : " + a);
  }
  
  
  public static void main(String[] args){
    Token token = new Token();
    // System.out.println("120730 zp : " + token.zp(120730, "+-a^+6"));
    token.bp("just");
  }
  
}
