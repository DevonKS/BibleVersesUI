(ns bible-verses.android.components.toolbar
  (:require [om.next :as om :refer-macros [defui]]
            [clojure.string :as string]))

(set! js/window.React (js/require "react"))
(def ReactNative (js/require "react-native"))
(def ReactNativeMaterialUI (js/require "react-native-material-ui"))

(defn create-element [rn-comp opts & children]
  (apply js/React.createElement rn-comp (clj->js opts) children))

(def view (partial create-element (.-View ReactNative)))
(def text (partial create-element (.-Text ReactNative)))
(def material-ui-toolbar (partial create-element (.-Toolbar ReactNativeMaterialUI)))

(defui Toolbar
  static om/IQuery
  (query [this]
         '[:toolbar/search-text :toolbar/title])

  Object
  (render [this]
          (let [{:keys [toolbar/search-text toolbar/title]} (om/props this)]
            (println "-------------------------------------------------------")
            (println (str "\"" search-text "\""))
            (println "-------------------------------------------------------")
            (material-ui-toolbar {:centerElement title
                                  :searchable    {:autoFocus   true
                                                  :placeholder "Search"
                                                  :onChangeText #(om/transact! this `[(toolbar/change-searchtext {:search-text ~(string/lower-case %)})])
                                                  :onSearchClosed #(om/transact! this '[(toolbar/change-searchtext {:search-text ""})])}}))))

(def toolbar (om/factory Toolbar))
