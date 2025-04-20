# 🤖 Bot de Captura de Tela via Telegram

Este é um projeto Java que integra a biblioteca [Playwright](https://playwright.dev/java/) para automação de navegador com a [TelegramBots Java API](https://github.com/rubenlagus/TelegramBots), permitindo que usuários do Telegram enviem links e recebam de volta uma captura de tela da página solicitada.

---

## 🚀 Tecnologias utilizadas

- **Java 17+**
- **Playwright (via Playwright for Java)**
- **TelegramBots (Java)**
- **SLF4J (Logging)**
- **Maven** (para gerenciamento de dependências)

---

## 📸 Funcionamento

1. O usuário envia um link para o bot no Telegram.
2. O bot navega até a página usando o Playwright (modo headless).
3. Após o carregamento, ele tira uma captura de tela e envia de volta como documento.
4. Caso a URL esteja incorreta ou a página não carregue corretamente, o bot retorna uma mensagem de erro amigável.

---

## 🤝 Integração com Telegram

Este bot utiliza a [API do Telegram para criação de BOTs](https://core.telegram.org/bots), uma biblioteca que facilita a criação de bots para a plataforma.

### Como funciona a integração:

- A classe principal `BotService` estende `TelegramLongPollingBot`, que escuta continuamente mensagens enviadas ao bot.
- Toda vez que uma mensagem é recebida, ela é tratada pelo método `onUpdateReceived(Update update)`.
- Caso o conteúdo seja um link, o bot inicia o processo de captura com o Playwright e retorna a imagem via `SendDocument`.
- Se a mensagem for `/start`, o bot responde com uma mensagem personalizada de boas-vindas.

### Requisitos no Telegram:

1. Acesse o **[@BotFather](https://t.me/BotFather)** no Telegram e crie um novo bot:
    - Comando: `/newbot`
    - Dê um nome e um @username único
2. O BotFather irá fornecer um **Token** de autenticação. Exemplo:
    - 123456789:ABCDefGhIJKlmNOpQRstuvWXyz
3. Copie esse token e coloque no arquivo `application.properties`:
```properties
BOT_USERNAME=NomeDoSeuBot
BOT_TOKEN=123456789:ABCDefGhIJKlmNOpQRstuvWXyz
```
### Estrutura do projeto:
```
.
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── org/
│   │   │       └── Teste/
│   │   │           ├── Main.java
│   │   │           ├── config/
│   │   │           │   └── ConfigLoader.java
│   │   │           ├── service/
│   │   │               ├── BotService.java
│   │   │               └── ScreenshotService.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── screenshots/
│   │             └── screenshot_YYYYMMDD_HHmmss.png 
└── .gitignore
```

### 📬 Contato
Se você tiver sugestões, dúvidas ou quiser contribuir, fique à vontade para abrir uma issue ou pull request!