# 스프링부트2로 웹서비스 출시하기
https://github.com/jojoldu/freelec-springboot2-webservice

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
