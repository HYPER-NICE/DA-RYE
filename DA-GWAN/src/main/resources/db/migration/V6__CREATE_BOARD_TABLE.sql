-- =========================================
-- 4. 게시판 관련 코드 테이블
-- =========================================


CREATE TABLE BOARD_CATEGORY_CODE
(
    -- 기본키
    ID                   BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '게시판 카테고리 코드 고유 ID (기본 키)',

    -- 대체 키
    ROOT_CATEGORY        BIGINT  NOT NULL COMMENT '부모 카테고리 코드',
    SUB_CATEGORY         BIGINT  NOT NULL COMMENT '서브 카테고리 코드',
    UNIQUE KEY (ROOT_CATEGORY, SUB_CATEGORY),

    -- 데이터 컬럼
    ROOT_NAME                 VARCHAR(100) NOT NULL COMMENT '루트 카테고리 명칭(예: 공지사항, FAQ, 1대1 문의)',
    ROOT_DESCRIPTION          VARCHAR(255) NULL COMMENT '루트 카테고리에 대한 상세 설명',
    SUB_NAME                  VARCHAR(100) NOT NULL COMMENT '서브 카테고리 명칭(예: 전체, 배송, 교환, 환불)',
    SUB_DESCRIPTION           VARCHAR(255) NULL COMMENT '서브 카테고리에 대한 상세 설명',

    -- 시스템 관리 컬럼
    CREATED_DATE         DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '레코드 생성 날짜 및 시간',
    LAST_MODIFIED_DATE   DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '마지막 수정된 날짜 및 시간',
    LAST_MODIFIED_MEMBER BIGINT       NULL COMMENT '레코드를 마지막으로 수정한 회원 ID',
    DELETED_DATE         DATETIME(6) DEFAULT NULL COMMENT '레코드가 삭제된 날짜 (논리 삭제)',
    CONSTRAINT FK_BOARD_CATEGORY_LAST_MODIFIED_MEMBER FOREIGN KEY (LAST_MODIFIED_MEMBER) REFERENCES MEMBER (ID) ON UPDATE CASCADE ON DELETE RESTRICT

)
    COMMENT = '게시판 유형 코드 테이블로, 다양한 게시판 유형을 관리하기 위한 테이블입니다.';



CREATE TABLE BOARD
(
    -- 기본키
    ID                   BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '게시판 고유 ID (기본 키)',

    -- 외래 키
    PARENT_ID            BIGINT         NULL COMMENT '부모 게시판 ID (외래 키)',
    CATEGORY_ID          BIGINT         NOT NULL COMMENT '게시판 카테고리 ID (외래 키)',
    WRITER_ID            BIGINT         NOT NULL COMMENT '작성자 ID (외래 키)',
    CONSTRAINT FK_BOARD_PARENT_ID FOREIGN KEY (PARENT_ID) REFERENCES BOARD (ID),
    CONSTRAINT FK_BOARD_CATEGORY_ID FOREIGN KEY (CATEGORY_ID) REFERENCES BOARD_CATEGORY_CODE (ID),
    CONSTRAINT FK_BOARD_WRITER_ID FOREIGN KEY (WRITER_ID) REFERENCES MEMBER (ID),

    -- 데이터
    TITLE                 VARCHAR(1000) NOT NULL COMMENT '게시글 제목',
    CONTENT               TEXT          NOT NULL COMMENT '게시글 내용',
    FIXED                 BOOLEAN       NOT NULL DEFAULT FALSE COMMENT '게시글 상단 고정 여부',
    REG_DATE              DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '게시글 작성 날짜',
    CHECK (LENGTH(TITLE) > 0),
    CHECK (LENGTH(CONTENT) > 0),

    -- 시스템 관리 컬럼
    CREATED_DATE         DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '레코드 생성 날짜 및 시간',
    LAST_MODIFIED_DATE   DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '마지막 수정된 날짜 및 시간',
    LAST_MODIFIED_MEMBER BIGINT       NULL COMMENT '레코드를 마지막으로 수정한 회원 ID',
    DELETED_DATE         DATETIME(6) DEFAULT NULL COMMENT '레코드가 삭제된 날짜 (논리 삭제)',
    CONSTRAINT FK_BOARD_MOD_MEMBER FOREIGN KEY (LAST_MODIFIED_MEMBER) REFERENCES MEMBER (ID) ON UPDATE CASCADE ON DELETE RESTRICT
);


CREATE TABLE BOARD_IMAGE
(
    -- 기본키
    ID                   BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '게시글 이미지 고유 ID (기본 키)',

    -- 외래 키
    BOARD_ID            BIGINT         NOT NULL COMMENT '게시글 ID (외래 키)',
    CONSTRAINT FK_BOARD_IMAGE_BOARD_ID FOREIGN KEY (BOARD_ID) REFERENCES BOARD (ID),

    -- 데이터
    IMAGE                BLOB          NOT NULL COMMENT '게시글 이미지',

    -- 시스템 관리 컬럼
    CREATED_DATE         DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '레코드 생성 날짜 및 시간',
    LAST_MODIFIED_DATE   DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '마지막 수정된 날짜 및 시간',
    LAST_MODIFIED_MEMBER BIGINT       NULL COMMENT '레코드를 마지막으로 수정한 회원 ID',
    DELETED_DATE         DATETIME(6) DEFAULT NULL COMMENT '레코드가 삭제된 날짜 (논리 삭제)'
)
    COMMENT = '게시글 이미지 테이블로, 게시글에 등록된 이미지를 관리하기 위한 테이블입니다.';
