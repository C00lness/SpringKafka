@Controller - Возвращает Имя HTML-страницы (view), @ResponseBody нужен (если нужно вернуть данные) (Веб сайты) Работает с html через Thymeleaf и Model (ssr)
http://localhost:8080/api/hello @GetMapping("/hello")
@RestController - возвращает JSON / XML / текст, @ResponseBody включен.  Для REST API - основная логика бута
http://localhost:8080/api/hello @RequestMapping("/api") @GetMapping("/hello")

Бин — это объект, которым управляет Spring. Класс становится бином, если он помечен одной из аннотаций стереотипа (@Component, @Service, @Repository, @Controller) или объявлен через @Bean в конфигурации. Spring создаёт бины, настраивает их и внедряет зависимости через DI.

DI - 3 варианта, во всех нужен @Autowired кроме варианта когда конструктор единственный - этот вариант бэст практика для внедрения т к можно делать поля final, гарантирует что зависимости переданы при создании объекта, как-то упрощает тестирование.

Асинхронность в Spring включается через @EnableAsync. Методы с @Async выполняются в отдельном потоке. Возвращать результат можно через CompletableFuture. Это позволяет не блокировать основной поток, например, при долгих вычислениях или вызовах внешних API.
@GetMapping("/async")
public String async(@RequestParam int a, @RequestParam int b) throws ExecutionException, InterruptedException {
CompletableFuture<Integer> future = mathService.addAsync(a, b);
return "Результат: " + future.get(); // ждём завершения
}

@Service — это аннотация, которая говорит Spring: «Этот класс — бин, в нём лежит бизнес-логика, управляй им, @Repository - для БД
