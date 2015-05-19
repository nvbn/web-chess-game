(ns ^:figwheel-always chess-game.core
  (:require [quil.core :as q]
            [quil.middleware]
            [jayq.core :as jq]
            [clj-di.core :refer [get-dep register!]]
            [chess-game.config :as config]
            [chess-game.images :refer [get-images!]]
            [chess-game.board :as board]
            [chess-game.drawing :as drawing]))

(enable-console-print!)

(defn setup! []
  "Initialize everything"
  ;; :chessboard
  ;; A hash-map representing the current chessboard
  ;;
  ;; :selected-tile
  ;; The currently selected tile, after being clicked
  (let [env (atom {})]
    (swap! env assoc
           :chessboard (board/make-standard-board)
           :selected-tile nil)
    (register! :env env
               :images (get-images!))))

(defn draw!
  []
  "Draw the sketch"
  (drawing/draw-checkered-board)
  (drawing/draw-chessmens!))

(defn mark-selected-tile!
  [{:keys [x y]}]
  "Mark the tile selected by the click event"
  (let [env (get-dep :env)
        {:keys [selected-tile chessboard]} @env
        selected (map #(.floor js/Math (/ % config/tile-size))
                      [x y])]
    (swap! env assoc
           :selected-tile (when-not (= selected-tile selected)
                            selected)
           :chessboard (board/update-board chessboard
                                           selected-tile
                                           selected))))

(defn mouse-event-full
  []
  {:x (q/mouse-x)
   :y (q/mouse-y)
   :button (q/mouse-button)})

(def counter (atom 1))

(defn on-mouse-pressed
  []
  "Handle mouse press"
  (mark-selected-tile! (mouse-event-full)))

(jq/document-ready
  (try (q/sketch :title "Chess board"
                 :size config/board-size
                 :host "canvas"
                 :setup setup!
                 :mouse-pressed on-mouse-pressed
                 :draw draw!)
       (catch :default e (.error js/console e))))
