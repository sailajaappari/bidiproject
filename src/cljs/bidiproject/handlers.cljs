(ns bidiproject.handlers
    (:require [re-frame.core :as re-frame]
              [bidiproject.db :as db]))

(re-frame/reg-event-db
 :initialize-db
 (fn  [_ _]
   db/default-db))

(re-frame/reg-event-db
 :set-active-panel
 (fn [db [_ active-panel]]
   (assoc db :active-panel active-panel)))

(re-frame/reg-event-db
 :set-star-rating
 (fn [db [_ p]]
   (assoc db :star p)))

(re-frame/reg-event-db
 :set-search-star-data
 (fn [db [_ data]]
   (assoc db :data data)))
