(ns com.flexiana.app.core
  (:require-macros
   [cljs.core.async.macros :refer [go]]
   [reagent.ratom :refer [reaction]])
  (:require
   [reagent.core :as reagent :refer [atom]]
   [reagent.dom :refer [render]]
   [cljs-http.client :as http]
   [cljs.core.async :refer [<!]]))

(enable-console-print!)

(defn log [& xs]
  (mapv #(js/console.log %) xs))

(defn tval [e]
  (.-value (.-target e)))

(defn lower-alpha? [x]
  (re-matches #"[a-z]*" x))

(defn main []

  (let [inner-state
        (atom {:str-1 "hello"
               :str-2 "world"
               :scramble? false
               :warning nil})

        refresh
        (fn []
          (go (let [response
                    (<! (http/get
                         (str "http://localhost:8090/api/scrambles/" (:str-1 @inner-state) "/" (:str-2 @inner-state))))]
                (swap! inner-state assoc
                       :scramble? (:scramble? (:body response))))))

        set-str-field
        (fn [k e]
          (swap! inner-state assoc k (tval e))
          (if (and (lower-alpha? (:str-1 @inner-state))
                   (lower-alpha? (:str-2 @inner-state)))
            (do (swap! inner-state dissoc :warning)
                (refresh))
            (swap! inner-state assoc :warning
                   "Lower-case alpha numeric chars only!")))]

    (fn []
      [:div

       [:div.text-6xl.font-normal.leading-normal.mt-0.mb-2.text-pink-800 "Scramble?"]

       [:div.text-xl.font-normal.leading-normal.mt-0.mb-2.text-black-800
        "Does a portion of str1 characters can be rearranged to match str2?"]

       [:input.px-3.py-3.placeholder-blueGray-300.text-blueGray-600.relative.rounded.text-sm.border-0.shadow.outline-none.focus:outline-none.focus:ring
        {:type "text"
         :value (:str-1 @inner-state)
         :on-change #(set-str-field :str-1 %)}]

       [:input.px-3.py-3.placeholder-blueGray-300.text-blueGray-600.relative.rounded.text-sm.border-0.shadow.outline-none.focus:outline-none.focus:ring
        {:type "text"
         :value (:str-2 @inner-state)
         :on-change #(set-str-field :str-2 %)
         :class
         (cond
           (:warning @inner-state) "bg-red-100"
           (:scramble? @inner-state) "bg-green-400"
           :else "bg-red-500")}]

       [:div.bg-red-100 (:warning @inner-state)]])))

(defn ^:export mount-root []
  (render [main] (.getElementById js/document "app")))

(defn ^:export start []
  (mount-root))

(defn restart
  [])
