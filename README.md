# Assignment1
모바일프로그래밍 개인과제1

20181617 박정현



## 깃허브 주소

https://github.com/kmucs-2019-mobile-programming/Assignment1



## 테스트 환경

1. ##### 안드로이드 스튜디오

   Android Studio 3.5

   Build #AI-191.8026.42.35.5791312, built on August 9, 2019

   Android SDK Build-Tools : 29.0.2

2. ##### 자바 버전

   JRE: 1.8.0_202-release-1483-b49-5587405 x86_64

   JVM: OpenJDK 64-Bit Server VM by JetBrains s.r.o

3. ##### 운영체제

   macOS 10.15

4. ##### 테스트 기기

   갤럭시 s9+ (Android 9.0) 실기기



## 개인 과제 공지

[배점] 첫번째 화면 3점, 두번째 화면 3점, 세번째 화면 3점, 유저관리 1점 
      (만점 10점, 각 화면의 구성+동작으로 평가, 깃허브 가산점)


1. ##### 첫번째 화면 (Relative Layout 사용)
- [x] 1. 앱 접속 페이지, 회원 ID/비밀번호(EditView), 로그인/회원가입(Button)
- [x] 2. 첫번째 화면 초기화시에 파일에서 개인정보 읽어 오기
- [x] 3. ID, 비밀번호 입력 시 기존에 가입한 회원 ID, 비밀번호 체크 오류 시 에러 메시지 출력
- [x] 4. ID, 비밀번호 입력이 정상이고 로그인 버튼 클릭 시 세번째 페이지 이동 

2. ##### 두번째 화면 (Linear Layout 사용)
- [x] 1. 회원가입 페이지, 첫번째 페이지에서 회원가입 버튼 클릭 시 출력 
- [x] 2. ID(EditView, 중복검사), 비밀번호(EditView, 자릿수/특수키 등 규칙 체크) 
- [x] 3. 이름/전화번호/주소(EditView)
- [x] 4. 개인정보 사용 동의 간략 약관(TextView), 동의 여부(Radio Button, Decline/Accept)
- [x] 5. 회원정보는 파일로 저장하고 첫번째 페이지로 이동

3. ##### 세번째 화면 (Constraint Layout, Table Layout, Grid Layout, Frame Layout 중 하나 사용)
- [x] 1. 첫번째 페이지에서 ID, 비밀번호 입력 시 정상이고 로그인 버튼 클릭 시 화면 출력
- [x] 2. 세번째 화면을 간단한 기능을 수행하도록 구성 (ex. 간편 계산기 등)
- [x] 3. View을 상속한 여러가지 위젯을 사용하여 화면을 구성(기능에 맞는 위젯 선택하여 구성)
     View Group을 상속한 위젯 ListView, GridView, AdapterView, ToolBar 등
     Text View을 상속한 CheckBox, Switch, ToggleButton, RadioButton 등
     ImageView, ImageButton 등

##### *과제 제출 

- [x] 이캠퍼스 과제 공지 게시판에 구현내용(Readme 파일) 및 깃허브 주소 기재
  소스 파일은 압축(ZIP)하여 등록
- [x] 깃허브에 제출할 과제명으로 Repository 생성 후 소스파일 upload 및 Readme 파일 등록 
