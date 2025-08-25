package com.csc.demo.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.csc.cscesign.Enums;
import com.csc.cscesign.InitateRequest;
import com.csc.cscesign.eSignCreater;
import com.csc.cscesign.eSignInput;
import com.csc.cscesign.eSignInputBuilder;
import com.csc.demo.service.TokenValidator;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.codec.Base64;

@Controller
public class ValidatorGatewayController {

    @Autowired
    private TokenValidator tokenValidator;
    
    @Value("${PFX.path}")
    private String pfxPath;
    
    
    @Value("${Temp.path}")
    private String tempPath;
    

    @PostMapping("/validate-and-sign")
    public String handleEsignRequest(
            @RequestParam("token") String token,
            @RequestParam("name") String name,
            @RequestParam("pdfFile") MultipartFile pdfFile,
            Model model
    ) throws DocumentException {
        if (!tokenValidator.validate(token)) {
            model.addAttribute("error", "Invalid or expired token.");
            return "error";
        }

        try {
            byte[] pdfBytes = pdfFile.getBytes(); // ← this replaces the hardcoded file reading

            String pdfBase64_1 = Base64.encodeBytes(pdfBytes);
            eSignInput bulider1 = eSignInputBuilder.init()
                    .setDocBase64(pdfBase64_1)
                    .setAppearanceType(Enums.AppearanceType.StandardSignature)
                    .setDocInfo("test")
                    .setDocURL("https://")
                    .setLocation("test")
                    .setReason("test")
                    .setSignedBy(name)
                    .setCoSign(true)
                    .setPageTobeSigned(Enums.PageTobeSigned.PageLevel)
                                    .setPageLevelCoordinates("All-55,78,174,19")
                    .setCoordinates(Enums.Coordinates.BottomRight)
                    .setTickRequired(true)
                    .setBorderRequired(true)
                    .build();
             // Alias Name for emusig.pfx == class 3 individual test
            // Alias Name for Prod PFX == te-8acc3dc1-df23-468d-9855-4f63044d4df5
            //Alias Name for Ladakh PFX == te-1ab63a05-7e03-4bb8-9035-d33259b7f8db
            //Ladakh ASP ID == LADAKH E- GOVERNANCE AGENCY Pass : 123456 -- BC Agree ASP ID == CSCASP Pass : 654321
       eSignCreater esign = new eSignCreater("CSC", pfxPath, "1", "ds emudhra test 13");
     //eSignCreater esign = new eSignCreater("JIT", "D:\\CSC\\CSC-ESign\\Test-Class3Individual2022\\jharkhandesign.pfx", "emudhra", "class 3 organization test");
      // eSignCreater esign = new eSignCreater("CSCASP", "D:\\CSC\\CSC-ESign\\Test-Class3Individual2022\\DS_CSC.pfx", "654321", "te-8acc3dc1-df23-468d-9855-4f63044d4df5");
       //eSignCreater esign = new eSignCreater("CSC", "D:\\CSC\\CSC-ESign\\Test-Class3Individual2022\\emusig2.pfx", "1", "ds emudhra test 13");

    ArrayList<eSignInput> inputs = new ArrayList<>();
    inputs.add(bulider1);
    //InitateRequest serviceReturn = esign.getDataSign(inputs, "", "", "https://cscdemo.emudhra.net/esignResponse","https://cscdemo.emudhra.net/esignResponse", "F:\\CSC\\CSC-ESign\\SignedPDF\\temp", Enums.eSignAPIVersion.V2, Enums.AuthMode.OTP);
    //InitateRequest serviceReturn = esign.getDataSign(inputs, "", "", "http://localhost:8080/esignResponse","http://localhost:8080/esignResponse", "D:\\CSC\\CSC-ESign\\SignedPDF\\temp", Enums.eSignAPIVersion.V2, Enums.AuthMode.OTP);
    InitateRequest serviceReturn = esign.getDataSign(inputs, "", "", "http://localhost:8080/esignResponse1","http://localhost:8080/esignResponse1", tempPath, Enums.eSignAPIVersion.V2, Enums.AuthMode.OTP);

    String preSignedPdf = serviceReturn.getPreSignedPdf();
    System.out.println("Presigned PDF :: "+preSignedPdf);

    //session.setAttribute("preSignedPdf", preSignedPdf);
    String reqXml = serviceReturn.getRequestXml();

            model.addAttribute("signedXml", reqXml);
            model.addAttribute("name", name);
            return "forward-to-esign";

        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("error", "Error reading PDF file.");
            return "error";
        }
    }
    
    
    
    

    
    
   @PostMapping("/validate-pan-esign")
   	public String panEsignRequest(@RequestParam("token") String token, @RequestParam("name") String name,
   			@RequestParam("pdfFile") MultipartFile[] pdfFile, Model model) throws DocumentException {
   		// Validate token first
   		if (!tokenValidator.validate(token)) {
   			model.addAttribute("error", "Invalid or expired token.");
   			return "error";
   		}
    
   		// Initialize the eSignCreater and list of eSignInput objects
   		eSignCreater esign = new eSignCreater("CSC", pfxPath, "1", "ds emudhra test 13");
   		ArrayList<eSignInput> inputs = new ArrayList<>();
    
   		// Loop through the list of uploaded PDFs
   		for (MultipartFile file : pdfFile) {
   			if (file.isEmpty())
   				continue;
   			try {
   				// Convert the current PDF file to byte array
   				byte[] pdfBytes = file.getBytes();
    
   				// Encode the byte array to Base64
   				String pdfBase64_1 = Base64.encodeBytes(pdfBytes);
    
   				// Initialize the eSignInput builder for each PDF file
   				eSignInput builder1 = eSignInputBuilder.init().setDocBase64(pdfBase64_1)
   						.setAppearanceType(Enums.AppearanceType.StandardSignature).setDocInfo("Document Info for")
   						.setDocURL("https://").setLocation("test").setReason("test").setSignedBy(name)
   						.setCoSign(true).setPageTobeSigned(Enums.PageTobeSigned.PageLevel)
   						.setPageLevelCoordinates("All-55,78,174,19").setCoordinates(Enums.Coordinates.BottomRight)
   						.setTickRequired(true).setBorderRequired(true).build();
    
   				// Add the built eSignInput object to the list
   				inputs.add(builder1);
    
   			} catch (IOException e) {
   				e.printStackTrace();
   				model.addAttribute("error", "Error reading PDF file: " + file.getOriginalFilename());
   				return "error";
   			}
   		}
    
   		// Now call the eSign API to get the signed PDF (or proceed with other steps)
   		try {
   			InitateRequest serviceReturn = esign.getDataSign(inputs, "9128387236", "",
   					"http://localhost:8080/esignResponsePan", "http://localhost:8080/esignResponsePan",
   					tempPath, Enums.eSignAPIVersion.V3, Enums.AuthMode.OTP);
    
   			String preSignedPdf = serviceReturn.getPreSignedPdf();
   			System.out.println("Presigned PDF: " + preSignedPdf);
    
   			String reqXml = serviceReturn.getRequestXml();
   			System.out.println("Request XML1: " + reqXml);
    
   			model.addAttribute("signedXml", reqXml);
   			model.addAttribute("name", name);
    
   			return "forward-pan-esign";
   		} catch (Exception e) {
   			e.printStackTrace();
   			model.addAttribute("error", "Error processing eSign request.");
   			return "error";
   		}
   	}


}
