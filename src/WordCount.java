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
	private static int charNum=0;  //�ַ���
	private static int lineNum=0;  //����
	private static int wordsNum=0; //������  
	private static int sourceNum = 0;     //��������          
	private static int blankNum= 0;      //�հ�����              
	private static int commentsNum= 0;    //ע������
	
	private static  void listNext(File file) throws IOException { 		
		InputStreamReader isr=new InputStreamReader(new FileInputStream(file));
		BufferedReader br=new BufferedReader(isr);
		/**
		 * �����ļ��ַ�����������������
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
	 * ��ȡ�ļ������������հ�������ע������
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
	 * ���ļ����ַ�������ʽ��ȡ
	 */
	private static String  fromFile(File f) throws IOException {
		FileInputStream fis = new FileInputStream(f);
		byte[] b = new byte[(int) f.length()];
		//��f�ĳ��ȳ�ʼ��һ��byte�����飻�Է��صĳ���ֵ��ǿ������ת����ת����int��
		fis.read(b);
		fis.close();
		return new String(b);//���Ƿ���һ��b��ʵ��
	}
 
	/**
	 * �����ַ���
	 * ����ƥ���ַ���
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
		System.out.println("�������ļ�·��:");
		String path=input.next();
		listNext(new File(path));
		fileLine(new File(path));
		fromFile(new File(path));
		System.out.println("��������"+lineNum);
		System.out.println("�ַ���:"+charNum);
		System.out.println("������:"+wordsNum);
		System.out.println("����������"+sourceNum);
		System.out.println("�հ�����:"+blankNum);
		System.out.println("ע������:"+commentsNum);
	}
}
