package com.github.bluelink8888.constant;

/**
 * This design for different language params
 * @author YuWeiHung
 *
 */
public enum Language {

  ENGLISH("英文", "en"),
  
  FRENCH("法文", "fr"),
  
  GERMAN("德文" , "de"),
  
  JAPANESE("日文", "ja"),
  
  KOREAN("韓文", "ko"),
  
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
