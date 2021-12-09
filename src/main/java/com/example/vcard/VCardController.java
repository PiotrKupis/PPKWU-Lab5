package com.example.vcard;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
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

        List<Business> businesses = getBusinesses(profession);

        StringBuilder page = new StringBuilder();
        page.append("<html><header><title>VCard</title></header><body>");

        page.append("<table><tr>");
        page.append("<th>Nazwa</th>");
        page.append("<th>Numer telefonu</th>");
        page.append("<th>Email</th>");
        page.append("<th>Adres</th>");
        page.append("<th>Operacja</th></tr>");
        businesses.forEach(business -> {
            page.append("<tr>");
            page.append("<td>").append(business.getName()).append("</td>");
            page.append("<td>").append(business.getTelephone()).append("</td>");
            page.append("<td>").append(business.getEmail()).append("</td>");
            page.append("<td>").append(business.getStreetAddress()).append(", ")
                .append(business.getPostalCode()).append(" ").append(business.getCity())
                .append("</td>");
            page.append(
                    "<td><button onclick=\"location.href='http://localhost:8080/vCard/generate/")
                .append(profession).append("/").append(business.getEmail())
                .append("'\" type=\"button\">wygeneruj vCard</button>")
                .append("</td>");
            page.append("</tr>");
        });
        page.append("</body></html>");

        saveFile("index.html", page);

        return page.toString();
    }

    @GetMapping("/vCard/generate/{profession}/{email}")
    public String generateVCard(@PathVariable("profession") String profession,
        @PathVariable("email") String email)
        throws IOException {
        Business business = getBusinesses(profession).stream()
            .filter(b -> b.getEmail().equals(email))
            .findFirst()
            .get();

        StringBuilder vCard = new StringBuilder("BEGIN:VCARD\r\n");
        vCard.append("VERSION:4.0\r\n");
        vCard.append("ORG:").append(business.getName()).append("\r\n");
        vCard.append("TEL:").append(business.getTelephone()).append("\r\n");
        vCard.append("ADR:").append(business.getStreetAddress()).append(", ")
            .append(business.getPostalCode()).append(" ").append(business.getCity()).append("\r\n");
        vCard.append("EMAIL:").append(business.getEmail()).append("\r\n");
        vCard.append("END:VCARD\n");

        String path = "vCards/vcard_" + business.getName().replaceAll(" ", "_") + ".vcf";
        saveFile(path, vCard);
        return vCard.toString();
    }

    private void saveFile(String path, StringBuilder text) throws IOException {
        File file = new File(path);
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(text.toString().getBytes(StandardCharsets.UTF_8));
    }

    private List<Business> getBusinesses(String profession) throws IOException {
        String url = String.format("https://panoramafirm.pl/szukaj=%s", profession);
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
        return businesses;
    }

    private String getField(String json, String field) {
        int indexOf = json.indexOf(field);
        return json.substring(indexOf + field.length() + 3,
            json.indexOf("\"", indexOf + field.length() + 3));
    }
}
