# sevendumplings.site
# 만두일곱개 팀 AWS 클라우드 인프라 활용한 게시판 웹 애플리케이션

## 프로젝트 소개
이 프로젝트는 "만두일곱개" 팀이 AWS 클라우드 인프라를 활용하여 개발한 게시판 웹 애플리케이션입니다. 게시판은 JAVA JDK 21과 MAVEN으로 빌드되며, WAR 파일로 배포되었습니다. 배포된 톰캣은 8.5 버전을 사용하였습니다. 이 프로젝트에는 RDS 계정 정보가 포함되어 있으나, 해당 리소스는 이미 삭제되어 있습니다.

## 설치 가이드
프로젝트를 빌드하고 배포하기 위해 다음 툴을 사용합니다:
- JAVA JDK 21
- MAVEN

프로젝트를 빌드하고 WAR 파일을 배포하는 단계는 다음과 같습니다:
1. Eclipse IDE를 사용하여 동적 웹 프로젝트를 생성합니다 (버전: 2023-12).
2. 생성된 프로젝트를 MAVEN 프로젝트로 변경합니다.
3. MAVEN BUILD를 실행하여 WAR 파일을 생성합니다.
4. Eclipse IDE에서 프로젝트를 선택하고, "Import" > "Maven" > "Existing Maven Projects"를 선택하여 프로젝트를 추가합니다.
5. 동일한 버전의 톰캣 서버에서 프로젝트를 기동합니다.

**참고사항:** RDS 계정은 프로젝트 종료후 삭제되었습니다. 

## 프로젝트 참가자
- 팀장: 류교서
- 팀원: 정현화, 강종성, 이범학, 김민승

## 시큐어 코딩 특징
- 패스워드 저장과 일치 여부 확인을 위해 SHA-256 해시와 솔트값을 사용하였습니다.
- XSS 방어를 위해 오픈 소스 루시 서블릿 필터를 사용하였습니다.
- preparestatement 쿼리 및 바인딩

## Special Thanks to 이범학
프로젝트를 쉽게 배포할 수 있는 AWS 클라우드 인프라를 구현해준 이범학님께 진심으로 감사드립니다.

