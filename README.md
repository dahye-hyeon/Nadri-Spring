NADRI(나드리)
=============
* 국내여행 Planner 웹 프로젝트
* 기간:2021.11.15~2021.12.27(36일간)
* 팀원소개: 정선일(팀장) | Frontend: 안서영, 정선아, 정선일 | Backend: 이성현, 이태경, 정선일, 현다혜
* 사용언어:JAVA(JDK 1.8), JSP/Servlet, HTML5, CSS, Javscript, Oracle SQL(11g Express)
* 사용기술:Spring Framework, MyBatis, jQuery, Ajax, Open API(kakao Login, kakao map, Naver Login)
* Server:AWS, Apache Tomcat 8.5

---------------------------------------

## 개발목적
* 코로나19로 해외 여행이 어려워져 국내 여행의 수요 증가
  → 국내 여행 플랜 사이트는 해외 여행에 비해 상대적으로 작음
    → 관광지 뿐만 아니라 숙박, 음식점 등 함께 소개하여 여행 경로(루트) 추천   
 
 → 보다 쉽게 여행 계획을 세우는데에 도움을 주고자 함
---------------------------------------

## 구동화면
#### [프로그램 동작 youtube 영상](https://youtu.be/sSeUrVEvGl0, "youtube link")

* 메인 화면
<img width="960" alt="image" src="https://user-images.githubusercontent.com/67157818/147849239-ded2e822-bd02-4b7d-acdb-5bfcf099b47f.png">

* 여행지 선택 화면
![image](https://user-images.githubusercontent.com/67157818/147849310-b6f13782-7ef7-4506-8eb5-501c448d0a8b.png)

* 일정 만들기 화면(여행 시작 지점:각 지역 버스터미널)
  * 달력으로 여행 일정 설정 가능하며 선택된 날짜에 따라 일정 수(Day1, Day2, Day3 ...)가 증가
<img width="960" alt="image" src="https://user-images.githubusercontent.com/67157818/147849363-dde3cf9c-ea33-4f57-bc32-c3ac8a0017ae.png">

* 일정 생성 화면
  * 각 여행 일차 별로 선의 색을 다르게 하였으며, 방문 예정 장소나 음식점을 드래그하여 위치이동이 가능함(->경로를 재탐색 ) 
![image](https://user-images.githubusercontent.com/67157818/147849611-3cdc6164-ffda-45fe-b171-82acb51b6d90.png)

* 마이 페이지
![image](https://user-images.githubusercontent.com/67157818/147849478-7a32c42a-a0c0-4c7a-b318-3ea3c8d58954.png)

* 로그인 화면
![image](https://user-images.githubusercontent.com/67157818/147849499-5e7e6b28-bceb-4232-84a3-fec173acbd24.png)

* 회원가입 화면
  * 비밀번호에 validation 설정(영소문자+숫자 6문자 이상)
<img width="960" alt="image" src="https://user-images.githubusercontent.com/67157818/147850180-fcb0e0d9-4e96-49b6-ad8f-1884bfea2820.png">

---------------------------------------

## 추천 경로 알고리즘 - Greedy 알고리즘 채택
#### 전제조건 : 여행 가고자 하는 도시에는 대중교통을 이용하여 도착(= 시작점은 고속버스터미널)
![image](https://user-images.githubusercontent.com/67157818/147850720-7ba654b0-2ed7-4afb-a536-879854c8d6fe.png)

