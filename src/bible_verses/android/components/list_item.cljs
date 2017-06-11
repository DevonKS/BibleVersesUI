(ns bible-verses.android.components.list-item
  (:require [om.next :as om :refer-macros [defui]]))

(set! js/window.React (js/require "react"))
(def ReactNative (js/require "react-native"))
(def ReactNativeMaterialUI (js/require "react-native-material-ui"))

(defn create-element [rn-comp opts & children]
  (apply js/React.createElement rn-comp (clj->js opts) children))

(def material-ui-list-item (partial create-element (.-ListItem ReactNativeMaterialUI)))
(def view (partial create-element (.-View ReactNative)))

(defn alert [title]
  (.alert (.-Alert ReactNative) title))

(defui ListItem
  static om/Ident
  (ident [this {:keys [db/id]}]
         [:item/by-id id])

  static om/IQuery
  (query [this]
         '[:db/id :left-element :center-element :right-element])

  Object
  (render [this]
          (let [{:keys [:leftElement :centerElement :rightElement]} (om/props this)]
            (view {} ;; this empty view is required so that the items are displayed correctly
                  (material-ui-list-item {:divider true
                                          :leftElement            leftElement
                                          :centerElement          centerElement
                                          :rightElement           rightElement
                                          :onLeftElementPress     #(alert "left element pressed")
                                          :onPress                #(alert "list element pressed")
                                          :onRightElementress     #(alert "Rigth element pressed")})))))

(def list-item (om/factory ListItem))
