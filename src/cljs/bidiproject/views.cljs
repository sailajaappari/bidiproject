(ns bidiproject.views
  (:require [re-frame.core :as re-frame]
            [ajax.core :as ajax]
            [bidiproject.routes :as routes]
            [reagent.core :as reagent]))

(defn success-handler [response]
  (re-frame/dispatch [:set-search-star-data (take 10 response)]))

(defn error-handler [{:keys [status status-text]}]
  (js/console.log (str status " " status-text)))

#_(defn get-data-from-server []
    (let [star (re-frame/subscribe [:star-rating])]
      (ajax/GET (str "http://192.168.0.117:8080/json/searchhotels.json"
                     "?star=" @star)
                {:response-format :json
                 :keywords? true
                 :handler success-handler
                 :error-handler error-handler})))

(defn get-data-from-server [pred]
  (js/console.log @pred)
  (ajax/GET (str "http://192.168.0.117:8080/json/searchhotels.json"
                 "?star=" @pred)
            {:response-format :json
             :keywords? true
             :handler success-handler
             :error-handler error-handler}))

#_(do (re-frame/dispatch
       [:set-star-rating
        (-> % .-target .-checked)])
      (get-data-from-server
       @(re-frame/subscribe [:star-rating])
       #_(-> % .-target .-checked)))

(defn starrating []
  (let [star (re-frame/subscribe [:star-rating])]
    (fn []
      [:div
       [:input {:type "checkbox"
                :on-click (fn [e]
                            (re-frame/dispatch
                             [:set-star-rating
                              (-> e .-target .-checked)])
                            (reagent/flush)
                            (get-data-from-server star))}]])))


(defn display []
  (let [data (re-frame/subscribe [:data])]
    (fn []
      [:div
       [:div "HotelName: " (:hotelName @data)]])))

(defn home-panel []
  (let [name (re-frame/subscribe [:name])]
    (fn []
      [:div (str "Hello from " @name ". This is the Home Page.")
       [:div "display hotel names based on star"]
       [starrating]
       [:div [:a {:href (routes/url-for :about)} "go to About Page"]]])))

(defn about-panel []
  (fn []
    [:div "This is the About Page."
     [:div [:a {:href (routes/url-for :home)} "go to Home Page"]]]))

;; --------------------
(defmulti panels identity)
(defmethod panels :home-panel [] [home-panel])
(defmethod panels :about-panel [] [about-panel])
(defmethod panels :default [] [:div])

(defn main-panel []
  (let [active-panel (re-frame/subscribe [:active-panel])]
    (fn []
      (panels @active-panel))))
