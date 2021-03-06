package scalatra.i18n

import org.scalatra.scalate.ScalateSupport
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.scalatra.scalate.ScalatraRenderContext
import org.fusesource.scalate.Binding
import org.fusesource.scalate.RenderContext
import org.scalatra.ScalatraServlet
import org.scalatra.CookieSupport
import javax.servlet.ServletConfig
import org.fusesource.scalate.servlet.ServletTemplateEngine
import javax.servlet.FilterConfig
import org.fusesource.scalate.TemplateEngine

trait I18nScalateSupport extends ScalateSupport with I18nSupport {
  this: ScalatraServlet with CookieSupport =>

  /*
   * Binding done here seems to work all the time. 
   * 
   * If it were placed in createRenderContext, it wouldn't work for "view" templates
   * on first access. However, on subsequent accesses, it worked fine. 
   */
  before() {
    templateEngine.bindings ::= Binding("messages", classOf[Messages].getName, true, isImplicit = true)
  }
  
  /**
   * Added "messages" into the template context so it can be accessed like:
   * #{messages("hello")}
   */
  override protected def createRenderContext(req: HttpServletRequest = request, resp: HttpServletResponse = response): RenderContext = {
    val context = new ScalatraRenderContext(this, req, resp)
    context.attributes.update("messages", messages)
    context
  }
}
