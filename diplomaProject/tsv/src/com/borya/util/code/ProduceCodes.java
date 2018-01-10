package com.borya.util.code;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

@SuppressWarnings("unused")
public class ProduceCodes {
	private final static ProduceCodes instance = new ProduceCodes();
	private String codeXmlFilePath = "src/com/borya/util/code/code.xml";
	private String codeJavaFilePath = "src/com/borya/util/code/";
	private String codeJavFileName = "StatusCode";
	
	private ProduceCodes(){
		produceFile();
	}
	
	private void produceFile() {
		try{
			File codeFile = new File(codeJavaFilePath,codeJavFileName + ".java");
			writeCodeToClass(codeFile);
			System.out.println(codeFile.getAbsolutePath());
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void writeCodeToClass(File file) throws Exception{
		file.createNewFile();
		
		DocumentBuilderFactory builderFactory;
		DocumentBuilder builder;
		Document doc;
		File codeFile;
		try {
			codeFile = new File(codeXmlFilePath);
			builderFactory = DocumentBuilderFactory.newInstance();
			builder = builderFactory.newDocumentBuilder();
			doc = builder.parse(codeFile);
		} catch(FileNotFoundException e){
			throw new RuntimeException("code.xml not found");
		} catch (Exception e) {
			throw new RuntimeException("code.xml transfrom document error");
		}
		
		NodeList codes = doc.getElementsByTagName("code");
		BufferedWriter bw = new BufferedWriter(new FileWriter(file));
		
		boolean debugSwitch = false;
		Node item = doc.getElementsByTagName("dubug-switch").item(0);
		if(item instanceof Element){
			Element ele = (Element) item;
			debugSwitch = Boolean.valueOf(ele.getTextContent());
		}
		
		String lf = "\r\n";
		String tab = "\t";
		bw.write("package com.borya.util.code;" + lf + lf);
		bw.write("public enum " + codeJavFileName + "{" + lf);
		for (int i = 0, j = codes.getLength(); i < j; i++) {
			Node node = codes.item(i);
			if(node instanceof Element){
				Element ele = (Element) node;
				String num = ele.getElementsByTagName("num").item(0).getTextContent().trim();
				String msg = ele.getElementsByTagName("msg").item(0).getTextContent().trim();
				String id = ele.getAttribute("id");
				bw.write(tab + "/**" + msg + "**/" + lf);
				if(i != j - 1){
					bw.write(tab + id + "(\"" + num + "\",\"" + msg + "\",\"\")" + "," + lf);
				}else{
					bw.write(tab + id + "(\"" + num + "\",\"" + msg + "\",\"\")" + ";" + lf);
				}
			}
		}
		bw.write(lf);
		bw.write(tab + "private String num;" + lf);
		bw.write(tab + "private String msg;" + lf);
		bw.write(tab + "private String debugInfo;" + lf);
		bw.write(tab + "private boolean debugSwitch = " + debugSwitch + ";" + lf);
		bw.write(tab + "private " + codeJavFileName + "(String num,String msg,String debugInfo){this.num=num;this.msg=msg;this.debugInfo = debugInfo;}" + lf);
		bw.write(tab + "public String num(){return num;}" + lf);
		bw.write(tab + "public String msg(){return msg;}" + lf);
		bw.write(tab + "public String debugInfo(){return debugInfo;}" + lf);
		if(debugSwitch){
			bw.write(tab + "public StatusCode debugInfo(String debugInfo){this.debugInfo = debugInfo;return this;}" + lf);
		}
		bw.write(tab + "public String toJSON() {" + lf);
		bw.write(tab + tab + "String debugInfo = this.debugInfo;" + lf);
		bw.write(tab + tab + "if(debugSwitch){this.debugInfo = \"\";return \"{\\\"code\\\":\\\"\" + num + \"\\\",\\\"msg\\\":\\\"\" + msg + \"\\\",\\\"debugInfo\\\":\\\"\" + debugInfo + \"\\\"}\";}" + lf);
		bw.write(tab + tab + "return \"{\\\"code\\\":\\\"\" + num + \"\\\",\\\"msg\\\":\\\"\" + msg + \"\\\"}\";}" + lf);
		bw.write(tab + "public String toJSON(String msg) {" + lf);
		bw.write(tab + tab + "String debugInfo = this.debugInfo;" + lf);
		bw.write(tab + tab + "if(debugSwitch){this.debugInfo = \"\";return \"{\\\"code\\\":\\\"\" + num + \"\\\",\\\"msg\\\":\\\"\" + msg + \"\\\",\\\"debugInfo\\\":\\\"\" + debugInfo + \"\\\"}\";}" + lf);
		bw.write(tab + tab + "return \"{\\\"code\\\":\\\"\" + num + \"\\\",\\\"msg\\\":\\\"\" + msg + \"\\\"}\";}" + lf);
		bw.write(tab + "public String toString() {return num;}" + lf);
		
		bw.write(tab + "public String toDiyJson(String attrName,Object value){" + lf);
		bw.write(tab + tab + "String debugInfo = this.debugInfo;" + lf);
		bw.write(tab + tab + "if(debugSwitch){this.debugInfo = \"\";return \"{\\\"code\\\":\\\"\" + num + \"\\\",\\\"msg\\\":\\\"\" + msg + \"\\\",\\\"debugInfo\\\":\\\"\" + debugInfo + \"\\\",\\\"\" + attrName + \"\\\":\" + value + \"}\";}" + lf);
		bw.write(tab + tab + "return \"{\\\"code\\\":\\\"\" + num + \"\\\",\\\"msg\\\":\\\"\" + msg + \"\\\",\\\"\" + attrName + \"\\\":\" + value + \"}\";}" + lf);
		
		bw.write(tab + "public String toDiyJson(String attrName,String value){" + lf);
		bw.write(tab + tab + "String debugInfo = this.debugInfo;" + lf);
		bw.write(tab + tab + "if(debugSwitch){this.debugInfo = \"\";return \"{\\\"code\\\":\\\"\" + num + \"\\\",\\\"msg\\\":\\\"\" + msg + \"\\\",\\\"debugInfo\\\":\\\"\" + debugInfo + \"\\\",\\\"\" + attrName + \"\\\":\\\"\" + value + \"\\\"}\";}" + lf);
		
//		bw.write(tab + tab + "if(value.startsWith(\"\"\")){" + lf);
//		bw.write(tab + tab + "return \"{\\\"code\\\":\\\"\" + num + \"\\\",\\\"msg\\\":\\\"\" + msg + \"\\\",\\\"\" + attrName + \"\\\":\" + value + \"}\";}" + lf);
//		bw.write(tab + tab + "}" + lf);
		bw.write(tab + tab + "return \"{\\\"code\\\":\\\"\" + num + \"\\\",\\\"msg\\\":\\\"\" + msg + \"\\\",\\\"\" + attrName + \"\\\":\\\"\" + value + \"\\\"}\";}" + lf);
		
		bw.write("}");
		bw.flush();
		bw.close();
	}
	
	private void write2xml(Document document,File file){
		try {
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer();
			Source xmlSource = new DOMSource(document);
			Result outputTarget = new StreamResult(file); 
			transformer.transform(xmlSource, outputTarget);
		} catch (Exception e) {
			throw new RuntimeException("write the xml file error");
		}
	}
	
	public static ProduceCodes getInstance() {
		return instance;
	}
	
	public static void main(String[] args) {
		ProduceCodes.getInstance();
	}
}
