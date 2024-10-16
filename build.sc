import mill._, scalalib._
import coursier.maven.MavenRepository

object ivys {
  val scala = "2.12.17"
  val chisel = ivy"edu.berkeley.cs::chisel3:3.6.0"
  val chiselPlugin = ivy"edu.berkeley.cs:::chisel3-plugin:3.6.0"
  val chiselTest = ivy"edu.berkeley.cs::chiseltest:0.6.0"
}

trait CommonModule extends ScalaModule {
  override def scalaVersion = ivys.scala
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