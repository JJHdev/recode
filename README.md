# recode
myRecodeProject
</br>
 1. 프로젝트 생성 (구현 생각)
    1. 지금까지 익혀온 Spring기술들을 활용하여 홈페이지 만들며 내것으로 만들기
    2. 로그인한 사용자의 개인 추억을 보관하는 홈페이지의 컨셉으로 만들 예정</br>
    
 2. CI-CD 파이프라인 구축
    1. 기존 GitAuction으로 구축 한 경험이 있어, 이번엔 JenKins로 구축 설정
    2. Jenkins설치 > 환경변수 설정 > 접속 IP변경 > git 연동
       3. JenKins는 git에서 연동시 해당 IP주소로 접근이 가능하여야 해서, ngrok를 이용해서 접근 허용하도록 설정</br>
        -> ngrok config add-aythtoken [토큰번호-ngrok로그인 후 얻는 토큰번호]</br>
        -> ngrok http [로컬포트번호]</br>
    3. jenkins에서 메모리를 gitAuction보다 많이 잡아먹는 이슈가 생겨 gitAution으로 CI-CD 파이프라인 구축하기로 변경

3. docker 구축
    1. 로컬서버 환경
        1. JAVA17, gradle, jar, 스프링부트, oracle, 내장톰캣, 윈도우OS
        2. JVM메모리(-Xms512m -Xmx1024m)

    2. 운영서버 환경
        1. JAVA17, gralde, jar, 스프링부트, oracle, nginx, redhat
        2. JVM메모리(-Xms512m -Xmx1024m)
        3. 리버스 프록시 : Nginx (80포트) -> 애플리케이션(8080포트)

    3. 프로세스
       1. 프로젝트를 수정한다.
       2. git에 커밋 한다.
       3. git에서 파일 업로드 후 gitauction에서 인식하고 CI를 수행한다
       4. 각 환경에 따라 appliaction.yml을 만든다.
       5. dockerfile 내용에 맞게 jar파일을 경량화 해서 dockerhub에 업로드 된다.
       6. docker-compose up을 실행하여 jar이미지와 DB 이미지를 로컬로 pull 해서 컨테이너에 올린다.

