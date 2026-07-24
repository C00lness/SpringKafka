@Controller - Возвращает Имя HTML-страницы (view), @ResponseBody нужен (если нужно вернуть данные) (Веб сайты) Работает с html через Thymeleaf и Model (ssr)
http://localhost:8080/hello @GetMapping("/hello")
@RestController - возвращает JSON / XML / текст, @ResponseBody включен.  Для REST API - основная логика бута
http://localhost:8080/api/hello @RequestMapping("/api") @GetMapping("/hello")
@PostMapping для пост запросов (get - получить данные, загрузить страницу, параметры в URL, Post - отправить данные, добавить, параметры в теле запроса)
Бин — это объект, которым управляет Spring. Класс становится бином, если он помечен одной из аннотаций стереотипа (@Component, @Service, @Repository, @Controller) или объявлен через @Bean в конфигурации. Spring создаёт бины, настраивает их и внедряет зависимости через DI.

@RequestMapping - универсальный - для любых запросов, @GetMapping - специальный для GET
В @RestController все методы автоматически оборачиваются в @ResponseBody. Поэтому они возвращают данные, а не HTML.
@Data похоже на Data Class  в Kotlin
DI - 3 варианта, во всех нужен @Autowired кроме варианта когда конструктор единственный - этот вариант бэст практика для внедрения т к можно делать поля final, гарантирует что зависимости переданы при создании объекта, как-то упрощает тестирование.

Асинхронность в Spring включается через @EnableAsync. Методы с @Async выполняются в отдельном потоке. Возвращать результат можно через CompletableFuture. Это позволяет не блокировать основной поток, например, при долгих вычислениях или вызовах внешних API.
@GetMapping("/async")
public String async(@RequestParam int a, @RequestParam int b) throws ExecutionException, InterruptedException {
CompletableFuture<Integer> future = mathService.addAsync(a, b);
return "Результат: " + future.get(); // ждём завершения
}

@Service — это аннотация, которая говорит Spring: «Этот класс — бин, в нём лежит бизнес-логика, управляй им, @Repository - для БД
JPA (Java Persistence API) — это набор правил (спецификация) для работы с базами данных в Java. В нём описано:
какие аннотации использовать (@Entity, @Id, @Column)
как должен выглядеть код для работы с БД
как должны называться методы
Но JPA не умеет ничего делать сам — это просто документ, в котором написано: «Делайте так»
Hibernate — это конкретная библиотека, которая реализует правила JPA. Она:
превращает Java-объекты в SQL-запросы
создаёт таблицы, выполняет запросы к БД, управляет связями между таблицами

Когда ты пишешь:
java
@Entity
public class Message { ... }
JPA говорит: «Так должно выглядеть». Hibernate говорит: «Я создам таблицу message с колонками id, text, timestamp».
JPA — это стандарт для работы с БД в Java, а Hibernate — это его реализация. В Spring Boot я использовал Spring Data JPA, которая включает Hibernate под капотом. Это позволило работать с БД через Java-объекты и аннотации, без написания SQL вручную
Индексация - как указатель в книге (оглавление). CREATE INDEX idx_messages_text ON messages(text);
EXPLAIN — это команда, которая показывает, как база планирует выполнять запрос.

sql
EXPLAIN SELECT * FROM messages WHERE text = 'HelloKafka';
Вывод покажет:

Seq Scan — полное сканирование (плохо, если таблица большая)
Index Scan — используется индекс (хорошо)
Индексы ускоряют поиск по таблицам. Самый распространённый — B-Tree. Создаю индексы на поля, которые часто используются в WHERE, JOIN, ORDER BY. Для проверки использую EXPLAIN — он показывает, использует ли база индекс или сканирует всю таблицу.
Микросервисы — это когда система разделена на независимые сервисы. 
Каждый сервис отвечает за свою функциональность, имеет свою БД и общается с другими 
через REST или Kafka. В отличие от многомодульности в Android, 
где модули живут в одном приложении и общаются через DI, 
микросервисы живут на разных серверах и общаются через сеть.

Делаем просто сущность: 
@Entity
@Table(name = "messages")
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column

Далее создаем репозиторий:
@Repository
public interface MessageRepository extends JpaRepository<Message, Long> { } (уже есть методы save(), findAll(), findById())

И потом рабоатем с ним как с обычным классом 

Kafka — это распределённая очередь сообщений. Producer отправляет сообщения в Topic, Consumer читает их оттуда. 
Kafka хранит сообщения в партициях и гарантирует порядок. Используется для асинхронного обмена между сервисами, обработки потоков данных и highload-систем.
Apache Kafka — это распределённая платформа для потоковой передачи данных.
Простыми словами: очередь сообщений между сервисами с возможностью хранить их некоторое время.

📌 Основные понятия
Термин	Что это
Topic	«Ящик» или канал, куда отправляются сообщения (например, orders, logs)
Producer	Отправитель — кладёт сообщение в Topic
Consumer	Получатель — читает сообщение из Topic
Partition	Topic разбит на части для масштабирования
Offset	Номер сообщения внутри партиции
Broker	Сервер Kafka (может быть несколько в кластере)
Consumer Group	Группа Consumer'ов, которые читают один Topic — каждое сообщение доставляется только одному из них
Хранит сообщения заданное время (даже после прочтения)

Гарантирует порядок внутри партиции
Выдерживает огромные нагрузки (миллионы сообщений в секунду)
Связывает сервисы асинхронно (Producer не ждёт Consumer)

🆚 Отличие от очереди (RabbitMQ)
Очередь (RabbitMQ)	Kafka
Удаляет сообщения после прочтения	Хранит сообщения заданное время
Не гарантирует порядок	Гарантирует порядок внутри партиции
Средняя производительность	Очень высокая производительность
Для задач (Jobs)	Для потоков событий
🎯 Как сказать на собеседовании
«Kafka — это распределённая очередь сообщений. Producer отправляет сообщения в Topic, Consumer читает их оттуда. Kafka хранит сообщения в партициях и гарантирует порядок. Используется для асинхронного обмена между сервисами, обработки потоков данных и highload-систем.»

Для кафки: добавляем в пом и в application.properties
1. MessageController отправляет запрос такого вида: kafkaProducerService.sendMessage("user-check", message); 
2. kafkaProducerService -> kafkaTemplate.send(topic, message);
3. UserService слушает как @KafkaListener(topics = "user-check")

Для ws добавили просто конфиг
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Клиенты подключаются по адресу /ws
        registry.addEndpoint("/ws").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // Клиенты подписываются на /topic/messages
        registry.enableSimpleBroker("/topic");
        // Клиенты отправляют сообщения на /app/**
        registry.setApplicationDestinationPrefixes("/app");
    }
}
messagingTemplate.convertAndSend("/topic/messages", message); //Service/KafkaConsumer
WebSocket — это постоянное двустороннее соединение. В проекте я использовал Spring WebSocket с STOMP. Клиенты подключаются по адресу /ws, 
подписываются на топик, а сервер через SimpMessagingTemplate отправляет сообщения всем подписчикам, например, когда приходит новое сообщение из Kafka.

### 🟢 Блок 1: Основы и Ядро (Core)

**1. Чем Spring Boot отличается от обычного Spring?**
*   **Spring** — это огромный фреймворк, требующий много ручной настройки (XML или JavaConfig).
*   **Spring Boot** — это надстройка, которая предоставляет *Convention over Configuration* (Соглашение важнее конфигурации). Убирает бойлерплейт.

**2. Три кита Spring Boot:**
*   **Auto-configuration (Автоконфигурация):** Spring Boot смотрит на `classpath` (какие библиотеки добавлены) и автоматически настраивает бины. *Пример: добавили H2 в зависимости -> Spring сам создал DataSource и EntityManager.*
*   **Starters (Стартеры):** Наборы готовых зависимостей. Например, `spring-boot-starter-web` тянет Tomcat, Spring MVC, Jackson.
*   **Spring Boot CLI / Actuator:** Actuator (`spring-boot-starter-actuator`) добавляет production-ready фичи: эндпоинты `/actuator/health`, `/metrics`, `/env` для мониторинга.

**3. Как работает Auto-configuration под капотом?**
*   Аннотация `@EnableAutoConfiguration` (включена внутри `@SpringBootApplication`).
*   Она использует `@Import(AutoConfigurationImportSelector.class)`.
*   Селектор читает файл `META-INF/spring.factories` (в Spring Boot 2.x) или `META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports` (в Spring Boot 3.x), находит классы автоконфигурации и применяет их, если выполнены условия (`@ConditionalOnClass`, `@ConditionalOnMissingBean`, `@ConditionalOnProperty`).

---

### 🟡 Блок 2: Бины и Внедрение Зависимостей (DI)

**1. Стереотипы (аннотации):**
*   `@Component` — базовый компонент.
*   `@Service` — бизнес-логика (семантически то же, что Component, но для читаемости).
*   `@Repository` — слой доступа к данным (DAO). **Важно:** автоматически переводит SQL-исключения в Spring `DataAccessException`.
*   `@Controller` / `@RestController` — веб-слой.

**2. `@Component` vs `@Bean`:**
*   `@Component` вешается на сам класс (работает через сканирование пакетов `@ComponentScan`).
*   `@Bean` вешается на метод внутри класса с `@Configuration`. Используется, когда нужно создать бин для **стороннего класса** (например, из чужой библиотеки), который нельзя пометить `@Component`.

**3. scopes (Области видимости бинов):**
*   `singleton` (по умолчанию) — один экземпляр на весь контекст.
*   `prototype` — новый экземпляр при каждом запросе.
*   `request`, `session`, `application` — только для Web-контекста.

---

###  Блок 3: Конфигурация и Профили

**1. `@Value` vs `@ConfigurationProperties`:**
*   `@Value("${app.name}")` — внедряет одно конкретное значение. Нет строгой типизации и валидации.
*   `@ConfigurationProperties(prefix = "app")` — маппит группу свойств в Java-объект (POJO). **Интервьюерский фаворит:** поддерживает валидацию (`@Validated`), работает с коллекциями и сложными структурами.

**2. Профили (Profiles):**
*   Используются для разных окружений (dev, test, prod).
*   Файлы: `application-dev.yml`, `application-prod.yml`.
*   Активация: `spring.profiles.active=dev` в properties или через аргументы JVM `-Dspring.profiles.active=dev`.
*   Аннотация `@Profile("dev")` на бинах или конфигурациях.

---

###  Блок 4: Работа с данными (Spring Data JPA)

**1. Репозитории:**
*   `CrudRepository` — базовые CRUD.
*   `PagingAndSortingRepository` — добавляет пагинацию и сортировку.
*   `JpaRepository` — наследует всё вышеперечисленное + добавляет JPA-специфичные методы (flush, deleteInBatch).

**2. `@Transactional` (ОЧЕНЬ ЧАСТЫЙ ВОПРОС):**
*   Управляет транзакциями. Открывает транзакцию до метода, коммитит после, делает rollback при unchecked exceptions (RuntimeException).
*   **Почему может не работать?**
    1. Метод не `public` (прокси Spring не видит private/protected).
    2. Вызов `@Transactional` метода из другого метода **того же класса** (self-invocation bypasses proxy).
    3. Исключение перехвачено внутри метода (`try-catch`).
    4. Используется не тот менеджер транзакций.

**3. Проблема N+1 и как её решать:**
*   *Проблема:* При загрузке списка сущностей с `@OneToMany(LAZY)` Hibernate делает 1 запрос на список и N запросов на дочерние сущности.
*   *Решение:* `JOIN FETCH` в JPQL, `@EntityGraph`, или использование DTO-проекций.

---

### 🟣 Блок 5: REST и Web

**1. Аннотации контроллеров:**
*   `@RestController` = `@Controller` + `@ResponseBody` (возвращает JSON/XML, а не имя view).
*   `@RequestMapping` (базовый путь), `@GetMapping`, `@PostMapping` и т.д.
*   `@PathVariable` (параметр в URL: `/users/{id}`), `@RequestParam` (query-параметр: `/users?id=1`), `@RequestBody` (тело запроса, парсится через Jackson).

**2. Глобальная обработка ошибок:**
*   `@ControllerAdvice` + `@ExceptionHandler`. Позволяет централизованно ловить исключения и возвращать красивый JSON-ответ (например, RFC 7807 Problem Details).

---

### 🔵 Блок 6: Тестирование

*   `@SpringBootTest` — поднимает **весь** контекст приложения (тяжелые интеграционные тесты).
*   `@WebMvcTest` — тестирует только слой контроллеров (быстро, не поднимает БД).
*   `@DataJpaTest` — тестирует только слой репозиториев (поднимает H2 в памяти).
*   `@MockBean` — заменяет реальный бин в контексте Spring на Mockito-мок.

---

###  Блок 7: Топ каверзных вопросов (Gotchas)

1.  **Circular Dependency (Круговая зависимость):** Бин A требует Бин B, а Бин B требует Бин A.
    *   *Как решить:* Рефакторинг (вынести общую логику в Бин C), использование `@Lazy` на одном из полей, или переход на Setter Injection вместо Constructor Injection.
2.  **Как Spring Boot запускается?**
    *   Метод `SpringApplication.run()`. Он создает `ApplicationContext`, запускает embedded server (Tomcat/Jetty/Undertow), применяет автоконфигурацию и вызывает `ApplicationRunner`/`CommandLineRunner`.
3.  **В чем разница между `application.properties` и `application.yml`?**
    *   Функционально они одинаковы. YAML удобнее для иерархических структур и списков, Properties — проще и линейнее. Если есть оба файла, Properties имеет приоритет (в старых версиях) или они мержатся.
4.  **Что такое Spring Boot DevTools?**
    *   Набор инструментов для разработки. Главная фича — **Automatic Restart** (быстрая перезагрузка при изменении классов) и **LiveReload**.

---

**💡 Совет перед собесом:**
Не просто заучивайте аннотации. Интервьюеры обожают спрашивать: *"А что произойдет, если...?"* или *"Как это работает внутри?"*. Всегда пытайтесь связать ответ с жизненным циклом бина, прокси-объектами или тем, как работает classloader.

API Gateway Cloud - просто напросто делаем application.yaml (новый проект) и пишем туда
spring:
cloud:
gateway:
routes:
- id: user-service
uri: http://localhost:8081
predicates:
- Path=/api/users/**          # запросы на /api/users/* идут в user-service
- id: message-service
uri: http://localhost:8082
predicates:
- Path=/api/messages/**       # запросы на /api/messages/* идут в message-service
YAML удобнее для сложных конфигураций, а properties — проще для простых.
