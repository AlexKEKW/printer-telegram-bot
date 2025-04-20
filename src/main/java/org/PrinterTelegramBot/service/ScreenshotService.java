package org.PrinterTelegramBot.service;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.LoadState;

import java.io.File;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScreenshotService {

    private static final Logger logger = LoggerFactory.getLogger(ScreenshotService.class);

    public String takeScreenshot(String url) {
        File screenshotDir = new File("screenshots");
        if (!screenshotDir.exists()) {
            screenshotDir.mkdirs();
        }

        String timestamp = new SimpleDateFormat("yyyy-MM-dd'T'HH-mm").format(new Date());
        String screenshotPath = "src/main/resources/screenshots/screenshot_" + timestamp + ".png";

        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
            Page page = browser.newPage();

            try {
                page.navigate(url, new Page.NavigateOptions().setTimeout(60000));
                page.waitForLoadState(LoadState.NETWORKIDLE, new Page.WaitForLoadStateOptions().setTimeout(60000));
            } catch (Exception e) {
                logger.warn("Página não carregou completamente para a URL: {} - Tentando capturar mesmo assim.", url);
            }
            page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(screenshotPath)));

            return screenshotPath;
        } catch (Exception e) {
            logger.error("Erro ao tirar screenshot.", e);
            return null;
        }
    }

}
