package app.game

trait Game {
  def init(): Unit

  def render(): Unit
  
  def update(): Unit
}
