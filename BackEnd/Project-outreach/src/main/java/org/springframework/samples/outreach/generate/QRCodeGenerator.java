package org.springframework.samples.outreach.generate;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Hex;
import org.json.JSONObject;

public class QRCodeGenerator {
    private static final String QR_CODE_IMAGE_PATH = "./MyQRCode.png";

    private static void generateQRCodeImage(String text, int width, int height, String filePath)
            throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        Path path = FileSystems.getDefault().getPath(filePath);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
    }

    public static void main(String[] args) {
        try {
            generateQRCodeImage("This is my first QR Code", 350, 350, QR_CODE_IMAGE_PATH);
        } catch (WriterException e) {
            System.out.println("Could not generate QR Code, WriterException :: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Could not generate QR Code, IOException :: " + e.getMessage());
        }
    }
    /* 
    This method takes the text to be encoded, the width and height of the QR Code, 
    and returns the QR Code in the form of a byte array.
    */
    public static byte[] getQRCodeImage(String text, int width, int height) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
        
        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        byte[] pngData = pngOutputStream.toByteArray(); 
        return pngData;
    }
    
//    public byte[] qrCodeGenerator(String id) throws IOException, 
//    WriterException, 
//    InvalidKeySpecException, 
//    NoSuchAlgorithmException {
//
//String filePath = "QRCode.png";
//String charset = "UTF-8";
//Map hintMap = new HashMap();
//hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
//
//Map<String, String> qrCodeDataMap = Map.of(
//"Name", id,
//"Key", keyProvider.generateVerificationKey(id) 
//// see next section for ´generateVerificationKey´ method
//);
//
//String jsonString = new JSONObject(qrCodeDataMap).toString();
//createQRCode(jsonString, filePath, charset, hintMap, 500, 500);
//
//BufferedImage image = ImageIO.read(new File(filePath));
//ByteArrayOutputStream baos = new ByteArrayOutputStream();
//ImageIO.write(image, "png", baos);
//byte[] imageData = baos.toByteArray();
//
//return imageData;
//}
//
//private void createQRCode(String qrCodeData, 
//String filePath, 
//String charset, 
//Map hintMap, 
//int qrCodeHeight, 
//int qrCodeWidth) throws WriterException, 
//      IOException {
//
//BitMatrix matrix = new MultiFormatWriter().encode(
//new String(qrCodeData.getBytes(charset), charset),
//BarcodeFormat.QR_CODE,
//qrCodeWidth,
//qrCodeHeight,
//hintMap
//);
//
//MatrixToImageWriter.writeToPath(
//matrix,
//filePath.substring(filePath.lastIndexOf('.') + 1),
//FileSystems.getDefault().getPath(filePath)
//);
//}
//
//public String generateVerificationKey(String str) throws NoSuchAlgorithmException,
//InvalidKeySpecException {
//int iterations = 10000;
//int keyLength = 512;
//char[] strChars = str.toCharArray();
//byte[] saltBytes = salt;
//SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
//PBEKeySpec spec = new PBEKeySpec(strChars, saltBytes, iterations, keyLength);
//SecretKey key = skf.generateSecret( spec );
//byte[] hashedBytes = key.getEncoded( );
//return Hex.encodeHexString(hashedBytes);
//}
}