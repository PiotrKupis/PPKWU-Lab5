package com.example.vcard;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VCardController {

    @GetMapping("/vCard/{profession}")
    public String getData(@PathVariable("profession") String profession){

        StringBuilder page = new StringBuilder();
        page.append("<html><header><title>VCard</title></header><body>");
        page.append("test");

        page.append("<table><tr>");
        page.append("<th>Nazwa</th>");
        page.append("<th>Numer telefonu</th>");
        page.append("<th>Email</th>");
        page.append("<th>Adres</th>");

        page.append("</body></html>");


        return page.toString();
    }
}
