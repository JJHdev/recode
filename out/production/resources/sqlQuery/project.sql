CREATE TABLE PROJECT (
        SEQ_CODE      NUMBER(10)        PRIMARY KEY, -- 시퀀스 코드
        TITLE         VARCHAR2(200),    -- 제목
        CONTENT       CLOB,                         -- 내용
        START_DATE    DATE,                         -- 시작기간
        END_DATE      DATE,                         -- 종료기간
        FILE_NO       VARCHAR2(200),
        REGI_ID       VARCHAR2(50)      NOT NULL,    -- 등록자
        REGI_DATE     DATE             DEFAULT SYSDATE, -- 등록일
        UPDT_ID       VARCHAR2(50),                -- 수정자
        UPDT_DATE     DATE                          -- 수정일자
);

CREATE SEQUENCE project_key_seq
    START WITH 1
    INCREMENT BY 1
    NOCACHE;