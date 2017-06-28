package com.karco.mq;

import org.springframework.util.ErrorHandler;

public class CustomErrorHandler implements ErrorHandler {

	@Override
	public void handleError(Throwable t) {
		t.printStackTrace();
	}

}
