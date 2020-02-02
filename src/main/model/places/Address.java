package model.places;

public class Address {

    private String street;
    private String city;
    private String province;

    public Address(String street, String city, String province) {
        this.street = street;
        this.city = city;
        this.province = province;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String makeQueryableAddress() {
        return street.replaceAll(" ", "+") + ",+"
                + city.replaceAll(" ", "+") + ",+"
                + province.replaceAll(" ", "+");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Address)) {
            return false;
        }

        Address address = (Address) o;

        if (!street.equals(address.street)) {
            return false;
        }
        if (!city.equals(address.city)) {
            return false;
        }
        return province.equals(address.province);
    }

    @Override
    public int hashCode() {
        int result = street.hashCode();
        result = 31 * result + city.hashCode();
        result = 31 * result + province.hashCode();
        return result;
    }
}
