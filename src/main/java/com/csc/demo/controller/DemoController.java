package com.csc.demo.controller;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.csc.cscesign.CscReturnDocument;
import com.csc.cscesign.InitateRequest;
import com.csc.cscesign.eSignCreater;
import com.csc.cscesign.eSignInput;
import com.csc.cscesign.eSignInputBuilder;

import jakarta.servlet.http.HttpSession;

@Controller
public class DemoController {
	private static Logger logger = LogManager.getLogger(SpringBootServletInitializer.class);

	
	@Value("${PFX.path}")
    private String pfxPath;
	
	@Value("${Signed.Pdf.path}")
    private String signedpdfPath;
    
    
    @Value("${Temp.path}")
    private String tempPath;
	   
	//   @CrossOrigin(origins = "http://localhost:3000") 
	   @RequestMapping("/")
	   @GetMapping
	    public String upload() {
	      return "upload";
	    }
	   
	   @RequestMapping("/uploadDocPAN")
	    public String uploadDocPAN() {
	      return "uploadDocPAN";
	    }

	   
	  
	   
	   @PostMapping("/esignResponsePan")
		@ResponseBody
		public ResponseEntity<String> esignResponsePan(@RequestBody String xmlResponse, HttpSession session)
				throws FileNotFoundException, Exception {
	 
			// Print the XML response (for debugging)
			System.out.println("Received XML Callback: " + xmlResponse);
	 
			String response = xmlResponse;
			logger.debug("Post process respose from Gateway ::: " + response);
			System.out.println("Received XML Callback: " + response);
			Map<String, String> parsedResponse = parseResponse(response);
			String base64EncodedXml = parsedResponse.get("xml");
			String txn = parsedResponse.get("txn");
			String doccount = parsedResponse.get("doccount");
			System.out.println();
	 
			Path path = Paths.get(tempPath + "\\" + txn + ".sig");
	 
			String preSignedPdf = new String(Files.readAllBytes(path));
	 
			System.out.println("Post process respose for preSignedPdf" + preSignedPdf);
			System.out.println("Post process respose for txn" + txn);
			System.out.println("Post process respose for doccount" + doccount);
	 
			String xml = URLDecoder.decode(base64EncodedXml, StandardCharsets.UTF_8.name());
	 
			System.out.println("Post process respose for xml" + xml);
	 
			byte[] base64 = Base64.decode(xml);
			xml = new String(base64);
			eSignCreater esign = new eSignCreater("CSC", pfxPath, "1", "ds emudhra test 13");
			eSignInput bulider = eSignInputBuilder.init().setPkcs7Xml(xml).setPreSignedPdf(preSignedPdf).build();
			InitateRequest serviceReturn = esign.append(bulider);
			//String pdfPath = signedpdfPath + File.separator + txn + ".pdf";
	 
			List<String> savedFiles = new ArrayList<>();
	 
			if ("1".equals(serviceReturn.getStatus())) {
				List<CscReturnDocument> returnDocuments = serviceReturn.getReturnDocuments();
	 
				int index = 1;
				for (CscReturnDocument returnDocument : returnDocuments) {
					String pdfBase64 = returnDocument.getSignedDocument();
					byte[] signedBytes = Base64.decode(pdfBase64);
	 
					String fileName = txn + "_" + index + ".pdf"; // Append index to distinguish files
					Path signedPdfPath = Paths.get(signedpdfPath, fileName);
	 
					try (FileOutputStream fos = new FileOutputStream(signedPdfPath.toFile())) {
						fos.write(signedBytes);
						savedFiles.add(signedPdfPath.toString());
					} catch (IOException e) {
						logger.error("Error writing signed PDF to file: {}", signedPdfPath, e);
						return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
								.body("Error saving signed documents.");
					}
	 
					index++;
				}
			}
	 
			return ResponseEntity.ok("PDF Signed successfully");
	 
		}
	 
	   
	   
	   @PostMapping("/esignResponse1")
	   @ResponseBody
	   public ResponseEntity<String> handleCallback(@RequestBody String xmlResponse) throws FileNotFoundException, Exception {
	       // Print the XML response (for debugging)
	       System.out.println("Received XML Callback: " + xmlResponse);
	       
	       String response = xmlResponse;
	       
	       //logger.debug("Post process respose from Gateway ::: "+response);
	       System.out.println("Received XML Callback: " + response);
	       Map<String, String> parsedResponse = parseResponse(response);
	       String base64EncodedXml = parsedResponse.get("xml");
	        String txn = parsedResponse.get("txn");
	       String doccount = parsedResponse.get("doccount");
	       
	      // String preSignedPdf = (String) session.getAttribute("preSignedPdf");
	       
	      // Path path = Paths.get("F:\\CSC\\CSC-ESign\\SignedPDF\\temp\\"+txn+".sig");      
	       Path path = Paths.get(tempPath + "\\" + txn + ".sig");

			String preSignedPdf = new String(Files.readAllBytes(path));
			
			
	      
	       System.out.println("Post process respose for preSignedPdf"+preSignedPdf);
	       System.out.println("Post process respose for txn"+txn);
	       System.out.println("Post process respose for doccount"+doccount);
	       
	       String xml = URLDecoder.decode(base64EncodedXml, StandardCharsets.UTF_8.name());
	       
	       
	       System.out.println("Post process respose for xml"+xml);
	       
	       
	       byte[] base64 = Base64.decode(xml);
	       xml = new String(base64);
	       eSignCreater esign = new eSignCreater("CSC", pfxPath, "1", "ds emudhra test 13");
	  //   eSignCreater esign = new eSignCreater("JIT", "D:\\CSC\\CSC-ESign\\Test-Class3Individual2022\\jharkhandesign.pfx", "emudhra", "class 3 organization test");
	   //    eSignCreater esign = new eSignCreater("CSCASP", "D:\\CSC\\CSC-ESign\\Test-Class3Individual2022\\DS_CSC.pfx", "654321", "te-8acc3dc1-df23-468d-9855-4f63044d4df5");

   
	       eSignInput bulider = eSignInputBuilder.init()
	               .setPkcs7Xml(xml)
	               .setPreSignedPdf(preSignedPdf)
	               .build();
	       InitateRequest serviceReturn = esign.append(bulider);
	   //   String pdfPath = "F:\\CSC\\CSC-ESign\\SignedPDF" + File.separator + txn  + ".pdf";
	        String pdfPath = signedpdfPath + File.separator + txn  + ".pdf";
	       
	       if (serviceReturn.getStatus() == "1") {
	           ArrayList<CscReturnDocument> returnDocuments = serviceReturn.getReturnDocuments();
	           int i = 0;
	           for (CscReturnDocument returnDocument : returnDocuments) {
	               String pdfBase64 = returnDocument.getSignedDocument();
	               byte[] signedBytes = Base64.decode(pdfBase64);
	               try (FileOutputStream fos = new FileOutputStream(pdfPath)) {
	                   fos.write(signedBytes);
	               }
	               i++;
	           }
	       }
	       
	       
	       return  ResponseEntity.ok("PDF Signed successfully");
	       

}
	   
	   private static Map<String, String> parseResponse(String response) {
	        Map<String, String> map = new HashMap<>();
	        StringTokenizer tokenizer = new StringTokenizer(response, "&");
	        while (tokenizer.hasMoreTokens()) {
	            String pair = tokenizer.nextToken();
	            String[] keyValue = pair.split("=");
	            if (keyValue.length == 2) {
	                map.put(keyValue[0], keyValue[1]);
	            }
	        }
	        return map;
	    }
	    

}
