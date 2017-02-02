package finatra

import javax.inject.Inject

import com.twitter.finagle.http.Request
import com.twitter.finatra.http.routing.HttpRouter
import com.twitter.finatra.http.{Controller, HttpServer}
import com.twitter.finatra.httpclient.modules.HttpClientModule
import com.twitter.finatra.httpclient.{HttpClient, RequestBuilder}

object FrontendMain extends FrontendServer

object BackendHttpClientModule extends HttpClientModule {
  override val dest = "localhost:9001"
}

class FrontendController @Inject()(backendClient: HttpClient) extends Controller {
  get("/") { request: Request =>
    backendClient.execute(RequestBuilder.get("/api"))
  }
}

class FrontendServer extends HttpServer {

  override def defaultHttpServerName = "frontend"

  override def defaultFinatraHttpPort = ":9000"

  override val modules = Seq(BackendHttpClientModule)

  override val disableAdminHttpServer = true

  override def configureHttp(router: HttpRouter) {
    router.add[FrontendController]
  }
}
