(ns ^:figwheel-always chess-game.core
  (:require [jayq.core :as jq]
            [om.core :as om :include-macros true]
            [chess-game.images :refer [get-images!]]
            [chess-game.board :as board]
            [chess-game.components :refer [surface]]
            [chess-game.controllers :refer [chessboard-controller]]))

(enable-console-print!)

(jq/document-ready
  (let [state (atom {:chessboard (board/make-standard-board)
                     :images (get-images!)
                     :selected []})]
    (swap! state assoc :chessboard-ch (chessboard-controller state))
    (om/root surface state
             {:target (.getElementById js/document "surface")})))
