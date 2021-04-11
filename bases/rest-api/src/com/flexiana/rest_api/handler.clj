(ns com.flexiana.rest-api.handler
  (:require [clojure.edn :as edn]
            [com.flexiana.scramble.interface :as scramble]
            [clojure.spec.alpha :as s]))

(defn- parse-query-param [param]
  (if (string? param)
    (try
      (edn/read-string param)
      (catch Exception _
        param))
    param))

(defn- handle
  ([status body]
   {:status (or status 404)
    :body   body})
  ([status]
   (handle status nil)))

(defn options [_]
  (handle 200))

(defn health [_]
  (handle 200 {:environment :development}))

(defn other [_]
  (handle 404 {:errors {:other ["Route not found."]}}))

(defn scramble? [req]
  (let [{:keys [str-1 str-2]} (-> req :params)]
    (handle 200 {:str-1 str-1 :str-2 str-2 :scramble? (scramble/scramble? str-1 str-2)})))
