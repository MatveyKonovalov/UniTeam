# UniTeam: запуск backend и отдельного frontend-сервера

Frontend теперь запускается отдельно на Node.js. Он раздаёт веб-интерфейс на порту `3000` и проксирует запросы `/api/...` в Spring Boot backend (по умолчанию `http://localhost:8080`). Дополнительные npm-пакеты не нужны.

## Требования

- JDK 21;
- PostgreSQL с настройками из `src/main/resources/application.properties`;
- установленный Gradle (или запуск через IntelliJ IDEA);
- Node.js 18 или новее.

## 1. Запусти базу данных

Убедись, что PostgreSQL работает и параметры подключения в `src/main/resources/application.properties` соответствуют твоей конфигурации.

## 2. Запусти Java backend

В корне проекта `m_konovalov`:

```bash
gradle bootRun
```

Либо открой проект в IntelliJ IDEA и запусти `WebplatformApplication`. По умолчанию backend слушает `http://localhost:8080`.

## 3. Запусти отдельный Node.js сервер

Открой второй терминал и перейди в папку `frontend-server`:

```bash
cd frontend-server
npm start
```

В браузере открой **http://localhost:3000**.

Проверить, что Node-сервер работает, можно по адресу `http://localhost:3000/health`.

## Настройка адреса backend

По умолчанию прокси направляет API-запросы на `http://localhost:8080`. Если Java backend запущен на другом адресе, укажи переменную окружения перед запуском.

Linux/macOS:

```bash
BACKEND_URL=http://localhost:8081 PORT=3000 npm start
```

Windows PowerShell:

```powershell
$env:BACKEND_URL="http://localhost:8081"
$env:PORT="3000"
npm start
```

## Как устроен запуск

- `frontend-server/server.js` — HTTP-сервер Node.js и API-прокси;
- `frontend-server/public/index.html` — интерфейс;
- `/api/v0/...` — запросы, передаваемые Java backend без изменения пути и тела запроса;
- `src/main/java/...` — существующий Spring Boot backend.

Node.js-сервер сам по себе не заменяет backend и базу данных: для авторизации и данных проектов должны быть запущены Spring Boot и PostgreSQL.

## Если не работает

- `EADDRINUSE`: порт занят; запусти с `PORT=3001` (Windows PowerShell: `$env:PORT="3001"`).
- `502` или ошибка подключения: запусти Java backend и проверь `BACKEND_URL`.
- Backend не запускается: проверь PostgreSQL и настройки в `application.properties`.
