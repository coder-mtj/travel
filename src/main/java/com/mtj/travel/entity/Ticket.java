package com.mtj.travel.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {

  private String id;
  private String sessionId;
  private String type;
  private double price;
  private long quantity;
  private long sold;
  private java.sql.Timestamp startTime;
  private java.sql.Timestamp endTime;


}
