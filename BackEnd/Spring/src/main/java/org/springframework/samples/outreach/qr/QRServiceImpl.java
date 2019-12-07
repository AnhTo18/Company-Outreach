package org.springframework.samples.outreach.qr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.samples.outreach.qr.QRcode;
import org.springframework.samples.outreach.qr.QRRepository;

/**
 * implements the findall function for productservice
 * @author creimers
 * @author kschrock
 */
@Service("productService")
public class QRServiceImpl implements QRService {

	@Autowired
	private QRRepository productRepository;

	@Override
	public Iterable<QRcode> findAll() {
		return productRepository.findAll();
	}

}
