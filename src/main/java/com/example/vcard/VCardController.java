package com.example.vcard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VCardController {

    @GetMapping("/vCard/{profession}")
    public String getData(@PathVariable("profession") String profession) throws IOException {

        String url = String.format("https://panoramafirm.pl/szukaj=%s", profession);
        System.out.println(url);
        Document document = Jsoup.connect(url).get();

        List<Business> businesses = new ArrayList<>();
        Elements elements = document.select("script");
        for (Element element : elements) {
            if (element.attr("type").equals("application/ld+json")) {
                if (element.data().contains("LocalBusiness")) {
                    Business business = new Business();
                    business.setName(getField(element.data(), "name"));
                    business.setTelephone(getField(element.data(), "telephone"));
                    business.setEmail(getField(element.data(), "email"));
                    business.setPostalCode(getField(element.data(), "postalCode"));
                    business.setStreetAddress(getField(element.data(), "streetAddress"));
                    business.setCity(getField(element.data(), "addressLocality"));
                    businesses.add(business);
                }
            }
        }

        businesses.forEach(System.out::println);

        StringBuilder page = new StringBuilder();
        page.append("<html><header><title>VCard</title></header><body>");

        page.append("<table><tr>");
        page.append("<th>Nazwa</th>");
        page.append("<th>Numer telefonu</th>");
        page.append("<th>Email</th>");
        page.append("<th>Adres</th>");

        page.append("</body></html>");

        return page.toString();
    }

    private String getField(String json, String field) {
        int indexOf = json.indexOf(field);
        return json.substring(indexOf + field.length() + 3,
            json.indexOf("\"", indexOf + field.length() + 3));
    }
}
