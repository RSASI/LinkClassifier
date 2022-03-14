/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.valgen.lc.model;

import java.util.List;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 *
 * @author developer
 */
public class Logtracepojo {

    private String Stage;
    private String Ref_id;
    private String Site_id;
    private String Access_Token;
    private String Status;
    private String LogMessage;
    private String Platform_Response;
    private String Time;
    private String Processed_IP;
    private String BatchName;
    private String BatchId;
    private Long Uid;
    private Long pid;
    private String bot_name;

    public Input getInput_arguments() {
        return input_arguments;
    }

    public void setInput_arguments(Input input_arguments) {
        this.input_arguments = input_arguments;
    }
    private Input input_arguments;

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public String getBot_name() {
        return bot_name;
    }

    public void setBot_name(String bot_name) {
        this.bot_name = bot_name;
    }

   

    public String getBatchName() {
        return BatchName;
    }

    public void setBatchName(String BatchName) {
        this.BatchName = BatchName;
    }

    public String getBatchId() {
        return BatchId;
    }

    public void setBatchId(String BatchId) {
        this.BatchId = BatchId;
    }

    public Long getUid() {
        return Uid;
    }

    public void setUid(Long uid) {
        this.Uid = uid;
    }

    public String getProcessed_IP() {
        return Processed_IP;
    }

    public void setProcessed_IP(String processed_IP) {
        Processed_IP = processed_IP;
    }

    public String getStage() {
        return Stage;
    }

    public void setStage(String stage) {
        Stage = stage;
    }

    public String getRef_id() {
        return Ref_id;
    }

    public void setRef_id(String ref_id) {
        Ref_id = ref_id;
    }

    public String getSite_id() {
        return Site_id;
    }

    public void setSite_id(String site_id) {
        Site_id = site_id;
    }

    public String getAccess_Token() {
        return Access_Token;
    }

    public void setAccess_Token(String access_Token) {
        Access_Token = access_Token;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getLogMessage() {
        return LogMessage;
    }

    public void setLogMessage(String logMessage) {
        LogMessage = logMessage;
    }

    public String getPlatform_Response() {
        return Platform_Response;
    }

    public void setPlatform_Response(String platform_Response) {
        Platform_Response = platform_Response;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

}
