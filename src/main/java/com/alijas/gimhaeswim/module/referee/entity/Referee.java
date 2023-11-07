package com.alijas.gimhaeswim.module.referee.entity;

import com.alijas.gimhaeswim.module.common.enums.ApplyStatus;
import com.alijas.gimhaeswim.module.common.jpa.BaseTime;
import com.alijas.gimhaeswim.module.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "REFEREES")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Referee extends BaseTime {

    @Id
    @Comment("고유 번호")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("심판 정보")
    @ManyToOne
    private User user;

    @Comment("심판 상태")
    @Enumerated(EnumType.STRING)
    private ApplyStatus status;
}
