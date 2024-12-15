-- =========================================
-- 등급
-- =========================================

-- 멤버 등급 삽입
INSERT INTO MEMBER_GRADE_POLICY (GRADE_NAME, MIN_AMOUNT, MAX_AMOUNT, PERIOD_DAYS, DESCRIPTION)
VALUES ('나누리', 100000, 999999999, 360, '최근 360일 동안 10만원 이상 구매 고객'),
       ('벗드리', 50000, 99999, 270, '최근 270일 동안 5만원 이상 10만원 미만 구매 고객'),
       ('다소니', 10000, 49999, 180, '최근 180일 동안 1만원 이상 5만원 미만 구매 고객'),
       ('마루한', 0, 9999, 90, '최근 90일 동안 1만원 미만 구매 고객');

INSERT INTO MEMBER_GRADE_POLICY_HISTORY (GRADE_ID, GRADE_NAME, MIN_AMOUNT, MAX_AMOUNT, PERIOD_DAYS, DESCRIPTION, OPERATION_TYPE, HISTORY_CREATED_DATE)
VALUES
    (1, '나누리', 200000, 999999999, 360, '최근 360일 동안 10만원 이상 구매 고객', 'UPDATE', CURRENT_TIMESTAMP),
    (2, '벗드리', 100000, 19999, 270, '최근 270일 동안 5만원 이상 10만원 미만 구매 고객', 'UPDATE', CURRENT_TIMESTAMP),
    (3, '다소니', 30000, 99999, 180, '최근 180일 동안 1만원 이상 5만원 미만 구매 고객', 'UPDATE', CURRENT_TIMESTAMP),
    (4, '마루한', 0, 29999, 90, '최근 90일 동안 1만원 미만 구매 고객', 'UPDATE', CURRENT_TIMESTAMP);

-- =========================================
-- 포인트 정책
-- =========================================

-- 고정 포인트 정책 삽입
INSERT INTO FIXED_POINT_POLICY (POLICY_NAME, DESCRIPTION, ACTIVATED)
VALUES
    ('가입 축하 포인트', '회원 가입을 축하하는 고정 포인트 지급 정책', TRUE),
    ('고객 생일 포인트', '고객의 생일을 기념하여 지급하는 고정 포인트 정책', TRUE),
    ('친구 추천 포인트', '친구를 추천한 고객에게 지급하는 고정 포인트 정책', TRUE),
    ('휴면 고객 재활성화 포인트', '휴면 고객의 재활성화를 위한 고정 포인트 정책', TRUE),
    ('첫 구매 포인트', '첫 구매 시 지급하는 고정 포인트 정책', TRUE);

-- FIXED_POINT_POLICY_DETAIL 테이블에 더미 데이터 삽입 (각 고정 포인트 정책의 지급 금액)
INSERT INTO FIXED_POINT_POLICY_DETAIL (FIXED_POLICY_ID, POINT_AMOUNT, EFFECTIVE_START_DATE, EFFECTIVE_END_DATE)
VALUES
    (1, 1000, '2024-01-01 00:00:00', '2024-12-31 23:59:59'),
    (2, 3000, '2024-01-01 00:00:00', '2024-12-31 23:59:59'),
    (3, 2000, '2024-01-01 00:00:00', '2024-12-31 23:59:59'),
    (4, 500, '2024-01-01 00:00:00', '2024-12-31 23:59:59'),
    (5, 1000, '2024-01-01 00:00:00', '2024-12-31 23:59:59');

-- 퍼센트 기반 포인트 정책 삽입
INSERT INTO PERCENTAGE_POINT_POLICY (MEMBER_GRADE_POLICY_ID, DESCRIPTION, ACTIVATED)
VALUES
    (1, '나누리 등급 고객의 포인트 적립 정책', TRUE),
    (2, '벗드리 등급 고객의 포인트 적립 정책', TRUE),
    (3, '다소니 고객의 포인트 적립 정책', TRUE),
    (4, '마루한 등급 고객의 포인트 적립 정책', TRUE);

-- 퍼센트 기반 포인트 정책 상세
INSERT INTO PERCENTAGE_POINT_POLICY_DETAIL (PERCENTAGE_POLICY_ID, PERCENTAGE, EFFECTIVE_START_DATE, EFFECTIVE_END_DATE)
VALUES
    (1, 5.00, '2024-01-01 00:00:00', '2024-12-31 23:59:59'),
    (2, 3.00, '2024-01-01 00:00:00', '2024-12-31 23:59:59'),
    (3, 2.00, '2024-01-01 00:00:00', '2024-12-31 23:59:59'),
    (4, 1.00, '2024-01-01 00:00:00', '2024-12-31 23:59:59');

-- =========================================
-- 약관
-- =========================================

-- 약관 메인 데이터 삽입
INSERT INTO TERMS (NAME, DESCRIPTION)
VALUES ('이용약관', '서비스 이용에 관한 일반 약관'),
       ('개인정보처리방침', '개인정보의 수집, 이용, 처리 및 보호에 관한 약관');

-- 약관 버전 데이터 삽입
INSERT INTO TERMS_VERSION (TERMS_ID, VERSION_NO, CONTENT, EFFECTIVE_START_DATE)
SELECT ID,
       1,
       '이 약관은 회사의 서비스 사용과 관련된 기본 사항을 규정합니다.',
       NOW()
FROM TERMS;

-- 서브 약관 데이터 삽입
INSERT INTO SUB_TERMS (TERMS_ID, NAME, DESCRIPTION)
SELECT ID,
       '개인정보 이용 동의',
       '서비스 이용 시 개인정보 이용에 대한 동의 조항'
FROM TERMS
WHERE NAME = '개인정보처리방침';

-- 서브 약관 버전 데이터 삽입
INSERT INTO SUB_TERMS_VERSION (SUB_TERMS_ID, VERSION_NO, CONTENT, EFFECTIVE_START_DATE)
SELECT ID,
       1,
       '서비스 제공을 위해 개인정보 이용에 동의합니다.',
       NOW()
FROM SUB_TERMS;

-- =========================================
-- 사용자 데이터
-- 애플리케이션 사용중에 입력될 데이터
-- =========================================

-- 멤버 삽입
INSERT INTO MEMBER (ID, NAME, SEX, BIRTHDATE, EMAIL, PASSWORD, ADDRESS, ADDRESS_LINE, ADDRESS_ZIP_CODE, MOBILE, LANDLINE, GRADE_ID, CURRENT_POINTS, TOTAL_EARNED_POINTS, TOTAL_REDEEMED_POINTS, MEMBER_STATUS, LATEST_LOGIN_DATE, REG_DATE)
VALUES
    (1, '홍길동', 'M', '1985-05-15', 'hong.gildong@example.com', 'password123', '서울특별시 강남구 테헤란로 123', '빌딩 5층 501호', '06123', '010-1234-5678', '02-555-1234', 4, 500, 1000, 200, 'Active', '2024-12-01 10:30:00', '2024-01-15 08:00:00'),
    (2, '김영희', 'F', '1992-08-20', 'kim.younghee@example.com', 'password456', '서울특별시 송파구 올림픽로 456', '세븐타워 14층', '05567', '010-2345-6789', '02-555-5678', 2, 300, 700, 100, 'Active', '2024-12-01 12:45:00', '2024-02-18 09:30:00'),
    (3, '이수진', 'F', '1988-11-10', 'lee.sujin@example.com', 'password789', '부산광역시 해운대구 센텀동로 789', '그랜드맨션 3층 303호', '48003', '010-3456-7890', '051-555-2345', 3, 600, 1200, 400, 'Active', '2024-12-01 14:00:00', '2024-03-20 10:15:00'),
    (4, '박철수', 'M', '1975-02-25', 'park.chulsoo@example.com', 'password101', '경기도 수원시 영통구 광교로 101', '아파트 201호', '16644', '010-4567-8901', '031-555-3456', 4, 100, 200, 50, 'Inactive', '2024-12-01 15:30:00', '2024-04-25 11:00:00'),
    (5, '정민수', 'M', '2000-06-10', 'jeong.minsu@example.com', 'password102', '대구광역시 중구 동성로 202', '상가 4층', '41922', '010-5678-9012', '053-555-6789', 1, 800, 1500, 500, 'Active', '2024-12-01 16:00:00', '2024-05-30 12:00:00');

-- 배송지 주소 삽입
INSERT INTO DELIVERY_ADDRESS (MEMBER_ID, DELIVERY_ADDRESS, DELIVERY_ADDRESS_LINE, DELIVERY_ADDRESS_ZIP_CODE, ADDRESS_TYPE, IS_PRIMARY_ADDRESS, IS_LATEST_ADDRESS)
VALUES
    (1, '서울특별시 강남구 테헤란로 123', '빌딩 5층 501호', 06123, '자택', TRUE, TRUE),
    (1, '서울특별시 강남구 역삼로 456', '빌딩 2층 202호', 06124, '직장', FALSE, FALSE),
    (2, '서울특별시 송파구 올림픽로 456', '세븐타워 14층', 05567, '자택', TRUE, TRUE),
    (2, '서울특별시 송파구 문정로 123', '건물 7층 703호', 05568, '직장', FALSE, FALSE),
    (3, '부산광역시 해운대구 센텀동로 789', '그랜드맨션 3층 303호', 48003, '자택', TRUE, TRUE);

-- 멤버 이력 삽입
INSERT INTO MEMBER_HISTORY (MEMBER_ID, NAME, EMAIL, PASSWORD, ADDRESS, MOBILE, GRADE_ID, OPERATION_TYPE, HISTORY_CREATED_DATE)
VALUES
    (1, '홍길동', 'hong.gildong@example.com', 'password123', '서울특별시 강남구 테헤란로 123', '010-1234-5678', 1, 'UPDATE', '2024-01-15 08:00:00'),
    (1, '홍길동', 'hong.gildong@example.com', 'newpassword123', '서울특별시 강남구 테헤란로 123', '010-1234-5678', 1, 'UPDATE', '2024-02-01 09:00:00'),
    (2, '김영희', 'kim.younghee@example.com', 'password456', '서울특별시 송파구 올림픽로 456', '010-2345-6789', 2, 'UPDATE', '2024-02-18 09:30:00'),
    (3, '이수진', 'lee.sujin@example.com', 'password789', '부산광역시 해운대구 센텀동로 789', '010-3456-7890', 3, 'DELETE', '2024-03-20 10:15:00'),
    (4, '박철수', 'park.chulsoo@example.com', 'password101', '경기도 수원시 영통구 광교로 101', '010-4567-8901', 4, 'DELETE', '2024-04-25 11:00:00');
