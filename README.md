# Apache Flink Workflow Gateway Backend

이 프로젝트는 UI 워크플로우 JSON을 받아 Apache Flink 실시간 처리 파이프라인으로 컴파일하고, Jar 배포/모니터링/CEP Rule 무중단 업데이트를 제공하는 Spring Boot 백엔드 예제입니다.

## 주요 기능
- **API Gateway**: `X-SESSION-ID` 헤더 기반으로 인증/권한을 확인하는 Gateway 필터 제공
- **세션 관리**: `/api/sessions` API에서 사용자 세션 생성/조회/삭제
- **워크플로우 컴파일 & 배포**: `/api/workflows/compile`, `/api/workflows/deploy`
- **프로세스 모니터링**: `/api/users/{userId}/processes`, `/api/processes/{jobId}/metrics`
- **CEP 규칙 무중단 업데이트**: `/api/cep/rules`

## 빌드 & 실행
```bash
mvn spring-boot:run
```

로컬 저장소에서 Maven Central에 접근할 수 없는 경우, 내부 Nexus 혹은 캐시된 Maven 저장소를 설정해야 합니다.

애플리케이션을 실행한 뒤, 다음 순서로 사용합니다.
1. `/api/sessions` 에 username/password(기본 admin/admin, analyst/analyst)를 전달하여 세션 생성
2. 발급된 `sessionId` 값을 모든 API 호출 헤더 `X-SESSION-ID` 로 전달
3. 워크플로우 JSON을 컴파일/배포하고, 프로세스 모니터링 및 CEP 업데이트를 수행
