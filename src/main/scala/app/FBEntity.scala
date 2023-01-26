package app

trait FBEntity {
  def update(): Unit

  def render(): Unit
}
