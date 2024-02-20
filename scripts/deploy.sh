#!/usr/bin/env bash

# 기본 설정: 리포지토리와 애플리케이션 이름
REPOSITORY="/home/ubuntu/studay"
APP_NAME="studay"

# 최신 JAR 파일 경로 확인
cd "$REPOSITORY"
LATEST_JAR=$(ls ./build/libs/*SNAPSHOT.jar | tail -n 1)

# 실행 중인 애플리케이션 종료 로직
CURRENT_PID=$(pgrep -f "$LATEST_JAR")
if [[ -n $CURRENT_PID ]]; then
  echo "종료할 프로세스 PID: $CURRENT_PID"
  kill $CURRENT_PID
  # 프로세스 종료 대기
  while ps -p $CURRENT_PID &>/dev/null; do 
    sleep 1
  done
fi

# 심볼릭 링크 업데이트
SYMLINK_PATH="/home/ubuntu/studay/$APP_NAME-latest.jar"
ln -sf "$LATEST_JAR" "$SYMLINK_PATH"

# 애플리케이션 백그라운드 실행
nohup java -jar "$SYMLINK_PATH" &>/dev/null &
