## 👋 MEFI 👋
#### 팀 단위 화상회의와 다자간의 문서편집이 가능하도록 도와주는 협업툴 서비스

<br>

### MEFI 소개 및 시연 영상

<br>

### MEFI 서비스 화면

<br>

### 주요 기능

<br>

### 🖥️ 개발 환경 
🖱 백앤드
- intellij
- spring boot 3.2.1
- spring-boot-jpa
- spring security 6.1.3
- java 17
- MySQL 8.0.36

🖱 프론트
- Visual Studio Code
- Vue3
- Node.js 20.10.0
- Vuetify
- figma

🖱 라이브러리
- openvidu 2.29.0
- Yjs
- Quill

🖱 Infra
- Docker
- Jenkins 2.426.2
- AWS EC2
- AWS S3
- Server 20.04.6 LTS (GNU/Linux 5.15.0-1051-aws x86_64)
- Nginx 1.24.0

<br>

### 💫서비스 아키텍처 
![Untitled](https://prod-files-secure.s3.us-west-2.amazonaws.com/9fd5842a-e364-4a3e-81f6-e575843e2010/510e91f4-29bd-442c-875b-31e5d63d088b/Untitled.png)

<br>

### 💫CICD

<br>

### 💥기술 특이점
- WebRTC(Openvidu)
- Yjs & Quill
- S3
- 배포

<br>

### 👨‍👩‍👧 협업툴
- Git
- Jira
- Gerrit
- Notion
- Mattermost

<br>

### 📝 요구사항 정의서

<br>

### 🎨 화면 설계서
![img](./readme/피그마%20페이지.png)

<br>

### ⚡️ Git 컨벤션
⚡️제목
- 🚧 : 작업 도중 저장
- ✨ : 기능 구현
- 🎨 : 파일 구조 수정
- 🚑️ : 버그 수정
- 🧱 : 인프라 관련 작업
- 🔨 : 코드 리팩토링
- 🔧 : 포트, 환경, 세팅에 관한 수정

<br>

⚡️꼬리말
- Fixes : 이슈 수정 중 (미해결)
- Resolves : 이슈 해결 후
- Ref : 참고할 이슈
- Related to : 해당 커밋과 관련된 이슈 번호(미해결)

<br>

⚡️commit message 예시
```
✨: "추가 로그인 함수"  // 제목

로그인 API 개발          // 본문

Resolves: #123          // 꼬리말 
Ref: #456
Related to: #48, #45

issue_key               // jira 연동
issue_key #done         // jira 해당 작업 완료
```

<br>

### ⚡️ Git Flow
⚡️ Git flow 사용한 브랜치
- master : 배포
- develop : 개발 및 테스트
- feature : 기능

<br>

⚡️ Git flow 사용한 브랜치
- 개발 시, 맡은 기능 별로 develop 하위에 feature 브랜치 생성
- 개발 완료 시, 해당 feature 브랜치를 develop에 merge한다.
- 개발 테스트 시, develop에 파이프라인 연결하여 배포 및 테스트 작업 진행
- 개발 완료 및 테스트 완료 시, master 브랜치로 배포 진행

<br>

⚡️ Git 브랜치 이름 컨벤션
```
backend/domain/feature
frontend/domain/feature

예시 : be/user/login
```

<br>

### 🔥 Gerrit 활용
🔥 Gerrit과 GitLab의 code review 비교
- GitLab은 MR 요청 시, code review가 가능합니다
- Gerrit은 commit 시점 마다 가능하며, 코드 작성자가 코드 리뷰를 원할 경우 코드 리뷰 요청을 보낼 수 있습니다.

<br>

🔥 Gerrit을 이용한 이유
- GitLab은 merge를 할때만 코드 리뷰를 진행하므로, 리뷰해야할 코드 양이 많습니다
  그에 비해 gerrit은 commit단위로 코드리뷰를 할 수 있어서, 리뷰해야할 코드 양이 적습니다.
- Gerrit은 코드 리뷰 시점을 개발자가 정할 수 있습니다.

<br>

🔥 Gerrit 사용 방법
- jira를 GitLab과 연동
- GitLab과 Gerrit 연동
- Gerrit의 Repository를 clone하여 개발 작업 및 commit
- commit한 내용은 GitLab에 자동 업로드
- commit에 Jira issue key를 기재하면, jira에 자동 업로드

<br>

🔥 Gerrit을 이용한 코드 리뷰 과정
- feature 브랜치에서 작업 진행
- 해당 작업을 완료하여 commit을 하고, 코드리뷰 요청
- 팀원들의 코드리뷰 평가
- 피드백으로 코드를 수정 후 다시 코드 리뷰 요청
- 팀원들의 평가로부터 통과를 받으면, 코드를 push할 수 있습니다.

<br>

### 🐛 코드 컨벤션
🐛 프론트 코드 컨밴션

<br>

🐛 백앤드 코드 컨밴션
- File : domain infomation
- Method
  - 생성 create[domain]
  - 수정 modify [domain] [content]
  - 삭제 delete [domain] [content]
  - 조회 get [target]
- Swagger API  
![img](./readme/swaggerAPI01.png)   
![img](./readme/swaggerAPI02.png)   
- Swagger 파라미터
![img](./readme/swagger파라미터%2001.png)   
![img](./readme/swagger파라미터%2002.png)   

<br>

### 👨‍👩‍👧 Jira
협업 및 일정, 업무 관리를 위해 Jira를 이용하였습니다. 매주 월요일 오전 회의를 통해 한 주 동안 진행할 스프린트를 계획하고, 진행할 스토리와 테스트를 해당 스프린트에 생성하여 등록하였습니다. 또한 매주 금요일 오후에 회의를 진행하여 해당 스프린트에 대한 회고를 진행하였습니다.
- Epic : 큰 도메인으로 분류
- Stroy : 사용자 관점에서 기능을 사용하는 상황을 기술
- subtask : 사용자가 사용할 기능을 개발할때 필요한 목록으로 디테일하게 기술, 소요된 시간 및 스토리포인트 할당

<br>

### 👨‍👩‍👧 Notion
개발 환경 구축에 필요한 정보, 라이브러리 및 기술 관련 링크, 회의록 작성 및 프로젝트 진행에 관련된 산출물을 기록하고 공유하는 용도로 사용하였습니다. 컨벤션 및 브랜치 전략 등 또한 노션에 기록함으로써, 모두가 항시 확인할 수 있도록 관리하였습니다.

<br>

### 👨‍👩‍👧 Scrum
매일 아침 9시에 스크럼 회의를 10분 동안 진행하며, 어제 했던 일과 오늘 진행할 업무, 발생한 이슈를 공유하는 시간을 가졌습니다. 스크럼을 통해 팀원들의 현재 상황을 파악할 수 있고, 프로젝트에 대해 꾸준히 모니터링할 수 있었습니다.

<br>

### 🎨 ERD

<br>

### 🎨 EC2 포트 정리
| 이름 | 내부 포트 | 외부 포트 |
| :-----: | :-----: | :-----: |
| Vue | 5173 | 5173 |
| SpringBoot | 8080 | 8080 |
| Jenkins | 9000 | 9000 |
| MySQL | 3306 | 3306 |
| http | 80 | ----- |
| https | 443 | ----- |
| openvidu-coturn-1 | 3478 | 3478 |

<br>

### 👨‍👩‍👧 팀원 역할
😃권현준


😊신영한


😘최부광


😝박병조


😛김준수


😀이지연
- 회원 가입
- 로그인
- 이메일 인증
- 비밀번호 찾기
- 비밀번호 변경
- 회원 정보 조회 및 수정
- 회원 탈퇴
- 알림

<br>

### 👨‍👩‍👧 프로젝트 소감
😃권현준

😊신영한

😘최부광

😝박병조

😛김준수

😀이지연