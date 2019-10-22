package org.springframework.samples.outreach.qr;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.samples.outreach.owner.Owners;
import org.springframework.samples.outreach.qr.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
}