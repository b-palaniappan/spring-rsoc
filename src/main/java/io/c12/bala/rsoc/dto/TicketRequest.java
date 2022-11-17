package io.c12.bala.rsoc.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TicketRequest {
    private String requestId;
    private TicketStatus status = TicketStatus.PENDING;
}
