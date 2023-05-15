package com.mtj.travel.Dto;

import com.mtj.travel.entity.Session;
import com.mtj.travel.entity.Ticket;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketDto extends Ticket {
    private Session session;
}
