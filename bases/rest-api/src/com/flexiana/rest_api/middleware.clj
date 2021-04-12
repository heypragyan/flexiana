(ns com.flexiana.rest-api.middleware
  (:require [clojure.string :as str]
            [com.flexiana.log.interface :as log]))

(defn wrap-exceptions [handler]
  (fn [req]
    (try
      (handler req)
      (catch Exception e
        (let [message (str "An unknown exception occurred.")]
          (log/error e message)
          {:status 500
           :body   {:errors {:other [message]}}})))))

(defn create-access-control-header [_]
  {"Access-Control-Allow-Origin"  "http://localhost:8000"
   "Access-Control-Allow-Methods" "POST, GET, PUT, OPTIONS, DELETE"
   "Access-Control-Max-Age"       "3600"
   "Access-Control-Allow-Headers" "*"
   "Access-Control-Allow-Credentials" "true"})

(defn wrap-cors [handler]
  (fn [req]
    (let [origin (get (:headers req) "origin")
          response (handler req)]
      (assoc response :headers (merge (:headers response) (create-access-control-header origin))))))
