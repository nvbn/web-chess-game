(ns chess-game.environ)

(defonce environment (atom {}))

(defn get-env []
  "Return the current value of *environment*. This is the recommended way to access `*environment*`."
  @environment)

(defn get-in-env [keys]
  "Retreive one of the nested associated values."
  (get-in @environment keys))

(defn assoc-in-env [keys value]
  "Update the environment."
  (swap! environment assoc-in keys value))

(defn current-env-file []
  "A reference to the current environment file"
  nil)

(defn to-string []
  "A string representation of the state in a way a human can read."
  nil)

(defn to-file []
  "Save an environment to a file"
  nil)


(defn from-file []
  "Load an environment from file."
  nil)
