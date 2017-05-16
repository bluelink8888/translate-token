package org.yuwei.google_translate;

public class Token {
  
  public Object yp(Object a){
    return a;
  }
  
  // b = +-a^+6
  public long zp(long a, String b){
    
    for(int i = 0; i<b.length()-2; i +=3){
      String c = "" + b.charAt(i+2);
      long d = 0;
      if("a".codePointAt(0)<= c.charAt(0)){
      }
      d = ("a".codePointAt(0)<= c.charAt(0)) ? c.codePointAt(0) -87 : Long.parseLong(c);
      a = ("+".equals(b.charAt(i))) ? a + d & 4294967295L : a ^ d;
    }
    
    return a;
  }
  
}
