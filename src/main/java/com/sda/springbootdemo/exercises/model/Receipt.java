package com.sda.springbootdemo.exercises.model;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="receipts")
public class Receipt extends BaseEntity {

    @NotNull
    @Size(min = 1, max = 255)
    @Column(nullable = false)
    private String buyer;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime date;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "receipt_products",
        joinColumns =
        @JoinColumn(name = "receipt_id", nullable = false),
        inverseJoinColumns =
        @JoinColumn(name = "product_id", nullable = false))
    private List<Product> products;
}
