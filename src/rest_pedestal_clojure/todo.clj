(ns rest-pedestal-clojure.todo
  (:require [rest-pedestal-clojure.database :as db]
            [cheshire.core :as cheshire]
            ))


(defn index-handler [request]
  ;; (clojure.pprint/pprint (db/find-todo))
  {:status  200
   :headers {"Content-Type" "application/json"}
   :body    (cheshire/generate-string {:msg "hello"})})

(defn find-handler [request]
  (clojure.pprint/pprint (-> request :path-params))
  {:status  200
   :headers {"Content-Type" "application/json"}
   :body    "todo-find-handler"})

(defn insert-handler [request]
  (clojure.pprint/pprint (-> request :json-params))
  {:status  200
   :headers {"Content-Type" "application/json"}
   :body    "todo-insert-handler"})

(defn update-handler [request]
  (clojure.pprint/pprint (-> request :path-params))
  (clojure.pprint/pprint (-> request :json-params))
  {:status  200
   :headers {"Content-Type" "application/json"}
   :body    "todo-update-handler"})

(defn delete-handler [request]
  (clojure.pprint/pprint (-> request :path-params))
  {:status  200
   :headers {"Content-Type" "application/json"}
   :body    "todo-delete-handler"})



