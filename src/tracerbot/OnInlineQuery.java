package tracerbot;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.telegram.telegrambots.api.objects.inlinequery.inputmessagecontent.InputTextMessageContent;
import org.telegram.telegrambots.api.objects.inlinequery.inputmessagecontent.InputVenueMessageContent;
import org.telegram.telegrambots.api.objects.inlinequery.result.InlineQueryResult;
import org.telegram.telegrambots.api.objects.inlinequery.result.InlineQueryResultArticle;
import utils.Emoji;
import utils.IpInfo;
import utils.IpInfoService;
import utils.LocalisationService;

/**
 * run the query (which is, perform the lookup).
 * I had no time to put all the strings inside properties file also was no need to add such short strings inside there.
 * @author Luke
 */
public class OnInlineQuery {
    String query = "";
    
    public List<InlineQueryResult> runQuery(String ip){
        this.query=ip;
        IpInfo ipInfo = getInfo();

        List<InlineQueryResult> ilr = new ArrayList<>();
        InlineQueryResultArticle r = new InlineQueryResultArticle();
        r.setTitle("Scan result");
        r.setId("0");
        InputTextMessageContent messageContent = new InputTextMessageContent();        

        if(ipInfo==null){
            r.setDescription("Invalid address");
            messageContent.setMessageText(LocalisationService.getInstance().getString("invalid", "EN"));
            r.setInputMessageContent(messageContent);
        }else{
            r.setDescription("Tap here to get full scan results");
            messageContent.setMessageText(buildResponse(ipInfo));
            messageContent.disableWebPagePreview();
            messageContent.enableMarkdown(true);
            r.setInputMessageContent(messageContent);
        }
        
        ilr.add(r);
        
        InlineQueryResultArticle s = new InlineQueryResultArticle();
        InputVenueMessageContent venueContent = new InputVenueMessageContent();
        s.setTitle("Get geo position");
        s.setId("1");
        if(ipInfo==null){
            s.setDescription("Invalid address");
            messageContent.setMessageText(LocalisationService.getInstance().getString("invalid", "EN"));
            s.setInputMessageContent(messageContent);
        }else{
            s.setDescription("Tap here to locate the position on a map");
            venueContent.setLatitude((float)ipInfo.getLat());
            venueContent.setLongitude((float)ipInfo.getLon());
            venueContent.setTitle("");
            venueContent.setAddress("");
            s.setInputMessageContent(venueContent);
        }
        
        ilr.add(s);
        
        return ilr;
    }
    
    /**
     * perform the GET request
     * @return result of the request as a IpInfo object
     * @see IpInfo
     */
    private IpInfo getInfo(){
        IpInfoService service = new IpInfoService(query);
        IpInfo ipInfo = null;
        
        try {
            service.openConnection();
            service.runQuery();
            ipInfo = service.getIpInfo();
        } catch (FileNotFoundException ex) {
            System.out.println("not found");
            System.out.println(ex.toString());
        } catch (IOException ex) {
            System.out.println("io exception\n");
            System.out.println(ex.toString());
        }
        
        return ipInfo;
    }
    
    /**
     * prepare the message response text
     * @param info response from the api website
     * @return the formatted text with results inside
     */
    private String buildResponse(IpInfo info){
        StringBuilder sb = new StringBuilder();
        sb.append("Here is what you searched for.").append("\n\n");
        
        if(info.getIp()!=null){
            sb.append(Emoji.SmallRedTriangle).append("_Ip: _  ").append(info.getIp()).append("\n");
        }
        if(info.getHostname()!=null){
            sb.append(Emoji.BustInSilhouette).append("_Host: _  ").append(info.getIp()).append("\n\n");
        }
        if(info.getCity()!=null){
            sb.append(Emoji.House).append("_City: _  ").append(info.getCity()).append("\n");
        }
        if(info.getRegion()!=null){
            sb.append(Emoji.Office).append("_Region: _  ").append(info.getRegion()).append("\n");
        }
        if(info.getCountry()!=null){
            sb.append(Emoji.EarthAmericas).append("_Country: _  ").append(info.getCountry()).append("\n");
        }
        if(info.getZipcode()!=0){
            sb.append(Emoji.Postbox).append("_Zip: _  ").append(info.getZipcode()).append("\n");
        }
        if(info.getOrganisation()!=null){
            sb.append(Emoji.EuropeanPostOffice).append("_Org: _  ").append(info.getOrganisation()).append("\n\n");
        }
        if(info.getLat()!=0){
            sb.append(Emoji.RoundPushpin).append("_Lat: _  ").append(info.getLat()).append("\n");
        }
        if(info.getLon()!=0){
            sb.append(Emoji.RoundPushpin).append("_Lon: _  ").append(info.getLon()).append("\n");
        }
        
        return sb.toString();
    }
}
