# 스프링부트로 웹서비스 출시하기
https://github.com/jojoldu/freelec-springboot2-webservice

## 기술 스택
- Java 11
- Springboot 2.6.2
- MaridDB
- JPA
- Thymeleaf
- OAuth2.0
- AWS

## 구현 사항
- JPA로 구현한 CRUD
- Spring Security와 OAuth2.0을 이용한 소셜 로그인
- AWS를 이용한 CI/CD 및 무중단 배포

## 책과의 차이점
- Java 8에서 Java 11로 버전업
- Mustache 대신 Thymeleaf 사용
- Travis-ci의 유료화로 Github Action 사용

## Builder Pattern
- 객체 생성 시 사용하는 패턴
- 생성자에 비해 유연하게 객체 생성에 필요한 파라미터를 조절할 수 있다.
- 매개변수가 많아지면 떨어지는, 가독성을 높일 수 있다.
- @Builder로 쉽게 구현할 수 있다.
```JAVA
public class Posts{
    .
    .
    .
    @Builder
    public Posts(Long id, String title, String content, String author) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
    }
}
```

## DTO vs Entity
- Entity는 DB table에 1대1로 맵핑되는 객체이다.
- DTO는 계층 간의 데이터 전송을 위해 사용하는 객체이다. 보통 getter, setter를 가진다.
- Entity에서 직접 추출해 사용하지 않고 DTO를 따로 만들어서 사용하는데 그 이유는 DB와 서비스단을 분리하기 위함이다.
- DB나 서비스단 중 어느 하나에 변경이 일어나면 양쪽 모두에게 영향을 줄 수 있으므로 이를 피하기 위해 DTO를 이용한다.

## Service Layer, Domain model
- Spring MVC는 Web, Service, Repository Layer로 구분된다.
- 여기서 Service Layer는 직접 비지니스 로직을 처리하지 않는다. 트랜잭션과 도메인 간의 순서를 보장하는 역할만 한다.
- 비지니스 로직은 Domain이 담당한다.
- 도메인은 해당 서비스의 기능을 제공하는 곳, 소프트웨어로 해결하고자하는 문제 영역이다.
- 택시 앱을 개발한다고 했을 때, 배차, 탑승, 요금 등이 모두 도메인이 될 수 있다.
- 이 도메인들이 어떻게 관계를 맺는지 이해할 수 있도록 단순화시킨 것이 도메인 모델이다.

## JPA Auditing
- 엔티티에는 보통 생성시간, 수정시간을 공통으로 포함하고 있다.
- 매번 삽입, 수정마다 반복되는 코드가 사용되는 것을 JPA Auditing를 통해 해결할 수 있다.
```JAVA
//모든 entity의 상위 클래스
//createDate, modifiedDate 자동관리
@Getter
@MappedSuperclass //createdDate, modifiedDate도 칼럼으로 인식
@EntityListeners(AuditingEntityListener.class) //Audit, 감시 기능
public abstract class BaseTimeEntity {
    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;
}
```

## @Target, @Retention
- Meta Annotation 중 하나이다.
- @Controller 내부에 @Component가 존재하듯이 어노테이션에서 동작하는 어노테이션을 meta annotation이라 한다.
- @Target은 어노테이션이 생성될 수 있는 위치를 지정할 수 있다.
```JAVA
@Target()
ElementType.PACKAGE // 패키지 선언
ElementType.TYPE // 타입 선언
ElementType.ANNOTATION_TYPE // 어노테이션 타입 선언
ElementType.CONSTRUCTOR // 생성자 선언
ElementType.FIELD // 멤버 변수 선언
ElementType.LOCAL_VARIABLE // 지역 변수 선언
ElementType.METHOD // 메서드 선언
ElementType.PARAMETER // 전달인자 선언
ElementType.TYPE_PARAMETER // 전달인자 타입 선언
ElementType.TYPE_USE // 타입 선언
```
- @Retetion은 어노테이션이 적용되는 범위를 지정할 수 있다.
```JAVA
@Retention()
RetentionPolicy.RUNTIME // 컴파일 후에도 계속 참조 가능
RetentionPolicy.CLASS // 컴파일러가 클래스를 참조할 때까지 유효
RetentionPolicy.SOURCE // 컴파일 전까지만 유효, lombok의 getter, setter가 해당
```

## OAuth2.0
- OAuth이란 각종 웹, 모바일 어플리케이션에서 타사의 API를 사용하고 싶을 때 권한 획득을 위한 프로토콜(Protocol)이다.
- 로그인을 구현하는 과정은 복잡하다. 로그인 보안, 이메일·전화번호 인증, 아이디 찾기, 비밀번호 찾기·변경 등을 구현해야 한다.
- OAuth2.0을 사용하면 위의 것들을 구글, 페이스북, 네이버 등에 위임할 수 있다.
### OAuth 참여자
- Resource Server 
    - Google, twitter 등이 속하는 영역으로 사용하고자 하는 자원을 제어하는 곳이다..
- Resource Owner
    - 자원을 가지고 있는 곳으로 로그인하는 유저가 이곳에 속한다.
- Client
    - Resource Server에서 제공하는 자원을 이용하는 어플리케이션이다.
### 등록
- 클라이언트가 리소스 서버를 이용하기 위해선 클라이언트 별로 등록이 필요하다.
- 등록을 통해 클라이언트 ID, 클라이언트 secret이 생성되고 authorized redirect URL를 지정할 수 있다.
- 클라이언트 ID, 클라이언트 secret을 통해 클라이언트를 구분하고 인증 정보를 authorized redirect URL로 보낸다.
### 승인
- 리소스 오너가 클라이언트에 접근하면 클라이언트는 리소스 오너의 자원이 필요하므로 리소스 서버를 통해 인증을 한다.(클라이언트에서 구글 로그인 창을 띄워 인증)
- 리소스 서버에서 인증에 성공하면 클라이언트의 클라이언트 ID와 redirect URL이 리소스 서버의 것과 같은지 확인한다.
- 같다면 리소스 서버의 기능을 클라이언트에 사용할 지 리소스 오너에 요청한다.
- 요청을 허락한다면 authorization code라는 임시 비밀번호를 생성한다.
- 리소스 오너는 이 코드를 가지고 클라이언트로 이동한다.
- 클라이언트는 클라이언트 ID, secret, authorization code 등이 모두 일치하는 정보가 있는지 확인한다.
### Access Token
- 일치하면 이제 authorization code는 지워지고 리소스 서버는 access token을 발급한다.
- 리소스 서버는 access token을 보고 어떤 기능을 제공해야 하는지 판단한다.
### API
- api를 이용해 리소스 서버의 기능을 이용할 수 있다.
### Refresh Token
- Access token은 유효 기간이 있다. Access token이 만료되면 리소스 서버(uthorization server)에 refresh token을 이용해 요청을 하면 새 access token을 받을 수 있다.
- Refresh token은 보통 처음 access token을 받을 때 같이 생성된다.

## AWS
- 서버를 24시간 작동시키기 위한 3가지 선택지가 존재한다.
    - PC를 24시간 구동
    - 호스팅 서비스 이용
    - 클라우드 서비스 이용
- PC나 호스팅 서비스가 더 저렴하나 사용자가 특정 시간에 몰릴 수 있다면 사양을 유동적을 조절할 수 있는 클라우드가 유리하다.
- AWS를 사용하는 이유는 1년간 서비스가 무료이고 모니터링, 로그관리 등 지원 기능이 많으며, 많은 기업들이 AWS를 사용하기 때문이다.
### EC2
- AWS에서 성능, 용량 등을 유동적으로 사용할 수 있도록 제공하는 서버이다.
- EC2란 24시간 구동 가능한 컴퓨터 한 대를 사용할 수 있게 된다.
### RDS
- AWS에서 지원하는 클라우드 기반 관계형 데이터베이스이다.
- EC2에 직접 DB를 설치할 수도 있다. 이 경우 돈을 절약할 수 있지만 DB의 설정, 운영, 백업 등의 기능을 직접 구현해야하는 불편함이 존재한다.
### S3
- 파일 저장소의 역할을 하는 서비스이다. 일반적인 파일서버는 트래픽이 증가함에 따라서 장비를 증설하는 작업을 해야 하는데 S3는 이와 같은 것을 대행한다.
### CodeDeploy
- CD를 제공하는 AWS 서비스이다.
- CodeDeploy는 저장 기능이 없으므로 S3에 저장한 빌드한 결과물을 가져와 배포를 한다.

## CI/CD
- Continuous Integration/Continuous Deploy의 약자이다.
### CI
- 각자가 작업한 결과물을 한 번에 합치는 것은 시간도 많이 걸리고 한꺼번에 오류가 발생하기 쉽다.
- 뼈대에 살점을 하나씩 붙이듯 새로운 코드를 지속적으로 통합하고 테스트하여 완성해 나가는 방식의 개발 방법론이다.
### CD
- 서비스의 사용자는 최대한 빠른 시간 내에 최신 버전을 제공받을 필요가 있다.
- 소프트웨어가 항상 신뢰 가능한 수준에서 배포될 수 있도록 지속적으로 관리하자는 개념으로 CI의 연장선에 있다고 할 수 있다.


- AWS와 CI툴로 travis-ci를 이용하면 다음과 같은 구조로 CI/CD의 자동화가 이뤄진다. 
![image](https://user-images.githubusercontent.com/63232876/148563001-cc9dd2f6-a53e-4628-afd0-6cee01ba7ac9.png)

출처: https://devlog-wjdrbs96.tistory.com/m/291


    
