package com.eco.beminichat.response;

import com.eco.beminichat.dto.MessageDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class SingleMessageResponse {

    MessageDto message;

    Long conversationId;

}
