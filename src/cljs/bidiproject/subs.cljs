(ns bidiproject.subs
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require [re-frame.core :as re-frame]
            [re-frame.subs :as subs]))

(re-frame/reg-sub
 :name
 (fn [db]
   (:name db)))

(re-frame/reg-sub
 :active-panel
 (fn [db _]
   (:active-panel db)))

(subs/register-raw
 :star-rating
 (fn [db _]
   (reaction (get @db :star))))

(subs/register-raw
 :data
 (fn [db _]
   (reaction (get @db :data))))
