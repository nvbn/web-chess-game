(ns chess-game.components.chessboard
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [cljs.core.async :refer [>!]]
            [om.core :as om :include-macros true]
            [om-tools.core :refer-macros [defcomponent]]
            [om-tools.dom :as dom :include-macros true]
            [chess-game.config :as config]
            [chess-game.components.tile :refer [tile]]))

(defn get-chessboard-items
  "Returns list of dicts for rendering chessboard."
  [chessboard selected msg-ch]
  (for [i (range 0 config/board-tiles-x)
        j (range 0 config/board-tiles-y)
        :let [{:keys [color type]} (get chessboard [i j])
              chessman (get-in config/images [color type])]]
    {:i i
     :j j
     :chessman chessman
     :selected (= selected [i j])
     :msg-ch msg-ch}))

(defcomponent chessboard
  [{:keys [chessboard selected msg-ch]} _]
  (render-state [_ _]
    (dom/g (om/build-all tile
             (get-chessboard-items chessboard selected msg-ch)))))
