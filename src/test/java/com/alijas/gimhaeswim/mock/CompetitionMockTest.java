package com.alijas.gimhaeswim.mock;

import com.alijas.gimhaeswim.config.SecurityConfig;
import com.alijas.gimhaeswim.example.CompetitionExample;
import com.alijas.gimhaeswim.module.competition.controller.CompetitionViewController;
import com.alijas.gimhaeswim.module.competition.dto.CompetitionListDTO;
import com.alijas.gimhaeswim.module.competition.entity.Competition;
import com.alijas.gimhaeswim.module.competition.entity.CompetitionEvent;
import com.alijas.gimhaeswim.module.competition.enums.status.CompetitionStatus;
import com.alijas.gimhaeswim.module.competition.service.CompetitionEventService;
import com.alijas.gimhaeswim.module.competition.service.CompetitionService;
import com.alijas.gimhaeswim.util.DateTimeConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(CompetitionViewController.class)
@Import(SecurityConfig.class)
public class CompetitionMockTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CompetitionService competitionService;

    @MockBean
    private CompetitionEventService competitionEventService;

    @BeforeEach
    public void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(
                        new CompetitionViewController(competitionService, competitionEventService)
                )
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @DisplayName("대회 목록 조회")
    @Test
    void getCompetitionList() throws Exception {
        Pageable pageable = CompetitionExample.pageable;
        Competition competition = CompetitionExample.competition;

        List<Competition> competitionList = List.of(competition, new Competition());

        Page<Competition> competitionPage = new PageImpl<>(competitionList, pageable, competitionList.size());
        Page<CompetitionListDTO> map = competitionPage.map(Competition::toCompetitionListDTO);
        given(competitionService.findAll(any(PageRequest.class))).willReturn(
                map
        );

        ResultActions perform = mvc.perform(
                get("/competitions")
                        .param("page", String.valueOf(pageable.getPageNumber()))
                        .param("size", String.valueOf(pageable.getPageSize()))
                        .param("sort", "id")
        );

        perform
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("competitionList"))
                .andExpect(model().attribute("competitionResponses", competitionPage))
                .andExpect(model().size(1))
                .andExpect(model().attributeExists("competitionResponses"));

        assertThat(competitionList.size()).isEqualTo(2);
        assertThat(competitionList.get(0).getId()).isEqualTo(1L);
        assertThat(competitionList.get(0).getCompetitionName()).isEqualTo("2021년 제1회 김해시장배 수영대회");
        assertThat(competitionList.get(0).getCompetitionDate()).isEqualTo(DateTimeConverter.StringToLocalDateTime("2021-10-28 09:00:00"));
        assertThat(competitionList.get(0).getCompetitionPlace()).isEqualTo("김해시 체육관");
        assertThat(competitionList.get(0).getCompetitionApplyStartDate()).isEqualTo(DateTimeConverter.StringToLocalDateTime("2021-10-01 00:00:00"));
        assertThat(competitionList.get(0).getCompetitionApplyEndDate()).isEqualTo(DateTimeConverter.StringToLocalDateTime("2021-10-20 23:59:59"));
        assertThat(competitionList.get(0).getCompetitionContent()).isEqualTo("2021년 제1회 김해시장배 수영대회");
        assertThat(competitionList.get(0).getCompetitionFee()).isEqualTo(10000);
        assertThat(competitionList.get(0).getCompetitionStudentFee()).isEqualTo(5000);
        assertThat(competitionList.get(0).getCompetitionAccount()).isEqualTo("신한은행 110-123-456789 홍길동");
        assertThat(competitionList.get(0).getStatus()).isEqualTo(CompetitionStatus.ACTIVE);

    }


    @Test
    @DisplayName("대회 상세 조회 실패 - 존재하지 않는 대회")
    void getCompetitionDetailFail() throws Exception {
        given(competitionService.getCompetition(1L)).willReturn(
                Optional.empty()
        );

        ResultActions perform = mvc.perform(
                get("/competitions/{id}", 0L)
        );

        perform
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(view().name("error"))
                .andExpect(model().attributeExists("message"))
                .andExpect(model().attributeExists("status"))
                .andExpect(model().attributeExists("timestamp"))
                .andExpect(model().size(3));
    }


}
