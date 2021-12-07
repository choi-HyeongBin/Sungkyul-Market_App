# Sungkyul-Market
---
### 프로젝트 설명
> 팀원 4명과 함께 졸업작품으로 성결대 학생들을 위한 직거래 어플을 개발하였습니다.<br> 
> android Studio와 Java, Firebase를 사용하여 게시글을 list형식으로 확인할 수 있고 게시글을 작성하여 올리는 기능, 쪽지를 통해 직거래를 할 수 있는 기능 등이 있습니다. 
---
### 프로젝트 계획 이유

> 학교 교양책이 없어서 서점에 가보니 매물이 없었습니다. 당장 내일 수업인데 서점에 없어서 막막했던 상황이 있었습니다. 알라딘 등 중고책을 전문적으로 다루는 매체를 이용할 시 전공 서적은 종종 있었지만 성결대학교에서만 사용하는 교양 서적은 구하는데 어려움이 있었습니다. 또한, 매 학기 여러 강의를 수강하면 구매해야하는 교재가 적지 않습니다. 이를 전부 구입하면 학생은 금전적인 부담이 생기고, 학기가 끝나면 필요 없어지는 책들이 많아집니다. <br> 그래서 성결장터 어플을 통해 학생들간의 거래를 유도하여, 앞서 언급한 문제점들을 해결하기 위해 프로젝트를 개발하게 되었습니다.
---
### 사용 기술

  + Androidstudio
  + Java
  + FireBase
---
### Sungkyul-Market 기능

1. 메인화면 
+ 게시글들 확인 기능
+ 각각의 화면은 Fragment로 구성하여 BottomNavigation bar으로 장바구니, 마이페이지, 신고, 게시글 등록 화면으로 전환 기능<br><br>
![image](https://user-images.githubusercontent.com/94504100/144965178-89aa2640-7cc1-461f-a893-22da5e7f8c8e.png)

2. 카테고리
+ 카테고리(교재, 의류, 기타 등)를 선택하여 해당 카테고리의 게시글만 보여주는 기능 <br><br>
![image](https://user-images.githubusercontent.com/94504100/144965281-35ac87bb-75a6-4068-86b1-1ef19e9ae6ed.png)

3. 게시글 자세히 보기
+ 물품 사진, 판매자 아이디, 내용, 가격 등 게시글의 내용을 보여주는 기능<br><br>
![image](https://user-images.githubusercontent.com/94504100/144965572-cc52dbe8-bbaf-4c9c-bd34-1202f8d0416d.png)

4. 로그인, 회원가입
+ 성결대학교 아이디, 사용자 정보를 기입하여 회원 가입 후 로그인 할 수 있는 기능<br><br>
![image](https://user-images.githubusercontent.com/94504100/144965339-d32778f5-671f-41f5-800a-ae8a84319d7b.png)
![image](https://user-images.githubusercontent.com/94504100/144965359-c009b04d-62b6-4b57-9262-afdcdb49d922.png)
![image](https://user-images.githubusercontent.com/94504100/144965384-a31cb02c-b140-4274-b18f-aabe67be7aad.png)

5. 장바구니
+ 마음에 드는 게시글들을 저장하여 따로 보여주는 기능<br><br>
![image](https://user-images.githubusercontent.com/94504100/144965518-e83a3bb3-edb4-4589-b9c0-11a013901fb8.png)

6. 게시글 등록
+ 팔고자 하는 물품의 정보들을 기입하고 게시글을 올리는 기능<br><br>
![image](https://user-images.githubusercontent.com/94504100/144965639-3a4a0f96-3114-42e4-9c80-034813470d57.png)

7. 마이페이지
+ 구매, 판매 내역/ 구매현황, 판매 현황/ 쪽지함/ 상품 관리/ 내 정보 확인 화면으로 전환 가능한 기능<br><br>
![image](https://user-images.githubusercontent.com/94504100/144965660-befdc887-9d9f-4b7e-a596-a09bef37d543.png)

8. 구매현황, 판매 현황
+ 자신이 구매, 판매중인 물품(거래 진행)의 현황을 보여주는 기능
+ 거래완료를 클릭하면 구매내역/판매내역으로 넘어감<br><br>
![image](https://user-images.githubusercontent.com/94504100/144966469-cbba1809-2681-47f3-955a-0866068c8905.png)
![image](https://user-images.githubusercontent.com/94504100/144966497-aae2e84f-8bf9-46a5-bbee-ff7de625f262.png)

9. 구매내역, 판매 내역
+ 자신이 구매, 판매한 물품(거래 완료)의 현황을 보여주는 기능<br><br>
![image](https://user-images.githubusercontent.com/94504100/144966537-59a499c7-4edb-47c1-a18d-b26ae5830e5f.png)
![image](https://user-images.githubusercontent.com/94504100/144966663-2f6f80e4-66b4-41ce-9b1d-713c35600c4d.png)

10. 쪽지
+ 구매자와 판매자가 대화를 할 수 있는 기능
+ 주고 받은 쪽지들을 볼 수 있는 기능<br><br>
![image](https://user-images.githubusercontent.com/94504100/144966724-30c8e9c5-4d9f-4ffa-8b68-ac29c0190c85.png)
![image](https://user-images.githubusercontent.com/94504100/144966756-e4ba3992-1f7f-4a9c-a8ab-272724c4c0a2.png)


11. 상품 관리
+ 자신이 올린 게시글들을 확인하고 수정할 수 있는 기능<br><br>
![image](https://user-images.githubusercontent.com/94504100/144966807-ff9a0bc7-51bb-4970-8f8a-dc49283aa781.png)

12. 내 정보
+ 회원가입때 적은 자신의 정보를 확인하고 수정, 탈퇴할 수 있는 기능<br><br>
![image](https://user-images.githubusercontent.com/94504100/144966890-c1e0b502-d6e6-4682-8f1d-252be54d48ca.png)


