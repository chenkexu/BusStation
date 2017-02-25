package com.wxhl.core.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 * Created by CaiBingZhang on 15/8/28.
 */
public class IOUtil {

    /**
     * 将输入流解析为String
     * @param is
     * @return
     * @throws Exception
     */
    public static String inputStreamToString(InputStream is)throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is,"UTF-8"));
        StringBuilder sb = new StringBuilder();
        String line ;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } finally {
            try {
                if(reader!=null){
                    reader.close();
                }
                if(is!=null){
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        line=sb.toString();
        return line;
    }

    //	// 从流中读取文件
//	public static String readTextInputStream(InputStream is) throws IOException {
//		StringBuffer strbuffer = new StringBuffer();
//		String line;
//		BufferedReader reader = null;
//		try {
//			reader = new BufferedReader(new InputStreamReader(is));
//			while ((line = reader.readLine()) != null) {
//				strbuffer.append(line).append("\r\n");
//			}
//		} finally {
//			if (reader != null) {
//				reader.close();
//			}
//		}
//		return strbuffer.toString();
//	}

    /**
     *
     * @author caibing.zhang
     * @createdate 2012-6-26 下午10:38:11
     * @Description: 字符串转InputStream
     * @param xml
     * @return
     */
    public static InputStream stringToInputStream(String xml){
        ByteArrayInputStream stream = new ByteArrayInputStream(xml.getBytes());
        return stream;
    }

    /**
     * 得到文件二进制流
     * @param fileName
     * @return
     * @throws Exception
     */
    public static byte[] getFileContent(String fileName) throws Exception{
        File file = new File(fileName);
        if( file.exists() ){
            InputStream is = new FileInputStream(file);
            return getFileByte(is);
        }
        return null;
    }

    /**
     * 输入流转byte数组
     * @param is
     * @return
     */
    public static byte[] getFileByte(InputStream is){
        try {
            byte[] buffer = new byte[1024];
            int len = -1;
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            while ((len = is.read(buffer)) != -1) {
                outStream.write(buffer, 0, len);
            }
            byte[] data = outStream.toByteArray();
            outStream.close();
            is.close();
            return data;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 关闭输入流
     * @param in
     */
    public static void closeInputStream(InputStream in){
        try {
            if(in!=null){
                in.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭输入流
     * @param in BufferedReader
     */
    public static void closeBufferedReader(BufferedReader in){
        try {
            if(in!=null){
                in.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭输出流
     * @param out
     */
    public static void closeOutputStream(OutputStream out){
        try {
            if(out!=null){
                out.flush();
                out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
