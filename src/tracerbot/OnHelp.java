package tracerbot;

import utils.LocalisationService;

/**
 * prepare the reply when command /help is used.
 * @author Luke
 */
public class OnHelp {
    
    public String getText(){
        StringBuilder sb = new StringBuilder();
        sb.append(LocalisationService.getInstance().getString("onHelp", "EN"));
        return sb.toString();
    }
}
