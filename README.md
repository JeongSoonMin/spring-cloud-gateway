Settlement Gateway Application
==================================

## 개발 구성
* JDK 21.x
* SpringBoot 3.3.0
* SpringCloudGateway 2023.0.2

## 로컬 개발 환경시 셋팅 필요 사항
* SpringBoot 구동시 환경변수 추가 필요(AWS SecretsManager 설정 변수들...)
  * MANAGEMENT_URL=http://localhost:8080
  * AUTHS_URL=http://localhost:8081
  * JWT_SECRET_KEY=xxxx

## 개발 구성
* application-gateway 에 설정된 경로로 route
* AuthenticationFilter 적용될 경우 JWT 토큰 체크하여 proxy되는 applicaiton의 request header에 두 항목 전달
  * userId : ex) tb_user.USER_ID
  * userRole : ex) ADMIN
  * 토큰 만료된 경우 AccessTokenExpiredException 처리. HttpStatus: 401. errorCode: COMMON_ACCESS_TOKEN_EXPIRE 
* allow-roles 지정되어 있을 경우
  * 해당 토큰의 roles이 allow-roles에 등록된 권한 일 때만 route
  * 없는 경우 ForbiddenException 처리. HttpStatus: 403. errorCode: COMMON_FORBIDDEN
  * allow-role은 설정되어 있으나 토큰값 없이 요청 될 경우 UnAuthorizedException 처리. HttpStatus: 401. errorCode: COMMON_UNAUTHORIZED

## application-gateway.yaml 설정 예시
    # 기능 설명 작성
    - id: applicationPath/기능Path  # 
      uri: ${MANAGEMENT_URL} # ex) SecretsManager에 정의된 변수 사용 
      predicates:
        - Path=/fassto-admin/api/v1/applicationPath/기능path # gateway에서 받는 URI 이며, rewrite가 설정되어 있지 않을 경우 proxy 되는 경로
        - Method=GET # 허용되는 메소드. ex) GET, POST, PUT, DELETE / 같은 Path의 GET, PUT 이 있을 경우 하나의 설정에 GET, PUT으로 설정
      filters:
        - RewritePath=/기능path, /기능anotherPath # 다른 경로로 라우팅 필요할 경우 사용.
        - name: AuthenticationFilter # 토큰 체크 필터, proxy 되는 application에 userId, userRole을 넘겨주거나, 토큰값 인증, 권한 인가 처리가 필요할 경우 사용. 토큰 체크 필요 없을 경우 생략.
          args:
            allow-roles: # 허용되는 role
              - ADMIN
              - CS
              - MARKETING
              - SALES
              - SETTLE