(ns ^:figwheel-always chess-game.core
  (:require [cljs.core.async :refer [chan]]
            [jayq.core :as jq]
            [chess-game.images :refer [get-images!]]
            [chess-game.board :as board]
            [chess-game.components :refer [init-components!]]
            [chess-game.controllers :refer [init-router!]]))

(enable-console-print!)

(jq/document-ready
  (let [msg-ch (chan)
        state (atom {:chessboard (board/make-standard-board)
                     :images (get-images!)
                     :selected []
                     :msg-ch msg-ch
                     :can-move true})]
    (init-router! msg-ch state :white :black)
    (init-components! state (.getElementById js/document "surface"))))
