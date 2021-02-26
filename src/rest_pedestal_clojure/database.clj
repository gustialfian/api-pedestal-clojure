(ns rest-pedestal-clojure.database
  (:require [clojure.java.jdbc :as jdbc]))


(def pg-db {:dbtype "postgresql"
            :dbname "sandbox"
            :host "localhost"
            :port 6543
            :user "sandbox"
            :password "sandbox"
            :ssl false
            :sslfactory "org.postgresql.ssl.NonValidatingFactory"})

(defn find-todo 
  ([] (jdbc/query pg-db ["select * from todo"]))
  ([id] (jdbc/query pg-db ["select * from todo where id = ?" id])))

(defn insert-todo [todo]
  (jdbc/query pg-db 
    ["insert into todo (name, status) values (?, ?) returning *"
      (:name todo) (:status todo)]))

(defn update-todo [id todo] 
  (jdbc/query pg-db 
    ["update todo set name = ?, status = ? where id = ? returning *"
      (:name todo) (:status todo) id]))

(defn delete-todo [id]
  (jdbc/query pg-db ["delete from todo where id = ? returning *" id]))