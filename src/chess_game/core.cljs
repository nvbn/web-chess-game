(ns ^:figwheel-always chess-game.core
  (:require [quil.core :as q]
            [quil.middleware]
            [jayq.core :as jq]
            [clj-di.core :refer [get-dep register!]]
            [om.core :as om :include-macros true]
            [om.dom :as dom]
            [chess-game.config :as config]
            [chess-game.images :refer [get-images!]]
            [chess-game.board :as board]
            [chess-game.drawing :as drawing]))

(enable-console-print!)

(defn image
  [{:keys [href x y width height]} owner]
  (reify
    om/IRenderState
    (render-state [_ _]
      (let [html (str "<image x='" x "' y='" y "'"
                      "width='" width "' height='" height "'"
                      "xlink:href='" href "'/>")]
        (dom/g #js {:dangerouslySetInnerHTML #js {:__html html}})))))

(defn chessboard-tile
  [{:keys [i j chessman selected]} owner]
  (reify
    om/IRenderState
    (render-state [_ _]
      (let [x (* i config/tile-size)
            y (* j config/tile-size)
            width config/tile-size
            height config/tile-size
            is-selected (= @selected [i j])]
        (dom/g #js {:onClick (fn [] (om/update! selected [i j]))}
               (dom/rect #js {:x x
                              :y y
                              :width width
                              :height height
                              :fill (drawing/tile-fill-color i j is-selected)})
               (when chessman
                 (om/build image {:href chessman
                                    :x x
                                    :y y
                                    :width width
                                    :height height})))))))

(defn chessboard
  [{:keys [chessboard images selected]} owner]
  (reify
    om/IRenderState
    (render-state [_ _]
      (apply dom/g #js {}
             (for [i (range 0 config/board-tiles-x)
                   j (range 0 config/board-tiles-y)
                   :let [{:keys [color type]} (get chessboard (list i j))
                         chessman (get-in images [color type])]]
               (om/build chessboard-tile {:i i
                                          :j j
                                          :chessman chessman
                                          :selected selected}))))))

(defn surface
  [props owner]
  (reify
    om/IRenderState
    (render-state [_ _]
      (dom/svg #js {:width (* config/tile-size config/board-tiles-x)
                    :height (* config/tile-size config/board-tiles-y)}
               (om/build chessboard props)))))

(jq/document-ready
  (om/root surface (atom {:chessboard (board/make-standard-board)
                          :images (get-images!)
                          :selected []})
           {:target (.getElementById js/document "surface")}))
