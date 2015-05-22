(ns chess-game.controllers.ai
  (:require-macros [cljs.core.async.macros :refer [go-loop]])
  (:require [cljs.core.async :refer [chan <! >!]]
            [cljs.core.match :refer-macros [match]]
            [chess-game.config :as config]))

(defn is-any-ai?
  [chessboard color]
  (->> chessboard
       vals
       (map :color)
       (filter #{color})
       seq))

(defn move-random-ai
  [chessboard color]
  (let [ais (filter #(-> % second :color (= color)) chessboard)
        [pos chessman] (rand-nth ais)
        ais-places (map first ais)
        moves (for [i (range 0 config/board-tiles-x)
                    j (range 0 config/board-tiles-y)
                    :when (not (get ais-places [i j]))]
                [i j])
        place (rand-nth moves)]
    (assoc chessboard
      pos nil
      place chessman)))

(defn ai-controller
  [state _ color]
  (let [ai-ch (chan)]
    (go-loop []
      (<! ai-ch)
      (if (is-any-ai? (:chessboard @state) color)
        (swap! state assoc
               :can-move true
               :chessboard (move-random-ai (:chessboard @state) color))
        (swap! state assoc :win true))
      (recur))
    ai-ch))
