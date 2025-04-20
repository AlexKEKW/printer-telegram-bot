package org.PrinterTelegramBot.service;

import org.PrinterTelegramBot.config.ConfigLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BotService extends TelegramLongPollingBot {

    private static final Logger logger = LoggerFactory.getLogger(BotService.class);
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    TelegramService telegramService = new TelegramService(this);
    ScreenshotService screenshotService = new ScreenshotService();

    @Override
    public String getBotUsername() {
        return ConfigLoader.get("BOT_USERNAME");
    }

    @Override
    public String getBotToken() {
        return ConfigLoader.get("BOT_TOKEN");
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String currentDateTime = LocalDateTime.now().format(dateTimeFormatter);
            MDC.put("datetime", currentDateTime);

            String messageText = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();
            String chatUsername = update.getMessage().getFrom().getUserName();

            if (messageText.equals("/start")) {
                telegramService.sendWelcomeMessage("Olá, " + chatUsername + "!", chatId);
                logger.info("[{}] chatID = {}", currentDateTime, chatId);
                logger.info("[{}] getUserName = {}", currentDateTime, chatUsername);
            } else {
                String screenshotPath = screenshotService.takeScreenshot(messageText);
                telegramService.sendScreenshot(chatId, screenshotPath);
            }

            MDC.remove("datetime"); // Limpa o MDC após o uso
        }
    }
}