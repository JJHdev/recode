CREATE TABLE FILE (
      SEQ_CODE       NUMBER(10)             PRIMARY KEY,        -- 파일 ID (고유키)
      EXTERNAL_SEQ   NUMBER(10)             NOT NULL,           -- 프로젝트 테이블과 연관된 SEQ_CODE
      FILE_ORIG_NAME     VARCHAR2(255)      NOT NULL,           -- 원본 파일 이름
      FILE_COPY_NAME     VARCHAR2(255)      NOT NULL,           -- 원본 파일 이름
      FILE_PATH     VARCHAR2(500)           NOT NULL,           -- 파일 저장 경로
      FILE_SIZE     NUMBER(20),                                 -- 파일 크기 (바이트 단위)
      FILE_TYPE     VARCHAR2(100),                              -- 파일 타입 (예: image/png, application/pdf 등)
      REGI_ID       VARCHAR2(50)            NOT NULL,           -- 등록자
      REGI_DATE     DATE                    DEFAULT SYSDATE,    -- 등록일
      UPDT_ID       VARCHAR2(50),                               -- 수정자
      UPDT_DATE     DATE                                        -- 수정일자
);

CREATE SEQUENCE file_key_seq
    START WITH 1
    INCREMENT BY 1
    NOCACHE;

CREATE SEQUENCE file_external_key_seq
    START WITH 1
    INCREMENT BY 1
    NOCACHE;