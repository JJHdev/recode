CREATE USER recodeDB IDENTIFIED BY 1234;
GRANT ALL PRIVILEGES TO recodeDB;

ALTER SESSION SET CURRENT_SCHEMA = recodeDB;

CREATE TABLE users (
   USER_KEY Number PRIMARY KEY,
   USER_ID VARCHAR2(300) NOT NULL UNIQUE,
   USER_NAME VARCHAR2(50) NOT NULL,
   PASSWORD VARCHAR2(300) NOT NULL,
   GENDER CHAR(1) NOT NULL CHECK (GENDER IN ('M', 'F')),
   EMAIL VARCHAR2(200) UNIQUE,
   PROFILE_PICTURE_URL VARCHAR2(300),
   STATUS VARCHAR2(20) DEFAULT 'ACTIVE',
   CREATE_DATE DATE DEFAULT SYSDATE,
   UPDATE_DATE DATE,
   UPDATE_ID VARCHAR2(300)
);

CREATE SEQUENCE user_key_seq
    START WITH 1
    INCREMENT BY 1
    NOCACHE
    NOCYCLE;

COMMENT ON COLUMN users.USER_KEY IS '사용자의 KEY 값';
COMMENT ON COLUMN users.USER_ID IS '사용자의 아이디';
COMMENT ON COLUMN users.USER_NAME IS '사용자의 이름';
COMMENT ON COLUMN users.PASSWORD IS '사용자 계정의 비밀번호';
COMMENT ON COLUMN users.GENDER IS '사용자의 성별 (남성은 M, 여성은 F)';
COMMENT ON COLUMN users.EMAIL IS '사용자의 이메일 주소';
COMMENT ON COLUMN users.PROFILE_PICTURE_URL IS '사용자의 프로필 사진 URL';
COMMENT ON COLUMN users.STATUS IS '사용자 계정의 상태 (기본값은 ACTIVE)';
COMMENT ON COLUMN users.CREATE_DATE IS '사용자 계정 생성 날짜 (기본값은 현재 날짜)';
COMMENT ON COLUMN users.UPDATE_DATE IS '사용자 계정 마지막 수정 날짜';
COMMENT ON COLUMN users.UPDATE_ID IS '레코드를 마지막으로 수정한 사용자의 ID';







CREATE TABLE email_verification (
    email_id NUMBER PRIMARY KEY,
    email VARCHAR2(255) UNIQUE NOT NULL,
    email_status NUMBER(1) DEFAULT 0 NOT NULL,
    code VARCHAR2(6),
    expiration TIMESTAMP
);

CREATE SEQUENCE email_verification_seq START WITH 1 INCREMENT BY 1;








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





CREATE SEQUENCE token_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE token (
                       token_Id NUMBER(19) PRIMARY KEY,
                       user_Id VARCHAR2(255) NOT NULL UNIQUE,
                       token VARCHAR2(255) NOT NULL,
                       expiration TIMESTAMP
);