package com.doms.automation.bean;

import java.util.ArrayList;

public class WarningQueryVO {
ArrayList<String> inputParameters;
String query;
String dataBase;
public ArrayList<String> getInputParameters() {
	return inputParameters;
}
public void setInputParameters(ArrayList<String> inputParameters) {
	this.inputParameters = inputParameters;
}
public String getQuery() {
	return query;
}
public void setQuery(String query) {
	this.query = query;
}
public String getDataBase() {
	return dataBase;
}
public void setDataBase(String dataBase) {
	this.dataBase = dataBase;
}


}
