package top

import nutcore._

object EmitNutCore extends App {
  // config
  val s = (FormalSettings()) ++ (InOrderSettings())
  s.foreach { Settings.settings += _ }
  Settings.settings.toList.sortBy(_._1)(Ordering.String).foreach {
    case (f, v: Long) =>
      println(f + " = 0x" + v.toHexString)
    case (f, v) =>
      println(f + " = " + v)
  }

  import _root_.circt.stage.ChiselStage
  ChiselStage.emitSystemVerilogFile(
    new NutCore()(NutCoreConfig()),
    Array("--target-dir", "test_run_dir/Elaborate"),
    firtoolOpts = Array("-disable-all-randomization", "--emit-chisel-asserts-as-sva")
  )
}
