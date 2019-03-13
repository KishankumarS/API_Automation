package com.doms.automation.bean;

import java.util.Map;

public class TestCaseResultVO {
    @SuppressWarnings("unchecked")
	private Map result ;
    
    
    @SuppressWarnings("unchecked")
	public TestCaseResultVO(Map result){
        this.result = result;
    }
   

	@SuppressWarnings("unchecked")
	public Map getResult() {
		return result;
	}

	@SuppressWarnings("unchecked")
	public void setResult(Map result) {
		this.result = result;
	}
    
}
