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