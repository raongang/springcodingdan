

1. myBatis 기존의 방식
 - DAO 인터페이스 작성
 - Mapper XML 생성 및 sql 처리
 - DAO 인터페이스를 구현 클래스 작성
 
 
2. Mapper 인터페이스를 사용하는 경우
  - Mapper 인터페이스 작성
  - MyBatis 애노테이션 혹은 Mapper XML작성
  
  
3. 소스 범위
  - Mapper 인터페이스 작성 및 연결
  - Mapper 인터페이스와 XML을 같이 활용하기
  ※ mybatis-spring이 작동으로 Mapper 인터페이스를 구현한 프록시 객체를 생성해주기 때문.