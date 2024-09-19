# Store Reservation
---

>매장 테이블 예약과 리뷰 작성 서비스

---

## ERD
<img width="957" alt="스크린샷 2024-09-19 오후 4 01 50" src="https://github.com/user-attachments/assets/89db857c-843d-4b02-81f0-aa16d041991f">

---

## 비즈니스 로직
<img width="432" alt="스크린샷 2024-09-19 오후 4 00 47" src="https://github.com/user-attachments/assets/57e58055-d245-4b7f-b7da-99672a7a815e">

---

## Swagger 화면


<img width="1840" alt="스크린샷 2024-09-19 오후 4 16 02" src="https://github.com/user-attachments/assets/810187ce-2b9a-4246-9bb6-539d30c96906">
<img width="1840" alt="스크린샷 2024-09-19 오후 4 16 13" src="https://github.com/user-attachments/assets/93dafc74-b168-41af-bb06-98cb0558af44">

---

## 프로젝트 설명
### User
- 사용자 회원가입 구현 (비민번호는 암호화 되어 DB에 저장)
- 로그인용 ID 는 phoneNumber 로 사용
- admin 계정(별도 구현 필요) 은 회원가입한 사용자를 파트너로 전환 가능한 기능 구현

### Store
- 파트너로 등록된 사용자는 매장 등록 구현
- 매장을 등록한 파트너 계정으로 매장 수정 구현
- 매장을 등록한 파트너 계정으로 매장 삭제 구현
- 모든 유저는 매장 목록을 볼 수 있음
- 매장 목록에서 매장 상세 정보로 볼 수 있음

### Reservation
- 매장 상세 화면에서 예약 진행
- 해당 매장에 등록된 예약이 있을 시 예약 진행 불가능
- 매장 관리자는 접수된 예약을 확인, 취소 두가지 상태로 변경 가능
- 예약이 확인된 사용자는 예약 10분전에 매장 키오스크로 방문 체크 가능
- 예약시간이 지났으면, 키오스크에서 체크 불가능(예외처리를 통해 직원에게 문의 요청 전달)

### Review
- 매장을 예약해서 이용한 사용자는 리뷰 작성 가능. Reservation 의 status로 이용 판단(VISITED 일 경우.)
- 리뷰를 작성한 이용자는 리뷰 수정이 가능.
- 리뷰 삭제는 리뷰 작성자와 해당 리뷰가 작성되어있는 매장 관리자 두명이 삭제 가능
