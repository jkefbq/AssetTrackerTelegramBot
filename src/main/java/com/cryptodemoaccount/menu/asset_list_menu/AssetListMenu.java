package com.cryptodemoaccount.menu.asset_list_menu;

import com.cryptodemoaccount.menu.IMenu;
import com.cryptodemoaccount.menu.incorrect_update_asset_menu.CancelToMyAssets;
import com.cryptodemoaccount.service.LastMessageService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.List;

@Component
@AllArgsConstructor
public class AssetListMenu implements IMenu {

    private final TelegramClient telegramClient;
    private final LastMessageService lastMessageService;
    private final BitcoinButton bitcoinButton;
    private final BNBButton bnbButton;
    private final DogecoinButton dogecoinButton;
    private final EthereumButton ethereumButton;
    private final LiteCoinButton liteCoinButton;
    private final PepeButton pepeButton;
    private final SolanaButton solanaButton;
    private final USDTButton usdtButton;
    private final TronButton tronButton;
    private final XRPButton xrpButton;
    private final CancelToMyAssets cancelButton;

    @Override
    @SneakyThrows
    public void editMsgAndSendMenu(Long chatId, Integer messageId) {
        EditMessageText editMessageText = EditMessageText.builder()
                .chatId(chatId)
                .messageId(messageId)
                .text(getMenuText())
                .replyMarkup(getMarkup())
                .parseMode(ParseMode.HTML)
                .build();
        telegramClient.execute(editMessageText);
    }

    @Override
    @SneakyThrows
    public void sendMenu(Long chatId) {
        SendMessage sendMessage = SendMessage.builder()
                .chatId(chatId)
                .text(getMenuText())
                .replyMarkup(getMarkup())
                .parseMode(ParseMode.HTML)
                .build();
        Message msg = telegramClient.execute(sendMessage);
        lastMessageService.setLastMessage(chatId, msg.getMessageId());
    }

    public InlineKeyboardMarkup getMarkup() {
        return new InlineKeyboardMarkup(
                List.of(
                        new InlineKeyboardRow(bitcoinButton.getButton(), ethereumButton.getButton()),
                        new InlineKeyboardRow(bnbButton.getButton(), solanaButton.getButton()),
                        new InlineKeyboardRow(liteCoinButton.getButton(), usdtButton.getButton()),
                        new InlineKeyboardRow(dogecoinButton.getButton(), pepeButton.getButton()),
                        new InlineKeyboardRow(tronButton.getButton(), xrpButton.getButton()),
                        new InlineKeyboardRow(cancelButton.getButton())
                )
        );
    }

    private String getMenuText() {
        return """
                <pre><code class="language-java">
                new Thread(() -> {
                    System.out.println(
                        "Выбери любую монету"
                    );
                }).start();
                </code></pre>""";
    }
}
