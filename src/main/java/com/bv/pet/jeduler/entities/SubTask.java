package com.bv.pet.jeduler.entities;

import jakarta.persistence.*;

@Entity
@Table
public class SubTask {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    private String name;
}
