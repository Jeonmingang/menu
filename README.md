# MenuPlugin-ShiftF (Spigot 1.16.5, Java 8)

- Shift + F (웅크리기 + 손전환키) 를 누르면 GUI 메뉴가 열립니다.
- `PlayerSwapHandItemsEvent` 를 이용하므로, 손전환(기본 F) 동작은 메뉴가 열릴 때 취소됩니다.

## 빌드
```bash
mvn -q -e -DskipTests package
```
생성물: `target/MenuPlugin-ShiftF-1.0.0.jar`

## 설치
1. `MenuPlugin-ShiftF-1.0.0.jar` 를 `plugins/` 폴더에 넣고 서버 재시작
2. `plugins/MenuPlugin-ShiftF/config.yml` 수정 후 `/reload` 또는 서버 재시작

## 설정(config.yml)
- `menu.title`: 메뉴 제목 (색코드 `&` 사용)
- `menu.size`: 9,18,27,36,45,54 중 하나 (다르면 자동 반올림)
- `menu.items.*` 항목:
  - `slot`: 0부터 시작
  - `material`: 아이템 재질
  - `name`, `lore`: 표시 이름과 로어 (색코드 `&`)
  - `command`: 클릭 시 플레이어 권한으로 실행할 명령(앞의 `/`는 생략 가능)
  - `close`: 클릭 후 메뉴 닫기 여부

## 주의
- 서버 버전: **Java 8 / Spigot 1.16.5**
- 권한 필요 없음 (무권한 기본 동작)
- 다른 플러그인과 GUI 제목이 같지 않도록 주의하세요.
