/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.valgen.lc.model;

/**
 *
 * @author Developer
 */
public class HtmlHeaderAttributes {

    private int responseCode = 0;
    private String responseMessage = "";
    private String pageSoure = "";
    private String parserType = "";
    private String domainUrl = "";
    private String landingUrl = "";
    private String errorDescription = "";
    private String contentType = "";
    private int errorCode = 0;
    private String Isredirected = "";
//    private int pdf_download_status = 0;
    private int process_status = 0;
//    private String pdf_lastmodifieddate = "";
//    private String pdf_content_length = "";
    private String contentDisposition = "";
    private String contentLength = "";
    private String modifiedURL = "";
    private String redirectedFrom = "";
//    private boolean is_newpdf = false;

    

    public HtmlHeaderAttributes() {
    }

    public HtmlHeaderAttributes(int responseCode, String responseMessage, String pageSoure, String parserType, String domainUrl, String landingUrl, String errorDescription, String contentType, int errorCode) {
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
        this.pageSoure = pageSoure;
        this.parserType = parserType;
        this.domainUrl = domainUrl;
        this.landingUrl = landingUrl;
        this.errorDescription = errorDescription;
        this.contentType = contentType;
        this.errorCode = errorCode;
    }

    public String getModifiedURL() {
        return modifiedURL;
    }

    public void setModifiedURL(String modifiedURL) {
        this.modifiedURL = modifiedURL;
    }

    public String getRedirectedFrom() {
        return redirectedFrom;
    }

    public void setRedirectedFrom(String redirectedFrom) {
        this.redirectedFrom = redirectedFrom;
    }

    
    
    
    
    
    public String getContentLength() {
        return contentLength;
    }

    public void setContentLength(String contentLength) {
        this.contentLength = contentLength;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public String getPageSoure() {
        return pageSoure;
    }

    public void setPageSoure(String pageSoure) {
        this.pageSoure = pageSoure;
    }

    public String getParserType() {
        return parserType;
    }

    public void setParserType(String parserType) {
        this.parserType = parserType;
    }

    public String getDomainUrl() {
        return domainUrl;
    }

    public void setDomainUrl(String domainUrl) {
        this.domainUrl = domainUrl;
    }

    public String getLandingUrl() {
        return landingUrl;
    }

    public void setLandingUrl(String landingUrl) {
        this.landingUrl = landingUrl;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getIsredirected() {
        return Isredirected;
    }

    public void setIsredirected(String Isredirected) {
        this.Isredirected = Isredirected;
    }

    
    public String getContentDisposition() {
        return contentDisposition;
    }

    public void setContentDisposition(String contentDisposition) {
        this.contentDisposition = contentDisposition;
    }

    public int getProcess_status() {
        return process_status;
    }

    public void setProcess_status(int process_status) {
        this.process_status = process_status;
    }

}
