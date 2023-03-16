# HelpU

### 주제 - 건강/다이어트를 위한 이커머스 애플리케이션

다이어트 식단 구독 앱으로 Healthy Food를 줄여 “헬푸”라는 이름을 선정함

### 관심사

실무와 유사한 트러블 슈팅 , 협업 방식 경험

확장성이 있는 OOP 프로젝트를 구성하는 것

지속적인 성능 이슈나 코드 수정

단위 테스트 작성

### 기술 스택

- 네이버 클라우드 플랫폼 크레딧 신청
- SpringBoot 2.7.x / JAVA 17
    - java 17 : 최근에 나온 것 중 안정적이며 가장 긴 LTS
    - StringBoot 2.7.x : 메이저 버전
- Gradle
    - Maven 보다 빨라서 선택, 팀원간 익숙함으로 인해 선택
- Mybatis 2.3 : SpringBoot 2.7
    - [https://mybatis.org/spring-boot-starter/mybatis-spring-boot-autoconfigure/](https://mybatis.org/spring-boot-starter/mybatis-spring-boot-autoconfigure/)
- MySQL 8.0
    - 5.7 보다 최대 2배 빠름, 많은 레퍼런스
- CI/CD : Jenkins에 대해서 알아가보자해서 선택

### 기술 문화

- IntelliJ, Source-Tree 를 사용
- 처음 등장하는 어노테이션은 주석으로 설명
- 멘티 간 작성한 코드 반드시 리뷰하기
- 테스트 코드 작성 및 테스트코드 커버리지 측정
- 개발 문서는 노션에 기능별로 정리하기
- 객체지향 체조 4가지
    1. 메소드 하나에 한 단계의 들여쓰기
    2. 메소드 이름을 줄이지 않는다
    3. 한 줄에 점 하나만 찍는다
    4. else 키워드를 쓰지 않는다
- GitHub-Flow 전략
    - `main` : 메인 브랜치로 배포가 이루어지는 브랜치
    - `feature` : 기능이름을 딴 브랜치, 주로 개발이 이루어짐
        - 개발이 이루어지는 브랜치
            1. PR 요청
            2. 코드리뷰
            3. 테스트 
            4. `main` 브랜치에 `merge`
            5. 배포하기
        - PR 전략 : 한 기능이 완성/수정이 되면 PR을 요청
        - 커밋 메시지 컨벤션 (한글작성)
            
            ```
            [기능] 기능이름 추가/수정/삭제
            - 뭐뭐 수정
            - 이거 추가
            - 기능 개선 등등..
            ```
            
    - `merge` 조건 : 멘토님의 승인
- 코딩스타일 컨벤션 : Google 스타일 컨벤션
- [Wiki 명세 ](https://github.com/f-lab-edu/HelpU/wiki)

훅 테스트1