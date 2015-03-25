(ns chess-game.images
  (:require [quil.core :as q]
            [chess-game.config :as config]))

;; White Chessmen
(def white-pawn-image (atom nil))
(def white-king-image (atom nil))
(def white-bishop-image (atom nil))
(def white-knight-image (atom nil))
(def white-rook-image (atom nil))
(def white-queen-image (atom nil))

;; Black Chessmen
(def black-pawn-image (atom nil))
(def black-king-image (atom nil))
(def black-bishop-image (atom nil))
(def black-knight-image (atom nil))
(def black-rook-image (atom nil))
(def black-queen-image (atom nil))

;; TODO: Consider exporting this to a more general framework
(defn load-image [file-name]
  "Load image for chessmen"
  (let [image (q/load-image file-name)]
    (q/resize image config/tile-size config/tile-size)
    image))

(defn setup-images []
  "Set up the chessmen images"

  ;; White Chessmen
  (reset! white-pawn-image (load-image "/images/white-pawn.png"))
  (reset! white-king-image (load-image "/images/white-king.png"))
  (reset! white-bishop-image (load-image "/images/white-bishop.png"))
  (reset! white-knight-image (load-image "/images/white-knight.png"))
  (reset! white-rook-image (load-image "/images/white-rook.png"))
  (reset! white-queen-image (load-image "/images/white-queen.png"))

  ;; Black Chessmen
  (reset! black-pawn-image (load-image "/images/black-pawn.png"))
  (reset! black-king-image (load-image "/images/black-king.png"))
  (reset! black-bishop-image (load-image "/images/black-bishop.png"))
  (reset! black-knight-image (load-image "/images/black-knight.png"))
  (reset! black-rook-image (load-image "/images/black-rook.png"))
  (reset! black-queen-image (load-image "/images/black-queen.png")))

(defn draw-image-on-grid [image x y]
  "Convenience method for drawing on a grid of size `tile-size`."
  (q/image image (* x config/tile-size) (* y config/tile-size)))

(defn black-image-for-chessman-type [type]
  "Return image for chessman type (Black)"
  (case type
    :pawn @black-pawn-image
    :king @black-king-image
    :knight @black-knight-image
    :queen @black-queen-image
    :rook @black-rook-image
    :bishop @black-bishop-image))

(defn white-image-for-chessman-type [type]
  "Return image for chessman type (White)"
  (case type
    :pawn @white-pawn-image
    :king @white-king-image
    :knight @white-knight-image
    :queen @white-queen-image
    :rook @white-rook-image
    :bishop @white-bishop-image))

(defn image-for-chessman-color-and-type [color type]
  "Returns image for chessman color and type"
  (cond
    (= color :black) (black-image-for-chessman-type type)
    (= color :white) (white-image-for-chessman-type type)))

(defn draw-chessman [chessman pos]
  (let [chessman-type (:type chessman)
        chessman-color (:color chessman)
        image (image-for-chessman-color-and-type chessman-color chessman-type)]
    (draw-image-on-grid image (first pos) (second pos))))
