package com.alijas.gimhaeswim.module.history.entity;

import com.alijas.gimhaeswim.module.common.jpa.BaseTime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "HISTORY")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class History extends BaseTime {

    @Id
    @Comment("고유 번호")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("연혁 년도")
    private String historyYear;

    @Comment("연혁 월")
    private String historyMonth;

    @Comment("연혁 내용")
    private String content;
}
