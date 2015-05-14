(ns chess-game.images-test
  (:require [cljs.test :refer-macros [deftest is]]
            [clj-di.test :refer-macros [with-registered]]
            [quil.core :as q]
            [chess-game.images :as img]))

(deftest test-load-image!
  (with-redefs [q/load-image (fn [src]
                               (is (= src "image.png"))
                               :image)
                q/resize (fn [img w h]
                           (is (= img :image))
                           (is (= w h 120)))]
    (img/load-image! "image.png")))

(deftest test-get-images!
  (with-redefs [img/load-image! identity]
    (is (= (img/get-images!)
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
                    :queen "/images/black-queen.png"}}))))

(deftest test-draw-image-on-grid!
  (with-redefs [q/image (fn [img x y]
                          (is (= img :image))
                          (is (= x 240))
                          (is (= y 480)))]
    (img/draw-image-on-grid! :image [2 4])))

(deftest test-get-image-for-chessman
  (with-registered [:images {:white {:pawn :white-pawn-img}
                             :black {:king :black-king-img}}]
    (is (= (img/get-image-for-chessman {:color :white
                                        :type :pawn})
           :white-pawn-img))
    (is (= (img/get-image-for-chessman {:color :black
                                        :type :king})
           :black-king-img))))

(deftest test-draw-chessman!
  (with-registered [:images {:white {:pawn :white-pawn-img}}]
    (with-redefs [img/draw-image-on-grid! (fn [image pos]
                                            (is (= image :white-pawn-img))
                                            (is (= pos [2 3])))]
      (img/draw-chessman! {:color :white
                           :type :pawn}
                          [2 3]))))
