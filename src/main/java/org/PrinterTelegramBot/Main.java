package org.PrinterTelegramBot;

import org.PrinterTelegramBot.service.BotService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main {
    public static void main(String[] args) {

        final Logger logger = LoggerFactory.getLogger(Main.class);

        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new BotService());
            logger.info("Bot iniciado com sucesso.");
        } catch (TelegramApiException e) {
            logger.error("Erro ao iniciar o bot.", e);
        }

    }
}