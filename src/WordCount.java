import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
 
public class WordCount {
	private static int charNum=0;  //字符数
	private static int lineNum=0;  //行数
	private static int wordsNum=0; //单词数  
	private static int sourceNum = 0;     //代码行数          
	private static int blankNum= 0;      //空白行数              
	private static int commentsNum= 0;    //注释行数
	
	private static  void listNext(File file) throws IOException { 		
		InputStreamReader isr=new InputStreamReader(new FileInputStream(file));
		BufferedReader br=new BufferedReader(isr);
		/**
		 * 计算文件字符数，行数，单词数
		 */
		while((br.read())!=-1)
		{
			String s=br.readLine();
			charNum+=s.length();
			wordsNum+=s.split(" ").length;
			lineNum++;	
		}
		isr.close();
	}
	/**
	 * 读取文件代码行数，空白行数，注释行数
	 */
	private static void fileLine(File f) throws IOException{
		String strLine = "";
		String str = fromFile(f);
		if (str.length() > 0) {
			while (str.indexOf('\n') != -1) { 
				
				strLine = str.substring(0, str.indexOf('\n')).trim();
					
				 if (strLine.length() == 0 ) {
					blankNum++;
				}
				 else if (strLine.charAt(0) == '*' || strLine.charAt(0) == '/') {
						commentsNum++;
					}else{
						sourceNum++;
						String regEx = ".*\\/\\/.*";
						if(regEx(strLine,regEx)){ 
							commentsNum++;
						}		
				}
				str = str.substring(str.indexOf('\n') + 1, str.length());
				
			}
		}   
	}
 
	/**
	 * 将文件以字符数组形式读取
	 */
	private static String  fromFile(File f) throws IOException {
		FileInputStream fis = new FileInputStream(f);
		byte[] b = new byte[(int) f.length()];
		//用f的长度初始化一个byte型数组；对返回的长度值做强制类型转换，转换成int型
		fis.read(b);
		fis.close();
		return new String(b);//就是返回一个b的实例
	}
 
	/**
	 * 输入字符串
	 * 正则匹配字符串
	 */
	private static  boolean regEx(String str,String regEx){
		Pattern p=Pattern.compile(regEx);
		Matcher m=p.matcher(str);
		boolean result=m.find();
		return result;
	}
	
	public static void main(String[] args) throws IOException {
		//File directory = new File("");
		Scanner input=new Scanner(System.in);
		System.out.println("请输入文件路径:");
		String path=input.next();
		listNext(new File(path));
		fileLine(new File(path));
		fromFile(new File(path));
		System.out.println("总行数："+lineNum);
		System.out.println("字符数:"+charNum);
		System.out.println("单词数:"+wordsNum);
		System.out.println("代码行数："+sourceNum);
		System.out.println("空白行数:"+blankNum);
		System.out.println("注释行数:"+commentsNum);
	}
}
