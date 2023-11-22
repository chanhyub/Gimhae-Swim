package com.alijas.gimhaeswim.repository;

import com.alijas.gimhaeswim.module.notice.entity.Notice;
import com.alijas.gimhaeswim.module.notice.enums.NoticeStatus;
import com.alijas.gimhaeswim.module.notice.repository.NoticeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DisplayName("NoticeRepositoryTest 테스트")
public class NoticeRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private NoticeRepository noticeRepository;

    @BeforeEach
    public void init() {
        setUp(
                "테스트 공지사항",
                "내용"
        );
    }

    @Test
    @DisplayName("공지사항 전체 조회")
    void selectAll() {
        var notices = noticeRepository.findAll();
        assertNotEquals(notices.size(), 0);

        assertEquals(notices.get(0).getTitle(), "테스트 공지사항");
        assertEquals(notices.get(0).getContent(), "내용");
        assertEquals(notices.get(0).getStatus(), NoticeStatus.ACTIVE);
    }

    @Test
    @DisplayName("공지사항 상세 조회 및 수정")
    void selectAndUpdate() {
        Optional<Notice> optionalNotice = noticeRepository.findById(1L);

        if (optionalNotice.isPresent()) {
            Notice result = optionalNotice.get();

            assertEquals(result.getTitle(), "테스트 공지사항");
            assertEquals(result.getContent(), "내용");
            assertEquals(result.getStatus(), NoticeStatus.ACTIVE);

            String newTitle = "진짜 테스트 공지사항";
            result.setTitle(newTitle);
            Notice updateNotice = entityManager.persist(result);

            assertEquals(updateNotice.getTitle(), newTitle);
        } else {
            assertNotNull(optionalNotice);
        }
    }

    @Test
    @DisplayName("공지사항 추가 및 삭제")
    void insertAndDelete() {
        Notice setUp = setUp(
                "테스트 공지사항2",
                "내용"
        );
        Optional<Notice> optionalNotice = noticeRepository.findById(setUp.getId());

        if (optionalNotice.isPresent()) {
            Notice notice = optionalNotice.get();
            assertEquals(notice.getTitle(), "테스트 공지사항2");
            assertEquals(notice.getContent(), "내용");

            entityManager.remove(notice);
            Optional<Notice> removeNotice = noticeRepository.findById(setUp.getId());

            removeNotice.ifPresent(Assertions::assertNull);
        }
    }

    public Notice setUp(
            String title,
            String content
    ) {
        Notice notice = new Notice(
                null,
                title,
                content,
                null,
                NoticeStatus.ACTIVE,
                null
        );
        return entityManager.persist(notice);
    }
}
