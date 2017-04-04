(ns bible-verses.android.core
  (:require [om.next :as om :refer-macros [defui]]
            [re-natal.support :as sup]
            [bible-verses.state :as state]
            [bible-verses.android.components.toolbar :as toolbar]))

(set! js/window.React (js/require "react"))
(def ReactNative (js/require "react-native"))
(def ReactNativeMaterialUI (js/require "react-native-material-ui"))

(defn create-element [rn-comp opts & children]
      (apply js/React.createElement rn-comp (clj->js opts) children))

(def app-registry (.-AppRegistry ReactNative))
(def view (partial create-element (.-View ReactNative)))
(def text (partial create-element (.-Text ReactNative)))
(def image (partial create-element (.-Image ReactNative)))
(def touchable-highlight (partial create-element (.-TouchableHighlight ReactNative)))

(def theme-provider (partial create-element (.-ThemeProvider ReactNativeMaterialUI)))
(def color (.-COLOR ReactNativeMaterialUI))

(def logo-img (js/require "./images/cljs.png"))

(def ui-theme {:palette {:primaryColor (.-green500 color)
                         :accentColor (.-pink500 color)}})

(defn alert [title]
      (.alert (.-Alert ReactNative) title))

(defui AppRoot
       static om/IQuery
       (query [this]
              '[:app/msg :app/toolbar])
       Object
       (render [this]
               (let [{msg :app/msg toolbar-props :app/toolbar} (om/props this)]
                 (theme-provider {:uiTheme ui-theme}
                                 (view {}
                                       (toolbar/toolbar toolbar-props)
                                       (view {:style {:flexDirection "column" :margin 40 :alignItems "center"}}
                                        (text {:style {:fontSize 30 :fontWeight "100" :marginBottom 20 :textAlign "center"}} msg)
                                        (image {:source logo-img
                                                :style  {:width 80 :height 80 :marginBottom 30}})
                                        (touchable-highlight {:style   {:backgroundColor "#999" :padding 10 :borderRadius 5}
                                                              :onPress #(alert "HELLO!")}
                                                             (text {:style {:color "white" :textAlign "center" :fontWeight "bold"}} "press me"))))))))

(defonce RootNode (sup/root-node! 1))
(defonce app-root (om/factory RootNode))

(defn init []
      (om/add-root! state/reconciler AppRoot 1)
      (.registerComponent app-registry "BibleVerses" (fn [] app-root)))