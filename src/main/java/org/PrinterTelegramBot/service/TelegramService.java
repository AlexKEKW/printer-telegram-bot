package org.PrinterTelegramBot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;


public class TelegramService {

    private static final Logger logger = LoggerFactory.getLogger(TelegramService.class);
    private final AbsSender bot;

    public TelegramService(AbsSender bot) {
        this.bot = bot;
    }


    public void sendWelcomeMessage(String getChatUserName, Long chatId) {

        String welcomeMessage = getChatUserName + "\nBem-vindo ao bot de captura de tela! 📸\nEnvie um link " + "e eu retornarei uma captura de tela da página.";
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText(welcomeMessage);

        try {
            bot.execute(message);
        } catch (TelegramApiException e) {
            logger.error("Erro ao enviar mensagem de boas-vindas", e);
        }
    }

    public void sendScreenshot(Long chatId, String screenshotPath) {
        if (screenshotPath == null || !new File(screenshotPath).exists()) {
            SendMessage errorMessage = new SendMessage();
            errorMessage.setChatId(chatId.toString());
            errorMessage.setText("⚠️ Houve um erro ao capturar a tela. Por favor, tente novamente.");
            try {
                bot.execute(errorMessage);
            } catch (TelegramApiException e) {
                logger.error("Erro ao enviar mensagem informando falha na hora de tirar screenshot.", e);
            }
            return;
        }

        SendDocument sendDocumentRequest = new SendDocument();
        sendDocumentRequest.setChatId(chatId.toString());
        sendDocumentRequest.setDocument(new InputFile(new File(screenshotPath)));

        try {
            bot.execute(sendDocumentRequest);
        } catch (TelegramApiException e) {
            logger.error("Erro ao enviar screenshot.", e);
        }
    }

}
