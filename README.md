# PPKWU-Lab5   

## GET /vCard/{profession}  
Description: endpoint responsible for generating website with found results for passed request.   

Path: /vCard/{profession}   (GET)  
Params:   
 {profession} - search profession   
Return: website with found results for passed profession

Example:   
/vCard/hydraulik   

Response:   
[example generated website](https://piotrkupis.github.io/PPKWU-Lab5/)


## GET /vCard/generate/{profession}/{business-email}/  
Description: endpoint responsible for generating vCard for business with passed email.    

Path: /vCard/generate/{profession}/{business-email}  (GET)  
Params:   
 {profession} - search profession  
 {business-email} - email of specific business     
Return: vCard of specific business  

Example:   
/vCard/generate/hydraulik/biuro@transpol.czest.pl   

Response:   
[example vCard](https://github.com/PiotrKupis/PPKWU-Lab5/blob/main/vCards/vcard_Transpol_Roboty_ziemne_Rafa%C5%82_Michnicki.vcf)
