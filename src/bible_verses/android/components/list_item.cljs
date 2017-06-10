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
  static om/IQuery
  (query [this]
         '[:left-element :center-element :right-element])

  Object
  (render [this]
          (let [{:keys [:left-element :center-element :right-element]} (om/props this)]
            (view {} ;; this empty view is required so that the items are displayed correctly
                  (material-ui-list-item {:divider true
                                          :leftElement            left-element
                                          :centerElement          center-element
                                          :rightElement           right-element
                                          :onLeftElementPress     #(alert "left element pressed")
                                          :onPress                #(alert "list element pressed")
                                          :onRightElementress     #(alert "Rigth element pressed")})))))

(def list-item (om/factory ListItem))
