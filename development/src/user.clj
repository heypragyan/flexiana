(ns user
  (:require [com.flexiana.rest-api.core :as core]))

(defn start! [port]
  (core/start! port))

(defn stop! []
  (core/stop!))

(comment
  (start! 6003)
  (stop!))
