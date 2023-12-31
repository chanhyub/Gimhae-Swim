INSERT INTO USERS (username, password, birthday, phone_number, email, gender, is_agree, status, apply_status, role, created_date, modified_date)
VALUES
    ('Jorge', '1234', '1999-10-28', '01083384583', 'test1234@test.com', 'M', true, 'ACTIVE', 'APPROVED', 'ADMIN', '2023-11-20 00:00:00', '2023-11-20 00:00:00'),
    ('user1', 'password1', '1990-01-01', '01012345678', 'user1@example.com', 'M', true, 'ACTIVE', 'APPROVED', 'USER', '2023-11-20 00:00:00', '2023-11-20 00:00:00'),
    ('user2', 'password2', '1990-01-01', '01012345678', 'user2@example.com', 'M', true, 'ACTIVE', 'APPROVED', 'USER', '2023-11-20 00:00:00', '2023-11-20 00:00:00'),
    ('user3', 'password3', '1990-01-01', '01012345678', 'user3@example.com', 'M', true, 'ACTIVE', 'APPROVED', 'USER', '2023-11-20 00:00:00', '2023-11-20 00:00:00'),
    ('user4', 'password4', '1990-01-01', '01012345678', 'user4@example.com', 'M', true, 'ACTIVE', 'APPROVED', 'USER', '2023-11-20 00:00:00', '2023-11-20 00:00:00'),
    ('user5', 'password5', '1990-01-01', '01012345678', 'user5@example.com', 'M', true, 'ACTIVE', 'APPROVED', 'USER', '2023-11-20 00:00:00', '2023-11-20 00:00:00'),
    ('user6', 'password6', '1990-01-01', '01012345678', 'user6@example.com', 'M', true, 'ACTIVE', 'APPROVED', 'USER', '2023-11-20 00:00:00', '2023-11-20 00:00:00'),
    ('user7', 'password7', '1990-01-01', '01012345678', 'user7@example.com', 'M', true, 'ACTIVE', 'APPROVED', 'USER', '2023-11-20 00:00:00', '2023-11-20 00:00:00'),
    ('user8', 'password8', '1990-01-01', '01012345678', 'user8@example.com', 'M', true, 'ACTIVE', 'APPROVED', 'USER', '2023-11-20 00:00:00', '2023-11-20 00:00:00'),
    ('user9', 'password9', '1990-01-01', '01012345678', 'user9@example.com', 'M', true, 'ACTIVE', 'APPROVED', 'USER', '2023-11-20 00:00:00', '2023-11-20 00:00:00')
;

INSERT INTO DEPARTMENTS (department_age, more_or_less, status, department_info, created_date, modified_date) VALUES
(10, 'LESS', 'ACTIVE', '여자학생부', '2023-01-01 00:00:00', '2023-01-01 00:00:00'),
(15, 'MORE', 'INACTIVE', '남자학생부', '2023-01-02 00:00:00', '2023-01-02 00:00:00'),
(20, 'LESS', 'ACTIVE', '여자학생부', '2023-01-03 00:00:00', '2023-01-03 00:00:00'),
(25, 'MORE', 'INACTIVE', '남자학생부', '2023-01-04 00:00:00', '2023-01-04 00:00:00'),
(30, 'LESS', 'ACTIVE', '여자학생부', '2023-01-05 00:00:00', '2023-01-05 00:00:00'),
(35, 'MORE', 'INACTIVE', '남자학생부', '2023-01-06 00:00:00', '2023-01-06 00:00:00'),
(40, 'LESS', 'ACTIVE', '여자학생부', '2023-01-07 00:00:00', '2023-01-07 00:00:00'),
(45, 'MORE', 'INACTIVE', '남자학생부', '2023-01-08 00:00:00', '2023-01-08 00:00:00'),
(50, 'LESS', 'ACTIVE', '여자학생부', '2023-01-09 00:00:00', '2023-01-09 00:00:00'),
(55, 'MORE', 'INACTIVE', '남자학생부', '2023-01-10 00:00:00', '2023-01-10 00:00:00')
;

INSERT INTO EVENTS (event_name, status, created_date, modified_date) VALUES
('접영', 'ACTIVE', '2023-01-01 00:00:00', '2023-01-01 00:00:00'),
('배영', 'INACTIVE', '2023-01-02 00:00:00', '2023-01-02 00:00:00'),
('자유형', 'ACTIVE', '2023-01-03 00:00:00', '2023-01-03 00:00:00'),
('평영', 'INACTIVE', '2023-01-04 00:00:00', '2023-01-04 00:00:00'),
('혼계영', 'ACTIVE', '2023-01-05 00:00:00', '2023-01-05 00:00:00')
;

INSERT INTO METERS (meter, created_date, modified_date) VALUES
('100M', '2023-01-01 00:00:00', '2023-01-01 00:00:00'),
('200M', '2023-01-02 00:00:00', '2023-01-02 00:00:00'),
('400M', '2023-01-03 00:00:00', '2023-01-03 00:00:00'),
('800M', '2023-01-04 00:00:00', '2023-01-04 00:00:00'),
('1500M', '2023-01-05 00:00:00', '2023-01-05 00:00:00'),
('50M', '2023-01-06 00:00:00', '2023-01-06 00:00:00'),
('1000M', '2023-01-07 00:00:00', '2023-01-07 00:00:00'),
('300M', '2023-01-08 00:00:00', '2023-01-08 00:00:00'),
('500M', '2023-01-09 00:00:00', '2023-01-09 00:00:00'),
('1200M', '2023-01-10 00:00:00', '2023-01-10 00:00:00')
;

INSERT INTO COMPETITIONS (competition_name, competition_date, competition_place,
                          competition_apply_start_date, competition_apply_end_date, competition_content,
                          competition_fee, competition_student_fee, competition_account, status, created_date, modified_date)
VALUES
    ('2021년 제1회 김해시장배 수영대회', '2021-10-28 09:00:00', '김해시 체육관', '2021-10-01 00:00:00', '2021-10-20 23:59:59', '2021년 제1회 김해시장배 수영대회', 10000, 5000, '신한은행 110-123-456789 홍길동', 'ACTIVE', '2023-11-20 00:00:00', '2023-11-20 00:00:00'),
    ('Competition 2', '2023-11-21 00:00:00', 'Place 2', '2023-11-11 00:00:00', '2023-11-16 00:00:00', 'Content 2', 120, 60, 'Bank 2, Account 2, Owner 2', 'ACTIVE', '2023-11-20 00:00:00', '2023-11-20 00:00:00'),
    ('Competition 3', '2023-11-22 00:00:00', 'Place 3', '2023-11-12 00:00:00', '2023-11-17 00:00:00', 'Content 3', 80, 40, 'Bank 3, Account 3, Owner 3', 'ACTIVE', '2023-11-20 00:00:00', '2023-11-20 00:00:00'),
    ('Competition 4', '2023-11-23 00:00:00', 'Place 4', '2023-11-13 00:00:00', '2023-11-18 00:00:00', 'Content 4', 150, 75, 'Bank 4, Account 4, Owner 4', 'ACTIVE', '2023-11-20 00:00:00', '2023-11-20 00:00:00'),
    ('Competition 5', '2023-11-24 00:00:00', 'Place 5', '2023-11-14 00:00:00', '2023-11-19 00:00:00', 'Content 5', 90, 45, 'Bank 5, Account 5, Owner 5', 'ACTIVE', '2023-11-20 00:00:00', '2023-11-20 00:00:00'),
    ('Competition 6', '2023-11-25 00:00:00', 'Place 6', '2023-11-15 00:00:00', '2023-11-20 00:00:00', 'Content 6', 110, 55, 'Bank 6, Account 6, Owner 6', 'ACTIVE', '2023-11-20 00:00:00', '2023-11-20 00:00:00'),
    ('Competition 7', '2023-11-26 00:00:00', 'Place 7', '2023-11-16 00:00:00', '2023-11-21 00:00:00', 'Content 7', 130, 65, 'Bank 7, Account 7, Owner 7', 'ACTIVE', '2023-11-20 00:00:00', '2023-11-20 00:00:00'),
    ('Competition 8', '2023-11-27 00:00:00', 'Place 8', '2023-11-17 00:00:00', '2023-11-22 00:00:00', 'Content 8', 70, 35, 'Bank 8, Account 8, Owner 8', 'ACTIVE', '2023-11-20 00:00:00', '2023-11-20 00:00:00'),
    ('Competition 9', '2023-11-28 00:00:00', 'Place 9', '2023-11-18 00:00:00', '2023-11-23 00:00:00', 'Content 9', 100, 50, 'Bank 9, Account 9, Owner 9', 'ACTIVE', '2023-11-20 00:00:00', '2023-11-20 00:00:00'),
    ('Competition 10', '2023-11-29 00:00:00', 'Place 10', '2023-11-19 00:00:00', '2023-11-24 00:00:00', 'Content 10', 120, 60, 'Bank 10, Account 10, Owner 10', 'ACTIVE', '2023-11-20 00:00:00', '2023-11-20 00:00:00')
;

INSERT INTO COMPETITION_EVENTS (event_type, department_id, event_id, meter_id, event_capacity, first_score, second_score, third_score, fourth_score, fifth_score, sixth_score, seventh_score, eighth_score, competition_id, created_date, modified_date)
VALUES
    ('ORGANIZATION_MALE', 1, 1, 1, 100, 10, 8, 6, 4, 2, 0, 0, 0, 1, '2023-11-20 00:00:00', '2023-11-20 00:00:00'),
    ('ORGANIZATION_MALE', 2, 2, 2, 50, 80, 70, 60, 50, 40, 30, 20, 10, 2, '2023-11-20 00:00:00', '2023-11-20 00:00:00'),
    ('ORGANIZATION_MALE', 3, 3, 3, 200, 70, 60, 50, 40, 30, 20, 10, 5, 3, '2023-11-20 00:00:00', '2023-11-20 00:00:00'),
    ('ORGANIZATION_MALE', 4, 4, 4, 80, 60, 50, 40, 30, 20, 10, 5, 2, 4, '2023-11-20 00:00:00', '2023-11-20 00:00:00'),
    ('ORGANIZATION_MALE', 5, 5, 5, 120, 50, 40, 30, 20, 10, 5, 2, 1, 5, '2023-11-20 00:00:00', '2023-11-20 00:00:00'),
    ('ORGANIZATION_FEMALE', 6, 1, 6, 60, 40, 30, 20, 10, 5, 2, 1, 1, 6, '2023-11-20 00:00:00', '2023-11-20 00:00:00'),
    ('ORGANIZATION_FEMALE', 7, 2, 7, 150, 30, 20, 10, 5, 2, 1, 1, 1, 7, '2023-11-20 00:00:00', '2023-11-20 00:00:00'),
    ('ORGANIZATION_FEMALE', 8, 3, 8, 30, 20, 10, 5, 2, 1, 1, 1, 1, 8, '2023-11-20 00:00:00', '2023-11-20 00:00:00'),
    ('ORGANIZATION_FEMALE', 9, 4, 9, 100, 10, 5, 2, 1, 1, 1, 1, 1, 9, '2023-11-20 00:00:00', '2023-11-20 00:00:00'),
    ('ORGANIZATION_FEMALE', 10, 5, 10, 80, 5, 2, 1, 1, 1, 1, 1, 1, 10, '2023-11-20 00:00:00', '2023-11-20 00:00:00')
;


