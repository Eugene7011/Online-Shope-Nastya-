package entity;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private int id;
    private String name;
    private double price;
    private LocalDateTime creationDate;
}

