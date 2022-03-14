package com.valgen.lc.model;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class Configuration {
        @JsonProperty("EndID")
        private int EndID;

    @JsonProperty("Username")
        private String Username;
    @JsonProperty("Password")
        private String Password;
    @JsonProperty("Database")
        private String Database;
    @JsonProperty("InputTable")
        private String InputTable;
    @JsonProperty("OutputTable")
        private String OutputTable;
    @JsonProperty("Deadlockpriority")
        private int Deadlockpriority;
    @JsonProperty("StartID")
        private int StartID;
    @JsonProperty("Threadpool")
        private int Threadpool;
    @JsonProperty("AdditionalQuery")
        private String AdditionalQuery;

    public String getAdditionalQuery() {
        return AdditionalQuery;
    }

    public void setAdditionalQuery(String AdditionalQuery) {
        this.AdditionalQuery = AdditionalQuery;
    }

    public int getThreadpool() {
        return Threadpool;
    }

    public void setThreadpool(int Threadpool) {
        this.Threadpool = Threadpool;
    }

    
    
    
    
	@JsonProperty("accessKey")
	private String accessKey;
	@JsonProperty("secretKey")
	private String secretKey;
	@JsonProperty("bucketName")
	private String bucketName;
	@JsonProperty("Charset")
	private String Charset;
	@JsonProperty("TimeoutForProcessingInMinutes")
	private int TimeoutForProcessingInMinutes;
	@JsonProperty("TimeRelayInSeconds")
	private int TimeRelayInSeconds;
	
	private boolean FileServerStorage;
    private String FileServerIP;
    private String FileServerUsername;
    private String FileServerPassword;
    private String region;
    
    private boolean PersistentMode;
	private String UserAgent;
	private int RedirectCount;
	private int ConnectionTimeoutInSec;
	private String metaURLRegex;
	private boolean Online;
	
        @JsonProperty("IPAddress")
        private String IPAddress;

    public String getIPAddress() {
        return IPAddress;
    }

    public void setIPAddress(String IPAddress) {
        this.IPAddress = IPAddress;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String Username) {
        this.Username = Username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getDatabase() {
        return Database;
    }

    public void setDatabase(String Database) {
        this.Database = Database;
    }

    public String getInputTable() {
        return InputTable;
    }

    public void setInputTable(String InputTable) {
        this.InputTable = InputTable;
    }

    public String getOutputTable() {
        return OutputTable;
    }

    public void setOutputTable(String OutputTable) {
        this.OutputTable = OutputTable;
    }

    public int getDeadlockpriority() {
        return Deadlockpriority;
    }

    public void setDeadlockpriority(int Deadlockpriority) {
        this.Deadlockpriority = Deadlockpriority;
    }
    
    public int getStartID() {
        return StartID;
    }

    public void setStartID(int StartID) {
        this.StartID = StartID;
    }

    public int getEndID() {
        return EndID;
    }

    public void setEndID(int EndID) {
        this.EndID = EndID;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
	public int getConnectionTimeoutInSec() {
		return ConnectionTimeoutInSec;
	}
	public void setConnectionTimeoutInSec(int connectionTimeoutInSec) {
		ConnectionTimeoutInSec = connectionTimeoutInSec;
	}
	public String getUserAgent() {
		return UserAgent;
	}
	public void setUserAgent(String userAgent) {
		UserAgent = userAgent;
	}
	public int getRedirectCount() {
		return RedirectCount;
	}
	public void setRedirectCount(int redirectCount) {
		RedirectCount = redirectCount;
	}
	public String getMetaURLRegex() {
		return metaURLRegex;
	}
	public void setMetaURLRegex(String metaURLRegex) {
		this.metaURLRegex = metaURLRegex;
	}
	public boolean isOnline() {
		return Online;
	}
	public void setOnline(boolean online) {
		Online = online;
	}
	public boolean isFileServerStorage() {
		return FileServerStorage;
	}
	public void setFileServerStorage(boolean fileServerStorage) {
		FileServerStorage = fileServerStorage;
	}
	public String getFileServerIP() {
		return FileServerIP;
	}
	public void setFileServerIP(String fileServerIP) {
		FileServerIP = fileServerIP;
	}
	public String getFileServerUsername() {
		return FileServerUsername;
	}
	public void setFileServerUsername(String fileServerUsername) {
		FileServerUsername = fileServerUsername;
	}
	public String getFileServerPassword() {
		return FileServerPassword;
	}
	public void setFileServerPassword(String fileServerPassword) {
		FileServerPassword = fileServerPassword;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public boolean isPersistentMode() {
		return PersistentMode;
	}
	public void setPersistentMode(boolean persistentMode) {
		PersistentMode = persistentMode;
	}
	public int getTimeoutForProcessingInMinutes() {
		return TimeoutForProcessingInMinutes;
	}
	public void setTimeoutForProcessingInMinutes(int timeoutForProcessingInMinutes) {
		TimeoutForProcessingInMinutes = timeoutForProcessingInMinutes;
	}
	public int getTimeRelayInSeconds() {
		return TimeRelayInSeconds;
	}
	public void setTimeRelayInSeconds(int timeRelayInSeconds) {
		TimeRelayInSeconds = timeRelayInSeconds;
	}
	public String getAccessKey() {
		return accessKey;
	}
	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}
	public String getSecretKey() {
		return secretKey;
	}
	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}
	public String getBucketName() {
		return bucketName;
	}
	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}
	public String getCharset() {
		return Charset;
	}
	public void setCharset(String charset) {
		Charset = charset;
	}
	
	
	
	
}
