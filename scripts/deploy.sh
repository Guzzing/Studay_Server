#!/usr/bin/env bash

# JAR 파일 경로 설정
JAR_PATH="/home/ubuntu/studay/build/libs/studayserver-0.0.1-SNAPSHOT.jar"

# 실행 중인 애플리케이션의 PID 찾기
CURRENT_PID=$(pgrep -f $JAR_PATH)

# 실행 중인 애플리케이션이 있으면 종료
if [[ -n $CURRENT_PID ]]; then
  echo "종료할 프로세스 PID: $CURRENT_PID"
  sudo kill $CURRENT_PID
  
  # 프로세스가 종료될 때까지 대기
  while ps -p $CURRENT_PID &>/dev/null; do
    sleep 1
  done
fi

# 애플리케이션 실행
nohup java -jar $JAR_PATH &>/dev/null &
