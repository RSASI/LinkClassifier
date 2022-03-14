package com.valgen.lc.impl;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.json.simple.JSONObject;

public class ThreadScheduler  implements Callable<String>{
String filePath;
String pagesource;
	public ThreadScheduler(String filePath,String Pagesource){
		this.filePath=filePath;
		this.pagesource=Pagesource;
	}
	@Override
	public String call() throws Exception {
		// TODO Auto-generated method stub
		
		String tempMatchedWord=readFile(this.filePath,this.pagesource);
		return tempMatchedWord;
	}
	
//	public static String readFile(String fileName) {
//        String matchedKeyword = "";
//        try {
//            FileReader file = new FileReader(fileName);
//            BufferedReader br = new BufferedReader(file);
//            String scan;
//
//            int i=0;
//            System.out.println("starting ..");
//            while ((scan = br.readLine()) != null) {
////                str = str + "\n" + scan;
//            	String word=matchedText(Classification.pageSource,scan,0);
//            	if(!"".equals(word)){
//            		if("".equals(matchedKeyword)){
//            			matchedKeyword=word;
//            		}else{
//            			matchedKeyword=matchedKeyword+"|"+word;
//            		}
//            	}
//            	System.out.println(""+i);
//            	i++;
//            }
//            br.close();
//            file.close();
//        } catch (Exception e) {
//        	Classification.log.error(Classification.appendLog(""),e);
////            System.err.println(e + " not loaded.");
////            log.error(output.getProcessed_IP() + ":" + PID + " - not loaded ", e);
//
////            System.exit(0);
//        }
//        return matchedKeyword;
//    }
	
	
	
	 public static String readFile(String fileName,String Pagesource) {
	        String matchedKeyword = "";
	        try {
	            FileReader file = new FileReader(fileName);
	            BufferedReader br = new BufferedReader(file);
	            String scan;

//	            int i=0;
//	            System.out.println("starting ..");
	            List<String> keytwordList=new ArrayList<String>();
	            while ((scan = br.readLine()) != null) {

	            	keytwordList.add(scan);
	            	
	            }
	            br.close();
	            file.close();
	            
	            
	            matchedKeyword = keytwordList.parallelStream().filter(line->match(Pagesource,line)).map(line->matchedText(Pagesource,line,0)).collect(Collectors.joining("|")); 
	            
	            
	        } catch (Exception e) {
	        	Classification.log.error(Classification.appendLog(""),e);
//	            System.err.println(e + " not loaded.");
//	            log.error(output.getProcessed_IP() + ":" + PID + " - not loaded ", e);

//	            System.exit(0);
	        }
	        return matchedKeyword;
	    }
	public static boolean match(String blockSource, String regex) {
        Pattern regexSub = Pattern.compile(regex, Pattern.CANON_EQ | Pattern.DOTALL | Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE | Pattern.MULTILINE);
        Matcher regexMatcherSub = regexSub.matcher(blockSource);
        if (regexMatcherSub.find()) {
            return true;
        }
        return false;
    }
	 public static String matchedText(String blockSource, String reg, int groupNum) {
	        Pattern regexSub = Pattern.compile(reg, Pattern.CANON_EQ | Pattern.DOTALL | Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE | Pattern.MULTILINE);
	        Matcher regexMatcherSub = regexSub.matcher(blockSource);
	        
	        
	        if (regexMatcherSub.find()) {
	            return regexMatcherSub.group(groupNum);
	        }
	        return "";
	    }


	
	
	
}
