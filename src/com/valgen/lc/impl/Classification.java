package com.valgen.lc.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.management.ManagementFactory;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.security.Security;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.codehaus.jackson.JsonParser;

import com.valgen.htmldownload.parsers.HttpURLParser;
import com.valgen.htmldownload.parsers.JsoupParser;
import com.valgen.lc.model.AssetProperties;
import com.valgen.lc.model.Configuration;
import com.valgen.lc.model.FinalOutput;
import com.valgen.lc.model.HtmlHeaderAttributes;
import com.valgen.lc.model.Input;
import com.valgen.lc.model.Logtracepojo;
import com.valgen.lc.model.Result;

import com.sun.net.ssl.internal.ssl.Provider;
import static com.valgen.lc.impl.DBConnectivity.UpdateInputTable;
import static com.valgen.lc.impl.DBConnectivity.con;
//import com.valgen.lc.model.Output;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.GregorianCalendar;
import java.util.regex.PatternSyntaxException;

import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;

public class Classification {

    static Logger log = Logger.getLogger(Classification.class.getName());
//	static AssetProperties assetProperties;
//    static Input input = new Input();
//    static FinalOutput output;
//    static Result result;
    static String pid = "";
    static ObjectMapper objectMapper;
//    static javax.jms.Connection activeMQCon;
    static boolean timeoutFlag = false;
//    static    SimpleDateFormat sdf=null; 
    public static Configuration config = null;
    public static int responseTimeInSec = 0;
    public static Date date = new Date();
    static long Starttime = 0;
    static String startTime = "";
//    static JSONArray outputList = new JSONArray();
    public static SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    public static String pageSource = "";
    public static File resource = new File("Resources/");
    public static File resource1 = new File("Resources1/");
    public static String inputURL = "";
//    public static HtmlHeaderAttributes htmlHeaderAttributes = new HtmlHeaderAttributes();

    public static int id;
    public static String urlid;
    public static String extractedURL;
    public static String Linktext;
    public static String DomainURL;

    public static void main(String[] args) throws SQLException, IOException {
        // TODO Auto-generated method stub

        long start = System.currentTimeMillis();
        pid = ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
        System.out.println("pid:" + pid);
        long startTime1 = System.currentTimeMillis();
        PropertyConfigurator.configure("log4j.properties");
        sdf = new SimpleDateFormat("MMddyyyyHHmmssz");
        startTime = sdf.format(new Date());
        objectMapper = new ObjectMapper().configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        objectMapper.setVisibilityChecker(objectMapper.getSerializationConfig().getDefaultVisibilityChecker()
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withCreatorVisibility(JsonAutoDetect.Visibility.NONE));

        try {

            if (!resource.exists()) {
                log.info(appendLog("Resource Not Found"));
                log.info(createLogJson1("Bot Initiation", 0, "Resource Not Found "));
//                 log.error(appendLog(""),e);
                System.exit(0);
            }
            if (resource.listFiles().length == 0) {
                log.info(appendLog("Resource has No Files"));
                log.info(createLogJson1("Bot Initiation", 0, "Resource has No Files "));
//                 log.error(appendLog(""),e);
                System.exit(0);
            }
            if (!resource1.exists()) {
                log.info(appendLog("Resource1 Not Found"));
                log.info(createLogJson1("Bot Initiation", 0, "Resource1 Not Found "));
//                 log.error(appendLog(""),e);
                System.exit(0);
            }
            if (resource1.listFiles().length == 0) {
                log.info(appendLog("Resource1 has No Files"));
                log.info(createLogJson1("Bot Initiation", 0, "Resource1 has No Files "));
//                 log.error(appendLog(""),e);
                System.exit(0);
            }

            try {
                File file = new File("Configuration.json");
                byte[] jsonData = new byte[(int) file.length()];
                FileInputStream fin = new FileInputStream(file);
                fin.read(jsonData);
                config = objectMapper.readValue(jsonData, Configuration.class);
            } catch (FileNotFoundException e) {
                log.info(appendLog("Configuration.json File Missed"));
                log.info(createLogJson1("Bot Initiation", 0, "Configuration File Not Found " + e.getMessage()));

                log.error(appendLog(""), e);
                System.exit(0);
            } catch (Exception e) {
                log.info(appendLog("Error while reading Configuration.json"));
                log.info(createLogJson1("Bot Initiation", 0, "Error while reading Configuration.json" + e.getMessage()));

                log.error(appendLog(""), e);
              System.exit(0);
            }

            set_charset(config.getCharset());
            DBConnectivity.DBConnection();
            DBConnectivity.CreateOutputtable();
            ExecutorService executor = Executors.newFixedThreadPool(config.getThreadpool());

            System.out.println(">>>>>>>>>> DB Connected <<<<<<<<<<");
            try {
                PreparedStatement st1 = DBConnectivity.con.prepareStatement("BEGIN TRY BEGIN TRANSACTION SET DEADLOCK_PRIORITY ? COMMIT TRANSACTION END TRY BEGIN CATCH IF XACT_STATE() <> 0 ROLLBACK TRANSACTION END CATCH");
                st1.setInt(1, config.getDeadlockpriority());
                st1.executeUpdate();
                st1.clearBatch();
                st1.close();
            } catch (Exception var10) {
                log.info(appendLog("Error While Setting Deadlock Priority"));
                log.info(createLogJson1("Bot Initiated", 0, "Error While Setting Deadlock Priority " + var10.getMessage()));
                log.error(appendLog(""), var10);
                System.exit(0);
            }
            Statement st = DBConnectivity.con.createStatement();
//            ResultSet rs = st.executeQuery("select * from " + DBConnectivity.DBName + ".dbo." + DBConnectivity.Input_Table + " WITH (NOLOCK) where Status=0 and ID between " + DBConnectivity.Start + " and " + DBConnectivity.End + " and oid not in (select oid from " + DBConnectivity.DBName + ".dbo." + DBConnectivity.New_OutputTable + " ) order by id");
            ResultSet rs = st.executeQuery("select * from " + config.getDatabase() + ".dbo." + config.getInputTable() + " WITH (NOLOCK) where Status=1 and "+config.getAdditionalQuery()+" ID between " + config.getStartID() + " and " + config.getEndID() + " order by id");
            System.out.println("select * from " + config.getDatabase() + ".dbo." + config.getInputTable() + " WITH (NOLOCK) where Status=1 and  "+config.getAdditionalQuery()+" ID between " + config.getStartID() + " and " + config.getEndID() + " order by id");
//         
            while (rs.next()) {
                id = rs.getInt("id");
                urlid = rs.getString("URL_ID");
//                input.setInputId(urlid);
                extractedURL = rs.getString("extractedURL");
                System.out.println("Processsing URL "+ extractedURL);
//                input.setInputURL(extractedURL);
                Linktext = rs.getString("Link_Text");
//                input.setLinkText(Linktext);

                DomainURL = rs.getString("DomainURL");
                {

                    log.info(createLogJson1("Bot Initiation", 1, "Bot Initiation Successfull"));

                    Execute_Classification link_classier = new Execute_Classification(id, urlid, extractedURL, DomainURL);
                    executor.execute(link_classier);
                    continue;

                }
            }
            executor.shutdown();
                while (!executor.isTerminated()) {
                }
                executor.shutdown();
            long end1 = System.currentTimeMillis();

            long ProcessedTime = end1 - start;
            long second = (ProcessedTime / 1000) % 60;
            long minute = (ProcessedTime / (1000 * 60)) % 60;
            long hour = (ProcessedTime / (1000 * 60 * 60)) % 24;
            String hms = "";
            if (hour > 0) {
                hms = String.format("%02d Hr %02d Min %02d Sec", hour, minute, second);
            } else if (minute > 0) {
                hms = String.format("%02d Min %02d Sec", minute, second);
            } else if (second > 0) {
                hms = String.format("%02d Sec", second);
            } else {
                hms = String.format("%02d Sec", 1);
            }
            long stopTime = System.currentTimeMillis();

            long elapsedTime = stopTime - startTime1;

            long minutes = TimeUnit.MILLISECONDS.toMinutes(elapsedTime);
            long seconds = TimeUnit.MILLISECONDS.toSeconds(elapsedTime);
            int min = (int) seconds / 60;
            if (min != 0) {
                seconds = seconds - (min * 60);
            }
            log.info(appendLog("\nTotal Processing Time - " + hms));
            log.info(createLogJson1("Bot Termination", 1, "Record Processed Successfully. Total Time Taken for Process Completion - " + min + " Mins " + seconds + " Sec"));
//                 InsertDB();
//                 UpdateDB();
//            }
            DBConnectivity.UpdateOutputTable();
            try {
                DBConnectivity.con.close();
            } catch (Exception r) {
                r.printStackTrace();
            }
            System.exit(0);

        } catch (Exception e) {
            log.info(createLogJson1("Process Execution", 0, "Execution Failed - " + e.getMessage()));
            log.error(appendLog(""), e);
//                  List<Output> outputList=new ArrayList<Output>();
//                          outputList.add(new Output(input.getInputId(),input.getOutputId(),input.getMobId(),input.getClientID(),input.getDomainURL(),"","","","","","","","","","","","","","","","","","","","","","","","","","","","","", "", "", "", "", "", "", "", "", "", "", "Process Ends With Exception "+e.getMessage(), "Failure", startTime, sdf.format(new Date())));

//            lookUPKeyword(resource, "Execution Failed - " + e.getMessage());
//            result.setOutput(outputList);
//            String outputQueue = "";
//            try {
//                outputQueue = objectMapper.writeValueAsString(output);
//            } catch (Exception ex) {
//
//            }
//            InsertDB();
//            UpdateDB();
//            ActiveMQConnection.sendMessageToQueue(assetProperties, outputQueue);
            try {
                DBConnectivity.con.close();
            } catch (Exception r) {
                r.printStackTrace();
            }
            System.exit(0);
        }

    }

    public static void printJsonObject(JSONObject jsonObj) {
//    for (Object keyStr : jsonObj.keySet()) {
//        Object keyvalue = jsonObj.get(keyStr);
//
//        //Print key and value
//        System.out.println("key: "+ keyStr + " value: " + keyvalue);
//
//        //for nested objects iteration if required
//        //if (keyvalue instanceof JSONObject)
//        //    printJsonObject((JSONObject)keyvalue);
//    }

        ArrayList<Object> listdata = new ArrayList<Object>();

    }

    public static void makeOutputArray(int inputid, String link, String urlid, String Domainurl, String status, String US_State_City, String US_Zipcode) {
//		JSONObject tempOutput=new JSONObject();
        try {
//			org.json.JSONArray outputArray=new org.json.JSONArray(elasticResult.get_source().getOutput().toJSONString());

//		JSONParser parser = new JSONParser();
//            tempOutput.put("InputId", .getInputId());
////            tempOutput.put("OutputId", Classification.input.getOutputId());
////            tempOutput.put("MobId", Classification.input.getMobId());
////            tempOutput.put("ClientId", Classification.input.getClientID());
////            tempOutput.put("InputFilePath", Classification.input.getInputS3FilePath());
//            tempOutput.put("InputURL", input.getInputURL());
//            tempOutput.put("DomainURL", input.getDomainURL());
//            tempOutput.put("LinkText", input.getLinkText());
            String finalStatus = "";
            String finalStatusScenario = "";
            String matchedWordsInLink = "";
            String AboutusMatched = "";
            String LinkLookupMatched = "";
            if (status.equals("Process Completed") || status.equals("Not Matches") || status.equals("Page source empty")) {
                for (final File fileEntry1 : resource1.listFiles()) {
                    String fileName1 = "";
                    if (fileEntry1.isFile()) {
                        fileName1 = fileEntry1.getName();
                        String fileName_Columnname = fileName1.replaceAll("(?sim)\\.txt", "") + "";
//			matchedWordsInLink=matchKeywordsInLink("LinkLookupKeyword.txt");
                        matchedWordsInLink = matchKeywordsInLink(resource1 + "/" + fileName1, link);
                        finalStatus = "Success";
//                        tempOutput.put(fileName_Columnname, matchedWordsInLink);
                        if (fileName1.contains("About")) {
                            AboutusMatched = matchedWordsInLink;
                        }
                        if (fileName1.contains("Link")) {
                            LinkLookupMatched = matchedWordsInLink;
                        }

                    } else {
//                        tempOutput.put(fileName1, "");
//			tempOutput.put("LookupKeywordsInLink", "");
                        finalStatus = "Failure";
                    }
                }
            }

            finalStatusScenario = status;
            int botstatus = 0;
            if (!US_Zipcode.isEmpty() && !US_State_City.isEmpty()) {
                botstatus = 1;
            } else if (US_Zipcode.isEmpty() && !US_State_City.isEmpty()) {
                botstatus = 2;
            } else if (!US_Zipcode.isEmpty() && US_State_City.isEmpty()) {
                botstatus = 2;
            } else if (US_Zipcode.isEmpty() && US_State_City.isEmpty() && AboutusMatched.trim().isEmpty() && LinkLookupMatched.trim().isEmpty()) {
                botstatus = 9;
            } else {
                botstatus = 3;
            }
            DBConnectivity.insertClassifiedValues(inputid, link, urlid, Domainurl, US_State_City, US_Zipcode, AboutusMatched, LinkLookupMatched, finalStatus, "2.0", finalStatusScenario, Classification.startTime, Classification.sdf.format(new Date()), botstatus);

//            tempOutput.put("Process_Final_Status", finalStatus);
//            tempOutput.put("Version", "2.0");
//            tempOutput.put("Final_Status_Scenario", finalStatusScenario);
//            tempOutput.put("Start_Time", Classification.startTime);
//            tempOutput.put("End_Time", Classification.sdf.format(new Date()));
        } catch (PatternSyntaxException ex) {
	// Syntax error in the regular expression
        }
    }
//            printJsonObject(tempOutput);

    public static void lookUPKeyword(int inputid, String link, String urlid, String Domainurl, File folder, String status,String Psource) {
        JSONObject tempOutput = new JSONObject();
        String US_City_State_MatchedWord = "";
        String US_ZipCode_MatchedWord = "";
        boolean flag = false;
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isFile()) {

                String fileName = fileEntry.getName();
                fileName = fileName.replaceAll("(?sim)\\.txt", "") + "_MatchedWord";
                String matchedWords = "";
                String matchedWordsInLink = "";
                if (status.equalsIgnoreCase("Process Completed")) {
//				matchedWords=readFile(fileEntry.getPath());
                    int timeOutForProcessing = config.getTimeoutForProcessingInMinutes() * 60;
                    timeOutForProcessing = timeOutForProcessing - config.getTimeRelayInSeconds();

                    ExecutorService executor = Executors.newSingleThreadExecutor();
//		              ExecutorService executor = Executors.newFixedThreadPool(10);
                    Future<String> future = executor.submit(new ThreadScheduler(fileEntry.getPath(),Psource));

                    try {
//		                

                        matchedWords = future.get(timeOutForProcessing, TimeUnit.MINUTES);
                        Thread.sleep(1000);

                    } catch (TimeoutException e) {
                        future.cancel(true);
                        status = "Process Timeout";
                        matchedWords = "";
                        timeoutFlag = true;

                    } catch (Exception e) {
                        log.error("Exception ", e);
                    }

                    executor.shutdownNow();

                    if ("".equals(matchedWords)) {
                        matchedWords = "";
                    }

                }

                if (fileName.contains("City")) {
                    US_City_State_MatchedWord = matchedWords;
                }
                if (fileName.contains("ZipCode")) {
                    US_ZipCode_MatchedWord = matchedWords;
                }

//                tempOutput.put(fileName, matchedWords);
            }
        }

//		status="Not Matches";
        makeOutputArray(inputid, link, urlid, Domainurl, status, US_City_State_MatchedWord, US_ZipCode_MatchedWord);
//		outputList.add(output);
    }

    public static String contentNormalization(String content) {
        String normalizedContent = content;
        try {
//            normalizedContent = normalizedContent.replaceAll("(?si)<script[^<>]*?>.*?</script>", " ");
//            normalizedContent = normalizedContent.replaceAll("(?si)<iframe[^<>]*?>.*?</iframe>", " ");
//            normalizedContent = normalizedContent.replaceAll("(?si)<iframe[^<>]*?>", "");
//            normalizedContent = normalizedContent.replaceAll("&#65533;", "'");
//            normalizedContent = normalizedContent.replaceAll("(?si)<img[^<>]*?>|</img>", " ");
//            normalizedContent = normalizedContent.replaceAll("(?si)<style[^<>]*?>.*?</style>", " ");
//            normalizedContent = normalizedContent.replaceAll("(?si)<noscript[^<>]*?>.*?</noscript>", " ");
//            normalizedContent = normalizedContent.replaceAll("<noscript/>", "");
//            normalizedContent = normalizedContent.replaceAll("(?sim)<head>.*?</head>", " ");
//            normalizedContent = normalizedContent.replaceAll("(?sim)<title\\s*[^<>]*>[^<>]*</title>", " ");
            normalizedContent = normalizedContent.replaceAll("(?sim)<!--.*?-->", " ");
            normalizedContent = normalizedContent.replaceAll("(?sim)<!--[^<>]*>", " ");

//            normalizedContent = normalizedContent.replaceAll("(?sim)&nbsp;", " ");
//            normalizedContent = normalizedContent.replaceAll("(?sim)<.*?>", " ");
//            normalizedContent = normalizedContent.replaceAll("(?sim)\\s\\s+", " ");
//            normalizedContent = normalizedContent.trim();
        } catch (Exception e) {
            log.error("Error contentNormalization():", e);
        }
        return normalizedContent;
    }

    public static void doTrustToCertificates() throws Exception {
        Security.addProvider(new Provider());

        TrustManager[] trustAllCerts = {new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            public void checkServerTrusted(X509Certificate[] certs, String authType)
                    throws CertificateException {
            }

            public void checkClientTrusted(X509Certificate[] certs, String authType)
                    throws CertificateException {
            }
        }};
        SSLContext sc = SSLContext.getInstance("TLS");
        sc.init(null, trustAllCerts, new SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        HostnameVerifier hv = new HostnameVerifier() {
            public boolean verify(String urlHostName, SSLSession session) {
                if (!urlHostName.equalsIgnoreCase(session.getPeerHost())) {
//                    System.out.println("Warning: URL host '" + urlHostName + "' is different to SSLSession host '" + session.getPeerHost() + "'.");
                    log.info(appendLog("Warning: URL host '" + urlHostName + "' is different to SSLSession host '" + session.getPeerHost() + "'."));
                }
                return true;
            }
        };
        HttpsURLConnection.setDefaultHostnameVerifier(hv);
    }

    public static String matchKeywordsInLink(String fileName, String url) {
        String matchedKeywords = "";

        try {
            FileReader file = new FileReader(fileName);
            BufferedReader br = new BufferedReader(file);
            String scan;
            String link = "";
            if (config.isOnline()) {
                link = url;
            } else {
                link = inputURL;
            }

            link = link.replaceAll("(?sim)/$", "");
            link = link.replaceAll("(?sim)http://|https://", "");
            link = link.replaceAll("(?sim)(.*?)/([^/]+)$", "$2");
            while ((scan = br.readLine()) != null) {
//                str = str + "\n" + scan;

                if (link.toLowerCase().contains(scan.toLowerCase())) {

                    if ("".equals(matchedKeywords)) {
                        matchedKeywords = scan;
                    } else {
                        matchedKeywords = matchedKeywords + "|" + scan;
                    }
                }

            }
            br.close();
            file.close();
        } catch (Exception e) {
            Classification.log.error(Classification.appendLog(""), e);
//            System.err.println(e + " not loaded.");
//            log.error(output.getProcessed_IP() + ":" + PID + " - not loaded ", e);

//            System.exit(0);
        }

        return matchedKeywords;
    }

    public static void set_charset(String EncodingCharset) {
        try {
//            System.setProperty("file.encoding", "utf-8");
//            System.setProperty("file.encoding", "cp1252");
            System.setProperty("file.encoding", EncodingCharset);
            Field charset = Charset.class.getDeclaredField("defaultCharset");
            charset.setAccessible(true);
            charset.set(null, null);
        } catch (Exception e) {
//            e.printStackTrace();
//            log.error("Exception : ", e);
//            log.error(appendLog(" - Exception : "), e);
            log.error(appendLog(" - Exception : "), e);
        }
    }

    public static String cleansing(String paseSource) {

        paseSource = paseSource.replaceAll("(?sim)\\s+", " ");
        paseSource = paseSource.replaceAll("(?sim)<!--.*?-->", " ");
        paseSource = paseSource.replaceAll("(?sim)&#[0-9]{1,5};", " ");
        paseSource = paseSource.replaceAll("(?sim)Ã‚Â ", " ");
        paseSource = paseSource.replaceAll("(?sim)&nbsp;", " ");
        paseSource = paseSource.replaceAll("(?sim)&ensp;", " ");
        paseSource = paseSource.replaceAll("(?sim)&emsp;", " ");
        paseSource = paseSource.replaceAll("(?sim)&thinsp;", " ");

        return paseSource;

    }

    public static String appendLog(String message) {

//        return output.getProcessed_IP() + ":" + pid + "-" + message;
        return inputURL + ":" + pid + "-" + message;
    }

    public static String createLogJson1(String stage,
            int status, String log_message) {
        String json = null;
        try {
            Logtracepojo logtracepojo = new Logtracepojo();
            logtracepojo.setStage(stage);
            logtracepojo.setStatus(status + "");
            logtracepojo.setLogMessage(log_message);
            logtracepojo.setTime(String.valueOf(sdf.format(new Date())));
//            logtracepojo.setProcessed_IP(output.getProcessed_IP());
//            logtracepojo.setBatchName(output.getBatch_Name());
//            logtracepojo.setBatchId(output.getBatch_Configuartion_ID());
            logtracepojo.setPid(Long.parseLong(pid));
            logtracepojo.setBot_name("LinkClassification_V2.0");
//                         List<Input> inputList=new ArrayList<Input>();
//                         inputList.add(input);
//            logtracepojo.setInput_arguments(input);
// 			                 ObjectWriter ow = new ObjectMapper().writer();
            ObjectWriter ow = new ObjectMapper().writer();
            json = ow.writeValueAsString(logtracepojo);
        } catch (Exception e) {
            log.error(appendLog(""), e);
            // LOG.error("Unable to write jdon log message" + e.getMessage() +
            // ", Refer token:" + access_token);
        }
        return json;

    }

    public static String GetPageContentFromSMBServer() throws MalformedURLException, IOException, Exception {
//        String content = "";
//        boolean isSuccess = false;
//        int attempt = 0;
//        InputStream in = null;
//        OutputStream out = null;
//        do {
//            try {
//                attempt++;
//                //Get a picture
//                String shareIP = config.getFileServerIP().replaceAll("/.*", "");
////             String remotePhotoUrl = "smb://mobius365:mobius365@192.168.0.214/e/Madhu/Html_29Aug2018"; //The shared directory to store pictures
////                 String remoteUrl = "smb://" + config.getFileServerUsername() + ":" + config.getFileServerPassword() + "@" + config.getFileServerIP() + "/" + input.getInput_FilePath(); //The shared directory to store pictures
//                String url = "smb://" + shareIP + "/" + input.getInputS3FilePath();
//
////                   String tempFolderPath= "smb://"+shareIP+"/";
//                NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(null, config.getFileServerUsername(), config.getFileServerPassword());
////                 String remoteUrl = "smb://" + config.getFileServerUsername() + ":" + config.getFileServerPassword() + "@" + shareIP + "/" + input.getInput_FilePath(); //The shared directory to store pictures
////                 SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmssSSS_");
//
////                 SmbFile remoteFile = new SmbFile(tempFolderPath,auth);
////                     remoteFile.connect();
//                SmbFile remoteFile = new SmbFile(url, auth);
//                remoteFile.connect(); //Try to connect
//
//                String line = "";
//                try (BufferedReader reader = new BufferedReader(new InputStreamReader(new SmbFileInputStream(remoteFile), config.getCharset()))) {
//                    while ((line = reader.readLine()) != null) {
//                        content = content.concat(line + "\n");
//                    }
//
//                    reader.close();
//                }
//                isSuccess = true;
//
//            } catch (Exception e) {
////             String msg = "The error occurred: " + e.getLocalizedMessage();
////             System.out.println(msg);
////             e.printStackTrace();
////                 log.error(finalOutput.getProcessed_IP() + ":" + pid + "- Error : ", e);
//                log.error(appendLog(""), e);
//                isSuccess = false;
//                attempt++;
////                 log.error(finalOutput.getProcessed_IP() + ":" + pid + "- SMB Read Connection Attempt : " + attempt);
//                log.error(appendLog("- SMB Read Connection Attempt : " + attempt), e);
//            }
//        } while (!isSuccess && attempt < 3);
//        if (isSuccess) {
////             log.info(Logtrace.getLogMessage("1", "SMB Connection", "SMB Read Conncection Sccessfull",
////                     finalOutput.getProcessed_IP(), finalOutput.getBatch_Configuartion_ID(), finalOutput.getBatch_Name(),
////                     input));
//
//            log.info(createLogJson1("SMB Connection", 1, "SMB Read Conncection Sccessfull"));
//
//        } else {
////             log.error(Logtrace.getLogMessage("0", "SMB Connection", "SMB Read Conncection Failure",
////                     finalOutput.getProcessed_IP(), finalOutput.getBatch_Configuartion_ID(), finalOutput.getBatch_Name(),
////                     input));
////             log.error(Logtrace.getLogMessage("0", "Process Execution", "Execution Failed - SMB Read Conncection Failure",
////                     finalOutput.getProcessed_IP(), finalOutput.getBatch_Configuartion_ID(), finalOutput.getBatch_Name(),
////                     input));
//
//            log.info(createLogJson1("SMB Connection", 0, "SMB Read Conncection Failure"));
////       	  List<Output> outputList = new ArrayList<Output>();
////       	  outputList.add(new Output( input.getInputId(),input.getOutputId(), input.getMobId(), input.getClientID(),input.getInputURL(),"","","","","","","","","", "Failure",  "Invalid SMB UserName/Password/Invalid File Path", startTime, sdf.format(new Date())));
//            lookUPKeyword(resource, "Invalid SMB UserName/Password/Invalid File Path");
//
//            result.setOutput(outputList);
//            String outputQueue = objectMapper.writeValueAsString(output);
////            ActiveMQConnection.sendMessageToQueue(assetProperties, outputQueue);
//            System.exit(1);
//        }
//        return content
        return "";
    }

    static {

        extractedURL = "";
//        exmsg = "";
        urlid = "";

//        Aboutus_Matched = "";
//        LinkLookup_matched = "";
//        created = "";
//        comment = "";
//        US_CityState = "";
//        down_on = "";
//        US_Zipcode = "";
//        tagg_sub = "";
//        content = "";
//        language = "";
//        status = 0;
//        oid = 0;
//        is_map = 0;
//        is_searched = 0;
//        count_check = 0;
//        output_totalcount = 0;
//        OutputTableCreation = false;
//        Exception_List = new ArrayList<String>();
//        toolstart = "";
//        toolend = "";
//        Gcalendar = new GregorianCalendar();
//        dateformate = new SimpleDateFormat("HH:mm:ss");
//        currdate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Gcalendar.getTime());
        date = new Date();
//        dateFormat = new SimpleDateFormat("dd-MM-yyyy");
//        machineIP = "";
//        Executionstatus = "";
//        ErrorDescription = "";
    }

    public static class Execute_Classification
            implements Runnable {

        int InputID = 0;
        String LoadingURL = "";
        String Page_source = "";
        int hashcode = 0;
        String Core_Content = "";
        String urlid = "";
        String sub = "";
        String domainurl = "";

        String pub_date = "";
        String created = "";
        String comment = "";
        String body = "";
        String down_on = "";
        String tagg_body = "";
        String tagg_sub = "";
        int oid = 0;
        int is_map = 0;
        int is_searched = 0;
        String language = "";

        String InputpageSource = "";
        String extractedURL = "";
        String pageSource = "";

        public Execute_Classification(Integer InputID, String urlid, String LoadingURL, String Domurl) {
            this.InputID = InputID;
            this.extractedURL = LoadingURL;
            this.urlid = urlid;
            this.domainurl = Domurl;
        }

        @Override
        public void run() {
            try {
                this.InputpageSource = "";
                try {
                    HtmlHeaderAttributes htmlHeaderAttributes = new HtmlHeaderAttributes();

                    if (config.isOnline()) {
                        htmlHeaderAttributes = HttpURLParser.getHttpURLContent(this.extractedURL);
//                        htmlHeaderAttributes = JsoupParser.SourceGetter(this.domainurl);
                        if (htmlHeaderAttributes == null || (htmlHeaderAttributes.getErrorDescription() == null || !htmlHeaderAttributes.getErrorDescription().equalsIgnoreCase("Read timed out"))) {
                            if (htmlHeaderAttributes.getResponseCode() != 200 || htmlHeaderAttributes.getPageSoure().isEmpty() || htmlHeaderAttributes.getPageSoure().length() < 50) {
                                try {
//                                    Thread.sleep(1000);
//                                    htmlHeaderAttributes = HttpURLParser.getHttpURLContent(this.domainurl);
                                    htmlHeaderAttributes = JsoupParser.SourceGetter(this.extractedURL);
                                } catch (SSLException sse) {
                                    htmlHeaderAttributes.setErrorDescription("SSl Exception from JSOUP");

                                    if (!this.extractedURL.toLowerCase().startsWith("https") && !this.extractedURL.toLowerCase().startsWith("http")) {

                                        if (this.extractedURL.toLowerCase().startsWith("www")) {
                                            this.extractedURL = "https://" + this.extractedURL;
                                        } else {
                                            this.extractedURL = "https://www." + this.extractedURL;
                                        }

                                    }

                                    htmlHeaderAttributes = HttpURLParser.SSl_fun_url(this.extractedURL);
                                }
                            }
                        }

                        this.InputpageSource = htmlHeaderAttributes.getPageSoure();

                        if ("".equals(this.InputpageSource)) {
                            log.info(createLogJson1("Process Execution", 0, "Page source empty"));
                            lookUPKeyword(this.InputID, this.extractedURL, this.urlid, this.domainurl, resource, "Page source empty","");
                            UpdateInputTable(this.urlid, this.extractedURL, 11);
                        }
                    }
                } catch (Exception s) {
                    log.error(appendLog("Error while getting Pagesource"), s);
                    log.info(createLogJson1("Process Execution", 0, "Execution Failed - while getting pagesource " + s.getMessage()));
//                            outputList.add(new Output(input.getInputId(),input.getOutputId(),input.getMobId(),input.getClientID(),input.getDomainURL(),"","","","","","","","","","","","","","","","","","","","","","","","","","","","","", "", "", "", "", "", "", "", "", "", "", "InputId is empty", "Failure", startTime, sdf.format(new Date())));
//                       
                    lookUPKeyword(this.InputID, this.extractedURL, this.urlid, this.domainurl, resource, "Exception while getting page source " + s.getMessage(),"");
                    UpdateInputTable(this.urlid, this.extractedURL, 12);
//                    result.setOutput(outputList);
//                         InsertDB();
//                 UpdateDB();
//                    System.exit(0);
                }

                this.pageSource = cleansing(this.InputpageSource);
//                this.pageSource = this.InputpageSource;
                lookUPKeyword(this.InputID, this.extractedURL, this.urlid, this.domainurl, resource, "Process Completed",this.pageSource);

//                result.setOutput(outputList);
//                String outputQueue = objectMapper.writeValueAsString(output);
//                  ActiveMQConnection.sendMessageToQueue( assetProperties, outputQueue); 
//                  l.info("{\"time\":\""+System.currentTimeMillis()+"\",\"status\":\"1\",\"site_id\":\"\",\"uid\":\"NA\",\"ref_id\":\"NA\",\"access_Token\":\"NA\",\"processed_IP\":\""+output.getProcessed_IP()+"\",\"stage\":\"Bot Process Status\",\"logMessage\":\"Process completed successfully\",\"platform_Response\":\"NA\"}");
                if (timeoutFlag) {
                    log.info(createLogJson1("Process Execution", 0, "Process Timeout"));
                    UpdateInputTable(this.urlid, this.extractedURL, 13);
                } else {
                    log.info(createLogJson1("Process Execution", 1, "Process Executed With Records - 1"));
                    UpdateInputTable(this.urlid, this.extractedURL, 16);
//                    DBConnectivity.UpdateOutputTable();
                }

            } catch (Exception e) {

            }
        }
    }

}
