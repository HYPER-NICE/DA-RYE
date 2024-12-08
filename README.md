# Da-Rye

다례(茶禮)는 한국 전통의 차 문화와 예절을 의미하며, 다도의 한국식 표현입니다. 차를 우려내고 대접하는 과정에서 예의를 갖추는 의식적인 행위를 가리키며, 마음을 가다듬고 타인을 존중하는 정신이 담겨 있습니다.

이를 바탕으로, 우리는 다례 문화를 중심으로 재화의 유통을 목표로 하는 도메인을 구성하고자 합니다. 프로젝트는 백엔드와 프론트엔드로 나뉘어 운영되며, 각각의 이름은 다례의 상징성을 반영하고 있습니다.

**백엔드 프로젝트명은 "다관(茶罐)"**입니다. 다관은 차를 우려내는 데 사용하는 도구로, 뜨거운 물과 찻잎을 담아 향과 맛을 끌어내는 중요한 역할을 합니다. 백엔드는 이러한 다관처럼 데이터를 우려내고 가공하여 프론트엔드에 필요한 정보를 제공하는 핵심 시스템으로 작동합니다.

**프론트엔드 프로젝트명은 "다반(茶盤)"**입니다. 다반은 차를 내놓고 손님에게 대접할 때 사용하는 받침이나 쟁반을 의미합니다. 다반은 백엔드에서 제공하는 데이터를 시각적으로 표현하고 사용자에게 매끄럽게 전달하는 역할을 합니다.

이처럼 다관과 다반은 각자의 역할을 충실히 수행하며, 조화를 이루어 다례 문화와 관련된 재화와 서비스를 원활하게 유통하는 것을 목표로 합니다. 이 프로젝트는 전통 문화를 현대적인 방식으로 계승하고, 사람들에게 차 문화의 깊이와 가치를 전달하고자 합니다.

## 프로젝트 정의서

1. 프로젝트 구현 및 실행 조건
    1. ERD툴을 이용한 논리 모델링 작성
    2. 물리 모델링 & 테이블 생성 및 데이터 입력 스트립트 작성
    3. MySQL8.x 데이터 베이스에 물리 모델 구축

## 프로젝트 요구 사항 (필수)

1. 기본 정보 테이블 & 행위 테이블 설계
    1. 고객, 상품, 관련 테이블이 포함되어 있어야 함
    2. 주문, 결제, 배송, 환불 및 상태, 이력 관리 테이블이 포함되어 있어야함
2. 카테고리 & 코드 테이블 설계
    1. 상품의 분류를 3계층(대중소)로 나눈 테이블을 작성
    2. 고객 직업, 주문 상태, 결제 방법 등의 코드를 통합한 테이블 작성
3. 테이블 공통 필수 사항
    1. 각 테이블에는 PK컬럼과 시스템 컬럼을 필수로 추가

## 프로젝트 요구 사항 (권장)

1. 쿠폰 & 포인트 테이블 추가
    1. 쿠폰 사용 조건과 정책을 위한 테이블 설계
    2. 상품 구매시 발생하는 포인트를 관리하는 테이블 설계
2. 재고 관리와 거래처 테이블 추가
    1. 상품의 재고와 입고를 관리하는 테이블 설계
    2. 상품을 매입하는 거래처를 관리하는 테이블 설계
3. 자바(JDBC)로 각 테이블의 데이터를 조회 및 검증
    1. JDBC로 MySQL연결 확인
    2. TDD로 테이블의 제약조건(PK, FK, 기본값, CHECK)이 잘 적용되었는지 확인
4. 이외 원하는 기능을 자유롭게 설계 및 구현
