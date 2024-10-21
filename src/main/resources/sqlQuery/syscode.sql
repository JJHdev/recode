DROP TABLE SYS_CODE;
DROP SEQUENCE SYS_CODE_SEQ;

CREATE TABLE SYS_CODE (
                          SEQ         NUMBER(10) PRIMARY KEY,
                          PARENT_CODE VARCHAR2(100),
                          CODE        VARCHAR2(100)  NOT NULL,
                          CODE_NM     VARCHAR2(100)  NOT NULL,
                          ODR       NUMBER(10) ,
                          USE_YN      CHAR(1) DEFAULT 'Y' NOT NULL,
                          REGI_ID     VARCHAR2(16),
                          REGI_DATE   TIMESTAMP
);

CREATE SEQUENCE SYS_CODE_SEQ START WITH 1000 INCREMENT BY 1;

insert into SYS_CODE(SEQ, PARENT_CODE, CODE, CODE_NM, ODR, USE_YN, REGI_ID, REGI_DATE) values  (1, 'EMAIL_CODE' , '@naver.com' ,'EMAIL_TYPE001', 1 , 'Y', 'admin', sysdate);
insert into SYS_CODE(SEQ, PARENT_CODE, CODE, CODE_NM, ODR, USE_YN, REGI_ID, REGI_DATE) values  (2, 'EMAIL_CODE' , '@daum.net'  , 'EMAIL_TYPE002', 2 , 'Y', 'admin', sysdate);
insert into SYS_CODE(SEQ, PARENT_CODE, CODE, CODE_NM, ODR, USE_YN, REGI_ID, REGI_DATE) values  (3, 'EMAIL_CODE' , '@gmail.com' , 'EMAIL_TYPE003', 3 , 'Y', 'admin', sysdate);
insert into SYS_CODE(SEQ, PARENT_CODE, CODE, CODE_NM, ODR, USE_YN, REGI_ID, REGI_DATE) values  (4, 'EMAIL_CODE' , '@hanmail.com', 'EMAIL_TYPE004', 4 , 'Y', 'admin', sysdate);
insert into SYS_CODE(SEQ, PARENT_CODE, CODE, CODE_NM, ODR, USE_YN, REGI_ID, REGI_DATE) values  (5, 'SEX_CODE' ,'M', '남성', 1 , 'Y', 'admin', sysdate);
insert into SYS_CODE(SEQ, PARENT_CODE, CODE, CODE_NM, ODR, USE_YN, REGI_ID, REGI_DATE) values  (6, 'SEX_CODE' ,'F', '여성', 2 , 'Y', 'admin', sysdate);

COMMENT ON COLUMN SYS_CODE.SEQ IS '시퀀스';
COMMENT ON COLUMN SYS_CODE.PARENT_CODE IS '상위코드';
COMMENT ON COLUMN SYS_CODE.CODE IS '코드';
COMMENT ON COLUMN SYS_CODE.CODE_NM IS '코드명';
COMMENT ON COLUMN SYS_CODE.ODR IS '순서';
COMMENT ON COLUMN SYS_CODE.USE_YN IS '사용여부';
COMMENT ON COLUMN SYS_CODE.REGI_ID IS '등록아이디';
COMMENT ON COLUMN SYS_CODE.REGI_DATE IS '등록일';