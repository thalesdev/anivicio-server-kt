rootProject.name = "anivicio-server"

include("domain", "http-server", "shared", "adapter:mysql", "adapter:crypto")

project(":domain").projectDir = file("domain")
project(":http-server").projectDir = file("driver/http-server")
project(":adapter:mysql").projectDir = file("adapter/mysql")
project(":adapter:crypto").projectDir = file("adapter/crypto")