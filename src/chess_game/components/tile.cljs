(ns chess-game.components.tile
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [cljs.core.async :refer [>!]]
            [om.core :as om :include-macros true]
            [om-tools.core :refer-macros [defcomponent]]
            [om-tools.dom :as dom :include-macros true]
            [chess-game.config :as config]))

(defcomponent image
  "React doesn't support svg image out of the box."
  [{:keys [href x y width height]} owner]
  (render-state [_ _]
    (let [html (str "<image x='" x "' y='" y "'"
                    "width='" width "' height='" height "'"
                    "xlink:href='" href "'/>")]
      (dom/g {:dangerouslySetInnerHTML {:__html html}}))))

(defn tile-color
  [x y selected]
  (let [color-name (if (even? (+ x y)) :white :black)]
    (get-in config/colors [color-name selected])))

(defcomponent tile
  [{:keys [i j chessman selected msg-ch]} owner]
  (render-state [_ _]
    (let [x (* i config/tile-size)
          y (* j config/tile-size)
          width config/tile-size
          height config/tile-size]
      (dom/g {:onClick #(go (>! msg-ch [:tile-clicked i j]))}
             (dom/rect {:x x
                        :y y
                        :width width
                        :height height
                        :fill (tile-color i j selected)})
             (when chessman
               (om/build image {:href chessman
                                :x x
                                :y y
                                :width width
                                :height height}))))))
