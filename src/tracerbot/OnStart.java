package tracerbot;

import utils.LocalisationService;


public class OnStart {
    
    public String getText(){
        StringBuilder sb = new StringBuilder();
        sb.append(LocalisationService.getInstance().getString("onStart", "EN"));
        return sb.toString();
    }
}
