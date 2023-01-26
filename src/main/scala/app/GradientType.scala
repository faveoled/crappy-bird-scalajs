package app

enum GradientType {
  case Dawn, Day, Dusk, Night

  def next(): GradientType = {
    var bg = GradientType.Day
    val gradients = List(GradientType.Day, GradientType.Dusk, GradientType.Night, GradientType.Dawn)
    for (i <- gradients.indices) {
      if (this == gradients(i)) {
        if (i == gradients.length - 1) {
          bg = GradientType.Day
        } else {
          bg = gradients(i + 1)
        }
      }
    }
    bg
  }
}
