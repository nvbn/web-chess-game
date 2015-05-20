(ns chess-game.images
  (:require [quil.core :as q]
            [clj-di.core :refer [get-dep]]
            [chess-game.config :as config]))

;; TODO: Consider exporting this to a more general framework
(defn load-image!
  [file-name]
  "Load image for chessmen"
  (let [image (q/load-image file-name)]
    (q/resize image config/tile-size config/tile-size)
    image))

(defn get-images!
  []
  "Set up the chessmen images"
  {:white {:pawn "/images/white-pawn.png"
           :king "/images/white-king.png"
           :bishop "/images/white-bishop.png"
           :knight "/images/white-knight.png"
           :rook "/images/white-rook.png"
           :queen "/images/white-queen.png"}
   :black {:pawn "/images/black-pawn.png"
           :king "/images/black-king.png"
           :bishop "/images/black-bishop.png"
           :knight "/images/black-knight.png"
           :rook "/images/black-rook.png"
           :queen "/images/black-queen.png"}})

(defn draw-image-on-grid!
  [image [x y]]
  "Convenience method for drawing on a grid of size `tile-size`."
  (q/image image (* x config/tile-size) (* y config/tile-size)))

(defn get-image-for-chessman
  [{:keys [color type]}]
  "Returns image for chessman color and type"
  (get-in (get-dep :images) [color type]))

(defn draw-chessman!
  [chessman pos]
  (draw-image-on-grid! (get-image-for-chessman chessman)
                       pos))
