package br.com.talitafavore.FlowerPower.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "plants")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class PlantModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "subtitle", length = 200, nullable = false)
    private String subtitle;

    @Column(name = "price", precision = 10, scale = 2, nullable = false)
    private BigDecimal price;

    @Column(name = "description", length = 300, nullable = false)
    private String description;

    @Column(name = "img", length = 100, nullable = false)
    private String img;

    @ManyToOne
    @JoinColumn (name = "type_id")
    private TypeModel type;
}
