package finatra

import java.util.Date

import com.twitter.finagle.http.Request
import com.twitter.finatra.http.routing.HttpRouter
import com.twitter.finatra.http.{Controller, HttpServer}

object BackendMain extends BackendServer

class BackendController extends Controller {
  get("/api") { request: Request =>
    new Date()
  }
}

class BackendServer extends HttpServer {

  override def defaultHttpServerName = "backend"

  override def defaultFinatraHttpPort = ":9001"

  override val disableAdminHttpServer = true

  override def configureHttp(router: HttpRouter) {
    router.add[BackendController]
  }
}
