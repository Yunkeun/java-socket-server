# java-socket-server
사용 언어: Java
개발 환경: IntelliJ
github: https://github.com/Yunkeun/java-socket-server
빌드 및 실행 방법: 
- 서버 실행 방법
1. git clone https://github.com/Yunkeun/java-socket-server.git
2. cd java-socket-server
3. 빌드
- 서버측
  - % cd server
  - % ./gradlew build clean
  - % ./gradlew build
- 클라이언트측
  - % cd ./../client
  - % ./gradlew build clean
  - % ./gradlew build
4. 실행
- 최상위 디렉토리로 이동한다.
	- % cd .. <br>
- 서버측
  - % java -cp ./server/build/libs/java-socket-server-0.0.1-SNAPSHOT.jar com.yoonveloping.javasocketserver.ChatServerApplication
- 클라이언트측 (새로운 터미널 탭)
  - % java -cp ./client/build/libs/java-socket-client-1.0-SNAPSHOT.jar com.yoonveloping.javasocketclient.ClientApplication
5. 이후 이름 입력 후 채팅창 입장
