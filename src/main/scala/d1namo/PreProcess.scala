package d1namo

object PreProcess {
  def main(args:Array[String])={
    //new EcgProcess().summarize("raw/2015_05_01-05_33_00_ECG.csv")
    //new BreathingProcess().summarize("raw/2015_05_01-05_33_00_Breathing.csv")
    new AccelerometerProcess().summarize("raw/2015_05_01-05_33_00_Accel.csv")
  }
}