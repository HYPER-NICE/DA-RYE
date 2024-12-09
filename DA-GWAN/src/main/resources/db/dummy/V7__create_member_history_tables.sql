-- V7__create_member_history_tables.sql
-- 생성 날짜: 2024-12-10
-- 설명: 멤버 정보 이력 테이블 및 히스토리 관리 트리거 추가
--       회원 정보 이력은 멤버 정보의 UPDATE, DELETE 시점에 이전 상태의 데이터를 저장

-- =======================
-- 멤버 이력 테이블 생성
-- =======================


-- member_history 테이블에 참조 무결성을 강제할 필요가 있다면 외래 키 추가 가능(선택 사항)
-- 예: ALTER TABLE member_history
--       ADD CONSTRAINT FK_member_history_member FOREIGN KEY (member_id) REFERENCES member (id) ON DELETE NO ACTION;

-- =======================
-- 트리거 생성
-- =======================

DELIMITER $$

-- 멤버 정보 UPDATE 전 이력 기록 트리거
-- 기존 데이터를 member_history에 남기고 UPDATE 처리
CREATE TRIGGER trg_member_before_update
    BEFORE UPDATE ON member
    FOR EACH ROW
BEGIN
    INSERT INTO member_history (
        member_id, name, email, password, address, mobile, grade_id,
        created_date, last_modified_date, deleted_date, operation_type
    ) VALUES (
                 OLD.id, OLD.name, OLD.email, OLD.password, OLD.address, OLD.mobile, OLD.grade_id,
                 OLD.created_date, OLD.last_modified_date, OLD.deleted_date, 'UPDATE'
             );
END$$

-- 멤버 정보 DELETE 전 이력 기록 트리거
-- 삭제되기 전 기존 데이터를 member_history에 남기고 DELETE 처리
CREATE TRIGGER trg_member_before_delete
    BEFORE DELETE ON member
    FOR EACH ROW
BEGIN
    INSERT INTO member_history (
        member_id, name, email, password, address, mobile, grade_id,
        created_date, last_modified_date, deleted_date, operation_type
    ) VALUES (
                 OLD.id, OLD.name, OLD.email, OLD.password, OLD.address, OLD.mobile, OLD.grade_id,
                 OLD.created_date, OLD.last_modified_date, OLD.deleted_date, 'DELETE'
             );
END$$

DELIMITER ;
