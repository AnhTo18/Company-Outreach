package org.springframework.samples.outreach.qr;

import org.springframework.samples.outreach.qr.QRcode;


/**
 * iterates through products
 * @author creimers
 * @author kschrock
 */
public interface QRService {

	public Iterable<QRcode> findAll();

}
