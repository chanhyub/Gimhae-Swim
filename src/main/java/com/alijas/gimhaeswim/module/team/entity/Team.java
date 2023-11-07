package com.alijas.gimhaeswim.module.team.entity;

import com.alijas.gimhaeswim.module.common.jpa.BaseTime;
import com.alijas.gimhaeswim.module.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "TEAMS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Team extends BaseTime {

    @Id
    @Comment("고유 번호")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("팀 이름")
    @Column(unique = true)
    private String teamName;

    @Comment("팀 선수")
    @ManyToOne
    private User teamPlayer;
}
