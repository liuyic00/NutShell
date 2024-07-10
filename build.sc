import mill._, scalalib._
import coursier.maven.MavenRepository

object ivys {
  val scala = "2.12.17"
  val chiselCrossVersions = Map(
    "3.6.0" -> (ivy"edu.berkeley.cs::chisel3:3.6.0", ivy"edu.berkeley.cs:::chisel3-plugin:3.6.0"),
  )
}

trait CommonModule extends ScalaModule {
  override def scalaVersion = ivys.scala
}

trait HasChiselCross extends ScalaModule with Cross.Module[String]{
  override def repositoriesTask = T.task {
    super.repositoriesTask() ++ Seq(
      MavenRepository("https://oss.sonatype.org/content/repositories/snapshots")
    )
  }
  override def ivyDeps = Agg(ivys.chiselCrossVersions(crossValue)._1)
  override def scalacPluginIvyDeps = Agg(ivys.chiselCrossVersions(crossValue)._2)
}

trait HasRiscvSpecCore extends ScalaModule with Cross.Module[String]{
  override def repositoriesTask = T.task {
    super.repositoriesTask() ++ Seq(
      MavenRepository("https://s01.oss.sonatype.org/content/repositories/snapshots")
    )
  }
  override def ivyDeps = Agg(ivy"cn.ac.ios.tis::riscvspeccore:1.1-SNAPSHOT")
}

trait HasChiselTests extends SbtModule {
  object test extends SbtModuleTests with TestModule.ScalaTest {
    override def ivyDeps = Agg(ivy"edu.berkeley.cs::chiseltest:0.6.0")
  }
}

trait CommonNS extends SbtModule with CommonModule with HasChiselCross with HasRiscvSpecCore

object difftest extends Cross[CommonNS](ivys.chiselCrossVersions.keys.toSeq){
  override def millSourcePath = os.pwd / "difftest"
}

object chiselModule extends Cross[ChiselModule](ivys.chiselCrossVersions.keys.toSeq)

trait ChiselModule extends CommonNS with Cross.Module[String] with HasChiselTests {
  override def millSourcePath = os.pwd

  override def moduleDeps = super.moduleDeps ++ Seq(
    difftest(crossValue)
  )
}

object generator extends Cross[Generator](ivys.chiselCrossVersions.keys.toSeq)

trait Generator extends CommonNS with HasChiselTests with Cross.Module[String] {
  private val directory = if (crossValue.startsWith("3")) "chisel3" else "chisel"
  override def millSourcePath = os.pwd / "generator" / directory

  override def moduleDeps = super.moduleDeps ++ Seq(
    chiselModule(crossValue)
  )
}