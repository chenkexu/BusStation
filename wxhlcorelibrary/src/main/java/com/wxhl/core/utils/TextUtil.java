package com.wxhl.core.utils;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 文本工具类
 * @author caibing.zhang
 *
 */
public class TextUtil {



	public static boolean isChinese(String name) {
		boolean isResult = false;
		try {
			String comp = "[\\u4e00-\\u9fa5]+";
			Pattern pattern = Pattern.compile(comp);
			Matcher matcher = pattern.matcher(name);
			isResult = matcher.find();
		} catch (Exception e) {
			// TODO: handle exception
		}
		System.out.println("___________isResult = " + isResult);
		return isResult;
	}


	/**
	 * 字符串不能空
	 * @param value  要验证的字符串
	 * @return
	 */
	public static boolean stringIsNotNull(String value){
		return  value!=null && value.trim().length()>0;
	}
	
	/**
	 * 字符串为空
	 * @param value  要验证的字符串
	 * @return
	 */
	public static boolean stringIsNull(String value){
		return value ==null || value.trim().length()<=0;
	}

	/**
	 * 获取文本内容的长度，中文算一个字符，英文算半个字符，包括标点符号
	 * @param str
	 * @return
	 */
	public static int getTextLengthes(String str){
		int number=getTextLength(str);
		int length=number/2;
		if(number % 2 != 0){
			length+=1;
		}
		str=null;
		return length;
	}
	
	/**
	 * 获取文本内容的长度(中文算两个字符，英文算一个字符)
	 * @param str
	 * @return
	 */
	public static int getTextLength(String str){
		int length=0;
		try {
			str=new String(str.getBytes("GBK"), "ISO8859_1");
			length=str.length();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return length;
	}
	
	public static String F="%.2f";
	/**
	 * 加法
	 * @param a
	 * @param b
	 * @return
	 */
	public static String addition(String a,String b){
		if(TextUtil.stringIsNull(a)){
			return b;
		}
		if(TextUtil.stringIsNull(b)){
			return a;
		}
		double value=Double.parseDouble(a)+Double.parseDouble(b);
		return format(value);
	}
	
	/**
	 * @author caibing.zhang
	 * @createdate 2013-4-10 下午6:41:45
	 * @Description: 格式化，保留二位小数
	 * @param value
	 * @return
	 */
	public static String format(double value){
		return String.format(F, value);
	}
	
	/**
	 * 验证金额
	 * @param str
	 * @return
	 */
    public static boolean isSum(String str) { 
    	String s="^(-)?(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){1,7})?$";
        java.util.regex.Pattern pattern=java.util.regex.Pattern.compile(s); // 判断小数点后一位的数字的正则表达式
        java.util.regex.Matcher match=pattern.matcher(str); 
        if(match.matches()==false){ 
           return false; 
        } else { 
           return true; 
        } 
    }
    

}
