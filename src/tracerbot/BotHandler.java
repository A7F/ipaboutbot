package tracerbot;

import java.util.ArrayList;
import keyboards.MyKeyboard;
import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.api.methods.AnswerInlineQuery;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.inlinequery.InlineQuery;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import settings.Settings;


public class BotHandler extends TelegramLongPollingBot{
    

    @Override
    public String getBotToken(){
        return Settings.BOT_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update){
        Message message=update.getMessage();
        SendMessage messageRequest = new SendMessage();
        
        //handle /start and /help commands. These must be implemented as specified by telegram standard guidelines
        if (update.hasMessage()){
            String messageText = message.getText();
            messageRequest.setChatId(message.getChatId().toString());
            
            if(messageText.equals("/start")){
                messageRequest.setText(new OnStart().getText());
                messageRequest.enableMarkdown(true);
                messageRequest.setReplyMarkup(new MyKeyboard());
                
                try {
                    sendMessage(messageRequest);
                } catch (TelegramApiException ex) {
                    ex.toString();
                }
            }
            
            if(messageText.equals("/help") || message.getText().equals("help")){
                messageRequest.setText(new OnHelp().getText());
                messageRequest.enableMarkdown(true);
                messageRequest.setChatId(message.getChatId().toString());
                
                try {
                    sendMessage(messageRequest);
                } catch (TelegramApiException ex) {
                    ex.toString();
                }
            }
            
        }
        
        //handle inline queries
        if(update.hasInlineQuery()){
            InlineQuery inlineQuery = update.getInlineQuery();
            AnswerInlineQuery answer = new AnswerInlineQuery();
            answer.setInlineQueryId(inlineQuery.getId());
            
            if(!inlineQuery.getQuery().isEmpty()){
                if(inlineQuery.getQuery().equals("127.0.0.1") || inlineQuery.getQuery().equals("localhost")){
                    answer.setResults(new ArrayList<>());
                }
                OnInlineQuery myQuery = new OnInlineQuery();
                answer.setCacheTime(100);
                answer.setResults(myQuery.runQuery(inlineQuery.getQuery()));
            }else{
                answer.setResults(new ArrayList<>());
            }
            try {
                answerInlineQuery(answer);
            } catch (TelegramApiException ex) {
                ex.toString();
            }
            
        }
    }

    @Override
    public String getBotUsername() {
        return Settings.BOT_USERNAME;
    }
    
}
