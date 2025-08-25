<!DOCTYPE html>
<html>
<head>
    <title>eSign Consent</title>
    <script>
        function handleConsentChange(checkbox) {
            if (checkbox.checked) {
                document.getElementById("esignForm").submit();
            }
        }
    </script>
    
     <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            background-color: #fff;
        }
        .header {
            padding: 10px;
            border-bottom: 3px solid #3e73c5;
            display: flex;
            align-items: center;
        }
        .header img {
            height: 50px;
            margin-left: 10px;
        }
        .header-title {
            margin-left: 15px;
            font-size: 20px;
            font-weight: bold;
        }
        .content {
            margin: 40px auto;
            padding: 90px;
            width: 80%;
            hight:90%;
            max-width: 800px;
            border: 1px solid #ccc;
            border-radius: 10px;
             height: 200px;
        }
        .consent-text {
            font-size: 16px;
        }
        .footer {
            text-align: center;
            font-size: 12px;
            color: #666;
            margin-top: 60px;
       
        }
    </style>
</head>
<body>
 <div class="header">
        
        <img src="webapp/assets/images/mapitbhopalogo.png" />
        <div class="header-title">eSign Gateway</div>
    </div>
   <!--  <h2>eSign Consent Form</h2> -->

    <form id="esignForm" action="https://uatemra.cscdigisign.in/eSignAuthenticatorV1/AadhareSign.jsp" method="post">
        <!-- Hidden inputs with signed XML and name -->
        <input type="hidden" name="requestXMLone" value="${signedXml}" />
        <input type="hidden" name="NameAsAadhaar" value="" />

      <!--   <label>
            <input type="checkbox" onchange="handleConsentChange(this)" />
            I consent to use Aadhaar-based eSign service to sign the document.
        </label> -->
        
        <div class="content">
        <label class="consent-text">
            <input type="checkbox" id="consentCheckbox" onchange="handleConsentChange(this)">
            By clicking the checkbox, I hereby give my consent for using e-KYC services data from AADHAAR 
            for the purpose of signing selected document and generating Digital signature.
        </label>
    </div>
    </form>
    
   
   
    
    <div class="footer">
        © Powered by .
    </div>


</body>
</html>
