# AssetTracker (Telegram-bot)
Телеграм бот **демо-счет** криптовалютной биржи. CRUD операции над активами, просмотр статистики по активам, торговля с нейросетью и возможность писать в поддержку с дальнейшей обработкой этих вопросов администратором через защищенный Spring Security сайт.
<details>
<summary>подробнее</summary>
подробная инфа
</details>

## ⚙Технологии
### приложение
- Spring Boot (Security, Web, Data, Cache)
- PostgreSQL
- Redis
- Docker
- MapStruct
- Lombok
- TelegramBots
### тесты
- JUnit
- Mockito
- TestContainers
- Liquibase
- Awaitility

## Панель администратора
![Image alt](https://github.com/jkefbq/AssetTrackerTelegramBot/raw/main/src/main/resources/static/admin-support-panel.png)
>Панель для обработки пользовательских вопросов или жалоб, ответ сразу отправляется нужному пользователю в чат. Реализован поиск всех вопросов какого-то юзера по его ID или по содержанию.

✅ Сайт защищен через Spring Security не только на уровне URL, но и на уровне методов.  
✅ Реализована защита от CSRF  

## Телеграм Бот

<img src="https://github.com/user-attachments/assets/92293095-9a2f-4704-a8f4-232031fad7d4" width="49%"> <img src="https://github.com/jkefbq/AssetTrackerTelegramBot/raw/main/src/main/resources/static/bag-menu-choose-assets-tg-bot.jpg" width="50%">
<img src="https://github.com/user-attachments/assets/efa41765-d76a-466c-8668-869e3b3777ac" width="50%"> <img src="https://github.com/user-attachments/assets/86e66fb3-3fca-447c-b922-4d7da944b9ec" width="49%">



