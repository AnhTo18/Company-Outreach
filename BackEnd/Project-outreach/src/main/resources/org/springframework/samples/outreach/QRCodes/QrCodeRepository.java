package org.springframework.samples.outreach.QRCodes;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import org.springframework.samples.outreach.entities.Qr;

//import com.demo.entities.Product;
//import org.springframework.samples.outreach.entities.Qr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("qrRepository")
public interface QrCodeRepository extends JpaRepository<QrCode, String>{

}
