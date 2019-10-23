package org.springframework.samples.outreach.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.outreach.entities.Qr;
import org.springframework.stereotype.Service;

import org.springframework.samples.outreach.QRCodes.*;

@Service("Qrservice")
public class QrServiceImpl implements QrService {

	@Autowired
	private QrCodeRepository qrRepository;

	@Override
	public Iterable<Qr> findAll() {
		return qrRepository.findAll();
	}

}