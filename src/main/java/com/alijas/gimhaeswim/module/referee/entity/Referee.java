package com.alijas.gimhaeswim.module.referee.entity;

import com.alijas.gimhaeswim.module.common.enums.ApplyStatus;
import com.alijas.gimhaeswim.module.common.jpa.BaseTime;
import com.alijas.gimhaeswim.module.referee.dto.RefereeDTO;
import com.alijas.gimhaeswim.module.referee.dto.RefereeListDTO;
import com.alijas.gimhaeswim.module.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "REFEREES")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    public RefereeDTO toDTO() {
        return new RefereeDTO(
                this.id,
                this.user.toUserDTO(),
                this.status
        );
    }

    public RefereeListDTO toRefereeListDTO() {
        return new RefereeListDTO(
                this.id,
                this.user.getFullName(),
                this.user.getEmail(),
                this.user.getPhoneNumber()
        );
    }
}
