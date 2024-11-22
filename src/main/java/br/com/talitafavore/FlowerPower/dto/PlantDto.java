package br.com.talitafavore.FlowerPower.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class PlantDto {

    private long id;
    private String name;
    private String subtitle;
    private BigDecimal price;
    private String description;
    private String img;
    private TypeDto type;
}
