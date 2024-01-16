#!/usr/bin/env bash

REPOSITORY=/home/ubuntu/studay
cd $REPOSITORY

APP_NAME=studay
JAR_NAME=$(ls $REPOSITORY/build/libs/*.jar | grep 'SNAPSHOT.jar' | tail -n 1)
JAR_PATH=$JAR_NAME

# systemd 서비스를 중지합니다.
sudo systemctl stop $APP_NAME.service

# 심볼릭 링크를 이용하여 새로운 JAR 파일로 서비스를 업데이트합니다.
ln -sf $JAR_PATH /home/ubuntu/studay/$APP_NAME-latest.jar

# systemd 서비스를 시작합니다.
sudo systemctl start $APP_NAME.service

# 서비스의 상태를 확인합니다.
sudo systemctl status $APP_NAME.service
