/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.valgen.lc.model;
import java.util.*;
import org.codehaus.jackson.annotate.JsonProperty;
import org.json.simple.JSONArray;

/**
 *
 * @author developer
 */
public class Result {

    
    @JsonProperty("Output")
    private JSONArray output;
//@JsonProperty("Output")
//    private List<Output> output;
@JsonProperty("Input")
    Input input;

   

    public JSONArray getOutput() {
	return output;
}

public void setOutput(JSONArray output) {
	this.output = output;
}

	public Input getInput() {
        return input;
    }

    public void setInput(Input input) {
        this.input = input;
    }
    
   
}
