package com.eco.beminichat.dto;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class NotificationDto {

    Long id;

    String content;

    String posterUrl;

    Timestamp createdAt;

    String type;

}
