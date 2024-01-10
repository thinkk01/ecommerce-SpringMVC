package com.ecommerce.library.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "categories", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;
    @Column(name="name")
    private String name;
    @Column(name = "is_activated")
    private boolean isActivated;
    @Column(name = "is_deleted")
    private boolean isDeleted;
    @Column(name = "activated")
    private boolean activated;
    @Column(name = "deleted")
    private boolean deleted;


    public Category(String name) {
        this.name = name;
        this.isActivated = true;
        this.isDeleted = false;
        this.activated = true;
        this.deleted = false;
    }

    public boolean isActivated() {
        return isActivated;
    }

    public void setActivated(boolean activated) {
        isActivated = activated;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
