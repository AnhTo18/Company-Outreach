package org.springframework.samples.outreach.services;

import org.springframework.samples.outreach.entities.Qr;

public interface QrService {

	public Iterable<Qr> findAll();

}