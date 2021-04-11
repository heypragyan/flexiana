(ns com.flexiana.rest-api.api
  (:require [com.flexiana.rest-api.handler :as h]
            [com.flexiana.rest-api.middleware :as m]
            [com.flexiana.log.interface :as log]
            [compojure.core :refer [routes wrap-routes defroutes GET POST PUT DELETE ANY OPTIONS]]
            [ring.logger.timbre :as logger]
            [ring.middleware.json :as js]
            [ring.middleware.keyword-params :as kp]
            [ring.middleware.multipart-params :as mp]
            [ring.middleware.nested-params :as np]
            [ring.middleware.params :as pr]))

(defroutes public-routes
  (OPTIONS "/**"                              [] h/options)
  (GET     "/api/health"                      [] h/health)
  (GET    "/api/scrambles/:str-1/:str-2"     [] h/scramble?))

(defroutes other-routes
  (ANY     "/**"                              [] h/other))

(def ^:private app-routes
  (routes
   public-routes
   other-routes))

(def app
  (-> app-routes
      logger/wrap-with-logger
      kp/wrap-keyword-params
      pr/wrap-params
      mp/wrap-multipart-params
      js/wrap-json-params
      np/wrap-nested-params
      m/wrap-exceptions
      js/wrap-json-response
      m/wrap-cors))

(defn init []
  (try
    (log/init)
    (log/info "Initialized server.")
    (catch Exception e
      (log/error e "Could not start server."))))

(defn destroy []
  (log/info "Destroyed server."))
