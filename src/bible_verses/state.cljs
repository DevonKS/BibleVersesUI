(ns bible-verses.state
  (:require [om.next :as om]
            [re-natal.support :as sup]))

(defonce app-state (atom {:app/msg "Hello Clojure in iOS and Android!"
                          :app/toolbar {:toolbar/title "Bible Verses"
                                        :toolbar/search-text "Testing"}
                          :app/list {:list/list-items [{:leftElement "person"
                                                        :centerElement {:primaryText "Center element as an object"
                                                                        :secondaryText "Subtext"}
                                                        :rightElement "info"}
                                                       {:leftElement "person"
                                                        :centerElement {:primaryText "Lorem ipsum dolor sit amet, consectetur adipiscing elit"
                                                                        :secondaryText "Pellentesque commodo ultrices diam. Praesent in ipsum"}
                                                        :rightElement "info"}]}}))

(defmulti read om/dispatch)
(defmethod read :default
           [{:keys [state]} k _]
           (let [st @state]
                (if-let [[_ v] (find st k)]
                        {:value v}
                        {:value :not-found})))

(defonce reconciler
         (om/reconciler
           {:state        app-state
            :parser       (om/parser {:read read})
            :root-render  sup/root-render
            :root-unmount sup/root-unmount}))
