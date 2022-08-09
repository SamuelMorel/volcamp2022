# Backend API

This API has been built for a demonstration purpose only, with the objective to  
compare how you can use a generic backend in REST and in GraphQL, relying on a 
relational database.

The relational model is freely inspired from Volcamp's talk display.

## Running the application in dev mode


> **_PREREQUISITE:_**  The identity provider used in this project is **Auth0**.  
In order to get the project fully functional, you must create:
>  - a `.env` file containing these keys 
> ```# AUTH0 config
>  AUTH0_AUTH_URL=<The Realm URL in Auth0>
>  AUTH0_CLIENT_ID=<Your client ID>
>  AUTH0_CLIENT_SECRET=<Your client secret>
>  AUTH0_CLIENT_AUDIENCE=<the name of your API in Auth0>
> ```
> - Two permissions in you API called `read_only` and `read_write`

You can run your application in dev mode that enables live coding using:

```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.
