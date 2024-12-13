# 도커 실행 명령어

## 도커 실행

```bash
docker-compose up -d
```

## 도커 종료

```bash
docker-compose down
```

## 도커 볼륨 전체 삭제

이전에 생성된 볼륨을 삭제해서 완전 초기화 한다.
삭제 후 다시 실행하면 완전 초기화가 된다.

```bash
docker volume prune --all
```