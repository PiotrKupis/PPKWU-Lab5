package com.example.vcard;

public class Business {

    private String name;
    private String telephone;
    private String email;
    private String city;
    private String streetAddress;
    private String postalCode;

    public Business() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @Override
    public String toString() {
        return "Business{" +
            "name='" + name + '\'' +
            ", telephone='" + telephone + '\'' +
            ", email='" + email + '\'' +
            ", city='" + city + '\'' +
            ", streetAddress='" + streetAddress + '\'' +
            ", postalCode='" + postalCode + '\'' +
            '}';
    }
}
