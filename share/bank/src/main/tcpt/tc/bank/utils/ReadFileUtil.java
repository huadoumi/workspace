package tc.bank.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * path 本地路径  encoding 编码格式  returnType 返回值类型
 * 作用：用于本地文件的读入   本地测试时可以使用
 * @author linqiliang
 *
 */
public class ReadFileUtil {
	public static Object ReadFile(String path,String encoding,String type){
		BufferedReader bufferReader = null;
		String lastStr = "";
		
		try {
			FileInputStream fileInputStream = new FileInputStream(path);
			InputStreamReader inputStream = new InputStreamReader(fileInputStream,encoding);
			bufferReader = new BufferedReader(inputStream);
			String tempString = null;
			while((tempString = bufferReader.readLine()) != null){
				lastStr += tempString;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
				bufferReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if("byte[]".equals(type)){
			byte[] b = lastStr.getBytes();
			return b;
		}else{
			return lastStr;
		}
	}
	public static void main(String[] args) {
		String s = (String)ReadFileUtil.ReadFile("D://351100.xml", "UTF-8", "String");
		System.out.println(s);
	}
}
