
# Filmfolio App

An easy-to-use app to browse Now Playing, Popular, Upcoming, and Top Rated movies from around the world.


## Tech Stack

- MVVM architecture design (Model View ViewModel)
- Repository Pattern
- Coroutine Flow
- Koin Dependency Injection
- Jetpack Paging
- Coil Image Loader
- Retrofit
- JUnit4
- Shimmer

## How to use the app

- Splash Screen and Onboarding: The app will initially display a splash screen followed by an onboarding process.

- Home Screen Navigation: After onboarding, you will be navigated to the Home Screen where you can view a list of movies as well as those you have added to your favorites.

- Movie Details: By clicking on a movie poster or the info button, a detailed dialog about the movie will be displayed.

- See More Section: If you click on "See More" in any section of the home feed, it will open a paginated list of movies based on the section type.

## Filmfolio API Used
[TMDB api](https://developer.themoviedb.org/reference/intro/getting-started)

#### Get Now Playing Movie

```http
GET movie/now_playing
```

| Query | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `api_key` | `string` | **Required**. TMDB Api Key   |
| `page` | `int` |  Page for response  |
| `language` | `string` | Language for response  |



#### Get Upcoming Movie

```http
GET movie/upcoming
```

| Query | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `api_key` | `string` | **Required**. TMDB Api Key   |
| `page` | `int` |  Page for response  |
| `language` | `string` | Language for response  |


#### Get Popular Movie

```http
GET movie/popular
```

| Query | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `api_key` | `string` | **Required**. TMDB Api Key   |
| `page` | `int` |  Page for response  |
| `language` | `string` | Language for response  |



#### Get Top Rated Movie

```http
GET movie/top_rated
```

| Query | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `api_key` | `string` | **Required**. TMDB Api Key   |
| `page` | `int` |  Page for response  |
| `language` | `string` | Language for response  |

