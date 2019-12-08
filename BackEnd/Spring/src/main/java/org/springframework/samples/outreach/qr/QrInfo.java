package org.springframework.samples.outreach.qr;

import java.time.LocalTime;

public interface QrInfo {
	 	String getEvent();
	    LocalTime getExpireTime();
	    int getQuantity();
	    int getPoints();
}
