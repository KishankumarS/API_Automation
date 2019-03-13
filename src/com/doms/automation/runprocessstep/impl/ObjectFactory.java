package com.doms.automation.runprocessstep.impl;

public class ObjectFactory {

	public Auth createAuth() {
		return new Auth();
	}

	public CreateOrder createCreateOrder() {
		return new CreateOrder();
	}

	public AuthorizeOrder createAuthorizeOrder() {
		return new AuthorizeOrder();
	}

}
