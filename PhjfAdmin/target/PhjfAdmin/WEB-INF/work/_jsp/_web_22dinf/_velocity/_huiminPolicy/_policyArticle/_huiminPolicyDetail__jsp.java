/*
 * JSP generated by Resin-4.0.47 (built Thu, 03 Dec 2015 10:34:34 PST)
 */

package _jsp._web_22dinf._velocity._huiminPolicy._policyArticle;
import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.http.*;

public class _huiminPolicyDetail__jsp extends com.caucho.jsp.JavaPage
{
  private static final java.util.HashMap<String,java.lang.reflect.Method> _jsp_functionMap = new java.util.HashMap<String,java.lang.reflect.Method>();
  private boolean _caucho_isDead;
  private boolean _caucho_isNotModified;
  private com.caucho.jsp.PageManager _jsp_pageManager;
  
  public void
  _jspService(javax.servlet.http.HttpServletRequest request,
              javax.servlet.http.HttpServletResponse response)
    throws java.io.IOException, javax.servlet.ServletException
  {
    javax.servlet.http.HttpSession session = request.getSession(true);
    com.caucho.server.webapp.WebApp _jsp_application = _caucho_getApplication();
    com.caucho.jsp.PageContextImpl pageContext = _jsp_pageManager.allocatePageContext(this, _jsp_application, request, response, null, session, 8192, true, false);

    TagState _jsp_state = null;

    try {
      _jspService(request, response, pageContext, _jsp_application, session, _jsp_state);
    } catch (java.lang.Throwable _jsp_e) {
      pageContext.handlePageException(_jsp_e);
    } finally {
      _jsp_pageManager.freePageContext(pageContext);
    }
  }
  
  private void
  _jspService(javax.servlet.http.HttpServletRequest request,
              javax.servlet.http.HttpServletResponse response,
              com.caucho.jsp.PageContextImpl pageContext,
              javax.servlet.ServletContext application,
              javax.servlet.http.HttpSession session,
              TagState _jsp_state)
    throws Throwable
  {
    javax.servlet.jsp.JspWriter out = pageContext.getOut();
    final javax.el.ELContext _jsp_env = pageContext.getELContext();
    javax.servlet.ServletConfig config = getServletConfig();
    javax.servlet.Servlet page = this;
    javax.servlet.jsp.tagext.JspTag _jsp_parent_tag = null;
    com.caucho.jsp.PageContextImpl _jsp_parentContext = pageContext;
    response.setContentType("text/html; charset=UTF-8");

    out.write(_jsp_string0, 0, _jsp_string0.length);
    
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"";

    out.write(_jsp_string1, 0, _jsp_string1.length);
    pageContext.include("/WEB-INF/jsp/pub/basePhjfInclude.jsp", true);out.write(_jsp_string2, 0, _jsp_string2.length);
    _caucho_expr_0.print(out, _jsp_env, false);
    out.write(_jsp_string3, 0, _jsp_string3.length);
    _caucho_expr_1.print(out, _jsp_env, false);
    out.write(_jsp_string4, 0, _jsp_string4.length);
    _caucho_expr_2.print(out, _jsp_env, false);
    out.write(_jsp_string5, 0, _jsp_string5.length);
    _caucho_expr_3.print(out, _jsp_env, false);
    out.write(_jsp_string6, 0, _jsp_string6.length);
    _caucho_expr_4.print(out, _jsp_env, false);
    out.write(_jsp_string7, 0, _jsp_string7.length);
    _caucho_expr_5.print(out, _jsp_env, false);
    out.write(_jsp_string8, 0, _jsp_string8.length);
  }

  private com.caucho.make.DependencyContainer _caucho_depends
    = new com.caucho.make.DependencyContainer();

  public java.util.ArrayList<com.caucho.vfs.Dependency> _caucho_getDependList()
  {
    return _caucho_depends.getDependencies();
  }

  public void _caucho_addDepend(com.caucho.vfs.PersistentDependency depend)
  {
    super._caucho_addDepend(depend);
    _caucho_depends.add(depend);
  }

  protected void _caucho_setNeverModified(boolean isNotModified)
  {
    _caucho_isNotModified = true;
  }

  public boolean _caucho_isModified()
  {
    if (_caucho_isDead)
      return true;

    if (_caucho_isNotModified)
      return false;

    if (com.caucho.server.util.CauchoSystem.getVersionId() != 8324212715306645294L)
      return true;

    return _caucho_depends.isModified();
  }

  public long _caucho_lastModified()
  {
    return 0;
  }

  public void destroy()
  {
      _caucho_isDead = true;
      super.destroy();
    TagState tagState;
  }

  public void init(com.caucho.vfs.Path appDir)
    throws javax.servlet.ServletException
  {
    com.caucho.vfs.Path resinHome = com.caucho.server.util.CauchoSystem.getResinHome();
    com.caucho.vfs.MergePath mergePath = new com.caucho.vfs.MergePath();
    mergePath.addMergePath(appDir);
    mergePath.addMergePath(resinHome);
    com.caucho.loader.DynamicClassLoader loader;
    loader = (com.caucho.loader.DynamicClassLoader) getClass().getClassLoader();
    String resourcePath = loader.getResourcePathSpecificFirst();
    mergePath.addClassPath(resourcePath);
    com.caucho.vfs.Depend depend;
    depend = new com.caucho.vfs.Depend(appDir.lookup("WEB-INF/velocity/huiminPolicy/policyArticle/huiminPolicyDetail.jsp"), -690984789940153709L, false);
    _caucho_depends.add(depend);
    loader.addDependency(depend);
  }

  final static class TagState {

    void release()
    {
    }
  }

  public java.util.HashMap<String,java.lang.reflect.Method> _caucho_getFunctionMap()
  {
    return _jsp_functionMap;
  }

  public void caucho_init(ServletConfig config)
  {
    try {
      com.caucho.server.webapp.WebApp webApp
        = (com.caucho.server.webapp.WebApp) config.getServletContext();
      init(config);
      if (com.caucho.jsp.JspManager.getCheckInterval() >= 0)
        _caucho_depends.setCheckInterval(com.caucho.jsp.JspManager.getCheckInterval());
      _jsp_pageManager = webApp.getJspApplicationContext().getPageManager();
      com.caucho.jsp.TaglibManager manager = webApp.getJspApplicationContext().getTaglibManager();
      manager.addTaglibFunctions(_jsp_functionMap, "c", "http://java.sun.com/jsp/jstl/core");
      manager.addTaglibFunctions(_jsp_functionMap, "webTag", "/compent/jstl/webTag-Bootstrap.tld");
      com.caucho.jsp.PageContextImpl pageContext = new com.caucho.jsp.InitPageContextImpl(webApp, this);
      _caucho_expr_0 = com.caucho.jsp.JspUtil.createExpr(pageContext.getELContext(), "${data.title}");
      _caucho_expr_1 = com.caucho.jsp.JspUtil.createExpr(pageContext.getELContext(), "${data.plate_list}");
      _caucho_expr_2 = com.caucho.jsp.JspUtil.createExpr(pageContext.getELContext(), "${data.source}");
      _caucho_expr_3 = com.caucho.jsp.JspUtil.createExpr(pageContext.getELContext(), "${data.publish_date}");
      _caucho_expr_4 = com.caucho.jsp.JspUtil.createExpr(pageContext.getELContext(), "${data.content}");
      _caucho_expr_5 = com.caucho.jsp.JspUtil.createExpr(pageContext.getELContext(), "${data.source_url}");
    } catch (Exception e) {
      throw com.caucho.config.ConfigException.create(e);
    }
  }
  private static com.caucho.el.Expr _caucho_expr_0;
  private static com.caucho.el.Expr _caucho_expr_1;
  private static com.caucho.el.Expr _caucho_expr_2;
  private static com.caucho.el.Expr _caucho_expr_3;
  private static com.caucho.el.Expr _caucho_expr_4;
  private static com.caucho.el.Expr _caucho_expr_5;

  private final static char []_jsp_string8;
  private final static char []_jsp_string4;
  private final static char []_jsp_string2;
  private final static char []_jsp_string1;
  private final static char []_jsp_string7;
  private final static char []_jsp_string5;
  private final static char []_jsp_string0;
  private final static char []_jsp_string6;
  private final static char []_jsp_string3;
  static {
    _jsp_string8 = "\" target=\"_blank\">\u67e5\u770b\u539f\u6587</a>\r\n                </div>\r\n            </div>\r\n            <div class=\"row\" align=\"right\">\r\n                <a class=\"btn btn-danger\" href=\"/phjfht/api/v1/policy/goHuiminPolicyQueryPage\" ><i class=\"icon-share-alt icon-white\"></i>\u8fd4\u56de\u5217\u8868</a>\r\n            </div>\r\n        </form>\r\n    </div>\r\n    <!-- \u6570\u636e\u533a end -->\r\n</div>\r\n<!-- 		\u5e95\u90e8\u9ad8\u5ea6\u586b\u5145\u5757 -->\r\n<div class=\"zeoBottom\"></div>\r\n<!-- 		\u5e95\u90e8\u9ad8\u5ea6\u586b\u5145\u5757 \u7ed3\u675f-->\r\n</body>\r\n\r\n</html>\r\n".toCharArray();
    _jsp_string4 = "</span>\r\n                    </div>\r\n                    <div class=\"sourceInfo\">\r\n                        <span>\u6765\u81ea\u4e8e\uff1a".toCharArray();
    _jsp_string2 = "\r\n    <style>\r\n        .mainContent{\r\n            text-shadow: none;\r\n            padding:0 3%;\r\n        }\r\n        .newsTitle{\r\n            padding:0.8rem 0 1.2rem 0;\r\n        }\r\n        .newsTitle h5{\r\n            color: #2a2929;\r\n            font-size:1.2rem;\r\n        }\r\n        .sourceInfo{\r\n            color: #999;\r\n            font-size:0.7rem;\r\n            padding-top:0.5rem;\r\n        }\r\n        .sourceInfo span{\r\n            margin-right:0.5rem;\r\n        }\r\n        .contentBox{\r\n            color: #646464;\r\n            font-size:0.8rem;\r\n            line-height:1.5rem;\r\n        }\r\n\r\n        .a{\r\n            text-indent: 20px;\r\n            text-decoration: none;\r\n            color: #00a5ec;\r\n            font-size:0.7rem;\r\n        }\r\n    </style>\r\n    <script>\r\n        $(function(){\r\n            $(\".container-fluid\").animate({height:(document.documentElement.clientHeight - 10)})\r\n            window.onresize = function(){\r\n                $(\".container-fluid\").animate({height:(document.documentElement.clientHeight - 10)})\r\n            };\r\n        });\r\n    </script>\r\n\r\n</head>\r\n<body>\r\n<div class=\"container-fluid\" style=\"height: 650px; overflow-y: auto;\">\r\n    <!-- \u9762\u5305\u5c51\u5bfc\u822a  start -->\r\n    <div class=\"dreadcount\">\r\n        <span class=mrl14><i class=\"icon-list icon-red\"></i>\u65b0\u95fb\u7ba1\u7406 </span><span class=\"divider\">/</span>\r\n        <span><i class=\"icon-list icon-red\"></i>\u65b0\u95fb\u6587\u7ae0\u7ba1\u7406 </span><span class=\"divider\">/</span>\r\n        <span><i class=\"icon-list icon-red\"></i>\u9884\u89c8</span>\r\n    </div>\r\n    <!-- \u9762\u5305\u5c51\u5bfc\u822a  end -->\r\n    <!-- \u6dfb\u52a0\u4fe1\u606f\u6570\u636e\u533a start -->\r\n    <div class=\"row-fluid\">\r\n        <form id=\"mainForm\" class=\"form-horizontal alert alert-info fade in span12\">\r\n            <div class=\"mainContent\">\r\n                <div class=\"newsTitle\">\r\n                    <h5>".toCharArray();
    _jsp_string1 = "\r\n<!DOCTYPE html>\r\n<html lang=\"en\">\r\n<head>\r\n    <title>\u7cfb\u7edf</title>\r\n    ".toCharArray();
    _jsp_string7 = "\r\n                </div>\r\n                <div>\r\n                    <a class= \"a\" href=\"".toCharArray();
    _jsp_string5 = "</span>\r\n                        <span>".toCharArray();
    _jsp_string0 = "\r\n\r\n\r\n".toCharArray();
    _jsp_string6 = "</span>\r\n                    </div>\r\n                </div>\r\n                <div class=\"contentBox\">\r\n                    ".toCharArray();
    _jsp_string3 = "</h5>\r\n                    <div class=\"sourceInfo\">\r\n                        <span>\u6240\u5c5e\u677f\u5757\uff1a".toCharArray();
  }
}
