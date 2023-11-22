package com.alijas.gimhaeswim.module.organization.entity;

import com.alijas.gimhaeswim.module.common.jpa.BaseTime;
import com.alijas.gimhaeswim.module.organization.enums.OrganizationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "ORGANIZATIONS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Organization extends BaseTime {
    @Id
    @Comment("고유 번호")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("조직도 순서")
    private Integer organizationIndex;

    @Comment("직위")
    private String position;

    @Comment("이름")
    private String name;

    @Comment("직업")
    private String job;

    @Comment("비고")
    private String note;

    @Comment("조직도 상태")
    @Enumerated(EnumType.STRING)
    private OrganizationStatus status;
}
