package org.yuwei.constant;

public enum Language {

  ENGLISH("英文", "en"),
  
  JAPAN("日文", "ja"),
  
  TRADITIONAL_CHINESE("繁體中文", "zh-TW"),
  
  SIMPLIFIED_CHINESE("簡體中文", "zh-CN")
  ;
  
  private String name;
  
  private String value;

  private Language(String name, String value) {
    this.name = name;
    this.value = value;
  }
  
  public String getName(){
    return name;
  }

  public String getValue() {
    return value;
  }

}
