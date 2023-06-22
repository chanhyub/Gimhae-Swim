package com.alijas.gimhaeswim.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "history")
public class History {
    @Id
    @Column(name = "history_seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer historySeq;

    @Column(name = "history_year", nullable = false)
    private Integer historyYear;

    @Column(name = "history_month", nullable = false)
    private Integer historyMonth;

    @Column(name = "detail", nullable = false)
    private String detail;

}
