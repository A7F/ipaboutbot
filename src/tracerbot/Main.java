package tracerbot;

import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.logging.BotLogger;

/**
 * this bot main class.
 * @author Luke
 */
public class Main{

    public static void main(String[] args){
        String LOGTAG = "Log >> ";
        
        TelegramBotsApi tgBotApi = new TelegramBotsApi();
        try {
            tgBotApi.registerBot(new BotHandler());
        } catch (TelegramApiException ex){
            BotLogger.error(LOGTAG, ex);
        }
    }
    
}
