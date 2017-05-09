package utils;

/**
 * model of a response from ipinfo api website.
 * @author Luke
 */
public class IpInfo {
    private String ip;
    private String hostname=null;
    private String city=null;
    private String region=null;
    private String country=null;
    private int zipcode = 0;
    private double lat;
    private double lon;
    private String organisation;

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setZipcode(int zipcode){
        this.zipcode = zipcode;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setLat(String lat) {
        String[] result = lat.split(",");
        double latitude = Double.valueOf(result[0]);
        this.lat = latitude;
    }

    public void setLon(String lon) {
        String[] result = lon.split(",");
        double longitude = Double.valueOf(result[1]);
        this.lon = longitude;
    }

    public void setOrganisation(String organisation) {
        this.organisation = organisation;
    }

    public String getIp() {
        return ip;
    }

    public int getZipcode() {
        return zipcode;
    }

    public String getHostname() {
        if(hostname.equals("No Hostname")){
            return null;
        }
        return hostname;
    }

    public String getCity() {
        if(city.equals("")){
            return null;
        }
        return city;
    }

    public String getRegion() {
        if(region.equals("")){
            return null;
        }
        return region;
    }

    public String getCountry() {
        if(country.equals("")){
            return null;
        }
        return country;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public String getOrganisation() {
        if(organisation.equals("")){
            return null;
        }
        return organisation;
    }

    @Override
    public String toString() {
        return "IpInfo{" + "ip=" + ip + ", hostname=" + hostname + ", city=" + city + ", region=" + region + ", country=" + country + ", lat=" + lat + ", lon=" + lon + ", organisation=" + organisation + '}';
    }
    
}
