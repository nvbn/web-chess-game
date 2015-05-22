(ns ^:figwheel-always chess-game.core
  (:require [cljs.core.async :refer [chan]]
            [chess-game.config :as config]
            [chess-game.components.core :refer [init-components!]]
            [chess-game.controllers.core :refer [init-router!]]))

(defn main
  []
  (let [msg-ch (chan)
        state (atom {:chessboard config/standard-board
                     :selected []
                     :msg-ch msg-ch
                     :can-move true})]
    (init-router! msg-ch state :white :black)
    (init-components! state (.getElementById js/document "surface"))))
