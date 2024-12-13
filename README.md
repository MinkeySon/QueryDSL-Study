## QueryDSL-Study 라이브러리 공부
### 💻 개발환경
<img src="https://img.shields.io/badge/springboot-6DB33F?style=flat-square&logo=springboot&logoColor=white"/> V3.2.1


### Dependency
Springboot 3.x.x 버전부터 classifier로 jakarta'를 추가하여 Querydsl이 JakartaEE를 지원하도록 해야 한다.

```
 <dependency>
            <groupId>com.querydsl</groupId>
            <artifactId>querydsl-jpa</artifactId>
            <version>5.0.0</version>
            <classifier>jakarta</classifier>
        </dependency>
        <dependency>
            <groupId>com.querydsl</groupId>
            <artifactId>querydsl-apt</artifactId>
            <version>5.0.0</version>
            <classifier>jakarta</classifier>
        </dependency>
```
추가적인 plugin 없이 build 해도 sources 로 인식한다.

### 사용장 정의 레포지토리
QueryDSL 로 내가 원하는 구현 코드를 넣으려면 사용자 정의 리포지토리 방법을 사용해야 한다.
![QueryDSL-1](https://github.com/user-attachments/assets/6e228275-fe20-45b3-851c-0b32e6699803)

`CustomRepository` 인터페이스에 QueryDSL 로 구현할 메서드를 정의하고 `CustomRepositoryImpl` 에서 구현후 `@Service` 어노테이션으로 빈을 등록한다.
기존 `JPARepository` 에서 `CustomRepository` 를 상속하여 다른 클래스에서 의존성 주입을 통해 사용하는 방식이다.

확실히 코드 분리성이 좋아지고, QueryDSL 를 사용해 구현한 인터페이스를 상속하는 방식이라 JPA 와 QueryDSL 둘 다 사용할 수 있는 게 장점인 것 같다.

### 속도 차이?
조회에서 속도차이가 궁금하여 테스트 해보았다.
단순하게 user 더미 데이터를 150개 생성하고 
``` java
    @Test
    @DisplayName("유저 생성")
    void createUser(){
        List<Team> allTeam = teamRepository.getAllTeams();
        for(int i=1; i<=150; i++){
            int age = (int) (Math.random() * 50 + 10);
            int index = (int)(Math.random() * 3);
            User user = new User("test" + i, age, allTeam.get(index));
            userRepository.save(user);
        }
    }
```
<img width="530" alt="image" src="https://github.com/user-attachments/assets/8a33059f-fb8b-455f-a867-55c0d4ebac3a" />

이름으로 조회하는 쿼리 처리 속도를 JPA 와 QueryDSL 로 비교해 보았다.
``` java
    @Test
    @DisplayName("유저 이름으로 조회 JPA")
    void findUserByNameEqualJPA(){
        User user = userRepository.findUserByName("test79");

        System.out.println(user.toString());
    }
```
<img width="749" alt="image" src="https://github.com/user-attachments/assets/5287faaa-bbbd-4d1f-96d5-e3530cda8b6c" />

``` java
    @Test
    @DisplayName("유저 이름으로 조회 QueryDSL")
    void findUserByNameEqualQueryDSL(){
        User user = userRepository.getUserByName("test79");

        System.out.println(user.toString());
    }
```
<img width="723" alt="image" src="https://github.com/user-attachments/assets/7afdf923-f644-4565-8274-c7769e64c686" />

약 220ms 아주 미세한 차이가 난다.
QueryDSL 과 JPA 둘 다 결국 JPQL로 실행되기 때문에 성능에는 차이가 거의 없다.

하지만, QueryDSL 이 복잡한 쿼리와 작성하는 데에 도움을 줄 뿐만 아니라, 쿼리를 자바 코드로 작성할 수 있기 때문에 문법 오류를 컴파일 시점에 잡아낼 수 있기 때문에 많이 사용된다.

### 기본 문법
#### 조회
**eq** - 일치하는 데이터 조회
``` java
public User getUserByName(String name) {
        QUser quser = QUser.user;

        return jpaQueryFactory.selectFrom(quser)
                .where(quser.name.eq(name))
                .fetchOne();
    }
```
**ne** - 일치하지 않는 데이터 조회
``` java
public User getUserByName(String name) {
        QUser quser = QUser.user;

        return jpaQueryFactory.selectFrom(quser)
                .where(quser.name.eq(name))
                .fetchOne();
    }
```

**lt** - N 보다 작은 경우 데이터 조회
``` java
public List<User> getUsersByLtAge(int age) {
        QUser qUser = QUser.user;

        return jpaQueryFactory.select(qUser)
                .from(qUser)
                .where(qUser.age.lt(age))
                .fetch();
    }
```
**loe** - N 보다 작거나 같은 경우 데이터 조회
``` java
public List<User> getUsersByLoeAge(int age) {
        QUser qUser = QUser.user;

        return jpaQueryFactory.select(qUser)
                .from(qUser)
                .where(qUser.age.loe(age))
                .fetch();
    }
```
**gt** - N 보다 큰 경우 데이터 조회
``` java
public List<User> getUsersByGtAge(int age) {
        QUser qUser = QUser.user;

        return jpaQueryFactory.select(qUser)
                .from(qUser)
                .where(qUser.age.gt(age))
                .fetch();
    }
```
**goe** - N 보다 크거나 같은 경우 데이터 조회
``` java
public List<User> getUsersByGoeAge(int age) {
        QUser qUser = QUser.user;

        return jpaQueryFactory.select(qUser)
                .from(qUser)
                .where(qUser.age.goe(age))
                .fetch();
    }
```
**between** - N과 M 사이에 있는 경우 데이터 조회
``` java
public List<User> getUsersByBetweenAge(int minAge, int maxAge) {
        QUser qUser = QUser.user;

        return jpaQueryFactory.select(qUser)
                .from(qUser)
                .where(qUser.age.between(minAge,maxAge))
                .fetch();
    }
```
**in** - N 또는 M 이 있는 경우 데이터 조회
``` java
public List<User> getUsersByAorB(int firstAge, int secondAge) {
        QUser qUser = QUser.user;

        return jpaQueryFactory.select(qUser)
                .from(qUser)
                .where(qUser.age.in(firstAge,secondAge))
                .fetch();
    }
```
**notIn** - N 또는 M 이 없는 경우 데이터 조회
``` java
public List<User> getUsersByANorB(int firstAge, int secondAge) {
        QUser qUser = QUser.user;

        return jpaQueryFactory.select(qUser)
                .from(qUser)
                .where(qUser.age.notIn(firstAge,secondAge))
                .fetch();
    }
```
---
#### 정렬
**orderBy** - N 또는 M 이 없는 경우 데이터 조회
``` java
/**
오름차순
*/
public List<User> sortUsersByAgeAsc(int firstAge, int secondAge) {
        QUser qUser = QUser.user;

        return jpaQueryFactory.select(qUser)
                .from(qUser)
                .where(qUser.age.between(firstAge,secondAge))
                .orderBy(qUser.age.asc())
                .fetch();
    }
    
/**
내림차순
*/
public List<User> sortUsersByAgeDesc(int firstAge, int secondAge) {
        QUser qUser = QUser.user;

        return jpaQueryFactory.select(qUser)
                .from(qUser)
                .where(qUser.age.between(firstAge,secondAge))
                .orderBy(qUser.age.desc())
                .fetch();
    }
```

---
#### 페이징 처리
QueryDSL 5.0 버전 이전에는 `fetchResult()` 를 사용하여 페이징 결과를 받아왔다.
이때 2개의 쿼리가 작동한다.
- 전체 결과 수를 계산하는 count 쿼리.
- 현재 페이지에 해당하는 content 쿼리.

페이지를 이동할 때 전체 결과 수를 계산하는 쿼리가 불필요하게 호출된다는 뜻이다.
`PageableExecutionUtils` 의 `getPage()` 를 사용하면 필요할 때만 호출이 가능하다.

``` java
public static <T> Page<T> getPage(List<T> content, Pageable pageable, LongSupplier totalSupplier) {
	if (pageable.isUnpaged() || pageable.getOffset() == 0) {
		if (pageable.isUnpaged() || pageable.getPageSize() > content.size()) {
			return new PageImpl<>(content, pageable, content.size());
		}
		return new PageImpl<>(content, pageable, totalSupplier.getAsLong());
	}
	if (content.size() != 0 && pageable.getPageSize() > content.size()) {
		return new PageImpl<>(content, pageable, pageable.getOffset() + content.size());
	}
	return new PageImpl<>(content, pageable, totalSupplier.getAsLong());
}
```
> 첫 번째 페이지이면서 content 크기가 한 페이지의 사이즈보다 작은 경우와 offset 이 0이 아니면서, content 크기가 한 페이지의 사이즈보다 작은 경우 content.size()와 getOffset()으로 대체 된다.

``` java
 public Page<User> pageUsers(Pageable pageable) {
        QUser qUser = QUser.user;

        List<User> userList = jpaQueryFactory.select(qUser)
                .from(qUser)
                .where(qUser.age.between(20,29), qUser.team.name.eq("강한팀"))
                .orderBy(qUser.age.asc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        JPQLQuery<Long> count = jpaQueryFactory
                .select(qUser.count())
                .from(qUser)
                .where(qUser.age.between(20,29), qUser.team.name.eq("강한팀"));

        return PageableExecutionUtils.getPage(userList, pageable, count::fetchOne);
    }
```
---

#### 조인
**innerJoin**
``` java
 public List<User> getUsersJoinTeam(int firstAge, int secondAge) {
        QUser qUser = QUser.user;
        return jpaQueryFactory.select(qUser)
                .from(qUser)
                .where(qUser.age.between(firstAge, secondAge))
                .join(qUser.team)
                .fetch();
    }
```
**innerJoin**
``` java
public List<User> getUsersLeftJoinTeam(int firstAge, int secondAge) {
        QUser qUser = QUser.user;
        return jpaQueryFactory.select(qUser)
                .from(qUser)
                .where(qUser.age.between(firstAge, secondAge))
                .leftJoin(qUser.team)
                .fetch();
    }
```















