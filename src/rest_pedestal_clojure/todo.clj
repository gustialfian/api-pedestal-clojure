(ns rest-pedestal-clojure.todo
  (:require [rest-pedestal-clojure.database :as db]
            [cheshire.core :as cheshire]))


(defn index-handler [request]
  {:status  200
   :headers {"Content-Type" "application/json"}
   :body    (cheshire/generate-string (db/find-todo))})

(defn find-handler [request]
  (clojure.pprint/pprint (-> request :path-params :id))
  (let [id (Integer/parseInt (-> request :path-params :id))]
    {:status  200
     :headers {"Content-Type" "application/json"}
     :body    (cheshire/generate-string (db/find-todo id))}))

(defn insert-handler [request]
  (clojure.pprint/pprint (select-keys (-> request :json-params) [:name :status]))
  (let [todo (select-keys (-> request :json-params) [:name :status])]
    {:status  200
     :headers {"Content-Type" "application/json"}
     :body    (cheshire/generate-string (db/insert-todo todo))}))

(defn update-handler [request]
  (clojure.pprint/pprint (-> request :path-params))
  (clojure.pprint/pprint (-> request :json-params))
  (let [id (Integer/parseInt (-> request :path-params :id))
        todo (select-keys (-> request :json-params) [:name :status])]
    {:status  200
     :headers {"Content-Type" "application/json"}
     :body    (cheshire/generate-string (db/update-todo id todo))}))

(defn delete-handler [request]
  (clojure.pprint/pprint (-> request :path-params))
  (let [id (Integer/parseInt (-> request :path-params :id))]
    {:status  200
     :headers {"Content-Type" "application/json"}
     :body    (cheshire/generate-string (db/delete-todo id))}))



