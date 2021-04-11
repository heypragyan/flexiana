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

(defn create-access-control-header [origin]
  (let [allowed-origins (or  "*")
        origins (str/split allowed-origins #",")
        allowed-origin (some #{origin} origins)]
    {"Access-Control-Allow-Origin"  allowed-origin
     "Access-Control-Allow-Methods" "POST, GET, PUT, OPTIONS, DELETE"
     "Access-Control-Max-Age"       "3600"
     "Access-Control-Allow-Headers" "Authorization, Content-Type, x-requested-with"}))

(defn wrap-cors [handler]
  (fn [req]
    (let [origin (get (:headers req) "origin")
          response (handler req)]
      (assoc response :headers (merge (:headers response) (create-access-control-header origin))))))
