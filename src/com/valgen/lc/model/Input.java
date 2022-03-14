/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.valgen.lc.model;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 *
 * @author developer
 */
public class Input {
@JsonProperty("MobId")
    private String mobId;
@JsonProperty("InputId")
   private String inputId;
@JsonProperty("ClientId")
   private String clientID;
@JsonProperty("OutputId")
   private String outputId;

   @JsonProperty("InputS3FilePath")
   private String inputS3FilePath;
   @JsonProperty("InputURL")
   private String inputURL;
   @JsonProperty("LinkText")
   private String LinkText;
   @JsonProperty("DomainURL")
   private String DomainURL;

    public String getDomainURL() {
        return DomainURL;
    }

    public void setDomainURL(String DomainURL) {
        this.DomainURL = DomainURL;
    }

    public String getLinkText() {
        return LinkText;
    }

    public void setLinkText(String LinkText) {
        this.LinkText = LinkText;
    }
   

    public String getInputURL() {
        return inputURL;
    }

    public void setInputURL(String inputURL) {
        this.inputURL = inputURL;
    }
   


    public Input(String mobId, String inputId, String clientID, String outputId, String inputS3FilePath) {
        this.mobId = mobId;
        this.inputId = inputId;
        this.clientID = clientID;
        this.outputId = outputId;
        this.inputS3FilePath = inputS3FilePath;
    }

    public String getInputId() {
        return inputId;
    }

    public void setInputId(String inputId) {
        this.inputId = inputId;
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public String getOutputId() {
        return outputId;
    }

    public void setOutputId(String outputId) {
        this.outputId = outputId;
    }

   
   


   

    

   
	public String getInputS3FilePath() {
		return inputS3FilePath;
	}

	public void setInputS3FilePath(String inputS3FilePath) {
		this.inputS3FilePath = inputS3FilePath;
	}

	public Input() {

    }

   

   
    public String getMobId() {
        return mobId;
    }

    public void setMobId(String mobId) {
        this.mobId = mobId;
    }


   

}
