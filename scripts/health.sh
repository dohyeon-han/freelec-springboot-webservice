#!/usr/bin/env bash

ABSPATH=$(readlink -f "$0")
ABSDIR=$(dirname "$ABSPATH")
source "$ABSDIR"/profile.sh
source "$ABSDIR"/switch.sh

echo "> Health check start"
echo "> IDLE_PORT: $IDLE_PORT"
echo "> curl -s http://localhost:$IDLE_PORT/profile"

for RETRY_CNT in {1..10}
do
  RESPONSE=$(curl -s http://localhost:"$IDLE_PORT"/profile)
  UP_CNT=$(echo "$RESPONSE" | grep "real" | wc -l)

  if [ "$UP_CNT" -ge 1 ]
  then
    echo "> Health check 성공"
    switch_proxy
    break
  else
    echo "> Health check 응답 없음"
    echo "> Health check: $RESPONSE"
  fi

  if [ "$RETRY_CNT" -eq 10 ]
  then
    echo "> Health check 실패"
    echo "> nginx에 연결하지 않고 종료"
    exit 1
  fi

  echo "> Health check 연결 실패. 재시도.."
  sleep 10
done