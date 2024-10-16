import mill._, scalalib._
import coursier.maven.MavenRepository

object ivys {
  val scala = "2.13.14"
  val chisel = ivy"org.chipsalliance::chisel:6.4.0"
  val chiselPlugin = ivy"org.chipsalliance:::chisel-plugin:6.4.0"
  val chiselTest = ivy"edu.berkeley.cs::chiseltest:6.0.0"
}

trait CommonModule extends ScalaModule {
  override def scalaVersion = ivys.scala

  override def scalacOptions = Seq("-Ymacro-annotations")
}

trait HasChisel extends ScalaModule {
  override def ivyDeps = Agg(ivys.chisel)
  override def scalacPluginIvyDeps = Agg(ivys.chiselPlugin)
}

trait HasChiselTests extends SbtModule {
  object test extends SbtModuleTests with TestModule.ScalaTest {
    override def ivyDeps = Agg(ivys.chiselTest)
  }
}

trait CommonNS extends SbtModule with CommonModule with HasChisel

object riscvSpecCore extends CommonNS {
  override def millSourcePath = os.pwd / "riscv-spec-core"
}

object chiselModule extends CommonNS with HasChiselTests {
  override def millSourcePath = os.pwd

  override def moduleDeps = super.moduleDeps ++ Seq(
    riscvSpecCore
  )
}
