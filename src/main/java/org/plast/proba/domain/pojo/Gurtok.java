package org.plast.proba.domain.pojo;

import javax.persistence.*;

@Entity
@Table(name = "gurtok")
public class Gurtok {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "vyhovnyk_id")
    private User vyhovnykId;

}
