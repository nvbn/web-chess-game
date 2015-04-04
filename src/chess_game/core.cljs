(ns ^:figwheel-always chess-game.core
  (:require [quil.core :as q]
            [quil.middleware]
            [jayq.core :as jq]
            [clj-di.core :refer [get-dep register!]]
            [chess-game.config :as config]
            [chess-game.images :as images]
            [chess-game.environ :as env]
            [chess-game.board :as board]
            [chess-game.drawing :as drawing]))

(enable-console-print!)

(defn setup []
  "Initialize everything"
  (images/setup-images)

  ;; :chessboard
  ;; A hash-map representing the current chessboard
  ;;
  ;; :selected-tile
  ;; The currently selected tile, after being clicked
  (let [env (atom {})]
    (swap! env assoc
           :chessboard (board/make-standard-board)
           :selected-tile nil)
    (register! :env env)))

(defn selected-chessman [e]
  (let [selected-tile (:selected-tile e)
        chessboard (:chessboard e)]
    (get chessboard selected-tile)))

(defn draw []
  "Draw the sketch"
  (drawing/draw-checkered-board)
  (drawing/draw-chessmen))

(defn mark-selected-tile [event]
  "Mark the tile selected by the click event"
  (let [current (:selected-tile @(get-dep :env))
        x (.floor js/Math (/ (:x event) config/tile-size))
        y (.floor js/Math (/ (:y event) config/tile-size))
        selected (list x y)]
    (if (= current selected)
      (env/assoc-in-env [:selected-tile] nil)
      (env/assoc-in-env [:selected-tile] selected))))

(defn mouse-event-full []
  {:x (q/mouse-x)
   :y (q/mouse-y)
   :button (q/mouse-button)})

(defn on-mouse-pressed []
  "Handle mouse press"
  (let [event (mouse-event-full)]
    (println event)
    (mark-selected-tile event)))

(jq/document-ready
  (try (q/sketch :title "Chess board"
                 :size config/board-size
                 :host "canvas"
                 :setup setup
                 :mouse-pressed on-mouse-pressed
                 :draw draw)
       (catch :default e (.error js/console e))))
