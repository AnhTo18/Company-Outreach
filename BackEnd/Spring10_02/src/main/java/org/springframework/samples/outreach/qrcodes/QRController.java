package org.springframework.samples.outreach.qrcodes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.outreach.qrcodes.QRController;
import org.springframework.samples.outreach.owner.Owners;
import org.springframework.samples.outreach.qrcodes.QRCodes;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.security.acl.Owner;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.imageio.ImageIO;
import javax.validation.Valid;

@RestController
class QRController {
	
	@Autowired
	    QRRepository qrRepository;
	    
	  

	  private final Logger logger= LoggerFactory.getLogger(QRController.class);

	    /*to save an users*/
//	    @PostMapping( path = "/owners/new")
//	    public String saveOwner(Owners owner) {
//	        ownersRepository.save(owner);
//	        return "New Owner "+ owner.getFirstName() + " Saved";
//	    }


	  
	  @RequestMapping(value= "/qrcodes/add", method= RequestMethod.POST)
		public String createCode(@RequestBody QRCodes newcode) {
			System.out.println(this.getClass().getSimpleName() + " - Create new employee method is invoked.");
			 qrRepository.save(newcode);
			 return "New Code " + newcode.getRandom() + " Saved";
		}
	 
	  @RequestMapping(value = "/qrcodes/generate", method= RequestMethod.POST) 

				public String main(String[] args) {
					String myCodeText = "https:coms-309-ss-8.misc.iastate.edu/";
					String filePath = "/home/public/QR.png";
					int size = 250;
					String fileType = "png";
					File myFile = new File(filePath);
					try {
						
						Map<EncodeHintType, Object> hintMap = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
						hintMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");
						
						// Now with zxing version 3.2.1 you could change border size (white border size to just 1)
						hintMap.put(EncodeHintType.MARGIN, 1); /* default = 4 */
						hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
			 
						QRCodeWriter qrCodeWriter = new QRCodeWriter();
						BitMatrix byteMatrix = qrCodeWriter.encode(myCodeText, BarcodeFormat.QR_CODE, size,
								size, hintMap);
						int CrunchifyWidth = byteMatrix.getWidth();
						BufferedImage image = new BufferedImage(CrunchifyWidth, CrunchifyWidth,
								BufferedImage.TYPE_INT_RGB);
						image.createGraphics();
			 
						Graphics2D graphics = (Graphics2D) image.getGraphics();
						graphics.setColor(Color.WHITE);
						graphics.fillRect(0, 0, CrunchifyWidth, CrunchifyWidth);
						graphics.setColor(Color.BLACK);
			 
						for (int i = 0; i < CrunchifyWidth; i++) {
							for (int j = 0; j < CrunchifyWidth; j++) {
								if (byteMatrix.get(i, j)) {
									graphics.fillRect(i, j, 1, 1);
								}
							}
						}
						ImageIO.write(image, fileType, myFile);
					} catch (WriterException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					return "\n\nYou have successfully created QR Code.";
				}
		  
	  /*get all codes, just for testing. Make sure it is only privately available later*/
	    @RequestMapping(method = RequestMethod.GET, path = "/qrcodes")
	    public List<QRCodes> getAllCodes() {
	        logger.info("Entered into Controller Layer");
	        List<QRCodes> results = qrRepository.findAll();
	        logger.info("Number of Records Fetched:" + results.size());
	        return results;
	    }
	    
	    //use query method to match
	    
}
