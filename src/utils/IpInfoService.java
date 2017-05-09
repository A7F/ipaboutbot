package utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import org.json.JSONObject;

/**
 * this is just a basic API made by myself to handle GET requests to ipinfo website.
 * Nothing special but does its job.
 * @author Luke
 */
public class IpInfoService {
    private final String BASE_URL = "http://www.ipinfo.io/";
    private String IP;
    private String TARGET;
    private int response_code = 0;
    private URL url;
    private HttpURLConnection con;
    private final String USER_AGENT = "Mozilla/5.0";
    JSONObject jsonObject;
    
    
    public IpInfoService(String ip){
        this.IP=ip;
    }
    
    public IpInfoService(){
        this.IP="/json";
    }
    
    public void openConnection() throws FileNotFoundException{
        try {
            buildRequest();
            url = new URL(TARGET);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", USER_AGENT);
            response_code = con.getResponseCode();
        } catch (IOException ex) {
            ex.toString();
        }
        
    }
    
    private void buildRequest(){
        if(this.IP.equals("/json")){
            this.TARGET=BASE_URL+IP;
        }else{
            this.TARGET=BASE_URL+IP+"/json";
        }
    }
    
    public void runQuery() throws IOException,FileNotFoundException {
        InputStream is = null;
        try{
            is = con.getInputStream();
        }catch(UnknownHostException e){
            e.toString();
        }
        BufferedReader in = new BufferedReader(new InputStreamReader(is));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        jsonObject = new JSONObject(response.toString());
    }
    
    public void setUrl(String address){
        this.IP=address;
    }
    
    public IpInfo getIpInfo(){
        IpInfo ipInfo = new IpInfo();
        
        if(jsonObject.has("ip")){
            ipInfo.setIp(IP);
        }
        if(jsonObject.has("loc")){
            ipInfo.setLat(jsonObject.getString("loc"));
            ipInfo.setLon(jsonObject.getString("loc"));
        }
        if(jsonObject.has("postal")){
            ipInfo.setZipcode(jsonObject.getInt("postal"));
        }
        if(jsonObject.has("city")){
            ipInfo.setCity(jsonObject.getString("city"));
        }
        if(jsonObject.has("region")){
            ipInfo.setRegion(jsonObject.getString("region"));
        }
        if(jsonObject.has("country")){
            ipInfo.setCountry(jsonObject.getString("country"));
        }
        if(jsonObject.has("org")){
            ipInfo.setOrganisation(jsonObject.getString("org"));
        }
        if(jsonObject.has("hostname")){
            ipInfo.setHostname(jsonObject.getString("hostname"));
        }
        
        return ipInfo;
    }
    
    public int getResponseCode(){
        return this.response_code;
    }
}
