# 프로젝트 기획서


## 1. 개요

- 음식 주문 및 배달 플랫폼
- 2025.03.24 ~ 2025.03.27
- 팀원
    - 팀장 도진호
    - 팀원 김지원
    - 팀원 김현지
    - 팀원 이도현

### 1.1 목적 및 배경

본 프로젝트는 기존 배달앱 서비스에 고객만 사용하는 것이 아니라 배달원과 가게 사장님도 사용 가능하게 제공하고자 합니다.

### 개발 환경
- 프로그래밍 언어: Java (Java SE 8 이상)

- 데이터베이스: Oracle Database 

- 개발 도구:
IntelliJ IDEA (또는 Eclipse) - Java 개발 환경 및 IDE

- 데이터베이스 관리 및 쿼리 도구: Oracle SQL Developer  

- 형상 관리 툴: Git, GitHub

Java로 무엇을 했는지, DB는 뭘 썼는지, 어떤 서비스를 구현했는지에 대한 대략적인 소개를 넣어주시면 됩니다. // 이것만 할지 물어보기기

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
    -사용자의 회원 가입, 로그인, 정보 조회 등의 기능을 구현했습니다.

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

![{F148676A-2800-4B38-A341-A7B7A8698C5D}](https://github.com/user-attachments/assets/f59a66dd-46d1-4384-bc26-2b826d803887)
 

### 트러블 슈팅 및 한계점
- 프로그램의 order 로직에 오류가 있어 예상과 다른 결과를 출력하거나 오류를 발생시켰습니다. 그래서 디버깅 도구를 사용하여 코드의 실행 과정을 추적하고, 오류가 발생하는 지점을 찾아 수정했습니다. 또한, 테스트 케이스를 추가하여 각 기능이 올바르게 작동하는지 확인했습니다.

- 사용자가 잘못된 형식의 데이터를 입력하여 프로그램이 예외를 발생시키거나 예상치 못한 동작을 했습니다. 그래서 사용자 입력 값에 대한 유효성 검사를 추가하고, 잘못된 입력이 들어올 경우 사용자에게 적절한 오류 메시지를 표시하도록 수정했습니다.

- 현재 프로젝트는 특정 데이터베이스에 종속되어 있습니다. 데이터베이스 추상화 레이어를 도입하여 다양한 데이터베이스를 지원하도록 확장해야 합니다.

### 회고

각 팀원들의 이번 프로젝트를 진행하면서 느낀 점 및 개인적인 성과 등을 회고하는 파트입니다. 


기획서는 대부분의 학생들이 Markdown 문법을 활용하여 Github Repository에 README.md 형태로 작성하는 경우가 대부분 입니다. 마지막 발표 전에 README 파일을 수정해서 기획서 형태로 만들어 보세요. 제가 언급한 내용 이외에 추가적인 내용이 더 들어가도 좋습니다!

# 요구사항 명세서
![{D9D8F07C-6E5E-44C4-BEBE-CFAF32ED6C1F}](https://github.com/user-attachments/assets/f766f9c7-cb7e-4ad1-a8e3-caaf4905b010)

![{7AA65041-F82B-46C4-A6F4-8E52841406E5}](https://github.com/user-attachments/assets/d598ea8b-d551-4361-b1d3-1964f7b3a1a4)

![{50909D91-414C-44AE-8F10-9CB64A3910FF}](https://github.com/user-attachments/assets/d77d7301-4803-45ce-940d-21a4443e4ff8)

![{D9D8F07C-6E5E-44C4-BEBE-CFAF32ED6C1F}](https://github.com/user-attachments/assets/60c9076f-9ac4-4939-bb45-4fbaf07ceb38)

![{DAECF116-31CA-45FA-9B5A-7140A892F906}](https://github.com/user-attachments/assets/d94c3648-1dd3-4b14-96de-ace0bf24a346)

![{22944ADA-84A6-4627-8646-665E51C9FF70}](https://github.com/user-attachments/assets/4c281ba9-2c4e-49d3-a274-d175ef77f095)


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
    * `UserController.java`: 사용자 계정 관련 API 제공
    * `UserService.java`: 사용자 계정 관련 비즈니스 로직 처리
    * `UserRepository.java`: 사용자 데이터 접근
    * `AuthService.java`: 인증 및 권한 관리

#### 1.2. 식당 서비스 (Restaurant Service)

* **역할:** 식당 정보 관리, 메뉴 관리
* **책임:**
    * 식당 등록, 수정, 삭제
    * 메뉴 등록, 수정, 삭제
    * 식당 검색, 메뉴 검색
* **구성 요소:**
    * `RestaurantController.java`: 식당 관련 API 제공
    * `RestaurantService.java`: 식당 관련 비즈니스 로직 처리
    * `RestaurantRepository.java`: 식당 데이터 접근
    * `MenuController.java`: 메뉴 관련 API 제공
    * `MenuService.java`: 메뉴 관련 비즈니스 로직 처리
    * `MenuRepository.java`: 메뉴 데이터 접근

#### 1.3. 주문 서비스 (Order Service)

* **역할:** 주문 생성, 관리, 결제 처리
* **책임:**
    * 주문 생성, 취소, 조회
    * 결제 처리
    * 주문 상태 관리
* **구성 요소:**
    * `OrderController.java`: 주문 관련 API 제공
    * `OrderService.java`: 주문 관련 비즈니스 로직 처리
    * `OrderRepository.java`: 주문 데이터 접근
    * `PaymentService.java`: 결제 처리

#### 1.4. 배달 서비스 (Delivery Service)

* **역할:** 배달원 관리, 배달 상태 관리
* **책임:**
    * 배달원 등록, 관리
    * 배달 상태 관리
    * 배달원 위치 추적
* **구성 요소:**
    * `DeliveryController.java`: 배달 관련 API 제공
    * `DeliveryService.java`: 배달 관련 비즈니스 로직 처리
    * `DeliveryRepository.java`: 배달 데이터 접근

#### 1.5. 리뷰 서비스 (Review Service)

* **역할:** 리뷰 생성, 관리, 조회
* **책임:**
    * 리뷰 생성, 수정, 삭제
    * 리뷰 조회
    * 리뷰 평점 관리
* **구성 요소:**
    * `ReviewController.java`: 리뷰 관련 API 제공
    * `ReviewService.java`: 리뷰 관련 비즈니스 로직 처리
    * `ReviewRepository.java`: 리뷰 데이터 접근

#### 1.6. 사용자 인터페이스 서비스 (UI Service)

* **역할:** 사용자와의 모든 상호작용 담당
* **책임:**
    * 메뉴 표시 및 네비게이션
    * 사용자 입력 수집 및 검증
    * 결과 출력 및 포맷팅
* **구성 요소:**
    * `MenuView.java`: 메뉴 표시 및 선택 처리
    * `InputView.java`: 사용자 입력 처리
    * `OutputView.java`: 결과 출력 처리


### 데이터 액세스 서비스 (DAO Service)

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


- 구성요소:
    - DBConnectionManager.java: DB 연결 관리
    - DBProperties.java: DB 설정 관리

-----------------------------------------------------------------------------------
