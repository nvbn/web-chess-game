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
  {:white {:pawn (load-image! "/images/white-pawn.png")
           :king (load-image! "/images/white-king.png")
           :bishop (load-image! "/images/white-bishop.png")
           :knight (load-image! "/images/white-knight.png")
           :rook (load-image! "/images/white-rook.png")
           :queen (load-image! "/images/white-queen.png")}
   :black {:pawn (load-image! "/images/black-pawn.png")
           :king (load-image! "/images/black-king.png")
           :bishop (load-image! "/images/black-bishop.png")
           :knight (load-image! "/images/black-knight.png")
           :rook (load-image! "/images/black-rook.png")
           :queen (load-image! "/images/black-queen.png")}})

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
