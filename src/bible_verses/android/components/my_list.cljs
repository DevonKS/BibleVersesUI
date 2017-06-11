(ns bible-verses.android.components.my-list
  (:require [om.next :as om :refer-macros [defui]]
            [bible-verses.android.components.list-item :as list-item]
            [clojure.string :as string]))

(set! js/window.React (js/require "react"))
(def ReactNative (js/require "react-native"))
(def ReactNativeMaterialUI (js/require "react-native-material-ui"))

(defn create-element [rn-comp opts & children]
  (apply js/React.createElement rn-comp (clj->js opts) children))

(def scroll-view (partial create-element (.-ScrollView ReactNative)))
(def style {:container {:flex 1}})

(defn filter-items
  [items search-text]
  (if (string/blank? search-text)
    items
    (filter #(string/includes? (string/lower-case (get-in % [:centerElement :primaryText])) search-text) items)))

(defui MyList
  static om/IQueryParams
  (params [this]
          {:list-items (om/get-query list-item/ListItem)})

  static om/IQuery
  (query [this]
         '[{:list/list-items ?list-items} :toolbar/search-text])

  Object
  (render [this]
          (let [{:keys [toolbar/search-text list/list-items]} (om/props this)]
            (println "-------------------------------------------------------")
            (println (str "\"" search-text "\""))
            (println "-------------------------------------------------------")
              (scroll-view {:style (:container style)}
                        (map list-item/list-item (filter-items list-items search-text))))))

(def my-list (om/factory MyList))
