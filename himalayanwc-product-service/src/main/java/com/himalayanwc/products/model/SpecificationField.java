package com.himalayanwc.products.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "spec_fields")
@Getter
@Setter
public class SpecificationField {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fieldName;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FieldType fieldType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @JsonIgnore
    private Category category;


    @OneToMany(mappedBy = "specificationField", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<SpecificationOption> options = new ArrayList<>();
}