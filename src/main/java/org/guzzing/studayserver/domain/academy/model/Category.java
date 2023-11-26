package org.guzzing.studayserver.domain.academy.model;

import jakarta.persistence.*;


@Table(name = "categories")
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "category_name")
    @Enumerated(value = EnumType.STRING)
    CategoryName categoryName;

}
