package com.google.blockly.pojo;

import java.util.List;

public class jsonPojo {


    private String type;
    private String message0;
    private String arg0;
    private String previousStatement;
    private String nextStatement;
    private Integer colour;
    private String tooltip;

    public void setType(String type) {
        this.type = type;
    }

    public void setMessage0(String message0) {
        this.message0 = message0;
    }

    public void setArg(String arg0) {
        this.arg0 = arg0;
    }

    public void setPreviousStatement(String previousStatement) {
        this.previousStatement = previousStatement;
    }

    public void setNextStatement(String nextStatement) {
        this.nextStatement = nextStatement;
    }

    public void setColour(Integer colour) {
        this.colour = colour;
    }

    public void setTooltip(String tooltip) {
        this.tooltip = tooltip;
    }

    public String getType() {
        return type;
    }

    public String getMessage0() {
        return message0;
    }

    public String getArg0() {
        return arg0;
    }

    public String getPreviousStatement() {
        return previousStatement;
    }

    public String getNextStatement() {
        return nextStatement;
    }

    public Integer getColour() {
        return colour;
    }

    public String getTooltip() {
        return tooltip;
    }
}
