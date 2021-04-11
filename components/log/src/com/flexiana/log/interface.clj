(ns com.flexiana.log.interface
  (:require [com.flexiana.log.config :as config]
            [com.flexiana.log.core :as core]))

(defn init []
  (config/init))

(defmacro info [& args]
  `(core/info ~args))

(defmacro warn [& args]
  `(core/info ~args))

(defmacro error [& args]
  `(core/error ~args))
