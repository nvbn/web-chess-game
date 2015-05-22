(ns chess-game.controllers.core
  (:require-macros [cljs.core.async.macros :refer [go-loop]])
  (:require [cljs.core.async :refer [chan <! >!]]
            [cljs.core.match :refer-macros [match]]
            [chess-game.controllers.ai :refer [ai-controller]]
            [chess-game.controllers.player :refer [player-controller]]))

(defn init-router!
  [ch state user-color ai-color]
  (let [chessboard-ch (player-controller state ch user-color)
        ai-ch (ai-controller state ch ai-color)]
    (go-loop []
      (match (<! ch)
        [:tile-clicked & pos] (>! chessboard-ch pos)
        [:ai-turn] (>! ai-ch [])
        [& msg] (println "Unknown message" msg))
      (recur))))
