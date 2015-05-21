(ns chess-game.components
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [cljs.core.async :refer [>!]]
            [om.core :as om :include-macros true]
            [om-tools.core :refer-macros [defcomponent]]
            [om-tools.dom :as dom :include-macros true]
            [chess-game.config :as config]
            [chess-game.drawing :as drawing]))

(defcomponent image
  "React doesn't support svg image out of the box."
  [{:keys [href x y width height]} owner]
  (render-state [_ _]
    (let [html (str "<image x='" x "' y='" y "'"
                    "width='" width "' height='" height "'"
                    "xlink:href='" href "'/>")]
      (dom/g {:dangerouslySetInnerHTML {:__html html}}))))

(defcomponent chessboard-tile
  [{:keys [i j chessman selected chessboard-ch]} owner]
  (render-state [_ _]
    (let [x (* i config/tile-size)
          y (* j config/tile-size)
          width config/tile-size
          height config/tile-size
          is-selected (= selected [i j])]
      (dom/g {:onClick #(go (>! chessboard-ch [i j]))}
             (dom/rect {:x x
                        :y y
                        :width width
                        :height height
                        :fill (drawing/tile-fill-color i j is-selected)})
             (when chessman
               (om/build image {:href chessman
                                :x x
                                :y y
                                :width width
                                :height height}))))))

(defcomponent chessboard
  [{:keys [chessboard images selected chessboard-ch]} owner]
  (render-state [_ _]
    (dom/g (om/build-all chessboard-tile
             (for [i (range 0 config/board-tiles-x)
                   j (range 0 config/board-tiles-y)
                   :let [{:keys [color type]} (get chessboard [i j])
                         chessman (get-in images [color type])]]
               {:i i
                :j j
                :chessman chessman
                :selected selected
                :chessboard-ch chessboard-ch})))))

(defcomponent surface
  [props owner]
  (render-state [_ _]
    (dom/svg {:width (* config/tile-size config/board-tiles-x)
              :height (* config/tile-size config/board-tiles-y)}
             (om/build chessboard props))))
