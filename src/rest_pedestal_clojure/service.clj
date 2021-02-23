(ns rest-pedestal-clojure.service
  (:require [io.pedestal.http :as http]
            [io.pedestal.http.route :as route]
            [io.pedestal.http.body-params :as body-params]
            [ring.util.response :as ring-resp]
            [clojure.data.json :as json]
            [clojure.java.jdbc :as jdbc]))


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

(defn hello-index-handler [request]
  {:status  200
   :headers {"Content-Type" "application/json"}
   :body    "Safe."})

(defn todo-index-handler [request]
  (clojure.pprint/pprint (find-todo))
  {:status  200
   :headers {"Content-Type" "application/json"}
   :body    (json/write-str {:msg "hello"})})

(defn todo-find-handler [request]
  (clojure.pprint/pprint (-> request :path-params))
  {:status  200
   :headers {"Content-Type" "application/json"}
   :body    "todo-find-handler"})

(defn todo-insert-handler [request]
  {:status  200
   :headers {"Content-Type" "application/json"}
   :body    "todo-insert-handler"})

(defn todo-update-handler [request]
  (clojure.pprint/pprint (-> request :path-params))
  {:status  200
   :headers {"Content-Type" "application/json"}
   :body    "todo-update-handler"})

(defn todo-delete-handler [request]
  (clojure.pprint/pprint (-> request :path-params))
  {:status  200
   :headers {"Content-Type" "application/json"}
   :body    "todo-delete-handler"})

;; Defines "/" and "/about" routes with their associated :get handlers.
;; The interceptors defined after the verb map (e.g., {:get home-page}
;; apply to / and its children (/about).
(def common-interceptors [(body-params/body-params) http/html-body])

;; Tabular routes
(def routes #{["/"         :get     (conj common-interceptors `hello-index-handler)]
              ["/todo"     :get     (conj common-interceptors `todo-index-handler)]
              ["/todo/:id" :get     (conj common-interceptors `todo-find-handler)]
              ["/todo"     :post    (conj common-interceptors `todo-insert-handler)]
              ["/todo/:id" :put     (conj common-interceptors `todo-update-handler)]
              ["/todo/:id" :delete  (conj common-interceptors `todo-delete-handler)]})

;; Map-based routes
;(def routes `{"/" {:interceptors [(body-params/body-params) http/html-body]
;                   :get home-page
;                   "/about" {:get about-page}}})

;; Terse/Vector-based routes
;(def routes
;  `[[["/" {:get home-page}
;      ^:interceptors [(body-params/body-params) http/html-body]
;      ["/about" {:get about-page}]]]])


;; Consumed by rest-pedestal-clojure.server/create-server
;; See http/default-interceptors for additional options you can configure
(def service {:env :prod
              ;; You can bring your own non-default interceptors. Make
              ;; sure you include routing and set it up right for
              ;; dev-mode. If you do, many other keys for configuring
              ;; default interceptors will be ignored.
              ;; ::http/interceptors []
              ::http/routes routes

              ;; Uncomment next line to enable CORS support, add
              ;; string(s) specifying scheme, host and port for
              ;; allowed source(s):
              ;;
              ;; "http://localhost:8080"
              ;;
              ;;::http/allowed-origins ["scheme://host:port"]

              ;; Tune the Secure Headers
              ;; and specifically the Content Security Policy appropriate to your service/application
              ;; For more information, see: https://content-security-policy.com/
              ;;   See also: https://github.com/pedestal/pedestal/issues/499
              ;;::http/secure-headers {:content-security-policy-settings {:object-src "'none'"
              ;;                                                          :script-src "'unsafe-inline' 'unsafe-eval' 'strict-dynamic' https: http:"
              ;;                                                          :frame-ancestors "'none'"}}

              ;; Root for resource interceptor that is available by default.
              ::http/resource-path "/public"

              ;; Either :jetty, :immutant or :tomcat (see comments in project.clj)
              ;;  This can also be your own chain provider/server-fn -- http://pedestal.io/reference/architecture-overview#_chain_provider
              ::http/type :jetty
              ;;::http/host "localhost"
              ::http/port 3000
              ;; Options to pass to the container (Jetty)
              ::http/container-options {:h2c? true
                                        :h2? false
                                        ;:keystore "test/hp/keystore.jks"
                                        ;:key-password "password"
                                        ;:ssl-port 8443
                                        :ssl? false
                                        ;; Alternatively, You can specify you're own Jetty HTTPConfiguration
                                        ;; via the `:io.pedestal.http.jetty/http-configuration` container option.
                                        ;:io.pedestal.http.jetty/http-configuration (org.eclipse.jetty.server.HttpConfiguration.)
                                        }})
