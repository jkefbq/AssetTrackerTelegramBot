package com.cryptodemoaccount.events;

import com.cryptodemoaccount.database.dto.UpdateDto;
import lombok.Getter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;

@ToString
public class MessageEvent extends ApplicationEvent {

    @Getter
    private final Message message;
    @Getter
    private final UpdateDto updateDto;

    public MessageEvent(Object source, Message message, UpdateDto updateDto) {
        super(source);
        this.message = message;
        this.updateDto = updateDto;
    }
}
