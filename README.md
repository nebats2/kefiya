# kefiya
- sample github for cart pricing and reservation
- A classic Layered (n-tier) architecture using MVC + Repository.
- Controller → Service → Repository → DB (and back), with mapping between Entity ↔ DTO/Request and Response models around the
- (For API documentation pls refer - swagger : \n http://[IP]:8082/kefiya/swagger-ui/index.html#/ )

# Features
 - Product
 - Cart/Order
 - Rule Engine
# ProductService

- create(ProductCreateModel) – creates product, wraps DB errors

- detail(id) – fetch by id (404 on missing)

- listAll(pageNumber) – 0-based paging, maps to DTO Page<ProductOutModel)

# OrderService

- createQuote(CartRequest) – validates items & stock, runs pricing rules via RuleEngine, persists a pending Order

- confirmCart(CartResponse) – validates order by idempotency key, deducts stock per item, marks order CONFIRMED

# RuleEngine

- Item-level rules then cart-level rules
- BUY_X_GET_Y → Y item becomes free when present
- PERCENTAGE_OFF_CATEGORY → % off per item category
- BULK_DISCOUNT → % off on final cart price

# Data model highlights

- PromotionEntity has composite uniqueness: (x_product_id, y_product_id, enabled)

Unit Tests (JUnit + Mockito)
ProductServiceTest

create() success + wraps DataIntegrityViolationException

detail() found/missing

listAll() maps to DTO or throws on empty

# RuleEngineTest
- ProductServiceTest
  create() success + wraps DataIntegrityViolationException
  detail() found/missing 
- RuleEngineTest:
   Verifies combined promos: BuyXGetY + Category% + Bulk%
   Example expectation: A(2×100) with 10% off + B(1×50) free →
   total=250, final=(200*0.9 + 0)*0.95 = 171

-  CartServiceTest
    Mocks RuleEngine and ensures createQuote returns promotion-computed CartResponse
   
# Tech Stack
- Spring Boot 3, Spring Data JPA, H2

- springdoc-openapi (Swagger UI)

- JUnit 5, Mockito, AssertJ

# Run CLI
- Build : ./gradlew clean build
- Ttesting: ./gradlew test
- Run : ./gradlew bootrun

# API Information:
- Server: server.port=8082 context path /kefiya    (E.g. on localhost http://localhost:8082/kefiya
- swagger :  http://localhost:8082/kefiya/swagger-ui/index.html#/ 
