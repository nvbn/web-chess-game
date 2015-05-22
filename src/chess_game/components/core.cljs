(ns chess-game.components.core
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [cljs.core.async :refer [>!]]
            [om.core :as om :include-macros true]
            [om-tools.core :refer-macros [defcomponent]]
            [om-tools.dom :as dom :include-macros true]
            [chess-game.config :as config]
            [chess-game.components.chessboard :refer [chessboard]]))

(defcomponent message
  [msg _]
  (render-state [_ _]
    (dom/text {:x config/tile-size
               :y config/tile-size
               :fill "red"
               :style {:font-size "60pt"}}
              msg)))

(defcomponent surface
  [props _]
  (render-state [_ _]
    (dom/svg {:width (* config/tile-size config/board-tiles-x)
              :height (* config/tile-size config/board-tiles-y)}
             (om/build chessboard props)
             (when (:win props)
               (om/build message "You're win!")))))

(defn init-components!
  [state target]
  (om/root surface state {:target target}))
