package com.doms.automation.populatemaps;

import java.util.LinkedHashMap;
import java.util.Map;

import com.doms.automation.runprocessstep.RunProcessStep;
import com.doms.automation.runprocessstep.impl.ObjectFactory;

public class PopulateRunProcessStepMap {
	public Map<String, RunProcessStep> getRunProcessStepMap() {

		ObjectFactory objectFactory = new ObjectFactory();
		Map<String, RunProcessStep> runProcessStepMap = new LinkedHashMap<String, RunProcessStep>();
		
		runProcessStepMap.put("Auth", objectFactory.createAuth());
		runProcessStepMap.put("CreateOrder", objectFactory.createCreateOrder());
		runProcessStepMap.put("AuthorizeOrder", objectFactory.createAuthorizeOrder());
		
		return runProcessStepMap;
	}
}
