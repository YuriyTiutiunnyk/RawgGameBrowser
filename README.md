# RAWG Games Browser

> 🎯 **This is a showcase project** demonstrating the architectural approaches, design patterns, and best practices I apply in my day-to-day Android development work. It is intentionally kept focused to highlight code quality over feature quantity.

An Android application for browsing and viewing details about video games using the [RAWG Video Games Database API](https://rawg.io/apidocs).

---

## 📱 Features

### Games List Screen
- Paginated list with **manual infinite scrolling** (`derivedStateOf` + threshold detection)
- Game image, name, and rating display
- Loading / Error / Empty states with retry

### Game Detail Screen
- Hero image with gradient overlay
- Full game info: rating, Metacritic, genres, platforms, developers
- Back navigation via TopAppBar

### Favorites Screen
- Saved games list with rating display
- Navigation to game details
- Decomposed UI following the same patterns as other screens

### Offline-First Architecture
- Room DB as **single source of truth**
- Games list: network-first with Room fallback on error
- Game details: Room-first with network on cache miss

---

## 🏗 Architecture

### Clean Architecture + MVI-flavored MVVM

A hybrid that takes the best from both patterns:
- From **MVVM** — `ViewModel` with `StateFlow`, minimal boilerplate
- From **MVI** — `sealed interface UiEvent` for typed actions, single immutable `UiState`, unidirectional data flow

```
User Action → UiEvent → ViewModel.handleEvent() → updateState { copy(...) } → StateFlow → Compose recomposes
```

### Data Flow

```
RAWG API → Retrofit → DataSource (DTO) → Mapper → Repository (Room ↔ Domain) → UseCase → ViewModel → Compose UI
```

### Layers

```
┌─────────────────────────────────────────────────────────┐
│  PRESENTATION        Compose Screens ↔ ViewModel        │
│                      (BaseVm + UiState + UiEvent)       │
├─────────────────────────────────────────────────────────┤
│  DOMAIN              Use Cases + Repository Interfaces   │
│                      (pure Kotlin, no Android deps)      │
├─────────────────────────────────────────────────────────┤
│  DATA                Repository Impl + DataSource        │
│                      + Room Entities + DTOs + Mappers    │
└─────────────────────────────────────────────────────────┘

Dependency Rule:  Presentation → Domain ← Data
```

### Module Structure

```
RawgGamesBrowser/
├── app/                    # Application entry point, Navigation, DI init
├── core/
│   ├── common/             # DataState, ErrorEntity, ExceptionHandler, Mapper interfaces
│   ├── domain/             # UseCase<Input, Output> contract
│   ├── network/            # Retrofit + OkHttp + Gson, NetworkExceptionHandler, ErrorMapper
│   ├── presentation/       # BaseVm, NetworkExecutor DSL, BaseScreen, common screens
│   └── ui/                 # Material 3 theme, Spacing/Dimens, reusable components (GameCard, NetworkImage)
└── feature/
    ├── games/              # Games feature — Data + Domain + Presentation (vertical slice)
    └── favorites/          # Favorites feature — Presentation (placeholder, demonstrates consistent patterns)
```

### Module Dependency Graph

```
                        ┌─────────┐
                        │   app   │
                        └────┬────┘
              ┌──────────────┼──────────────┐
              ▼              ▼              ▼
     ┌─────────────┐  ┌──────────┐  ┌──────────┐
     │  feature/   │  │  core/   │  │  core/   │
     │games,favs...│  │present.  │  │   ui     │
     └─────┬───────┘  └────┬─────┘  └──────────┘
             │              │
        ┌────┼──────────────┤
        ▼    ▼              ▼
   ┌────────┐ ┌────────┐ ┌────────┐
   │ core/  │ │ core/  │ │ core/  │
   │ domain │ │network │ │common  │
   └────────┘ └───┬────┘ └────────┘
                  ▼
              ┌────────┐
              │ core/  │
              │ common │
              └────────┘
```

> `core:presentation` does **NOT** depend on `core:network`. Error handling abstractions (`ErrorEntity`, `ExceptionHandler`, `ErrorMessageMapper`) live in `core:common`; implementations live in `core:network` — following the Dependency Inversion Principle.

---

## 🧩 Key Patterns & Decisions

### NetworkExecutor DSL
Custom Kotlin DSL that eliminates try/catch boilerplate across all ViewModels:
```kotlin
networkExecutor {
    onStart { updateState { copy(isLoading = true) } }
    execute { getGameDetailUseCase(gameId) }
    success { detail -> updateState { copy(gameDetail = detail) } }
    error { msg -> updateState { copy(error = msg) } }
}
```
Built with lambdas with receiver (`T.() -> Unit`), injected as a singleton via Koin.

### 3-Level Error Handling Pipeline
```
Raw Exception → ExceptionHandler.handle() → ErrorEntity → ErrorMessageMapper.map() → String → UI
```
- **Level 1:** Classification — `SocketTimeout → Timeout`, `UnknownHost → NoConnection`, `HttpException(500) → Server(500)`
- **Level 2:** User-friendly messages — `"Connection timed out..."`
- **Level 3:** ViewModel stores in `UiState`, Compose renders `ErrorScreen`

`ErrorEntity` is a sealed class hierarchy: `NoConnection`, `Server(code)`, `Timeout`, `Http(code)`, `Unknown`.

### Manual Pagination (not Paging 3)
Paging 3 was intentionally avoided — it introduces `PagingData`/`LazyPagingItems` into the Domain layer, violating the Dependency Rule, and creates a second source of truth outside of `UiState`.

Instead: ViewModel tracks `currentPage`, accumulates data on `LoadMore`, rolls back `currentPage--` on error. `InfiniteScrollEffect` composable uses `derivedStateOf` for efficient threshold detection.

### BaseVm\<UiState, UiEvent\>
Generic base ViewModel providing:
- `StateFlow<UIState>` — reactive, immutable state
- `handleEvent(UIEvent)` — typed event dispatch
- `updateState { copy(...) }` — reducer-like atomic updates
- `networkExecutor { }` — DSL access for async operations

### Offline-First Caching
| Screen | Strategy | Reason |
|--------|----------|--------|
| Games list | Network-first + Room fallback | Fresh data when online; cache when offline |
| Game details | Room-first + network on miss | Instant loading for previously viewed games |

Room schema uses `orderIndex` to preserve API ordering and `PageInfoEntity` to track pagination state.

### Type-Safe Navigation
Jetpack Navigation 2.8+ with `@Serializable` route classes — compile-time safety, no string-based routes:
```kotlin
@Serializable data class GameDetailRoute(val gameId: Int)
navController.navigate(GameDetailRoute(gameId = 3498))
```

---

## 🛠 Technology Stack

| Technology | Version | Purpose |
|---|---|---|
| **Kotlin** | 2.1.0 | Primary language |
| **Jetpack Compose** | BOM 2025.02.00 | Declarative UI |
| **Koin** | 4.0.0 | Dependency injection |
| **Coroutines + Flow** | 1.10.1 | Async programming, reactive state |
| **Retrofit + Gson** | 2.11.0 | REST API client |
| **OkHttp** | 4.12.0 | HTTP client, interceptors, logging |
| **Room + KSP** | 2.6.1 | Offline persistence, compile-time schema |
| **Coil 3** | 3.0.4 | Image loading (Compose-first, OkHttp integration) |
| **Navigation Compose** | 2.9.7 | Type-safe screen navigation |
| **Material 3** | via Compose BOM | Design system |
| **Kotlinx Serialization** | 1.7.3 | Route serialization |

---

## 🚀 Setup & Run

1. Clone the repository
2. Open in Android Studio → Sync → Run

> ℹ️ A test API key is already included in `gradle.properties` for convenience. You can replace it with your own from [rawg.io/apidocs](https://rawg.io/apidocs).

---

## 📖 Documentation

See [DOCUMENTATION_ENG.md](DOCUMENTATION_ENG.md) for an in-depth technical breakdown covering:
- Detailed module analysis with code examples
- Design decisions and trade-offs (Koin vs Hilt, Retrofit vs Ktor, Room vs DataStore, manual pagination vs Paging 3)
- Complete data flow walkthrough
- Kotlin Coroutines deep-dive (scopes, dispatchers, structured concurrency, Flow operators)
