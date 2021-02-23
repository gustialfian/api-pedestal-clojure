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

(defn find-todo []
  (jdbc/query pg-db
    ["select * from todo"]))

