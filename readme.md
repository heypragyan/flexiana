# Flexiana Project

The project follows polylith architecture. 

## Development Flow

### Start Development Server
``` sh
clj -M:dev # Launch repl
user> (start! 8090) # Starts the development server
```

``` sh
curl http://localhost:8090/api/scrambles/:str-1/:str-2
```


### Start Frontend

``` sh
npx shadow-cljs watch main
```

Open `http://localhost:8000/` in browser

### Production Build
