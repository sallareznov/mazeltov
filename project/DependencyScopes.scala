  import sbt._

  object DependencyScopes {

    def compileDependencies(dependencies: ModuleID*): List[ModuleID] = withScope(Compile, dependencies)

    def providedDependencies(dependencies: ModuleID*): List[ModuleID] = withScope(Provided, dependencies)

    def testDependencies(dependencies: ModuleID*): List[ModuleID] = withScope(Test, dependencies)

    def itTestDependencies(dependencies: ModuleID*): List[ModuleID] = withScope(IntegrationTest, dependencies)

    def runtimeDependencies(dependencies: ModuleID*): List[ModuleID] = withScope(Runtime, dependencies)

    private def withScope(scope: Configuration, dependencies: Seq[ModuleID]): List[ModuleID] =
      dependencies.map(_ % scope).toList

  }

