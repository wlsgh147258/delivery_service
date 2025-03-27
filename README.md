# 프로젝트 기획서


## 1. 개요

- 음식 주문 및 배달 플랫폼
- 2025.03.24 ~ 2025.03.27

## 팀원 구성

| 김지원 | 김현지 | 도진호 | 이도현 |
|---|---|---|---|
| <img src="https://github.com/user-attachments/assets/7fe9ff91-7225-48d3-acc6-d6056b54f1ba" width="150"> | <img src="https://github.com/user-attachments/assets/60190558-2e0a-41fe-844a-698dc2c60ba0" width="150"> | <img src="https://github.com/user-attachments/assets/900f816a-865a-42ac-a3da-b14cca4108d4" width="150"> | <img src="https://github.com/user-attachments/assets/76c1081a-a151-429c-b59d-bc56b4745a89" width="150"> |
| [GitHub](https://github.com/kimjiwon0450) | [GitHub](https://github.com/vbnmopas) | [GitHub](https://github.com/wlsgh147258) | [GitHub](https://github.com/ehgus8) |

### 1.1 목적 및 배경

본 프로젝트의 목적은 기존 배달앱 서비스의 이용 주체를 고객 뿐만이 아닌 
배달원과 점주까지 포함하여 서비스하고자 하는 것입니다.
해당 주제를 선정한 이유는 직전주에 강사님의 도움을 받아 실습한 [비디오 콘솔 프로그램]과 유사하지만
보다 규모가 있어 다뤄야 할 부분도 다양할 것이라고 판단했고
그만큼 그동안 학습한 Java, JDBC, SQL를 효과적으로 익힐 수 있는 기회라고 생각했습니다.
무엇보다 배달 서비스는 현재 우리 삶에 깊게 침투되어 있는만큼 흥미로운 주제였기 때문에
첫번째 프로젝트 주제로 선정하게 되었습니다.


### 개발 환경

- 프로그래밍 언어: Java (Java SE 8 이상)

- 데이터베이스: Oracle Database 

- 개발 도구: IntelliJ IDEA(Java 개발 환경 및 IDE)

- 데이터베이스 관리 및 쿼리 도구: ERDCloud, Oracle SQL Developer  

- 형상 관리 툴: Git, GitHub


### 주요 기능

Java 개발:

- 애플리케이션 구조 설계: 
    - 요구사항 분석을 바탕으로 애플리케이션의 전반적인 구조를 설계하고, 각 기능을 담당하는 패키지와 클래스를 구성했습니다.

- JDBC를 이용한 데이터베이스 연동: 
    - Java Database Connectivity (JDBC)를 사용하여 Oracle Database와 연결하고, SQL 쿼리를 통해 데이터를 CRUD (Create, Read, Update, Delete) 작업을 수행하는 기능을 구현했습니다.

- 콘솔 기반 사용자 인터페이스 (UI) 구현:   
    - 사용자와의 상호 작용을 위한 기본적인 메뉴 및 입력/출력 기능을 콘솔 환경에서 구현했습니다.

- 서비스 계층 구현: 
    - 비즈니스 로직을 처리하는 서비스 클래스를 구현하여, 데이터 접근 계층과 UI 계층을 분리하고, 각 기능의 역할을 명확히 했습니다.

- 예외 처리: 
    - 프로그램 실행 중 발생할 수 있는 예외 상황을 처리하기 위한 예외 처리 코드를 작성하여 안정성을 확보했습니다.

- 데이터베이스 (Oracle Database) 활용:

    - 데이터베이스 스키마 설계: 
        - 음식 주문, 메뉴 정보, 사용자 정보 등을 저장하기 위한 데이터베이스 테이블을 설계했습니다.

    - SQL 쿼리 작성: 
        - 데이터베이스에서 데이터를 조회, 삽입, 수정, 삭제하기 위한 다양한 SQL 쿼리를 작성하고 실행했습니다.

    - 데이터 관리: 
        - Oracle SQL Developer 등의 도구를 활용하여 데이터베이스의 테이블 구조를 확인하고, 데이터를 관리했습니다.

구현 서비스:

본 프로젝트에서는 다음과 같은 주요 서비스를 구현했습니다.

- 사용자 관리 서비스: 
    - 사용자의 회원 가입, 로그인, 정보 조회 등의 기능을 구현했습니다.
    - 로그인 후 사용자 타입(고객/라이더/점주)에 따라 다른 UI가 실행되도록 구현했습니다. 

- 메뉴 관리 서비스: 
    - 음식점의 메뉴를 추가, 조회, 수정, 삭제하는 기능을 구현했습니다.
    
- 주문 서비스:  
    - 사용자가 메뉴를 선택하고 주문을 처리하는 기능을 구현했습니다.

- 주문 조회 서비스: 
    - 사용자가 자신의 주문 내역을 조회하는 기능을 구현했습니다.
      
- 리뷰 서비스:
   - 사용자가 자신의 리뷰를 작성, 조회, 삭제하는 기능을 구현 했습니다.
   - 점주가 자신이 운영하는 가게에 리뷰를 조회할 수 있는 기능을 구현 했습니다.
     
### WBS (Work Breakdown Structure)

<img width="1136" alt="image" src="https://github.com/user-attachments/assets/b28884bb-31f5-4907-9f8b-1490b506e5f0" />

 

### 트러블 슈팅 및 한계점
- 프로그램의 order 로직에 오류가 있어 예상과 다른 결과를 출력하거나 오류를 발생시켰습니다. 그래서 디버깅 도구를 사용하여 코드의 실행 과정을 추적하고, 오류가 발생하는 지점을 찾아 수정했습니다. 또한, 테스트 케이스를 추가하여 각 기능이 올바르게 작동하는지 확인했습니다.

- 사용자가 잘못된 형식의 데이터를 입력하여 프로그램이 예외를 발생시키거나 예상치 못한 동작을 했습니다. 그래서 사용자 입력 값에 대한 유효성 검사를 추가하고, 잘못된 입력이 들어올 경우 사용자에게 적절한 오류 메시지를 표시하도록 수정했습니다.

### 한계점 
- 주문 당 메뉴 1개만 지정할 수 있기 때문에 메뉴를 여러 개 담고 싶으면 일일이 입력해야 합니다.
  
- 현재 프로젝트는 특정 데이터베이스에 종속되어 있습니다. 데이터베이스 추상화 레이어를 도입하여 다양한 데이터베이스를 지원하도록 확장해야 합니다.

# 요구사항 명세서
![{44AD827E-B619-4045-95D8-871E5B90148C}](https://github.com/user-attachments/assets/92e50625-58ba-4bd7-9986-c9e4493f3518)

![{72C242BB-ECCE-4E81-91E3-55CD33A0C97C}](https://github.com/user-attachments/assets/ccd392f5-7132-49cf-838b-0195c2be33eb)

![{5F8829E5-269B-466D-B166-70B6659B12E3}](https://github.com/user-attachments/assets/23a3587f-2bb1-44c5-975f-75bc7d05cd89)

![{5797BE62-F7E0-4371-A92F-0A88F5C35DB3}](https://github.com/user-attachments/assets/4cedb317-60f8-402d-ab1b-c14863b751e0)

![{718E90E6-0576-44E7-A120-049B5AFD5EEA}](https://github.com/user-attachments/assets/607e41a4-d2a5-47cd-846c-c17e54532136)



# ERD
![delivery-ERD (1)](https://github.com/user-attachments/assets/36246c16-97e3-4b61-84f6-bfc2fec52cce)





# 화면 명세서와 스토리 보드

## 화면 명세서 및 스토리보드 (콘솔 기반)

### 1. 개요

* 본 시스템은 콘솔 환경에서 실행되는 음식 배달 시스템입니다.
* 사용자는 텍스트 기반의 메뉴와 출력을 통해 시스템과 상호작용합니다.
* 본 명세서는 사용자 경험을 최적화하기 위한 메뉴 구성, 출력 형식, 사용자 입력 처리 방식을 정의합니다.

### 2. 메뉴 구성

#### 2.1. 메인 메뉴
![{A435C69C-0192-41C3-ADF3-E523C2B46B62}](https://github.com/user-attachments/assets/3db3c291-bde4-4c40-9e73-e56f735dfe1e)

* **설명:** 시스템 시작 시 사용자에게 로그인 또는 회원가입 옵션을 제공합니다.
* **입력 처리:** 1 또는 2를 입력받아 해당 기능으로 이동합니다.

#### 2.2. 사용자 유형 선택 메뉴 (로그인 성공 시)
![{6671D2E1-6956-4BD6-BCE5-22F2E189E250}](https://github.com/user-attachments/assets/e31bcb2c-8e21-4ebc-b061-4ddde566fdfb)
* **설명:** 로그인한 사용자의 유형에 따라 이용 가능한 서비스를 제공합니다.
* **입력 처리:** 1, 2, 또는 3을 입력받아 해당 서비스 또는 프로그램 종료를 실행합니다.

#### 2.3. 식당 서비스 메뉴 (점주 로그인 시)
![{C10BB125-9A1A-403F-A912-B94866F71FAF}](https://github.com/user-attachments/assets/289e1c96-06d0-472e-8e92-772127d2396e)

* **설명:** 점주에게 식당 관리 및 리뷰 확인 서비스를 제공합니다.
* **입력 처리:** 1, 2, 또는 3을 입력받아 해당 서비스 또는 프로그램 종료를 실행합니다.

#### 2.4. 라이더 서비스 메뉴 (라이더 로그인 시)
![{EFE136EC-5869-4BA2-BAC3-03D2DFB29590}](https://github.com/user-attachments/assets/b9135390-8ce6-422f-9e71-f508c1be0479)

* **설명:** 라이더에게 라이더 관리 서비스를 제공합니다.
* **입력 처리:** 1, 또는 2을 입력받아 해당 서비스 또는 프로그램 종료를 실행합니다.

#### 2.5. 각 서비스별 하위 메뉴

* 각 서비스(사용자 관리, 주문, 식당, 라이더, 리뷰) 내에서 필요한 하위 메뉴를 구성합니다.
* 하위 메뉴는 사용자에게 필요한 기능을 명확하게 제시하고, 직관적인 탐색을 가능하게 합니다.

### 3. 출력 형식

#### 3.1. 사용자 관리 시스템 출력
![{1F42518B-9624-4E97-94DE-59AA5E31387E}](https://github.com/user-attachments/assets/4ac96c1c-64a1-45c7-a641-90e95ae11de3)

#### 3.2. 주문 관리 시스템 출력
![{D5F53A0A-DA62-4689-9A5C-314F8CFE7FFD}](https://github.com/user-attachments/assets/8aa2d227-6e28-41b4-bed4-59a0bab16a00)

#### 3.3. 식당 관리 시스템 출력
![{F83260EF-E590-4760-9201-E30C87C83E7D}](https://github.com/user-attachments/assets/147c9d69-fb05-4d6e-9d6d-e3c48ceef782)

#### 3.3. 식당 리뷰 확인 시스템 출력 // 해야됨

#### 3.4. 라이더 시스템 출력
![{671F58D6-779B-4BEB-843A-CCAD73BC4B3C}](https://github.com/user-attachments/assets/4c2ba8f5-aa05-4284-a8c7-d579fd9d2f64)


# MSA 설계서

### 시스템 개요

시스템은 다음과 같은 마이크로서비스로 구성됩니다.

* **사용자 서비스 (User Service):** 사용자 계정 관리, 인증, 권한 관리
* **식당 서비스 (Restaurant Service):** 식당 정보 관리, 메뉴 관리
* **주문 서비스 (Order Service):** 주문 생성, 관리, 결제 처리
* **배달 서비스 (Delivery Service):** 배달원 관리, 배달 상태 관리
* **리뷰 서비스 (Review Service):** 리뷰 생성, 관리, 조회
* **사용자 인터페이스 서비스 (UI Service):** 사용자 인터페이스 제공

### 1. 서비스 정의

#### 1.1. 사용자 서비스 (User Service)

* **역할:** 사용자 계정 관리, 인증, 권한 관리
* **책임:**
    * 사용자 등록, 수정, 삭제
    * 로그인, 로그아웃
* **구성 요소:**
    * `UserService.java`: 사용자 계정 관련 비즈니스 로직 처리
    * `UserRepository.java`: 사용자 데이터 접근

#### 1.2. 식당 서비스 (Restaurant Service)

* **역할:** 식당 정보 관리, 메뉴 관리
* **책임:**
    * 식당 등록, 수정, 삭제
    * 메뉴 등록, 수정, 삭제
    * 식당 검색, 메뉴 검색
* **구성 요소:**
    * `RestaurantService.java`: 식당 관련 비즈니스 로직 처리
    * `RestaurantRepository.java`: 식당 데이터 접근
    * `MenuService.java`: 메뉴 관련 비즈니스 로직 처리
    * `MenuRepository.java`: 메뉴 데이터 접근

#### 1.3. 주문 서비스 (Order Service)

* **역할:** 주문 생성, 관리, 결제 처리
* **책임:**
    * 주문 생성, 취소, 조회
    * 결제 처리
    * 주문 상태 관리
* **구성 요소:**
    * `OrderService.java`: 주문 관련 비즈니스 로직 처리
    * `OrderRepository.java`: 주문 데이터 접근
    * `PaymentService.java`: 결제 처리

#### 1.4. 배달 서비스 (Delivery Service)

* **역할:** 배달원 관리, 배달 상태 관리
* **책임:**
    * 배달원 등록, 관리
    * 배달 상태 관리
* **구성 요소:**
    * `DeliveryService.java`: 배달 관련 비즈니스 로직 처리
    * `DeliveryRepository.java`: 배달 데이터 접근

#### 1.5. 리뷰 서비스 (Review Service)

* **역할:** 리뷰 생성, 관리, 조회
* **책임:**
    * 리뷰 생성, 수정, 삭제
    * 리뷰 조회
    * 리뷰 평점 관리
* **구성 요소:**
    * `ReviewService.java`: 리뷰 관련 비즈니스 로직 처리
    * `ReviewRepository.java`: 리뷰 데이터 접근

### 데이터 액세스 서비스 (DAO Service) // 고쳐야함

- 역할: 데이터베이스와의 모든 상호작용 담당
- 책임:
    - SQL 쿼리 실행
    - 데이터 매핑 (DB ↔ 객체)
    - CRUD 작업 수행


- 구성요소:
    - UserDAO.java: 사용자 데이터 액세스
-    ProductDAO.java: 상품 데이터 액세스
-    OrderDAO.java: 주문 데이터 액세스



### 데이터베이스 서비스 (DB Service)

- 역할: 데이터베이스 연결 관리
- 책임:
    - 커넥션 풀 관리
    - 트랜잭션 지원
    - 데이터베이스 설정

### 회고

- 도진호
    - 이번 프로젝트를 하게 되면서 아직 많이 부족하다는 것을 알게 되었다. 첫 번째로 github 사용법을 다시 공부를 해야겠고, 두 번째로 요구사항에 부합하는 로직을 짜기 위해 알고리즘 공부도 꾸준히 해야겠다고 생각이 들었다. 그래도 팀원 모두가 열심히 해 준 덕분에 프로젝트가 성공적으로 마쳤다고 생각한다. 처음엔 소통도 잘 안되었지만, 하루에 두 번씩 정기적인 회의를 거치면서 다듬어졌다고 생각한다. 우리 프로젝트에 좋았던 점은 고객이든 라이더든 점주든 사용할 수 있다는 점이었다. 그 사용자의 정보에 따라 다른 인터페이스와 기능을 사용한다는 것이 좋은 점이라고 생각한다. 이제 조금 아쉬웠던 점은 라이더의 배달 시간을 넣고, 라이더를 자동으로 배달할 수 있게 하는 시스템을 
시도해보는 것도 좋을 거 같다.

- 김현지
  - 이번 프로젝트를 통해 기획부터 데이터베이스 구축, SQL 쿼리 작성, 그리고 협업까지 전반적인 과정을 빠르게 경험할 수 있었습니다. 데이터 정규화와 효율적인 쿼리 작성에 대해 고민하며 많은 것을 배웠고, 예상치 못한 문제를 해결하는 과정에서 협업의 중요성을 다시 한번 실감했습니다. 제한된 시간 내에 최적화까지 완벽히 이루지 못한 점은 아쉽지만, 설계 단계에서 유지보수와 확장성을 고려하는 것이 얼마나 중요한지 깨달았습니다. 처음에는 막연하게 느껴졌던 개념들이 직접 설계하고 구현하는 과정을 거치며 점점 명확해졌고, 문제 해결 과정에서도 많은 배움이 있었습니다. 특히, 팀원들과 자유롭게 의견을 나누고 조율하는 과정이 의미 있는 경험이었으며, 덕분에 더욱 성장할 수 있었습니다. 앞으로도 이러한 경험을 바탕으로 발전해 나가고 싶습니다.


기획서는 대부분의 학생들이 Markdown 문법을 활용하여 Github Repository에 README.md 형태로 작성하는 경우가 대부분 입니다. 마지막 발표 전에 README 파일을 수정해서 기획서 형태로 만들어 보세요. 제가 언급한 내용 이외에 추가적인 내용이 더 들어가도 좋습니다!


- 구성요소:
    - DBConnectionManager.java: DB 연결 관리
    - DBProperties.java: DB 설정 관리

-----------------------------------------------------------------------------------
