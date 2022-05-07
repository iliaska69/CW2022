package com.cw.cwSpring.models.Base;

import javax.persistence.*;

@MappedSuperclass()
public class EntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
