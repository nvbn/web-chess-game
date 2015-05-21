(ns chess-game.controllers
  (:require-macros [cljs.core.async.macros :refer [go-loop]])
  (:require [cljs.core.async :refer [chan <!]]
            [chess-game.board :as board]))

(defn chessboard-controller
  [state]
  (let [ch (chan)]
    (go-loop [old-pos nil]
      (let [new-pos (<! ch)]
        (swap! state assoc :selected (if (= new-pos old-pos) [] new-pos))
        (swap! state update-in [:chessboard]
               board/update-board old-pos new-pos)
        (recur new-pos)))
    ch))
