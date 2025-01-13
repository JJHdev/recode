CREATE TABLE EXPERIENCE (
        SEQ_CODE      NUMBER(10)        PRIMARY KEY, -- 시퀀스 코드
        TITLE         VARCHAR2(200)     NOT NULL,    -- 제목
        SUB_CONTENT   VARCHAR2(500),                -- 간략내용
        CONTENT       CLOB,                         -- 내용
        START_DATE    DATE,                         -- 시작기간
        END_DATE      DATE,                         -- 종료기간
        REGI_ID     VARCHAR2(50)      NOT NULL,    -- 등록자
        REGI_DATE     DATE             DEFAULT SYSDATE, -- 등록일
        UPDT_ID     VARCHAR2(50),                -- 수정자
        UPDT_DATE     DATE                          -- 수정일자
);

CREATE SEQUENCE experience_key_seq
    START WITH 1
    INCREMENT BY 1
    NOCACHE;

CREATE TABLE EDUCATION (
    SEQ_CODE      NUMBER(10)        PRIMARY KEY, -- 시퀀스 코드
    TITLE         VARCHAR2(200)     NOT NULL,    -- 제목
    SUB_CONTENT   VARCHAR2(500),                -- 간략내용
    CONTENT       CLOB,                         -- 내용
    START_DATE    DATE,                         -- 시작기간
    END_DATE      DATE,                         -- 종료기간
    REGI_ID     VARCHAR2(50)      NOT NULL,    -- 등록자
    REGI_DATE     DATE             DEFAULT SYSDATE, -- 등록일
    UPDT_ID     VARCHAR2(50),                -- 수정자
    UPDT_DATE     DATE                          -- 수정일자
);

CREATE SEQUENCE education_key_seq
    START WITH 1
    INCREMENT BY 1
    NOCACHE;

CREATE TABLE SKILL (
    SEQ_CODE      NUMBER(10)        PRIMARY KEY, -- 시퀀스 코드
    TITLE         VARCHAR2(200)     NOT NULL,    -- 제목
    REGI_ID     VARCHAR2(50)      NOT NULL,    -- 등록자
    REGI_DATE     DATE             DEFAULT SYSDATE, -- 등록일
    UPDT_ID     VARCHAR2(50),                -- 수정자
    UPDT_DATE     DATE                          -- 수정일자
);

CREATE SEQUENCE skill_key_seq
    START WITH 1
    INCREMENT BY 1
    NOCACHE;

CREATE TABLE LANGUAGES (
   SEQ_CODE      NUMBER(10)        PRIMARY KEY, -- 시퀀스 코드
   TITLE         VARCHAR2(200)     NOT NULL,    -- 제목
   REGI_ID     VARCHAR2(50)      NOT NULL,    -- 등록자
   REGI_DATE     DATE             DEFAULT SYSDATE, -- 등록일
   UPDT_ID     VARCHAR2(50),                -- 수정자
   UPDT_DATE     DATE                          -- 수정일자
);

CREATE SEQUENCE languages_key_seq
    START WITH 1
    INCREMENT BY 1
    NOCACHE;













CREATE TABLE RESUME (
    SEQ_CODE      NUMBER(10)        PRIMARY KEY, -- 시퀀스 코드
    RESUME_GRBUN  VARCHAR2(50)      NOT NULL,    -- 구분코드 (Experience, Education, Skills, Languages)
    TITLE         VARCHAR2(200)     NOT NULL,    -- 제목
    SUB_CONTENT   VARCHAR2(500),                -- 간략내용
    CONTENT       CLOB,                         -- 내용
    START_DATE    DATE,                         -- 시작기간
    END_DATE      DATE,                         -- 종료기간
    REGI_ID     VARCHAR2(50)      NOT NULL,    -- 등록자
    REGI_DATE     DATE             DEFAULT SYSDATE, -- 등록일
    UPDT_ID     VARCHAR2(50),                -- 수정자
    UPDT_DATE     DATE                          -- 수정일자
);

CREATE SEQUENCE resume_key_seq
    START WITH 1
    INCREMENT BY 1
NOCACHE;