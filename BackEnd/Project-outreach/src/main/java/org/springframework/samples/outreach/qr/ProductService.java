package org.springframework.samples.outreach.qr;

import org.springframework.samples.outreach.qr.Product;

public interface ProductService {

	public Iterable<Product> findAll();

}